/*
 * qry_trading_account_handler.cpp
 *
 *  Created on: 2018年8月10日
 *      Author: wangli
 */
#include "qry_trading_account_handler.h"

#include <string.h>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


QryTradingAccountHandler::QryTradingAccountHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , qry_account_rsp_(new QryTradingAccountResp()){
}


int QryTradingAccountHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryTradingAccountField field;
    strncpy(field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    strcpy(field.CurrencyID, "CNY");
    return trader_api->ReqQryTradingAccount(&field, getRequestId());
}

void QryTradingAccountHandler::OnRspQryTradingAccount(
        CThostFtdcTradingAccountField *pTradingAccount
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    APPLOG_INFO("OnRspQryTradingAccount ..., pTradingAccount={}, bIsLast={}"
            , (int64_t)pTradingAccount, bIsLast);

    if (pTradingAccount) {
        qry_account_rsp_->funds.push_back(*pTradingAccount);
    }
    qry_account_rsp_->setCtpRspInfo(pRspInfo);
}
