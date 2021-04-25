/*
 * qry_instruments_info_confirm_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "qry_settlement_info_confirm_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"


using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QrySettlementInfoConfirmHandler::QrySettlementInfoConfirmHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , qry_info_rsp_(new QrySettlementInfoConfirmResp()){
    CHECK(login_info_);
}

int QrySettlementInfoConfirmHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQrySettlementInfoConfirmField field;
    memset(&field, 0, sizeof(CThostFtdcQrySettlementInfoConfirmField));
    strncpy(field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    return trader_api->ReqQrySettlementInfoConfirm(&field, getRequestId());
}

void QrySettlementInfoConfirmHandler::OnRspQrySettlementInfoConfirm(
        CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pSettlementInfoConfirm) {
        qry_info_rsp_->confirm_field.reset(new CThostFtdcSettlementInfoConfirmField(*pSettlementInfoConfirm));
    }
    qry_info_rsp_->setCtpRspInfo(pRspInfo);
}




