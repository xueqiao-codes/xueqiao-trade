/*
 * es9_order_insert_processor.h
 *
 *  Created on: 2018年4月25日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_INSERT_PROCESSOR_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_INSERT_PROCESSOR_H_

#include <condition_variable>
#include <mutex>
#include "base/lru_cache.h"
#include "es9_request_dispatcher.h"
#include "es9_order_insert_handler.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9OrderInsertProcessor {
public:
    struct OrderInsertProcessInfo {
        bool waiting = false;  // 是否在等待处理
        int64_t sending_timestampms = 0; // 发送至上手API的时间
    };

    Es9OrderInsertProcessor(const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher);
    virtual ~Es9OrderInsertProcessor();

    std::shared_ptr<OrderInsertProcessInfo> getInsertProcessInfo(int64_t exec_order_id);
    void addInsertOrder(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order);

    virtual std::string chooseAccountNo(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order) = 0;
    virtual void handleOrderInsertResp(const std::shared_ptr<Es9OrderInsertResp>& resp) = 0;

private:
    void onWorking();
    void sendOrderInsert(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& delete_order);

    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;

    std::mutex lock_;
    std::queue<std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>> waiting_orders_;
    std::map<int64_t, std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>> waiting_orders_mapping_;
    soldier::base::LruCache<int64_t, int64_t> sending_timestampms_;

    std::condition_variable cv_;

    std::thread working_thread_;
    volatile bool stop_ = {false};
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_INSERT_PROCESSOR_H_ */
