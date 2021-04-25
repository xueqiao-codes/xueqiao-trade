package xueqiao.trade.hosting.position.statis.core.quotation;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatContractPositionDynamicInfoCalculator;

/**
 * 监听合约行情
 */
public class StatContractQuotationListener extends StatQuotationListener {

    protected StatContractQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    @Override
    public void onHandleQuotationItem(StatQuotationCache quotationCache) throws Exception {
        new StatContractPositionDynamicInfoCalculator(subAccountId, targetKey, targetType).onCalculate(quotationCache);
    }
}
