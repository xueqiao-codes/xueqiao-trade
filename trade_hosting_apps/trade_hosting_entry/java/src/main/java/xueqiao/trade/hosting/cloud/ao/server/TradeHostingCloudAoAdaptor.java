package xueqiao.trade.hosting.cloud.ao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.cloud.ao.HostingInitReq;
import xueqiao.trade.hosting.cloud.ao.HostingInitResp;
import xueqiao.trade.hosting.cloud.ao.HostingResetReq;
import xueqiao.trade.hosting.cloud.ao.LoginReq;
import xueqiao.trade.hosting.cloud.ao.LoginResp;
import xueqiao.trade.hosting.cloud.ao.RegisterHostingUserResp;
import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAo;
import xueqiao.trade.hosting.cloud.ao.TradeHostingCloudAoVariable;


public abstract class TradeHostingCloudAoAdaptor implements TradeHostingCloudAo.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public HostingInitResp initHosting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingInitReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"initHosting",platformArgs);
    return initHosting(oCntl, req);
  }

  protected abstract HostingInitResp initHosting(TServiceCntl oCntl, HostingInitReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.HostingInfo getHostingInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"getHostingInfo",platformArgs);
    return getHostingInfo(oCntl);
  }

  protected abstract xueqiao.trade.hosting.HostingInfo getHostingInfo(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void resetHosting(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingResetReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"resetHosting",platformArgs);
resetHosting(oCntl, req);
  }

  protected abstract void resetHosting(TServiceCntl oCntl, HostingResetReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public RegisterHostingUserResp registerHostingUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingUser newUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"registerHostingUser",platformArgs);
    return registerHostingUser(oCntl, newUser);
  }

  protected abstract RegisterHostingUserResp registerHostingUser(TServiceCntl oCntl, xueqiao.trade.hosting.HostingUser newUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateHostingUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingUser updateUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"updateHostingUser",platformArgs);
updateHostingUser(oCntl, updateUser);
  }

  protected abstract void updateHostingUser(TServiceCntl oCntl, xueqiao.trade.hosting.HostingUser updateUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void disableHostingUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"disableHostingUser",platformArgs);
disableHostingUser(oCntl, subUserId);
  }

  protected abstract void disableHostingUser(TServiceCntl oCntl, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public xueqiao.trade.hosting.QueryHostingUserPage getHostingUserPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"getHostingUserPage",platformArgs);
    return getHostingUserPage(oCntl, queryOption, pageOption);
  }

  protected abstract xueqiao.trade.hosting.QueryHostingUserPage getHostingUserPage(TServiceCntl oCntl, xueqiao.trade.hosting.QueryHostingUserOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void enableHostingUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"enableHostingUser",platformArgs);
enableHostingUser(oCntl, subUserId);
  }

  protected abstract void enableHostingUser(TServiceCntl oCntl, int subUserId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public LoginResp login(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, LoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"login",platformArgs);
    return login(oCntl, req);
  }

  protected abstract LoginResp login(TServiceCntl oCntl, LoginReq req) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public boolean checkSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.HostingSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingCloudAoVariable.serviceKey,"checkSession",platformArgs);
    return checkSession(oCntl, session);
  }

  protected abstract boolean checkSession(TServiceCntl oCntl, xueqiao.trade.hosting.HostingSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingCloudAoAdaptor(){
    methodParameterNameMap.put("initHosting",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("getHostingInfo",new String[]{"platformArgs"});
    methodParameterNameMap.put("resetHosting",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("registerHostingUser",new String[]{"platformArgs", "newUser"});
    methodParameterNameMap.put("updateHostingUser",new String[]{"platformArgs", "updateUser"});
    methodParameterNameMap.put("disableHostingUser",new String[]{"platformArgs", "subUserId"});
    methodParameterNameMap.put("getHostingUserPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("enableHostingUser",new String[]{"platformArgs", "subUserId"});
    methodParameterNameMap.put("login",new String[]{"platformArgs", "req"});
    methodParameterNameMap.put("checkSession",new String[]{"platformArgs", "session"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
