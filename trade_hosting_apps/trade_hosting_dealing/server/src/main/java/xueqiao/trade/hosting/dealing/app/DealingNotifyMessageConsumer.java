package xueqiao.trade.hosting.dealing.app;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.events.HostingEventType;
import xueqiao.trade.hosting.events.OrderRouteTreeEvent;
import xueqiao.trade.hosting.events.OrderRouteTreeEventType;
import xueqiao.trade.hosting.order.route.OrderRouteTreeCache;

/**
 *  主要是处理通知订单需要的内部事件发生，例如加载配置到内存
 */
public class DealingNotifyMessageConsumer implements IMessageConsumer {
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
        OrderRouteTreeCache.Global().syncFromDB();
    }

    @consume(HostingEvent.class)
    ConsumeResult onHandleHostingEvent(HostingEvent event) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleHostingEvent received " + event);
        }
        if (event.getType() == HostingEventType.HOSTING_INITED) {
            OrderRouteTreeCache.Global().syncFromDB();
        }

        return ConsumeResult.CONSUME_OK;
    }

    @consume(OrderRouteTreeEvent.class)
    ConsumeResult onHandleOrderRouteTreeEvent(OrderRouteTreeEvent event) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleOrderRouteTreeEvent received " + event);
        }

        if (event.getType() == OrderRouteTreeEventType.ORDER_ROUTE_TREE_ALL_CLEARD) {
            OrderRouteTreeCache.Global().syncFromDB();
            return ConsumeResult.CONSUME_OK;
        }

        if (event.getSubAccountId() <= 0) {
            AppLog.w("onHandleOrderRouteTreeEvent received " + event + ", but subAccountId <= 0");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        OrderRouteTreeCache routeTreeCache = OrderRouteTreeCache.Global();

        if (routeTreeCache.contains(event.getSubAccountId())) {
            OrderRouteTreeCache.Global().update(event.getSubAccountId()
                    , OrderRouteTreeCache.Global().getRouteTreeNullIfLostFromDB(event.getSubAccountId()));
        } else {
            OrderRouteTreeCache.Global().add(event.getSubAccountId()
                    , OrderRouteTreeCache.Global().getRouteTreeNullIfLostFromDB(event.getSubAccountId()));
        }

        return ConsumeResult.CONSUME_OK;
    }
}
