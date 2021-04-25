/*
 * ctp_deal_manager.h
 *
 *  Created on: 2018年3月26日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_DEAL_MANAGER_H_
#define SRC_SUBPROCESS_CTP_CTP_DEAL_MANAGER_H_

#include "ctp_request_dispatcher.h"
#include "ctp_data_manager.h"
#include "TradeHostingUpsideEntry.h"
#include "order_insert_handler.h"
#include "order_delete_handler.h"
#include "order_sync_state_handler.h"
#include "order_sync_trades_handler.h"
#include "order_sync_state_batch_handler.h"
#include "self_match_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

/**
 *  负责下单管理的相关内容
 */
class CTPDealManager : public CThostFtdcTraderSpi, public IDataManagerListener {
public:
    CTPDealManager(
            const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
            , const std::shared_ptr<CTPLoginManager>& login_manager
            , const std::shared_ptr<CTPDataManager>& data_manager
            , const std::shared_ptr<CTPPositionManager>& position_manager);

    virtual ~CTPDealManager() = default;

    void orderInsert(const ::xueqiao::trade::hosting::HostingExecOrder& insert_order) throw (::platform::comm::ErrorInfo);
    void orderDelete(const ::xueqiao::trade::hosting::HostingExecOrder& delete_order) throw (::platform::comm::ErrorInfo);
    void syncOrderState(const ::xueqiao::trade::hosting::HostingExecOrder& sync_order) throw (::platform::comm::ErrorInfo);
    void syncOrderTrades(const  ::xueqiao::trade::hosting::HostingExecOrder& sync_order) throw (::platform::comm::ErrorInfo);
    void syncOrderStateBatch(const TSyncOrderStateBatchReq& batch_req) throw (::platform::comm::ErrorInfo);

    virtual void OnErrRtnOrderAction(CThostFtdcOrderActionField *pOrderAction, CThostFtdcRspInfoField *pRspInfo);
    virtual void OnRtnOrder(CThostFtdcOrderField *pOrder);
    virtual void OnRtnTrade(CThostFtdcTradeField *pTrade);

    virtual void onDataManagerInitStart();
    virtual void onQryOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp);
    virtual void onDataManagerInitFinished();

private:
    void checkAccountSummaryValid(const ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary)
        throw(::platform::comm::ErrorInfo);
    void checkOrderInputExtContractSummary(const ::xueqiao::trade::hosting::CTPOrderInputExt& ctpInputExt)
        throw(::platform::comm::ErrorInfo);
    void checkOrderInputExt(const ::xueqiao::trade::hosting::CTPOrderInputExt& ctpInputExt)
        throw(::platform::comm::ErrorInfo);
    void checkOrderRef(const ::xueqiao::trade::hosting::CTPOrderRef& ctpRef)
        throw(::platform::comm::ErrorInfo);

    std::string ctpString2Utf8(const TThostFtdcErrorMsgType& msg);
    std::string ctpDateTime2StandardDateTime(const TThostFtdcDateType& date
            , const TThostFtdcTimeType& time);
    std::string getCTPUsefulMsg(const std::string& msg);

    int64_t getExecOrderIdByOrderField(const CThostFtdcOrderField* pOrder
                , const std::string& call_function) throw(::platform::comm::ErrorInfo);
    int64_t getExecOrderIdByTradeField(const CThostFtdcTradeField *pTrade
                , const std::string& call_function) throw(::platform::comm::ErrorInfo);
    int64_t getExecOrderIdByInputActionField(const CThostFtdcInputOrderActionField* pInputOrderAction
                , const std::string& call_function) throw(::platform::comm::ErrorInfo);
    int64_t getExecOrderIdByActionField(const CThostFtdcOrderActionField *pOrderAction
                , const std::string& call_function) throw(::platform::comm::ErrorInfo);

    bool order2NotifyStateInfo(const CThostFtdcOrderField* pOrder
                    , int64_t exec_order_id
                    , ::xueqiao::trade::hosting::HostingUpsideNotifyStateInfo& state_info);
    bool trade2TradeLegInfo(const CThostFtdcTradeField* pTrade
                    , ::xueqiao::trade::hosting::HostingExecTradeLegInfo& trade_leg_info);
    bool trade2TradeLegContractSummary(const CThostFtdcTradeField* pTrade
                    , ::xueqiao::trade::hosting::HostingExecOrderLegContractSummary& leg_contract_summary);

    void sendOrderDeleteFailedEvent(int64_t exec_order_id
                , int32_t upside_error_code
                , const std::string& upside_error_msg);

    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>
         processNotFoundOrder(const CThostFtdcOrderField* pOrder) throw(::platform::comm::ErrorInfo);
    void handleOrderInsertRsp(const std::shared_ptr<OrderInsertResp>& rsp);
    void handleOrderDeleteRsp(const std::shared_ptr<OrderDeleteResp>& rsp);
    void handleSyncOrderStateRsp(const std::shared_ptr<OrderSyncStateResp>& rsp);
    void handleSyncTradesRsp(const std::shared_ptr<OrderSyncTradesResp>& rsp);
    void handleSyncOrderBatchStateRsp(const std::shared_ptr<OrderSyncStateBatchResp>& rsp);

    void onRtnOrderProcess(const CThostFtdcOrderField* pOrder) throw(::platform::comm::ErrorInfo);
    void onRtnTradeProcess(const CThostFtdcTradeField* pTrade) throw(::platform::comm::ErrorInfo);
    int64_t processOrderSyncState(const CThostFtdcOrderField *pOrder
            , long sync_start_timestampms
            , long send_msg_exec_order_id = -1) throw(::platform::comm::ErrorInfo);
    void processTradeSync(CThostFtdcTradeField* pTrade, long sync_start_timestampms) throw(::platform::comm::ErrorInfo);

    void syncOrders(const std::vector<CThostFtdcOrderField>& orders
            , long sync_start_timestampms
            , bool need_notify_position_and_self_match);

    std::shared_ptr<ctpext::framework::CTPRequestDispatcher> dispatcher_;
    std::shared_ptr<CTPLoginManager> login_manager_;
    std::shared_ptr<CTPDataManager> data_manager_;
    std::shared_ptr<CTPPositionManager> position_manager_;
    std::shared_ptr<SelfMatchManager<CThostFtdcOrderField>> self_match_manager_;

};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_CTP_DEAL_MANAGER_H_ */
