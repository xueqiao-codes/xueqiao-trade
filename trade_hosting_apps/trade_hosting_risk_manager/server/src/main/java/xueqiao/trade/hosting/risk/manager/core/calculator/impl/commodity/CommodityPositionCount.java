package xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity;

import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;

public class CommodityPositionCount extends CommodityCalculator {

    @Override
    protected void onCalculate(RawDataPool rawDataPool, RiskItemValue newValue) {
        Long positionCount = rawDataPool.getCommodityPositionCounts().get(mSledCommodityId);
        if (positionCount != null) {
            newValue.setValue(Math.abs(positionCount));
        }
    }

}
