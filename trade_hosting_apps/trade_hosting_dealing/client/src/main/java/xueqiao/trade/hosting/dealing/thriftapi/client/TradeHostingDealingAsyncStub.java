package xueqiao.trade.hosting.dealing.thriftapi.client;

import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealing;
import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealingVariable;
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
import xueqiao.trade.hosting.dealing.thriftapi.HostingExecOrderPage;

public class TradeHostingDealingAsyncStub extends BaseStub { 
  public TradeHostingDealingAsyncStub() {
    super(TradeHostingDealingVariable.serviceKey);
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

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingDealing.clearAll_args, TradeHostingDealing.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingDealing.clearAll_args, TradeHostingDealing.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.clearAll_args request = new TradeHostingDealing.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.clearAll_result.class);
    return serviceCall;
  }

  public void send_createUserDeal(int routeKey, int timeout, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createUserDealServiceCall(routeKey, timeout, platformArgs, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source), new TRequestOption());
  }

  public void send_createUserDeal(int routeKey, int timeout, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createUserDealServiceCall(routeKey, timeout, platformArgs, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source), requestOption);
  }

  public long createUserDeal(int routeKey, int timeout, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source, IMethodCallback<TradeHostingDealing.createUserDeal_args, TradeHostingDealing.createUserDeal_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createUserDealServiceCall(routeKey, timeout, platformArgs, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source), callback);
  }

  public long add_createUserDealCall(AsyncCallRunner runner, int routeKey, int timeout, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source, IMethodCallback<TradeHostingDealing.createUserDeal_args, TradeHostingDealing.createUserDeal_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createUserDealServiceCall(routeKey, timeout, platformArgs, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source), callback);
  }

  protected TServiceCall create_createUserDealServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.createUserDeal_args request = new TradeHostingDealing.createUserDeal_args();
    request.setPlatformArgs(platformArgs);
    request.setSubUserId(subUserId);
    request.setSubAccountId(subAccountId);
    request.setExecOrderId(execOrderId);
    request.setContractSummary(contractSummary);
    request.setOrderDetail(orderDetail);
    request.setSource(source);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createUserDeal");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.createUserDeal_result.class);
    return serviceCall;
  }

  public void send_revokeDeal(int routeKey, int timeout, long execOrderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_revokeDealServiceCall(routeKey, timeout, platformArgs, execOrderId), new TRequestOption());
  }

  public void send_revokeDeal(int routeKey, int timeout, long execOrderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_revokeDealServiceCall(routeKey, timeout, platformArgs, execOrderId), requestOption);
  }

  public long revokeDeal(int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.revokeDeal_args, TradeHostingDealing.revokeDeal_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_revokeDealServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  public long add_revokeDealCall(AsyncCallRunner runner, int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.revokeDeal_args, TradeHostingDealing.revokeDeal_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_revokeDealServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  protected TServiceCall create_revokeDealServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.revokeDeal_args request = new TradeHostingDealing.revokeDeal_args();
    request.setPlatformArgs(platformArgs);
    request.setExecOrderId(execOrderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("revokeDeal");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.revokeDeal_result.class);
    return serviceCall;
  }

  public void send_getOrder(int routeKey, int timeout, long execOrderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getOrderServiceCall(routeKey, timeout, platformArgs, execOrderId), new TRequestOption());
  }

  public void send_getOrder(int routeKey, int timeout, long execOrderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getOrderServiceCall(routeKey, timeout, platformArgs, execOrderId), requestOption);
  }

  public long getOrder(int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.getOrder_args, TradeHostingDealing.getOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getOrderServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  public long add_getOrderCall(AsyncCallRunner runner, int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.getOrder_args, TradeHostingDealing.getOrder_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getOrderServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  protected TServiceCall create_getOrderServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getOrder_args request = new TradeHostingDealing.getOrder_args();
    request.setPlatformArgs(platformArgs);
    request.setExecOrderId(execOrderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getOrder");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getOrder_result.class);
    return serviceCall;
  }

  public void send_getOrderTrades(int routeKey, int timeout, long execOrderId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getOrderTradesServiceCall(routeKey, timeout, platformArgs, execOrderId), new TRequestOption());
  }

  public void send_getOrderTrades(int routeKey, int timeout, long execOrderId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getOrderTradesServiceCall(routeKey, timeout, platformArgs, execOrderId), requestOption);
  }

  public long getOrderTrades(int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.getOrderTrades_args, TradeHostingDealing.getOrderTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getOrderTradesServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  public long add_getOrderTradesCall(AsyncCallRunner runner, int routeKey, int timeout, long execOrderId, IMethodCallback<TradeHostingDealing.getOrderTrades_args, TradeHostingDealing.getOrderTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getOrderTradesServiceCall(routeKey, timeout, platformArgs, execOrderId), callback);
  }

  protected TServiceCall create_getOrderTradesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getOrderTrades_args request = new TradeHostingDealing.getOrderTrades_args();
    request.setPlatformArgs(platformArgs);
    request.setExecOrderId(execOrderId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getOrderTrades");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getOrderTrades_result.class);
    return serviceCall;
  }

  public void send_getTradeRelatedLegs(int routeKey, int timeout, long execTradeId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getTradeRelatedLegsServiceCall(routeKey, timeout, platformArgs, execTradeId), new TRequestOption());
  }

  public void send_getTradeRelatedLegs(int routeKey, int timeout, long execTradeId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getTradeRelatedLegsServiceCall(routeKey, timeout, platformArgs, execTradeId), requestOption);
  }

  public long getTradeRelatedLegs(int routeKey, int timeout, long execTradeId, IMethodCallback<TradeHostingDealing.getTradeRelatedLegs_args, TradeHostingDealing.getTradeRelatedLegs_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeRelatedLegsServiceCall(routeKey, timeout, platformArgs, execTradeId), callback);
  }

  public long add_getTradeRelatedLegsCall(AsyncCallRunner runner, int routeKey, int timeout, long execTradeId, IMethodCallback<TradeHostingDealing.getTradeRelatedLegs_args, TradeHostingDealing.getTradeRelatedLegs_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTradeRelatedLegsServiceCall(routeKey, timeout, platformArgs, execTradeId), callback);
  }

  protected TServiceCall create_getTradeRelatedLegsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execTradeId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getTradeRelatedLegs_args request = new TradeHostingDealing.getTradeRelatedLegs_args();
    request.setPlatformArgs(platformArgs);
    request.setExecTradeId(execTradeId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeRelatedLegs");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getTradeRelatedLegs_result.class);
    return serviceCall;
  }

  public void send_getRunningExecOrderIdByOrderRef(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRunningExecOrderIdByOrderRefServiceCall(routeKey, timeout, platformArgs, accountSummary, orderRef), new TRequestOption());
  }

  public void send_getRunningExecOrderIdByOrderRef(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRunningExecOrderIdByOrderRefServiceCall(routeKey, timeout, platformArgs, accountSummary, orderRef), requestOption);
  }

  public long getRunningExecOrderIdByOrderRef(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef, IMethodCallback<TradeHostingDealing.getRunningExecOrderIdByOrderRef_args, TradeHostingDealing.getRunningExecOrderIdByOrderRef_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRunningExecOrderIdByOrderRefServiceCall(routeKey, timeout, platformArgs, accountSummary, orderRef), callback);
  }

  public long add_getRunningExecOrderIdByOrderRefCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef, IMethodCallback<TradeHostingDealing.getRunningExecOrderIdByOrderRef_args, TradeHostingDealing.getRunningExecOrderIdByOrderRef_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRunningExecOrderIdByOrderRefServiceCall(routeKey, timeout, platformArgs, accountSummary, orderRef), callback);
  }

  protected TServiceCall create_getRunningExecOrderIdByOrderRefServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getRunningExecOrderIdByOrderRef_args request = new TradeHostingDealing.getRunningExecOrderIdByOrderRef_args();
    request.setPlatformArgs(platformArgs);
    request.setAccountSummary(accountSummary);
    request.setOrderRef(orderRef);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRunningExecOrderIdByOrderRef");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getRunningExecOrderIdByOrderRef_result.class);
    return serviceCall;
  }

  public void send_getRunningExecOrderIdByOrderDealID(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRunningExecOrderIdByOrderDealIDServiceCall(routeKey, timeout, platformArgs, accountSummary, dealId), new TRequestOption());
  }

  public void send_getRunningExecOrderIdByOrderDealID(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRunningExecOrderIdByOrderDealIDServiceCall(routeKey, timeout, platformArgs, accountSummary, dealId), requestOption);
  }

  public long getRunningExecOrderIdByOrderDealID(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId, IMethodCallback<TradeHostingDealing.getRunningExecOrderIdByOrderDealID_args, TradeHostingDealing.getRunningExecOrderIdByOrderDealID_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRunningExecOrderIdByOrderDealIDServiceCall(routeKey, timeout, platformArgs, accountSummary, dealId), callback);
  }

  public long add_getRunningExecOrderIdByOrderDealIDCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId, IMethodCallback<TradeHostingDealing.getRunningExecOrderIdByOrderDealID_args, TradeHostingDealing.getRunningExecOrderIdByOrderDealID_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getRunningExecOrderIdByOrderDealIDServiceCall(routeKey, timeout, platformArgs, accountSummary, dealId), callback);
  }

  protected TServiceCall create_getRunningExecOrderIdByOrderDealIDServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getRunningExecOrderIdByOrderDealID_args request = new TradeHostingDealing.getRunningExecOrderIdByOrderDealID_args();
    request.setPlatformArgs(platformArgs);
    request.setAccountSummary(accountSummary);
    request.setDealId(dealId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRunningExecOrderIdByOrderDealID");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getRunningExecOrderIdByOrderDealID_result.class);
    return serviceCall;
  }

  public void send_getInDealingOrderPage(int routeKey, int timeout, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getInDealingOrderPageServiceCall(routeKey, timeout, platformArgs, pageOption), new TRequestOption());
  }

  public void send_getInDealingOrderPage(int routeKey, int timeout, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getInDealingOrderPageServiceCall(routeKey, timeout, platformArgs, pageOption), requestOption);
  }

  public long getInDealingOrderPage(int routeKey, int timeout, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingDealing.getInDealingOrderPage_args, TradeHostingDealing.getInDealingOrderPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getInDealingOrderPageServiceCall(routeKey, timeout, platformArgs, pageOption), callback);
  }

  public long add_getInDealingOrderPageCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingDealing.getInDealingOrderPage_args, TradeHostingDealing.getInDealingOrderPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getInDealingOrderPageServiceCall(routeKey, timeout, platformArgs, pageOption), callback);
  }

  protected TServiceCall create_getInDealingOrderPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.getInDealingOrderPage_args request = new TradeHostingDealing.getInDealingOrderPage_args();
    request.setPlatformArgs(platformArgs);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getInDealingOrderPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.getInDealingOrderPage_result.class);
    return serviceCall;
  }

  public void send_createExecOrderId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecOrderIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createExecOrderId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecOrderIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createExecOrderId(int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecOrderId_args, TradeHostingDealing.createExecOrderId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecOrderIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createExecOrderIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecOrderId_args, TradeHostingDealing.createExecOrderId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecOrderIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createExecOrderIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.createExecOrderId_args request = new TradeHostingDealing.createExecOrderId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createExecOrderId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.createExecOrderId_result.class);
    return serviceCall;
  }

  public void send_createExecTradeId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecTradeIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createExecTradeId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecTradeIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createExecTradeId(int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecTradeId_args, TradeHostingDealing.createExecTradeId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecTradeIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createExecTradeIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecTradeId_args, TradeHostingDealing.createExecTradeId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecTradeIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createExecTradeIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.createExecTradeId_args request = new TradeHostingDealing.createExecTradeId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createExecTradeId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.createExecTradeId_result.class);
    return serviceCall;
  }

  public void send_createExecTradeLegId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecTradeLegIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createExecTradeLegId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createExecTradeLegIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createExecTradeLegId(int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecTradeLegId_args, TradeHostingDealing.createExecTradeLegId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecTradeLegIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createExecTradeLegIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingDealing.createExecTradeLegId_args, TradeHostingDealing.createExecTradeLegId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createExecTradeLegIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createExecTradeLegIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingDealing.createExecTradeLegId_args request = new TradeHostingDealing.createExecTradeLegId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createExecTradeLegId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingDealing.createExecTradeLegId_result.class);
    return serviceCall;
  }

}
