/*
 * qry_trading_account_handler.h
 *
 *  Created on: 2018年8月10日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_TRADING_ACCOUNT_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_TRADING_ACCOUNT_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class QryTradingAccountResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcTradingAccountField> funds;
};

class QryTradingAccountHandler : public ctpext::framework::CTPRequestWithCallback<QryTradingAccountResp> {
public:
    QryTradingAccountHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info);
    virtual ~QryTradingAccountHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 3000; }

    virtual const std::shared_ptr<QryTradingAccountResp>& getResponse() {
        return qry_account_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryTradingAccount(CThostFtdcTradingAccountField *pTradingAccount
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);


    virtual std::string description() {
        return "QryTradingAccountHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<QryTradingAccountResp> qry_account_rsp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_TRADING_ACCOUNT_HANDLER_H_ */
