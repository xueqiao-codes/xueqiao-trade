package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

public class TargetData {
    private long subAccountId;
    private String targetKey;
    private HostingXQTargetType targetType;

    public TargetData(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        this.subAccountId = subAccountId;
        this.targetKey = targetKey;
        this.targetType = targetType;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public HostingXQTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(HostingXQTargetType targetType) {
        this.targetType = targetType;
    }
}
