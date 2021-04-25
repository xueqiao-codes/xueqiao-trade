package xueqiao.trade.hosting.arbitrage.core.actor.contractparked;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class XQOrderContractParkedCreatedActor implements IXQOrderActor {

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        // 检查交易时间
        if (!executor.refreshTradeTimeSpanList()) {
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
            return ;
        }
        
        TradeTimeSpan nearestSpan 
            = executor.getTradeTimeSpanList().getNearestSpan(executor.getOrder().getCreateTimestampMs());
        if (nearestSpan == null) {
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return ;
        }
        if (nearestSpan.getStartTimestampMs() <= executor.getOrder().getCreateTimestampMs()) {
            // 目前处于开市状态
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_MARKET_OPENDED_FOR_PARKED.getValue());
            return ;
        }
        
        executor.start();
    }

}
