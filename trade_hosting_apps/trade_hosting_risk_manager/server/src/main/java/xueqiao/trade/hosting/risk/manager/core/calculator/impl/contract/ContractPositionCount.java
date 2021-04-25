package xueqiao.trade.hosting.risk.manager.core.calculator.impl.contract;

import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;

import java.util.Map;

public class ContractPositionCount extends ContractCalculator {

    @Override
    protected void onCalculate(RawDataPool rawDataPool, RiskItemValue newValue) {
        Map<Long, HostingSledContractPosition> commodityContractPositions
                = rawDataPool.getContractPositions().get(mSledCommodityId);
        if (commodityContractPositions == null) {
            return ;
        }

        HostingSledContractPosition contractPosition = commodityContractPositions.get(mSledContractId);
        if (contractPosition == null || !contractPosition.isSetPositionVolume()) {
            return ;
        }

        newValue.setValue(Math.abs(contractPosition.getPositionVolume().getNetPosition()));
    }
}
