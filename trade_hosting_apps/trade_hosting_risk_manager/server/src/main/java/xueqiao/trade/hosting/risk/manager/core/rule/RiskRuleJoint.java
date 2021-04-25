package xueqiao.trade.hosting.risk.manager.core.rule;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import xueqiao.trade.hosting.risk.manager.core.RiskSupportedInfoHolder;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskLevel;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RiskRuleJoint extends HostingRiskRuleJoint {

    public RiskRuleJoint() {
        this.setVersion(0);
        this.setSubAccountId(0);
        this.setRiskEnabled(false);
        this.setGlobalOpenedItemIds(new HashSet<>());
        this.setGlobalRules(new HashMap<>());
        this.setTradedCommodityIds(new HashSet<>());
        this.setCommodityRules(new HashMap<>());
        this.setCommodityOpenedItemIds(new HashSet<>());
    }

    public RiskRuleJoint(RiskRuleJoint joint) {
        super(joint);
    }

    public void batchOpenSupportedItems(Set<String> openedItemIds) {
        if (openedItemIds == null || openedItemIds.isEmpty()) {
            return ;
        }

        Map<String, RiskSupportedInfoHolder.HostingRiskSupportedInfo> supportedInfoMap
                = RiskSupportedInfoHolder.Global().getSupportedInfos();

        for (String openedItemId : openedItemIds) {
            if (StringUtils.isEmpty(openedItemId)) {
                continue;
            }

            if (getGlobalOpenedItemIds().contains(openedItemId) || getCommodityOpenedItemIds().contains(openedItemId)) {
                continue;
            }

            RiskSupportedInfoHolder.HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(openedItemId);
            if (supportedInfo == null) {
                continue;
            }

            if (supportedInfo.getItem().getRiskLevel() == EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL) {
                getGlobalOpenedItemIds().add(openedItemId);
            } else if (supportedInfo.getItem().getRiskLevel() == EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY){
                getCommodityOpenedItemIds().add(openedItemId);
            } else {
                Preconditions.checkState(false);
            }
        }
    }

    public void batchCloseSupportedItems(Set<String> closedItemIds) {
        if (closedItemIds == null || closedItemIds.isEmpty()) {
            return ;
        }

        Map<String, RiskSupportedInfoHolder.HostingRiskSupportedInfo> supportedInfoMap
                = RiskSupportedInfoHolder.Global().getSupportedInfos();

        for (String closedItemId : closedItemIds) {
            if (StringUtils.isEmpty(closedItemId)) {
                continue;
            }

            // 关闭的条目压根没有打开
            if (!getGlobalOpenedItemIds().contains(closedItemId) && !getCommodityOpenedItemIds().contains(closedItemId)) {
                continue;
            }

            RiskSupportedInfoHolder.HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(closedItemId);
            if (supportedInfo == null) {
                continue;
            }

            // 禁用所有上该条目的规则
            if (supportedInfo.getItem().getRiskLevel() == EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL) {
                HostingRiskRuleItem globalRule = getGlobalRules().get(closedItemId);
                if (globalRule != null) {
                    globalRule.setRuleEnabled(false);
                }
            } else if (supportedInfo.getItem().getRiskLevel() == EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY) {
                for (Map<String, HostingRiskRuleItem> commodityRuleMap : getCommodityRules().values()) {
                    HostingRiskRuleItem commodityRule = commodityRuleMap.get(closedItemId);
                    if (commodityRule != null) {
                        commodityRule.setRuleEnabled(false);
                    }
                }
            }
        }
    }

    public void batchEnabledTradeCommodityItems(Set<Long> enabledCommodityIds) {
        if (enabledCommodityIds == null || enabledCommodityIds.isEmpty()) {
            return ;
        }

        for (Long enabledCommodityId : enabledCommodityIds) {
            if (enabledCommodityId <= 0 || getTradedCommodityIds().contains(enabledCommodityId)) {
                continue;
            }

            getTradedCommodityIds().add(enabledCommodityId);
        }
    }

    public void batchDisableTradedCommodityItems(Set<Long> disabledCommodityIds) {
        if (disabledCommodityIds == null || disabledCommodityIds.isEmpty()) {
            return ;
        }

        for (Long disabledCommodityId : disabledCommodityIds) {
            if (disabledCommodityId <= 0 || !getTradedCommodityIds().contains(disabledCommodityId)) {
                continue;
            }

            getTradedCommodityIds().remove(disabledCommodityId);

            Map<String, HostingRiskRuleItem> commodityRuleMap = getCommodityRules().get(disabledCommodityId);
            if (commodityRuleMap != null) {
                for (HostingRiskRuleItem commodityRule : commodityRuleMap.values()) {
                    commodityRule.setRuleEnabled(false);
                }
            }
        }
    }

    public void batchSetGlobalRules(Map<String, HostingRiskRuleItem> opGlobalRules) {
        if (opGlobalRules == null || opGlobalRules.isEmpty()) {
            return ;
        }

        Map<String, RiskSupportedInfoHolder.HostingRiskSupportedInfo> supportedInfoMap
                = RiskSupportedInfoHolder.Global().getSupportedInfos();

        for (Map.Entry<String, HostingRiskRuleItem> opEntry : opGlobalRules.entrySet()) {
            if (!getGlobalOpenedItemIds().contains(opEntry.getKey())) {
                continue;
            }

            RiskSupportedInfoHolder.HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(opEntry.getKey());
            if (supportedInfo == null
                    || supportedInfo.getItem().getRiskLevel() != EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL) {
                continue;
            }

            getGlobalRules().put(opEntry.getKey(), opEntry.getValue());
        }
    }


    public void batchSetCommodityRules(Map<Long, Map<String, HostingRiskRuleItem>> opCommodityRules) {
        if (opCommodityRules == null || opCommodityRules.isEmpty()) {
            return ;
        }

        Map<String, RiskSupportedInfoHolder.HostingRiskSupportedInfo> supportedInfoMap
                = RiskSupportedInfoHolder.Global().getSupportedInfos();

        for (Map.Entry<Long, Map<String, HostingRiskRuleItem>> commodityRuleEntry : opCommodityRules.entrySet()) {
            long sledCommodityId = commodityRuleEntry.getKey();
            if (sledCommodityId <= 0 || !getTradedCommodityIds().contains(sledCommodityId)) {
                continue;
            }

            Map<String, HostingRiskRuleItem> commodityRuleMap = getCommodityRules().get(sledCommodityId);
            if (commodityRuleMap == null) {
                commodityRuleMap = new HashMap<>();
                getCommodityRules().put(sledCommodityId, commodityRuleMap);
            }

            for (Map.Entry<String, HostingRiskRuleItem> itemEntry : commodityRuleEntry.getValue().entrySet()) {
                RiskSupportedInfoHolder.HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(itemEntry.getKey());
                if (supportedInfo == null
                        || supportedInfo.getItem().getRiskLevel() != EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY) {
                    continue;
                }

                commodityRuleMap.put(itemEntry.getKey(), itemEntry.getValue());
            }

        }
    }


//    public static void main(String[] args) {
//        AppLog.init("test_rrt");
//
//        RiskRuleJoint rrJoint = new RiskRuleJoint();
//        rrJoint.setSubAccountId(1000);
//
//        Set<Long> tradedCommodityIds = new HashSet<>();
//        tradedCommodityIds.add(1000L);
//        tradedCommodityIds.add(2000L);
//        rrJoint.batchEnabledTradeCommodityItems(tradedCommodityIds);
//
//        Set<String> openedItemIds = new HashSet<>();
//        openedItemIds.add("op.g.f.dynamicBenefit");
//        openedItemIds.add("op.cm.p.count");
//        openedItemIds.add("op.cm.f.margin");
//        openedItemIds.add("not.existed");
//
//        rrJoint.setRiskEnabled(true);
//        rrJoint.batchOpenSupportedItems(openedItemIds);
//
//        List<HostingRiskCommodityRule> commodityRules = new ArrayList<>();
//        commodityRules.add(new HostingRiskCommodityRule().setSubAccountId(1000)
//                        .setItemId("op.cm.f.margin")
//                        .setSledCommodityId(1000L)
//                        .setRuleItem(new HostingRiskRuleItem()
//                                .setRuleEnabled(true)
//                                .setAlarmValue(new HostingRiskRuleItemValue().setDoubeValue(10000d)))
//                      );
//        rrJoint.batchSetCommodityRules(commodityRules);
//        System.out.println(rrJoint.toString());
//
//        RiskRuleJoint hahaJoint = ProtocolUtil.unSerialize(new TCompactProtocol.Factory()
//                , ProtocolUtil.serialize2Bytes(new TCompactProtocol.Factory(), rrJoint)
//                , RiskRuleJoint.class);
//
//        System.out.println(hahaJoint.toString());
//    }
}
