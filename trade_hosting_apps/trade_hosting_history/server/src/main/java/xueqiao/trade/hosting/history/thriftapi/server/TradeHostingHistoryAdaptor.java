package xueqiao.trade.hosting.history.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.TradeHostingHistory;
import xueqiao.trade.hosting.history.thriftapi.TradeHostingHistoryVariable;


public abstract class TradeHostingHistoryAdaptor implements TradeHostingHistory.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingHistoryVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQOrderHisIndexPage getXQOrderHisIndexPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingHistoryVariable.serviceKey,"getXQOrderHisIndexPage",platformArgs);
    return getXQOrderHisIndexPage(oCntl, qryOption, pageOption);
  }

  protected abstract HostingXQOrderHisIndexPage getXQOrderHisIndexPage(TServiceCntl oCntl, QueryXQOrderHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingXQTradeHisIndexPage getXQTradeHisIndexPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingHistoryVariable.serviceKey,"getXQTradeHisIndexPage",platformArgs);
    return getXQTradeHisIndexPage(oCntl, qryOption, pageOption);
  }

  protected abstract HostingXQTradeHisIndexPage getXQTradeHisIndexPage(TServiceCntl oCntl, QueryXQTradeHisIndexItemOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingHistoryAdaptor(){
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("getXQOrderHisIndexPage",new String[]{"platformArgs", "qryOption", "pageOption"});
    methodParameterNameMap.put("getXQTradeHisIndexPage",new String[]{"platformArgs", "qryOption", "pageOption"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
