/*
 * es9_qry_account_handler.cpp
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */
#include "es9_qry_account_handler.h"

#include "base/code_defense.h"
#include "iTapAPIError.h"


using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9QryAccountHandler::Es9QryAccountHandler(CallbackFunction callback)
    : Es9RequestWithCallback(callback)
      , qry_account_resp_(new Es9QryAccountResp()) {
}

int Es9QryAccountHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIAccQryReq req;
    return trader_api->QryAccount(&session_id, &req);
}

void Es9QryAccountHandler::OnRspQryAccount(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIUINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountInfo *info) {
    qry_account_resp_->setErrorCode(errorCode);
    if (info) {
        qry_account_resp_->account_infos.push_back(*info);
    }
}
