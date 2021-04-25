/*
 * performance_utils.cpp
 *
 *  Created on: 2018年9月19日
 *      Author: wangli
 */

#include "performance_utils.h"

#include "base/app_log.h"

using namespace xueqiao::trade::hosting::upside::entry;

BlockTimerPrinter::BlockTimerPrinter(const std::string& description)
    : description_(description) {
    start_clock_ = std::chrono::high_resolution_clock::now();
}

BlockTimerPrinter::~BlockTimerPrinter() {
    APPLOG_INFO("{}, escapedUs={}", description_,
            std::chrono::duration_cast<std::chrono::microseconds>(
                    std::chrono::high_resolution_clock::now() - start_clock_).count());
}

