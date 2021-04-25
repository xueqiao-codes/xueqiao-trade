/*
 * ctp_authenticate_handler.h
 *
 *  Created on: 2019年5月15日
 *      Author: jason
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_CTP_AUTHENTICATE_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_CTP_AUTHENTICATE_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPAuthenticateResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<CThostFtdcRspAuthenticateField> authenticate_rsp_field;
};

class CTPAuthenticateHandler : public ctpext::framework::CTPRequestWithCallback<CTPAuthenticateResp> {
public:
    CTPAuthenticateHandler(
            CallbackFunction callback
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account
            , const std::string& ctp_broker_id_info
			, const std::string& ctp_app_id_info
			, const std::string& ctp_auth_code_info);

    virtual ~CTPAuthenticateHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<CTPAuthenticateResp>& getResponse() {
        return ctp_authenticate_rsp_;
    }

    virtual void OnRspAuthenticate(CThostFtdcRspAuthenticateField  *pRspAuthenticateField
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);
    virtual int onStart(CThostFtdcTraderApi* trader_api);

    virtual std::string description() {
        return "CTPAuthenticateHandler";
    }

private:
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;
    std::string ctp_broker_id_info_;
    std::string ctp_app_id_info_;
    std::string ctp_auth_code_info_;

    std::shared_ptr<CTPAuthenticateResp> ctp_authenticate_rsp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_CTP_AUTHENTICATE_HANDLER_H_ */
