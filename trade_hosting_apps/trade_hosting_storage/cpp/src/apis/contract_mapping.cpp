/*
 * contract_mapping.cpp
 *
 *  Created on: 2017年11月30日
 *      Author: wileywang
 */
#include "contract_mapping.h"

#include <boost/lexical_cast.hpp>
#include <mutex>

#include "base/code_defense.h"
#include "base/hash.h"
#include "base/url_util.h"
#include "base/time_helper.h"
#include "thrift/protocol/TJSONProtocol.h"
#include "contract_online_dao_service_stub.h"
#include "ContractOnlineDaoService_variables.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace storage {
namespace api {

static const int NOT_FOUND_CACHE_TIMEMS = 5*60*1000; // 五分钟更新一次查询不存在的缓存

static std::unique_ptr<ContractMapping> s_instance;

static std::string contract_online_socket_file;

static void initContractOnlineSocketFileIfNeeded() {
    if (contract_online_socket_file.empty()) {
        static std::mutex lock;
        lock.lock();
        if (contract_online_socket_file.empty()) {
            contract_online_socket_file.append("/data/run/service_")
                               .append(boost::lexical_cast<std::string>(
                                       ::xueqiao::contract::online::dao::ContractOnlineDaoService_service_key))
                               .append(".sock");
        }
        lock.unlock();
    }
}


ContractMapping::ContractMapping(size_t max_cache_commodity_items)
    : commodity_mapping_cache_(max_cache_commodity_items) {
}

ContractMapping::~ContractMapping() {
}

void ContractMapping::lockCache() {
    while(std::atomic_flag_test_and_set_explicit(
                                &lock_flag_, std::memory_order_seq_cst));
}

void ContractMapping::unlockCache() {
    lock_flag_.clear();
}

void ContractMapping::setCache(const std::string& cache_key, std::shared_ptr<CommodityMappingEntry> mapping_entry) {
    std::shared_ptr<CommodityMappingCacheEntry> cache_entry(new CommodityMappingCacheEntry());
    cache_entry->cached_timestampms = soldier::base::NowInMilliSeconds();
    cache_entry->entry = mapping_entry;
    lockCache();
    commodity_mapping_cache_.put(cache_key, cache_entry);
    unlockCache();
}

std::string ContractMapping::getKey(const CommodityMappingOption& option) {
    if (option.brokerExchangeCode.empty()) {
        return "";
    }
    if (option.brokerCommodityCode.empty()) {
        return "";
    }
    if (option.brokerCommodityType.empty()) {
        return "";
    }
    std::string cache_key;
    cache_key.append(boost::lexical_cast<std::string>(option.brokerTechPlatform))
             .append("_").append(option.brokerExchangeCode)
             .append("_").append(option.brokerCommodityType)
             .append("_").append(option.brokerCommodityCode)
             .append("_").append(boost::lexical_cast<std::string>(option.sledBrokerId));
    return cache_key;
}

std::shared_ptr<CommodityMappingEntry> ContractMapping::getCache(const std::string& cache_key, bool& need_update) {
    lockCache();
    const std::shared_ptr<CommodityMappingCacheEntry>* cache_entry = commodity_mapping_cache_.get(cache_key);
    if (cache_entry != nullptr) {
        if ((*cache_entry)->entry) {
            unlockCache();
            need_update = false;
            return (*cache_entry)->entry;
        }
        if (soldier::base::NowInMilliSeconds() <= (*cache_entry)->cached_timestampms + NOT_FOUND_CACHE_TIMEMS) {
            unlockCache();
            need_update = false;
            return (*cache_entry)->entry;
        }
    }
    unlockCache();
    need_update = true;
    return std::shared_ptr<CommodityMappingEntry>();
}

std::shared_ptr<CommodityMappingEntry> ContractMapping::getCommodityMapping(
            const CommodityMappingOption& option) {
    std::string cache_key = getKey(option);
    if (cache_key.empty()) {
        APPLOG_WARN("Unaccept option={}", option);
        return std::shared_ptr<CommodityMappingEntry>();
    }
    bool need_update = false;
    std::shared_ptr<CommodityMappingEntry> cache_entry = getCache(cache_key, need_update);
    if (!need_update) {
        return cache_entry;
    }

    CHECK(!cache_entry);

    ::xueqiao::contract::online::dao::ContractOnlineDaoServiceStub stub;
    initContractOnlineSocketFileIfNeeded();
    stub.setSocketFile(contract_online_socket_file);

    ::xueqiao::contract::standard::ReqCommodityMappingOption mapping_option;
    mapping_option.__set_brokerEntryId(option.sledBrokerId);
    mapping_option.__set_commodityCode(option.brokerCommodityCode);
    mapping_option.__set_exchange(option.brokerExchangeCode);
    mapping_option.__set_commodityType(option.brokerCommodityType);
    mapping_option.__set_techPlatform(option.brokerTechPlatform);

    ::xueqiao::contract::standard::CommodityMappingPage mapping_page;
    try {
        STUB_SYNC_INVOKE(stub, reqCommodityMapping, mapping_page, mapping_option, 0, 10);
    } catch(::apache::thrift::TException& e) {
        APPLOG_ERROR("failed to found commodity mapping, e.what()={}", e.what());
        return std::shared_ptr<CommodityMappingEntry>();
    }

    if (mapping_page.page.empty()) {
        setCacheEmpty(cache_key);
        return std::shared_ptr<CommodityMappingEntry>();
    }

    if (mapping_page.page.size() > 1) {
        APPLOG_ERROR("mapping size > 1 for sledBrokerId={}, brokerCommodityCode={}"
                ", brokerExchangeCode={}, brokerCommodityType={}, brokerTechPlatform={}"
                , option.sledBrokerId, option.brokerCommodityCode
                , option.brokerExchangeCode, option.brokerCommodityType
                , option.brokerTechPlatform);
        return std::shared_ptr<CommodityMappingEntry>();
    }

    std::shared_ptr<CommodityMappingEntry> mapping_entry(new CommodityMappingEntry());
    mapping_entry->mapping = mapping_page.page[0];

    ::xueqiao::contract::standard::ReqSledCommodityOption commodity_option;
    std::vector<int32_t> commodity_ids;
    commodity_ids.push_back(mapping_entry->mapping.sledCommodityId);
    commodity_option.__set_sledCommodityIdList(commodity_ids);

    ::xueqiao::contract::standard::SledCommodityPage commodity_page;
    try {
        STUB_SYNC_INVOKE(stub, reqSledCommodity, commodity_page, commodity_option, 0, 10);
    } catch(::apache::thrift::TException& e) {
        APPLOG_ERROR("failed to found commodity for sledCommodityId={}, e.what()={}"
                , mapping_entry->mapping.sledCommodityId
                , e.what());
        return std::shared_ptr<CommodityMappingEntry>();
    }

    if (commodity_page.page.size() != 1) {
        APPLOG_ERROR("found {} commodity entry for sledCommodityId={}"
                , commodity_page.page.size(), mapping_entry->mapping.sledCommodityId);
        return std::shared_ptr<CommodityMappingEntry>();
    }

    mapping_entry->commodity = commodity_page.page[0];
    setCache(cache_key, mapping_entry);
    return mapping_entry;
}

}  // end namespace api
}  // end namespace storage
}  // end namespace hosting
}  // end namespace trade
} // end namespace xueqiao


