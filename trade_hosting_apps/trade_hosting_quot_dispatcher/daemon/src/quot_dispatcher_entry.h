/*
 * quot_dispatcher_entry.h
 *
 *  Created on: 2018年4月25日
 *      Author: 44385
 */

#ifndef SRC_QUOT_DISPATCHER_ENTRY_H_
#define SRC_QUOT_DISPATCHER_ENTRY_H_

#include <set>
#include "base/thread_pool.h"
#include "base/sync_call.h"
#include "quotation/subscribe/api/subscribe_callback.h"
#include "TradeHostingQuotDispatcher.h"
#include "zmq.hpp"


namespace xueqiao {
namespace trade {
namespace hosting {
namespace quot {
namespace dispatcher {


class QuotDispatcherEntry : public TradeHostingQuotDispatcherIf, public ::xueqiao::quotation::ISubscribeCallback {
public:
    QuotDispatcherEntry();
    virtual ~QuotDispatcherEntry();

    bool init();

    virtual void syncTopics(const ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest);
    virtual void onReceivedItemData(const ::xueqiao::quotation::Topic& topic, uint8_t* data , size_t size);

private:
    struct QuotationItemData {
        ::xueqiao::quotation::Topic topic;
        uint8_t* data = nullptr;
        size_t size = 0;

        QuotationItemData(
                const ::xueqiao::quotation::Topic& topic_p
                , const uint8_t* data_p
                , const size_t& size_p)
            : topic(topic_p)
              , size(size_p){
            if (data_p) {
                data = (uint8_t*)malloc(size_p + 1);
                memcpy(data, data_p, size_p);
            }
        }

        ~QuotationItemData() {
            if (data) {
                free(data);
                data = nullptr;
            }
        }
    };

    void handleSyncTopics(const std::shared_ptr<SyncTopicsRequest>& sync_request);
    void handleTransferQuotationItemData(const std::shared_ptr<QuotationItemData>& item);

    void analysisSubscribeTopics(soldier::base::SyncCall* sync_call);
    void caculateSubscribeTopics(
            const std::shared_ptr<std::set<::xueqiao::quotation::Topic>>& need_subscribe_topics);

    void onTimerWorking();

    struct ConsumerNode {
        std::string consumer_key;
        std::shared_ptr<std::set<::xueqiao::quotation::Topic>> consumer_topics;
        int64_t last_sync_timestamp = 0;
    };

    std::map<std::string, std::shared_ptr<ConsumerNode>> consumer_nodes_;
    std::shared_ptr<std::set<::xueqiao::quotation::Topic>> subscribed_topics_;
    std::unique_ptr<soldier::base::TaskThread> analysis_thread_;

    std::unique_ptr<zmq::context_t> context_;
    std::unique_ptr<zmq::socket_t> transfer_socket_;
    std::unique_ptr<soldier::base::TaskThread> transfer_thread_;

    std::atomic_bool stopped_ = {false};
    std::unique_ptr<std::thread> check_timer_thread_;
};


} // end namespace dispatcher
} // end namespace quot
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_QUOT_DISPATCHER_ENTRY_H_ */
