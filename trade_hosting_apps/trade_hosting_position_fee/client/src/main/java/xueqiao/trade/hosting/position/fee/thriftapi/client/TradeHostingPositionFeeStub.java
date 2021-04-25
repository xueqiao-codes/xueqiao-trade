package xueqiao.trade.hosting.position.fee.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.position.fee.thriftapi.PositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.TradeHostingPositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.TradeHostingPositionFeeVariable;

public class TradeHostingPositionFeeStub extends BaseStub {

  public TradeHostingPositionFeeStub() {
    super(TradeHostingPositionFeeVariable.serviceKey);
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
      new TradeHostingPositionFee.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralMarginSetting(XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralMarginSetting(marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralMarginSetting(XQGeneralMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setGeneralMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).setGeneralMarginSetting(platformArgs, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  setGeneralMarginSetting(int routeKey, int timeout,XQGeneralMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralMarginSetting(marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralCommissionSetting(XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralCommissionSetting(commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  setGeneralCommissionSetting(XQGeneralCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setGeneralCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).setGeneralCommissionSetting(platformArgs, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  setGeneralCommissionSetting(int routeKey, int timeout,XQGeneralCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    setGeneralCommissionSetting(commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecMarginSetting(XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecMarginSetting(marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecMarginSetting(XQSpecMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).addSpecMarginSetting(platformArgs, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  addSpecMarginSetting(int routeKey, int timeout,XQSpecMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecMarginSetting(marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecCommissionSetting(XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecCommissionSetting(commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addSpecCommissionSetting(XQSpecCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).addSpecCommissionSetting(platformArgs, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  addSpecCommissionSetting(int routeKey, int timeout,XQSpecCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addSpecCommissionSetting(commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecMarginSetting(XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecMarginSetting(marginSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecMarginSetting(XQSpecMarginSettings marginSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).updateSpecMarginSetting(platformArgs, marginSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateSpecMarginSetting(int routeKey, int timeout,XQSpecMarginSettings marginSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecMarginSetting(marginSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecCommissionSetting(XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecCommissionSetting(commissionSettings, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSpecCommissionSetting(XQSpecCommissionSettings commissionSettings,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).updateSpecCommissionSetting(platformArgs, commissionSettings);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateSpecCommissionSetting(int routeKey, int timeout,XQSpecCommissionSettings commissionSettings)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSpecCommissionSetting(commissionSettings, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecMarginSetting(long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecMarginSetting(subAccountId, commodityId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecMarginSetting(long subAccountId, long commodityId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteSpecMarginSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).deleteSpecMarginSetting(platformArgs, subAccountId, commodityId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteSpecMarginSetting(int routeKey, int timeout,long subAccountId, long commodityId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecMarginSetting(subAccountId, commodityId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecCommissionSetting(long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecCommissionSetting(subAccountId, commodityId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSpecCommissionSetting(long subAccountId, long commodityId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteSpecCommissionSetting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionFee.Client(protocol).deleteSpecCommissionSetting(platformArgs, subAccountId, commodityId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteSpecCommissionSetting(int routeKey, int timeout,long subAccountId, long commodityId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSpecCommissionSetting(subAccountId, commodityId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQGeneralMarginSettings  queryXQGeneralMarginSettings(long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralMarginSettings(subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQGeneralMarginSettings  queryXQGeneralMarginSettings(long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQGeneralMarginSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQGeneralMarginSettings>(){
    @Override
    public XQGeneralMarginSettings call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQGeneralMarginSettings(platformArgs, subAccountId);
      }
    }, invokeInfo);
  }

  public XQGeneralMarginSettings  queryXQGeneralMarginSettings(int routeKey, int timeout,long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralMarginSettings(subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralCommissionSettings(subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQGeneralCommissionSettings").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQGeneralCommissionSettings>(){
    @Override
    public XQGeneralCommissionSettings call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQGeneralCommissionSettings(platformArgs, subAccountId);
      }
    }, invokeInfo);
  }

  public XQGeneralCommissionSettings  queryXQGeneralCommissionSettings(int routeKey, int timeout,long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQGeneralCommissionSettings(subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecMarginSettingPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQSpecMarginSettingPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQSpecMarginSettingPage>(){
    @Override
    public XQSpecMarginSettingPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQSpecMarginSettingPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public XQSpecMarginSettingPage  queryXQSpecMarginSettingPage(int routeKey, int timeout,QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecMarginSettingPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecCommissionSettingPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQSpecCommissionSettingPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQSpecCommissionSettingPage>(){
    @Override
    public XQSpecCommissionSettingPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQSpecCommissionSettingPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public XQSpecCommissionSettingPage  queryXQSpecCommissionSettingPage(int routeKey, int timeout,QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQSpecCommissionSettingPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public UpsideContractMarginPage  queryUpsideContractMarginPage(QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractMarginPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public UpsideContractMarginPage  queryUpsideContractMarginPage(QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUpsideContractMarginPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<UpsideContractMarginPage>(){
    @Override
    public UpsideContractMarginPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryUpsideContractMarginPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public UpsideContractMarginPage  queryUpsideContractMarginPage(int routeKey, int timeout,QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractMarginPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public UpsideContractCommissionPage  queryUpsideContractCommissionPage(QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractCommissionPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public UpsideContractCommissionPage  queryUpsideContractCommissionPage(QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUpsideContractCommissionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<UpsideContractCommissionPage>(){
    @Override
    public UpsideContractCommissionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryUpsideContractCommissionPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public UpsideContractCommissionPage  queryUpsideContractCommissionPage(int routeKey, int timeout,QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpsideContractCommissionPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQContractMarginPage  queryXQContractMarginPage(QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractMarginPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQContractMarginPage  queryXQContractMarginPage(QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQContractMarginPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQContractMarginPage>(){
    @Override
    public XQContractMarginPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQContractMarginPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public XQContractMarginPage  queryXQContractMarginPage(int routeKey, int timeout,QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractMarginPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQContractCommissionPage  queryXQContractCommissionPage(QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractCommissionPage(queryOptions, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public XQContractCommissionPage  queryXQContractCommissionPage(QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryXQContractCommissionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<XQContractCommissionPage>(){
    @Override
    public XQContractCommissionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryXQContractCommissionPage(platformArgs, queryOptions, pageOption);
      }
    }, invokeInfo);
  }

  public XQContractCommissionPage  queryXQContractCommissionPage(int routeKey, int timeout,QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryXQContractCommissionPage(queryOptions, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionFee  queryPositionFee(long subAccountId, long contractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryPositionFee(subAccountId, contractId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionFee  queryPositionFee(long subAccountId, long contractId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryPositionFee").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionFee>(){
    @Override
    public PositionFee call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionFee.Client(protocol).queryPositionFee(platformArgs, subAccountId, contractId);
      }
    }, invokeInfo);
  }

  public PositionFee  queryPositionFee(int routeKey, int timeout,long subAccountId, long contractId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryPositionFee(subAccountId, contractId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
