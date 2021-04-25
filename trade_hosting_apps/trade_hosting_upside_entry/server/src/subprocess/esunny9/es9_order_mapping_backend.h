/*
 * es9_order_mapping_backend.h
 *
 *  Created on: 2018年4月16日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_MAPPING_BACKEND_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_MAPPING_BACKEND_H_

#include "order_mapping_backend.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9OrderMappingBackend : public IOrderMappingBackend {
public:
    Es9OrderMappingBackend(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~Es9OrderMappingBackend() = default;

    virtual std::string calulateKey(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref);
    virtual std::string calulateKey(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id);

    virtual int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderRef& order_ref)
           throw (::platform::comm::ErrorInfo);
    virtual int64_t getExecOrderId(const ::xueqiao::trade::hosting::HostingExecOrderDealID& deal_id)
           throw (::platform::comm::ErrorInfo);

private:
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_ORDER_MAPPING_BACKEND_H_ */
