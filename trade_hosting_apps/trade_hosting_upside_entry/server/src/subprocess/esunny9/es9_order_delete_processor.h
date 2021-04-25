/*
 * es9_order_delete_processor.h
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_DELETE_PROCESSOR_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_DELETE_PROCESSOR_H_

#include <condition_variable>
#include <mutex>
#include "base/lru_cache.h"
#include "es9_request_dispatcher.h"
#include "es9_order_delete_handler.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9OrderDeleteProcessor {
public:
    Es9OrderDeleteProcessor(const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher);
    virtual ~Es9OrderDeleteProcessor();

    void addDeleteOrder(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& delete_order);
    virtual void handleOrderDeleteResp(const std::shared_ptr<Es9OrderDeleteResp>& resp) = 0;

private:
    void onWorking();
    void sendOrderDelete(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& delete_order);

    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;

    std::mutex lock_;
    std::queue<std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>> waiting_orders_;
    std::map<int64_t, std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>> waiting_orders_mapping_;

    std::condition_variable cv_;

    std::thread working_thread_;
    volatile bool stop_ = {false};
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_DELETE_PROCESSOR_H_ */
