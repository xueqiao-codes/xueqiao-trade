#ifndef TradeHostingQuotDispatcher_HANDLER_H
#define TradeHostingQuotDispatcher_HANDLER_H
#include "TradeHostingQuotDispatcher.h"
namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {
class TradeHostingQuotDispatcherHandler : public TradeHostingQuotDispatcherIf {
public:
  TradeHostingQuotDispatcherHandler();
  virtual ~TradeHostingQuotDispatcherHandler();
  virtual void syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest);
};

}}}}} // namespace
#endif
