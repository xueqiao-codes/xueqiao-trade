package xueqiao.hosting.machine.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.hosting.machine.dao.HostingMachineDao;
import xueqiao.hosting.machine.dao.HostingMachineDaoVariable;

public class HostingMachineDaoStub extends BaseStub {

  public HostingMachineDaoStub() {
    super(HostingMachineDaoVariable.serviceKey);
  }

  public long  addHostingMachine(xueqiao.hosting.machine.HostingMachine newMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addHostingMachine(newMachine, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addHostingMachine(xueqiao.hosting.machine.HostingMachine newMachine,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addHostingMachine").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new HostingMachineDao.Client(protocol).addHostingMachine(platformArgs, newMachine);
      }
    }, invokeInfo);
  }

  public long  addHostingMachine(int routeKey, int timeout,xueqiao.hosting.machine.HostingMachine newMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addHostingMachine(newMachine, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingMachine(xueqiao.hosting.machine.HostingMachine updateMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingMachine(updateMachine, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingMachine(xueqiao.hosting.machine.HostingMachine updateMachine,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateHostingMachine").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new HostingMachineDao.Client(protocol).updateHostingMachine(platformArgs, updateMachine);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateHostingMachine(int routeKey, int timeout,xueqiao.hosting.machine.HostingMachine updateMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingMachine(updateMachine, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteHostingMachine(long machineId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteHostingMachine(machineId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteHostingMachine(long machineId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteHostingMachine").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new HostingMachineDao.Client(protocol).deleteHostingMachine(platformArgs, machineId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteHostingMachine(int routeKey, int timeout,long machineId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteHostingMachine(machineId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.hosting.machine.HostingMachinePageResult  queryHostingMachinePage(xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHostingMachinePage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.hosting.machine.HostingMachinePageResult  queryHostingMachinePage(xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryHostingMachinePage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.hosting.machine.HostingMachinePageResult>(){
    @Override
    public xueqiao.hosting.machine.HostingMachinePageResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new HostingMachineDao.Client(protocol).queryHostingMachinePage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.hosting.machine.HostingMachinePageResult  queryHostingMachinePage(int routeKey, int timeout,xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHostingMachinePage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addRelatedInfo(xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addRelatedInfo(newRelatedInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addRelatedInfo(xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addRelatedInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new HostingMachineDao.Client(protocol).addRelatedInfo(platformArgs, newRelatedInfo);
      }
    }, invokeInfo);
  }

  public long  addRelatedInfo(int routeKey, int timeout,xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addRelatedInfo(newRelatedInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateRelatedInfo(xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateRelatedInfo(updateRelatedInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateRelatedInfo(xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateRelatedInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new HostingMachineDao.Client(protocol).updateRelatedInfo(platformArgs, updateRelatedInfo);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateRelatedInfo(int routeKey, int timeout,xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateRelatedInfo(updateRelatedInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteRelatedInfo(long relatedId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteRelatedInfo(relatedId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteRelatedInfo(long relatedId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteRelatedInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new HostingMachineDao.Client(protocol).deleteRelatedInfo(platformArgs, relatedId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteRelatedInfo(int routeKey, int timeout,long relatedId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteRelatedInfo(relatedId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.hosting.machine.HostingRelatedInfoPageResult  queryRelatedInfoPage(xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryRelatedInfoPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.hosting.machine.HostingRelatedInfoPageResult  queryRelatedInfoPage(xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryRelatedInfoPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.hosting.machine.HostingRelatedInfoPageResult>(){
    @Override
    public xueqiao.hosting.machine.HostingRelatedInfoPageResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new HostingMachineDao.Client(protocol).queryRelatedInfoPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.hosting.machine.HostingRelatedInfoPageResult  queryRelatedInfoPage(int routeKey, int timeout,xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryRelatedInfoPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
