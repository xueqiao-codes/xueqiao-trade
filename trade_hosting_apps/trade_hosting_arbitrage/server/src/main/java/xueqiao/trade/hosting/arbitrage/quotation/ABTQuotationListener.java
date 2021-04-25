package xueqiao.trade.hosting.arbitrage.quotation;

import org.soldier.base.logger.AppLog;

import com.google.common.base.Preconditions;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;

public abstract class ABTQuotationListener implements IQuotationListener {

    private boolean  mAttached = false;
    
    protected void attachQuotationListener() {
        Preconditions.checkState(getTaskThread().isInCurrentThread());
        mAttached = true;
    }
    
    protected void detachQuotationListener() {
        Preconditions.checkState(getTaskThread().isInCurrentThread());
        mAttached = false;
    }
    
    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception {
        getTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mAttached) {
                        return ;
                    }
                    onHandleQuotationItem(quotationItem);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        });
    }

    public abstract TaskThread getTaskThread();
    public abstract void onHandleQuotationItem(QuotationItem quotationItem) throws Exception;
}
