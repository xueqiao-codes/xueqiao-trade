/*
 * es9_deal_manager.h
 *
 *  Created on: 2018年4月14日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_DEAL_MANAGER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_DEAL_MANAGER_H_

#include <atomic>
#include "es9_request_dispatcher.h"
#include "es9_order_insert_handler.h"
#include "es9_order_delete_handler.h"
#include "es9_order_sync_state_handler.h"
#include "es9_order_sync_trades_handler.h"
#include "es9_order_sync_state_batch_handler.h"
#include "TradeHostingUpsideEntry.h"
#include "es9_login_manager.h"
#include "es9_data_manager.h"
#include "es9_order_delete_processor.h"
#include "es9_order_insert_processor.h"
#include "es9_self_match_order_expired_cleaner.h"
#include "self_match_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9DealManager : public es9ext::framework::Es9TradeNotifyBase
            , public Es9OrderInsertProcessor
            , public Es9OrderDeleteProcessor
            , public IEs9DataManagerListener {
public:
    Es9DealManager(const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher
            , const std::shared_ptr<Es9LoginManager>& login_manager
            , const std::shared_ptr<Es9DataManager>& data_manager);
    virtual ~Es9DealManager();

    void allocOrderRef(::xueqiao::trade::hosting::HostingExecOrderRef& _return) throw(::platform::comm::ErrorInfo);
    void orderInsert(const ::xueqiao::trade::hosting::HostingExecOrder& insert_order) throw(::platform::comm::ErrorInfo);
    void orderDelete(const ::xueqiao::trade::hosting::HostingExecOrder& delete_order) throw(::platform::comm::ErrorInfo);

    void syncOrderState(const ::xueqiao::trade::hosting::HostingExecOrder& sync_order) throw(::platform::comm::ErrorInfo);
    void syncOrderTrades(const ::xueqiao::trade::hosting::HostingExecOrder& sync_order) throw(::platform::comm::ErrorInfo);
    void syncOrderStateBatch(const TSyncOrderStateBatchReq& batch_req) throw(::platform::comm::ErrorInfo);

    virtual void onEs9DataManagerInitStart();
    virtual void onEs9QryOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp);
    virtual void onEs9DataManagerInitFinished();

    virtual std::string chooseAccountNo(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order);
    virtual void handleOrderInsertResp(const std::shared_ptr<Es9OrderInsertResp>& resp);
    virtual void handleOrderDeleteResp(const std::shared_ptr<Es9OrderDeleteResp>& resp);

    virtual void OnRtnOrder(const ITapTrade::TapAPIOrderInfoNotice *info);
    virtual void OnRtnFill(const ITapTrade::TapAPIFillInfo *info);

private:
    void checkAccountSummaryValid(const ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary)
            throw(::platform::comm::ErrorInfo);
    void checkOrderInputExtContractSummary(const ::xueqiao::trade::hosting::ESunny9OrderInputExt& es9InputExt)
            throw(::platform::comm::ErrorInfo);
    void checkOrderInputExt(const ::xueqiao::trade::hosting::ESunny9OrderInputExt& es9InputExt)
                throw(::platform::comm::ErrorInfo);
    void checkOrderRef(const ::xueqiao::trade::hosting::ESunny9OrderRef& es9Ref) throw(::platform::comm::ErrorInfo);

    int64_t getExecOrderIdByOrderField(const ITapTrade::TapAPIOrderInfo& order_info
                    , const std::string& call_function) throw(::platform::comm::ErrorInfo);
    int64_t getExecOrderIdByFillField(const ITapTrade::TapAPIFillInfo& fill_info
                    , const std::string& call_function) throw(::platform::comm::ErrorInfo);

    bool order2NotifyStateInfo(const ITapTrade::TapAPIOrderInfo& order_info
                    , int64_t exec_order_id
                    , ::xueqiao::trade::hosting::HostingUpsideNotifyStateInfo& state_info);
    bool fill2TradeLegInfo(const ITapTrade::TapAPIFillInfo& fill_info
                    , ::xueqiao::trade::hosting::HostingExecTradeLegInfo& trade_leg_info);
    bool fill2TradeLegContractSummary(const ITapTrade::TapAPIFillInfo& fill_info
                    , ::xueqiao::trade::hosting::HostingExecOrderLegContractSummary& leg_contract_summary);

    std::string es9UsefulErrorText(const char* error_text) const;
    std::string es9HDateTime2StandardDateTime(const char* es9_date_time);

    void handleSyncOrderStateResp(const std::shared_ptr<Es9OrderSyncStateResp>& resp);
    void handleSyncOrderTradesResp(const std::shared_ptr<Es9OrderSyncTradesResp>& resp);
    void handleSyncOrderStateBatchResp(const std::shared_ptr<Es9OrderSyncStateBatchResp>& resp);

    void sendOrderDeleteFailedEvent(int64_t exec_order_id
                    , int32_t upside_error_code
                    , const std::string& upside_error_msg);

    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>
             processNotFoundOrder(const ITapTrade::TapAPIOrderInfo& order_info) throw(::platform::comm::ErrorInfo);
    int64_t processOrderSyncState(const ITapTrade::TapAPIOrderInfo& order_info
            , long sync_start_timestampms
            , long send_msg_exec_order_id = -1) throw(::platform::comm::ErrorInfo);
    void processFillSync(const ITapTrade::TapAPIFillInfo& fill_info
            , long sync_start_timestampms) throw(::platform::comm::ErrorInfo);

    void onRtnOrderProcess(const ITapTrade::TapAPIOrderInfo& order_info) throw(::platform::comm::ErrorInfo);
    void onRtnFillProcess(const ITapTrade::TapAPIFillInfo& trade_info) throw(::platform::comm::ErrorInfo);
    void syncOrders(const std::vector<ITapTrade::TapAPIOrderInfo>& order_infos
            , long sync_start_timestampms
            , bool notify_selfmatch_manager);

    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;
    std::shared_ptr<Es9LoginManager> login_manager_;
    std::shared_ptr<Es9DataManager> data_manager_;
    std::shared_ptr<SelfMatchManager<ITapTrade::TapAPIOrderInfo>> self_match_manager_;
    std::shared_ptr<Es9SelfMatchOrderExpiredCleaner> self_match_cleaner_;

    int64_t init_timestamp_us_ = 0;
    std::atomic_int last_order_ref_id_ = {1};
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_DEAL_MANAGER_H_ */
