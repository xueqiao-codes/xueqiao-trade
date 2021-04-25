#ifndef TradeHostingStorage_STUB_H
#define TradeHostingStorage_STUB_H
#include "stub_base.h"
#include "trade_hosting_storage_types.h"
namespace xueqiao { namespace trade { namespace hosting { namespace storage { namespace thriftapi {
class TradeHostingStorageStub : public ::soldier::svr_platform::TStubBase {
public:
  TradeHostingStorageStub();
  virtual ~TradeHostingStorageStub() = default;

  void getTraddeAccount(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return
    , const int64_t tradeAccountId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getBrokerAccessEntry(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::BrokerAccessEntry> & _return
    , const int64_t tradeAccountId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void setTradeAccountInvalid(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int64_t tradeAccountId, const TradeAccountInvalidDescription& invalidDescription
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void setTradeAccountActive(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int64_t tradeAccountId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getAllTradeAccounts(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getBrokerAccessEntryFromCloud(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::BrokerAccessEntry> & _return
    , const int64_t tradeBrokerId, const int64_t tradeBrokerAccessId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createComposeGraphId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createTradeAccountId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t createSubAccountId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void updateConfig(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const UpdateConfigDescription& configDescription
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  int64_t getMachineId(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void getHostingSession(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , std::vector< ::xueqiao::trade::hosting::HostingSession> & _return
    , const int32_t subUserId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
};

}}}}} // namespace
#endif
