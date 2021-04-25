package xueqiao.trade.hosting.tasknote.thriftapi.client;

import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNote;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNoteVariable;
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
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;

public class TradeHostingTaskNoteAsyncStub extends BaseStub { 
  public TradeHostingTaskNoteAsyncStub() {
    super(TradeHostingTaskNoteVariable.serviceKey);
  }
  public void send_getTaskNotePage(int routeKey, int timeout, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getTaskNotePageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), new TRequestOption());
  }

  public void send_getTaskNotePage(int routeKey, int timeout, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getTaskNotePageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), requestOption);
  }

  public long getTaskNotePage(int routeKey, int timeout, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTaskNote.getTaskNotePage_args, TradeHostingTaskNote.getTaskNotePage_result> callback) throws TException{
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
            create_getTaskNotePageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), callback);
  }

  public long add_getTaskNotePageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingTaskNote.getTaskNotePage_args, TradeHostingTaskNote.getTaskNotePage_result> callback) throws TException{
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
            create_getTaskNotePageServiceCall(routeKey, timeout, platformArgs, qryOption, pageOption), callback);
  }

  protected TServiceCall create_getTaskNotePageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTaskNoteVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTaskNote.getTaskNotePage_args request = new TradeHostingTaskNote.getTaskNotePage_args();
    request.setPlatformArgs(platformArgs);
    request.setQryOption(qryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTaskNotePage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTaskNote.getTaskNotePage_result.class);
    return serviceCall;
  }

  public void send_delTaskNote(int routeKey, int timeout, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws TException {
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
        create_delTaskNoteServiceCall(routeKey, timeout, platformArgs, noteType, noteKey), new TRequestOption());
  }

  public void send_delTaskNote(int routeKey, int timeout, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey,TRequestOption requestOption) throws TException { 
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
        create_delTaskNoteServiceCall(routeKey, timeout, platformArgs, noteType, noteKey), requestOption);
  }

  public long delTaskNote(int routeKey, int timeout, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey, IMethodCallback<TradeHostingTaskNote.delTaskNote_args, TradeHostingTaskNote.delTaskNote_result> callback) throws TException{
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
            create_delTaskNoteServiceCall(routeKey, timeout, platformArgs, noteType, noteKey), callback);
  }

  public long add_delTaskNoteCall(AsyncCallRunner runner, int routeKey, int timeout, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey, IMethodCallback<TradeHostingTaskNote.delTaskNote_args, TradeHostingTaskNote.delTaskNote_result> callback) throws TException{
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
            create_delTaskNoteServiceCall(routeKey, timeout, platformArgs, noteType, noteKey), callback);
  }

  protected TServiceCall create_delTaskNoteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingTaskNoteVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingTaskNote.delTaskNote_args request = new TradeHostingTaskNote.delTaskNote_args();
    request.setPlatformArgs(platformArgs);
    request.setNoteType(noteType);
    request.setNoteKey(noteKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("delTaskNote");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingTaskNote.delTaskNote_result.class);
    return serviceCall;
  }

}
