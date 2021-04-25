/*
 * es9_request_dispatcher.h
 *
 *  Created on: 2018年4月12日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_DISPATCHER_H_
#define SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_DISPATCHER_H_

#include <map>
#include <memory>
#include <vector>
#include <boost/asio.hpp>
#include "base/lru_cache.h"
#include "base/thread_pool.h"
#include "comm_types.h"
#include "es9_request_base.h"
#include "es9_trade_notify_base.h"

namespace es9ext {
namespace framework {

class Es9RequestDispatcher : public Es9TradeNotifyBase {
public:
    Es9RequestDispatcher();
    virtual ~Es9RequestDispatcher();

    void setTraderApi(ITapTrade::ITapTradeAPI* trader_api);
    void cancel(int request_id);

    void registerNotifyCallback(const std::shared_ptr<Es9TradeNotifyBase>& notify_callback);

    int sendRequest(const std::shared_ptr<Es9RequestBase>& request) throw(::platform::comm::ErrorInfo);

    virtual void TAP_CDECL OnConnect();
    virtual void TAP_CDECL OnRspLogin(ITapTrade::TAPIINT32 errorCode, const ITapTrade::TapAPITradeLoginRspInfo *loginRspInfo);
    virtual void TAP_CDECL OnExpriationDate(ITapTrade::TAPIDATE date, int days);
    virtual void TAP_CDECL OnAPIReady();
    virtual void TAP_CDECL OnDisconnect(ITapTrade::TAPIINT32 reasonCode);
    virtual void TAP_CDECL OnRtnOrder(const ITapTrade::TapAPIOrderInfoNotice *info);
    virtual void TAP_CDECL OnRtnFill(const ITapTrade::TapAPIFillInfo *info);

    virtual void TAP_CDECL OnRspQryAccount(
            ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIUINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountInfo *info);

    virtual void TAP_CDECL OnRspOrderAction(
            ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderActionRsp *info);

    virtual void TAP_CDECL OnRspQryOrder(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIOrderInfo *info);

    virtual void TAP_CDECL OnRspQryFill(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIFillInfo *info);

    virtual void TAP_CDECL OnRspQryFund(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIFundData *info);

    virtual void TAP_CDECL OnRspQryBill(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIBillQryRsp *info);

    virtual void TAP_CDECL OnRspQryPosition(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPIPositionInfo *info);

    virtual void TAP_CDECL OnRspQryAccountFeeRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountFeeRentQryRsp *info);

    virtual void TAP_CDECL OnRspQryAccountMarginRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountMarginRentQryRsp *info);

    virtual void TAP_CDECL OnRspQryCommodity(ITapTrade::TAPIUINT32 sessionID
                , ITapTrade::TAPIINT32 errorCode
                , ITapTrade::TAPIYNFLAG isLast
                , const ITapTrade::TapAPICommodityInfo *info);


private:
    static void onTimerWorking();
    void onHandleRequest(const std::shared_ptr<Es9RequestBase>& request
                , ::platform::comm::ErrorInfo* error_info);
    void onHandleTimeout(const boost::system::error_code &ec, int request_id);

    struct Es9RequestEntry {
        std::shared_ptr<Es9RequestBase> request;
        std::unique_ptr<boost::asio::deadline_timer> timer;
        ~Es9RequestEntry();
    };

    void addRequest(const std::shared_ptr<Es9RequestBase>& request);
    std::shared_ptr<Es9RequestBase> getRequestByRequestIdUnSafe(int request_id);
    std::shared_ptr<Es9RequestBase> getRequestBySessionId(ITapTrade::TAPIUINT32 session_id
            , const std::string& call_function);
    void rmRequest(const std::shared_ptr<Es9RequestBase>& request);

    std::vector<std::shared_ptr<Es9TradeNotifyBase>> notify_callbacks_;

    std::unique_ptr<soldier::base::TaskThread> dispatcher_thread_;
    ITapTrade::ITapTradeAPI* trader_api_ = nullptr;

    std::mutex request_lock_;
    soldier::base::LruCache<int, std::shared_ptr<Es9RequestEntry>> unsure_requests_;  // 不确定有回报的请求类型
    std::map<int, std::shared_ptr<Es9RequestEntry>> acture_requests_;  //明确回报的请求类型
    soldier::base::LruCache<ITapTrade::TAPIUINT32, int> session_id_2_request_ids_;   //SessionID对应RequestID的映射
};

} // end namespace framework
} // end namespace es9ext


#endif /* SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_DISPATCHER_H_ */
