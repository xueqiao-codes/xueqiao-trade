package xueqiao.trade.hosting.position.adjust.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
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

public class TradeHostingPositionAdjustStub extends BaseStub {

  public TradeHostingPositionAdjustStub() {
    super(TradeHostingPositionAdjustVariable.serviceKey);
  }

  public ManualInputPositionResp  manualInputPosition(List<PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return manualInputPosition(positionManualInputs, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ManualInputPositionResp  manualInputPosition(List<PositionManualInput> positionManualInputs,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("manualInputPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ManualInputPositionResp>(){
    @Override
    public ManualInputPositionResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).manualInputPosition(platformArgs, positionManualInputs);
      }
    }, invokeInfo);
  }

  public ManualInputPositionResp  manualInputPosition(int routeKey, int timeout,List<PositionManualInput> positionManualInputs)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return manualInputPosition(positionManualInputs, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionManualInputPage  reqManualInputPosition(ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqManualInputPosition(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionManualInputPage  reqManualInputPosition(ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqManualInputPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionManualInputPage>(){
    @Override
    public PositionManualInputPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqManualInputPosition(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public PositionManualInputPage  reqManualInputPosition(int routeKey, int timeout,ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqManualInputPosition(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionUnassignedPage  reqPositionUnassigned(ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionUnassigned(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionUnassignedPage  reqPositionUnassigned(ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionUnassigned").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionUnassignedPage>(){
    @Override
    public PositionUnassignedPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqPositionUnassigned(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public PositionUnassignedPage  reqPositionUnassigned(int routeKey, int timeout,ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionUnassigned(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionAssignedPage  reqPositionAssigned(ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionAssigned(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionAssignedPage  reqPositionAssigned(ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionAssigned").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionAssignedPage>(){
    @Override
    public PositionAssignedPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqPositionAssigned(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public PositionAssignedPage  reqPositionAssigned(int routeKey, int timeout,ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionAssigned(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(List<PositionAssignOption> assignOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(assignOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(List<PositionAssignOption> assignOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("assignPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp>(){
    @Override
    public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).assignPosition(platformArgs, assignOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp  assignPosition(int routeKey, int timeout,List<PositionAssignOption> assignOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return assignPosition(assignOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionEditLock  reqPositionEditLock(String lockKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionEditLock(lockKey, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionEditLock  reqPositionEditLock(String lockKey,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionEditLock>(){
    @Override
    public PositionEditLock call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqPositionEditLock(platformArgs, lockKey);
      }
    }, invokeInfo);
  }

  public PositionEditLock  reqPositionEditLock(int routeKey, int timeout,String lockKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionEditLock(lockKey, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addPositionEditLock(PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addPositionEditLock(positionEditLock, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addPositionEditLock(PositionEditLock positionEditLock,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addPositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionAdjust.Client(protocol).addPositionEditLock(platformArgs, positionEditLock);
      return null;
      }
    }, invokeInfo);
  }

  public void  addPositionEditLock(int routeKey, int timeout,PositionEditLock positionEditLock)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addPositionEditLock(positionEditLock, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removePositionEditLock(PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removePositionEditLock(positionEditLock, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removePositionEditLock(PositionEditLock positionEditLock,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removePositionEditLock").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionAdjust.Client(protocol).removePositionEditLock(platformArgs, positionEditLock);
      return null;
      }
    }, invokeInfo);
  }

  public void  removePositionEditLock(int routeKey, int timeout,PositionEditLock positionEditLock)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removePositionEditLock(positionEditLock, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionVerifyPage  reqPositionVerify(ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionVerify(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionVerifyPage  reqPositionVerify(ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionVerify").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionVerifyPage>(){
    @Override
    public PositionVerifyPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqPositionVerify(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public PositionVerifyPage  reqPositionVerify(int routeKey, int timeout,ReqPositionVerifyOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionVerify(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionDifferencePage  reqPositionDifference(ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionDifference(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public PositionDifferencePage  reqPositionDifference(ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqPositionDifference").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<PositionDifferencePage>(){
    @Override
    public PositionDifferencePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqPositionDifference(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public PositionDifferencePage  reqPositionDifference(int routeKey, int timeout,ReqPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqPositionDifference(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public SettlementTimeRelateSledReqTime  reqSettlementTimeRelateSledReqTime(long tradeAccountId, String settlementDate) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSettlementTimeRelateSledReqTime(tradeAccountId, settlementDate, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public SettlementTimeRelateSledReqTime  reqSettlementTimeRelateSledReqTime(long tradeAccountId, String settlementDate,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqSettlementTimeRelateSledReqTime").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<SettlementTimeRelateSledReqTime>(){
    @Override
    public SettlementTimeRelateSledReqTime call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqSettlementTimeRelateSledReqTime(platformArgs, tradeAccountId, settlementDate);
      }
    }, invokeInfo);
  }

  public SettlementTimeRelateSledReqTime  reqSettlementTimeRelateSledReqTime(int routeKey, int timeout,long tradeAccountId, String settlementDate)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqSettlementTimeRelateSledReqTime(tradeAccountId, settlementDate, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public DailyPositionDifferencePage  reqDailyPositionDifference(ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDailyPositionDifference(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public DailyPositionDifferencePage  reqDailyPositionDifference(ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqDailyPositionDifference").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<DailyPositionDifferencePage>(){
    @Override
    public DailyPositionDifferencePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionAdjust.Client(protocol).reqDailyPositionDifference(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public DailyPositionDifferencePage  reqDailyPositionDifference(int routeKey, int timeout,ReqDailyPositionDifferenceOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqDailyPositionDifference(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateDailyPositionDifferenceNote(DailyPositionDifference difference) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateDailyPositionDifferenceNote(difference, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateDailyPositionDifferenceNote(DailyPositionDifference difference,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateDailyPositionDifferenceNote").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionAdjust.Client(protocol).updateDailyPositionDifferenceNote(platformArgs, difference);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateDailyPositionDifferenceNote(int routeKey, int timeout,DailyPositionDifference difference)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateDailyPositionDifferenceNote(difference, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
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
      new TradeHostingPositionAdjust.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
