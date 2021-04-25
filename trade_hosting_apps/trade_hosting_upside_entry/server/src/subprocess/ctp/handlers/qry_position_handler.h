/*
 * qry_position_handler.h
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class QryPositionResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInvestorPositionField> positions;
};

class QryPositionHandler : public ctpext::framework::CTPRequestWithCallback<QryPositionResp> {
public:
    QryPositionHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryInvestorPositionField> req_field);

    virtual ~QryPositionHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 3000; }

    virtual const std::shared_ptr<QryPositionResp>& getResponse() {
        return resp_positions_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInvestorPosition(
            CThostFtdcInvestorPositionField *pInvestorPosition
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryPositionHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryInvestorPositionField> req_field_;

    std::shared_ptr<QryPositionResp> resp_positions_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_HANDLER_H_ */
