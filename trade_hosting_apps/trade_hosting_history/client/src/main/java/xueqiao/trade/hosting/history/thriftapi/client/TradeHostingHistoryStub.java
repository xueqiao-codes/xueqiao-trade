package xueqiao.trade.hosting.history.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.TradeHostingHistory;
import xueqiao.trade.hosting.history.thriftapi.TradeHostingHistoryVariable;

public class TradeHostingHistoryStub extends BaseStub {

  public TradeHostingHistoryStub() {
    super(TradeHostingHistoryVariable.serviceKey);
  }

  public void  clearAll() throws ErrorInfo, TException{
    clearAll(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  clearAll(TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("clearAll").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingHistory.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderHisIndexPage  getXQOrderHisIndexPage(QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws ErrorInfo, TException{
    return getXQOrderHisIndexPage(qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQOrderHisIndexPage  getXQOrderHisIndexPage(QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQOrderHisIndexPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQOrderHisIndexPage>(){
    @Override
    public HostingXQOrderHisIndexPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingHistory.Client(protocol).getXQOrderHisIndexPage(platformArgs, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingXQOrderHisIndexPage  getXQOrderHisIndexPage(int routeKey, int timeout,QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws ErrorInfo, TException{
    return getXQOrderHisIndexPage(qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQTradeHisIndexPage  getXQTradeHisIndexPage(QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws ErrorInfo, TException{
    return getXQTradeHisIndexPage(qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingXQTradeHisIndexPage  getXQTradeHisIndexPage(QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getXQTradeHisIndexPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingXQTradeHisIndexPage>(){
    @Override
    public HostingXQTradeHisIndexPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingHistory.Client(protocol).getXQTradeHisIndexPage(platformArgs, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingXQTradeHisIndexPage  getXQTradeHisIndexPage(int routeKey, int timeout,QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws ErrorInfo, TException{
    return getXQTradeHisIndexPage(qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
