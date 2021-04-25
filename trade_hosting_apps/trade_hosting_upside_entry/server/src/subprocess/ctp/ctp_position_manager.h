/*
 * ctp_position_manager.h
 *
 *  Created on: 2018年3月27日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_POSITION_MANAGER_H_
#define SRC_SUBPROCESS_CTP_CTP_POSITION_MANAGER_H_

#include "base/thread_pool.h"
#include "ctp_data_manager.h"
#include "trade_hosting_upside_position_types.h"
#include "ThostFtdcTraderApi.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPPositionManager : public CThostFtdcTraderSpi, public IDataManagerListener {
public:
    CTPPositionManager(const std::shared_ptr<CTPDataManager>& data_manager);
    virtual ~CTPPositionManager();

    virtual void onDataManagerInitStart();
    virtual void onQryPositionsFinished(const std::shared_ptr<QryPositionResp>& positions_rsp);
    virtual void onQryOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp);
    virtual void onQryTradesFinished(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp);

    // 下单准备，填充持仓参数
    void orderInsertPrepare(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , TThostFtdcOffsetFlagType& out_offset_flag) throw(::platform::comm::ErrorInfo);
    // 准备结束后，又取消，发送orderInsert失败时调用
    void orderInsertCanceled(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_canceled_order);

    void syncOrder(const CThostFtdcOrderField* p_order);
    void syncTrade(const CThostFtdcTradeField* p_trade);

    ///报单通知
    virtual void OnRtnOrder(CThostFtdcOrderField *pOrder);
    virtual void OnRtnTrade(CThostFtdcTradeField *pTrade);

    void dumpPositionSummaries(
            std::vector<::xueqiao::trade::hosting::upside::position::PositionSummary>& position_summaries);


    // 以下方法实际供内部类使用
    // 判断昨仓量是否足够
    bool hasCloseYDPosition(
            const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
            , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
            , int32_t volume) const ;

    // 判断今仓量是否足够
    bool hasCloseTDPosition(
            const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
            , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
            , int32_t volume) const ;

    // 判断平仓量是否足够
    bool hasClosePosition(
            const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
            , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction
            , int32_t volume) const;

    class CTPPositionChooser {
        public:
            CTPPositionChooser(CTPPositionManager& position_manager
                    , HostingExecOrderTradeDirection::type order_trade_direction);
            virtual ~CTPPositionChooser() = default;

            virtual void chooseOffsetFlag(
                    const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field
                    , const std::vector<std::shared_ptr<CThostFtdcInstrumentField>>& leg_instrument_fields
                    , int32_t volume) = 0;

            inline TThostFtdcOffsetFlagType getChoosedOffsetFlag() {
                return choosed_offset_flag_;
            }

        protected:
            ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type
                         getClosePosDirection(int leg_index);

            CTPPositionManager& position_manager_;
            HostingExecOrderTradeDirection::type order_trade_direction_; // 订单下单方向

            TThostFtdcOffsetFlagType choosed_offset_flag_ = THOST_FTDC_OF_Open;
    };

private:
    void handleInitStart();
    void handleInitPositions(const std::shared_ptr<QryPositionResp>& positions_rsp);
    void handleInitOrders(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp);
    void handleInitTrades(const std::shared_ptr<QryAllTradesResp>& all_trades_rsp);
    void handleInitFinished();
    void handleOrderNotify(const CThostFtdcOrderField& pOrder);
    void handleTradeNotify(const CThostFtdcTradeField& pTrade);
    void handleDumpPositionSummaries(soldier::base::SyncCall* sync_call
            , std::vector<::xueqiao::trade::hosting::upside::position::PositionSummary>* position_summaries);

    void handleOrderInsertPrepare(
            soldier::base::SyncCall* sync_call
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , TThostFtdcOffsetFlagType* p_out_offset_flag
            , platform::comm::ErrorInfo* err);

    void handleOrderInsertCanceled(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_cancelled_order);

    void processOrder(const CThostFtdcOrderField& order_field);
    void processLegOrder(const std::shared_ptr<CThostFtdcInstrumentField>& leg_instrument_field
            , int leg_index
            , const CThostFtdcOrderField& order_field);

    void processTrade(const CThostFtdcTradeField& trade_field);

    std::shared_ptr<CTPDataManager> data_manager_;
    std::unique_ptr<soldier::base::TaskThread> working_thread_;

    enum Status {
        NONE,
        IN_INITED,
        INIT_FINISHED,
    };
    Status state_ = {Status::NONE};

    std::queue<CThostFtdcOrderField> waiting_orders_;
    std::queue<CThostFtdcTradeField> waiting_trades_;

    // 单腿交易信息
    struct OrderInfo {
        std::string instrument_id;
        std::shared_ptr<CThostFtdcInstrumentField> instrument_field;
        ::xueqiao::trade::hosting::CTPTradeDirection::type trade_direction; // 对应腿的买卖方向
        ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type pos_direction;  // 订单操作的持仓方向
        ::xueqiao::trade::hosting::upside::position::CTPFronzenPositionDateType::type fronzen_date_type; // 冻结仓位信息

        TThostFtdcOffsetFlagType ctp_offset_flag;
        std::shared_ptr<CThostFtdcOrderField> order; // 原始订单信息
        std::vector<CThostFtdcTradeField> trades; // 单腿下的成交信息

        OrderInfo(const std::shared_ptr<CThostFtdcInstrumentField>& instrument_field_p
                , ::xueqiao::trade::hosting::CTPTradeDirection::type trade_direction_p
                , const CThostFtdcOrderField& order_p);
    };


    struct IndexedOrderInfoMap {
        std::map<std::string, std::shared_ptr<OrderInfo>> ref_orders;
        std::map<std::string, std::shared_ptr<OrderInfo>> id_orders;
    };

    void processOrderAdded(const std::shared_ptr<OrderInfo>& new_order_info);
    void processOrderUpdated(const std::shared_ptr<OrderInfo>& update_order_info);
    void processTradeAdded(const std::shared_ptr<OrderInfo>& order_info, const CThostFtdcTradeField& trade);

    std::string getOrderRefKey(const CThostFtdcOrderField& order_field) const;
    std::string getOrderRefKey(int front_id, int session_id, const std::string& order_ref) const;
    std::string getOrderIdKey(const CThostFtdcOrderField& order_field) const;
    std::string getOrderIdKey(const CThostFtdcTradeField& trade_field) const;
    std::string getPositionKey(const std::string& instrument_id
                , const ::xueqiao::trade::hosting::upside::position::CTPPositionDirection::type& pos_direction) const;

    std::map<std::string, std::shared_ptr<IndexedOrderInfoMap>> indexed_orders_;
    std::map<std::string, std::shared_ptr<::xueqiao::trade::hosting::upside::position::CTPPositionSummary>> position_summaries_;
};





} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace position



#endif /* SRC_SUBPROCESS_CTP_CTP_POSITION_MANAGER_H_ */
