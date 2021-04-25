/*
 * upside_effective_manager.cpp
 *
 *  Created on: 2018年2月26日
 *      Author: wileywang
 */

#include "upside_effective_manager.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/time_helper.h"
#include "subprocess_manager.h"
#include "trade_hosting_upside_entry_stub.h"

using namespace soldier::base;
using namespace xueqiao::trade::hosting::upside::entry;

static std::unique_ptr<UpsideEffectiveManager> s_effective_manager;

#define EFFECTIVE_CHECK_SECONDS 15    // 每次检测间隔的时间
#define MAX_EFFECTIVE_PERIOD_SECONDS 60  // 触发账户进程重启，账户进程有效时间超过的最大间隔

UpsideEffectiveManager& UpsideEffectiveManager::Global() {
    if (!s_effective_manager) {
        static std::mutex lock;
        lock.lock();
        if (!s_effective_manager) {
            s_effective_manager.reset(new UpsideEffectiveManager());
        }
        lock.unlock();
    }
    return *s_effective_manager;
}

UpsideEffectiveManager::UpsideEffectiveManager() {
    work_thread_.reset(new std::thread(&UpsideEffectiveManager::onWorking, this));
}

UpsideEffectiveManager::~UpsideEffectiveManager() {
    stopped_ = true;
    work_thread_->join();
}

void UpsideEffectiveManager::clearAll() {
    lock_.lock();
    upside_trade_accounts_.clear();
    lock_.unlock();
}

void UpsideEffectiveManager::addTradeAccount(int64_t trade_account_id) {
    if (trade_account_id <= 0) {
        return ;
    }

    lock_.lock();
    upside_trade_accounts_.insert(trade_account_id);
    lock_.unlock();
}

void UpsideEffectiveManager::removeTradeAccount(int64_t trade_account_id) {
    if (trade_account_id <= 0) {
        return ;
    }

    lock_.lock();
    upside_trade_accounts_.erase(trade_account_id);
    lock_.unlock();
}

void UpsideEffectiveManager::onWorking() {
    while(!stopped_) {
        int32_t sleed_period_ms = 100;
        for (int count = 0; count < (EFFECTIVE_CHECK_SECONDS*1000)/ sleed_period_ms; ++count) {
            std::this_thread::sleep_for(std::chrono::milliseconds(sleed_period_ms));
            if (stopped_) {
                break;
            }
        }

        std::unique_ptr<std::set<int64_t>> check_trade_accounts;
        lock_.lock();
        check_trade_accounts.reset(new std::set<int64_t>(upside_trade_accounts_));
        lock_.unlock();

        for (auto it = check_trade_accounts->begin(); it != check_trade_accounts->end(); ++it) {
            std::string socket_file("/data/trade_hosting_upside_entry/run/");
            socket_file.append(boost::lexical_cast<std::string>(*it)).append(".sock");

            TradeHostingUpsideEntryStub stub;
            stub.setSocketFile(socket_file);

            ::soldier::svr_platform::TPrepareSyncCallArgs platformCallArgs;
            platformCallArgs.file_name = __FILE__;
            platformCallArgs.line = __LINE__;
            platformCallArgs.function_name = __FUNCTION__;

            stub.setTimeoutMs(2000);
            int64_t last_effective_timestamp = 0;
            try {
                last_effective_timestamp  = stub.getLastUpsideEffectiveTimestamp(platformCallArgs);
            } catch (::platform::comm::ErrorInfo& ei) {
                APPLOG_ERROR("getLastUpsideEffectiveTimestamp failed, trade_account_id={}, errorCode={}, errorMsg={}"
                        , *it, ei.errorCode, ei.errorMsg);
            } catch (::apache::thrift::TException& e) {
                APPLOG_ERROR("getLastUpsideEffectiveTimestamp failed, trade_account_id={}, {}"
                        , *it
                        , e.what());
                continue;
            }

            int64_t now_timestamp = NowInSeconds();
            APPLOG_INFO("UpsideEffectiveManager working, trade_account_id={}, now_timestamp={}, last_effective_timestamp={}"
                    , *it, now_timestamp, last_effective_timestamp);

            if (now_timestamp > last_effective_timestamp + MAX_EFFECTIVE_PERIOD_SECONDS) {
                SubProcessManager::Global().restartSubProcess(*it, std::shared_ptr<HostingTradeAccount>());
                continue;
            }

            if (now_timestamp < last_effective_timestamp + EFFECTIVE_CHECK_SECONDS) {
                continue;
            }

            try {
                stub.sendUpsideHeartBeat(platformCallArgs);
            } catch (::platform::comm::ErrorInfo& ei) {
                APPLOG_ERROR("sendUpsideHeartBeat failed, trade_account_id={}, errorCode={}, errorMsg={}"
                        , *it, ei.errorCode, ei.errorMsg);
            } catch (::apache::thrift::TException& e) {
                APPLOG_ERROR("sendUpsideHeartBeat failed, {}", e.what());
            }
        }
    }
}


