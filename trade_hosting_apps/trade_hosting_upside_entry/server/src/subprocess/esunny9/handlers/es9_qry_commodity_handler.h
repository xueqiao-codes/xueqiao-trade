/*
 * es9_qry_commodity_handler.h
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_COMMODITY_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_COMMODITY_HANDLER_H_

#include "es9_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct Es9QryCommodityResp : public es9ext::framework::Es9ResponseBase {
    std::vector<ITapTrade::TapAPICommodityInfo> commodities;
};


class Es9QryCommodityHandler : public es9ext::framework::Es9RequestWithCallback<Es9QryCommodityResp> {
public:
    Es9QryCommodityHandler(CallbackFunction callback);
    virtual ~Es9QryCommodityHandler() = default;

    virtual bool hasActureRsp() {
        return true;
    }

    virtual int timeoutMs() {
        return 30000;
    }

    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id);
    virtual void OnRspQryCommodity(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPICommodityInfo *info);

    virtual std::string description() {
        return "Es9QryCommodityHandler";
    }

    virtual const std::shared_ptr<Es9QryCommodityResp>& getResponse() {
        return commodity_resp_;
    }

private:
    std::shared_ptr<Es9QryCommodityResp> commodity_resp_;

};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_ESUNNY9_HANDLERS_ES9_QRY_COMMODITY_HANDLER_H_ */
