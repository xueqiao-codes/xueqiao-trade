package xueqiao.trade.hosting.position.fee.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
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


public abstract class TradeHostingPositionFeeAdaptor implements TradeHostingPositionFee.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setGeneralMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"setGeneralMarginSetting",platformArgs);
setGeneralMarginSetting(oCntl, marginSettings);
  }

  protected abstract void setGeneralMarginSetting(TServiceCntl oCntl, XQGeneralMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setGeneralCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"setGeneralCommissionSetting",platformArgs);
setGeneralCommissionSetting(oCntl, commissionSettings);
  }

  protected abstract void setGeneralCommissionSetting(TServiceCntl oCntl, XQGeneralCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"addSpecMarginSetting",platformArgs);
addSpecMarginSetting(oCntl, marginSettings);
  }

  protected abstract void addSpecMarginSetting(TServiceCntl oCntl, XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"addSpecCommissionSetting",platformArgs);
addSpecCommissionSetting(oCntl, commissionSettings);
  }

  protected abstract void addSpecCommissionSetting(TServiceCntl oCntl, XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"updateSpecMarginSetting",platformArgs);
updateSpecMarginSetting(oCntl, marginSettings);
  }

  protected abstract void updateSpecMarginSetting(TServiceCntl oCntl, XQSpecMarginSettings marginSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"updateSpecCommissionSetting",platformArgs);
updateSpecCommissionSetting(oCntl, commissionSettings);
  }

  protected abstract void updateSpecCommissionSetting(TServiceCntl oCntl, XQSpecCommissionSettings commissionSettings) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSpecMarginSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"deleteSpecMarginSetting",platformArgs);
deleteSpecMarginSetting(oCntl, subAccountId, commodityId);
  }

  protected abstract void deleteSpecMarginSetting(TServiceCntl oCntl, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSpecCommissionSetting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"deleteSpecCommissionSetting",platformArgs);
deleteSpecCommissionSetting(oCntl, subAccountId, commodityId);
  }

  protected abstract void deleteSpecCommissionSetting(TServiceCntl oCntl, long subAccountId, long commodityId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQGeneralMarginSettings queryXQGeneralMarginSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQGeneralMarginSettings",platformArgs);
    return queryXQGeneralMarginSettings(oCntl, subAccountId);
  }

  protected abstract XQGeneralMarginSettings queryXQGeneralMarginSettings(TServiceCntl oCntl, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQGeneralCommissionSettings queryXQGeneralCommissionSettings(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQGeneralCommissionSettings",platformArgs);
    return queryXQGeneralCommissionSettings(oCntl, subAccountId);
  }

  protected abstract XQGeneralCommissionSettings queryXQGeneralCommissionSettings(TServiceCntl oCntl, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQSpecMarginSettingPage queryXQSpecMarginSettingPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQSpecMarginSettingPage",platformArgs);
    return queryXQSpecMarginSettingPage(oCntl, queryOptions, pageOption);
  }

  protected abstract XQSpecMarginSettingPage queryXQSpecMarginSettingPage(TServiceCntl oCntl, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQSpecCommissionSettingPage",platformArgs);
    return queryXQSpecCommissionSettingPage(oCntl, queryOptions, pageOption);
  }

  protected abstract XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(TServiceCntl oCntl, QueryXQSpecSettingOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public UpsideContractMarginPage queryUpsideContractMarginPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryUpsideContractMarginPage",platformArgs);
    return queryUpsideContractMarginPage(oCntl, queryOptions, pageOption);
  }

  protected abstract UpsideContractMarginPage queryUpsideContractMarginPage(TServiceCntl oCntl, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public UpsideContractCommissionPage queryUpsideContractCommissionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryUpsideContractCommissionPage",platformArgs);
    return queryUpsideContractCommissionPage(oCntl, queryOptions, pageOption);
  }

  protected abstract UpsideContractCommissionPage queryUpsideContractCommissionPage(TServiceCntl oCntl, QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQContractMarginPage queryXQContractMarginPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQContractMarginPage",platformArgs);
    return queryXQContractMarginPage(oCntl, queryOptions, pageOption);
  }

  protected abstract XQContractMarginPage queryXQContractMarginPage(TServiceCntl oCntl, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public XQContractCommissionPage queryXQContractCommissionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryXQContractCommissionPage",platformArgs);
    return queryXQContractCommissionPage(oCntl, queryOptions, pageOption);
  }

  protected abstract XQContractCommissionPage queryXQContractCommissionPage(TServiceCntl oCntl, QueryXQPFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionFee queryPositionFee(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long contractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionFeeVariable.serviceKey,"queryPositionFee",platformArgs);
    return queryPositionFee(oCntl, subAccountId, contractId);
  }

  protected abstract PositionFee queryPositionFee(TServiceCntl oCntl, long subAccountId, long contractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingPositionFeeAdaptor(){
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("setGeneralMarginSetting",new String[]{"platformArgs", "marginSettings"});
    methodParameterNameMap.put("setGeneralCommissionSetting",new String[]{"platformArgs", "commissionSettings"});
    methodParameterNameMap.put("addSpecMarginSetting",new String[]{"platformArgs", "marginSettings"});
    methodParameterNameMap.put("addSpecCommissionSetting",new String[]{"platformArgs", "commissionSettings"});
    methodParameterNameMap.put("updateSpecMarginSetting",new String[]{"platformArgs", "marginSettings"});
    methodParameterNameMap.put("updateSpecCommissionSetting",new String[]{"platformArgs", "commissionSettings"});
    methodParameterNameMap.put("deleteSpecMarginSetting",new String[]{"platformArgs", "subAccountId", "commodityId"});
    methodParameterNameMap.put("deleteSpecCommissionSetting",new String[]{"platformArgs", "subAccountId", "commodityId"});
    methodParameterNameMap.put("queryXQGeneralMarginSettings",new String[]{"platformArgs", "subAccountId"});
    methodParameterNameMap.put("queryXQGeneralCommissionSettings",new String[]{"platformArgs", "subAccountId"});
    methodParameterNameMap.put("queryXQSpecMarginSettingPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQSpecCommissionSettingPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryUpsideContractMarginPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryUpsideContractCommissionPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQContractMarginPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryXQContractCommissionPage",new String[]{"platformArgs", "queryOptions", "pageOption"});
    methodParameterNameMap.put("queryPositionFee",new String[]{"platformArgs", "subAccountId", "contractId"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
