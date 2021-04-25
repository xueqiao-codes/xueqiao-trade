package xueqiao.trade.hosting.tasknote.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNote;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNoteVariable;

public class TradeHostingTaskNoteStub extends BaseStub {

  public TradeHostingTaskNoteStub() {
    super(TradeHostingTaskNoteVariable.serviceKey);
  }

  public HostingTaskNotePage  getTaskNotePage(QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTaskNotePage(qryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public HostingTaskNotePage  getTaskNotePage(QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getTaskNotePage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<HostingTaskNotePage>(){
    @Override
    public HostingTaskNotePage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingTaskNote.Client(protocol).getTaskNotePage(platformArgs, qryOption, pageOption);
      }
    }, invokeInfo);
  }

  public HostingTaskNotePage  getTaskNotePage(int routeKey, int timeout,QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getTaskNotePage(qryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  delTaskNote(HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    delTaskNote(noteType, noteKey, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  delTaskNote(HostingTaskNoteType noteType, HostingTaskNoteKey noteKey,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("delTaskNote").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingTaskNote.Client(protocol).delTaskNote(platformArgs, noteType, noteKey);
      return null;
      }
    }, invokeInfo);
  }

  public void  delTaskNote(int routeKey, int timeout,HostingTaskNoteType noteType, HostingTaskNoteKey noteKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    delTaskNote(noteType, noteKey, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
