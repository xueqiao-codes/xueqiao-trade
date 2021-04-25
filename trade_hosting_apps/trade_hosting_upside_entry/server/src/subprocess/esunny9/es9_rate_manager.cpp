/*
 * es9_rate_manager.cpp
 *
 *  Created on: 2019年4月3日
 *      Author: 44385
 */

#include "es9_rate_manager.h"

#include <sstream>
#include "hosting_message_sender.h"
#include "trade_hosting_upside_events_types.h"

using namespace soldier::base;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::events;
using namespace xueqiao::trade::hosting::upside::entry;


Es9RateManager::Es9RateManager(const std::shared_ptr<Es9LoginManager>& login_manager
        , const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher)
    : login_manager_(login_manager)
      , request_dispatcher_(request_dispatcher)
      , work_thread_(new TaskThread()){
}

Es9RateManager::~Es9RateManager() {
}

std::shared_ptr<std::map<std::string, std::shared_ptr<Es9CommodityRate>>> Es9RateManager::getRates() {
    std::unique_lock<std::mutex> auto_lock(rates_lock_);
    return commodity_rates_;
}

void Es9RateManager::onEs9DataManagerInitStart() {
    work_thread_->postTask(&Es9RateManager::handleInitStart, this);
}

void Es9RateManager::onEs9QryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp) {
    work_thread_->postTask(&Es9RateManager::handleQryCommoditiesFinished, this, commodity_resp);
}

void Es9RateManager::onEs9QryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp) {
    work_thread_->postTask(&Es9RateManager::handleQryAccountFeeFinished, this, account_fee_resp);
}

void Es9RateManager::onEs9DataManagerInitFinished() {
    work_thread_->postTask(&Es9RateManager::handleInitFinished, this);
}

void Es9RateManager::handleInitStart() {
}

void Es9RateManager::handleInitFinished() {
    APPLOG_INFO("handleInitFinished, start schedule task...");
    scheduleTask();
}

void Es9RateManager::handleQryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp) {
    APPLOG_INFO("handleQryCommoditiesFinished commodities.size()={}", commodity_resp->commodities.size());
    for (auto& commodity : commodity_resp->commodities) {
        APPLOG_INFO("ExchangeNo={}, CommodityType={}, CommodityNo={}, CommodityEngName={}"
                ", RelateExchangeNo={}, RelateCommodityType={}, RelateCommodityNo={}, RelateExchangeNo2={}"
                ", RelateCommodityType2={}, RelateCommodityNo2={}, CurrencyGroupNo={}, TradeCurrency={}"
                ", ContractSize={}, OpenCloseMode={}, StrikePriceTimes={}, CommodityTickSize={}"
                ", CommodityDenominator={}, CmbDirect={}, DeliveryMode={}, DeliveryDays={}"
                ", AddOneTime={}, CommodityTimeZone={}, IsAddOne={}"
                , commodity.ExchangeNo, commodity.CommodityType, commodity.CommodityNo, commodity.CommodityEngName
                , commodity.RelateCommodityNo, commodity.RelateCommodityType, commodity.RelateExchangeNo, commodity.RelateExchangeNo2
                , commodity.RelateCommodityNo2, commodity.RelateCommodityNo2, commodity.CurrencyGroupNo, commodity.TradeCurrency
                , commodity.ContractSize, commodity.OpenCloseMode, commodity.StrikePriceTimes, commodity.CommodityTickSize
                , commodity.CommodityDenominator, commodity.CmbDirect, commodity.DeliveryMode, commodity.DeliveryDays
                , commodity.AddOneTime, commodity.CommodityTimeZone, commodity.IsAddOne);

        if (commodity.CommodityType != ITapTrade::TAPI_COMMODITY_TYPE_FUTURES) {
            // 非期货商品不处理
            continue;
        }

        if (waiting_commodity_types_.find(commodity.CommodityType) == waiting_commodity_types_.end()) {
            waiting_commodity_types_.insert(commodity.CommodityType);
        }

        commodities_[getKey(commodity)] = commodity;
    }

}

void Es9RateManager::handleQryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp) {
    APPLOG_INFO("handleQryAccountFeeFinished fees.size()={}", account_fee_resp->fees.size());
    for (auto& fee : account_fee_resp->fees) {
        APPLOG_INFO("ExchangeNo={}, CommodityType={}, CommodityNo={}, MatchSource={}, CalculateMode={}"
                ", CurrencyGroupNo={}, CurrencyNo={}, OpenCloseFee={}, CloseTodayFee={}"
                , fee.ExchangeNo, fee.CommodityType, fee.CommodityNo, fee.MatchSource, fee.CalculateMode
                , fee.CurrencyGroupNo, fee.CurrencyNo, fee.OpenCloseFee, fee.CloseTodayFee);

        account_fees_[getKey(fee.ExchangeNo, fee.CommodityType, fee.CommodityNo)]
                      = std::shared_ptr<ITapTrade::TapAPIAccountFeeRentQryRsp>(
                              new ITapTrade::TapAPIAccountFeeRentQryRsp(fee));
    }
}

std::string Es9RateManager::getKey(const ITapTrade::TapAPICommodityInfo& commodity) {
    return getKey(commodity.ExchangeNo, commodity.CommodityType, commodity.CommodityNo);
}

std::string Es9RateManager::getKey(
        const std::string& exchange_no
        , ITapTrade::TAPICommodityType commodity_type
        , const std::string& commodity_no) {
    std::stringstream ss;
    ss << exchange_no
       << "_" << commodity_type
       << "_" << commodity_no;
    return ss.str();
}

std::string Es9RateManager::getKey(const std::string& exchange_no
        , ITapTrade::TAPICommodityType commodity_type
        , const std::string& commodity_no
        , const std::string& contract_code
        , const std::string& strick_price
        , ITapTrade::TAPICallOrPutFlagType call_or_put_flag) {
    std::stringstream ss;
    ss << exchange_no
       << "_" << commodity_type
       << "_" << commodity_no
       << "_" << contract_code;
    if (!strick_price.empty()) {
        ss << "_" << strick_price << "_" << call_or_put_flag;
    }
    return ss.str();
}

void Es9RateManager::scheduleTask() {
    qrying_commodity_type_.reset();

    if (waiting_commodity_types_.empty()) {
        collationResult();
        return ;
    }

    qrying_commodity_type_.reset(new ITapTrade::TAPICommodityType(*(waiting_commodity_types_.begin())));
    waiting_commodity_types_.erase(*qrying_commodity_type_);

    work_thread_->postTask(&Es9RateManager::sendQryAccountMargin, this);
}

void Es9RateManager::sendQryAccountMargin() {
    int ret = 0;
    do {
        std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryReq> req(new ITapTrade::TapAPIAccountMarginRentQryReq());
        memset(req.get(), 0, sizeof(ITapTrade::TapAPIAccountMarginRentQryReq));
        strcpy(req->AccountNo, login_manager_->getLoginRspData()->account_infos[0].AccountNo);
//        strcpy(req->ExchangeNo, qrying_commodity_->ExchangeNo);
        req->CommodityType = *qrying_commodity_type_;
//        strcpy(req->CommodityNo, qrying_commodity_->CommodityNo);

        APPLOG_INFO("sendQryAccountMargin AccountNo={}, CommodityType={}"
                    , req->AccountNo
                    , req->CommodityType);

        std::shared_ptr<Es9QryAccountMarginHandler> qry_handler(
                new Es9QryAccountMarginHandler(
                        Es9QryAccountMarginHandler::CallbackFunction(
                                std::bind(&Es9RateManager::onRspQryAccountMarginFinished, this, std::placeholders::_1))
                , req));
        qry_handler->setCallbackThread(work_thread_);
        ret = request_dispatcher_->sendRequest(qry_handler);
        if (0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1500));
        }
    } while(0 != ret);
}

void Es9RateManager::onRspQryAccountMarginFinished(
        const std::shared_ptr<Es9QryAccountMarginResp>& account_margin_resp) {
    APPLOG_INFO("onRspQryAccountMarginFinished commodity_type={}, errorCode={}, margins.size={}"
            , *qrying_commodity_type_, account_margin_resp->getErrorCode()
            , account_margin_resp->margins.size());
    if (account_margin_resp->getErrorCode() != 0) {
        sendQryAccountMargin();
        return ;
    }

    for (auto& margin : account_margin_resp->margins) {
        APPLOG_INFO("ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}, StrikePrice={}"
                ", CallOrPutFlag={}, CalculateMode={}, CurrencyGroupNo={}, CurrencyNo={}"
                ", InitialMargin={}, MaintenanceMargin={}, SellInitialMargin={}"
                ", SellMaintenanceMargin={}, LockMargin={}"
                , margin.ExchangeNo, margin.CommodityType, margin.CommodityNo, margin.ContractNo
                , margin.StrikePrice, margin.CallOrPutFlag, margin.CalculateMode
                , margin.CurrencyGroupNo, margin.CurrencyNo
                , margin.InitialMargin, margin.MaintenanceMargin, margin.SellInitialMargin
                , margin.SellMaintenanceMargin, margin.LockMargin);

        std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryRsp>
                account_margin(new ITapTrade::TapAPIAccountMarginRentQryRsp(margin));
        std::string commodity_key = getKey(margin.ExchangeNo, margin.CommodityType, margin.CommodityNo);
        if (margin.ContractNo[0] != 0) {
            std::string contract_key = getKey(margin.ExchangeNo
                    , margin.CommodityType
                    , margin.CommodityNo
                    , margin.ContractNo
                    , margin.StrikePrice
                    , margin.CallOrPutFlag);
            commodity_contracts_[commodity_key].insert(contract_key);
            account_margins_[contract_key] = account_margin;
        } else {
            account_margins_[commodity_key]= account_margin;
        }
    }

    work_thread_->postTask(&Es9RateManager::scheduleTask, this);
}

void Es9RateManager::collationResult() {
    APPLOG_INFO("collationResult...");

    std::shared_ptr<std::map<std::string, std::shared_ptr<Es9CommodityRate>>>
            result_commodity_rates(new std::map<std::string, std::shared_ptr<Es9CommodityRate>>());
    for (auto& commodity_it : commodities_) {
        std::shared_ptr<Es9CommodityRate> commodity_rate(new Es9CommodityRate());
        commodity_rate->exchange_no = commodity_it.second.ExchangeNo;
        commodity_rate->commodity_type = commodity_it.second.CommodityType;
        commodity_rate->commodity_no = commodity_it.second.CommodityNo;

        std::string commodity_key = getKey(commodity_it.second);

        if (account_fees_.find(commodity_key) != account_fees_.end()) {
            commodity_rate->account_fee = account_fees_[commodity_key];
        }

        if (account_margins_.find(commodity_key) != account_margins_.end()) {
            commodity_rate->account_margin = account_margins_[commodity_key];
        }

        (*result_commodity_rates)[commodity_key] = commodity_rate;

        auto contract_keys_it = commodity_contracts_.find(commodity_key);
        if (contract_keys_it == commodity_contracts_.end()) {
            continue;
        }

        for (auto& contract_key : contract_keys_it->second) {
            auto contact_margin_rent_it = account_margins_.find(contract_key);
            if (contact_margin_rent_it == account_margins_.end()) {
                continue;
            }

            std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryRsp>
                contract_account_margin = contact_margin_rent_it->second;
            if (!contract_account_margin) {
                continue;
            }

            Es9ContractRate contract_rate;
            contract_rate.contract_no = contract_account_margin->ContractNo;
            contract_rate.strike_price = contract_account_margin->StrikePrice;
            contract_rate.call_or_put_flag = contract_account_margin->CallOrPutFlag;
            contract_rate.account_margin = contract_account_margin;
            commodity_rate->contract_rates.push_back(contract_rate);
        }
    }

    {
        std::unique_lock<std::mutex> auto_lock(rates_lock_);
        commodity_rates_ = result_commodity_rates;
        APPLOG_INFO("collationResult finished, commodities results size={}", commodity_rates_->size());
    }

    SEND_MESSAGE_BEGIN(UpsidePositionRateDetailsUpdatedEvent, updated_event)
    updated_event.__set_tradeAccountId(login_manager_->getTradeAccount()->tradeAccountId);
    SEND_MESSAGE_END(updated_event)
}


