package xueqiao.trade.hosting.arbitrage.core.composelimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;

public interface IComposeEngine {
    public void onCreate(XQOrderComposeLimitExecutor executor) throws ErrorInfo;
    
    public void onDestroy() throws ErrorInfo;
    
    public void onReceivedQuotationItem(QuotationItem quotationItem) throws ErrorInfo;
    
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor
            , HostingExecOrder lastestOrder) throws ErrorInfo;

    public void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo;
}
