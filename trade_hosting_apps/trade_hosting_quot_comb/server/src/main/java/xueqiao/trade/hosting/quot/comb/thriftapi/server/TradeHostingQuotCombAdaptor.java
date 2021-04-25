package xueqiao.trade.hosting.quot.comb.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;
import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotComb;
import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotCombVariable;


public abstract class TradeHostingQuotCombAdaptor implements TradeHostingQuotComb.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingQuotCombVariable.serviceKey,"syncCombTopics",platformArgs);
syncCombTopics(oCntl, syncRequest);
  }

  protected abstract void syncCombTopics(TServiceCntl oCntl, SyncCombTopicsRequest syncRequest) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingQuotCombAdaptor(){
    methodParameterNameMap.put("syncCombTopics",new String[]{"platformArgs", "syncRequest"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
