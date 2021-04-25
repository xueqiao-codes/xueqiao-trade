package xueqiao.trade.hosting.terminal.ao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.terminal.ao.HostingComposeViewDetail;
import xueqiao.trade.hosting.terminal.ao.HostingSAWRUItemListPage;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundHisItem;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundItem;
import xueqiao.trade.hosting.terminal.ao.HostingUserSetting;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderPage;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderWithTradeListPage;
import xueqiao.trade.hosting.terminal.ao.HostingXQTradePage;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailPage;
import xueqiao.trade.hosting.terminal.ao.QueryHostingSAWRUItemListOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountPage;
import xueqiao.trade.hosting.terminal.ao.QuerySameComposeGraphsPage;
import xueqiao.trade.hosting.terminal.ao.QueryXQTradeLameTaskNotePageOption;
import xueqiao.trade.hosting.terminal.ao.ReqMailBoxMessageOption;
import xueqiao.trade.hosting.terminal.ao.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.terminal.ao.TradeAccountSettlementInfoWithRelatedTime;
import xueqiao.trade.hosting.terminal.ao.TradeHostingTerminalAo;
import xueqiao.trade.hosting.terminal.ao.TradeHostingTerminalAoVariable;


public abstract class TradeHostingTerminalAoAdaptor implements TradeHostingTerminalAo.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public xueqiao.trade.hosting.QueryHostingUserPage getHostingUserPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingUserPage",platformArgs);
    return getHostingUserPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.QueryHostingUserPage getHostingUserPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void heartBeat(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"heartBeat",platformArgs);
heartBeat(oCntl, landingInfo);
  }

  protected abstract void heartBeat(TServiceCntl oCntl, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void logout(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"logout",platformArgs);
logout(oCntl, landingInfo);
  }

  protected abstract void logout(TServiceCntl oCntl, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,HostingComposeViewDetail> getComposeViewDetails(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getComposeViewDetails",platformArgs);
    return getComposeViewDetails(oCntl, landingInfo, composeGraphIds);
  }

  protected abstract Map<Long,HostingComposeViewDetail> getComposeViewDetails(TServiceCntl oCntl, LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void changeComposeViewPrecisionNumber(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"changeComposeViewPrecisionNumber",platformArgs);
changeComposeViewPrecisionNumber(oCntl, landingInfo, composeGraphId, precisionNumber);
  }

  protected abstract void changeComposeViewPrecisionNumber(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createComposeGraph(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"createComposeGraph",platformArgs);
    return createComposeGraph(oCntl, landingInfo, newGraph, aliasName, precisionNumber);
  }

  protected abstract long createComposeGraph(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void delComposeView(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"delComposeView",platformArgs);
delComposeView(oCntl, landingInfo, composeGraphId);
  }

  protected abstract void delComposeView(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public QueryHostingComposeViewDetailPage getComposeViewDetailPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getComposeViewDetailPage",platformArgs);
    return getComposeViewDetailPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract QueryHostingComposeViewDetailPage getComposeViewDetailPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public QuerySameComposeGraphsPage getSameComposeGraphsPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSameComposeGraphsPage",platformArgs);
    return getSameComposeGraphsPage(oCntl, landingInfo, graph, pageOption);
  }

  protected abstract QuerySameComposeGraphsPage getSameComposeGraphsPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addComposeViewBySearch(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addComposeViewBySearch",platformArgs);
addComposeViewBySearch(oCntl, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber);
  }

  protected abstract void addComposeViewBySearch(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void subscribeComposeViewQuotation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"subscribeComposeViewQuotation",platformArgs);
subscribeComposeViewQuotation(oCntl, landingInfo, composeGraphId);
  }

  protected abstract void subscribeComposeViewQuotation(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void unSubscribeComposeViewQuotation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"unSubscribeComposeViewQuotation",platformArgs);
unSubscribeComposeViewQuotation(oCntl, landingInfo, composeGraphId);
  }

  protected abstract void unSubscribeComposeViewQuotation(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void changeComposeViewAliasName(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String aliasName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"changeComposeViewAliasName",platformArgs);
changeComposeViewAliasName(oCntl, landingInfo, composeGraphId, aliasName);
  }

  protected abstract void changeComposeViewAliasName(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId, String aliasName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,xueqiao.trade.hosting.HostingComposeGraph> getComposeGraphs(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getComposeGraphs",platformArgs);
    return getComposeGraphs(oCntl, landingInfo, composeGraphIds);
  }

  protected abstract Map<Long,xueqiao.trade.hosting.HostingComposeGraph> getComposeGraphs(TServiceCntl oCntl, LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addComposeViewByShare(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addComposeViewByShare",platformArgs);
addComposeViewByShare(oCntl, landingInfo, composeGraphId, aliasName, precisionNumber);
  }

  protected abstract void addComposeViewByShare(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long addTradeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addTradeAccount",platformArgs);
    return addTradeAccount(oCntl, landingInfo, newAccount);
  }

  protected abstract long addTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void disableTradeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"disableTradeAccount",platformArgs);
disableTradeAccount(oCntl, landingInfo, tradeAccountId);
  }

  protected abstract void disableTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public QueryHostingTradeAccountPage getTradeAccountPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountPage",platformArgs);
    return getTradeAccountPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract QueryHostingTradeAccountPage getTradeAccountPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void enableTradeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"enableTradeAccount",platformArgs);
enableTradeAccount(oCntl, landingInfo, tradeAccountId);
  }

  protected abstract void enableTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateTradeAccountInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateTradeAccountInfo",platformArgs);
updateTradeAccountInfo(oCntl, landingInfo, updateAccount);
  }

  protected abstract void updateTradeAccountInfo(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void rmTradeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"rmTradeAccount",platformArgs);
rmTradeAccount(oCntl, landingInfo, tradeAccountId);
  }

  protected abstract void rmTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingTradeAccount> getPersonalUserTradeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getPersonalUserTradeAccount",platformArgs);
    return getPersonalUserTradeAccount(oCntl, landingInfo, subAccountId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingTradeAccount> getPersonalUserTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.HostingOrderRouteTree getHostingOrderRouteTree(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingOrderRouteTree",platformArgs);
    return getHostingOrderRouteTree(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.HostingOrderRouteTree getHostingOrderRouteTree(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateHostingOrderRouteTree(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateHostingOrderRouteTree",platformArgs);
updateHostingOrderRouteTree(oCntl, landingInfo, subAccountId, routeTree);
  }

  protected abstract void updateHostingOrderRouteTree(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getHostingOrderRouteTreeVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingOrderRouteTreeVersion",platformArgs);
    return getHostingOrderRouteTreeVersion(oCntl, landingInfo, subAccountId);
  }

  protected abstract int getHostingOrderRouteTreeVersion(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.HostingOrderRouteTree getPersonalUserHostingOrderRouteTree(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getPersonalUserHostingOrderRouteTree",platformArgs);
    return getPersonalUserHostingOrderRouteTree(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.HostingOrderRouteTree getPersonalUserHostingOrderRouteTree(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void createXQOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"createXQOrder",platformArgs);
createXQOrder(oCntl, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail);
  }

  protected abstract void createXQOrder(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchSuspendXQOrders(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchSuspendXQOrders",platformArgs);
    return batchSuspendXQOrders(oCntl, landingInfo, orderIds);
  }

  protected abstract Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchSuspendXQOrders(TServiceCntl oCntl, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchResumeXQOrders(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchResumeXQOrders",platformArgs);
    return batchResumeXQOrders(oCntl, landingInfo, orderIds, resumeModes);
  }

  protected abstract Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchResumeXQOrders(TServiceCntl oCntl, LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchCancelXQOrders(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchCancelXQOrders",platformArgs);
    return batchCancelXQOrders(oCntl, landingInfo, orderIds);
  }

  protected abstract Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> batchCancelXQOrders(TServiceCntl oCntl, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQOrderWithTradeListPage getEffectXQOrderWithTradeListPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getEffectXQOrderWithTradeListPage",platformArgs);
    return getEffectXQOrderWithTradeListPage(oCntl, landingInfo, qryOption, pageOption);
  }

  protected abstract HostingXQOrderWithTradeListPage getEffectXQOrderWithTradeListPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList> getXQOrderWithTradeLists(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getXQOrderWithTradeLists",platformArgs);
    return getXQOrderWithTradeLists(oCntl, landingInfo, orderIds);
  }

  protected abstract Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList> getXQOrderWithTradeLists(TServiceCntl oCntl, LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail getXQOrderExecDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getXQOrderExecDetail",platformArgs);
    return getXQOrderExecDetail(oCntl, landingInfo, orderId);
  }

  protected abstract xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail getXQOrderExecDetail(TServiceCntl oCntl, LandingInfo landingInfo, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQOrderPage getXQOrderHisPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getXQOrderHisPage",platformArgs);
    return getXQOrderHisPage(oCntl, landingInfo, qryOption, pageOption);
  }

  protected abstract HostingXQOrderPage getXQOrderHisPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQTradePage getXQTradeHisPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getXQTradeHisPage",platformArgs);
    return getXQTradeHisPage(oCntl, landingInfo, qryOption, pageOption);
  }

  protected abstract HostingXQTradePage getXQTradeHisPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingUserSetting getUserSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getUserSetting",platformArgs);
    return getUserSetting(oCntl, landingInfo, key);
  }

  protected abstract HostingUserSetting getUserSetting(TServiceCntl oCntl, LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateUserSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key, HostingUserSetting setting) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateUserSetting",platformArgs);
updateUserSetting(oCntl, landingInfo, key, setting);
  }

  protected abstract void updateUserSetting(TServiceCntl oCntl, LandingInfo landingInfo, String key, HostingUserSetting setting) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getUserSettingVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getUserSettingVersion",platformArgs);
    return getUserSettingVersion(oCntl, landingInfo, key);
  }

  protected abstract int getUserSettingVersion(TServiceCntl oCntl, LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingSAWRUItemListPage getSAWRUTListPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSAWRUTListPage",platformArgs);
    return getSAWRUTListPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract HostingSAWRUItemListPage getSAWRUTListPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> getSARUTBySubAccountId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> subAccountIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSARUTBySubAccountId",platformArgs);
    return getSARUTBySubAccountId(oCntl, landingInfo, subAccountIds);
  }

  protected abstract Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> getSARUTBySubAccountId(TServiceCntl oCntl, LandingInfo landingInfo, Set<Long> subAccountIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> getSARUTBySubUserId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Integer> subUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSARUTBySubUserId",platformArgs);
    return getSARUTBySubUserId(oCntl, landingInfo, subUserIds);
  }

  protected abstract Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> getSARUTBySubUserId(TServiceCntl oCntl, LandingInfo landingInfo, Set<Integer> subUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void assignSubAccountRelatedUsers(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"assignSubAccountRelatedUsers",platformArgs);
assignSubAccountRelatedUsers(oCntl, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds);
  }

  protected abstract void assignSubAccountRelatedUsers(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void renameSubAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String subAccountName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"renameSubAccount",platformArgs);
renameSubAccount(oCntl, landingInfo, subAccountId, subAccountName);
  }

  protected abstract void renameSubAccount(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, String subAccountName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createSubAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"createSubAccount",platformArgs);
    return createSubAccount(oCntl, landingInfo, newSubAccount);
  }

  protected abstract long createSubAccount(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage getHostingSledContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingSledContractPosition",platformArgs);
    return getHostingSledContractPosition(oCntl, landingInfo, option);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage getHostingSledContractPosition(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage getHostingSubAccountFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingSubAccountFund",platformArgs);
    return getHostingSubAccountFund(oCntl, landingInfo, option);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingFundPage getHostingSubAccountFund(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund changeSubAccountFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"changeSubAccountFund",platformArgs);
    return changeSubAccountFund(oCntl, landingInfo, fundChange);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund changeSubAccountFund(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund setSubAccountCreditAmount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"setSubAccountCreditAmount",platformArgs);
    return setSubAccountCreditAmount(oCntl, landingInfo, amountChange);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund setSubAccountCreditAmount(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getAssetPositionTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getAssetPositionTradeDetail",platformArgs);
    return getAssetPositionTradeDetail(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getAssetPositionTradeDetail(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getHostingSubAccountMoneyRecord",platformArgs);
    return getHostingSubAccountMoneyRecord(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage getSubAccountFundHistory(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSubAccountFundHistory",platformArgs);
    return getSubAccountFundHistory(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.HostingFundPage getSubAccountFundHistory(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage getSubAccountPositionHistory(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSubAccountPositionHistory",platformArgs);
    return getSubAccountPositionHistory(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage getSubAccountPositionHistory(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getSubAccountPositionHistoryTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getSubAccountPositionHistoryTradeDetail",platformArgs);
    return getSubAccountPositionHistoryTradeDetail(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getSubAccountPositionHistoryTradeDetail(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteExpiredContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"deleteExpiredContractPosition",platformArgs);
deleteExpiredContractPosition(oCntl, landingInfo, subAccountId, sledContractId);
  }

  protected abstract void deleteExpiredContractPosition(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<HostingTAFundItem> getTradeAccountFundNow(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountFundNow",platformArgs);
    return getTradeAccountFundNow(oCntl, landingInfo, tradeAccountId);
  }

  protected abstract List<HostingTAFundItem> getTradeAccountFundNow(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<HostingTAFundHisItem> getTradeAccountFundHis(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountFundHis",platformArgs);
    return getTradeAccountFundHis(oCntl, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd);
  }

  protected abstract List<HostingTAFundHisItem> getTradeAccountFundHis(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo> getTradeAccountSettlementInfos(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountSettlementInfos",platformArgs);
    return getTradeAccountSettlementInfos(oCntl, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd);
  }

  protected abstract List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo> getTradeAccountSettlementInfos(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TradeAccountSettlementInfoWithRelatedTime> getTradeAccountSettlementInfosWithRelatedTime(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountSettlementInfosWithRelatedTime",platformArgs);
    return getTradeAccountSettlementInfosWithRelatedTime(oCntl, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd);
  }

  protected abstract List<TradeAccountSettlementInfoWithRelatedTime> getTradeAccountSettlementInfosWithRelatedTime(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getTradeAccountPositionTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getTradeAccountPositionTradeDetail",platformArgs);
    return getTradeAccountPositionTradeDetail(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage getTradeAccountPositionTradeDetail(TServiceCntl oCntl, LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage reqPositionVerify(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqPositionVerify",platformArgs);
    return reqPositionVerify(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage reqPositionVerify(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage reqPositionDifference(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqPositionDifference",platformArgs);
    return reqPositionDifference(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage reqPositionDifference(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp manualInputPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"manualInputPosition",platformArgs);
    return manualInputPosition(oCntl, landingInfo, positionManualInputs);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp manualInputPosition(TServiceCntl oCntl, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage reqPositionUnassigned(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqPositionUnassigned",platformArgs);
    return reqPositionUnassigned(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage reqPositionUnassigned(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"assignPosition",platformArgs);
    return assignPosition(oCntl, landingInfo, assignOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(TServiceCntl oCntl, LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock reqPositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqPositionEditLock",platformArgs);
    return reqPositionEditLock(oCntl, landingInfo, lockKey);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock reqPositionEditLock(TServiceCntl oCntl, LandingInfo landingInfo, String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addPositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addPositionEditLock",platformArgs);
addPositionEditLock(oCntl, landingInfo, positionEditLock);
  }

  protected abstract void addPositionEditLock(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void removePositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"removePositionEditLock",platformArgs);
removePositionEditLock(oCntl, landingInfo, positionEditLock);
  }

  protected abstract void removePositionEditLock(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage reqDailyPositionDifference(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqDailyPositionDifference",platformArgs);
    return reqDailyPositionDifference(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage reqDailyPositionDifference(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDailyPositionDifferenceNote(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateDailyPositionDifferenceNote",platformArgs);
updateDailyPositionDifferenceNote(oCntl, landingInfo, difference);
  }

  protected abstract void updateDailyPositionDifferenceNote(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage reqPositionAssigned(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"reqPositionAssigned",platformArgs);
    return reqPositionAssigned(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage reqPositionAssigned(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void contructCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"contructCompose",platformArgs);
contructCompose(oCntl, landingInfo, contructComposeReq);
  }

  protected abstract void contructCompose(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void disassembleCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"disassembleCompose",platformArgs);
disassembleCompose(oCntl, landingInfo, disassembleComposePositionReq);
  }

  protected abstract void disassembleCompose(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void batchClosePosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchClosePosition",platformArgs);
batchClosePosition(oCntl, landingInfo, batchClosedPositionReq);
  }

  protected abstract void batchClosePosition(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void recoverClosedPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"recoverClosedPosition",platformArgs);
recoverClosedPosition(oCntl, landingInfo, subAccountId, targetKey, targetType);
  }

  protected abstract void recoverClosedPosition(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void mergeToCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"mergeToCompose",platformArgs);
mergeToCompose(oCntl, landingInfo, mergeToComposeReq);
  }

  protected abstract void mergeToCompose(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteExpiredStatContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"deleteExpiredStatContractPosition",platformArgs);
deleteExpiredStatContractPosition(oCntl, landingInfo, subAccountId, sledContractId);
  }

  protected abstract void deleteExpiredStatContractPosition(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatPositionSummaryPage queryStatPositionSummaryPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryStatPositionSummaryPage",platformArgs);
    return queryStatPositionSummaryPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatPositionSummaryPage queryStatPositionSummaryPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatPositionItemPage queryStatPositionItemPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryStatPositionItemPage",platformArgs);
    return queryStatPositionItemPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatPositionItemPage queryStatPositionItemPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryCurrentDayStatClosedPositionPage",platformArgs);
    return queryCurrentDayStatClosedPositionPage(oCntl, landingInfo, subAccountId, targetKey, targetType);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryStatClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryStatClosedPositionDetail",platformArgs);
    return queryStatClosedPositionDetail(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryStatClosedPositionDetail(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryArchivedClosedPositionPage",platformArgs);
    return queryArchivedClosedPositionPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryArchivedClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryArchivedClosedPositionDetail",platformArgs);
    return queryArchivedClosedPositionDetail(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryArchivedClosedPositionDetail(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage queryStatPositionSummaryExPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryStatPositionSummaryExPage",platformArgs);
    return queryStatPositionSummaryExPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage queryStatPositionSummaryExPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatPositionUnitPage queryStatPositionUnitPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryStatPositionUnitPage",platformArgs);
    return queryStatPositionUnitPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatPositionUnitPage queryStatPositionUnitPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryHistoryClosedPositionPage",platformArgs);
    return queryHistoryClosedPositionPage(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryHistoryClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryHistoryClosedPositionDetail",platformArgs);
    return queryHistoryClosedPositionDetail(oCntl, landingInfo, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.statis.StatClosedPositionDetail queryHistoryClosedPositionDetail(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage getXQTradeLameTaskNotePage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getXQTradeLameTaskNotePage",platformArgs);
    return getXQTradeLameTaskNotePage(oCntl, landingInfo, qryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage getXQTradeLameTaskNotePage(TServiceCntl oCntl, LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo> batchDeleteXQTradeLameTaskNotes(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchDeleteXQTradeLameTaskNotes",platformArgs);
    return batchDeleteXQTradeLameTaskNotes(oCntl, landingInfo, subAccountId, xqTradeIds);
  }

  protected abstract Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo> batchDeleteXQTradeLameTaskNotes(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage queryMailBoxMessage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryMailBoxMessage",platformArgs);
    return queryMailBoxMessage(oCntl, landingInfo, option, pageOption);
  }

  protected abstract xueqiao.mailbox.user.message.thriftapi.UserMessagePage queryMailBoxMessage(TServiceCntl oCntl, LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public boolean markMessageAsRead(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"markMessageAsRead",platformArgs);
    return markMessageAsRead(oCntl, landingInfo, hostingMessageIds);
  }

  protected abstract boolean markMessageAsRead(TServiceCntl oCntl, LandingInfo landingInfo, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem> getAllSupportedItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getAllSupportedItems",platformArgs);
    return getAllSupportedItems(oCntl, landingInfo);
  }

  protected abstract List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem> getAllSupportedItems(TServiceCntl oCntl, LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getRiskRuleJointVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getRiskRuleJointVersion",platformArgs);
    return getRiskRuleJointVersion(oCntl, landingInfo, subAccountId);
  }

  protected abstract int getRiskRuleJointVersion(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint getRiskRuleJoint(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getRiskRuleJoint",platformArgs);
    return getRiskRuleJoint(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint getRiskRuleJoint(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetSupportedItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchSetSupportedItems",platformArgs);
    return batchSetSupportedItems(oCntl, landingInfo, subAccountId, version, openedItemIds, closedItemIds);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetSupportedItems(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetTradedCommodityItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchSetTradedCommodityItems",platformArgs);
    return batchSetTradedCommodityItems(oCntl, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetTradedCommodityItems(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetGlobalRules(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchSetGlobalRules",platformArgs);
    return batchSetGlobalRules(oCntl, landingInfo, subAccountId, version, ruleItems);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetGlobalRules(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetCommodityRules(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"batchSetCommodityRules",platformArgs);
    return batchSetCommodityRules(oCntl, landingInfo, subAccountId, version, rules);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint batchSetCommodityRules(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint setRiskEnabled(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"setRiskEnabled",platformArgs);
    return setRiskEnabled(oCntl, landingInfo, subAccountId, version, riskEnabled);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint setRiskEnabled(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo getRiskFrameDataInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"getRiskFrameDataInfo",platformArgs);
    return getRiskFrameDataInfo(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo getRiskFrameDataInfo(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setGeneralMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"setGeneralMarginSetting",platformArgs);
setGeneralMarginSetting(oCntl, landingInfo, marginSettings);
  }

  protected abstract void setGeneralMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setGeneralCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"setGeneralCommissionSetting",platformArgs);
setGeneralCommissionSetting(oCntl, landingInfo, commissionSettings);
  }

  protected abstract void setGeneralCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addSpecMarginSetting",platformArgs);
addSpecMarginSetting(oCntl, landingInfo, marginSettings);
  }

  protected abstract void addSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addSpecCommissionSetting",platformArgs);
addSpecCommissionSetting(oCntl, landingInfo, commissionSettings);
  }

  protected abstract void addSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateSpecMarginSetting",platformArgs);
updateSpecMarginSetting(oCntl, landingInfo, marginSettings);
  }

  protected abstract void updateSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"updateSpecCommissionSetting",platformArgs);
updateSpecCommissionSetting(oCntl, landingInfo, commissionSettings);
  }

  protected abstract void updateSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"deleteSpecMarginSetting",platformArgs);
deleteSpecMarginSetting(oCntl, landingInfo, subAccountId, commodityId);
  }

  protected abstract void deleteSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"deleteSpecCommissionSetting",platformArgs);
deleteSpecCommissionSetting(oCntl, landingInfo, subAccountId, commodityId);
  }

  protected abstract void deleteSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings queryXQGeneralMarginSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQGeneralMarginSettings",platformArgs);
    return queryXQGeneralMarginSettings(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings queryXQGeneralMarginSettings(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings queryXQGeneralCommissionSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQGeneralCommissionSettings",platformArgs);
    return queryXQGeneralCommissionSettings(oCntl, landingInfo, subAccountId);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings queryXQGeneralCommissionSettings(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage queryXQSpecMarginSettingPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQSpecMarginSettingPage",platformArgs);
    return queryXQSpecMarginSettingPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage queryXQSpecMarginSettingPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQSpecCommissionSettingPage",platformArgs);
    return queryXQSpecCommissionSettingPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage queryUpsideContractMarginPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryUpsideContractMarginPage",platformArgs);
    return queryUpsideContractMarginPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage queryUpsideContractMarginPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage queryUpsideContractCommissionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryUpsideContractCommissionPage",platformArgs);
    return queryUpsideContractCommissionPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage queryUpsideContractCommissionPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage queryXQContractMarginPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQContractMarginPage",platformArgs);
    return queryXQContractMarginPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage queryXQContractMarginPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage queryXQContractCommissionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"queryXQContractCommissionPage",platformArgs);
    return queryXQContractCommissionPage(oCntl, landingInfo, queryOptions, pageOption);
  }

  protected abstract xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage queryXQContractCommissionPage(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long addAssetAccountWorkingOrder(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTerminalAoVariable.serviceKey,"addAssetAccountWorkingOrder",platformArgs);
    return addAssetAccountWorkingOrder(oCntl, landingInfo, assetAccount);
  }

  protected abstract long addAssetAccountWorkingOrder(TServiceCntl oCntl, LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingTerminalAoAdaptor(){
    methodParameterNameMap.put("getHostingUserPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("heartBeat",new String[]{"platformArgs", "landingInfo"});
    methodParameterNameMap.put("logout",new String[]{"platformArgs", "landingInfo"});
    methodParameterNameMap.put("getComposeViewDetails",new String[]{"platformArgs", "landingInfo", "composeGraphIds"});
    methodParameterNameMap.put("changeComposeViewPrecisionNumber",new String[]{"platformArgs", "landingInfo", "composeGraphId", "precisionNumber"});
    methodParameterNameMap.put("createComposeGraph",new String[]{"platformArgs", "landingInfo", "newGraph", "aliasName", "precisionNumber"});
    methodParameterNameMap.put("delComposeView",new String[]{"platformArgs", "landingInfo", "composeGraphId"});
    methodParameterNameMap.put("getComposeViewDetailPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("getSameComposeGraphsPage",new String[]{"platformArgs", "landingInfo", "graph", "pageOption"});
    methodParameterNameMap.put("addComposeViewBySearch",new String[]{"platformArgs", "landingInfo", "composeGraphId", "composeGraphKey", "aliasName", "precisionNumber"});
    methodParameterNameMap.put("subscribeComposeViewQuotation",new String[]{"platformArgs", "landingInfo", "composeGraphId"});
    methodParameterNameMap.put("unSubscribeComposeViewQuotation",new String[]{"platformArgs", "landingInfo", "composeGraphId"});
    methodParameterNameMap.put("changeComposeViewAliasName",new String[]{"platformArgs", "landingInfo", "composeGraphId", "aliasName"});
    methodParameterNameMap.put("getComposeGraphs",new String[]{"platformArgs", "landingInfo", "composeGraphIds"});
    methodParameterNameMap.put("addComposeViewByShare",new String[]{"platformArgs", "landingInfo", "composeGraphId", "aliasName", "precisionNumber"});
    methodParameterNameMap.put("addTradeAccount",new String[]{"platformArgs", "landingInfo", "newAccount"});
    methodParameterNameMap.put("disableTradeAccount",new String[]{"platformArgs", "landingInfo", "tradeAccountId"});
    methodParameterNameMap.put("getTradeAccountPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("enableTradeAccount",new String[]{"platformArgs", "landingInfo", "tradeAccountId"});
    methodParameterNameMap.put("updateTradeAccountInfo",new String[]{"platformArgs", "landingInfo", "updateAccount"});
    methodParameterNameMap.put("rmTradeAccount",new String[]{"platformArgs", "landingInfo", "tradeAccountId"});
    methodParameterNameMap.put("getPersonalUserTradeAccount",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("getHostingOrderRouteTree",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("updateHostingOrderRouteTree",new String[]{"platformArgs", "landingInfo", "subAccountId", "routeTree"});
    methodParameterNameMap.put("getHostingOrderRouteTreeVersion",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("getPersonalUserHostingOrderRouteTree",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("createXQOrder",new String[]{"platformArgs", "landingInfo", "subAccountId", "orderId", "orderType", "orderTarget", "orderDetail"});
    methodParameterNameMap.put("batchSuspendXQOrders",new String[]{"platformArgs", "landingInfo", "orderIds"});
    methodParameterNameMap.put("batchResumeXQOrders",new String[]{"platformArgs", "landingInfo", "orderIds", "resumeModes"});
    methodParameterNameMap.put("batchCancelXQOrders",new String[]{"platformArgs", "landingInfo", "orderIds"});
    methodParameterNameMap.put("getEffectXQOrderWithTradeListPage",new String[]{"platformArgs", "landingInfo", "qryOption", "pageOption"});
    methodParameterNameMap.put("getXQOrderWithTradeLists",new String[]{"platformArgs", "landingInfo", "orderIds"});
    methodParameterNameMap.put("getXQOrderExecDetail",new String[]{"platformArgs", "landingInfo", "orderId"});
    methodParameterNameMap.put("getXQOrderHisPage",new String[]{"platformArgs", "landingInfo", "qryOption", "pageOption"});
    methodParameterNameMap.put("getXQTradeHisPage",new String[]{"platformArgs", "landingInfo", "qryOption", "pageOption"});
    methodParameterNameMap.put("getUserSetting",new String[]{"platformArgs", "landingInfo", "key"});
    methodParameterNameMap.put("updateUserSetting",new String[]{"platformArgs", "landingInfo", "key", "setting"});
    methodParameterNameMap.put("getUserSettingVersion",new String[]{"platformArgs", "landingInfo", "key"});
    methodParameterNameMap.put("getSAWRUTListPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("getSARUTBySubAccountId",new String[]{"platformArgs", "landingInfo", "subAccountIds"});
    methodParameterNameMap.put("getSARUTBySubUserId",new String[]{"platformArgs", "landingInfo", "subUserIds"});
    methodParameterNameMap.put("assignSubAccountRelatedUsers",new String[]{"platformArgs", "landingInfo", "subAccountId", "relatedSubUserIds", "unRelatedSubUserIds"});
    methodParameterNameMap.put("renameSubAccount",new String[]{"platformArgs", "landingInfo", "subAccountId", "subAccountName"});
    methodParameterNameMap.put("createSubAccount",new String[]{"platformArgs", "landingInfo", "newSubAccount"});
    methodParameterNameMap.put("getHostingSledContractPosition",new String[]{"platformArgs", "landingInfo", "option"});
    methodParameterNameMap.put("getHostingSubAccountFund",new String[]{"platformArgs", "landingInfo", "option"});
    methodParameterNameMap.put("changeSubAccountFund",new String[]{"platformArgs", "landingInfo", "fundChange"});
    methodParameterNameMap.put("setSubAccountCreditAmount",new String[]{"platformArgs", "landingInfo", "amountChange"});
    methodParameterNameMap.put("getAssetPositionTradeDetail",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("getHostingSubAccountMoneyRecord",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("getSubAccountFundHistory",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("getSubAccountPositionHistory",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("getSubAccountPositionHistoryTradeDetail",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("deleteExpiredContractPosition",new String[]{"platformArgs", "landingInfo", "subAccountId", "sledContractId"});
    methodParameterNameMap.put("getTradeAccountFundNow",new String[]{"platformArgs", "landingInfo", "tradeAccountId"});
    methodParameterNameMap.put("getTradeAccountFundHis",new String[]{"platformArgs", "landingInfo", "tradeAccountId", "fundDateBegin", "fundDateEnd"});
    methodParameterNameMap.put("getTradeAccountSettlementInfos",new String[]{"platformArgs", "landingInfo", "tradeAccountId", "settlementDateBegin", "settlementDateEnd"});
    methodParameterNameMap.put("getTradeAccountSettlementInfosWithRelatedTime",new String[]{"platformArgs", "landingInfo", "tradeAccountId", "settlementDateBegin", "settlementDateEnd"});
    methodParameterNameMap.put("getTradeAccountPositionTradeDetail",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("reqPositionVerify",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("reqPositionDifference",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("manualInputPosition",new String[]{"platformArgs", "landingInfo", "positionManualInputs"});
    methodParameterNameMap.put("reqPositionUnassigned",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("assignPosition",new String[]{"platformArgs", "landingInfo", "assignOption"});
    methodParameterNameMap.put("reqPositionEditLock",new String[]{"platformArgs", "landingInfo", "lockKey"});
    methodParameterNameMap.put("addPositionEditLock",new String[]{"platformArgs", "landingInfo", "positionEditLock"});
    methodParameterNameMap.put("removePositionEditLock",new String[]{"platformArgs", "landingInfo", "positionEditLock"});
    methodParameterNameMap.put("reqDailyPositionDifference",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("updateDailyPositionDifferenceNote",new String[]{"platformArgs", "landingInfo", "difference"});
    methodParameterNameMap.put("reqPositionAssigned",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("contructCompose",new String[]{"platformArgs", "landingInfo", "contructComposeReq"});
    methodParameterNameMap.put("disassembleCompose",new String[]{"platformArgs", "landingInfo", "disassembleComposePositionReq"});
    methodParameterNameMap.put("batchClosePosition",new String[]{"platformArgs", "landingInfo", "batchClosedPositionReq"});
    methodParameterNameMap.put("recoverClosedPosition",new String[]{"platformArgs", "landingInfo", "subAccountId", "targetKey", "targetType"});
    methodParameterNameMap.put("mergeToCompose",new String[]{"platformArgs", "landingInfo", "mergeToComposeReq"});
    methodParameterNameMap.put("deleteExpiredStatContractPosition",new String[]{"platformArgs", "landingInfo", "subAccountId", "sledContractId"});
    methodParameterNameMap.put("queryStatPositionSummaryPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionItemPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryCurrentDayStatClosedPositionPage",new String[]{"platformArgs", "landingInfo", "subAccountId", "targetKey", "targetType"});
    methodParameterNameMap.put("queryStatClosedPositionDetail",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryArchivedClosedPositionPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryArchivedClosedPositionDetail",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionSummaryExPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionUnitPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryHistoryClosedPositionPage",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryHistoryClosedPositionDetail",new String[]{"platformArgs", "landingInfo", "queryOption", "pageOption"});
    methodParameterNameMap.put("getXQTradeLameTaskNotePage",new String[]{"platformArgs", "landingInfo", "qryOption", "pageOption"});
    methodParameterNameMap.put("batchDeleteXQTradeLameTaskNotes",new String[]{"platformArgs", "landingInfo", "subAccountId", "xqTradeIds"});
    methodParameterNameMap.put("queryMailBoxMessage",new String[]{"platformArgs", "landingInfo", "option", "pageOption"});
    methodParameterNameMap.put("markMessageAsRead",new String[]{"platformArgs", "landingInfo", "hostingMessageIds"});
    methodParameterNameMap.put("getAllSupportedItems",new String[]{"platformArgs", "landingInfo"});
    methodParameterNameMap.put("getRiskRuleJointVersion",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("getRiskRuleJoint",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("batchSetSupportedItems",new String[]{"platformArgs", "landingInfo", "subAccountId", "version", "openedItemIds", "closedItemIds"});
    methodParameterNameMap.put("batchSetTradedCommodityItems",new String[]{"platformArgs", "landingInfo", "subAccountId", "version", "enabledCommodityIds", "disabledCommodityIds"});
    methodParameterNameMap.put("batchSetGlobalRules",new String[]{"platformArgs", "landingInfo", "subAccountId", "version", "ruleItems"});
    methodParameterNameMap.put("batchSetCommodityRules",new String[]{"platformArgs", "landingInfo", "subAccountId", "version", "rules"});
    methodParameterNameMap.put("setRiskEnabled",new String[]{"platformArgs", "landingInfo", "subAccountId", "version", "riskEnabled"});
    methodParameterNameMap.put("getRiskFrameDataInfo",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("setGeneralMarginSetting",new String[]{"platformArgs", "landingInfo", "marginSettings"});
    methodParameterNameMap.put("setGeneralCommissionSetting",new String[]{"platformArgs", "landingInfo", "commissionSettings"});
    methodParameterNameMap.put("addSpecMarginSetting",new String[]{"platformArgs", "landingInfo", "marginSettings"});
    methodParameterNameMap.put("addSpecCommissionSetting",new String[]{"platformArgs", "landingInfo", "commissionSettings"});
    methodParameterNameMap.put("updateSpecMarginSetting",new String[]{"platformArgs", "landingInfo", "marginSettings"});
    methodParameterNameMap.put("updateSpecCommissionSetting",new String[]{"platformArgs", "landingInfo", "commissionSettings"});
    methodParameterNameMap.put("deleteSpecMarginSetting",new String[]{"platformArgs", "landingInfo", "subAccountId", "commodityId"});
    methodParameterNameMap.put("deleteSpecCommissionSetting",new String[]{"platformArgs", "landingInfo", "subAccountId", "commodityId"});
    methodParameterNameMap.put("queryXQGeneralMarginSettings",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("queryXQGeneralCommissionSettings",new String[]{"platformArgs", "landingInfo", "subAccountId"});
    methodParameterNameMap.put("queryXQSpecMarginSettingPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQSpecCommissionSettingPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryUpsideContractMarginPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryUpsideContractCommissionPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQContractMarginPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQContractCommissionPage",new String[]{"platformArgs", "landingInfo", "queryOptions", "pageOption"});
    methodParameterNameMap.put("addAssetAccountWorkingOrder",new String[]{"platformArgs", "landingInfo", "assetAccount"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
