/*
 * es9_deal_manager.cpp
 *
 *  Created on: 2018年4月16日
 *      Author: 44385
 */
#include "es9_deal_manager.h"

#include <boost/format.hpp>
#include <boost/lexical_cast.hpp>
#include <string.h>
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "errorinfo_helper.h"
#include "es9_self_match_manager_backend.h"
#include "hosting_message_sender.h"
#include "order_validation.h"
#include "trade_hosting_basic_errors_types.h"
#include "trade_hosting_basic_events_types.h"
#include "ContractConvertor.h"
#include "iTapAPIError.h"


using namespace apache::thrift;
using namespace es9ext::framework;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::events;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9DealManager::Es9DealManager(
        const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher
        , const std::shared_ptr<Es9LoginManager>& login_manager
        , const std::shared_ptr<Es9DataManager>& data_manager)
    : Es9OrderInsertProcessor(request_dispatcher)
      , Es9OrderDeleteProcessor(request_dispatcher)
      ,request_dispatcher_(request_dispatcher)
      , login_manager_(login_manager)
      , data_manager_(data_manager)
      , init_timestamp_us_(std::chrono::duration_cast<std::chrono::microseconds>(
              std::chrono::system_clock::now().time_since_epoch()).count()) {
    std::shared_ptr<SelfMatchManagerBackend<ITapTrade::TapAPIOrderInfo>> self_match_backend(new Es9SelfMatchManagerBackend());
    self_match_manager_.reset(new SelfMatchManager<ITapTrade::TapAPIOrderInfo>(self_match_backend));

    self_match_cleaner_.reset(new Es9SelfMatchOrderExpiredCleaner(self_match_backend
            , self_match_manager_, request_dispatcher));

    self_match_cleaner_->startWorking(900);
}

Es9DealManager::~Es9DealManager() {
}

void Es9DealManager::checkAccountSummaryValid(const HostingExecOrderTradeAccountSummary& accountSummary) throw(ErrorInfo) {
    // 账号信息匹配性检测
    CHECK_PARAM_ERRORINFO(accountSummary.tradeAccountId == login_manager_->getTradeAccount()->tradeAccountId);
    CHECK_PARAM_ERRORINFO(accountSummary.brokerId == login_manager_->getTradeAccount()->tradeBrokerId);
    CHECK_PARAM_ERRORINFO(accountSummary.techPlatform == BrokerTechPlatform::TECH_ESUNNY_9);
}

void Es9DealManager::checkOrderInputExtContractSummary(const ESunny9OrderInputExt& es9InputExt) throw(ErrorInfo) {
    CHECK_PARAM_ERRORINFO(es9InputExt.__isset.contractSummary);
    CHECK_PARAM_ERRORINFO(es9InputExt.contractSummary.__isset.esunny9ExchangeNo && !es9InputExt.contractSummary.esunny9ExchangeNo.empty());
    CHECK_PARAM_ERRORINFO(es9InputExt.contractSummary.__isset.esunny9CommodityType && es9InputExt.contractSummary.esunny9CommodityType > 0)
    CHECK_PARAM_ERRORINFO(es9InputExt.contractSummary.__isset.esunny9CommodityNo && !es9InputExt.contractSummary.esunny9CommodityNo.empty());
    CHECK_PARAM_ERRORINFO(es9InputExt.contractSummary.__isset.esunny9ContractNo && !es9InputExt.contractSummary.esunny9ContractNo.empty());
}

void Es9DealManager::checkOrderInputExt(const ESunny9OrderInputExt& es9InputExt) throw(ErrorInfo) {
    checkOrderInputExtContractSummary(es9InputExt);
}

void Es9DealManager::checkOrderRef(const ESunny9OrderRef& es9Ref) throw(ErrorInfo) {
    CHECK_PARAM_ERRORINFO(es9Ref.__isset.refString && !es9Ref.refString.empty());
}

void Es9DealManager::allocOrderRef(HostingExecOrderRef& _return) throw(ErrorInfo) {
    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    boost::format order_ref_format("XQ%x_%x_%x");
    order_ref_format%login_manager_->getTradeAccount()->tradeAccountId%init_timestamp_us_%last_order_ref_id_.fetch_add(1);
    _return.__isset.esunny9Ref  = true;
    _return.esunny9Ref.__set_refString(order_ref_format.str());
}

void Es9DealManager::orderInsert(const HostingExecOrder& insert_order) throw(ErrorInfo) {
    OrderValidation::checkInsertOrderStandard(insert_order);
    checkAccountSummaryValid(insert_order.accountSummary);

    CHECK_PARAM_ERRORINFO(insert_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(insert_order.orderInputExt.__isset.esunny9InputExt);
    checkOrderInputExt(insert_order.orderInputExt.esunny9InputExt);

    CHECK_PARAM_ERRORINFO(insert_order.upsideOrderRef.__isset.esunny9Ref);
    checkOrderRef(insert_order.upsideOrderRef.esunny9Ref);

    if (insert_order.orderDetail.orderType == HostingExecOrderType::ORDER_WITH_CONDITION) {
        CHECK_PARAM_ERRORINFO(SUPPORTED_ESUNNY9_ORDER_CONDITION_PRICETYPES.find(insert_order.orderDetail.condition)
                != SUPPORTED_ESUNNY9_ORDER_CONDITION_PRICETYPES.end());
    }

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    if (login_rsp_data->account_infos.empty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO
                , "no account number");
    }

    std::shared_ptr<HostingExecOrder> insert_order_s(new HostingExecOrder(insert_order));
    {
//        BLOCK_TIME_PRINT("Es9DealManager::hasSelfMatch")
        bool has_self_match = false;
        self_match_manager_->orderInsertPrepare(insert_order_s, has_self_match);
        if (has_self_match) {
            ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_SELF_MATCH, "self match");
        }
    }

    addInsertOrder(insert_order_s);
    data_manager_->getOrderMappingManager().setExecOrderId(insert_order.upsideOrderRef, insert_order.execOrderId);
}

std::string Es9DealManager::chooseAccountNo(
        const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& insert_order) {
    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    CHECK(login_rsp_data);
    CHECK(!login_rsp_data->account_infos.empty());
    return login_rsp_data->account_infos[0].AccountNo;
}

void Es9DealManager::handleOrderInsertResp(const std::shared_ptr<Es9OrderInsertResp>& resp) {
    APPLOG_INFO("handleOrderInsertResp errorCode={}, errorMsg={}", resp->getErrorCode(), resp->getUtf8ErrorMsg());
    if (resp->getErrorCode() == 0 || resp->hasTimeOut()) {
        return ;
    }

    self_match_manager_->orderInsertCanceled(resp->insert_order);

    SEND_MESSAGE_BEGIN(UpsideOrderInsertFailedEvent, failed_event)
    failed_event.__set_execOrderId(resp->insert_order->execOrderId);
    failed_event.__set_upsideErrorCode(resp->getErrorCode());
    failed_event.__set_upsideErrorMsg(resp->getUtf8ErrorMsg());
    failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER);
    failed_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
    SEND_MESSAGE_END(failed_event)
}

void Es9DealManager::orderDelete(const HostingExecOrder& delete_order) throw(ErrorInfo) {
    OrderValidation::checkDeleteOrderStandard(delete_order);
    checkAccountSummaryValid(delete_order.accountSummary);

    CHECK_PARAM_ERRORINFO(delete_order.dealInfo.dealId.esunny9DealId.__isset.orderNo);
    CHECK_PARAM_ERRORINFO(!delete_order.dealInfo.dealId.esunny9DealId.orderNo.empty());

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    addDeleteOrder(std::shared_ptr<HostingExecOrder>(new HostingExecOrder(delete_order)));
}

void Es9DealManager::handleOrderDeleteResp(const std::shared_ptr<Es9OrderDeleteResp>& resp) {
    APPLOG_INFO("handleOrderDeleteResp errorCode={}, errorMsg={}", resp->getErrorCode(), resp->getUtf8ErrorMsg());
    if (resp->getErrorCode() == 0 || resp->hasTimeOut()) {
        return ;
    }

    sendOrderDeleteFailedEvent(resp->delete_order->execOrderId, resp->getErrorCode(), resp->getUtf8ErrorMsg());
}

void Es9DealManager::syncOrderState(const HostingExecOrder& sync_order) throw(ErrorInfo) {
    OrderValidation::checkInsertOrderStandard(sync_order);
    checkAccountSummaryValid(sync_order.accountSummary);

    CHECK_PARAM_ERRORINFO(sync_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(sync_order.orderInputExt.__isset.esunny9InputExt);
    checkOrderInputExtContractSummary(sync_order.orderInputExt.esunny9InputExt);

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<OrderInsertProcessInfo> insert_process_info = getInsertProcessInfo(sync_order.execOrderId);
    CHECK(insert_process_info);

    int64_t now_timestampms = NowInMilliSeconds();
    APPLOG_INFO("syncOrderState OrderInsertProcessInfo exec_order_id={}, waiting={}, sending_timestampms={}, sending_time={}.{}"
            ", now_timestampms={}, now_time={}.{}"
            , sync_order.execOrderId, insert_process_info->waiting, insert_process_info->sending_timestampms
            , Timestamp2String((int32_t)(insert_process_info->sending_timestampms/1000), "%Y-%m-%d %H:%M:%S")
            , insert_process_info->sending_timestampms%1000
            , now_timestampms
            , Timestamp2String((int32_t)(now_timestampms/1000), "%Y-%m-%d %H:%M:%S")
            , now_timestampms%1000);
    if (insert_process_info->waiting) {
        APPLOG_INFO("syncOrderState exec_order_id={} called, but order is waiting sending...", sync_order.execOrderId);
        return ;
    }
    if (now_timestampms <= insert_process_info->sending_timestampms + 2500) {
        // 查询间隔距离时间太短，不便于实际的落地情况查询，丢弃这次请求
        // 同时用WarnLog记录一次这个事件的发送，发送和查询的时间必须间隔一定时间
        APPLOG_WARN("[NOTICE]syncOrderState exec_order_id={} called, but order is sending period too small"
                , sync_order.execOrderId);
        return ;
    }

    std::shared_ptr<Es9OrderSyncStateHandler> sync_state_handler(
            new Es9OrderSyncStateHandler(
                    Es9OrderSyncStateHandler::CallbackFunction(
                            std::bind(&Es9DealManager::handleSyncOrderStateResp, this, std::placeholders::_1))
                    , std::shared_ptr<HostingExecOrder>(new HostingExecOrder(sync_order))));
    int ret = request_dispatcher_->sendRequest(sync_state_handler);
    APPLOG_INFO("syncOrderState exec_order_id={}, ret={}, errorMsg={}", sync_order.execOrderId, ret, es9ErrorMsg(ret));
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                        , "syncOrderState ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void Es9DealManager::handleSyncOrderStateResp(const std::shared_ptr<Es9OrderSyncStateResp>& resp) {
    APPLOG_INFO("handleSyncOrderStateResp errorCode={}, errorMsg={}, order_infos.size()={}"
            , resp->getErrorCode(), resp->getUtf8ErrorMsg(), resp->order_infos.size());
    if (resp->getErrorCode() != 0) {
        return ;
    }

    try {
        bool order_founded = false;
        for (int index = 0; index < (int)resp->order_infos.size(); ++index) {
            ITapTrade::TapAPIOrderInfo& order = resp->order_infos[index];

            self_match_manager_->processOrder(
                    std::shared_ptr<ITapTrade::TapAPIOrderInfo>(new ITapTrade::TapAPIOrderInfo(order)));

            int64_t exec_order_id = processOrderSyncState(
                    order, resp->getRequestStartTimestampMs(), resp->sync_order->execOrderId);
            if (exec_order_id == resp->sync_order->execOrderId) {
                order_founded = true;
            }
        }

        if (!resp->sync_order_has_dealinfo && !order_founded) {
            self_match_manager_->orderInsertCanceled(resp->sync_order);

            SEND_MESSAGE_BEGIN(UpsideNotifySyncStateEvent, sync_event);
            sync_event.__set_execOrderId(resp->sync_order->execOrderId);
            sync_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
            sync_event.__set_syncReqTimestampMs(resp->getRequestStartTimestampMs());
            sync_event.__set_syncRespTimestampMs(NowInMilliSeconds());
            sync_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND);
            sync_event.__set_upsideErrorCode(-1);
            sync_event.__set_upsideErrorMsg("订单未找到");
            SEND_MESSAGE_END(sync_event);
            return ;
        }
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("handleSyncOrderStateRsp error occurs, errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

void Es9DealManager::syncOrderTrades(const HostingExecOrder& sync_order) throw(ErrorInfo) {
    OrderValidation::checkSyncOrderTradesStandard(sync_order);
    checkAccountSummaryValid(sync_order.accountSummary);

    CHECK_PARAM_ERRORINFO(sync_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(sync_order.orderInputExt.__isset.esunny9InputExt);
    checkOrderInputExtContractSummary(sync_order.orderInputExt.esunny9InputExt);

    CHECK_PARAM_ERRORINFO(sync_order.__isset.dealInfo);
    CHECK_PARAM_ERRORINFO(sync_order.dealInfo.__isset.dealId);
    CHECK_PARAM_ERRORINFO(sync_order.dealInfo.dealId.__isset.esunny9DealId);
    CHECK_PARAM_ERRORINFO(sync_order.dealInfo.dealId.esunny9DealId.__isset.orderNo);
    CHECK_PARAM_ERRORINFO(!sync_order.dealInfo.dealId.esunny9DealId.orderNo.empty());

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }
    if (login_rsp_data->account_infos.empty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO
                , "no account number");
    }

    std::shared_ptr<Es9OrderSyncTradesHandler> sync_trades_handler(
            new Es9OrderSyncTradesHandler(
                Es9OrderSyncTradesHandler::CallbackFunction(std::bind(&Es9DealManager::handleSyncOrderTradesResp, this, std::placeholders::_1))
                , login_rsp_data->account_infos[0].AccountNo
                , std::shared_ptr<HostingExecOrder>(new HostingExecOrder(sync_order))
            ));
    int ret = request_dispatcher_->sendRequest(sync_trades_handler);
    APPLOG_INFO("syncOrderTrades exec_order_id={}, ret={}, errorMsg={}", sync_order.execOrderId, ret, es9ErrorMsg(ret));
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "syncOrderTrades ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void Es9DealManager::handleSyncOrderTradesResp(const std::shared_ptr<Es9OrderSyncTradesResp>& resp) {
    APPLOG_INFO("handleSyncOrderTradesResp errorCode={}, errorMsg={}, fill_infos.size()={}"
            , resp->getErrorCode(), resp->getUtf8ErrorMsg(), resp->fill_infos.size());
    if (resp->getErrorCode() != 0) {
        return ;
    }

    for (int index = 0; index < (int)resp->fill_infos.size(); ++index) {
        const ITapTrade::TapAPIFillInfo& fill_info = resp->fill_infos[index];
        try {
            processFillSync(fill_info, resp->getRequestStartTimestampMs());
        } catch (ErrorInfo& e) {
            APPLOG_ERROR("processFillSync error ocurrs, errorCode={}, errorMsg={}"
                    , e.errorCode, e.errorMsg);
        }
    }
}

void Es9DealManager::syncOrderStateBatch(const TSyncOrderStateBatchReq& batch_req) throw(ErrorInfo) {
    CHECK_PARAM_ERRORINFO(batch_req.__isset.accountSummary);
    OrderValidation::checkAccountSummary(batch_req.accountSummary);
    checkAccountSummaryValid(batch_req.accountSummary);

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<Es9OrderSyncStateBatchHandler> sync_state_batch_handler(
            new Es9OrderSyncStateBatchHandler(
                Es9OrderSyncStateBatchHandler::CallbackFunction(
                        std::bind(&Es9DealManager::handleSyncOrderStateBatchResp, this, std::placeholders::_1))
                , std::shared_ptr<TSyncOrderStateBatchReq>(new TSyncOrderStateBatchReq(batch_req))
            ));
    int ret = request_dispatcher_->sendRequest(sync_state_batch_handler);
    APPLOG_INFO("syncOrderStateBatch ret={}, errorMsg={}", ret, es9ErrorMsg(ret));
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "syncOrderStateBatch ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void Es9DealManager::handleSyncOrderStateBatchResp(const std::shared_ptr<Es9OrderSyncStateBatchResp>& resp) {
    APPLOG_INFO("handleSyncOrderStateBatchResp errorCode={}, errorMsg={}", resp->getErrorCode(), resp->getUtf8ErrorMsg());
    if (resp->getErrorCode() != 0) {
        return ;
    }

    syncOrders(resp->order_infos, resp->getRequestStartTimestampMs(), true);
}

void Es9DealManager::onEs9DataManagerInitStart() {
    self_match_manager_->onInitStart();
}

void Es9DealManager::onEs9QryOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp) {
    syncOrders(all_orders_resp->order_infos, all_orders_resp->getRequestStartTimestampMs(), false);

    std::shared_ptr<std::vector<std::shared_ptr<ITapTrade::TapAPIOrderInfo>>>
                all_order_fields(new std::vector<std::shared_ptr<ITapTrade::TapAPIOrderInfo>>());
    all_order_fields->reserve(all_orders_resp->order_infos.size() + 1);
    for (auto it = all_orders_resp->order_infos.begin(); it != all_orders_resp->order_infos.end(); ++it) {
        all_order_fields->push_back(std::shared_ptr<ITapTrade::TapAPIOrderInfo>(new ITapTrade::TapAPIOrderInfo(*it)));
    }
    self_match_manager_->onInitAllOrders(all_order_fields);
}

void Es9DealManager::onEs9DataManagerInitFinished() {
    self_match_manager_->onInitFinished();
}

int64_t Es9DealManager::processOrderSyncState(
        const ITapTrade::TapAPIOrderInfo& order_info
        , long sync_start_timestampms
        , long send_msg_exec_order_id) throw(::platform::comm::ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByOrderField(order_info, __FUNCTION__);
    if (exec_order_id <= 0) {
        std::shared_ptr<HostingExecOrder> exec_order = processNotFoundOrder(order_info);
        if (exec_order) {
            exec_order_id = exec_order->execOrderId;
        }
    }

    if (exec_order_id > 0 ) {
        if (send_msg_exec_order_id < 0
                || send_msg_exec_order_id == exec_order_id) {
            SEND_MESSAGE_BEGIN(UpsideNotifySyncStateEvent, sync_event)
            sync_event.__set_execOrderId(exec_order_id);
            sync_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
            sync_event.__set_syncReqTimestampMs(sync_start_timestampms);
            sync_event.__set_syncRespTimestampMs(NowInMilliSeconds());

            if (order2NotifyStateInfo(order_info, exec_order_id, sync_event.syncStateInfo)) {
                sync_event.__isset.syncStateInfo = true;
                SEND_MESSAGE_END(sync_event);
            }
        }
    }

    return exec_order_id;
}

void Es9DealManager::processFillSync(
        const ITapTrade::TapAPIFillInfo& fill_info
        , long sync_start_timestampms) throw(ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByFillField(fill_info, __FUNCTION__);
    if (exec_order_id <= 0) {
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifySyncTradeEvent, sync_trade_event);
    sync_trade_event.__set_execOrderId(exec_order_id);
    sync_trade_event.__set_syncReqTimestampMs(sync_start_timestampms);
    sync_trade_event.__set_syncRespTimestampMs(NowInMilliSeconds());
    sync_trade_event.__set_eventCreateTimestampMs(NowInMilliSeconds());

    HostingExecTradeLegInfo trade_leg_info;
    if (fill2TradeLegInfo(fill_info, trade_leg_info)) {
        sync_trade_event.syncTradeLegInfos.push_back(trade_leg_info);
        sync_trade_event.__isset.syncTradeLegInfos = true;
    } else {
        return ;
    }

    if (fill2TradeLegContractSummary(fill_info, sync_trade_event.syncTradeLegContractSummary)) {
        sync_trade_event.__isset.syncTradeLegContractSummary = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(sync_trade_event);
}

void Es9DealManager::OnRtnOrder(const ITapTrade::TapAPIOrderInfoNotice *info) {
    APPLOG_INFO("OnRtnOrder SessionID={}, errorCode={}, errorMsg={}"
            , info->SessionID, info->ErrorCode, es9ErrorMsg(info->ErrorCode));

    if (!info->OrderInfo) {
        if (info->ErrorCode == 0) {
            APPLOG_WARN("[WARNING]OnRtnOrder Called, ErrorCode=0, But OrderInfo is Null");
        }
        return ;
    }

    try {
        if (info->ErrorCode == ITapTrade::TAPIERROR_ORDERDELETE_NOT_SYSNO
            || info->ErrorCode == ITapTrade::TAPIERROR_ORDERDELETE_NOT_STATE
            || info->ErrorCode == ITapTrade::TAPIERROR_ORDERDELETE_NO_INPUT) {
            // 易胜的交易所撤单错误
            int64_t exec_order_id = getExecOrderIdByOrderField(*(info->OrderInfo), __FUNCTION__);
            if (exec_order_id > 0) {
                sendOrderDeleteFailedEvent(exec_order_id, info->ErrorCode, es9ErrorMsg(info->ErrorCode));
            }

            return ;
        }

        self_match_manager_->processOrder(
                std::shared_ptr<ITapTrade::TapAPIOrderInfo>(new ITapTrade::TapAPIOrderInfo(*(info->OrderInfo))));
        onRtnOrderProcess(*(info->OrderInfo));
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("onRtnOrderProcess error! errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

void Es9DealManager::onRtnOrderProcess(const ITapTrade::TapAPIOrderInfo& order_info) throw(ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByOrderField(order_info, __FUNCTION__);
    if (exec_order_id <= 0) {
        processNotFoundOrder(order_info);
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifyForwardStateEvent, state_event)
    state_event.__set_execOrderId(exec_order_id);
    state_event.__set_receivedTimestampMs(NowInMilliSeconds());
    state_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
    if (order2NotifyStateInfo(order_info, exec_order_id, state_event.forwardStateInfo)) {
        state_event.__isset.forwardStateInfo = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(state_event)

}

void Es9DealManager::OnRtnFill(const ITapTrade::TapAPIFillInfo *info) {
    if (!info) {
        APPLOG_WARN("[WARNING]OnRtnFill Called, but info is empty");
        return ;
    }

    try {
        onRtnFillProcess(*info);
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("onRtnTradeProcess error! errorCode={}, errorMsg={}", e.errorCode, e.errorMsg);
    }
}

void Es9DealManager::onRtnFillProcess(const ITapTrade::TapAPIFillInfo& fill_info) throw(ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByFillField(fill_info, __FUNCTION__);
    if (exec_order_id <= 0) {
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifyForwardTradeEvent, trade_event)
    trade_event.__set_execOrderId(exec_order_id);
    trade_event.__set_receivedTimestampMs(NowInMilliSeconds());
    trade_event.__set_eventCreateTimestampMs(NowInMilliSeconds());

    if (fill2TradeLegInfo(fill_info, trade_event.forwardTradeLegInfo)) {
        trade_event.__isset.forwardTradeLegInfo = true;
    } else {
        return ;
    }

    if (fill2TradeLegContractSummary(fill_info, trade_event.forwardTradeLegContractSummary)) {
        trade_event.__isset.forwardTradeLegContractSummary = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(trade_event)
}


int64_t Es9DealManager::getExecOrderIdByOrderField(
        const ITapTrade::TapAPIOrderInfo& order_info
        , const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = 0;

    if (order_info.RefString[0] != 0) {
        HostingExecOrderRef order_ref;
        order_ref.__isset.esunny9Ref = true;
        order_ref.esunny9Ref.__set_refString(order_info.RefString);
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(order_ref);
    }

    if (exec_order_id <= 0) {
        HostingExecOrderDealID deal_id;
        deal_id.__isset.esunny9DealId = true;
        deal_id.esunny9DealId.__set_orderNo(order_info.OrderNo);
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);
    }

    APPLOG_INFO("{} AccountNo={}, ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}, ContractNo2={}, OrderType={}"
               ", OrderSide={}, OrderQty={}, OrderPrice={}, RefString={}, ServerFlag={}, OrderNo={}, ClientOrderNo={}"
               ", OrderInsertTime={}, OrderUpdateTime={}, OrderState={}, OrderMatchPrice={}, OrderMatchQty={}"
               ", OrderMatchPrice2={}, OrderMatchQty2={}, OrderInfoErrorCode={}, OrderInfoErrorMsg={}, IsAddOne={}"
               " -> exec_order_id={}", call_function
               , order_info.AccountNo, order_info.ExchangeNo, order_info.CommodityType
               , order_info.CommodityNo, order_info.ContractNo
               , order_info.ContractNo2, order_info.OrderType, order_info.OrderSide, order_info.OrderQty
               , order_info.OrderPrice, order_info.RefString, order_info.ServerFlag, order_info.OrderNo, order_info.ClientOrderNo
               , order_info.OrderInsertTime, order_info.OrderUpdateTime, order_info.OrderState
               , order_info.OrderMatchPrice, order_info.OrderMatchQty
               , order_info.OrderMatchPrice2, order_info.OrderMatchPrice2
               , order_info.ErrorCode, es9UsefulErrorText(order_info.ErrorText), order_info.IsAddOne
               , exec_order_id);

    return exec_order_id;
}

int64_t Es9DealManager::getExecOrderIdByFillField(
        const ITapTrade::TapAPIFillInfo& fill_info
        , const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = 0;

    HostingExecOrderDealID  deal_id;
    deal_id.__isset.esunny9DealId = true;
    deal_id.esunny9DealId.__set_orderNo(fill_info.OrderNo);
    exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);

    APPLOG_INFO(
            "{} AccountNo={}, ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}"
            ", MatchSide={}, PositionEffect={}, OrderNo={}, MatchNo={}, MatchDateTime={}"
            ", MatchPrice={}, MatchQty={}, IsDeleted={}, IsAddOne={}"
            ", FeeCurrencyGroup={}, FeeCurrency={}, FeeValue={}, IsManualFee={}, ClosePrositionPrice={}"
            " -> exec_order_id={}", call_function
            , fill_info.AccountNo, fill_info.ExchangeNo, fill_info.CommodityType, fill_info.CommodityNo, fill_info.ContractNo
            , fill_info.MatchSide, fill_info.PositionEffect, fill_info.OrderNo, fill_info.MatchNo, fill_info.MatchDateTime
            , fill_info.MatchPrice, fill_info.MatchQty, fill_info.IsDeleted, fill_info.IsAddOne
            , fill_info.FeeCurrencyGroup, fill_info.FeeCurrency, fill_info.FeeValue, fill_info.IsManualFee, fill_info.ClosePrositionPrice
            , exec_order_id);

    return exec_order_id;
}

std::shared_ptr<HostingExecOrder> Es9DealManager::processNotFoundOrder(const ITapTrade::TapAPIOrderInfo& order_info) throw(ErrorInfo) {
    return std::shared_ptr<HostingExecOrder>();
}

bool Es9DealManager::order2NotifyStateInfo(
        const ITapTrade::TapAPIOrderInfo& order_info
        , int64_t exec_order_id
        , ::xueqiao::trade::hosting::HostingUpsideNotifyStateInfo& state_info) {
    state_info.__isset.dealInfo = true;
    if (order_info.OrderNo[0] != 0) {
        state_info.dealInfo.__isset.dealId = true;
        state_info.dealInfo.dealId.__isset.esunny9DealId = true;
        state_info.dealInfo.dealId.esunny9DealId.__set_orderNo(order_info.OrderNo);
        data_manager_->getOrderMappingManager().setExecOrderId(state_info.dealInfo.dealId, exec_order_id);
    }

    std::string insert_date_time = es9HDateTime2StandardDateTime(order_info.OrderInsertTime);
    if (!insert_date_time.empty()) {
        state_info.dealInfo.__set_orderInsertDateTime(insert_date_time);
    }

    state_info.dealInfo.__isset.esunny9DealInfo = true;
    state_info.dealInfo.esunny9DealInfo.__set_serverFlag(order_info.ServerFlag);
    state_info.dealInfo.esunny9DealInfo.__set_isAddOne(order_info.IsAddOne);

    // 以第一条腿成交量为基准
    state_info.__set_volumeTraded(order_info.OrderMatchQty);
    state_info.__set_volumeResting(order_info.OrderQty - order_info.OrderMatchQty);
    state_info.__set_tradeAveragePrice(order_info.OrderMatchPrice);
    if (order_info.OrderMatchQty2 > 0 && order_info.OrderMatchQty == order_info.OrderMatchQty2) {
        // 上手给出的组合平均成交均价，目前为先手腿减去后手腿
        state_info.__set_tradeAveragePrice(order_info.OrderMatchPrice - order_info.OrderMatchPrice2);
    }
    if (order_info.ErrorCode != 0) {
        state_info.__set_statusUsefulMsg(es9UsefulErrorText(order_info.ErrorText));
    }

    if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_ACCEPT) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_UPSIDE_RECIVED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_TRIGGERING) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_CONDITION_NOT_TRIGGERED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_EXCTRIGGERING) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_CONDITION_TRIGGERED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_FINISHED) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_FINISHED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_CANCELED
            || order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_LEFTDELETED
            || order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_DELETED) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_CANCELLED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_PARTFINISHED) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_PARTFINISHED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_QUEUED
            || order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_SUPPENDED) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_RESTING);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_FAIL) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_REJECTED);
    } else if (order_info.OrderState == ITapTrade::TAPI_ORDER_STATE_CANCELING) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_CANCEL_RECEIVED);
    } else {
        APPLOG_WARN("[WARNING]status={} can not mapping", order_info.OrderState);
        return false;
    }

    return true;
}

bool Es9DealManager::fill2TradeLegInfo(
        const ITapTrade::TapAPIFillInfo& fill_info
        , HostingExecTradeLegInfo& trade_leg_info ) {
    if (fill_info.MatchNo[0] == 0) {
        APPLOG_WARN("[WARNING]fill2TradeLegInfo fill_info's matchNo is empty!");
        return false;
    }
    if (std::isnan(fill_info.MatchPrice) || std::isinf(fill_info.MatchPrice)) {
        APPLOG_WARN("[WARNING]fill2TradeLegInfo fill_info's MatchPrice is not a valid num, MatchNo={}"
                , fill_info.MatchNo);
        return false;
    }
    if (fill_info.MatchQty <= 0) {
        APPLOG_WARN("[WARNING]fill2TradeLegInfo fill_info's MatchQty={} is not valid, MatchNo={}"
               , fill_info.MatchQty, fill_info.MatchNo);
        return false;
    }

    trade_leg_info.__isset.legUpsideTradeId = true;
    trade_leg_info.legUpsideTradeId.__isset.esunny9TradeId = true;
    trade_leg_info.legUpsideTradeId.esunny9TradeId.__set_matchNo(fill_info.MatchNo);
    trade_leg_info.__set_legTradePrice(fill_info.MatchPrice);
    trade_leg_info.__set_legTradeVolume(fill_info.MatchQty);

    if (fill_info.MatchSide == ITapTrade::TAPI_SIDE_BUY) {
        trade_leg_info.__set_legUpsideTradeDirection(HostingExecTradeDirection::TRADE_BUY);
    } else if (fill_info.MatchSide == ITapTrade::TAPI_SIDE_SELL){
        trade_leg_info.__set_legUpsideTradeDirection(HostingExecTradeDirection::TRADE_SELL);
    } else {
        APPLOG_WARN("[WARNING]fill2TradeLegInfo fill_info's MatchSide can not recognise!");
        return false;
    }

    std::string trade_datetime = fill_info.MatchDateTime;
    if (!trade_datetime.empty()) {
        trade_leg_info.__set_legTradeDateTime(trade_datetime);
    }
    return true;
}

bool Es9DealManager::fill2TradeLegContractSummary(
        const ITapTrade::TapAPIFillInfo& fill_info
        , HostingExecOrderLegContractSummary& leg_contract_summary) {
    CommodityMappingOption mapping_option;
    mapping_option.sledBrokerId = 0;
    mapping_option.brokerExchangeCode = fill_info.ExchangeNo;
    mapping_option.brokerCommodityType.append(1, fill_info.CommodityType);
    mapping_option.brokerCommodityCode = fill_info.CommodityNo;
    mapping_option.brokerTechPlatform = ::xueqiao::contract::standard::TechPlatform::ESUNNY;

    std::shared_ptr<CommodityMappingEntry> mapping_entry;
    mapping_entry = data_manager_->getContractMapping().getCommodityMapping(mapping_option);
    if (!mapping_entry) {
        APPLOG_WARN("[WARNING]Failed to found commodity mapping for ExchangeNo={}, CommodityType={}, CommodityNo={}"
                    , fill_info.ExchangeNo
                    , fill_info.CommodityType
                    , fill_info.CommodityNo);
        return false;
    }

    // 获取合约代码
    TechPlatformContractToSledArgs args;
    TechPlatformContractToSledResult result;
    memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
    memset(&result, 0, sizeof(TechPlatformContractToSledResult));

    strcpy(args.CommonContract_.TechPlatform_Exchange_, fill_info.ExchangeNo);
    args.CommonContract_.TechPlatform_CommodityType_[0] = fill_info.CommodityType;
    strcpy(args.CommonContract_.TechPlatform_CommodityCode_, fill_info.CommodityNo);
    strcpy(args.CommonContract_.TechPlatform_ContractCode_, fill_info.ContractNo);
    args.TechPlatform_ = TechPlatform_ESUNNY;
    result = PlatformToSledContract(args);

    if (0 == result.MixContract_.SledContractCode_[0]) {
        APPLOG_WARN("[WARNING]Failed to mapping contract code for ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}"
                    , fill_info.ExchangeNo, fill_info.CommodityType
                    , fill_info.CommodityNo, fill_info.ContractNo);
        return false;
    }

    APPLOG_INFO("Contract ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}"
                "-> mappingID={}, sledCommodityId={}, sledExchangeCode={}"
                ", sledCommodityType={}, sledCommodityCode={}, sledContractCode={}"
                , fill_info.ExchangeNo, fill_info.CommodityType
                , fill_info.CommodityNo, fill_info.ContractNo
                , mapping_entry->mapping.mappingId, mapping_entry->mapping.sledCommodityId
                , mapping_entry->commodity.exchangeMic, mapping_entry->commodity.sledCommodityType
                , mapping_entry->commodity.sledCommodityCode
                , result.MixContract_.SledContractCode_);

    leg_contract_summary.__set_legSledContractCode(result.MixContract_.SledContractCode_);
    leg_contract_summary.__set_legSledCommodityId(mapping_entry->mapping.sledCommodityId);
    leg_contract_summary.__set_legSledCommodityCode(mapping_entry->commodity.sledCommodityCode);
    leg_contract_summary.__set_legSledCommodityType(mapping_entry->commodity.sledCommodityType);
    leg_contract_summary.__set_legSledExchangeMic(mapping_entry->commodity.exchangeMic);

    return true;
}

std::string Es9DealManager::es9HDateTime2StandardDateTime(const char* es9_date_time) {
    // YYMMDDHHmmSS.xxxxxx
    size_t len = strlen(es9_date_time);
    if (len < 13) {
        APPLOG_WARN("esunny9 HDateTime={} is not right! length small", es9_date_time);
        return "";
    }
    if (es9_date_time[12] != '.') {
        APPLOG_WARN("esunny9 HDateTime={} is not right! . is not in 12", es9_date_time);
        return "";
    }

    std::stringstream result_stream;
    result_stream << "20" << es9_date_time[0] << es9_date_time[1]
                  << "-" << es9_date_time[2] << es9_date_time[3]
                  << "-" << es9_date_time[4] << es9_date_time[5]
                  << " " << es9_date_time[6] << es9_date_time[7]
                  << ":" << es9_date_time[8] << es9_date_time[9]
                  << ":" << es9_date_time[10] << es9_date_time[11];
    return result_stream.str();
}

std::string Es9DealManager::es9UsefulErrorText(const char* error_text) const {
    std::string utf8_error_text;
    if (!StringUtil::gbkToUTF8(error_text, utf8_error_text)) {
        return "";
    }

    std::vector<std::string> tokens;
    StringUtil::tokenize(utf8_error_text, tokens, "，", true);
    if (tokens.empty()) {
        return "";
    }
    return tokens.back();
}

void Es9DealManager::sendOrderDeleteFailedEvent(
        int64_t exec_order_id
        , int32_t upside_error_code
        , const std::string& upside_error_msg) {
    SEND_MESSAGE_BEGIN(UpsideOrderDeleteFailedEvent, failed_event)
    failed_event.__set_execOrderId(exec_order_id);
    failed_event.__set_upsideErrorCode(upside_error_code);
    failed_event.__set_upsideErrorMsg(upside_error_msg);
    failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER);
    failed_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
    SEND_MESSAGE_END(failed_event)
}

void Es9DealManager::syncOrders(const std::vector<ITapTrade::TapAPIOrderInfo>& order_infos
        , long sync_start_timestampms
        , bool notify_selfmatch_manager) {
    for (int index = 0; index < (int)order_infos.size(); ++index) {
        const ITapTrade::TapAPIOrderInfo& order_info = order_infos[index];
        try {
            if (notify_selfmatch_manager) {
                self_match_manager_->processOrder(
                        std::shared_ptr<ITapTrade::TapAPIOrderInfo>(new ITapTrade::TapAPIOrderInfo(order_info)));
            }

            processOrderSyncState(order_info, sync_start_timestampms);
        } catch (ErrorInfo& e) {
            APPLOG_ERROR("processOrderSyncState error ocurrs, errorCode={}, errorMsg={}"
                    , e.errorCode, e.errorMsg);
        }
    }
}


