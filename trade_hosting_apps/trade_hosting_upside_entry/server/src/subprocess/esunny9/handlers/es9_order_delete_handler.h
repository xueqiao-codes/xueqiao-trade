/*
 * es9_order_delete_handler.h
 *
 *  Created on: 2018年4月17日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_DELETE_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_DELETE_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


struct Es9OrderDeleteResp : public es9ext::framework::Es9ResponseBase {
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> delete_order;
    std::shared_ptr<ITapTrade::TapAPIOrderInfo> order_info;
};

class Es9OrderDeleteHandler : public es9ext::framework::Es9RequestWithCallback<Es9OrderDeleteResp> {
public:
    Es9OrderDeleteHandler(CallbackFunction callback
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& delete_order);
    virtual ~Es9OrderDeleteHandler() = default;

    virtual bool hasActureRsp() {
        return false;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual bool hasSessionID() {
        return false;
    }

    virtual const std::shared_ptr<Es9OrderDeleteResp>& getResponse() {
        return order_delete_resp_;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspOrderAction(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , const ITapTrade::TapAPIOrderActionRsp *info);

    virtual std::string description() {
        return "Es9OrderDeleteHandler";
    }

private:
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> delete_order_;
    std::shared_ptr<Es9OrderDeleteResp> order_delete_resp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_DELETE_HANDLER_H_ */
