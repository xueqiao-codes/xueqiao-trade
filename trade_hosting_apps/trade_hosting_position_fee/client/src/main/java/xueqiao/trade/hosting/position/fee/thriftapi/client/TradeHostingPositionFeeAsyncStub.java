package xueqiao.trade.hosting.position.fee.thriftapi.client;

import xueqiao.trade.hosting.position.fee.thriftapi.TradeHostingPositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.TradeHostingPositionFeeVariable;
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
import xueqiao.trade.hosting.position.fee.thriftapi.PositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;

public class TradeHostingPositionFeeAsyncStub extends BaseStub { 
  public TradeHostingPositionFeeAsyncStub() {
    super(TradeHostingPositionFeeVariable.serviceKey);
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

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingPositionFee.clearAll_args, TradeHostingPositionFee.clearAll_result> callback) throws TException{
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

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingPositionFee.clearAll_args, TradeHostingPositionFee.clearAll_result> callback) throws TException{
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
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.clearAll_args request = new TradeHostingPositionFee.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.clearAll_result.class);
    return serviceCall;
  }

  public void send_setGeneralMarginSetting(int routeKey, int timeout, XQGeneralMarginSettings marginSettings) throws TException {
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
        create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), new TRequestOption());
  }

  public void send_setGeneralMarginSetting(int routeKey, int timeout, XQGeneralMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
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
        create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), requestOption);
  }

  public long setGeneralMarginSetting(int routeKey, int timeout, XQGeneralMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.setGeneralMarginSetting_args, TradeHostingPositionFee.setGeneralMarginSetting_result> callback) throws TException{
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
            create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  public long add_setGeneralMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQGeneralMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.setGeneralMarginSetting_args, TradeHostingPositionFee.setGeneralMarginSetting_result> callback) throws TException{
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
            create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  protected TServiceCall create_setGeneralMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQGeneralMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.setGeneralMarginSetting_args request = new TradeHostingPositionFee.setGeneralMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setGeneralMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.setGeneralMarginSetting_result.class);
    return serviceCall;
  }

  public void send_setGeneralCommissionSetting(int routeKey, int timeout, XQGeneralCommissionSettings commissionSettings) throws TException {
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
        create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), new TRequestOption());
  }

  public void send_setGeneralCommissionSetting(int routeKey, int timeout, XQGeneralCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
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
        create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), requestOption);
  }

  public long setGeneralCommissionSetting(int routeKey, int timeout, XQGeneralCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.setGeneralCommissionSetting_args, TradeHostingPositionFee.setGeneralCommissionSetting_result> callback) throws TException{
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
            create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  public long add_setGeneralCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQGeneralCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.setGeneralCommissionSetting_args, TradeHostingPositionFee.setGeneralCommissionSetting_result> callback) throws TException{
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
            create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  protected TServiceCall create_setGeneralCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQGeneralCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.setGeneralCommissionSetting_args request = new TradeHostingPositionFee.setGeneralCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setGeneralCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.setGeneralCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_addSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings) throws TException {
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
        create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), new TRequestOption());
  }

  public void send_addSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
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
        create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), requestOption);
  }

  public long addSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.addSpecMarginSetting_args, TradeHostingPositionFee.addSpecMarginSetting_result> callback) throws TException{
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
            create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  public long add_addSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.addSpecMarginSetting_args, TradeHostingPositionFee.addSpecMarginSetting_result> callback) throws TException{
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
            create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  protected TServiceCall create_addSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.addSpecMarginSetting_args request = new TradeHostingPositionFee.addSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.addSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_addSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings) throws TException {
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
        create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), new TRequestOption());
  }

  public void send_addSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
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
        create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), requestOption);
  }

  public long addSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.addSpecCommissionSetting_args, TradeHostingPositionFee.addSpecCommissionSetting_result> callback) throws TException{
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
            create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  public long add_addSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.addSpecCommissionSetting_args, TradeHostingPositionFee.addSpecCommissionSetting_result> callback) throws TException{
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
            create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  protected TServiceCall create_addSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.addSpecCommissionSetting_args request = new TradeHostingPositionFee.addSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.addSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_updateSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings) throws TException {
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
        create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), new TRequestOption());
  }

  public void send_updateSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
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
        create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), requestOption);
  }

  public long updateSpecMarginSetting(int routeKey, int timeout, XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.updateSpecMarginSetting_args, TradeHostingPositionFee.updateSpecMarginSetting_result> callback) throws TException{
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
            create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  public long add_updateSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingPositionFee.updateSpecMarginSetting_args, TradeHostingPositionFee.updateSpecMarginSetting_result> callback) throws TException{
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
            create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, marginSettings), callback);
  }

  protected TServiceCall create_updateSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.updateSpecMarginSetting_args request = new TradeHostingPositionFee.updateSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.updateSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_updateSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings) throws TException {
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
        create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), new TRequestOption());
  }

  public void send_updateSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
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
        create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), requestOption);
  }

  public long updateSpecCommissionSetting(int routeKey, int timeout, XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.updateSpecCommissionSetting_args, TradeHostingPositionFee.updateSpecCommissionSetting_result> callback) throws TException{
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
            create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  public long add_updateSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingPositionFee.updateSpecCommissionSetting_args, TradeHostingPositionFee.updateSpecCommissionSetting_result> callback) throws TException{
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
            create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, commissionSettings), callback);
  }

  protected TServiceCall create_updateSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.updateSpecCommissionSetting_args request = new TradeHostingPositionFee.updateSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.updateSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_deleteSpecMarginSetting(int routeKey, int timeout, long subAccountId, long commodityId) throws TException {
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
        create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), new TRequestOption());
  }

  public void send_deleteSpecMarginSetting(int routeKey, int timeout, long subAccountId, long commodityId,TRequestOption requestOption) throws TException { 
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
        create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), requestOption);
  }

  public long deleteSpecMarginSetting(int routeKey, int timeout, long subAccountId, long commodityId, IMethodCallback<TradeHostingPositionFee.deleteSpecMarginSetting_args, TradeHostingPositionFee.deleteSpecMarginSetting_result> callback) throws TException{
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
            create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), callback);
  }

  public long add_deleteSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, long commodityId, IMethodCallback<TradeHostingPositionFee.deleteSpecMarginSetting_args, TradeHostingPositionFee.deleteSpecMarginSetting_result> callback) throws TException{
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
            create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), callback);
  }

  protected TServiceCall create_deleteSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long commodityId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.deleteSpecMarginSetting_args request = new TradeHostingPositionFee.deleteSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setCommodityId(commodityId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.deleteSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_deleteSpecCommissionSetting(int routeKey, int timeout, long subAccountId, long commodityId) throws TException {
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
        create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), new TRequestOption());
  }

  public void send_deleteSpecCommissionSetting(int routeKey, int timeout, long subAccountId, long commodityId,TRequestOption requestOption) throws TException { 
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
        create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), requestOption);
  }

  public long deleteSpecCommissionSetting(int routeKey, int timeout, long subAccountId, long commodityId, IMethodCallback<TradeHostingPositionFee.deleteSpecCommissionSetting_args, TradeHostingPositionFee.deleteSpecCommissionSetting_result> callback) throws TException{
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
            create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), callback);
  }

  public long add_deleteSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, long commodityId, IMethodCallback<TradeHostingPositionFee.deleteSpecCommissionSetting_args, TradeHostingPositionFee.deleteSpecCommissionSetting_result> callback) throws TException{
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
            create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, subAccountId, commodityId), callback);
  }

  protected TServiceCall create_deleteSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long commodityId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.deleteSpecCommissionSetting_args request = new TradeHostingPositionFee.deleteSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setCommodityId(commodityId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.deleteSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_queryXQGeneralMarginSettings(int routeKey, int timeout, long subAccountId) throws TException {
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
        create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), new TRequestOption());
  }

  public void send_queryXQGeneralMarginSettings(int routeKey, int timeout, long subAccountId,TRequestOption requestOption) throws TException { 
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
        create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), requestOption);
  }

  public long queryXQGeneralMarginSettings(int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingPositionFee.queryXQGeneralMarginSettings_args, TradeHostingPositionFee.queryXQGeneralMarginSettings_result> callback) throws TException{
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
            create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  public long add_queryXQGeneralMarginSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingPositionFee.queryXQGeneralMarginSettings_args, TradeHostingPositionFee.queryXQGeneralMarginSettings_result> callback) throws TException{
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
            create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  protected TServiceCall create_queryXQGeneralMarginSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQGeneralMarginSettings_args request = new TradeHostingPositionFee.queryXQGeneralMarginSettings_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQGeneralMarginSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQGeneralMarginSettings_result.class);
    return serviceCall;
  }

  public void send_queryXQGeneralCommissionSettings(int routeKey, int timeout, long subAccountId) throws TException {
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
        create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), new TRequestOption());
  }

  public void send_queryXQGeneralCommissionSettings(int routeKey, int timeout, long subAccountId,TRequestOption requestOption) throws TException { 
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
        create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), requestOption);
  }

  public long queryXQGeneralCommissionSettings(int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingPositionFee.queryXQGeneralCommissionSettings_args, TradeHostingPositionFee.queryXQGeneralCommissionSettings_result> callback) throws TException{
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
            create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  public long add_queryXQGeneralCommissionSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingPositionFee.queryXQGeneralCommissionSettings_args, TradeHostingPositionFee.queryXQGeneralCommissionSettings_result> callback) throws TException{
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
            create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  protected TServiceCall create_queryXQGeneralCommissionSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQGeneralCommissionSettings_args request = new TradeHostingPositionFee.queryXQGeneralCommissionSettings_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQGeneralCommissionSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQGeneralCommissionSettings_result.class);
    return serviceCall;
  }

  public void send_queryXQSpecMarginSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQSpecMarginSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryXQSpecMarginSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQSpecMarginSettingPage_args, TradeHostingPositionFee.queryXQSpecMarginSettingPage_result> callback) throws TException{
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
            create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryXQSpecMarginSettingPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQSpecMarginSettingPage_args, TradeHostingPositionFee.queryXQSpecMarginSettingPage_result> callback) throws TException{
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
            create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQSpecMarginSettingPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQSpecMarginSettingPage_args request = new TradeHostingPositionFee.queryXQSpecMarginSettingPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQSpecMarginSettingPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQSpecMarginSettingPage_result.class);
    return serviceCall;
  }

  public void send_queryXQSpecCommissionSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQSpecCommissionSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryXQSpecCommissionSettingPage(int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQSpecCommissionSettingPage_args, TradeHostingPositionFee.queryXQSpecCommissionSettingPage_result> callback) throws TException{
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
            create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryXQSpecCommissionSettingPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQSpecCommissionSettingPage_args, TradeHostingPositionFee.queryXQSpecCommissionSettingPage_result> callback) throws TException{
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
            create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQSpecCommissionSettingPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQSpecCommissionSettingPage_args request = new TradeHostingPositionFee.queryXQSpecCommissionSettingPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQSpecCommissionSettingPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQSpecCommissionSettingPage_result.class);
    return serviceCall;
  }

  public void send_queryUpsideContractMarginPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryUpsideContractMarginPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryUpsideContractMarginPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryUpsideContractMarginPage_args, TradeHostingPositionFee.queryUpsideContractMarginPage_result> callback) throws TException{
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
            create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryUpsideContractMarginPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryUpsideContractMarginPage_args, TradeHostingPositionFee.queryUpsideContractMarginPage_result> callback) throws TException{
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
            create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryUpsideContractMarginPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryUpsideContractMarginPage_args request = new TradeHostingPositionFee.queryUpsideContractMarginPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUpsideContractMarginPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryUpsideContractMarginPage_result.class);
    return serviceCall;
  }

  public void send_queryUpsideContractCommissionPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryUpsideContractCommissionPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryUpsideContractCommissionPage(int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryUpsideContractCommissionPage_args, TradeHostingPositionFee.queryUpsideContractCommissionPage_result> callback) throws TException{
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
            create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryUpsideContractCommissionPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryUpsideContractCommissionPage_args, TradeHostingPositionFee.queryUpsideContractCommissionPage_result> callback) throws TException{
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
            create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryUpsideContractCommissionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryUpsideContractCommissionPage_args request = new TradeHostingPositionFee.queryUpsideContractCommissionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUpsideContractCommissionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryUpsideContractCommissionPage_result.class);
    return serviceCall;
  }

  public void send_queryXQContractMarginPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQContractMarginPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryXQContractMarginPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQContractMarginPage_args, TradeHostingPositionFee.queryXQContractMarginPage_result> callback) throws TException{
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
            create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryXQContractMarginPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQContractMarginPage_args, TradeHostingPositionFee.queryXQContractMarginPage_result> callback) throws TException{
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
            create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQContractMarginPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQContractMarginPage_args request = new TradeHostingPositionFee.queryXQContractMarginPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQContractMarginPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQContractMarginPage_result.class);
    return serviceCall;
  }

  public void send_queryXQContractCommissionPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQContractCommissionPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), requestOption);
  }

  public long queryXQContractCommissionPage(int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQContractCommissionPage_args, TradeHostingPositionFee.queryXQContractCommissionPage_result> callback) throws TException{
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
            create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  public long add_queryXQContractCommissionPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionFee.queryXQContractCommissionPage_args, TradeHostingPositionFee.queryXQContractCommissionPage_result> callback) throws TException{
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
            create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQContractCommissionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryXQContractCommissionPage_args request = new TradeHostingPositionFee.queryXQContractCommissionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQContractCommissionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryXQContractCommissionPage_result.class);
    return serviceCall;
  }

  public void send_queryPositionFee(int routeKey, int timeout, long subAccountId, long contractId) throws TException {
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
        create_queryPositionFeeServiceCall(routeKey, timeout, platformArgs, subAccountId, contractId), new TRequestOption());
  }

  public void send_queryPositionFee(int routeKey, int timeout, long subAccountId, long contractId,TRequestOption requestOption) throws TException { 
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
        create_queryPositionFeeServiceCall(routeKey, timeout, platformArgs, subAccountId, contractId), requestOption);
  }

  public long queryPositionFee(int routeKey, int timeout, long subAccountId, long contractId, IMethodCallback<TradeHostingPositionFee.queryPositionFee_args, TradeHostingPositionFee.queryPositionFee_result> callback) throws TException{
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
            create_queryPositionFeeServiceCall(routeKey, timeout, platformArgs, subAccountId, contractId), callback);
  }

  public long add_queryPositionFeeCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, long contractId, IMethodCallback<TradeHostingPositionFee.queryPositionFee_args, TradeHostingPositionFee.queryPositionFee_result> callback) throws TException{
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
            create_queryPositionFeeServiceCall(routeKey, timeout, platformArgs, subAccountId, contractId), callback);
  }

  protected TServiceCall create_queryPositionFeeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long contractId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionFeeVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionFee.queryPositionFee_args request = new TradeHostingPositionFee.queryPositionFee_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setContractId(contractId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryPositionFee");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionFee.queryPositionFee_result.class);
    return serviceCall;
  }

}
