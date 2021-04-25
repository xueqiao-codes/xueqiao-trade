/*
 * ctp_data_manager.cpp
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#include "ctp_data_manager.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "ctp_order_mapping_backend.h"
#include "trade_hosting_storage_api.h"
#include "trade_hosting_basic_errors_types.h"
#include "thrift/protocol/TDebugProtocol.h"


using namespace apache::thrift;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;

CTPDataManager::CTPDataManager(
        const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
        , const std::shared_ptr<HostingTradeAccount>& trade_account)
    :  dispatcher_(dispatcher)
       , order_mapping_manager_(new CTPOrderMappingBackend(trade_account), 10000)
       , contract_mapping_(1000){
    init_thread_.reset(new soldier::base::TaskThread());
}

void CTPDataManager::onLoginSuccess(const std::shared_ptr<CThostFtdcRspUserLoginField>& login_rsp) {
    login_rsp_ = login_rsp;

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onDataManagerInitStart();
    }

    // 登录后进入重新初始化
    sendQrySettlementInfoConfirm();
}

void CTPDataManager::sendQrySettlementInfoConfirm() {
    int ret = 0;
    do {
        std::shared_ptr<QrySettlementInfoConfirmHandler> qry_handler(
                new QrySettlementInfoConfirmHandler(
                        QrySettlementInfoConfirmHandler::CallbackFunction(
                                std::bind(&CTPDataManager::onQrySettlementInfoConfirmFinished, this, std::placeholders::_1))
                , login_rsp_));
        qry_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_handler);
        if (0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(0 != ret);
}

void CTPDataManager::onQrySettlementInfoConfirmFinished(
        const std::shared_ptr<QrySettlementInfoConfirmResp>& confirm_rsp) {
    if (confirm_rsp->hasTimeOut() || confirm_rsp->getErrorCode() != 0) {
        sendQrySettlementInfoConfirm();
        return ;
    }

    if (confirm_rsp->confirm_field && confirm_rsp->confirm_field->ConfirmDate[0] != 0) {
        APPLOG_INFO("OnRspQrySettlementInfoConfirm BrokerID={}, InvestorID={}, ConfirmDate={}, ConfirmTime={}"
                , confirm_rsp->confirm_field->BrokerID, confirm_rsp->confirm_field->InvestorID
                , confirm_rsp->confirm_field->ConfirmDate, confirm_rsp->confirm_field->ConfirmTime);
        sendQryAllInstruments();
        return ;
    }

    sendReqSettlementInfoConfirm();
}

void CTPDataManager::sendReqSettlementInfoConfirm() {
    int ret = 0;
    do {
        std::shared_ptr<ReqSettlementInfoConfirmHandler> req_handler(
            new ReqSettlementInfoConfirmHandler(
                    ReqSettlementInfoConfirmHandler::CallbackFunction(
                            std::bind(&CTPDataManager::onReqSettlementInfoConfirmFinished, this, std::placeholders::_1))
                , login_rsp_));
        req_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(req_handler);
        if( 0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(0 != ret);
}

void CTPDataManager::onReqSettlementInfoConfirmFinished(const std::shared_ptr<ReqSettlementInfoConfirmResp>& confirm_rsp) {
    if (confirm_rsp->hasTimeOut() || confirm_rsp->getErrorCode() != 0) {
        sendReqSettlementInfoConfirm();
        return ;
    }
    sendQryAllInstruments();
}

void CTPDataManager::sendQryAllInstruments() {
    int ret = 0;
    do {
        std::shared_ptr<QryInstrumentsHandler> instruments_handler(
            new QryInstrumentsHandler(
                    QryInstrumentsHandler::CallbackFunction(
                            std::bind(&CTPDataManager::onQryAllInstrumentsFinished, this, std::placeholders::_1))));
        instruments_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(instruments_handler);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllInstrumentsFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp) {
    APPLOG_INFO("onQryAllInstrumentsFinished hasTimeOut={}, error_code={}"
            , instruments_rsp->hasTimeOut(), instruments_rsp->getErrorCode());
    if (instruments_rsp->hasTimeOut() || instruments_rsp->getErrorCode() != 0) {
        sendQryAllInstruments();
        return ;
    }

    ctp_instruments_holder_.updateAllInstruments(instruments_rsp->instruments);

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryAllInstrumentsFinished(instruments_rsp);
    }

    sendQryAllExchangeMarginRates();
}

void CTPDataManager::sendQryAllExchangeMarginRates() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryExchangeMarginRateField> req_field(new CThostFtdcQryExchangeMarginRateField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryExchangeMarginRateField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        req_field->HedgeFlag = THOST_FTDC_HF_Speculation;
        std::shared_ptr<QryExchangeMarginRateHandler> qry_exchange_margin_rates_hander(new QryExchangeMarginRateHandler(
                QryExchangeMarginRateHandler::CallbackFunction(std::bind(&CTPDataManager::onQryAllExchangeMarginRatesFinished, this, std::placeholders::_1))
             , req_field));
        qry_exchange_margin_rates_hander->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_exchange_margin_rates_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllExchangeMarginRatesFinished(const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp) {
    APPLOG_INFO("onQryAllExchangeMarginRates hasTimeout={}, errorCode={}, rates size={}"
            , exchange_margin_rates_rsp->hasTimeOut()
            , exchange_margin_rates_rsp->getErrorCode()
            , exchange_margin_rates_rsp->rates.size());
    if (exchange_margin_rates_rsp->hasTimeOut() || exchange_margin_rates_rsp->getErrorCode() != 0) {
        sendQryAllExchangeMarginRates();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryExchangeMarginRatesFinished(exchange_margin_rates_rsp);
    }

    sendQryAllInstrumentMarginRates();
}

void CTPDataManager::sendQryAllInstrumentMarginRates() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInstrumentMarginRateField> req_field(new CThostFtdcQryInstrumentMarginRateField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInstrumentMarginRateField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_rsp_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
        req_field->HedgeFlag = THOST_FTDC_HF_Speculation;
        std::shared_ptr<QryInstrumentMarginRateHandler> qry_instrument_margin_rates_hander(new QryInstrumentMarginRateHandler(
                QryInstrumentMarginRateHandler::CallbackFunction(
                        std::bind(&CTPDataManager::onQryAllInstrumentMarginRatesFinished, this, std::placeholders::_1))
                        , req_field));
        qry_instrument_margin_rates_hander->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_instrument_margin_rates_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);

}

void CTPDataManager::onQryAllInstrumentMarginRatesFinished(
        const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp) {
    APPLOG_INFO("onQryAllInstrumentMarginRatesFinished hasTimeout={}, errorCode={}, rates size={}"
                , instrument_margin_rates_rsp->hasTimeOut()
                , instrument_margin_rates_rsp->getErrorCode()
                , instrument_margin_rates_rsp->rates.size());
    if (instrument_margin_rates_rsp->hasTimeOut() || instrument_margin_rates_rsp->getErrorCode() != 0) {
        sendQryAllInstrumentMarginRates();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryInstrumentMarginRatesFinished(instrument_margin_rates_rsp);
    }

    sendQryAllInstrumentCommissionRates();
}

void CTPDataManager::sendQryAllInstrumentCommissionRates() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInstrumentCommissionRateField> req_field(new CThostFtdcQryInstrumentCommissionRateField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInstrumentCommissionRateField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_rsp_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

        std::shared_ptr<QryInstrumentCommissionRateHandler> qry_instrument_commission_rates_hander(new QryInstrumentCommissionRateHandler(
                QryInstrumentCommissionRateHandler::CallbackFunction(
                        std::bind(&CTPDataManager::onQryAllInstrumentCommissionRatesFinished, this, std::placeholders::_1))
                , req_field));
        qry_instrument_commission_rates_hander->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_instrument_commission_rates_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllInstrumentCommissionRatesFinished(
        const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp) {
    APPLOG_INFO("onQryAllInstrumentCommissionRatesFinished hasTimeout={}, errorCode={}, rates size={}"
                , instrument_commission_rates_rsp->hasTimeOut()
                , instrument_commission_rates_rsp->getErrorCode()
                , instrument_commission_rates_rsp->rates.size());
    if (instrument_commission_rates_rsp->hasTimeOut() || instrument_commission_rates_rsp->getErrorCode() != 0) {
        sendQryAllInstrumentCommissionRates();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryInstrumentCommissionRatesFinished(instrument_commission_rates_rsp);
    }

    sendQryAllPositions();
}

void CTPDataManager::sendQryAllPositions() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInvestorPositionField> req_field(new CThostFtdcQryInvestorPositionField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInvestorPositionField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_rsp_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

        std::shared_ptr<QryPositionHandler> position_handler(new QryPositionHandler(
                QryPositionHandler::CallbackFunction(
                        std::bind(&CTPDataManager::onQryPositionsFinished, this, std::placeholders::_1))
                , req_field));
        position_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(position_handler);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryPositionsFinished(const std::shared_ptr<QryPositionResp>& positions_rsp) {
    APPLOG_INFO("onQryPositionsFinished finished, hasTimeOut={}, errorCode={}, positions size={}"
            , positions_rsp->hasTimeOut(), positions_rsp->getErrorCode(), positions_rsp->positions.size());
    if (positions_rsp->hasTimeOut() || positions_rsp->getErrorCode() != 0) {
        sendQryAllPositions();
        return ;
    }

    for (auto& position_field : positions_rsp->positions) {
        APPLOG_INFO("PostionField InstrumentID={}, PosiDirection={}, HedgeFlag={}, PositionDate={}"
                ", YdPosition={}, Position={}, LongFrozen={}, ShortFrozen={}, LongFrozenAmount={}"
                ", ShortFrozenAmount={}, OpenVolume={}, CloseVolume={}, OpenAmount={}, CloseAmount={}"
                ", PositionCost={}, TodayPosition={}",
                position_field.InstrumentID, position_field.PosiDirection, position_field.HedgeFlag, position_field.PositionDate
                , position_field.YdPosition, position_field.Position, position_field.LongFrozen
                , position_field.ShortFrozen, position_field.LongFrozenAmount, position_field.ShortFrozenAmount
                , position_field.OpenVolume, position_field.CloseVolume
                , position_field.OpenAmount, position_field.CloseAmount
                , position_field.PositionCost, position_field.TodayPosition);
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryPositionsFinished(positions_rsp);
    }

    sendQryAllPositionDetails();
}

void CTPDataManager::sendQryAllPositionDetails() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInvestorPositionDetailField> req_field(new CThostFtdcQryInvestorPositionDetailField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInvestorPositionDetailField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_rsp_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
        std::shared_ptr<QryPositionDetailHandler> qry_position_detail_hander(new QryPositionDetailHandler(
                QryPositionDetailHandler::CallbackFunction(std::bind(&CTPDataManager::onQryAllPositionDetailsFinished, this, std::placeholders::_1))
                , req_field));
        qry_position_detail_hander->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_position_detail_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllPositionDetailsFinished(
        const std::shared_ptr<QryPositionDetailResp>& all_position_details_rsp) {
    APPLOG_INFO("onQryAllPositionDetailsFinished hasTimeout={}, errorCode={}, details size={}"
            , all_position_details_rsp->hasTimeOut()
            , all_position_details_rsp->getErrorCode()
            , all_position_details_rsp->details.size());
    if (all_position_details_rsp->hasTimeOut() || all_position_details_rsp->getErrorCode() != 0) {
        sendQryAllPositionDetails();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryPositionDetailsFinished(all_position_details_rsp);
    }

    sendQryAllPositionCombDetails();
}

void CTPDataManager::sendQryAllPositionCombDetails() {
    int ret = 0;
    do {
        std::shared_ptr<CThostFtdcQryInvestorPositionCombineDetailField> req_field(new CThostFtdcQryInvestorPositionCombineDetailField());
        memset(req_field.get(), 0, sizeof(CThostFtdcQryInvestorPositionCombineDetailField));
        strncpy(req_field->BrokerID, login_rsp_->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
        strncpy(req_field->InvestorID, login_rsp_->UserID, sizeof(TThostFtdcInvestorIDType) - 1);
        std::shared_ptr<QryPositionCombDetailHandler> qry_position_detail_hander(new QryPositionCombDetailHandler(
                QryPositionCombDetailHandler::CallbackFunction(
                        std::bind(&CTPDataManager::onQryAllPositionCombDetailsFinished, this, std::placeholders::_1))
            , req_field));
        qry_position_detail_hander->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(qry_position_detail_hander);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllPositionCombDetailsFinished(
        const std::shared_ptr<QryPositionCombDetailResp>& all_position_comb_details_rsp) {
    APPLOG_INFO("onQryAllPositionCombDetailsFinished hasTimeout={}, errorCode={}, details size={}"
            , all_position_comb_details_rsp->hasTimeOut()
            , all_position_comb_details_rsp->getErrorCode()
            , all_position_comb_details_rsp->details.size());
    if (all_position_comb_details_rsp->hasTimeOut() || all_position_comb_details_rsp->getErrorCode() != 0) {
        sendQryAllPositionCombDetails();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryPositionCombDetailsFinished(all_position_comb_details_rsp);
    }

    sendQryAllOrders();
}

void CTPDataManager::sendQryAllOrders() {
    int ret = 0;
    do {
        std::shared_ptr<QryAllOrdersHandler> orders_handler(new QryAllOrdersHandler(
                QryAllOrdersHandler::CallbackFunction(std::bind(&CTPDataManager::onQryAllOrdersFinished, this, std::placeholders::_1))
                , login_rsp_));
        orders_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(orders_handler);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp) {
    APPLOG_INFO("onQryOrdersFinished hasTimeout={}, errorCode={}, orders size={}"
            , all_orders_rsp->hasTimeOut(), all_orders_rsp->getErrorCode(), all_orders_rsp->orders.size());
    if (all_orders_rsp->hasTimeOut() || all_orders_rsp->getErrorCode() != 0) {
        sendQryAllOrders();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryOrdersFinished(all_orders_rsp);
    }

    sendQryAllTrades();
}

void CTPDataManager::sendQryAllTrades() {
    int ret = 0;
    do {
        std::shared_ptr<QryAllTradesHandler> orders_handler(new QryAllTradesHandler(
                QryAllTradesHandler::CallbackFunction(std::bind(&CTPDataManager::onQryAllTradesFinished, this, std::placeholders::_1))
                , login_rsp_));
        orders_handler->setCallbackThread(init_thread_);
        ret = dispatcher_->sendRequest(orders_handler);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void CTPDataManager::onQryAllTradesFinished(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp) {
    APPLOG_INFO("onQryAllTradesFinished hasTimeout={}, errorCode={}, trades size={}"
            , all_trades_rsp->hasTimeOut(), all_trades_rsp->getErrorCode(), all_trades_rsp->trades.size());
    if (all_trades_rsp->hasTimeOut() || all_trades_rsp->getErrorCode() != 0) {
        sendQryAllTrades();
        return ;
    }

    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onQryTradesFinished(all_trades_rsp);
    }

    notifyInitFinished();
}

void CTPDataManager::notifyInitFinished() {
    std::vector<std::shared_ptr<IDataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onDataManagerInitFinished();
    }
}

bool CTPDataManager::sliceCombinationInstrument(
        const std::string& instrument_id
        , std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields) {
    size_t space_n_pos = instrument_id.find_last_of(' ');
    if (space_n_pos == std::string::npos) {
        APPLOG_ERROR("Cannot find space for combination, instrument_id={}", instrument_id);
        return false;
    }
    if (space_n_pos + 1 >= instrument_id.length()) {
        APPLOG_ERROR("No legs declare for combination, instrument_id={}", instrument_id);
        return false;
    }

    std::vector<std::string> leg_instrument_ids;
    soldier::base::StringUtil::tokenize(instrument_id.substr(space_n_pos + 1), leg_instrument_ids, "&", true);
    if (leg_instrument_ids.size() <= 1) {
        APPLOG_ERROR("Leg is too little for combination, instrument_id={}", instrument_id);
        return false;
    }

    for (const std::string& leg_instrument_id : leg_instrument_ids) {
        std::shared_ptr<CThostFtdcInstrumentField> leg_instrument_field
                = getInstrumentsHolder().getInstrument(leg_instrument_id.c_str());
        if (!leg_instrument_field) {
            APPLOG_ERROR("Failed to found leg_instrument_id={} instrument fields , instrument_id={}"
                    , leg_instrument_id, instrument_id);
            return false;
        }
        leg_instrument_fields.push_back(leg_instrument_field);
    }

    return true;
}



