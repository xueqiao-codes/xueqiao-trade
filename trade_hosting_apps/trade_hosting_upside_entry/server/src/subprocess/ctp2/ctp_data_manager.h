/*
 * data_manager.h
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_DATA_MANAGER_H_
#define SRC_SUBPROCESS_CTP_CTP_DATA_MANAGER_H_

#include <atomic>
#include "base/listener_container.h"
#include "ctp_login_manager.h"
#include "ctp_instruments_holder.h"
#include "order_mapping_manager.h"
#include "contract_mapping.h"
#include "qry_instruments_handler.h"
#include "qry_all_orders_handler.h"
#include "qry_all_trades_handler.h"
#include "qry_position_handler.h"
#include "qry_settlement_info_confirm_handler.h"
#include "req_settlement_info_confirm_handler.h"
#include "qry_position_detail_handler.h"
#include "qry_position_comb_detail_handler.h"
#include "qry_exchange_margin_rate_handler.h"
#include "qry_instrument_commission_rate_handler.h"
#include "qry_instrument_margin_rate_handler.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class IDataManagerListener {
public:
    virtual ~IDataManagerListener() = default;

    virtual void onDataManagerInitStart() {}

    virtual void onQryAllInstrumentsFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp) {}
    virtual void onQryExchangeMarginRatesFinished(
            const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp){}
    virtual void onQryInstrumentMarginRatesFinished(
            const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp){}
    virtual void onQryInstrumentCommissionRatesFinished(
            const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp){}

    virtual void onQryPositionsFinished(const std::shared_ptr<QryPositionResp>& positions_rsp) {}
    virtual void onQryPositionDetailsFinished(const std::shared_ptr<QryPositionDetailResp>& position_details_rsp) {}
    virtual void onQryPositionCombDetailsFinished(
            const std::shared_ptr<QryPositionCombDetailResp>& position_comb_details_rsp) {}
    virtual void onQryOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp) {}
    virtual void onQryTradesFinished(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp) {}

    virtual void onDataManagerInitFinished() {}

protected:
    IDataManagerListener() = default;
};


class CTPDataManager : public ILoginListener, public soldier::base::ListenerContainer<IDataManagerListener> {
public:
    CTPDataManager(
            const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~CTPDataManager() = default;

    inline CtpInstrumentsHolder& getInstrumentsHolder() {
        return ctp_instruments_holder_;
    }

    inline OrderMappingManager& getOrderMappingManager() {
        return order_mapping_manager_;
    }

    inline ::xueqiao::trade::hosting::storage::api::ContractMapping& getContractMapping() {
        return contract_mapping_;
    }

    virtual void onLoginSuccess(const std::shared_ptr<CThostFtdcRspUserLoginField>& login_rsp);

    bool sliceCombinationInstrument(const std::string& instrument_id
                , std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields);

private:
    // 查询结算信息
    void sendQrySettlementInfoConfirm();
    void onQrySettlementInfoConfirmFinished(const std::shared_ptr<QrySettlementInfoConfirmResp>& confirm_rsp);

    // 发送结算确认
    void sendReqSettlementInfoConfirm();
    void onReqSettlementInfoConfirmFinished(const std::shared_ptr<ReqSettlementInfoConfirmResp>& confirm_rsp);

    // 查询合约信息
    void sendQryAllInstruments();
    void onQryAllInstrumentsFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp);

    // 查询所有交易所保证金设置
    void sendQryAllExchangeMarginRates();
    void onQryAllExchangeMarginRatesFinished(const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp);

    // 查询所有合约保证金率设置
    void sendQryAllInstrumentMarginRates();
    void onQryAllInstrumentMarginRatesFinished(const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp);

    // 查询所有合约手续费设置
    void sendQryAllInstrumentCommissionRates();
    void onQryAllInstrumentCommissionRatesFinished(
            const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp);

    // 查询持仓信息
    void sendQryAllPositions();
    void onQryPositionsFinished(const std::shared_ptr<QryPositionResp>& positions_rsp);

    // 查询持仓明细
    void sendQryAllPositionDetails();
    void onQryAllPositionDetailsFinished(const std::shared_ptr<QryPositionDetailResp>& all_position_details_rsp);

    // 查询组合持仓明细
    void sendQryAllPositionCombDetails();
    void onQryAllPositionCombDetailsFinished(
            const std::shared_ptr<QryPositionCombDetailResp>& all_position_comb_details_rsp);

    // 查询所有订单信息
    void sendQryAllOrders();
    void onQryAllOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp);

    // 查询成交列表信息
    void sendQryAllTrades();
    void onQryAllTradesFinished(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp);

    void notifyInitFinished();

    std::shared_ptr<ctpext::framework::CTPRequestDispatcher> dispatcher_;
    std::shared_ptr<soldier::base::TaskThread> init_thread_;
    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp_;

    CtpInstrumentsHolder ctp_instruments_holder_;
    OrderMappingManager order_mapping_manager_;
    ::xueqiao::trade::hosting::storage::api::ContractMapping contract_mapping_;
};




} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_CTP_CTP_DATA_MANAGER_H_ */
