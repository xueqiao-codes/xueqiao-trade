/*
 * order_insert_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "order_insert_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace platform::comm;
using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

std::map<CTPCombOffsetFlagType::type, char> SUPPORTED_CTP_COMB_OFFSET = {
    {CTPCombOffsetFlagType::THOST_FTDC_OF_OPEN, THOST_FTDC_OF_Open},
    {CTPCombOffsetFlagType::THOST_FTDC_OF_ClOSE, THOST_FTDC_OF_Close},
    {CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSETODAY, THOST_FTDC_OF_CloseToday},
    {CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSEYESTERDAY, THOST_FTDC_OF_CloseYesterday}
};

std::map<CTPCombHedgeFlagType::type, char> SUPPORTED_CTP_COMB_HEDGE = {
    {CTPCombHedgeFlagType::THOST_FTDC_HF_SPECULATION, THOST_FTDC_HF_Speculation},
    {CTPCombHedgeFlagType::THOST_FTDC_HF_ARBITRAGE, THOST_FTDC_HF_Arbitrage},
    {CTPCombHedgeFlagType::THOST_FTDC_HF_HEDGE, THOST_FTDC_HF_Hedge},
    {CTPCombHedgeFlagType::THOST_FTDC_HF_MARKETMAKER, THOST_FTDC_HF_MarketMaker}
};

std::map<HostingExecOrderCondition::type, char> SUPPORTED_CTP_ORDER_CONDITIONS = {
    {HostingExecOrderCondition::LASTEST_PRICE_GT, THOST_FTDC_CC_LastPriceGreaterThanStopPrice},
    {HostingExecOrderCondition::LASTEST_PRICE_GE, THOST_FTDC_CC_LastPriceGreaterEqualStopPrice},
    {HostingExecOrderCondition::LASTEST_PRICE_LT, THOST_FTDC_CC_LastPriceLesserThanStopPrice},
    {HostingExecOrderCondition::LASTEST_PRICE_LE, THOST_FTDC_CC_LastPriceLesserEqualStopPrice},
    {HostingExecOrderCondition::SELLONE_PRICE_GT, THOST_FTDC_CC_AskPriceGreaterThanStopPrice},
    {HostingExecOrderCondition::SELLONE_PRICE_GE, THOST_FTDC_CC_AskPriceGreaterEqualStopPrice},
    {HostingExecOrderCondition::SELLONE_PRICE_LT, THOST_FTDC_CC_AskPriceLesserThanStopPrice},
    {HostingExecOrderCondition::SELLONE_PRICE_LE, THOST_FTDC_CC_AskPriceLesserEqualStopPrice},
    {HostingExecOrderCondition::BUYONE_PRICE_GT, THOST_FTDC_CC_BidPriceGreaterThanStopPrice},
    {HostingExecOrderCondition::BUYONE_PRICE_GE, THOST_FTDC_CC_BidPriceGreaterEqualStopPrice},
    {HostingExecOrderCondition::BUYONE_PRICE_LT, THOST_FTDC_CC_BidPriceLesserThanStopPrice},
    {HostingExecOrderCondition::BUYONE_PRICE_LE, THOST_FTDC_CC_BidPriceLesserEqualStopPrice}
};



OrderInsertHandler::OrderInsertHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::shared_ptr<HostingExecOrder>& insert_order
        , const std::shared_ptr<CTPPositionManager>& position_manager)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , insert_order_(insert_order)
      , position_manager_(position_manager)
      , order_insert_rsp_(new OrderInsertResp()){
    CHECK(login_info_);
    CHECK(insert_order_);
    CHECK(position_manager_);
    order_insert_rsp_->insert_order = insert_order;
}

int OrderInsertHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcInputOrderField input_order_field;
    memset(&input_order_field, 0, sizeof(CThostFtdcInputOrderField));
    strncpy(input_order_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(input_order_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    strncpy(input_order_field.InstrumentID
            , insert_order_->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str()
            , sizeof(TThostFtdcInstrumentIDType) - 1);
			
	strncpy(input_order_field.ExchangeID
            , insert_order_->orderInputExt.ctpInputExt.contractSummary.ctpExchangeCode.c_str()
            , sizeof(TThostFtdcExchangeIDType) - 1);

    strncpy(input_order_field.OrderRef, insert_order_->upsideOrderRef.ctpRef.orderRef.c_str(), sizeof(TThostFtdcOrderRefType) - 1);

    if (insert_order_->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_SELL) {
        input_order_field.Direction = THOST_FTDC_D_Sell;
    } else {
        input_order_field.Direction = THOST_FTDC_D_Buy;
    }

    if (insert_order_->orderInputExt.ctpInputExt.__isset.combOffsetFlag
            && insert_order_->orderInputExt.ctpInputExt.combOffsetFlag == CTPCombOffsetFlagType::THOST_FTDC_OF_OPEN){
        // 调用方要求开仓
        input_order_field.CombOffsetFlag[0] = THOST_FTDC_OF_Open;
    } else {
        // 目前不允许外部控制平仓
        TThostFtdcOffsetFlagType offset_flag = 0;
        position_manager_->orderInsertPrepare(insert_order_, offset_flag);
        input_order_field.CombOffsetFlag[0] = offset_flag;
    }

    APPLOG_INFO("OrderInsertHandler onStart, direction={} offset_flag={}"
            , input_order_field.Direction, input_order_field.CombOffsetFlag[0]);

    if (insert_order_->orderInputExt.ctpInputExt.__isset.combHedgeFlag) {
        input_order_field.CombHedgeFlag[0] = SUPPORTED_CTP_COMB_HEDGE[insert_order_->orderInputExt.ctpInputExt.combHedgeFlag];
    } else {
        input_order_field.CombHedgeFlag[0] = THOST_FTDC_HF_Speculation;
    }

    input_order_field.VolumeTotalOriginal = insert_order_->orderDetail.quantity;
    if (insert_order_->orderDetail.orderType == HostingExecOrderType::ORDER_LIMIT_PRICE) {
        input_order_field.ContingentCondition = THOST_FTDC_CC_Immediately;
    } else if (insert_order_->orderDetail.orderType == HostingExecOrderType::ORDER_WITH_CONDITION) {
        input_order_field.ContingentCondition = SUPPORTED_CTP_ORDER_CONDITIONS[insert_order_->orderDetail.condition];
        input_order_field.StopPrice = insert_order_->orderDetail.conditionPrice;
    } else {
        CHECK(false);
    }
    input_order_field.LimitPrice = insert_order_->orderDetail.limitPrice;
    input_order_field.OrderPriceType = THOST_FTDC_OPT_LimitPrice;

    input_order_field.TimeCondition = THOST_FTDC_TC_GFD;

    if (insert_order_->orderInputExt.ctpInputExt.__isset.minVolume
            &&insert_order_->orderInputExt.ctpInputExt.minVolume > 1) {
        input_order_field.MinVolume = insert_order_->orderInputExt.ctpInputExt.minVolume;
        input_order_field.VolumeCondition = THOST_FTDC_VC_MV;
    } else {
        input_order_field.MinVolume = 1;
        input_order_field.VolumeCondition = THOST_FTDC_VC_AV;
    }

    input_order_field.ForceCloseReason = THOST_FTDC_FCC_NotForceClose;
    input_order_field.IsAutoSuspend = 0;
    input_order_field.UserForceClose = 0;

    int ret = trader_api->ReqOrderInsert(&input_order_field, getRequestId());
    if (ret != 0) {
        position_manager_->orderInsertCanceled(insert_order_);
    }
    return ret;
}

void OrderInsertHandler::OnRspOrderInsert(
        CThostFtdcInputOrderField *pInputOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInputOrder) {
        order_insert_rsp_->input_order_field.reset(new CThostFtdcInputOrderField(*pInputOrder));
    }
    order_insert_rsp_->setCtpRspInfo(pRspInfo);
}

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


