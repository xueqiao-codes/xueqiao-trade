/*
 * quot_dispatcher_entry.cpp
 *
 *  Created on: 2018年4月25日
 *      Author: 44385
 */

#include "quot_dispatcher_entry.h"

#include "attr/attr_reporter_factory.h"
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "quotation/subscribe/api/subscribe_system.h"

using namespace apache::thrift;
using namespace platform::comm;
using namespace soldier::base;
using namespace xueqiao::quotation;
using namespace xueqiao::trade::hosting::quot::dispatcher;

QuotDispatcherEntry::QuotDispatcherEntry()
    : subscribed_topics_(new std::set<Topic>())
      , analysis_thread_(new TaskThread())
      , transfer_thread_(new TaskThread()) {
}

QuotDispatcherEntry::~QuotDispatcherEntry() {
    stopped_ = true;
    if (check_timer_thread_) {
        check_timer_thread_->join();
        check_timer_thread_.reset();
    }
}

bool QuotDispatcherEntry::init() {
    APPLOG_INFO("QuotDispatcherEntry init...");

    context_.reset(new zmq::context_t());
    transfer_socket_.reset(new zmq::socket_t(*context_, zmq::socket_type::pub));
    transfer_socket_->setsockopt(ZMQ_SNDHWM, 100);
    transfer_socket_->bind("tcp://127.0.0.1:1800");

    check_timer_thread_.reset(new std::thread(&QuotDispatcherEntry::onTimerWorking, this));

    soldier::attr::AttrReporterFactory::Global().minute()
    	                .keep(soldier::attr::AttrReporterFactory::Global().minute().requireKey(
    	                        "xueqiao.trade.hosting.process.keepalive",
    	                        {{"processname", "trade_hosting_quot_dispatcher"}}), 1);

    return true;
}

void QuotDispatcherEntry::syncTopics(const PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest) {
    APPLOG_INFO("syncTopics syncRequest={}", ThriftDebugString(syncRequest));

    if (!syncRequest.__isset.consumerKey || syncRequest.consumerKey.empty()) {
        return ;
    }

    analysis_thread_->postTask(&QuotDispatcherEntry::handleSyncTopics
            , this, std::shared_ptr<SyncTopicsRequest>(new SyncTopicsRequest(syncRequest)));
}


void QuotDispatcherEntry::onReceivedItemData(const Topic& topic, uint8_t* data , size_t size) {
    APPLOG_DEBUG("onReceivedItemData topic={}, size={}", topic.description(), size);
    transfer_thread_->postTask(
            &QuotDispatcherEntry::handleTransferQuotationItemData
            , this
            , std::shared_ptr<QuotationItemData>(new QuotationItemData(topic, data, size)));
}

void QuotDispatcherEntry::handleTransferQuotationItemData(const std::shared_ptr<QuotationItemData>& item) {
    std::string topic_content = item->topic.description();
    zmq_send((void*)(*transfer_socket_), topic_content.c_str(), topic_content.length(), ZMQ_SNDMORE);
    zmq_send((void*)(*transfer_socket_), item->data, (size_t)item->size, 0);
}

void QuotDispatcherEntry::handleSyncTopics(const std::shared_ptr<SyncTopicsRequest>& sync_request) {
    std::shared_ptr<std::set<Topic>> sync_topics(new std::set<Topic>());
    for (const auto& quot_topic : sync_request->quotTopics) {
        if (!quot_topic.platform.empty() && !quot_topic.contractSymbol.empty()) {
            sync_topics->insert(Topic(quot_topic.platform, quot_topic.contractSymbol));
        }
    }

    std::shared_ptr<ConsumerNode> consumer_node;
    auto consumer_node_it = consumer_nodes_.find(sync_request->consumerKey);
    if (consumer_node_it != consumer_nodes_.end()) {
        consumer_node = consumer_node_it->second;
        CHECK(consumer_node);
        CHECK(consumer_node->consumer_topics);
        if (*sync_topics == *(consumer_node->consumer_topics)) {
            consumer_node->last_sync_timestamp = NowInSeconds();
            return ;
        }
    } else {
        consumer_node = std::shared_ptr<ConsumerNode>(new ConsumerNode());
        consumer_node->consumer_key = sync_request->consumerKey;
        consumer_nodes_[consumer_node->consumer_key] = consumer_node;
    }

    consumer_node->consumer_topics = sync_topics;
    consumer_node->last_sync_timestamp = NowInSeconds();

    analysisSubscribeTopics(nullptr);
}

void QuotDispatcherEntry::analysisSubscribeTopics(SyncCall* sync_call) {
    std::unique_ptr<AutoSyncCallNotify> auto_sync_call;
    if (sync_call) {
        auto_sync_call.reset(new AutoSyncCallNotify(*sync_call));
    }

    std::chrono::time_point<std::chrono::high_resolution_clock> start_clock = std::chrono::high_resolution_clock::now();

    std::shared_ptr<std::set<Topic>> need_subscribe_topics(new std::set<Topic>());
    caculateSubscribeTopics(need_subscribe_topics);

    APPLOG_INFO("caculateSubscribeTopics finished, need_subscribe_topics size={}, subscribe_topics size={}"
            , need_subscribe_topics->size(), subscribed_topics_->size());
    for (const auto& need_subscribe_topic : (*need_subscribe_topics)) {
        auto it = subscribed_topics_->find(need_subscribe_topic);
        if (it == subscribed_topics_->end()) {
            APPLOG_INFO("addTopic {}", need_subscribe_topic.description());
            SubscribeSystem::Get()->addTopic(need_subscribe_topic);
        }
    }

    for (const auto& subscribed_topic : (*subscribed_topics_)) {
        auto it = need_subscribe_topics->find(subscribed_topic);
        if (it == need_subscribe_topics->end()) {
            APPLOG_INFO("removeTopic {}", subscribed_topic.description());
            SubscribeSystem::Get()->removeTopic(subscribed_topic);
        }
    }

    subscribed_topics_ = need_subscribe_topics;

    int64_t time_escaped_us = std::chrono::duration_cast<std::chrono::microseconds>(
            std::chrono::high_resolution_clock::now() - start_clock).count();
    APPLOG_INFO("analysisSubscribeTopics timeEscaped={}us", time_escaped_us);
}


void QuotDispatcherEntry::caculateSubscribeTopics(
        const std::shared_ptr<std::set<Topic>>& need_subscribe_topics) {
    int64_t now_timestamp = NowInSeconds();
    for (auto consumer_node_it = consumer_nodes_.begin()
            ; consumer_node_it != consumer_nodes_.end()
            ; ++consumer_node_it) {
        APPLOG_DEBUG("caculateSubscribeTopics consumer_node={}, now_timestamp={}, last_sync_timestamp={}, topics_size={}"
                , consumer_node_it->second->consumer_key
                , now_timestamp
                , consumer_node_it->second->last_sync_timestamp
                , consumer_node_it->second->consumer_topics->size());

        // 一分钟没有同步主题列表，则认为对应的consumer失效
        if (now_timestamp >= consumer_node_it->second->last_sync_timestamp + 15) {
            APPLOG_INFO("consumer {} is not effective", consumer_node_it->second->consumer_key);
            consumer_node_it->second->consumer_topics->clear();
            continue;
        }

        for (auto topic_it = consumer_node_it->second->consumer_topics->begin()
                ; topic_it != consumer_node_it->second->consumer_topics->end()
                ; ++topic_it) {
            need_subscribe_topics->insert(*topic_it);
        }
    }
}

void QuotDispatcherEntry::onTimerWorking() {
    while(!stopped_) {
        int sleep_seconds = 5;
        for (int count = 0; count < sleep_seconds *10; ++count) {
            if (stopped_) {
                return ;
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
        }

        APPLOG_INFO("Trigger analysisSubscribeTopics...");
        SyncCall sync_call;
        analysis_thread_->postTask(&QuotDispatcherEntry::analysisSubscribeTopics, this, &sync_call);
        sync_call.wait();
    }
}
