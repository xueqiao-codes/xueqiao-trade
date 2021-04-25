/*
 * qry_investor_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "qry_investor_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QryInvestorHandler::QryInvestorHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , invertor_resp_(new QryInverstorResp()){
    CHECK(login_info_);
}

int QryInvestorHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryInvestorField invertor_field;
    memset(&invertor_field, 0, sizeof(CThostFtdcQryInvestorField));
    strncpy(invertor_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(invertor_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    return trader_api->ReqQryInvestor(&invertor_field, getRequestId());
}

void QryInvestorHandler::OnRspQryInvestor(
        CThostFtdcInvestorField *pInvestor
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInvestor) {
        invertor_resp_->invertor_field.reset(new CThostFtdcInvestorField(*pInvestor));
    }
    invertor_resp_->setCtpRspInfo(pRspInfo);
}



