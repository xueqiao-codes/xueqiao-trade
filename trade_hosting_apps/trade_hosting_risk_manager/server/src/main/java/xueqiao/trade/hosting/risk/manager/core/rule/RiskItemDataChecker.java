package xueqiao.trade.hosting.risk.manager.core.rule;

import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;
import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity.CommodityCalculator;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract.ContractCalculator;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueLevel;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueType;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskLadderType;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItemValue;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class RiskItemDataChecker {
    private Map<String, RiskRuleResult> mResults;

    public void begin() {
        mResults = new HashMap<>();
    }

    private boolean isRuleValueTriggerd(
            HostingRiskSupportedItem supportedItem
            , RiskItemValue calValue
            , HostingRiskRuleItemValue ruleItemValue
            , StringBuilder descBuilder) {
        if (!calValue.isSetValue()) {
            return false;
        }

        boolean triggered = false;
        if (supportedItem.getItemValueType() == EHostingRiskItemValueType.LONG_VALUE) {
            if (!ruleItemValue.isSetLongValue()) {
                return false;
            }

            long rule = ruleItemValue.getLongValue();
            long cal = calValue.getLong();

            if (supportedItem.getRiskLadderType() == EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK) {
                triggered =(cal >= rule);
            } else {
                triggered = (cal <= rule);
            }
        } else {
            if (!ruleItemValue.isSetDoubeValue() || !Double.isFinite(ruleItemValue.getDoubeValue())) {
                return false;
            }

            double rule = ruleItemValue.getDoubeValue();
            double cal = calValue.getDouble();

            if (supportedItem.getRiskLadderType() == EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK) {
                triggered = (Double.compare(cal, rule) >= 0);
            } else {
                triggered = (Double.compare(cal, rule) <= 0);
            }
        }

        if (triggered) {
            descBuilder.append(supportedItem.getItemCnName());
            if (supportedItem.getRiskLadderType() == EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK) {
                descBuilder.append("过高,超过");
            } else {
                descBuilder.append("过低,低于");
            }
            if (supportedItem.getItemValueType() == EHostingRiskItemValueType.LONG_VALUE) {
                descBuilder.append(ruleItemValue.getLongValue());
            } else {
                descBuilder.append(String.format("%#.2f", ruleItemValue.getDoubeValue()));
                if (supportedItem.getItemValueType() == EHostingRiskItemValueType.PERCENT_VALUE) {
                    descBuilder.append("%");
                }
            }
        }

        return triggered;
    }

    public void check(RiskItemCalculator calculator, RiskRuleJoint rrJoint) {
        HostingRiskSupportedItem supportedItem = calculator.getItem();

        if (!rrJoint.isRiskEnabled()) {
            return ;
        }

        long sledCommodityId = -1;
        long sledContractId = -1;
        HostingRiskRuleItem ruleItem = null;
        if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE) {
            ruleItem = rrJoint.getGlobalRules().get(supportedItem.getItemId());
        } else if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE) {
            sledCommodityId = ((CommodityCalculator)calculator).getCommodityId();
            Map<String,HostingRiskRuleItem> commodityRuleMap = rrJoint.getCommodityRules().get(sledCommodityId);
            if (commodityRuleMap != null) {
                ruleItem = commodityRuleMap.get(supportedItem.getItemId());
            }
        } else if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_CONTRACT_VALUE) {
            sledCommodityId = ((ContractCalculator)calculator).getCommodityId();
            sledContractId = ((ContractCalculator)calculator).getContractId();

            Map<String,HostingRiskRuleItem> commodityRuleMap = rrJoint.getCommodityRules().get(sledCommodityId);
            if (commodityRuleMap != null) {
                ruleItem = commodityRuleMap.get(supportedItem.getItemId());
            }
        }

        if (ruleItem == null || !ruleItem.isRuleEnabled()) {
            return ;
        }

        boolean isAlarmTriggered = false;
        StringBuilder alarmDescBuilder = new StringBuilder(64);
        boolean isForbiddenOpenPositionTriggered = false;
        StringBuilder forbiddenOpenPositionDescBuilder = new StringBuilder(64);

        if (ruleItem.isSetAlarmValue()) {
            isAlarmTriggered = isRuleValueTriggerd(supportedItem
                    , calculator.getValue()
                    , ruleItem.getAlarmValue()
                    , alarmDescBuilder);
        }
        if (ruleItem.isSetForbiddenOpenPositionValue()) {
            isForbiddenOpenPositionTriggered = isRuleValueTriggerd(supportedItem
                    , calculator.getValue()
                    , ruleItem.getForbiddenOpenPositionValue()
                    , forbiddenOpenPositionDescBuilder);
        }

        if (isAlarmTriggered || isForbiddenOpenPositionTriggered) {
            RiskRuleResult result = new RiskRuleResult(supportedItem, calculator.getParams());
            if (sledCommodityId > 0) {
                result.setCommodityId(sledCommodityId);
            }

            if (sledContractId > 0) {
                result.setContractId(sledContractId);
            }

            result.setAlarmTriggered(isAlarmTriggered, alarmDescBuilder.toString());
            result.setForbiddenOpenPositionTriggered(isForbiddenOpenPositionTriggered
                            , forbiddenOpenPositionDescBuilder.toString());

            mResults.put(result.getKey(), result);
        }

    }

    public Map<String, RiskRuleResult> getResults() {
        return mResults;
    }

}
