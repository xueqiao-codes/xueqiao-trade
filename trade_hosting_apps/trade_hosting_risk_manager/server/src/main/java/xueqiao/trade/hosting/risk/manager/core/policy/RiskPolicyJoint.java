package xueqiao.trade.hosting.risk.manager.core.policy;

import java.util.HashMap;
import java.util.Map;

public class RiskPolicyJoint {

    private RiskPolicyItem mGlobalPolicy = new RiskPolicyItem();
    private Map<Long, Long> mCommodityPositionCounts; // 全局策略禁止开仓时有效

    private Map<Long, RiskPolicyItem> mCommodityPolicies = new HashMap<>();
    private Map<Long, RiskPolicyItem> mContractPolicies = new HashMap<>();

    public RiskPolicyItem getGlobalPolicy() {
        return mGlobalPolicy;
    }

    public Map<Long, RiskPolicyItem> getCommodityPolices() {
        return mCommodityPolicies;
    }

    public Map<Long, RiskPolicyItem> getContractPolicies() {
        return mContractPolicies;
    }

    public Map<Long, Long> getCommodityPositionCounts() {
        return mCommodityPositionCounts;
    }

    public void setCommodityPositionCounts(Map<Long, Long> commodityPositionCounts) {
        this.mCommodityPositionCounts = commodityPositionCounts;
    }

    @Override
    public String toString() {
        StringBuilder descBuilder = new StringBuilder(512);
        descBuilder.append("global:").append(mGlobalPolicy).append("\n");
        if (mCommodityPositionCounts != null) {
            descBuilder.append("commodityPositions:").append(mCommodityPositionCounts).append("\n");
        }
        for (RiskPolicyItem commodityPolicy : mCommodityPolicies.values()) {
            descBuilder.append("  commodity:").append(commodityPolicy).append("\n");
        }
        for (RiskPolicyItem contractPolicy : mContractPolicies.values()) {
            descBuilder.append("    contract:").append(contractPolicy).append("\n");
        }

        return descBuilder.toString();
    }

}
