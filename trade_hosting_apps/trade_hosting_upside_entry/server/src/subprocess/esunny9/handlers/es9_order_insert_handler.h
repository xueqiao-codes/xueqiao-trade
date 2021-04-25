/*
 * es9_order_insert_handler.h
 *
 *  Created on: 2018年4月16日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_INSERT_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_INSERT_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

extern std::map<HostingExecOrderCondition::type, ITapTrade::TAPITriggerPriceTypeType> SUPPORTED_ESUNNY9_ORDER_CONDITION_PRICETYPES;

struct Es9OrderInsertResp : public es9ext::framework::Es9ResponseBase {
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> insert_order;
    ITapTrade::TAPISTR_50 client_order_no = {0};
    std::shared_ptr<ITapTrade::TapAPIOrderInfo> order_info;
};


class Es9OrderInsertHandler : public es9ext::framework::Es9RequestWithCallback<Es9OrderInsertResp> {
public:
    Es9OrderInsertHandler(CallbackFunction callback
            , const std::string& account_no
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order);

    virtual ~Es9OrderInsertHandler() = default;

    virtual bool hasActureRsp() {
        return false;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual const std::shared_ptr<Es9OrderInsertResp>& getResponse() {
        return order_insert_rsp_;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);

    virtual void OnRspOrderAction(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderActionRsp *info);

    virtual std::string description() {
        return "Es9OrderInsertHandler";
    }

private:
    std::string account_no_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder> insert_order_;

    std::shared_ptr<Es9OrderInsertResp> order_insert_rsp_;
};




} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_ORDER_INSERT_HANDLER_H_ */
