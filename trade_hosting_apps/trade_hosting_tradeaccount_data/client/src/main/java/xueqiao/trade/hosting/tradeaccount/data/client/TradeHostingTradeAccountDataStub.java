package xueqiao.trade.hosting.tradeaccount.data.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountData;
import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountDataVariable;

public class TradeHostingTradeAccountDataStub extends BaseStub {

  public TradeHostingTradeAccountDataStub() {
    super(TradeHostingTradeAccountDataVariable.serviceKey);
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
      new TradeHostingTradeAccountData.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountFund>  getNowFund(long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getNowFund(tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountFund>  getNowFund(long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getNowFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TradeAccountFund>>(){
    @Override
    public List<TradeAccountFund> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTradeAccountData.Client(protocol).getNowFund(platformArgs, tradeAccountId);
      }
    }, invokeInfo);
  }

  public List<TradeAccountFund>  getNowFund(int routeKey, int timeout,long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getNowFund(tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountFundHisItem>  getHisFunds(long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHisFunds(tradeAccountId, fundDateBegin, fundDateEnd, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountFundHisItem>  getHisFunds(long tradeAccountId, String fundDateBegin, String fundDateEnd,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHisFunds").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TradeAccountFundHisItem>>(){
    @Override
    public List<TradeAccountFundHisItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTradeAccountData.Client(protocol).getHisFunds(platformArgs, tradeAccountId, fundDateBegin, fundDateEnd);
      }
    }, invokeInfo);
  }

  public List<TradeAccountFundHisItem>  getHisFunds(int routeKey, int timeout,long tradeAccountId, String fundDateBegin, String fundDateEnd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHisFunds(tradeAccountId, fundDateBegin, fundDateEnd, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountSettlementInfo>  getSettlementInfos(long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementInfos(tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountSettlementInfo>  getSettlementInfos(long tradeAccountId, String settlementDateBegin, String settlementDateEnd,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSettlementInfos").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TradeAccountSettlementInfo>>(){
    @Override
    public List<TradeAccountSettlementInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTradeAccountData.Client(protocol).getSettlementInfos(platformArgs, tradeAccountId, settlementDateBegin, settlementDateEnd);
      }
    }, invokeInfo);
  }

  public List<TradeAccountSettlementInfo>  getSettlementInfos(int routeKey, int timeout,long tradeAccountId, String settlementDateBegin, String settlementDateEnd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementInfos(tradeAccountId, settlementDateBegin, settlementDateEnd, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountNetPositionSummary>  getNetPositionSummaries(long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getNetPositionSummaries(tradeAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TradeAccountNetPositionSummary>  getNetPositionSummaries(long tradeAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getNetPositionSummaries").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TradeAccountNetPositionSummary>>(){
    @Override
    public List<TradeAccountNetPositionSummary> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTradeAccountData.Client(protocol).getNetPositionSummaries(platformArgs, tradeAccountId);
      }
    }, invokeInfo);
  }

  public List<TradeAccountNetPositionSummary>  getNetPositionSummaries(int routeKey, int timeout,long tradeAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getNetPositionSummaries(tradeAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
