/*
 * qry_all_orders_handler.h
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_ORDERS_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_ORDERS_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryAllOrdersResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcOrderField> orders;
};

class QryAllOrdersHandler : public ctpext::framework::CTPRequestWithCallback<QryAllOrdersResp> {
public:
    QryAllOrdersHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info);
    virtual ~QryAllOrdersHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 60000; }

    virtual const std::shared_ptr<QryAllOrdersResp>& getResponse() {
        return all_orders_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryOrder(
            CThostFtdcOrderField *pOrder
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryAllOrdersHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<QryAllOrdersResp> all_orders_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_ORDERS_HANDLER_H_ */
