package xueqiao.trade.hosting.tradeaccount.data.client;

import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountData;
import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountDataVariable;
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
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;

public class TradeHostingTradeAccountDataAsyncStub extends BaseStub { 
  public TradeHostingTradeAccountDataAsyncStub() {
    super(TradeHostingTradeAccountDataVariable.serviceKey);
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

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingTradeAccountData.clearAll_args, TradeHostingTradeAccountData.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingTradeAccountData.clearAll_args, TradeHostingTradeAccountData.clearAll_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
    serviceCall.setServiceKey(TradeHostingTradeAccountDataVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTradeAccountData.clearAll_args request = new TradeHostingTradeAccountData.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTradeAccountData.clearAll_result.class);
    return serviceCall;
  }

  public void send_getNowFund(int routeKey, int timeout, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getNowFundServiceCall(routeKey, timeout, platformArgs, tradeAccountId), new TRequestOption());
  }

  public void send_getNowFund(int routeKey, int timeout, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getNowFundServiceCall(routeKey, timeout, platformArgs, tradeAccountId), requestOption);
  }

  public long getNowFund(int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingTradeAccountData.getNowFund_args, TradeHostingTradeAccountData.getNowFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getNowFundServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  public long add_getNowFundCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingTradeAccountData.getNowFund_args, TradeHostingTradeAccountData.getNowFund_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getNowFundServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  protected TServiceCall create_getNowFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTradeAccountDataVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTradeAccountData.getNowFund_args request = new TradeHostingTradeAccountData.getNowFund_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getNowFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTradeAccountData.getNowFund_result.class);
    return serviceCall;
  }

  public void send_getHisFunds(int routeKey, int timeout, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getHisFundsServiceCall(routeKey, timeout, platformArgs, tradeAccountId, fundDateBegin, fundDateEnd), new TRequestOption());
  }

  public void send_getHisFunds(int routeKey, int timeout, long tradeAccountId, String fundDateBegin, String fundDateEnd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getHisFundsServiceCall(routeKey, timeout, platformArgs, tradeAccountId, fundDateBegin, fundDateEnd), requestOption);
  }

  public long getHisFunds(int routeKey, int timeout, long tradeAccountId, String fundDateBegin, String fundDateEnd, IMethodCallback<TradeHostingTradeAccountData.getHisFunds_args, TradeHostingTradeAccountData.getHisFunds_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHisFundsServiceCall(routeKey, timeout, platformArgs, tradeAccountId, fundDateBegin, fundDateEnd), callback);
  }

  public long add_getHisFundsCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, String fundDateBegin, String fundDateEnd, IMethodCallback<TradeHostingTradeAccountData.getHisFunds_args, TradeHostingTradeAccountData.getHisFunds_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getHisFundsServiceCall(routeKey, timeout, platformArgs, tradeAccountId, fundDateBegin, fundDateEnd), callback);
  }

  protected TServiceCall create_getHisFundsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String fundDateBegin, String fundDateEnd){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTradeAccountDataVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTradeAccountData.getHisFunds_args request = new TradeHostingTradeAccountData.getHisFunds_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    request.setFundDateBegin(fundDateBegin);
    request.setFundDateEnd(fundDateEnd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHisFunds");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTradeAccountData.getHisFunds_result.class);
    return serviceCall;
  }

  public void send_getSettlementInfos(int routeKey, int timeout, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSettlementInfosServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDateBegin, settlementDateEnd), new TRequestOption());
  }

  public void send_getSettlementInfos(int routeKey, int timeout, long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getSettlementInfosServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDateBegin, settlementDateEnd), requestOption);
  }

  public long getSettlementInfos(int routeKey, int timeout, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTradeAccountData.getSettlementInfos_args, TradeHostingTradeAccountData.getSettlementInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSettlementInfosServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  public long add_getSettlementInfosCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, String settlementDateBegin, String settlementDateEnd, IMethodCallback<TradeHostingTradeAccountData.getSettlementInfos_args, TradeHostingTradeAccountData.getSettlementInfos_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getSettlementInfosServiceCall(routeKey, timeout, platformArgs, tradeAccountId, settlementDateBegin, settlementDateEnd), callback);
  }

  protected TServiceCall create_getSettlementInfosServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String settlementDateBegin, String settlementDateEnd){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTradeAccountDataVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTradeAccountData.getSettlementInfos_args request = new TradeHostingTradeAccountData.getSettlementInfos_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    request.setSettlementDateBegin(settlementDateBegin);
    request.setSettlementDateEnd(settlementDateEnd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSettlementInfos");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTradeAccountData.getSettlementInfos_result.class);
    return serviceCall;
  }

  public void send_getNetPositionSummaries(int routeKey, int timeout, long tradeAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getNetPositionSummariesServiceCall(routeKey, timeout, platformArgs, tradeAccountId), new TRequestOption());
  }

  public void send_getNetPositionSummaries(int routeKey, int timeout, long tradeAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getNetPositionSummariesServiceCall(routeKey, timeout, platformArgs, tradeAccountId), requestOption);
  }

  public long getNetPositionSummaries(int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingTradeAccountData.getNetPositionSummaries_args, TradeHostingTradeAccountData.getNetPositionSummaries_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getNetPositionSummariesServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  public long add_getNetPositionSummariesCall(AsyncCallRunner runner, int routeKey, int timeout, long tradeAccountId, IMethodCallback<TradeHostingTradeAccountData.getNetPositionSummaries_args, TradeHostingTradeAccountData.getNetPositionSummaries_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_getNetPositionSummariesServiceCall(routeKey, timeout, platformArgs, tradeAccountId), callback);
  }

  protected TServiceCall create_getNetPositionSummariesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTradeAccountDataVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTradeAccountData.getNetPositionSummaries_args request = new TradeHostingTradeAccountData.getNetPositionSummaries_args();
    request.setPlatformArgs(platformArgs);
    request.setTradeAccountId(tradeAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getNetPositionSummaries");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTradeAccountData.getNetPositionSummaries_result.class);
    return serviceCall;
  }

}
