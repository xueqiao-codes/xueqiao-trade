/*
 * ctp_rate_manager.cpp
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */
#include "ctp_rate_manager.h"

#include <string.h>
#include <memory>
#include "hosting_message_sender.h"
#include "trade_hosting_upside_events_types.h"

using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::events;
using namespace xueqiao::trade::hosting::upside::entry;

CTPRateManager::CTPRateManager(const std::shared_ptr<CTPDataManager>& data_manager
        , const std::shared_ptr<CTPLoginManager>& login_manager
        , const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher)
    : data_manager_(data_manager)
      , login_manager_(login_manager)
      , dispatcher_(dispatcher)
      , work_thread_(new TaskThread()){
}

CTPRateManager::~CTPRateManager() {
}

std::shared_ptr<std::map<std::string, std::shared_ptr<CTPProductRate>>> CTPRateManager::getRates() {
    std::unique_lock<std::mutex> auto_lock(rates_lock_);
    return product_rates_;
}

void CTPRateManager::onDataManagerInitStart() {
    work_thread_->postTask(&CTPRateManager::handleInitStart, this);
}

void CTPRateManager::onQryAllInstrumentsFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp) {
    work_thread_->postTask(&CTPRateManager::handleAllInstrumentFinished, this, instruments_rsp);
}

void CTPRateManager::onQryExchangeMarginRatesFinished(
        const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp) {
    work_thread_->postTask(&CTPRateManager::handleExchangeMarginRates, this, exchange_margin_rates_rsp);
}

void CTPRateManager::onQryInstrumentMarginRatesFinished(
        const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp) {
    work_thread_->postTask(&CTPRateManager::handleInstrumentMarginRates, this, instrument_margin_rates_rsp);
}

void CTPRateManager::onQryInstrumentCommissionRatesFinished(
        const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp) {
    work_thread_->postTask(&CTPRateManager::handleInstrumentCommissionRates, this, instrument_commission_rates_rsp);
}

void CTPRateManager::onDataManagerInitFinished() {
    work_thread_->postTask(&CTPRateManager::handleInitFinished, this);
}

void CTPRateManager::handleInitStart() {

}

void CTPRateManager::handleInitFinished() {
    APPLOG_INFO("handleInitFinished, start schedule task...");
    scheduleTask();
}

void CTPRateManager::handleAllInstrumentFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp) {
    APPLOG_INFO("handleAllInstrumentFinished size={}", instruments_rsp->instruments.size());
    for (auto& instrument: instruments_rsp->instruments) {
        APPLOG_INFO("InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                , instrument.InstrumentID, instrument.ExchangeID
                , instrument.ProductClass, instrument.ProductID);
        if (product_contracts_.find(instrument.ProductID) == product_contracts_.end()) {
            product_contracts_[instrument.ProductID] = std::set<std::string>();
        }
        product_contracts_[instrument.ProductID].insert(instrument.InstrumentID);

        waiting_instruments_.push_back(instrument.InstrumentID);
    }

    APPLOG_INFO("handleAllInstrumentFinished, product_contracts_.size={}", product_contracts_.size());
}

void CTPRateManager::handleExchangeMarginRates(
        const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp) {
    APPLOG_INFO("handleExchangeMarginRates size={}...", exchange_margin_rates_rsp->rates.size());
    for (auto& rate : exchange_margin_rates_rsp->rates) {
        APPLOG_INFO("InstrumentID={}, HedgeFlag={}, LongMarginRatioByMoney={}, LongMarginRatioByVolume={}"
                ", ShortMarginRatioByMoney={}, ShortMarginRatioByVolume={}"
                , rate.InstrumentID, rate.HedgeFlag
                , rate.LongMarginRatioByMoney, rate.LongMarginRatioByVolume
                , rate.ShortMarginRatioByMoney, rate.ShortMarginRatioByVolume);
        exchange_margins_[rate.InstrumentID] = std::shared_ptr<CThostFtdcExchangeMarginRateField>(
                new CThostFtdcExchangeMarginRateField(rate));
    }
}

void CTPRateManager::handleInstrumentMarginRates(
        const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp) {
    APPLOG_INFO("handleInstrumentMarginRates size={}...", instrument_margin_rates_rsp->rates.size());
    for (auto& rate : instrument_margin_rates_rsp->rates) {
        APPLOG_INFO("InstrumentID={}, InvestorRange={}, HedgeFlag={}, LongMarginRatioByMoney={}"
                ", LongMarginRatioByVolume={}, ShortMarginRatioByMoney={}"
                ", ShortMarginRatioByVolume={}, IsRelative={}"
                , rate.InstrumentID, rate.InvestorRange, rate.HedgeFlag
                , rate.LongMarginRatioByMoney, rate.LongMarginRatioByVolume
                , rate.ShortMarginRatioByMoney, rate.ShortMarginRatioByVolume
                , rate.IsRelative);
        instrument_margins_[rate.InstrumentID] = std::shared_ptr<CThostFtdcInstrumentMarginRateField>(
                new CThostFtdcInstrumentMarginRateField(rate));
    }
}

void CTPRateManager::handleInstrumentCommissionRates(
        const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp) {
    APPLOG_INFO("handleInstrumentCommissionRates size={}..."
            , instrument_commission_rates_rsp->rates.size());
    for (auto& rate : instrument_commission_rates_rsp->rates) {
        APPLOG_INFO("InstrumentID={}, InvestorRange={}, OpenRatioByMoney={}, OpenRatioByVolume={},"
                ", CloseRatioByMoney={}, CloseRatioByVolume={}"
                ", CloseTodayRatioByMoney={}, CloseTodayRatioByVolume={}"
                , rate.InstrumentID, rate.InvestorRange, rate.OpenRatioByMoney, rate.OpenRatioByVolume
                , rate.CloseRatioByMoney, rate.CloseRatioByVolume
                , rate.CloseTodayRatioByMoney, rate.CloseTodayRatioByVolume);
        commissions_[rate.InstrumentID] = std::shared_ptr<CThostFtdcInstrumentCommissionRateField>(
                new CThostFtdcInstrumentCommissionRateField(rate));
    }
}

void CTPRateManager::scheduleTask() {
    qrying_instrument_id_.reset();

    if (waiting_instruments_.empty()) {
        // 任务全部完成，整理得出最终的数据结构
        collationResult();
        return ;
    }

    qrying_instrument_id_.reset(new std::string(waiting_instruments_.front()));
    waiting_instruments_.pop_front();

    work_thread_->postTask(&CTPRateManager::qryInstrumentMargin, this);
}

void CTPRateManager::qryInstrumentMargin() {
    auto it = instrument_margins_.find(*qrying_instrument_id_);
    if (it != instrument_margins_.end()) {
        work_thread_->postTask(&CTPRateManager::qryInstrumentCommission, this);
        return ;
    }

    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInstrumentMarginRateField> req_field(new CThostFtdcQryInstrumentMarginRateField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInstrumentMarginRateField));
        strncpy(req_field->BrokerID, login_manager_->getLoginRsp()->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_manager_->getLoginRsp()->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
        strncpy(req_field->InstrumentID, qrying_instrument_id_->c_str(), sizeof(TThostFtdcInstrumentIDType) - 1);

        req_field->HedgeFlag = THOST_FTDC_HF_Speculation;
        std::shared_ptr<QryInstrumentMarginRateHandler> qry_instrument_margin_rates_hander(new QryInstrumentMarginRateHandler(
                    QryInstrumentMarginRateHandler::CallbackFunction(
                            std::bind(&CTPRateManager::onQryInstrumentMarginFinished, this, std::placeholders::_1))
                            , req_field));
        qry_instrument_margin_rates_hander->setCallbackThread(work_thread_);
        ret = dispatcher_->sendRequest(qry_instrument_margin_rates_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1500));
        }
    } while(ret != 0);
}

void CTPRateManager::onQryInstrumentMarginFinished(
        const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp) {
    APPLOG_INFO("onQryInstrumentMarginFinished instrument_id={} hasTimeout={}, errorCode={}, rates size={}"
                , *qrying_instrument_id_
                , instrument_margin_rates_rsp->hasTimeOut()
                , instrument_margin_rates_rsp->getErrorCode()
                , instrument_margin_rates_rsp->rates.size());

    if (instrument_margin_rates_rsp->hasTimeOut() || instrument_margin_rates_rsp->getErrorCode() != 0) {
        qryInstrumentMargin();
        return ;
    }

    for (auto& rate : instrument_margin_rates_rsp->rates) {
        APPLOG_INFO("InstrumentID={}, InvestorRange={}, HedgeFlag={}, LongMarginRatioByMoney={}"
                ", LongMarginRatioByVolume={}, ShortMarginRatioByMoney={}"
                ", ShortMarginRatioByVolume={}, IsRelative={}"
                , rate.InstrumentID, rate.InvestorRange, rate.HedgeFlag
                , rate.LongMarginRatioByMoney, rate.LongMarginRatioByVolume
                , rate.ShortMarginRatioByMoney, rate.ShortMarginRatioByVolume
                , rate.IsRelative);
        instrument_margins_[rate.InstrumentID] = std::shared_ptr<CThostFtdcInstrumentMarginRateField>(
                new CThostFtdcInstrumentMarginRateField(rate));
    }

    work_thread_->postTask(&CTPRateManager::qryInstrumentCommission, this);
}

void CTPRateManager::qryInstrumentCommission() {
    auto it = commissions_.find(*qrying_instrument_id_);
    if (it != commissions_.end()) {
        work_thread_->postTask(&CTPRateManager::scheduleTask, this);
        return ;
    }

    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInstrumentCommissionRateField> req_field(new CThostFtdcQryInstrumentCommissionRateField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInstrumentCommissionRateField));
        strncpy(req_field->BrokerID, login_manager_->getLoginRsp()->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_manager_->getLoginRsp()->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
        strncpy(req_field->InstrumentID, qrying_instrument_id_->c_str(), sizeof(TThostFtdcInstrumentIDType) - 1);

        std::shared_ptr<QryInstrumentCommissionRateHandler> qry_instrument_commission_rates_hander(new QryInstrumentCommissionRateHandler(
                QryInstrumentCommissionRateHandler::CallbackFunction(
                        std::bind(&CTPRateManager::onQryInstrumentCommissionFinished, this, std::placeholders::_1))
                , req_field));
        qry_instrument_commission_rates_hander->setCallbackThread(work_thread_);
        ret = dispatcher_->sendRequest(qry_instrument_commission_rates_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1500));
        }
    } while(ret != 0);
}

void CTPRateManager::onQryInstrumentCommissionFinished(
        const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp) {
    APPLOG_INFO("onQryInstrumentCommissionFinished instrumentId={} hasTimeout={}, errorCode={}, rates size={}"
            , *qrying_instrument_id_
            , instrument_commission_rates_rsp->hasTimeOut()
            , instrument_commission_rates_rsp->getErrorCode()
            , instrument_commission_rates_rsp->rates.size());
    if (instrument_commission_rates_rsp->hasTimeOut() || instrument_commission_rates_rsp->getErrorCode() != 0) {
        qryInstrumentCommission();
        return ;
    }

    for (auto& rate : instrument_commission_rates_rsp->rates) {
        APPLOG_INFO("InstrumentID={}, InvestorRange={}, OpenRatioByMoney={}, OpenRatioByVolume={},"
                ", CloseRatioByMoney={}, CloseRatioByVolume={}"
                ", CloseTodayRatioByMoney={}, CloseTodayRatioByVolume={}"
                , rate.InstrumentID, rate.InvestorRange, rate.OpenRatioByMoney, rate.OpenRatioByVolume
                , rate.CloseRatioByMoney, rate.CloseRatioByVolume
                , rate.CloseTodayRatioByMoney, rate.CloseTodayRatioByVolume);
        commissions_[rate.InstrumentID] = std::shared_ptr<CThostFtdcInstrumentCommissionRateField>(
                new CThostFtdcInstrumentCommissionRateField(rate));
    }

    work_thread_->postTask(&CTPRateManager::scheduleTask, this);
}

void CTPRateManager::collationResult() {
    APPLOG_INFO("collationResult...");

    std::shared_ptr<std::map<std::string, std::shared_ptr<CTPProductRate>>>
        result_product_rates(new std::map<std::string, std::shared_ptr<CTPProductRate>>());

    for (auto& it : product_contracts_) {
        std::string product_id = it.first;

        std::shared_ptr<CTPProductRate> product_rate(new CTPProductRate());
        product_rate->product_id = product_id;

        if (exchange_margins_.find(product_id) != exchange_margins_.end()) {
            product_rate->exchange_margin = exchange_margins_[product_id];
        }

        if (instrument_margins_.find(product_id) != instrument_margins_.end()) {
            product_rate->instrument_margin = instrument_margins_[product_id];
        }

        if (commissions_.find(product_id) != commissions_.end()) {
            product_rate->commission = commissions_[product_id];
        }

        for (auto& instrument_id : it.second) {
            if (product_rate->exchange_id.empty()) {
                std::shared_ptr<CThostFtdcInstrumentField> instrument_field
                    = data_manager_->getInstrumentsHolder().getInstrument(instrument_id.c_str());
                if (instrument_field) {
                    product_rate->exchange_id = instrument_field->ExchangeID;
                    product_rate->product_class = instrument_field->ProductClass;
                    product_rate->product_id = instrument_field->ProductID;
                }
            }

            std::shared_ptr<CTPContractRate> contract_rate(new CTPContractRate());
            contract_rate->instrument_id = instrument_id;

            if (exchange_margins_.find(instrument_id) != exchange_margins_.end()) {
                contract_rate->exchange_margin = exchange_margins_[instrument_id];
            }

            if (instrument_margins_.find(instrument_id) != instrument_margins_.end()) {
                contract_rate->instrument_margin = instrument_margins_[instrument_id];
            }

            if (commissions_.find(instrument_id) != commissions_.end()) {
                contract_rate->commission = commissions_[instrument_id];
            }

            product_rate->contract_rates[instrument_id] = contract_rate;
        }

        (*result_product_rates)[product_id] = product_rate;
    }

    {
        std::unique_lock<std::mutex> auto_lock(rates_lock_);
        product_rates_ = result_product_rates;
        APPLOG_INFO("collationResult finished, product results size={}", product_rates_->size());
    }

    SEND_MESSAGE_BEGIN(UpsidePositionRateDetailsUpdatedEvent, updated_event)
    updated_event.__set_tradeAccountId(login_manager_->getTradeAccount()->tradeAccountId);
    SEND_MESSAGE_END(updated_event)
}

