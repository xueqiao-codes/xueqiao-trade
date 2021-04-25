/*
 * es9_data_manager.cpp
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#include "es9_data_manager.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "es9_order_mapping_backend.h"
#include "trade_hosting_storage_api.h"
#include "thrift/protocol/TDebugProtocol.h"


using namespace apache::thrift;
using namespace es9ext::framework;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9DataManager::Es9DataManager(const std::shared_ptr<Es9RequestDispatcher>& request_dispatcher
        , const std::shared_ptr<HostingTradeAccount>& trade_account)
    : request_dispatcher_(request_dispatcher)
      , order_mapping_manager_(new Es9OrderMappingBackend(trade_account), 10000)
      , contract_mapping_(1000)
      , init_thread_(new TaskThread()){
}

void Es9DataManager::onLoginFinished(const std::shared_ptr<LoginRspData>& login_rsp_data) {
    std::vector<std::shared_ptr<IEs9DataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onEs9DataManagerInitStart();
    }

    login_rsp_data_ = login_rsp_data;

    sendQryCommodities();
}

void Es9DataManager::sendQryCommodities() {
    int ret = 0;
    do {
        std::shared_ptr<Es9QryCommodityHandler> qry_handler(
                new Es9QryCommodityHandler(
                        Es9QryCommodityHandler::CallbackFunction(
                                std::bind(&Es9DataManager::onQryCommoditiesFinished, this, std::placeholders::_1))));
        qry_handler->setCallbackThread(init_thread_);
        ret = request_dispatcher_->sendRequest(qry_handler);
        if (0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(0 != ret);
}

void Es9DataManager::onQryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp) {
    APPLOG_INFO("onQryCommoditiesFinished errorCode={}, commodities.size()={}"
            , commodity_resp->getErrorCode(), commodity_resp->commodities.size());

    if (commodity_resp->getErrorCode() != 0) {
        sendQryCommodities();
        return ;
    }

    std::vector<std::shared_ptr<IEs9DataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onEs9QryCommoditiesFinished(commodity_resp);
    }

    sendQryAccountFee();
}

void Es9DataManager::sendQryAccountFee() {
    int ret = 0;
    do {
        std::shared_ptr<Es9QryAccountFeeHandler> qry_handler(
                new Es9QryAccountFeeHandler(
                        Es9QryAccountFeeHandler::CallbackFunction(
                                std::bind(&Es9DataManager::onQryAccountFeeFinished, this, std::placeholders::_1))
                , login_rsp_data_->account_infos[0].AccountNo));
        qry_handler->setCallbackThread(init_thread_);
        ret = request_dispatcher_->sendRequest(qry_handler);
        if (0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(0 != ret);
}

void Es9DataManager::onQryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp) {
    APPLOG_INFO("onQryAccountFeeFinished errorCode={}, fees.size()={}"
            , account_fee_resp->getErrorCode(), account_fee_resp->fees.size());

    if (account_fee_resp->getErrorCode() != 0) {
        sendQryAccountFee();
        return ;
    }

    std::vector<std::shared_ptr<IEs9DataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onEs9QryAccountFeeFinished(account_fee_resp);
    }

    sendQryAllOrders();
}

void Es9DataManager::sendQryAllOrders() {
    int ret = 0;
    do {
        std::shared_ptr<Es9QryAllOrdersHandler> qry_handler(
                new Es9QryAllOrdersHandler(
                        Es9QryAllOrdersHandler::CallbackFunction(
                                    std::bind(&Es9DataManager::onQryAllOrdersFinished, this, std::placeholders::_1))));
        qry_handler->setCallbackThread(init_thread_);
        ret = request_dispatcher_->sendRequest(qry_handler);
        if (0 != ret) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(0 != ret);
}

void Es9DataManager::onQryAllOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp) {
    APPLOG_INFO("onQryAllOrdersFinished errorCode={}, orders.size()={}"
            , all_orders_resp->getErrorCode(), all_orders_resp->order_infos.size());

    if (all_orders_resp->getErrorCode() != 0) {
        sendQryAllOrders();
        return ;
    }

    std::vector<std::shared_ptr<IEs9DataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onEs9QryOrdersFinished(all_orders_resp);
    }

    notifyInitFinished();
}

void Es9DataManager::notifyInitFinished() {
    std::vector<std::shared_ptr<IEs9DataManagerListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onEs9DataManagerInitFinished();
    }
}


