/*
 * qry_exchange_margin_rate_handler.h
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_EXCHANGE_MARGIN_RATE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_EXCHANGE_MARGIN_RATE_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryExchangeMarginRateResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcExchangeMarginRateField> rates;
};

class QryExchangeMarginRateHandler : public ctpext::framework::CTPRequestWithCallback<QryExchangeMarginRateResp> {
public:
    QryExchangeMarginRateHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryExchangeMarginRateField>& req_field);
    virtual ~QryExchangeMarginRateHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 10000; }

    virtual const std::shared_ptr<QryExchangeMarginRateResp>& getResponse() {
        return resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryExchangeMarginRate(CThostFtdcExchangeMarginRateField *pExchangeMarginRate
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryExchangeMarginRateHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryExchangeMarginRateField> req_field_;
    std::shared_ptr<QryExchangeMarginRateResp> resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_EXCHANGE_MARGIN_RATE_HANDLER_H_ */
