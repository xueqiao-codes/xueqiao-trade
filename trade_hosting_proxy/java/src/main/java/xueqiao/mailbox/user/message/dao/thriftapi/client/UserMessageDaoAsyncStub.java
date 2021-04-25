package xueqiao.mailbox.user.message.dao.thriftapi.client;

import xueqiao.mailbox.user.message.dao.thriftapi.UserMessageDao;
import xueqiao.mailbox.user.message.dao.thriftapi.UserMessageDaoVariable;
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

public class UserMessageDaoAsyncStub extends BaseStub { 
  public UserMessageDaoAsyncStub() {
    super(UserMessageDaoVariable.serviceKey);
  }
  public void send_addUserMessage(int routeKey, int timeout, List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage) throws TException {
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
        create_addUserMessageServiceCall(routeKey, timeout, platformArgs, userMessage), new TRequestOption());
  }

  public void send_addUserMessage(int routeKey, int timeout, List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage,TRequestOption requestOption) throws TException { 
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
        create_addUserMessageServiceCall(routeKey, timeout, platformArgs, userMessage), requestOption);
  }

  public long addUserMessage(int routeKey, int timeout, List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage, IMethodCallback<UserMessageDao.addUserMessage_args, UserMessageDao.addUserMessage_result> callback) throws TException{
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
            create_addUserMessageServiceCall(routeKey, timeout, platformArgs, userMessage), callback);
  }

  public long add_addUserMessageCall(AsyncCallRunner runner, int routeKey, int timeout, List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage, IMethodCallback<UserMessageDao.addUserMessage_args, UserMessageDao.addUserMessage_result> callback) throws TException{
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
            create_addUserMessageServiceCall(routeKey, timeout, platformArgs, userMessage), callback);
  }

  protected TServiceCall create_addUserMessageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(UserMessageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserMessageDao.addUserMessage_args request = new UserMessageDao.addUserMessage_args();
    request.setPlatformArgs(platformArgs);
    request.setUserMessage(userMessage);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addUserMessage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserMessageDao.addUserMessage_result.class);
    return serviceCall;
  }

  public void send_reqUserMessage(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqUserMessageServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqUserMessage(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqUserMessageServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqUserMessage(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<UserMessageDao.reqUserMessage_args, UserMessageDao.reqUserMessage_result> callback) throws TException{
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
            create_reqUserMessageServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqUserMessageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<UserMessageDao.reqUserMessage_args, UserMessageDao.reqUserMessage_result> callback) throws TException{
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
            create_reqUserMessageServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqUserMessageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(UserMessageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserMessageDao.reqUserMessage_args request = new UserMessageDao.reqUserMessage_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqUserMessage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserMessageDao.reqUserMessage_result.class);
    return serviceCall;
  }

  public void send_markAsRead(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.MarkReadOption option) throws TException {
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
        create_markAsReadServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_markAsRead(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.MarkReadOption option,TRequestOption requestOption) throws TException { 
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
        create_markAsReadServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long markAsRead(int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.MarkReadOption option, IMethodCallback<UserMessageDao.markAsRead_args, UserMessageDao.markAsRead_result> callback) throws TException{
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
            create_markAsReadServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_markAsReadCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.mailbox.user.message.thriftapi.MarkReadOption option, IMethodCallback<UserMessageDao.markAsRead_args, UserMessageDao.markAsRead_result> callback) throws TException{
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
            create_markAsReadServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_markAsReadServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.mailbox.user.message.thriftapi.MarkReadOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(UserMessageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserMessageDao.markAsRead_args request = new UserMessageDao.markAsRead_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("markAsRead");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserMessageDao.markAsRead_result.class);
    return serviceCall;
  }

}
