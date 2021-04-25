/*
 * es9_self_match_order_expired_cleaner.h
 *
 *  Created on: 2018年4月24日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_ORDER_EXPIRED_CLEANER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_ORDER_EXPIRED_CLEANER_H_

#include "es9_request_dispatcher.h"
#include "es9_qry_all_orders_handler.h"
#include "self_match_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9SelfMatchOrderExpiredCleaner : public SelfMatchOrderExpiredCleaner<ITapTrade::TapAPIOrderInfo> {
public:
    Es9SelfMatchOrderExpiredCleaner(
            const std::shared_ptr<SelfMatchManagerBackend<ITapTrade::TapAPIOrderInfo>>& backend
            , const std::shared_ptr<SelfMatchManager<ITapTrade::TapAPIOrderInfo>>& manager
            , const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher);
    virtual ~Es9SelfMatchOrderExpiredCleaner() = default;

    virtual int startQryAllOrders(const std::shared_ptr<soldier::base::TaskThread>& callback_thread);
    void onQryAllOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp);

private:
    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_ORDER_EXPIRED_CLEANER_H_ */
