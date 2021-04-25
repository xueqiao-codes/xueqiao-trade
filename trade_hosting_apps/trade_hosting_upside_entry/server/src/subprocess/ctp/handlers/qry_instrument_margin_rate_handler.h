/*
 * qry_instrument_margin_rate_handler.h
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_MARGIN_RATE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_MARGIN_RATE_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryInstrumentMarginRateResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInstrumentMarginRateField> rates;
};


class QryInstrumentMarginRateHandler : public ctpext::framework::CTPRequestWithCallback<QryInstrumentMarginRateResp> {
public:
    QryInstrumentMarginRateHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryInstrumentMarginRateField>& req_field);
    virtual ~QryInstrumentMarginRateHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 10000; }

    virtual const std::shared_ptr<QryInstrumentMarginRateResp>& getResponse() {
        return resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInstrumentMarginRate(CThostFtdcInstrumentMarginRateField *pInstrumentMarginRate
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryInstrumentMarginRateHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryInstrumentMarginRateField> req_field_;
    std::shared_ptr<QryInstrumentMarginRateResp> resp_;

};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENT_MARGIN_RATE_HANDLER_H_ */
