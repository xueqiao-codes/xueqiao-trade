package xueqiao.hosting.machine.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.platform.svr_platform.client.BaseStub;
import xueqiao.hosting.machine.dao.HostingMachineDao;
import xueqiao.hosting.machine.dao.HostingMachineDaoVariable;

public class HostingMachineDaoStub extends BaseStub {

  public HostingMachineDaoStub() {
    super(HostingMachineDaoVariable.serviceKey);
}

  public long  addHostingMachine(int routeKey, int timeout,xueqiao.hosting.machine.HostingMachine newMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("addHostingMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("addHostingMachine");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.addHostingMachine(platformArgs, newMachine);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"addHostingMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"addHostingMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  updateHostingMachine(int routeKey, int timeout,xueqiao.hosting.machine.HostingMachine updateMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("updateHostingMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("updateHostingMachine");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    try {
      transport.open();
      client.updateHostingMachine(platformArgs, updateMachine);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"updateHostingMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"updateHostingMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteHostingMachine(int routeKey, int timeout,long machineId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("deleteHostingMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("deleteHostingMachine");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    try {
      transport.open();
      client.deleteHostingMachine(platformArgs, machineId);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"deleteHostingMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"deleteHostingMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public xueqiao.hosting.machine.HostingMachinePageResult  queryHostingMachinePage(int routeKey, int timeout,xueqiao.hosting.machine.QueryHostingMachineOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("queryHostingMachinePage", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("queryHostingMachinePage");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    xueqiao.hosting.machine.HostingMachinePageResult result = null;
    try {
      transport.open();
      result = client.queryHostingMachinePage(platformArgs, queryOption, pageOption);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"queryHostingMachinePage", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"queryHostingMachinePage", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public long  addRelatedInfo(int routeKey, int timeout,xueqiao.hosting.machine.HostingRelatedInfo newRelatedInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("addRelatedInfo", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("addRelatedInfo");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.addRelatedInfo(platformArgs, newRelatedInfo);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"addRelatedInfo", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"addRelatedInfo", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  updateRelatedInfo(int routeKey, int timeout,xueqiao.hosting.machine.HostingRelatedInfo updateRelatedInfo)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("updateRelatedInfo", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("updateRelatedInfo");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    try {
      transport.open();
      client.updateRelatedInfo(platformArgs, updateRelatedInfo);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"updateRelatedInfo", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"updateRelatedInfo", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteRelatedInfo(int routeKey, int timeout,long relatedId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("deleteRelatedInfo", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("deleteRelatedInfo");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    try {
      transport.open();
      client.deleteRelatedInfo(platformArgs, relatedId);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"deleteRelatedInfo", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"deleteRelatedInfo", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public xueqiao.hosting.machine.HostingRelatedInfoPageResult  queryRelatedInfoPage(int routeKey, int timeout,xueqiao.hosting.machine.QueryHostingRelatedInfoOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(HostingMachineDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(HostingMachineDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("queryRelatedInfoPage", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("queryRelatedInfoPage");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    HostingMachineDao.Client client = new  HostingMachineDao.Client(protocol);
    xueqiao.hosting.machine.HostingRelatedInfoPageResult result = null;
    try {
      transport.open();
      result = client.queryRelatedInfoPage(platformArgs, queryOption, pageOption);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"queryRelatedInfoPage", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        HostingMachineDaoVariable.serviceKey,"queryRelatedInfoPage", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
