/*
 * es9_order_sync_state_batch_handler.cpp
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */
#include "es9_order_sync_state_batch_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


Es9OrderSyncStateBatchHandler::Es9OrderSyncStateBatchHandler(CallbackFunction callback
        , const std::shared_ptr<TSyncOrderStateBatchReq>& batch_req)
    : Es9RequestWithCallback(callback)
      , batch_req_(batch_req)
      , sync_state_batch_resp_(new Es9OrderSyncStateBatchResp()){
    CHECK(batch_req_);

    sync_state_batch_resp_->batch_req = batch_req;
}


int Es9OrderSyncStateBatchHandler::onStart(
        ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {

    ITapTrade::TapAPIOrderQryReq order_qry_field;
    memset(&order_qry_field, 0, sizeof(ITapTrade::TapAPIOrderQryReq));

    if (batch_req_->__isset.esunny9ContractSummary) {
        if (batch_req_->esunny9ContractSummary.__isset.esunny9ExchangeNo) {
            strcpy(order_qry_field.ExchangeNo, batch_req_->esunny9ContractSummary.esunny9ExchangeNo.c_str());
        }
        if (batch_req_->esunny9ContractSummary.__isset.esunny9CommodityType) {
            order_qry_field.CommodityType = (ITapTrade::TAPICommodityType)batch_req_->esunny9ContractSummary.esunny9CommodityType;
        }
        if (batch_req_->esunny9ContractSummary.__isset.esunny9CommodityNo) {
            strcpy(order_qry_field.CommodityNo, batch_req_->esunny9ContractSummary.esunny9CommodityNo.c_str());
        }
    }

    return trader_api->QryOrder(&session_id, &order_qry_field);
}

void Es9OrderSyncStateBatchHandler::OnRspQryOrder(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIOrderInfo *info) {
    APPLOG_INFO("OnRspQryOrder sessionID={}, errorCode={}, isLast={}, info=0x{:x}"
                , sessionID, errorCode, isLast, (int64_t)info);
    if (errorCode != 0) {
        sync_state_batch_resp_->setErrorCode(errorCode);
    }
    if (info) {
        sync_state_batch_resp_->order_infos.push_back(*info);
    }
}
