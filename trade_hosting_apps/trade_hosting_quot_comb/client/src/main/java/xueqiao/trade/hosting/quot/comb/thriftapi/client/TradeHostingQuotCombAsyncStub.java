package xueqiao.trade.hosting.quot.comb.thriftapi.client;

import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotComb;
import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotCombVariable;
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
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;

public class TradeHostingQuotCombAsyncStub extends BaseStub { 
  public TradeHostingQuotCombAsyncStub() {
    super(TradeHostingQuotCombVariable.serviceKey);
  }
  public void send_syncCombTopics(int routeKey, int timeout, SyncCombTopicsRequest syncRequest) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncCombTopicsServiceCall(routeKey, timeout, platformArgs, syncRequest), new TRequestOption());
  }

  public void send_syncCombTopics(int routeKey, int timeout, SyncCombTopicsRequest syncRequest,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncCombTopicsServiceCall(routeKey, timeout, platformArgs, syncRequest), requestOption);
  }

  public long syncCombTopics(int routeKey, int timeout, SyncCombTopicsRequest syncRequest, IMethodCallback<TradeHostingQuotComb.syncCombTopics_args, TradeHostingQuotComb.syncCombTopics_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncCombTopicsServiceCall(routeKey, timeout, platformArgs, syncRequest), callback);
  }

  public long add_syncCombTopicsCall(AsyncCallRunner runner, int routeKey, int timeout, SyncCombTopicsRequest syncRequest, IMethodCallback<TradeHostingQuotComb.syncCombTopics_args, TradeHostingQuotComb.syncCombTopics_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_syncCombTopicsServiceCall(routeKey, timeout, platformArgs, syncRequest), callback);
  }

  protected TServiceCall create_syncCombTopicsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingQuotCombVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingQuotComb.syncCombTopics_args request = new TradeHostingQuotComb.syncCombTopics_args();
    request.setPlatformArgs(platformArgs);
    request.setSyncRequest(syncRequest);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("syncCombTopics");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingQuotComb.syncCombTopics_result.class);
    return serviceCall;
  }

}
