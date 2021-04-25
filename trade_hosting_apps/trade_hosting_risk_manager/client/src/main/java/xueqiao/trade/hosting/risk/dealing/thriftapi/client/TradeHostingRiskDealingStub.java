package xueqiao.trade.hosting.risk.dealing.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.risk.dealing.thriftapi.HostingExecOrderRiskAction;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealing;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealingVariable;

public class TradeHostingRiskDealingStub extends BaseStub {

  public TradeHostingRiskDealingStub() {
    super(TradeHostingRiskDealingVariable.serviceKey);
  }

  public HostingExecOrderRiskAction  riskCheck(long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return riskCheck(subAccountId, orderContractSummary, orderDetail, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingExecOrderRiskAction  riskCheck(long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("riskCheck").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingExecOrderRiskAction>(){
    @Override
    public HostingExecOrderRiskAction call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskDealing.Client(protocol).riskCheck(platformArgs, subAccountId, orderContractSummary, orderDetail);
      }
    }, invokeInfo);
  }

  public HostingExecOrderRiskAction  riskCheck(int routeKey, int timeout,long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return riskCheck(subAccountId, orderContractSummary, orderDetail, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
