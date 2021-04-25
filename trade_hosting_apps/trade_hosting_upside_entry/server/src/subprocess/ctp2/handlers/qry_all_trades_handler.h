/*
 * qry_all_trades_handler.h
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_TRADES_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_TRADES_HANDLER_H_



#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryAllTradesResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcTradeField> trades;
};

class QryAllTradesHandler : public ctpext::framework::CTPRequestWithCallback<QryAllTradesResp> {
public:
    QryAllTradesHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info);
    virtual ~QryAllTradesHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 60000; }

    virtual const std::shared_ptr<QryAllTradesResp>& getResponse() {
        return all_trades_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryTrade(
            CThostFtdcTradeField *pTrade
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryAllTradesHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<QryAllTradesResp> all_trades_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_ALL_TRADES_HANDLER_H_ */
