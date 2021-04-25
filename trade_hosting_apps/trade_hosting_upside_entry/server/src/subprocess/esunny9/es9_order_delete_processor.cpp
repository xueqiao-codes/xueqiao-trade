/*
 * es9_order_delete_processor.cpp
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */
#include "es9_order_delete_processor.h"

#include "base/code_defense.h"
#include "iTapAPIError.h"

using namespace es9ext::framework;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


Es9OrderDeleteProcessor::Es9OrderDeleteProcessor(
        const std::shared_ptr<Es9RequestDispatcher>& request_dispatcher)
    : request_dispatcher_(request_dispatcher)
      , working_thread_(&Es9OrderDeleteProcessor::onWorking, this){
}

Es9OrderDeleteProcessor::~Es9OrderDeleteProcessor() {
    stop_ = true;
    working_thread_.join();
}

void Es9OrderDeleteProcessor::addDeleteOrder(const std::shared_ptr<HostingExecOrder>& delete_order) {
    CHECK(delete_order);
    {
        std::unique_lock<std::mutex> auto_lock(lock_);
        if(stop_) {
            throw std::runtime_error("processor already stopped");
        }

        auto it = waiting_orders_mapping_.find(delete_order->execOrderId);
        if (it != waiting_orders_mapping_.end()) {
            APPLOG_INFO("delete order for exec_order_id is already in queue");
            return ;
        }
        waiting_orders_.emplace(delete_order);
        waiting_orders_mapping_[delete_order->execOrderId] = delete_order;
    }

    cv_.notify_one();
}

void Es9OrderDeleteProcessor::onWorking() {
    while(!stop_) {
        std::shared_ptr<HostingExecOrder> delete_order;
        {
            std::unique_lock<std::mutex> auto_lock(lock_);
            cv_.wait(auto_lock,
                    [this]{ return stop_ || !this->waiting_orders_.empty(); });
            if (stop_) {
                return ;
            }
            delete_order = waiting_orders_.front();
            waiting_orders_.pop();
            waiting_orders_mapping_.erase(delete_order->execOrderId);
        }

        CHECK(delete_order);
        APPLOG_INFO("sendOrderDelete exec_order_id={}", delete_order->execOrderId);
        sendOrderDelete(delete_order);
    }
}

void Es9OrderDeleteProcessor::sendOrderDelete(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& delete_order) {
    while(true) {
        std::shared_ptr<Es9OrderDeleteHandler> order_delete_handler(
                new Es9OrderDeleteHandler(
                    Es9OrderDeleteHandler::CallbackFunction(std::bind(&Es9OrderDeleteProcessor::handleOrderDeleteResp, this, std::placeholders::_1))
                    , delete_order
                ));
        int ret = request_dispatcher_->sendRequest(order_delete_handler);
        if (ret == ITapTrade::TAPIERROR_ORDER_FREQUENCY) {
            std::this_thread::sleep_for(std::chrono::milliseconds(10));
            continue;
        }
        if (ret != 0) {
            std::shared_ptr<Es9OrderDeleteResp> delete_resp(new Es9OrderDeleteResp());
            delete_resp->delete_order = delete_order;
            delete_resp->setErrorCode(ret);
            handleOrderDeleteResp(delete_resp);
        }
        return ;
    }
}
