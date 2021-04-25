/*
 * ctp_login_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "ctp_login_handler.h"

#include "base/app_log.h"
#include <boost/lexical_cast.hpp>
#include <string.h>

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

CTPLoginHandler::CTPLoginHandler(
        CallbackFunction callback
        , const std::shared_ptr<HostingTradeAccount>& trade_account
        , const std::string& ctp_broker_id_info )
    : CTPRequestWithCallback(callback)
      , trade_account_(trade_account)
      , ctp_broker_id_info_(ctp_broker_id_info)
      , ctp_login_rsp_(new CTPLoginResp()) {
}

void CTPLoginHandler::OnRspUserLogin(
        CThostFtdcRspUserLoginField *pRspUserLogin
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pRspUserLogin) {
        ctp_login_rsp_->login_rsp_field.reset(new CThostFtdcRspUserLoginField(*pRspUserLogin));
    }
    ctp_login_rsp_->setCtpRspInfo(pRspInfo);
}

int CTPLoginHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcReqUserLoginField login_field;
    memset(&login_field, 0, sizeof(CThostFtdcReqUserLoginField));

    strncpy(login_field.BrokerID, ctp_broker_id_info_.c_str(), sizeof(TThostFtdcBrokerIDType) - 1);
    login_field.BrokerID[sizeof(TThostFtdcBrokerIDType) - 1] = 0;
    strncpy(login_field.UserID, trade_account_->loginUserName.c_str(), sizeof(TThostFtdcUserIDType) - 1);
    login_field.UserID[sizeof(TThostFtdcUserIDType) - 1] = 0;
    strncpy(login_field.Password, trade_account_->loginPassword.c_str(), sizeof(TThostFtdcPasswordType) - 1);
    login_field.Password[sizeof(TThostFtdcPasswordType) - 1] = 0;
    return trader_api->ReqUserLogin(&login_field, getRequestId());
}


