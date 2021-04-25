#ifndef ContractOnlineDaoService_HANDLER_H
#define ContractOnlineDaoService_HANDLER_H
#include "ContractOnlineDaoService.h"
namespace xueqiao { namespace contract { namespace online { namespace dao {
class ContractOnlineDaoServiceHandler : public ContractOnlineDaoServiceIf {
public:
  ContractOnlineDaoServiceHandler();
  virtual ~ContractOnlineDaoServiceHandler();
  virtual void reqSledContract( ::xueqiao::contract::standard::SledContractPage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::standard::ReqSledContractOption& option, const int32_t pageIndex, const int32_t pageSize);
  virtual void reqCommodityMapping( ::xueqiao::contract::standard::CommodityMappingPage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::standard::ReqCommodityMappingOption& option, const int32_t pageIndex, const int32_t pageSize);
  virtual void reqSledExchange( ::xueqiao::contract::standard::SledExchangePage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::standard::ReqSledExchangeOption& option, const int32_t pageIndex, const int32_t pageSize);
  virtual void reqSledCommodity( ::xueqiao::contract::standard::SledCommodityPage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::standard::ReqSledCommodityOption& option, const int32_t pageIndex, const int32_t pageSize);
  virtual void reqContractVersion( ::xueqiao::contract::ContractVersionPage& _return, const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::ReqContractVersionOption& option, const int32_t pageIndex, const int32_t pageSize);
  virtual void updateContractVersion(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::ContractVersion& contractVersion);
  virtual void removeContractVersion(const  ::platform::comm::PlatformArgs& platformArgs, const int32_t versionId);
  virtual void addDbLocking(const  ::platform::comm::PlatformArgs& platformArgs, const  ::xueqiao::contract::DbLockingInfo& dbLockingInfo);
  virtual void removeDbLocking(const  ::platform::comm::PlatformArgs& platformArgs, const std::string& lockedBy);
  virtual void reqDbLockingInfo( ::xueqiao::contract::DbLockingInfo& _return, const  ::platform::comm::PlatformArgs& platformArgs);
};

}}}} // namespace
#endif
