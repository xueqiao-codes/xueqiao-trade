/*
 * es9_qry_commodity_handler.cpp
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */
#include "es9_qry_commodity_handler.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9QryCommodityHandler::Es9QryCommodityHandler(CallbackFunction callback)
    : Es9RequestWithCallback(callback)
      , commodity_resp_(new Es9QryCommodityResp()){
}

int Es9QryCommodityHandler::onStart(ITapTrade::ITapTradeAPI* trader_api
        , ITapTrade::TAPIUINT32& session_id) {
    return trader_api->QryCommodity(&session_id);
}

void Es9QryCommodityHandler::OnRspQryCommodity(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPICommodityInfo *info) {
    if (errorCode != 0) {
        commodity_resp_->setErrorCode(errorCode);
    }
    if (info) {
        commodity_resp_->commodities.push_back(*info);
    }
}

