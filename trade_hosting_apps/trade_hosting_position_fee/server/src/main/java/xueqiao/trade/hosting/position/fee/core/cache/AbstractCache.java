package xueqiao.trade.hosting.position.fee.core.cache;

import com.antiy.error_code.ErrorCodeInner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCache<K, T> {

    private LoadingCache<K, T> loadingCache;

    public AbstractCache() {
        initLoadingCache();
    }

    private void initLoadingCache() {
        /*
         * 指定如何数据不存在时的获取方法
         * */
        CacheLoader<K, T> cacheLoader = new CacheLoader<K, T>() {
            @Override
            public T load(K key) throws Exception {
                return loadWhenAbsent(key);
            }
        };
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(getExpireAfterWriteDuration(), getExpireAfterWriteTimeUnit()) // 1天后过期
                .maximumSize(getCacheMaxmumSize())
                .build(cacheLoader);
    }

    protected abstract T loadWhenAbsent(K key) throws TException;

    public T get(K key) throws ErrorInfo {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            AppLog.e("get fail ---- key : " + key, e);
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "get cache fail: " + e.getMessage());
        }
    }

    /**
     * 写后过期周期
     */
    protected int getExpireAfterWriteDuration() {
        return 1;
    }

    /**
     * 写后过期单位
     */
    protected TimeUnit getExpireAfterWriteTimeUnit() {
        return TimeUnit.DAYS;
    }

    /**
     * 缓存数量
     */
    protected int getCacheMaxmumSize() {
        return 50;
    }
}
