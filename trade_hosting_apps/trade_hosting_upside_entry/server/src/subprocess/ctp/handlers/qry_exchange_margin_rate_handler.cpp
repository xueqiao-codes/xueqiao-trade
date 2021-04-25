/*
 * qry_exchange_margin_rate_handler.cpp
 *
 *  Created on: 2019年1月21日
 *      Author: wangli
 */

#include "qry_exchange_margin_rate_handler.h"

#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QryExchangeMarginRateHandler::QryExchangeMarginRateHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcQryExchangeMarginRateField>& req_field)
    : CTPRequestWithCallback(callback)
      , req_field_(req_field)
      , resp_(new QryExchangeMarginRateResp()) {
    CHECK(req_field_);
}

int QryExchangeMarginRateHandler::onStart(CThostFtdcTraderApi* trader_api) {
    return trader_api->ReqQryExchangeMarginRate(req_field_.get(), getRequestId());
}

void QryExchangeMarginRateHandler::OnRspQryExchangeMarginRate(
        CThostFtdcExchangeMarginRateField *pExchangeMarginRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pExchangeMarginRate) {
        resp_->rates.push_back(*pExchangeMarginRate);
    }
    resp_->setCtpRspInfo(pRspInfo);
}

