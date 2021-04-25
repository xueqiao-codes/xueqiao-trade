package xueqiao.company.service.maintenance.dao.thriftapi.client;

import xueqiao.company.service.maintenance.dao.thriftapi.CompanyServiceMaintenanceDao;
import xueqiao.company.service.maintenance.dao.thriftapi.CompanyServiceMaintenanceDaoVariable;
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
import java.util.Set;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqScheduleOperateDetailOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqServiceMaintenanceOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ServiceMaintenancePage;

public class CompanyServiceMaintenanceDaoAsyncStub extends BaseStub { 
  public CompanyServiceMaintenanceDaoAsyncStub() {
    super(CompanyServiceMaintenanceDaoVariable.serviceKey);
  }
  public void send_addServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance) throws TException {
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
        create_addServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), new TRequestOption());
  }

  public void send_addServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance,TRequestOption requestOption) throws TException { 
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
        create_addServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), requestOption);
  }

  public long addServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance, IMethodCallback<CompanyServiceMaintenanceDao.addServiceMaintenance_args, CompanyServiceMaintenanceDao.addServiceMaintenance_result> callback) throws TException{
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
            create_addServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), callback);
  }

  public long add_addServiceMaintenanceCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance, IMethodCallback<CompanyServiceMaintenanceDao.addServiceMaintenance_args, CompanyServiceMaintenanceDao.addServiceMaintenance_result> callback) throws TException{
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
            create_addServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), callback);
  }

  protected TServiceCall create_addServiceMaintenanceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.addServiceMaintenance_args request = new CompanyServiceMaintenanceDao.addServiceMaintenance_args();
    request.setPlatformArgs(platformArgs);
    request.setServiceMaintenance(serviceMaintenance);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addServiceMaintenance");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.addServiceMaintenance_result.class);
    return serviceCall;
  }

  public void send_updateServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance) throws TException {
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
        create_updateServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), new TRequestOption());
  }

  public void send_updateServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance,TRequestOption requestOption) throws TException { 
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
        create_updateServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), requestOption);
  }

  public long updateServiceMaintenance(int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance, IMethodCallback<CompanyServiceMaintenanceDao.updateServiceMaintenance_args, CompanyServiceMaintenanceDao.updateServiceMaintenance_result> callback) throws TException{
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
            create_updateServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), callback);
  }

  public long add_updateServiceMaintenanceCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance, IMethodCallback<CompanyServiceMaintenanceDao.updateServiceMaintenance_args, CompanyServiceMaintenanceDao.updateServiceMaintenance_result> callback) throws TException{
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
            create_updateServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, serviceMaintenance), callback);
  }

  protected TServiceCall create_updateServiceMaintenanceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.updateServiceMaintenance_args request = new CompanyServiceMaintenanceDao.updateServiceMaintenance_args();
    request.setPlatformArgs(platformArgs);
    request.setServiceMaintenance(serviceMaintenance);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateServiceMaintenance");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.updateServiceMaintenance_result.class);
    return serviceCall;
  }

  public void send_reqServiceMaintenance(int routeKey, int timeout, ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_reqServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_reqServiceMaintenance(int routeKey, int timeout, ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_reqServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long reqServiceMaintenance(int routeKey, int timeout, ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyServiceMaintenanceDao.reqServiceMaintenance_args, CompanyServiceMaintenanceDao.reqServiceMaintenance_result> callback) throws TException{
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
            create_reqServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_reqServiceMaintenanceCall(AsyncCallRunner runner, int routeKey, int timeout, ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<CompanyServiceMaintenanceDao.reqServiceMaintenance_args, CompanyServiceMaintenanceDao.reqServiceMaintenance_result> callback) throws TException{
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
            create_reqServiceMaintenanceServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_reqServiceMaintenanceServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.reqServiceMaintenance_args request = new CompanyServiceMaintenanceDao.reqServiceMaintenance_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqServiceMaintenance");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.reqServiceMaintenance_result.class);
    return serviceCall;
  }

  public void send_reqMaintenanceHistory(int routeKey, int timeout, Set<Long> companyIds) throws TException {
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
        create_reqMaintenanceHistoryServiceCall(routeKey, timeout, platformArgs, companyIds), new TRequestOption());
  }

  public void send_reqMaintenanceHistory(int routeKey, int timeout, Set<Long> companyIds,TRequestOption requestOption) throws TException { 
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
        create_reqMaintenanceHistoryServiceCall(routeKey, timeout, platformArgs, companyIds), requestOption);
  }

  public long reqMaintenanceHistory(int routeKey, int timeout, Set<Long> companyIds, IMethodCallback<CompanyServiceMaintenanceDao.reqMaintenanceHistory_args, CompanyServiceMaintenanceDao.reqMaintenanceHistory_result> callback) throws TException{
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
            create_reqMaintenanceHistoryServiceCall(routeKey, timeout, platformArgs, companyIds), callback);
  }

  public long add_reqMaintenanceHistoryCall(AsyncCallRunner runner, int routeKey, int timeout, Set<Long> companyIds, IMethodCallback<CompanyServiceMaintenanceDao.reqMaintenanceHistory_args, CompanyServiceMaintenanceDao.reqMaintenanceHistory_result> callback) throws TException{
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
            create_reqMaintenanceHistoryServiceCall(routeKey, timeout, platformArgs, companyIds), callback);
  }

  protected TServiceCall create_reqMaintenanceHistoryServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<Long> companyIds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.reqMaintenanceHistory_args request = new CompanyServiceMaintenanceDao.reqMaintenanceHistory_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyIds(companyIds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqMaintenanceHistory");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.reqMaintenanceHistory_result.class);
    return serviceCall;
  }

  public void send_addScheduleOperateDetail(int routeKey, int timeout, List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details) throws TException {
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
        create_addScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, details), new TRequestOption());
  }

  public void send_addScheduleOperateDetail(int routeKey, int timeout, List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details,TRequestOption requestOption) throws TException { 
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
        create_addScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, details), requestOption);
  }

  public long addScheduleOperateDetail(int routeKey, int timeout, List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details, IMethodCallback<CompanyServiceMaintenanceDao.addScheduleOperateDetail_args, CompanyServiceMaintenanceDao.addScheduleOperateDetail_result> callback) throws TException{
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
            create_addScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, details), callback);
  }

  public long add_addScheduleOperateDetailCall(AsyncCallRunner runner, int routeKey, int timeout, List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details, IMethodCallback<CompanyServiceMaintenanceDao.addScheduleOperateDetail_args, CompanyServiceMaintenanceDao.addScheduleOperateDetail_result> callback) throws TException{
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
            create_addScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, details), callback);
  }

  protected TServiceCall create_addScheduleOperateDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.addScheduleOperateDetail_args request = new CompanyServiceMaintenanceDao.addScheduleOperateDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setDetails(details);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addScheduleOperateDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.addScheduleOperateDetail_result.class);
    return serviceCall;
  }

  public void send_updateScheduleOperateDetail(int routeKey, int timeout, xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail) throws TException {
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
        create_updateScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, scheduleOperateDetail), new TRequestOption());
  }

  public void send_updateScheduleOperateDetail(int routeKey, int timeout, xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail,TRequestOption requestOption) throws TException { 
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
        create_updateScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, scheduleOperateDetail), requestOption);
  }

  public long updateScheduleOperateDetail(int routeKey, int timeout, xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail, IMethodCallback<CompanyServiceMaintenanceDao.updateScheduleOperateDetail_args, CompanyServiceMaintenanceDao.updateScheduleOperateDetail_result> callback) throws TException{
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
            create_updateScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, scheduleOperateDetail), callback);
  }

  public long add_updateScheduleOperateDetailCall(AsyncCallRunner runner, int routeKey, int timeout, xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail, IMethodCallback<CompanyServiceMaintenanceDao.updateScheduleOperateDetail_args, CompanyServiceMaintenanceDao.updateScheduleOperateDetail_result> callback) throws TException{
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
            create_updateScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, scheduleOperateDetail), callback);
  }

  protected TServiceCall create_updateScheduleOperateDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.updateScheduleOperateDetail_args request = new CompanyServiceMaintenanceDao.updateScheduleOperateDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setScheduleOperateDetail(scheduleOperateDetail);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateScheduleOperateDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.updateScheduleOperateDetail_result.class);
    return serviceCall;
  }

  public void send_reqScheduleOperateDetail(int routeKey, int timeout, ReqScheduleOperateDetailOption option) throws TException {
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
        create_reqScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_reqScheduleOperateDetail(int routeKey, int timeout, ReqScheduleOperateDetailOption option,TRequestOption requestOption) throws TException { 
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
        create_reqScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long reqScheduleOperateDetail(int routeKey, int timeout, ReqScheduleOperateDetailOption option, IMethodCallback<CompanyServiceMaintenanceDao.reqScheduleOperateDetail_args, CompanyServiceMaintenanceDao.reqScheduleOperateDetail_result> callback) throws TException{
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
            create_reqScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_reqScheduleOperateDetailCall(AsyncCallRunner runner, int routeKey, int timeout, ReqScheduleOperateDetailOption option, IMethodCallback<CompanyServiceMaintenanceDao.reqScheduleOperateDetail_args, CompanyServiceMaintenanceDao.reqScheduleOperateDetail_result> callback) throws TException{
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
            create_reqScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_reqScheduleOperateDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqScheduleOperateDetailOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.reqScheduleOperateDetail_args request = new CompanyServiceMaintenanceDao.reqScheduleOperateDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqScheduleOperateDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.reqScheduleOperateDetail_result.class);
    return serviceCall;
  }

  public void send_removeScheduleOperateDetail(int routeKey, int timeout, long companyId) throws TException {
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
        create_removeScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, companyId), new TRequestOption());
  }

  public void send_removeScheduleOperateDetail(int routeKey, int timeout, long companyId,TRequestOption requestOption) throws TException { 
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
        create_removeScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, companyId), requestOption);
  }

  public long removeScheduleOperateDetail(int routeKey, int timeout, long companyId, IMethodCallback<CompanyServiceMaintenanceDao.removeScheduleOperateDetail_args, CompanyServiceMaintenanceDao.removeScheduleOperateDetail_result> callback) throws TException{
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
            create_removeScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, companyId), callback);
  }

  public long add_removeScheduleOperateDetailCall(AsyncCallRunner runner, int routeKey, int timeout, long companyId, IMethodCallback<CompanyServiceMaintenanceDao.removeScheduleOperateDetail_args, CompanyServiceMaintenanceDao.removeScheduleOperateDetail_result> callback) throws TException{
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
            create_removeScheduleOperateDetailServiceCall(routeKey, timeout, platformArgs, companyId), callback);
  }

  protected TServiceCall create_removeScheduleOperateDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long companyId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(CompanyServiceMaintenanceDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    CompanyServiceMaintenanceDao.removeScheduleOperateDetail_args request = new CompanyServiceMaintenanceDao.removeScheduleOperateDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setCompanyId(companyId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeScheduleOperateDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(CompanyServiceMaintenanceDao.removeScheduleOperateDetail_result.class);
    return serviceCall;
  }

}
