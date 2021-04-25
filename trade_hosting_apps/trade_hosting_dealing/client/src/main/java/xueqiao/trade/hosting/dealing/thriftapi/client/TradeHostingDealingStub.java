package xueqiao.trade.hosting.dealing.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import xueqiao.trade.hosting.dealing.thriftapi.HostingExecOrderPage;
import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealing;
import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealingVariable;

public class TradeHostingDealingStub extends BaseStub {

  public TradeHostingDealingStub() {
    super(TradeHostingDealingVariable.serviceKey);
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
      new TradeHostingDealing.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  createUserDeal(int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createUserDeal(subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  createUserDeal(int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createUserDeal").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingDealing.Client(protocol).createUserDeal(platformArgs, subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source);
      return null;
      }
    }, invokeInfo);
  }

  public void  createUserDeal(int routeKey, int timeout,int subUserId, long subAccountId, long execOrderId, xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary, xueqiao.trade.hosting.HostingExecOrderDetail orderDetail, String source)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    createUserDeal(subUserId, subAccountId, execOrderId, contractSummary, orderDetail, source, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  revokeDeal(long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    revokeDeal(execOrderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  revokeDeal(long execOrderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("revokeDeal").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingDealing.Client(protocol).revokeDeal(platformArgs, execOrderId);
      return null;
      }
    }, invokeInfo);
  }

  public void  revokeDeal(int routeKey, int timeout,long execOrderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    revokeDeal(execOrderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecOrder>  getOrder(long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getOrder(execOrderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecOrder>  getOrder(long execOrderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getOrder").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingExecOrder>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingExecOrder> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getOrder(platformArgs, execOrderId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingExecOrder>  getOrder(int routeKey, int timeout,long execOrderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getOrder(execOrderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecTrade>  getOrderTrades(long execOrderId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getOrderTrades(execOrderId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecTrade>  getOrderTrades(long execOrderId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getOrderTrades").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingExecTrade>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingExecTrade> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getOrderTrades(platformArgs, execOrderId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingExecTrade>  getOrderTrades(int routeKey, int timeout,long execOrderId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getOrderTrades(execOrderId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecTradeLeg>  getTradeRelatedLegs(long execTradeId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeRelatedLegs(execTradeId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingExecTradeLeg>  getTradeRelatedLegs(long execTradeId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeRelatedLegs").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingExecTradeLeg>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingExecTradeLeg> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getTradeRelatedLegs(platformArgs, execTradeId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingExecTradeLeg>  getTradeRelatedLegs(int routeKey, int timeout,long execTradeId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeRelatedLegs(execTradeId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getRunningExecOrderIdByOrderRef(xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRunningExecOrderIdByOrderRef(accountSummary, orderRef, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getRunningExecOrderIdByOrderRef(xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRunningExecOrderIdByOrderRef").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getRunningExecOrderIdByOrderRef(platformArgs, accountSummary, orderRef);
      }
    }, invokeInfo);
  }

  public long  getRunningExecOrderIdByOrderRef(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderRef orderRef)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRunningExecOrderIdByOrderRef(accountSummary, orderRef, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getRunningExecOrderIdByOrderDealID(xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRunningExecOrderIdByOrderDealID(accountSummary, dealId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getRunningExecOrderIdByOrderDealID(xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRunningExecOrderIdByOrderDealID").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getRunningExecOrderIdByOrderDealID(platformArgs, accountSummary, dealId);
      }
    }, invokeInfo);
  }

  public long  getRunningExecOrderIdByOrderDealID(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary accountSummary, xueqiao.trade.hosting.HostingExecOrderDealID dealId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRunningExecOrderIdByOrderDealID(accountSummary, dealId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingExecOrderPage  getInDealingOrderPage(org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getInDealingOrderPage(pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingExecOrderPage  getInDealingOrderPage(org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getInDealingOrderPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingExecOrderPage>(){
    @Override
    public HostingExecOrderPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).getInDealingOrderPage(platformArgs, pageOption);
      }
    }, invokeInfo);
  }

  public HostingExecOrderPage  getInDealingOrderPage(int routeKey, int timeout,org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getInDealingOrderPage(pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecOrderId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecOrderId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecOrderId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createExecOrderId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).createExecOrderId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createExecOrderId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecOrderId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecTradeId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecTradeId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecTradeId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createExecTradeId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).createExecTradeId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createExecTradeId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecTradeId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecTradeLegId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecTradeLegId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createExecTradeLegId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createExecTradeLegId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingDealing.Client(protocol).createExecTradeLegId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createExecTradeLegId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createExecTradeLegId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
