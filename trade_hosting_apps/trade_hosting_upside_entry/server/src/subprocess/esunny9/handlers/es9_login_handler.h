/*
 * es9_login_handler.h
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_LOGIN_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_LOGIN_HANDLER_H_

#include "es9_request_base.h"
#include "trade_hosting_basic_types.h"


namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9LoginHandler : public es9ext::framework::Es9RequestBase {
public:
    Es9LoginHandler(const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~Es9LoginHandler() = default;

    virtual int timeoutMs() { return 3000; }
    virtual bool hasActureRsp() { return false; }
    virtual bool hasSessionID() { return false; }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api
                , ITapTrade::TAPIUINT32& session_id);

    virtual void onTimeOut() {
    }

    virtual void onLastFinished() {
    }

    virtual std::string description() {
        return "Es9LoginHandler";
    }

private:
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_LOGIN_HANDLER_H_ */
