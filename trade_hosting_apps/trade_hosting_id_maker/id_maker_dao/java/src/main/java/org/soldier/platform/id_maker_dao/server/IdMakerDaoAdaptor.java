package org.soldier.platform.id_maker_dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.IdMakerDao;
import org.soldier.platform.id_maker_dao.IdMakerDaoVariable;


public abstract class IdMakerDaoAdaptor implements IdMakerDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public IdAllocResult allocIds(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerDaoVariable.serviceKey,"allocIds",platformArgs);
    return allocIds(oCntl, type);
  }

  protected abstract IdAllocResult allocIds(TServiceCntl oCntl, int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void registerType(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, IdMakerInfo info) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerDaoVariable.serviceKey,"registerType",platformArgs);
registerType(oCntl, info);
  }

  protected abstract void registerType(TServiceCntl oCntl, IdMakerInfo info) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateType(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, IdMakerInfo info) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerDaoVariable.serviceKey,"updateType",platformArgs);
updateType(oCntl, info);
  }

  protected abstract void updateType(TServiceCntl oCntl, IdMakerInfo info) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public IdMakerInfo getIdMakerInfoByType(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerDaoVariable.serviceKey,"getIdMakerInfoByType",platformArgs);
    return getIdMakerInfoByType(oCntl, type);
  }

  protected abstract IdMakerInfo getIdMakerInfoByType(TServiceCntl oCntl, int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public IdMakerInfoList queryIdMakerTypeInfoList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, IdMakerQueryOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerDaoVariable.serviceKey,"queryIdMakerTypeInfoList",platformArgs);
    return queryIdMakerTypeInfoList(oCntl, pageIndex, pageSize, option);
  }

  protected abstract IdMakerInfoList queryIdMakerTypeInfoList(TServiceCntl oCntl, int pageIndex, int pageSize, IdMakerQueryOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected IdMakerDaoAdaptor(){
    methodParameterNameMap.put("allocIds",new String[]{"platformArgs", "type"});
    methodParameterNameMap.put("registerType",new String[]{"platformArgs", "info"});
    methodParameterNameMap.put("updateType",new String[]{"platformArgs", "info"});
    methodParameterNameMap.put("getIdMakerInfoByType",new String[]{"platformArgs", "type"});
    methodParameterNameMap.put("queryIdMakerTypeInfoList",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
