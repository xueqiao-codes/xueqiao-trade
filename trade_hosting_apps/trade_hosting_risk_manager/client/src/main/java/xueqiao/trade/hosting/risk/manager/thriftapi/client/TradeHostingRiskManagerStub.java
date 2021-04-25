package xueqiao.trade.hosting.risk.manager.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManager;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManagerVariable;

public class TradeHostingRiskManagerStub extends BaseStub {

  public TradeHostingRiskManagerStub() {
    super(TradeHostingRiskManagerVariable.serviceKey);
  }

  public List<HostingRiskSupportedItem>  getAllSupportedItems() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSupportedItems(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<HostingRiskSupportedItem>  getAllSupportedItems(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAllSupportedItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<HostingRiskSupportedItem>>(){
    @Override
    public List<HostingRiskSupportedItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).getAllSupportedItems(platformArgs);
      }
    }, invokeInfo);
  }

  public List<HostingRiskSupportedItem>  getAllSupportedItems(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSupportedItems(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getRiskRuleJointVersion(long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJointVersion(subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getRiskRuleJointVersion(long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskRuleJointVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).getRiskRuleJointVersion(platformArgs, subAccountId);
      }
    }, invokeInfo);
  }

  public int  getRiskRuleJointVersion(int routeKey, int timeout,long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJointVersion(subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  getRiskRuleJoint(long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJoint(subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  getRiskRuleJoint(long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskRuleJoint").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).getRiskRuleJoint(platformArgs, subAccountId);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  getRiskRuleJoint(int routeKey, int timeout,long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskRuleJoint(subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetSupportedItems(long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetSupportedItems(subAccountId, version, openedItemIds, closedItemIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetSupportedItems(long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetSupportedItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).batchSetSupportedItems(platformArgs, subAccountId, version, openedItemIds, closedItemIds);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  batchSetSupportedItems(int routeKey, int timeout,long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetSupportedItems(subAccountId, version, openedItemIds, closedItemIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetTradedCommodityItems(long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetTradedCommodityItems(subAccountId, version, enabledCommodityIds, disabledCommodityIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetTradedCommodityItems(long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetTradedCommodityItems").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).batchSetTradedCommodityItems(platformArgs, subAccountId, version, enabledCommodityIds, disabledCommodityIds);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  batchSetTradedCommodityItems(int routeKey, int timeout,long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetTradedCommodityItems(subAccountId, version, enabledCommodityIds, disabledCommodityIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetGlobalRules(long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetGlobalRules(subAccountId, version, ruleItems, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetGlobalRules(long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetGlobalRules").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).batchSetGlobalRules(platformArgs, subAccountId, version, ruleItems);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  batchSetGlobalRules(int routeKey, int timeout,long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetGlobalRules(subAccountId, version, ruleItems, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetCommodityRules(long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetCommodityRules(subAccountId, version, rules, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  batchSetCommodityRules(long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchSetCommodityRules").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).batchSetCommodityRules(platformArgs, subAccountId, version, rules);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  batchSetCommodityRules(int routeKey, int timeout,long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return batchSetCommodityRules(subAccountId, version, rules, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  setRiskEnabled(long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setRiskEnabled(subAccountId, version, riskEnabled, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskRuleJoint  setRiskEnabled(long subAccountId, int version, boolean riskEnabled,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("setRiskEnabled").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskRuleJoint>(){
    @Override
    public HostingRiskRuleJoint call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).setRiskEnabled(platformArgs, subAccountId, version, riskEnabled);
      }
    }, invokeInfo);
  }

  public HostingRiskRuleJoint  setRiskEnabled(int routeKey, int timeout,long subAccountId, int version, boolean riskEnabled)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return setRiskEnabled(subAccountId, version, riskEnabled, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskFrameDataInfo  getRiskFrameDataInfo(long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskFrameDataInfo(subAccountId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingRiskFrameDataInfo  getRiskFrameDataInfo(long subAccountId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getRiskFrameDataInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingRiskFrameDataInfo>(){
    @Override
    public HostingRiskFrameDataInfo call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingRiskManager.Client(protocol).getRiskFrameDataInfo(platformArgs, subAccountId);
      }
    }, invokeInfo);
  }

  public HostingRiskFrameDataInfo  getRiskFrameDataInfo(int routeKey, int timeout,long subAccountId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getRiskFrameDataInfo(subAccountId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
