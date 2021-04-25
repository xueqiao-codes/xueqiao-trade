/*
 * qry_instrument_commission_rate_handler.h
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_COMMISSION_RATE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_COMMISSION_RATE_HANDLER_H_


#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryInstrumentCommissionRateResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInstrumentCommissionRateField> rates;
};

class QryInstrumentCommissionRateHandler : public ctpext::framework::CTPRequestWithCallback<QryInstrumentCommissionRateResp> {
public:
    QryInstrumentCommissionRateHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryInstrumentCommissionRateField>& req_field);
    virtual ~QryInstrumentCommissionRateHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 10000; }

    virtual const std::shared_ptr<QryInstrumentCommissionRateResp>& getResponse() {
        return resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInstrumentCommissionRate(CThostFtdcInstrumentCommissionRateField *pInstrumentCommissionRate
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryInstrumentCommissionRateHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryInstrumentCommissionRateField> req_field_;
    std::shared_ptr<QryInstrumentCommissionRateResp> resp_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_COMMISSION_RATE_HANDLER_H_ */
