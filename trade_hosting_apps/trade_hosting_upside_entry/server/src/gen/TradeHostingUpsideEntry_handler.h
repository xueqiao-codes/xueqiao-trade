#ifndef TradeHostingUpsideEntry_HANDLER_H
#define TradeHostingUpsideEntry_HANDLER_H
#include "TradeHostingUpsideEntry.h"
namespace xueqiao { namespace trade { namespace hosting { namespace upside { namespace entry {
class TradeHostingUpsideEntryHandler : public TradeHostingUpsideEntryIf {
public:
  TradeHostingUpsideEntryHandler();
  virtual ~TradeHostingUpsideEntryHandler();
  virtual void getSubProcessInfos(std::vector<TSubProcessInfo> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void restartSubProcess(const  ::platform::comm::PlatformArgs& platformArgs, const int64_t trade_account_id);
  virtual void allocOrderRef( ::xueqiao::trade::hosting::HostingExecOrderRef& _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void orderInsert(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrder& insertOrder);
  virtual void orderDelete(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrder& deleteOrder);
  virtual void syncOrderState(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder);
  virtual void syncOrderTrades(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder);
  virtual void syncOrderStateBatch(const  ::platform::comm::PlatformArgs& platformArgs, const TSyncOrderStateBatchReq& batchReq);
  virtual int64_t getLastUpsideEffectiveTimestamp(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void sendUpsideHeartBeat(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void dumpPositionSummaries(std::vector< ::xueqiao::trade::hosting::upside::position::PositionSummary> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getFunds(std::vector<TFund> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getSettlementInfo(TSettlementInfo& _return, const  ::platform::comm::PlatformArgs& platformArgs, const std::string& settlementDate);
  virtual void getNetPositionSummaries(std::vector<TNetPositionSummary> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getPositionInfos(std::vector<TPositionInfo> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getPositionRateDetails(TPositionRateDetails& _return, const  ::platform::comm::PlatformArgs& platformArgs);
};

}}}}} // namespace
#endif
