package xueqiao.trade.hosting.tradeaccount.data.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountData;
import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountDataVariable;


public abstract class TradeHostingTradeAccountDataAdaptor implements TradeHostingTradeAccountData.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTradeAccountDataVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TradeAccountFund> getNowFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTradeAccountDataVariable.serviceKey,"getNowFund",platformArgs);
    return getNowFund(oCntl, tradeAccountId);
  }

  protected abstract List<TradeAccountFund> getNowFund(TServiceCntl oCntl, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TradeAccountFundHisItem> getHisFunds(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTradeAccountDataVariable.serviceKey,"getHisFunds",platformArgs);
    return getHisFunds(oCntl, tradeAccountId, fundDateBegin, fundDateEnd);
  }

  protected abstract List<TradeAccountFundHisItem> getHisFunds(TServiceCntl oCntl, long tradeAccountId, String fundDateBegin, String fundDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TradeAccountSettlementInfo> getSettlementInfos(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTradeAccountDataVariable.serviceKey,"getSettlementInfos",platformArgs);
    return getSettlementInfos(oCntl, tradeAccountId, settlementDateBegin, settlementDateEnd);
  }

  protected abstract List<TradeAccountSettlementInfo> getSettlementInfos(TServiceCntl oCntl, long tradeAccountId, String settlementDateBegin, String settlementDateEnd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TradeAccountNetPositionSummary> getNetPositionSummaries(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTradeAccountDataVariable.serviceKey,"getNetPositionSummaries",platformArgs);
    return getNetPositionSummaries(oCntl, tradeAccountId);
  }

  protected abstract List<TradeAccountNetPositionSummary> getNetPositionSummaries(TServiceCntl oCntl, long tradeAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingTradeAccountDataAdaptor(){
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("getNowFund",new String[]{"platformArgs", "tradeAccountId"});
    methodParameterNameMap.put("getHisFunds",new String[]{"platformArgs", "tradeAccountId", "fundDateBegin", "fundDateEnd"});
    methodParameterNameMap.put("getSettlementInfos",new String[]{"platformArgs", "tradeAccountId", "settlementDateBegin", "settlementDateEnd"});
    methodParameterNameMap.put("getNetPositionSummaries",new String[]{"platformArgs", "tradeAccountId"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
