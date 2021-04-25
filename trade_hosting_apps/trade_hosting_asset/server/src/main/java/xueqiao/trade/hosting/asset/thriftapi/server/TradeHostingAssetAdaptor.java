package xueqiao.trade.hosting.asset.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange;
import xueqiao.trade.hosting.asset.thriftapi.FundChange;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingPositionFundPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingPositionVolumePage;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingPositionFundOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingPositionVolumeOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionPage;
import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAsset;
import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAssetVariable;


public abstract class TradeHostingAssetAdaptor implements TradeHostingAsset.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public HostingSledContractPositionPage getHostingSledContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getHostingSledContractPosition",platformArgs);
    return getHostingSledContractPosition(oCntl, option, pageOption);
  }

  protected abstract HostingSledContractPositionPage getHostingSledContractPosition(TServiceCntl oCntl, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingFundPage getHostingSubAccountFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getHostingSubAccountFund",platformArgs);
    return getHostingSubAccountFund(oCntl, option);
  }

  protected abstract HostingFundPage getHostingSubAccountFund(TServiceCntl oCntl, ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingSubAccountFund changeSubAccountFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"changeSubAccountFund",platformArgs);
    return changeSubAccountFund(oCntl, fundChange);
  }

  protected abstract HostingSubAccountFund changeSubAccountFund(TServiceCntl oCntl, FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingSubAccountFund setSubAccountCreditAmount(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"setSubAccountCreditAmount",platformArgs);
    return setSubAccountCreditAmount(oCntl, amountChange);
  }

  protected abstract HostingSubAccountFund setSubAccountCreditAmount(TServiceCntl oCntl, CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public SettlementPositionDetailPage getSettlementPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getSettlementPositionDetail",platformArgs);
    return getSettlementPositionDetail(oCntl, option, pageOption);
  }

  protected abstract SettlementPositionDetailPage getSettlementPositionDetail(TServiceCntl oCntl, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getHostingSubAccountMoneyRecord",platformArgs);
    return getHostingSubAccountMoneyRecord(oCntl, option, pageOption);
  }

  protected abstract HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(TServiceCntl oCntl, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public AssetTradeDetailPage getAssetPositionTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getAssetPositionTradeDetail",platformArgs);
    return getAssetPositionTradeDetail(oCntl, option, pageOption);
  }

  protected abstract AssetTradeDetailPage getAssetPositionTradeDetail(TServiceCntl oCntl, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public AssetTradeDetailPage getSettlementPositionTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getSettlementPositionTradeDetail",platformArgs);
    return getSettlementPositionTradeDetail(oCntl, option, pageOption);
  }

  protected abstract AssetTradeDetailPage getSettlementPositionTradeDetail(TServiceCntl oCntl, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingPositionVolumePage getHostingPositionVolume(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getHostingPositionVolume",platformArgs);
    return getHostingPositionVolume(oCntl, option, pageOption);
  }

  protected abstract HostingPositionVolumePage getHostingPositionVolume(TServiceCntl oCntl, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingPositionFundPage getHostingPositionFund(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getHostingPositionFund",platformArgs);
    return getHostingPositionFund(oCntl, option, pageOption);
  }

  protected abstract HostingPositionFundPage getHostingPositionFund(TServiceCntl oCntl, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingFundPage getSubAccountFundHistory(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getSubAccountFundHistory",platformArgs);
    return getSubAccountFundHistory(oCntl, option, pageOption);
  }

  protected abstract HostingFundPage getSubAccountFundHistory(TServiceCntl oCntl, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteExpiredContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"deleteExpiredContractPosition",platformArgs);
deleteExpiredContractPosition(oCntl, subAccountId, sledContractId);
  }

  protected abstract void deleteExpiredContractPosition(TServiceCntl oCntl, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public AssetTradeDetailPage getTradeAccountPositionTradeDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getTradeAccountPositionTradeDetail",platformArgs);
    return getTradeAccountPositionTradeDetail(oCntl, option, pageOption);
  }

  protected abstract AssetTradeDetailPage getTradeAccountPositionTradeDetail(TServiceCntl oCntl, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public TradeAccountPositionPage getTradeAccountPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"getTradeAccountPosition",platformArgs);
    return getTradeAccountPosition(oCntl, option, pageOption);
  }

  protected abstract TradeAccountPositionPage getTradeAccountPosition(TServiceCntl oCntl, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"assignPosition",platformArgs);
    return assignPosition(oCntl, positonAssigneds);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(TServiceCntl oCntl, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void removeAllAssetData(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingAssetVariable.serviceKey,"removeAllAssetData",platformArgs);
removeAllAssetData(oCntl);
  }

  protected abstract void removeAllAssetData(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingAssetAdaptor(){
    methodParameterNameMap.put("getHostingSledContractPosition",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getHostingSubAccountFund",new String[]{"platformArgs", "option"});
    methodParameterNameMap.put("changeSubAccountFund",new String[]{"platformArgs", "fundChange"});
    methodParameterNameMap.put("setSubAccountCreditAmount",new String[]{"platformArgs", "amountChange"});
    methodParameterNameMap.put("getSettlementPositionDetail",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getHostingSubAccountMoneyRecord",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getAssetPositionTradeDetail",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getSettlementPositionTradeDetail",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getHostingPositionVolume",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getHostingPositionFund",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getSubAccountFundHistory",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("deleteExpiredContractPosition",new String[]{"platformArgs", "subAccountId", "sledContractId"});
    methodParameterNameMap.put("getTradeAccountPositionTradeDetail",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("getTradeAccountPosition",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("assignPosition",new String[]{"platformArgs", "positonAssigneds"});
    methodParameterNameMap.put("removeAllAssetData",new String[]{"platformArgs"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
