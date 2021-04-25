/*
 * es9_qry_account_handler.h
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9QryAccountResp : public es9ext::framework::Es9ResponseBase {
    std::vector<ITapTrade::TapAPIAccountInfo> account_infos;
};

class Es9QryAccountHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryAccountResp> {
public:
    Es9QryAccountHandler(CallbackFunction callback);
    virtual ~Es9QryAccountHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryAccount(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIUINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIAccountInfo *info);

    virtual const std::shared_ptr<Es9QryAccountResp>& getResponse() {
        return qry_account_resp_;
    }

    virtual std::string description() {
        return "Es9QryAccountHandler";
    }

private:
    std::shared_ptr<Es9QryAccountResp> qry_account_resp_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_HANDLER_H_ */
