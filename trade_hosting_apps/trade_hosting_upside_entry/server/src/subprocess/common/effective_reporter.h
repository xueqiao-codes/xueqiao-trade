/*
 * effective_reporter.h
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_COMMON_EFFECTIVE_REPORTER_H_
#define SRC_SUBPROCESS_COMMON_EFFECTIVE_REPORTER_H_


#include <atomic>
#include "base/time_helper.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class EffectiveReporter {
public:
    static EffectiveReporter& Global();
    ~EffectiveReporter() = default;

    inline void setEffective() {
        last_effective_timestamp_ =  soldier::base::NowInSeconds();
    }

   inline int64_t getLastEffetiveTimestamp() {
       return last_effective_timestamp_;
   }

    inline bool isAccountInfoInvalid() {
        return account_info_invalid_;
    }

    inline void setAccountInfoInvalid(int api_ret_code) {
        last_account_info_invalid_api_ret_code_ = api_ret_code;
        account_info_invalid_ = true;
    }

    inline bool isAccountInfoInvalid(int& last_api_ret_code) {
        last_api_ret_code = last_account_info_invalid_api_ret_code_;
        return account_info_invalid_;
    }

private:
    EffectiveReporter() = default;

    bool account_info_invalid_ = false;
    int last_account_info_invalid_api_ret_code_ = 0;

    std::atomic<int64_t> last_effective_timestamp_ = {0};
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_COMMON_EFFECTIVE_REPORTER_H_ */
