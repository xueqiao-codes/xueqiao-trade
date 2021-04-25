package xueqiao.trade.hosting.risk.dealing.thriftapi.client;

import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealing;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealingVariable;
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
import xueqiao.trade.hosting.risk.dealing.thriftapi.HostingExecOrderRiskAction;

public class TradeHostingRiskDealingAsyncStub extends BaseStub { 
  public TradeHostingRiskDealingAsyncStub() {
    super(TradeHostingRiskDealingVariable.serviceKey);
  }
  public void send_riskCheck(int routeKey, int timeout, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_riskCheckServiceCall(routeKey, timeout, platformArgs, subAccountId, orderContractSummary, orderDetail), new TRequestOption());
  }

  public void send_riskCheck(int routeKey, int timeout, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_riskCheckServiceCall(routeKey, timeout, platformArgs, subAccountId, orderContractSummary, orderDetail), requestOption);
  }

  public long riskCheck(int routeKey, int timeout, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, IMethodCallback<TradeHostingRiskDealing.riskCheck_args, TradeHostingRiskDealing.riskCheck_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_riskCheckServiceCall(routeKey, timeout, platformArgs, subAccountId, orderContractSummary, orderDetail), callback);
  }

  public long add_riskCheckCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, IMethodCallback<TradeHostingRiskDealing.riskCheck_args, TradeHostingRiskDealing.riskCheck_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
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
            create_riskCheckServiceCall(routeKey, timeout, platformArgs, subAccountId, orderContractSummary, orderDetail), callback);
  }

  protected TServiceCall create_riskCheckServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskDealingVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskDealing.riskCheck_args request = new TradeHostingRiskDealing.riskCheck_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setOrderContractSummary(orderContractSummary);
    request.setOrderDetail(orderDetail);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("riskCheck");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskDealing.riskCheck_result.class);
    return serviceCall;
  }

}
