#include "trade_hosting_upside_entry_stub.h"
#include "TradeHostingUpsideEntry.h"
#include "TradeHostingUpsideEntry_variables.h"
using namespace soldier::svr_platform;

namespace xueqiao { namespace trade { namespace hosting { namespace upside { namespace entry {
TradeHostingUpsideEntryStub::TradeHostingUpsideEntryStub()
  :TStubBase(TradeHostingUpsideEntry_service_key) {
}
void TradeHostingUpsideEntryStub::getSubProcessInfos(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector<TSubProcessInfo> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getSubProcessInfos(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::restartSubProcess(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int64_t trade_account_id
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.restartSubProcess(callResp.platform_args, trade_account_id);
}

void TradeHostingUpsideEntryStub::allocOrderRef(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::trade::hosting::HostingExecOrderRef& _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.allocOrderRef(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::orderInsert(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrder& insertOrder
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.orderInsert(callResp.platform_args, insertOrder);
}

void TradeHostingUpsideEntryStub::orderDelete(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrder& deleteOrder
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.orderDelete(callResp.platform_args, deleteOrder);
}

void TradeHostingUpsideEntryStub::syncOrderState(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.syncOrderState(callResp.platform_args, syncOrder);
}

void TradeHostingUpsideEntryStub::syncOrderTrades(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.syncOrderTrades(callResp.platform_args, syncOrder);
}

void TradeHostingUpsideEntryStub::syncOrderStateBatch(
  const TPrepareSyncCallArgs& platformCallArgs
  , const TSyncOrderStateBatchReq& batchReq
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.syncOrderStateBatch(callResp.platform_args, batchReq);
}

int64_t TradeHostingUpsideEntryStub::getLastUpsideEffectiveTimestamp(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  return client.getLastUpsideEffectiveTimestamp(callResp.platform_args);
}

void TradeHostingUpsideEntryStub::sendUpsideHeartBeat(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.sendUpsideHeartBeat(callResp.platform_args);
}

void TradeHostingUpsideEntryStub::dumpPositionSummaries(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::upside::position::PositionSummary> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.dumpPositionSummaries(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::getFunds(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector<TFund> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getFunds(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::getSettlementInfo(
  const TPrepareSyncCallArgs& platformCallArgs
  , TSettlementInfo& _return
  , const std::string& settlementDate
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getSettlementInfo(_return, callResp.platform_args, settlementDate);
}

void TradeHostingUpsideEntryStub::getNetPositionSummaries(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector<TNetPositionSummary> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getNetPositionSummaries(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::getPositionInfos(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector<TPositionInfo> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getPositionInfos(_return, callResp.platform_args);
}

void TradeHostingUpsideEntryStub::getPositionRateDetails(
  const TPrepareSyncCallArgs& platformCallArgs
  , TPositionRateDetails& _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingUpsideEntryClient client(callResp.protocol);
  client.getPositionRateDetails(_return, callResp.platform_args);
}


}}}}} // namespace
