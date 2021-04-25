/*
 * qry_all_trades_handler.cpp
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */
#include "qry_all_trades_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryAllTradesHandler::QryAllTradesHandler(CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , all_trades_resp_(new QryAllTradesResp()) {
}

int QryAllTradesHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryTradeField qry_trade_field;
    memset(&qry_trade_field, 0, sizeof(CThostFtdcQryTradeField));
    strncpy(qry_trade_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(qry_trade_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    return trader_api->ReqQryTrade(&qry_trade_field, getRequestId());
}

void QryAllTradesHandler::OnRspQryTrade(
        CThostFtdcTradeField *pTrade
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pTrade) {
        all_trades_resp_->trades.push_back(*pTrade);
    }
    all_trades_resp_->setCtpRspInfo(pRspInfo);
}
