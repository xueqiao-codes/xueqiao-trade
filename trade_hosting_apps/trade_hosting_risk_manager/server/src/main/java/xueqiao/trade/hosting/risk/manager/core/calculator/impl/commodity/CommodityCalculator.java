package xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity;

import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.thriftapi.trade_hosting_risk_managerConstants;

public abstract class CommodityCalculator extends RiskItemCalculator {
    protected long mSledCommodityId;

    public long getCommodityId() {
        return mSledCommodityId;
    }

    @Override
    protected void onInit() {
        mSledCommodityId = Long.parseLong(
                getParamForSure(trade_hosting_risk_managerConstants.PARAM_RISKITEM_COMMODITY_ID));
    }
}
