/*
 * es9_login_handler.cpp
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */

#include "es9_login_handler.h"
#include "base/code_defense.h"


using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9LoginHandler::Es9LoginHandler(
        const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account)
    : trade_account_(trade_account) {
    CHECK(trade_account_);
}

int Es9LoginHandler::onStart(
        ITapTrade::ITapTradeAPI* trader_api
        , ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPITradeLoginAuth login_auth_req;
    memset(&login_auth_req, 0, sizeof(ITapTrade::TapAPITradeLoginAuth));
    strncpy(login_auth_req.UserNo, trade_account_->loginUserName.c_str(), sizeof(ITapTrade::TAPISTR_20) - 1);
    strncpy(login_auth_req.Password, trade_account_->loginPassword.c_str(), sizeof(ITapTrade::TAPISTR_20) - 1);
    login_auth_req.ISModifyPassword = ITapTrade::APIYNFLAG_NO;

    return trader_api->Login(&login_auth_req);
}
