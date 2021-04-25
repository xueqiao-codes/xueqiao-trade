/*
 * es9_qry_account_fee_handler.cpp
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */
#include "es9_qry_account_fee_handler.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9QryAccountFeeHandler::Es9QryAccountFeeHandler(
        CallbackFunction callback
        , const std::string& account_no)
    : Es9RequestWithCallback(callback)
      , account_no_(account_no)
      , account_fee_resp_(new Es9QryAccountFeeResp()){
}

int Es9QryAccountFeeHandler::onStart(ITapTrade::ITapTradeAPI* trader_api
        , ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIAccountFeeRentQryReq req;
    memset(&req, 0, sizeof(ITapTrade::TapAPIAccountFeeRentQryReq));
    strcpy(req.AccountNo, account_no_.c_str());

    return trader_api->QryAccountFeeRent(&session_id, &req);
}

void Es9QryAccountFeeHandler::OnRspQryAccountFeeRent(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountFeeRentQryRsp *info) {
    if (errorCode != 0) {
        account_fee_resp_->setErrorCode(errorCode);
    }
    if (info) {
        account_fee_resp_->fees.push_back(*info);
    }
}
