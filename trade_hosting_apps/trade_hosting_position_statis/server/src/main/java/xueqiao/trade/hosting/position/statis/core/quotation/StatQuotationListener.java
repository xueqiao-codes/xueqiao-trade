package xueqiao.trade.hosting.position.statis.core.quotation;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;

public abstract class StatQuotationListener extends StatQuotationBaseListener<QuotationItem, StatQuotationCache> implements IQuotationListener {

    protected StatQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception {
        /*
        * 如果最新价无效，则丢弃该行情，并且不触发计算
        * */
        if (!quotationItem.isSetLastPrice()) {
            return;
        }
        taskContext.getmTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    StatQuotationCache quotationCache = cacheQuotation(quotationItem);
                    if (isSkip()) {
                        return;
                    }
                    onHandleQuotationItem(quotationCache);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 缓存新最的行情
     */
    @Override
    protected StatQuotationCache cacheQuotation(QuotationItem quoItem) throws TException {
        long contractId = Long.valueOf(targetKey);
        return StatQuotationCacheManager.getInstance().putLatestQuotation(contractId, quoItem);
    }
}
