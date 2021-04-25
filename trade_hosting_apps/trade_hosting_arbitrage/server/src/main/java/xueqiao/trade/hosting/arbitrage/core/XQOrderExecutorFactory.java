package xueqiao.trade.hosting.arbitrage.core;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderConditionExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractParkedExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

public class XQOrderExecutorFactory {
    
    public static XQOrderBaseExecutor createExecutor(HostingXQOrder order) throws ErrorInfo {
        Preconditions.checkNotNull(order);
        if (order.getOrderType() == HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT) {
            return new XQOrderContractLimitExecutor(order);
        } else if (order.getOrderType() == HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT) {
            return new XQOrderComposeLimitExecutor(order);
        } else if (order.getOrderType() == HostingXQOrderType.XQ_ORDER_CONDITION) {
            return new XQOrderConditionExecutor(order);
        } else if (order.getOrderType() == HostingXQOrderType.XQ_ORDER_CONTRACT_PARKED) {
            return new XQOrderContractParkedExecutor(order);
        }
        throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TYPE_NOT_SUPPORTED.getValue()
                , "order is not supported");
    }
    
}
