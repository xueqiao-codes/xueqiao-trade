/*
 * thqd_swig.cpp
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#include "thqd_swig.h"

#include <memory>
#include "base/app_log.h"
#include "thqd_receivor.h"

using namespace soldier::base;
using namespace xueqiao::trade::hosting::quot::dispatcher::client;
using namespace xueqiao::trade::hosting::quot::dispatcher::client::swig;

static std::shared_ptr<THQDReceivor> g_receivor;
static std::shared_ptr<ITHQDCallback> g_thqd_callback;

void THQDSwig::init(
        const std::string& consumer_key,
        ITHQDCallback* callback) {
    if (g_thqd_callback) {
        return ;
    }
    AppLog::Init("apps/trade_hosting_quot_dispatcher/client/swig_" + consumer_key);
    g_thqd_callback.reset(callback);
    g_receivor.reset(new THQDReceivor(consumer_key));
    g_receivor->addListener(g_thqd_callback);
}

void THQDSwig::addTopic(const std::string& platform, const std::string& contract_symbol) {
    if (!g_receivor) {
        return ;
    }

    g_receivor->addTopic(platform, contract_symbol);
}

void THQDSwig::removeTopic(const std::string& platform, const std::string& contract_symbol) {
    if (!g_receivor) {
        return ;
    }

    g_receivor->removeTopic(platform, contract_symbol);
}

void THQDSwig::destroy() {
    g_thqd_callback.reset();
    g_receivor.reset();
}
