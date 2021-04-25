/*
 * es9_qry_fund_handler.h
 *
 *  Created on: 2018年8月11日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_FUND_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_FUND_HANDLER_H_


#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9QryFundResp : public es9ext::framework::Es9ResponseBase {
public:
    std::vector<ITapTrade::TapAPIFundData> funds;
};

class Es9QryFundHandler: public es9ext::framework::Es9RequestWithCallback<Es9QryFundResp> {
public:
    Es9QryFundHandler(CallbackFunction callback, const std::string& account_no);
    virtual ~Es9QryFundHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryFund(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIFundData *info);

    virtual const std::shared_ptr<Es9QryFundResp>& getResponse() {
        return qry_fund_resp_;
    }

    virtual std::string description() {
        return "Es9QryFundHandler";
    }

private:
    std::string account_no_;

    std::shared_ptr<Es9QryFundResp> qry_fund_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_FUND_HANDLER_H_ */
