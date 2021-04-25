/*
 * es9_order_sync_trades_handler.h
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_TRADES_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_TRADES_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9OrderSyncTradesResp : public es9ext::framework::Es9ResponseBase {
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order;
    std::vector<ITapTrade::TapAPIFillInfo> fill_infos;
};

class Es9OrderSyncTradesHandler : public es9ext::framework::Es9RequestWithCallback<Es9OrderSyncTradesResp> {
public:
    Es9OrderSyncTradesHandler(CallbackFunction callback
            , const std::string& account_no
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& sync_order);
    virtual ~Es9OrderSyncTradesHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryFill(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIFillInfo *info);

    virtual std::string description() {
        return "Es9OrderSyncTradesHandler";
    }

    virtual const std::shared_ptr<Es9OrderSyncTradesResp>& getResponse() {
        return order_sync_trades_resp_;
    }

private:
    std::string account_no_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> sync_order_;

    std::shared_ptr<Es9OrderSyncTradesResp> order_sync_trades_resp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_SYNC_TRADES_HANDLER_H_ */
