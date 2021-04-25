package xueqiao.trade.hosting.asset.quotation;

import org.soldier.base.logger.AppLog;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;

import java.math.BigDecimal;

public abstract class AssetABQuotationListener implements IQuotationListener {

    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception {
        getTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    onHandleQuotationItem(quotationItem);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        });
    }

    public abstract TaskThread getTaskThread();

    public abstract void onHandleQuotationItem(QuotationItem quotationItem) throws Exception;

    protected BigDecimal doubleToBigDecimal(double x) {
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        return new BigDecimal(Double.toString(x));
    }
}
