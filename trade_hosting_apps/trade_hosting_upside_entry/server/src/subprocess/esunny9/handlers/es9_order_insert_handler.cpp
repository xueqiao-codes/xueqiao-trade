/*
 * es9_order_insert_handler.cpp
 *
 *  Created on: 2018年4月16日
 *      Author: 44385
 */
#include "es9_order_insert_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

std::map<HostingExecOrderCondition::type, ITapTrade::TAPITriggerConditionType> SUPPORTED_ESUNNY9_ORDER_CONDITION_TYPES = {
    {HostingExecOrderCondition::LASTEST_PRICE_GT, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::LASTEST_PRICE_GE, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::LASTEST_PRICE_LT, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE},
    {HostingExecOrderCondition::LASTEST_PRICE_LE, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE},
    {HostingExecOrderCondition::SELLONE_PRICE_GT, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::SELLONE_PRICE_GE, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::SELLONE_PRICE_LT, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE},
    {HostingExecOrderCondition::SELLONE_PRICE_LE, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE},
    {HostingExecOrderCondition::BUYONE_PRICE_GT, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::BUYONE_PRICE_GE, ITapTrade::TAPI_TRIGGER_CONDITION_GREAT},
    {HostingExecOrderCondition::BUYONE_PRICE_LT, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE},
    {HostingExecOrderCondition::BUYONE_PRICE_LE, ITapTrade::TAPI_TRIGGER_CONDITION_LITTLE}
};

std::map<HostingExecOrderCondition::type, ITapTrade::TAPITriggerPriceTypeType> SUPPORTED_ESUNNY9_ORDER_CONDITION_PRICETYPES = {
    {HostingExecOrderCondition::LASTEST_PRICE_GT, ITapTrade::TAPI_TRIGGER_PRICE_LAST},
    {HostingExecOrderCondition::LASTEST_PRICE_GE, ITapTrade::TAPI_TRIGGER_PRICE_LAST},
    {HostingExecOrderCondition::LASTEST_PRICE_LT, ITapTrade::TAPI_TRIGGER_PRICE_LAST},
    {HostingExecOrderCondition::LASTEST_PRICE_LE, ITapTrade::TAPI_TRIGGER_PRICE_LAST},
    {HostingExecOrderCondition::SELLONE_PRICE_GT, ITapTrade::TAPI_TRIGGER_PRICE_SELL},
    {HostingExecOrderCondition::SELLONE_PRICE_GE, ITapTrade::TAPI_TRIGGER_PRICE_SELL},
    {HostingExecOrderCondition::SELLONE_PRICE_LT, ITapTrade::TAPI_TRIGGER_PRICE_SELL},
    {HostingExecOrderCondition::SELLONE_PRICE_LE, ITapTrade::TAPI_TRIGGER_PRICE_SELL},
    {HostingExecOrderCondition::BUYONE_PRICE_GT, ITapTrade::TAPI_TRIGGER_PRICE_BUY},
    {HostingExecOrderCondition::BUYONE_PRICE_GE, ITapTrade::TAPI_TRIGGER_PRICE_BUY},
    {HostingExecOrderCondition::BUYONE_PRICE_LT, ITapTrade::TAPI_TRIGGER_PRICE_BUY},
    {HostingExecOrderCondition::BUYONE_PRICE_LE, ITapTrade::TAPI_TRIGGER_PRICE_BUY}
};


Es9OrderInsertHandler::Es9OrderInsertHandler(
        CallbackFunction callback
        , const std::string& account_no
        , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order)
    : Es9RequestWithCallback(callback)
      , account_no_(account_no)
      , insert_order_(insert_order)
      , order_insert_rsp_(new Es9OrderInsertResp()) {
    CHECK(insert_order_);

    order_insert_rsp_->insert_order = insert_order;
}

int Es9OrderInsertHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPINewOrder new_order_field;
    const ESunny9ContractSummary& contractSummary
        = insert_order_->orderInputExt.esunny9InputExt.contractSummary;

    strcpy(new_order_field.AccountNo, account_no_.c_str());
    strncpy(new_order_field.ExchangeNo
            , contractSummary.esunny9ExchangeNo.c_str()
            , sizeof(ITapTrade::TAPISTR_10) - 1);
    new_order_field.CommodityType = (ITapTrade::TAPICommodityType)contractSummary.esunny9CommodityType;
    strncpy(new_order_field.CommodityNo
            , contractSummary.esunny9CommodityNo.c_str()
            , sizeof(ITapTrade::TAPISTR_10) - 1);
    strncpy(new_order_field.ContractNo
            , contractSummary.esunny9ContractNo.c_str()
            , sizeof(ITapTrade::TAPISTR_10) - 1);

    if (contractSummary.__isset.esunny9ContractNo2 && !contractSummary.esunny9ContractNo2.empty()) {
        strncpy(new_order_field.ContractNo2
                , contractSummary.esunny9ContractNo2.c_str()
                , sizeof(ITapTrade::TAPISTR_10) - 1);
    }

    CHECK(insert_order_->upsideOrderRef.esunny9Ref.refString.length() < sizeof(ITapTrade::TAPISTR_50));
    strcpy(new_order_field.RefString, insert_order_->upsideOrderRef.esunny9Ref.refString.c_str());

    new_order_field.OrderSource = ITapTrade::TAPI_ORDER_SOURCE_ESUNNY_API;
    if (insert_order_->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_SELL) {
        new_order_field.OrderSide = ITapTrade::TAPI_SIDE_SELL;
    } else {
        new_order_field.OrderSide = ITapTrade::TAPI_SIDE_BUY;
    }

    new_order_field.OrderQty = insert_order_->orderDetail.quantity;
    new_order_field.OrderPrice = insert_order_->orderDetail.limitPrice;

    new_order_field.TimeInForce = ITapTrade::TAPI_ORDER_TIMEINFORCE_GFD;

    if (insert_order_->orderDetail.orderType == HostingExecOrderType::ORDER_LIMIT_PRICE) {
        new_order_field.OrderType = ITapTrade::TAPI_ORDER_TYPE_LIMIT;
    } else if (insert_order_->orderDetail.orderType == HostingExecOrderType::ORDER_WITH_CONDITION) {
        // TODO
        new_order_field.TacticsType = ITapTrade::TAPI_TACTICS_TYPE_CONDITION;
        new_order_field.StopPrice = insert_order_->orderDetail.conditionPrice;
    } else {
        CHECK(false);
    }

    new_order_field.AddOneIsValid = ITapTrade::APIYNFLAG_YES;
    APPLOG_INFO("InsertOrder TapAPINewOrder AccountNo={},ExchangeNo={}, CommodityType={}, CommodityNo={}"
            ", ContractNo={}, CallOrPutFlag={}, ContractNo2={},  CallOrPutFlag2={}, refString={}"
            ", OrderSide={}, OrderQty={}, OrderPrice={}, TimeInForce={}, OrderType={}"
            , new_order_field.AccountNo, new_order_field.ExchangeNo, new_order_field.CommodityType
            , new_order_field.CommodityNo, new_order_field.ContractNo, new_order_field.CallOrPutFlag
            , new_order_field.ContractNo2, new_order_field.CallOrPutFlag2, new_order_field.RefString
            , new_order_field.OrderSide, new_order_field.OrderQty, new_order_field.OrderPrice
            , new_order_field.TimeInForce, new_order_field.OrderType);

    return trader_api->InsertOrder(&session_id, &order_insert_rsp_->client_order_no, &new_order_field);
}

void Es9OrderInsertHandler::OnRspOrderAction(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , const ITapTrade::TapAPIOrderActionRsp *info) {
    if (errorCode != 0) {
        order_insert_rsp_->setErrorCode(errorCode);
    }
    if (info) {
        CHECK(info->ActionType == ITapTrade::APIORDER_INSERT);
        if (info->OrderInfo) {
            order_insert_rsp_->order_info.reset(new ITapTrade::TapAPIOrderInfo(*(info->OrderInfo)));
        }
    }
}

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


