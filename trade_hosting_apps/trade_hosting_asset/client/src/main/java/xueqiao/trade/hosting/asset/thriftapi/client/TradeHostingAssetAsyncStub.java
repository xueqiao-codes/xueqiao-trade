package xueqiao.trade.hosting.asset.thriftapi.client;

import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAsset;
import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAssetVariable;
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
import java.util.List;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.CreditAmountChange;
import xueqiao.trade.hosting.asset.thriftapi.FundChange;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingPositionFundPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingPositionVolumePage;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingPositionFundOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingPositionVolumeOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionPage;

public class TradeHostingAssetAsyncStub extends BaseStub { 
  public TradeHostingAssetAsyncStub() {
    super(TradeHostingAssetVariable.serviceKey);
  }
  public void send_getHostingSledContractPosition(int routeKey, int timeout, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getHostingSledContractPosition(int routeKey, int timeout, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getHostingSledContractPosition(int routeKey, int timeout, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingSledContractPosition_args, TradeHostingAsset.getHostingSledContractPosition_result> callback) throws TException{
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
            create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getHostingSledContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingSledContractPosition_args, TradeHostingAsset.getHostingSledContractPosition_result> callback) throws TException{
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
            create_getHostingSledContractPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getHostingSledContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingSledContractPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getHostingSledContractPosition_args request = new TradeHostingAsset.getHostingSledContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSledContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getHostingSledContractPosition_result.class);
    return serviceCall;
  }

  public void send_getHostingSubAccountFund(int routeKey, int timeout, ReqHostingFundOption option) throws TException {
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
        create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_getHostingSubAccountFund(int routeKey, int timeout, ReqHostingFundOption option,TRequestOption requestOption) throws TException { 
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
        create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long getHostingSubAccountFund(int routeKey, int timeout, ReqHostingFundOption option, IMethodCallback<TradeHostingAsset.getHostingSubAccountFund_args, TradeHostingAsset.getHostingSubAccountFund_result> callback) throws TException{
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
            create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_getHostingSubAccountFundCall(AsyncCallRunner runner, int routeKey, int timeout, ReqHostingFundOption option, IMethodCallback<TradeHostingAsset.getHostingSubAccountFund_args, TradeHostingAsset.getHostingSubAccountFund_result> callback) throws TException{
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
            create_getHostingSubAccountFundServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_getHostingSubAccountFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingFundOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getHostingSubAccountFund_args request = new TradeHostingAsset.getHostingSubAccountFund_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSubAccountFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getHostingSubAccountFund_result.class);
    return serviceCall;
  }

  public void send_changeSubAccountFund(int routeKey, int timeout, FundChange fundChange) throws TException {
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
        create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, fundChange), new TRequestOption());
  }

  public void send_changeSubAccountFund(int routeKey, int timeout, FundChange fundChange,TRequestOption requestOption) throws TException { 
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
        create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, fundChange), requestOption);
  }

  public long changeSubAccountFund(int routeKey, int timeout, FundChange fundChange, IMethodCallback<TradeHostingAsset.changeSubAccountFund_args, TradeHostingAsset.changeSubAccountFund_result> callback) throws TException{
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
            create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, fundChange), callback);
  }

  public long add_changeSubAccountFundCall(AsyncCallRunner runner, int routeKey, int timeout, FundChange fundChange, IMethodCallback<TradeHostingAsset.changeSubAccountFund_args, TradeHostingAsset.changeSubAccountFund_result> callback) throws TException{
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
            create_changeSubAccountFundServiceCall(routeKey, timeout, platformArgs, fundChange), callback);
  }

  protected TServiceCall create_changeSubAccountFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, FundChange fundChange){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.changeSubAccountFund_args request = new TradeHostingAsset.changeSubAccountFund_args();
    request.setPlatformArgs(platformArgs);
    request.setFundChange(fundChange);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("changeSubAccountFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.changeSubAccountFund_result.class);
    return serviceCall;
  }

  public void send_setSubAccountCreditAmount(int routeKey, int timeout, CreditAmountChange amountChange) throws TException {
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
        create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, amountChange), new TRequestOption());
  }

  public void send_setSubAccountCreditAmount(int routeKey, int timeout, CreditAmountChange amountChange,TRequestOption requestOption) throws TException { 
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
        create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, amountChange), requestOption);
  }

  public long setSubAccountCreditAmount(int routeKey, int timeout, CreditAmountChange amountChange, IMethodCallback<TradeHostingAsset.setSubAccountCreditAmount_args, TradeHostingAsset.setSubAccountCreditAmount_result> callback) throws TException{
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
            create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, amountChange), callback);
  }

  public long add_setSubAccountCreditAmountCall(AsyncCallRunner runner, int routeKey, int timeout, CreditAmountChange amountChange, IMethodCallback<TradeHostingAsset.setSubAccountCreditAmount_args, TradeHostingAsset.setSubAccountCreditAmount_result> callback) throws TException{
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
            create_setSubAccountCreditAmountServiceCall(routeKey, timeout, platformArgs, amountChange), callback);
  }

  protected TServiceCall create_setSubAccountCreditAmountServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, CreditAmountChange amountChange){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.setSubAccountCreditAmount_args request = new TradeHostingAsset.setSubAccountCreditAmount_args();
    request.setPlatformArgs(platformArgs);
    request.setAmountChange(amountChange);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setSubAccountCreditAmount");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.setSubAccountCreditAmount_result.class);
    return serviceCall;
  }

  public void send_getSettlementPositionDetail(int routeKey, int timeout, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getSettlementPositionDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getSettlementPositionDetail(int routeKey, int timeout, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getSettlementPositionDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getSettlementPositionDetail(int routeKey, int timeout, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSettlementPositionDetail_args, TradeHostingAsset.getSettlementPositionDetail_result> callback) throws TException{
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
            create_getSettlementPositionDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getSettlementPositionDetailCall(AsyncCallRunner runner, int routeKey, int timeout, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSettlementPositionDetail_args, TradeHostingAsset.getSettlementPositionDetail_result> callback) throws TException{
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
            create_getSettlementPositionDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getSettlementPositionDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSettlementPositionDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getSettlementPositionDetail_args request = new TradeHostingAsset.getSettlementPositionDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSettlementPositionDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getSettlementPositionDetail_result.class);
    return serviceCall;
  }

  public void send_getHostingSubAccountMoneyRecord(int routeKey, int timeout, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getHostingSubAccountMoneyRecord(int routeKey, int timeout, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getHostingSubAccountMoneyRecord(int routeKey, int timeout, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingSubAccountMoneyRecord_args, TradeHostingAsset.getHostingSubAccountMoneyRecord_result> callback) throws TException{
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
            create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getHostingSubAccountMoneyRecordCall(AsyncCallRunner runner, int routeKey, int timeout, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingSubAccountMoneyRecord_args, TradeHostingAsset.getHostingSubAccountMoneyRecord_result> callback) throws TException{
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
            create_getHostingSubAccountMoneyRecordServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getHostingSubAccountMoneyRecordServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getHostingSubAccountMoneyRecord_args request = new TradeHostingAsset.getHostingSubAccountMoneyRecord_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingSubAccountMoneyRecord");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getHostingSubAccountMoneyRecord_result.class);
    return serviceCall;
  }

  public void send_getAssetPositionTradeDetail(int routeKey, int timeout, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getAssetPositionTradeDetail(int routeKey, int timeout, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getAssetPositionTradeDetail(int routeKey, int timeout, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getAssetPositionTradeDetail_args, TradeHostingAsset.getAssetPositionTradeDetail_result> callback) throws TException{
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
            create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getAssetPositionTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getAssetPositionTradeDetail_args, TradeHostingAsset.getAssetPositionTradeDetail_result> callback) throws TException{
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
            create_getAssetPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getAssetPositionTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingAssetTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getAssetPositionTradeDetail_args request = new TradeHostingAsset.getAssetPositionTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAssetPositionTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getAssetPositionTradeDetail_result.class);
    return serviceCall;
  }

  public void send_getSettlementPositionTradeDetail(int routeKey, int timeout, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getSettlementPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getSettlementPositionTradeDetail(int routeKey, int timeout, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getSettlementPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getSettlementPositionTradeDetail(int routeKey, int timeout, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSettlementPositionTradeDetail_args, TradeHostingAsset.getSettlementPositionTradeDetail_result> callback) throws TException{
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
            create_getSettlementPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getSettlementPositionTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSettlementPositionTradeDetail_args, TradeHostingAsset.getSettlementPositionTradeDetail_result> callback) throws TException{
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
            create_getSettlementPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getSettlementPositionTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSettlementPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getSettlementPositionTradeDetail_args request = new TradeHostingAsset.getSettlementPositionTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSettlementPositionTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getSettlementPositionTradeDetail_result.class);
    return serviceCall;
  }

  public void send_getHostingPositionVolume(int routeKey, int timeout, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getHostingPositionVolumeServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getHostingPositionVolume(int routeKey, int timeout, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getHostingPositionVolumeServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getHostingPositionVolume(int routeKey, int timeout, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingPositionVolume_args, TradeHostingAsset.getHostingPositionVolume_result> callback) throws TException{
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
            create_getHostingPositionVolumeServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getHostingPositionVolumeCall(AsyncCallRunner runner, int routeKey, int timeout, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingPositionVolume_args, TradeHostingAsset.getHostingPositionVolume_result> callback) throws TException{
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
            create_getHostingPositionVolumeServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getHostingPositionVolumeServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingPositionVolumeOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getHostingPositionVolume_args request = new TradeHostingAsset.getHostingPositionVolume_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingPositionVolume");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getHostingPositionVolume_result.class);
    return serviceCall;
  }

  public void send_getHostingPositionFund(int routeKey, int timeout, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getHostingPositionFundServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getHostingPositionFund(int routeKey, int timeout, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getHostingPositionFundServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getHostingPositionFund(int routeKey, int timeout, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingPositionFund_args, TradeHostingAsset.getHostingPositionFund_result> callback) throws TException{
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
            create_getHostingPositionFundServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getHostingPositionFundCall(AsyncCallRunner runner, int routeKey, int timeout, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getHostingPositionFund_args, TradeHostingAsset.getHostingPositionFund_result> callback) throws TException{
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
            create_getHostingPositionFundServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getHostingPositionFundServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqHostingPositionFundOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getHostingPositionFund_args request = new TradeHostingAsset.getHostingPositionFund_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getHostingPositionFund");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getHostingPositionFund_result.class);
    return serviceCall;
  }

  public void send_getSubAccountFundHistory(int routeKey, int timeout, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getSubAccountFundHistory(int routeKey, int timeout, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getSubAccountFundHistory(int routeKey, int timeout, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSubAccountFundHistory_args, TradeHostingAsset.getSubAccountFundHistory_result> callback) throws TException{
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
            create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getSubAccountFundHistoryCall(AsyncCallRunner runner, int routeKey, int timeout, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getSubAccountFundHistory_args, TradeHostingAsset.getSubAccountFundHistory_result> callback) throws TException{
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
            create_getSubAccountFundHistoryServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getSubAccountFundHistoryServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqSubAccountFundHistoryOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getSubAccountFundHistory_args request = new TradeHostingAsset.getSubAccountFundHistory_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubAccountFundHistory");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getSubAccountFundHistory_result.class);
    return serviceCall;
  }

  public void send_deleteExpiredContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId) throws TException {
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
        create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), new TRequestOption());
  }

  public void send_deleteExpiredContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId,TRequestOption requestOption) throws TException { 
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
        create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), requestOption);
  }

  public long deleteExpiredContractPosition(int routeKey, int timeout, long subAccountId, long sledContractId, IMethodCallback<TradeHostingAsset.deleteExpiredContractPosition_args, TradeHostingAsset.deleteExpiredContractPosition_result> callback) throws TException{
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
            create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), callback);
  }

  public long add_deleteExpiredContractPositionCall(AsyncCallRunner runner, int routeKey, int timeout, long subAccountId, long sledContractId, IMethodCallback<TradeHostingAsset.deleteExpiredContractPosition_args, TradeHostingAsset.deleteExpiredContractPosition_result> callback) throws TException{
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
            create_deleteExpiredContractPositionServiceCall(routeKey, timeout, platformArgs, subAccountId, sledContractId), callback);
  }

  protected TServiceCall create_deleteExpiredContractPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long subAccountId, long sledContractId){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.deleteExpiredContractPosition_args request = new TradeHostingAsset.deleteExpiredContractPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setSubAccountId(subAccountId);
    request.setSledContractId(sledContractId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteExpiredContractPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.deleteExpiredContractPosition_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountPositionTradeDetail(int routeKey, int timeout, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getTradeAccountPositionTradeDetail(int routeKey, int timeout, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getTradeAccountPositionTradeDetail(int routeKey, int timeout, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getTradeAccountPositionTradeDetail_args, TradeHostingAsset.getTradeAccountPositionTradeDetail_result> callback) throws TException{
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
            create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getTradeAccountPositionTradeDetailCall(AsyncCallRunner runner, int routeKey, int timeout, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getTradeAccountPositionTradeDetail_args, TradeHostingAsset.getTradeAccountPositionTradeDetail_result> callback) throws TException{
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
            create_getTradeAccountPositionTradeDetailServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getTradeAccountPositionTradeDetailServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqTradeAccountPositionTradeDetailOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getTradeAccountPositionTradeDetail_args request = new TradeHostingAsset.getTradeAccountPositionTradeDetail_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountPositionTradeDetail");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getTradeAccountPositionTradeDetail_result.class);
    return serviceCall;
  }

  public void send_getTradeAccountPosition(int routeKey, int timeout, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws TException {
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
        create_getTradeAccountPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), new TRequestOption());
  }

  public void send_getTradeAccountPosition(int routeKey, int timeout, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption,TRequestOption requestOption) throws TException { 
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
        create_getTradeAccountPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), requestOption);
  }

  public long getTradeAccountPosition(int routeKey, int timeout, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getTradeAccountPosition_args, TradeHostingAsset.getTradeAccountPosition_result> callback) throws TException{
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
            create_getTradeAccountPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  public long add_getTradeAccountPositionCall(AsyncCallRunner runner, int routeKey, int timeout, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption, IMethodCallback<TradeHostingAsset.getTradeAccountPosition_args, TradeHostingAsset.getTradeAccountPosition_result> callback) throws TException{
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
            create_getTradeAccountPositionServiceCall(routeKey, timeout, platformArgs, option, pageOption), callback);
  }

  protected TServiceCall create_getTradeAccountPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ReqTradeAccountPositionOption option, org.soldier.platform.page.IndexedPageOption pageOption){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.getTradeAccountPosition_args request = new TradeHostingAsset.getTradeAccountPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageOption(pageOption);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getTradeAccountPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.getTradeAccountPosition_result.class);
    return serviceCall;
  }

  public void send_assignPosition(int routeKey, int timeout, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds) throws TException {
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, positonAssigneds), new TRequestOption());
  }

  public void send_assignPosition(int routeKey, int timeout, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds,TRequestOption requestOption) throws TException { 
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
        create_assignPositionServiceCall(routeKey, timeout, platformArgs, positonAssigneds), requestOption);
  }

  public long assignPosition(int routeKey, int timeout, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds, IMethodCallback<TradeHostingAsset.assignPosition_args, TradeHostingAsset.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, positonAssigneds), callback);
  }

  public long add_assignPositionCall(AsyncCallRunner runner, int routeKey, int timeout, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds, IMethodCallback<TradeHostingAsset.assignPosition_args, TradeHostingAsset.assignPosition_result> callback) throws TException{
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
            create_assignPositionServiceCall(routeKey, timeout, platformArgs, positonAssigneds), callback);
  }

  protected TServiceCall create_assignPositionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned> positonAssigneds){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.assignPosition_args request = new TradeHostingAsset.assignPosition_args();
    request.setPlatformArgs(platformArgs);
    request.setPositonAssigneds(positonAssigneds);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("assignPosition");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.assignPosition_result.class);
    return serviceCall;
  }

  public void send_removeAllAssetData(int routeKey, int timeout) throws TException {
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
        create_removeAllAssetDataServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_removeAllAssetData(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
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
        create_removeAllAssetDataServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long removeAllAssetData(int routeKey, int timeout, IMethodCallback<TradeHostingAsset.removeAllAssetData_args, TradeHostingAsset.removeAllAssetData_result> callback) throws TException{
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
            create_removeAllAssetDataServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_removeAllAssetDataCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<TradeHostingAsset.removeAllAssetData_args, TradeHostingAsset.removeAllAssetData_result> callback) throws TException{
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
            create_removeAllAssetDataServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_removeAllAssetDataServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(TradeHostingAssetVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    TradeHostingAsset.removeAllAssetData_args request = new TradeHostingAsset.removeAllAssetData_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("removeAllAssetData");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(TradeHostingAsset.removeAllAssetData_result.class);
    return serviceCall;
  }

}
