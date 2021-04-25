/*
 * qry_settlement_info_handler.h
 *
 *  Created on: 2018年8月10日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_HANDLER_H_

#include "ctp_request_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class QrySettlementInfoResp : public ctpext::framework::CTPResponseBase {
public:
    std::vector<CThostFtdcSettlementInfoField> settlement_infos;
};

class QrySettlementInfoHandler : public ctpext::framework::CTPRequestWithCallback<QrySettlementInfoResp> {
public:
    QrySettlementInfoHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::string& trading_day);

    virtual ~QrySettlementInfoHandler() = default;

    virtual bool hasActureRsp() { return true; }
    virtual int timeoutMs() { return 3000; }

    virtual const std::shared_ptr<QrySettlementInfoResp>& getResponse() {
        return qry_settlement_info_resp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField *pSettlementInfo
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "QrySettlementInfoHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::string trading_day_;
    std::shared_ptr<QrySettlementInfoResp> qry_settlement_info_resp_;
};




} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_HANDLERS_QRY_SETTLEMENT_INFO_HANDLER_H_ */
