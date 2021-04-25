/*
 * es9_order_sync_trades_handler.cpp
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#include "es9_order_sync_trades_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9OrderSyncTradesHandler::Es9OrderSyncTradesHandler(
        CallbackFunction callback
        , const std::string& account_no
        , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order)
    : Es9RequestWithCallback(callback)
      , account_no_(account_no)
      , sync_order_(sync_order)
      , order_sync_trades_resp_(new Es9OrderSyncTradesResp()){
    CHECK(sync_order_);
}

int Es9OrderSyncTradesHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIFillQryReq fill_qry_field;
    memset(&fill_qry_field, 0, sizeof(ITapTrade::TapAPIFillQryReq));

    strcpy(fill_qry_field.AccountNo, account_no_.c_str());
    strcpy(fill_qry_field.ExchangeNo, sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9ExchangeNo.c_str());
    fill_qry_field.CommodityType = (ITapTrade::TAPICommodityType)
            sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9CommodityType;
    strcpy(fill_qry_field.CommodityNo, sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9CommodityNo.c_str());
    strcpy(fill_qry_field.OrderNo, sync_order_->dealInfo.dealId.esunny9DealId.orderNo.c_str());

    return trader_api->QryFill(&session_id, &fill_qry_field);
}

void Es9OrderSyncTradesHandler::OnRspQryFill(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIFillInfo *info) {
    if (errorCode != 0) {
        order_sync_trades_resp_->setErrorCode(errorCode);
    }
    if (info) {
        order_sync_trades_resp_->fill_infos.push_back(*info);
    }
}


