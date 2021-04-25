package xueqiao.hosting.machine.dao.client;

import xueqiao.hosting.machine.dao.HostingMachineDao;
import xueqiao.hosting.machine.dao.HostingMachineDaoVariable;
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

public class HostingMachineDaoAsyncStub extends BaseStub { 
  public HostingMachineDaoAsyncStub() {
    super(HostingMachineDaoVariable.serviceKey);
  }
  public void send_addHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine newMachine) throws TException {
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
        create_addHostingMachineServiceCall(routeKey, timeout, platformArgs, newMachine), new TRequestOption());
  }

  public void send_addHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine newMachine,TRequestOption requestOption) throws TException { 
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
        create_addHostingMachineServiceCall(routeKey, timeout, platformArgs, newMachine), requestOption);
  }

  public long addHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine newMachine, IMethodCallback<HostingMachineDao.addHostingMachine_args, HostingMachineDao.addHostingMachine_result> callback) throws TException{
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
            create_addHostingMachineServiceCall(routeKey, timeout, platformArgs, newMachine), callback);
  }

  public long add_addHostingMachineCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine newMachine, IMethodCallback<HostingMachineDao.addHostingMachine_args, HostingMachineDao.addHostingMachine_result> callback) throws TException{
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
            create_addHostingMachineServiceCall(routeKey, timeout, platformArgs, newMachine), callback);
  }

  protected TServiceCall create_addHostingMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.HostingMachine newMachine){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.addHostingMachine_args request = new HostingMachineDao.addHostingMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setNewMachine(newMachine);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addHostingMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.addHostingMachine_result.class);
    return serviceCall;
  }

  public void send_updateHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine updateMachine) throws TException {
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
        create_updateHostingMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), new TRequestOption());
  }

  public void send_updateHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine updateMachine,TRequestOption requestOption) throws TException { 
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
        create_updateHostingMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), requestOption);
  }

  public long updateHostingMachine(int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine updateMachine, IMethodCallback<HostingMachineDao.updateHostingMachine_args, HostingMachineDao.updateHostingMachine_result> callback) throws TException{
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
            create_updateHostingMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), callback);
  }

  public long add_updateHostingMachineCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.HostingMachine updateMachine, IMethodCallback<HostingMachineDao.updateHostingMachine_args, HostingMachineDao.updateHostingMachine_result> callback) throws TException{
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
            create_updateHostingMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), callback);
  }

  protected TServiceCall create_updateHostingMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.HostingMachine updateMachine){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.updateHostingMachine_args request = new HostingMachineDao.updateHostingMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateMachine(updateMachine);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateHostingMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.updateHostingMachine_result.class);
    return serviceCall;
  }

  public void send_deleteHostingMachine(int routeKey, int timeout, long machineId) throws TException {
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
        create_deleteHostingMachineServiceCall(routeKey, timeout, platformArgs, machineId), new TRequestOption());
  }

  public void send_deleteHostingMachine(int routeKey, int timeout, long machineId,TRequestOption requestOption) throws TException { 
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
        create_deleteHostingMachineServiceCall(routeKey, timeout, platformArgs, machineId), requestOption);
  }

  public long deleteHostingMachine(int routeKey, int timeout, long machineId, IMethodCallback<HostingMachineDao.deleteHostingMachine_args, HostingMachineDao.deleteHostingMachine_result> callback) throws TException{
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
            create_deleteHostingMachineServiceCall(routeKey, timeout, platformArgs, machineId), callback);
  }

  public long add_deleteHostingMachineCall(AsyncCallRunner runner, int routeKey, int timeout, long machineId, IMethodCallback<HostingMachineDao.deleteHostingMachine_args, HostingMachineDao.deleteHostingMachine_result> callback) throws TException{
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
            create_deleteHostingMachineServiceCall(routeKey, timeout, platformArgs, machineId), callback);
  }

  protected TServiceCall create_deleteHostingMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long machineId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.deleteHostingMachine_args request = new HostingMachineDao.deleteHostingMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setMachineId(machineId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteHostingMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.deleteHostingMachine_result.class);
    return serviceCall;
  }

  public void send_queryHostingMachinePage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryHostingMachinePageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryHostingMachinePage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryHostingMachinePageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryHostingMachinePage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<HostingMachineDao.queryHostingMachinePage_args, HostingMachineDao.queryHostingMachinePage_result> callback) throws TException{
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
            create_queryHostingMachinePageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryHostingMachinePageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<HostingMachineDao.queryHostingMachinePage_args, HostingMachineDao.queryHostingMachinePage_result> callback) throws TException{
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
            create_queryHostingMachinePageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryHostingMachinePageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.queryHostingMachinePage_args request = new HostingMachineDao.queryHostingMachinePage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryHostingMachinePage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.queryHostingMachinePage_result.class);
    return serviceCall;
  }

  public void send_addRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo) throws TException {
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
        create_addRelatedInfoServiceCall(routeKey, timeout, platformArgs, newRelatedInfo), new TRequestOption());
  }

  public void send_addRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo,TRequestOption requestOption) throws TException { 
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
        create_addRelatedInfoServiceCall(routeKey, timeout, platformArgs, newRelatedInfo), requestOption);
  }

  public long addRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo, IMethodCallback<HostingMachineDao.addRelatedInfo_args, HostingMachineDao.addRelatedInfo_result> callback) throws TException{
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
            create_addRelatedInfoServiceCall(routeKey, timeout, platformArgs, newRelatedInfo), callback);
  }

  public long add_addRelatedInfoCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo, IMethodCallback<HostingMachineDao.addRelatedInfo_args, HostingMachineDao.addRelatedInfo_result> callback) throws TException{
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
            create_addRelatedInfoServiceCall(routeKey, timeout, platformArgs, newRelatedInfo), callback);
  }

  protected TServiceCall create_addRelatedInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.addRelatedInfo_args request = new HostingMachineDao.addRelatedInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setNewRelatedInfo(newRelatedInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addRelatedInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.addRelatedInfo_result.class);
    return serviceCall;
  }

  public void send_updateRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo) throws TException {
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
        create_updateRelatedInfoServiceCall(routeKey, timeout, platformArgs, updateRelatedInfo), new TRequestOption());
  }

  public void send_updateRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo,TRequestOption requestOption) throws TException { 
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
        create_updateRelatedInfoServiceCall(routeKey, timeout, platformArgs, updateRelatedInfo), requestOption);
  }

  public long updateRelatedInfo(int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo, IMethodCallback<HostingMachineDao.updateRelatedInfo_args, HostingMachineDao.updateRelatedInfo_result> callback) throws TException{
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
            create_updateRelatedInfoServiceCall(routeKey, timeout, platformArgs, updateRelatedInfo), callback);
  }

  public long add_updateRelatedInfoCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo, IMethodCallback<HostingMachineDao.updateRelatedInfo_args, HostingMachineDao.updateRelatedInfo_result> callback) throws TException{
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
            create_updateRelatedInfoServiceCall(routeKey, timeout, platformArgs, updateRelatedInfo), callback);
  }

  protected TServiceCall create_updateRelatedInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.updateRelatedInfo_args request = new HostingMachineDao.updateRelatedInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateRelatedInfo(updateRelatedInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateRelatedInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.updateRelatedInfo_result.class);
    return serviceCall;
  }

  public void send_deleteRelatedInfo(int routeKey, int timeout, long relatedId) throws TException {
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
        create_deleteRelatedInfoServiceCall(routeKey, timeout, platformArgs, relatedId), new TRequestOption());
  }

  public void send_deleteRelatedInfo(int routeKey, int timeout, long relatedId,TRequestOption requestOption) throws TException { 
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
        create_deleteRelatedInfoServiceCall(routeKey, timeout, platformArgs, relatedId), requestOption);
  }

  public long deleteRelatedInfo(int routeKey, int timeout, long relatedId, IMethodCallback<HostingMachineDao.deleteRelatedInfo_args, HostingMachineDao.deleteRelatedInfo_result> callback) throws TException{
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
            create_deleteRelatedInfoServiceCall(routeKey, timeout, platformArgs, relatedId), callback);
  }

  public long add_deleteRelatedInfoCall(AsyncCallRunner runner, int routeKey, int timeout, long relatedId, IMethodCallback<HostingMachineDao.deleteRelatedInfo_args, HostingMachineDao.deleteRelatedInfo_result> callback) throws TException{
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
            create_deleteRelatedInfoServiceCall(routeKey, timeout, platformArgs, relatedId), callback);
  }

  protected TServiceCall create_deleteRelatedInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long relatedId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.deleteRelatedInfo_args request = new HostingMachineDao.deleteRelatedInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setRelatedId(relatedId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteRelatedInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.deleteRelatedInfo_result.class);
    return serviceCall;
  }

  public void send_queryRelatedInfoPage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryRelatedInfoPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryRelatedInfoPage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryRelatedInfoPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryRelatedInfoPage(int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<HostingMachineDao.queryRelatedInfoPage_args, HostingMachineDao.queryRelatedInfoPage_result> callback) throws TException{
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
            create_queryRelatedInfoPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryRelatedInfoPageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<HostingMachineDao.queryRelatedInfoPage_args, HostingMachineDao.queryRelatedInfoPage_result> callback) throws TException{
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
            create_queryRelatedInfoPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryRelatedInfoPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(HostingMachineDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    HostingMachineDao.queryRelatedInfoPage_args request = new HostingMachineDao.queryRelatedInfoPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryRelatedInfoPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(HostingMachineDao.queryRelatedInfoPage_result.class);
    return serviceCall;
  }

}
