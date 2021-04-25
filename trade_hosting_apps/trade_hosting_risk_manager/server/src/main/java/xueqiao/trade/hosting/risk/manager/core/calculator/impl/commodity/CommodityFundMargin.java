package xueqiao.trade.hosting.risk.manager.core.calculator.impl.commodity;

import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;

import java.util.Map;

public class CommodityFundMargin extends CommodityCalculator {
    @Override
    protected void onCalculate(RawDataPool rawDataPool, RiskItemValue newValue) {
        Map<Long, HostingSledContractPosition> commodityContractPositions
                = rawDataPool.getContractPositions().get(mSledCommodityId);
        if (commodityContractPositions == null || commodityContractPositions.isEmpty()) {
            return ;
        }

        double longPositionMargin = 0d;
        double shortPositionMargin = 0d;

        for (HostingSledContractPosition contractPosition : commodityContractPositions.values()) {
            if (!contractPosition.isSetPositionVolume()) {
                continue;
            }

            if (!contractPosition.isSetPositionFund()) {
                continue;
            }

            if (contractPosition.getPositionVolume().getNetPosition() > 0) {
                longPositionMargin += contractPosition.getPositionFund().getUseMargin();
            } else if (contractPosition.getPositionVolume().getNetPosition() < 0) {
                shortPositionMargin += contractPosition.getPositionFund().getUseMargin();
            }
        }

        newValue.setValue(Math.max(longPositionMargin, shortPositionMargin));
    }
}
