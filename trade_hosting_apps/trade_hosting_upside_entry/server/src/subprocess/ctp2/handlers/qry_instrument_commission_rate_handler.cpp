/*
 * qry_instrument_commission_rate_handler.cpp
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#include "qry_instrument_commission_rate_handler.h"

#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryInstrumentCommissionRateHandler::QryInstrumentCommissionRateHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryInstrumentCommissionRateField>& req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_(new QryInstrumentCommissionRateResp()){
    CHECK(req_field_);
}

int QryInstrumentCommissionRateHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryInstrumentCommissionRate(req_field_.get(), getRequestId());
}

void QryInstrumentCommissionRateHandler::OnRspQryInstrumentCommissionRate(
        CThostFtdcInstrumentCommissionRateField *pInstrumentCommissionRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInstrumentCommissionRate) {
        resp_->rates.push_back(*pInstrumentCommissionRate);
    }
    resp_->setCtpRspInfo(pRspInfo);
}
