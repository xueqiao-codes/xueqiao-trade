/*
 * thqd_receivor.h
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#ifndef SRC_GEN_THQD_RECEIVOR_H_
#define SRC_GEN_THQD_RECEIVOR_H_

#include <atomic>
#include <chrono>
#include <set>
#include <thread>
#include "base/listener_container.h"
#include "base/thread_pool.h"
#include "zmq.hpp"
#include "thqd_callback.h"


namespace xueqiao {
namespace trade {
namespace hosting {
namespace quot {
namespace dispatcher {
namespace client {

class THQDReceivor : public soldier::base::ListenerContainer<ITHQDCallback> {
public:
    THQDReceivor(const std::string& consumer_key);
    virtual ~THQDReceivor();

    void addTopic(const std::string& platform, const std::string& contract_symbol);
    void removeTopic(const std::string& platform, const std::string& contract_symbol);

private:
    void end();

    void onReceiveWorking() noexcept;
    void onCall();
    void onProcess();
    void syncTopics();

    void onTimerWorking();
    void sendCallResp();
    void onHandleQuotationMsg(const std::string& platform, const std::string& contract_symbol
            , const std::shared_ptr<zmq::message_t>& msg);

    std::string getTopicDescription(const std::string& platform, const std::string& contract_symbol);
    bool sliceTopicDescription(const std::string& topic_description
            , std::string& platform
            , std::string& contract_symbol);

    std::string consumer_key_;

    std::string call_socket_description_;
    std::atomic_bool ending_ = { false };

    std::set<std::string> subscribe_topics_;

    std::unique_ptr<std::thread> receive_thread_;
    std::unique_ptr<zmq::context_t> context_;
    std::unique_ptr<zmq::socket_t> receive_socket_;
    std::unique_ptr<zmq::socket_t> call_socket_;
    std::unique_ptr<soldier::base::TaskThread> quotation_thread_;

    std::unique_ptr<std::thread> sync_topic_timer_thread_;
};


} // end namespace client
} // end namespace dispatcher
} // end namespace quot
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_GEN_THQD_RECEIVOR_H_ */
