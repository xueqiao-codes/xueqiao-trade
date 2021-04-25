/*
 * es9_qry_all_orders_handler.h
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ALL_ORDERS_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ALL_ORDERS_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


struct Es9QryAllOrdersResp : public es9ext::framework::Es9ResponseBase {
    std::vector<ITapTrade::TapAPIOrderInfo> order_infos;
};


class Es9QryAllOrdersHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryAllOrdersResp> {
public:
    Es9QryAllOrdersHandler(CallbackFunction callback);
    virtual ~Es9QryAllOrdersHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 60000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryOrder(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIOrderInfo *info);

    virtual std::string description() {
        return "Es9QryAllOrdersHandler";
    }

    virtual const std::shared_ptr<Es9QryAllOrdersResp>& getResponse() {
        return all_orders_resp_;
    }

private:
    std::shared_ptr<Es9QryAllOrdersResp> all_orders_resp_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ALL_ORDERS_HANDLER_H_ */
