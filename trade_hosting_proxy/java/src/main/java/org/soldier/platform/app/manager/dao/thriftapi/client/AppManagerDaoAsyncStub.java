package org.soldier.platform.app.manager.dao.thriftapi.client;

import org.soldier.platform.app.manager.dao.thriftapi.AppManagerDao;
import org.soldier.platform.app.manager.dao.thriftapi.AppManagerDaoVariable;
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
import java.util.List;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppVersionOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqProjectOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqServerAppSupportOption;

public class AppManagerDaoAsyncStub extends BaseStub { 
  public AppManagerDaoAsyncStub() {
    super(AppManagerDaoVariable.serviceKey);
  }
  public void send_reqProject(int routeKey, int timeout, ReqProjectOption option) throws TException {
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
        create_reqProjectServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_reqProject(int routeKey, int timeout, ReqProjectOption option,TRequestOption requestOption) throws TException { 
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
        create_reqProjectServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long reqProject(int routeKey, int timeout, ReqProjectOption option, IMethodCallback<AppManagerDao.reqProject_args, AppManagerDao.reqProject_result> callback) throws TException{
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
            create_reqProjectServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_reqProjectCall(AsyncCallRunner runner, int routeKey, int timeout, ReqProjectOption option, IMethodCallback<AppManagerDao.reqProject_args, AppManagerDao.reqProject_result> callback) throws TException{
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
            create_reqProjectServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_reqProjectServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqProjectOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.reqProject_args request = new AppManagerDao.reqProject_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqProject");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.reqProject_result.class);
    return serviceCall;
  }

  public void send_addProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project) throws TException {
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
        create_addProjectServiceCall(routeKey, timeout, platformArgs, project), new TRequestOption());
  }

  public void send_addProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project,TRequestOption requestOption) throws TException { 
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
        create_addProjectServiceCall(routeKey, timeout, platformArgs, project), requestOption);
  }

  public long addProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project, IMethodCallback<AppManagerDao.addProject_args, AppManagerDao.addProject_result> callback) throws TException{
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
            create_addProjectServiceCall(routeKey, timeout, platformArgs, project), callback);
  }

  public long add_addProjectCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project, IMethodCallback<AppManagerDao.addProject_args, AppManagerDao.addProject_result> callback) throws TException{
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
            create_addProjectServiceCall(routeKey, timeout, platformArgs, project), callback);
  }

  protected TServiceCall create_addProjectServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.Project project){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.addProject_args request = new AppManagerDao.addProject_args();
    request.setPlatformArgs(platformArgs);
    request.setProject(project);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addProject");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.addProject_result.class);
    return serviceCall;
  }

  public void send_updateProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project) throws TException {
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
        create_updateProjectServiceCall(routeKey, timeout, platformArgs, project), new TRequestOption());
  }

  public void send_updateProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project,TRequestOption requestOption) throws TException { 
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
        create_updateProjectServiceCall(routeKey, timeout, platformArgs, project), requestOption);
  }

  public long updateProject(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project, IMethodCallback<AppManagerDao.updateProject_args, AppManagerDao.updateProject_result> callback) throws TException{
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
            create_updateProjectServiceCall(routeKey, timeout, platformArgs, project), callback);
  }

  public long add_updateProjectCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.Project project, IMethodCallback<AppManagerDao.updateProject_args, AppManagerDao.updateProject_result> callback) throws TException{
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
            create_updateProjectServiceCall(routeKey, timeout, platformArgs, project), callback);
  }

  protected TServiceCall create_updateProjectServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.Project project){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.updateProject_args request = new AppManagerDao.updateProject_args();
    request.setPlatformArgs(platformArgs);
    request.setProject(project);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateProject");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.updateProject_result.class);
    return serviceCall;
  }

  public void send_removeProject(int routeKey, int timeout, long projectId) throws TException {
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
        create_removeProjectServiceCall(routeKey, timeout, platformArgs, projectId), new TRequestOption());
  }

  public void send_removeProject(int routeKey, int timeout, long projectId,TRequestOption requestOption) throws TException { 
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
        create_removeProjectServiceCall(routeKey, timeout, platformArgs, projectId), requestOption);
  }

  public long removeProject(int routeKey, int timeout, long projectId, IMethodCallback<AppManagerDao.removeProject_args, AppManagerDao.removeProject_result> callback) throws TException{
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
            create_removeProjectServiceCall(routeKey, timeout, platformArgs, projectId), callback);
  }

  public long add_removeProjectCall(AsyncCallRunner runner, int routeKey, int timeout, long projectId, IMethodCallback<AppManagerDao.removeProject_args, AppManagerDao.removeProject_result> callback) throws TException{
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
            create_removeProjectServiceCall(routeKey, timeout, platformArgs, projectId), callback);
  }

  protected TServiceCall create_removeProjectServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long projectId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.removeProject_args request = new AppManagerDao.removeProject_args();
    request.setPlatformArgs(platformArgs);
    request.setProjectId(projectId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeProject");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.removeProject_result.class);
    return serviceCall;
  }

  public void send_reqApp(int routeKey, int timeout, ReqAppOption option) throws TException {
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
        create_reqAppServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_reqApp(int routeKey, int timeout, ReqAppOption option,TRequestOption requestOption) throws TException { 
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
        create_reqAppServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long reqApp(int routeKey, int timeout, ReqAppOption option, IMethodCallback<AppManagerDao.reqApp_args, AppManagerDao.reqApp_result> callback) throws TException{
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
            create_reqAppServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_reqAppCall(AsyncCallRunner runner, int routeKey, int timeout, ReqAppOption option, IMethodCallback<AppManagerDao.reqApp_args, AppManagerDao.reqApp_result> callback) throws TException{
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
            create_reqAppServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_reqAppServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqAppOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.reqApp_args request = new AppManagerDao.reqApp_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqApp");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.reqApp_result.class);
    return serviceCall;
  }

  public void send_addApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app) throws TException {
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
        create_addAppServiceCall(routeKey, timeout, platformArgs, app), new TRequestOption());
  }

  public void send_addApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app,TRequestOption requestOption) throws TException { 
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
        create_addAppServiceCall(routeKey, timeout, platformArgs, app), requestOption);
  }

  public long addApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app, IMethodCallback<AppManagerDao.addApp_args, AppManagerDao.addApp_result> callback) throws TException{
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
            create_addAppServiceCall(routeKey, timeout, platformArgs, app), callback);
  }

  public long add_addAppCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app, IMethodCallback<AppManagerDao.addApp_args, AppManagerDao.addApp_result> callback) throws TException{
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
            create_addAppServiceCall(routeKey, timeout, platformArgs, app), callback);
  }

  protected TServiceCall create_addAppServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.App app){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.addApp_args request = new AppManagerDao.addApp_args();
    request.setPlatformArgs(platformArgs);
    request.setApp(app);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addApp");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.addApp_result.class);
    return serviceCall;
  }

  public void send_updateApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app) throws TException {
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
        create_updateAppServiceCall(routeKey, timeout, platformArgs, app), new TRequestOption());
  }

  public void send_updateApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app,TRequestOption requestOption) throws TException { 
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
        create_updateAppServiceCall(routeKey, timeout, platformArgs, app), requestOption);
  }

  public long updateApp(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app, IMethodCallback<AppManagerDao.updateApp_args, AppManagerDao.updateApp_result> callback) throws TException{
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
            create_updateAppServiceCall(routeKey, timeout, platformArgs, app), callback);
  }

  public long add_updateAppCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.App app, IMethodCallback<AppManagerDao.updateApp_args, AppManagerDao.updateApp_result> callback) throws TException{
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
            create_updateAppServiceCall(routeKey, timeout, platformArgs, app), callback);
  }

  protected TServiceCall create_updateAppServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.App app){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.updateApp_args request = new AppManagerDao.updateApp_args();
    request.setPlatformArgs(platformArgs);
    request.setApp(app);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateApp");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.updateApp_result.class);
    return serviceCall;
  }

  public void send_removeApp(int routeKey, int timeout, long appId) throws TException {
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
        create_removeAppServiceCall(routeKey, timeout, platformArgs, appId), new TRequestOption());
  }

  public void send_removeApp(int routeKey, int timeout, long appId,TRequestOption requestOption) throws TException { 
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
        create_removeAppServiceCall(routeKey, timeout, platformArgs, appId), requestOption);
  }

  public long removeApp(int routeKey, int timeout, long appId, IMethodCallback<AppManagerDao.removeApp_args, AppManagerDao.removeApp_result> callback) throws TException{
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
            create_removeAppServiceCall(routeKey, timeout, platformArgs, appId), callback);
  }

  public long add_removeAppCall(AsyncCallRunner runner, int routeKey, int timeout, long appId, IMethodCallback<AppManagerDao.removeApp_args, AppManagerDao.removeApp_result> callback) throws TException{
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
            create_removeAppServiceCall(routeKey, timeout, platformArgs, appId), callback);
  }

  protected TServiceCall create_removeAppServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long appId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.removeApp_args request = new AppManagerDao.removeApp_args();
    request.setPlatformArgs(platformArgs);
    request.setAppId(appId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeApp");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.removeApp_result.class);
    return serviceCall;
  }

  public void send_reqAppVersion(int routeKey, int timeout, ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqAppVersionServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqAppVersion(int routeKey, int timeout, ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqAppVersionServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqAppVersion(int routeKey, int timeout, ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<AppManagerDao.reqAppVersion_args, AppManagerDao.reqAppVersion_result> callback) throws TException{
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
            create_reqAppVersionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqAppVersionCall(AsyncCallRunner runner, int routeKey, int timeout, ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<AppManagerDao.reqAppVersion_args, AppManagerDao.reqAppVersion_result> callback) throws TException{
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
            create_reqAppVersionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqAppVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.reqAppVersion_args request = new AppManagerDao.reqAppVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqAppVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.reqAppVersion_result.class);
    return serviceCall;
  }

  public void send_addAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion) throws TException {
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
        create_addAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), new TRequestOption());
  }

  public void send_addAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion,TRequestOption requestOption) throws TException { 
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
        create_addAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), requestOption);
  }

  public long addAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion, IMethodCallback<AppManagerDao.addAppVersion_args, AppManagerDao.addAppVersion_result> callback) throws TException{
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
            create_addAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), callback);
  }

  public long add_addAppVersionCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion, IMethodCallback<AppManagerDao.addAppVersion_args, AppManagerDao.addAppVersion_result> callback) throws TException{
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
            create_addAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), callback);
  }

  protected TServiceCall create_addAppVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.addAppVersion_args request = new AppManagerDao.addAppVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setAppVersion(appVersion);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addAppVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.addAppVersion_result.class);
    return serviceCall;
  }

  public void send_updateAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion) throws TException {
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
        create_updateAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), new TRequestOption());
  }

  public void send_updateAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion,TRequestOption requestOption) throws TException { 
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
        create_updateAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), requestOption);
  }

  public long updateAppVersion(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion, IMethodCallback<AppManagerDao.updateAppVersion_args, AppManagerDao.updateAppVersion_result> callback) throws TException{
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
            create_updateAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), callback);
  }

  public long add_updateAppVersionCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion, IMethodCallback<AppManagerDao.updateAppVersion_args, AppManagerDao.updateAppVersion_result> callback) throws TException{
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
            create_updateAppVersionServiceCall(routeKey, timeout, platformArgs, appVersion), callback);
  }

  protected TServiceCall create_updateAppVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.AppVersion appVersion){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.updateAppVersion_args request = new AppManagerDao.updateAppVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setAppVersion(appVersion);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateAppVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.updateAppVersion_result.class);
    return serviceCall;
  }

  public void send_removeAppVersion(int routeKey, int timeout, long appVersionId) throws TException {
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
        create_removeAppVersionServiceCall(routeKey, timeout, platformArgs, appVersionId), new TRequestOption());
  }

  public void send_removeAppVersion(int routeKey, int timeout, long appVersionId,TRequestOption requestOption) throws TException { 
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
        create_removeAppVersionServiceCall(routeKey, timeout, platformArgs, appVersionId), requestOption);
  }

  public long removeAppVersion(int routeKey, int timeout, long appVersionId, IMethodCallback<AppManagerDao.removeAppVersion_args, AppManagerDao.removeAppVersion_result> callback) throws TException{
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
            create_removeAppVersionServiceCall(routeKey, timeout, platformArgs, appVersionId), callback);
  }

  public long add_removeAppVersionCall(AsyncCallRunner runner, int routeKey, int timeout, long appVersionId, IMethodCallback<AppManagerDao.removeAppVersion_args, AppManagerDao.removeAppVersion_result> callback) throws TException{
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
            create_removeAppVersionServiceCall(routeKey, timeout, platformArgs, appVersionId), callback);
  }

  protected TServiceCall create_removeAppVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long appVersionId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.removeAppVersion_args request = new AppManagerDao.removeAppVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setAppVersionId(appVersionId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeAppVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.removeAppVersion_result.class);
    return serviceCall;
  }

  public void send_reqServerAppSupport(int routeKey, int timeout, ReqServerAppSupportOption option) throws TException {
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
        create_reqServerAppSupportServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_reqServerAppSupport(int routeKey, int timeout, ReqServerAppSupportOption option,TRequestOption requestOption) throws TException { 
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
        create_reqServerAppSupportServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long reqServerAppSupport(int routeKey, int timeout, ReqServerAppSupportOption option, IMethodCallback<AppManagerDao.reqServerAppSupport_args, AppManagerDao.reqServerAppSupport_result> callback) throws TException{
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
            create_reqServerAppSupportServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_reqServerAppSupportCall(AsyncCallRunner runner, int routeKey, int timeout, ReqServerAppSupportOption option, IMethodCallback<AppManagerDao.reqServerAppSupport_args, AppManagerDao.reqServerAppSupport_result> callback) throws TException{
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
            create_reqServerAppSupportServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_reqServerAppSupportServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqServerAppSupportOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.reqServerAppSupport_args request = new AppManagerDao.reqServerAppSupport_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqServerAppSupport");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.reqServerAppSupport_result.class);
    return serviceCall;
  }

  public void send_addServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support) throws TException {
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
        create_addServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), new TRequestOption());
  }

  public void send_addServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support,TRequestOption requestOption) throws TException { 
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
        create_addServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), requestOption);
  }

  public long addServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support, IMethodCallback<AppManagerDao.addServerAppSupport_args, AppManagerDao.addServerAppSupport_result> callback) throws TException{
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
            create_addServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), callback);
  }

  public long add_addServerAppSupportCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support, IMethodCallback<AppManagerDao.addServerAppSupport_args, AppManagerDao.addServerAppSupport_result> callback) throws TException{
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
            create_addServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), callback);
  }

  protected TServiceCall create_addServerAppSupportServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.addServerAppSupport_args request = new AppManagerDao.addServerAppSupport_args();
    request.setPlatformArgs(platformArgs);
    request.setSupport(support);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addServerAppSupport");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.addServerAppSupport_result.class);
    return serviceCall;
  }

  public void send_updateServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support) throws TException {
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
        create_updateServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), new TRequestOption());
  }

  public void send_updateServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support,TRequestOption requestOption) throws TException { 
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
        create_updateServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), requestOption);
  }

  public long updateServerAppSupport(int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support, IMethodCallback<AppManagerDao.updateServerAppSupport_args, AppManagerDao.updateServerAppSupport_result> callback) throws TException{
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
            create_updateServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), callback);
  }

  public long add_updateServerAppSupportCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support, IMethodCallback<AppManagerDao.updateServerAppSupport_args, AppManagerDao.updateServerAppSupport_result> callback) throws TException{
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
            create_updateServerAppSupportServiceCall(routeKey, timeout, platformArgs, support), callback);
  }

  protected TServiceCall create_updateServerAppSupportServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.app.manager.thriftapi.ServerAppSupport support){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.updateServerAppSupport_args request = new AppManagerDao.updateServerAppSupport_args();
    request.setPlatformArgs(platformArgs);
    request.setSupport(support);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateServerAppSupport");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.updateServerAppSupport_result.class);
    return serviceCall;
  }

  public void send_removeServerAppSupport(int routeKey, int timeout, long serverVersionId, long supportClientId) throws TException {
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
        create_removeServerAppSupportServiceCall(routeKey, timeout, platformArgs, serverVersionId, supportClientId), new TRequestOption());
  }

  public void send_removeServerAppSupport(int routeKey, int timeout, long serverVersionId, long supportClientId,TRequestOption requestOption) throws TException { 
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
        create_removeServerAppSupportServiceCall(routeKey, timeout, platformArgs, serverVersionId, supportClientId), requestOption);
  }

  public long removeServerAppSupport(int routeKey, int timeout, long serverVersionId, long supportClientId, IMethodCallback<AppManagerDao.removeServerAppSupport_args, AppManagerDao.removeServerAppSupport_result> callback) throws TException{
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
            create_removeServerAppSupportServiceCall(routeKey, timeout, platformArgs, serverVersionId, supportClientId), callback);
  }

  public long add_removeServerAppSupportCall(AsyncCallRunner runner, int routeKey, int timeout, long serverVersionId, long supportClientId, IMethodCallback<AppManagerDao.removeServerAppSupport_args, AppManagerDao.removeServerAppSupport_result> callback) throws TException{
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
            create_removeServerAppSupportServiceCall(routeKey, timeout, platformArgs, serverVersionId, supportClientId), callback);
  }

  protected TServiceCall create_removeServerAppSupportServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long serverVersionId, long supportClientId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(AppManagerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    AppManagerDao.removeServerAppSupport_args request = new AppManagerDao.removeServerAppSupport_args();
    request.setPlatformArgs(platformArgs);
    request.setServerVersionId(serverVersionId);
    request.setSupportClientId(supportClientId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeServerAppSupport");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(AppManagerDao.removeServerAppSupport_result.class);
    return serviceCall;
  }

}
