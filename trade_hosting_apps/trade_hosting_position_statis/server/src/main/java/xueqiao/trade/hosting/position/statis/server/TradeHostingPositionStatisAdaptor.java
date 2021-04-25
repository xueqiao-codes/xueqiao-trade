package xueqiao.trade.hosting.position.statis.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import xueqiao.trade.hosting.position.statis.BatchClosedPositionReq;
import xueqiao.trade.hosting.position.statis.DisassembleComposePositionReq;
import xueqiao.trade.hosting.position.statis.QueryHistoryClosedPositionOption;
import xueqiao.trade.hosting.position.statis.QueryStatArchiveItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionItemOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption;
import xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummaryPage;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDetail;
import xueqiao.trade.hosting.position.statis.StatContructComposeReq;
import xueqiao.trade.hosting.position.statis.StatMergeToComposeReq;
import xueqiao.trade.hosting.position.statis.StatPositionItemPage;
import xueqiao.trade.hosting.position.statis.StatPositionSummaryExPage;
import xueqiao.trade.hosting.position.statis.StatPositionSummaryPage;
import xueqiao.trade.hosting.position.statis.StatPositionUnitPage;
import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatis;
import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatisVariable;


public abstract class TradeHostingPositionStatisAdaptor implements TradeHostingPositionStatis.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void clearAll(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"clearAll",platformArgs);
clearAll(oCntl);
  }

  protected abstract void clearAll(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void contructCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"contructCompose",platformArgs);
contructCompose(oCntl, contructComposeReq);
  }

  protected abstract void contructCompose(TServiceCntl oCntl, StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void disassembleCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"disassembleCompose",platformArgs);
disassembleCompose(oCntl, disassembleComposePositionReq);
  }

  protected abstract void disassembleCompose(TServiceCntl oCntl, DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void batchClosePosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"batchClosePosition",platformArgs);
batchClosePosition(oCntl, batchClosedPositionReq);
  }

  protected abstract void batchClosePosition(TServiceCntl oCntl, BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void recoverClosedPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"recoverClosedPosition",platformArgs);
recoverClosedPosition(oCntl, subAccountId, targetKey, targetType);
  }

  protected abstract void recoverClosedPosition(TServiceCntl oCntl, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void assignPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"assignPosition",platformArgs);
assignPosition(oCntl, positionAssigned);
  }

  protected abstract void assignPosition(TServiceCntl oCntl, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void mergeToCompose(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"mergeToCompose",platformArgs);
mergeToCompose(oCntl, mergeToComposeReq);
  }

  protected abstract void mergeToCompose(TServiceCntl oCntl, StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteExpiredStatContractPosition(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"deleteExpiredStatContractPosition",platformArgs);
deleteExpiredStatContractPosition(oCntl, subAccountId, sledContractId);
  }

  protected abstract void deleteExpiredStatContractPosition(TServiceCntl oCntl, long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatPositionSummaryPage queryStatPositionSummaryPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryStatPositionSummaryPage",platformArgs);
    return queryStatPositionSummaryPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatPositionSummaryPage queryStatPositionSummaryPage(TServiceCntl oCntl, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatPositionItemPage queryStatPositionItemPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryStatPositionItemPage",platformArgs);
    return queryStatPositionItemPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatPositionItemPage queryStatPositionItemPage(TServiceCntl oCntl, QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryCurrentDayStatClosedPositionPage",platformArgs);
    return queryCurrentDayStatClosedPositionPage(oCntl, subAccountId, targetKey, targetType);
  }

  protected abstract StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(TServiceCntl oCntl, long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDetail queryStatClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryStatClosedPositionDetail",platformArgs);
    return queryStatClosedPositionDetail(oCntl, queryOption, pageOption);
  }

  protected abstract StatClosedPositionDetail queryStatClosedPositionDetail(TServiceCntl oCntl, QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryArchivedClosedPositionPage",platformArgs);
    return queryArchivedClosedPositionPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(TServiceCntl oCntl, QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDetail queryArchivedClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryArchivedClosedPositionDetail",platformArgs);
    return queryArchivedClosedPositionDetail(oCntl, queryOption, pageOption);
  }

  protected abstract StatClosedPositionDetail queryArchivedClosedPositionDetail(TServiceCntl oCntl, QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatPositionSummaryExPage queryStatPositionSummaryExPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryStatPositionSummaryExPage",platformArgs);
    return queryStatPositionSummaryExPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatPositionSummaryExPage queryStatPositionSummaryExPage(TServiceCntl oCntl, QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatPositionUnitPage queryStatPositionUnitPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryStatPositionUnitPage",platformArgs);
    return queryStatPositionUnitPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatPositionUnitPage queryStatPositionUnitPage(TServiceCntl oCntl, QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryHistoryClosedPositionPage",platformArgs);
    return queryHistoryClosedPositionPage(oCntl, queryOption, pageOption);
  }

  protected abstract StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(TServiceCntl oCntl, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StatClosedPositionDetail queryHistoryClosedPositionDetail(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(TradeHostingPositionStatisVariable.serviceKey,"queryHistoryClosedPositionDetail",platformArgs);
    return queryHistoryClosedPositionDetail(oCntl, queryOption, pageOption);
  }

  protected abstract StatClosedPositionDetail queryHistoryClosedPositionDetail(TServiceCntl oCntl, QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected TradeHostingPositionStatisAdaptor(){
    methodParameterNameMap.put("clearAll",new String[]{"platformArgs"});
    methodParameterNameMap.put("contructCompose",new String[]{"platformArgs", "contructComposeReq"});
    methodParameterNameMap.put("disassembleCompose",new String[]{"platformArgs", "disassembleComposePositionReq"});
    methodParameterNameMap.put("batchClosePosition",new String[]{"platformArgs", "batchClosedPositionReq"});
    methodParameterNameMap.put("recoverClosedPosition",new String[]{"platformArgs", "subAccountId", "targetKey", "targetType"});
    methodParameterNameMap.put("assignPosition",new String[]{"platformArgs", "positionAssigned"});
    methodParameterNameMap.put("mergeToCompose",new String[]{"platformArgs", "mergeToComposeReq"});
    methodParameterNameMap.put("deleteExpiredStatContractPosition",new String[]{"platformArgs", "subAccountId", "sledContractId"});
    methodParameterNameMap.put("queryStatPositionSummaryPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionItemPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryCurrentDayStatClosedPositionPage",new String[]{"platformArgs", "subAccountId", "targetKey", "targetType"});
    methodParameterNameMap.put("queryStatClosedPositionDetail",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryArchivedClosedPositionPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryArchivedClosedPositionDetail",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionSummaryExPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryStatPositionUnitPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryHistoryClosedPositionPage",new String[]{"platformArgs", "queryOption", "pageOption"});
    methodParameterNameMap.put("queryHistoryClosedPositionDetail",new String[]{"platformArgs", "queryOption", "pageOption"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
