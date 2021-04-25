package xueqiao.trade.hosting.asset.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
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

public class TradeHostingAssetStub extends BaseStub {

  public TradeHostingAssetStub() {
    super(TradeHostingAssetVariable.serviceKey);
  }

  public HostingSledContractPositionPage  getHostingSledContractPosition(ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSledContractPosition(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSledContractPositionPage  getHostingSledContractPosition(ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSledContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingSledContractPositionPage>(){
    @Override
    public HostingSledContractPositionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getHostingSledContractPosition(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public HostingSledContractPositionPage  getHostingSledContractPosition(int routeKey, int timeout,ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSledContractPosition(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingFundPage  getHostingSubAccountFund(ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountFund(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingFundPage  getHostingSubAccountFund(ReqHostingFundOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSubAccountFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingFundPage>(){
    @Override
    public HostingFundPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getHostingSubAccountFund(platformArgs, option);
      }
    }, invokeInfo);
  }

  public HostingFundPage  getHostingSubAccountFund(int routeKey, int timeout,ReqHostingFundOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountFund(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountFund  changeSubAccountFund(FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return changeSubAccountFund(fundChange, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountFund  changeSubAccountFund(FundChange fundChange,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("changeSubAccountFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingSubAccountFund>(){
    @Override
    public HostingSubAccountFund call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).changeSubAccountFund(platformArgs, fundChange);
      }
    }, invokeInfo);
  }

  public HostingSubAccountFund  changeSubAccountFund(int routeKey, int timeout,FundChange fundChange)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return changeSubAccountFund(fundChange, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountFund  setSubAccountCreditAmount(CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setSubAccountCreditAmount(amountChange, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountFund  setSubAccountCreditAmount(CreditAmountChange amountChange,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setSubAccountCreditAmount").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingSubAccountFund>(){
    @Override
    public HostingSubAccountFund call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).setSubAccountCreditAmount(platformArgs, amountChange);
      }
    }, invokeInfo);
  }

  public HostingSubAccountFund  setSubAccountCreditAmount(int routeKey, int timeout,CreditAmountChange amountChange)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setSubAccountCreditAmount(amountChange, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public SettlementPositionDetailPage  getSettlementPositionDetail(ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementPositionDetail(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public SettlementPositionDetailPage  getSettlementPositionDetail(ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSettlementPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<SettlementPositionDetailPage>(){
    @Override
    public SettlementPositionDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getSettlementPositionDetail(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public SettlementPositionDetailPage  getSettlementPositionDetail(int routeKey, int timeout,ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementPositionDetail(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountMoneyRecord(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingSubAccountMoneyRecord").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingSubAccountMoneyRecordPage>(){
    @Override
    public HostingSubAccountMoneyRecordPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getHostingSubAccountMoneyRecord(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public HostingSubAccountMoneyRecordPage  getHostingSubAccountMoneyRecord(int routeKey, int timeout,ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingSubAccountMoneyRecord(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getAssetPositionTradeDetail(ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAssetPositionTradeDetail(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getAssetPositionTradeDetail(ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAssetPositionTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<AssetTradeDetailPage>(){
    @Override
    public AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getAssetPositionTradeDetail(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public AssetTradeDetailPage  getAssetPositionTradeDetail(int routeKey, int timeout,ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAssetPositionTradeDetail(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getSettlementPositionTradeDetail(ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementPositionTradeDetail(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getSettlementPositionTradeDetail(ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSettlementPositionTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<AssetTradeDetailPage>(){
    @Override
    public AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getSettlementPositionTradeDetail(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public AssetTradeDetailPage  getSettlementPositionTradeDetail(int routeKey, int timeout,ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSettlementPositionTradeDetail(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingPositionVolumePage  getHostingPositionVolume(ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingPositionVolume(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingPositionVolumePage  getHostingPositionVolume(ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingPositionVolume").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingPositionVolumePage>(){
    @Override
    public HostingPositionVolumePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getHostingPositionVolume(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public HostingPositionVolumePage  getHostingPositionVolume(int routeKey, int timeout,ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingPositionVolume(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingPositionFundPage  getHostingPositionFund(ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingPositionFund(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingPositionFundPage  getHostingPositionFund(ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingPositionFund").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingPositionFundPage>(){
    @Override
    public HostingPositionFundPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getHostingPositionFund(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public HostingPositionFundPage  getHostingPositionFund(int routeKey, int timeout,ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingPositionFund(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingFundPage  getSubAccountFundHistory(ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountFundHistory(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingFundPage  getSubAccountFundHistory(ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSubAccountFundHistory").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingFundPage>(){
    @Override
    public HostingFundPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getSubAccountFundHistory(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public HostingFundPage  getSubAccountFundHistory(int routeKey, int timeout,ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSubAccountFundHistory(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredContractPosition(long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredContractPosition(subAccountId, sledContractId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredContractPosition(long subAccountId, long sledContractId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteExpiredContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingAsset.Client(protocol).deleteExpiredContractPosition(platformArgs, subAccountId, sledContractId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteExpiredContractPosition(int routeKey, int timeout,long subAccountId, long sledContractId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredContractPosition(subAccountId, sledContractId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getTradeAccountPositionTradeDetail(ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPositionTradeDetail(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AssetTradeDetailPage  getTradeAccountPositionTradeDetail(ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountPositionTradeDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<AssetTradeDetailPage>(){
    @Override
    public AssetTradeDetailPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getTradeAccountPositionTradeDetail(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public AssetTradeDetailPage  getTradeAccountPositionTradeDetail(int routeKey, int timeout,ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPositionTradeDetail(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public TradeAccountPositionPage  getTradeAccountPosition(ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPosition(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public TradeAccountPositionPage  getTradeAccountPosition(ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTradeAccountPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<TradeAccountPositionPage>(){
    @Override
    public TradeAccountPositionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).getTradeAccountPosition(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public TradeAccountPositionPage  getTradeAccountPosition(int routeKey, int timeout,ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTradeAccountPosition(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(positonAssigneds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("assignPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingAsset.Client(protocol).assignPosition(platformArgs, positonAssigneds);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(int routeKey, int timeout,List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(positonAssigneds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeAllAssetData() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeAllAssetData(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeAllAssetData(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeAllAssetData").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingAsset.Client(protocol).removeAllAssetData(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeAllAssetData(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeAllAssetData(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
