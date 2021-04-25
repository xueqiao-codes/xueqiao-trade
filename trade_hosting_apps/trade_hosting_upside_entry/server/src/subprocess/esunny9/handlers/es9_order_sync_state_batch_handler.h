/*
 * es9_order_sync_state_batch_handler.h
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_BATCH_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_BATCH_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_upside_entry_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9OrderSyncStateBatchResp : public es9ext::framework::Es9ResponseBase {
    std::shared_ptr<TSyncOrderStateBatchReq> batch_req;
    std::vector<ITapTrade::TapAPIOrderInfo> order_infos;
};


class Es9OrderSyncStateBatchHandler : public es9ext::framework::Es9RequestWithCallback<Es9OrderSyncStateBatchResp> {
public:
    Es9OrderSyncStateBatchHandler(CallbackFunction callback
            , const std::shared_ptr<TSyncOrderStateBatchReq>& batch_req);
    virtual ~Es9OrderSyncStateBatchHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual const std::shared_ptr<Es9OrderSyncStateBatchResp>& getResponse() {
        return sync_state_batch_resp_;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryOrder(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIOrderInfo *info);

    virtual std::string description() {
        return "Es9OrderSyncStateBatchHandler";
    }

private:
    std::shared_ptr<TSyncOrderStateBatchReq> batch_req_;
    std::shared_ptr<Es9OrderSyncStateBatchResp> sync_state_batch_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_STATE_BATCH_HANDLER_H_ */
