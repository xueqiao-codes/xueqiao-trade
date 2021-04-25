#include "trade_hosting_dealing_stub.h"
#include "TradeHostingDealing.h"
#include "TradeHostingDealing_variables.h"
using namespace soldier::svr_platform;

namespace xueqiao { namespace trade { namespace hosting { namespace dealing { namespace thriftapi {
TradeHostingDealingStub::TradeHostingDealingStub()
  :TStubBase(TradeHostingDealing_service_key) {
}
void TradeHostingDealingStub::clearAll(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.clearAll(callResp.platform_args);
}

void TradeHostingDealingStub::createUserDeal(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int32_t subUserId, const int64_t subAccountId, const int64_t execOrderId, const  ::xueqiao::trade::hosting::HostingExecOrderContractSummary& contractSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDetail& orderDetail, const std::string& source
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.createUserDeal(callResp.platform_args, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source);
}

void TradeHostingDealingStub::revokeDeal(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int64_t execOrderId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.revokeDeal(callResp.platform_args, execOrderId);
}

void TradeHostingDealingStub::getOrder(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingExecOrder> & _return
  , const int64_t execOrderId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.getOrder(_return, callResp.platform_args, execOrderId);
}

void TradeHostingDealingStub::getOrderTrades(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingExecTrade> & _return
  , const int64_t execOrderId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.getOrderTrades(_return, callResp.platform_args, execOrderId);
}

void TradeHostingDealingStub::getTradeRelatedLegs(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingExecTradeLeg> & _return
  , const int64_t execTradeId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.getTradeRelatedLegs(_return, callResp.platform_args, execTradeId);
}

int64_t TradeHostingDealingStub::getRunningExecOrderIdByOrderRef(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderRef& orderRef
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  return client.getRunningExecOrderIdByOrderRef(callResp.platform_args, accountSummary, orderRef);
}

int64_t TradeHostingDealingStub::getRunningExecOrderIdByOrderDealID(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrderTradeAccountSummary& accountSummary, const  ::xueqiao::trade::hosting::HostingExecOrderDealID& dealId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  return client.getRunningExecOrderIdByOrderDealID(callResp.platform_args, accountSummary, dealId);
}

void TradeHostingDealingStub::getInDealingOrderPage(
  const TPrepareSyncCallArgs& platformCallArgs
  , HostingExecOrderPage& _return
  , const  ::platform::page::IndexedPageOption& pageOption
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  client.getInDealingOrderPage(_return, callResp.platform_args, pageOption);
}

int64_t TradeHostingDealingStub::createExecOrderId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  return client.createExecOrderId(callResp.platform_args);
}

int64_t TradeHostingDealingStub::createExecTradeId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  return client.createExecTradeId(callResp.platform_args);
}

int64_t TradeHostingDealingStub::createExecTradeLegId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingDealingClient client(callResp.protocol);
  return client.createExecTradeLegId(callResp.platform_args);
}


}}}}} // namespace
