package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.risk.helper.RiskStubFactory;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class HostingRiskHandler extends HandlerBase{
    public HostingRiskHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public List<HostingRiskSupportedItem> getAllSupportedItems() throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<List<HostingRiskSupportedItem>>() {
            @Override
            public List<HostingRiskSupportedItem> call() throws Exception {
                return RiskStubFactory.getManagerStub().getAllSupportedItems();
            }
        });
    }

    public int getRiskRuleJointVersion(long subAccountId) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return RiskStubFactory.getManagerStub().getRiskRuleJointVersion(subAccountId);
            }
        });
    }

    public HostingRiskRuleJoint getRiskRuleJoint(long subAccountId) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().getRiskRuleJoint(subAccountId);
            }
        });
    }

    public HostingRiskRuleJoint batchSetSupportedItems(long subAccountId
            , int version
            , Set<String> openedItemIds
            , Set<String> closedItemIds ) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().batchSetSupportedItems(subAccountId
                        , version, openedItemIds, closedItemIds);
            }
        });
    }

    public HostingRiskRuleJoint batchSetTradedCommodityItems(long subAccountId
            , int version
            , Set<Long> enabledCommodityIds
            , Set<Long> disabledCommodityIds) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().batchSetTradedCommodityItems(subAccountId
                        , version
                        , enabledCommodityIds
                        , disabledCommodityIds);
            }
        });
    }

    public HostingRiskRuleJoint batchSetGlobalRules(long subAccountId
            , int version
            , Map<String, HostingRiskRuleItem> ruleItems) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().batchSetGlobalRules(subAccountId, version, ruleItems);
            }
        });
    }

    public HostingRiskRuleJoint batchSetCommodityRules(long subAccountId
            , int version
            , Map<Long, Map<String, HostingRiskRuleItem>> rules) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().batchSetCommodityRules(subAccountId, version, rules);
            }
        });

    }

    public HostingRiskRuleJoint setRiskEnabled(long subAccountId
            , int version
            , boolean riskEnabled) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskRuleJoint>() {
            @Override
            public HostingRiskRuleJoint call() throws Exception {
                return RiskStubFactory.getManagerStub().setRiskEnabled(subAccountId, version, riskEnabled);
            }
        });

    }

    public HostingRiskFrameDataInfo getRiskFrameDataInfo(long subAccountId) throws ErrorInfo {
        hasPermission(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingRiskFrameDataInfo>() {
            @Override
            public HostingRiskFrameDataInfo call() throws Exception {
                return RiskStubFactory.getManagerStub().getRiskFrameDataInfo(subAccountId);
            }
        });
    }

}
