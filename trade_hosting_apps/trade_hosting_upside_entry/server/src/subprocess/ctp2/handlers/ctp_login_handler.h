/*
 * ctp_login_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_CTP_LOGIN_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_CTP_LOGIN_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPLoginResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp_field;
};

class CTPLoginHandler : public ctpext::framework::CTPRequestWithCallback<CTPLoginResp> {
public:
    CTPLoginHandler(
            CallbackFunction callback
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account
            , const std::string& ctp_broker_id_info);

    virtual ~CTPLoginHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<CTPLoginResp>& getResponse() {
        return ctp_login_rsp_;
    }

    virtual void OnRspUserLogin(CThostFtdcRspUserLoginField *pRspUserLogin
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);
    virtual int onStart(CThostFtdcTraderApi* trader_api);

    virtual std::string description() {
        return "CTPLoginHandler";
    }

private:
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;
    std::string ctp_broker_id_info_;

    std::shared_ptr<CTPLoginResp> ctp_login_rsp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_CTP_LOGIN_HANDLER_H_ */
