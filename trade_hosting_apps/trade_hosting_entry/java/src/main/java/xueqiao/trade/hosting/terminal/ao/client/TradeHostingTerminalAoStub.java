package xueqiao.trade.hosting.terminal.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

public class TradeHostingTerminalAoStub extends BaseStub {

  public TradeHostingTerminalAoStub() {
    super(TradeHostingTerminalAoVariable.serviceKey);
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingUserPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingUserPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.QueryHostingUserPage>(){
    @Override
    public xueqiao.trade.hosting.QueryHostingUserPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingUserPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingUserPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  heartBeat(LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    heartBeat(landingInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  heartBeat(LandingInfo landingInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("heartBeat").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).heartBeat(platformArgs, landingInfo);
      return null;
      }
    }, invokeInfo);
  }

  public void  heartBeat(int routeKey, int timeout,LandingInfo landingInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    heartBeat(landingInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  logout(LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    logout(landingInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  logout(LandingInfo landingInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("logout").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).logout(platformArgs, landingInfo);
      return null;
      }
    }, invokeInfo);
  }

  public void  logout(int routeKey, int timeout,LandingInfo landingInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    logout(landingInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,HostingComposeViewDetail>  getComposeViewDetails(LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeViewDetails(landingInfo, composeGraphIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,HostingComposeViewDetail>  getComposeViewDetails(LandingInfo landingInfo, Set<Long> composeGraphIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getComposeViewDetails").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,HostingComposeViewDetail>>(){
    @Override
    public Map<Long,HostingComposeViewDetail> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getComposeViewDetails(platformArgs, landingInfo, composeGraphIds);
      }
    }, invokeInfo);
  }

  public Map<Long,HostingComposeViewDetail>  getComposeViewDetails(int routeKey, int timeout,LandingInfo landingInfo, Set<Long> composeGraphIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeViewDetails(landingInfo, composeGraphIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  changeComposeViewPrecisionNumber(LandingInfo landingInfo, long composeGraphId, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    changeComposeViewPrecisionNumber(landingInfo, composeGraphId, precisionNumber, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  changeComposeViewPrecisionNumber(LandingInfo landingInfo, long composeGraphId, short precisionNumber,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("changeComposeViewPrecisionNumber").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).changeComposeViewPrecisionNumber(platformArgs, landingInfo, composeGraphId, precisionNumber);
      return null;
      }
    }, invokeInfo);
  }

  public void  changeComposeViewPrecisionNumber(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId, short precisionNumber)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    changeComposeViewPrecisionNumber(landingInfo, composeGraphId, precisionNumber, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createComposeGraph(LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createComposeGraph(landingInfo, newGraph, aliasName, precisionNumber, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createComposeGraph(LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createComposeGraph").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).createComposeGraph(platformArgs, landingInfo, newGraph, aliasName, precisionNumber);
      }
    }, invokeInfo);
  }

  public long  createComposeGraph(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph newGraph, String aliasName, short precisionNumber)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createComposeGraph(landingInfo, newGraph, aliasName, precisionNumber, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  delComposeView(LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    delComposeView(landingInfo, composeGraphId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  delComposeView(LandingInfo landingInfo, long composeGraphId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("delComposeView").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).delComposeView(platformArgs, landingInfo, composeGraphId);
      return null;
      }
    }, invokeInfo);
  }

  public void  delComposeView(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    delComposeView(landingInfo, composeGraphId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryHostingComposeViewDetailPage  getComposeViewDetailPage(LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeViewDetailPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryHostingComposeViewDetailPage  getComposeViewDetailPage(LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getComposeViewDetailPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<QueryHostingComposeViewDetailPage>(){
    @Override
    public QueryHostingComposeViewDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getComposeViewDetailPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public QueryHostingComposeViewDetailPage  getComposeViewDetailPage(int routeKey, int timeout,LandingInfo landingInfo, QueryHostingComposeViewDetailOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeViewDetailPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QuerySameComposeGraphsPage  getSameComposeGraphsPage(LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSameComposeGraphsPage(landingInfo, graph, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QuerySameComposeGraphsPage  getSameComposeGraphsPage(LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSameComposeGraphsPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<QuerySameComposeGraphsPage>(){
    @Override
    public QuerySameComposeGraphsPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSameComposeGraphsPage(platformArgs, landingInfo, graph, pageOption);
      }
    }, invokeInfo);
  }

  public QuerySameComposeGraphsPage  getSameComposeGraphsPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.HostingComposeGraph graph, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSameComposeGraphsPage(landingInfo, graph, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addComposeViewBySearch(LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addComposeViewBySearch(landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addComposeViewBySearch(LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addComposeViewBySearch").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).addComposeViewBySearch(platformArgs, landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber);
      return null;
      }
    }, invokeInfo);
  }

  public void  addComposeViewBySearch(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId, String composeGraphKey, String aliasName, short precisionNumber)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addComposeViewBySearch(landingInfo, composeGraphId, composeGraphKey, aliasName, precisionNumber, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  subscribeComposeViewQuotation(LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    subscribeComposeViewQuotation(landingInfo, composeGraphId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  subscribeComposeViewQuotation(LandingInfo landingInfo, long composeGraphId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("subscribeComposeViewQuotation").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).subscribeComposeViewQuotation(platformArgs, landingInfo, composeGraphId);
      return null;
      }
    }, invokeInfo);
  }

  public void  subscribeComposeViewQuotation(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    subscribeComposeViewQuotation(landingInfo, composeGraphId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  unSubscribeComposeViewQuotation(LandingInfo landingInfo, long composeGraphId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    unSubscribeComposeViewQuotation(landingInfo, composeGraphId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  unSubscribeComposeViewQuotation(LandingInfo landingInfo, long composeGraphId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("unSubscribeComposeViewQuotation").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).unSubscribeComposeViewQuotation(platformArgs, landingInfo, composeGraphId);
      return null;
      }
    }, invokeInfo);
  }

  public void  unSubscribeComposeViewQuotation(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    unSubscribeComposeViewQuotation(landingInfo, composeGraphId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  changeComposeViewAliasName(LandingInfo landingInfo, long composeGraphId, String aliasName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    changeComposeViewAliasName(landingInfo, composeGraphId, aliasName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  changeComposeViewAliasName(LandingInfo landingInfo, long composeGraphId, String aliasName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("changeComposeViewAliasName").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).changeComposeViewAliasName(platformArgs, landingInfo, composeGraphId, aliasName);
      return null;
      }
    }, invokeInfo);
  }

  public void  changeComposeViewAliasName(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId, String aliasName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    changeComposeViewAliasName(landingInfo, composeGraphId, aliasName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,xueqiao.trade.hosting.HostingComposeGraph>  getComposeGraphs(LandingInfo landingInfo, Set<Long> composeGraphIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeGraphs(landingInfo, composeGraphIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,xueqiao.trade.hosting.HostingComposeGraph>  getComposeGraphs(LandingInfo landingInfo, Set<Long> composeGraphIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getComposeGraphs").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,xueqiao.trade.hosting.HostingComposeGraph>>(){
    @Override
    public Map<Long,xueqiao.trade.hosting.HostingComposeGraph> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getComposeGraphs(platformArgs, landingInfo, composeGraphIds);
      }
    }, invokeInfo);
  }

  public Map<Long,xueqiao.trade.hosting.HostingComposeGraph>  getComposeGraphs(int routeKey, int timeout,LandingInfo landingInfo, Set<Long> composeGraphIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getComposeGraphs(landingInfo, composeGraphIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addComposeViewByShare(LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addComposeViewByShare(landingInfo, composeGraphId, aliasName, precisionNumber, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addComposeViewByShare(LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addComposeViewByShare").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).addComposeViewByShare(platformArgs, landingInfo, composeGraphId, aliasName, precisionNumber);
      return null;
      }
    }, invokeInfo);
  }

  public void  addComposeViewByShare(int routeKey, int timeout,LandingInfo landingInfo, long composeGraphId, String aliasName, short precisionNumber)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addComposeViewByShare(landingInfo, composeGraphId, aliasName, precisionNumber, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addTradeAccount(LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addTradeAccount(landingInfo, newAccount, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addTradeAccount(LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addTradeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).addTradeAccount(platformArgs, landingInfo, newAccount);
      }
    }, invokeInfo);
  }

  public long  addTradeAccount(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount newAccount)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addTradeAccount(landingInfo, newAccount, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disableTradeAccount(LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disableTradeAccount(landingInfo, tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disableTradeAccount(LandingInfo landingInfo, long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("disableTradeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).disableTradeAccount(platformArgs, landingInfo, tradeAccountId);
      return null;
      }
    }, invokeInfo);
  }

  public void  disableTradeAccount(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disableTradeAccount(landingInfo, tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryHostingTradeAccountPage  getTradeAccountPage(LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryHostingTradeAccountPage  getTradeAccountPage(LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<QueryHostingTradeAccountPage>(){
    @Override
    public QueryHostingTradeAccountPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public QueryHostingTradeAccountPage  getTradeAccountPage(int routeKey, int timeout,LandingInfo landingInfo, QueryHostingTradeAccountOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  enableTradeAccount(LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    enableTradeAccount(landingInfo, tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  enableTradeAccount(LandingInfo landingInfo, long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("enableTradeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).enableTradeAccount(platformArgs, landingInfo, tradeAccountId);
      return null;
      }
    }, invokeInfo);
  }

  public void  enableTradeAccount(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    enableTradeAccount(landingInfo, tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateTradeAccountInfo(LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateTradeAccountInfo(landingInfo, updateAccount, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateTradeAccountInfo(LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateTradeAccountInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateTradeAccountInfo(platformArgs, landingInfo, updateAccount);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateTradeAccountInfo(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.HostingTradeAccount updateAccount)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateTradeAccountInfo(landingInfo, updateAccount, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  rmTradeAccount(LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    rmTradeAccount(landingInfo, tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  rmTradeAccount(LandingInfo landingInfo, long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("rmTradeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).rmTradeAccount(platformArgs, landingInfo, tradeAccountId);
      return null;
      }
    }, invokeInfo);
  }

  public void  rmTradeAccount(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    rmTradeAccount(landingInfo, tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getPersonalUserTradeAccount(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getPersonalUserTradeAccount(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getPersonalUserTradeAccount(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getPersonalUserTradeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingTradeAccount>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingTradeAccount> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getPersonalUserTradeAccount(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getPersonalUserTradeAccount(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getPersonalUserTradeAccount(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingOrderRouteTree(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingOrderRouteTree").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.HostingOrderRouteTree>(){
    @Override
    public xueqiao.trade.hosting.HostingOrderRouteTree call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingOrderRouteTree(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getHostingOrderRouteTree(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingOrderRouteTree(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingOrderRouteTree(landingInfo, subAccountId, routeTree, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateHostingOrderRouteTree").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateHostingOrderRouteTree(platformArgs, landingInfo, subAccountId, routeTree);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateHostingOrderRouteTree(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, xueqiao.trade.hosting.HostingOrderRouteTree routeTree)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingOrderRouteTree(landingInfo, subAccountId, routeTree, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getHostingOrderRouteTreeVersion(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingOrderRouteTreeVersion(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getHostingOrderRouteTreeVersion(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingOrderRouteTreeVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingOrderRouteTreeVersion(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public int  getHostingOrderRouteTreeVersion(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingOrderRouteTreeVersion(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getPersonalUserHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getPersonalUserHostingOrderRouteTree(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getPersonalUserHostingOrderRouteTree(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getPersonalUserHostingOrderRouteTree").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.HostingOrderRouteTree>(){
    @Override
    public xueqiao.trade.hosting.HostingOrderRouteTree call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getPersonalUserHostingOrderRouteTree(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.HostingOrderRouteTree  getPersonalUserHostingOrderRouteTree(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getPersonalUserHostingOrderRouteTree(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  createXQOrder(LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createXQOrder(landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  createXQOrder(LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createXQOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).createXQOrder(platformArgs, landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail);
      return null;
      }
    }, invokeInfo);
  }

  public void  createXQOrder(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, String orderId, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType orderType, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget orderTarget, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail orderDetail)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createXQOrder(landingInfo, subAccountId, orderId, orderType, orderTarget, orderDetail, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchSuspendXQOrders(LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSuspendXQOrders(landingInfo, orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchSuspendXQOrders(LandingInfo landingInfo, Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSuspendXQOrders").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>>(){
    @Override
    public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchSuspendXQOrders(platformArgs, landingInfo, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchSuspendXQOrders(int routeKey, int timeout,LandingInfo landingInfo, Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSuspendXQOrders(landingInfo, orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchResumeXQOrders(LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchResumeXQOrders(landingInfo, orderIds, resumeModes, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchResumeXQOrders(LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchResumeXQOrders").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>>(){
    @Override
    public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchResumeXQOrders(platformArgs, landingInfo, orderIds, resumeModes);
      }
    }, invokeInfo);
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchResumeXQOrders(int routeKey, int timeout,LandingInfo landingInfo, Set<String> orderIds, Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode> resumeModes)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchResumeXQOrders(landingInfo, orderIds, resumeModes, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchCancelXQOrders(LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchCancelXQOrders(landingInfo, orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchCancelXQOrders(LandingInfo landingInfo, Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchCancelXQOrders").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>>(){
    @Override
    public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchCancelXQOrders(platformArgs, landingInfo, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchCancelXQOrders(int routeKey, int timeout,LandingInfo landingInfo, Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchCancelXQOrders(landingInfo, orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderWithTradeListPage  getEffectXQOrderWithTradeListPage(LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getEffectXQOrderWithTradeListPage(landingInfo, qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderWithTradeListPage  getEffectXQOrderWithTradeListPage(LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getEffectXQOrderWithTradeListPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQOrderWithTradeListPage>(){
    @Override
    public HostingXQOrderWithTradeListPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getEffectXQOrderWithTradeListPage(platformArgs, landingInfo, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingXQOrderWithTradeListPage  getEffectXQOrderWithTradeListPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getEffectXQOrderWithTradeListPage(landingInfo, qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(LandingInfo landingInfo, Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderWithTradeLists(landingInfo, orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(LandingInfo landingInfo, Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderWithTradeLists").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList>>(){
    @Override
    public Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getXQOrderWithTradeLists(platformArgs, landingInfo, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(int routeKey, int timeout,LandingInfo landingInfo, Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderWithTradeLists(landingInfo, orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail  getXQOrderExecDetail(LandingInfo landingInfo, String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderExecDetail(landingInfo, orderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail  getXQOrderExecDetail(LandingInfo landingInfo, String orderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderExecDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail>(){
    @Override
    public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getXQOrderExecDetail(platformArgs, landingInfo, orderId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail  getXQOrderExecDetail(int routeKey, int timeout,LandingInfo landingInfo, String orderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderExecDetail(landingInfo, orderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderPage  getXQOrderHisPage(LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderHisPage(landingInfo, qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderPage  getXQOrderHisPage(LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderHisPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQOrderPage>(){
    @Override
    public HostingXQOrderPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getXQOrderHisPage(platformArgs, landingInfo, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingXQOrderPage  getXQOrderHisPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderHisPage(landingInfo, qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQTradePage  getXQTradeHisPage(LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeHisPage(landingInfo, qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQTradePage  getXQTradeHisPage(LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQTradeHisPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQTradePage>(){
    @Override
    public HostingXQTradePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getXQTradeHisPage(platformArgs, landingInfo, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingXQTradePage  getXQTradeHisPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeHisPage(landingInfo, qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingUserSetting  getUserSetting(LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getUserSetting(landingInfo, key, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingUserSetting  getUserSetting(LandingInfo landingInfo, String key,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getUserSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingUserSetting>(){
    @Override
    public HostingUserSetting call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getUserSetting(platformArgs, landingInfo, key);
      }
    }, invokeInfo);
  }

  public HostingUserSetting  getUserSetting(int routeKey, int timeout,LandingInfo landingInfo, String key)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getUserSetting(landingInfo, key, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateUserSetting(LandingInfo landingInfo, String key, HostingUserSetting setting) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateUserSetting(landingInfo, key, setting, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateUserSetting(LandingInfo landingInfo, String key, HostingUserSetting setting,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateUserSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateUserSetting(platformArgs, landingInfo, key, setting);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateUserSetting(int routeKey, int timeout,LandingInfo landingInfo, String key, HostingUserSetting setting)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateUserSetting(landingInfo, key, setting, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getUserSettingVersion(LandingInfo landingInfo, String key) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getUserSettingVersion(landingInfo, key, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getUserSettingVersion(LandingInfo landingInfo, String key,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getUserSettingVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getUserSettingVersion(platformArgs, landingInfo, key);
      }
    }, invokeInfo);
  }

  public int  getUserSettingVersion(int routeKey, int timeout,LandingInfo landingInfo, String key)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getUserSettingVersion(landingInfo, key, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSAWRUItemListPage  getSAWRUTListPage(LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSAWRUTListPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSAWRUItemListPage  getSAWRUTListPage(LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSAWRUTListPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingSAWRUItemListPage>(){
    @Override
    public HostingSAWRUItemListPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSAWRUTListPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingSAWRUItemListPage  getSAWRUTListPage(int routeKey, int timeout,LandingInfo landingInfo, QueryHostingSAWRUItemListOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSAWRUTListPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubAccountId(LandingInfo landingInfo, Set<Long> subAccountIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSARUTBySubAccountId(landingInfo, subAccountIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubAccountId(LandingInfo landingInfo, Set<Long> subAccountIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSARUTBySubAccountId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>>(){
    @Override
    public Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSARUTBySubAccountId(platformArgs, landingInfo, subAccountIds);
      }
    }, invokeInfo);
  }

  public Map<Long,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubAccountId(int routeKey, int timeout,LandingInfo landingInfo, Set<Long> subAccountIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSARUTBySubAccountId(landingInfo, subAccountIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubUserId(LandingInfo landingInfo, Set<Integer> subUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSARUTBySubUserId(landingInfo, subUserIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubUserId(LandingInfo landingInfo, Set<Integer> subUserIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSARUTBySubUserId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>>(){
    @Override
    public Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSARUTBySubUserId(platformArgs, landingInfo, subUserIds);
      }
    }, invokeInfo);
  }

  public Map<Integer,List<xueqiao.trade.hosting.HostingSubAccountRelatedItem>>  getSARUTBySubUserId(int routeKey, int timeout,LandingInfo landingInfo, Set<Integer> subUserIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSARUTBySubUserId(landingInfo, subUserIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  assignSubAccountRelatedUsers(LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    assignSubAccountRelatedUsers(landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  assignSubAccountRelatedUsers(LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("assignSubAccountRelatedUsers").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).assignSubAccountRelatedUsers(platformArgs, landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds);
      return null;
      }
    }, invokeInfo);
  }

  public void  assignSubAccountRelatedUsers(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    assignSubAccountRelatedUsers(landingInfo, subAccountId, relatedSubUserIds, unRelatedSubUserIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  renameSubAccount(LandingInfo landingInfo, long subAccountId, String subAccountName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    renameSubAccount(landingInfo, subAccountId, subAccountName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  renameSubAccount(LandingInfo landingInfo, long subAccountId, String subAccountName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("renameSubAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).renameSubAccount(platformArgs, landingInfo, subAccountId, subAccountName);
      return null;
      }
    }, invokeInfo);
  }

  public void  renameSubAccount(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, String subAccountName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    renameSubAccount(landingInfo, subAccountId, subAccountName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createSubAccount(LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createSubAccount(landingInfo, newSubAccount, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createSubAccount(LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createSubAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).createSubAccount(platformArgs, landingInfo, newSubAccount);
      }
    }, invokeInfo);
  }

  public long  createSubAccount(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.HostingSubAccount newSubAccount)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createSubAccount(landingInfo, newSubAccount, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage  getHostingSledContractPosition(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSledContractPosition(landingInfo, option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage  getHostingSledContractPosition(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSledContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingSledContractPosition(platformArgs, landingInfo, option);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage  getHostingSledContractPosition(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSledContractPosition(landingInfo, option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getHostingSubAccountFund(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountFund(landingInfo, option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getHostingSubAccountFund(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSubAccountFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingFundPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingSubAccountFund(platformArgs, landingInfo, option);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getHostingSubAccountFund(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountFund(landingInfo, option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  changeSubAccountFund(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return changeSubAccountFund(landingInfo, fundChange, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  changeSubAccountFund(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("changeSubAccountFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).changeSubAccountFund(platformArgs, landingInfo, fundChange);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  changeSubAccountFund(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.FundChange fundChange)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return changeSubAccountFund(landingInfo, fundChange, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  setSubAccountCreditAmount(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setSubAccountCreditAmount(landingInfo, amountChange, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  setSubAccountCreditAmount(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setSubAccountCreditAmount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).setSubAccountCreditAmount(platformArgs, landingInfo, amountChange);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund  setSubAccountCreditAmount(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange amountChange)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setSubAccountCreditAmount(landingInfo, amountChange, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getAssetPositionTradeDetail(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAssetPositionTradeDetail(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getAssetPositionTradeDetail(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAssetPositionTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getAssetPositionTradeDetail(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getAssetPositionTradeDetail(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAssetPositionTradeDetail(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountMoneyRecord(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSubAccountMoneyRecord").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getHostingSubAccountMoneyRecord(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountMoneyRecord(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getSubAccountFundHistory(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountFundHistory(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getSubAccountFundHistory(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSubAccountFundHistory").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.HostingFundPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSubAccountFundHistory(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.HostingFundPage  getSubAccountFundHistory(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountFundHistory(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage  getSubAccountPositionHistory(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountPositionHistory(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage  getSubAccountPositionHistory(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSubAccountPositionHistory").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSubAccountPositionHistory(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage  getSubAccountPositionHistory(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountPositionHistory(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getSubAccountPositionHistoryTradeDetail(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountPositionHistoryTradeDetail(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getSubAccountPositionHistoryTradeDetail(LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSubAccountPositionHistoryTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getSubAccountPositionHistoryTradeDetail(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getSubAccountPositionHistoryTradeDetail(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountPositionHistoryTradeDetail(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredContractPosition(LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredContractPosition(landingInfo, subAccountId, sledContractId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredContractPosition(LandingInfo landingInfo, long subAccountId, long sledContractId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteExpiredContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).deleteExpiredContractPosition(platformArgs, landingInfo, subAccountId, sledContractId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteExpiredContractPosition(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, long sledContractId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredContractPosition(landingInfo, subAccountId, sledContractId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingTAFundItem>  getTradeAccountFundNow(LandingInfo landingInfo, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountFundNow(landingInfo, tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingTAFundItem>  getTradeAccountFundNow(LandingInfo landingInfo, long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountFundNow").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<HostingTAFundItem>>(){
    @Override
    public List<HostingTAFundItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountFundNow(platformArgs, landingInfo, tradeAccountId);
      }
    }, invokeInfo);
  }

  public List<HostingTAFundItem>  getTradeAccountFundNow(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountFundNow(landingInfo, tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingTAFundHisItem>  getTradeAccountFundHis(LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountFundHis(landingInfo, tradeAccountId, fundDateBegin, fundDateEnd, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingTAFundHisItem>  getTradeAccountFundHis(LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountFundHis").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<HostingTAFundHisItem>>(){
    @Override
    public List<HostingTAFundHisItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountFundHis(platformArgs, landingInfo, tradeAccountId, fundDateBegin, fundDateEnd);
      }
    }, invokeInfo);
  }

  public List<HostingTAFundHisItem>  getTradeAccountFundHis(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId, String fundDateBegin, String fundDateEnd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountFundHis(landingInfo, tradeAccountId, fundDateBegin, fundDateEnd, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo>  getTradeAccountSettlementInfos(LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountSettlementInfos(landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo>  getTradeAccountSettlementInfos(LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountSettlementInfos").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo>>(){
    @Override
    public List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountSettlementInfos(platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo>  getTradeAccountSettlementInfos(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountSettlementInfos(landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountSettlementInfoWithRelatedTime>  getTradeAccountSettlementInfosWithRelatedTime(LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountSettlementInfosWithRelatedTime(landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountSettlementInfoWithRelatedTime>  getTradeAccountSettlementInfosWithRelatedTime(LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountSettlementInfosWithRelatedTime").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TradeAccountSettlementInfoWithRelatedTime>>(){
    @Override
    public List<TradeAccountSettlementInfoWithRelatedTime> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountSettlementInfosWithRelatedTime(platformArgs, landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd);
      }
    }, invokeInfo);
  }

  public List<TradeAccountSettlementInfoWithRelatedTime>  getTradeAccountSettlementInfosWithRelatedTime(int routeKey, int timeout,LandingInfo landingInfo, long tradeAccountId, String settlementDateBegin, String settlementDateEnd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountSettlementInfosWithRelatedTime(landingInfo, tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getTradeAccountPositionTradeDetail(LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPositionTradeDetail(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getTradeAccountPositionTradeDetail(LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountPositionTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage>(){
    @Override
    public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getTradeAccountPositionTradeDetail(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage  getTradeAccountPositionTradeDetail(int routeKey, int timeout,LandingInfo landingInfo, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPositionTradeDetail(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage  reqPositionVerify(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionVerify(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage  reqPositionVerify(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionVerify").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqPositionVerify(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage  reqPositionVerify(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionVerify(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage  reqPositionDifference(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionDifference(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage  reqPositionDifference(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionDifference").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqPositionDifference(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage  reqPositionDifference(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionDifference(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp  manualInputPosition(LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return manualInputPosition(landingInfo, positionManualInputs, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp  manualInputPosition(LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("manualInputPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).manualInputPosition(platformArgs, landingInfo, positionManualInputs);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp  manualInputPosition(int routeKey, int timeout,LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput> positionManualInputs)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return manualInputPosition(landingInfo, positionManualInputs, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage  reqPositionUnassigned(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionUnassigned(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage  reqPositionUnassigned(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionUnassigned").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqPositionUnassigned(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage  reqPositionUnassigned(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionUnassigned(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(landingInfo, assignOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("assignPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).assignPosition(platformArgs, landingInfo, assignOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(int routeKey, int timeout,LandingInfo landingInfo, List<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption> assignOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(landingInfo, assignOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock  reqPositionEditLock(LandingInfo landingInfo, String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionEditLock(landingInfo, lockKey, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock  reqPositionEditLock(LandingInfo landingInfo, String lockKey,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqPositionEditLock(platformArgs, landingInfo, lockKey);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock  reqPositionEditLock(int routeKey, int timeout,LandingInfo landingInfo, String lockKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionEditLock(landingInfo, lockKey, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addPositionEditLock(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addPositionEditLock(landingInfo, positionEditLock, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addPositionEditLock(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addPositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).addPositionEditLock(platformArgs, landingInfo, positionEditLock);
      return null;
      }
    }, invokeInfo);
  }

  public void  addPositionEditLock(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addPositionEditLock(landingInfo, positionEditLock, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removePositionEditLock(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removePositionEditLock(landingInfo, positionEditLock, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removePositionEditLock(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removePositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).removePositionEditLock(platformArgs, landingInfo, positionEditLock);
      return null;
      }
    }, invokeInfo);
  }

  public void  removePositionEditLock(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock positionEditLock)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removePositionEditLock(landingInfo, positionEditLock, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage  reqDailyPositionDifference(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDailyPositionDifference(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage  reqDailyPositionDifference(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqDailyPositionDifference").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqDailyPositionDifference(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage  reqDailyPositionDifference(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDailyPositionDifference(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateDailyPositionDifferenceNote(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateDailyPositionDifferenceNote(landingInfo, difference, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateDailyPositionDifferenceNote(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateDailyPositionDifferenceNote").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateDailyPositionDifferenceNote(platformArgs, landingInfo, difference);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateDailyPositionDifferenceNote(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference difference)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateDailyPositionDifferenceNote(landingInfo, difference, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage  reqPositionAssigned(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionAssigned(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage  reqPositionAssigned(LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionAssigned").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).reqPositionAssigned(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage  reqPositionAssigned(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionAssigned(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  contructCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    contructCompose(landingInfo, contructComposeReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  contructCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("contructCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).contructCompose(platformArgs, landingInfo, contructComposeReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  contructCompose(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatContructComposeReq contructComposeReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    contructCompose(landingInfo, contructComposeReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disassembleCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disassembleCompose(landingInfo, disassembleComposePositionReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disassembleCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("disassembleCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).disassembleCompose(platformArgs, landingInfo, disassembleComposePositionReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  disassembleCompose(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq disassembleComposePositionReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disassembleCompose(landingInfo, disassembleComposePositionReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  batchClosePosition(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    batchClosePosition(landingInfo, batchClosedPositionReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  batchClosePosition(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchClosePosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).batchClosePosition(platformArgs, landingInfo, batchClosedPositionReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  batchClosePosition(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.BatchClosedPositionReq batchClosedPositionReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    batchClosePosition(landingInfo, batchClosedPositionReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  recoverClosedPosition(LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    recoverClosedPosition(landingInfo, subAccountId, targetKey, targetType, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  recoverClosedPosition(LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("recoverClosedPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).recoverClosedPosition(platformArgs, landingInfo, subAccountId, targetKey, targetType);
      return null;
      }
    }, invokeInfo);
  }

  public void  recoverClosedPosition(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    recoverClosedPosition(landingInfo, subAccountId, targetKey, targetType, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  mergeToCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    mergeToCompose(landingInfo, mergeToComposeReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  mergeToCompose(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("mergeToCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).mergeToCompose(platformArgs, landingInfo, mergeToComposeReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  mergeToCompose(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.StatMergeToComposeReq mergeToComposeReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    mergeToCompose(landingInfo, mergeToComposeReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredStatContractPosition(LandingInfo landingInfo, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredStatContractPosition(landingInfo, subAccountId, sledContractId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredStatContractPosition(LandingInfo landingInfo, long subAccountId, long sledContractId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteExpiredStatContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).deleteExpiredStatContractPosition(platformArgs, landingInfo, subAccountId, sledContractId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteExpiredStatContractPosition(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, long sledContractId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredStatContractPosition(landingInfo, subAccountId, sledContractId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryPage  queryStatPositionSummaryPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryPage  queryStatPositionSummaryPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionSummaryPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatPositionSummaryPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatPositionSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryStatPositionSummaryPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryPage  queryStatPositionSummaryPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionItemPage  queryStatPositionItemPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionItemPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionItemPage  queryStatPositionItemPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionItemPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatPositionItemPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatPositionItemPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryStatPositionItemPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatPositionItemPage  queryStatPositionItemPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionItemPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCurrentDayStatClosedPositionPage(landingInfo, subAccountId, targetKey, targetType, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCurrentDayStatClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryCurrentDayStatClosedPositionPage(platformArgs, landingInfo, subAccountId, targetKey, targetType);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCurrentDayStatClosedPositionPage(landingInfo, subAccountId, targetKey, targetType, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryStatClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryStatClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDetail>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryStatClosedPositionDetail(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryStatClosedPositionDetail(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryArchivedClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryArchivedClosedPositionPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryArchivedClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryArchivedClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryArchivedClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDetail>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryArchivedClosedPositionDetail(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryArchivedClosedPositionDetail(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage  queryStatPositionSummaryExPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryExPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage  queryStatPositionSummaryExPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionSummaryExPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryStatPositionSummaryExPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage  queryStatPositionSummaryExPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryExPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionUnitPage  queryStatPositionUnitPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionUnitPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatPositionUnitPage  queryStatPositionUnitPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionUnitPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatPositionUnitPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatPositionUnitPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryStatPositionUnitPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatPositionUnitPage  queryStatPositionUnitPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionUnitPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionPage(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryHistoryClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryHistoryClosedPositionPage(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionPage(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryHistoryClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryHistoryClosedPositionDetail(LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryHistoryClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.statis.StatClosedPositionDetail>(){
    @Override
    public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryHistoryClosedPositionDetail(platformArgs, landingInfo, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.statis.StatClosedPositionDetail  queryHistoryClosedPositionDetail(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionDetail(landingInfo, queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage  getXQTradeLameTaskNotePage(LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeLameTaskNotePage(landingInfo, qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage  getXQTradeLameTaskNotePage(LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQTradeLameTaskNotePage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage>(){
    @Override
    public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getXQTradeLameTaskNotePage(platformArgs, landingInfo, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage  getXQTradeLameTaskNotePage(int routeKey, int timeout,LandingInfo landingInfo, QueryXQTradeLameTaskNotePageOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeLameTaskNotePage(landingInfo, qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchDeleteXQTradeLameTaskNotes(LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchDeleteXQTradeLameTaskNotes(landingInfo, subAccountId, xqTradeIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchDeleteXQTradeLameTaskNotes(LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchDeleteXQTradeLameTaskNotes").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo>>(){
    @Override
    public Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchDeleteXQTradeLameTaskNotes(platformArgs, landingInfo, subAccountId, xqTradeIds);
      }
    }, invokeInfo);
  }

  public Map<Long,org.soldier.platform.svr_platform.comm.ErrorInfo>  batchDeleteXQTradeLameTaskNotes(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, Set<Long> xqTradeIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchDeleteXQTradeLameTaskNotes(landingInfo, subAccountId, xqTradeIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryMailBoxMessage(landingInfo, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryMailBoxMessage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.mailbox.user.message.thriftapi.UserMessagePage>(){
    @Override
    public xueqiao.mailbox.user.message.thriftapi.UserMessagePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryMailBoxMessage(platformArgs, landingInfo, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(int routeKey, int timeout,LandingInfo landingInfo, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryMailBoxMessage(landingInfo, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  markMessageAsRead(LandingInfo landingInfo, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return markMessageAsRead(landingInfo, hostingMessageIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  markMessageAsRead(LandingInfo landingInfo, Set<Long> hostingMessageIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("markMessageAsRead").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).markMessageAsRead(platformArgs, landingInfo, hostingMessageIds);
      }
    }, invokeInfo);
  }

  public boolean  markMessageAsRead(int routeKey, int timeout,LandingInfo landingInfo, Set<Long> hostingMessageIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return markMessageAsRead(landingInfo, hostingMessageIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem>  getAllSupportedItems(LandingInfo landingInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSupportedItems(landingInfo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem>  getAllSupportedItems(LandingInfo landingInfo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAllSupportedItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem>>(){
    @Override
    public List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getAllSupportedItems(platformArgs, landingInfo);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem>  getAllSupportedItems(int routeKey, int timeout,LandingInfo landingInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSupportedItems(landingInfo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getRiskRuleJointVersion(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJointVersion(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getRiskRuleJointVersion(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskRuleJointVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getRiskRuleJointVersion(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public int  getRiskRuleJointVersion(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJointVersion(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  getRiskRuleJoint(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJoint(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  getRiskRuleJoint(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskRuleJoint").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getRiskRuleJoint(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  getRiskRuleJoint(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJoint(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetSupportedItems(LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetSupportedItems(landingInfo, subAccountId, version, openedItemIds, closedItemIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetSupportedItems(LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetSupportedItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchSetSupportedItems(platformArgs, landingInfo, subAccountId, version, openedItemIds, closedItemIds);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetSupportedItems(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetSupportedItems(landingInfo, subAccountId, version, openedItemIds, closedItemIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetTradedCommodityItems(LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetTradedCommodityItems(landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetTradedCommodityItems(LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetTradedCommodityItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchSetTradedCommodityItems(platformArgs, landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetTradedCommodityItems(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetTradedCommodityItems(landingInfo, subAccountId, version, enabledCommodityIds, disabledCommodityIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetGlobalRules(LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetGlobalRules(landingInfo, subAccountId, version, ruleItems, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetGlobalRules(LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetGlobalRules").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchSetGlobalRules(platformArgs, landingInfo, subAccountId, version, ruleItems);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetGlobalRules(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, int version, Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem> ruleItems)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetGlobalRules(landingInfo, subAccountId, version, ruleItems, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetCommodityRules(LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetCommodityRules(landingInfo, subAccountId, version, rules, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetCommodityRules(LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetCommodityRules").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).batchSetCommodityRules(platformArgs, landingInfo, subAccountId, version, rules);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  batchSetCommodityRules(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, int version, Map<Long,Map<String,xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem>> rules)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetCommodityRules(landingInfo, subAccountId, version, rules, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  setRiskEnabled(LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setRiskEnabled(landingInfo, subAccountId, version, riskEnabled, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  setRiskEnabled(LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setRiskEnabled").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).setRiskEnabled(platformArgs, landingInfo, subAccountId, version, riskEnabled);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint  setRiskEnabled(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, int version, boolean riskEnabled)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setRiskEnabled(landingInfo, subAccountId, version, riskEnabled, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo  getRiskFrameDataInfo(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskFrameDataInfo(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo  getRiskFrameDataInfo(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskFrameDataInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo>(){
    @Override
    public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).getRiskFrameDataInfo(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo  getRiskFrameDataInfo(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskFrameDataInfo(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralMarginSetting(landingInfo, marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setGeneralMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).setGeneralMarginSetting(platformArgs, landingInfo, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  setGeneralMarginSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralMarginSetting(landingInfo, marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralCommissionSetting(landingInfo, commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setGeneralCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).setGeneralCommissionSetting(platformArgs, landingInfo, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  setGeneralCommissionSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralCommissionSetting(landingInfo, commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecMarginSetting(landingInfo, marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).addSpecMarginSetting(platformArgs, landingInfo, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  addSpecMarginSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecMarginSetting(landingInfo, marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecCommissionSetting(landingInfo, commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).addSpecCommissionSetting(platformArgs, landingInfo, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  addSpecCommissionSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecCommissionSetting(landingInfo, commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecMarginSetting(landingInfo, marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecMarginSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateSpecMarginSetting(platformArgs, landingInfo, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateSpecMarginSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecMarginSetting(landingInfo, marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecCommissionSetting(landingInfo, commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecCommissionSetting(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).updateSpecCommissionSetting(platformArgs, landingInfo, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateSpecCommissionSetting(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecCommissionSetting(landingInfo, commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecMarginSetting(LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecMarginSetting(landingInfo, subAccountId, commodityId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecMarginSetting(LandingInfo landingInfo, long subAccountId, long commodityId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).deleteSpecMarginSetting(platformArgs, landingInfo, subAccountId, commodityId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteSpecMarginSetting(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, long commodityId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecMarginSetting(landingInfo, subAccountId, commodityId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecCommissionSetting(LandingInfo landingInfo, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecCommissionSetting(landingInfo, subAccountId, commodityId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecCommissionSetting(LandingInfo landingInfo, long subAccountId, long commodityId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTerminalAo.Client(protocol).deleteSpecCommissionSetting(platformArgs, landingInfo, subAccountId, commodityId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteSpecCommissionSetting(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId, long commodityId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecCommissionSetting(landingInfo, subAccountId, commodityId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings  queryXQGeneralMarginSettings(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralMarginSettings(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings  queryXQGeneralMarginSettings(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQGeneralMarginSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQGeneralMarginSettings(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings  queryXQGeneralMarginSettings(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralMarginSettings(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(LandingInfo landingInfo, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralCommissionSettings(landingInfo, subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(LandingInfo landingInfo, long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQGeneralCommissionSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQGeneralCommissionSettings(platformArgs, landingInfo, subAccountId);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(int routeKey, int timeout,LandingInfo landingInfo, long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralCommissionSettings(landingInfo, subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecMarginSettingPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQSpecMarginSettingPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQSpecMarginSettingPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecMarginSettingPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecCommissionSettingPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQSpecCommissionSettingPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQSpecCommissionSettingPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecCommissionSettingPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage  queryUpsideContractMarginPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractMarginPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage  queryUpsideContractMarginPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUpsideContractMarginPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryUpsideContractMarginPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage  queryUpsideContractMarginPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractMarginPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage  queryUpsideContractCommissionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractCommissionPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage  queryUpsideContractCommissionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUpsideContractCommissionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryUpsideContractCommissionPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage  queryUpsideContractCommissionPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractCommissionPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage  queryXQContractMarginPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractMarginPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage  queryXQContractMarginPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQContractMarginPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQContractMarginPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage  queryXQContractMarginPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractMarginPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage  queryXQContractCommissionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractCommissionPage(landingInfo, queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage  queryXQContractCommissionPage(LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQContractCommissionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage>(){
    @Override
    public xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).queryXQContractCommissionPage(platformArgs, landingInfo, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage  queryXQContractCommissionPage(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractCommissionPage(landingInfo, queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addAssetAccountWorkingOrder(LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addAssetAccountWorkingOrder(landingInfo, assetAccount, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addAssetAccountWorkingOrder(LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addAssetAccountWorkingOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTerminalAo.Client(protocol).addAssetAccountWorkingOrder(platformArgs, landingInfo, assetAccount);
      }
    }, invokeInfo);
  }

  public long  addAssetAccountWorkingOrder(int routeKey, int timeout,LandingInfo landingInfo, xueqiao.working.order.thriftapi.AssetAccount assetAccount)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addAssetAccountWorkingOrder(landingInfo, assetAccount, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
