/*
 * effective_reporter.cpp
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */
#include "effective_reporter.h"


#include <memory>
#include <mutex>

using namespace xueqiao::trade::hosting::upside::entry;

static std::unique_ptr<EffectiveReporter> S_INSTANCE;

EffectiveReporter& EffectiveReporter::Global() {
    if (!S_INSTANCE) {
        static std::mutex lock;
        lock.lock();
        if (!S_INSTANCE){
            S_INSTANCE.reset(new EffectiveReporter());
        }
        lock.unlock();
    }
    return *S_INSTANCE;
}



