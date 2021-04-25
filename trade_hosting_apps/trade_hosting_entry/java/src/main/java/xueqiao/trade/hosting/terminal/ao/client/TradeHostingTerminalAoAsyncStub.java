package xueqiao.trade.hosting.terminal.ao.client;

import xueqiao.trade.hosting.terminal.ao.TradeHostingTerminalAo;
import xueqiao.trade.hosting.terminal.ao.TradeHostingTerminalAoVariable;
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
import java.util.Map;
import java.util.Set;
import xueqiao.trade.hosting.terminal.ao.HostingComposeViewDetail;
import xueqiao.trade.hosting.terminal.ao.HostingSAWRUItemListPage;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundHisItem;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundItem;
import xueqiao.trade.hosting.terminal.ao.HostingUserSetting;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderPage;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderWithTradeListPage;
import xueqiao.trade.hosting.terminal.ao.HostingXQTradePage;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailPage;
import xueqiao.trade.hosting.terminal.ao.QueryHostingSAWRUItemListOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountPage;
import xueqiao.trade.hosting.terminal.ao.QuerySameComposeGraphsPage;
import xueqiao.trade.hosting.terminal.ao.QueryXQTradeLameTaskNotePageOption;
import xueqiao.trade.hosting.terminal.ao.ReqMailBoxMessageOption;
import xueqiao.trade.hosting.terminal.ao.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.terminal.ao.TradeAccountSettlementInfoWithRelatedTime;

public class TradeHostingTerminalAoAsyncStub extends BaseStub { 
  public TradeHostingTerminalAoAsyncStub() {
    super(TradeHostingTerminalAoVariable.serviceKey);
  }
  public void send_getHostingUserPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_getHostingUserPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long getHostingUserPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getHostingUserPage_args, TradeHostingTerminalAo.getHostingUserPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_getHostingUserPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getHostingUserPage_args, TradeHostingTerminalAo.getHostingUserPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingUserPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_getHostingUserPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingUserPage_args request = new TradeHostingTerminalAo.getHostingUserPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingUserPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingUserPage_result.class);
    return serviceCall;
  }

  public void send_heartBeat(int routeKey, int timeout, LandingInfo landingInfo) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_heartBeatServiceCall(routeKey, timeout, platformArgs, landingInfo), new TRequestOption());
  }

  public void send_heartBeat(int routeKey, int timeout, LandingInfo landingInfo,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_heartBeatServiceCall(routeKey, timeout, platformArgs, landingInfo), requestOption);
  }

  public long heartBeat(int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.heartBeat_args, TradeHostingTerminalAo.heartBeat_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_heartBeatServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  public long add_heartBeatCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.heartBeat_args, TradeHostingTerminalAo.heartBeat_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_heartBeatServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  protected TServiceCall create_heartBeatServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.heartBeat_args request = new TradeHostingTerminalAo.heartBeat_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("heartBeat");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.heartBeat_result.class);
    return serviceCall;
  }

  public void send_logout(int routeKey, int timeout, LandingInfo landingInfo) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_logoutServiceCall(routeKey, timeout, platformArgs, landingInfo), new TRequestOption());
  }

  public void send_logout(int routeKey, int timeout, LandingInfo landingInfo,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_logoutServiceCall(routeKey, timeout, platformArgs, landingInfo), requestOption);
  }

  public long logout(int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.logout_args, TradeHostingTerminalAo.logout_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_logoutServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  public long add_logoutCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.logout_args, TradeHostingTerminalAo.logout_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_logoutServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  protected TServiceCall create_logoutServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.logout_args request = new TradeHostingTerminalAo.logout_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("logout");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.logout_result.class);
    return serviceCall;
  }

  public void send_getComposeViewDetails(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeViewDetailsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), new TRequestOption());
  }

  public void send_getComposeViewDetails(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeViewDetailsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), requestOption);
  }

  public long getComposeViewDetails(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds, IMethodCallback<TradeHostingTerminalAo.getComposeViewDetails_args, TradeHostingTerminalAo.getComposeViewDetails_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeViewDetailsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), callback);
  }

  public long add_getComposeViewDetailsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds, IMethodCallback<TradeHostingTerminalAo.getComposeViewDetails_args, TradeHostingTerminalAo.getComposeViewDetails_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeViewDetailsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), callback);
  }

  protected TServiceCall create_getComposeViewDetailsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> composeGraphIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getComposeViewDetails_args request = new TradeHostingTerminalAo.getComposeViewDetails_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphIds(composeGraphIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getComposeViewDetails");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getComposeViewDetails_result.class);
    return serviceCall;
  }

  public void send_changeComposeViewPrecisionNumber(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, short precisionNumber) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeComposeViewPrecisionNumberServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, precisionNumber), new TRequestOption());
  }

  public void send_changeComposeViewPrecisionNumber(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, short precisionNumber,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeComposeViewPrecisionNumberServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, precisionNumber), requestOption);
  }

  public long changeComposeViewPrecisionNumber(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.changeComposeViewPrecisionNumber_args, TradeHostingTerminalAo.changeComposeViewPrecisionNumber_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeComposeViewPrecisionNumberServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, precisionNumber), callback);
  }

  public long add_changeComposeViewPrecisionNumberCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.changeComposeViewPrecisionNumber_args, TradeHostingTerminalAo.changeComposeViewPrecisionNumber_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeComposeViewPrecisionNumberServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, precisionNumber), callback);
  }

  protected TServiceCall create_changeComposeViewPrecisionNumberServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, short precisionNumber){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.changeComposeViewPrecisionNumber_args request = new TradeHostingTerminalAo.changeComposeViewPrecisionNumber_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    request.setPrecisionNumber(precisionNumber);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("changeComposeViewPrecisionNumber");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.changeComposeViewPrecisionNumber_result.class);
    return serviceCall;
  }

  public void send_createComposeGraph(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createComposeGraphServiceCall(routeKey, timeout, platformArgs, landingInfo, newGraph, aliasName, precisionNumber), new TRequestOption());
  }

  public void send_createComposeGraph(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createComposeGraphServiceCall(routeKey, timeout, platformArgs, landingInfo, newGraph, aliasName, precisionNumber), requestOption);
  }

  public long createComposeGraph(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.createComposeGraph_args, TradeHostingTerminalAo.createComposeGraph_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createComposeGraphServiceCall(routeKey, timeout, platformArgs, landingInfo, newGraph, aliasName, precisionNumber), callback);
  }

  public long add_createComposeGraphCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.createComposeGraph_args, TradeHostingTerminalAo.createComposeGraph_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createComposeGraphServiceCall(routeKey, timeout, platformArgs, landingInfo, newGraph, aliasName, precisionNumber), callback);
  }

  protected TServiceCall create_createComposeGraphServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.createComposeGraph_args request = new TradeHostingTerminalAo.createComposeGraph_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setNewGraph(newGraph);
    request.setAliasName(aliasName);
    request.setPrecisionNumber(precisionNumber);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createComposeGraph");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.createComposeGraph_result.class);
    return serviceCall;
  }

  public void send_delComposeView(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_delComposeViewServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), new TRequestOption());
  }

  public void send_delComposeView(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_delComposeViewServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), requestOption);
  }

  public long delComposeView(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.delComposeView_args, TradeHostingTerminalAo.delComposeView_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_delComposeViewServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  public long add_delComposeViewCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.delComposeView_args, TradeHostingTerminalAo.delComposeView_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_delComposeViewServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  protected TServiceCall create_delComposeViewServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.delComposeView_args request = new TradeHostingTerminalAo.delComposeView_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("delComposeView");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.delComposeView_result.class);
    return serviceCall;
  }

  public void send_getComposeViewDetailPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeViewDetailPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_getComposeViewDetailPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeViewDetailPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long getComposeViewDetailPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getComposeViewDetailPage_args, TradeHostingTerminalAo.getComposeViewDetailPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeViewDetailPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_getComposeViewDetailPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getComposeViewDetailPage_args, TradeHostingTerminalAo.getComposeViewDetailPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeViewDetailPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_getComposeViewDetailPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getComposeViewDetailPage_args request = new TradeHostingTerminalAo.getComposeViewDetailPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getComposeViewDetailPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getComposeViewDetailPage_result.class);
    return serviceCall;
  }

  public void send_getSameComposeGraphsPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSameComposeGraphsPageServiceCall(routeKey, timeout, platformArgs, landingInfo, graph, pageOption), new TRequestOption());
  }

  public void send_getSameComposeGraphsPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSameComposeGraphsPageServiceCall(routeKey, timeout, platformArgs, landingInfo, graph, pageOption), requestOption);
  }

  public long getSameComposeGraphsPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSameComposeGraphsPage_args, TradeHostingTerminalAo.getSameComposeGraphsPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSameComposeGraphsPageServiceCall(routeKey, timeout, platformArgs, landingInfo, graph, pageOption), callback);
  }

  public long add_getSameComposeGraphsPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSameComposeGraphsPage_args, TradeHostingTerminalAo.getSameComposeGraphsPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSameComposeGraphsPageServiceCall(routeKey, timeout, platformArgs, landingInfo, graph, pageOption), callback);
  }

  protected TServiceCall create_getSameComposeGraphsPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSameComposeGraphsPage_args request = new TradeHostingTerminalAo.getSameComposeGraphsPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setGraph(graph);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSameComposeGraphsPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSameComposeGraphsPage_result.class);
    return serviceCall;
  }

  public void send_addComposeViewBySearch(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addComposeViewBySearchServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber), new TRequestOption());
  }

  public void send_addComposeViewBySearch(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addComposeViewBySearchServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber), requestOption);
  }

  public long addComposeViewBySearch(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.addComposeViewBySearch_args, TradeHostingTerminalAo.addComposeViewBySearch_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addComposeViewBySearchServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber), callback);
  }

  public long add_addComposeViewBySearchCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.addComposeViewBySearch_args, TradeHostingTerminalAo.addComposeViewBySearch_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addComposeViewBySearchServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber), callback);
  }

  protected TServiceCall create_addComposeViewBySearchServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addComposeViewBySearch_args request = new TradeHostingTerminalAo.addComposeViewBySearch_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    request.setComposeGraphKey(composeGraphKey);
    request.setAliasName(aliasName);
    request.setPrecisionNumber(precisionNumber);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addComposeViewBySearch");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addComposeViewBySearch_result.class);
    return serviceCall;
  }

  public void send_subscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_subscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), new TRequestOption());
  }

  public void send_subscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_subscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), requestOption);
  }

  public long subscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.subscribeComposeViewQuotation_args, TradeHostingTerminalAo.subscribeComposeViewQuotation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_subscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  public long add_subscribeComposeViewQuotationCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.subscribeComposeViewQuotation_args, TradeHostingTerminalAo.subscribeComposeViewQuotation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_subscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  protected TServiceCall create_subscribeComposeViewQuotationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.subscribeComposeViewQuotation_args request = new TradeHostingTerminalAo.subscribeComposeViewQuotation_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("subscribeComposeViewQuotation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.subscribeComposeViewQuotation_result.class);
    return serviceCall;
  }

  public void send_unSubscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_unSubscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), new TRequestOption());
  }

  public void send_unSubscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_unSubscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), requestOption);
  }

  public long unSubscribeComposeViewQuotation(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.unSubscribeComposeViewQuotation_args, TradeHostingTerminalAo.unSubscribeComposeViewQuotation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_unSubscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  public long add_unSubscribeComposeViewQuotationCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, IMethodCallback<TradeHostingTerminalAo.unSubscribeComposeViewQuotation_args, TradeHostingTerminalAo.unSubscribeComposeViewQuotation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_unSubscribeComposeViewQuotationServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId), callback);
  }

  protected TServiceCall create_unSubscribeComposeViewQuotationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.unSubscribeComposeViewQuotation_args request = new TradeHostingTerminalAo.unSubscribeComposeViewQuotation_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("unSubscribeComposeViewQuotation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.unSubscribeComposeViewQuotation_result.class);
    return serviceCall;
  }

  public void send_changeComposeViewAliasName(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeComposeViewAliasNameServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName), new TRequestOption());
  }

  public void send_changeComposeViewAliasName(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeComposeViewAliasNameServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName), requestOption);
  }

  public long changeComposeViewAliasName(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, IMethodCallback<TradeHostingTerminalAo.changeComposeViewAliasName_args, TradeHostingTerminalAo.changeComposeViewAliasName_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeComposeViewAliasNameServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName), callback);
  }

  public long add_changeComposeViewAliasNameCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, IMethodCallback<TradeHostingTerminalAo.changeComposeViewAliasName_args, TradeHostingTerminalAo.changeComposeViewAliasName_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeComposeViewAliasNameServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName), callback);
  }

  protected TServiceCall create_changeComposeViewAliasNameServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String aliasName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.changeComposeViewAliasName_args request = new TradeHostingTerminalAo.changeComposeViewAliasName_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    request.setAliasName(aliasName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("changeComposeViewAliasName");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.changeComposeViewAliasName_result.class);
    return serviceCall;
  }

  public void send_getComposeGraphs(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeGraphsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), new TRequestOption());
  }

  public void send_getComposeGraphs(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getComposeGraphsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), requestOption);
  }

  public long getComposeGraphs(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds, IMethodCallback<TradeHostingTerminalAo.getComposeGraphs_args, TradeHostingTerminalAo.getComposeGraphs_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeGraphsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), callback);
  }

  public long add_getComposeGraphsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<Long> composeGraphIds, IMethodCallback<TradeHostingTerminalAo.getComposeGraphs_args, TradeHostingTerminalAo.getComposeGraphs_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getComposeGraphsServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphIds), callback);
  }

  protected TServiceCall create_getComposeGraphsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> composeGraphIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getComposeGraphs_args request = new TradeHostingTerminalAo.getComposeGraphs_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphIds(composeGraphIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getComposeGraphs");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getComposeGraphs_result.class);
    return serviceCall;
  }

  public void send_addComposeViewByShare(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addComposeViewByShareServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName, precisionNumber), new TRequestOption());
  }

  public void send_addComposeViewByShare(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addComposeViewByShareServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName, precisionNumber), requestOption);
  }

  public long addComposeViewByShare(int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.addComposeViewByShare_args, TradeHostingTerminalAo.addComposeViewByShare_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addComposeViewByShareServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName, precisionNumber), callback);
  }

  public long add_addComposeViewByShareCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber, IMethodCallback<TradeHostingTerminalAo.addComposeViewByShare_args, TradeHostingTerminalAo.addComposeViewByShare_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addComposeViewByShareServiceCall(routeKey, timeout, platformArgs, landingInfo, composeGraphId, aliasName, precisionNumber), callback);
  }

  protected TServiceCall create_addComposeViewByShareServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addComposeViewByShare_args request = new TradeHostingTerminalAo.addComposeViewByShare_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setComposeGraphId(composeGraphId);
    request.setAliasName(aliasName);
    request.setPrecisionNumber(precisionNumber);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addComposeViewByShare");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addComposeViewByShare_result.class);
    return serviceCall;
  }

  public void send_addTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newAccount), new TRequestOption());
  }

  public void send_addTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newAccount), requestOption);
  }

  public long addTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount, IMethodCallback<TradeHostingTerminalAo.addTradeAccount_args, TradeHostingTerminalAo.addTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newAccount), callback);
  }

  public long add_addTradeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount, IMethodCallback<TradeHostingTerminalAo.addTradeAccount_args, TradeHostingTerminalAo.addTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newAccount), callback);
  }

  protected TServiceCall create_addTradeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addTradeAccount_args request = new TradeHostingTerminalAo.addTradeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setNewAccount(newAccount);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addTradeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addTradeAccount_result.class);
    return serviceCall;
  }

  public void send_disableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_disableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), new TRequestOption());
  }

  public void send_disableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_disableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), requestOption);
  }

  public long disableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.disableTradeAccount_args, TradeHostingTerminalAo.disableTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_disableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  public long add_disableTradeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.disableTradeAccount_args, TradeHostingTerminalAo.disableTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_disableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  protected TServiceCall create_disableTradeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.disableTradeAccount_args request = new TradeHostingTerminalAo.disableTradeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("disableTradeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.disableTradeAccount_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_getTradeAccountPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long getTradeAccountPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getTradeAccountPage_args, TradeHostingTerminalAo.getTradeAccountPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_getTradeAccountPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getTradeAccountPage_args, TradeHostingTerminalAo.getTradeAccountPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_getTradeAccountPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountPage_args request = new TradeHostingTerminalAo.getTradeAccountPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountPage_result.class);
    return serviceCall;
  }

  public void send_enableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_enableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), new TRequestOption());
  }

  public void send_enableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_enableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), requestOption);
  }

  public long enableTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.enableTradeAccount_args, TradeHostingTerminalAo.enableTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_enableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  public long add_enableTradeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.enableTradeAccount_args, TradeHostingTerminalAo.enableTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_enableTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  protected TServiceCall create_enableTradeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.enableTradeAccount_args request = new TradeHostingTerminalAo.enableTradeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("enableTradeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.enableTradeAccount_result.class);
    return serviceCall;
  }

  public void send_updateTradeAccountInfo(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateTradeAccountInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, updateAccount), new TRequestOption());
  }

  public void send_updateTradeAccountInfo(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateTradeAccountInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, updateAccount), requestOption);
  }

  public long updateTradeAccountInfo(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount, IMethodCallback<TradeHostingTerminalAo.updateTradeAccountInfo_args, TradeHostingTerminalAo.updateTradeAccountInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateTradeAccountInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, updateAccount), callback);
  }

  public long add_updateTradeAccountInfoCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount, IMethodCallback<TradeHostingTerminalAo.updateTradeAccountInfo_args, TradeHostingTerminalAo.updateTradeAccountInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateTradeAccountInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, updateAccount), callback);
  }

  protected TServiceCall create_updateTradeAccountInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateTradeAccountInfo_args request = new TradeHostingTerminalAo.updateTradeAccountInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setUpdateAccount(updateAccount);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateTradeAccountInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateTradeAccountInfo_result.class);
    return serviceCall;
  }

  public void send_rmTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_rmTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), new TRequestOption());
  }

  public void send_rmTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_rmTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), requestOption);
  }

  public long rmTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.rmTradeAccount_args, TradeHostingTerminalAo.rmTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_rmTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  public long add_rmTradeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.rmTradeAccount_args, TradeHostingTerminalAo.rmTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_rmTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  protected TServiceCall create_rmTradeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.rmTradeAccount_args request = new TradeHostingTerminalAo.rmTradeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("rmTradeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.rmTradeAccount_result.class);
    return serviceCall;
  }

  public void send_getPersonalUserTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getPersonalUserTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getPersonalUserTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getPersonalUserTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getPersonalUserTradeAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getPersonalUserTradeAccount_args, TradeHostingTerminalAo.getPersonalUserTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getPersonalUserTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getPersonalUserTradeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getPersonalUserTradeAccount_args, TradeHostingTerminalAo.getPersonalUserTradeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getPersonalUserTradeAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getPersonalUserTradeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getPersonalUserTradeAccount_args request = new TradeHostingTerminalAo.getPersonalUserTradeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getPersonalUserTradeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getPersonalUserTradeAccount_result.class);
    return serviceCall;
  }

  public void send_getHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getHostingOrderRouteTree_args, TradeHostingTerminalAo.getHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getHostingOrderRouteTreeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getHostingOrderRouteTree_args, TradeHostingTerminalAo.getHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getHostingOrderRouteTreeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingOrderRouteTree_args request = new TradeHostingTerminalAo.getHostingOrderRouteTree_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingOrderRouteTree");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingOrderRouteTree_result.class);
    return serviceCall;
  }

  public void send_updateHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, routeTree), new TRequestOption());
  }

  public void send_updateHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, routeTree), requestOption);
  }

  public long updateHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree, IMethodCallback<TradeHostingTerminalAo.updateHostingOrderRouteTree_args, TradeHostingTerminalAo.updateHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, routeTree), callback);
  }

  public long add_updateHostingOrderRouteTreeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree, IMethodCallback<TradeHostingTerminalAo.updateHostingOrderRouteTree_args, TradeHostingTerminalAo.updateHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, routeTree), callback);
  }

  protected TServiceCall create_updateHostingOrderRouteTreeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateHostingOrderRouteTree_args request = new TradeHostingTerminalAo.updateHostingOrderRouteTree_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setRouteTree(routeTree);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateHostingOrderRouteTree");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateHostingOrderRouteTree_result.class);
    return serviceCall;
  }

  public void send_getHostingOrderRouteTreeVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingOrderRouteTreeVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getHostingOrderRouteTreeVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingOrderRouteTreeVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getHostingOrderRouteTreeVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_args, TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingOrderRouteTreeVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getHostingOrderRouteTreeVersionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_args, TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingOrderRouteTreeVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getHostingOrderRouteTreeVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_args request = new TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingOrderRouteTreeVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingOrderRouteTreeVersion_result.class);
    return serviceCall;
  }

  public void send_getPersonalUserHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getPersonalUserHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getPersonalUserHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getPersonalUserHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getPersonalUserHostingOrderRouteTree(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_args, TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getPersonalUserHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getPersonalUserHostingOrderRouteTreeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_args, TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getPersonalUserHostingOrderRouteTreeServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getPersonalUserHostingOrderRouteTreeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_args request = new TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getPersonalUserHostingOrderRouteTree");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getPersonalUserHostingOrderRouteTree_result.class);
    return serviceCall;
  }

  public void send_createXQOrder(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createXQOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail), new TRequestOption());
  }

  public void send_createXQOrder(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createXQOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail), requestOption);
  }

  public long createXQOrder(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail, IMethodCallback<TradeHostingTerminalAo.createXQOrder_args, TradeHostingTerminalAo.createXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createXQOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail), callback);
  }

  public long add_createXQOrderCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail, IMethodCallback<TradeHostingTerminalAo.createXQOrder_args, TradeHostingTerminalAo.createXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createXQOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail), callback);
  }

  protected TServiceCall create_createXQOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.createXQOrder_args request = new TradeHostingTerminalAo.createXQOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setOrderId(orderId);
    request.setOrderType(orderType);
    request.setOrderTarget(orderTarget);
    request.setOrderDetail(orderDetail);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createXQOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.createXQOrder_result.class);
    return serviceCall;
  }

  public void send_batchSuspendXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSuspendXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), new TRequestOption());
  }

  public void send_batchSuspendXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSuspendXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), requestOption);
  }

  public long batchSuspendXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.batchSuspendXQOrders_args, TradeHostingTerminalAo.batchSuspendXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSuspendXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  public long add_batchSuspendXQOrdersCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.batchSuspendXQOrders_args, TradeHostingTerminalAo.batchSuspendXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSuspendXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  protected TServiceCall create_batchSuspendXQOrdersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchSuspendXQOrders_args request = new TradeHostingTerminalAo.batchSuspendXQOrders_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSuspendXQOrders");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchSuspendXQOrders_result.class);
    return serviceCall;
  }

  public void send_batchResumeXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchResumeXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds, resumeModes), new TRequestOption());
  }

  public void send_batchResumeXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchResumeXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds, resumeModes), requestOption);
  }

  public long batchResumeXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes, IMethodCallback<TradeHostingTerminalAo.batchResumeXQOrders_args, TradeHostingTerminalAo.batchResumeXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchResumeXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds, resumeModes), callback);
  }

  public long add_batchResumeXQOrdersCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes, IMethodCallback<TradeHostingTerminalAo.batchResumeXQOrders_args, TradeHostingTerminalAo.batchResumeXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchResumeXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds, resumeModes), callback);
  }

  protected TServiceCall create_batchResumeXQOrdersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchResumeXQOrders_args request = new TradeHostingTerminalAo.batchResumeXQOrders_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOrderIds(orderIds);
    request.setResumeModes(resumeModes);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchResumeXQOrders");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchResumeXQOrders_result.class);
    return serviceCall;
  }

  public void send_batchCancelXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchCancelXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), new TRequestOption());
  }

  public void send_batchCancelXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchCancelXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), requestOption);
  }

  public long batchCancelXQOrders(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.batchCancelXQOrders_args, TradeHostingTerminalAo.batchCancelXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchCancelXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  public long add_batchCancelXQOrdersCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.batchCancelXQOrders_args, TradeHostingTerminalAo.batchCancelXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchCancelXQOrdersServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  protected TServiceCall create_batchCancelXQOrdersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchCancelXQOrders_args request = new TradeHostingTerminalAo.batchCancelXQOrders_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchCancelXQOrders");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchCancelXQOrders_result.class);
    return serviceCall;
  }

  public void send_getEffectXQOrderWithTradeListPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getEffectXQOrderWithTradeListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), new TRequestOption());
  }

  public void send_getEffectXQOrderWithTradeListPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getEffectXQOrderWithTradeListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), requestOption);
  }

  public long getEffectXQOrderWithTradeListPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_args, TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getEffectXQOrderWithTradeListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  public long add_getEffectXQOrderWithTradeListPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_args, TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getEffectXQOrderWithTradeListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getEffectXQOrderWithTradeListPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_args request = new TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getEffectXQOrderWithTradeListPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getEffectXQOrderWithTradeListPage_result.class);
    return serviceCall;
  }

  public void send_getXQOrderWithTradeLists(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), new TRequestOption());
  }

  public void send_getXQOrderWithTradeLists(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), requestOption);
  }

  public long getXQOrderWithTradeLists(int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.getXQOrderWithTradeLists_args, TradeHostingTerminalAo.getXQOrderWithTradeLists_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  public long add_getXQOrderWithTradeListsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<String> orderIds, IMethodCallback<TradeHostingTerminalAo.getXQOrderWithTradeLists_args, TradeHostingTerminalAo.getXQOrderWithTradeLists_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, landingInfo, orderIds), callback);
  }

  protected TServiceCall create_getXQOrderWithTradeListsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getXQOrderWithTradeLists_args request = new TradeHostingTerminalAo.getXQOrderWithTradeLists_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrderWithTradeLists");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getXQOrderWithTradeLists_result.class);
    return serviceCall;
  }

  public void send_getXQOrderExecDetail(int routeKey, int timeout, LandingInfo landingInfo, String orderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, orderId), new TRequestOption());
  }

  public void send_getXQOrderExecDetail(int routeKey, int timeout, LandingInfo landingInfo, String orderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, orderId), requestOption);
  }

  public long getXQOrderExecDetail(int routeKey, int timeout, LandingInfo landingInfo, String orderId, IMethodCallback<TradeHostingTerminalAo.getXQOrderExecDetail_args, TradeHostingTerminalAo.getXQOrderExecDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, orderId), callback);
  }

  public long add_getXQOrderExecDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, String orderId, IMethodCallback<TradeHostingTerminalAo.getXQOrderExecDetail_args, TradeHostingTerminalAo.getXQOrderExecDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, orderId), callback);
  }

  protected TServiceCall create_getXQOrderExecDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String orderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getXQOrderExecDetail_args request = new TradeHostingTerminalAo.getXQOrderExecDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOrderId(orderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrderExecDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getXQOrderExecDetail_result.class);
    return serviceCall;
  }

  public void send_getXQOrderHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), new TRequestOption());
  }

  public void send_getXQOrderHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQOrderHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), requestOption);
  }

  public long getXQOrderHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQOrderHisPage_args, TradeHostingTerminalAo.getXQOrderHisPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  public long add_getXQOrderHisPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQOrderHisPage_args, TradeHostingTerminalAo.getXQOrderHisPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getXQOrderHisPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getXQOrderHisPage_args request = new TradeHostingTerminalAo.getXQOrderHisPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrderHisPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getXQOrderHisPage_result.class);
    return serviceCall;
  }

  public void send_getXQTradeHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQTradeHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), new TRequestOption());
  }

  public void send_getXQTradeHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQTradeHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), requestOption);
  }

  public long getXQTradeHisPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQTradeHisPage_args, TradeHostingTerminalAo.getXQTradeHisPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  public long add_getXQTradeHisPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQTradeHisPage_args, TradeHostingTerminalAo.getXQTradeHisPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeHisPageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getXQTradeHisPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getXQTradeHisPage_args request = new TradeHostingTerminalAo.getXQTradeHisPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQTradeHisPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getXQTradeHisPage_result.class);
    return serviceCall;
  }

  public void send_getUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key), new TRequestOption());
  }

  public void send_getUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key), requestOption);
  }

  public long getUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key, IMethodCallback<TradeHostingTerminalAo.getUserSetting_args, TradeHostingTerminalAo.getUserSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key), callback);
  }

  public long add_getUserSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, String key, IMethodCallback<TradeHostingTerminalAo.getUserSetting_args, TradeHostingTerminalAo.getUserSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key), callback);
  }

  protected TServiceCall create_getUserSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getUserSetting_args request = new TradeHostingTerminalAo.getUserSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setKey(key);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getUserSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getUserSetting_result.class);
    return serviceCall;
  }

  public void send_updateUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key, HostingUserSetting setting) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key, setting), new TRequestOption());
  }

  public void send_updateUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key, HostingUserSetting setting,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key, setting), requestOption);
  }

  public long updateUserSetting(int routeKey, int timeout, LandingInfo landingInfo, String key, HostingUserSetting setting, IMethodCallback<TradeHostingTerminalAo.updateUserSetting_args, TradeHostingTerminalAo.updateUserSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key, setting), callback);
  }

  public long add_updateUserSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, String key, HostingUserSetting setting, IMethodCallback<TradeHostingTerminalAo.updateUserSetting_args, TradeHostingTerminalAo.updateUserSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateUserSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, key, setting), callback);
  }

  protected TServiceCall create_updateUserSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key, HostingUserSetting setting){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateUserSetting_args request = new TradeHostingTerminalAo.updateUserSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setKey(key);
    request.setSetting(setting);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateUserSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateUserSetting_result.class);
    return serviceCall;
  }

  public void send_getUserSettingVersion(int routeKey, int timeout, LandingInfo landingInfo, String key) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getUserSettingVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, key), new TRequestOption());
  }

  public void send_getUserSettingVersion(int routeKey, int timeout, LandingInfo landingInfo, String key,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getUserSettingVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, key), requestOption);
  }

  public long getUserSettingVersion(int routeKey, int timeout, LandingInfo landingInfo, String key, IMethodCallback<TradeHostingTerminalAo.getUserSettingVersion_args, TradeHostingTerminalAo.getUserSettingVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getUserSettingVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, key), callback);
  }

  public long add_getUserSettingVersionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, String key, IMethodCallback<TradeHostingTerminalAo.getUserSettingVersion_args, TradeHostingTerminalAo.getUserSettingVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getUserSettingVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, key), callback);
  }

  protected TServiceCall create_getUserSettingVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getUserSettingVersion_args request = new TradeHostingTerminalAo.getUserSettingVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setKey(key);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getUserSettingVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getUserSettingVersion_result.class);
    return serviceCall;
  }

  public void send_getSAWRUTListPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSAWRUTListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_getSAWRUTListPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSAWRUTListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long getSAWRUTListPage(int routeKey, int timeout, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSAWRUTListPage_args, TradeHostingTerminalAo.getSAWRUTListPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSAWRUTListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_getSAWRUTListPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSAWRUTListPage_args, TradeHostingTerminalAo.getSAWRUTListPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSAWRUTListPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_getSAWRUTListPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSAWRUTListPage_args request = new TradeHostingTerminalAo.getSAWRUTListPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSAWRUTListPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSAWRUTListPage_result.class);
    return serviceCall;
  }

  public void send_getSARUTBySubAccountId(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> subAccountIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSARUTBySubAccountIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountIds), new TRequestOption());
  }

  public void send_getSARUTBySubAccountId(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> subAccountIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSARUTBySubAccountIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountIds), requestOption);
  }

  public long getSARUTBySubAccountId(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> subAccountIds, IMethodCallback<TradeHostingTerminalAo.getSARUTBySubAccountId_args, TradeHostingTerminalAo.getSARUTBySubAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSARUTBySubAccountIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountIds), callback);
  }

  public long add_getSARUTBySubAccountIdCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<Long> subAccountIds, IMethodCallback<TradeHostingTerminalAo.getSARUTBySubAccountId_args, TradeHostingTerminalAo.getSARUTBySubAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSARUTBySubAccountIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountIds), callback);
  }

  protected TServiceCall create_getSARUTBySubAccountIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> subAccountIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSARUTBySubAccountId_args request = new TradeHostingTerminalAo.getSARUTBySubAccountId_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountIds(subAccountIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSARUTBySubAccountId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSARUTBySubAccountId_result.class);
    return serviceCall;
  }

  public void send_getSARUTBySubUserId(int routeKey, int timeout, LandingInfo landingInfo, Set<Integer> subUserIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSARUTBySubUserIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subUserIds), new TRequestOption());
  }

  public void send_getSARUTBySubUserId(int routeKey, int timeout, LandingInfo landingInfo, Set<Integer> subUserIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSARUTBySubUserIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subUserIds), requestOption);
  }

  public long getSARUTBySubUserId(int routeKey, int timeout, LandingInfo landingInfo, Set<Integer> subUserIds, IMethodCallback<TradeHostingTerminalAo.getSARUTBySubUserId_args, TradeHostingTerminalAo.getSARUTBySubUserId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSARUTBySubUserIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subUserIds), callback);
  }

  public long add_getSARUTBySubUserIdCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<Integer> subUserIds, IMethodCallback<TradeHostingTerminalAo.getSARUTBySubUserId_args, TradeHostingTerminalAo.getSARUTBySubUserId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSARUTBySubUserIdServiceCall(routeKey, timeout, platformArgs, landingInfo, subUserIds), callback);
  }

  protected TServiceCall create_getSARUTBySubUserIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Integer> subUserIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSARUTBySubUserId_args request = new TradeHostingTerminalAo.getSARUTBySubUserId_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubUserIds(subUserIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSARUTBySubUserId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSARUTBySubUserId_result.class);
    return serviceCall;
  }

  public void send_assignSubAccountRelatedUsers(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_assignSubAccountRelatedUsersServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds), new TRequestOption());
  }

  public void send_assignSubAccountRelatedUsers(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_assignSubAccountRelatedUsersServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds), requestOption);
  }

  public long assignSubAccountRelatedUsers(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds, IMethodCallback<TradeHostingTerminalAo.assignSubAccountRelatedUsers_args, TradeHostingTerminalAo.assignSubAccountRelatedUsers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_assignSubAccountRelatedUsersServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds), callback);
  }

  public long add_assignSubAccountRelatedUsersCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds, IMethodCallback<TradeHostingTerminalAo.assignSubAccountRelatedUsers_args, TradeHostingTerminalAo.assignSubAccountRelatedUsers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_assignSubAccountRelatedUsersServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds), callback);
  }

  protected TServiceCall create_assignSubAccountRelatedUsersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.assignSubAccountRelatedUsers_args request = new TradeHostingTerminalAo.assignSubAccountRelatedUsers_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setRelatedSubUserIds(relatedSubUserIds);
    request.setUnRelatedSubUserIds(unRelatedSubUserIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("assignSubAccountRelatedUsers");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.assignSubAccountRelatedUsers_result.class);
    return serviceCall;
  }

  public void send_renameSubAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String subAccountName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_renameSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, subAccountName), new TRequestOption());
  }

  public void send_renameSubAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String subAccountName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_renameSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, subAccountName), requestOption);
  }

  public long renameSubAccount(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String subAccountName, IMethodCallback<TradeHostingTerminalAo.renameSubAccount_args, TradeHostingTerminalAo.renameSubAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_renameSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, subAccountName), callback);
  }

  public long add_renameSubAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String subAccountName, IMethodCallback<TradeHostingTerminalAo.renameSubAccount_args, TradeHostingTerminalAo.renameSubAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_renameSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, subAccountName), callback);
  }

  protected TServiceCall create_renameSubAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String subAccountName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.renameSubAccount_args request = new TradeHostingTerminalAo.renameSubAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setSubAccountName(subAccountName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("renameSubAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.renameSubAccount_result.class);
    return serviceCall;
  }

  public void send_createSubAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newSubAccount), new TRequestOption());
  }

  public void send_createSubAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_createSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newSubAccount), requestOption);
  }

  public long createSubAccount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount, IMethodCallback<TradeHostingTerminalAo.createSubAccount_args, TradeHostingTerminalAo.createSubAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newSubAccount), callback);
  }

  public long add_createSubAccountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount, IMethodCallback<TradeHostingTerminalAo.createSubAccount_args, TradeHostingTerminalAo.createSubAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createSubAccountServiceCall(routeKey, timeout, platformArgs, landingInfo, newSubAccount), callback);
  }

  protected TServiceCall create_createSubAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.createSubAccount_args request = new TradeHostingTerminalAo.createSubAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setNewSubAccount(newSubAccount);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createSubAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.createSubAccount_result.class);
    return serviceCall;
  }

  public void send_getHostingSledContractPosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, option), new TRequestOption());
  }

  public void send_getHostingSledContractPosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, option), requestOption);
  }

  public long getHostingSledContractPosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option, IMethodCallback<TradeHostingTerminalAo.getHostingSledContractPosition_args, TradeHostingTerminalAo.getHostingSledContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, option), callback);
  }

  public long add_getHostingSledContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option, IMethodCallback<TradeHostingTerminalAo.getHostingSledContractPosition_args, TradeHostingTerminalAo.getHostingSledContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, option), callback);
  }

  protected TServiceCall create_getHostingSledContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingSledContractPosition_args request = new TradeHostingTerminalAo.getHostingSledContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSledContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingSledContractPosition_result.class);
    return serviceCall;
  }

  public void send_getHostingSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, option), new TRequestOption());
  }

  public void send_getHostingSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, option), requestOption);
  }

  public long getHostingSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option, IMethodCallback<TradeHostingTerminalAo.getHostingSubAccountFund_args, TradeHostingTerminalAo.getHostingSubAccountFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, option), callback);
  }

  public long add_getHostingSubAccountFundCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option, IMethodCallback<TradeHostingTerminalAo.getHostingSubAccountFund_args, TradeHostingTerminalAo.getHostingSubAccountFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, option), callback);
  }

  protected TServiceCall create_getHostingSubAccountFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingSubAccountFund_args request = new TradeHostingTerminalAo.getHostingSubAccountFund_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSubAccountFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingSubAccountFund_result.class);
    return serviceCall;
  }

  public void send_changeSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, fundChange), new TRequestOption());
  }

  public void send_changeSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, fundChange), requestOption);
  }

  public long changeSubAccountFund(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange, IMethodCallback<TradeHostingTerminalAo.changeSubAccountFund_args, TradeHostingTerminalAo.changeSubAccountFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, fundChange), callback);
  }

  public long add_changeSubAccountFundCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange, IMethodCallback<TradeHostingTerminalAo.changeSubAccountFund_args, TradeHostingTerminalAo.changeSubAccountFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, landingInfo, fundChange), callback);
  }

  protected TServiceCall create_changeSubAccountFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.changeSubAccountFund_args request = new TradeHostingTerminalAo.changeSubAccountFund_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setFundChange(fundChange);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("changeSubAccountFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.changeSubAccountFund_result.class);
    return serviceCall;
  }

  public void send_setSubAccountCreditAmount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, landingInfo, amountChange), new TRequestOption());
  }

  public void send_setSubAccountCreditAmount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, landingInfo, amountChange), requestOption);
  }

  public long setSubAccountCreditAmount(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange, IMethodCallback<TradeHostingTerminalAo.setSubAccountCreditAmount_args, TradeHostingTerminalAo.setSubAccountCreditAmount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, landingInfo, amountChange), callback);
  }

  public long add_setSubAccountCreditAmountCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange, IMethodCallback<TradeHostingTerminalAo.setSubAccountCreditAmount_args, TradeHostingTerminalAo.setSubAccountCreditAmount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, landingInfo, amountChange), callback);
  }

  protected TServiceCall create_setSubAccountCreditAmountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.setSubAccountCreditAmount_args request = new TradeHostingTerminalAo.setSubAccountCreditAmount_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setAmountChange(amountChange);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setSubAccountCreditAmount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.setSubAccountCreditAmount_result.class);
    return serviceCall;
  }

  public void send_getAssetPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getAssetPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getAssetPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getAssetPositionTradeDetail_args, TradeHostingTerminalAo.getAssetPositionTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getAssetPositionTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getAssetPositionTradeDetail_args, TradeHostingTerminalAo.getAssetPositionTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getAssetPositionTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getAssetPositionTradeDetail_args request = new TradeHostingTerminalAo.getAssetPositionTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAssetPositionTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getAssetPositionTradeDetail_result.class);
    return serviceCall;
  }

  public void send_getHostingSubAccountMoneyRecord(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getHostingSubAccountMoneyRecord(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getHostingSubAccountMoneyRecord(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_args, TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getHostingSubAccountMoneyRecordCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_args, TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getHostingSubAccountMoneyRecordServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_args request = new TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSubAccountMoneyRecord");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getHostingSubAccountMoneyRecord_result.class);
    return serviceCall;
  }

  public void send_getSubAccountFundHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getSubAccountFundHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getSubAccountFundHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountFundHistory_args, TradeHostingTerminalAo.getSubAccountFundHistory_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getSubAccountFundHistoryCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountFundHistory_args, TradeHostingTerminalAo.getSubAccountFundHistory_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getSubAccountFundHistoryServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSubAccountFundHistory_args request = new TradeHostingTerminalAo.getSubAccountFundHistory_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubAccountFundHistory");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSubAccountFundHistory_result.class);
    return serviceCall;
  }

  public void send_getSubAccountPositionHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountPositionHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getSubAccountPositionHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountPositionHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getSubAccountPositionHistory(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountPositionHistory_args, TradeHostingTerminalAo.getSubAccountPositionHistory_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountPositionHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getSubAccountPositionHistoryCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountPositionHistory_args, TradeHostingTerminalAo.getSubAccountPositionHistory_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountPositionHistoryServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getSubAccountPositionHistoryServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSubAccountPositionHistory_args request = new TradeHostingTerminalAo.getSubAccountPositionHistory_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubAccountPositionHistory");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSubAccountPositionHistory_result.class);
    return serviceCall;
  }

  public void send_getSubAccountPositionHistoryTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountPositionHistoryTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getSubAccountPositionHistoryTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getSubAccountPositionHistoryTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getSubAccountPositionHistoryTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_args, TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountPositionHistoryTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getSubAccountPositionHistoryTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_args, TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubAccountPositionHistoryTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getSubAccountPositionHistoryTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_args request = new TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubAccountPositionHistoryTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getSubAccountPositionHistoryTradeDetail_result.class);
    return serviceCall;
  }

  public void send_deleteExpiredContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), new TRequestOption());
  }

  public void send_deleteExpiredContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), requestOption);
  }

  public long deleteExpiredContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId, IMethodCallback<TradeHostingTerminalAo.deleteExpiredContractPosition_args, TradeHostingTerminalAo.deleteExpiredContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), callback);
  }

  public long add_deleteExpiredContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId, IMethodCallback<TradeHostingTerminalAo.deleteExpiredContractPosition_args, TradeHostingTerminalAo.deleteExpiredContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), callback);
  }

  protected TServiceCall create_deleteExpiredContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long sledContractId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.deleteExpiredContractPosition_args request = new TradeHostingTerminalAo.deleteExpiredContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setSledContractId(sledContractId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteExpiredContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.deleteExpiredContractPosition_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountFundNow(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountFundNowServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), new TRequestOption());
  }

  public void send_getTradeAccountFundNow(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountFundNowServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), requestOption);
  }

  public long getTradeAccountFundNow(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.getTradeAccountFundNow_args, TradeHostingTerminalAo.getTradeAccountFundNow_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountFundNowServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  public long add_getTradeAccountFundNowCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, IMethodCallback<TradeHostingTerminalAo.getTradeAccountFundNow_args, TradeHostingTerminalAo.getTradeAccountFundNow_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountFundNowServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId), callback);
  }

  protected TServiceCall create_getTradeAccountFundNowServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountFundNow_args request = new TradeHostingTerminalAo.getTradeAccountFundNow_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountFundNow");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountFundNow_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountFundHis(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountFundHisServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd), new TRequestOption());
  }

  public void send_getTradeAccountFundHis(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountFundHisServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd), requestOption);
  }

  public long getTradeAccountFundHis(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountFundHis_args, TradeHostingTerminalAo.getTradeAccountFundHis_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountFundHisServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd), callback);
  }

  public long add_getTradeAccountFundHisCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountFundHis_args, TradeHostingTerminalAo.getTradeAccountFundHis_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountFundHisServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd), callback);
  }

  protected TServiceCall create_getTradeAccountFundHisServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountFundHis_args request = new TradeHostingTerminalAo.getTradeAccountFundHis_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    request.setFundDateBegin(fundDateBegin);
    request.setFundDateEnd(fundDateEnd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountFundHis");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountFundHis_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountSettlementInfos(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountSettlementInfosServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), new TRequestOption());
  }

  public void send_getTradeAccountSettlementInfos(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountSettlementInfosServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), requestOption);
  }

  public long getTradeAccountSettlementInfos(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountSettlementInfos_args, TradeHostingTerminalAo.getTradeAccountSettlementInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountSettlementInfosServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  public long add_getTradeAccountSettlementInfosCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountSettlementInfos_args, TradeHostingTerminalAo.getTradeAccountSettlementInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountSettlementInfosServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  protected TServiceCall create_getTradeAccountSettlementInfosServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountSettlementInfos_args request = new TradeHostingTerminalAo.getTradeAccountSettlementInfos_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    request.setSettlementDateBegin(settlementDateBegin);
    request.setSettlementDateEnd(settlementDateEnd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountSettlementInfos");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountSettlementInfos_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountSettlementInfosWithRelatedTime(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountSettlementInfosWithRelatedTimeServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), new TRequestOption());
  }

  public void send_getTradeAccountSettlementInfosWithRelatedTime(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountSettlementInfosWithRelatedTimeServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), requestOption);
  }

  public long getTradeAccountSettlementInfosWithRelatedTime(int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_args, TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountSettlementInfosWithRelatedTimeServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  public long add_getTradeAccountSettlementInfosWithRelatedTimeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_args, TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountSettlementInfosWithRelatedTimeServiceCall(routeKey, timeout, platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  protected TServiceCall create_getTradeAccountSettlementInfosWithRelatedTimeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_args request = new TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setTradeAccountId(tradeAccountId);
    request.setSettlementDateBegin(settlementDateBegin);
    request.setSettlementDateEnd(settlementDateEnd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountSettlementInfosWithRelatedTime");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountSettlementInfosWithRelatedTime_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_getTradeAccountPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long getTradeAccountPositionTradeDetail(int routeKey, int timeout, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_args, TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_getTradeAccountPositionTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_args, TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_getTradeAccountPositionTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_args request = new TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountPositionTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getTradeAccountPositionTradeDetail_result.class);
    return serviceCall;
  }

  public void send_reqPositionVerify(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionVerify(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long reqPositionVerify(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionVerify_args, TradeHostingTerminalAo.reqPositionVerify_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_reqPositionVerifyCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionVerify_args, TradeHostingTerminalAo.reqPositionVerify_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionVerifyServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionVerifyServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqPositionVerify_args request = new TradeHostingTerminalAo.reqPositionVerify_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionVerify");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqPositionVerify_result.class);
    return serviceCall;
  }

  public void send_reqPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long reqPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionDifference_args, TradeHostingTerminalAo.reqPositionDifference_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_reqPositionDifferenceCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionDifference_args, TradeHostingTerminalAo.reqPositionDifference_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionDifferenceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqPositionDifference_args request = new TradeHostingTerminalAo.reqPositionDifference_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionDifference");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqPositionDifference_result.class);
    return serviceCall;
  }

  public void send_manualInputPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, positionManualInputs), new TRequestOption());
  }

  public void send_manualInputPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, positionManualInputs), requestOption);
  }

  public long manualInputPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs, IMethodCallback<TradeHostingTerminalAo.manualInputPosition_args, TradeHostingTerminalAo.manualInputPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, positionManualInputs), callback);
  }

  public long add_manualInputPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs, IMethodCallback<TradeHostingTerminalAo.manualInputPosition_args, TradeHostingTerminalAo.manualInputPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_manualInputPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, positionManualInputs), callback);
  }

  protected TServiceCall create_manualInputPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.manualInputPosition_args request = new TradeHostingTerminalAo.manualInputPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setPositionManualInputs(positionManualInputs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("manualInputPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.manualInputPosition_result.class);
    return serviceCall;
  }

  public void send_reqPositionUnassigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionUnassigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long reqPositionUnassigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionUnassigned_args, TradeHostingTerminalAo.reqPositionUnassigned_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_reqPositionUnassignedCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionUnassigned_args, TradeHostingTerminalAo.reqPositionUnassigned_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionUnassignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionUnassignedServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqPositionUnassigned_args request = new TradeHostingTerminalAo.reqPositionUnassigned_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionUnassigned");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqPositionUnassigned_result.class);
    return serviceCall;
  }

  public void send_assignPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, assignOption), new TRequestOption());
  }

  public void send_assignPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, assignOption), requestOption);
  }

  public long assignPosition(int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption, IMethodCallback<TradeHostingTerminalAo.assignPosition_args, TradeHostingTerminalAo.assignPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, assignOption), callback);
  }

  public long add_assignPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption, IMethodCallback<TradeHostingTerminalAo.assignPosition_args, TradeHostingTerminalAo.assignPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, assignOption), callback);
  }

  protected TServiceCall create_assignPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.assignPosition_args request = new TradeHostingTerminalAo.assignPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setAssignOption(assignOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("assignPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.assignPosition_result.class);
    return serviceCall;
  }

  public void send_reqPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, String lockKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, lockKey), new TRequestOption());
  }

  public void send_reqPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, String lockKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, lockKey), requestOption);
  }

  public long reqPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, String lockKey, IMethodCallback<TradeHostingTerminalAo.reqPositionEditLock_args, TradeHostingTerminalAo.reqPositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, lockKey), callback);
  }

  public long add_reqPositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, String lockKey, IMethodCallback<TradeHostingTerminalAo.reqPositionEditLock_args, TradeHostingTerminalAo.reqPositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, lockKey), callback);
  }

  protected TServiceCall create_reqPositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String lockKey){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqPositionEditLock_args request = new TradeHostingTerminalAo.reqPositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setLockKey(lockKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqPositionEditLock_result.class);
    return serviceCall;
  }

  public void send_addPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), new TRequestOption());
  }

  public void send_addPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), requestOption);
  }

  public long addPositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock, IMethodCallback<TradeHostingTerminalAo.addPositionEditLock_args, TradeHostingTerminalAo.addPositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), callback);
  }

  public long add_addPositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock, IMethodCallback<TradeHostingTerminalAo.addPositionEditLock_args, TradeHostingTerminalAo.addPositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addPositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), callback);
  }

  protected TServiceCall create_addPositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addPositionEditLock_args request = new TradeHostingTerminalAo.addPositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setPositionEditLock(positionEditLock);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addPositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addPositionEditLock_result.class);
    return serviceCall;
  }

  public void send_removePositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), new TRequestOption());
  }

  public void send_removePositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), requestOption);
  }

  public long removePositionEditLock(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock, IMethodCallback<TradeHostingTerminalAo.removePositionEditLock_args, TradeHostingTerminalAo.removePositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), callback);
  }

  public long add_removePositionEditLockCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock, IMethodCallback<TradeHostingTerminalAo.removePositionEditLock_args, TradeHostingTerminalAo.removePositionEditLock_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_removePositionEditLockServiceCall(routeKey, timeout, platformArgs, landingInfo, positionEditLock), callback);
  }

  protected TServiceCall create_removePositionEditLockServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.removePositionEditLock_args request = new TradeHostingTerminalAo.removePositionEditLock_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setPositionEditLock(positionEditLock);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removePositionEditLock");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.removePositionEditLock_result.class);
    return serviceCall;
  }

  public void send_reqDailyPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_reqDailyPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long reqDailyPositionDifference(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqDailyPositionDifference_args, TradeHostingTerminalAo.reqDailyPositionDifference_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_reqDailyPositionDifferenceCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqDailyPositionDifference_args, TradeHostingTerminalAo.reqDailyPositionDifference_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqDailyPositionDifferenceServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_reqDailyPositionDifferenceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqDailyPositionDifference_args request = new TradeHostingTerminalAo.reqDailyPositionDifference_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqDailyPositionDifference");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqDailyPositionDifference_result.class);
    return serviceCall;
  }

  public void send_updateDailyPositionDifferenceNote(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, landingInfo, difference), new TRequestOption());
  }

  public void send_updateDailyPositionDifferenceNote(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, landingInfo, difference), requestOption);
  }

  public long updateDailyPositionDifferenceNote(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference, IMethodCallback<TradeHostingTerminalAo.updateDailyPositionDifferenceNote_args, TradeHostingTerminalAo.updateDailyPositionDifferenceNote_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, landingInfo, difference), callback);
  }

  public long add_updateDailyPositionDifferenceNoteCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference, IMethodCallback<TradeHostingTerminalAo.updateDailyPositionDifferenceNote_args, TradeHostingTerminalAo.updateDailyPositionDifferenceNote_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateDailyPositionDifferenceNoteServiceCall(routeKey, timeout, platformArgs, landingInfo, difference), callback);
  }

  protected TServiceCall create_updateDailyPositionDifferenceNoteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateDailyPositionDifferenceNote_args request = new TradeHostingTerminalAo.updateDailyPositionDifferenceNote_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setDifference(difference);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDailyPositionDifferenceNote");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateDailyPositionDifferenceNote_result.class);
    return serviceCall;
  }

  public void send_reqPositionAssigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_reqPositionAssigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long reqPositionAssigned(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionAssigned_args, TradeHostingTerminalAo.reqPositionAssigned_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_reqPositionAssignedCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.reqPositionAssigned_args, TradeHostingTerminalAo.reqPositionAssigned_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_reqPositionAssignedServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_reqPositionAssignedServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.reqPositionAssigned_args request = new TradeHostingTerminalAo.reqPositionAssigned_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqPositionAssigned");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.reqPositionAssigned_result.class);
    return serviceCall;
  }

  public void send_contructCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_contructComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, contructComposeReq), new TRequestOption());
  }

  public void send_contructCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_contructComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, contructComposeReq), requestOption);
  }

  public long contructCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq, IMethodCallback<TradeHostingTerminalAo.contructCompose_args, TradeHostingTerminalAo.contructCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_contructComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, contructComposeReq), callback);
  }

  public long add_contructComposeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq, IMethodCallback<TradeHostingTerminalAo.contructCompose_args, TradeHostingTerminalAo.contructCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_contructComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, contructComposeReq), callback);
  }

  protected TServiceCall create_contructComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.contructCompose_args request = new TradeHostingTerminalAo.contructCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setContructComposeReq(contructComposeReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("contructCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.contructCompose_result.class);
    return serviceCall;
  }

  public void send_disassembleCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, disassembleComposePositionReq), new TRequestOption());
  }

  public void send_disassembleCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, disassembleComposePositionReq), requestOption);
  }

  public long disassembleCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq, IMethodCallback<TradeHostingTerminalAo.disassembleCompose_args, TradeHostingTerminalAo.disassembleCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, disassembleComposePositionReq), callback);
  }

  public long add_disassembleComposeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq, IMethodCallback<TradeHostingTerminalAo.disassembleCompose_args, TradeHostingTerminalAo.disassembleCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, disassembleComposePositionReq), callback);
  }

  protected TServiceCall create_disassembleComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.disassembleCompose_args request = new TradeHostingTerminalAo.disassembleCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setDisassembleComposePositionReq(disassembleComposePositionReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("disassembleCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.disassembleCompose_result.class);
    return serviceCall;
  }

  public void send_batchClosePosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, landingInfo, batchClosedPositionReq), new TRequestOption());
  }

  public void send_batchClosePosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, landingInfo, batchClosedPositionReq), requestOption);
  }

  public long batchClosePosition(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq, IMethodCallback<TradeHostingTerminalAo.batchClosePosition_args, TradeHostingTerminalAo.batchClosePosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, landingInfo, batchClosedPositionReq), callback);
  }

  public long add_batchClosePositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq, IMethodCallback<TradeHostingTerminalAo.batchClosePosition_args, TradeHostingTerminalAo.batchClosePosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, landingInfo, batchClosedPositionReq), callback);
  }

  protected TServiceCall create_batchClosePositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchClosePosition_args request = new TradeHostingTerminalAo.batchClosePosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setBatchClosedPositionReq(batchClosedPositionReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchClosePosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchClosePosition_result.class);
    return serviceCall;
  }

  public void send_recoverClosedPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), new TRequestOption());
  }

  public void send_recoverClosedPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), requestOption);
  }

  public long recoverClosedPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingTerminalAo.recoverClosedPosition_args, TradeHostingTerminalAo.recoverClosedPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), callback);
  }

  public long add_recoverClosedPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingTerminalAo.recoverClosedPosition_args, TradeHostingTerminalAo.recoverClosedPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), callback);
  }

  protected TServiceCall create_recoverClosedPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.recoverClosedPosition_args request = new TradeHostingTerminalAo.recoverClosedPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setTargetKey(targetKey);
    request.setTargetType(targetType);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("recoverClosedPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.recoverClosedPosition_result.class);
    return serviceCall;
  }

  public void send_mergeToCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, mergeToComposeReq), new TRequestOption());
  }

  public void send_mergeToCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, mergeToComposeReq), requestOption);
  }

  public long mergeToCompose(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq, IMethodCallback<TradeHostingTerminalAo.mergeToCompose_args, TradeHostingTerminalAo.mergeToCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, mergeToComposeReq), callback);
  }

  public long add_mergeToComposeCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq, IMethodCallback<TradeHostingTerminalAo.mergeToCompose_args, TradeHostingTerminalAo.mergeToCompose_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, landingInfo, mergeToComposeReq), callback);
  }

  protected TServiceCall create_mergeToComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.mergeToCompose_args request = new TradeHostingTerminalAo.mergeToCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setMergeToComposeReq(mergeToComposeReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("mergeToCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.mergeToCompose_result.class);
    return serviceCall;
  }

  public void send_deleteExpiredStatContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), new TRequestOption());
  }

  public void send_deleteExpiredStatContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), requestOption);
  }

  public long deleteExpiredStatContractPosition(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId, IMethodCallback<TradeHostingTerminalAo.deleteExpiredStatContractPosition_args, TradeHostingTerminalAo.deleteExpiredStatContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), callback);
  }

  public long add_deleteExpiredStatContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long sledContractId, IMethodCallback<TradeHostingTerminalAo.deleteExpiredStatContractPosition_args, TradeHostingTerminalAo.deleteExpiredStatContractPosition_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, sledContractId), callback);
  }

  protected TServiceCall create_deleteExpiredStatContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long sledContractId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.deleteExpiredStatContractPosition_args request = new TradeHostingTerminalAo.deleteExpiredStatContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setSledContractId(sledContractId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteExpiredStatContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.deleteExpiredStatContractPosition_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionSummaryPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionSummaryPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionSummaryPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionSummaryPage_args, TradeHostingTerminalAo.queryStatPositionSummaryPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionSummaryPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionSummaryPage_args, TradeHostingTerminalAo.queryStatPositionSummaryPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionSummaryPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryStatPositionSummaryPage_args request = new TradeHostingTerminalAo.queryStatPositionSummaryPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionSummaryPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryStatPositionSummaryPage_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionItemPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionItemPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionItemPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionItemPage_args, TradeHostingTerminalAo.queryStatPositionItemPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionItemPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionItemPage_args, TradeHostingTerminalAo.queryStatPositionItemPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionItemPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryStatPositionItemPage_args request = new TradeHostingTerminalAo.queryStatPositionItemPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionItemPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryStatPositionItemPage_result.class);
    return serviceCall;
  }

  public void send_queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), new TRequestOption());
  }

  public void send_queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), requestOption);
  }

  public long queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_args, TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), callback);
  }

  public long add_queryCurrentDayStatClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_args, TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, targetKey, targetType), callback);
  }

  protected TServiceCall create_queryCurrentDayStatClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_args request = new TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setTargetKey(targetKey);
    request.setTargetType(targetType);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCurrentDayStatClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryCurrentDayStatClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryStatClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryStatClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatClosedPositionDetail_args, TradeHostingTerminalAo.queryStatClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryStatClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatClosedPositionDetail_args, TradeHostingTerminalAo.queryStatClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryStatClosedPositionDetail_args request = new TradeHostingTerminalAo.queryStatClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryStatClosedPositionDetail_result.class);
    return serviceCall;
  }

  public void send_queryArchivedClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryArchivedClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryArchivedClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryArchivedClosedPositionPage_args, TradeHostingTerminalAo.queryArchivedClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryArchivedClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryArchivedClosedPositionPage_args, TradeHostingTerminalAo.queryArchivedClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryArchivedClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryArchivedClosedPositionPage_args request = new TradeHostingTerminalAo.queryArchivedClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryArchivedClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryArchivedClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryArchivedClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryArchivedClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryArchivedClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryArchivedClosedPositionDetail_args, TradeHostingTerminalAo.queryArchivedClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryArchivedClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryArchivedClosedPositionDetail_args, TradeHostingTerminalAo.queryArchivedClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryArchivedClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryArchivedClosedPositionDetail_args request = new TradeHostingTerminalAo.queryArchivedClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryArchivedClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryArchivedClosedPositionDetail_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionSummaryExPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionSummaryExPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionSummaryExPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionSummaryExPage_args, TradeHostingTerminalAo.queryStatPositionSummaryExPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionSummaryExPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionSummaryExPage_args, TradeHostingTerminalAo.queryStatPositionSummaryExPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionSummaryExPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryStatPositionSummaryExPage_args request = new TradeHostingTerminalAo.queryStatPositionSummaryExPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionSummaryExPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryStatPositionSummaryExPage_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionUnitPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionUnitPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionUnitPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionUnitPage_args, TradeHostingTerminalAo.queryStatPositionUnitPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionUnitPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryStatPositionUnitPage_args, TradeHostingTerminalAo.queryStatPositionUnitPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionUnitPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryStatPositionUnitPage_args request = new TradeHostingTerminalAo.queryStatPositionUnitPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionUnitPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryStatPositionUnitPage_result.class);
    return serviceCall;
  }

  public void send_queryHistoryClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryHistoryClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryHistoryClosedPositionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryHistoryClosedPositionPage_args, TradeHostingTerminalAo.queryHistoryClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryHistoryClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryHistoryClosedPositionPage_args, TradeHostingTerminalAo.queryHistoryClosedPositionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryHistoryClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryHistoryClosedPositionPage_args request = new TradeHostingTerminalAo.queryHistoryClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryHistoryClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryHistoryClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryHistoryClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryHistoryClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), requestOption);
  }

  public long queryHistoryClosedPositionDetail(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryHistoryClosedPositionDetail_args, TradeHostingTerminalAo.queryHistoryClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  public long add_queryHistoryClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryHistoryClosedPositionDetail_args, TradeHostingTerminalAo.queryHistoryClosedPositionDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryHistoryClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryHistoryClosedPositionDetail_args request = new TradeHostingTerminalAo.queryHistoryClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryHistoryClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryHistoryClosedPositionDetail_result.class);
    return serviceCall;
  }

  public void send_getXQTradeLameTaskNotePage(int routeKey, int timeout, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQTradeLameTaskNotePageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), new TRequestOption());
  }

  public void send_getXQTradeLameTaskNotePage(int routeKey, int timeout, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getXQTradeLameTaskNotePageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), requestOption);
  }

  public long getXQTradeLameTaskNotePage(int routeKey, int timeout, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQTradeLameTaskNotePage_args, TradeHostingTerminalAo.getXQTradeLameTaskNotePage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeLameTaskNotePageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  public long add_getXQTradeLameTaskNotePageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.getXQTradeLameTaskNotePage_args, TradeHostingTerminalAo.getXQTradeLameTaskNotePage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeLameTaskNotePageServiceCall(routeKey, timeout, platformArgs, landingInfo, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getXQTradeLameTaskNotePageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getXQTradeLameTaskNotePage_args request = new TradeHostingTerminalAo.getXQTradeLameTaskNotePage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQTradeLameTaskNotePage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getXQTradeLameTaskNotePage_result.class);
    return serviceCall;
  }

  public void send_batchDeleteXQTradeLameTaskNotes(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchDeleteXQTradeLameTaskNotesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, xqTradeIds), new TRequestOption());
  }

  public void send_batchDeleteXQTradeLameTaskNotes(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchDeleteXQTradeLameTaskNotesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, xqTradeIds), requestOption);
  }

  public long batchDeleteXQTradeLameTaskNotes(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds, IMethodCallback<TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_args, TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchDeleteXQTradeLameTaskNotesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, xqTradeIds), callback);
  }

  public long add_batchDeleteXQTradeLameTaskNotesCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds, IMethodCallback<TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_args, TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchDeleteXQTradeLameTaskNotesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, xqTradeIds), callback);
  }

  protected TServiceCall create_batchDeleteXQTradeLameTaskNotesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_args request = new TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setXqTradeIds(xqTradeIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchDeleteXQTradeLameTaskNotes");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchDeleteXQTradeLameTaskNotes_result.class);
    return serviceCall;
  }

  public void send_queryMailBoxMessage(int routeKey, int timeout, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryMailBoxMessageServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), new TRequestOption());
  }

  public void send_queryMailBoxMessage(int routeKey, int timeout, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryMailBoxMessageServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), requestOption);
  }

  public long queryMailBoxMessage(int routeKey, int timeout, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryMailBoxMessage_args, TradeHostingTerminalAo.queryMailBoxMessage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryMailBoxMessageServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  public long add_queryMailBoxMessageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryMailBoxMessage_args, TradeHostingTerminalAo.queryMailBoxMessage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryMailBoxMessageServiceCall(routeKey, timeout, platformArgs, landingInfo, option, pageOption), callback);
  }

  protected TServiceCall create_queryMailBoxMessageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryMailBoxMessage_args request = new TradeHostingTerminalAo.queryMailBoxMessage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMailBoxMessage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryMailBoxMessage_result.class);
    return serviceCall;
  }

  public void send_markMessageAsRead(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> hostingMessageIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_markMessageAsReadServiceCall(routeKey, timeout, platformArgs, landingInfo, hostingMessageIds), new TRequestOption());
  }

  public void send_markMessageAsRead(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> hostingMessageIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_markMessageAsReadServiceCall(routeKey, timeout, platformArgs, landingInfo, hostingMessageIds), requestOption);
  }

  public long markMessageAsRead(int routeKey, int timeout, LandingInfo landingInfo, Set<Long> hostingMessageIds, IMethodCallback<TradeHostingTerminalAo.markMessageAsRead_args, TradeHostingTerminalAo.markMessageAsRead_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_markMessageAsReadServiceCall(routeKey, timeout, platformArgs, landingInfo, hostingMessageIds), callback);
  }

  public long add_markMessageAsReadCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, Set<Long> hostingMessageIds, IMethodCallback<TradeHostingTerminalAo.markMessageAsRead_args, TradeHostingTerminalAo.markMessageAsRead_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_markMessageAsReadServiceCall(routeKey, timeout, platformArgs, landingInfo, hostingMessageIds), callback);
  }

  protected TServiceCall create_markMessageAsReadServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> hostingMessageIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.markMessageAsRead_args request = new TradeHostingTerminalAo.markMessageAsRead_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setHostingMessageIds(hostingMessageIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("markMessageAsRead");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.markMessageAsRead_result.class);
    return serviceCall;
  }

  public void send_getAllSupportedItems(int routeKey, int timeout, LandingInfo landingInfo) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo), new TRequestOption());
  }

  public void send_getAllSupportedItems(int routeKey, int timeout, LandingInfo landingInfo,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo), requestOption);
  }

  public long getAllSupportedItems(int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.getAllSupportedItems_args, TradeHostingTerminalAo.getAllSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  public long add_getAllSupportedItemsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, IMethodCallback<TradeHostingTerminalAo.getAllSupportedItems_args, TradeHostingTerminalAo.getAllSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo), callback);
  }

  protected TServiceCall create_getAllSupportedItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getAllSupportedItems_args request = new TradeHostingTerminalAo.getAllSupportedItems_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAllSupportedItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getAllSupportedItems_result.class);
    return serviceCall;
  }

  public void send_getRiskRuleJointVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getRiskRuleJointVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getRiskRuleJointVersion(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskRuleJointVersion_args, TradeHostingTerminalAo.getRiskRuleJointVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getRiskRuleJointVersionCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskRuleJointVersion_args, TradeHostingTerminalAo.getRiskRuleJointVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getRiskRuleJointVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getRiskRuleJointVersion_args request = new TradeHostingTerminalAo.getRiskRuleJointVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskRuleJointVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getRiskRuleJointVersion_result.class);
    return serviceCall;
  }

  public void send_getRiskRuleJoint(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getRiskRuleJoint(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getRiskRuleJoint(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskRuleJoint_args, TradeHostingTerminalAo.getRiskRuleJoint_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getRiskRuleJointCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskRuleJoint_args, TradeHostingTerminalAo.getRiskRuleJoint_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getRiskRuleJointServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getRiskRuleJoint_args request = new TradeHostingTerminalAo.getRiskRuleJoint_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskRuleJoint");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getRiskRuleJoint_result.class);
    return serviceCall;
  }

  public void send_batchSetSupportedItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, openedItemIds, closedItemIds), new TRequestOption());
  }

  public void send_batchSetSupportedItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, openedItemIds, closedItemIds), requestOption);
  }

  public long batchSetSupportedItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds, IMethodCallback<TradeHostingTerminalAo.batchSetSupportedItems_args, TradeHostingTerminalAo.batchSetSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, openedItemIds, closedItemIds), callback);
  }

  public long add_batchSetSupportedItemsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds, IMethodCallback<TradeHostingTerminalAo.batchSetSupportedItems_args, TradeHostingTerminalAo.batchSetSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, openedItemIds, closedItemIds), callback);
  }

  protected TServiceCall create_batchSetSupportedItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchSetSupportedItems_args request = new TradeHostingTerminalAo.batchSetSupportedItems_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setOpenedItemIds(openedItemIds);
    request.setClosedItemIds(closedItemIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetSupportedItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchSetSupportedItems_result.class);
    return serviceCall;
  }

  public void send_batchSetTradedCommodityItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds), new TRequestOption());
  }

  public void send_batchSetTradedCommodityItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds), requestOption);
  }

  public long batchSetTradedCommodityItems(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds, IMethodCallback<TradeHostingTerminalAo.batchSetTradedCommodityItems_args, TradeHostingTerminalAo.batchSetTradedCommodityItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds), callback);
  }

  public long add_batchSetTradedCommodityItemsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds, IMethodCallback<TradeHostingTerminalAo.batchSetTradedCommodityItems_args, TradeHostingTerminalAo.batchSetTradedCommodityItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds), callback);
  }

  protected TServiceCall create_batchSetTradedCommodityItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchSetTradedCommodityItems_args request = new TradeHostingTerminalAo.batchSetTradedCommodityItems_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setEnabledCommodityIds(enabledCommodityIds);
    request.setDisabledCommodityIds(disabledCommodityIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetTradedCommodityItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchSetTradedCommodityItems_result.class);
    return serviceCall;
  }

  public void send_batchSetGlobalRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, ruleItems), new TRequestOption());
  }

  public void send_batchSetGlobalRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, ruleItems), requestOption);
  }

  public long batchSetGlobalRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems, IMethodCallback<TradeHostingTerminalAo.batchSetGlobalRules_args, TradeHostingTerminalAo.batchSetGlobalRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, ruleItems), callback);
  }

  public long add_batchSetGlobalRulesCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems, IMethodCallback<TradeHostingTerminalAo.batchSetGlobalRules_args, TradeHostingTerminalAo.batchSetGlobalRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, ruleItems), callback);
  }

  protected TServiceCall create_batchSetGlobalRulesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchSetGlobalRules_args request = new TradeHostingTerminalAo.batchSetGlobalRules_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRuleItems(ruleItems);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetGlobalRules");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchSetGlobalRules_result.class);
    return serviceCall;
  }

  public void send_batchSetCommodityRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, rules), new TRequestOption());
  }

  public void send_batchSetCommodityRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, rules), requestOption);
  }

  public long batchSetCommodityRules(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules, IMethodCallback<TradeHostingTerminalAo.batchSetCommodityRules_args, TradeHostingTerminalAo.batchSetCommodityRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, rules), callback);
  }

  public long add_batchSetCommodityRulesCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules, IMethodCallback<TradeHostingTerminalAo.batchSetCommodityRules_args, TradeHostingTerminalAo.batchSetCommodityRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, rules), callback);
  }

  protected TServiceCall create_batchSetCommodityRulesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.batchSetCommodityRules_args request = new TradeHostingTerminalAo.batchSetCommodityRules_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRules(rules);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetCommodityRules");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.batchSetCommodityRules_result.class);
    return serviceCall;
  }

  public void send_setRiskEnabled(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, riskEnabled), new TRequestOption());
  }

  public void send_setRiskEnabled(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, riskEnabled), requestOption);
  }

  public long setRiskEnabled(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled, IMethodCallback<TradeHostingTerminalAo.setRiskEnabled_args, TradeHostingTerminalAo.setRiskEnabled_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, riskEnabled), callback);
  }

  public long add_setRiskEnabledCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled, IMethodCallback<TradeHostingTerminalAo.setRiskEnabled_args, TradeHostingTerminalAo.setRiskEnabled_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, version, riskEnabled), callback);
  }

  protected TServiceCall create_setRiskEnabledServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.setRiskEnabled_args request = new TradeHostingTerminalAo.setRiskEnabled_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRiskEnabled(riskEnabled);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setRiskEnabled");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.setRiskEnabled_result.class);
    return serviceCall;
  }

  public void send_getRiskFrameDataInfo(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_getRiskFrameDataInfo(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long getRiskFrameDataInfo(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskFrameDataInfo_args, TradeHostingTerminalAo.getRiskFrameDataInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_getRiskFrameDataInfoCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.getRiskFrameDataInfo_args, TradeHostingTerminalAo.getRiskFrameDataInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_getRiskFrameDataInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.getRiskFrameDataInfo_args request = new TradeHostingTerminalAo.getRiskFrameDataInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskFrameDataInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.getRiskFrameDataInfo_result.class);
    return serviceCall;
  }

  public void send_setGeneralMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), new TRequestOption());
  }

  public void send_setGeneralMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), requestOption);
  }

  public long setGeneralMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.setGeneralMarginSetting_args, TradeHostingTerminalAo.setGeneralMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  public long add_setGeneralMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.setGeneralMarginSetting_args, TradeHostingTerminalAo.setGeneralMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setGeneralMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  protected TServiceCall create_setGeneralMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.setGeneralMarginSetting_args request = new TradeHostingTerminalAo.setGeneralMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setGeneralMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.setGeneralMarginSetting_result.class);
    return serviceCall;
  }

  public void send_setGeneralCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), new TRequestOption());
  }

  public void send_setGeneralCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), requestOption);
  }

  public long setGeneralCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.setGeneralCommissionSetting_args, TradeHostingTerminalAo.setGeneralCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  public long add_setGeneralCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.setGeneralCommissionSetting_args, TradeHostingTerminalAo.setGeneralCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setGeneralCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  protected TServiceCall create_setGeneralCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.setGeneralCommissionSetting_args request = new TradeHostingTerminalAo.setGeneralCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setGeneralCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.setGeneralCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_addSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), new TRequestOption());
  }

  public void send_addSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), requestOption);
  }

  public long addSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.addSpecMarginSetting_args, TradeHostingTerminalAo.addSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  public long add_addSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.addSpecMarginSetting_args, TradeHostingTerminalAo.addSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  protected TServiceCall create_addSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addSpecMarginSetting_args request = new TradeHostingTerminalAo.addSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_addSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), new TRequestOption());
  }

  public void send_addSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), requestOption);
  }

  public long addSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.addSpecCommissionSetting_args, TradeHostingTerminalAo.addSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  public long add_addSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.addSpecCommissionSetting_args, TradeHostingTerminalAo.addSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  protected TServiceCall create_addSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addSpecCommissionSetting_args request = new TradeHostingTerminalAo.addSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_updateSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), new TRequestOption());
  }

  public void send_updateSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), requestOption);
  }

  public long updateSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.updateSpecMarginSetting_args, TradeHostingTerminalAo.updateSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  public long add_updateSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings, IMethodCallback<TradeHostingTerminalAo.updateSpecMarginSetting_args, TradeHostingTerminalAo.updateSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, marginSettings), callback);
  }

  protected TServiceCall create_updateSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateSpecMarginSetting_args request = new TradeHostingTerminalAo.updateSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setMarginSettings(marginSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_updateSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), new TRequestOption());
  }

  public void send_updateSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), requestOption);
  }

  public long updateSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.updateSpecCommissionSetting_args, TradeHostingTerminalAo.updateSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  public long add_updateSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings, IMethodCallback<TradeHostingTerminalAo.updateSpecCommissionSetting_args, TradeHostingTerminalAo.updateSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, commissionSettings), callback);
  }

  protected TServiceCall create_updateSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.updateSpecCommissionSetting_args request = new TradeHostingTerminalAo.updateSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setCommissionSettings(commissionSettings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.updateSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_deleteSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), new TRequestOption());
  }

  public void send_deleteSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), requestOption);
  }

  public long deleteSpecMarginSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId, IMethodCallback<TradeHostingTerminalAo.deleteSpecMarginSetting_args, TradeHostingTerminalAo.deleteSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), callback);
  }

  public long add_deleteSpecMarginSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId, IMethodCallback<TradeHostingTerminalAo.deleteSpecMarginSetting_args, TradeHostingTerminalAo.deleteSpecMarginSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteSpecMarginSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), callback);
  }

  protected TServiceCall create_deleteSpecMarginSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long commodityId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.deleteSpecMarginSetting_args request = new TradeHostingTerminalAo.deleteSpecMarginSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setCommodityId(commodityId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSpecMarginSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.deleteSpecMarginSetting_result.class);
    return serviceCall;
  }

  public void send_deleteSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), new TRequestOption());
  }

  public void send_deleteSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), requestOption);
  }

  public long deleteSpecCommissionSetting(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId, IMethodCallback<TradeHostingTerminalAo.deleteSpecCommissionSetting_args, TradeHostingTerminalAo.deleteSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), callback);
  }

  public long add_deleteSpecCommissionSettingCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, long commodityId, IMethodCallback<TradeHostingTerminalAo.deleteSpecCommissionSetting_args, TradeHostingTerminalAo.deleteSpecCommissionSetting_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_deleteSpecCommissionSettingServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId, commodityId), callback);
  }

  protected TServiceCall create_deleteSpecCommissionSettingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long commodityId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.deleteSpecCommissionSetting_args request = new TradeHostingTerminalAo.deleteSpecCommissionSetting_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    request.setCommodityId(commodityId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSpecCommissionSetting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.deleteSpecCommissionSetting_result.class);
    return serviceCall;
  }

  public void send_queryXQGeneralMarginSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_queryXQGeneralMarginSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long queryXQGeneralMarginSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.queryXQGeneralMarginSettings_args, TradeHostingTerminalAo.queryXQGeneralMarginSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_queryXQGeneralMarginSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.queryXQGeneralMarginSettings_args, TradeHostingTerminalAo.queryXQGeneralMarginSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQGeneralMarginSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_queryXQGeneralMarginSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQGeneralMarginSettings_args request = new TradeHostingTerminalAo.queryXQGeneralMarginSettings_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQGeneralMarginSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQGeneralMarginSettings_result.class);
    return serviceCall;
  }

  public void send_queryXQGeneralCommissionSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), new TRequestOption());
  }

  public void send_queryXQGeneralCommissionSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), requestOption);
  }

  public long queryXQGeneralCommissionSettings(int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.queryXQGeneralCommissionSettings_args, TradeHostingTerminalAo.queryXQGeneralCommissionSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  public long add_queryXQGeneralCommissionSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, long subAccountId, IMethodCallback<TradeHostingTerminalAo.queryXQGeneralCommissionSettings_args, TradeHostingTerminalAo.queryXQGeneralCommissionSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQGeneralCommissionSettingsServiceCall(routeKey, timeout, platformArgs, landingInfo, subAccountId), callback);
  }

  protected TServiceCall create_queryXQGeneralCommissionSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQGeneralCommissionSettings_args request = new TradeHostingTerminalAo.queryXQGeneralCommissionSettings_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQGeneralCommissionSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQGeneralCommissionSettings_result.class);
    return serviceCall;
  }

  public void send_queryXQSpecMarginSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQSpecMarginSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryXQSpecMarginSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQSpecMarginSettingPage_args, TradeHostingTerminalAo.queryXQSpecMarginSettingPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryXQSpecMarginSettingPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQSpecMarginSettingPage_args, TradeHostingTerminalAo.queryXQSpecMarginSettingPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQSpecMarginSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQSpecMarginSettingPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQSpecMarginSettingPage_args request = new TradeHostingTerminalAo.queryXQSpecMarginSettingPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQSpecMarginSettingPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQSpecMarginSettingPage_result.class);
    return serviceCall;
  }

  public void send_queryXQSpecCommissionSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQSpecCommissionSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryXQSpecCommissionSettingPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_args, TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryXQSpecCommissionSettingPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_args, TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQSpecCommissionSettingPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQSpecCommissionSettingPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_args request = new TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQSpecCommissionSettingPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQSpecCommissionSettingPage_result.class);
    return serviceCall;
  }

  public void send_queryUpsideContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryUpsideContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryUpsideContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryUpsideContractMarginPage_args, TradeHostingTerminalAo.queryUpsideContractMarginPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryUpsideContractMarginPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryUpsideContractMarginPage_args, TradeHostingTerminalAo.queryUpsideContractMarginPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryUpsideContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryUpsideContractMarginPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryUpsideContractMarginPage_args request = new TradeHostingTerminalAo.queryUpsideContractMarginPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUpsideContractMarginPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryUpsideContractMarginPage_result.class);
    return serviceCall;
  }

  public void send_queryUpsideContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryUpsideContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryUpsideContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryUpsideContractCommissionPage_args, TradeHostingTerminalAo.queryUpsideContractCommissionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryUpsideContractCommissionPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryUpsideContractCommissionPage_args, TradeHostingTerminalAo.queryUpsideContractCommissionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryUpsideContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryUpsideContractCommissionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryUpsideContractCommissionPage_args request = new TradeHostingTerminalAo.queryUpsideContractCommissionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUpsideContractCommissionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryUpsideContractCommissionPage_result.class);
    return serviceCall;
  }

  public void send_queryXQContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryXQContractMarginPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQContractMarginPage_args, TradeHostingTerminalAo.queryXQContractMarginPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryXQContractMarginPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQContractMarginPage_args, TradeHostingTerminalAo.queryXQContractMarginPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQContractMarginPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQContractMarginPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQContractMarginPage_args request = new TradeHostingTerminalAo.queryXQContractMarginPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQContractMarginPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQContractMarginPage_result.class);
    return serviceCall;
  }

  public void send_queryXQContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), new TRequestOption());
  }

  public void send_queryXQContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), requestOption);
  }

  public long queryXQContractCommissionPage(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQContractCommissionPage_args, TradeHostingTerminalAo.queryXQContractCommissionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  public long add_queryXQContractCommissionPageCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTerminalAo.queryXQContractCommissionPage_args, TradeHostingTerminalAo.queryXQContractCommissionPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_queryXQContractCommissionPageServiceCall(routeKey, timeout, platformArgs, landingInfo, queryOptions, pageOption), callback);
  }

  protected TServiceCall create_queryXQContractCommissionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.queryXQContractCommissionPage_args request = new TradeHostingTerminalAo.queryXQContractCommissionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setQueryOptions(queryOptions);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryXQContractCommissionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.queryXQContractCommissionPage_result.class);
    return serviceCall;
  }

  public void send_addAssetAccountWorkingOrder(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addAssetAccountWorkingOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, assetAccount), new TRequestOption());
  }

  public void send_addAssetAccountWorkingOrder(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
        create_addAssetAccountWorkingOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, assetAccount), requestOption);
  }

  public long addAssetAccountWorkingOrder(int routeKey, int timeout, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount, IMethodCallback<TradeHostingTerminalAo.addAssetAccountWorkingOrder_args, TradeHostingTerminalAo.addAssetAccountWorkingOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addAssetAccountWorkingOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, assetAccount), callback);
  }

  public long add_addAssetAccountWorkingOrderCall(AsyncCallRunner runner, int routeKey, int timeout, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount, IMethodCallback<TradeHostingTerminalAo.addAssetAccountWorkingOrder_args, TradeHostingTerminalAo.addAssetAccountWorkingOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_addAssetAccountWorkingOrderServiceCall(routeKey, timeout, platformArgs, landingInfo, assetAccount), callback);
  }

  protected TServiceCall create_addAssetAccountWorkingOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTerminalAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTerminalAo.addAssetAccountWorkingOrder_args request = new TradeHostingTerminalAo.addAssetAccountWorkingOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setLandingInfo(landingInfo);
    request.setAssetAccount(assetAccount);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addAssetAccountWorkingOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTerminalAo.addAssetAccountWorkingOrder_result.class);
    return serviceCall;
  }

}
