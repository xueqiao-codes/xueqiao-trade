package xueqiao.trade.hosting.arbitrage.thriftapi.client;

import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrage;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageVariable;
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
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;

public class TradeHostingArbitrageAsyncStub extends BaseStub { 
  public TradeHostingArbitrageAsyncStub() {
    super(TradeHostingArbitrageVariable.serviceKey);
  }
  public void send_createXQOrder(int routeKey, int timeout, HostingXQOrder order) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createXQOrderServiceCall(routeKey, timeout, platformArgs, order), new TRequestOption());
  }

  public void send_createXQOrder(int routeKey, int timeout, HostingXQOrder order,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createXQOrderServiceCall(routeKey, timeout, platformArgs, order), requestOption);
  }

  public long createXQOrder(int routeKey, int timeout, HostingXQOrder order, IMethodCallback<TradeHostingArbitrage.createXQOrder_args, TradeHostingArbitrage.createXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createXQOrderServiceCall(routeKey, timeout, platformArgs, order), callback);
  }

  public long add_createXQOrderCall(AsyncCallRunner runner, int routeKey, int timeout, HostingXQOrder order, IMethodCallback<TradeHostingArbitrage.createXQOrder_args, TradeHostingArbitrage.createXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createXQOrderServiceCall(routeKey, timeout, platformArgs, order), callback);
  }

  protected TServiceCall create_createXQOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingXQOrder order){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.createXQOrder_args request = new TradeHostingArbitrage.createXQOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setOrder(order);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createXQOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.createXQOrder_result.class);
    return serviceCall;
  }

  public void send_cancelXQOrder(int routeKey, int timeout, String orderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_cancelXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), new TRequestOption());
  }

  public void send_cancelXQOrder(int routeKey, int timeout, String orderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_cancelXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), requestOption);
  }

  public long cancelXQOrder(int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.cancelXQOrder_args, TradeHostingArbitrage.cancelXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_cancelXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  public long add_cancelXQOrderCall(AsyncCallRunner runner, int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.cancelXQOrder_args, TradeHostingArbitrage.cancelXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_cancelXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  protected TServiceCall create_cancelXQOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.cancelXQOrder_args request = new TradeHostingArbitrage.cancelXQOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("cancelXQOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.cancelXQOrder_result.class);
    return serviceCall;
  }

  public void send_suspendXQOrder(int routeKey, int timeout, String orderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_suspendXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), new TRequestOption());
  }

  public void send_suspendXQOrder(int routeKey, int timeout, String orderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_suspendXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), requestOption);
  }

  public long suspendXQOrder(int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.suspendXQOrder_args, TradeHostingArbitrage.suspendXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_suspendXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  public long add_suspendXQOrderCall(AsyncCallRunner runner, int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.suspendXQOrder_args, TradeHostingArbitrage.suspendXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_suspendXQOrderServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  protected TServiceCall create_suspendXQOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.suspendXQOrder_args request = new TradeHostingArbitrage.suspendXQOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("suspendXQOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.suspendXQOrder_result.class);
    return serviceCall;
  }

  public void send_resumeXQOrder(int routeKey, int timeout, String orderId, HostingXQOrderResumeMode resumeMode) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_resumeXQOrderServiceCall(routeKey, timeout, platformArgs, orderId, resumeMode), new TRequestOption());
  }

  public void send_resumeXQOrder(int routeKey, int timeout, String orderId, HostingXQOrderResumeMode resumeMode,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_resumeXQOrderServiceCall(routeKey, timeout, platformArgs, orderId, resumeMode), requestOption);
  }

  public long resumeXQOrder(int routeKey, int timeout, String orderId, HostingXQOrderResumeMode resumeMode, IMethodCallback<TradeHostingArbitrage.resumeXQOrder_args, TradeHostingArbitrage.resumeXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_resumeXQOrderServiceCall(routeKey, timeout, platformArgs, orderId, resumeMode), callback);
  }

  public long add_resumeXQOrderCall(AsyncCallRunner runner, int routeKey, int timeout, String orderId, HostingXQOrderResumeMode resumeMode, IMethodCallback<TradeHostingArbitrage.resumeXQOrder_args, TradeHostingArbitrage.resumeXQOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_resumeXQOrderServiceCall(routeKey, timeout, platformArgs, orderId, resumeMode), callback);
  }

  protected TServiceCall create_resumeXQOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId, HostingXQOrderResumeMode resumeMode){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.resumeXQOrder_args request = new TradeHostingArbitrage.resumeXQOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    request.setResumeMode(resumeMode);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("resumeXQOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.resumeXQOrder_result.class);
    return serviceCall;
  }

  public void send_getEffectXQOrderIndexPage(int routeKey, int timeout, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getEffectXQOrderIndexPageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), new TRequestOption());
  }

  public void send_getEffectXQOrderIndexPage(int routeKey, int timeout, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getEffectXQOrderIndexPageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), requestOption);
  }

  public long getEffectXQOrderIndexPage(int routeKey, int timeout, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingArbitrage.getEffectXQOrderIndexPage_args, TradeHostingArbitrage.getEffectXQOrderIndexPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getEffectXQOrderIndexPageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), callback);
  }

  public long add_getEffectXQOrderIndexPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingArbitrage.getEffectXQOrderIndexPage_args, TradeHostingArbitrage.getEffectXQOrderIndexPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getEffectXQOrderIndexPageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getEffectXQOrderIndexPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getEffectXQOrderIndexPage_args request = new TradeHostingArbitrage.getEffectXQOrderIndexPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getEffectXQOrderIndexPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getEffectXQOrderIndexPage_result.class);
    return serviceCall;
  }

  public void send_getXQOrders(int routeKey, int timeout, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrdersServiceCall(routeKey, timeout, platformArgs, orderIds), new TRequestOption());
  }

  public void send_getXQOrders(int routeKey, int timeout, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrdersServiceCall(routeKey, timeout, platformArgs, orderIds), requestOption);
  }

  public long getXQOrders(int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQOrders_args, TradeHostingArbitrage.getXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrdersServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  public long add_getXQOrdersCall(AsyncCallRunner runner, int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQOrders_args, TradeHostingArbitrage.getXQOrders_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrdersServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  protected TServiceCall create_getXQOrdersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getXQOrders_args request = new TradeHostingArbitrage.getXQOrders_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrders");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getXQOrders_result.class);
    return serviceCall;
  }

  public void send_getXQTrades(int routeKey, int timeout, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds), new TRequestOption());
  }

  public void send_getXQTrades(int routeKey, int timeout, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds), requestOption);
  }

  public long getXQTrades(int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQTrades_args, TradeHostingArbitrage.getXQTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  public long add_getXQTradesCall(AsyncCallRunner runner, int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQTrades_args, TradeHostingArbitrage.getXQTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  protected TServiceCall create_getXQTradesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getXQTrades_args request = new TradeHostingArbitrage.getXQTrades_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQTrades");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getXQTrades_result.class);
    return serviceCall;
  }

  public void send_getXQOrderWithTradeLists(int routeKey, int timeout, Set<String> orderIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, orderIds), new TRequestOption());
  }

  public void send_getXQOrderWithTradeLists(int routeKey, int timeout, Set<String> orderIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, orderIds), requestOption);
  }

  public long getXQOrderWithTradeLists(int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQOrderWithTradeLists_args, TradeHostingArbitrage.getXQOrderWithTradeLists_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  public long add_getXQOrderWithTradeListsCall(AsyncCallRunner runner, int routeKey, int timeout, Set<String> orderIds, IMethodCallback<TradeHostingArbitrage.getXQOrderWithTradeLists_args, TradeHostingArbitrage.getXQOrderWithTradeLists_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderWithTradeListsServiceCall(routeKey, timeout, platformArgs, orderIds), callback);
  }

  protected TServiceCall create_getXQOrderWithTradeListsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getXQOrderWithTradeLists_args request = new TradeHostingArbitrage.getXQOrderWithTradeLists_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderIds(orderIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrderWithTradeLists");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getXQOrderWithTradeLists_result.class);
    return serviceCall;
  }

  public void send_getXQOrderExecDetail(int routeKey, int timeout, String orderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, orderId), new TRequestOption());
  }

  public void send_getXQOrderExecDetail(int routeKey, int timeout, String orderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, orderId), requestOption);
  }

  public long getXQOrderExecDetail(int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.getXQOrderExecDetail_args, TradeHostingArbitrage.getXQOrderExecDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  public long add_getXQOrderExecDetailCall(AsyncCallRunner runner, int routeKey, int timeout, String orderId, IMethodCallback<TradeHostingArbitrage.getXQOrderExecDetail_args, TradeHostingArbitrage.getXQOrderExecDetail_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQOrderExecDetailServiceCall(routeKey, timeout, platformArgs, orderId), callback);
  }

  protected TServiceCall create_getXQOrderExecDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getXQOrderExecDetail_args request = new TradeHostingArbitrage.getXQOrderExecDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQOrderExecDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getXQOrderExecDetail_result.class);
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

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingArbitrage.clearAll_args, TradeHostingArbitrage.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingArbitrage.clearAll_args, TradeHostingArbitrage.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.clearAll_args request = new TradeHostingArbitrage.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.clearAll_result.class);
    return serviceCall;
  }

  public void send_filterXQTrades(int routeKey, int timeout, Set<String> orderIds, Set<Long> tradeIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_filterXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds, tradeIds), new TRequestOption());
  }

  public void send_filterXQTrades(int routeKey, int timeout, Set<String> orderIds, Set<Long> tradeIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_filterXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds, tradeIds), requestOption);
  }

  public long filterXQTrades(int routeKey, int timeout, Set<String> orderIds, Set<Long> tradeIds, IMethodCallback<TradeHostingArbitrage.filterXQTrades_args, TradeHostingArbitrage.filterXQTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_filterXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds, tradeIds), callback);
  }

  public long add_filterXQTradesCall(AsyncCallRunner runner, int routeKey, int timeout, Set<String> orderIds, Set<Long> tradeIds, IMethodCallback<TradeHostingArbitrage.filterXQTrades_args, TradeHostingArbitrage.filterXQTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_filterXQTradesServiceCall(routeKey, timeout, platformArgs, orderIds, tradeIds), callback);
  }

  protected TServiceCall create_filterXQTradesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds, Set<Long> tradeIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.filterXQTrades_args request = new TradeHostingArbitrage.filterXQTrades_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderIds(orderIds);
    request.setTradeIds(tradeIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("filterXQTrades");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.filterXQTrades_result.class);
    return serviceCall;
  }

  public void send_getXQTradeRelatedItems(int routeKey, int timeout, String orderId, long tradeId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQTradeRelatedItemsServiceCall(routeKey, timeout, platformArgs, orderId, tradeId), new TRequestOption());
  }

  public void send_getXQTradeRelatedItems(int routeKey, int timeout, String orderId, long tradeId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getXQTradeRelatedItemsServiceCall(routeKey, timeout, platformArgs, orderId, tradeId), requestOption);
  }

  public long getXQTradeRelatedItems(int routeKey, int timeout, String orderId, long tradeId, IMethodCallback<TradeHostingArbitrage.getXQTradeRelatedItems_args, TradeHostingArbitrage.getXQTradeRelatedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeRelatedItemsServiceCall(routeKey, timeout, platformArgs, orderId, tradeId), callback);
  }

  public long add_getXQTradeRelatedItemsCall(AsyncCallRunner runner, int routeKey, int timeout, String orderId, long tradeId, IMethodCallback<TradeHostingArbitrage.getXQTradeRelatedItems_args, TradeHostingArbitrage.getXQTradeRelatedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getXQTradeRelatedItemsServiceCall(routeKey, timeout, platformArgs, orderId, tradeId), callback);
  }

  protected TServiceCall create_getXQTradeRelatedItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId, long tradeId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getXQTradeRelatedItems_args request = new TradeHostingArbitrage.getXQTradeRelatedItems_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    request.setTradeId(tradeId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getXQTradeRelatedItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getXQTradeRelatedItems_result.class);
    return serviceCall;
  }

  public void send_getSystemXQComposeLimitOrderSettings(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getSystemXQComposeLimitOrderSettings(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getSystemXQComposeLimitOrderSettings(int routeKey, int timeout, IMethodCallback<TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_args, TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getSystemXQComposeLimitOrderSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_args, TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getSystemXQComposeLimitOrderSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_args request = new TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSystemXQComposeLimitOrderSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.getSystemXQComposeLimitOrderSettings_result.class);
    return serviceCall;
  }

  public void send_setSystemXQComposeLimitOrderSettings(int routeKey, int timeout, HostingXQComposeLimitOrderSettings settings) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs, settings), new TRequestOption());
  }

  public void send_setSystemXQComposeLimitOrderSettings(int routeKey, int timeout, HostingXQComposeLimitOrderSettings settings,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs, settings), requestOption);
  }

  public long setSystemXQComposeLimitOrderSettings(int routeKey, int timeout, HostingXQComposeLimitOrderSettings settings, IMethodCallback<TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_args, TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs, settings), callback);
  }

  public long add_setSystemXQComposeLimitOrderSettingsCall(AsyncCallRunner runner, int routeKey, int timeout, HostingXQComposeLimitOrderSettings settings, IMethodCallback<TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_args, TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setSystemXQComposeLimitOrderSettingsServiceCall(routeKey, timeout, platformArgs, settings), callback);
  }

  protected TServiceCall create_setSystemXQComposeLimitOrderSettingsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingXQComposeLimitOrderSettings settings){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingArbitrageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_args request = new TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_args();
    request.setPlatformArgs(platformArgs);
    request.setSettings(settings);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setSystemXQComposeLimitOrderSettings");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingArbitrage.setSystemXQComposeLimitOrderSettings_result.class);
    return serviceCall;
  }

}
