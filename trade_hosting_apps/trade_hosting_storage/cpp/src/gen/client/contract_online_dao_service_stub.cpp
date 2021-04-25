#include "contract_online_dao_service_stub.h"
#include "ContractOnlineDaoService.h"
#include "ContractOnlineDaoService_variables.h"
using namespace soldier::svr_platform;

namespace xueqiao { namespace contract { namespace online { namespace dao {
ContractOnlineDaoServiceStub::ContractOnlineDaoServiceStub()
  :TStubBase(ContractOnlineDaoService_service_key) {
}
void ContractOnlineDaoServiceStub::reqSledContract(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::standard::SledContractPage& _return
  , const  ::xueqiao::contract::standard::ReqSledContractOption& option, const int32_t pageIndex, const int32_t pageSize
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqSledContract(_return, callResp.platform_args, option, pageIndex, pageSize);
}

void ContractOnlineDaoServiceStub::reqCommodityMapping(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::standard::CommodityMappingPage& _return
  , const  ::xueqiao::contract::standard::ReqCommodityMappingOption& option, const int32_t pageIndex, const int32_t pageSize
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqCommodityMapping(_return, callResp.platform_args, option, pageIndex, pageSize);
}

void ContractOnlineDaoServiceStub::reqSledExchange(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::standard::SledExchangePage& _return
  , const  ::xueqiao::contract::standard::ReqSledExchangeOption& option, const int32_t pageIndex, const int32_t pageSize
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqSledExchange(_return, callResp.platform_args, option, pageIndex, pageSize);
}

void ContractOnlineDaoServiceStub::reqSledCommodity(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::standard::SledCommodityPage& _return
  , const  ::xueqiao::contract::standard::ReqSledCommodityOption& option, const int32_t pageIndex, const int32_t pageSize
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqSledCommodity(_return, callResp.platform_args, option, pageIndex, pageSize);
}

void ContractOnlineDaoServiceStub::reqContractVersion(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::ContractVersionPage& _return
  , const  ::xueqiao::contract::ReqContractVersionOption& option, const int32_t pageIndex, const int32_t pageSize
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqContractVersion(_return, callResp.platform_args, option, pageIndex, pageSize);
}

void ContractOnlineDaoServiceStub::updateContractVersion(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::contract::ContractVersion& contractVersion
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.updateContractVersion(callResp.platform_args, contractVersion);
}

void ContractOnlineDaoServiceStub::removeContractVersion(
  const TPrepareSyncCallArgs& platformCallArgs
  , const int32_t versionId
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.removeContractVersion(callResp.platform_args, versionId);
}

void ContractOnlineDaoServiceStub::addDbLocking(
  const TPrepareSyncCallArgs& platformCallArgs
  , const  ::xueqiao::contract::DbLockingInfo& dbLockingInfo
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.addDbLocking(callResp.platform_args, dbLockingInfo);
}

void ContractOnlineDaoServiceStub::removeDbLocking(
  const TPrepareSyncCallArgs& platformCallArgs
  , const std::string& lockedBy
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.removeDbLocking(callResp.platform_args, lockedBy);
}

void ContractOnlineDaoServiceStub::reqDbLockingInfo(
  const TPrepareSyncCallArgs& platformCallArgs
  ,  ::xueqiao::contract::DbLockingInfo& _return
  ) throw(::platform::comm::ErrorInfo, ::apache::thrift::TException) {
  TPrepareSyncCallResp callResp;
  prepareSyncCall(platformCallArgs, callResp);
  ContractOnlineDaoServiceClient client(callResp.protocol);
  client.reqDbLockingInfo(_return, callResp.platform_args);
}


}}}} // namespace
