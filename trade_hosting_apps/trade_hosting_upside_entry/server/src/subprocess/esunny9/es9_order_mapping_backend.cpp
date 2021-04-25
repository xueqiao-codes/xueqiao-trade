/*
 * es9_order_mapping_backend.cpp
 *
 *  Created on: 2018年4月16日
 *      Author: 44385
 */

#include "es9_order_mapping_backend.h"

#include "trade_hosting_storage_api.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9OrderMappingBackend::Es9OrderMappingBackend(
        const std::shared_ptr<HostingTradeAccount>& trade_account)
    : trade_account_(trade_account) {
}

std::string Es9OrderMappingBackend::calulateKey(const HostingExecOrderRef& order_ref) {
    if (!order_ref.__isset.esunny9Ref) {
        return "";
    }
    if (!order_ref.esunny9Ref.__isset.refString
           || order_ref.esunny9Ref.refString.empty()) {
        return "";
    }
    return order_ref.esunny9Ref.refString;
}

std::string Es9OrderMappingBackend::calulateKey(const HostingExecOrderDealID& deal_id) {
    if (!deal_id.__isset.esunny9DealId) {
        return "";
    }
    if (!deal_id.esunny9DealId.__isset.orderNo
            || deal_id.esunny9DealId.orderNo.empty()) {
        return "";
    }
    return deal_id.esunny9DealId.orderNo;
}

int64_t Es9OrderMappingBackend::getExecOrderId(const HostingExecOrderRef& order_ref) throw (ErrorInfo) {
    HostingExecOrderTradeAccountSummary account_summary;
    account_summary.__set_tradeAccountId(trade_account_->tradeAccountId);
    account_summary.__set_brokerId(trade_account_->tradeBrokerId);
    account_summary.__set_techPlatform(trade_account_->brokerTechPlatform);
    return HostingDealingAPI::getRunningExecOrderIdByOrderRef(account_summary, order_ref);
}

int64_t Es9OrderMappingBackend::getExecOrderId(const HostingExecOrderDealID& deal_id) throw (ErrorInfo) {
    HostingExecOrderTradeAccountSummary account_summary;
    account_summary.__set_tradeAccountId(trade_account_->tradeAccountId);
    account_summary.__set_brokerId(trade_account_->tradeBrokerId);
    account_summary.__set_techPlatform(trade_account_->brokerTechPlatform);
    return HostingDealingAPI::getRunningExecOrderIdByOrderDealID(account_summary, deal_id);
}
