/*
 * order_validation.h
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_COMMON_ORDER_VALIDATION_H_
#define SRC_SUBPROCESS_COMMON_ORDER_VALIDATION_H_

#include "comm_types.h"
#include "trade_hosting_basic_types.h"


namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class OrderValidation {
public:
	static void checkInsertOrderStandard(const ::xueqiao::trade::hosting::HostingExecOrder& insertOrder)
		throw(::platform::comm::ErrorInfo);

	static void checkDeleteOrderStandard(const ::xueqiao::trade::hosting::HostingExecOrder& deleteOrder)
		throw(::platform::comm::ErrorInfo);

	static void checkSyncOrderStateStandard(const ::xueqiao::trade::hosting::HostingExecOrder& syncOrder)
	    throw(::platform::comm::ErrorInfo);

	static void checkSyncOrderTradesStandard(const ::xueqiao::trade::hosting::HostingExecOrder& syncOrder);

	static void checkAccountSummary(const ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary)
		throw(::platform::comm::ErrorInfo);

};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_COMMON_ORDER_VALIDATION_H_ */
