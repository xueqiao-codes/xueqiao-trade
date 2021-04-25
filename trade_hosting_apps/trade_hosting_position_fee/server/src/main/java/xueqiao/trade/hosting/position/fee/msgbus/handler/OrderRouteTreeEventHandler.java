package xueqiao.trade.hosting.position.fee.msgbus.handler;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import xueqiao.trade.hosting.events.OrderRouteTreeEvent;
import xueqiao.trade.hosting.events.OrderRouteTreeEventType;
import xueqiao.trade.hosting.position.fee.core.calculator.OneSubAccountContractPositionRateCalculator;

public class OrderRouteTreeEventHandler implements IMessageHandler<OrderRouteTreeEvent> {
    @Override
    public void onInit() {

    }

    @Override
    public IMessageConsumer.ConsumeResult onHandle(OrderRouteTreeEvent event) {
        if (event.getType() == null) {
            if (AppLog.warnEnabled()) {
                AppLog.w("received null type trade account event! drop it!");
            }
            return IMessageConsumer.ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (event.getType() == OrderRouteTreeEventType.ORDER_ROUTE_TREE_CHANGED) {
            if (AppLog.infoEnabled()) {
                AppLog.i("##### Message Event ##### OrderRouteTreeEventHandler received " + event);
            }
            OneSubAccountContractPositionRateCalculator calculator = new OneSubAccountContractPositionRateCalculator(event.getSubAccountId());
            calculator.addTask(null);
        }
        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }
}
