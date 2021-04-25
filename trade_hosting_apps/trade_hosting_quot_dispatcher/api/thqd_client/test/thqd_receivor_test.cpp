/*
 * thqd_receivor_test.cpp
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#include <iostream>
#include "base/app_log.h"
#include "thqd_receivor.h"

using namespace xueqiao::trade::hosting::quot::dispatcher::client;

class TestTHQDCallback : public ITHQDCallback {
public:
    virtual void onReceivedItemData(
                const std::string& platform
                , const std::string& contract_symbol
                , uint8_t* data
                , size_t size) {
        APPLOG_DEBUG("onReceivedItemData platform={}, contract_symbol={}, size={}, data=0x{:x}"
                , platform, contract_symbol, size, (int64_t)data);
    }
};


int main(int argc, char* argv[]) {
    soldier::base::AppLog::Init("test/thqd_receivor_test");

    std::unique_ptr<THQDReceivor> receivor(new THQDReceivor("thqd_receivor_test"));
    std::shared_ptr<ITHQDCallback> callback(new TestTHQDCallback());
    receivor->addListener(callback);
//
    receivor->addTopic("SXQ", "XCEC%7C1%7CGC%7C1812");
    receivor->addTopic("SXQ", "XCEC%7C1%7CGC%7C1812");
    std::cout << "add same two" << std::endl;
    receivor->addTopic("SXQ", "XCEC%7C1%7CGC%7C1812");
    std::cout << "add same three" << std::endl;
    getchar();

    return 0;
}
