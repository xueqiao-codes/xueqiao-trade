package org.soldier.platform.id_maker_dao.client;

import org.soldier.platform.id_maker_dao.IdMakerDao;
import org.soldier.platform.id_maker_dao.IdMakerDaoVariable;
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
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;

public class IdMakerDaoAsyncStub extends BaseStub {
  public IdMakerDaoAsyncStub() {
    super(IdMakerDaoVariable.serviceKey);
  }
  public void send_allocIds(int routeKey, int timeout, int type) throws TException {
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
        create_allocIdsServiceCall(routeKey, timeout, platformArgs, type), new TRequestOption());
  }

  public void send_allocIds(int routeKey, int timeout, int type,TRequestOption requestOption) throws TException { 
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
        create_allocIdsServiceCall(routeKey, timeout, platformArgs, type), requestOption);
  }

  public long allocIds(int routeKey, int timeout, int type, IMethodCallback<IdMakerDao.allocIds_args, IdMakerDao.allocIds_result> callback) throws TException{
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
            create_allocIdsServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  public long add_allocIdsCall(AsyncCallRunner runner, int routeKey, int timeout, int type, IMethodCallback<IdMakerDao.allocIds_args, IdMakerDao.allocIds_result> callback) throws TException{
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
            create_allocIdsServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  protected TServiceCall create_allocIdsServiceCall(int routeKey, int timeout, PlatformArgs platformArgs, int type){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(IdMakerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMakerDao.allocIds_args request = new IdMakerDao.allocIds_args();
    request.setPlatformArgs(platformArgs);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("allocIds");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMakerDao.allocIds_result.class);
    return serviceCall;
  }

  public void send_registerType(int routeKey, int timeout, IdMakerInfo info) throws TException {
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
        create_registerTypeServiceCall(routeKey, timeout, platformArgs, info), new TRequestOption());
  }

  public void send_registerType(int routeKey, int timeout, IdMakerInfo info,TRequestOption requestOption) throws TException { 
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
        create_registerTypeServiceCall(routeKey, timeout, platformArgs, info), requestOption);
  }

  public long registerType(int routeKey, int timeout, IdMakerInfo info, IMethodCallback<IdMakerDao.registerType_args, IdMakerDao.registerType_result> callback) throws TException{
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
            create_registerTypeServiceCall(routeKey, timeout, platformArgs, info), callback);
  }

  public long add_registerTypeCall(AsyncCallRunner runner, int routeKey, int timeout, IdMakerInfo info, IMethodCallback<IdMakerDao.registerType_args, IdMakerDao.registerType_result> callback) throws TException{
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
            create_registerTypeServiceCall(routeKey, timeout, platformArgs, info), callback);
  }

  protected TServiceCall create_registerTypeServiceCall(int routeKey, int timeout, PlatformArgs platformArgs, IdMakerInfo info){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(IdMakerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMakerDao.registerType_args request = new IdMakerDao.registerType_args();
    request.setPlatformArgs(platformArgs);
    request.setInfo(info);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("registerType");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMakerDao.registerType_result.class);
    return serviceCall;
  }

  public void send_updateType(int routeKey, int timeout, IdMakerInfo info) throws TException {
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
        create_updateTypeServiceCall(routeKey, timeout, platformArgs, info), new TRequestOption());
  }

  public void send_updateType(int routeKey, int timeout, IdMakerInfo info,TRequestOption requestOption) throws TException { 
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
        create_updateTypeServiceCall(routeKey, timeout, platformArgs, info), requestOption);
  }

  public long updateType(int routeKey, int timeout, IdMakerInfo info, IMethodCallback<IdMakerDao.updateType_args, IdMakerDao.updateType_result> callback) throws TException{
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
            create_updateTypeServiceCall(routeKey, timeout, platformArgs, info), callback);
  }

  public long add_updateTypeCall(AsyncCallRunner runner, int routeKey, int timeout, IdMakerInfo info, IMethodCallback<IdMakerDao.updateType_args, IdMakerDao.updateType_result> callback) throws TException{
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
            create_updateTypeServiceCall(routeKey, timeout, platformArgs, info), callback);
  }

  protected TServiceCall create_updateTypeServiceCall(int routeKey, int timeout, PlatformArgs platformArgs, IdMakerInfo info){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(IdMakerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMakerDao.updateType_args request = new IdMakerDao.updateType_args();
    request.setPlatformArgs(platformArgs);
    request.setInfo(info);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateType");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMakerDao.updateType_result.class);
    return serviceCall;
  }

  public void send_getIdMakerInfoByType(int routeKey, int timeout, int type) throws TException {
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
        create_getIdMakerInfoByTypeServiceCall(routeKey, timeout, platformArgs, type), new TRequestOption());
  }

  public void send_getIdMakerInfoByType(int routeKey, int timeout, int type,TRequestOption requestOption) throws TException { 
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
        create_getIdMakerInfoByTypeServiceCall(routeKey, timeout, platformArgs, type), requestOption);
  }

  public long getIdMakerInfoByType(int routeKey, int timeout, int type, IMethodCallback<IdMakerDao.getIdMakerInfoByType_args, IdMakerDao.getIdMakerInfoByType_result> callback) throws TException{
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
            create_getIdMakerInfoByTypeServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  public long add_getIdMakerInfoByTypeCall(AsyncCallRunner runner, int routeKey, int timeout, int type, IMethodCallback<IdMakerDao.getIdMakerInfoByType_args, IdMakerDao.getIdMakerInfoByType_result> callback) throws TException{
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
            create_getIdMakerInfoByTypeServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  protected TServiceCall create_getIdMakerInfoByTypeServiceCall(int routeKey, int timeout, PlatformArgs platformArgs, int type){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(IdMakerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMakerDao.getIdMakerInfoByType_args request = new IdMakerDao.getIdMakerInfoByType_args();
    request.setPlatformArgs(platformArgs);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getIdMakerInfoByType");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMakerDao.getIdMakerInfoByType_result.class);
    return serviceCall;
  }

  public void send_queryIdMakerTypeInfoList(int routeKey, int timeout, int pageIndex, int pageSize, IdMakerQueryOption option) throws TException {
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
        create_queryIdMakerTypeInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryIdMakerTypeInfoList(int routeKey, int timeout, int pageIndex, int pageSize, IdMakerQueryOption option,TRequestOption requestOption) throws TException { 
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
        create_queryIdMakerTypeInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryIdMakerTypeInfoList(int routeKey, int timeout, int pageIndex, int pageSize, IdMakerQueryOption option, IMethodCallback<IdMakerDao.queryIdMakerTypeInfoList_args, IdMakerDao.queryIdMakerTypeInfoList_result> callback) throws TException{
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
            create_queryIdMakerTypeInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryIdMakerTypeInfoListCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, IdMakerQueryOption option, IMethodCallback<IdMakerDao.queryIdMakerTypeInfoList_args, IdMakerDao.queryIdMakerTypeInfoList_result> callback) throws TException{
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
            create_queryIdMakerTypeInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryIdMakerTypeInfoListServiceCall(int routeKey, int timeout, PlatformArgs platformArgs, int pageIndex, int pageSize, IdMakerQueryOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(IdMakerDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMakerDao.queryIdMakerTypeInfoList_args request = new IdMakerDao.queryIdMakerTypeInfoList_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryIdMakerTypeInfoList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMakerDao.queryIdMakerTypeInfoList_result.class);
    return serviceCall;
  }

}
