/*
 * es9_qry_bill_handler.h
 *
 *  Created on: 2018年8月13日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_BILL_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_BILL_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9QryBillResp : public es9ext::framework::Es9ResponseBase{
public:
    std::string bill_content;
};

class Es9QryBillHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryBillResp> {
public:
    Es9QryBillHandler(CallbackFunction callback
            , const std::string& user_no
            , const std::string& bill_date);
    virtual ~Es9QryBillHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 3000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void TAP_CDECL OnRspQryBill(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIBillQryRsp *info);

    virtual const std::shared_ptr<Es9QryBillResp>& getResponse() {
        return qry_bill_resp_;
    }

    virtual std::string description() {
        return "Es9QryFundHandler";
    }

private:
    std::string user_no_;
    std::string bill_date_;

    std::shared_ptr<Es9QryBillResp> qry_bill_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_BILL_HANDLER_H_ */
