/*
 * qry_position_handler.cpp
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */
#include "qry_position_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QryPositionHandler::QryPositionHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryInvestorPositionField> req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_positions_(new QryPositionResp()){
    CHECK(req_field_);
}

int QryPositionHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryInvestorPosition(req_field_.get(), getRequestId());
}

void QryPositionHandler::OnRspQryInvestorPosition(
        CThostFtdcInvestorPositionField *pInvestorPosition
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInvestorPosition) {
        resp_positions_->positions.push_back(*pInvestorPosition);
    }
    resp_positions_->setCtpRspInfo(pRspInfo);
}

