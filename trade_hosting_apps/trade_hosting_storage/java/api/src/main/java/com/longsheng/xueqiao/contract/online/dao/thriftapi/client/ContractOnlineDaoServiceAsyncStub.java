package com.longsheng.xueqiao.contract.online.dao.thriftapi.client;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.ContractOnlineDaoService;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.ContractOnlineDaoServiceVariable;
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

public class ContractOnlineDaoServiceAsyncStub extends BaseStub { 
  public ContractOnlineDaoServiceAsyncStub() {
    super(ContractOnlineDaoServiceVariable.serviceKey);
  }
  public void send_reqSledContract(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqSledContractServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqSledContract(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqSledContractServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqSledContract(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledContract_args, ContractOnlineDaoService.reqSledContract_result> callback) throws TException{
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
            create_reqSledContractServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqSledContractCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledContract_args, ContractOnlineDaoService.reqSledContract_result> callback) throws TException{
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
            create_reqSledContractServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqSledContractServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqSledContract_args request = new ContractOnlineDaoService.reqSledContract_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSledContract");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqSledContract_result.class);
    return serviceCall;
  }

  public void send_reqSledContractDetail(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqSledContractDetailServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqSledContractDetail(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqSledContractDetailServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqSledContractDetail(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledContractDetail_args, ContractOnlineDaoService.reqSledContractDetail_result> callback) throws TException{
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
            create_reqSledContractDetailServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqSledContractDetailCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledContractDetail_args, ContractOnlineDaoService.reqSledContractDetail_result> callback) throws TException{
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
            create_reqSledContractDetailServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqSledContractDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqSledContractDetail_args request = new ContractOnlineDaoService.reqSledContractDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSledContractDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqSledContractDetail_result.class);
    return serviceCall;
  }

  public void send_reqCommodityMapping(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqCommodityMappingServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqCommodityMapping(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqCommodityMappingServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqCommodityMapping(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqCommodityMapping_args, ContractOnlineDaoService.reqCommodityMapping_result> callback) throws TException{
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
            create_reqCommodityMappingServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqCommodityMappingCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqCommodityMapping_args, ContractOnlineDaoService.reqCommodityMapping_result> callback) throws TException{
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
            create_reqCommodityMappingServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqCommodityMappingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqCommodityMapping_args request = new ContractOnlineDaoService.reqCommodityMapping_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqCommodityMapping");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqCommodityMapping_result.class);
    return serviceCall;
  }

  public void send_reqSledExchange(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqSledExchangeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqSledExchange(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqSledExchangeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqSledExchange(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledExchange_args, ContractOnlineDaoService.reqSledExchange_result> callback) throws TException{
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
            create_reqSledExchangeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqSledExchangeCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledExchange_args, ContractOnlineDaoService.reqSledExchange_result> callback) throws TException{
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
            create_reqSledExchangeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqSledExchangeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqSledExchange_args request = new ContractOnlineDaoService.reqSledExchange_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSledExchange");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqSledExchange_result.class);
    return serviceCall;
  }

  public void send_reqSledCommodity(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqSledCommodityServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqSledCommodity(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqSledCommodityServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqSledCommodity(int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledCommodity_args, ContractOnlineDaoService.reqSledCommodity_result> callback) throws TException{
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
            create_reqSledCommodityServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqSledCommodityCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledCommodity_args, ContractOnlineDaoService.reqSledCommodity_result> callback) throws TException{
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
            create_reqSledCommodityServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqSledCommodityServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqSledCommodity_args request = new ContractOnlineDaoService.reqSledCommodity_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSledCommodity");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqSledCommodity_result.class);
    return serviceCall;
  }

  public void send_reqContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqContractVersionServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqContractVersionServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqContractVersion_args, ContractOnlineDaoService.reqContractVersion_result> callback) throws TException{
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
            create_reqContractVersionServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqContractVersionCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqContractVersion_args, ContractOnlineDaoService.reqContractVersion_result> callback) throws TException{
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
            create_reqContractVersionServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqContractVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqContractVersion_args request = new ContractOnlineDaoService.reqContractVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqContractVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqContractVersion_result.class);
    return serviceCall;
  }

  public void send_updateContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion) throws TException {
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
        create_updateContractVersionServiceCall(routeKey, timeout, platformArgs, contractVersion), new TRequestOption());
  }

  public void send_updateContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion,TRequestOption requestOption) throws TException { 
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
        create_updateContractVersionServiceCall(routeKey, timeout, platformArgs, contractVersion), requestOption);
  }

  public long updateContractVersion(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion, IMethodCallback<ContractOnlineDaoService.updateContractVersion_args, ContractOnlineDaoService.updateContractVersion_result> callback) throws TException{
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
            create_updateContractVersionServiceCall(routeKey, timeout, platformArgs, contractVersion), callback);
  }

  public long add_updateContractVersionCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion, IMethodCallback<ContractOnlineDaoService.updateContractVersion_args, ContractOnlineDaoService.updateContractVersion_result> callback) throws TException{
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
            create_updateContractVersionServiceCall(routeKey, timeout, platformArgs, contractVersion), callback);
  }

  protected TServiceCall create_updateContractVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.thriftapi.ContractVersion contractVersion){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.updateContractVersion_args request = new ContractOnlineDaoService.updateContractVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setContractVersion(contractVersion);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateContractVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.updateContractVersion_result.class);
    return serviceCall;
  }

  public void send_removeContractVersion(int routeKey, int timeout, int versionId) throws TException {
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
        create_removeContractVersionServiceCall(routeKey, timeout, platformArgs, versionId), new TRequestOption());
  }

  public void send_removeContractVersion(int routeKey, int timeout, int versionId,TRequestOption requestOption) throws TException { 
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
        create_removeContractVersionServiceCall(routeKey, timeout, platformArgs, versionId), requestOption);
  }

  public long removeContractVersion(int routeKey, int timeout, int versionId, IMethodCallback<ContractOnlineDaoService.removeContractVersion_args, ContractOnlineDaoService.removeContractVersion_result> callback) throws TException{
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
            create_removeContractVersionServiceCall(routeKey, timeout, platformArgs, versionId), callback);
  }

  public long add_removeContractVersionCall(AsyncCallRunner runner, int routeKey, int timeout, int versionId, IMethodCallback<ContractOnlineDaoService.removeContractVersion_args, ContractOnlineDaoService.removeContractVersion_result> callback) throws TException{
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
            create_removeContractVersionServiceCall(routeKey, timeout, platformArgs, versionId), callback);
  }

  protected TServiceCall create_removeContractVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int versionId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.removeContractVersion_args request = new ContractOnlineDaoService.removeContractVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setVersionId(versionId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeContractVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.removeContractVersion_result.class);
    return serviceCall;
  }

  public void send_addDbLocking(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo) throws TException {
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
        create_addDbLockingServiceCall(routeKey, timeout, platformArgs, dbLockingInfo), new TRequestOption());
  }

  public void send_addDbLocking(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo,TRequestOption requestOption) throws TException { 
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
        create_addDbLockingServiceCall(routeKey, timeout, platformArgs, dbLockingInfo), requestOption);
  }

  public long addDbLocking(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo, IMethodCallback<ContractOnlineDaoService.addDbLocking_args, ContractOnlineDaoService.addDbLocking_result> callback) throws TException{
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
            create_addDbLockingServiceCall(routeKey, timeout, platformArgs, dbLockingInfo), callback);
  }

  public long add_addDbLockingCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo, IMethodCallback<ContractOnlineDaoService.addDbLocking_args, ContractOnlineDaoService.addDbLocking_result> callback) throws TException{
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
            create_addDbLockingServiceCall(routeKey, timeout, platformArgs, dbLockingInfo), callback);
  }

  protected TServiceCall create_addDbLockingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo dbLockingInfo){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.addDbLocking_args request = new ContractOnlineDaoService.addDbLocking_args();
    request.setPlatformArgs(platformArgs);
    request.setDbLockingInfo(dbLockingInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addDbLocking");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.addDbLocking_result.class);
    return serviceCall;
  }

  public void send_removeDbLocking(int routeKey, int timeout, String lockedBy) throws TException {
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
        create_removeDbLockingServiceCall(routeKey, timeout, platformArgs, lockedBy), new TRequestOption());
  }

  public void send_removeDbLocking(int routeKey, int timeout, String lockedBy,TRequestOption requestOption) throws TException { 
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
        create_removeDbLockingServiceCall(routeKey, timeout, platformArgs, lockedBy), requestOption);
  }

  public long removeDbLocking(int routeKey, int timeout, String lockedBy, IMethodCallback<ContractOnlineDaoService.removeDbLocking_args, ContractOnlineDaoService.removeDbLocking_result> callback) throws TException{
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
            create_removeDbLockingServiceCall(routeKey, timeout, platformArgs, lockedBy), callback);
  }

  public long add_removeDbLockingCall(AsyncCallRunner runner, int routeKey, int timeout, String lockedBy, IMethodCallback<ContractOnlineDaoService.removeDbLocking_args, ContractOnlineDaoService.removeDbLocking_result> callback) throws TException{
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
            create_removeDbLockingServiceCall(routeKey, timeout, platformArgs, lockedBy), callback);
  }

  protected TServiceCall create_removeDbLockingServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String lockedBy){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.removeDbLocking_args request = new ContractOnlineDaoService.removeDbLocking_args();
    request.setPlatformArgs(platformArgs);
    request.setLockedBy(lockedBy);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeDbLocking");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.removeDbLocking_result.class);
    return serviceCall;
  }

  public void send_reqDbLockingInfo(int routeKey, int timeout) throws TException {
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
        create_reqDbLockingInfoServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_reqDbLockingInfo(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_reqDbLockingInfoServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long reqDbLockingInfo(int routeKey, int timeout, IMethodCallback<ContractOnlineDaoService.reqDbLockingInfo_args, ContractOnlineDaoService.reqDbLockingInfo_result> callback) throws TException{
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
            create_reqDbLockingInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_reqDbLockingInfoCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<ContractOnlineDaoService.reqDbLockingInfo_args, ContractOnlineDaoService.reqDbLockingInfo_result> callback) throws TException{
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
            create_reqDbLockingInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_reqDbLockingInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqDbLockingInfo_args request = new ContractOnlineDaoService.reqDbLockingInfo_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqDbLockingInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqDbLockingInfo_result.class);
    return serviceCall;
  }

  public void send_reqSledTradeTime(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize) throws TException {
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
        create_reqSledTradeTimeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_reqSledTradeTime(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_reqSledTradeTimeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long reqSledTradeTime(int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledTradeTime_args, ContractOnlineDaoService.reqSledTradeTime_result> callback) throws TException{
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
            create_reqSledTradeTimeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_reqSledTradeTimeCall(AsyncCallRunner runner, int routeKey, int timeout, com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize, IMethodCallback<ContractOnlineDaoService.reqSledTradeTime_args, ContractOnlineDaoService.reqSledTradeTime_result> callback) throws TException{
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
            create_reqSledTradeTimeServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_reqSledTradeTimeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ContractOnlineDaoServiceVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ContractOnlineDaoService.reqSledTradeTime_args request = new ContractOnlineDaoService.reqSledTradeTime_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("reqSledTradeTime");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ContractOnlineDaoService.reqSledTradeTime_result.class);
    return serviceCall;
  }

}
