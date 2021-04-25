/*
 * thqd_receivor.cpp
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#include "thqd_receivor.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/string_util.h"
#include "base/time_helper.h"
#include "trade_hosting_quot_dispatcher_stub.h"
#include "TradeHostingQuotDispatcher_variables.h"
#include "zmq_addon.hpp"

using namespace soldier::base;
using namespace xueqiao::trade::hosting::quot::dispatcher::client;

static std::string ADD_TOPIC_CALL = "add_topic";
static std::string REMOVE_TOPIC_CALL = "remove_topic";
static std::string SYNC_TOPICS_CALL = "sync_topic";
static std::string END_CALL = "end";
static std::string REP_SUCCESS = "success";

THQDReceivor::THQDReceivor(const std::string& consumer_key)
    : consumer_key_(consumer_key) {
    call_socket_description_.append("inproc://#thqd_receivor_").append(consumer_key);
    context_.reset(new zmq::context_t(1));

    receive_thread_.reset(new std::thread(&THQDReceivor::onReceiveWorking, this));
    quotation_thread_.reset(new soldier::base::TaskThread());
    sync_topic_timer_thread_.reset(new std::thread(&THQDReceivor::onTimerWorking, this));
}

THQDReceivor::~THQDReceivor() {
    end();
    receive_thread_->join();
    sync_topic_timer_thread_->join();
}

void THQDReceivor::end() {
    ending_ = true;

    zmq::socket_t socket(*context_, zmq::socket_type::req);
    socket.connect(call_socket_description_);
    zmq::multipart_t req;
    req.addstr(END_CALL);
    req.send(socket);
}


void THQDReceivor::addTopic(const std::string& platform, const std::string& contract_symbol) {
    if (platform.empty() || contract_symbol.empty()) {
        return ;
    }

    zmq::socket_t socket(*context_, zmq::socket_type::req);
    socket.connect(call_socket_description_);

    zmq::multipart_t req;
    req.addstr(ADD_TOPIC_CALL);
    req.addstr(platform);
    req.addstr(contract_symbol);
    req.send(socket);

    zmq::message_t resp;
    socket.recv(&resp);
}

void THQDReceivor::removeTopic(const std::string& platform, const std::string& contract_symbol) {
    if (platform.empty() || contract_symbol.empty()) {
        return ;
    }

    zmq::socket_t socket(*context_, zmq::socket_type::req);
    socket.connect(call_socket_description_);

    zmq::multipart_t req;
    req.addstr(REMOVE_TOPIC_CALL);
    req.addstr(platform);
    req.addstr(contract_symbol);
    req.send(socket);

    zmq::message_t resp;
    socket.recv(&resp);
}

void THQDReceivor::onCall() {
    zmq::multipart_t msg;
    while(msg.recv(*call_socket_, ZMQ_DONTWAIT)) {
        std::string call_type = msg.popstr();
        APPLOG_INFO("onCall {}", call_type);

        if (call_type == ADD_TOPIC_CALL) {
            std::string platform = msg.popstr();
            std::string contract_symbol = msg.popstr();

            std::string topic_description = getTopicDescription(platform, contract_symbol);
            auto it = subscribe_topics_.find(topic_description);
            if (it != subscribe_topics_.end()) {
                sendCallResp();
                continue;
            }
            subscribe_topics_.insert(topic_description);
            if (receive_socket_) {
                receive_socket_->setsockopt(ZMQ_SUBSCRIBE, topic_description.c_str(), topic_description.length());
            }

            syncTopics();

            APPLOG_INFO("add topic {}", topic_description);
        } else if (call_type == REMOVE_TOPIC_CALL) {
            std::string platform = msg.popstr();
            std::string contract_symbol = msg.popstr();

            std::string topic_description = getTopicDescription(platform, contract_symbol);
            auto it = subscribe_topics_.find(topic_description);
            if (it == subscribe_topics_.end()) {
                sendCallResp();
                continue;
            }
            subscribe_topics_.erase(it);
            if (receive_socket_) {
                receive_socket_->setsockopt(ZMQ_UNSUBSCRIBE, topic_description.c_str(), topic_description.length());
            }

            syncTopics();
            APPLOG_INFO("remove topic {}", topic_description);
        } else if (call_type == SYNC_TOPICS_CALL) {
            syncTopics();
            APPLOG_INFO("syncTopics called");
        }

        sendCallResp();

        if (call_type == END_CALL) {
            APPLOG_INFO("end...");
            break;
        }
    }
}

void THQDReceivor::onProcess() {
    zmq::multipart_t msg;
    int received_count = 0;

    while(received_count < 100) {
        if (!msg.recv(*receive_socket_, ZMQ_DONTWAIT)) {
            break;
        }

        ++received_count;
        if (msg.size() != 2) {
            continue;
        }

        std::string topic_description = msg.popstr();
        zmq::message_t content_msg = msg.pop();

        std::string platform;
        std::string contract_symbol;
        if (!sliceTopicDescription(topic_description, platform, contract_symbol)) {
            APPLOG_ERROR("sliceTopicDescription topic_description={} failed", topic_description);
            continue;
        }

        quotation_thread_->postTask(&THQDReceivor::onHandleQuotationMsg, this
                , platform, contract_symbol
                , std::shared_ptr<zmq::message_t>(new zmq::message_t(std::move(content_msg))));
    }
}

void THQDReceivor::onHandleQuotationMsg(const std::string& platform
        , const std::string& contract_symbol
        , const std::shared_ptr<zmq::message_t>& msg) {
    std::vector<std::shared_ptr<ITHQDCallback>> listeners;
    getListeners(listeners);

    for (auto listener : listeners) {
        listener->onReceivedItemData(platform, contract_symbol, (uint8_t*)msg->data(), msg->size());
    }
}

void THQDReceivor::onReceiveWorking() noexcept {
    call_socket_.reset(new zmq::socket_t(*context_, zmq::socket_type::rep));
    call_socket_->bind(call_socket_description_);

    receive_socket_.reset(new zmq::socket_t(*context_, zmq::socket_type::sub));
    receive_socket_->setsockopt(ZMQ_HEARTBEAT_IVL, 15000);
    receive_socket_->setsockopt(ZMQ_HEARTBEAT_TIMEOUT, 3000);
    receive_socket_->setsockopt(ZMQ_HEARTBEAT_TTL, 60000);
    receive_socket_->setsockopt(ZMQ_RCVHWM, 100);
    receive_socket_->connect("tcp://127.0.0.1:1800");

    while(!ending_) {
        int ret = 0;

        zmq_pollitem_t items[2];
        items[0].socket = (void*)(*call_socket_);
        items[0].events = ZMQ_POLLIN;
        items[0].revents = 0;

        if (receive_socket_) {
            items[1].socket = (void*)(*receive_socket_);
            items[1].events = ZMQ_POLLIN;
            items[1].revents = 0;
            ret = zmq_poll(items, 2, -1);
        } else {
            items[1].socket = nullptr;
            items[1].events = 0;
            items[1].revents = 0;
            ret = zmq_poll(items, 1, -1);
        }

        if (ret <= 0) {
            if (ret < 0) {
                APPLOG_ERROR("poll sockets failed, err{} : {}", zmq_errno(), zmq_strerror(zmq_errno()));
            }
            continue;
        }

        if (items[0].revents > 0) {
            onCall();
            if (ending_) {
                break;
            }
        }
        if (items[1].socket != nullptr && items[1].revents > 0) {
            onProcess();
        }
    }

    receive_socket_.reset();
    call_socket_.reset();
}

void THQDReceivor::syncTopics() {
    SyncTopicsRequest request;
    request.__set_consumerKey(consumer_key_);
    request.__isset.quotTopics = true;
    for (const std::string& topic_description : subscribe_topics_) {
        SyncQuotTopic topic;
        if (!sliceTopicDescription(topic_description, topic.platform, topic.contractSymbol)) {
            continue;
        }
        topic.__isset.platform = true;
        topic.__isset.contractSymbol = true;
        request.quotTopics.push_back(topic);
    }

    TradeHostingQuotDispatcherStub stub;
    std::string socket_file;
    socket_file.append("/data/run/service_")
                .append(boost::lexical_cast<std::string>(TradeHostingQuotDispatcher_service_key))
                .append(".sock");
    stub.setSocketFile(socket_file);
    stub.setTimeoutMs(100);
    try {
        STUB_SYNC_INVOKE(stub, syncTopics, request);
    } catch (::platform::comm::ErrorInfo& e) {
        APPLOG_ERROR("syncTopics call failed, errorCode={}, errorMsg={}", e.errorCode, e.errorMsg);
    } catch (::apache::thrift::TException& e) {
        APPLOG_ERROR("syncTopics call failed, TException.msg={}", e.what());
    }
}

std::string THQDReceivor::getTopicDescription(const std::string& platform, const std::string& contract_symbol) {
    std::stringstream ss;
    ss << "/quotation/" << platform << "/" << contract_symbol << "/";
    return ss.str();
}

bool THQDReceivor::sliceTopicDescription(
        const std::string& topic_description
        , std::string& platform
        , std::string& contract_symbol) {
    std::vector<std::string> tokens;
    StringUtil::tokenize(topic_description, tokens, "/", false);
    if (tokens.size() >= 5 && tokens[1] == "quotation" && tokens[tokens.size() - 1].empty()) {
        platform = tokens[2];
        contract_symbol = tokens[3];
        for (size_t index = 4; index < tokens.size() - 1; ++index) {
            contract_symbol += "/";
            contract_symbol += tokens[index];
        }
    }
    if (platform.empty() || contract_symbol.empty()) {
        return false;
    }
    return true;
}

void THQDReceivor::onTimerWorking() {
    while(!ending_) {
        int sleep_seconds = 5;
        for (int count = 0; count < sleep_seconds *10; ++count) {
            if (ending_) {
                return ;
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
        }

        APPLOG_INFO("Trigger syncTopics start...");
        zmq::socket_t socket(*context_, zmq::socket_type::req);
        socket.connect(call_socket_description_);

        zmq::multipart_t req;
        req.addstr(SYNC_TOPICS_CALL);
        req.send(socket);

        zmq::message_t resp;
        socket.recv(&resp);
        APPLOG_INFO("Trigger syncTopics finished...");
    }
}

void THQDReceivor::sendCallResp() {
    zmq::message_t resp(REP_SUCCESS.c_str(), REP_SUCCESS.length());
    call_socket_->send(resp);
}



