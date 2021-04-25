/*
 * order_sync_state_batch_handler.h
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_BATCH_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_BATCH_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"
#include "trade_hosting_upside_entry_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class OrderSyncStateBatchResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<TSyncOrderStateBatchReq> batch_req;
    std::vector<CThostFtdcOrderField> resp_orders;
};


class OrderSyncStateBatchHandler : public ctpext::framework::CTPRequestWithCallback<OrderSyncStateBatchResp> {
public:
    OrderSyncStateBatchHandler(
            CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::shared_ptr<TSyncOrderStateBatchReq>& batch_req);
    virtual ~OrderSyncStateBatchHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 30000; }

    virtual const std::shared_ptr<OrderSyncStateBatchResp>& getResponse() {
        return order_sync_state_batch_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryOrder(
            CThostFtdcOrderField *pOrder
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "OrderSyncStateBatchHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<TSyncOrderStateBatchReq> batch_req_;
    std::shared_ptr<OrderSyncStateBatchResp> order_sync_state_batch_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_STATE_BATCH_HANDLER_H_ */
