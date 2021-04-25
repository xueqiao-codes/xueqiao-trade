/*
 * qry_investor_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_INVESTOR_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_INVESTOR_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QryInverstorResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<CThostFtdcInvestorField> invertor_field;
};

class QryInvestorHandler : public ctpext::framework::CTPRequestWithCallback<QryInverstorResp> {
public:
    QryInvestorHandler(
            CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info);
    virtual ~QryInvestorHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 2000; }

    virtual const std::shared_ptr<QryInverstorResp>& getResponse() {
        return invertor_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQryInvestor(
            CThostFtdcInvestorField *pInvestor
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QryInvestorHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<QryInverstorResp> invertor_resp_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_INVESTOR_HANDLER_H_ */
