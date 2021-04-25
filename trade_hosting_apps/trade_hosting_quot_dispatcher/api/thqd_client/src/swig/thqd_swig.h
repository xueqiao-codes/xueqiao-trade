/*
 * thqd_swig.h
 *
 *  Created on: 2018年4月26日
 *      Author: 44385
 */

#ifndef SRC_SWIG_THQD_SWIG_H_
#define SRC_SWIG_THQD_SWIG_H_

#include <string>
#include "thqd_callback.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace quot {
namespace dispatcher {
namespace client {
namespace swig {


class THQDSwig {
public:
    static void init(const std::string& consumer_key,
            ::xueqiao::trade::hosting::quot::dispatcher::client::ITHQDCallback* callback);
    static void addTopic(const std::string& platform, const std::string& contract_symbol);
    static void removeTopic(const std::string& platform, const std::string& contract_symbol);
    static void destroy();
};


} // end namespace swig
} // end namespace client
} // end namespace dispatcher
} // end namespace quot
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao









#endif /* SRC_SWIG_THQD_SWIG_H_ */
