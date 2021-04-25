/*
 * order_mapping_manager.h
 *
 *  Created on: 2018年2月7日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_COMMON_ORDER_MAPPING_MANAGER_H_
#define SRC_SUBPROCESS_COMMON_ORDER_MAPPING_MANAGER_H_

#include <memory>
#include <mutex>
#include "base/lru_cache.h"
#include "comm_types.h"
#include "trade_hosting_basic_types.h"
#include "order_mapping_backend.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class OrderMappingManager {
public:
	OrderMappingManager(IOrderMappingBackend* backend
			, size_t max_cache_items);

	virtual ~OrderMappingManager() = default;

	/**
	 *  @return -1 : NotFound
	 *          >0 : execOrderId
	 */
	int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref)
		throw(::platform::comm::ErrorInfo);

	int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id)
		throw(::platform::comm::ErrorInfo);

	void setExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref
			, const int64_t& exec_order_id);

	void setExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id
			, const int64_t& exec_order_id);

private:
	std::unique_ptr<IOrderMappingBackend> backend_;

	std::mutex order_ref_cache_lock_;
	soldier::base::LruCache<std::string, int64_t> order_ref_cache_;

	std::mutex deal_id_cache_lock_;
	soldier::base::LruCache<std::string, int64_t> deal_id_cache_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao




#endif /* SRC_SUBPROCESS_COMMON_ORDER_MAPPING_MANAGER_H_ */
