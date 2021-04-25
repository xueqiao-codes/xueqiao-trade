package xueqiao.trade.hosting.upside.entry.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import xueqiao.trade.hosting.upside.entry.TSubProcessInfo;
import xueqiao.trade.hosting.upside.entry.TSyncOrderStateBatchReq;
import xueqiao.trade.hosting.upside.entry.TradeHostingUpsideEntry;
import xueqiao.trade.hosting.upside.entry.TradeHostingUpsideEntryVariable;

public class TradeHostingUpsideEntryStub extends BaseStub {

  public TradeHostingUpsideEntryStub() {
    super(TradeHostingUpsideEntryVariable.serviceKey);
  }

  public List<TSubProcessInfo>  getSubProcessInfos() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubProcessInfos(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TSubProcessInfo>  getSubProcessInfos(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSubProcessInfos").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TSubProcessInfo>>(){
    @Override
    public List<TSubProcessInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingUpsideEntry.Client(protocol).getSubProcessInfos(platformArgs);
      }
    }, invokeInfo);
  }

  public List<TSubProcessInfo>  getSubProcessInfos(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubProcessInfos(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  restartSubProcess(long trade_account_id) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    restartSubProcess(trade_account_id, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  restartSubProcess(long trade_account_id,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("restartSubProcess").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).restartSubProcess(platformArgs, trade_account_id);
      return null;
      }
    }, invokeInfo);
  }

  public void  restartSubProcess(int routeKey, int timeout,long trade_account_id)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    restartSubProcess(trade_account_id, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingExecOrderRef  allocOrderRef() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return allocOrderRef(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingExecOrderRef  allocOrderRef(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("allocOrderRef").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.HostingExecOrderRef>(){
    @Override
    public xueqiao.trade.hosting.HostingExecOrderRef call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingUpsideEntry.Client(protocol).allocOrderRef(platformArgs);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.HostingExecOrderRef  allocOrderRef(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return allocOrderRef(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  orderInsert(xueqiao.trade.hosting.HostingExecOrder insertOrder) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    orderInsert(insertOrder, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  orderInsert(xueqiao.trade.hosting.HostingExecOrder insertOrder,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("orderInsert").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).orderInsert(platformArgs, insertOrder);
      return null;
      }
    }, invokeInfo);
  }

  public void  orderInsert(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrder insertOrder)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    orderInsert(insertOrder, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  orderDelete(xueqiao.trade.hosting.HostingExecOrder deleteOrder) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    orderDelete(deleteOrder, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  orderDelete(xueqiao.trade.hosting.HostingExecOrder deleteOrder,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("orderDelete").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).orderDelete(platformArgs, deleteOrder);
      return null;
      }
    }, invokeInfo);
  }

  public void  orderDelete(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrder deleteOrder)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    orderDelete(deleteOrder, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderState(xueqiao.trade.hosting.HostingExecOrder syncOrder) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderState(syncOrder, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderState(xueqiao.trade.hosting.HostingExecOrder syncOrder,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("syncOrderState").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).syncOrderState(platformArgs, syncOrder);
      return null;
      }
    }, invokeInfo);
  }

  public void  syncOrderState(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrder syncOrder)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderState(syncOrder, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderTrades(xueqiao.trade.hosting.HostingExecOrder syncOrder) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderTrades(syncOrder, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderTrades(xueqiao.trade.hosting.HostingExecOrder syncOrder,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("syncOrderTrades").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).syncOrderTrades(platformArgs, syncOrder);
      return null;
      }
    }, invokeInfo);
  }

  public void  syncOrderTrades(int routeKey, int timeout,xueqiao.trade.hosting.HostingExecOrder syncOrder)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderTrades(syncOrder, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderStateBatch(TSyncOrderStateBatchReq batchReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderStateBatch(batchReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncOrderStateBatch(TSyncOrderStateBatchReq batchReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("syncOrderStateBatch").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingUpsideEntry.Client(protocol).syncOrderStateBatch(platformArgs, batchReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  syncOrderStateBatch(int routeKey, int timeout,TSyncOrderStateBatchReq batchReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncOrderStateBatch(batchReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
