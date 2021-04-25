/*
 * order_mapping_backend.h
 *
 *  Created on: 2018年2月7日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_COMMON_ORDER_MAPPING_BACKEND_H_
#define SRC_SUBPROCESS_COMMON_ORDER_MAPPING_BACKEND_H_

#include <memory>
#include "comm_types.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class IOrderMappingBackend {
public:
	virtual ~IOrderMappingBackend() = default;

	virtual std::string calulateKey(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref) = 0;
	virtual std::string calulateKey(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id) = 0;

	virtual int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref)
		throw(::platform::comm::ErrorInfo) = 0;
	virtual int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id)
		throw(::platform::comm::ErrorInfo)= 0;

protected:
	IOrderMappingBackend() = default;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_COMMON_ORDER_MAPPING_BACKEND_H_ */
