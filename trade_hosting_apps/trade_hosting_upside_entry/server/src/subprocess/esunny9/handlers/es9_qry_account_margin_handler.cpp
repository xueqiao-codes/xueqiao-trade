/*
 * es9_qry_account_margin_handler.cpp
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */
#include "es9_qry_account_margin_handler.h"

#include "base/code_defense.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


Es9QryAccountMarginHandler::Es9QryAccountMarginHandler(
        CallbackFunction callback
        , const std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryReq>& req)
    : Es9RequestWithCallback(callback)
      , req_(req)
      , account_margin_resp_(new Es9QryAccountMarginResp()){
    CHECK(req_);
}

int Es9QryAccountMarginHandler::onStart(ITapTrade::ITapTradeAPI* trader_api
        , ITapTrade::TAPIUINT32& session_id) {
    return trader_api->QryAccountMarginRent(&session_id, req_.get());
}

void Es9QryAccountMarginHandler::OnRspQryAccountMarginRent(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountMarginRentQryRsp *info) {
    if (errorCode != 0) {
        account_margin_resp_->setErrorCode(errorCode);
    }
    if (info) {
        account_margin_resp_->margins.push_back(*info);
    }
}
