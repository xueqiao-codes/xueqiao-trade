/*
 * upside_effective_manager.h
 *
 *  Created on: 2018年2月26日
 *      Author: wileywang
 */

#ifndef SRC_MAINPROCESS_UPSIDE_EFFECTIVE_MANAGER_H_
#define SRC_MAINPROCESS_UPSIDE_EFFECTIVE_MANAGER_H_

#include <atomic>
#include <memory>
#include <mutex>
#include <set>
#include <thread>

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class UpsideEffectiveManager {
public:
    static UpsideEffectiveManager& Global();
    ~UpsideEffectiveManager();

    void clearAll();
    void addTradeAccount(int64_t trade_account_id);
    void removeTradeAccount(int64_t trade_account_id);

private:
    void onWorking();

    UpsideEffectiveManager();

    std::atomic_bool stopped_ = {false};

    std::mutex lock_;
    std::set<int64_t> upside_trade_accounts_;

    std::unique_ptr<std::thread> work_thread_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_MAINPROCESS_UPSIDE_EFFECTIVE_MANAGER_H_ */
