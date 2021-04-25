/*
 * ctp_request_dispatcher.cpp
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */
#include "ctp_request_dispatcher.h"

#include <atomic>
#include <boost/asio.hpp>
#include <boost/timer.hpp>
#include <boost/bind.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include "comm_types.h"
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "effective_reporter.h"


namespace ctpext {
namespace framework {

static boost::asio::io_service g_timer_io_service;
static std::unique_ptr<std::thread> g_timer_thread;

static std::atomic_int S_LAST_REQUEST_ID = {1};
static int createRequestId() {
    return S_LAST_REQUEST_ID.fetch_add(1);
}

CTPRequestDispatcher::CTPRequestDispatcher()
    : dispatcher_thread_(new soldier::base::TaskThread())
      , unsure_requests_(1000){
    if (!g_timer_thread) {
        static std::mutex g_instance_lock;
        g_instance_lock.lock();
        if (!g_timer_thread) {
            g_timer_thread.reset(new std::thread(&CTPRequestDispatcher::onTimerWorking));
            g_timer_thread->detach();
        }
        g_instance_lock.unlock();
    }
}

CTPRequestDispatcher::~CTPRequestDispatcher() {
    if (trader_api_) {
        trader_api_->Release();
        trader_api_ = nullptr;
    }
}

void CTPRequestDispatcher::onTimerWorking() {
    APPLOG_INFO("Dispatcher Timer start...");
    boost::asio::io_service::work work(g_timer_io_service);
    g_timer_io_service.run();
    APPLOG_INFO("Dispatcher Timer end...");
}

void CTPRequestDispatcher::setTraderApi(CThostFtdcTraderApi* trader_api) {
    trader_api_ = trader_api;
    trader_api_->RegisterSpi(this);
}

void CTPRequestDispatcher::cancel(int request_id) {
    std::shared_ptr<CTPRequestBase> request = getRequest(request_id, __FUNCTION__);
    if (!request) {
        return ;
    }
    rmRequest(request);
}

void CTPRequestDispatcher::registerNotifyCallback(const std::shared_ptr<CThostFtdcTraderSpi>& notify_callback) {
    notify_callbacks_.push_back(notify_callback);
}

int CTPRequestDispatcher::sendRequest(const std::shared_ptr<CTPRequestBase>& request) throw(::platform::comm::ErrorInfo){
    CHECK(request);
    platform::comm::ErrorInfo error_info;
    dispatcher_thread_->postTask(&CTPRequestDispatcher::onHandleRequest, this, request, &error_info);
    request->getOnStartSyncCall().wait();
    if (error_info.errorCode != 0) {
        throw error_info;
    }
    return request->getOnStartRet();
}

void CTPRequestDispatcher::onHandleRequest(const std::shared_ptr<CTPRequestBase>& request
        , ::platform::comm::ErrorInfo* error_info) {
    soldier::base::AutoSyncCallNotify auto_sync_call_notify(request->getOnStartSyncCall());

    int request_id = createRequestId();
    request->setRequestId(request_id);

    addRequest(request);
    try {
        request->setRequestStartTimestampMs(soldier::base::NowInMilliSeconds());
        int ret = request->onStart(trader_api_);
        if (ret != 0) {
            rmRequest(request);
            request->setOnStartRet(ret);
            APPLOG_ERROR("CTPRequest {}, request_id={} start failed, ret={}"
                    , request->description()
                    , request->getRequestId()
                    , ret);
            return ;
        }
        request->setOnStartRet(0);
    } catch (platform::comm::ErrorInfo& e) {
        rmRequest(request);
        error_info->__set_errorCode(e.errorCode);
        error_info->__set_errorMsg(e.errorMsg);
        APPLOG_ERROR("CTPRequest {}, request_id={} start failed, errorCode={}, errorMsg={}"
                , request->description()
                , request->getRequestId()
                , e.errorCode
                , e.errorMsg);
        return ;
    }

    APPLOG_INFO("CTPRequest {}, request_id={} start success!"
            , request->description()
            , request->getRequestId());
}

void CTPRequestDispatcher::onHandleTimeout(const boost::system::error_code &ec, int request_id) {
    if (ec) {
        // timer cancelled
        return ;
    }

    std::shared_ptr<CTPRequestBase> request;
    {
        std::unique_lock<std::mutex> auto_lock(request_lock_);
        auto it = acture_requests_.find(request_id);
        if (it == acture_requests_.end()) {
            return ;
        }
        request = it->second->request;
        acture_requests_.erase(request_id);
    }

    APPLOG_INFO("onHandleTimeOut request_id={}, request={}", request_id, request->description());
    request->onTimeOut();
}

CTPRequestDispatcher::RequestEntry::~RequestEntry() {
    if (timer) {
        timer->cancel();
        timer.reset();
    }
}

void CTPRequestDispatcher::addRequest(const std::shared_ptr<CTPRequestBase>& request) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);

    std::shared_ptr<RequestEntry> request_entry(new RequestEntry());
    request_entry->request = request;

    if (request->hasActureRsp()) {
        acture_requests_[request->getRequestId()] = request_entry;
        request_entry->timer.reset(new boost::asio::deadline_timer(g_timer_io_service
                , boost::posix_time::milliseconds(request->timeoutMs())));
        request_entry->timer->async_wait(
                boost::bind(&CTPRequestDispatcher::onHandleTimeout, this
                        , boost::asio::placeholders::error
                        , request->getRequestId()));
    } else {
        unsure_requests_.put(request->getRequestId(), request_entry);
    }
}

std::shared_ptr<CTPRequestBase> CTPRequestDispatcher::getRequest(int request_id
        , const std::string& call_function) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    auto it = acture_requests_.find(request_id);
    if (it != acture_requests_.end()) {
        return it->second->request;
    }

    const std::shared_ptr<RequestEntry>* p_request = unsure_requests_.get(request_id);
    if (p_request) {
        return (*p_request)->request;
    }

    APPLOG_INFO("function {} failed to found request({})", call_function, request_id);
    return std::shared_ptr<CTPRequestBase>();
}

void CTPRequestDispatcher::rmRequest(const std::shared_ptr<CTPRequestBase>& request) {
    std::unique_lock<std::mutex> auto_lock(request_lock_);
    if (request->hasActureRsp()) {
        acture_requests_.erase(request->getRequestId());
    } else {
        unsure_requests_.remove(request->getRequestId());
    }
}

void CTPRequestDispatcher::OnFrontConnected() {
    APPLOG_WARN("[NOTICE]OnFrontConnected");
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnFrontConnected();
    }
}

void CTPRequestDispatcher::OnFrontDisconnected(int nReason) {
    APPLOG_WARN("[NOTICE]OnFrontDisconnected nReason={}", nReason);
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnFrontDisconnected(nReason);
    }
}

void CTPRequestDispatcher::OnHeartBeatWarning(int nTimeLapse) {
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnHeartBeatWarning(nTimeLapse);
    }
}

void CTPRequestDispatcher::OnRtnOrder(CThostFtdcOrderField *pOrder) {
    xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnRtnOrder(pOrder);
    }
}

void CTPRequestDispatcher::OnRtnTrade(CThostFtdcTradeField *pTrade) {
    xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnRtnTrade(pTrade);
    }
}

void CTPRequestDispatcher::OnErrRtnOrderAction(CThostFtdcOrderActionField *pOrderAction
        , CThostFtdcRspInfoField *pRspInfo) {
    for (auto notify_callback : notify_callbacks_) {
        notify_callback->OnErrRtnOrderAction(pOrderAction, pRspInfo);
    }
}

#define CALLBACK_IMPL(function_name, ...) \
        std::shared_ptr<CTPRequestBase> request = getRequest(nRequestID, __FUNCTION__); \
        APPLOG_DEBUG("{} Called, nRequestID={}, bIsLast={}, requst={}" \
            , #function_name, nRequestID, bIsLast, (request?request->description() : "")); \
        if (!request) { \
            return ; \
        } \
        if (!pRspInfo || pRspInfo->ErrorID == 0) { \
            xueqiao::trade::hosting::upside::entry::EffectiveReporter::Global().setEffective();\
        }\
        request->function_name(__VA_ARGS__); \
        if (bIsLast) { \
            rmRequest(request); \
            request->onLastFinished(); \
        }

void CTPRequestDispatcher::OnRspQryInvestor(
        CThostFtdcInvestorField *pInvestor
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInvestor, pInvestor, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspAuthenticate(
		CThostFtdcRspAuthenticateField *pRspAuthenticateField
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspAuthenticate, pRspAuthenticateField, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspUserLogin(
        CThostFtdcRspUserLoginField *pRspUserLogin
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspUserLogin, pRspUserLogin, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspOrderInsert(
        CThostFtdcInputOrderField *pInputOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspOrderInsert, pInputOrder, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspOrderAction(
        CThostFtdcInputOrderActionField *pInputOrderAction
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspOrderAction, pInputOrderAction, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQryOrder(
        CThostFtdcOrderField *pOrder
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryOrder, pOrder, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQryTrade(
        CThostFtdcTradeField *pTrade
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryTrade, pTrade, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQryInstrument(
        CThostFtdcInstrumentField *pInstrument
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInstrument, pInstrument, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQrySettlementInfoConfirm(
        CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQrySettlementInfoConfirm, pSettlementInfoConfirm, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspSettlementInfoConfirm(
        CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspSettlementInfoConfirm, pSettlementInfoConfirm, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQryInvestorPosition(
        CThostFtdcInvestorPositionField *pInvestorPosition
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInvestorPosition, pInvestorPosition, pRspInfo, nRequestID, bIsLast)
}

void CTPRequestDispatcher::OnRspQryTradingAccount(CThostFtdcTradingAccountField *pTradingAccount
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryTradingAccount, pTradingAccount, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQrySettlementInfo(CThostFtdcSettlementInfoField *pSettlementInfo
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQrySettlementInfo, pSettlementInfo, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQryInvestorPositionDetail(
        CThostFtdcInvestorPositionDetailField *pInvestorPositionDetail
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInvestorPositionDetail, pInvestorPositionDetail, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQryInvestorPositionCombineDetail(
        CThostFtdcInvestorPositionCombineDetailField *pInvestorPositionCombineDetail
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInvestorPositionCombineDetail, pInvestorPositionCombineDetail, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQryExchangeMarginRate(
        CThostFtdcExchangeMarginRateField *pExchangeMarginRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryExchangeMarginRate, pExchangeMarginRate, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQryInstrumentMarginRate(
        CThostFtdcInstrumentMarginRateField *pInstrumentMarginRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInstrumentMarginRate, pInstrumentMarginRate, pRspInfo, nRequestID, bIsLast);
}

void CTPRequestDispatcher::OnRspQryInstrumentCommissionRate(
        CThostFtdcInstrumentCommissionRateField *pInstrumentCommissionRate
        , CThostFtdcRspInfoField *pRspInfo
        , int nRequestID
        , bool bIsLast) {
    CALLBACK_IMPL(OnRspQryInstrumentCommissionRate, pInstrumentCommissionRate, pRspInfo, nRequestID, bIsLast);
}

} // end namespace framework
} // end namespace ctpext
