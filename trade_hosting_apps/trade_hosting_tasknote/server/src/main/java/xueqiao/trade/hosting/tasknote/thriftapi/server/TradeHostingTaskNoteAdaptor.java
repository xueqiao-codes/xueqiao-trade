package xueqiao.trade.hosting.tasknote.thriftapi.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNote;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNoteVariable;


public abstract class TradeHostingTaskNoteAdaptor implements TradeHostingTaskNote.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public HostingTaskNotePage getTaskNotePage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTaskNoteVariable.serviceKey,"getTaskNotePage",platformArgs);
    return getTaskNotePage(oCntl, qryOption, pageOption);
  }

  protected abstract HostingTaskNotePage getTaskNotePage(TServiceCntl oCntl, QueryTaskNoteOption qryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void delTaskNote(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingTaskNoteVariable.serviceKey,"delTaskNote",platformArgs);
delTaskNote(oCntl, noteType, noteKey);
  }

  protected abstract void delTaskNote(TServiceCntl oCntl, HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingTaskNoteAdaptor(){
    methodParameterNameMap.put("getTaskNotePage",new String[]{"platformArgs", "qryOption", "pageOption"});
    methodParameterNameMap.put("delTaskNote",new String[]{"platformArgs", "noteType", "noteKey"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
