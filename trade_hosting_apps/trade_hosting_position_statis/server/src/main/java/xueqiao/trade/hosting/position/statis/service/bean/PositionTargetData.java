package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

public class PositionTargetData {
    private String targetKey;
    private HostingXQTargetType targetType;

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
