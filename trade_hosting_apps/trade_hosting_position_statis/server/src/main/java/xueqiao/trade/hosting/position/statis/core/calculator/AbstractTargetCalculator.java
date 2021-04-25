package xueqiao.trade.hosting.position.statis.core.calculator;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

public abstract class AbstractTargetCalculator<T, Q> extends AbstractCalculator<T, Q> {

    protected long subAccountId;
    protected String targetKey;
    protected HostingXQTargetType targetType;

    public AbstractTargetCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId);
        this.subAccountId = subAccountId;
        this.targetKey = targetKey;
        this.targetType = targetType;
    }
}
