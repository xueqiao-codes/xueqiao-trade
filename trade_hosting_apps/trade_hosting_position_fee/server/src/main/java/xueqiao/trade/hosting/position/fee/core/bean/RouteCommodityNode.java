package xueqiao.trade.hosting.position.fee.core.bean;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityType;

public class RouteCommodityNode {
    //    private String exchangeMic;
    private long commodityId;
    private SledCommodityType commodityType;
    private boolean forbidden;
    private long tradeAccountId;

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public SledCommodityType getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(SledCommodityType commodityType) {
        this.commodityType = commodityType;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    public long getTradeAccountId() {
        return tradeAccountId;
    }

    public void setTradeAccountId(long tradeAccountId) {
        this.tradeAccountId = tradeAccountId;
    }
}
