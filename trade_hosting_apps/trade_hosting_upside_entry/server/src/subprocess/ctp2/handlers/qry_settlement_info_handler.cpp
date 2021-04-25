/*
 * qry_settlement_info_handler.cpp
 *
 *  Created on: 2018年8月10日
 *      Author: wangli
 */
#include "qry_settlement_info_handler.h"

#include <string.h>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QrySettlementInfoHandler::QrySettlementInfoHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::string& trading_day)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , trading_day_(trading_day)
      , qry_settlement_info_resp_(new QrySettlementInfoResp()){
}

int QrySettlementInfoHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQrySettlementInfoField field;
    memset(&field, 0, sizeof(CThostFtdcQrySettlementInfoConfirmField));
    strncpy(field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    strncpy(field.TradingDay, trading_day_.c_str(), sizeof(TThostFtdcDateType)- 1);

    return trader_api->ReqQrySettlementInfo(&field, getRequestId());
}

void QrySettlementInfoHandler::OnRspQrySettlementInfo(
        CThostFtdcSettlementInfoField *pSettlementInfo
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pSettlementInfo) {
        qry_settlement_info_resp_->settlement_infos.push_back(*pSettlementInfo);
    }
    qry_settlement_info_resp_->setCtpRspInfo(pRspInfo);
}

