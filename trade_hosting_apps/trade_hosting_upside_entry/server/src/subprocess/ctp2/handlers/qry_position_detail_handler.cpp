/*
 * qry_position_detail_handler.cpp
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */
#include "qry_position_detail_handler.h"

#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryPositionDetailHandler::QryPositionDetailHandler(CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryInvestorPositionDetailField>& req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_position_details_(new QryPositionDetailResp()){
    CHECK(req_field_);
}

int QryPositionDetailHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryInvestorPositionDetail(req_field_.get(), getRequestId());
}

void QryPositionDetailHandler::OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField *pInvestorPositionDetail
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInvestorPositionDetail) {
        resp_position_details_->details.push_back(*pInvestorPositionDetail);
    }
    resp_position_details_->setCtpRspInfo(pRspInfo);
}


