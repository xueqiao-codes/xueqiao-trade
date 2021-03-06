package xueqiao.trade.hosting.risk.dealing.thriftapi.server.impl;

import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.risk.dealing.thriftapi.EHostingExecOrderRiskActionType;
import xueqiao.trade.hosting.risk.dealing.thriftapi.HostingExecOrderRiskAction;
import xueqiao.trade.hosting.risk.dealing.thriftapi.server.TradeHostingRiskDealingAdaptor;
import xueqiao.trade.hosting.risk.manager.core.RiskManager;
import xueqiao.trade.hosting.risk.manager.core.RiskManagerFactory;
import xueqiao.trade.hosting.risk.manager.core.policy.RiskPolicyItem;
import xueqiao.trade.hosting.risk.manager.core.policy.RiskPolicyJoint;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskRuleJoint;

import java.util.Properties;

public class TradeHostingRiskDealingHandler extends TradeHostingRiskDealingAdaptor {

    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected HostingExecOrderRiskAction riskCheck(TServiceCntl oCntl
            , long subAccountId
            , HostingExecOrderContractSummary orderContractSummary
            , HostingExecOrderDetail orderDetail) throws ErrorInfo, TException {
        HostingExecOrderRiskAction riskAction = new HostingExecOrderRiskAction();
        riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_PASSED);

        RiskManager riskManager = RiskManagerFactory.Global().getRiskManager(subAccountId);
        if (riskManager == null) {
            return riskAction;
        }

        RiskRuleJoint rrJoint = riskManager.getRuleManager().getJoint();
        if (!rrJoint.isRiskEnabled()) {
            return riskAction;
        }

        if (!rrJoint.getTradedCommodityIds().contains(orderContractSummary.getSledCommodityId())) {
            riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
            riskAction.setActionMessage("??????????????????");
            return riskAction;
        }

        RiskPolicyJoint rpJoint = riskManager.getController().getRiskPolicyJoint();
        RiskPolicyItem rpItem = rpJoint.getContractPolicies().get(orderContractSummary.getSledContractId());
        if (rpItem != null && rpItem.isForbiddenOpenPosition()) {
            if (orderDetail.getTradeDirection() != rpItem.getClosePositionDirection()) {
                riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                riskAction.setActionMessage(rpItem.getForbiddenOpenPositionReason());
                return riskAction;
            } else {
                if (orderDetail.getQuantity() > Math.abs(rpItem.getNetPositionCount())) {
                    riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                    riskAction.setActionMessage(
                            rpItem.getForbiddenOpenPositionReason() + ",??????????????????????????????");
                }
            }
        }

        rpItem = rpJoint.getCommodityPolices().get(orderContractSummary.getSledCommodityId());
        if (rpItem != null && rpItem.isForbiddenOpenPosition()) {
            if (orderDetail.getTradeDirection() != rpItem.getClosePositionDirection()) {
                riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                riskAction.setActionMessage(rpItem.getForbiddenOpenPositionReason());
                return riskAction;
            } else {
                if (orderDetail.getQuantity() > Math.abs(rpItem.getNetPositionCount())) {
                    riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                    riskAction.setActionMessage(
                            rpItem.getForbiddenOpenPositionReason() + ",??????????????????????????????");
                }
            }
        }

        rpItem = rpJoint.getGlobalPolicy();
        if (rpItem != null && rpItem.isForbiddenOpenPosition()) {
            // ?????????????????????????????????????????????????????????
            Long positionCount = rpJoint.getCommodityPositionCounts().get(orderContractSummary.getSledCommodityId());
            if (positionCount != null) {
                HostingExecOrderTradeDirection closePositionDirection = null;
                if (positionCount > 0) {
                    closePositionDirection = HostingExecOrderTradeDirection.ORDER_SELL;
                } else if (positionCount < 0) {
                    closePositionDirection = HostingExecOrderTradeDirection.ORDER_BUY;
                }

                if (orderDetail.getTradeDirection() != closePositionDirection) {
                    riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                    riskAction.setActionMessage(rpItem.getForbiddenOpenPositionReason());
                    return riskAction;
                } else {
                    if (orderDetail.getQuantity() > Math.abs(positionCount)) {
                        riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                        riskAction.setActionMessage(
                                rpItem.getForbiddenOpenPositionReason() + ",??????????????????????????????");
                    }
                }

            } else {
                riskAction.setActionType(EHostingExecOrderRiskActionType.RISK_FORBIDDEN);
                riskAction.setActionMessage(rpItem.getForbiddenOpenPositionReason());
            }
        }


        return riskAction;
    }

    @Override
    public void destroy() {
    }
}
