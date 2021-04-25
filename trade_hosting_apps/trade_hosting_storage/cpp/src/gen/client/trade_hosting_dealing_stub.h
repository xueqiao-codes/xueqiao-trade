#ifndef TradeHostingDealing_STUB_H
#define TradeHostingDealing_STUB_H
#include "stub_base.h"
#include "trade_hosting_dealing_types.h"
namespace xueqiao { namespace trade { namespace hosting { namespace dealing { namespace thriftapi {
class TradeHostingDealingStub : public ::soldier::svr_platform::TStubBase {
public:
  TradeHostingDealingStub();
  virtual ~TradeHostingDealingStub() = default;

  void clearAll(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void createUserDeal(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int32_t subUserId, const int64_t subAccountId, const int64_t execOrderId, const  ::xueqiao::trade::hosting::HostingExecOrderContractSummary& contractSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDetail& orderDetail, const std::string& source
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void revokeDeal(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int64_t execOrderId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getOrder(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingExecOrder> & _return
    , const int64_t execOrderId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getOrderTrades(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingExecTrade> & _return
    , const int64_t execOrderId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getTradeRelatedLegs(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingExecTradeLeg> & _return
    , const int64_t execTradeId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t getRunningExecOrderIdByOrderRef(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderRef& orderRef
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t getRunningExecOrderIdByOrderDealID(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDealID& dealId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getInDealingOrderPage(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , HostingExecOrderPage& _return
    , const  ::platform::page::IndexedPageOption& pageOption
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createExecOrderId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createExecTradeId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createExecTradeLegId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
};

}}}}} // namespace
#endif
