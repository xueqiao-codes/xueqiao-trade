/*
 * es9_qry_position_handler.cpp
 *
 *  Created on: 2018年8月14日
 *      Author: wangli
 */

#include "es9_qry_position_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9QryPositionHandler::Es9QryPositionHandler(CallbackFunction callback, const std::string& account_no)
    : Es9RequestWithCallback(callback)
      , account_no_(account_no)
      , qry_position_resp_(new Es9QryPositionResp()){
}


int Es9QryPositionHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIPositionQryReq req;
    memset(&req, 0, sizeof(ITapTrade::TapAPIPositionQryReq));
    strncpy(req.AccountNo, account_no_.c_str(), sizeof(ITapTrade::TAPISTR_20) - 1);

    return trader_api->QryPosition(&session_id, &req);
}

void Es9QryPositionHandler::OnRspQryPosition(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIPositionInfo *info) {
    if (info) {
        qry_position_resp_->positions.push_back(*info);
    }
    qry_position_resp_->setErrorCode(errorCode);
}
