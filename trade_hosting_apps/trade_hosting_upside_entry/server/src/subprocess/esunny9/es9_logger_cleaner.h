/*
 * es9_logger_cleaner.h
 *
 *  Created on: 2019年1月9日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_LOGGER_CLEANER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_LOGGER_CLEANER_H_

#include "base/thread_pool.h"
#include <string>

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9LoggerCleaner {
public:
    Es9LoggerCleaner(const std::string& directory
            , const std::string& prefix);
    ~Es9LoggerCleaner();

private:
    void onStart();
    void onCheckOnce();
    void process();

    std::string directory_;
    std::string prefix_;

    std::unique_ptr<soldier::base::TaskThread> work_thread_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao




#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_LOGGER_CLEANER_H_ */
