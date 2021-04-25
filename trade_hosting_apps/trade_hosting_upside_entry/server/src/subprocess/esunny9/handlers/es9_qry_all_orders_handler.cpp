/*
 * es9_qry_all_orders_handler.cpp
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#include "es9_qry_all_orders_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9QryAllOrdersHandler::Es9QryAllOrdersHandler(CallbackFunction callback)
    : Es9RequestWithCallback(callback)
      , all_orders_resp_(new Es9QryAllOrdersResp()){
}

int Es9QryAllOrdersHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIOrderQryReq order_qry_field;
    memset(&order_qry_field, 0, sizeof(ITapTrade::TapAPIOrderQryReq));

    return trader_api->QryOrder(&session_id, &order_qry_field);
}

void Es9QryAllOrdersHandler::OnRspQryOrder(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIOrderInfo *info) {
//    APPLOG_INFO("OnRspQryOrder sessionID={}, errorCode={}, isLast={}, info=0x{:x}"
//                    , sessionID, errorCode, isLast, (int64_t)info);
    if (errorCode != 0) {
        all_orders_resp_->setErrorCode(errorCode);
    }
    if (info) {
        all_orders_resp_->order_infos.push_back(*info);
    }
}
