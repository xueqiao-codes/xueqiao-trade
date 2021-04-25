package xueqiao.trade.hosting.position.statis.client;

import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatis;
import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatisVariable;
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
import xueqiao.trade.hosting.position.statis.BatchClosedPositionReq;
import xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq;
import xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption;
import xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDetail;
import xueqiao.trade.hosting.position.statis.StatContructComposeReq;
import xueqiao.trade.hosting.position.statis.StatMergeToComposeReq;
import xueqiao.trade.hosting.position.statis.StatPositionItemPage;
import xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage;
import xueqiao.trade.hosting.position.statis.StatPositionSummaryPage;
import xueqiao.trade.hosting.position.statis.StatPositionUnitPage;

public class TradeHostingPositionStatisAsyncStub extends BaseStub { 
  public TradeHostingPositionStatisAsyncStub() {
    super(TradeHostingPositionStatisVariable.serviceKey);
  }
  public void send_clearAll(int routeKey, int timeout) throws TException {
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
        create_clearAllServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_clearAll(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_clearAllServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long clearAll(int routeKey, int timeout, IMethodCallback<TradeHostingPositionStatis.clearAll_args, TradeHostingPositionStatis.clearAll_result> callback) throws TException{
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
            create_clearAllServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_clearAllCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingPositionStatis.clearAll_args, TradeHostingPositionStatis.clearAll_result> callback) throws TException{
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
            create_clearAllServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_clearAllServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.clearAll_args request = new TradeHostingPositionStatis.clearAll_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("clearAll");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.clearAll_result.class);
    return serviceCall;
  }

  public void send_contructCompose(int routeKey, int timeout, StatContructComposeReq contructComposeReq) throws TException {
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
        create_contructComposeServiceCall(routeKey, timeout, platformArgs, contructComposeReq), new TRequestOption());
  }

  public void send_contructCompose(int routeKey, int timeout, StatContructComposeReq contructComposeReq,TRequestOption requestOption) throws TException { 
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
        create_contructComposeServiceCall(routeKey, timeout, platformArgs, contructComposeReq), requestOption);
  }

  public long contructCompose(int routeKey, int timeout, StatContructComposeReq contructComposeReq, IMethodCallback<TradeHostingPositionStatis.contructCompose_args, TradeHostingPositionStatis.contructCompose_result> callback) throws TException{
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
            create_contructComposeServiceCall(routeKey, timeout, platformArgs, contructComposeReq), callback);
  }

  public long add_contructComposeCall(AsyncCallRunner runner, int routeKey, int timeout, StatContructComposeReq contructComposeReq, IMethodCallback<TradeHostingPositionStatis.contructCompose_args, TradeHostingPositionStatis.contructCompose_result> callback) throws TException{
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
            create_contructComposeServiceCall(routeKey, timeout, platformArgs, contructComposeReq), callback);
  }

  protected TServiceCall create_contructComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StatContructComposeReq contructComposeReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.contructCompose_args request = new TradeHostingPositionStatis.contructCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setContructComposeReq(contructComposeReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("contructCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.contructCompose_result.class);
    return serviceCall;
  }

  public void send_disassembleCompose(int routeKey, int timeout, DisassembleComposePositionReq disassembleComposePositionReq) throws TException {
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
        create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, disassembleComposePositionReq), new TRequestOption());
  }

  public void send_disassembleCompose(int routeKey, int timeout, DisassembleComposePositionReq disassembleComposePositionReq,TRequestOption requestOption) throws TException { 
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
        create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, disassembleComposePositionReq), requestOption);
  }

  public long disassembleCompose(int routeKey, int timeout, DisassembleComposePositionReq disassembleComposePositionReq, IMethodCallback<TradeHostingPositionStatis.disassembleCompose_args, TradeHostingPositionStatis.disassembleCompose_result> callback) throws TException{
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
            create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, disassembleComposePositionReq), callback);
  }

  public long add_disassembleComposeCall(AsyncCallRunner runner, int routeKey, int timeout, DisassembleComposePositionReq disassembleComposePositionReq, IMethodCallback<TradeHostingPositionStatis.disassembleCompose_args, TradeHostingPositionStatis.disassembleCompose_result> callback) throws TException{
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
            create_disassembleComposeServiceCall(routeKey, timeout, platformArgs, disassembleComposePositionReq), callback);
  }

  protected TServiceCall create_disassembleComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DisassembleComposePositionReq disassembleComposePositionReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.disassembleCompose_args request = new TradeHostingPositionStatis.disassembleCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setDisassembleComposePositionReq(disassembleComposePositionReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("disassembleCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.disassembleCompose_result.class);
    return serviceCall;
  }

  public void send_batchClosePosition(int routeKey, int timeout, BatchClosedPositionReq batchClosedPositionReq) throws TException {
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
        create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, batchClosedPositionReq), new TRequestOption());
  }

  public void send_batchClosePosition(int routeKey, int timeout, BatchClosedPositionReq batchClosedPositionReq,TRequestOption requestOption) throws TException { 
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
        create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, batchClosedPositionReq), requestOption);
  }

  public long batchClosePosition(int routeKey, int timeout, BatchClosedPositionReq batchClosedPositionReq, IMethodCallback<TradeHostingPositionStatis.batchClosePosition_args, TradeHostingPositionStatis.batchClosePosition_result> callback) throws TException{
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
            create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, batchClosedPositionReq), callback);
  }

  public long add_batchClosePositionCall(AsyncCallRunner runner, int routeKey, int timeout, BatchClosedPositionReq batchClosedPositionReq, IMethodCallback<TradeHostingPositionStatis.batchClosePosition_args, TradeHostingPositionStatis.batchClosePosition_result> callback) throws TException{
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
            create_batchClosePositionServiceCall(routeKey, timeout, platformArgs, batchClosedPositionReq), callback);
  }

  protected TServiceCall create_batchClosePositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, BatchClosedPositionReq batchClosedPositionReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.batchClosePosition_args request = new TradeHostingPositionStatis.batchClosePosition_args();
    request.setPlatformArgs(platformArgs);
    request.setBatchClosedPositionReq(batchClosedPositionReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("batchClosePosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.batchClosePosition_result.class);
    return serviceCall;
  }

  public void send_recoverClosedPosition(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws TException {
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
        create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), new TRequestOption());
  }

  public void send_recoverClosedPosition(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TRequestOption requestOption) throws TException { 
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
        create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), requestOption);
  }

  public long recoverClosedPosition(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingPositionStatis.recoverClosedPosition_args, TradeHostingPositionStatis.recoverClosedPosition_result> callback) throws TException{
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
            create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), callback);
  }

  public long add_recoverClosedPositionCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingPositionStatis.recoverClosedPosition_args, TradeHostingPositionStatis.recoverClosedPosition_result> callback) throws TException{
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
            create_recoverClosedPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), callback);
  }

  protected TServiceCall create_recoverClosedPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.recoverClosedPosition_args request = new TradeHostingPositionStatis.recoverClosedPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setTargetKey(targetKey);
    request.setTargetType(targetType);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("recoverClosedPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.recoverClosedPosition_result.class);
    return serviceCall;
  }

  public void send_assignPosition(int routeKey, int timeout, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned) throws TException {
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, positionAssigned), new TRequestOption());
  }

  public void send_assignPosition(int routeKey, int timeout, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned,TRequestOption requestOption) throws TException { 
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, positionAssigned), requestOption);
  }

  public long assignPosition(int routeKey, int timeout, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned, IMethodCallback<TradeHostingPositionStatis.assignPosition_args, TradeHostingPositionStatis.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, positionAssigned), callback);
  }

  public long add_assignPositionCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned, IMethodCallback<TradeHostingPositionStatis.assignPosition_args, TradeHostingPositionStatis.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, positionAssigned), callback);
  }

  protected TServiceCall create_assignPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.assignPosition_args request = new TradeHostingPositionStatis.assignPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setPositionAssigned(positionAssigned);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("assignPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.assignPosition_result.class);
    return serviceCall;
  }

  public void send_mergeToCompose(int routeKey, int timeout, StatMergeToComposeReq mergeToComposeReq) throws TException {
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
        create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, mergeToComposeReq), new TRequestOption());
  }

  public void send_mergeToCompose(int routeKey, int timeout, StatMergeToComposeReq mergeToComposeReq,TRequestOption requestOption) throws TException { 
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
        create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, mergeToComposeReq), requestOption);
  }

  public long mergeToCompose(int routeKey, int timeout, StatMergeToComposeReq mergeToComposeReq, IMethodCallback<TradeHostingPositionStatis.mergeToCompose_args, TradeHostingPositionStatis.mergeToCompose_result> callback) throws TException{
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
            create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, mergeToComposeReq), callback);
  }

  public long add_mergeToComposeCall(AsyncCallRunner runner, int routeKey, int timeout, StatMergeToComposeReq mergeToComposeReq, IMethodCallback<TradeHostingPositionStatis.mergeToCompose_args, TradeHostingPositionStatis.mergeToCompose_result> callback) throws TException{
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
            create_mergeToComposeServiceCall(routeKey, timeout, platformArgs, mergeToComposeReq), callback);
  }

  protected TServiceCall create_mergeToComposeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StatMergeToComposeReq mergeToComposeReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.mergeToCompose_args request = new TradeHostingPositionStatis.mergeToCompose_args();
    request.setPlatformArgs(platformArgs);
    request.setMergeToComposeReq(mergeToComposeReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("mergeToCompose");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.mergeToCompose_result.class);
    return serviceCall;
  }

  public void send_deleteExpiredStatContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId) throws TException {
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
        create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), new TRequestOption());
  }

  public void send_deleteExpiredStatContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId,TRequestOption requestOption) throws TException { 
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
        create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), requestOption);
  }

  public long deleteExpiredStatContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId, IMethodCallback<TradeHostingPositionStatis.deleteExpiredStatContractPosition_args, TradeHostingPositionStatis.deleteExpiredStatContractPosition_result> callback) throws TException{
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
            create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), callback);
  }

  public long add_deleteExpiredStatContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, long sledContractId, IMethodCallback<TradeHostingPositionStatis.deleteExpiredStatContractPosition_args, TradeHostingPositionStatis.deleteExpiredStatContractPosition_result> callback) throws TException{
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
            create_deleteExpiredStatContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), callback);
  }

  protected TServiceCall create_deleteExpiredStatContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long sledContractId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.deleteExpiredStatContractPosition_args request = new TradeHostingPositionStatis.deleteExpiredStatContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setSledContractId(sledContractId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteExpiredStatContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.deleteExpiredStatContractPosition_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionSummaryPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionSummaryPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionSummaryPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionSummaryPage_args, TradeHostingPositionStatis.queryStatPositionSummaryPage_result> callback) throws TException{
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
            create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionSummaryPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionSummaryPage_args, TradeHostingPositionStatis.queryStatPositionSummaryPage_result> callback) throws TException{
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
            create_queryStatPositionSummaryPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionSummaryPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryStatPositionSummaryPage_args request = new TradeHostingPositionStatis.queryStatPositionSummaryPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionSummaryPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryStatPositionSummaryPage_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionItemPage(int routeKey, int timeout, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionItemPage(int routeKey, int timeout, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionItemPage(int routeKey, int timeout, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionItemPage_args, TradeHostingPositionStatis.queryStatPositionItemPage_result> callback) throws TException{
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
            create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionItemPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionItemPage_args, TradeHostingPositionStatis.queryStatPositionItemPage_result> callback) throws TException{
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
            create_queryStatPositionItemPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionItemPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryStatPositionItemPage_args request = new TradeHostingPositionStatis.queryStatPositionItemPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionItemPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryStatPositionItemPage_result.class);
    return serviceCall;
  }

  public void send_queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws TException {
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
        create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), new TRequestOption());
  }

  public void send_queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TRequestOption requestOption) throws TException { 
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
        create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), requestOption);
  }

  public long queryCurrentDayStatClosedPositionPage(int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_args, TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_result> callback) throws TException{
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
            create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), callback);
  }

  public long add_queryCurrentDayStatClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType, IMethodCallback<TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_args, TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_result> callback) throws TException{
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
            create_queryCurrentDayStatClosedPositionPageServiceCall(routeKey, timeout, platformArgs, subAccountId, targetKey, targetType), callback);
  }

  protected TServiceCall create_queryCurrentDayStatClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_args request = new TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setTargetKey(targetKey);
    request.setTargetType(targetType);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCurrentDayStatClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryCurrentDayStatClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryStatClosedPositionDetail(int routeKey, int timeout, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatClosedPositionDetail(int routeKey, int timeout, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryStatClosedPositionDetail(int routeKey, int timeout, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatClosedPositionDetail_args, TradeHostingPositionStatis.queryStatClosedPositionDetail_result> callback) throws TException{
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
            create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryStatClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatClosedPositionDetail_args, TradeHostingPositionStatis.queryStatClosedPositionDetail_result> callback) throws TException{
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
            create_queryStatClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryStatClosedPositionDetail_args request = new TradeHostingPositionStatis.queryStatClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryStatClosedPositionDetail_result.class);
    return serviceCall;
  }

  public void send_queryArchivedClosedPositionPage(int routeKey, int timeout, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryArchivedClosedPositionPage(int routeKey, int timeout, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryArchivedClosedPositionPage(int routeKey, int timeout, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryArchivedClosedPositionPage_args, TradeHostingPositionStatis.queryArchivedClosedPositionPage_result> callback) throws TException{
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
            create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryArchivedClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryArchivedClosedPositionPage_args, TradeHostingPositionStatis.queryArchivedClosedPositionPage_result> callback) throws TException{
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
            create_queryArchivedClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryArchivedClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryArchivedClosedPositionPage_args request = new TradeHostingPositionStatis.queryArchivedClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryArchivedClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryArchivedClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryArchivedClosedPositionDetail(int routeKey, int timeout, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryArchivedClosedPositionDetail(int routeKey, int timeout, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryArchivedClosedPositionDetail(int routeKey, int timeout, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryArchivedClosedPositionDetail_args, TradeHostingPositionStatis.queryArchivedClosedPositionDetail_result> callback) throws TException{
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
            create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryArchivedClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryArchivedClosedPositionDetail_args, TradeHostingPositionStatis.queryArchivedClosedPositionDetail_result> callback) throws TException{
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
            create_queryArchivedClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryArchivedClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryArchivedClosedPositionDetail_args request = new TradeHostingPositionStatis.queryArchivedClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryArchivedClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryArchivedClosedPositionDetail_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionSummaryExPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionSummaryExPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionSummaryExPage(int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionSummaryExPage_args, TradeHostingPositionStatis.queryStatPositionSummaryExPage_result> callback) throws TException{
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
            create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionSummaryExPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionSummaryExPage_args, TradeHostingPositionStatis.queryStatPositionSummaryExPage_result> callback) throws TException{
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
            create_queryStatPositionSummaryExPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionSummaryExPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryStatPositionSummaryExPage_args request = new TradeHostingPositionStatis.queryStatPositionSummaryExPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionSummaryExPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryStatPositionSummaryExPage_result.class);
    return serviceCall;
  }

  public void send_queryStatPositionUnitPage(int routeKey, int timeout, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryStatPositionUnitPage(int routeKey, int timeout, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryStatPositionUnitPage(int routeKey, int timeout, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionUnitPage_args, TradeHostingPositionStatis.queryStatPositionUnitPage_result> callback) throws TException{
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
            create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryStatPositionUnitPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryStatPositionUnitPage_args, TradeHostingPositionStatis.queryStatPositionUnitPage_result> callback) throws TException{
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
            create_queryStatPositionUnitPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryStatPositionUnitPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryStatPositionUnitPage_args request = new TradeHostingPositionStatis.queryStatPositionUnitPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStatPositionUnitPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryStatPositionUnitPage_result.class);
    return serviceCall;
  }

  public void send_queryHistoryClosedPositionPage(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryHistoryClosedPositionPage(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryHistoryClosedPositionPage(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryHistoryClosedPositionPage_args, TradeHostingPositionStatis.queryHistoryClosedPositionPage_result> callback) throws TException{
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
            create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryHistoryClosedPositionPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryHistoryClosedPositionPage_args, TradeHostingPositionStatis.queryHistoryClosedPositionPage_result> callback) throws TException{
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
            create_queryHistoryClosedPositionPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryHistoryClosedPositionPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryHistoryClosedPositionPage_args request = new TradeHostingPositionStatis.queryHistoryClosedPositionPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryHistoryClosedPositionPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryHistoryClosedPositionPage_result.class);
    return serviceCall;
  }

  public void send_queryHistoryClosedPositionDetail(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryHistoryClosedPositionDetail(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryHistoryClosedPositionDetail(int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryHistoryClosedPositionDetail_args, TradeHostingPositionStatis.queryHistoryClosedPositionDetail_result> callback) throws TException{
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
            create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryHistoryClosedPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingPositionStatis.queryHistoryClosedPositionDetail_args, TradeHostingPositionStatis.queryHistoryClosedPositionDetail_result> callback) throws TException{
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
            create_queryHistoryClosedPositionDetailServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryHistoryClosedPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingPositionStatisVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingPositionStatis.queryHistoryClosedPositionDetail_args request = new TradeHostingPositionStatis.queryHistoryClosedPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryHistoryClosedPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingPositionStatis.queryHistoryClosedPositionDetail_result.class);
    return serviceCall;
  }

}
