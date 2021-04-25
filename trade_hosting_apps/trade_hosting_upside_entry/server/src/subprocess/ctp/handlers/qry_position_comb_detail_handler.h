/*
 * qry_position_comb_detail_handler.h
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_COMB_DETAIL_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_COMB_DETAIL_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class QryPositionCombDetailResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcInvestorPositionCombineDetailField> details;
};

class QryPositionCombDetailHandler : public ctpext::framework::CTPRequestWithCallback<QryPositionCombDetailResp> {
public:
    QryPositionCombDetailHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcQryInvestorPositionCombineDetailField> & req_field);
    virtual ~QryPositionCombDetailHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 30000; }

    virtual const std::shared_ptr<QryPositionCombDetailResp>& getResponse() {
        return resp_position_comb_details_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInvestorPositionCombineDetail(
        CThostFtdcInvestorPositionCombineDetailField *pInvestorPositionCombineDetail
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast);

    virtual std::string description() {
        return "QryPositionCombDetailHandler";
    }

private:
    std::shared_ptr<CThostFtdcQryInvestorPositionCombineDetailField> req_field_;
    std::shared_ptr<QryPositionCombDetailResp> resp_position_comb_details_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_POSITION_COMB_DETAIL_HANDLER_H_ */
