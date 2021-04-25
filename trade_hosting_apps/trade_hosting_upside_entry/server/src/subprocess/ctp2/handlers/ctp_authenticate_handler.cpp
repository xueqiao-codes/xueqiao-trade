/*
 * ctp_authenticate_handler.cpp
 *
 *  Created on: 2019年5月15日
 *      Author: jason
 */
#include "ctp_authenticate_handler.h"

#include "base/app_log.h"
#include <boost/lexical_cast.hpp>
#include <string.h>

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

CTPAuthenticateHandler::CTPAuthenticateHandler(
        CallbackFunction callback
        , const std::shared_ptr<HostingTradeAccount>& trade_account
        , const std::string& ctp_broker_id_info
		, const std::string& ctp_app_id_info
		, const std::string& ctp_auth_code_info )
    : CTPRequestWithCallback(callback)
      , trade_account_(trade_account)
      , ctp_broker_id_info_(ctp_broker_id_info)
	  , ctp_app_id_info_(ctp_app_id_info)
	  , ctp_auth_code_info_(ctp_auth_code_info)
      , ctp_authenticate_rsp_(new CTPAuthenticateResp()) {
}

void CTPAuthenticateHandler::OnRspAuthenticate(
		CThostFtdcRspAuthenticateField *pRspAuthenticateField
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pRspAuthenticateField) {
        ctp_authenticate_rsp_->authenticate_rsp_field.reset(new CThostFtdcRspAuthenticateField(*pRspAuthenticateField));
    }
    ctp_authenticate_rsp_->setCtpRspInfo(pRspInfo);
}

int CTPAuthenticateHandler::onStart(CThostFtdcTraderApi* trader_api) {
	CThostFtdcReqAuthenticateField authenticate_field;
    memset(&authenticate_field, 0, sizeof(CThostFtdcReqAuthenticateField));

    strncpy(authenticate_field.BrokerID, ctp_broker_id_info_.c_str(), sizeof(TThostFtdcBrokerIDType) - 1);
    authenticate_field.BrokerID[sizeof(TThostFtdcBrokerIDType) - 1] = 0;
    strncpy(authenticate_field.UserID, trade_account_->loginUserName.c_str(), sizeof(TThostFtdcUserIDType) - 1);
    authenticate_field.UserID[sizeof(TThostFtdcUserIDType) - 1] = 0;
    strncpy(authenticate_field.AppID, ctp_app_id_info_.c_str(), sizeof(TThostFtdcAppIDType) - 1);
    authenticate_field.AppID[sizeof(TThostFtdcAppIDType) - 1] = 0;
    strncpy(authenticate_field.AuthCode, ctp_auth_code_info_.c_str(), sizeof(TThostFtdcAuthCodeType) - 1);
    authenticate_field.AuthCode[sizeof(TThostFtdcAuthCodeType) - 1] = 0;
    return trader_api->ReqAuthenticate(&authenticate_field, getRequestId());
}


