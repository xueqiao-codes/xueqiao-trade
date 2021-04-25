package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.risk.manager.core.RiskSupportedInfoHolder.HostingRiskSupportedInfo;
import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity.CommodityCalculator;
import xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract.ContractCalculator;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskItemDataChecker;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskRuleResult;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueLevel;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskItemDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItemValue;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.trade_hosting_risk_managerConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RiskItemManager implements IRawDataSampleListener, RiskRuleManager.IListener {

    private RiskController mController;
    private RiskRuleManager mRuleManager;
    private RawDataPool mRawDataPool;

    private Map<String, RiskItemCalculator> mCurrentCalculators = new HashMap<>();
    private Map<String, RiskRuleResult> mRuleResults = new HashMap<>();

    public RiskItemManager(RiskController controller
            , RiskRuleManager ruleManager
            , RawDataPool rawDataPool) {
        this.mController = controller;
        this.mRuleManager = ruleManager;
        this.mRawDataPool = rawDataPool;

        this.mRuleManager.addListener(this);
        this.mRawDataPool.addListener(this);
    }

    public void destroy() {
        this.mRuleManager.rmListener(this);
        this.mRawDataPool.rmListener(this);
    }

    private static class RiskItemCalculatorInstance {
        private HostingRiskSupportedInfo mSupportedInfo;
        private TreeMap<String, String> mParams;

        public RiskItemCalculatorInstance(HostingRiskSupportedInfo supportedInfo
                , TreeMap<String, String> params) {
            Preconditions.checkNotNull(supportedInfo);

            this.mSupportedInfo = supportedInfo;
            if (params == null) {
                this.mParams = new TreeMap<>();
            } else {
                this.mParams = new TreeMap<>(params);
            }

        }

        public HostingRiskSupportedInfo getSupportedInfo() {
            return mSupportedInfo;
        }

        public TreeMap<String, String> getParams() {
            return mParams;
        }

        public String getKey() {
            return RiskItemKeyBuilder.buildKey(mSupportedInfo.getItem().getItemId(), mParams);
        }

    }

    public HostingRiskFrameDataInfo getRiskFrameDataInfo() {
        HostingRiskFrameDataInfo resultFrame = new HostingRiskFrameDataInfo();
        resultFrame.setGlobalDataInfos(new ArrayList<>());
        resultFrame.setCommodityDataInfos(new HashMap<>());
        resultFrame.setContractDataInfos(new HashMap<>());

        for (RiskItemCalculator calculator : mCurrentCalculators.values()) {
            HostingRiskSupportedItem supportedItem = calculator.getItem();

            HostingRiskItemDataInfo itemDataInfo = new HostingRiskItemDataInfo();
            itemDataInfo.setItemId(supportedItem.getItemId());

            if (calculator.getValue().isSetValue()) {
                if (calculator.getValue().getValueType() == RiskItemValue.EValueType.VALUE_LONG) {
                    itemDataInfo.setItemValue(new HostingRiskRuleItemValue().setLongValue(calculator.getValue().getLong()));
                } else {
                    itemDataInfo.setItemValue(new HostingRiskRuleItemValue().setDoubeValue(calculator.getValue().getDouble()));
                }
            }

            RiskRuleResult ruleResult = mRuleResults.get(calculator.getKey());
            if (ruleResult != null) {
                if (ruleResult.isAlarmTriggered()) {
                    itemDataInfo.setAlarmTriggered(true);
                }
                if (ruleResult.isForbiddenOpenPositionTriggered()) {
                    itemDataInfo.setForbiddenOpenPositionTriggered(true);
                }
            }

            if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_CONTRACT_VALUE) {
                itemDataInfo.setSledCommodityId(((ContractCalculator)calculator).getCommodityId());
                itemDataInfo.setSledContractId(((ContractCalculator)calculator).getContractId());

                Map<Long, List<HostingRiskItemDataInfo>> commodityDataMap
                        = resultFrame.getContractDataInfos().get(itemDataInfo.getSledCommodityId());
                if (commodityDataMap == null) {
                    commodityDataMap = new HashMap<>();
                    resultFrame.getContractDataInfos().put(itemDataInfo.getSledCommodityId(), commodityDataMap);
                }

                List<HostingRiskItemDataInfo> contractDataInfos = commodityDataMap.get(itemDataInfo.getSledContractId());
                if (contractDataInfos == null) {
                    contractDataInfos  = new ArrayList<>();
                    commodityDataMap.put(itemDataInfo.getSledContractId(), contractDataInfos);
                }
                contractDataInfos.add(itemDataInfo);

            } else if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE) {
                itemDataInfo.setSledCommodityId(((CommodityCalculator)calculator).getCommodityId());

                List<HostingRiskItemDataInfo> commodityDataInfos
                        =  resultFrame.getCommodityDataInfos().get(itemDataInfo.getSledCommodityId());
                if (commodityDataInfos == null) {
                    commodityDataInfos = new ArrayList<>();
                    resultFrame.getCommodityDataInfos().put(itemDataInfo.getSledCommodityId(), commodityDataInfos);
                }
                commodityDataInfos.add(itemDataInfo);

            } else if (supportedItem.getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_GLOBAL_VALUE) {
                resultFrame.getGlobalDataInfos().add(itemDataInfo);
            } else {
                Preconditions.checkState(false);
            }

        }

        return resultFrame;
    }

    /**
     *  整理计算器
     */
    private void arrangeCalculators(RiskRuleJoint rrJoint) {
        Map<String, HostingRiskSupportedInfo> supportedInfoMap = RiskSupportedInfoHolder.Global().getSupportedInfos();
        Map<String, RiskItemCalculatorInstance> enabledCalculatorInstance = new HashMap<>();

        for (String globalItemId : rrJoint.getGlobalOpenedItemIds()) {
            HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(globalItemId);
            if (supportedInfo == null) {
                AppLog.e("failed to found SupportedInfo for itemId=" + globalItemId);
                continue ;
            }
            RiskItemCalculatorInstance cInstance = new RiskItemCalculatorInstance(supportedInfo, null);
            enabledCalculatorInstance.put(cInstance.getKey(), cInstance);
        }

        for (Long tradedCommodityId : rrJoint.getTradedCommodityIds()) {

            Map<Long, HostingSledContractPosition> commodityContractPositions
                    = mRawDataPool.getContractPositions().get(tradedCommodityId);
            if (commodityContractPositions == null || commodityContractPositions.isEmpty()) {
                continue;
            }

            for (String commodityItemId : rrJoint.getCommodityOpenedItemIds()) {
                TreeMap<String, String> params = new TreeMap<>();
                params.put(trade_hosting_risk_managerConstants.PARAM_RISKITEM_COMMODITY_ID, String.valueOf(tradedCommodityId));

                HostingRiskSupportedInfo supportedInfo = supportedInfoMap.get(commodityItemId);
                if (supportedInfo == null) {
                    AppLog.e("failed to found SupportedInfo for itemId=" + commodityItemId);
                    continue ;
                }

                if (supportedInfo.getItem().getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_COMMODITY_VALUE) {
                    // 说明是商品的规则
                    RiskItemCalculatorInstance cInstance = new RiskItemCalculatorInstance(supportedInfo, params);
                    enabledCalculatorInstance.put(cInstance.getKey(), cInstance);

                } else if (supportedInfo.getItem().getItemValueLevel() == EHostingRiskItemValueLevel.OPERATION_ACCOUNT_CONTRACT_VALUE) {

                    for (HostingSledContractPosition contractPosition : commodityContractPositions.values()) {
                        params.put(trade_hosting_risk_managerConstants.PARAM_RISKITEM_CONTRACT_ID
                                , String.valueOf(contractPosition.getSledContractId()));
                        RiskItemCalculatorInstance cInstance = new RiskItemCalculatorInstance(supportedInfo, params);
                        enabledCalculatorInstance.put(cInstance.getKey(), cInstance);
                    }

                }
            }
        }

        Iterator<Map.Entry<String, RiskItemCalculator>> it = mCurrentCalculators.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, RiskItemCalculator> entry = it.next();
            if (!enabledCalculatorInstance.containsKey(entry.getKey())) {
                it.remove();
            }
        }

        for (String itemKey : enabledCalculatorInstance.keySet()) {
            if (!mCurrentCalculators.containsKey(itemKey)) {
                mCurrentCalculators.put(itemKey, createCalculator(enabledCalculatorInstance.get(itemKey)));
            }
        }

    }

    private RiskItemCalculator createCalculator(RiskItemCalculatorInstance instance) {
        try {
            return instance.getSupportedInfo().getProcessorClass().newInstance()
                    .init(instance.getSupportedInfo().getItem()
                            , instance.getParams());
        } catch (Throwable e) {
            AppLog.f(e.getMessage(), e);
        }
        return null;
    }


    @Override
    public void onSampleProcess(RawDataPool dataPool) {
        Preconditions.checkState(dataPool == mRawDataPool);
        long startTimestampMs = System.currentTimeMillis();

        RiskRuleJoint rrJoint = mRuleManager.getJoint();

        arrangeCalculators(rrJoint);

        long arrangeTimestampMs = System.currentTimeMillis();

        RiskItemDataChecker dataChecker = new RiskItemDataChecker();
        dataChecker.begin();
        for (RiskItemCalculator calculator : mCurrentCalculators.values()) {
            calculator.calculate(mRawDataPool);
            dataChecker.check(calculator, rrJoint);
        }

        mController.handleRiskRuleResultCheck(mRuleResults, dataChecker.getResults());
        mRuleResults = dataChecker.getResults();

        if (AppLog.debugEnabled()) {
            AppLog.d("onSampleProcess subAccountId=" + mRuleManager.getContext().getSubAccountId()
                    + ", calculators'size=" + mCurrentCalculators.size()
                    + ", arrangeTimeEscaped=" + (arrangeTimestampMs - arrangeTimestampMs) + "ms"
                    + ", totalTimeEscaped=" + (System.currentTimeMillis() - startTimestampMs) + "ms");
        }
    }

    @Override
    public void onSampleFinished(RawDataPool dataPool) {
    }

    @Override
    public void onRulesChanged(RiskRuleManager ruleManager) {
        Preconditions.checkState(ruleManager == mRuleManager);
    }
}
