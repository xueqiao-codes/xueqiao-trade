package xueqiao.trade.hosting.risk.manager.core;

import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity.CommodityFundMargin;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity.CommodityPositionCount;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract.ContractFundMargin;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract.ContractPositionCount;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundAviable;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundDynamicBenefit;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundGoodsValue;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundLeverage;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundMargin;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.global.GlobalFundRiskRate;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueLevel;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueType;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskLadderType;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskLevel;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;

import java.util.HashMap;
import java.util.Map;

public class RiskSupportedInfoHolder {
    private static RiskSupportedInfoHolder sInstance;
    public static RiskSupportedInfoHolder Global() {
        if (sInstance == null) {
            synchronized (RiskSupportedInfoHolder.class) {
                if (sInstance == null) {
                    sInstance = new RiskSupportedInfoHolder();
                }
            }
        }
        return sInstance;
    }

    public static class HostingRiskSupportedInfo {
        private HostingRiskSupportedItem mItem;
        private Class<? extends RiskItemCalculator> mProcessorClass;

        public HostingRiskSupportedInfo(HostingRiskSupportedItem item
                , Class<? extends RiskItemCalculator> processorClass) {
            this.mItem = item;
            this.mProcessorClass = processorClass;
        }

        public Class<? extends RiskItemCalculator> getProcessorClass() {
            return mProcessorClass;
        }

        public HostingRiskSupportedItem getItem() {
            return mItem;
        }


    }

    private int mLastOrderNum = 1;

    private Map<String, HostingRiskSupportedInfo> mSupportedInfos = new HashMap<>();

    public Map<String, HostingRiskSupportedInfo> getSupportedInfos() {
        return mSupportedInfos;
    }

    private RiskSupportedInfoHolder() {
        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.dynamicBenefit"
                , "????????????"
                , "????????????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.LOW_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundDynamicBenefit.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.avaiable"
                , "????????????"
                , "????????????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.LOW_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundAviable.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.margin"
                , "?????????"
                , "?????????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundMargin.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.riskRate"
                , "?????????"
                , "?????????"
                , EHostingRiskItemValueType.PERCENT_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundRiskRate.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.goodsValue"
                , "??????"
                , "??????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundGoodsValue.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_GLOBAL
                , "op.g.f.leverage"
                , "??????"
                ,"??????"
                , EHostingRiskItemValueType.PERCENT_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE
                , GlobalFundLeverage.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
                , "op.cm.p.count"
                , "???????????????"
                , "????????????????????????????????????"
                , EHostingRiskItemValueType.LONG_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE
                , CommodityPositionCount.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
                , "op.cn.p.count"
                , "??????????????????"
                , "???????????????"
                , EHostingRiskItemValueType.LONG_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_CONTRACT_VALUE
                , ContractPositionCount.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
                , "op.cm.f.margin"
                , "???????????????"
                , "????????????????????????????????????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE
                , CommodityFundMargin.class);

        addSupportedInfo(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
                , "op.cn.f.margin"
                , "??????????????????"
                , "??????????????????????????????"
                , EHostingRiskItemValueType.DOUBLE_VALUE
                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK
                , EHostingRiskItemValueLevel.OPERATION_ACCOUNT_CONTRACT_VALUE
                , ContractFundMargin.class);
//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cm.f.riskRate"
//                , "???????????????"
//                , "???????????????"
//                , EHostingRiskItemValueType.PERCENT_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cm.f.goodsValue"
//                , "????????????"
//                , "????????????"
//                , EHostingRiskItemValueType.DOUBLE_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cm.f.leverage"
//                , "????????????"
//                , "????????????"
//                , EHostingRiskItemValueType.PERCENT_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
//


//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cn.f.riskRate"
//                , "??????????????????"
//                , "??????????????????"
//                , EHostingRiskItemValueType.PERCENT_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cn.f.goodsValue"
//                , "???????????????"
//                , "???????????????"
//                , EHostingRiskItemValueType.DOUBLE_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
//
//        addSupportedItems(EHostingRiskLevel.OPERATION_ACCOUNT_COMMODITY
//                , "op.cn.f.leverage"
//                , "???????????????"
//                , "???????????????"
//                , EHostingRiskItemValueType.PERCENT_VALUE
//                , EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK);
    }

    private void addSupportedInfo(
            EHostingRiskLevel riskLevel
            , String itemId
            , String cnName
            , String cnDescription
            , EHostingRiskItemValueType itemValueType
            , EHostingRiskLadderType riskLadderType
            , EHostingRiskItemValueLevel itemValueLevel
            , Class<? extends RiskItemCalculator> processorClass) {

        HostingRiskSupportedItem supportedItem = new HostingRiskSupportedItem();
        supportedItem.setRiskLevel(riskLevel)
                     .setItemId(itemId)
                     .setItemCnName(cnName)
                     .setItemDescription(cnDescription)
                     .setItemValueType(itemValueType)
                     .setRiskLadderType(riskLadderType)
                     .setItemValueLevel(itemValueLevel)
                     .setOrderNum(mLastOrderNum);
        mLastOrderNum += 1;
        HostingRiskSupportedInfo supportedInfo = new HostingRiskSupportedInfo(
                supportedItem
                , processorClass);
        mSupportedInfos.put(supportedItem.getItemId(), supportedInfo);
    }

}
