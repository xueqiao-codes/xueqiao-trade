/*
 * performance_utils.h
 *
 *  Created on: 2018年9月19日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_COMMON_PERFORMANCE_UTILS_H_
#define SRC_SUBPROCESS_COMMON_PERFORMANCE_UTILS_H_


#include <string>
#include <chrono>

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class BlockTimerPrinter {
public:
    BlockTimerPrinter(const std::string& description);
    ~BlockTimerPrinter();

private:
    std::string description_;
    std::chrono::time_point<std::chrono::high_resolution_clock> start_clock_;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#define BLOCK_TIME_PRINT(description)  BLOCK_TIME_PRINT_NAME(TIMER_PRINTER, description)
#define BLOCK_TIME_PRINT_NAME(name, description) ::xueqiao::trade::hosting::upside::entry::BlockTimerPrinter name(description);

#endif /* SRC_SUBPROCESS_COMMON_PERFORMANCE_UTILS_H_ */
