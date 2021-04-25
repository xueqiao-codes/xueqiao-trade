#ifndef TradeHostingStorage_HANDLER_H
#define TradeHostingStorage_HANDLER_H
#include "TradeHostingStorage.h"
namespace xueqiao { namespace trade { namespace hosting { namespace storage { namespace thriftapi {
class TradeHostingStorageHandler : public TradeHostingStorageIf {
public:
  TradeHostingStorageHandler();
  virtual ~TradeHostingStorageHandler();
  virtual void getTraddeAccount(std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t tradeAccountId);
  virtual void getBrokerAccessEntry(std::vector< ::BrokerAccessEntry> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t tradeAccountId);
  virtual void setTradeAccountInvalid(const  ::platform::comm::PlatformArgs& platformArgs, const int64_t tradeAccountId, const TradeAccountInvalidDescription& invalidDescription);
  virtual void setTradeAccountActive(const  ::platform::comm::PlatformArgs& platformArgs, const int64_t tradeAccountId);
  virtual void getAllTradeAccounts(std::vector< ::xueqiao::trade::hosting::HostingTradeAccount> & _return, const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getBrokerAccessEntryFromCloud(std::vector< ::BrokerAccessEntry> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int64_t tradeBrokerId, const int64_t tradeBrokerAccessId);
  virtual int64_t createComposeGraphId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual int64_t createTradeAccountId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual int64_t createSubAccountId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void updateConfig(const  ::platform::comm::PlatformArgs& platformArgs, const UpdateConfigDescription& configDescription);
  virtual int64_t getMachineId(const  ::platform::comm::PlatformArgs& platformArgs);
  virtual void getHostingSession(std::vector< ::xueqiao::trade::hosting::HostingSession> & _return, const  ::platform::comm::PlatformArgs& platformArgs, const int32_t subUserId);
};

}}}}} // namespace
#endif
