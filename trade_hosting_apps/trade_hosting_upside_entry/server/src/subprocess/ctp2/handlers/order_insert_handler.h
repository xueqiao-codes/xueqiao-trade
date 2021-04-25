/*
 * order_insert_handler.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_HANDLERS_ORDER_INSERT_HANDLER_H_
#define SRC_SUBPROCESS_CTP_HANDLERS_ORDER_INSERT_HANDLER_H_

#include "ctp_request_base.h"
#include "trade_hosting_basic_types.h"
#include "ctp_position_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

extern std::map<CTPCombOffsetFlagType::type, char> SUPPORTED_CTP_COMB_OFFSET;
extern std::map<CTPCombHedgeFlagType::type, char> SUPPORTED_CTP_COMB_HEDGE;
extern std::map<HostingExecOrderCondition::type, char> SUPPORTED_CTP_ORDER_CONDITIONS;

class OrderInsertResp : public ctpext::framework::CTPResponseBase {
public:
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> insert_order; // 原始请求
    std::shared_ptr<CThostFtdcInputOrderField> input_order_field;
};

class OrderInsertHandler : public ctpext::framework::CTPRequestWithCallback<OrderInsertResp> {
public:
    OrderInsertHandler(CallbackFunction callback
            , const std::shared_ptr<CThostFtdcRspUserLoginField>& login_info
            , const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , const std::shared_ptr<CTPPositionManager>& position_manager);
    virtual ~OrderInsertHandler() = default;

    virtual bool hasActureRsp() { return false; }
    virtual int timeoutMs() { return 5000; }

    virtual const std::shared_ptr<OrderInsertResp>& getResponse() {
        return order_insert_rsp_;
    }

    virtual int onStart(CThostFtdcTraderApi* trader_api);
    virtual void OnRspOrderInsert(
            CThostFtdcInputOrderField *pInputOrder
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual std::string description() {
        return "OrderInsertHandler";
    }

private:
    std::shared_ptr<CThostFtdcRspUserLoginField> login_info_;
    std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder> insert_order_;
    std::shared_ptr<CTPPositionManager> position_manager_;

    std::shared_ptr<OrderInsertResp> order_insert_rsp_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_HANDLERS_ORDER_INSERT_HANDLER_H_ */
