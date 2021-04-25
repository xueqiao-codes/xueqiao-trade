/*
 * ctp_request_dispatcher.h
 *
 *  Created on: 2018年3月23日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_REQUEST_DISPATCHER_H_
#define SRC_SUBPROCESS_CTP_CTP_REQUEST_DISPATCHER_H_

#include <vector>
#include <map>
#include <boost/asio.hpp>
#include "base/lru_cache.h"
#include "base/thread_pool.h"
#include "ctp_request_base.h"
#include "comm_types.h"


namespace ctpext {
namespace framework {

class CTPRequestDispatcher : public CThostFtdcTraderSpi {
public:
    CTPRequestDispatcher();

    virtual ~CTPRequestDispatcher();

    void setTraderApi(CThostFtdcTraderApi* trader_api);
    void cancel(int request_id);

    // 在使用Dispatcher发送请求之前使用
    void registerNotifyCallback(const std::shared_ptr<CThostFtdcTraderSpi>& notify_callback);

    // 发送请求
    int sendRequest(const std::shared_ptr<CTPRequestBase>& request) throw(::platform::comm::ErrorInfo);

    virtual void OnFrontConnected();
    virtual void OnFrontDisconnected(int nReason);
    virtual void OnHeartBeatWarning(int nTimeLapse);

    virtual void OnErrRtnOrderAction(CThostFtdcOrderActionField *pOrderAction, CThostFtdcRspInfoField *pRspInfo);
    virtual void OnRtnOrder(CThostFtdcOrderField *pOrder) ;
    virtual void OnRtnTrade(CThostFtdcTradeField *pTrade) ;

    virtual void OnRspQryInvestor(CThostFtdcInvestorField *pInvestor
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspUserLogin(CThostFtdcRspUserLoginField *pRspUserLogin
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspOrderInsert(
                CThostFtdcInputOrderField *pInputOrder
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspOrderAction(CThostFtdcInputOrderActionField *pInputOrderAction
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspQryOrder(CThostFtdcOrderField *pOrder
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspQryTrade(CThostFtdcTradeField *pTrade
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

    virtual void OnRspQryInstrument(CThostFtdcInstrumentField *pInstrument
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast) ;

    virtual void OnRspQrySettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField *pSettlementInfoConfirm
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryInvestorPosition(
            CThostFtdcInvestorPositionField *pInvestorPosition
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryTradingAccount(CThostFtdcTradingAccountField *pTradingAccount
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField *pSettlementInfo
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField *pInvestorPositionDetail
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryInvestorPositionCombineDetail(
            CThostFtdcInvestorPositionCombineDetailField *pInvestorPositionCombineDetail
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryExchangeMarginRate(
            CThostFtdcExchangeMarginRateField *pExchangeMarginRate
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryInstrumentMarginRate(CThostFtdcInstrumentMarginRateField *pInstrumentMarginRate
            , CThostFtdcRspInfoField *pRspInfo
            , int nRequestID
            , bool bIsLast);

    virtual void OnRspQryInstrumentCommissionRate(CThostFtdcInstrumentCommissionRateField *pInstrumentCommissionRate
                , CThostFtdcRspInfoField *pRspInfo
                , int nRequestID
                , bool bIsLast);

private:
    static void onTimerWorking();
    void onHandleRequest(const std::shared_ptr<CTPRequestBase>& request
            , ::platform::comm::ErrorInfo* error_info);
    void onHandleTimeout(const boost::system::error_code &ec, int request_id);

    void addRequest(const std::shared_ptr<CTPRequestBase>& request);
    std::shared_ptr<CTPRequestBase> getRequest(int request_id, const std::string& call_function);
    void rmRequest(const std::shared_ptr<CTPRequestBase>& request);

    struct RequestEntry {
        std::shared_ptr<CTPRequestBase> request;
        std::unique_ptr<boost::asio::deadline_timer> timer;

        ~RequestEntry();
    };

    CThostFtdcTraderApi* trader_api_ = nullptr;
    std::unique_ptr<soldier::base::TaskThread> dispatcher_thread_;  // 分发工作线程
    std::vector<std::shared_ptr<CThostFtdcTraderSpi>> notify_callbacks_;  // 上手通知类型回调

    std::mutex request_lock_;
    soldier::base::LruCache<int, std::shared_ptr<RequestEntry>> unsure_requests_;  // 不确定有回报的请求类型
    std::map<int, std::shared_ptr<RequestEntry>> acture_requests_;  //明确回报的请求类型

};


} // end namespace framework
} // end namespace ctpext




#endif /* SRC_SUBPROCESS_CTP_CTP_REQUEST_DISPATCHER_H_ */
