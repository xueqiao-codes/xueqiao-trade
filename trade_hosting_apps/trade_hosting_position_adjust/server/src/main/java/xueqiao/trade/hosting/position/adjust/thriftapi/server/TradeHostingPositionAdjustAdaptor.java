package xueqiao.trade.hosting.position.adjust.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference;
import xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifferencePage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ManualInputPositionResp;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignedPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifferencePage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInputPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionUnassignedPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqDailyPositionDifferenceOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionDifferenceOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionManualInputOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.SettlementTimeRelateSledReqTime;
import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjust;
import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjustVariable;


public abstract class TradeHostingPositionAdjustAdaptor implements TradeHostingPositionAdjust.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public ManualInputPositionResp manualInputPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"manualInputPosition",platformArgs);
    return manualInputPosition(oCntl, positionManualInputs);
  }

  protected abstract ManualInputPositionResp manualInputPosition(TServiceCntl oCntl, List<PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionManualInputPage reqManualInputPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqManualInputPosition",platformArgs);
    return reqManualInputPosition(oCntl, option, pageOption);
  }

  protected abstract PositionManualInputPage reqManualInputPosition(TServiceCntl oCntl, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionUnassignedPage reqPositionUnassigned(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqPositionUnassigned",platformArgs);
    return reqPositionUnassigned(oCntl, option, pageOption);
  }

  protected abstract PositionUnassignedPage reqPositionUnassigned(TServiceCntl oCntl, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionAssignedPage reqPositionAssigned(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqPositionAssigned",platformArgs);
    return reqPositionAssigned(oCntl, option, pageOption);
  }

  protected abstract PositionAssignedPage reqPositionAssigned(TServiceCntl oCntl, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"assignPosition",platformArgs);
    return assignPosition(oCntl, assignOption);
  }

  protected abstract xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp assignPosition(TServiceCntl oCntl, List<PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionEditLock reqPositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqPositionEditLock",platformArgs);
    return reqPositionEditLock(oCntl, lockKey);
  }

  protected abstract PositionEditLock reqPositionEditLock(TServiceCntl oCntl, String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addPositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"addPositionEditLock",platformArgs);
addPositionEditLock(oCntl, positionEditLock);
  }

  protected abstract void addPositionEditLock(TServiceCntl oCntl, PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void removePositionEditLock(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"removePositionEditLock",platformArgs);
removePositionEditLock(oCntl, positionEditLock);
  }

  protected abstract void removePositionEditLock(TServiceCntl oCntl, PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionVerifyPage reqPositionVerify(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqPositionVerify",platformArgs);
    return reqPositionVerify(oCntl, option, pageOption);
  }

  protected abstract PositionVerifyPage reqPositionVerify(TServiceCntl oCntl, ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public PositionDifferencePage reqPositionDifference(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqPositionDifference",platformArgs);
    return reqPositionDifference(oCntl, option, pageOption);
  }

  protected abstract PositionDifferencePage reqPositionDifference(TServiceCntl oCntl, ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public SettlementTimeRelateSledReqTime reqSettlementTimeRelateSledReqTime(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long tradeAccountId, String settlementDate) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqSettlementTimeRelateSledReqTime",platformArgs);
    return reqSettlementTimeRelateSledReqTime(oCntl, tradeAccountId, settlementDate);
  }

  protected abstract SettlementTimeRelateSledReqTime reqSettlementTimeRelateSledReqTime(TServiceCntl oCntl, long tradeAccountId, String settlementDate) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public DailyPositionDifferencePage reqDailyPositionDifference(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"reqDailyPositionDifference",platformArgs);
    return reqDailyPositionDifference(oCntl, option, pageOption);
  }

  protected abstract DailyPositionDifferencePage reqDailyPositionDifference(TServiceCntl oCntl, ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDailyPositionDifferenceNote(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"updateDailyPositionDifferenceNote",platformArgs);
updateDailyPositionDifferenceNote(oCntl, difference);
  }

  protected abstract void updateDailyPositionDifferenceNote(TServiceCntl oCntl, DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionAdjustVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingPositionAdjustAdaptor(){
    methodParameterNameMap.put("manualInputPosition",new String[]{"platformArgs", "positionManualInputs"});
    methodParameterNameMap.put("reqManualInputPosition",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("reqPositionUnassigned",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("reqPositionAssigned",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("assignPosition",new String[]{"platformArgs", "assignOption"});
    methodParameterNameMap.put("reqPositionEditLock",new String[]{"platformArgs", "lockKey"});
    methodParameterNameMap.put("addPositionEditLock",new String[]{"platformArgs", "positionEditLock"});
    methodParameterNameMap.put("removePositionEditLock",new String[]{"platformArgs", "positionEditLock"});
    methodParameterNameMap.put("reqPositionVerify",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("reqPositionDifference",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("reqSettlementTimeRelateSledReqTime",new String[]{"platformArgs", "tradeAccountId", "settlementDate"});
    methodParameterNameMap.put("reqDailyPositionDifference",new String[]{"platformArgs", "option", "pageOption"});
    methodParameterNameMap.put("updateDailyPositionDifferenceNote",new String[]{"platformArgs", "difference"});
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
