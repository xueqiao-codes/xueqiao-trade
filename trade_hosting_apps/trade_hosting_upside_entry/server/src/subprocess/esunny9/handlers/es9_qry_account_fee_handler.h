/*
 * es9_qry_account_fee_handler.h
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_FEE_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_FEE_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9QryAccountFeeResp : public es9ext::framework::Es9ResponseBase {
    std::vector<ITapTrade::TapAPIAccountFeeRentQryRsp> fees;
};

/**
 *  查询手续费参数
 */
class Es9QryAccountFeeHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryAccountFeeResp> {
public:
    Es9QryAccountFeeHandler(CallbackFunction callback
            , const std::string& account_no);
    virtual ~Es9QryAccountFeeHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 30000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryAccountFeeRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountFeeRentQryRsp *info);

    virtual std::string description() {
        return "Es9QryAccountFeeHandler";
    }

    virtual const std::shared_ptr<Es9QryAccountFeeResp>& getResponse() {
        return account_fee_resp_;
    }

private:
    std::string account_no_;
    std::shared_ptr<Es9QryAccountFeeResp> account_fee_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_ACCOUNT_FEE_HANDLER_H_ */
