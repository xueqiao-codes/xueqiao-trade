#ifndef TradeHostingQuotDispatcher_STUB_H
#define TradeHostingQuotDispatcher_STUB_H
#include "stub_base.h"
#include "trade_hosting_quot_dispatcher_types.h"
namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {
class TradeHostingQuotDispatcherStub : public ::soldier::svr_platform::TStubBase {
public:
  TradeHostingQuotDispatcherStub();
  virtual ~TradeHostingQuotDispatcherStub() = default;

  void syncTopics(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const SyncTopicsRequest& syncRequest
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
};

}}}}} // namespace
#endif
