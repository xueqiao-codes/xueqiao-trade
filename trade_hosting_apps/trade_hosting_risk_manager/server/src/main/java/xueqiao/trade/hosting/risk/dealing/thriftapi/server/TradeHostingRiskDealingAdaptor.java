package xueqiao.trade.hosting.risk.dealing.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.risk.dealing.thriftapi.HostingExecOrderRiskAction;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealing;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealingVariable;


public abstract class TradeHostingRiskDealingAdaptor implements TradeHostingRiskDealing.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public HostingExecOrderRiskAction riskCheck(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskDealingVariable.serviceKey,"riskCheck",platformArgs);
    return riskCheck(oCntl, subAccountId, orderContractSummary, orderDetail);
  }

  protected abstract HostingExecOrderRiskAction riskCheck(TServiceCntl oCntl, long subAccountId, xueqiao.trade.hosting.HostingExecOrderContractSummary orderContractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingRiskDealingAdaptor(){
    methodParameterNameMap.put("riskCheck",new String[]{"platformArgs", "subAccountId", "orderContractSummary", "orderDetail"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
