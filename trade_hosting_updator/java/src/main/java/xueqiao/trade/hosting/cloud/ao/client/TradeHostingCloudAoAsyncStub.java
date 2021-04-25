package xueqiao.trade.hosting.cloud.ao.client;

import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAo;
import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAoVariable;
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
import xueqiao.trade.hosting.cloud.ao.HostingInitReq;
import xueqiao.trade.hosting.cloud.ao.HostingInitResp;
import xueqiao.trade.hosting.cloud.ao.HostingResetReq;
import xueqiao.trade.hosting.cloud.ao.LoginReq;
import xueqiao.trade.hosting.cloud.ao.LoginResp;
import xueqiao.trade.hosting.cloud.ao.RegisterHostingUserResp;

public class TradeHostingCloudAoAsyncStub extends BaseStub { 
  public TradeHostingCloudAoAsyncStub() {
    super(TradeHostingCloudAoVariable.serviceKey);
  }
  public void send_initHosting(int routeKey, int timeout, HostingInitReq req) throws TException {
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
        create_initHostingServiceCall(routeKey, timeout, platformArgs, req), new TRequestOption());
  }

  public void send_initHosting(int routeKey, int timeout, HostingInitReq req,TRequestOption requestOption) throws TException { 
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
        create_initHostingServiceCall(routeKey, timeout, platformArgs, req), requestOption);
  }

  public long initHosting(int routeKey, int timeout, HostingInitReq req, IMethodCallback<TradeHostingCloudAo.initHosting_args, TradeHostingCloudAo.initHosting_result> callback) throws TException{
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
            create_initHostingServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  public long add_initHostingCall(AsyncCallRunner runner, int routeKey, int timeout, HostingInitReq req, IMethodCallback<TradeHostingCloudAo.initHosting_args, TradeHostingCloudAo.initHosting_result> callback) throws TException{
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
            create_initHostingServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  protected TServiceCall create_initHostingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingInitReq req){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.initHosting_args request = new TradeHostingCloudAo.initHosting_args();
    request.setPlatformArgs(platformArgs);
    request.setReq(req);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("initHosting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.initHosting_result.class);
    return serviceCall;
  }

  public void send_getHostingInfo(int routeKey, int timeout) throws TException {
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
        create_getHostingInfoServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getHostingInfo(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_getHostingInfoServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getHostingInfo(int routeKey, int timeout, IMethodCallback<TradeHostingCloudAo.getHostingInfo_args, TradeHostingCloudAo.getHostingInfo_result> callback) throws TException{
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
            create_getHostingInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getHostingInfoCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingCloudAo.getHostingInfo_args, TradeHostingCloudAo.getHostingInfo_result> callback) throws TException{
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
            create_getHostingInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getHostingInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.getHostingInfo_args request = new TradeHostingCloudAo.getHostingInfo_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.getHostingInfo_result.class);
    return serviceCall;
  }

  public void send_resetHosting(int routeKey, int timeout, HostingResetReq req) throws TException {
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
        create_resetHostingServiceCall(routeKey, timeout, platformArgs, req), new TRequestOption());
  }

  public void send_resetHosting(int routeKey, int timeout, HostingResetReq req,TRequestOption requestOption) throws TException { 
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
        create_resetHostingServiceCall(routeKey, timeout, platformArgs, req), requestOption);
  }

  public long resetHosting(int routeKey, int timeout, HostingResetReq req, IMethodCallback<TradeHostingCloudAo.resetHosting_args, TradeHostingCloudAo.resetHosting_result> callback) throws TException{
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
            create_resetHostingServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  public long add_resetHostingCall(AsyncCallRunner runner, int routeKey, int timeout, HostingResetReq req, IMethodCallback<TradeHostingCloudAo.resetHosting_args, TradeHostingCloudAo.resetHosting_result> callback) throws TException{
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
            create_resetHostingServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  protected TServiceCall create_resetHostingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingResetReq req){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.resetHosting_args request = new TradeHostingCloudAo.resetHosting_args();
    request.setPlatformArgs(platformArgs);
    request.setReq(req);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("resetHosting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.resetHosting_result.class);
    return serviceCall;
  }

  public void send_registerHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser newUser) throws TException {
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
        create_registerHostingUserServiceCall(routeKey, timeout, platformArgs, newUser), new TRequestOption());
  }

  public void send_registerHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser newUser,TRequestOption requestOption) throws TException { 
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
        create_registerHostingUserServiceCall(routeKey, timeout, platformArgs, newUser), requestOption);
  }

  public long registerHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser newUser, IMethodCallback<TradeHostingCloudAo.registerHostingUser_args, TradeHostingCloudAo.registerHostingUser_result> callback) throws TException{
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
            create_registerHostingUserServiceCall(routeKey, timeout, platformArgs, newUser), callback);
  }

  public long add_registerHostingUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingUser newUser, IMethodCallback<TradeHostingCloudAo.registerHostingUser_args, TradeHostingCloudAo.registerHostingUser_result> callback) throws TException{
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
            create_registerHostingUserServiceCall(routeKey, timeout, platformArgs, newUser), callback);
  }

  protected TServiceCall create_registerHostingUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingUser newUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.registerHostingUser_args request = new TradeHostingCloudAo.registerHostingUser_args();
    request.setPlatformArgs(platformArgs);
    request.setNewUser(newUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("registerHostingUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.registerHostingUser_result.class);
    return serviceCall;
  }

  public void send_updateHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser updateUser) throws TException {
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
        create_updateHostingUserServiceCall(routeKey, timeout, platformArgs, updateUser), new TRequestOption());
  }

  public void send_updateHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser updateUser,TRequestOption requestOption) throws TException { 
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
        create_updateHostingUserServiceCall(routeKey, timeout, platformArgs, updateUser), requestOption);
  }

  public long updateHostingUser(int routeKey, int timeout, xueqiao.trade.hosting.HostingUser updateUser, IMethodCallback<TradeHostingCloudAo.updateHostingUser_args, TradeHostingCloudAo.updateHostingUser_result> callback) throws TException{
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
            create_updateHostingUserServiceCall(routeKey, timeout, platformArgs, updateUser), callback);
  }

  public long add_updateHostingUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingUser updateUser, IMethodCallback<TradeHostingCloudAo.updateHostingUser_args, TradeHostingCloudAo.updateHostingUser_result> callback) throws TException{
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
            create_updateHostingUserServiceCall(routeKey, timeout, platformArgs, updateUser), callback);
  }

  protected TServiceCall create_updateHostingUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingUser updateUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.updateHostingUser_args request = new TradeHostingCloudAo.updateHostingUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateUser(updateUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateHostingUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.updateHostingUser_result.class);
    return serviceCall;
  }

  public void send_disableHostingUser(int routeKey, int timeout, int subUserId) throws TException {
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
        create_disableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), new TRequestOption());
  }

  public void send_disableHostingUser(int routeKey, int timeout, int subUserId,TRequestOption requestOption) throws TException { 
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
        create_disableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), requestOption);
  }

  public long disableHostingUser(int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingCloudAo.disableHostingUser_args, TradeHostingCloudAo.disableHostingUser_result> callback) throws TException{
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
            create_disableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  public long add_disableHostingUserCall(AsyncCallRunner runner, int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingCloudAo.disableHostingUser_args, TradeHostingCloudAo.disableHostingUser_result> callback) throws TException{
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
            create_disableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  protected TServiceCall create_disableHostingUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.disableHostingUser_args request = new TradeHostingCloudAo.disableHostingUser_args();
    request.setPlatformArgs(platformArgs);
    request.setSubUserId(subUserId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("disableHostingUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.disableHostingUser_result.class);
    return serviceCall;
  }

  public void send_getHostingUserPage(int routeKey, int timeout, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_getHostingUserPage(int routeKey, int timeout, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long getHostingUserPage(int routeKey, int timeout, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingCloudAo.getHostingUserPage_args, TradeHostingCloudAo.getHostingUserPage_result> callback) throws TException{
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
            create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_getHostingUserPageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingCloudAo.getHostingUserPage_args, TradeHostingCloudAo.getHostingUserPage_result> callback) throws TException{
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
            create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_getHostingUserPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.getHostingUserPage_args request = new TradeHostingCloudAo.getHostingUserPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingUserPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.getHostingUserPage_result.class);
    return serviceCall;
  }

  public void send_enableHostingUser(int routeKey, int timeout, int subUserId) throws TException {
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
        create_enableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), new TRequestOption());
  }

  public void send_enableHostingUser(int routeKey, int timeout, int subUserId,TRequestOption requestOption) throws TException { 
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
        create_enableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), requestOption);
  }

  public long enableHostingUser(int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingCloudAo.enableHostingUser_args, TradeHostingCloudAo.enableHostingUser_result> callback) throws TException{
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
            create_enableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  public long add_enableHostingUserCall(AsyncCallRunner runner, int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingCloudAo.enableHostingUser_args, TradeHostingCloudAo.enableHostingUser_result> callback) throws TException{
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
            create_enableHostingUserServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  protected TServiceCall create_enableHostingUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.enableHostingUser_args request = new TradeHostingCloudAo.enableHostingUser_args();
    request.setPlatformArgs(platformArgs);
    request.setSubUserId(subUserId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("enableHostingUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.enableHostingUser_result.class);
    return serviceCall;
  }

  public void send_login(int routeKey, int timeout, LoginReq req) throws TException {
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
        create_loginServiceCall(routeKey, timeout, platformArgs, req), new TRequestOption());
  }

  public void send_login(int routeKey, int timeout, LoginReq req,TRequestOption requestOption) throws TException { 
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
        create_loginServiceCall(routeKey, timeout, platformArgs, req), requestOption);
  }

  public long login(int routeKey, int timeout, LoginReq req, IMethodCallback<TradeHostingCloudAo.login_args, TradeHostingCloudAo.login_result> callback) throws TException{
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
            create_loginServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  public long add_loginCall(AsyncCallRunner runner, int routeKey, int timeout, LoginReq req, IMethodCallback<TradeHostingCloudAo.login_args, TradeHostingCloudAo.login_result> callback) throws TException{
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
            create_loginServiceCall(routeKey, timeout, platformArgs, req), callback);
  }

  protected TServiceCall create_loginServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LoginReq req){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.login_args request = new TradeHostingCloudAo.login_args();
    request.setPlatformArgs(platformArgs);
    request.setReq(req);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("login");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.login_result.class);
    return serviceCall;
  }

  public void send_checkSession(int routeKey, int timeout, xueqiao.trade.hosting.HostingSession session) throws TException {
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
        create_checkSessionServiceCall(routeKey, timeout, platformArgs, session), new TRequestOption());
  }

  public void send_checkSession(int routeKey, int timeout, xueqiao.trade.hosting.HostingSession session,TRequestOption requestOption) throws TException { 
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
        create_checkSessionServiceCall(routeKey, timeout, platformArgs, session), requestOption);
  }

  public long checkSession(int routeKey, int timeout, xueqiao.trade.hosting.HostingSession session, IMethodCallback<TradeHostingCloudAo.checkSession_args, TradeHostingCloudAo.checkSession_result> callback) throws TException{
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
            create_checkSessionServiceCall(routeKey, timeout, platformArgs, session), callback);
  }

  public long add_checkSessionCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingSession session, IMethodCallback<TradeHostingCloudAo.checkSession_args, TradeHostingCloudAo.checkSession_result> callback) throws TException{
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
            create_checkSessionServiceCall(routeKey, timeout, platformArgs, session), callback);
  }

  protected TServiceCall create_checkSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingSession session){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingCloudAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingCloudAo.checkSession_args request = new TradeHostingCloudAo.checkSession_args();
    request.setPlatformArgs(platformArgs);
    request.setSession(session);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("checkSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingCloudAo.checkSession_result.class);
    return serviceCall;
  }

}
