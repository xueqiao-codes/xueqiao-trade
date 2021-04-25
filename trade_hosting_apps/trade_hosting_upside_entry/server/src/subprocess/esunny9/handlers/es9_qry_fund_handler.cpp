/*
 * es9_qry_fund_handler.cpp
 *
 *  Created on: 2018年8月11日
 *      Author: wangli
 */
#include "es9_qry_fund_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9QryFundHandler::Es9QryFundHandler(CallbackFunction callback, const std::string& account_no)
    : Es9RequestWithCallback(callback)
      , account_no_(account_no)
      , qry_fund_resp_(new Es9QryFundResp()) {
}

int Es9QryFundHandler::onStart(ITapTrade::ITapTradeAPI* trader_api
        , ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIFundReq req;
    memset(&req, 0, sizeof(req));
    strncpy(req.AccountNo, account_no_.c_str(), sizeof(ITapTrade::TAPISTR_20) - 1);
    return trader_api->QryFund(&session_id, &req);
}

void Es9QryFundHandler::OnRspQryFund(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIFundData *info) {
    if (info) {
        qry_fund_resp_->funds.push_back(*info);
    }
    qry_fund_resp_->setErrorCode(errorCode);
}
