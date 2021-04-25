package xueqiao.mailbox.user.message.dao.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import xueqiao.mailbox.user.message.dao.thriftapi.UserMessageDao;
import xueqiao.mailbox.user.message.dao.thriftapi.UserMessageDaoVariable;

public class UserMessageDaoStub extends BaseStub {

  public UserMessageDaoStub() {
    super(UserMessageDaoVariable.serviceKey);
  }

  public void  addUserMessage(List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addUserMessage(userMessage, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addUserMessage(List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addUserMessage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new UserMessageDao.Client(protocol).addUserMessage(platformArgs, userMessage);
      return null;
      }
    }, invokeInfo);
  }

  public void  addUserMessage(int routeKey, int timeout,List<xueqiao.mailbox.user.message.thriftapi.UserMessage> userMessage)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addUserMessage(userMessage, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  reqUserMessage(xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqUserMessage(option, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  reqUserMessage(xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("reqUserMessage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<xueqiao.mailbox.user.message.thriftapi.UserMessagePage>(){
    @Override
    public xueqiao.mailbox.user.message.thriftapi.UserMessagePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new UserMessageDao.Client(protocol).reqUserMessage(platformArgs, option, pageOption);
      }
    }, invokeInfo);
  }

  public xueqiao.mailbox.user.message.thriftapi.UserMessagePage  reqUserMessage(int routeKey, int timeout,xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption option, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return reqUserMessage(option, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  markAsRead(xueqiao.mailbox.user.message.thriftapi.MarkReadOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    markAsRead(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  markAsRead(xueqiao.mailbox.user.message.thriftapi.MarkReadOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("markAsRead").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new UserMessageDao.Client(protocol).markAsRead(platformArgs, option);
      return null;
      }
    }, invokeInfo);
  }

  public void  markAsRead(int routeKey, int timeout,xueqiao.mailbox.user.message.thriftapi.MarkReadOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    markAsRead(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
