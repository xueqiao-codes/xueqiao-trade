/*
 * order_sync_trades_handler.h
 *
 *  Created on: 2018年3月26日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_TRADES_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_TRADES_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class OrderSyncTradesResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order;
    std::vector<CThostFtdcTradeField> trades;
};

class OrderSyncTradesHandler : public ctpext::framework::CTPRequestWithCallback<OrderSyncTradesResp> {
public:
    OrderSyncTradesHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::string& sync_instrument_id
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order);

    virtual ~OrderSyncTradesHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<OrderSyncTradesResp>& getResponse() {
        return order_sync_trades_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryTrade(CThostFtdcTradeField *pTrade
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual std::string description() {
        return "OrderSyncTradesHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::string sync_instrument_id_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order_;
    std::shared_ptr<OrderSyncTradesResp> order_sync_trades_rsp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_CTP_HANDLERS_ORDER_SYNC_TRADES_HANDLER_H_ */
