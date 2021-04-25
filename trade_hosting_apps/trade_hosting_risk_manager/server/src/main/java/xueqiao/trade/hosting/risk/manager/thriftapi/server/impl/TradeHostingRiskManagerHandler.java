package xueqiao.trade.hosting.risk.manager.thriftapi.server.impl;


import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.risk.manager.core.RiskManager;
import xueqiao.trade.hosting.risk.manager.core.RiskManagerFactory;
import xueqiao.trade.hosting.risk.manager.core.RiskSupportedInfoHolder;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueType;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskLadderType;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.server.TradeHostingRiskManagerAdaptor;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TradeHostingRiskManagerHandler extends TradeHostingRiskManagerAdaptor {
    private IHostingContractApi mContractApi;

    @Override
    public int InitApp(Properties props) {
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        return 0;
    }

    @Override
    protected List<HostingRiskSupportedItem> getAllSupportedItems(TServiceCntl oCntl)
            throws ErrorInfo, TException {
        return null;
    }

    private void checkRiskRuleItem(String itemId
            , HostingRiskRuleItem riskRuleItem) throws ErrorInfo {
        RiskSupportedInfoHolder.HostingRiskSupportedInfo supportedInfo
                = RiskSupportedInfoHolder.Global().getSupportedInfos().get(itemId);
        if (supportedInfo == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_NOT_SUPPORTED.getValue()
                    , "ItemId=" + itemId + " is not supported");
        }

        if (supportedInfo.getItem().getItemValueType() == EHostingRiskItemValueType.LONG_VALUE) {
            if (riskRuleItem.isSetAlarmValue()) {
                ParameterChecker.check(riskRuleItem.getAlarmValue().isSetLongValue()
                        , "ruleItem's alarmValue for itemId=" + itemId + " should be longValue");
            }
            if (riskRuleItem.isSetForbiddenOpenPositionValue()) {
                ParameterChecker.check(riskRuleItem.getForbiddenOpenPositionValue().isSetLongValue()
                        , "ruleItem's alarmValue for itemId=" + itemId + " should be longValue");
            }

            if (riskRuleItem.isSetAlarmValue() && riskRuleItem.isSetForbiddenOpenPositionValue()) {
                if (supportedInfo.getItem().getRiskLadderType() == EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK) {
                    ParameterChecker.check(
                            riskRuleItem.getForbiddenOpenPositionValue().getLongValue() >=
                                        riskRuleItem.getAlarmValue().getLongValue()
                            , "ruleItem's forbiddenOpenPositionValue should >= alarmValue for itemId=" + itemId);
                } else {
                    ParameterChecker.check(
                            riskRuleItem.getForbiddenOpenPositionValue().getLongValue() <=
                                    riskRuleItem.getAlarmValue().getLongValue()
                            , "ruleItem's forbiddenOpenPositionValue should <= alarmValue for itemId=" + itemId);
                }
            }

        } else {
            if (riskRuleItem.isSetAlarmValue()) {
                ParameterChecker.check(riskRuleItem.getAlarmValue().isSetDoubeValue()
                        && Double.isFinite(riskRuleItem.getAlarmValue().getDoubeValue())
                        , "ruleItem's alarmValue for itemId=" + itemId + " should be finite doubleValue");
            }
            if (riskRuleItem.isSetForbiddenOpenPositionValue()) {
                ParameterChecker.check(riskRuleItem.getForbiddenOpenPositionValue().isSetDoubeValue()
                        && Double.isFinite(riskRuleItem.getForbiddenOpenPositionValue().getDoubeValue())
                        , "ruleItem's alarmValue for itemId=" + itemId + " should be finite doubleValue");
            }

            if (riskRuleItem.isSetAlarmValue() && riskRuleItem.isSetForbiddenOpenPositionValue()) {
                if (supportedInfo.getItem().getRiskLadderType() == EHostingRiskLadderType.HIGH_VALUE_HIGH_RISK) {
                    ParameterChecker.check(
                            Double.compare(riskRuleItem.getForbiddenOpenPositionValue().getDoubeValue()
                                    , riskRuleItem.getAlarmValue().getDoubeValue()) >= 0
                            , "ruleItem's forbiddenOpenPositionValue should >= alarmValue for itemId=" + itemId);
                } else {
                    ParameterChecker.check(
                            Double.compare(riskRuleItem.getForbiddenOpenPositionValue().getDoubeValue()
                                    , riskRuleItem.getAlarmValue().getDoubeValue()) <= 0
                            , "ruleItem's forbiddenOpenPositionValue should >= alarmValue for itemId=" + itemId);
                }
            }
        }
    }

    private void checkCommodityIdsExisted(Set<Long> commodityIds) throws ErrorInfo {
        List<Integer> reqCommoditIds = new ArrayList<>();
        for (long commodityId : commodityIds) {
            reqCommoditIds.add((int)commodityId);
        }

        try {
            SledCommodityPage sledCommodityPage = mContractApi.getContractOnlineStub().reqSledCommodity(
                    new ReqSledCommodityOption().setSledCommodityIdList(reqCommoditIds)
                    , 0
                    , commodityIds.size() + 1);
            Map<Long, SledCommodity> commodityMap = new HashMap<>();
            if (sledCommodityPage.getPageSize() > 0) {
                for (SledCommodity commodity : sledCommodityPage.getPage()) {
                    commodityMap.put((long)commodity.getSledCommodityId(), commodity);
                }
            }

            for (Long commodityId : commodityIds) {
                if (!commodityMap.containsKey(commodityId)) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMMODITY_NOT_EXISTED.getValue()
                        , "Commodity for sledCommodityId=" + commodityId + " is not existed");
                }
            }

        } catch (TException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                , "server inner error!");
        }
    }

    private RiskManager getRM(long subAccountId) throws ErrorInfo {
        return RiskManagerFactory.Global().getRiskManagerForSure(subAccountId);
    }

    @Override
    protected int getRiskRuleJointVersion(TServiceCntl oCntl
            , long subAccountId) throws ErrorInfo, TException {
        return getRM(subAccountId).getRuleManager().getJoint().getVersion();
    }

    @Override
    protected HostingRiskRuleJoint getRiskRuleJoint(TServiceCntl oCntl
            , long subAccountId) throws ErrorInfo, TException {
        return getRM(subAccountId).getRuleManager().getJoint();
    }

    @Override
    protected HostingRiskRuleJoint batchSetSupportedItems(TServiceCntl oCntl
            , long subAccountId
            , int version
            , Set<String> openedItemIds
            , Set<String> closedItemIds) throws ErrorInfo, TException {
        return getRM(subAccountId).getRuleManager().batchSetSupportedItems(version, openedItemIds, closedItemIds);
    }

    @Override
    protected HostingRiskRuleJoint batchSetTradedCommodityItems(TServiceCntl oCntl
            , long subAccountId
            , int version
            , Set<Long> enabledCommodityIds
            , Set<Long> disabledCommodityIds) throws ErrorInfo, TException {
        if (enabledCommodityIds != null) {
            checkCommodityIdsExisted(enabledCommodityIds);
        }
        if (disabledCommodityIds != null) {
            checkCommodityIdsExisted(disabledCommodityIds);
        }

        return getRM(subAccountId).getRuleManager().batchSetTradedCommodityItems(version
                , enabledCommodityIds, disabledCommodityIds);
    }

    @Override
    protected HostingRiskRuleJoint batchSetGlobalRules(TServiceCntl oCntl
            , long subAccountId
            , int version
            , Map<String, HostingRiskRuleItem> ruleItems) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(ruleItems, "ruleItems should not be null");
        for (Map.Entry<String, HostingRiskRuleItem> ruleItemEntry : ruleItems.entrySet()) {
            checkRiskRuleItem(ruleItemEntry.getKey(), ruleItemEntry.getValue());
        }

        return getRM(subAccountId).getRuleManager().batchSetGlobalRules(version, ruleItems);
    }

    @Override
    protected HostingRiskRuleJoint batchSetCommodityRules(TServiceCntl oCntl
            , long subAccountId
            , int version
            , Map<Long, Map<String, HostingRiskRuleItem>> rules) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(rules, "rules should not be null");

        checkCommodityIdsExisted(rules.keySet());
        for (Map<String, HostingRiskRuleItem> riskRuleItemMap : rules.values()) {
            for (Map.Entry<String, HostingRiskRuleItem> ruleItemEntry : riskRuleItemMap.entrySet()) {
                checkRiskRuleItem(ruleItemEntry.getKey(), ruleItemEntry.getValue());
            }
        }

        return getRM(subAccountId).getRuleManager().batchSetCommodityRules(version, rules);
    }

    @Override
    protected HostingRiskRuleJoint setRiskEnabled(TServiceCntl oCntl
            , long subAccountId
            , int version
            , boolean riskEnabled) throws ErrorInfo, TException {
        return getRM(subAccountId).getRuleManager().setRiskEnabled(version, riskEnabled);
    }

    @Override
    protected HostingRiskFrameDataInfo getRiskFrameDataInfo(TServiceCntl oCntl, long subAccountId)
            throws ErrorInfo, TException {
        final RiskManager riskManager = getRM(subAccountId);
        return new SyncResult<HostingRiskFrameDataInfo>(getRM(subAccountId).getContext().getWorkThread()) {

            @Override
            protected HostingRiskFrameDataInfo onCall() throws Exception {
                return riskManager.getRiskFrameDataInfo();
            }
        }.get();
    }

    @Override
    public void destroy() {
    }
}
