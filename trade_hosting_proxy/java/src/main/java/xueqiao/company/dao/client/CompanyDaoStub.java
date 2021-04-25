package xueqiao.company.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.company.dao.CompanyDao;
import xueqiao.company.dao.CompanyDaoVariable;

public class CompanyDaoStub extends BaseStub {

  public CompanyDaoStub() {
    super(CompanyDaoVariable.serviceKey);
  }

  public int  addCompany(xueqiao.company.CompanyEntry newCompany) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompany(newCompany, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompany(xueqiao.company.CompanyEntry newCompany,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addCompany").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).addCompany(platformArgs, newCompany);
      }
    }, invokeInfo);
  }

  public int  addCompany(int routeKey, int timeout,xueqiao.company.CompanyEntry newCompany)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompany(newCompany, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompany(xueqiao.company.CompanyEntry updateCompany) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompany(updateCompany, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompany(xueqiao.company.CompanyEntry updateCompany,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompany").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateCompany(platformArgs, updateCompany);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompany(int routeKey, int timeout,xueqiao.company.CompanyEntry updateCompany)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompany(updateCompany, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyPageResult  queryCompanyPage(xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyPageResult  queryCompanyPage(xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCompanyPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyPageResult>(){
    @Override
    public xueqiao.company.CompanyPageResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryCompanyPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyPageResult  queryCompanyPage(int routeKey, int timeout,xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyGroup(xueqiao.company.CompanyGroup newGroup) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyGroup(newGroup, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyGroup(xueqiao.company.CompanyGroup newGroup,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addCompanyGroup").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).addCompanyGroup(platformArgs, newGroup);
      }
    }, invokeInfo);
  }

  public int  addCompanyGroup(int routeKey, int timeout,xueqiao.company.CompanyGroup newGroup)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyGroup(newGroup, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyGroup(xueqiao.company.CompanyGroup updateGroup) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyGroup(updateGroup, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyGroup(xueqiao.company.CompanyGroup updateGroup,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompanyGroup").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateCompanyGroup(platformArgs, updateGroup);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompanyGroup(int routeKey, int timeout,xueqiao.company.CompanyGroup updateGroup)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyGroup(updateGroup, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteCompanyGroup(int companyId, int groupId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteCompanyGroup(companyId, groupId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteCompanyGroup(int companyId, int groupId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteCompanyGroup").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).deleteCompanyGroup(platformArgs, companyId, groupId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteCompanyGroup(int routeKey, int timeout,int companyId, int groupId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteCompanyGroup(companyId, groupId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupPageResult  queryCompanyGroupPage(xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyGroupPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupPageResult  queryCompanyGroupPage(xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCompanyGroupPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyGroupPageResult>(){
    @Override
    public xueqiao.company.CompanyGroupPageResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryCompanyGroupPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyGroupPageResult  queryCompanyGroupPage(int routeKey, int timeout,xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyGroupPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyGroupAndSpec(xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyGroupAndSpec(newGroup, newGroupSpec, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyGroupAndSpec(xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addCompanyGroupAndSpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).addCompanyGroupAndSpec(platformArgs, newGroup, newGroupSpec);
      }
    }, invokeInfo);
  }

  public int  addCompanyGroupAndSpec(int routeKey, int timeout,xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyGroupAndSpec(newGroup, newGroupSpec, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyGroupSpec(xueqiao.company.CompanyGroupSpec updateGroupSpec) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyGroupSpec(updateGroupSpec, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyGroupSpec(xueqiao.company.CompanyGroupSpec updateGroupSpec,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompanyGroupSpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateCompanyGroupSpec(platformArgs, updateGroupSpec);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompanyGroupSpec(int routeKey, int timeout,xueqiao.company.CompanyGroupSpec updateGroupSpec)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyGroupSpec(updateGroupSpec, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyUser(xueqiao.company.CompanyUser companyUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyUser(companyUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyUser(xueqiao.company.CompanyUser companyUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addCompanyUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).addCompanyUser(platformArgs, companyUser);
      }
    }, invokeInfo);
  }

  public int  addCompanyUser(int routeKey, int timeout,xueqiao.company.CompanyUser companyUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyUser(companyUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUser(xueqiao.company.CompanyUser companyUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUser(companyUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUser(xueqiao.company.CompanyUser companyUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompanyUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateCompanyUser(platformArgs, companyUser);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompanyUser(int routeKey, int timeout,xueqiao.company.CompanyUser companyUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUser(companyUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanySpecPage  queryCompanySpec(xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanySpec(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanySpecPage  queryCompanySpec(xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCompanySpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanySpecPage>(){
    @Override
    public xueqiao.company.CompanySpecPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryCompanySpec(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanySpecPage  queryCompanySpec(int routeKey, int timeout,xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanySpec(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addUser2Group(xueqiao.company.GroupUser groupUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addUser2Group(groupUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addUser2Group(xueqiao.company.GroupUser groupUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addUser2Group").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).addUser2Group(platformArgs, groupUser);
      return null;
      }
    }, invokeInfo);
  }

  public void  addUser2Group(int routeKey, int timeout,xueqiao.company.GroupUser groupUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addUser2Group(groupUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeGroupUser(xueqiao.company.GroupUser groupUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeGroupUser(groupUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeGroupUser(xueqiao.company.GroupUser groupUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeGroupUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).removeGroupUser(platformArgs, groupUser);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeGroupUser(int routeKey, int timeout,xueqiao.company.GroupUser groupUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeGroupUser(groupUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupSpecPage  queryCompanyGroupSpec(xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyGroupSpec(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupSpecPage  queryCompanyGroupSpec(xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCompanyGroupSpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyGroupSpecPage>(){
    @Override
    public xueqiao.company.CompanyGroupSpecPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryCompanyGroupSpec(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyGroupSpecPage  queryCompanyGroupSpec(int routeKey, int timeout,xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyGroupSpec(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyUserPage  queryCompanyUser(xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyUser(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyUserPage  queryCompanyUser(xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCompanyUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyUserPage>(){
    @Override
    public xueqiao.company.CompanyUserPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryCompanyUser(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyUserPage  queryCompanyUser(int routeKey, int timeout,xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCompanyUser(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.GroupUserPage  queryGroupUser(xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryGroupUser(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.GroupUserPage  queryGroupUser(xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryGroupUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.GroupUserPage>(){
    @Override
    public xueqiao.company.GroupUserPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryGroupUser(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.GroupUserPage  queryGroupUser(int routeKey, int timeout,xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryGroupUser(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateGroupUser(xueqiao.company.GroupUser groupUser) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateGroupUser(groupUser, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateGroupUser(xueqiao.company.GroupUser groupUser,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateGroupUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateGroupUser(platformArgs, groupUser);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateGroupUser(int routeKey, int timeout,xueqiao.company.GroupUser groupUser)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateGroupUser(groupUser, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupSpecPage  queryExpiredCompanyGroupSpec(xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryExpiredCompanyGroupSpec(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyGroupSpecPage  queryExpiredCompanyGroupSpec(xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryExpiredCompanyGroupSpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyGroupSpecPage>(){
    @Override
    public xueqiao.company.CompanyGroupSpecPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryExpiredCompanyGroupSpec(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyGroupSpecPage  queryExpiredCompanyGroupSpec(int routeKey, int timeout,xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryExpiredCompanyGroupSpec(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyUserEx(xueqiao.company.CompanyUserEx companyUserEx) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyUserEx(companyUserEx, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  addCompanyUserEx(xueqiao.company.CompanyUserEx companyUserEx,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addCompanyUserEx").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).addCompanyUserEx(platformArgs, companyUserEx);
      }
    }, invokeInfo);
  }

  public int  addCompanyUserEx(int routeKey, int timeout,xueqiao.company.CompanyUserEx companyUserEx)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return addCompanyUserEx(companyUserEx, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUserPassword(xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUserPassword(updateCompanyUserPasswordReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateCompanyUserPassword(xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateCompanyUserPassword").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).updateCompanyUserPassword(platformArgs, updateCompanyUserPasswordReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateCompanyUserPassword(int routeKey, int timeout,xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateCompanyUserPassword(updateCompanyUserPasswordReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  submitInitHosingTask(xueqiao.company.InitHostingMachineReq initHostingMachineReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    submitInitHosingTask(initHostingMachineReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  submitInitHosingTask(xueqiao.company.InitHostingMachineReq initHostingMachineReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("submitInitHosingTask").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).submitInitHosingTask(platformArgs, initHostingMachineReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  submitInitHosingTask(int routeKey, int timeout,xueqiao.company.InitHostingMachineReq initHostingMachineReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    submitInitHosingTask(initHostingMachineReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  doAfterInitHosting(xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    doAfterInitHosting(initHostingTask, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  doAfterInitHosting(xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("doAfterInitHosting").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).doAfterInitHosting(platformArgs, initHostingTask);
      return null;
      }
    }, invokeInfo);
  }

  public void  doAfterInitHosting(int routeKey, int timeout,xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    doAfterInitHosting(initHostingTask, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  doUpgradeGroupSpec(int orderId, String oaUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    doUpgradeGroupSpec(orderId, oaUserName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  doUpgradeGroupSpec(int orderId, String oaUserName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("doUpgradeGroupSpec").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyDao.Client(protocol).doUpgradeGroupSpec(platformArgs, orderId, oaUserName);
      return null;
      }
    }, invokeInfo);
  }

  public void  doUpgradeGroupSpec(int routeKey, int timeout,int orderId, String oaUserName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    doUpgradeGroupSpec(orderId, oaUserName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.GroupUserExPage  queryGroupUserEx(xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryGroupUserEx(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.GroupUserExPage  queryGroupUserEx(xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryGroupUserEx").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.GroupUserExPage>(){
    @Override
    public xueqiao.company.GroupUserExPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).queryGroupUserEx(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.company.GroupUserExPage  queryGroupUserEx(int routeKey, int timeout,xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryGroupUserEx(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyEntry  getCollectiveCompany() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getCollectiveCompany(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.company.CompanyEntry  getCollectiveCompany(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getCollectiveCompany").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.company.CompanyEntry>(){
    @Override
    public xueqiao.company.CompanyEntry call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyDao.Client(protocol).getCollectiveCompany(platformArgs);
      }
    }, invokeInfo);
  }

  public xueqiao.company.CompanyEntry  getCollectiveCompany(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getCollectiveCompany(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
