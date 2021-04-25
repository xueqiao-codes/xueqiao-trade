/*
 * ctp_position_manager.cpp
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */
#include "ctp_position_manager.h"

#include <string.h>
#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "trade_hosting_basic_errors_types.h"

using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::upside::position;


CTPPositionManager::CTPPositionManager(const std::shared_ptr<CTPDataManager>& data_manager)
    : data_manager_(data_manager)
      , working_thread_(new TaskThread()){
}

CTPPositionManager::~CTPPositionManager() {
}


void CTPPositionManager::onDataManagerInitStart() {
    working_thread_->postTask(&CTPPositionManager::handleInitStart, this);
}

void CTPPositionManager::onQryPositionsFinished(const std::shared_ptr<QryPositionResp>& positions_rsp) {
    working_thread_->postTask(&CTPPositionManager::handleInitPositions, this, positions_rsp);
}

void CTPPositionManager::onQryOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp) {
    working_thread_->postTask(&CTPPositionManager::handleInitOrders, this, all_orders_rsp);
}

void CTPPositionManager::onQryTradesFinished(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp) {
    working_thread_->postTask(&CTPPositionManager::handleInitTrades, this, all_trades_rsp);
}

void CTPPositionManager::orderInsertPrepare(
        const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
        , TThostFtdcOffsetFlagType& out_offset_flag) throw(ErrorInfo) {
    SyncCall sync_call;
    ErrorInfo err;
    working_thread_->postTask(&CTPPositionManager::handleOrderInsertPrepare, this, &sync_call
            , insert_order, &out_offset_flag, & err);
    sync_call.wait();
    if (err.errorCode != 0) {
        throw err;
    }
}

void CTPPositionManager::orderInsertCanceled(
        const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_cancelled_order) {
    working_thread_->postTask(&CTPPositionManager::handleOrderInsertCanceled, this, insert_cancelled_order);
}

void CTPPositionManager::syncOrder(const CThostFtdcOrderField* p_order) {
    working_thread_->postTask(&CTPPositionManager::handleOrderNotify, this, *p_order);
}

void CTPPositionManager::syncTrade(const CThostFtdcTradeField* p_trade) {
    working_thread_->postTask(&CTPPositionManager::handleTradeNotify, this, *p_trade);
}

void CTPPositionManager::OnRtnOrder(CThostFtdcOrderField *pOrder) {
    if (!pOrder) {
        return ;
    }

    working_thread_->postTask(&CTPPositionManager::handleOrderNotify, this, *pOrder);
}

void CTPPositionManager::OnRtnTrade(CThostFtdcTradeField *pTrade) {
    if (!pTrade) {
        return ;
    }

    working_thread_->postTask(&CTPPositionManager::handleTradeNotify, this, *pTrade);
}

void CTPPositionManager::dumpPositionSummaries(
        std::vector<PositionSummary>& position_summaries) {
    SyncCall sync_call;
    working_thread_->postTask(&CTPPositionManager::handleDumpPositionSummaries, this, &sync_call, &position_summaries);
    sync_call.wait();
}

CTPPositionManager::CTPPositionChooser::CTPPositionChooser(
        CTPPositionManager& position_manager
        , HostingExecOrderTradeDirection::type order_trade_direction)
    : position_manager_(position_manager)
      , order_trade_direction_(order_trade_direction) {
}

CTPPositionDirection::type CTPPositionManager::CTPPositionChooser::getClosePosDirection(int leg_index) {
    CTPPositionDirection::type pos_direction = CTPPositionDirection::POSITION_LONG;
    if (leg_index == 0) {
        if (order_trade_direction_ == HostingExecOrderTradeDirection::ORDER_BUY) {
            pos_direction = CTPPositionDirection::POSITION_SHORT;
        }
    } else {
        if (order_trade_direction_ == HostingExecOrderTradeDirection::ORDER_SELL) {
            pos_direction = CTPPositionDirection::POSITION_SHORT;
        }
    }
    return pos_direction;
}


// 区分历史持仓， 优先昨仓选择器
class YDFirstPositionChooser : public CTPPositionManager::CTPPositionChooser {
public:
    YDFirstPositionChooser(CTPPositionManager& position_manager
                    , HostingExecOrderTradeDirection::type order_trade_direction)
        : CTPPositionChooser(position_manager, order_trade_direction) {
    }

    virtual void chooseOffsetFlag(
            const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field
            , const std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields
            , int32_t volume) {
        bool all_has_closed_yd_volume = true;
        for (int leg_index = 0; leg_index < (int)leg_instrument_fields.size(); ++leg_index) {
            if (!position_manager_.hasCloseYDPosition(
                    leg_instrument_fields[leg_index], getClosePosDirection(leg_index), volume)) {
                all_has_closed_yd_volume = false;
                break;
            }
        }

        if (all_has_closed_yd_volume) {
            choosed_offset_flag_ = THOST_FTDC_OF_CloseYesterday;
            return ;
        }

        bool all_has_closed_td_volume = true;
        for (int leg_index = 0; leg_index< (int)leg_instrument_fields.size(); ++leg_index) {
            if (!position_manager_.hasCloseTDPosition(
                    leg_instrument_fields[leg_index], getClosePosDirection(leg_index), volume)) {
                all_has_closed_td_volume = false;
                break;
            }
        }

        if (all_has_closed_td_volume) {
            choosed_offset_flag_ = THOST_FTDC_OF_CloseToday;
        }
    }
};

// 区分历史持仓，优先平今选择器
class TDFirstPositionChooser : public CTPPositionManager::CTPPositionChooser {
public:
    TDFirstPositionChooser(
            CTPPositionManager& position_manager
            , HostingExecOrderTradeDirection::type order_trade_direction)
        : CTPPositionChooser(position_manager, order_trade_direction) {
    }

    virtual void chooseOffsetFlag(
            const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field
            , const std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields
            , int32_t volume) {
        bool all_has_closed_td_volume = true;
        for (int leg_index = 0; leg_index< (int)leg_instrument_fields.size(); ++leg_index) {
            if (!position_manager_.hasCloseTDPosition(
                    leg_instrument_fields[leg_index], getClosePosDirection(leg_index), volume)) {
                all_has_closed_td_volume = false;
                break;
            }
        }

        if (all_has_closed_td_volume) {
            choosed_offset_flag_ = THOST_FTDC_OF_CloseToday;
            return ;
        }

        bool all_has_closed_yd_volume = true;
        for (int leg_index = 0; leg_index < (int)leg_instrument_fields.size(); ++leg_index) {
            if (!position_manager_.hasCloseYDPosition(
                    leg_instrument_fields[leg_index], getClosePosDirection(leg_index), volume)) {
                all_has_closed_yd_volume = false;
                break;
            }
        }

        if (all_has_closed_yd_volume) {
            choosed_offset_flag_ = THOST_FTDC_OF_CloseYesterday;
        }
    }
};


// 不区分历史仓选择器
class AllClosePositionChooser : public CTPPositionManager::CTPPositionChooser {
public:
    AllClosePositionChooser(CTPPositionManager& position_manager
            , HostingExecOrderTradeDirection::type order_trade_direction)
        : CTPPositionChooser(position_manager, order_trade_direction) {
    }

    virtual void chooseOffsetFlag(
            const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field
            , const std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields
            , int32_t volume) {
        bool all_has_closed_volume = true;
        for (int leg_index = 0; leg_index < (int)leg_instrument_fields.size(); ++leg_index) {
            if (!position_manager_.hasClosePosition(
                    leg_instrument_fields[leg_index], getClosePosDirection(leg_index), volume)) {
                all_has_closed_volume = false;
                break;
            }
        }

        if (all_has_closed_volume) {
            choosed_offset_flag_ = THOST_FTDC_OF_Close;
        }
    }
};


void CTPPositionManager::handleOrderInsertPrepare(
            SyncCall* sync_call
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , TThostFtdcOffsetFlagType* p_out_offset_flag
            , platform::comm::ErrorInfo* err) {
    APPLOG_INFO("handleOrderInsertPrepare execOrderId={}, orderTradeDirection={}, quantity={}"
            , insert_order->execOrderId, insert_order->orderDetail.tradeDirection
            , insert_order->orderDetail.quantity);
    soldier::base::AutoSyncCallNotify auto_sync_call_notify(*sync_call);
    if (state_ != INIT_FINISHED) {
        err->__set_errorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT);
        err->__set_errorMsg("trade account in init");
        return ;
    }

    std::string order_instrument_id =
            insert_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode;

    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
        = data_manager_->getInstrumentsHolder().getInstrument(order_instrument_id.c_str());
    if (!instrument_field) {
        err->__set_errorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND);
        err->__set_errorMsg("ctp instrument not found for " + order_instrument_id);
        APPLOG_ERROR("handleOrderInsertPrepare failed to found instrument field for InstrumentID={}", order_instrument_id);
        return ;
    }

    std::vector<std::shared_ptr<CThostFtdcInstrumentField>> leg_instrument_fields;
    if (instrument_field->ProductClass == THOST_FTDC_PC_Combination) {
        if (!data_manager_->sliceCombinationInstrument(order_instrument_id, leg_instrument_fields)) {
            err->__set_errorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED);
            err->__set_errorMsg("error slice combination instrument_id=" + order_instrument_id);
            return ;
        }
    } else {
        leg_instrument_fields.push_back(instrument_field);
    }

    CHECK(leg_instrument_fields.size() > 0);

    std::unique_ptr<CTPPositionChooser> position_chooser;
    if (leg_instrument_fields[0]->PositionDateType == THOST_FTDC_PDT_UseHistory) {
        // 使用历史仓合约
        position_chooser.reset(new YDFirstPositionChooser(*this, insert_order->orderDetail.tradeDirection));
    } else {
        position_chooser.reset(new AllClosePositionChooser(*this, insert_order->orderDetail.tradeDirection));
    };

    position_chooser->chooseOffsetFlag(instrument_field, leg_instrument_fields, insert_order->orderDetail.quantity);

    *p_out_offset_flag = position_chooser->getChoosedOffsetFlag();

    if (*p_out_offset_flag == THOST_FTDC_OF_Open) {
        // 开仓不用挂锁仓假单
        return ;
    }

    // 模拟挂单, 只不过状态为?, 主要是用于锁仓假单
    CThostFtdcOrderField order_field;
    memset(&order_field, 0, sizeof(CThostFtdcOrderField));
    strncpy(order_field.InstrumentID
            , insert_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str()
            , sizeof(TThostFtdcInstrumentIDType) - 1);
    order_field.FrontID = insert_order->upsideOrderRef.ctpRef.frontID;
    order_field.SessionID = insert_order->upsideOrderRef.ctpRef.sessionID;
    strncpy(order_field.OrderRef, insert_order->upsideOrderRef.ctpRef.orderRef.c_str(), sizeof(TThostFtdcOrderRefType) - 1);
    order_field.OrderStatus = '?';
    order_field.VolumeTotalOriginal = insert_order->orderDetail.quantity;
    order_field.VolumeTotal = order_field.VolumeTotalOriginal;
    order_field.VolumeTraded = 0;
    if (insert_order->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_BUY) {
        order_field.Direction = THOST_FTDC_D_Buy;
    } else {
        order_field.Direction = THOST_FTDC_D_Sell;
    }
    order_field.CombOffsetFlag[0] = *p_out_offset_flag;

    processOrder(order_field);
}

bool CTPPositionManager::hasCloseYDPosition(
        const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
        , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
        , int32_t volume) const {
    std::string position_key = getPositionKey(leg_instrument_field->InstrumentID, pos_direction);

    auto pos_summary_it = position_summaries_.find(position_key);
    if (pos_summary_it == position_summaries_.end()) {
        return false;
    }

    std::shared_ptr<CTPPositionSummary> pos_summary = pos_summary_it->second;
    CHECK(pos_summary);

    int32_t total_volume = pos_summary->ydPosInfo.ydPosVolume;
    int32_t used_volume = pos_summary->closeYDInfo.closeYDVolume;
    auto it = pos_summary->fronzenInfos.find(CTPFronzenPositionDateType::FRONZEN_YD_POSITION);
    if (it != pos_summary->fronzenInfos.end()) {
        used_volume += it->second.fronzenTotalVolume;
    }

    if (total_volume >= used_volume + volume) {
        return true;
    }
    return false;
}

bool CTPPositionManager::hasCloseTDPosition(
        const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
        , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
        , int32_t volume) const {
    std::string position_key = getPositionKey(leg_instrument_field->InstrumentID, pos_direction);

    auto pos_summary_it = position_summaries_.find(position_key);
    if (pos_summary_it == position_summaries_.end()) {
        return false;
    }

    std::shared_ptr<CTPPositionSummary> pos_summary = pos_summary_it->second;
    CHECK(pos_summary);

    int total_volume = pos_summary->openTDInfo.openTDVolume;
    int used_volume= pos_summary->closeTDInfo.closeTDVolume;
    auto it = pos_summary->fronzenInfos.find(CTPFronzenPositionDateType::FRONZEN_TD_POSITION);
    if (it != pos_summary->fronzenInfos.end()) {
        used_volume += it->second.fronzenTotalVolume;
    }

    if (total_volume >= used_volume + volume) {
        return true;
    }
    return false;
}

bool CTPPositionManager::hasClosePosition(
        const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
        , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
        , int32_t volume) const {
    std::string position_key = getPositionKey(leg_instrument_field->InstrumentID, pos_direction);

    auto pos_summary_it = position_summaries_.find(position_key);
    if (pos_summary_it == position_summaries_.end()) {
        return false;
    }

    std::shared_ptr<CTPPositionSummary> pos_summary = pos_summary_it->second;
    CHECK(pos_summary);

    int32_t total_volume = pos_summary->ydPosInfo.ydPosVolume + pos_summary->openTDInfo.openTDVolume;
    int32_t used_volume = pos_summary->closeYDInfo.closeYDVolume + pos_summary->closeTDInfo.closeTDVolume;
    auto it = pos_summary->fronzenInfos.find(CTPFronzenPositionDateType::FRONZEN_ALL_POSITION);
    if (it != pos_summary->fronzenInfos.end()) {
        used_volume += it->second.fronzenTotalVolume;
    }
    if (total_volume >= used_volume + volume) {
        return true;
    }
    return false;
}

void CTPPositionManager::handleOrderInsertCanceled(
        const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_cancelled_order) {
    if (state_ != INIT_FINISHED) {
        return ;
    }

    std::string order_ref_key = getOrderRefKey(
            insert_cancelled_order->upsideOrderRef.ctpRef.frontID
            , insert_cancelled_order->upsideOrderRef.ctpRef.sessionID
            , insert_cancelled_order->upsideOrderRef.ctpRef.orderRef);
    if (order_ref_key.empty()) {
        return ;
    }

    std::string instrument_id = insert_cancelled_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode;
    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
        = data_manager_->getInstrumentsHolder().getInstrument(instrument_id.c_str());
    if (!instrument_field) {
        APPLOG_ERROR("Failed to found instrument field for InstrumentID={} for execOrderId={}",instrument_id
                , insert_cancelled_order->execOrderId);
        return ;
    }

    std::vector<std::shared_ptr<CThostFtdcInstrumentField>> leg_instrument_fields;
    if (instrument_field->ProductClass == THOST_FTDC_PC_Combination) {
        if (!data_manager_->sliceCombinationInstrument(instrument_id, leg_instrument_fields)) {
            return ;
        }
    } else {
        leg_instrument_fields.push_back(instrument_field);
    }
    CHECK(leg_instrument_fields.size() > 0);

    // 从一个腿查原有订单信息，如果找到了，说明有模拟挂单
    auto map_info_it = indexed_orders_.find(leg_instrument_fields[0]->InstrumentID);
    if (map_info_it == indexed_orders_.end()) {
        return ;
    }
    auto order_info_it = map_info_it->second->ref_orders.find(order_ref_key);
    if (order_info_it == map_info_it->second->ref_orders.end()) {
        return ;
    }

    std::shared_ptr<OrderInfo> order_info = order_info_it->second;
    CThostFtdcOrderField order_field(*(order_info->order));
    order_field.OrderStatus = THOST_FTDC_OST_Canceled;
    processOrder(order_field);
}

void CTPPositionManager::handleInitStart() {
    APPLOG_INFO("handleInitStart...");
    position_summaries_.clear();
    indexed_orders_.clear();
    while(!waiting_orders_.empty()) {
        waiting_orders_.pop();
    }
    while(!waiting_trades_.empty()) {
        waiting_trades_.pop();
    }
    state_ = IN_INITED;
}

void CTPPositionManager::handleInitPositions(const std::shared_ptr<QryPositionResp>& positions_rsp) {
    APPLOG_INFO("handleInitPositions positions.size={}", positions_rsp->positions.size());
    for (const CThostFtdcInvestorPositionField& position_field : positions_rsp->positions) {
        std::shared_ptr<CThostFtdcInstrumentField> instrument_field
            = data_manager_->getInstrumentsHolder().getInstrument(position_field.InstrumentID);
        if (!instrument_field) {
            APPLOG_ERROR("Failed to found instrument field for InstrumentID={}", position_field.InstrumentID);
            continue;
        }

        if (position_field.PosiDirection != THOST_FTDC_PD_Long
                && position_field.PosiDirection != THOST_FTDC_PD_Short) {
            // 只取多头或者空头
            APPLOG_INFO("Ignore InstrumentID={} PosiDirection={} item"
                     , position_field.InstrumentID, position_field.PosiDirection);
            continue;
        }

        std::shared_ptr<CTPPositionSummary> position_summary(new CTPPositionSummary());
        position_summary->__set_instrumentID(position_field.InstrumentID);
        position_summary->__set_exchangeID(instrument_field->ExchangeID);
        position_summary->__set_productClass(instrument_field->ProductClass);
        position_summary->__set_productID(instrument_field->ProductID);

        if (instrument_field->PositionDateType == THOST_FTDC_PDT_UseHistory) {
            // 区分历史仓合约
            if (position_field.PositionDate == THOST_FTDC_PSD_Today) {
                continue;
            }
            position_summary->__set_contractPosDateType(CTPContractPosDateType::POS_USE_HISTORY);
        } else {
            if (position_field.PositionDate == THOST_FTDC_PSD_History) {
                APPLOG_ERROR("[CAREFUL]InstrumentID={} do not use hosting, but received history position!");
                continue;
            }
            position_summary->__set_contractPosDateType(CTPContractPosDateType::POS_UNUSE_HISTORY);
        }

        if (position_field.PosiDirection == THOST_FTDC_PD_Long) {
            position_summary->__set_posDirection(CTPPositionDirection::POSITION_LONG);
        } else {
            position_summary->__set_posDirection(CTPPositionDirection::POSITION_SHORT);
        }
        position_summary->__isset.ydPosInfo = true;
        position_summary->ydPosInfo.__set_ydPosVolume(position_field.YdPosition);
        position_summary->__isset.fronzenInfos = true;

        position_summaries_[getPositionKey(position_summary->instrumentID, position_summary->posDirection)]
                                       = position_summary;
    }
}

void CTPPositionManager::handleInitOrders(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp) {
    APPLOG_INFO("handleInitOrders orders.size={}", all_orders_rsp->orders.size());
    for (const CThostFtdcOrderField& order_field : all_orders_rsp->orders) {
        processOrder(order_field);
    }
}

void CTPPositionManager::handleInitTrades(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp) {
    APPLOG_INFO("handleInitTrades trades.size={}", all_trades_rsp->trades.size());
    for (const CThostFtdcTradeField& trade_field : all_trades_rsp->trades) {
        processTrade(trade_field);
    }

    handleInitFinished();
}

void CTPPositionManager::handleInitFinished() {
    APPLOG_INFO("handleInitFinished...");
    while(!waiting_orders_.empty()) {
        processOrder(waiting_orders_.front());
        waiting_orders_.pop();
    }
    while(!waiting_trades_.empty()) {
        processTrade(waiting_trades_.front());
        waiting_trades_.pop();
    }

    state_ = INIT_FINISHED;
}

void CTPPositionManager::handleOrderNotify(const CThostFtdcOrderField& pOrder) {
    if (state_ != INIT_FINISHED) {
        waiting_orders_.push(pOrder);
        return ;
    }

    processOrder(pOrder);
}

void CTPPositionManager::handleTradeNotify(const CThostFtdcTradeField& pTrade) {
    if (state_ != INIT_FINISHED) {
        waiting_trades_.push(pTrade);
        return ;
    }

    processTrade(pTrade);
}


void CTPPositionManager::processOrder(const CThostFtdcOrderField& order_field) {
    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
        = data_manager_->getInstrumentsHolder().getInstrument(order_field.InstrumentID);
    if (!instrument_field) {
        APPLOG_ERROR("processOrder failed to found instrument field for InstrumentID={}", order_field.InstrumentID);
        return ;
    }

    APPLOG_INFO("processOrder InstrumentID={}, ProductClass={}"
            ", FrontID={}, SessionID={}, OrderRef={}, ExchangeID={}, OrderSysID={}"
            ", OffsetFlag={},OrderStatus={}, VolumeTotalOrigin={}, VolumeTraded={}"
            , order_field.InstrumentID, instrument_field->ProductClass
            , order_field.FrontID, order_field.SessionID, order_field.OrderRef
            , order_field.ExchangeID, order_field.OrderSysID
            , order_field.CombOffsetFlag[0]
            , order_field.OrderStatus
            , order_field.VolumeTotalOriginal
            , order_field.VolumeTraded);

    std::vector<std::shared_ptr<CThostFtdcInstrumentField>> leg_instrument_fields;
    if (instrument_field->ProductClass == THOST_FTDC_PC_Combination) {
        if (!data_manager_->sliceCombinationInstrument(order_field.InstrumentID, leg_instrument_fields)) {
            return ;
        }
    } else {
        leg_instrument_fields.push_back(instrument_field);
    }

    for (int leg_index = 0; leg_index < (int)leg_instrument_fields.size(); ++leg_index) {
        processLegOrder(leg_instrument_fields[leg_index], leg_index, order_field);
    }
}

void CTPPositionManager::processLegOrder(
        const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
        , int leg_index
        , const CThostFtdcOrderField& order_field) {
    CTPTradeDirection::type trade_direction = CTPTradeDirection::CTP_BUY;
    if (leg_index == 0) {
        if (order_field.Direction == THOST_FTDC_D_Sell) {
            trade_direction = CTPTradeDirection::CTP_SELL;
        }
    } else {
        if (order_field.Direction == THOST_FTDC_D_Buy) {
            trade_direction = CTPTradeDirection::CTP_SELL;
        }
    }

    std::string order_ref_key = getOrderRefKey(order_field);
    std::string order_id_key = getOrderIdKey(order_field);

    APPLOG_INFO("processLegOrder legInstrumentID={}, leg_index={}"
                ", FrontID={}, SessionID={}, OrderRef={}, ExchangeID={}, OrderSysID={}"
                ", order_ref_key={}, order_id_key={}, trade_direction={}"
                ", OffsetFlag={}, OrderStatus={}, VolumeTotalOrigin={}, VolumeTraded={}"
                , leg_instrument_field->InstrumentID, leg_index
                , order_field.FrontID, order_field.SessionID, order_field.OrderRef
                , order_field.ExchangeID, order_field.OrderSysID
                , order_ref_key, order_id_key, trade_direction
                , order_field.CombOffsetFlag[0]
                , order_field.OrderStatus
                , order_field.VolumeTotalOriginal
                , order_field.VolumeTraded);

    if (order_ref_key.empty() && order_id_key.empty()) {
        APPLOG_WARN("Unexpected CThostFtdcOrderField, does not have any key");
        return ;
    }

    std::shared_ptr<IndexedOrderInfoMap> indexed_order_info_map;
    auto map_it = indexed_orders_.find(leg_instrument_field->InstrumentID);
    if (map_it == indexed_orders_.end()) {
        indexed_order_info_map.reset(new IndexedOrderInfoMap());
        indexed_orders_[leg_instrument_field->InstrumentID] = indexed_order_info_map;
    } else {
        indexed_order_info_map = map_it->second;
    }

    std::shared_ptr<OrderInfo> order_info;
    if (!order_ref_key.empty()) {
        auto ref_it = indexed_order_info_map->ref_orders.find(order_ref_key);
        if (ref_it == indexed_order_info_map->ref_orders.end()) {
            order_info.reset(new OrderInfo(leg_instrument_field, trade_direction, order_field));
            indexed_order_info_map->ref_orders[order_ref_key] = order_info;
        } else {
            order_info = ref_it->second;
        }

        if (!order_id_key.empty()
                && indexed_order_info_map->id_orders.find(order_id_key) == indexed_order_info_map->id_orders.end()) {
            indexed_order_info_map->id_orders[order_id_key] = order_info;
        }
    } else {
        auto id_it = indexed_order_info_map->id_orders.find(order_id_key);
        if (id_it == indexed_order_info_map->id_orders.end()) {
            order_info.reset(new OrderInfo(leg_instrument_field, trade_direction, order_field));
            indexed_order_info_map->id_orders[order_id_key] = order_info;
        } else {
            order_info = id_it->second;
        }
    }

    if (order_info->order) {
        // 订单终态不接受处理
        if (order_info->order->OrderStatus == THOST_FTDC_OST_Canceled
                || order_info->order->OrderStatus == THOST_FTDC_OST_AllTraded
                || order_info->order->OrderStatus == THOST_FTDC_OST_Touched) {
            return;
        }

        // 判断order_info是否有效
        if (order_field.VolumeTraded < order_info->order->VolumeTraded) {
            // 成交数量减少，明显出现状态回退，丢弃
            return ;
        }

        order_info->order.reset(new CThostFtdcOrderField(order_field));
        processOrderUpdated(order_info);
    } else {
        order_info->order.reset(new CThostFtdcOrderField(order_field));
        processOrderAdded(order_info);
    }
}

void CTPPositionManager::processTrade(const CThostFtdcTradeField& trade_field) {
    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
            = data_manager_->getInstrumentsHolder().getInstrument(trade_field.InstrumentID);
    if (!instrument_field) {
        APPLOG_ERROR("processTrade failed to found instrument field for InstrumentID={}", trade_field.InstrumentID);
        return ;
    }

    std::string order_id_key = getOrderIdKey(trade_field);
    if (order_id_key.empty()) {
        APPLOG_ERROR("processTrade failed to generate order id key, some thing wrong???");
        return ;
    }

    auto indexed_order_info_map_it = indexed_orders_.find(trade_field.InstrumentID);
    if (indexed_order_info_map_it == indexed_orders_.end()) {
        APPLOG_INFO("processTrade indexed order info map not found for instrument_id={}, maybe order not created!"
                , trade_field.InstrumentID);
        return ;
    }

    auto id_order_it = indexed_order_info_map_it->second->id_orders.find(order_id_key);
    if (id_order_it == indexed_order_info_map_it->second->id_orders.end()) {
        APPLOG_INFO("processTrade order not found for order_id_key={} instrument_id={}", order_id_key, trade_field.InstrumentID);
        return ;
    }

    std::shared_ptr<OrderInfo> operate_order_info = id_order_it->second;
    CHECK(operate_order_info);

    int index = 0;
    for (; index < (int)operate_order_info->trades.size(); ++index) {
        const CThostFtdcTradeField& in_order_trade = operate_order_info->trades[index];
        if (0 == strcmp(in_order_trade.TradeID, trade_field.TradeID)) {
            break;
        }
    }

    if (index < (int)operate_order_info->trades.size()) {
        APPLOG_INFO("trade already processed, trade_id={}, order_id_key={}, instrument_id={}"
                , trade_field.TradeID, order_id_key, trade_field.InstrumentID);
        return ;
    }

    operate_order_info->trades.push_back(trade_field);
    processTradeAdded(operate_order_info, trade_field);
}

void CTPPositionManager::processOrderAdded(const std::shared_ptr<OrderInfo>& new_order_info) {
    APPLOG_INFO("processOrderAdded instrument_id={}, ctp_offset_flag={}, pos_direction={}"
            ", FrontID={}, SessionID={}, OrderRef={}, ExchangeID={}, OrderSysID={}"
            ", OrderStatus={}, VolumeTotalOrigin={}, VolumeTraded={}"
            , new_order_info->instrument_id, new_order_info->ctp_offset_flag, new_order_info->pos_direction
            , new_order_info->order->FrontID, new_order_info->order->SessionID, new_order_info->order->OrderRef
            , new_order_info->order->ExchangeID, new_order_info->order->OrderSysID
            , new_order_info->order->OrderStatus, new_order_info->order->VolumeTotalOriginal
            , new_order_info->order->VolumeTraded);

    std::shared_ptr<CTPPositionSummary> position_summary;
    std::string position_key = getPositionKey(new_order_info->instrument_id, new_order_info->pos_direction);

    auto position_it = position_summaries_.find(position_key);
    if (position_it == position_summaries_.end()) {
        position_summary.reset(new CTPPositionSummary());
        position_summary->__set_instrumentID(new_order_info->instrument_id);
        position_summary->__set_exchangeID(new_order_info->instrument_field->ExchangeID);
        position_summary->__set_productClass(new_order_info->instrument_field->ProductClass);
        position_summary->__set_productID(new_order_info->instrument_field->ProductID);
        if (new_order_info->instrument_field->PositionDateType == THOST_FTDC_PDT_UseHistory) {
            position_summary->__set_contractPosDateType(CTPContractPosDateType::POS_USE_HISTORY);
        } else {
            position_summary->__set_contractPosDateType(CTPContractPosDateType::POS_UNUSE_HISTORY);
        }
        position_summary->__set_posDirection(new_order_info->pos_direction);
        position_summary->__isset.ydPosInfo = true;
        position_summary->ydPosInfo.__set_ydPosVolume(0);
        position_summary->__isset.fronzenInfos = true;

        position_summaries_[position_key] = position_summary;
    } else {
        position_summary = position_it->second;
    }

    CHECK(position_summary);

    // 处理平仓冻结信息
    if (new_order_info->ctp_offset_flag != THOST_FTDC_OF_Close
            && new_order_info->ctp_offset_flag != THOST_FTDC_OF_CloseToday
            && new_order_info->ctp_offset_flag != THOST_FTDC_OF_CloseYesterday) {
        return ;
    }

    auto pos_fronzen_it = position_summary->fronzenInfos.find(new_order_info->fronzen_date_type);
    if (pos_fronzen_it == position_summary->fronzenInfos.end()) {
        CTPFronzenInfo new_fronzen_info;
        new_fronzen_info.__set_fronzenTotalVolume(0);
        new_fronzen_info.__set_fronzenPosDateType(new_order_info->fronzen_date_type);
        pos_fronzen_it = position_summary->fronzenInfos.insert(
                std::pair<CTPFronzenPositionDateType::type, CTPFronzenInfo>(new_order_info->fronzen_date_type, new_fronzen_info)).first;
    }
    CTPFronzenInfo& fronzen_info = pos_fronzen_it->second;

    fronzen_info.__isset.fronzenTotalVolume = true;
    fronzen_info.fronzenTotalVolume += new_order_info->order->VolumeTotalOriginal;
    if (new_order_info->order->OrderStatus == THOST_FTDC_OST_Canceled) {
        fronzen_info.fronzenTotalVolume -= (new_order_info->order->VolumeTotalOriginal - new_order_info->order->VolumeTraded);
    }
    if (new_order_info->order->OrderStatus == THOST_FTDC_OST_Touched) {
        // 条件单被触发，这个时候条件单变成挂单, 条件单不能锁仓
        fronzen_info.fronzenTotalVolume -= new_order_info->order->VolumeTotalOriginal;
    }
}

void CTPPositionManager::processOrderUpdated(const std::shared_ptr<OrderInfo>& update_order_info) {
    APPLOG_INFO("processOrderUpdated instrument_id={}, ctp_offset_flag={}, pos_direction={}"
                ", FrontID={}, SessionID={}, OrderRef={}, ExchangeID={}, OrderSysID={}"
                ", OrderStatus={}, VolumeTotalOrigin={}, VolumeTraded={}"
                , update_order_info->instrument_id, update_order_info->ctp_offset_flag, update_order_info->pos_direction
                , update_order_info->order->FrontID, update_order_info->order->SessionID, update_order_info->order->OrderRef
                , update_order_info->order->ExchangeID, update_order_info->order->OrderSysID
                , update_order_info->order->OrderStatus, update_order_info->order->VolumeTotalOriginal
                , update_order_info->order->VolumeTraded);

    // 只需处理平仓信息，开仓由成交处理
    if (update_order_info->ctp_offset_flag != THOST_FTDC_OF_Close
            && update_order_info->ctp_offset_flag != THOST_FTDC_OF_CloseToday
            && update_order_info->ctp_offset_flag != THOST_FTDC_OF_CloseYesterday) {
        APPLOG_INFO("processOrderUpdated ignore, ctp_offset_flag={}, orderRefKey={}, orderIdKey={}"
                , update_order_info->ctp_offset_flag
                , getOrderRefKey(*(update_order_info->order))
                , getOrderIdKey(*(update_order_info->order)));
        return ;
    }

    std::string position_key = getPositionKey(update_order_info->instrument_id, update_order_info->pos_direction);
    std::shared_ptr<CTPPositionSummary> position_summary = position_summaries_[position_key];
    CHECK(position_summary);

    auto fronzen_info_it = position_summary->fronzenInfos.find(update_order_info->fronzen_date_type);
    CHECK(fronzen_info_it != position_summary->fronzenInfos.end());

    if (update_order_info->order->OrderStatus == THOST_FTDC_OST_Canceled) {
        fronzen_info_it->second.fronzenTotalVolume
            -= (update_order_info->order->VolumeTotalOriginal - update_order_info->order->VolumeTraded);
    }
    if (update_order_info->order->OrderStatus == THOST_FTDC_OST_Touched) {
        // 条件单被触发，这个时候条件单变成挂单, 释放原有的锁单量, 由挂单触发重新锁单
        fronzen_info_it->second.fronzenTotalVolume -= update_order_info->order->VolumeTotalOriginal;
    }
}

void CTPPositionManager::processTradeAdded(const std::shared_ptr<OrderInfo>& order_info
        , const CThostFtdcTradeField& trade) {
    std::string position_key = getPositionKey(order_info->instrument_id, order_info->pos_direction);

    APPLOG_INFO("processTradeAdded instrument_id={}, ctp_offset_flag={}, pos_direction={}"
                ", FrontID={}, SessionID={}, OrderRef={}, ExchangeID={}, OrderSysID={}"
                ", OrderStatus={}, VolumeTotalOrigin={}, VolumeTraded={}"
                ", TradeID={}, TradeVolume={}, TradePrice={}, position_key={}"
                , order_info->instrument_id, order_info->ctp_offset_flag, order_info->pos_direction
                , order_info->order->FrontID, order_info->order->SessionID, order_info->order->OrderRef
                , order_info->order->ExchangeID, order_info->order->OrderSysID
                , order_info->order->OrderStatus, order_info->order->VolumeTotalOriginal, order_info->order->VolumeTraded
                , trade.TradeID, trade.Volume, trade.Price
                , position_key);

    std::shared_ptr<CTPPositionSummary> position_summary = position_summaries_[position_key];
    CHECK(position_summary);

    if (order_info->ctp_offset_flag == THOST_FTDC_OF_Open) {
        // 开仓操作
        position_summary->__isset.openTDInfo = true;
        position_summary->openTDInfo.__isset.openTDVolume = true;
        position_summary->openTDInfo.openTDVolume += trade.Volume;
        return ;
    }

    if (order_info->ctp_offset_flag != THOST_FTDC_OF_Close
            && order_info->ctp_offset_flag != THOST_FTDC_OF_CloseToday
            && order_info->ctp_offset_flag != THOST_FTDC_OF_CloseYesterday) {
        APPLOG_INFO("onTradeAdded ignore, ctp_offset_flag={}, orderIdKey={}, tradeId={}"
                , order_info->ctp_offset_flag, getOrderIdKey(trade), trade.TradeID);
        return ;
    }

    // 增加平今或者平昨仓位
    if (order_info->instrument_field->PositionDateType == THOST_FTDC_PDT_UseHistory) {
        if (order_info->ctp_offset_flag == THOST_FTDC_OF_Close
                || order_info->ctp_offset_flag == THOST_FTDC_OF_CloseYesterday) {
            position_summary->__isset.closeYDInfo = true;
            position_summary->closeYDInfo.__isset.closeYDVolume = true;
            position_summary->closeYDInfo.closeYDVolume += trade.Volume;
        } else {
            position_summary->__isset.closeTDInfo = true;
            position_summary->closeTDInfo.__isset.closeTDVolume = true;
            position_summary->closeTDInfo.closeTDVolume += trade.Volume;
        }
    } else {
        // 不区分历史持仓，则优先计算为平昨，如果总量超过了昨持仓量，则转化为平今
        position_summary->__isset.closeYDInfo = true;
        position_summary->closeYDInfo.__isset.closeYDVolume = true;
        position_summary->closeYDInfo.closeYDVolume += trade.Volume;

        if (position_summary->closeYDInfo.closeYDVolume > position_summary->ydPosInfo.ydPosVolume) {
            position_summary->__isset.closeTDInfo = true;
            position_summary->closeTDInfo.__isset.closeTDVolume = true;
            position_summary->closeTDInfo.closeTDVolume +=
                    (position_summary->closeYDInfo.closeYDVolume - position_summary->ydPosInfo.ydPosVolume);
            position_summary->closeYDInfo.closeYDVolume = position_summary->ydPosInfo.ydPosVolume;
        }
    }

    auto fronzen_info_it = position_summary->fronzenInfos.find(order_info->fronzen_date_type);
    CHECK(fronzen_info_it != position_summary->fronzenInfos.end());
    fronzen_info_it->second.fronzenTotalVolume -= trade.Volume;
}

std::string CTPPositionManager::getOrderRefKey(const CThostFtdcOrderField& order_field) const {
    if (order_field.OrderRef[0] == 0) {
        return "";
    }

    return getOrderRefKey(order_field.FrontID, order_field.SessionID, order_field.OrderRef);
}

std::string CTPPositionManager::getOrderRefKey(int front_id, int session_id, const std::string& order_ref) const {
    if (order_ref.empty()) {
        return "";
    }
    std::string order_ref_key;
    order_ref_key.append(boost::lexical_cast<std::string>(front_id))
                 .append("_")
                 .append(boost::lexical_cast<std::string>(session_id))
                 .append("_")
                 .append(order_ref);
    return order_ref_key;
}

std::string CTPPositionManager::getOrderIdKey(const CThostFtdcOrderField& order_field) const {
    if (order_field.OrderSysID[0] == 0) {
        return "";
    }

    std::string order_id_key;
    order_id_key.append(order_field.ExchangeID).append("_").append(order_field.OrderSysID);
    return order_id_key;
}

std::string CTPPositionManager::getOrderIdKey(const CThostFtdcTradeField& trade_field) const {
    if (trade_field.OrderSysID[0] == 0) {
        return "";
    }

    std::string order_id_key;
    order_id_key.append(trade_field.ExchangeID).append("_").append(trade_field.OrderSysID);
    return order_id_key;
}


std::string CTPPositionManager::getPositionKey(
        const std::string& instrument_id
        , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction) const {
    std::string key(instrument_id);
    key.append("_").append(boost::lexical_cast<std::string>(pos_direction));
    return key;
}

CTPPositionManager::OrderInfo::OrderInfo(
        const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field_p
        , ::xueqiao::trade::hosting::CTPTradeDirection::type trade_direction_p
        , const CThostFtdcOrderField& order_p) {
    instrument_id = instrument_field_p->InstrumentID;
    instrument_field = instrument_field_p;
    trade_direction = trade_direction_p;
    ctp_offset_flag = order_p.CombOffsetFlag[0];

    // 操作的持仓
    if (ctp_offset_flag == THOST_FTDC_OF_Open) {
        if (trade_direction == CTPTradeDirection::CTP_BUY) {
            pos_direction = CTPPositionDirection::POSITION_LONG;
        } else {
            pos_direction = CTPPositionDirection::POSITION_SHORT;
        }
    } else {
        // 平仓方向相反
        if (trade_direction == CTPTradeDirection::CTP_BUY) {
            pos_direction = CTPPositionDirection::POSITION_SHORT;
        } else {
            pos_direction = CTPPositionDirection::POSITION_LONG;
        }
    }

    // 冻结的仓位信息
    fronzen_date_type = CTPFronzenPositionDateType::FRONZEN_ALL_POSITION;
    if (instrument_field_p->PositionDateType == THOST_FTDC_PDT_UseHistory) {
        if (ctp_offset_flag == THOST_FTDC_OF_Close || ctp_offset_flag == THOST_FTDC_OF_CloseYesterday) {
            fronzen_date_type = CTPFronzenPositionDateType::FRONZEN_YD_POSITION;
        } else {
            fronzen_date_type = CTPFronzenPositionDateType::FRONZEN_TD_POSITION;
        }
    }

}

void CTPPositionManager::handleDumpPositionSummaries(
        SyncCall* sync_call, std::vector<PositionSummary>* position_summaries) {
    for (const auto& ctp_summary : position_summaries_) {
        PositionSummary pos_summary;
        pos_summary.__set_techPlatform(BrokerTechPlatform::TECH_CTP);
        pos_summary.__isset.ctpPosSummary = true;
        CHECK(ctp_summary.second);
        pos_summary.ctpPosSummary = *(ctp_summary.second);
        position_summaries->push_back(pos_summary);
    }
    sync_call->notify();
}

