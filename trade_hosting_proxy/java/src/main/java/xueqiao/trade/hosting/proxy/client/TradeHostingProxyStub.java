package xueqiao.trade.hosting.proxy.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.Set;
import xueqiao.trade.hosting.proxy.AppVersion;
import xueqiao.trade.hosting.proxy.ProxyFakeLoginReq;
import xueqiao.trade.hosting.proxy.ProxyFakeLoginResp;
import xueqiao.trade.hosting.proxy.ProxyLoginReq;
import xueqiao.trade.hosting.proxy.ProxyLoginResp;
import xueqiao.trade.hosting.proxy.ProxyUpdatePasswordReq;
import xueqiao.trade.hosting.proxy.ReqMailBoxMessageOption;
import xueqiao.trade.hosting.proxy.UpdateInfoReq;
import xueqiao.trade.hosting.proxy.TradeHostingProxy;
import xueqiao.trade.hosting.proxy.TradeHostingProxyVariable;

public class TradeHostingProxyStub extends BaseStub {

  public TradeHostingProxyStub() {
    super(TradeHostingProxyVariable.serviceKey);
  }

  public ProxyLoginResp  login(ProxyLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ProxyLoginResp  login(ProxyLoginReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("login").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ProxyLoginResp>(){
    @Override
    public ProxyLoginResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).login(platformArgs, req);
      }
    }, invokeInfo);
  }

  public ProxyLoginResp  login(int routeKey, int timeout,ProxyLoginReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ProxyFakeLoginResp  fakeLogin(ProxyFakeLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return fakeLogin(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ProxyFakeLoginResp  fakeLogin(ProxyFakeLoginReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("fakeLogin").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ProxyFakeLoginResp>(){
    @Override
    public ProxyFakeLoginResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).fakeLogin(platformArgs, req);
      }
    }, invokeInfo);
  }

  public ProxyFakeLoginResp  fakeLogin(int routeKey, int timeout,ProxyFakeLoginReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return fakeLogin(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUserPassword(ProxyUpdatePasswordReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUserPassword(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUserPassword(ProxyUpdatePasswordReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompanyUserPassword").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingProxy.Client(protocol).updateCompanyUserPassword(platformArgs, req);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompanyUserPassword(int routeKey, int timeout,ProxyUpdatePasswordReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUserPassword(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  checkSession(xueqiao.trade.hosting.HostingSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSession(session, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  checkSession(xueqiao.trade.hosting.HostingSession session,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("checkSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).checkSession(platformArgs, session);
      }
    }, invokeInfo);
  }

  public boolean  checkSession(int routeKey, int timeout,xueqiao.trade.hosting.HostingSession session)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSession(session, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AppVersion  queryUpdateInfo(UpdateInfoReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpdateInfo(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public AppVersion  queryUpdateInfo(UpdateInfoReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUpdateInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<AppVersion>(){
    @Override
    public AppVersion call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).queryUpdateInfo(platformArgs, req);
      }
    }, invokeInfo);
  }

  public AppVersion  queryUpdateInfo(int routeKey, int timeout,UpdateInfoReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUpdateInfo(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(xueqiao.trade.hosting.HostingSession session, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryMailBoxMessage(session, option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(xueqiao.trade.hosting.HostingSession session, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryMailBoxMessage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.mailbox.user.message.thriftapi.UserMessagePage>(){
    @Override
    public xueqiao.mailbox.user.message.thriftapi.UserMessagePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).queryMailBoxMessage(platformArgs, session, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  queryMailBoxMessage(int routeKey, int timeout,xueqiao.trade.hosting.HostingSession session, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryMailBoxMessage(session, option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  markMessageAsRead(xueqiao.trade.hosting.HostingSession session, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return markMessageAsRead(session, hostingMessageIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  markMessageAsRead(xueqiao.trade.hosting.HostingSession session, Set<Long> hostingMessageIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("markMessageAsRead").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingProxy.Client(protocol).markMessageAsRead(platformArgs, session, hostingMessageIds);
      }
    }, invokeInfo);
  }

  public boolean  markMessageAsRead(int routeKey, int timeout,xueqiao.trade.hosting.HostingSession session, Set<Long> hostingMessageIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return markMessageAsRead(session, hostingMessageIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
