package xueqiao.trade.hosting.storage.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.storage.thriftapi.TradeAccountInvalidDescription;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;
import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorage;
import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorageVariable;


public abstract class TradeHostingStorageAdaptor implements TradeHostingStorage.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public List<xueqiao.trade.hosting.HostingTradeAccount> getTraddeAccount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getTraddeAccount",platformArgs);
    return getTraddeAccount(oCntl, tradeAccountId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingTradeAccount> getTraddeAccount(TServiceCntl oCntl, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> getBrokerAccessEntry(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getBrokerAccessEntry",platformArgs);
    return getBrokerAccessEntry(oCntl, tradeAccountId);
  }

  protected abstract List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> getBrokerAccessEntry(TServiceCntl oCntl, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setTradeAccountInvalid(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, TradeAccountInvalidDescription invalidDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"setTradeAccountInvalid",platformArgs);
setTradeAccountInvalid(oCntl, tradeAccountId, invalidDescription);
  }

  protected abstract void setTradeAccountInvalid(TServiceCntl oCntl, long tradeAccountId, TradeAccountInvalidDescription invalidDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setTradeAccountActive(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"setTradeAccountActive",platformArgs);
setTradeAccountActive(oCntl, tradeAccountId);
  }

  protected abstract void setTradeAccountActive(TServiceCntl oCntl, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingTradeAccount> getAllTradeAccounts(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getAllTradeAccounts",platformArgs);
    return getAllTradeAccounts(oCntl);
  }

  protected abstract List<xueqiao.trade.hosting.HostingTradeAccount> getAllTradeAccounts(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> getBrokerAccessEntryFromCloud(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeBrokerId, long tradeBrokerAccessId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getBrokerAccessEntryFromCloud",platformArgs);
    return getBrokerAccessEntryFromCloud(oCntl, tradeBrokerId, tradeBrokerAccessId);
  }

  protected abstract List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> getBrokerAccessEntryFromCloud(TServiceCntl oCntl, long tradeBrokerId, long tradeBrokerAccessId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createComposeGraphId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"createComposeGraphId",platformArgs);
    return createComposeGraphId(oCntl);
  }

  protected abstract long createComposeGraphId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createTradeAccountId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"createTradeAccountId",platformArgs);
    return createTradeAccountId(oCntl);
  }

  protected abstract long createTradeAccountId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long createSubAccountId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"createSubAccountId",platformArgs);
    return createSubAccountId(oCntl);
  }

  protected abstract long createSubAccountId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, UpdateConfigDescription configDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"updateConfig",platformArgs);
updateConfig(oCntl, configDescription);
  }

  protected abstract void updateConfig(TServiceCntl oCntl, UpdateConfigDescription configDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long getMachineId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getMachineId",platformArgs);
    return getMachineId(oCntl);
  }

  protected abstract long getMachineId(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<xueqiao.trade.hosting.HostingSession> getHostingSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingStorageVariable.serviceKey,"getHostingSession",platformArgs);
    return getHostingSession(oCntl, subUserId);
  }

  protected abstract List<xueqiao.trade.hosting.HostingSession> getHostingSession(TServiceCntl oCntl, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingStorageAdaptor(){
    methodParameterNameMap.put("getTraddeAccount",new String[]{"platformArgs", "tradeAccountId"});
    methodParameterNameMap.put("getBrokerAccessEntry",new String[]{"platformArgs", "tradeAccountId"});
    methodParameterNameMap.put("setTradeAccountInvalid",new String[]{"platformArgs", "tradeAccountId", "invalidDescription"});
    methodParameterNameMap.put("setTradeAccountActive",new String[]{"platformArgs", "tradeAccountId"});
    methodParameterNameMap.put("getAllTradeAccounts",new String[]{"platformArgs"});
    methodParameterNameMap.put("getBrokerAccessEntryFromCloud",new String[]{"platformArgs", "tradeBrokerId", "tradeBrokerAccessId"});
    methodParameterNameMap.put("createComposeGraphId",new String[]{"platformArgs"});
    methodParameterNameMap.put("createTradeAccountId",new String[]{"platformArgs"});
    methodParameterNameMap.put("createSubAccountId",new String[]{"platformArgs"});
    methodParameterNameMap.put("updateConfig",new String[]{"platformArgs", "configDescription"});
    methodParameterNameMap.put("getMachineId",new String[]{"platformArgs"});
    methodParameterNameMap.put("getHostingSession",new String[]{"platformArgs", "subUserId"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
