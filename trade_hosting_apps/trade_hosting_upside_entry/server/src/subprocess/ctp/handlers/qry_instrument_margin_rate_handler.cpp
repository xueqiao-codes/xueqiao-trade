/*
 * qry_instrument_margin_rate_handler.cpp
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#include "qry_instrument_margin_rate_handler.h"

#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryInstrumentMarginRateHandler::QryInstrumentMarginRateHandler(CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryInstrumentMarginRateField>& req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_(new QryInstrumentMarginRateResp()){
    CHECK(req_field_);
}

int QryInstrumentMarginRateHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryInstrumentMarginRate(req_field_.get(), getRequestId());
}

void QryInstrumentMarginRateHandler::OnRspQryInstrumentMarginRate(
        CThostFtdcInstrumentMarginRateField *pInstrumentMarginRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInstrumentMarginRate) {
        resp_->rates.push_back(*pInstrumentMarginRate);
    }
    resp_->setCtpRspInfo(pRspInfo);
}
