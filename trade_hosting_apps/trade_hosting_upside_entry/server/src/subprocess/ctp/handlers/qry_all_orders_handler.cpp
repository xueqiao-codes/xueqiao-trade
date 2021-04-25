/*
 * qry_all_orders_handler.cpp
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */
#include "qry_all_orders_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

QryAllOrdersHandler::QryAllOrdersHandler(CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , all_orders_resp_(new QryAllOrdersResp()) {
    CHECK(login_info_);
}

int QryAllOrdersHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcQryOrderField qry_order_field;
    memset(&qry_order_field, 0, sizeof(CThostFtdcQryOrderField));
    strncpy(qry_order_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(qry_order_field.InvestorID,  login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    return trader_api->ReqQryOrder(&qry_order_field, getRequestId());
}

void QryAllOrdersHandler::OnRspQryOrder(
        CThostFtdcOrderField *pOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pOrder) {
        all_orders_resp_->orders.push_back(*pOrder);
    }
    all_orders_resp_->setCtpRspInfo(pRspInfo);
}



