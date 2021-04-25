/*
 * trade_hosting_storage_api.cpp
 *
 *  Created on: 2018年2月4日
 *      Author: wangli
 */
#include <boost/lexical_cast.hpp>
#include <mutex>
#include "base/code_defense.h"
#include "errorinfo_helper.h"
#include "trade_hosting_storage_api.h"
#include "TradeHostingStorage_variables.h"
#include "TradeHostingDealing_variables.h"
#include "trade_hosting_storage_stub.h"
#include "trade_hosting_dealing_stub.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::storage::thriftapi;
using namespace xueqiao::trade::hosting::dealing::thriftapi;
using namespace apache::thrift;

static std::string storage_socket_file;
static std::string dealing_socket_file;

static void initStorageSocketFileIfNeeded() {
	if (storage_socket_file.empty()) {
		static std::mutex lock;
		lock.lock();
		if (storage_socket_file.empty()) {
			storage_socket_file.append("/data/run/service_")
							   .append(boost::lexical_cast<std::string>(TradeHostingStorage_service_key))
							   .append(".sock");
		}
		lock.unlock();
	}
}

static void initDealingSocketFileIfNeeded() {
    if (dealing_socket_file.empty()) {
        static std::mutex lock;
        lock.lock();
        if (dealing_socket_file.empty()) {
            dealing_socket_file.append("/data/run/service_")
                               .append(boost::lexical_cast<std::string>(TradeHostingDealing_service_key))
                               .append(".sock");
        }
        lock.unlock();
    }
}

std::shared_ptr<HostingTradeAccount> HostingTradeAccountAPI::getTradeAccount(
		int64_t trade_account_id) throw(ErrorInfo) {
	initStorageSocketFileIfNeeded();

	TradeHostingStorageStub stub;
	stub.setSocketFile(storage_socket_file);

	std::vector<HostingTradeAccount> trade_accounts;
	STUB_SYNC_INVOKE_ERRORINFO(stub, getTraddeAccount, trade_accounts, trade_account_id);

	if (trade_accounts.empty()) {
		return std::shared_ptr<HostingTradeAccount>();
	}
	return std::shared_ptr<HostingTradeAccount>(new HostingTradeAccount(trade_accounts[0]));
}

std::shared_ptr<BrokerAccessEntry> HostingTradeAccountAPI::getBrokerAccessEntry(
		int64_t trade_account_id) throw(ErrorInfo) {
	initStorageSocketFileIfNeeded();

	TradeHostingStorageStub stub;
	stub.setSocketFile(storage_socket_file);

	std::vector<BrokerAccessEntry> entries;
	STUB_SYNC_INVOKE_ERRORINFO(stub, getBrokerAccessEntry, entries, trade_account_id);

	if (entries.empty()) {
		return std::shared_ptr<BrokerAccessEntry>();
	}
	return std::shared_ptr<BrokerAccessEntry>(new BrokerAccessEntry(entries[0]));
}

void HostingTradeAccountAPI::setTradeAccountActive(
		int64_t trade_account_id) throw(ErrorInfo) {
	initStorageSocketFileIfNeeded();

	TradeHostingStorageStub stub;
	stub.setSocketFile(storage_socket_file);

	STUB_SYNC_INVOKE_ERRORINFO(stub, setTradeAccountActive, trade_account_id)
}

void HostingTradeAccountAPI::setTradeAccountInvalid(
		int64_t trade_account_id
		, const InvalidDescription& invalid_description) throw(ErrorInfo) {
	initStorageSocketFileIfNeeded();

	TradeAccountInvalidDescription thrift_invalid_description;
	thrift_invalid_description.__set_invalidErrorCode(invalid_description.invalid_error_code);
	thrift_invalid_description.__set_invalidReason(invalid_description.invalid_reason);
	thrift_invalid_description.__set_apiRetCode(invalid_description.api_ret_code);

	TradeHostingStorageStub stub;
	stub.setSocketFile(storage_socket_file);
	STUB_SYNC_INVOKE_ERRORINFO(stub, setTradeAccountInvalid, trade_account_id, thrift_invalid_description)
}

void HostingTradeAccountAPI::getAllTradeAccounts(
	std::vector<HostingTradeAccount>& trade_accounts) throw(ErrorInfo) {
	initStorageSocketFileIfNeeded();

	TradeHostingStorageStub stub;
	stub.setSocketFile(storage_socket_file);
	STUB_SYNC_INVOKE_ERRORINFO(stub, getAllTradeAccounts, trade_accounts)
}

int64_t HostingDealingAPI::createOrderId() throw(ErrorInfo) {
    initDealingSocketFileIfNeeded();
    TradeHostingDealingStub stub;
    stub.setSocketFile(dealing_socket_file);

    STUB_SYNC_INVOKE_NOARGS_ERRORINFO_RETURN(stub, createExecOrderId)
    CHECK(false);
    return -1;
}

int64_t HostingDealingAPI::getRunningExecOrderIdByOrderRef(
            const  HostingExecOrderTradeAccountSummary& accountSummary
            , const HostingExecOrderRef& orderRef ) throw(ErrorInfo) {
    initDealingSocketFileIfNeeded();
    TradeHostingDealingStub stub;
    stub.setSocketFile(dealing_socket_file);

    STUB_SYNC_INVOKE_ERRORINFO_RETURN(stub, getRunningExecOrderIdByOrderRef, accountSummary, orderRef)
    CHECK(false);
    return -1;
}

int64_t HostingDealingAPI::getRunningExecOrderIdByOrderDealID(
           const HostingExecOrderTradeAccountSummary& accountSummary
           , const HostingExecOrderDealID& dealID) throw(ErrorInfo) {
    initDealingSocketFileIfNeeded();
    TradeHostingDealingStub stub;
    stub.setSocketFile(dealing_socket_file);

    STUB_SYNC_INVOKE_ERRORINFO_RETURN(stub, getRunningExecOrderIdByOrderDealID, accountSummary, dealID)
    CHECK(false);
    return -1;
}




