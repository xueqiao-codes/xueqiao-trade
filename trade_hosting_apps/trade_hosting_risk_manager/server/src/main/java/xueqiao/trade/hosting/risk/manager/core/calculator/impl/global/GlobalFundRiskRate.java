package xueqiao.trade.hosting.risk.manager.core.calculator.impl.global;

import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;

public class GlobalFundRiskRate extends GlobalCalculator {

    @Override
    protected void onCalculate(RawDataPool rawDataPool, RiskItemValue newValue) {
        HostingFund hostingFund = rawDataPool.getBaseCurrencyFund();
        if (hostingFund != null && hostingFund.isSetRiskRate()) {
            newValue.setValue(hostingFund.getRiskRate());
        }
    }
}
