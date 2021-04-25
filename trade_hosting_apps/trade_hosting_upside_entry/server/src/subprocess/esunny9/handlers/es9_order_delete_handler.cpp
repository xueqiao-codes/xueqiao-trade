/*
 * es9_order_delete_handler.cpp
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#include "es9_order_delete_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::upside::entry;


Es9OrderDeleteHandler::Es9OrderDeleteHandler(
        CallbackFunction callback
        , const std::shared_ptr<HostingExecOrder>& delete_order)
    : Es9RequestWithCallback(callback)
      , delete_order_(delete_order)
      , order_delete_resp_(new Es9OrderDeleteResp()){
    CHECK(delete_order_);
    order_delete_resp_->delete_order = delete_order;
}

int Es9OrderDeleteHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIOrderCancelReq order_cancel_field;
    memset(&order_cancel_field, 0, sizeof(ITapTrade::TapAPIOrderCancelReq));

    if (delete_order_->upsideOrderRef.esunny9Ref.__isset.refString
            && !delete_order_->upsideOrderRef.esunny9Ref.refString.empty()) {
        strcpy(order_cancel_field.RefString, delete_order_->upsideOrderRef.esunny9Ref.refString.c_str());
    }

    if (delete_order_->dealInfo.dealId.esunny9DealId.__isset.orderNo
            && !delete_order_->dealInfo.dealId.esunny9DealId.orderNo.empty()) {
        strcpy(order_cancel_field.OrderNo, delete_order_->dealInfo.dealId.esunny9DealId.orderNo.c_str());
    } else {
        CHECK(false);
    }

    return trader_api->CancelOrder(&session_id, &order_cancel_field);
}

void Es9OrderDeleteHandler::OnRspOrderAction(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , const ITapTrade::TapAPIOrderActionRsp *info) {
    if (errorCode != 0) {
        order_delete_resp_->setErrorCode(errorCode);
    }
    if (info) {
        CHECK(info->ActionType == ITapTrade::APIORDER_DELETE);
        if (info->OrderInfo) {
            order_delete_resp_->order_info.reset(new ITapTrade::TapAPIOrderInfo(*(info->OrderInfo)));
        }
    }
}

