package xueqiao.trade.hosting.storage.thriftapi.client;

import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorage;
import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorageVariable;
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
import xueqiao.trade.hosting.storage.thriftapi.TradeAccountInvalidDescription;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;

public class TradeHostingStorageAsyncStub extends BaseStub { 
  public TradeHostingStorageAsyncStub() {
    super(TradeHostingStorageVariable.serviceKey);
  }
  public void send_getTraddeAccount(int routeKey, int timeout, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getTraddeAccountServiceCall(routeKey, timeout, platformArgs, tradeAccountId), new TRequestOption());
  }

  public void send_getTraddeAccount(int routeKey, int timeout, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getTraddeAccountServiceCall(routeKey, timeout, platformArgs, tradeAccountId), requestOption);
  }

  public long getTraddeAccount(int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.getTraddeAccount_args, TradeHostingStorage.getTraddeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTraddeAccountServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  public long add_getTraddeAccountCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.getTraddeAccount_args, TradeHostingStorage.getTraddeAccount_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getTraddeAccountServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  protected TServiceCall create_getTraddeAccountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getTraddeAccount_args request = new TradeHostingStorage.getTraddeAccount_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTraddeAccount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getTraddeAccount_result.class);
    return serviceCall;
  }

  public void send_getBrokerAccessEntry(int routeKey, int timeout, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getBrokerAccessEntryServiceCall(routeKey, timeout, platformArgs, tradeAccountId), new TRequestOption());
  }

  public void send_getBrokerAccessEntry(int routeKey, int timeout, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getBrokerAccessEntryServiceCall(routeKey, timeout, platformArgs, tradeAccountId), requestOption);
  }

  public long getBrokerAccessEntry(int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.getBrokerAccessEntry_args, TradeHostingStorage.getBrokerAccessEntry_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getBrokerAccessEntryServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  public long add_getBrokerAccessEntryCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.getBrokerAccessEntry_args, TradeHostingStorage.getBrokerAccessEntry_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getBrokerAccessEntryServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  protected TServiceCall create_getBrokerAccessEntryServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getBrokerAccessEntry_args request = new TradeHostingStorage.getBrokerAccessEntry_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getBrokerAccessEntry");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getBrokerAccessEntry_result.class);
    return serviceCall;
  }

  public void send_setTradeAccountInvalid(int routeKey, int timeout, long tradeAccountId, TradeAccountInvalidDescription invalidDescription) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setTradeAccountInvalidServiceCall(routeKey, timeout, platformArgs, tradeAccountId, invalidDescription), new TRequestOption());
  }

  public void send_setTradeAccountInvalid(int routeKey, int timeout, long tradeAccountId, TradeAccountInvalidDescription invalidDescription,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setTradeAccountInvalidServiceCall(routeKey, timeout, platformArgs, tradeAccountId, invalidDescription), requestOption);
  }

  public long setTradeAccountInvalid(int routeKey, int timeout, long tradeAccountId, TradeAccountInvalidDescription invalidDescription, IMethodCallback<TradeHostingStorage.setTradeAccountInvalid_args, TradeHostingStorage.setTradeAccountInvalid_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setTradeAccountInvalidServiceCall(routeKey, timeout, platformArgs, tradeAccountId, invalidDescription), callback);
  }

  public long add_setTradeAccountInvalidCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, TradeAccountInvalidDescription invalidDescription, IMethodCallback<TradeHostingStorage.setTradeAccountInvalid_args, TradeHostingStorage.setTradeAccountInvalid_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setTradeAccountInvalidServiceCall(routeKey, timeout, platformArgs, tradeAccountId, invalidDescription), callback);
  }

  protected TServiceCall create_setTradeAccountInvalidServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, TradeAccountInvalidDescription invalidDescription){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.setTradeAccountInvalid_args request = new TradeHostingStorage.setTradeAccountInvalid_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    request.setInvalidDescription(invalidDescription);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setTradeAccountInvalid");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.setTradeAccountInvalid_result.class);
    return serviceCall;
  }

  public void send_setTradeAccountActive(int routeKey, int timeout, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setTradeAccountActiveServiceCall(routeKey, timeout, platformArgs, tradeAccountId), new TRequestOption());
  }

  public void send_setTradeAccountActive(int routeKey, int timeout, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setTradeAccountActiveServiceCall(routeKey, timeout, platformArgs, tradeAccountId), requestOption);
  }

  public long setTradeAccountActive(int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.setTradeAccountActive_args, TradeHostingStorage.setTradeAccountActive_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setTradeAccountActiveServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  public long add_setTradeAccountActiveCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingStorage.setTradeAccountActive_args, TradeHostingStorage.setTradeAccountActive_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_setTradeAccountActiveServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  protected TServiceCall create_setTradeAccountActiveServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.setTradeAccountActive_args request = new TradeHostingStorage.setTradeAccountActive_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setTradeAccountActive");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.setTradeAccountActive_result.class);
    return serviceCall;
  }

  public void send_getAllTradeAccounts(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllTradeAccountsServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getAllTradeAccounts(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllTradeAccountsServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getAllTradeAccounts(int routeKey, int timeout, IMethodCallback<TradeHostingStorage.getAllTradeAccounts_args, TradeHostingStorage.getAllTradeAccounts_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAllTradeAccountsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getAllTradeAccountsCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingStorage.getAllTradeAccounts_args, TradeHostingStorage.getAllTradeAccounts_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getAllTradeAccountsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getAllTradeAccountsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getAllTradeAccounts_args request = new TradeHostingStorage.getAllTradeAccounts_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAllTradeAccounts");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getAllTradeAccounts_result.class);
    return serviceCall;
  }

  public void send_getBrokerAccessEntryFromCloud(int routeKey, int timeout, long tradeBrokerId, long tradeBrokerAccessId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getBrokerAccessEntryFromCloudServiceCall(routeKey, timeout, platformArgs, tradeBrokerId, tradeBrokerAccessId), new TRequestOption());
  }

  public void send_getBrokerAccessEntryFromCloud(int routeKey, int timeout, long tradeBrokerId, long tradeBrokerAccessId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getBrokerAccessEntryFromCloudServiceCall(routeKey, timeout, platformArgs, tradeBrokerId, tradeBrokerAccessId), requestOption);
  }

  public long getBrokerAccessEntryFromCloud(int routeKey, int timeout, long tradeBrokerId, long tradeBrokerAccessId, IMethodCallback<TradeHostingStorage.getBrokerAccessEntryFromCloud_args, TradeHostingStorage.getBrokerAccessEntryFromCloud_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getBrokerAccessEntryFromCloudServiceCall(routeKey, timeout, platformArgs, tradeBrokerId, tradeBrokerAccessId), callback);
  }

  public long add_getBrokerAccessEntryFromCloudCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeBrokerId, long tradeBrokerAccessId, IMethodCallback<TradeHostingStorage.getBrokerAccessEntryFromCloud_args, TradeHostingStorage.getBrokerAccessEntryFromCloud_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getBrokerAccessEntryFromCloudServiceCall(routeKey, timeout, platformArgs, tradeBrokerId, tradeBrokerAccessId), callback);
  }

  protected TServiceCall create_getBrokerAccessEntryFromCloudServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeBrokerId, long tradeBrokerAccessId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getBrokerAccessEntryFromCloud_args request = new TradeHostingStorage.getBrokerAccessEntryFromCloud_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeBrokerId(tradeBrokerId);
    request.setTradeBrokerAccessId(tradeBrokerAccessId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getBrokerAccessEntryFromCloud");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getBrokerAccessEntryFromCloud_result.class);
    return serviceCall;
  }

  public void send_createComposeGraphId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createComposeGraphIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createComposeGraphId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createComposeGraphIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createComposeGraphId(int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createComposeGraphId_args, TradeHostingStorage.createComposeGraphId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createComposeGraphIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createComposeGraphIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createComposeGraphId_args, TradeHostingStorage.createComposeGraphId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createComposeGraphIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createComposeGraphIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.createComposeGraphId_args request = new TradeHostingStorage.createComposeGraphId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createComposeGraphId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.createComposeGraphId_result.class);
    return serviceCall;
  }

  public void send_createTradeAccountId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createTradeAccountIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createTradeAccountId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createTradeAccountIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createTradeAccountId(int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createTradeAccountId_args, TradeHostingStorage.createTradeAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createTradeAccountIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createTradeAccountIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createTradeAccountId_args, TradeHostingStorage.createTradeAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createTradeAccountIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createTradeAccountIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.createTradeAccountId_args request = new TradeHostingStorage.createTradeAccountId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createTradeAccountId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.createTradeAccountId_result.class);
    return serviceCall;
  }

  public void send_createSubAccountId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createSubAccountIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_createSubAccountId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_createSubAccountIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long createSubAccountId(int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createSubAccountId_args, TradeHostingStorage.createSubAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createSubAccountIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_createSubAccountIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingStorage.createSubAccountId_args, TradeHostingStorage.createSubAccountId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_createSubAccountIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_createSubAccountIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.createSubAccountId_args request = new TradeHostingStorage.createSubAccountId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("createSubAccountId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.createSubAccountId_result.class);
    return serviceCall;
  }

  public void send_updateConfig(int routeKey, int timeout, UpdateConfigDescription configDescription) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateConfigServiceCall(routeKey, timeout, platformArgs, configDescription), new TRequestOption());
  }

  public void send_updateConfig(int routeKey, int timeout, UpdateConfigDescription configDescription,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateConfigServiceCall(routeKey, timeout, platformArgs, configDescription), requestOption);
  }

  public long updateConfig(int routeKey, int timeout, UpdateConfigDescription configDescription, IMethodCallback<TradeHostingStorage.updateConfig_args, TradeHostingStorage.updateConfig_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateConfigServiceCall(routeKey, timeout, platformArgs, configDescription), callback);
  }

  public long add_updateConfigCall(AsyncCallRunner runner, int routeKey, int timeout, UpdateConfigDescription configDescription, IMethodCallback<TradeHostingStorage.updateConfig_args, TradeHostingStorage.updateConfig_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_updateConfigServiceCall(routeKey, timeout, platformArgs, configDescription), callback);
  }

  protected TServiceCall create_updateConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, UpdateConfigDescription configDescription){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.updateConfig_args request = new TradeHostingStorage.updateConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setConfigDescription(configDescription);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.updateConfig_result.class);
    return serviceCall;
  }

  public void send_getMachineId(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getMachineIdServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getMachineId(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getMachineIdServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getMachineId(int routeKey, int timeout, IMethodCallback<TradeHostingStorage.getMachineId_args, TradeHostingStorage.getMachineId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getMachineIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getMachineIdCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingStorage.getMachineId_args, TradeHostingStorage.getMachineId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getMachineIdServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getMachineIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getMachineId_args request = new TradeHostingStorage.getMachineId_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getMachineId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getMachineId_result.class);
    return serviceCall;
  }

  public void send_getHostingSession(int routeKey, int timeout, int subUserId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getHostingSessionServiceCall(routeKey, timeout, platformArgs, subUserId), new TRequestOption());
  }

  public void send_getHostingSession(int routeKey, int timeout, int subUserId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getHostingSessionServiceCall(routeKey, timeout, platformArgs, subUserId), requestOption);
  }

  public long getHostingSession(int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingStorage.getHostingSession_args, TradeHostingStorage.getHostingSession_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSessionServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  public long add_getHostingSessionCall(AsyncCallRunner runner, int routeKey, int timeout, int subUserId, IMethodCallback<TradeHostingStorage.getHostingSession_args, TradeHostingStorage.getHostingSession_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHostingSessionServiceCall(routeKey, timeout, platformArgs, subUserId), callback);
  }

  protected TServiceCall create_getHostingSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingStorageVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingStorage.getHostingSession_args request = new TradeHostingStorage.getHostingSession_args();
    request.setPlatformArgs(platformArgs);
    request.setSubUserId(subUserId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingStorage.getHostingSession_result.class);
    return serviceCall;
  }

}
