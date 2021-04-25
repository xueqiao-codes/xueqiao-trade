package com.longsheng.xueqiao.goose.thriftapi.client;

import com.longsheng.xueqiao.goose.thriftapi.GooseAo;
import com.longsheng.xueqiao.goose.thriftapi.GooseAoVariable;
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

public class GooseAoAsyncStub extends BaseStub { 
  public GooseAoAsyncStub() {
    super(GooseAoVariable.serviceKey);
  }
  public void send_sendVerifyCode(int routeKey, int timeout, String mobileNo) throws TException {
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
        create_sendVerifyCodeServiceCall(routeKey, timeout, platformArgs, mobileNo), new TRequestOption());
  }

  public void send_sendVerifyCode(int routeKey, int timeout, String mobileNo,TRequestOption requestOption) throws TException { 
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
        create_sendVerifyCodeServiceCall(routeKey, timeout, platformArgs, mobileNo), requestOption);
  }

  public long sendVerifyCode(int routeKey, int timeout, String mobileNo, IMethodCallback<GooseAo.sendVerifyCode_args, GooseAo.sendVerifyCode_result> callback) throws TException{
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
            create_sendVerifyCodeServiceCall(routeKey, timeout, platformArgs, mobileNo), callback);
  }

  public long add_sendVerifyCodeCall(AsyncCallRunner runner, int routeKey, int timeout, String mobileNo, IMethodCallback<GooseAo.sendVerifyCode_args, GooseAo.sendVerifyCode_result> callback) throws TException{
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
            create_sendVerifyCodeServiceCall(routeKey, timeout, platformArgs, mobileNo), callback);
  }

  protected TServiceCall create_sendVerifyCodeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String mobileNo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.sendVerifyCode_args request = new GooseAo.sendVerifyCode_args();
    request.setPlatformArgs(platformArgs);
    request.setMobileNo(mobileNo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("sendVerifyCode");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.sendVerifyCode_result.class);
    return serviceCall;
  }

  public void send_verifySmsCode(int routeKey, int timeout, String mobileNo, String smsCode) throws TException {
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
        create_verifySmsCodeServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode), new TRequestOption());
  }

  public void send_verifySmsCode(int routeKey, int timeout, String mobileNo, String smsCode,TRequestOption requestOption) throws TException { 
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
        create_verifySmsCodeServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode), requestOption);
  }

  public long verifySmsCode(int routeKey, int timeout, String mobileNo, String smsCode, IMethodCallback<GooseAo.verifySmsCode_args, GooseAo.verifySmsCode_result> callback) throws TException{
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
            create_verifySmsCodeServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode), callback);
  }

  public long add_verifySmsCodeCall(AsyncCallRunner runner, int routeKey, int timeout, String mobileNo, String smsCode, IMethodCallback<GooseAo.verifySmsCode_args, GooseAo.verifySmsCode_result> callback) throws TException{
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
            create_verifySmsCodeServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode), callback);
  }

  protected TServiceCall create_verifySmsCodeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String mobileNo, String smsCode){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.verifySmsCode_args request = new GooseAo.verifySmsCode_args();
    request.setPlatformArgs(platformArgs);
    request.setMobileNo(mobileNo);
    request.setSmsCode(smsCode);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("verifySmsCode");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.verifySmsCode_result.class);
    return serviceCall;
  }

  public void send_sendUserNotificationSms(int routeKey, int timeout, String mobileNo, String msg) throws TException {
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
        create_sendUserNotificationSmsServiceCall(routeKey, timeout, platformArgs, mobileNo, msg), new TRequestOption());
  }

  public void send_sendUserNotificationSms(int routeKey, int timeout, String mobileNo, String msg,TRequestOption requestOption) throws TException { 
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
        create_sendUserNotificationSmsServiceCall(routeKey, timeout, platformArgs, mobileNo, msg), requestOption);
  }

  public long sendUserNotificationSms(int routeKey, int timeout, String mobileNo, String msg, IMethodCallback<GooseAo.sendUserNotificationSms_args, GooseAo.sendUserNotificationSms_result> callback) throws TException{
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
            create_sendUserNotificationSmsServiceCall(routeKey, timeout, platformArgs, mobileNo, msg), callback);
  }

  public long add_sendUserNotificationSmsCall(AsyncCallRunner runner, int routeKey, int timeout, String mobileNo, String msg, IMethodCallback<GooseAo.sendUserNotificationSms_args, GooseAo.sendUserNotificationSms_result> callback) throws TException{
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
            create_sendUserNotificationSmsServiceCall(routeKey, timeout, platformArgs, mobileNo, msg), callback);
  }

  protected TServiceCall create_sendUserNotificationSmsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String mobileNo, String msg){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.sendUserNotificationSms_args request = new GooseAo.sendUserNotificationSms_args();
    request.setPlatformArgs(platformArgs);
    request.setMobileNo(mobileNo);
    request.setMsg(msg);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("sendUserNotificationSms");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.sendUserNotificationSms_result.class);
    return serviceCall;
  }

  public void send_sendMaintenanceNotificationSms(int routeKey, int timeout, String msg) throws TException {
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
        create_sendMaintenanceNotificationSmsServiceCall(routeKey, timeout, platformArgs, msg), new TRequestOption());
  }

  public void send_sendMaintenanceNotificationSms(int routeKey, int timeout, String msg,TRequestOption requestOption) throws TException { 
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
        create_sendMaintenanceNotificationSmsServiceCall(routeKey, timeout, platformArgs, msg), requestOption);
  }

  public long sendMaintenanceNotificationSms(int routeKey, int timeout, String msg, IMethodCallback<GooseAo.sendMaintenanceNotificationSms_args, GooseAo.sendMaintenanceNotificationSms_result> callback) throws TException{
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
            create_sendMaintenanceNotificationSmsServiceCall(routeKey, timeout, platformArgs, msg), callback);
  }

  public long add_sendMaintenanceNotificationSmsCall(AsyncCallRunner runner, int routeKey, int timeout, String msg, IMethodCallback<GooseAo.sendMaintenanceNotificationSms_args, GooseAo.sendMaintenanceNotificationSms_result> callback) throws TException{
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
            create_sendMaintenanceNotificationSmsServiceCall(routeKey, timeout, platformArgs, msg), callback);
  }

  protected TServiceCall create_sendMaintenanceNotificationSmsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String msg){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.sendMaintenanceNotificationSms_args request = new GooseAo.sendMaintenanceNotificationSms_args();
    request.setPlatformArgs(platformArgs);
    request.setMsg(msg);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("sendMaintenanceNotificationSms");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.sendMaintenanceNotificationSms_result.class);
    return serviceCall;
  }

  public void send_sendMailboxMessage(int routeKey, int timeout, String tel, String userName, String content) throws TException {
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
        create_sendMailboxMessageServiceCall(routeKey, timeout, platformArgs, tel, userName, content), new TRequestOption());
  }

  public void send_sendMailboxMessage(int routeKey, int timeout, String tel, String userName, String content,TRequestOption requestOption) throws TException { 
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
        create_sendMailboxMessageServiceCall(routeKey, timeout, platformArgs, tel, userName, content), requestOption);
  }

  public long sendMailboxMessage(int routeKey, int timeout, String tel, String userName, String content, IMethodCallback<GooseAo.sendMailboxMessage_args, GooseAo.sendMailboxMessage_result> callback) throws TException{
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
            create_sendMailboxMessageServiceCall(routeKey, timeout, platformArgs, tel, userName, content), callback);
  }

  public long add_sendMailboxMessageCall(AsyncCallRunner runner, int routeKey, int timeout, String tel, String userName, String content, IMethodCallback<GooseAo.sendMailboxMessage_args, GooseAo.sendMailboxMessage_result> callback) throws TException{
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
            create_sendMailboxMessageServiceCall(routeKey, timeout, platformArgs, tel, userName, content), callback);
  }

  protected TServiceCall create_sendMailboxMessageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String tel, String userName, String content){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.sendMailboxMessage_args request = new GooseAo.sendMailboxMessage_args();
    request.setPlatformArgs(platformArgs);
    request.setTel(tel);
    request.setUserName(userName);
    request.setContent(content);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("sendMailboxMessage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.sendMailboxMessage_result.class);
    return serviceCall;
  }

  public void send_verifySmsCodeForClear(int routeKey, int timeout, String mobileNo, String smsCode, boolean clear) throws TException {
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
        create_verifySmsCodeForClearServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode, clear), new TRequestOption());
  }

  public void send_verifySmsCodeForClear(int routeKey, int timeout, String mobileNo, String smsCode, boolean clear,TRequestOption requestOption) throws TException { 
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
        create_verifySmsCodeForClearServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode, clear), requestOption);
  }

  public long verifySmsCodeForClear(int routeKey, int timeout, String mobileNo, String smsCode, boolean clear, IMethodCallback<GooseAo.verifySmsCodeForClear_args, GooseAo.verifySmsCodeForClear_result> callback) throws TException{
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
            create_verifySmsCodeForClearServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode, clear), callback);
  }

  public long add_verifySmsCodeForClearCall(AsyncCallRunner runner, int routeKey, int timeout, String mobileNo, String smsCode, boolean clear, IMethodCallback<GooseAo.verifySmsCodeForClear_args, GooseAo.verifySmsCodeForClear_result> callback) throws TException{
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
            create_verifySmsCodeForClearServiceCall(routeKey, timeout, platformArgs, mobileNo, smsCode, clear), callback);
  }

  protected TServiceCall create_verifySmsCodeForClearServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String mobileNo, String smsCode, boolean clear){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(GooseAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    GooseAo.verifySmsCodeForClear_args request = new GooseAo.verifySmsCodeForClear_args();
    request.setPlatformArgs(platformArgs);
    request.setMobileNo(mobileNo);
    request.setSmsCode(smsCode);
    request.setClear(clear);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("verifySmsCodeForClear");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(GooseAo.verifySmsCodeForClear_result.class);
    return serviceCall;
  }

}
