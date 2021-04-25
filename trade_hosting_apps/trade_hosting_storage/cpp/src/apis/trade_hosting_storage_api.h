/*
 * trade_hosting_storage_api.h
 *
 *  Created on: 2018年2月4日
 *      Author: wangli
 */

#ifndef SRC_APIS_TRADE_HOSTING_STORAGE_API_H_
#define SRC_APIS_TRADE_HOSTING_STORAGE_API_H_

#include <memory>
#include "comm_types.h"
#include "broker_types.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace storage {
namespace api {

class HostingTradeAccountAPI {
public:
	struct InvalidDescription {
		std::string invalid_reason;
		int32_t api_ret_code = 0;
		int32_t invalid_error_code = 0;
	};

	static std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>
		getTradeAccount(int64_t trade_account_id) throw(::platform::comm::ErrorInfo);

	static std::shared_ptr<BrokerAccessEntry>
		getBrokerAccessEntry(int64_t trade_account_id) throw(::platform::comm::ErrorInfo);

	static void setTradeAccountActive(int64_t trade_account_id) throw(::platform::comm::ErrorInfo);

	static void setTradeAccountInvalid(int64_t trade_account_id
			, const InvalidDescription& invalid_description) throw(::platform::comm::ErrorInfo);

	static void getAllTradeAccounts(
			std::vector<::xueqiao::trade::hosting::HostingTradeAccount>& trade_accounts) throw(::platform::comm::ErrorInfo);

};


class HostingDealingAPI {
public:
    static int64_t createOrderId() throw(::platform::comm::ErrorInfo);

    static int64_t getRunningExecOrderIdByOrderRef(
            const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary
            , const  ::xueqiao::trade::hosting::HostingExecOrderRef& orderRef ) throw(::platform::comm::ErrorInfo);

    static int64_t  getRunningExecOrderIdByOrderDealID(
            const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary
            , const  ::xueqiao::trade::hosting::HostingExecOrderDealID& dealID) throw(::platform::comm::ErrorInfo);
};


}  // end namespace api
}  // end namespace storage
}  // end namespace hosting
}  // end namespace trade
} // end namespace xueqiao




#endif /* SRC_APIS_TRADE_HOSTING_STORAGE_API_H_ */
