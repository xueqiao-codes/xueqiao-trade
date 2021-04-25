/*
 * order_delete_handler.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#include "order_delete_handler.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"
#include "errorinfo_helper.h"
#include "trade_hosting_basic_errors_types.h"

using namespace ctpext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::upside::entry;

OrderDeleteHandler::OrderDeleteHandler(
        CallbackFunction callback
        , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
        , const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& delete_order)
    : CTPRequestWithCallback(callback)
      , login_info_(login_info)
      , delete_order_(delete_order)
      , delete_rsp_(new OrderDeleteResp()){
    CHECK(login_info_);
    CHECK(delete_order_);
    delete_rsp_->delete_order = delete_order;
}

int OrderDeleteHandler::onStart(CThostFtdcTraderApi* trader_api) {
    CThostFtdcInputOrderActionField delete_order_field;
    memset(&delete_order_field, 0, sizeof(CThostFtdcInputOrderActionField));
    delete_order_field.ActionFlag = THOST_FTDC_AF_Delete;

    strncpy(delete_order_field.BrokerID, login_info_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(delete_order_field.InvestorID, login_info_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
    strncpy(delete_order_field.InstrumentID
            , delete_order_->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str()
            , sizeof(TThostFtdcInstrumentIDType) - 1);
	strncpy(delete_order_field.ExchangeID
            , delete_order_->orderInputExt.ctpInputExt.contractSummary.ctpExchangeCode.c_str()
            , sizeof(TThostFtdcExchangeIDType) - 1);

    bool field_param_finished = false;
    if (delete_order_->upsideOrderRef.__isset.ctpRef) {
        // 具备订单引用信息，直接利用订单引用撤掉订单
        const CTPOrderRef& ctp_ref = delete_order_->upsideOrderRef.ctpRef;
        if (ctp_ref.__isset.frontID && ctp_ref.__isset.sessionID
                && ctp_ref.__isset.orderRef && !ctp_ref.orderRef.empty()) {
            delete_order_field.FrontID = ctp_ref.frontID;
            delete_order_field.SessionID = ctp_ref.sessionID;
            strncpy(delete_order_field.OrderRef, ctp_ref.orderRef.c_str(), sizeof(TThostFtdcOrderRefType) - 1);
            field_param_finished = true;
        }
    }

    if (delete_order_->dealInfo.dealId.__isset.ctpDealId) {
        const CTPDealID& ctp_dealId = delete_order_->dealInfo.dealId.ctpDealId;
        if (ctp_dealId.__isset.exchangeId && !ctp_dealId.exchangeId.empty()
                && ctp_dealId.__isset.orderSysId && !ctp_dealId.orderSysId.empty()) {
            strncpy(delete_order_field.ExchangeID, ctp_dealId.exchangeId.c_str(), sizeof(TThostFtdcExchangeIDType) - 1);
            strncpy(delete_order_field.OrderSysID, ctp_dealId.orderSysId.c_str(), sizeof(TThostFtdcOrderSysIDType) - 1);
            field_param_finished = true;
        }
    }

    if (!field_param_finished) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO
                , "orderDelete dealinfo is not complete");
    }

    return trader_api->ReqOrderAction(&delete_order_field, getRequestId());
}

void OrderDeleteHandler::OnRspOrderAction(
        CThostFtdcInputOrderActionField *pInputOrderAction
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    if (pInputOrderAction) {
        delete_rsp_->action_field.reset(new CThostFtdcInputOrderActionField(*pInputOrderAction));
    }
    delete_rsp_->setCtpRspInfo(pRspInfo);
}

