/*
 * es9_request_dispatcher.cpp
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */
#include "es9_request_dispatcher.h"

#include <atomic>
#include <boost/asio.hpp>
#include <boost/timer.hpp>
#include <boost/bind.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "iTapAPIError.h"
#include "effective_reporter.h"

namespace es9ext {
namespace framework {

static boost::asio::io_service g_timer_io_service;
static std::unique_ptr<std::thread> g_timer_thread;

static std::atomic_int S_LAST_REQUEST_ID = {1};
static int createRequestId() {
    return S_LAST_REQUEST_ID.fetch_add(1);
}

Es9RequestDispatcher::Es9RequestDispatcher()
    : dispatcher_thread_(new soldier::base::TaskThread())
      , unsure_requests_(1000)
      , session_id_2_request_ids_(10000) {
    if (!g_timer_thread) {
        static std::mutex g_instance_lock;
        g_instance_lock.lock();
        if (!g_timer_thread) {
                g_timer_thread.reset(new std::thread(&Es9RequestDispatcher::onTimerWorking));
                g_timer_thread->detach();
            }
            g_instance_lock.unlock();
        }
}

Es9RequestDispatcher::~Es9RequestDispatcher() {
    if (trader_api_) {
        FreeITapTradeAPI(trader_api_);
        trader_api_ = nullptr;
    }
}

void Es9RequestDispatcher::onTimerWorking() {
    APPLOG_INFO("Dispatcher Timer start...");
    boost::asio::io_service::work work(g_timer_io_service);
    g_timer_io_service.run();
    APPLOG_INFO("Dispatcher Timer end...");
}

void Es9RequestDispatcher::cancel(int request_id) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    std::shared_ptr<Es9RequestBase> request = getRequestByRequestIdUnSafe(request_id);
    if (!request) {
        return ;
    }
    rmRequest(request);
}

void Es9RequestDispatcher::registerNotifyCallback(const std::shared_ptr<Es9TradeNotifyBase>& notify_callback) {
    notify_callbacks_.push_back(notify_callback);
}

void Es9RequestDispatcher::setTraderApi(ITapTrade::ITapTradeAPI* trader_api) {
    trader_api_ = trader_api;
    trader_api_->SetAPINotify(this);
}

Es9RequestDispatcher::Es9RequestEntry::~Es9RequestEntry() {
    if (timer) {
        timer->cancel();
        timer.reset();
    }
}

void Es9RequestDispatcher::addRequest(const std::shared_ptr<Es9RequestBase>& request) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    std::shared_ptr<Es9RequestEntry> request_entry(new Es9RequestEntry());
    request_entry->request = request;
    if (request->hasActureRsp()) {
        acture_requests_[request->getRequestId()] = request_entry;
        request_entry->timer.reset(new boost::asio::deadline_timer(g_timer_io_service
                       , boost::posix_time::milliseconds(request->timeoutMs())));
        request_entry->timer->async_wait(
                       boost::bind(&Es9RequestDispatcher::onHandleTimeout, this
                               , boost::asio::placeholders::error
                               , request->getRequestId()));

    } else {
        unsure_requests_.put(request->getRequestId(), request_entry);
    }
}

std::shared_ptr<Es9RequestBase> Es9RequestDispatcher::getRequestByRequestIdUnSafe(
        int request_id) {
    auto it = acture_requests_.find(request_id);
    if (it != acture_requests_.end()) {
        return it->second->request;
    }

    const std::shared_ptr<Es9RequestEntry>* p_request = unsure_requests_.get(request_id);
    if (p_request) {
        return (*p_request)->request;
    }

    return std::shared_ptr<Es9RequestBase>();
}

std::shared_ptr<Es9RequestBase> Es9RequestDispatcher::getRequestBySessionId(
        ITapTrade::TAPIUINT32 session_id
        , const std::string& call_function) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    const int* p_request_id = session_id_2_request_ids_.get(session_id);
    if (!p_request_id) {
        APPLOG_INFO("function {} failed to found request(session_id={})", call_function, session_id);
        return std::shared_ptr<Es9RequestBase>();
    }

    std::shared_ptr<Es9RequestBase> request = getRequestByRequestIdUnSafe(*p_request_id);
    if (!request) {
        APPLOG_INFO("function {} failed to found request(session_id={})", call_function, session_id);
    }
    return request;
}

void Es9RequestDispatcher::rmRequest(const std::shared_ptr<Es9RequestBase>& request) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    if (request->hasActureRsp()) {
        acture_requests_.erase(request->getRequestId());
    } else {
        unsure_requests_.remove(request->getRequestId());
    }
    session_id_2_request_ids_.remove(request->getSessionId());
}

int Es9RequestDispatcher::sendRequest(
        const std::shared_ptr<Es9RequestBase>& request) throw(::platform::comm::ErrorInfo) {
    CHECK(request);
    platform::comm::ErrorInfo error_info;
    dispatcher_thread_->postTask(&Es9RequestDispatcher::onHandleRequest, this, request, &error_info);
    request->getOnStartSyncCall().wait();
    if (error_info.errorCode != 0) {
        throw error_info;
    }
    return request->getOnStartRet();
}

void Es9RequestDispatcher::onHandleRequest(const std::shared_ptr<Es9RequestBase>& request
            , ::platform::comm::ErrorInfo* error_info) {
    soldier::base::AutoSyncCallNotify auto_sync_call_notify(request->getOnStartSyncCall());

    int request_id = createRequestId();
    request->setRequestId(request_id);

    addRequest(request);
    try {
        request->setRequestStartTimestampMs(soldier::base::NowInMilliSeconds());

        int ret = 0;
        {
            std::unique_lock<std::mutex> auto_session_request_lock(request_lock_);
            ITapTrade::TAPIUINT32 session_id = 0;
            ret = request->onStart(trader_api_, session_id);
            if (request->hasSessionID() && ret == 0) {
                CHECK(session_id != 0);
                request->setSessionId(session_id);
                session_id_2_request_ids_.put(session_id, request->getRequestId());
            }
        }

        if (ret != 0) {
            rmRequest(request);
            request->setOnStartRet(ret);
            APPLOG_ERROR("Es9Request {}, request_id={} start failed, ret={}, retMsg={}"
                       , request->description()
                       , request->getRequestId()
                       , ret
                       , es9ErrorMsg(ret));
            return ;
        }
        request->setOnStartRet(0);
    } catch (platform::comm::ErrorInfo& e) {
        rmRequest(request);
        error_info->__set_errorCode(e.errorCode);
        error_info->__set_errorMsg(e.errorMsg);
        APPLOG_ERROR("Es9Request {}, request_id={} start failed, errorCode={}, errorMsg={}"
                , request->description()
                , request->getRequestId()
                , e.errorCode
                , e.errorMsg);
        return ;
    }

    APPLOG_INFO("Es9Request {}, request_id={} session_id={} start success!"
               , request->description()
               , request->getRequestId()
               , request->getSessionId());
}

void Es9RequestDispatcher::onHandleTimeout(const boost::system::error_code &ec, int request_id) {
    if (ec) {
        // timer cancelled
        return ;
    }

    std::shared_ptr<Es9RequestBase> request;
    {
        std::unique_lock<std::mutex> auto_lock(request_lock_);
        auto it = acture_requests_.find(request_id);
        if (it == acture_requests_.end()) {
            return ;
        }
        request = it->second->request;
        acture_requests_.erase(request_id);
        session_id_2_request_ids_.remove(request->getSessionId());
    }

    APPLOG_INFO("onHandleTimeOut request_id={}, request={}", request_id, request->description());
    request->onTimeOut();
}


void Es9RequestDispatcher::OnConnect() {
    APPLOG_WARN("[NOTICE]OnConnect...");
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnConnect();
    }
}

void Es9RequestDispatcher::OnRspLogin(ITapTrade::TAPIINT32 errorCode
        , const ITapTrade::TapAPITradeLoginRspInfo *loginRspInfo) {
    if (errorCode == ITapTrade::TAPIERROR_SUCCEED){
        xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();
    }

    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnRspLogin(errorCode, loginRspInfo);
    }
}

void Es9RequestDispatcher::OnExpriationDate(ITapTrade::TAPIDATE date, int days) {
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnExpriationDate(date, days);
    }
}

void Es9RequestDispatcher::OnAPIReady() {
    APPLOG_WARN("[NOTICE]OnAPIReady...");
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnAPIReady();
    }
}

void Es9RequestDispatcher::OnDisconnect(ITapTrade::TAPIINT32 reasonCode) {
    APPLOG_WARN("[NOTICE]OnDisconnect..., reasonCode={}", reasonCode);
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnDisconnect(reasonCode);
    }
}

void Es9RequestDispatcher::OnRtnOrder(const ITapTrade::TapAPIOrderInfoNotice *info) {
    xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnRtnOrder(info);
    }
}

void Es9RequestDispatcher::OnRtnFill(const ITapTrade::TapAPIFillInfo *info) {
    xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnRtnFill(info);
    }
}

#define CALLBACK_IMPL_WITH_LAST(function_name, ...) \
        std::shared_ptr<Es9RequestBase> request = getRequestBySessionId(sessionID, __FUNCTION__); \
        APPLOG_DEBUG("{} Called, sessionID={}, isLast={}, requst={}" \
            , #function_name, sessionID, isLast, (request?request->description() : "")); \
        if (!request) { \
            return ; \
        } \
        if (errorCode == 0) { \
            xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();\
        }\
        request->function_name(__VA_ARGS__); \
        if (isLast == ITapTrade::APIYNFLAG_YES) { \
            rmRequest(request); \
            request->onLastFinished(); \
        }

#define CALLBACK_IMPL_NO_LAST(function_name, ...) \
        std::shared_ptr<Es9RequestBase> request = getRequestBySessionId(sessionID, __FUNCTION__); \
        APPLOG_DEBUG("{} Called, sessionID={}, request={}" \
                , #function_name, sessionID, (request?request->description() : "")); \
        if (!request) { \
            return ; \
        } \
        if (errorCode == 0) { \
            xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();\
        }\
        request->function_name(__VA_ARGS__); \
        rmRequest(request); \
        request->onLastFinished();

void Es9RequestDispatcher::OnRspQryAccount(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIUINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountInfo *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryAccount, sessionID, errorCode, isLast, info)
}

void Es9RequestDispatcher::OnRspOrderAction(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , const ITapTrade::TapAPIOrderActionRsp *info) {
    CALLBACK_IMPL_NO_LAST(OnRspOrderAction, sessionID, errorCode, info)
}

void Es9RequestDispatcher::OnRspQryOrder(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIOrderInfo *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryOrder, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryFill(
        ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIFillInfo *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryFill, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryFund(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIFundData *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryFund, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryBill(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIBillQryRsp *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryBill, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryPosition(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIPositionInfo *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryPosition, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryAccountFeeRent(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountFeeRentQryRsp *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryAccountFeeRent, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryAccountMarginRent(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIAccountMarginRentQryRsp *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryAccountMarginRent, sessionID, errorCode, isLast, info);
}

void Es9RequestDispatcher::OnRspQryCommodity(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPICommodityInfo *info) {
    CALLBACK_IMPL_WITH_LAST(OnRspQryCommodity, sessionID, errorCode, isLast, info);
}

} // end namespace framework
} // end namespace es9ext
