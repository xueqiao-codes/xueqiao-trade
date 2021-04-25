/*
 * order_sync_trades_handler.cpp
 *
 *  Created on: 2018年3月26日
 *      Author: wangli
 */

#include "order_sync_trades_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"
#include "base/time_helper.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

OrderSyncTradesHandler::OrderSyncTradesHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::string& sync_instrument_id
        , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , sync_instrument_id_(sync_instrument_id)
      , sync_order_(sync_order)
      , order_sync_trades_rsp_(new OrderSyncTradesResp()) {
    CHECK(login_info_);
    CHECK(sync_order_);
    order_sync_trades_rsp_->sync_order = sync_order;
}

int OrderSyncTradesHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryTradeField qry_trade_field;
    memset(&qry_trade_field, 0, sizeof(CThostFtdcQryTradeField));
    strncpy(qry_trade_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(qry_trade_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    if (!sync_instrument_id_.empty()) {
        strncpy(qry_trade_field.InstrumentID, sync_instrument_id_.c_str(), sizeof(TThostFtdcInstrumentIDType) - 1);
    }

    if (sync_order_->__isset.dealInfo) {
        if (sync_order_->dealInfo.__isset.dealId
                && sync_order_->dealInfo.dealId.__isset.ctpDealId) {
            if (sync_order_->dealInfo.dealId.ctpDealId.__isset.exchangeId
                && !sync_order_->dealInfo.dealId.ctpDealId.exchangeId.empty()) {
                strncpy(qry_trade_field.ExchangeID
                        , sync_order_->dealInfo.dealId.ctpDealId.exchangeId.c_str()
                        , sizeof(TThostFtdcExchangeIDType) - 1);
            }
        }
        if (sync_order_->dealInfo.__isset.orderInsertDateTime
                && !sync_order_->dealInfo.orderInsertDateTime.empty()) {
            int32_t insert_timestamp
                = soldier::base::String2Timestamp(sync_order_->dealInfo.orderInsertDateTime, "%Y-%m-%d %H:%M:%S");
            if (insert_timestamp > 0) {
                std::string insert_time = soldier::base::Timestamp2String(insert_timestamp, "%H:%M:%S");
                strncpy(qry_trade_field.TradeTimeStart, insert_time.c_str(), sizeof(TThostFtdcTimeType) - 1);
            }
        }
    }

    APPLOG_INFO("OrderSyncTradesHandler OnStart BrokerID={}, UserID={}, InstrumentID={}, ExchangeID={}, TradeTimeStart={}"
            , login_info_->BrokerID, login_info_->UserID, qry_trade_field.InstrumentID
            , qry_trade_field.ExchangeID, qry_trade_field.TradeTimeStart);
    return trader_api->ReqQryTrade(&qry_trade_field, getRequestId());
}

void OrderSyncTradesHandler::OnRspQryTrade(
        CThostFtdcTradeField *pTrade
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pTrade) {
        order_sync_trades_rsp_->trades.push_back(*pTrade);
    }
    order_sync_trades_rsp_->setCtpRspInfo(pRspInfo);
}
