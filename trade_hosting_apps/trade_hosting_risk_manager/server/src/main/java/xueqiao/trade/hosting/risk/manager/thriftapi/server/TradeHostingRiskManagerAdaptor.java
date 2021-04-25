package xueqiao.trade.hosting.risk.manager.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManager;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManagerVariable;


public abstract class TradeHostingRiskManagerAdaptor implements TradeHostingRiskManager.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public List<HostingRiskSupportedItem> getAllSupportedItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"getAllSupportedItems",platformArgs);
    return getAllSupportedItems(oCntl);
  }

  protected abstract List<HostingRiskSupportedItem> getAllSupportedItems(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getRiskRuleJointVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"getRiskRuleJointVersion",platformArgs);
    return getRiskRuleJointVersion(oCntl, subAccountId);
  }

  protected abstract int getRiskRuleJointVersion(TServiceCntl oCntl, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint getRiskRuleJoint(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"getRiskRuleJoint",platformArgs);
    return getRiskRuleJoint(oCntl, subAccountId);
  }

  protected abstract HostingRiskRuleJoint getRiskRuleJoint(TServiceCntl oCntl, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint batchSetSupportedItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"batchSetSupportedItems",platformArgs);
    return batchSetSupportedItems(oCntl, subAccountId, version, openedItemIds, closedItemIds);
  }

  protected abstract HostingRiskRuleJoint batchSetSupportedItems(TServiceCntl oCntl, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint batchSetTradedCommodityItems(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"batchSetTradedCommodityItems",platformArgs);
    return batchSetTradedCommodityItems(oCntl, subAccountId, version, enabledCommodityIds, disabledCommodityIds);
  }

  protected abstract HostingRiskRuleJoint batchSetTradedCommodityItems(TServiceCntl oCntl, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint batchSetGlobalRules(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"batchSetGlobalRules",platformArgs);
    return batchSetGlobalRules(oCntl, subAccountId, version, ruleItems);
  }

  protected abstract HostingRiskRuleJoint batchSetGlobalRules(TServiceCntl oCntl, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint batchSetCommodityRules(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"batchSetCommodityRules",platformArgs);
    return batchSetCommodityRules(oCntl, subAccountId, version, rules);
  }

  protected abstract HostingRiskRuleJoint batchSetCommodityRules(TServiceCntl oCntl, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskRuleJoint setRiskEnabled(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"setRiskEnabled",platformArgs);
    return setRiskEnabled(oCntl, subAccountId, version, riskEnabled);
  }

  protected abstract HostingRiskRuleJoint setRiskEnabled(TServiceCntl oCntl, long subAccountId, int version, boolean riskEnabled) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public HostingRiskFrameDataInfo getRiskFrameDataInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingRiskManagerVariable.serviceKey,"getRiskFrameDataInfo",platformArgs);
    return getRiskFrameDataInfo(oCntl, subAccountId);
  }

  protected abstract HostingRiskFrameDataInfo getRiskFrameDataInfo(TServiceCntl oCntl, long subAccountId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingRiskManagerAdaptor(){
    methodParameterNameMap.put("getAllSupportedItems",new String[]{"platformArgs"});
    methodParameterNameMap.put("getRiskRuleJointVersion",new String[]{"platformArgs", "subAccountId"});
    methodParameterNameMap.put("getRiskRuleJoint",new String[]{"platformArgs", "subAccountId"});
    methodParameterNameMap.put("batchSetSupportedItems",new String[]{"platformArgs", "subAccountId", "version", "openedItemIds", "closedItemIds"});
    methodParameterNameMap.put("batchSetTradedCommodityItems",new String[]{"platformArgs", "subAccountId", "version", "enabledCommodityIds", "disabledCommodityIds"});
    methodParameterNameMap.put("batchSetGlobalRules",new String[]{"platformArgs", "subAccountId", "version", "ruleItems"});
    methodParameterNameMap.put("batchSetCommodityRules",new String[]{"platformArgs", "subAccountId", "version", "rules"});
    methodParameterNameMap.put("setRiskEnabled",new String[]{"platformArgs", "subAccountId", "version", "riskEnabled"});
    methodParameterNameMap.put("getRiskFrameDataInfo",new String[]{"platformArgs", "subAccountId"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
