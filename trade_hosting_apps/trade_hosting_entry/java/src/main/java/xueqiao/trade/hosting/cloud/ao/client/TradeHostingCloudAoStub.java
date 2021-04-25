package xueqiao.trade.hosting.cloud.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.cloud.ao.HostingInitReq;
import xueqiao.trade.hosting.cloud.ao.HostingInitResp;
import xueqiao.trade.hosting.cloud.ao.HostingResetReq;
import xueqiao.trade.hosting.cloud.ao.LoginReq;
import xueqiao.trade.hosting.cloud.ao.LoginResp;
import xueqiao.trade.hosting.cloud.ao.RegisterHostingUserResp;
import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAo;
import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAoVariable;

public class TradeHostingCloudAoStub extends BaseStub {

  public TradeHostingCloudAoStub() {
    super(TradeHostingCloudAoVariable.serviceKey);
  }

  public HostingInitResp  initHosting(HostingInitReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return initHosting(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingInitResp  initHosting(HostingInitReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("initHosting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingInitResp>(){
    @Override
    public HostingInitResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingCloudAo.Client(protocol).initHosting(platformArgs, req);
      }
    }, invokeInfo);
  }

  public HostingInitResp  initHosting(int routeKey, int timeout,HostingInitReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return initHosting(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingInfo  getHostingInfo() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingInfo(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.HostingInfo  getHostingInfo(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.HostingInfo>(){
    @Override
    public xueqiao.trade.hosting.HostingInfo call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingCloudAo.Client(protocol).getHostingInfo(platformArgs);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.HostingInfo  getHostingInfo(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingInfo(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  resetHosting(HostingResetReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    resetHosting(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  resetHosting(HostingResetReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("resetHosting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingCloudAo.Client(protocol).resetHosting(platformArgs, req);
      return null;
      }
    }, invokeInfo);
  }

  public void  resetHosting(int routeKey, int timeout,HostingResetReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    resetHosting(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public RegisterHostingUserResp  registerHostingUser(xueqiao.trade.hosting.HostingUser newUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return registerHostingUser(newUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public RegisterHostingUserResp  registerHostingUser(xueqiao.trade.hosting.HostingUser newUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("registerHostingUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<RegisterHostingUserResp>(){
    @Override
    public RegisterHostingUserResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingCloudAo.Client(protocol).registerHostingUser(platformArgs, newUser);
      }
    }, invokeInfo);
  }

  public RegisterHostingUserResp  registerHostingUser(int routeKey, int timeout,xueqiao.trade.hosting.HostingUser newUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return registerHostingUser(newUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingUser(xueqiao.trade.hosting.HostingUser updateUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingUser(updateUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateHostingUser(xueqiao.trade.hosting.HostingUser updateUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateHostingUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingCloudAo.Client(protocol).updateHostingUser(platformArgs, updateUser);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateHostingUser(int routeKey, int timeout,xueqiao.trade.hosting.HostingUser updateUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateHostingUser(updateUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disableHostingUser(int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disableHostingUser(subUserId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disableHostingUser(int subUserId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("disableHostingUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingCloudAo.Client(protocol).disableHostingUser(platformArgs, subUserId);
      return null;
      }
    }, invokeInfo);
  }

  public void  disableHostingUser(int routeKey, int timeout,int subUserId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disableHostingUser(subUserId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingUserPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getHostingUserPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.trade.hosting.QueryHostingUserPage>(){
    @Override
    public xueqiao.trade.hosting.QueryHostingUserPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingCloudAo.Client(protocol).getHostingUserPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.trade.hosting.QueryHostingUserPage  getHostingUserPage(int routeKey, int timeout,xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getHostingUserPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  enableHostingUser(int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    enableHostingUser(subUserId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  enableHostingUser(int subUserId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("enableHostingUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingCloudAo.Client(protocol).enableHostingUser(platformArgs, subUserId);
      return null;
      }
    }, invokeInfo);
  }

  public void  enableHostingUser(int routeKey, int timeout,int subUserId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    enableHostingUser(subUserId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public LoginResp  login(LoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(req, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public LoginResp  login(LoginReq req,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("login").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<LoginResp>(){
    @Override
    public LoginResp call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingCloudAo.Client(protocol).login(platformArgs, req);
      }
    }, invokeInfo);
  }

  public LoginResp  login(int routeKey, int timeout,LoginReq req)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(req, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
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
      return new TradeHostingCloudAo.Client(protocol).checkSession(platformArgs, session);
      }
    }, invokeInfo);
  }

  public boolean  checkSession(int routeKey, int timeout,xueqiao.trade.hosting.HostingSession session)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSession(session, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
