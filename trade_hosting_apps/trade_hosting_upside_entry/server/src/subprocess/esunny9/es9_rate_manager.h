/*
 * es9_rate_manager.h
 *
 *  Created on: 2019年4月3日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_RATE_MANAGER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_RATE_MANAGER_H_

#include <mutex>
#include "base/thread_pool.h"
#include "es9_data_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9ContractRate {
    std::string contract_no;
    std::string strike_price;
    ITapTrade::TAPICallOrPutFlagType call_or_put_flag;

    std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryRsp> account_margin;
};

struct Es9CommodityRate {
    std::string exchange_no;
    ITapTrade::TAPICommodityType commodity_type;
    std::string commodity_no;

    std::shared_ptr<ITapTrade::TapAPIAccountFeeRentQryRsp> account_fee;
    std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryRsp> account_margin;

    std::vector<Es9ContractRate> contract_rates;
};

class Es9RateManager : public IEs9DataManagerListener {
public:
    Es9RateManager(const std::shared_ptr<Es9LoginManager>& login_manager
            , const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher);
    virtual ~Es9RateManager();

    std::shared_ptr<std::map<std::string, std::shared_ptr<Es9CommodityRate>>> getRates();


    virtual void onEs9DataManagerInitStart();
    virtual void onEs9QryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp);
    virtual void onEs9QryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp);
    virtual void onEs9DataManagerInitFinished();

private:
    void handleInitStart();
    void handleInitFinished();

    void handleQryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp);
    void handleQryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp);

    void sendQryAccountMargin();
    void onRspQryAccountMarginFinished(const std::shared_ptr<Es9QryAccountMarginResp>& account_margin_resp);

    void scheduleTask();
    void collationResult();

    std::string getKey(const ITapTrade::TapAPICommodityInfo& commodity);
    std::string getKey(const std::string& exchange_no
            , ITapTrade::TAPICommodityType commodity_type
            , const std::string& commodity_no);
    std::string getKey(const std::string& exchange_no
            , ITapTrade::TAPICommodityType commodity_type
            , const std::string& commodity_no
            , const std::string& contract_code
            , const std::string& strick_price
            , ITapTrade::TAPICallOrPutFlagType call_or_put_flag);

    std::shared_ptr<Es9LoginManager> login_manager_;
    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;
    std::shared_ptr<soldier::base::TaskThread> work_thread_;

    // 商品
    std::map<std::string, ITapTrade::TapAPICommodityInfo> commodities_;

    // 查询margin的时候，搜索到的商品下包含的合约编码
    std::map<std::string, std::set<std::string>> commodity_contracts_;

    std::set<ITapTrade::TAPICommodityType> waiting_commodity_types_;
    std::unique_ptr<ITapTrade::TAPICommodityType> qrying_commodity_type_;

    std::map<std::string, std::shared_ptr<ITapTrade::TapAPIAccountFeeRentQryRsp>> account_fees_;
    std::map<std::string, std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryRsp>> account_margins_;

    // 整理出来的结果
    std::mutex rates_lock_;
    std::shared_ptr<std::map<std::string, std::shared_ptr<Es9CommodityRate>>> commodity_rates_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_RATE_MANAGER_H_ */
