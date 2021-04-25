package xueqiao.company.service.maintenance.dao.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Set;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqScheduleOperateDetailOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqServiceMaintenanceOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ServiceMaintenancePage;
import xueqiao.company.service.maintenance.dao.thriftapi.CompanyServiceMaintenanceDao;
import xueqiao.company.service.maintenance.dao.thriftapi.CompanyServiceMaintenanceDaoVariable;

public class CompanyServiceMaintenanceDaoStub extends BaseStub {

  public CompanyServiceMaintenanceDaoStub() {
    super(CompanyServiceMaintenanceDaoVariable.serviceKey);
  }

  public void  addServiceMaintenance(xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addServiceMaintenance(serviceMaintenance, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addServiceMaintenance(xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addServiceMaintenance").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyServiceMaintenanceDao.Client(protocol).addServiceMaintenance(platformArgs, serviceMaintenance);
      return null;
      }
    }, invokeInfo);
  }

  public void  addServiceMaintenance(int routeKey, int timeout,xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addServiceMaintenance(serviceMaintenance, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateServiceMaintenance(xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateServiceMaintenance(serviceMaintenance, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateServiceMaintenance(xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateServiceMaintenance").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyServiceMaintenanceDao.Client(protocol).updateServiceMaintenance(platformArgs, serviceMaintenance);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateServiceMaintenance(int routeKey, int timeout,xueqiao.company.service.maintenance.ServiceMaintenance serviceMaintenance)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateServiceMaintenance(serviceMaintenance, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ServiceMaintenancePage  reqServiceMaintenance(ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqServiceMaintenance(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ServiceMaintenancePage  reqServiceMaintenance(ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqServiceMaintenance").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ServiceMaintenancePage>(){
    @Override
    public ServiceMaintenancePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyServiceMaintenanceDao.Client(protocol).reqServiceMaintenance(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public ServiceMaintenancePage  reqServiceMaintenance(int routeKey, int timeout,ReqServiceMaintenanceOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqServiceMaintenance(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.company.service.maintenance.MaintenanceHistory>  reqMaintenanceHistory(Set<Long> companyIds) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqMaintenanceHistory(companyIds, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.company.service.maintenance.MaintenanceHistory>  reqMaintenanceHistory(Set<Long> companyIds,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqMaintenanceHistory").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.company.service.maintenance.MaintenanceHistory>>(){
    @Override
    public List<xueqiao.company.service.maintenance.MaintenanceHistory> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyServiceMaintenanceDao.Client(protocol).reqMaintenanceHistory(platformArgs, companyIds);
      }
    }, invokeInfo);
  }

  public List<xueqiao.company.service.maintenance.MaintenanceHistory>  reqMaintenanceHistory(int routeKey, int timeout,Set<Long> companyIds)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqMaintenanceHistory(companyIds, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addScheduleOperateDetail(List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addScheduleOperateDetail(details, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addScheduleOperateDetail(List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addScheduleOperateDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyServiceMaintenanceDao.Client(protocol).addScheduleOperateDetail(platformArgs, details);
      return null;
      }
    }, invokeInfo);
  }

  public void  addScheduleOperateDetail(int routeKey, int timeout,List<xueqiao.company.service.maintenance.ScheduleOperateDetail> details)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addScheduleOperateDetail(details, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateScheduleOperateDetail(xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateScheduleOperateDetail(scheduleOperateDetail, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateScheduleOperateDetail(xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateScheduleOperateDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyServiceMaintenanceDao.Client(protocol).updateScheduleOperateDetail(platformArgs, scheduleOperateDetail);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateScheduleOperateDetail(int routeKey, int timeout,xueqiao.company.service.maintenance.ScheduleOperateDetail scheduleOperateDetail)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateScheduleOperateDetail(scheduleOperateDetail, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.company.service.maintenance.ScheduleOperateDetail>  reqScheduleOperateDetail(ReqScheduleOperateDetailOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqScheduleOperateDetail(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<xueqiao.company.service.maintenance.ScheduleOperateDetail>  reqScheduleOperateDetail(ReqScheduleOperateDetailOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqScheduleOperateDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<xueqiao.company.service.maintenance.ScheduleOperateDetail>>(){
    @Override
    public List<xueqiao.company.service.maintenance.ScheduleOperateDetail> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new CompanyServiceMaintenanceDao.Client(protocol).reqScheduleOperateDetail(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<xueqiao.company.service.maintenance.ScheduleOperateDetail>  reqScheduleOperateDetail(int routeKey, int timeout,ReqScheduleOperateDetailOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqScheduleOperateDetail(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeScheduleOperateDetail(long companyId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeScheduleOperateDetail(companyId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  removeScheduleOperateDetail(long companyId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("removeScheduleOperateDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new CompanyServiceMaintenanceDao.Client(protocol).removeScheduleOperateDetail(platformArgs, companyId);
      return null;
      }
    }, invokeInfo);
  }

  public void  removeScheduleOperateDetail(int routeKey, int timeout,long companyId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    removeScheduleOperateDetail(companyId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
