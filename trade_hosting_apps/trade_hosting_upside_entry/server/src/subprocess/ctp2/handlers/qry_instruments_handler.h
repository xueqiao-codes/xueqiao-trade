/*
 * qry_instruments_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENTS_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENTS_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryInstrumentsResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInstrumentField> instruments;
};

class QryInstrumentsHandler : public ctpext::framework::CTPRequestWithCallback<QryInstrumentsResp> {
public:
    QryInstrumentsHandler(CallbackFunction callback);
    virtual ~QryInstrumentsHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 8000; }

    virtual const std::shared_ptr<QryInstrumentsResp>& getResponse() {
        return instruments_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInstrument(
            CThostFtdcInstrumentField *pInstrument
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryInstrumentsHandler";
    }

private:
    std::shared_ptr<QryInstrumentsResp> instruments_rsp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_INSTRUMENTS_HANDLER_H_ */
