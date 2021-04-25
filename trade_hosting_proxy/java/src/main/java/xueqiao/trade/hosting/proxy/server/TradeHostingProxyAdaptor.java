package xueqiao.trade.hosting.proxy.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
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


public abstract class TradeHostingProxyAdaptor implements TradeHostingProxy.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public ProxyLoginResp login(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ProxyLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"login",platformArgs);
    return login(oCntl, req);
  }

  protected abstract ProxyLoginResp login(TServiceCntl oCntl, ProxyLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public ProxyFakeLoginResp fakeLogin(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ProxyFakeLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"fakeLogin",platformArgs);
    return fakeLogin(oCntl, req);
  }

  protected abstract ProxyFakeLoginResp fakeLogin(TServiceCntl oCntl, ProxyFakeLoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateCompanyUserPassword(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ProxyUpdatePasswordReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"updateCompanyUserPassword",platformArgs);
updateCompanyUserPassword(oCntl, req);
  }

  protected abstract void updateCompanyUserPassword(TServiceCntl oCntl, ProxyUpdatePasswordReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public boolean checkSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"checkSession",platformArgs);
    return checkSession(oCntl, session);
  }

  protected abstract boolean checkSession(TServiceCntl oCntl, xueqiao.trade.hosting.HostingSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public AppVersion queryUpdateInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, UpdateInfoReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"queryUpdateInfo",platformArgs);
    return queryUpdateInfo(oCntl, req);
  }

  protected abstract AppVersion queryUpdateInfo(TServiceCntl oCntl, UpdateInfoReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage queryMailBoxMessage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingSession session, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"queryMailBoxMessage",platformArgs);
    return queryMailBoxMessage(oCntl, session, option, pageOption);
  }

  protected abstract xueqiao.mailbox.user.message.thriftapi.UserMessagePage queryMailBoxMessage(TServiceCntl oCntl, xueqiao.trade.hosting.HostingSession session, ReqMailBoxMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public boolean markMessageAsRead(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingSession session, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingProxyVariable.serviceKey,"markMessageAsRead",platformArgs);
    return markMessageAsRead(oCntl, session, hostingMessageIds);
  }

  protected abstract boolean markMessageAsRead(TServiceCntl oCntl, xueqiao.trade.hosting.HostingSession session, Set<Long> hostingMessageIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingProxyAdaptor(){
    methodParameterNameMap.put("login",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("fakeLogin",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("updateCompanyUserPassword",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("checkSession",new String[]{"platformArgs", "session"});
    methodParameterNameMap.put("queryUpdateInfo",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("queryMailBoxMessage",new String[]{"platformArgs", "session", "option", "pageOption"});
    methodParameterNameMap.put("markMessageAsRead",new String[]{"platformArgs", "session", "hostingMessageIds"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
