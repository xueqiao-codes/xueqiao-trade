/*
 * es9_qry_position_handler.h
 *
 *  Created on: 2018年8月14日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_POSITION_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_POSITION_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9QryPositionResp : public es9ext::framework::Es9ResponseBase {
public:
    std::vector<ITapTrade::TapAPIPositionInfo> positions;
};

class Es9QryPositionHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryPositionResp> {
public:
    Es9QryPositionHandler(CallbackFunction callback
            , const std::string& account_no);
    virtual ~Es9QryPositionHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 10000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryPosition(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIPositionInfo *info);

    virtual const std::shared_ptr<Es9QryPositionResp>& getResponse() {
        return qry_position_resp_;
    }

    virtual std::string description() {
        return "Es9QryPositionHandler";
    }

private:
    std::string account_no_;

    std::shared_ptr<Es9QryPositionResp> qry_position_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_POSITION_HANDLER_H_ */
