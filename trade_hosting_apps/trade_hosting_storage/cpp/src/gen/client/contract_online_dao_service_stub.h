#ifndef ContractOnlineDaoService_STUB_H
#define ContractOnlineDaoService_STUB_H
#include "stub_base.h"
#include "contract_online_dao_service_types.h"
namespace xueqiao { namespace contract { namespace online { namespace dao {
class ContractOnlineDaoServiceStub : public ::soldier::svr_platform::TStubBase {
public:
  ContractOnlineDaoServiceStub();
  virtual ~ContractOnlineDaoServiceStub() = default;

  void reqSledContract(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::standard::SledContractPage& _return
    , const  ::xueqiao::contract::standard::ReqSledContractOption& option, const int32_t pageIndex, const int32_t pageSize
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void reqCommodityMapping(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::standard::CommodityMappingPage& _return
    , const  ::xueqiao::contract::standard::ReqCommodityMappingOption& option, const int32_t pageIndex, const int32_t pageSize
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void reqSledExchange(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::standard::SledExchangePage& _return
    , const  ::xueqiao::contract::standard::ReqSledExchangeOption& option, const int32_t pageIndex, const int32_t pageSize
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void reqSledCommodity(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::standard::SledCommodityPage& _return
    , const  ::xueqiao::contract::standard::ReqSledCommodityOption& option, const int32_t pageIndex, const int32_t pageSize
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void reqContractVersion(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::ContractVersionPage& _return
    , const  ::xueqiao::contract::ReqContractVersionOption& option, const int32_t pageIndex, const int32_t pageSize
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void updateContractVersion(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::contract::ContractVersion& contractVersion
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void removeContractVersion(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const int32_t versionId
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void addDbLocking(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const  ::xueqiao::contract::DbLockingInfo& dbLockingInfo
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void removeDbLocking(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    , const std::string& lockedBy
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
  void reqDbLockingInfo(
    const ::soldier::svr_platform::TPrepareSyncCallArgs& platformCallArgs
    ,  ::xueqiao::contract::DbLockingInfo& _return
    ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException);
};

}}}} // namespace
#endif
