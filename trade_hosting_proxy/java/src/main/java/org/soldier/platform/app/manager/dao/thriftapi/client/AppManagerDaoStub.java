package org.soldier.platform.app.manager.dao.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppVersionOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqProjectOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqServerAppSupportOption;
import org.soldier.platform.app.manager.dao.thriftapi.AppManagerDao;
import org.soldier.platform.app.manager.dao.thriftapi.AppManagerDaoVariable;

public class AppManagerDaoStub extends BaseStub {

  public AppManagerDaoStub() {
    super(AppManagerDaoVariable.serviceKey);
  }

  public List<org.soldier.platform.app.manager.thriftapi.Project>  reqProject(ReqProjectOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqProject(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.app.manager.thriftapi.Project>  reqProject(ReqProjectOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqProject").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<org.soldier.platform.app.manager.thriftapi.Project>>(){
    @Override
    public List<org.soldier.platform.app.manager.thriftapi.Project> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).reqProject(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<org.soldier.platform.app.manager.thriftapi.Project>  reqProject(int routeKey, int timeout,ReqProjectOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqProject(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addProject(org.soldier.platform.app.manager.thriftapi.Project project) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addProject(project, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addProject(org.soldier.platform.app.manager.thriftapi.Project project,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addProject").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).addProject(platformArgs, project);
      }
    }, invokeInfo);
  }

  public long  addProject(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.Project project)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addProject(project, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateProject(org.soldier.platform.app.manager.thriftapi.Project project) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateProject(project, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateProject(org.soldier.platform.app.manager.thriftapi.Project project,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateProject").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).updateProject(platformArgs, project);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateProject(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.Project project)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateProject(project, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeProject(long projectId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeProject(projectId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeProject(long projectId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeProject").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).removeProject(platformArgs, projectId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeProject(int routeKey, int timeout,long projectId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeProject(projectId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.app.manager.thriftapi.App>  reqApp(ReqAppOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqApp(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.app.manager.thriftapi.App>  reqApp(ReqAppOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqApp").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<org.soldier.platform.app.manager.thriftapi.App>>(){
    @Override
    public List<org.soldier.platform.app.manager.thriftapi.App> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).reqApp(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<org.soldier.platform.app.manager.thriftapi.App>  reqApp(int routeKey, int timeout,ReqAppOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqApp(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addApp(org.soldier.platform.app.manager.thriftapi.App app) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addApp(app, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addApp(org.soldier.platform.app.manager.thriftapi.App app,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addApp").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).addApp(platformArgs, app);
      }
    }, invokeInfo);
  }

  public long  addApp(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.App app)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addApp(app, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateApp(org.soldier.platform.app.manager.thriftapi.App app) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateApp(app, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateApp(org.soldier.platform.app.manager.thriftapi.App app,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateApp").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).updateApp(platformArgs, app);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateApp(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.App app)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateApp(app, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeApp(long appId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeApp(appId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeApp(long appId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeApp").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).removeApp(platformArgs, appId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeApp(int routeKey, int timeout,long appId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeApp(appId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public org.soldier.platform.app.manager.thriftapi.AppVersionPage  reqAppVersion(ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqAppVersion(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public org.soldier.platform.app.manager.thriftapi.AppVersionPage  reqAppVersion(ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqAppVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<org.soldier.platform.app.manager.thriftapi.AppVersionPage>(){
    @Override
    public org.soldier.platform.app.manager.thriftapi.AppVersionPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).reqAppVersion(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public org.soldier.platform.app.manager.thriftapi.AppVersionPage  reqAppVersion(int routeKey, int timeout,ReqAppVersionOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqAppVersion(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addAppVersion(org.soldier.platform.app.manager.thriftapi.AppVersion appVersion) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addAppVersion(appVersion, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public long  addAppVersion(org.soldier.platform.app.manager.thriftapi.AppVersion appVersion,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addAppVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Long>(){
    @Override
    public Long call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).addAppVersion(platformArgs, appVersion);
      }
    }, invokeInfo);
  }

  public long  addAppVersion(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.AppVersion appVersion)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addAppVersion(appVersion, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateAppVersion(org.soldier.platform.app.manager.thriftapi.AppVersion appVersion) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateAppVersion(appVersion, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateAppVersion(org.soldier.platform.app.manager.thriftapi.AppVersion appVersion,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateAppVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).updateAppVersion(platformArgs, appVersion);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateAppVersion(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.AppVersion appVersion)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateAppVersion(appVersion, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeAppVersion(long appVersionId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeAppVersion(appVersionId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeAppVersion(long appVersionId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeAppVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).removeAppVersion(platformArgs, appVersionId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeAppVersion(int routeKey, int timeout,long appVersionId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeAppVersion(appVersionId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.app.manager.thriftapi.ServerAppSupport>  reqServerAppSupport(ReqServerAppSupportOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqServerAppSupport(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.app.manager.thriftapi.ServerAppSupport>  reqServerAppSupport(ReqServerAppSupportOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqServerAppSupport").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<org.soldier.platform.app.manager.thriftapi.ServerAppSupport>>(){
    @Override
    public List<org.soldier.platform.app.manager.thriftapi.ServerAppSupport> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new AppManagerDao.Client(protocol).reqServerAppSupport(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<org.soldier.platform.app.manager.thriftapi.ServerAppSupport>  reqServerAppSupport(int routeKey, int timeout,ReqServerAppSupportOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqServerAppSupport(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addServerAppSupport(org.soldier.platform.app.manager.thriftapi.ServerAppSupport support) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addServerAppSupport(support, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addServerAppSupport(org.soldier.platform.app.manager.thriftapi.ServerAppSupport support,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addServerAppSupport").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).addServerAppSupport(platformArgs, support);
      return null;
      }
    }, invokeInfo);
  }

  public void  addServerAppSupport(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.ServerAppSupport support)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addServerAppSupport(support, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateServerAppSupport(org.soldier.platform.app.manager.thriftapi.ServerAppSupport support) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateServerAppSupport(support, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateServerAppSupport(org.soldier.platform.app.manager.thriftapi.ServerAppSupport support,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateServerAppSupport").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).updateServerAppSupport(platformArgs, support);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateServerAppSupport(int routeKey, int timeout,org.soldier.platform.app.manager.thriftapi.ServerAppSupport support)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateServerAppSupport(support, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeServerAppSupport(long serverVersionId, long supportClientId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeServerAppSupport(serverVersionId, supportClientId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeServerAppSupport(long serverVersionId, long supportClientId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeServerAppSupport").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new AppManagerDao.Client(protocol).removeServerAppSupport(platformArgs, serverVersionId, supportClientId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeServerAppSupport(int routeKey, int timeout,long serverVersionId, long supportClientId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeServerAppSupport(serverVersionId, supportClientId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
