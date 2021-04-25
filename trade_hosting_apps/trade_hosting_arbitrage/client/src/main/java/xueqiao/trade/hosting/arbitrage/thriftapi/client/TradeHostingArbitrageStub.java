package xueqiao.trade.hosting.arbitrage.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

public class TradeHostingArbitrageStub extends BaseStub {

  public TradeHostingArbitrageStub() {
    super(TradeHostingArbitrageVariable.serviceKey);
  }

  public void  createXQOrder(HostingXQOrder order) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createXQOrder(order, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  createXQOrder(HostingXQOrder order,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createXQOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).createXQOrder(platformArgs, order);
      return null;
      }
    }, invokeInfo);
  }

  public void  createXQOrder(int routeKey, int timeout,HostingXQOrder order)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createXQOrder(order, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  cancelXQOrder(String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    cancelXQOrder(orderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  cancelXQOrder(String orderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("cancelXQOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).cancelXQOrder(platformArgs, orderId);
      return null;
      }
    }, invokeInfo);
  }

  public void  cancelXQOrder(int routeKey, int timeout,String orderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    cancelXQOrder(orderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  suspendXQOrder(String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    suspendXQOrder(orderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  suspendXQOrder(String orderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("suspendXQOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).suspendXQOrder(platformArgs, orderId);
      return null;
      }
    }, invokeInfo);
  }

  public void  suspendXQOrder(int routeKey, int timeout,String orderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    suspendXQOrder(orderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  resumeXQOrder(String orderId, HostingXQOrderResumeMode resumeMode) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    resumeXQOrder(orderId, resumeMode, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  resumeXQOrder(String orderId, HostingXQOrderResumeMode resumeMode,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("resumeXQOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).resumeXQOrder(platformArgs, orderId, resumeMode);
      return null;
      }
    }, invokeInfo);
  }

  public void  resumeXQOrder(int routeKey, int timeout,String orderId, HostingXQOrderResumeMode resumeMode)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    resumeXQOrder(orderId, resumeMode, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryEffectXQOrderIndexPage  getEffectXQOrderIndexPage(QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getEffectXQOrderIndexPage(qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public QueryEffectXQOrderIndexPage  getEffectXQOrderIndexPage(QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getEffectXQOrderIndexPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<QueryEffectXQOrderIndexPage>(){
    @Override
    public QueryEffectXQOrderIndexPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getEffectXQOrderIndexPage(platformArgs, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public QueryEffectXQOrderIndexPage  getEffectXQOrderIndexPage(int routeKey, int timeout,QueryEffectXQOrderIndexOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getEffectXQOrderIndexPage(qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,HostingXQOrder>  getXQOrders(Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrders(orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,HostingXQOrder>  getXQOrders(Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrders").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,HostingXQOrder>>(){
    @Override
    public Map<String,HostingXQOrder> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getXQOrders(platformArgs, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,HostingXQOrder>  getXQOrders(int routeKey, int timeout,Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrders(orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,List<HostingXQTrade>>  getXQTrades(Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTrades(orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,List<HostingXQTrade>>  getXQTrades(Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQTrades").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,List<HostingXQTrade>>>(){
    @Override
    public Map<String,List<HostingXQTrade>> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getXQTrades(platformArgs, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,List<HostingXQTrade>>  getXQTrades(int routeKey, int timeout,Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTrades(orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(Set<String> orderIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderWithTradeLists(orderIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<String,HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(Set<String> orderIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderWithTradeLists").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<String,HostingXQOrderWithTradeList>>(){
    @Override
    public Map<String,HostingXQOrderWithTradeList> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getXQOrderWithTradeLists(platformArgs, orderIds);
      }
    }, invokeInfo);
  }

  public Map<String,HostingXQOrderWithTradeList>  getXQOrderWithTradeLists(int routeKey, int timeout,Set<String> orderIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderWithTradeLists(orderIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderExecDetail  getXQOrderExecDetail(String orderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderExecDetail(orderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderExecDetail  getXQOrderExecDetail(String orderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderExecDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQOrderExecDetail>(){
    @Override
    public HostingXQOrderExecDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getXQOrderExecDetail(platformArgs, orderId);
      }
    }, invokeInfo);
  }

  public HostingXQOrderExecDetail  getXQOrderExecDetail(int routeKey, int timeout,String orderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQOrderExecDetail(orderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  clearAll() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  clearAll(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("clearAll").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,HostingXQTrade>  filterXQTrades(Set<String> orderIds, Set<Long> tradeIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return filterXQTrades(orderIds, tradeIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,HostingXQTrade>  filterXQTrades(Set<String> orderIds, Set<Long> tradeIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("filterXQTrades").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,HostingXQTrade>>(){
    @Override
    public Map<Long,HostingXQTrade> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).filterXQTrades(platformArgs, orderIds, tradeIds);
      }
    }, invokeInfo);
  }

  public Map<Long,HostingXQTrade>  filterXQTrades(int routeKey, int timeout,Set<String> orderIds, Set<Long> tradeIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return filterXQTrades(orderIds, tradeIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingXQTradeRelatedItem>  getXQTradeRelatedItems(String orderId, long tradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeRelatedItems(orderId, tradeId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingXQTradeRelatedItem>  getXQTradeRelatedItems(String orderId, long tradeId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQTradeRelatedItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<HostingXQTradeRelatedItem>>(){
    @Override
    public List<HostingXQTradeRelatedItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getXQTradeRelatedItems(platformArgs, orderId, tradeId);
      }
    }, invokeInfo);
  }

  public List<HostingXQTradeRelatedItem>  getXQTradeRelatedItems(int routeKey, int timeout,String orderId, long tradeId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getXQTradeRelatedItems(orderId, tradeId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQComposeLimitOrderSettings  getSystemXQComposeLimitOrderSettings() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSystemXQComposeLimitOrderSettings(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQComposeLimitOrderSettings  getSystemXQComposeLimitOrderSettings(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSystemXQComposeLimitOrderSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQComposeLimitOrderSettings>(){
    @Override
    public HostingXQComposeLimitOrderSettings call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingArbitrage.Client(protocol).getSystemXQComposeLimitOrderSettings(platformArgs);
      }
    }, invokeInfo);
  }

  public HostingXQComposeLimitOrderSettings  getSystemXQComposeLimitOrderSettings(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSystemXQComposeLimitOrderSettings(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setSystemXQComposeLimitOrderSettings(HostingXQComposeLimitOrderSettings settings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setSystemXQComposeLimitOrderSettings(settings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setSystemXQComposeLimitOrderSettings(HostingXQComposeLimitOrderSettings settings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setSystemXQComposeLimitOrderSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingArbitrage.Client(protocol).setSystemXQComposeLimitOrderSettings(platformArgs, settings);
      return null;
      }
    }, invokeInfo);
  }

  public void  setSystemXQComposeLimitOrderSettings(int routeKey, int timeout,HostingXQComposeLimitOrderSettings settings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setSystemXQComposeLimitOrderSettings(settings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
