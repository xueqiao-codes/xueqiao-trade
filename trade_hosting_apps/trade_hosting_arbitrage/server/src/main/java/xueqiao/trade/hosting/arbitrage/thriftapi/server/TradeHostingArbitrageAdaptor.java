package xueqiao.trade.hosting.arbitrage.thriftapi.server;

import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrage;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public abstract class TradeHostingArbitrageAdaptor implements TradeHostingArbitrage.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void createXQOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingXQOrder order) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"createXQOrder",platformArgs);
createXQOrder(oCntl, order);
  }

  protected abstract void createXQOrder(TServiceCntl oCntl, HostingXQOrder order) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void cancelXQOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"cancelXQOrder",platformArgs);
cancelXQOrder(oCntl, orderId);
  }

  protected abstract void cancelXQOrder(TServiceCntl oCntl, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void suspendXQOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"suspendXQOrder",platformArgs);
suspendXQOrder(oCntl, orderId);
  }

  protected abstract void suspendXQOrder(TServiceCntl oCntl, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void resumeXQOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId, HostingXQOrderResumeMode resumeMode) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"resumeXQOrder",platformArgs);
resumeXQOrder(oCntl, orderId, resumeMode);
  }

  protected abstract void resumeXQOrder(TServiceCntl oCntl, String orderId, HostingXQOrderResumeMode resumeMode) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public QueryEffectXQOrderIndexPage getEffectXQOrderIndexPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getEffectXQOrderIndexPage",platformArgs);
    return getEffectXQOrderIndexPage(oCntl, qryOption, pageOption);
  }

  protected abstract QueryEffectXQOrderIndexPage getEffectXQOrderIndexPage(TServiceCntl oCntl, QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,HostingXQOrder> getXQOrders(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getXQOrders",platformArgs);
    return getXQOrders(oCntl, orderIds);
  }

  protected abstract Map<String,HostingXQOrder> getXQOrders(TServiceCntl oCntl, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,List<HostingXQTrade>> getXQTrades(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getXQTrades",platformArgs);
    return getXQTrades(oCntl, orderIds);
  }

  protected abstract Map<String,List<HostingXQTrade>> getXQTrades(TServiceCntl oCntl, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,HostingXQOrderWithTradeList> getXQOrderWithTradeLists(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getXQOrderWithTradeLists",platformArgs);
    return getXQOrderWithTradeLists(oCntl, orderIds);
  }

  protected abstract Map<String,HostingXQOrderWithTradeList> getXQOrderWithTradeLists(TServiceCntl oCntl, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQOrderExecDetail getXQOrderExecDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getXQOrderExecDetail",platformArgs);
    return getXQOrderExecDetail(oCntl, orderId);
  }

  protected abstract HostingXQOrderExecDetail getXQOrderExecDetail(TServiceCntl oCntl, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,HostingXQTrade> filterXQTrades(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<String> orderIds, Set<Long> tradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"filterXQTrades",platformArgs);
    return filterXQTrades(oCntl, orderIds, tradeIds);
  }

  protected abstract Map<Long,HostingXQTrade> filterXQTrades(TServiceCntl oCntl, Set<String> orderIds, Set<Long> tradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<HostingXQTradeRelatedItem> getXQTradeRelatedItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String orderId, long tradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getXQTradeRelatedItems",platformArgs);
    return getXQTradeRelatedItems(oCntl, orderId, tradeId);
  }

  protected abstract List<HostingXQTradeRelatedItem> getXQTradeRelatedItems(TServiceCntl oCntl, String orderId, long tradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQComposeLimitOrderSettings getSystemXQComposeLimitOrderSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"getSystemXQComposeLimitOrderSettings",platformArgs);
    return getSystemXQComposeLimitOrderSettings(oCntl);
  }

  protected abstract HostingXQComposeLimitOrderSettings getSystemXQComposeLimitOrderSettings(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setSystemXQComposeLimitOrderSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingXQComposeLimitOrderSettings settings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingArbitrageVariable.serviceKey,"setSystemXQComposeLimitOrderSettings",platformArgs);
setSystemXQComposeLimitOrderSettings(oCntl, settings);
  }

  protected abstract void setSystemXQComposeLimitOrderSettings(TServiceCntl oCntl, HostingXQComposeLimitOrderSettings settings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingArbitrageAdaptor(){
    methodParameterNameMap.put("createXQOrder",new String[]{"platformArgs", "order"});
    methodParameterNameMap.put("cancelXQOrder",new String[]{"platformArgs", "orderId"});
    methodParameterNameMap.put("suspendXQOrder",new String[]{"platformArgs", "orderId"});
    methodParameterNameMap.put("resumeXQOrder",new String[]{"platformArgs", "orderId", "resumeMode"});
    methodParameterNameMap.put("getEffectXQOrderIndexPage",new String[]{"platformArgs", "qryOption", "pageOption"});
    methodParameterNameMap.put("getXQOrders",new String[]{"platformArgs", "orderIds"});
    methodParameterNameMap.put("getXQTrades",new String[]{"platformArgs", "orderIds"});
    methodParameterNameMap.put("getXQOrderWithTradeLists",new String[]{"platformArgs", "orderIds"});
    methodParameterNameMap.put("getXQOrderExecDetail",new String[]{"platformArgs", "orderId"});
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("filterXQTrades",new String[]{"platformArgs", "orderIds", "tradeIds"});
    methodParameterNameMap.put("getXQTradeRelatedItems",new String[]{"platformArgs", "orderId", "tradeId"});
    methodParameterNameMap.put("getSystemXQComposeLimitOrderSettings",new String[]{"platformArgs"});
    methodParameterNameMap.put("setSystemXQComposeLimitOrderSettings",new String[]{"platformArgs", "settings"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
