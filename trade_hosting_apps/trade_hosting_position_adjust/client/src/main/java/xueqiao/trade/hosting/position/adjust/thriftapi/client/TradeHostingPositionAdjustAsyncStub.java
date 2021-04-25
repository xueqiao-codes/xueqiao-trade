package xueqiao.trade.hosting.position.adjust.thriftapi.client;

import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjust;
import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjustVariable;
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
import xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference;
import xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInputPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionManualInputOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.SettlementTimeRelateSledReqTime;

public class TradeHostingPositionAdjustAsyncStub extends BaseStub { 
  public TradeHostingPositionAdjustAsyncStub() {
    super(TradeHostingPositionAdjustVariable.serviceKey);
  }
  public void send_manualInputPosition(int routeKey, int timeout, List<PositionManualInput> positionManualInputs) throws TException {
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
        create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, positionManualInputs), new TRequestOption());
  }

  public void send_manualInputPosition(int routeKey, int timeout, List<PositionManualInput> positionManualInputs,TRequestOption requestOption) throws TException { 
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
        create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, positionManualInputs), requestOption);
  }

  public long manualInputPosition(int routeKey, int timeout, List<PositionManualInput> positionManualInputs, IMethodCallback<TradeHostingPositionAdjust.manualInputPosition_args, TradeHostingPositionAdjust.manualInputPosition_result> callback) throws TException{
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
            create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, positionManualInputs), callback);
  }

  public long add_manualInputPositionCall(AsyncCallRunner runner, int routeKey, int timeout, List<PositionManualInput> positionManualInputs, IMethodCallback<TradeHostingPositionAdjust.manualInputPosition_args, TradeHostingPositionAdjust.manualInputPosition_result> callback) throws TException{
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
            create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, positionManualInputs), callback);
  }

  protected TServiceCall create_manualInputPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<PositionManualInput> positionManualInputs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.manualInputPosition_args request = new TradeHostingPositionAdjust.manualInputPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setPositionManualInputs(positionManualInputs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("manualInputPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.manualInputPosition_result.class);
    return serviceCall;
  }

  public void send_reqManualInputPosition(int routeKey, int timeout, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqManualInputPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqManualInputPosition(int routeKey, int timeout, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqManualInputPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqManualInputPosition(int routeKey, int timeout, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqManualInputPosition_args, TradeHostingPositionAdjust.reqManualInputPosition_result> callback) throws TException{
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
            create_reqManualInputPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqManualInputPositionCall(AsyncCallRunner runner, int routeKey, int timeout, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqManualInputPosition_args, TradeHostingPositionAdjust.reqManualInputPosition_result> callback) throws TException{
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
            create_reqManualInputPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqManualInputPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqManualInputPosition_args request = new TradeHostingPositionAdjust.reqManualInputPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqManualInputPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqManualInputPosition_result.class);
    return serviceCall;
  }

  public void send_reqPositionUnassigned(int routeKey, int timeout, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionUnassigned(int routeKey, int timeout, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqPositionUnassigned(int routeKey, int timeout, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionUnassigned_args, TradeHostingPositionAdjust.reqPositionUnassigned_result> callback) throws TException{
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
            create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqPositionUnassignedCall(AsyncCallRunner runner, int routeKey, int timeout, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionUnassigned_args, TradeHostingPositionAdjust.reqPositionUnassigned_result> callback) throws TException{
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
            create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionUnassignedServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqPositionUnassigned_args request = new TradeHostingPositionAdjust.reqPositionUnassigned_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionUnassigned");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqPositionUnassigned_result.class);
    return serviceCall;
  }

  public void send_reqPositionAssigned(int routeKey, int timeout, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionAssigned(int routeKey, int timeout, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqPositionAssigned(int routeKey, int timeout, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionAssigned_args, TradeHostingPositionAdjust.reqPositionAssigned_result> callback) throws TException{
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
            create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqPositionAssignedCall(AsyncCallRunner runner, int routeKey, int timeout, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionAssigned_args, TradeHostingPositionAdjust.reqPositionAssigned_result> callback) throws TException{
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
            create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionAssignedServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqPositionAssigned_args request = new TradeHostingPositionAdjust.reqPositionAssigned_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionAssigned");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqPositionAssigned_result.class);
    return serviceCall;
  }

  public void send_assignPosition(int routeKey, int timeout, List<PositionAssignOption> assignOption) throws TException {
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, assignOption), new TRequestOption());
  }

  public void send_assignPosition(int routeKey, int timeout, List<PositionAssignOption> assignOption,TRequestOption requestOption) throws TException { 
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, assignOption), requestOption);
  }

  public long assignPosition(int routeKey, int timeout, List<PositionAssignOption> assignOption, IMethodCallback<TradeHostingPositionAdjust.assignPosition_args, TradeHostingPositionAdjust.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, assignOption), callback);
  }

  public long add_assignPositionCall(AsyncCallRunner runner, int routeKey, int timeout, List<PositionAssignOption> assignOption, IMethodCallback<TradeHostingPositionAdjust.assignPosition_args, TradeHostingPositionAdjust.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, assignOption), callback);
  }

  protected TServiceCall create_assignPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<PositionAssignOption> assignOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.assignPosition_args request = new TradeHostingPositionAdjust.assignPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setAssignOption(assignOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("assignPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.assignPosition_result.class);
    return serviceCall;
  }

  public void send_reqPositionEditLock(int routeKey, int timeout, String lockKey) throws TException {
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
        create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, lockKey), new TRequestOption());
  }

  public void send_reqPositionEditLock(int routeKey, int timeout, String lockKey,TRequestOption requestOption) throws TException { 
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
        create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, lockKey), requestOption);
  }

  public long reqPositionEditLock(int routeKey, int timeout, String lockKey, IMethodCallback<TradeHostingPositionAdjust.reqPositionEditLock_args, TradeHostingPositionAdjust.reqPositionEditLock_result> callback) throws TException{
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
            create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, lockKey), callback);
  }

  public long add_reqPositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, String lockKey, IMethodCallback<TradeHostingPositionAdjust.reqPositionEditLock_args, TradeHostingPositionAdjust.reqPositionEditLock_result> callback) throws TException{
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
            create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, lockKey), callback);
  }

  protected TServiceCall create_reqPositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String lockKey){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqPositionEditLock_args request = new TradeHostingPositionAdjust.reqPositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setLockKey(lockKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqPositionEditLock_result.class);
    return serviceCall;
  }

  public void send_addPositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock) throws TException {
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
        create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), new TRequestOption());
  }

  public void send_addPositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock,TRequestOption requestOption) throws TException { 
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
        create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), requestOption);
  }

  public long addPositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock, IMethodCallback<TradeHostingPositionAdjust.addPositionEditLock_args, TradeHostingPositionAdjust.addPositionEditLock_result> callback) throws TException{
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
            create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), callback);
  }

  public long add_addPositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, PositionEditLock positionEditLock, IMethodCallback<TradeHostingPositionAdjust.addPositionEditLock_args, TradeHostingPositionAdjust.addPositionEditLock_result> callback) throws TException{
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
            create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), callback);
  }

  protected TServiceCall create_addPositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, PositionEditLock positionEditLock){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.addPositionEditLock_args request = new TradeHostingPositionAdjust.addPositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setPositionEditLock(positionEditLock);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addPositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.addPositionEditLock_result.class);
    return serviceCall;
  }

  public void send_removePositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock) throws TException {
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
        create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), new TRequestOption());
  }

  public void send_removePositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock,TRequestOption requestOption) throws TException { 
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
        create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), requestOption);
  }

  public long removePositionEditLock(int routeKey, int timeout, PositionEditLock positionEditLock, IMethodCallback<TradeHostingPositionAdjust.removePositionEditLock_args, TradeHostingPositionAdjust.removePositionEditLock_result> callback) throws TException{
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
            create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), callback);
  }

  public long add_removePositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, PositionEditLock positionEditLock, IMethodCallback<TradeHostingPositionAdjust.removePositionEditLock_args, TradeHostingPositionAdjust.removePositionEditLock_result> callback) throws TException{
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
            create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, positionEditLock), callback);
  }

  protected TServiceCall create_removePositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, PositionEditLock positionEditLock){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.removePositionEditLock_args request = new TradeHostingPositionAdjust.removePositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setPositionEditLock(positionEditLock);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removePositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.removePositionEditLock_result.class);
    return serviceCall;
  }

  public void send_reqPositionVerify(int routeKey, int timeout, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionVerify(int routeKey, int timeout, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqPositionVerify(int routeKey, int timeout, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionVerify_args, TradeHostingPositionAdjust.reqPositionVerify_result> callback) throws TException{
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
            create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqPositionVerifyCall(AsyncCallRunner runner, int routeKey, int timeout, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionVerify_args, TradeHostingPositionAdjust.reqPositionVerify_result> callback) throws TException{
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
            create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionVerifyServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqPositionVerify_args request = new TradeHostingPositionAdjust.reqPositionVerify_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionVerify");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqPositionVerify_result.class);
    return serviceCall;
  }

  public void send_reqPositionDifference(int routeKey, int timeout, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionDifference(int routeKey, int timeout, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqPositionDifference(int routeKey, int timeout, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionDifference_args, TradeHostingPositionAdjust.reqPositionDifference_result> callback) throws TException{
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
            create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqPositionDifferenceCall(AsyncCallRunner runner, int routeKey, int timeout, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqPositionDifference_args, TradeHostingPositionAdjust.reqPositionDifference_result> callback) throws TException{
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
            create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionDifferenceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqPositionDifference_args request = new TradeHostingPositionAdjust.reqPositionDifference_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionDifference");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqPositionDifference_result.class);
    return serviceCall;
  }

  public void send_reqSettlementTimeRelateSledReqTime(int routeKey, int timeout, long tradeAccountId, String settlementDate) throws TException {
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
        create_reqSettlementTimeRelateSledReqTimeServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDate), new TRequestOption());
  }

  public void send_reqSettlementTimeRelateSledReqTime(int routeKey, int timeout, long tradeAccountId, String settlementDate,TRequestOption requestOption) throws TException { 
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
        create_reqSettlementTimeRelateSledReqTimeServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDate), requestOption);
  }

  public long reqSettlementTimeRelateSledReqTime(int routeKey, int timeout, long tradeAccountId, String settlementDate, IMethodCallback<TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_args, TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_result> callback) throws TException{
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
            create_reqSettlementTimeRelateSledReqTimeServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDate), callback);
  }

  public long add_reqSettlementTimeRelateSledReqTimeCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, String settlementDate, IMethodCallback<TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_args, TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_result> callback) throws TException{
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
            create_reqSettlementTimeRelateSledReqTimeServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDate), callback);
  }

  protected TServiceCall create_reqSettlementTimeRelateSledReqTimeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String settlementDate){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_args request = new TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    request.setSettlementDate(settlementDate);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSettlementTimeRelateSledReqTime");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqSettlementTimeRelateSledReqTime_result.class);
    return serviceCall;
  }

  public void send_reqDailyPositionDifference(int routeKey, int timeout, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqDailyPositionDifference(int routeKey, int timeout, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqDailyPositionDifference(int routeKey, int timeout, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqDailyPositionDifference_args, TradeHostingPositionAdjust.reqDailyPositionDifference_result> callback) throws TException{
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
            create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqDailyPositionDifferenceCall(AsyncCallRunner runner, int routeKey, int timeout, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionAdjust.reqDailyPositionDifference_args, TradeHostingPositionAdjust.reqDailyPositionDifference_result> callback) throws TException{
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
            create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqDailyPositionDifferenceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.reqDailyPositionDifference_args request = new TradeHostingPositionAdjust.reqDailyPositionDifference_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqDailyPositionDifference");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.reqDailyPositionDifference_result.class);
    return serviceCall;
  }

  public void send_updateDailyPositionDifferenceNote(int routeKey, int timeout, DailyPositionDifference difference) throws TException {
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
        create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, difference), new TRequestOption());
  }

  public void send_updateDailyPositionDifferenceNote(int routeKey, int timeout, DailyPositionDifference difference,TRequestOption requestOption) throws TException { 
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
        create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, difference), requestOption);
  }

  public long updateDailyPositionDifferenceNote(int routeKey, int timeout, DailyPositionDifference difference, IMethodCallback<TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_args, TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_result> callback) throws TException{
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
            create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, difference), callback);
  }

  public long add_updateDailyPositionDifferenceNoteCall(AsyncCallRunner runner, int routeKey, int timeout, DailyPositionDifference difference, IMethodCallback<TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_args, TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_result> callback) throws TException{
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
            create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, difference), callback);
  }

  protected TServiceCall create_updateDailyPositionDifferenceNoteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DailyPositionDifference difference){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_args request = new TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_args();
    request.setPlatformArgs(platformArgs);
    request.setDifference(difference);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDailyPositionDifferenceNote");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.updateDailyPositionDifferenceNote_result.class);
    return serviceCall;
  }

  public void send_clearAll(int routeKey, int timeout) throws TException {
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
        create_clearAllServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_clearAll(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_clearAllServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingPositionAdjust.clearAll_args, TradeHostingPositionAdjust.clearAll_result> callback) throws TException{
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
            create_clearAllServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingPositionAdjust.clearAll_args, TradeHostingPositionAdjust.clearAll_result> callback) throws TException{
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
            create_clearAllServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_clearAllServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionAdjustVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionAdjust.clearAll_args request = new TradeHostingPositionAdjust.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionAdjust.clearAll_result.class);
    return serviceCall;
  }

}
