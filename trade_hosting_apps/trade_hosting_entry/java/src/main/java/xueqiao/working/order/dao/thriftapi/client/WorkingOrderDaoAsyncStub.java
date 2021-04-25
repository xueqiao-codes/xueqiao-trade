package xueqiao.working.order.dao.thriftapi.client;

import xueqiao.working.order.dao.thriftapi.WorkingOrderDao;
import xueqiao.working.order.dao.thriftapi.WorkingOrderDaoVariable;
import org.apache.thrift.TException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.client.BaseStub;
import xueqiao.working.order.dao.thriftapi.WorkingOrderStorage;
import xueqiao.working.order.dao.thriftapi.WorkingOrderStoragePage;

public class WorkingOrderDaoAsyncStub extends BaseStub { 
  public WorkingOrderDaoAsyncStub() {
    super(WorkingOrderDaoVariable.serviceKey);
  }
  public void send_addWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), new TRequestOption());
  }

  public void send_addWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), requestOption);
  }

  public long addWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage, IMethodCallback<WorkingOrderDao.addWorkingOrderStorage_args, WorkingOrderDao.addWorkingOrderStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), callback);
  }

  public long add_addWorkingOrderStorageCall(AsyncCallRunner runner, int routeKey, int timeout, WorkingOrderStorage workingOrderStorage, IMethodCallback<WorkingOrderDao.addWorkingOrderStorage_args, WorkingOrderDao.addWorkingOrderStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), callback);
  }

  protected TServiceCall create_addWorkingOrderStorageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WorkingOrderStorage workingOrderStorage){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WorkingOrderDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WorkingOrderDao.addWorkingOrderStorage_args request = new WorkingOrderDao.addWorkingOrderStorage_args();
    request.setPlatformArgs(platformArgs);
    request.setWorkingOrderStorage(workingOrderStorage);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addWorkingOrderStorage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WorkingOrderDao.addWorkingOrderStorage_result.class);
    return serviceCall;
  }

  public void send_updateWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), new TRequestOption());
  }

  public void send_updateWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), requestOption);
  }

  public long updateWorkingOrderStorage(int routeKey, int timeout, WorkingOrderStorage workingOrderStorage, IMethodCallback<WorkingOrderDao.updateWorkingOrderStorage_args, WorkingOrderDao.updateWorkingOrderStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), callback);
  }

  public long add_updateWorkingOrderStorageCall(AsyncCallRunner runner, int routeKey, int timeout, WorkingOrderStorage workingOrderStorage, IMethodCallback<WorkingOrderDao.updateWorkingOrderStorage_args, WorkingOrderDao.updateWorkingOrderStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateWorkingOrderStorageServiceCall(routeKey, timeout, platformArgs, workingOrderStorage), callback);
  }

  protected TServiceCall create_updateWorkingOrderStorageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WorkingOrderStorage workingOrderStorage){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WorkingOrderDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WorkingOrderDao.updateWorkingOrderStorage_args request = new WorkingOrderDao.updateWorkingOrderStorage_args();
    request.setPlatformArgs(platformArgs);
    request.setWorkingOrderStorage(workingOrderStorage);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateWorkingOrderStorage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WorkingOrderDao.updateWorkingOrderStorage_result.class);
    return serviceCall;
  }

  public void send_reqWorkingOrderInfo(int routeKey, int timeout, xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_reqWorkingOrderInfoServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqWorkingOrderInfo(int routeKey, int timeout, xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_reqWorkingOrderInfoServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqWorkingOrderInfo(int routeKey, int timeout, xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<WorkingOrderDao.reqWorkingOrderInfo_args, WorkingOrderDao.reqWorkingOrderInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_reqWorkingOrderInfoServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqWorkingOrderInfoCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<WorkingOrderDao.reqWorkingOrderInfo_args, WorkingOrderDao.reqWorkingOrderInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_reqWorkingOrderInfoServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqWorkingOrderInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.working.order.thriftapi.ReqWorkingOrderOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WorkingOrderDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WorkingOrderDao.reqWorkingOrderInfo_args request = new WorkingOrderDao.reqWorkingOrderInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqWorkingOrderInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WorkingOrderDao.reqWorkingOrderInfo_result.class);
    return serviceCall;
  }

}
