/*
 * es9_request_base.h
 *
 *  Created on: 2018年4月12日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_BASE_H_
#define SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_BASE_H_

#include <limits.h>
#include <memory>
#include <string.h>
#include "base/sync_call.h"
#include "base/string_util.h"
#include "base/time_helper.h"
#include "base/thread_pool.h"
#include "es9_trade_notify_base.h"
#include "es9_error_mapping.h"

namespace es9ext {
namespace framework {

class Es9ResponseBase {
public:
    Es9ResponseBase() = default;
    virtual ~Es9ResponseBase() = default;

    inline Es9ResponseBase& setErrorCode(int error_code) {
        error_code_ = error_code;
        utf8_error_msg_ = es9ErrorMsg(error_code);
        return *this;
    }

    inline int getErrorCode() const {
        return error_code_;
    }

    inline Es9ResponseBase& setGBKErrorMsg(const char* error_msg_gbk) {
        soldier::base::StringUtil::gbkToUTF8(error_msg_gbk, utf8_error_msg_);
        return *this;
    }

    const std::string& getUtf8ErrorMsg() const  {
        return utf8_error_msg_;
    }

    inline Es9ResponseBase& setHasTimeOut(bool has_timeout) {
        has_timeout_ = has_timeout;
        if (has_timeout) {
            error_code_ = INT_MIN;
            utf8_error_msg_ = "调用超时";
        }
        return *this;
    }

    inline bool hasTimeOut() const {
        return has_timeout_;
    }

    inline Es9ResponseBase& setRequestStartTimestampMs(int64_t timestampms) {
        return *this;
    }

    inline int64_t getRequestStartTimestampMs() {
        return request_start_timestampms_;
    }

    inline Es9ResponseBase& setResponseFinishedTimestampMs(int64_t timestampms) {
        response_finished_timestampms_ = timestampms;
        return *this;
    }

    inline int64_t getResponseFinishedTimestampMs() const {
        return response_finished_timestampms_;
    }

private:
    bool has_timeout_ = false;
    int error_code_ = 0;
    std::string utf8_error_msg_;
    int64_t request_start_timestampms_ = 0;
    int64_t response_finished_timestampms_ = 0;
};

class Es9RequestBase : public Es9TradeNotifyBase {
public:
    virtual ~Es9RequestBase() = default;

    /**
     * 请求具备明确的回调
     */
    virtual bool hasActureRsp() = 0;

    /**
     * 请求超时的时间
     */
    virtual int timeoutMs() = 0;

    /**
     *  请求是否具备SessionID
     */
    virtual bool hasSessionID() {
        return true;
    }

    /**
     *  发起API请求
     */
    virtual int onStart(ITapTrade::ITapTradeAPI* trader_api
            , ITapTrade::TAPIUINT32& session_id) = 0;

    virtual void onTimeOut() = 0;

    virtual void onLastFinished() = 0;

    virtual std::string description() = 0;

    inline void setRequestId(int request_id) {
        request_id_ = request_id;
    }

    inline int getRequestId() const {
        return request_id_;
    }

    inline int getOnStartRet() const {
        return on_start_ret_;
    }

    inline void setOnStartRet(int on_start_ret) {
        on_start_ret_ = on_start_ret;
    }

    inline soldier::base::SyncCall& getOnStartSyncCall() {
        return start_sync_call_;
    }

    inline void setRequestStartTimestampMs(int64_t timestampms) {
        request_start_timestampms_ = timestampms;
    }

    inline int64_t getRequestStartTimestampMs() const{
        return request_start_timestampms_;
    }

    inline void setSessionId(ITapTrade::TAPIUINT32 session_id) {
        session_id_ = session_id;
    }

    inline ITapTrade::TAPIUINT32 getSessionId() const {
        return session_id_;
    }

protected:
    Es9RequestBase() = default;

    int request_id_ = -1;
    int on_start_ret_ = 0;
    soldier::base::SyncCall start_sync_call_;
    int64_t request_start_timestampms_ = 0;
    ITapTrade::TAPIUINT32 session_id_ = UINT_MAX;
};

template<class RespType>
class Es9RequestWithCallback : public Es9RequestBase {
public:
    typedef std::function<void(const std::shared_ptr<RespType>&)> CallbackFunction;

    Es9RequestWithCallback(CallbackFunction callback)
        : callback_(std::move(callback)) {
    }

    virtual const std::shared_ptr<RespType>& getResponse() = 0;
    virtual void onTimeOut() {
        getResponse()->setHasTimeOut(true);
        if (callback_thread_) {
            callback_thread_->postTask(&Es9RequestWithCallback::invokeCallback, callback_, getResponse());
        } else {
            invokeCallback(callback_, getResponse());
        }
    }

    inline void setCallbackThread(
            const std::shared_ptr<soldier::base::TaskThread>& callback_thread) {
        callback_thread_  = callback_thread;
    }

    virtual void onLastFinished() {
        getResponse()->setRequestStartTimestampMs(getRequestStartTimestampMs());
        getResponse()->setResponseFinishedTimestampMs(soldier::base::NowInMilliSeconds());
        if (callback_thread_) {
            callback_thread_->postTask(&Es9RequestWithCallback::invokeCallback
                    , callback_, getResponse());
        } else {
            invokeCallback(callback_, getResponse());
        }
    }

private:
    static void invokeCallback(CallbackFunction callback, const std::shared_ptr<RespType>& resp) {
        callback(resp);
    }

    std::shared_ptr<soldier::base::TaskThread> callback_thread_;
    CallbackFunction callback_;
};




} // end namespace framework
} // end namespace es9ext


#endif /* SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_REQUEST_BASE_H_ */
