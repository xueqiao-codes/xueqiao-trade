package com.longsheng.xueqiao.contract.online.dao.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.ContractOnlineDaoService;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.ContractOnlineDaoServiceVariable;

public class ContractOnlineDaoServiceStub extends BaseStub {

  public ContractOnlineDaoServiceStub() {
    super(ContractOnlineDaoServiceVariable.serviceKey);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage  reqSledContract(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledContract(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage  reqSledContract(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSledContract").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage>(){
    @Override
    public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqSledContract(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage  reqSledContract(int routeKey, int timeout,com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledContract(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage  reqSledContractDetail(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledContractDetail(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage  reqSledContractDetail(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSledContractDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage>(){
    @Override
    public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqSledContractDetail(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage  reqSledContractDetail(int routeKey, int timeout,com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledContractDetail(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage  reqCommodityMapping(com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqCommodityMapping(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage  reqCommodityMapping(com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqCommodityMapping").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage>(){
    @Override
    public com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqCommodityMapping(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage  reqCommodityMapping(int routeKey, int timeout,com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqCommodityMapping(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage  reqSledExchange(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledExchange(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage  reqSledExchange(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSledExchange").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage>(){
    @Override
    public com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqSledExchange(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage  reqSledExchange(int routeKey, int timeout,com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledExchange(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage  reqSledCommodity(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledCommodity(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage  reqSledCommodity(com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSledCommodity").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage>(){
    @Override
    public com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqSledCommodity(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage  reqSledCommodity(int routeKey, int timeout,com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledCommodity(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage  reqContractVersion(com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqContractVersion(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage  reqContractVersion(com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqContractVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage>(){
    @Override
    public com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqContractVersion(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage  reqContractVersion(int routeKey, int timeout,com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqContractVersion(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateContractVersion(com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateContractVersion(contractVersion, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateContractVersion(com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateContractVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new ContractOnlineDaoService.Client(protocol).updateContractVersion(platformArgs, contractVersion);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateContractVersion(int routeKey, int timeout,com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateContractVersion(contractVersion, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeContractVersion(int versionId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeContractVersion(versionId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeContractVersion(int versionId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeContractVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new ContractOnlineDaoService.Client(protocol).removeContractVersion(platformArgs, versionId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeContractVersion(int routeKey, int timeout,int versionId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeContractVersion(versionId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addDbLocking(com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addDbLocking(dbLockingInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addDbLocking(com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addDbLocking").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new ContractOnlineDaoService.Client(protocol).addDbLocking(platformArgs, dbLockingInfo);
      return null;
      }
    }, invokeInfo);
  }

  public void  addDbLocking(int routeKey, int timeout,com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addDbLocking(dbLockingInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeDbLocking(String lockedBy) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeDbLocking(lockedBy, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeDbLocking(String lockedBy,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeDbLocking").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new ContractOnlineDaoService.Client(protocol).removeDbLocking(platformArgs, lockedBy);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeDbLocking(int routeKey, int timeout,String lockedBy)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeDbLocking(lockedBy, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo  reqDbLockingInfo() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDbLockingInfo(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo  reqDbLockingInfo(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqDbLockingInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo>(){
    @Override
    public com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqDbLockingInfo(platformArgs);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo  reqDbLockingInfo(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDbLockingInfo(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage  reqSledTradeTime(com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledTradeTime(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage  reqSledTradeTime(com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSledTradeTime").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage>(){
    @Override
    public com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ContractOnlineDaoService.Client(protocol).reqSledTradeTime(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage  reqSledTradeTime(int routeKey, int timeout,com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSledTradeTime(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
