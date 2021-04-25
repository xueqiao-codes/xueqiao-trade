/*
 * qry_position_detail_handler.h
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_DETAIL_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_DETAIL_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryPositionDetailResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInvestorPositionDetailField> details;
};


class QryPositionDetailHandler : public ctpext::framework::CTPRequestWithCallback<QryPositionDetailResp> {
public:
    QryPositionDetailHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryInvestorPositionDetailField>& req_field);
    virtual ~QryPositionDetailHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 30000; }

    virtual const std::shared_ptr<QryPositionDetailResp>& getResponse() {
        return resp_position_details_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField *pInvestorPositionDetail
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryPositionDetailHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryInvestorPositionDetailField> req_field_;
    std::shared_ptr<QryPositionDetailResp> resp_position_details_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_DETAIL_HANDLER_H_ */
