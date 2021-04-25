package xueqiao.trade.hosting.position.statis.core.cache.quotation;

import xueqiao.trade.hosting.position.statis.core.cache.ICacheManager;

public interface IQuoCacheManager<Q, C> extends ICacheManager {
    public C getLatestQuotation(long key);

    public C putLatestQuotation(long key, Q quotation);
}
