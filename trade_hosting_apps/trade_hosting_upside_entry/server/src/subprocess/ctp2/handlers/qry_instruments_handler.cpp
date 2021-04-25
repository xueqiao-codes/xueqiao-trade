/*
 * qry_instruments_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#include "qry_instruments_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryInstrumentsHandler::QryInstrumentsHandler(
        CallbackFunction callback)
    : CTPRequestWithCallback(callback)
      , instruments_rsp_(new QryInstrumentsResp()){
}

int QryInstrumentsHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryInstrumentField field;
    memset(&field, 0, sizeof(CThostFtdcQryInstrumentField));
    return trader_api->ReqQryInstrument(&field, getRequestId());
}

void QryInstrumentsHandler::OnRspQryInstrument(
        CThostFtdcInstrumentField *pInstrument
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInstrument) {
        instruments_rsp_->instruments.push_back(*pInstrument);
    }
    instruments_rsp_->setCtpRspInfo(pRspInfo);
}
