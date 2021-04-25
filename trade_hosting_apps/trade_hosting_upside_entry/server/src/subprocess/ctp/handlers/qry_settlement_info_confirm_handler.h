/*
 * qry_settlement_info_confirm_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_CONFIRM_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_CONFIRM_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QrySettlementInfoConfirmResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<CThostFtdcSettlementInfoConfirmField> confirm_field;
};

class QrySettlementInfoConfirmHandler : public ctpext::framework::CTPRequestWithCallback<QrySettlementInfoConfirmResp> {
public:
    QrySettlementInfoConfirmHandler(
            CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info);

    virtual ~QrySettlementInfoConfirmHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 3000; }

    virtual const std::shared_ptr<QrySettlementInfoConfirmResp>& getResponse() {
        return qry_info_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQrySettlementInfoConfirm(
            CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QrySettlementInfoConfirmHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;

    std::shared_ptr<QrySettlementInfoConfirmResp> qry_info_rsp_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_CONFIRM_HANDLER_H_ */
