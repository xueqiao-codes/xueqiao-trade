#include "trade_hosting_quot_dispatcher_stub.h"
#include "TradeHostingQuotDispatcher.h"
#include "TradeHostingQuotDispatcher_variables.h"
using namespace soldier::svr_platform;

namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {
TradeHostingQuotDispatcherStub::TradeHostingQuotDispatcherStub()
  :TStubBase(TradeHostingQuotDispatcher_service_key) {
}
void TradeHostingQuotDispatcherStub::syncTopics(
  const TPrepareSyncCallArgs& platformCallArgs
  , const SyncTopicsRequest& syncRequest
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  TradeHostingQuotDispatcherClient client(callResp.protocol);
  client.syncTopics(callResp.platform_args, syncRequest);
}


}}}}} // namespace
