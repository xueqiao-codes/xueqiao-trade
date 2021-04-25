package xueqiao.trade.hosting.position.statis.core.quotation;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.quot.comb.sdk.IQuotCombListener;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

public abstract class StatQuoCombListener extends StatQuotationBaseListener<HostingQuotationComb, StatQuoCombCache> implements IQuotCombListener {

    protected StatQuoCombListener(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    @Override
    public void onReceivedQuotationComb(HostingQuotationComb quotationComb) throws Exception {
        /*
         * 如果最新价无效，则丢弃该行情，并且不触发计算
         * */
        if (!quotationComb.getCombItem().isSetLastPrice()) {
            return;
        }
        taskContext.getmTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    StatQuoCombCache quoCombCache = cacheQuotation(quotationComb);
                    if (isSkip()) {
                        return;
                    }
                    onHandleQuotationItem(quoCombCache);
                } catch (Throwable e) {
                    AppLog.e("StatQuoCombListener # onReceivedQuotationComb ---- throwable ", e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected StatQuoCombCache cacheQuotation(HostingQuotationComb quoItem) throws TException {
        return StatQuoCombCacheManager.getInstance().putLatestQuotation(quoItem.getComposeGraphId(), quoItem);
    }
}
