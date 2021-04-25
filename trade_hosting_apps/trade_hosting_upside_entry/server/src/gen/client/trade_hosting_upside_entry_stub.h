#ifndef TradeHostingUpsideEntry_STUB_H
#define TradeHostingUpsideEntry_STUB_H
#include "stub_base.h"
#include "trade_hosting_upside_entry_types.h"
namespace xueqiao { namespace trade { namespace hosting { namespace upside { namespace entry {
class TradeHostingUpsideEntryStub : public ::soldier::svr_platform::TStubBase {
public:
  TradeHostingUpsideEntryStub();
  virtual ~TradeHostingUpsideEntryStub() = default;

  void getSubProcessInfos(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector<TSubProcessInfo> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void restartSubProcess(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int64_t trade_account_id
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void allocOrderRef(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::trade::hosting::HostingExecOrderRef& _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void orderInsert(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrder& insertOrder
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void orderDelete(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrder& deleteOrder
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void syncOrderState(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void syncOrderTrades(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void syncOrderStateBatch(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const TSyncOrderStateBatchReq& batchReq
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t getLastUpsideEffectiveTimestamp(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void sendUpsideHeartBeat(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void dumpPositionSummaries(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::upside::position::PositionSummary> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getFunds(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector<TFund> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getSettlementInfo(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , TSettlementInfo& _return
    , const std::string& settlementDate
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getNetPositionSummaries(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector<TNetPositionSummary> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getPositionInfos(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector<TPositionInfo> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getPositionRateDetails(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , TPositionRateDetails& _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
};

}}}}} // namespace
#endif
