/*
 * order_sync_state_handler.cpp
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */
#include "order_sync_state_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


OrderSyncStateHandler::OrderSyncStateHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order)
    : CTPRequestWithCallback(callback)
     , login_info_(login_info)
     , sync_order_(sync_order)
     , order_sync_state_resp_(new OrderSyncStateResp()){
    CHECK(login_info_);
    CHECK(sync_order_);
    order_sync_state_resp_->sync_order = sync_order_;
}

int OrderSyncStateHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryOrderField qry_order_field;
    memset(&qry_order_field, 0, sizeof(CThostFtdcQryOrderField));
    strncpy(qry_order_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(qry_order_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    strncpy(qry_order_field.InstrumentID
            , sync_order_->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str()
            , sizeof(TThostFtdcInstrumentIDType) - 1);
	strncpy(qry_order_field.ExchangeID
            , sync_order_->orderInputExt.ctpInputExt.contractSummary.ctpExchangeCode.c_str()
            , sizeof(TThostFtdcExchangeIDType) - 1);

    if (sync_order_->__isset.dealInfo
            && sync_order_->dealInfo.__isset.dealId
            && sync_order_->dealInfo.dealId.__isset.ctpDealId) {
            const CTPDealID& ctp_deal_id = sync_order_->dealInfo.dealId.ctpDealId;
            if (!ctp_deal_id.exchangeId.empty() && !ctp_deal_id.orderSysId.empty()) {
                strncpy(qry_order_field.ExchangeID, ctp_deal_id.exchangeId.c_str(), sizeof(TThostFtdcExchangeIDType) - 1);
                strncpy(qry_order_field.OrderSysID, ctp_deal_id.orderSysId.c_str(), sizeof(TThostFtdcOrderSysIDType) - 1);
                order_sync_state_resp_->sync_order_has_dealinfo = true;
            }
    }

    return trader_api->ReqQryOrder(&qry_order_field, getRequestId());
}

void OrderSyncStateHandler::OnRspQryOrder(
        CThostFtdcOrderField *pOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pOrder) {
        order_sync_state_resp_->resp_orders.push_back(*pOrder);
    }
    order_sync_state_resp_->setCtpRspInfo(pRspInfo);
}


