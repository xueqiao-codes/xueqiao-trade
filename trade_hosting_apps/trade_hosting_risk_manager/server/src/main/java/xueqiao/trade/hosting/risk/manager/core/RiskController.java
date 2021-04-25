package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.risk.manager.core.policy.RiskPolicyItem;
import xueqiao.trade.hosting.risk.manager.core.policy.RiskPolicyJoint;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskRuleResult;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueLevel;

import java.util.Map;

/**
 *  风险控制器
 */
public class RiskController {

    private RiskContext mContext;
    private RawDataPool mRawDataPool;

    private volatile RiskPolicyJoint mCurrentJoint;

    public RiskController(RiskContext context
            , RawDataPool rawDataPool) {
        this.mContext = context;
        this.mRawDataPool = rawDataPool;
        this.mCurrentJoint = new RiskPolicyJoint();
    }

    public RiskPolicyJoint getRiskPolicyJoint() {
        return mCurrentJoint;
    }

    /**
     *  检测报警结果的的变化
     */
    public void handleRiskRuleResultCheck(Map<String, RiskRuleResult> oldResults
                , Map<String, RiskRuleResult> newResults) {
        Preconditions.checkState(mContext.getWorkThread().isInCurrentThread());

        RiskPolicyJoint newJoint = new RiskPolicyJoint();

        for (Map.Entry<String, RiskRuleResult> newEntry : newResults.entrySet()) {
            RiskRuleResult newResult = newEntry.getValue();
            RiskRuleResult oldResult = oldResults.get(newEntry.getKey());
            if (oldResult == null) {
                // TODO 一个风控报警触发
            } else {
                if (!oldResult.equals(newResult)) {
                    // TODO 风控报警发生了变化
                }
            }

            if (!newResult.isForbiddenOpenPositionTriggered()) {
                continue;
            }

            // 确定禁止开仓的级别
            if (newResult.getItem().getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE) {
                if (newJoint.getGlobalPolicy().isForbiddenOpenPosition()) {
                    continue;
                }

                newJoint.getGlobalPolicy().setForbiddenOpenPosition(true
                        , newResult.getForbiddenOpenPositionDescription()
                        , 0);
                newJoint.setCommodityPositionCounts(mRawDataPool.getCommodityPositionCounts());
            } else {
                Preconditions.checkState(newResult.getCommodityId() > 0);
                if (newResult.getItem().getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE) {
                    // 商品上

                    RiskPolicyItem commodityPolicy = newJoint.getCommodityPolices().get(newResult.getCommodityId());
                    if (commodityPolicy != null) {
                        continue;
                    }

                    commodityPolicy = new RiskPolicyItem(newResult.getCommodityId());
                    Long positionCount = mRawDataPool.getCommodityPositionCounts().get(newResult.getCommodityId());
                    if (positionCount == null) {
                        continue;
                    }

                    commodityPolicy.setForbiddenOpenPosition(true
                            , newResult.getForbiddenOpenPositionDescription()
                            , positionCount);

                    newJoint.getCommodityPolices().put(commodityPolicy.getCommodityId(), commodityPolicy);

                } else {
                    // 合约上

                    Preconditions.checkState(newResult.getContractId() > 0);
                    RiskPolicyItem contractPolicy = newJoint.getContractPolicies().get(newResult.getContractId());
                    if (contractPolicy != null) {
                        continue;
                    }

                    contractPolicy = new RiskPolicyItem(newResult.getCommodityId(), newResult.getContractId());
                    Map<Long, HostingSledContractPosition> contractPositionMap
                            = mRawDataPool.getContractPositions().get(newResult.getCommodityId());
                    if (contractPositionMap == null) {
                        continue;
                    }
                    HostingSledContractPosition contractPosition = contractPositionMap.get(newResult.getContractId());
                    if (contractPosition == null || !contractPosition.isSetPositionVolume()) {
                        continue;
                    }

                    contractPolicy.setForbiddenOpenPosition(true
                            , newResult.getForbiddenOpenPositionDescription()
                            , contractPosition.getPositionVolume().getNetPosition());

                    newJoint.getContractPolicies().put(contractPolicy.getContractId(), contractPolicy);
                }
            }
        }

        mCurrentJoint = newJoint;

        if (AppLog.debugEnabled()) {
            AppLog.d("handleRiskRuleResultCheck subAccountId=" + mContext.getSubAccountId()
                    + ", newJoint=\n" + newJoint);
        }

        for (Map.Entry<String, RiskRuleResult> oldEntry : oldResults.entrySet()) {
            RiskRuleResult newResult = newResults.get(oldEntry.getKey());
            if (newResult == null) {
                // TODO 风控解除
            }
        }

    }


}
