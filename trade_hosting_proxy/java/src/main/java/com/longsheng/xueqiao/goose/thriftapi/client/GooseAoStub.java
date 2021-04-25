package com.longsheng.xueqiao.goose.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import com.longsheng.xueqiao.goose.thriftapi.GooseAo;
import com.longsheng.xueqiao.goose.thriftapi.GooseAoVariable;

public class GooseAoStub extends BaseStub {

  public GooseAoStub() {
    super(GooseAoVariable.serviceKey);
  }

  public void  sendVerifyCode(String mobileNo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendVerifyCode(mobileNo, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendVerifyCode(String mobileNo,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("sendVerifyCode").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new GooseAo.Client(protocol).sendVerifyCode(platformArgs, mobileNo);
      return null;
      }
    }, invokeInfo);
  }

  public void  sendVerifyCode(int routeKey, int timeout,String mobileNo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendVerifyCode(mobileNo, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  verifySmsCode(String mobileNo, String smsCode) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return verifySmsCode(mobileNo, smsCode, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  verifySmsCode(String mobileNo, String smsCode,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("verifySmsCode").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new GooseAo.Client(protocol).verifySmsCode(platformArgs, mobileNo, smsCode);
      }
    }, invokeInfo);
  }

  public boolean  verifySmsCode(int routeKey, int timeout,String mobileNo, String smsCode)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return verifySmsCode(mobileNo, smsCode, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendUserNotificationSms(String mobileNo, String msg) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendUserNotificationSms(mobileNo, msg, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendUserNotificationSms(String mobileNo, String msg,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("sendUserNotificationSms").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new GooseAo.Client(protocol).sendUserNotificationSms(platformArgs, mobileNo, msg);
      return null;
      }
    }, invokeInfo);
  }

  public void  sendUserNotificationSms(int routeKey, int timeout,String mobileNo, String msg)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendUserNotificationSms(mobileNo, msg, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendMaintenanceNotificationSms(String msg) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendMaintenanceNotificationSms(msg, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendMaintenanceNotificationSms(String msg,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("sendMaintenanceNotificationSms").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new GooseAo.Client(protocol).sendMaintenanceNotificationSms(platformArgs, msg);
      return null;
      }
    }, invokeInfo);
  }

  public void  sendMaintenanceNotificationSms(int routeKey, int timeout,String msg)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendMaintenanceNotificationSms(msg, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendMailboxMessage(String tel, String userName, String content) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendMailboxMessage(tel, userName, content, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  sendMailboxMessage(String tel, String userName, String content,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("sendMailboxMessage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new GooseAo.Client(protocol).sendMailboxMessage(platformArgs, tel, userName, content);
      return null;
      }
    }, invokeInfo);
  }

  public void  sendMailboxMessage(int routeKey, int timeout,String tel, String userName, String content)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    sendMailboxMessage(tel, userName, content, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  verifySmsCodeForClear(String mobileNo, String smsCode, boolean clear) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return verifySmsCodeForClear(mobileNo, smsCode, clear, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  verifySmsCodeForClear(String mobileNo, String smsCode, boolean clear,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("verifySmsCodeForClear").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new GooseAo.Client(protocol).verifySmsCodeForClear(platformArgs, mobileNo, smsCode, clear);
      }
    }, invokeInfo);
  }

  public boolean  verifySmsCodeForClear(int routeKey, int timeout,String mobileNo, String smsCode, boolean clear)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return verifySmsCodeForClear(mobileNo, smsCode, clear, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
