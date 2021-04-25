package xueqiao.trade.hosting.position.statis.core.quotation;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatComposePositionDynamicInfoCalculator;

/**
 * 监听组合行情
 * 分别监听组合中各个合约的行情
 */
public class StatComposeQuotationListener extends StatQuoCombListener {

    protected StatComposeQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        super(subAccountId, targetKey, targetType);
    }

    @Override
    public void onHandleQuotationItem(StatQuoCombCache quoCombCache) throws Exception {
        new StatComposePositionDynamicInfoCalculator(subAccountId, targetKey, targetType).onCalculate(quoCombCache);
    }
}
