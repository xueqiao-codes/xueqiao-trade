/*
 * order_sync_state_handler.h
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_HANDLER_H_


#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class OrderSyncStateResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order;
    bool sync_order_has_dealinfo = false;
    std::vector<CThostFtdcOrderField> resp_orders;
};

class OrderSyncStateHandler : public ctpext::framework::CTPRequestWithCallback<OrderSyncStateResp> {
public:
    OrderSyncStateHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order);

    virtual ~OrderSyncStateHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<OrderSyncStateResp>& getResponse() {
        return order_sync_state_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryOrder(
               CThostFtdcOrderField *pOrder
               , CThostFtdcRspInfoField *pRspInfo
               , int nRequestID
               , bool bIsLast);

    virtual std::string description() {
        return "OrderSyncStateHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order_;

    std::shared_ptr<OrderSyncStateResp> order_sync_state_resp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_HANDLER_H_ */
