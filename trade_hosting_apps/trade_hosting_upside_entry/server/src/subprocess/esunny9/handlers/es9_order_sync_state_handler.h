/*
 * es9_order_sync_state_handler.h
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


struct Es9OrderSyncStateResp : public es9ext::framework::Es9ResponseBase {
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order;
    std::vector<ITapTrade::TapAPIOrderInfo> order_infos;
    bool sync_order_has_dealinfo = { false };
};

class Es9OrderSyncStateHandler : public es9ext::framework::Es9RequestWithCallback<Es9OrderSyncStateResp> {
public:
    Es9OrderSyncStateHandler(CallbackFunction callback
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order);
    virtual ~Es9OrderSyncStateHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 15000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryOrder(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIOrderInfo *info);

    virtual std::string description() {
        return "Es9OrderSyncStateHandler";
    }

    virtual const std::shared_ptr<Es9OrderSyncStateResp>& getResponse() {
        return order_sync_state_resp_;
    }

private:
    std::string account_no_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order_;

    std::shared_ptr<Es9OrderSyncStateResp> order_sync_state_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_HANDLER_H_ */
