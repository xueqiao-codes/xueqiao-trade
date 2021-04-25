package xueqiao.trade.hosting.arbitrage.core.actor.composelimit;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeEngineFactory;
import xueqiao.trade.hosting.arbitrage.core.composelimit.IComposeEngine;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.quotation.ABTQuotationListener;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.io.UnsupportedEncodingException;

public class XQOrderComposeLimitRunningActor extends ABTQuotationListener implements IXQOrderActor {
    private XQOrderComposeLimitExecutor mExecutor;
    private IComposeEngine mEngine;
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderComposeLimitExecutor.class.cast(executor);
        
        if (!mExecutor.getEffectDateController().handleActorRunning(mExecutor)) {
            return ;
        }
        
        // 订阅行情
        super.attachQuotationListener();
        for (XQOrderSubExecutor subExecutor : mExecutor.getSubExecutors().values()) {
            try {
                if (AppLog.debugEnabled()) {
                    AppLog.d("registerQuotationListener /" + subExecutor.getQuotationPlatform()
                            + "/" + subExecutor.getQuotationContractSymbol()
                            + " for xqOrderId=" + mExecutor.getOrder().getOrderId());
                }
                QuotationDispatcher.Global().registerListener(
                        subExecutor.getQuotationPlatform(), subExecutor.getQuotationContractSymbol(), this);
            } catch (UnsupportedEncodingException e) {
                AppLog.f(e.getMessage(), e);
            }
        }
        
        mEngine = ComposeEngineFactory.createEngine(mExecutor.getOrder());
        if (mEngine != null) {
            mEngine.onCreate(mExecutor);
        }
    }

    @Override
    public void onDestroy() throws ErrorInfo {
        for (XQOrderSubExecutor subExecutor : mExecutor.getSubExecutors().values()) {
            try {
                if (AppLog.debugEnabled()) {
                    AppLog.d("unRegisterQuotationListener /" + subExecutor.getQuotationPlatform()
                            + "/" + subExecutor.getQuotationContractSymbol()
                            + " for xqOrderId=" + mExecutor.getOrder().getOrderId());
                }
                QuotationDispatcher.Global().unRegisterListener(
                        subExecutor.getQuotationPlatform(), subExecutor.getQuotationContractSymbol(), this);
            } catch (UnsupportedEncodingException e) {
                AppLog.f(e.getMessage(), e);
            }
        }
        super.detachQuotationListener();
        
        if (mEngine != null) {
            mEngine.onDestroy();
            mEngine = null;
        }
    }
    
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor, HostingExecOrder lastestOrder) throws ErrorInfo {
        if (mEngine != null) {
            mEngine.onLastestOrderChanged(subExecutor, lastestOrder);
        }
    }
    
    @Override
    public void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo {
        if (mEngine != null) {
            mEngine.onTradeListChanged(subExecutor);
        }
    }

    @Override
    public TaskThread getTaskThread() {
        return mExecutor.getContext().getWorkThread();
    }

    @Override
    public void onHandleQuotationItem(QuotationItem quotationItem) throws Exception {
        new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                if (mEngine != null) {
                    mEngine.onReceivedQuotationItem(quotationItem);
                }
            }
        }.run();
    }

}
