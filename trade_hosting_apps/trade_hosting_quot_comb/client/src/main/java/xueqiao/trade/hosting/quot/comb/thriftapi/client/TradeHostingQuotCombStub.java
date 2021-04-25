package xueqiao.trade.hosting.quot.comb.thriftapi.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;
import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotComb;
import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotCombVariable;

public class TradeHostingQuotCombStub extends BaseStub {

  public TradeHostingQuotCombStub() {
    super(TradeHostingQuotCombVariable.serviceKey);
  }

  public void  syncCombTopics(SyncCombTopicsRequest syncRequest) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncCombTopics(syncRequest, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncCombTopics(SyncCombTopicsRequest syncRequest,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("syncCombTopics").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new TradeHostingQuotComb.Client(protocol).syncCombTopics(platformArgs, syncRequest);
      return null;
      }
    }, invokeInfo);
  }

  public void  syncCombTopics(int routeKey, int timeout,SyncCombTopicsRequest syncRequest)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncCombTopics(syncRequest, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
