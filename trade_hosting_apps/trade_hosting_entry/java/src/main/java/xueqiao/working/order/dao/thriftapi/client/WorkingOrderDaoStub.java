package xueqiao.working.order.dao.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.working.order.dao.thriftapi.WorkingOrderStorage;
import xueqiao.working.order.dao.thriftapi.WorkingOrderStoragePage;
import xueqiao.working.order.dao.thriftapi.WorkingOrderDao;
import xueqiao.working.order.dao.thriftapi.WorkingOrderDaoVariable;

public class WorkingOrderDaoStub extends BaseStub {

  public WorkingOrderDaoStub() {
    super(WorkingOrderDaoVariable.serviceKey);
  }

  public long  addWorkingOrderStorage(WorkingOrderStorage workingOrderStorage) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addWorkingOrderStorage(workingOrderStorage, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addWorkingOrderStorage(WorkingOrderStorage workingOrderStorage,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addWorkingOrderStorage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WorkingOrderDao.Client(protocol).addWorkingOrderStorage(platformArgs, workingOrderStorage);
      }
    }, invokeInfo);
  }

  public long  addWorkingOrderStorage(int routeKey, int timeout,WorkingOrderStorage workingOrderStorage)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addWorkingOrderStorage(workingOrderStorage, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateWorkingOrderStorage(WorkingOrderStorage workingOrderStorage) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateWorkingOrderStorage(workingOrderStorage, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateWorkingOrderStorage(WorkingOrderStorage workingOrderStorage,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateWorkingOrderStorage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new WorkingOrderDao.Client(protocol).updateWorkingOrderStorage(platformArgs, workingOrderStorage);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateWorkingOrderStorage(int routeKey, int timeout,WorkingOrderStorage workingOrderStorage)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateWorkingOrderStorage(workingOrderStorage, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public WorkingOrderStoragePage  reqWorkingOrderInfo(xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqWorkingOrderInfo(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public WorkingOrderStoragePage  reqWorkingOrderInfo(xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqWorkingOrderInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<WorkingOrderStoragePage>(){
    @Override
    public WorkingOrderStoragePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WorkingOrderDao.Client(protocol).reqWorkingOrderInfo(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public WorkingOrderStoragePage  reqWorkingOrderInfo(int routeKey, int timeout,xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqWorkingOrderInfo(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
