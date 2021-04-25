package xueqiao.trade.hosting.storage.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import xueqiao.trade.hosting.storage.thriftapi.TradeAccountInvalidDescription;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;
import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorage;
import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorageVariable;

public class TradeHostingStorageStub extends BaseStub {

  public TradeHostingStorageStub() {
    super(TradeHostingStorageVariable.serviceKey);
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getTraddeAccount(long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTraddeAccount(tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getTraddeAccount(long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTraddeAccount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingTradeAccount>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingTradeAccount> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getTraddeAccount(platformArgs, tradeAccountId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getTraddeAccount(int routeKey, int timeout,long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTraddeAccount(tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntry(long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getBrokerAccessEntry(tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntry(long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getBrokerAccessEntry").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>>(){
    @Override
    public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getBrokerAccessEntry(platformArgs, tradeAccountId);
      }
    }, invokeInfo);
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntry(int routeKey, int timeout,long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getBrokerAccessEntry(tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setTradeAccountInvalid(long tradeAccountId, TradeAccountInvalidDescription invalidDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setTradeAccountInvalid(tradeAccountId, invalidDescription, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setTradeAccountInvalid(long tradeAccountId, TradeAccountInvalidDescription invalidDescription,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setTradeAccountInvalid").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingStorage.Client(protocol).setTradeAccountInvalid(platformArgs, tradeAccountId, invalidDescription);
      return null;
      }
    }, invokeInfo);
  }

  public void  setTradeAccountInvalid(int routeKey, int timeout,long tradeAccountId, TradeAccountInvalidDescription invalidDescription)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setTradeAccountInvalid(tradeAccountId, invalidDescription, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setTradeAccountActive(long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setTradeAccountActive(tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setTradeAccountActive(long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setTradeAccountActive").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingStorage.Client(protocol).setTradeAccountActive(platformArgs, tradeAccountId);
      return null;
      }
    }, invokeInfo);
  }

  public void  setTradeAccountActive(int routeKey, int timeout,long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setTradeAccountActive(tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getAllTradeAccounts() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllTradeAccounts(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getAllTradeAccounts(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAllTradeAccounts").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingTradeAccount>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingTradeAccount> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getAllTradeAccounts(platformArgs);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingTradeAccount>  getAllTradeAccounts(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllTradeAccounts(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntryFromCloud(long tradeBrokerId, long tradeBrokerAccessId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getBrokerAccessEntryFromCloud(tradeBrokerId, tradeBrokerAccessId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntryFromCloud(long tradeBrokerId, long tradeBrokerAccessId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getBrokerAccessEntryFromCloud").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>>(){
    @Override
    public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getBrokerAccessEntryFromCloud(platformArgs, tradeBrokerId, tradeBrokerAccessId);
      }
    }, invokeInfo);
  }

  public List<com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry>  getBrokerAccessEntryFromCloud(int routeKey, int timeout,long tradeBrokerId, long tradeBrokerAccessId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getBrokerAccessEntryFromCloud(tradeBrokerId, tradeBrokerAccessId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createComposeGraphId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createComposeGraphId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createComposeGraphId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createComposeGraphId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).createComposeGraphId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createComposeGraphId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createComposeGraphId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createTradeAccountId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createTradeAccountId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createTradeAccountId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createTradeAccountId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).createTradeAccountId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createTradeAccountId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createTradeAccountId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createSubAccountId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createSubAccountId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  createSubAccountId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("createSubAccountId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).createSubAccountId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  createSubAccountId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return createSubAccountId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateConfig(UpdateConfigDescription configDescription) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateConfig(configDescription, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateConfig(UpdateConfigDescription configDescription,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingStorage.Client(protocol).updateConfig(platformArgs, configDescription);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateConfig(int routeKey, int timeout,UpdateConfigDescription configDescription)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateConfig(configDescription, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getMachineId() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getMachineId(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  getMachineId(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getMachineId").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getMachineId(platformArgs);
      }
    }, invokeInfo);
  }

  public long  getMachineId(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getMachineId(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingSession>  getHostingSession(int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSession(subUserId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.trade.hosting.HostingSession>  getHostingSession(int subUserId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.trade.hosting.HostingSession>>(){
    @Override
    public List<xueqiao.trade.hosting.HostingSession> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingStorage.Client(protocol).getHostingSession(platformArgs, subUserId);
      }
    }, invokeInfo);
  }

  public List<xueqiao.trade.hosting.HostingSession>  getHostingSession(int routeKey, int timeout,int subUserId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSession(subUserId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
