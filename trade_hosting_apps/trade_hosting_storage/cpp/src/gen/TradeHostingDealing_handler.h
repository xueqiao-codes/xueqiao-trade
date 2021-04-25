#ifndef TradeHostingDealing_HANDLER_H
#define TradeHostingDealing_HANDLER_H
#include "TradeHostingDealing.h"
namespace xueqiao { namespace trade { namespace hosting { namespace dealing { namespace thriftapi {
class TradeHostingDealingHandler : public TradeHostingDealingIf {
public:
  TradeHostingDealingHandler();
  virtual ~TradeHostingDealingHandler();
  virtual void clearAll(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void createUserDeal(const  ::platform::comm::PlatformArgs& platformArgs, const int32_t subUserId, const int64_t subAccountId, const int64_t execOrderId, const  ::xueqiao::trade::hosting::HostingExecOrderContractSummary& contractSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDetail& orderDetail, const std::string& source);
  virtual void revokeDeal(const  ::platform::comm::PlatformArgs& platformArgs, const int64_t execOrderId);
  virtual void getOrder(std::vector< ::xueqiao::trade::hosting::HostingExecOrder> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t execOrderId);
  virtual void getOrderTrades(std::vector< ::xueqiao::trade::hosting::HostingExecTrade> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t execOrderId);
  virtual void getTradeRelatedLegs(std::vector< ::xueqiao::trade::hosting::HostingExecTradeLeg> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t execTradeId);
  virtual int64_t getRunningExecOrderIdByOrderRef(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderRef& orderRef);
  virtual int64_t getRunningExecOrderIdByOrderDealID(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDealID& dealId);
  virtual void getInDealingOrderPage(HostingExecOrderPage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::platform::page::IndexedPageOption& pageOption);
  virtual int64_t createExecOrderId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual int64_t createExecTradeId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual int64_t createExecTradeLegId(const  ::platform::comm::PlatformArgs& platformArgs);
};

}}}}} // namespace
#endif
