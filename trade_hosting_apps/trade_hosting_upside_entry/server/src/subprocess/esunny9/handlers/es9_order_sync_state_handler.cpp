/*
 * es9_order_sync_state_handler.cpp
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#include "es9_order_sync_state_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

Es9OrderSyncStateHandler::Es9OrderSyncStateHandler(
        CallbackFunction callback
        , const std::shared_ptr<HostingExecOrder>& sync_order)
    : Es9RequestWithCallback(callback)
      , sync_order_(sync_order)
      , order_sync_state_resp_(new Es9OrderSyncStateResp()){
    CHECK(sync_order_);
    order_sync_state_resp_->sync_order = sync_order;
}

int Es9OrderSyncStateHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIOrderQryReq order_qry_field;
    memset(&order_qry_field, 0, sizeof(ITapTrade::TapAPIOrderQryReq));

    strcpy(order_qry_field.ExchangeNo, sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9ExchangeNo.c_str());
    order_qry_field.CommodityType = (ITapTrade::TAPICommodityType)
            sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9CommodityType;
    strcpy(order_qry_field.CommodityNo, sync_order_->orderInputExt.esunny9InputExt.contractSummary.esunny9CommodityNo.c_str());

    if (sync_order_->__isset.dealInfo
            && sync_order_->dealInfo.__isset.dealId
            && sync_order_->dealInfo.dealId.__isset.esunny9DealId) {
        const ESunny9DealID& deal_id = sync_order_->dealInfo.dealId.esunny9DealId;
        if (deal_id.__isset.orderNo && !deal_id.orderNo.empty()) {
            strcpy(order_qry_field.OrderNo, deal_id.orderNo.c_str());
            order_sync_state_resp_->sync_order_has_dealinfo = true;
        }
    }

    return trader_api->QryOrder(&session_id, &order_qry_field);
}

void Es9OrderSyncStateHandler::OnRspQryOrder(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIOrderInfo *info) {
    APPLOG_INFO("OnRspQryOrder sessionID={}, errorCode={}, isLast={}, info=0x{:x}"
            , sessionID, errorCode, isLast, (int64_t)info);
    if (errorCode != 0) {
        order_sync_state_resp_->setErrorCode(errorCode);
    }
    if (info) {
        order_sync_state_resp_->order_infos.push_back(*info);
    }
}
