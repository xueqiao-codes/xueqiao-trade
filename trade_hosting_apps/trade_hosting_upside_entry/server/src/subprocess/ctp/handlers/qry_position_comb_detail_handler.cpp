/*
 * qry_position_comb_detail_handler.cpp
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */
#include "qry_position_comb_detail_handler.h"

#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryPositionCombDetailHandler::QryPositionCombDetailHandler(CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryInvestorPositionCombineDetailField> & req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_position_comb_details_(new QryPositionCombDetailResp()){
    CHECK(req_field_);
}

int QryPositionCombDetailHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryInvestorPositionCombineDetail(req_field_.get(), getRequestId());
}

void QryPositionCombDetailHandler::OnRspQryInvestorPositionCombineDetail(
        CThostFtdcInvestorPositionCombineDetailField *pInvestorPositionCombineDetail
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInvestorPositionCombineDetail) {
        resp_position_comb_details_->details.push_back(*pInvestorPositionCombineDetail);
    }
    resp_position_comb_details_->setCtpRspInfo(pRspInfo);
}
