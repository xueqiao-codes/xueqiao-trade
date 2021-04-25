/*
 * req_settlement_info_confirm_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "req_settlement_info_confirm_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

ReqSettlementInfoConfirmHandler::ReqSettlementInfoConfirmHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , confirm_resp_(new ReqSettlementInfoConfirmResp()){
    CHECK(login_info_);
}

int ReqSettlementInfoConfirmHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcSettlementInfoConfirmField field;

    memset(&field, 0, sizeof(CThostFtdcSettlementInfoConfirmField));
    strncpy(field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    return trader_api->ReqSettlementInfoConfirm(&field, getRequestId());
}

void ReqSettlementInfoConfirmHandler::OnRspSettlementInfoConfirm(
        CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pSettlementInfoConfirm) {
        confirm_resp_->confirm_field.reset(new CThostFtdcSettlementInfoConfirmField(*pSettlementInfoConfirm));
    }
    confirm_resp_->setCtpRspInfo(pRspInfo);
}



