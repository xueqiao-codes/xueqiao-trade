package xueqiao.trade.hosting.risk.manager.core.policy;

import xueqiao.trade.hosting.HostingExecOrderTradeDirection;

/**
 *  商品或者合约的风控规则
 */
public class RiskPolicyItem {

    private long mSledCommodityId;
    private long mSledContractId;

    private boolean mForbiddenOpenPosition;  // 禁止开仓
    private String mForbiddenOpenPositionReason; // 禁止开仓原因

    private long mNetPositionCount;  // 风控时的净仓数目
    private HostingExecOrderTradeDirection mClosePositionDirection; // 平仓方向

    public RiskPolicyItem() {
    }

    public RiskPolicyItem(long sledCommodityId) {
        this.mSledCommodityId = sledCommodityId;
    }

    public RiskPolicyItem(long sledCommodityId, long sledContractId) {
        this.mSledCommodityId = sledCommodityId;
        this.mSledContractId = sledContractId;
    }

    public long getCommodityId() {
        return mSledCommodityId;
    }

    public long getContractId() {
        return mSledContractId;
    }

    public void setForbiddenOpenPosition(boolean forbiddened
            , String reason
            , long netPositionCount) {
        this.mForbiddenOpenPosition = forbiddened;
        this.mForbiddenOpenPositionReason = reason;
        this.mNetPositionCount = netPositionCount;

        if (mNetPositionCount > 0) {
            mClosePositionDirection = HostingExecOrderTradeDirection.ORDER_SELL;
        } else if (mNetPositionCount < 0) {
            mClosePositionDirection = HostingExecOrderTradeDirection.ORDER_BUY;
        }
    }

    public long getNetPositionCount() {
        return mNetPositionCount;
    }

    public boolean isForbiddenOpenPosition() {
        return mForbiddenOpenPosition;
    }

    public String getForbiddenOpenPositionReason() {
        return mForbiddenOpenPositionReason;
    }

    public HostingExecOrderTradeDirection getClosePositionDirection() {
        return mClosePositionDirection;
    }

    @Override
    public String toString() {
        StringBuilder descBuilder = new StringBuilder(64);
        descBuilder.append("RiskPolicyItem[commodityId=").append(mSledCommodityId)
                   .append(",contractId=").append(mSledContractId)
                   .append("], forbidden=")
                   .append(mForbiddenOpenPosition)
                   .append(", reason=")
                   .append(mForbiddenOpenPositionReason)
                   .append(", direction=")
                   .append(mClosePositionDirection);
        return descBuilder.toString();
    }
}
