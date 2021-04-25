package xueqiao.trade.hosting.position.fee.core.bean;

import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;

public class XQDefaultPositionRate {

    private long commodityId;
    private CommissionInfo commissionInfo;
    private MarginInfo marginInfo;

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public CommissionInfo getCommissionInfo() {
        return commissionInfo;
    }

    public void setCommissionInfo(CommissionInfo commissionInfo) {
        this.commissionInfo = commissionInfo;
    }

    public MarginInfo getMarginInfo() {
        return marginInfo;
    }

    public void setMarginInfo(MarginInfo marginInfo) {
        this.marginInfo = marginInfo;
    }

}
