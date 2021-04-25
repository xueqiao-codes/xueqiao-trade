/*
 * ctp_order_mapping_backend.cpp
 *
 *  Created on: 2018年2月7日
 *      Author: wangli
 */

#include "ctp_order_mapping_backend.h"

#include <boost/lexical_cast.hpp>

#include "trade_hosting_storage_api.h"


using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;

CTPOrderMappingBackend::CTPOrderMappingBackend(
        const std::shared_ptr<HostingTradeAccount>& trade_account)
    : trade_account_(trade_account) {
}

std::string CTPOrderMappingBackend::calulateKey(
        const HostingExecOrderRef& order_ref) {
    if (!order_ref.__isset.ctpRef) {
        return "";
    }

    if (!order_ref.ctpRef.__isset.frontID
        || !order_ref.ctpRef.__isset.sessionID
        || !order_ref.ctpRef.__isset.orderRef
        || order_ref.ctpRef.orderRef.empty()) {
        return "";
    }

    std::string order_ref_key;
    order_ref_key.append(boost::lexical_cast<std::string>(order_ref.ctpRef.frontID))
                 .append("_")
                 .append(boost::lexical_cast<std::string>(order_ref.ctpRef.sessionID))
                 .append("_")
                 .append(order_ref.ctpRef.orderRef);
    return order_ref_key;
}

std::string CTPOrderMappingBackend::calulateKey(const HostingExecOrderDealID& deal_id) {
    if (!deal_id.__isset.ctpDealId) {
        return "";
    }

    if (!deal_id.ctpDealId.__isset.exchangeId
         || deal_id.ctpDealId.exchangeId.empty()
         || !deal_id.ctpDealId.__isset.orderSysId
         || deal_id.ctpDealId.orderSysId.empty()) {
        return "";
    }

    std::string deal_id_key;
    deal_id_key.append(deal_id.ctpDealId.exchangeId)
               .append("_").append(deal_id.ctpDealId.orderSysId);
    return deal_id_key;
}

int64_t CTPOrderMappingBackend::getExecOrderId(const HostingExecOrderRef& order_ref) throw (ErrorInfo) {
    HostingExecOrderTradeAccountSummary account_summary;
    account_summary.__set_tradeAccountId(trade_account_->tradeAccountId);
    account_summary.__set_brokerId(trade_account_->tradeBrokerId);
    account_summary.__set_techPlatform(trade_account_->brokerTechPlatform);
    return HostingDealingAPI::getRunningExecOrderIdByOrderRef(account_summary, order_ref);
}

int64_t CTPOrderMappingBackend::getExecOrderId(const HostingExecOrderDealID& deal_id) throw (ErrorInfo) {
    HostingExecOrderTradeAccountSummary account_summary;
    account_summary.__set_tradeAccountId(trade_account_->tradeAccountId);
    account_summary.__set_brokerId(trade_account_->tradeBrokerId);
    account_summary.__set_techPlatform(trade_account_->brokerTechPlatform);
    return HostingDealingAPI::getRunningExecOrderIdByOrderDealID(account_summary, deal_id);
}
