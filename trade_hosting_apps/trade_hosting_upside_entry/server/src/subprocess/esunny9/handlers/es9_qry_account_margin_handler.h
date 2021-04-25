/*
 * es9_qry_account_margin_handler.h
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_MARGIN_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_MARGIN_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9QryAccountMarginResp : public es9ext::framework::Es9ResponseBase {
    std::vector<ITapTrade::TapAPIAccountMarginRentQryRsp> margins;
};

struct Es9QryAccountMarginHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryAccountMarginResp> {
public:
    Es9QryAccountMarginHandler(CallbackFunction callback
            , const std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryReq>& req);
    virtual ~Es9QryAccountMarginHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 30000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryAccountMarginRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountMarginRentQryRsp *info);

    virtual std::string description() {
        return "Es9QryAccountMarginHandler";
    }

    virtual const std::shared_ptr<Es9QryAccountMarginResp>& getResponse() {
        return account_margin_resp_;
    }

private:
    std::shared_ptr<ITapTrade::TapAPIAccountMarginRentQryReq> req_;
    std::shared_ptr<Es9QryAccountMarginResp> account_margin_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_MARGIN_HANDLER_H_ */
