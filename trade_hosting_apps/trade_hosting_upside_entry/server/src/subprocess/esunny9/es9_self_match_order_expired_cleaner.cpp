/*
 * es9_self_match_order_expired_cleaner.cpp
 *
 *  Created on: 2018年4月24日
 *      Author: 44385
 */

#include "es9_self_match_order_expired_cleaner.h"

using namespace es9ext::framework;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


Es9SelfMatchOrderExpiredCleaner::Es9SelfMatchOrderExpiredCleaner(
        const std::shared_ptr<SelfMatchManagerBackend<ITapTrade::TapAPIOrderInfo>>& backend
        , const std::shared_ptr<SelfMatchManager<ITapTrade::TapAPIOrderInfo>>& manager
        , const std::shared_ptr<Es9RequestDispatcher>& request_dispatcher)
    : SelfMatchOrderExpiredCleaner(backend, manager)
      , request_dispatcher_(request_dispatcher) {
}

int Es9SelfMatchOrderExpiredCleaner::startQryAllOrders(
        const std::shared_ptr<soldier::base::TaskThread>& callback_thread) {
    std::shared_ptr<Es9QryAllOrdersHandler> qry_handler(
            new Es9QryAllOrdersHandler(
                    Es9QryAllOrdersHandler::CallbackFunction(
                            std::bind(&Es9SelfMatchOrderExpiredCleaner::onQryAllOrdersFinished, this, std::placeholders::_1))));
    qry_handler->setCallbackThread(callback_thread);
    return request_dispatcher_->sendRequest(qry_handler);
}

void Es9SelfMatchOrderExpiredCleaner::onQryAllOrdersFinished(
        const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp) {
    std::vector<std::shared_ptr<ITapTrade::TapAPIOrderInfo>> resp_order_infos;
    resp_order_infos.reserve(all_orders_resp->order_infos.size() + 1);

    for (auto it = all_orders_resp->order_infos.begin();
            it != all_orders_resp->order_infos.end(); ++it) {
        resp_order_infos.push_back(std::shared_ptr<ITapTrade::TapAPIOrderInfo>(new ITapTrade::TapAPIOrderInfo(*it)));
    }

    handleQryAllOrdersResp(all_orders_resp->getErrorCode()
            , all_orders_resp->getUtf8ErrorMsg()
            , resp_order_infos);
}
