/*
 * self_match_manager.h
 *
 *  Created on: 2018年4月21日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_COMMON_SELF_MATCH_MANAGER_H_
#define SRC_SUBPROCESS_COMMON_SELF_MATCH_MANAGER_H_

#include <list>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/sync_call.h"
#include "base/thread_pool.h"
#include "comm_types.h"
#include "trade_hosting_basic_types.h"
#include "trade_hosting_basic_errors_types.h"
#include "performance_utils.h"
#include "base/lru_cache.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

template<class OrderFieldType>
class SelfMatchManagerBackend {
public:
    virtual ~SelfMatchManagerBackend() = default;

    virtual const char* orderFieldTypeName() = 0;

    virtual bool isOrderSupported(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 订单成交方向
    virtual HostingExecOrderTradeDirection::type getTradeDirection(
            const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 订单价格
    virtual double getPrice(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 从执行订单中计算合约索引Key
    virtual std::string getOrderContractKey(const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& exec_order) = 0;

    // 计算订单的合约索引Key
    virtual std::string getOrderContractKey(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 计算订单的订单引用Key
    virtual std::string getOrderRefKey(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 计算订单的订单IDKey
    virtual std::string getOrderDealIDKey(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 是否比比较的订单版本更新
    virtual bool isNewerOrderField(const std::shared_ptr<OrderFieldType>& order_field
            , const std::shared_ptr<OrderFieldType>& origin_order_field) = 0;

    // 是否处在挂单状态
    virtual bool isHangStatus(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 打印订单日志
    virtual std::string dumpOrderField(const std::shared_ptr<OrderFieldType>& order_field) = 0;

    // 创建一个虚拟的雪橇发送状态的订单
    virtual std::shared_ptr<OrderFieldType> createDummyOrder(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& exec_order) = 0;

    bool isSameOrderField(const std::shared_ptr<OrderFieldType>& of1
            , const std::shared_ptr<OrderFieldType>& of2) {
        std::string of1_ref_key = getOrderRefKey(of1);
        std::string of2_ref_key = getOrderRefKey(of2);
        if (!of1_ref_key.empty() && !of2_ref_key.empty() && of1_ref_key == of2_ref_key) {
            return true;
        }

        std::string of1_dealid_key = getOrderDealIDKey(of1);
        std::string of2_dealid_key = getOrderDealIDKey(of2);
        if (!of1_dealid_key.empty() && !of2_dealid_key.empty() && of1_dealid_key == of2_dealid_key) {
            return true;
        }

        return false;
    }

protected:
    SelfMatchManagerBackend() = default;
};

template<class OrderFieldType>
class SelfMatchManager {
public:
    SelfMatchManager(const std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>>& backend)
        : recent_send_orderrefs_(100)
          , work_thread_(new soldier::base::TaskThread())
          , backend_(backend) {
        CHECK(backend_);
    }

    void onInitStart() {
        work_thread_->postTask(&SelfMatchManager::handleInitStart, this);
    }

    void onInitAllOrders(const std::shared_ptr<std::vector<std::shared_ptr<OrderFieldType>>>& all_order_fields) {
        CHECK(all_order_fields);
        work_thread_->postTask(&SelfMatchManager::handleInitAllOrders, this, all_order_fields);
    }

    void onInitFinished() {
        work_thread_->postTask(&SelfMatchManager::handleInitFinished, this);
    }

    void orderInsertPrepare(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , bool& has_self_match) throw(::platform::comm::ErrorInfo) {
        CHECK(insert_order);

        soldier::base::SyncCall sync_call;
        ::platform::comm::ErrorInfo err;
        has_self_match = false;
        work_thread_->postTask(&SelfMatchManager::handleOrderInsertPrepare, this, &sync_call
                    , insert_order, &has_self_match, & err);
        sync_call.wait();
        if (err.errorCode != 0) {
            throw err;
        }
    }

    void orderInsertCanceled(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_cancelled_order) {
        CHECK(insert_cancelled_order);
        work_thread_->postTask(&SelfMatchManager::handleOrderInsertCanceled, this, insert_cancelled_order);
    }

    void processOrder(const std::shared_ptr<OrderFieldType>& order_field) {
        CHECK(order_field)
        work_thread_->postTask(&SelfMatchManager::handleProcessOrder, this, order_field);
    }

    void deleteOrder(const std::shared_ptr<OrderFieldType>& order_field) {
        CHECK(order_field);
        work_thread_->postTask(&SelfMatchManager::handleDeleteOrder, this, order_field);
    }

    void copyOrdersHasDealID(std::list<std::shared_ptr<OrderFieldType>>& order_fields) {
        soldier::base::SyncCall sync_call;
        work_thread_->postTask(&SelfMatchManager::handleCopyOrdersHasDealID
                , this
                , &sync_call
                , &order_fields);
        sync_call.wait();
    }

private:
    struct OrderInfo {
        std::shared_ptr<OrderFieldType> order_field;
        bool in_hang_order_infos = false;
    };

    struct IndexedOrderInfoMap {
        std::map<std::string, std::shared_ptr<OrderInfo>> ref_orders;
        std::map<std::string, std::shared_ptr<OrderInfo>> dealid_orders;
    };

    struct HangOrderInfoList {
        // Order By Price Desc
        std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>> backend;
        std::vector<std::shared_ptr<OrderInfo>> hang_list;

        HangOrderInfoList(const std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>>& backend_param)
            : backend(backend_param) {
        }

        void insert(const std::shared_ptr<OrderInfo>& order_info) {
//            BLOCK_TIME_PRINT("HangOrderInfoList::insert")

            double price = backend->getPrice(order_info->order_field);
            auto it = hang_list.begin();
            for (; it != hang_list.end(); ++it) {
                if (price >= backend->getPrice((*it)->order_field)) {
                    break;
                }
            }
            if (it == hang_list.end()) {
                hang_list.push_back(order_info);
            } else {
                hang_list.insert(it, order_info);
            }

        }

        void remove(const std::shared_ptr<OrderInfo>& order_info) {
//            BLOCK_TIME_PRINT("HangOrderInfoList::remove")
            for (auto it = hang_list.begin(); it != hang_list.end(); ++it) {
                if (backend->isSameOrderField(order_info->order_field, (*it)->order_field)) {
                    hang_list.erase(it);
                    return ;
                }
            }
        }

        bool isEmpty() {
            return hang_list.empty();
        }

        double getMaxPrice() {
            return backend->getPrice(hang_list.front()->order_field);
        }

        double getMinPrice() {
            return backend->getPrice(hang_list.back()->order_field);
        }
    };

    void handleInitStart() {
        APPLOG_INFO("handleInitStart...");
        state_ = IN_INITED;
        indexed_all_order_infos_.clear();
        hang_order_infos_.clear();
        while(!waiting_orders_.empty()) {
            waiting_orders_.pop();
        }
    }

    void handleInitAllOrders(const std::shared_ptr<std::vector<std::shared_ptr<OrderFieldType>>>& all_order_fields) {
        APPLOG_INFO("handleInitAllOrders...");
        for (auto it = all_order_fields->begin(); it != all_order_fields->end(); ++it) {
            handleOrderAction(*it);
        }
    }

    void handleInitFinished() {
        while(!waiting_orders_.empty()) {
            CHECK(waiting_orders_.front());
            handleOrderAction(waiting_orders_.front());
            waiting_orders_.pop();
        }

        state_ = INIT_FINISHED;
        APPLOG_INFO("handleInitFinished...");
    }

    std::string getHangOrderKey(const std::shared_ptr<OrderFieldType>& order_field) {
        std::stringstream key_stream;
        key_stream << backend_->getOrderContractKey(order_field)
                   << "_"
                   << (int)backend_->getTradeDirection(order_field);
        return key_stream.str();
    }

    void addHangOrder(const std::shared_ptr<OrderInfo>& order_info) {
        CHECK(order_info);
        CHECK(!order_info->in_hang_order_infos);
        std::string key = getHangOrderKey(order_info->order_field);

        APPLOG_INFO("addHangOrder {}, key={}, order_field={}"
                , backend_->orderFieldTypeName(), key, backend_->dumpOrderField(order_info->order_field));

        std::shared_ptr<HangOrderInfoList> hang_order_info_list;
        auto order_info_it = hang_order_infos_.find(key);
        if(order_info_it == hang_order_infos_.end()) {
            hang_order_info_list.reset(new HangOrderInfoList(backend_));
            hang_order_infos_[key] = hang_order_info_list;
        } else {
            hang_order_info_list = order_info_it->second;
        }

        CHECK(hang_order_info_list);
        hang_order_info_list->insert(order_info);
        order_info->in_hang_order_infos = true;
    }

    void removeHangOrder(const std::shared_ptr<OrderInfo>& order_info) {
        CHECK(order_info);
        CHECK(order_info->in_hang_order_infos);

        std::string key = getHangOrderKey(order_info->order_field);
        APPLOG_INFO("removeHangOrder {}, key={}, order_field={}"
                , backend_->orderFieldTypeName(), key, backend_->dumpOrderField(order_info->order_field));

        std::shared_ptr<HangOrderInfoList> hang_order_info_list = hang_order_infos_[key];
        CHECK(hang_order_info_list);
        hang_order_info_list->remove(order_info);

        order_info->in_hang_order_infos = false;
    }

    void handleOrderAdded(const std::shared_ptr<OrderInfo>& order_info) {
        APPLOG_INFO("handleOrderAdded {}, order_field={}"
                , backend_->orderFieldTypeName(), backend_->dumpOrderField(order_info->order_field));

        if (backend_->isHangStatus(order_info->order_field)) {
            addHangOrder(order_info);
        }
    }

    void handleOrderUpdated(const std::shared_ptr<OrderInfo>& order_info) {
        APPLOG_INFO("handleOrderUpdated {}, order_field={}"
                , backend_->orderFieldTypeName(), backend_->dumpOrderField(order_info->order_field));

        if (!backend_->isHangStatus(order_info->order_field)) {
            if (order_info->in_hang_order_infos) {
                removeHangOrder(order_info);
            }
        } else {
            if (!order_info->in_hang_order_infos) {
                addHangOrder(order_info);
            }
        }
    }

    void handleOrderAction(const std::shared_ptr<OrderFieldType>& order_field) {
        CHECK(order_field);

        if (!backend_->isOrderSupported(order_field)) {
            return ;
        }

        std::string contractKey = backend_->getOrderContractKey(order_field);
        CHECK(!contractKey.empty());

        std::shared_ptr<IndexedOrderInfoMap> indexed_order_info_map;
        auto indexed_order_info_map_it = indexed_all_order_infos_.find(contractKey);
        if (indexed_order_info_map_it == indexed_all_order_infos_.end()) {
            indexed_order_info_map.reset(new IndexedOrderInfoMap());
            indexed_all_order_infos_[contractKey] = indexed_order_info_map;
        } else {
            indexed_order_info_map = indexed_order_info_map_it->second;
        }

        std::string order_ref_key = backend_->getOrderRefKey(order_field);
        std::string order_dealid_key = backend_->getOrderDealIDKey(order_field);

        if (order_ref_key.empty() && order_dealid_key.empty()) {
            APPLOG_WARN("[WARNING]Unexpected {}, does not have any key", backend_->orderFieldTypeName());
            return ;
        }

        CHECK(indexed_order_info_map);

        std::shared_ptr<OrderInfo> order_info;
        if (!order_ref_key.empty()) {
            auto ref_it = indexed_order_info_map->ref_orders.find(order_ref_key);
            if (ref_it == indexed_order_info_map->ref_orders.end()) {
                order_info.reset(new OrderInfo());
                indexed_order_info_map->ref_orders[order_ref_key] = order_info;
            } else {
                order_info = ref_it->second;
            }

            if (!order_dealid_key.empty()
                    && indexed_order_info_map->dealid_orders.find(order_dealid_key) == indexed_order_info_map->dealid_orders.end()) {
                indexed_order_info_map->dealid_orders[order_dealid_key] = order_info;
            }
        } else {
            auto id_it = indexed_order_info_map->dealid_orders.find(order_dealid_key);
            if (id_it == indexed_order_info_map->dealid_orders.end()) {
                order_info.reset(new OrderInfo());
                indexed_order_info_map->dealid_orders[order_dealid_key] = order_info;
            } else {
                order_info = id_it->second;
            }
        }

        CHECK(order_info);
        if (order_info->order_field) {
            if (!backend_->isNewerOrderField(order_field, order_info->order_field)) {
                return ;
            }

            order_info->order_field = order_field;
            handleOrderUpdated(order_info);
        } else {
            order_info->order_field = order_field;
            handleOrderAdded(order_info);
        }
    }

    void handleProcessOrder(const std::shared_ptr<OrderFieldType>& order) {
        CHECK(order);
        if (state_ != INIT_FINISHED) {
            waiting_orders_.push(order);
            return ;
        }
        handleOrderAction(order);
    }

    void handleOrderInsertPrepare(
            soldier::base::SyncCall* sync_call
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order
            , bool* has_self_match
            , ::platform::comm::ErrorInfo* err) {
        BLOCK_TIME_PRINT("SelfMatchManager::handleOrderInsertPrepare")

        APPLOG_INFO("handleOrderInsertPrepare execOrderId={}, orderTradeDirection={}, price={}"
                    , insert_order->execOrderId, insert_order->orderDetail.tradeDirection
                    , insert_order->orderDetail.limitPrice);
        soldier::base::AutoSyncCallNotify auto_sync_call_notify(*sync_call);
        if (state_ != INIT_FINISHED) {
            err->__set_errorCode(::xueqiao::trade::hosting::TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT);
            err->__set_errorMsg("trade account in init");
            return ;
        }

        std::shared_ptr<OrderFieldType> dummy_order = backend_->createDummyOrder(insert_order);
        std::string order_ref_key = backend_->getOrderRefKey(dummy_order);
        if (order_ref_key.empty()) {
            err->__set_errorCode(::xueqiao::trade::hosting::TradeHostingBasicErrorCode::ERROR_PARAMETER);
            err->__set_errorMsg("order no ref key!");
            return ;
        }

        if (recent_send_orderrefs_.exists(order_ref_key)) {
            // 说明最近准备发送过
            err->__set_errorCode(::xueqiao::trade::hosting::TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_DUPLICATE_SEND);
            err->__set_errorMsg("recent_send_orderrefs contains this order ref");
            return ;
        }

        std::string contractKey = backend_->getOrderContractKey(dummy_order);
        auto indexed_order_info_map_it = indexed_all_order_infos_.find(contractKey);
        if (indexed_order_info_map_it != indexed_all_order_infos_.end()) {
            std::shared_ptr<IndexedOrderInfoMap> indexed_order_info_map = indexed_order_info_map_it->second;

            if (indexed_order_info_map->ref_orders.find(order_ref_key) != indexed_order_info_map->ref_orders.end()) {
                // 上手的订单列表中存在
                err->__set_errorCode(::xueqiao::trade::hosting::TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_DUPLICATE_SEND);
                err->__set_errorMsg("indexed_order_info_map contains this order ref");
                return ;
            }
        }

        recent_send_orderrefs_.put(order_ref_key, 1);
        *has_self_match = hasSelfMatch(insert_order);
        if (!(*has_self_match)) {
            handleOrderAction(dummy_order);
        }
    }

    void handleCopyOrdersHasDealID(soldier::base::SyncCall* sync_call
            , std::list<std::shared_ptr<OrderFieldType>>* result_order_fields) {
        soldier::base::AutoSyncCallNotify auto_sync_call_notify(*sync_call);
        for (auto contract_it = indexed_all_order_infos_.begin();
                contract_it != indexed_all_order_infos_.end(); ++contract_it) {
            std::shared_ptr<IndexedOrderInfoMap> order_info_map = contract_it->second;
            for (auto order_it = order_info_map->dealid_orders.begin();
                    order_it != order_info_map->dealid_orders.end(); ++order_it) {
                result_order_fields->push_back(order_it->second->order_field);
            }
        }
    }

    bool hasSelfMatch(const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order) {
        std::stringstream key_stream;
        key_stream << backend_->getOrderContractKey(insert_order) << "_";
        if (insert_order->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_BUY) {
            key_stream << (int)HostingExecOrderTradeDirection::ORDER_SELL;
        } else {
            key_stream << (int)HostingExecOrderTradeDirection::ORDER_BUY;
        }

        auto hang_info_list_it = hang_order_infos_.find(key_stream.str());
        if (hang_info_list_it == hang_order_infos_.end()) {
            return false;
        }
        std::shared_ptr<HangOrderInfoList> hang_info_list = hang_info_list_it->second;
        if (!hang_info_list) {
            return false;
        }
        if (hang_info_list->isEmpty()) {
            return false;
        }

        if (insert_order->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_BUY) {
            if (insert_order->orderDetail.limitPrice >= hang_info_list->getMinPrice()) {
                // 买价>=所有挂单中卖价的最低价
                return true;
            }
        } else {
            if (insert_order->orderDetail.limitPrice <= hang_info_list->getMaxPrice()) {
                // 卖价<=所有挂单中买价的最高价
                return true;
            }
        }

        return false;
    }

    void handleDeleteOrder(
            const std::shared_ptr<OrderFieldType>& order_field) {
        if (!backend_->isOrderSupported(order_field)) {
            return ;
        }

        std::string contractKey = backend_->getOrderContractKey(order_field);
        CHECK(!contractKey.empty());

        std::shared_ptr<IndexedOrderInfoMap> indexed_order_info_map;
        auto indexed_order_info_map_it = indexed_all_order_infos_.find(contractKey);
        if (indexed_order_info_map_it == indexed_all_order_infos_.end()) {
           return ;
        }
        indexed_order_info_map = indexed_order_info_map_it->second;

        std::string order_ref_key = backend_->getOrderRefKey(order_field);
        std::string order_dealid_key = backend_->getOrderDealIDKey(order_field);

        if (order_ref_key.empty() && order_dealid_key.empty()) {
            APPLOG_WARN("[WARNING]Unexpected {}, does not have any key", backend_->orderFieldTypeName());
            return ;
        }

        std::shared_ptr<OrderInfo> delete_order_info;
        if (!order_ref_key.empty()) {
            auto ref_it = indexed_order_info_map->ref_orders.find(order_ref_key);
            if (ref_it != indexed_order_info_map->ref_orders.end()) {
                delete_order_info = ref_it->second;
                indexed_order_info_map->ref_orders.erase(ref_it);
            }
        }
        if (!order_dealid_key.empty()) {
            auto dealid_it = indexed_order_info_map->dealid_orders.find(order_dealid_key);
            if (dealid_it == indexed_order_info_map->dealid_orders.end()) {
                delete_order_info = dealid_it->second;
                indexed_order_info_map->dealid_orders.erase(dealid_it);
            }
        }

        if (!delete_order_info || !delete_order_info->in_hang_order_infos) {
            return ;
        }
        removeHangOrder(delete_order_info);
    }

    void handleOrderInsertCanceled(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_cancelled_order) {
        handleDeleteOrder(backend_->createDummyOrder(insert_cancelled_order));
    }

    enum Status {
        NONE,
        IN_INITED,
        INIT_FINISHED,
    };
    Status state_ = {Status::NONE};

    // 缓存最近发送的订单引用，包含不成功
    soldier::base::LruCache<std::string, int64_t> recent_send_orderrefs_;

    std::shared_ptr<soldier::base::TaskThread> work_thread_;
    std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>> backend_;
    std::queue<std::shared_ptr<OrderFieldType>> waiting_orders_;

    // key为ContractKey
    std::map<std::string, std::shared_ptr<IndexedOrderInfoMap>> indexed_all_order_infos_; // 所有的订单记录
    std::map<std::string, std::shared_ptr<HangOrderInfoList>> hang_order_infos_; // 所有挂单记录
};


template<class OrderFieldType>
class SelfMatchOrderExpiredCleaner {
public:
    SelfMatchOrderExpiredCleaner(
            const std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>>& backend
            , const std::shared_ptr<SelfMatchManager<OrderFieldType>>& manager
            )
        : backend_(backend)
          , manager_(manager)
          , work_thread_(new soldier::base::TaskThread()){
    }
    virtual ~SelfMatchOrderExpiredCleaner() = default;

    void startWorking(int delay_seconds) {
        delay_seconds_ = delay_seconds;
        work_thread_->postTask(&SelfMatchOrderExpiredCleaner::startCleanLoop, this);
    }

    void handleQryAllOrdersResp(int error_code
            , std::string error_msg
            , const std::vector<std::shared_ptr<OrderFieldType>>& all_orders) {
        CHECK(work_thread_->isRunningInTaskThread());

        APPLOG_INFO("handleQryAllOrdersResp error_code={}, error_msg={}"
                , error_code
                , error_msg);
        if (error_code != 0) {
            work_thread_->postTask(&SelfMatchOrderExpiredCleaner::cleanOnce, this);
            return ;
        }

        std::map<std::string, std::shared_ptr<OrderFieldType>> indexed_orders;
        for (auto order_it = all_orders.begin(); order_it != all_orders.end(); ++order_it){
            std::string dealid_key = backend_->getOrderDealIDKey(*order_it);
            if (dealid_key.empty()) {
                APPLOG_WARN("[WARNING]handleQryAllOrdersResp found none dealid orders in response!");
                continue;
            }
            indexed_orders[dealid_key] = *order_it;
        }

        for (auto check_order_it = check_order_fields_.begin();
                check_order_it != check_order_fields_.end(); ++check_order_it) {
            std::string check_dealid_key = backend_->getOrderDealIDKey(*check_order_it);
            if (check_dealid_key.empty()) {
                APPLOG_WARN("[WARNING]handleQryAllOrdersResp found none dealid orders in check orders!");
                continue;
            }

            if (indexed_orders.find(check_dealid_key) == indexed_orders.end()){
                APPLOG_WARN("[NOTICE]Order Expired for dealid_key={}, order_field={}"
                        , check_dealid_key, backend_->dumpOrderField(*check_order_it));
                manager_->deleteOrder(*check_order_it);
            } else {
                APPLOG_INFO("Order Not Expired, dealid_key={}, order_field={}"
                        , check_dealid_key, backend_->dumpOrderField(*check_order_it));
            }
        }

        check_order_fields_.clear();
        startCleanLoop();
    }

    virtual int startQryAllOrders(const std::shared_ptr<soldier::base::TaskThread>& callback_thread) = 0;

private:
    void startCleanLoop() {
        std::this_thread::sleep_for(std::chrono::seconds(delay_seconds_));
        cleanOnce();
    }

    void cleanOnce() {
        check_order_fields_.clear();
        manager_->copyOrdersHasDealID(check_order_fields_);
        int ret = 0;
        do {
            ret = startQryAllOrders(work_thread_);
            if (ret != 0) {
                std::this_thread::sleep_for(std::chrono::milliseconds(50));
            }
        } while(ret != 0);
    }

    enum Status {
        NOT_WORKING = 0,
        WORKING = 1
    };

    std::shared_ptr<SelfMatchManagerBackend<OrderFieldType>> backend_;
    std::shared_ptr<SelfMatchManager<OrderFieldType>> manager_;
    std::shared_ptr<soldier::base::TaskThread> work_thread_;

    std::list<std::shared_ptr<OrderFieldType>> check_order_fields_;
    int delay_seconds_ = 10*60;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao





#endif /* SRC_SUBPROCESS_COMMON_SELF_MATCH_MANAGER_H_ */
