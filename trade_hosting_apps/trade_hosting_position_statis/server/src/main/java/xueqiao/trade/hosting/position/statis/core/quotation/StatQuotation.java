package xueqiao.trade.hosting.position.statis.core.quotation;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.thread.TaskContext;
import xueqiao.trade.hosting.position.statis.core.thread.TaskThreadManager;

import java.math.BigDecimal;

public abstract class StatQuotation<T, Q> {
    protected long subAccountId;
    protected String targetKey;
    protected HostingXQTargetType targetType;
    protected TaskContext taskContext;

    protected StatQuotation(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        this.subAccountId = subAccountId;
        this.targetKey = targetKey;
        this.targetType = targetType;
        this.taskContext = TaskThreadManager.getInstance().getTaskThread(this.subAccountId);
    }

    /**
     * 降频策略
     */
    protected boolean isSkip() {
        return QuotationFreqStrategy.getInstance().isReduce(subAccountId, targetKey, targetType);
    }

    public abstract void onHandleQuotationItem(Q quoItem) throws Exception;

    /**
     * 缓存新最的行情
     */
    protected abstract Q cacheQuotation(T quoItem) throws TException;

    protected BigDecimal doubleToBigDecimal(double x) {
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        return new BigDecimal(Double.toString(x));
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public HostingXQTargetType getTargetType() {
        return targetType;
    }
}
