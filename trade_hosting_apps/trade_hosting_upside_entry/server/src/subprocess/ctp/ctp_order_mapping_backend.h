/*
 * ctp_order_mapping_backend.h
 *
 *  Created on: 2018年2月7日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_ORDER_MAPPING_BACKEND_H_
#define SRC_SUBPROCESS_CTP_CTP_ORDER_MAPPING_BACKEND_H_

#include "order_mapping_backend.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPOrderMappingBackend: public IOrderMappingBackend {
public:
    CTPOrderMappingBackend(
            const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~CTPOrderMappingBackend() = default;

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

#endif /* SRC_SUBPROCESS_CTP_CTP_ORDER_MAPPING_BACKEND_H_ */
