package xueqiao.trade.hosting.risk.manager.core.calculator.impl.global;

import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.calculator.RiskItemCalculator;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;

public class GlobalFundDynamicBenefit extends RiskItemCalculator {

    @Override
    protected void onInit() {
    }

    @Override
    protected void onCalculate(RawDataPool rawDataPool, RiskItemValue newValue) {
        HostingFund hostingFund = rawDataPool.getBaseCurrencyFund();
        if (hostingFund != null && hostingFund.isSetDynamicBenefit()) {
            newValue.setValue(hostingFund.getDynamicBenefit());
        }
    }
}
