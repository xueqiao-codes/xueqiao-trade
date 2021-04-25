package xueqiao.trade.hosting.dealing.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.dealing.thriftapi.HostingExecOrderPage;
import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealing;
import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealingVariable;


public abstract class TradeHostingDealingAdaptor implements TradeHostingDealing.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void createUserDeal(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"createUserDeal",platformArgs);
createUserDeal(oCntl, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source);
  }

  protected abstract void createUserDeal(TServiceCntl oCntl, int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void revokeDeal(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"revokeDeal",platformArgs);
revokeDeal(oCntl, execOrderId);
  }

  protected abstract void revokeDeal(TServiceCntl oCntl, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingExecOrder> getOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getOrder",platformArgs);
    return getOrder(oCntl, execOrderId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingExecOrder> getOrder(TServiceCntl oCntl, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingExecTrade> getOrderTrades(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getOrderTrades",platformArgs);
    return getOrderTrades(oCntl, execOrderId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingExecTrade> getOrderTrades(TServiceCntl oCntl, long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingExecTradeLeg> getTradeRelatedLegs(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long execTradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getTradeRelatedLegs",platformArgs);
    return getTradeRelatedLegs(oCntl, execTradeId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingExecTradeLeg> getTradeRelatedLegs(TServiceCntl oCntl, long execTradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long getRunningExecOrderIdByOrderRef(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getRunningExecOrderIdByOrderRef",platformArgs);
    return getRunningExecOrderIdByOrderRef(oCntl, accountSummary, orderRef);
  }

  protected abstract long getRunningExecOrderIdByOrderRef(TServiceCntl oCntl, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long getRunningExecOrderIdByOrderDealID(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getRunningExecOrderIdByOrderDealID",platformArgs);
    return getRunningExecOrderIdByOrderDealID(oCntl, accountSummary, dealId);
  }

  protected abstract long getRunningExecOrderIdByOrderDealID(TServiceCntl oCntl, xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingExecOrderPage getInDealingOrderPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"getInDealingOrderPage",platformArgs);
    return getInDealingOrderPage(oCntl, pageOption);
  }

  protected abstract HostingExecOrderPage getInDealingOrderPage(TServiceCntl oCntl, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createExecOrderId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"createExecOrderId",platformArgs);
    return createExecOrderId(oCntl);
  }

  protected abstract long createExecOrderId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createExecTradeId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"createExecTradeId",platformArgs);
    return createExecTradeId(oCntl);
  }

  protected abstract long createExecTradeId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createExecTradeLegId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingDealingVariable.serviceKey,"createExecTradeLegId",platformArgs);
    return createExecTradeLegId(oCntl);
  }

  protected abstract long createExecTradeLegId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingDealingAdaptor(){
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("createUserDeal",new String[]{"platformArgs", "subUserId", "subAccountId", "execOrderId", "contractSummary", "orderDetail", "source"});
    methodParameterNameMap.put("revokeDeal",new String[]{"platformArgs", "execOrderId"});
    methodParameterNameMap.put("getOrder",new String[]{"platformArgs", "execOrderId"});
    methodParameterNameMap.put("getOrderTrades",new String[]{"platformArgs", "execOrderId"});
    methodParameterNameMap.put("getTradeRelatedLegs",new String[]{"platformArgs", "execTradeId"});
    methodParameterNameMap.put("getRunningExecOrderIdByOrderRef",new String[]{"platformArgs", "accountSummary", "orderRef"});
    methodParameterNameMap.put("getRunningExecOrderIdByOrderDealID",new String[]{"platformArgs", "accountSummary", "dealId"});
    methodParameterNameMap.put("getInDealingOrderPage",new String[]{"platformArgs", "pageOption"});
    methodParameterNameMap.put("createExecOrderId",new String[]{"platformArgs"});
    methodParameterNameMap.put("createExecTradeId",new String[]{"platformArgs"});
    methodParameterNameMap.put("createExecTradeLegId",new String[]{"platformArgs"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
