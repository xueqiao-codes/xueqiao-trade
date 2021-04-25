/*
 * es9_order_insert_processor.cpp
 *
 *  Created on: 2018年4月25日
 *      Author: 44385
 */

#include "es9_order_insert_processor.h"

#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "iTapAPIError.h"
#include "performance_utils.h"

using namespace es9ext::framework;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;


Es9OrderInsertProcessor::Es9OrderInsertProcessor(
        const std::shared_ptr<Es9RequestDispatcher>& request_dispatcher)
    : request_dispatcher_(request_dispatcher)
      , sending_timestampms_(100000)
      , working_thread_(&Es9OrderInsertProcessor::onWorking, this) {
}

Es9OrderInsertProcessor::~Es9OrderInsertProcessor() {
    stop_ = true;
    working_thread_.join();
}

std::shared_ptr<Es9OrderInsertProcessor::OrderInsertProcessInfo>
    Es9OrderInsertProcessor::getInsertProcessInfo(int64_t exec_order_id) {
    std::shared_ptr<OrderInsertProcessInfo> process_info(new OrderInsertProcessInfo());
    {
        std::unique_lock<std::mutex> auto_lock(lock_);
        auto it = waiting_orders_mapping_.find(exec_order_id);
        if (it != waiting_orders_mapping_.end()) {
            process_info->waiting = true;
            return process_info;
        }

        const int64_t* p_sending_timestampms = sending_timestampms_.get(exec_order_id);
        if (p_sending_timestampms) {
            process_info->sending_timestampms = *p_sending_timestampms;
        }
    }
    return process_info;
}

void Es9OrderInsertProcessor::addInsertOrder(const std::shared_ptr<HostingExecOrder>& insert_order) {
//    BLOCK_TIME_PRINT("Es9OrderInsertProcessor::addInsertOrder execOrderId="
//            + boost::lexical_cast<std::string>(insert_order->execOrderId));
    CHECK(insert_order);
    {
        std::unique_lock<std::mutex> auto_lock(lock_);
        if(stop_) {
            throw std::runtime_error("processor already stopped");
        }

        auto it = waiting_orders_mapping_.find(insert_order->execOrderId);
        if (it != waiting_orders_mapping_.end()) {
            APPLOG_INFO("delete order for exec_order_id is already in queue");
            return ;
        }
        waiting_orders_.emplace(insert_order);
        waiting_orders_mapping_[insert_order->execOrderId] = insert_order;
    }

    cv_.notify_one();
}

void Es9OrderInsertProcessor::onWorking() {
    while(!stop_) {
        std::shared_ptr<HostingExecOrder> insert_order;
        {
            std::unique_lock<std::mutex> auto_lock(lock_);
            cv_.wait(auto_lock,
                    [this]{ return stop_ || !this->waiting_orders_.empty(); });
            if (stop_) {
                return ;
            }
            insert_order = waiting_orders_.front();

            waiting_orders_.pop();
            waiting_orders_mapping_.erase(insert_order->execOrderId);
            sending_timestampms_.put(insert_order->execOrderId, NowInMilliSeconds());
        }

        CHECK(insert_order);
        APPLOG_INFO("sendOrderInsert exec_order_id={}", insert_order->execOrderId);
        sendOrderInsert(insert_order);
    }
}

void Es9OrderInsertProcessor::sendOrderInsert(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order) {
    while(true) {
        std::shared_ptr<Es9OrderInsertHandler> order_delete_handler(
                new Es9OrderInsertHandler(
                    Es9OrderInsertHandler::CallbackFunction(std::bind(&Es9OrderInsertProcessor::handleOrderInsertResp, this, std::placeholders::_1))
                    , chooseAccountNo(insert_order)
                    , insert_order
                ));
        int ret = request_dispatcher_->sendRequest(order_delete_handler);
        if (ret == ITapTrade::TAPIERROR_ORDER_FREQUENCY) {
            std::this_thread::sleep_for(std::chrono::milliseconds(10));
            continue;
        }

        if (ret != 0) {
            std::shared_ptr<Es9OrderInsertResp> insert_resp(new Es9OrderInsertResp());
            insert_resp->insert_order = insert_order;
            insert_resp->setErrorCode(ret);
            handleOrderInsertResp(insert_resp);
        }
        return ;
    }
}
