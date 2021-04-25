/*
 * order_sync_state_batch_handler.cpp
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */
#include "order_sync_state_batch_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

OrderSyncStateBatchHandler::OrderSyncStateBatchHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::shared_ptr<TSyncOrderStateBatchReq>& batch_req)
    : CTPRequestWithCallback(callback)
     , login_info_(login_info)
     , batch_req_(batch_req)
     , order_sync_state_batch_resp_(new OrderSyncStateBatchResp()){
    CHECK(login_info_);
    CHECK(batch_req_);
    order_sync_state_batch_resp_->batch_req = batch_req_;
}

int OrderSyncStateBatchHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryOrderField qry_order_field;
    memset(&qry_order_field, 0, sizeof(CThostFtdcQryOrderField));
    strncpy(qry_order_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(qry_order_field.InvestorID,  login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    if (batch_req_->__isset.ctpContractSummary) {
        if (!batch_req_->ctpContractSummary.ctpContractCode.empty()) {
            strncpy(qry_order_field.InstrumentID
                    , batch_req_->ctpContractSummary.ctpContractCode.c_str()
                    , sizeof(TThostFtdcInstrumentIDType) - 1);
        }
    }

    return trader_api->ReqQryOrder(&qry_order_field, getRequestId());
}

void OrderSyncStateBatchHandler::OnRspQryOrder(
        CThostFtdcOrderField *pOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pOrder) {
        order_sync_state_batch_resp_->resp_orders.push_back(*pOrder);
    }
    order_sync_state_batch_resp_->setCtpRspInfo(pRspInfo);
}

