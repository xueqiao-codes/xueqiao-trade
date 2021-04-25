package xueqiao.trade.hosting.risk.manager.thriftapi.client;

import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManager;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManagerVariable;
import org.apache.thrift.TException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.client.BaseStub;
import java.util.List;
import java.util.Map;
import java.util.Set;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;

public class TradeHostingRiskManagerAsyncStub extends BaseStub { 
  public TradeHostingRiskManagerAsyncStub() {
    super(TradeHostingRiskManagerVariable.serviceKey);
  }
  public void send_getAllSupportedItems(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getAllSupportedItems(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getAllSupportedItems(int routeKey, int timeout, IMethodCallback<TradeHostingRiskManager.getAllSupportedItems_args, TradeHostingRiskManager.getAllSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getAllSupportedItemsCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingRiskManager.getAllSupportedItems_args, TradeHostingRiskManager.getAllSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getAllSupportedItemsServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getAllSupportedItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.getAllSupportedItems_args request = new TradeHostingRiskManager.getAllSupportedItems_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAllSupportedItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.getAllSupportedItems_result.class);
    return serviceCall;
  }

  public void send_getRiskRuleJointVersion(int routeKey, int timeout, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, subAccountId), new TRequestOption());
  }

  public void send_getRiskRuleJointVersion(int routeKey, int timeout, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, subAccountId), requestOption);
  }

  public long getRiskRuleJointVersion(int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskRuleJointVersion_args, TradeHostingRiskManager.getRiskRuleJointVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  public long add_getRiskRuleJointVersionCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskRuleJointVersion_args, TradeHostingRiskManager.getRiskRuleJointVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getRiskRuleJointVersionServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  protected TServiceCall create_getRiskRuleJointVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.getRiskRuleJointVersion_args request = new TradeHostingRiskManager.getRiskRuleJointVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskRuleJointVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.getRiskRuleJointVersion_result.class);
    return serviceCall;
  }

  public void send_getRiskRuleJoint(int routeKey, int timeout, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, subAccountId), new TRequestOption());
  }

  public void send_getRiskRuleJoint(int routeKey, int timeout, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, subAccountId), requestOption);
  }

  public long getRiskRuleJoint(int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskRuleJoint_args, TradeHostingRiskManager.getRiskRuleJoint_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  public long add_getRiskRuleJointCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskRuleJoint_args, TradeHostingRiskManager.getRiskRuleJoint_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getRiskRuleJointServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  protected TServiceCall create_getRiskRuleJointServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.getRiskRuleJoint_args request = new TradeHostingRiskManager.getRiskRuleJoint_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskRuleJoint");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.getRiskRuleJoint_result.class);
    return serviceCall;
  }

  public void send_batchSetSupportedItems(int routeKey, int timeout, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, openedItemIds, closedItemIds), new TRequestOption());
  }

  public void send_batchSetSupportedItems(int routeKey, int timeout, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, openedItemIds, closedItemIds), requestOption);
  }

  public long batchSetSupportedItems(int routeKey, int timeout, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds, IMethodCallback<TradeHostingRiskManager.batchSetSupportedItems_args, TradeHostingRiskManager.batchSetSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, openedItemIds, closedItemIds), callback);
  }

  public long add_batchSetSupportedItemsCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds, IMethodCallback<TradeHostingRiskManager.batchSetSupportedItems_args, TradeHostingRiskManager.batchSetSupportedItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_batchSetSupportedItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, openedItemIds, closedItemIds), callback);
  }

  protected TServiceCall create_batchSetSupportedItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Set<String> openedItemIds, Set<String> closedItemIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.batchSetSupportedItems_args request = new TradeHostingRiskManager.batchSetSupportedItems_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setOpenedItemIds(openedItemIds);
    request.setClosedItemIds(closedItemIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetSupportedItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.batchSetSupportedItems_result.class);
    return serviceCall;
  }

  public void send_batchSetTradedCommodityItems(int routeKey, int timeout, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, enabledCommodityIds, disabledCommodityIds), new TRequestOption());
  }

  public void send_batchSetTradedCommodityItems(int routeKey, int timeout, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, enabledCommodityIds, disabledCommodityIds), requestOption);
  }

  public long batchSetTradedCommodityItems(int routeKey, int timeout, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds, IMethodCallback<TradeHostingRiskManager.batchSetTradedCommodityItems_args, TradeHostingRiskManager.batchSetTradedCommodityItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, enabledCommodityIds, disabledCommodityIds), callback);
  }

  public long add_batchSetTradedCommodityItemsCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds, IMethodCallback<TradeHostingRiskManager.batchSetTradedCommodityItems_args, TradeHostingRiskManager.batchSetTradedCommodityItems_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_batchSetTradedCommodityItemsServiceCall(routeKey, timeout, platformArgs, subAccountId, version, enabledCommodityIds, disabledCommodityIds), callback);
  }

  protected TServiceCall create_batchSetTradedCommodityItemsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Set<Long> enabledCommodityIds, Set<Long> disabledCommodityIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.batchSetTradedCommodityItems_args request = new TradeHostingRiskManager.batchSetTradedCommodityItems_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setEnabledCommodityIds(enabledCommodityIds);
    request.setDisabledCommodityIds(disabledCommodityIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetTradedCommodityItems");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.batchSetTradedCommodityItems_result.class);
    return serviceCall;
  }

  public void send_batchSetGlobalRules(int routeKey, int timeout, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, ruleItems), new TRequestOption());
  }

  public void send_batchSetGlobalRules(int routeKey, int timeout, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, ruleItems), requestOption);
  }

  public long batchSetGlobalRules(int routeKey, int timeout, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems, IMethodCallback<TradeHostingRiskManager.batchSetGlobalRules_args, TradeHostingRiskManager.batchSetGlobalRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, ruleItems), callback);
  }

  public long add_batchSetGlobalRulesCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems, IMethodCallback<TradeHostingRiskManager.batchSetGlobalRules_args, TradeHostingRiskManager.batchSetGlobalRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_batchSetGlobalRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, ruleItems), callback);
  }

  protected TServiceCall create_batchSetGlobalRulesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Map<String,HostingRiskRuleItem> ruleItems){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.batchSetGlobalRules_args request = new TradeHostingRiskManager.batchSetGlobalRules_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRuleItems(ruleItems);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetGlobalRules");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.batchSetGlobalRules_result.class);
    return serviceCall;
  }

  public void send_batchSetCommodityRules(int routeKey, int timeout, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, rules), new TRequestOption());
  }

  public void send_batchSetCommodityRules(int routeKey, int timeout, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, rules), requestOption);
  }

  public long batchSetCommodityRules(int routeKey, int timeout, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules, IMethodCallback<TradeHostingRiskManager.batchSetCommodityRules_args, TradeHostingRiskManager.batchSetCommodityRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, rules), callback);
  }

  public long add_batchSetCommodityRulesCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules, IMethodCallback<TradeHostingRiskManager.batchSetCommodityRules_args, TradeHostingRiskManager.batchSetCommodityRules_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_batchSetCommodityRulesServiceCall(routeKey, timeout, platformArgs, subAccountId, version, rules), callback);
  }

  protected TServiceCall create_batchSetCommodityRulesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, Map<Long,Map<String,HostingRiskRuleItem>> rules){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.batchSetCommodityRules_args request = new TradeHostingRiskManager.batchSetCommodityRules_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRules(rules);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchSetCommodityRules");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.batchSetCommodityRules_result.class);
    return serviceCall;
  }

  public void send_setRiskEnabled(int routeKey, int timeout, long subAccountId, int version, boolean riskEnabled) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, subAccountId, version, riskEnabled), new TRequestOption());
  }

  public void send_setRiskEnabled(int routeKey, int timeout, long subAccountId, int version, boolean riskEnabled,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, subAccountId, version, riskEnabled), requestOption);
  }

  public long setRiskEnabled(int routeKey, int timeout, long subAccountId, int version, boolean riskEnabled, IMethodCallback<TradeHostingRiskManager.setRiskEnabled_args, TradeHostingRiskManager.setRiskEnabled_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, subAccountId, version, riskEnabled), callback);
  }

  public long add_setRiskEnabledCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, int version, boolean riskEnabled, IMethodCallback<TradeHostingRiskManager.setRiskEnabled_args, TradeHostingRiskManager.setRiskEnabled_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_setRiskEnabledServiceCall(routeKey, timeout, platformArgs, subAccountId, version, riskEnabled), callback);
  }

  protected TServiceCall create_setRiskEnabledServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, int version, boolean riskEnabled){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.setRiskEnabled_args request = new TradeHostingRiskManager.setRiskEnabled_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setVersion(version);
    request.setRiskEnabled(riskEnabled);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setRiskEnabled");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.setRiskEnabled_result.class);
    return serviceCall;
  }

  public void send_getRiskFrameDataInfo(int routeKey, int timeout, long subAccountId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, subAccountId), new TRequestOption());
  }

  public void send_getRiskFrameDataInfo(int routeKey, int timeout, long subAccountId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, subAccountId), requestOption);
  }

  public long getRiskFrameDataInfo(int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskFrameDataInfo_args, TradeHostingRiskManager.getRiskFrameDataInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  public long add_getRiskFrameDataInfoCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, IMethodCallback<TradeHostingRiskManager.getRiskFrameDataInfo_args, TradeHostingRiskManager.getRiskFrameDataInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getRiskFrameDataInfoServiceCall(routeKey, timeout, platformArgs, subAccountId), callback);
  }

  protected TServiceCall create_getRiskFrameDataInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingRiskManagerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingRiskManager.getRiskFrameDataInfo_args request = new TradeHostingRiskManager.getRiskFrameDataInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getRiskFrameDataInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingRiskManager.getRiskFrameDataInfo_result.class);
    return serviceCall;
  }

}
