package xueqiao.company.dao.client;

import xueqiao.company.dao.CompanyDao;
import xueqiao.company.dao.CompanyDaoVariable;
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

public class CompanyDaoAsyncStub extends BaseStub { 
  public CompanyDaoAsyncStub() {
    super(CompanyDaoVariable.serviceKey);
  }
  public void send_addCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry newCompany) throws TException {
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
        create_addCompanyServiceCall(routeKey, timeout, platformArgs, newCompany), new TRequestOption());
  }

  public void send_addCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry newCompany,TRequestOption requestOption) throws TException { 
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
        create_addCompanyServiceCall(routeKey, timeout, platformArgs, newCompany), requestOption);
  }

  public long addCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry newCompany, IMethodCallback<CompanyDao.addCompany_args, CompanyDao.addCompany_result> callback) throws TException{
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
            create_addCompanyServiceCall(routeKey, timeout, platformArgs, newCompany), callback);
  }

  public long add_addCompanyCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyEntry newCompany, IMethodCallback<CompanyDao.addCompany_args, CompanyDao.addCompany_result> callback) throws TException{
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
            create_addCompanyServiceCall(routeKey, timeout, platformArgs, newCompany), callback);
  }

  protected TServiceCall create_addCompanyServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyEntry newCompany){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addCompany_args request = new CompanyDao.addCompany_args();
    request.setPlatformArgs(platformArgs);
    request.setNewCompany(newCompany);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addCompany");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addCompany_result.class);
    return serviceCall;
  }

  public void send_updateCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry updateCompany) throws TException {
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
        create_updateCompanyServiceCall(routeKey, timeout, platformArgs, updateCompany), new TRequestOption());
  }

  public void send_updateCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry updateCompany,TRequestOption requestOption) throws TException { 
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
        create_updateCompanyServiceCall(routeKey, timeout, platformArgs, updateCompany), requestOption);
  }

  public long updateCompany(int routeKey, int timeout, xueqiao.company.CompanyEntry updateCompany, IMethodCallback<CompanyDao.updateCompany_args, CompanyDao.updateCompany_result> callback) throws TException{
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
            create_updateCompanyServiceCall(routeKey, timeout, platformArgs, updateCompany), callback);
  }

  public long add_updateCompanyCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyEntry updateCompany, IMethodCallback<CompanyDao.updateCompany_args, CompanyDao.updateCompany_result> callback) throws TException{
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
            create_updateCompanyServiceCall(routeKey, timeout, platformArgs, updateCompany), callback);
  }

  protected TServiceCall create_updateCompanyServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyEntry updateCompany){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateCompany_args request = new CompanyDao.updateCompany_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateCompany(updateCompany);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateCompany");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateCompany_result.class);
    return serviceCall;
  }

  public void send_queryCompanyPage(int routeKey, int timeout, xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryCompanyPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryCompanyPage(int routeKey, int timeout, xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryCompanyPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryCompanyPage(int routeKey, int timeout, xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyPage_args, CompanyDao.queryCompanyPage_result> callback) throws TException{
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
            create_queryCompanyPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryCompanyPageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyPage_args, CompanyDao.queryCompanyPage_result> callback) throws TException{
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
            create_queryCompanyPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryCompanyPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryCompanyOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryCompanyPage_args request = new CompanyDao.queryCompanyPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCompanyPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryCompanyPage_result.class);
    return serviceCall;
  }

  public void send_addCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup) throws TException {
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
        create_addCompanyGroupServiceCall(routeKey, timeout, platformArgs, newGroup), new TRequestOption());
  }

  public void send_addCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup,TRequestOption requestOption) throws TException { 
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
        create_addCompanyGroupServiceCall(routeKey, timeout, platformArgs, newGroup), requestOption);
  }

  public long addCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, IMethodCallback<CompanyDao.addCompanyGroup_args, CompanyDao.addCompanyGroup_result> callback) throws TException{
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
            create_addCompanyGroupServiceCall(routeKey, timeout, platformArgs, newGroup), callback);
  }

  public long add_addCompanyGroupCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, IMethodCallback<CompanyDao.addCompanyGroup_args, CompanyDao.addCompanyGroup_result> callback) throws TException{
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
            create_addCompanyGroupServiceCall(routeKey, timeout, platformArgs, newGroup), callback);
  }

  protected TServiceCall create_addCompanyGroupServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyGroup newGroup){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addCompanyGroup_args request = new CompanyDao.addCompanyGroup_args();
    request.setPlatformArgs(platformArgs);
    request.setNewGroup(newGroup);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addCompanyGroup");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addCompanyGroup_result.class);
    return serviceCall;
  }

  public void send_updateCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup updateGroup) throws TException {
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
        create_updateCompanyGroupServiceCall(routeKey, timeout, platformArgs, updateGroup), new TRequestOption());
  }

  public void send_updateCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup updateGroup,TRequestOption requestOption) throws TException { 
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
        create_updateCompanyGroupServiceCall(routeKey, timeout, platformArgs, updateGroup), requestOption);
  }

  public long updateCompanyGroup(int routeKey, int timeout, xueqiao.company.CompanyGroup updateGroup, IMethodCallback<CompanyDao.updateCompanyGroup_args, CompanyDao.updateCompanyGroup_result> callback) throws TException{
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
            create_updateCompanyGroupServiceCall(routeKey, timeout, platformArgs, updateGroup), callback);
  }

  public long add_updateCompanyGroupCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyGroup updateGroup, IMethodCallback<CompanyDao.updateCompanyGroup_args, CompanyDao.updateCompanyGroup_result> callback) throws TException{
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
            create_updateCompanyGroupServiceCall(routeKey, timeout, platformArgs, updateGroup), callback);
  }

  protected TServiceCall create_updateCompanyGroupServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyGroup updateGroup){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateCompanyGroup_args request = new CompanyDao.updateCompanyGroup_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateGroup(updateGroup);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateCompanyGroup");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateCompanyGroup_result.class);
    return serviceCall;
  }

  public void send_deleteCompanyGroup(int routeKey, int timeout, int companyId, int groupId) throws TException {
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
        create_deleteCompanyGroupServiceCall(routeKey, timeout, platformArgs, companyId, groupId), new TRequestOption());
  }

  public void send_deleteCompanyGroup(int routeKey, int timeout, int companyId, int groupId,TRequestOption requestOption) throws TException { 
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
        create_deleteCompanyGroupServiceCall(routeKey, timeout, platformArgs, companyId, groupId), requestOption);
  }

  public long deleteCompanyGroup(int routeKey, int timeout, int companyId, int groupId, IMethodCallback<CompanyDao.deleteCompanyGroup_args, CompanyDao.deleteCompanyGroup_result> callback) throws TException{
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
            create_deleteCompanyGroupServiceCall(routeKey, timeout, platformArgs, companyId, groupId), callback);
  }

  public long add_deleteCompanyGroupCall(AsyncCallRunner runner, int routeKey, int timeout, int companyId, int groupId, IMethodCallback<CompanyDao.deleteCompanyGroup_args, CompanyDao.deleteCompanyGroup_result> callback) throws TException{
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
            create_deleteCompanyGroupServiceCall(routeKey, timeout, platformArgs, companyId, groupId), callback);
  }

  protected TServiceCall create_deleteCompanyGroupServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int companyId, int groupId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.deleteCompanyGroup_args request = new CompanyDao.deleteCompanyGroup_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyId(companyId);
    request.setGroupId(groupId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteCompanyGroup");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.deleteCompanyGroup_result.class);
    return serviceCall;
  }

  public void send_queryCompanyGroupPage(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryCompanyGroupPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), new TRequestOption());
  }

  public void send_queryCompanyGroupPage(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryCompanyGroupPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), requestOption);
  }

  public long queryCompanyGroupPage(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyGroupPage_args, CompanyDao.queryCompanyGroupPage_result> callback) throws TException{
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
            create_queryCompanyGroupPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  public long add_queryCompanyGroupPageCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyGroupPage_args, CompanyDao.queryCompanyGroupPage_result> callback) throws TException{
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
            create_queryCompanyGroupPageServiceCall(routeKey, timeout, platformArgs, queryOption, pageOption), callback);
  }

  protected TServiceCall create_queryCompanyGroupPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryCompanyGroupOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryCompanyGroupPage_args request = new CompanyDao.queryCompanyGroupPage_args();
    request.setPlatformArgs(platformArgs);
    request.setQueryOption(queryOption);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCompanyGroupPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryCompanyGroupPage_result.class);
    return serviceCall;
  }

  public void send_addCompanyGroupAndSpec(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec) throws TException {
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
        create_addCompanyGroupAndSpecServiceCall(routeKey, timeout, platformArgs, newGroup, newGroupSpec), new TRequestOption());
  }

  public void send_addCompanyGroupAndSpec(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec,TRequestOption requestOption) throws TException { 
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
        create_addCompanyGroupAndSpecServiceCall(routeKey, timeout, platformArgs, newGroup, newGroupSpec), requestOption);
  }

  public long addCompanyGroupAndSpec(int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec, IMethodCallback<CompanyDao.addCompanyGroupAndSpec_args, CompanyDao.addCompanyGroupAndSpec_result> callback) throws TException{
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
            create_addCompanyGroupAndSpecServiceCall(routeKey, timeout, platformArgs, newGroup, newGroupSpec), callback);
  }

  public long add_addCompanyGroupAndSpecCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec, IMethodCallback<CompanyDao.addCompanyGroupAndSpec_args, CompanyDao.addCompanyGroupAndSpec_result> callback) throws TException{
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
            create_addCompanyGroupAndSpecServiceCall(routeKey, timeout, platformArgs, newGroup, newGroupSpec), callback);
  }

  protected TServiceCall create_addCompanyGroupAndSpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyGroup newGroup, xueqiao.company.CompanyGroupSpec newGroupSpec){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addCompanyGroupAndSpec_args request = new CompanyDao.addCompanyGroupAndSpec_args();
    request.setPlatformArgs(platformArgs);
    request.setNewGroup(newGroup);
    request.setNewGroupSpec(newGroupSpec);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addCompanyGroupAndSpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addCompanyGroupAndSpec_result.class);
    return serviceCall;
  }

  public void send_updateCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.CompanyGroupSpec updateGroupSpec) throws TException {
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
        create_updateCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, updateGroupSpec), new TRequestOption());
  }

  public void send_updateCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.CompanyGroupSpec updateGroupSpec,TRequestOption requestOption) throws TException { 
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
        create_updateCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, updateGroupSpec), requestOption);
  }

  public long updateCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.CompanyGroupSpec updateGroupSpec, IMethodCallback<CompanyDao.updateCompanyGroupSpec_args, CompanyDao.updateCompanyGroupSpec_result> callback) throws TException{
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
            create_updateCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, updateGroupSpec), callback);
  }

  public long add_updateCompanyGroupSpecCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyGroupSpec updateGroupSpec, IMethodCallback<CompanyDao.updateCompanyGroupSpec_args, CompanyDao.updateCompanyGroupSpec_result> callback) throws TException{
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
            create_updateCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, updateGroupSpec), callback);
  }

  protected TServiceCall create_updateCompanyGroupSpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyGroupSpec updateGroupSpec){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateCompanyGroupSpec_args request = new CompanyDao.updateCompanyGroupSpec_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateGroupSpec(updateGroupSpec);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateCompanyGroupSpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateCompanyGroupSpec_result.class);
    return serviceCall;
  }

  public void send_addCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser) throws TException {
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
        create_addCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), new TRequestOption());
  }

  public void send_addCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser,TRequestOption requestOption) throws TException { 
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
        create_addCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), requestOption);
  }

  public long addCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser, IMethodCallback<CompanyDao.addCompanyUser_args, CompanyDao.addCompanyUser_result> callback) throws TException{
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
            create_addCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), callback);
  }

  public long add_addCompanyUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyUser companyUser, IMethodCallback<CompanyDao.addCompanyUser_args, CompanyDao.addCompanyUser_result> callback) throws TException{
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
            create_addCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), callback);
  }

  protected TServiceCall create_addCompanyUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyUser companyUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addCompanyUser_args request = new CompanyDao.addCompanyUser_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyUser(companyUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addCompanyUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addCompanyUser_result.class);
    return serviceCall;
  }

  public void send_updateCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser) throws TException {
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
        create_updateCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), new TRequestOption());
  }

  public void send_updateCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser,TRequestOption requestOption) throws TException { 
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
        create_updateCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), requestOption);
  }

  public long updateCompanyUser(int routeKey, int timeout, xueqiao.company.CompanyUser companyUser, IMethodCallback<CompanyDao.updateCompanyUser_args, CompanyDao.updateCompanyUser_result> callback) throws TException{
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
            create_updateCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), callback);
  }

  public long add_updateCompanyUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyUser companyUser, IMethodCallback<CompanyDao.updateCompanyUser_args, CompanyDao.updateCompanyUser_result> callback) throws TException{
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
            create_updateCompanyUserServiceCall(routeKey, timeout, platformArgs, companyUser), callback);
  }

  protected TServiceCall create_updateCompanyUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyUser companyUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateCompanyUser_args request = new CompanyDao.updateCompanyUser_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyUser(companyUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateCompanyUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateCompanyUser_result.class);
    return serviceCall;
  }

  public void send_queryCompanySpec(int routeKey, int timeout, xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryCompanySpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryCompanySpec(int routeKey, int timeout, xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryCompanySpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryCompanySpec(int routeKey, int timeout, xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanySpec_args, CompanyDao.queryCompanySpec_result> callback) throws TException{
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
            create_queryCompanySpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryCompanySpecCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanySpec_args, CompanyDao.queryCompanySpec_result> callback) throws TException{
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
            create_queryCompanySpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryCompanySpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryCompanySpecOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryCompanySpec_args request = new CompanyDao.queryCompanySpec_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCompanySpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryCompanySpec_result.class);
    return serviceCall;
  }

  public void send_addUser2Group(int routeKey, int timeout, xueqiao.company.GroupUser groupUser) throws TException {
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
        create_addUser2GroupServiceCall(routeKey, timeout, platformArgs, groupUser), new TRequestOption());
  }

  public void send_addUser2Group(int routeKey, int timeout, xueqiao.company.GroupUser groupUser,TRequestOption requestOption) throws TException { 
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
        create_addUser2GroupServiceCall(routeKey, timeout, platformArgs, groupUser), requestOption);
  }

  public long addUser2Group(int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.addUser2Group_args, CompanyDao.addUser2Group_result> callback) throws TException{
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
            create_addUser2GroupServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  public long add_addUser2GroupCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.addUser2Group_args, CompanyDao.addUser2Group_result> callback) throws TException{
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
            create_addUser2GroupServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  protected TServiceCall create_addUser2GroupServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.GroupUser groupUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addUser2Group_args request = new CompanyDao.addUser2Group_args();
    request.setPlatformArgs(platformArgs);
    request.setGroupUser(groupUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addUser2Group");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addUser2Group_result.class);
    return serviceCall;
  }

  public void send_removeGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser) throws TException {
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
        create_removeGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), new TRequestOption());
  }

  public void send_removeGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser,TRequestOption requestOption) throws TException { 
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
        create_removeGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), requestOption);
  }

  public long removeGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.removeGroupUser_args, CompanyDao.removeGroupUser_result> callback) throws TException{
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
            create_removeGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  public long add_removeGroupUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.removeGroupUser_args, CompanyDao.removeGroupUser_result> callback) throws TException{
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
            create_removeGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  protected TServiceCall create_removeGroupUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.GroupUser groupUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.removeGroupUser_args request = new CompanyDao.removeGroupUser_args();
    request.setPlatformArgs(platformArgs);
    request.setGroupUser(groupUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeGroupUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.removeGroupUser_result.class);
    return serviceCall;
  }

  public void send_queryCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyGroupSpec_args, CompanyDao.queryCompanyGroupSpec_result> callback) throws TException{
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
            create_queryCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryCompanyGroupSpecCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyGroupSpec_args, CompanyDao.queryCompanyGroupSpec_result> callback) throws TException{
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
            create_queryCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryCompanyGroupSpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryCompanyGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryCompanyGroupSpec_args request = new CompanyDao.queryCompanyGroupSpec_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCompanyGroupSpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryCompanyGroupSpec_result.class);
    return serviceCall;
  }

  public void send_queryCompanyUser(int routeKey, int timeout, xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryCompanyUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryCompanyUser(int routeKey, int timeout, xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryCompanyUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryCompanyUser(int routeKey, int timeout, xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyUser_args, CompanyDao.queryCompanyUser_result> callback) throws TException{
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
            create_queryCompanyUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryCompanyUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryCompanyUser_args, CompanyDao.queryCompanyUser_result> callback) throws TException{
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
            create_queryCompanyUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryCompanyUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryCompanyUserOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryCompanyUser_args request = new CompanyDao.queryCompanyUser_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryCompanyUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryCompanyUser_result.class);
    return serviceCall;
  }

  public void send_queryGroupUser(int routeKey, int timeout, xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryGroupUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryGroupUser(int routeKey, int timeout, xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryGroupUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryGroupUser(int routeKey, int timeout, xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryGroupUser_args, CompanyDao.queryGroupUser_result> callback) throws TException{
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
            create_queryGroupUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryGroupUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryGroupUser_args, CompanyDao.queryGroupUser_result> callback) throws TException{
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
            create_queryGroupUserServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryGroupUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryGroupUserOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryGroupUser_args request = new CompanyDao.queryGroupUser_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryGroupUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryGroupUser_result.class);
    return serviceCall;
  }

  public void send_updateGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser) throws TException {
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
        create_updateGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), new TRequestOption());
  }

  public void send_updateGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser,TRequestOption requestOption) throws TException { 
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
        create_updateGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), requestOption);
  }

  public long updateGroupUser(int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.updateGroupUser_args, CompanyDao.updateGroupUser_result> callback) throws TException{
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
            create_updateGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  public long add_updateGroupUserCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.GroupUser groupUser, IMethodCallback<CompanyDao.updateGroupUser_args, CompanyDao.updateGroupUser_result> callback) throws TException{
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
            create_updateGroupUserServiceCall(routeKey, timeout, platformArgs, groupUser), callback);
  }

  protected TServiceCall create_updateGroupUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.GroupUser groupUser){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateGroupUser_args request = new CompanyDao.updateGroupUser_args();
    request.setPlatformArgs(platformArgs);
    request.setGroupUser(groupUser);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateGroupUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateGroupUser_result.class);
    return serviceCall;
  }

  public void send_queryExpiredCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryExpiredCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryExpiredCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryExpiredCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryExpiredCompanyGroupSpec(int routeKey, int timeout, xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryExpiredCompanyGroupSpec_args, CompanyDao.queryExpiredCompanyGroupSpec_result> callback) throws TException{
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
            create_queryExpiredCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryExpiredCompanyGroupSpecCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryExpiredCompanyGroupSpec_args, CompanyDao.queryExpiredCompanyGroupSpec_result> callback) throws TException{
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
            create_queryExpiredCompanyGroupSpecServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryExpiredCompanyGroupSpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryExpiredGroupSpecOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryExpiredCompanyGroupSpec_args request = new CompanyDao.queryExpiredCompanyGroupSpec_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryExpiredCompanyGroupSpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryExpiredCompanyGroupSpec_result.class);
    return serviceCall;
  }

  public void send_addCompanyUserEx(int routeKey, int timeout, xueqiao.company.CompanyUserEx companyUserEx) throws TException {
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
        create_addCompanyUserExServiceCall(routeKey, timeout, platformArgs, companyUserEx), new TRequestOption());
  }

  public void send_addCompanyUserEx(int routeKey, int timeout, xueqiao.company.CompanyUserEx companyUserEx,TRequestOption requestOption) throws TException { 
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
        create_addCompanyUserExServiceCall(routeKey, timeout, platformArgs, companyUserEx), requestOption);
  }

  public long addCompanyUserEx(int routeKey, int timeout, xueqiao.company.CompanyUserEx companyUserEx, IMethodCallback<CompanyDao.addCompanyUserEx_args, CompanyDao.addCompanyUserEx_result> callback) throws TException{
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
            create_addCompanyUserExServiceCall(routeKey, timeout, platformArgs, companyUserEx), callback);
  }

  public long add_addCompanyUserExCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.CompanyUserEx companyUserEx, IMethodCallback<CompanyDao.addCompanyUserEx_args, CompanyDao.addCompanyUserEx_result> callback) throws TException{
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
            create_addCompanyUserExServiceCall(routeKey, timeout, platformArgs, companyUserEx), callback);
  }

  protected TServiceCall create_addCompanyUserExServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.CompanyUserEx companyUserEx){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.addCompanyUserEx_args request = new CompanyDao.addCompanyUserEx_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyUserEx(companyUserEx);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addCompanyUserEx");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.addCompanyUserEx_result.class);
    return serviceCall;
  }

  public void send_updateCompanyUserPassword(int routeKey, int timeout, xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq) throws TException {
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
        create_updateCompanyUserPasswordServiceCall(routeKey, timeout, platformArgs, updateCompanyUserPasswordReq), new TRequestOption());
  }

  public void send_updateCompanyUserPassword(int routeKey, int timeout, xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq,TRequestOption requestOption) throws TException { 
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
        create_updateCompanyUserPasswordServiceCall(routeKey, timeout, platformArgs, updateCompanyUserPasswordReq), requestOption);
  }

  public long updateCompanyUserPassword(int routeKey, int timeout, xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq, IMethodCallback<CompanyDao.updateCompanyUserPassword_args, CompanyDao.updateCompanyUserPassword_result> callback) throws TException{
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
            create_updateCompanyUserPasswordServiceCall(routeKey, timeout, platformArgs, updateCompanyUserPasswordReq), callback);
  }

  public long add_updateCompanyUserPasswordCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq, IMethodCallback<CompanyDao.updateCompanyUserPassword_args, CompanyDao.updateCompanyUserPassword_result> callback) throws TException{
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
            create_updateCompanyUserPasswordServiceCall(routeKey, timeout, platformArgs, updateCompanyUserPasswordReq), callback);
  }

  protected TServiceCall create_updateCompanyUserPasswordServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.updateCompanyUserPassword_args request = new CompanyDao.updateCompanyUserPassword_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateCompanyUserPasswordReq(updateCompanyUserPasswordReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateCompanyUserPassword");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.updateCompanyUserPassword_result.class);
    return serviceCall;
  }

  public void send_submitInitHosingTask(int routeKey, int timeout, xueqiao.company.InitHostingMachineReq initHostingMachineReq) throws TException {
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
        create_submitInitHosingTaskServiceCall(routeKey, timeout, platformArgs, initHostingMachineReq), new TRequestOption());
  }

  public void send_submitInitHosingTask(int routeKey, int timeout, xueqiao.company.InitHostingMachineReq initHostingMachineReq,TRequestOption requestOption) throws TException { 
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
        create_submitInitHosingTaskServiceCall(routeKey, timeout, platformArgs, initHostingMachineReq), requestOption);
  }

  public long submitInitHosingTask(int routeKey, int timeout, xueqiao.company.InitHostingMachineReq initHostingMachineReq, IMethodCallback<CompanyDao.submitInitHosingTask_args, CompanyDao.submitInitHosingTask_result> callback) throws TException{
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
            create_submitInitHosingTaskServiceCall(routeKey, timeout, platformArgs, initHostingMachineReq), callback);
  }

  public long add_submitInitHosingTaskCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.InitHostingMachineReq initHostingMachineReq, IMethodCallback<CompanyDao.submitInitHosingTask_args, CompanyDao.submitInitHosingTask_result> callback) throws TException{
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
            create_submitInitHosingTaskServiceCall(routeKey, timeout, platformArgs, initHostingMachineReq), callback);
  }

  protected TServiceCall create_submitInitHosingTaskServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.InitHostingMachineReq initHostingMachineReq){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.submitInitHosingTask_args request = new CompanyDao.submitInitHosingTask_args();
    request.setPlatformArgs(platformArgs);
    request.setInitHostingMachineReq(initHostingMachineReq);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("submitInitHosingTask");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.submitInitHosingTask_result.class);
    return serviceCall;
  }

  public void send_doAfterInitHosting(int routeKey, int timeout, xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask) throws TException {
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
        create_doAfterInitHostingServiceCall(routeKey, timeout, platformArgs, initHostingTask), new TRequestOption());
  }

  public void send_doAfterInitHosting(int routeKey, int timeout, xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask,TRequestOption requestOption) throws TException { 
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
        create_doAfterInitHostingServiceCall(routeKey, timeout, platformArgs, initHostingTask), requestOption);
  }

  public long doAfterInitHosting(int routeKey, int timeout, xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask, IMethodCallback<CompanyDao.doAfterInitHosting_args, CompanyDao.doAfterInitHosting_result> callback) throws TException{
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
            create_doAfterInitHostingServiceCall(routeKey, timeout, platformArgs, initHostingTask), callback);
  }

  public long add_doAfterInitHostingCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask, IMethodCallback<CompanyDao.doAfterInitHosting_args, CompanyDao.doAfterInitHosting_result> callback) throws TException{
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
            create_doAfterInitHostingServiceCall(routeKey, timeout, platformArgs, initHostingTask), callback);
  }

  protected TServiceCall create_doAfterInitHostingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.doAfterInitHosting_args request = new CompanyDao.doAfterInitHosting_args();
    request.setPlatformArgs(platformArgs);
    request.setInitHostingTask(initHostingTask);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("doAfterInitHosting");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.doAfterInitHosting_result.class);
    return serviceCall;
  }

  public void send_doUpgradeGroupSpec(int routeKey, int timeout, int orderId, String oaUserName) throws TException {
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
        create_doUpgradeGroupSpecServiceCall(routeKey, timeout, platformArgs, orderId, oaUserName), new TRequestOption());
  }

  public void send_doUpgradeGroupSpec(int routeKey, int timeout, int orderId, String oaUserName,TRequestOption requestOption) throws TException { 
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
        create_doUpgradeGroupSpecServiceCall(routeKey, timeout, platformArgs, orderId, oaUserName), requestOption);
  }

  public long doUpgradeGroupSpec(int routeKey, int timeout, int orderId, String oaUserName, IMethodCallback<CompanyDao.doUpgradeGroupSpec_args, CompanyDao.doUpgradeGroupSpec_result> callback) throws TException{
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
            create_doUpgradeGroupSpecServiceCall(routeKey, timeout, platformArgs, orderId, oaUserName), callback);
  }

  public long add_doUpgradeGroupSpecCall(AsyncCallRunner runner, int routeKey, int timeout, int orderId, String oaUserName, IMethodCallback<CompanyDao.doUpgradeGroupSpec_args, CompanyDao.doUpgradeGroupSpec_result> callback) throws TException{
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
            create_doUpgradeGroupSpecServiceCall(routeKey, timeout, platformArgs, orderId, oaUserName), callback);
  }

  protected TServiceCall create_doUpgradeGroupSpecServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int orderId, String oaUserName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.doUpgradeGroupSpec_args request = new CompanyDao.doUpgradeGroupSpec_args();
    request.setPlatformArgs(platformArgs);
    request.setOrderId(orderId);
    request.setOaUserName(oaUserName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("doUpgradeGroupSpec");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.doUpgradeGroupSpec_result.class);
    return serviceCall;
  }

  public void send_queryGroupUserEx(int routeKey, int timeout, xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_queryGroupUserExServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_queryGroupUserEx(int routeKey, int timeout, xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_queryGroupUserExServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long queryGroupUserEx(int routeKey, int timeout, xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryGroupUserEx_args, CompanyDao.queryGroupUserEx_result> callback) throws TException{
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
            create_queryGroupUserExServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_queryGroupUserExCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyDao.queryGroupUserEx_args, CompanyDao.queryGroupUserEx_result> callback) throws TException{
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
            create_queryGroupUserExServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_queryGroupUserExServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.QueryGroupUserExOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.queryGroupUserEx_args request = new CompanyDao.queryGroupUserEx_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryGroupUserEx");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.queryGroupUserEx_result.class);
    return serviceCall;
  }

  public void send_getCollectiveCompany(int routeKey, int timeout) throws TException {
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
        create_getCollectiveCompanyServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getCollectiveCompany(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_getCollectiveCompanyServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getCollectiveCompany(int routeKey, int timeout, IMethodCallback<CompanyDao.getCollectiveCompany_args, CompanyDao.getCollectiveCompany_result> callback) throws TException{
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
            create_getCollectiveCompanyServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getCollectiveCompanyCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<CompanyDao.getCollectiveCompany_args, CompanyDao.getCollectiveCompany_result> callback) throws TException{
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
            create_getCollectiveCompanyServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getCollectiveCompanyServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyDao.getCollectiveCompany_args request = new CompanyDao.getCollectiveCompany_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getCollectiveCompany");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyDao.getCollectiveCompany_result.class);
    return serviceCall;
  }

}
