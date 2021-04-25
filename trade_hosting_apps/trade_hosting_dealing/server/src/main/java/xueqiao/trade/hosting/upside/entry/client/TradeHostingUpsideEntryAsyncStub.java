package xueqiao.trade.hosting.upside.entry.client;

import xueqiao.trade.hosting.upside.entry.TradeHostingUpsideEntry;
import xueqiao.trade.hosting.upside.entry.TradeHostingUpsideEntryVariable;
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
import xueqiao.trade.hosting.upside.entry.TSubProcessInfo;
import xueqiao.trade.hosting.upside.entry.TSyncOrderStateBatchReq;

public class TradeHostingUpsideEntryAsyncStub extends BaseStub { 
  public TradeHostingUpsideEntryAsyncStub() {
    super(TradeHostingUpsideEntryVariable.serviceKey);
  }
  public void send_getSubProcessInfos(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSubProcessInfosServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getSubProcessInfos(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSubProcessInfosServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getSubProcessInfos(int routeKey, int timeout, IMethodCallback<TradeHostingUpsideEntry.getSubProcessInfos_args, TradeHostingUpsideEntry.getSubProcessInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubProcessInfosServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getSubProcessInfosCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingUpsideEntry.getSubProcessInfos_args, TradeHostingUpsideEntry.getSubProcessInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSubProcessInfosServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getSubProcessInfosServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.getSubProcessInfos_args request = new TradeHostingUpsideEntry.getSubProcessInfos_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubProcessInfos");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.getSubProcessInfos_result.class);
    return serviceCall;
  }

  public void send_restartSubProcess(int routeKey, int timeout, long trade_account_id) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_restartSubProcessServiceCall(routeKey, timeout, platformArgs, trade_account_id), new TRequestOption());
  }

  public void send_restartSubProcess(int routeKey, int timeout, long trade_account_id,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_restartSubProcessServiceCall(routeKey, timeout, platformArgs, trade_account_id), requestOption);
  }

  public long restartSubProcess(int routeKey, int timeout, long trade_account_id, IMethodCallback<TradeHostingUpsideEntry.restartSubProcess_args, TradeHostingUpsideEntry.restartSubProcess_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_restartSubProcessServiceCall(routeKey, timeout, platformArgs, trade_account_id), callback);
  }

  public long add_restartSubProcessCall(AsyncCallRunner runner, int routeKey, int timeout, long trade_account_id, IMethodCallback<TradeHostingUpsideEntry.restartSubProcess_args, TradeHostingUpsideEntry.restartSubProcess_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_restartSubProcessServiceCall(routeKey, timeout, platformArgs, trade_account_id), callback);
  }

  protected TServiceCall create_restartSubProcessServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long trade_account_id){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.restartSubProcess_args request = new TradeHostingUpsideEntry.restartSubProcess_args();
    request.setPlatformArgs(platformArgs);
    request.setTrade_account_id(trade_account_id);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("restartSubProcess");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.restartSubProcess_result.class);
    return serviceCall;
  }

  public void send_allocOrderRef(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_allocOrderRefServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_allocOrderRef(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_allocOrderRefServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long allocOrderRef(int routeKey, int timeout, IMethodCallback<TradeHostingUpsideEntry.allocOrderRef_args, TradeHostingUpsideEntry.allocOrderRef_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_allocOrderRefServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_allocOrderRefCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingUpsideEntry.allocOrderRef_args, TradeHostingUpsideEntry.allocOrderRef_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_allocOrderRefServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_allocOrderRefServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.allocOrderRef_args request = new TradeHostingUpsideEntry.allocOrderRef_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("allocOrderRef");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.allocOrderRef_result.class);
    return serviceCall;
  }

  public void send_orderInsert(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder insertOrder) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_orderInsertServiceCall(routeKey, timeout, platformArgs, insertOrder), new TRequestOption());
  }

  public void send_orderInsert(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder insertOrder,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_orderInsertServiceCall(routeKey, timeout, platformArgs, insertOrder), requestOption);
  }

  public long orderInsert(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder insertOrder, IMethodCallback<TradeHostingUpsideEntry.orderInsert_args, TradeHostingUpsideEntry.orderInsert_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_orderInsertServiceCall(routeKey, timeout, platformArgs, insertOrder), callback);
  }

  public long add_orderInsertCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder insertOrder, IMethodCallback<TradeHostingUpsideEntry.orderInsert_args, TradeHostingUpsideEntry.orderInsert_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_orderInsertServiceCall(routeKey, timeout, platformArgs, insertOrder), callback);
  }

  protected TServiceCall create_orderInsertServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrder insertOrder){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.orderInsert_args request = new TradeHostingUpsideEntry.orderInsert_args();
    request.setPlatformArgs(platformArgs);
    request.setInsertOrder(insertOrder);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("orderInsert");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.orderInsert_result.class);
    return serviceCall;
  }

  public void send_orderDelete(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder deleteOrder) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_orderDeleteServiceCall(routeKey, timeout, platformArgs, deleteOrder), new TRequestOption());
  }

  public void send_orderDelete(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder deleteOrder,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_orderDeleteServiceCall(routeKey, timeout, platformArgs, deleteOrder), requestOption);
  }

  public long orderDelete(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder deleteOrder, IMethodCallback<TradeHostingUpsideEntry.orderDelete_args, TradeHostingUpsideEntry.orderDelete_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_orderDeleteServiceCall(routeKey, timeout, platformArgs, deleteOrder), callback);
  }

  public long add_orderDeleteCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder deleteOrder, IMethodCallback<TradeHostingUpsideEntry.orderDelete_args, TradeHostingUpsideEntry.orderDelete_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_orderDeleteServiceCall(routeKey, timeout, platformArgs, deleteOrder), callback);
  }

  protected TServiceCall create_orderDeleteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrder deleteOrder){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.orderDelete_args request = new TradeHostingUpsideEntry.orderDelete_args();
    request.setPlatformArgs(platformArgs);
    request.setDeleteOrder(deleteOrder);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("orderDelete");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.orderDelete_result.class);
    return serviceCall;
  }

  public void send_syncOrderState(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderStateServiceCall(routeKey, timeout, platformArgs, syncOrder), new TRequestOption());
  }

  public void send_syncOrderState(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderStateServiceCall(routeKey, timeout, platformArgs, syncOrder), requestOption);
  }

  public long syncOrderState(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder, IMethodCallback<TradeHostingUpsideEntry.syncOrderState_args, TradeHostingUpsideEntry.syncOrderState_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderStateServiceCall(routeKey, timeout, platformArgs, syncOrder), callback);
  }

  public long add_syncOrderStateCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder, IMethodCallback<TradeHostingUpsideEntry.syncOrderState_args, TradeHostingUpsideEntry.syncOrderState_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderStateServiceCall(routeKey, timeout, platformArgs, syncOrder), callback);
  }

  protected TServiceCall create_syncOrderStateServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrder syncOrder){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.syncOrderState_args request = new TradeHostingUpsideEntry.syncOrderState_args();
    request.setPlatformArgs(platformArgs);
    request.setSyncOrder(syncOrder);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("syncOrderState");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.syncOrderState_result.class);
    return serviceCall;
  }

  public void send_syncOrderTrades(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderTradesServiceCall(routeKey, timeout, platformArgs, syncOrder), new TRequestOption());
  }

  public void send_syncOrderTrades(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderTradesServiceCall(routeKey, timeout, platformArgs, syncOrder), requestOption);
  }

  public long syncOrderTrades(int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder, IMethodCallback<TradeHostingUpsideEntry.syncOrderTrades_args, TradeHostingUpsideEntry.syncOrderTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderTradesServiceCall(routeKey, timeout, platformArgs, syncOrder), callback);
  }

  public long add_syncOrderTradesCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.HostingExecOrder syncOrder, IMethodCallback<TradeHostingUpsideEntry.syncOrderTrades_args, TradeHostingUpsideEntry.syncOrderTrades_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderTradesServiceCall(routeKey, timeout, platformArgs, syncOrder), callback);
  }

  protected TServiceCall create_syncOrderTradesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrder syncOrder){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.syncOrderTrades_args request = new TradeHostingUpsideEntry.syncOrderTrades_args();
    request.setPlatformArgs(platformArgs);
    request.setSyncOrder(syncOrder);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("syncOrderTrades");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.syncOrderTrades_result.class);
    return serviceCall;
  }

  public void send_syncOrderStateBatch(int routeKey, int timeout, TSyncOrderStateBatchReq batchReq) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderStateBatchServiceCall(routeKey, timeout, platformArgs, batchReq), new TRequestOption());
  }

  public void send_syncOrderStateBatch(int routeKey, int timeout, TSyncOrderStateBatchReq batchReq,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncOrderStateBatchServiceCall(routeKey, timeout, platformArgs, batchReq), requestOption);
  }

  public long syncOrderStateBatch(int routeKey, int timeout, TSyncOrderStateBatchReq batchReq, IMethodCallback<TradeHostingUpsideEntry.syncOrderStateBatch_args, TradeHostingUpsideEntry.syncOrderStateBatch_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderStateBatchServiceCall(routeKey, timeout, platformArgs, batchReq), callback);
  }

  public long add_syncOrderStateBatchCall(AsyncCallRunner runner, int routeKey, int timeout, TSyncOrderStateBatchReq batchReq, IMethodCallback<TradeHostingUpsideEntry.syncOrderStateBatch_args, TradeHostingUpsideEntry.syncOrderStateBatch_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncOrderStateBatchServiceCall(routeKey, timeout, platformArgs, batchReq), callback);
  }

  protected TServiceCall create_syncOrderStateBatchServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSyncOrderStateBatchReq batchReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingUpsideEntryVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingUpsideEntry.syncOrderStateBatch_args request = new TradeHostingUpsideEntry.syncOrderStateBatch_args();
    request.setPlatformArgs(platformArgs);
    request.setBatchReq(batchReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("syncOrderStateBatch");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingUpsideEntry.syncOrderStateBatch_result.class);
    return serviceCall;
  }

}
