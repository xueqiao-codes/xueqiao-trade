#include "trade_hosting_storage_stub.h"
#include "TradeHostingStorage.h"
#include "TradeHostingStorage_variables.h"
using namespace soldier::svr_platform;

namespace xueqiao { namespace trade { namespace hosting { namespace storage { namespace thriftapi {
TradeHostingStorageStub::TradeHostingStorageStub()
  :TStubBase(TradeHostingStorage_service_key) {
}
void TradeHostingStorageStub::getTraddeAccount(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return
  , const int64_t tradeAccountId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.getTraddeAccount(_return, callResp.platform_args, tradeAccountId);
}

void TradeHostingStorageStub::getBrokerAccessEntry(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::BrokerAccessEntry> & _return
  , const int64_t tradeAccountId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.getBrokerAccessEntry(_return, callResp.platform_args, tradeAccountId);
}

void TradeHostingStorageStub::setTradeAccountInvalid(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int64_t tradeAccountId, const TradeAccountInvalidDescription& invalidDescription
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.setTradeAccountInvalid(callResp.platform_args, tradeAccountId, invalidDescription);
}

void TradeHostingStorageStub::setTradeAccountActive(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int64_t tradeAccountId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.setTradeAccountActive(callResp.platform_args, tradeAccountId);
}

void TradeHostingStorageStub::getAllTradeAccounts(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.getAllTradeAccounts(_return, callResp.platform_args);
}

void TradeHostingStorageStub::getBrokerAccessEntryFromCloud(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::BrokerAccessEntry> & _return
  , const int64_t tradeBrokerId, const int64_t tradeBrokerAccessId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.getBrokerAccessEntryFromCloud(_return, callResp.platform_args, tradeBrokerId, tradeBrokerAccessId);
}

int64_t TradeHostingStorageStub::createComposeGraphId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  return client.createComposeGraphId(callResp.platform_args);
}

int64_t TradeHostingStorageStub::createTradeAccountId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  return client.createTradeAccountId(callResp.platform_args);
}

int64_t TradeHostingStorageStub::createSubAccountId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  return client.createSubAccountId(callResp.platform_args);
}

void TradeHostingStorageStub::updateConfig(
  const TPrepareSyncCallArgs& platformCallArgs
  , const UpdateConfigDescription& configDescription
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.updateConfig(callResp.platform_args, configDescription);
}

int64_t TradeHostingStorageStub::getMachineId(
  const TPrepareSyncCallArgs& platformCallArgs
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  return client.getMachineId(callResp.platform_args);
}

void TradeHostingStorageStub::getHostingSession(
  const TPrepareSyncCallArgs& platformCallArgs
  , std::vector< ::xueqiao::trade::hosting::HostingSession> & _return
  , const int32_t subUserId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingStorageClient client(callResp.protocol);
  client.getHostingSession(_return, callResp.platform_args, subUserId);
}


}}}}} // namespace
