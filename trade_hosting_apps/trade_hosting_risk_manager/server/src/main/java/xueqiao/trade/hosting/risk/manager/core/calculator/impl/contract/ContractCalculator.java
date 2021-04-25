package xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract;

import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.thriftapi.trade_hosting_risk_managerConstants;

public abstract class ContractCalculator extends RiskItemCalculator {
    protected long mSledCommodityId;
    protected long mSledContractId;

    public long getCommodityId() {
        return mSledCommodityId;
    }

    public long getContractId() {
        return mSledContractId;
    }

    protected void onInit() {
        mSledCommodityId = Long.parseLong(
                getParamForSure(trade_hosting_risk_managerConstants.PARAM_RISKITEM_COMMODITY_ID));
        mSledContractId = Long.parseLong(
                getParamForSure(trade_hosting_risk_managerConstants.PARAM_RISKITEM_CONTRACT_ID));
    }
}
