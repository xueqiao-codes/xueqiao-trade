/*
 * order_delete_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_ORDER_DELETE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_ORDER_DELETE_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class OrderDeleteResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> delete_order;
    std::shared_ptr<CThostFtdcInputOrderActionField> action_field;
};

class OrderDeleteHandler : public ctpext::framework::CTPRequestWithCallback<OrderDeleteResp> {
public:
    OrderDeleteHandler(
            CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& delete_order);
    virtual ~OrderDeleteHandler() = default;

    virtual bool hasActureRsp() { return false; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<OrderDeleteResp>& getResponse() {
        return delete_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);

    virtual void OnRspOrderAction(CThostFtdcInputOrderActionField *pInputOrderAction
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual std::string description() {
        return "OrderDeleteHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> delete_order_;

    std::shared_ptr<OrderDeleteResp> delete_rsp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_ORDER_DELETE_HANDLER_H_ */
