package xueqiao.trade.hosting.asset.event.handler;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.calculator.account.sub.AssetCalculateConfigCalculator;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.events.*;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.util.*;

public class AssetSyncEventHandler implements IMessageConsumer {
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.RESERVE_QUEUE;
    }

    @Override
    public void onInit() throws Exception {
        new Init().initFromDealing();
    }

    /* 成交事件触发的持仓计算和成交明细存DB, 计算锁仓信息 */
    @consume(ExecTradeListChangedEvent.class)
    ConsumeResult onHandleExecTradeListChangedEvent(ExecTradeListChangedEvent event) throws Exception {
        new TradeListHandler().saveHostingExecTrade(event.getNewTradeList(), TradeDetailSource.XQ_TRADE, event.getExecOrder().getSource());
        return ConsumeResult.CONSUME_OK;
    }


    @consume(XQOrderGuardEvent.class)
    ConsumeResult onHandlerXQOrderGuardEventType(XQOrderGuardEvent guardEvent) throws ErrorInfo {
        if (!XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD.equals(guardEvent.getType())) {
            return ConsumeResult.CONSUME_OK;
        }

        long execOrderId = Long.valueOf(guardEvent.getOrderId());

        IHostingDealingApi dealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        List<HostingExecTrade> trades = dealingApi.getOrderTrades(execOrderId);
        HostingExecOrder execOrder = dealingApi.getOrder(execOrderId);
        new TradeListHandler().saveHostingExecTrade(trades, TradeDetailSource.XQ_TRADE, execOrder.getSource());
        return ConsumeResult.CONSUME_OK;
    }

}
