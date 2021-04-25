/*
 * thqd_callback.h
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#ifndef SRC_THQD_CALLBACK_H_
#define SRC_THQD_CALLBACK_H_

#include <string>

namespace xueqiao {
namespace trade {
namespace hosting {
namespace quot {
namespace dispatcher {
namespace client {


class ITHQDCallback {
public:
    virtual ~ITHQDCallback() {}

    virtual void onReceivedItemData(
            const std::string& platform
            , const std::string& contract_symbol
            , uint8_t* data
            , size_t size) = 0;

protected:
    ITHQDCallback() {}
};



} // end namespace client
} // end namespace dispatcher
} // end namespace quot
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_THQD_CALLBACK_H_ */
