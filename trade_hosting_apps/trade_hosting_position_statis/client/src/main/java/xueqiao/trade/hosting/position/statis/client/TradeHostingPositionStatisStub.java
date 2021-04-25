package xueqiao.trade.hosting.position.statis.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
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

public class TradeHostingPositionStatisStub extends BaseStub {

  public TradeHostingPositionStatisStub() {
    super(TradeHostingPositionStatisVariable.serviceKey);
  }

  public void  clearAll() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  clearAll(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("clearAll").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).clearAll(platformArgs);
      return null;
      }
    }, invokeInfo);
  }

  public void  clearAll(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    clearAll(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  contructCompose(StatContructComposeReq contructComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    contructCompose(contructComposeReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  contructCompose(StatContructComposeReq contructComposeReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("contructCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).contructCompose(platformArgs, contructComposeReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  contructCompose(int routeKey, int timeout,StatContructComposeReq contructComposeReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    contructCompose(contructComposeReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disassembleCompose(DisassembleComposePositionReq disassembleComposePositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disassembleCompose(disassembleComposePositionReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  disassembleCompose(DisassembleComposePositionReq disassembleComposePositionReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("disassembleCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).disassembleCompose(platformArgs, disassembleComposePositionReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  disassembleCompose(int routeKey, int timeout,DisassembleComposePositionReq disassembleComposePositionReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    disassembleCompose(disassembleComposePositionReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  batchClosePosition(BatchClosedPositionReq batchClosedPositionReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    batchClosePosition(batchClosedPositionReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  batchClosePosition(BatchClosedPositionReq batchClosedPositionReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("batchClosePosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).batchClosePosition(platformArgs, batchClosedPositionReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  batchClosePosition(int routeKey, int timeout,BatchClosedPositionReq batchClosedPositionReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    batchClosePosition(batchClosedPositionReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  recoverClosedPosition(long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    recoverClosedPosition(subAccountId, targetKey, targetType, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  recoverClosedPosition(long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("recoverClosedPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).recoverClosedPosition(platformArgs, subAccountId, targetKey, targetType);
      return null;
      }
    }, invokeInfo);
  }

  public void  recoverClosedPosition(int routeKey, int timeout,long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    recoverClosedPosition(subAccountId, targetKey, targetType, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  assignPosition(xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    assignPosition(positionAssigned, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  assignPosition(xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("assignPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).assignPosition(platformArgs, positionAssigned);
      return null;
      }
    }, invokeInfo);
  }

  public void  assignPosition(int routeKey, int timeout,xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    assignPosition(positionAssigned, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  mergeToCompose(StatMergeToComposeReq mergeToComposeReq) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    mergeToCompose(mergeToComposeReq, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  mergeToCompose(StatMergeToComposeReq mergeToComposeReq,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("mergeToCompose").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).mergeToCompose(platformArgs, mergeToComposeReq);
      return null;
      }
    }, invokeInfo);
  }

  public void  mergeToCompose(int routeKey, int timeout,StatMergeToComposeReq mergeToComposeReq)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    mergeToCompose(mergeToComposeReq, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredStatContractPosition(long subAccountId, long sledContractId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredStatContractPosition(subAccountId, sledContractId, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteExpiredStatContractPosition(long subAccountId, long sledContractId,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteExpiredStatContractPosition").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingPositionStatis.Client(protocol).deleteExpiredStatContractPosition(platformArgs, subAccountId, sledContractId);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteExpiredStatContractPosition(int routeKey, int timeout,long subAccountId, long sledContractId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteExpiredStatContractPosition(subAccountId, sledContractId, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionSummaryPage  queryStatPositionSummaryPage(QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionSummaryPage  queryStatPositionSummaryPage(QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionSummaryPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatPositionSummaryPage>(){
    @Override
    public StatPositionSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryStatPositionSummaryPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatPositionSummaryPage  queryStatPositionSummaryPage(int routeKey, int timeout,QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionItemPage  queryStatPositionItemPage(QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionItemPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionItemPage  queryStatPositionItemPage(QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionItemPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatPositionItemPage>(){
    @Override
    public StatPositionItemPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryStatPositionItemPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatPositionItemPage  queryStatPositionItemPage(int routeKey, int timeout,QueryStatPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionItemPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCurrentDayStatClosedPositionPage(subAccountId, targetKey, targetType, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryCurrentDayStatClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDateSummaryPage>(){
    @Override
    public StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryCurrentDayStatClosedPositionPage(platformArgs, subAccountId, targetKey, targetType);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDateSummaryPage  queryCurrentDayStatClosedPositionPage(int routeKey, int timeout,long subAccountId, String targetKey, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryCurrentDayStatClosedPositionPage(subAccountId, targetKey, targetType, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryStatClosedPositionDetail(QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatClosedPositionDetail(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryStatClosedPositionDetail(QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDetail>(){
    @Override
    public StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryStatClosedPositionDetail(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDetail  queryStatClosedPositionDetail(int routeKey, int timeout,QueryStatClosedPositionItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatClosedPositionDetail(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryArchivedClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDateSummaryPage>(){
    @Override
    public StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryArchivedClosedPositionPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDateSummaryPage  queryArchivedClosedPositionPage(int routeKey, int timeout,QueryStatClosedPositionDateSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryArchivedClosedPositionDetail(QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionDetail(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryArchivedClosedPositionDetail(QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryArchivedClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDetail>(){
    @Override
    public StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryArchivedClosedPositionDetail(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDetail  queryArchivedClosedPositionDetail(int routeKey, int timeout,QueryStatArchiveItemOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryArchivedClosedPositionDetail(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionSummaryExPage  queryStatPositionSummaryExPage(QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryExPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionSummaryExPage  queryStatPositionSummaryExPage(QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionSummaryExPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatPositionSummaryExPage>(){
    @Override
    public StatPositionSummaryExPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryStatPositionSummaryExPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatPositionSummaryExPage  queryStatPositionSummaryExPage(int routeKey, int timeout,QueryStatPositionSummaryOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionSummaryExPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionUnitPage  queryStatPositionUnitPage(QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionUnitPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatPositionUnitPage  queryStatPositionUnitPage(QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryStatPositionUnitPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatPositionUnitPage>(){
    @Override
    public StatPositionUnitPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryStatPositionUnitPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatPositionUnitPage  queryStatPositionUnitPage(int routeKey, int timeout,QueryStatPositionUnitOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryStatPositionUnitPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionPage(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryHistoryClosedPositionPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDateSummaryPage>(){
    @Override
    public StatClosedPositionDateSummaryPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryHistoryClosedPositionPage(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDateSummaryPage  queryHistoryClosedPositionPage(int routeKey, int timeout,QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionPage(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryHistoryClosedPositionDetail(QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionDetail(queryOption, pageOption, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public StatClosedPositionDetail  queryHistoryClosedPositionDetail(QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryHistoryClosedPositionDetail").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<StatClosedPositionDetail>(){
    @Override
    public StatClosedPositionDetail call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new TradeHostingPositionStatis.Client(protocol).queryHistoryClosedPositionDetail(platformArgs, queryOption, pageOption);
      }
    }, invokeInfo);
  }

  public StatClosedPositionDetail  queryHistoryClosedPositionDetail(int routeKey, int timeout,QueryHistoryClosedPositionOption queryOption, org.soldier.platform.page.IndexedPageOption pageOption)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryHistoryClosedPositionDetail(queryOption, pageOption, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
