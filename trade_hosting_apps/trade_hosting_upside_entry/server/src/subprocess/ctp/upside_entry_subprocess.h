/*
 * upside_entry_subprocess.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_UPSIDE_ENTRY_SUBPROCESS_H_
#define SRC_SUBPROCESS_UPSIDE_ENTRY_SUBPROCESS_H_

#include <boost/shared_ptr.hpp>
#include <memory>

#include "hosting_upside_entry_handler.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class UpsideEntrySubProcess {
public:
	UpsideEntrySubProcess(int64_t trade_account_id);
	~UpsideEntrySubProcess() = default;

	bool runLoop();

private:
	boost::shared_ptr<TradeHostingUpsideEntryIf> prepareIf() const;

	std::shared_ptr<BrokerAccessEntry> getBrokerAccessEntry(
			const std::shared_ptr<HostingTradeAccount>& trade_account) const;
	std::shared_ptr<HostingTradeAccount> getTradeAccount() const ;
	HostingUpsideEntryHandler* allocIfImpl(const std::shared_ptr<HostingTradeAccount>& trade_account) const;

	int64_t trade_account_id_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_UPSIDE_ENTRY_SUBPROCESS_H_ */
