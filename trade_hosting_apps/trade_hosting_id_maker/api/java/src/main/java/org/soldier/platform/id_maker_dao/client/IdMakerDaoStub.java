package org.soldier.platform.id_maker_dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.IdMakerDao;
import org.soldier.platform.id_maker_dao.IdMakerDaoVariable;

public class IdMakerDaoStub extends BaseStub {

  public IdMakerDaoStub() {
    super(IdMakerDaoVariable.serviceKey);
  }

  public IdAllocResult  allocIds(int type) throws ErrorInfo, TException{
    return allocIds(type, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public IdAllocResult  allocIds(int type,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("allocIds").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<IdAllocResult>(){
    @Override
    public IdAllocResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new IdMakerDao.Client(protocol).allocIds(platformArgs, type);
      }
    }, invokeInfo);
  }

  public IdAllocResult  allocIds(int routeKey, int timeout,int type)throws ErrorInfo, TException{
    return allocIds(type, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  registerType(IdMakerInfo info) throws ErrorInfo, TException{
    registerType(info, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  registerType(IdMakerInfo info,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("registerType").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new IdMakerDao.Client(protocol).registerType(platformArgs, info);
      return null;
      }
    }, invokeInfo);
  }

  public void  registerType(int routeKey, int timeout,IdMakerInfo info)throws ErrorInfo, TException{
    registerType(info, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateType(IdMakerInfo info) throws ErrorInfo, TException{
    updateType(info, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateType(IdMakerInfo info,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateType").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new IdMakerDao.Client(protocol).updateType(platformArgs, info);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateType(int routeKey, int timeout,IdMakerInfo info)throws ErrorInfo, TException{
    updateType(info, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public IdMakerInfo  getIdMakerInfoByType(int type) throws ErrorInfo, TException{
    return getIdMakerInfoByType(type, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public IdMakerInfo  getIdMakerInfoByType(int type,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getIdMakerInfoByType").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<IdMakerInfo>(){
    @Override
    public IdMakerInfo call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new IdMakerDao.Client(protocol).getIdMakerInfoByType(platformArgs, type);
      }
    }, invokeInfo);
  }

  public IdMakerInfo  getIdMakerInfoByType(int routeKey, int timeout,int type)throws ErrorInfo, TException{
    return getIdMakerInfoByType(type, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public IdMakerInfoList  queryIdMakerTypeInfoList(int pageIndex, int pageSize, IdMakerQueryOption option) throws ErrorInfo, TException{
    return queryIdMakerTypeInfoList(pageIndex, pageSize, option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public IdMakerInfoList  queryIdMakerTypeInfoList(int pageIndex, int pageSize, IdMakerQueryOption option,TStubOption platformStubOption) throws ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryIdMakerTypeInfoList").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<IdMakerInfoList>(){
    @Override
    public IdMakerInfoList call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new IdMakerDao.Client(protocol).queryIdMakerTypeInfoList(platformArgs, pageIndex, pageSize, option);
      }
    }, invokeInfo);
  }

  public IdMakerInfoList  queryIdMakerTypeInfoList(int routeKey, int timeout,int pageIndex, int pageSize, IdMakerQueryOption option)throws ErrorInfo, TException{
    return queryIdMakerTypeInfoList(pageIndex, pageSize, option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
