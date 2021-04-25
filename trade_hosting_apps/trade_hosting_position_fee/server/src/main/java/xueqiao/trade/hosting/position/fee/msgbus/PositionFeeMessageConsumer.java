package xueqiao.trade.hosting.position.fee.msgbus;

import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import xueqiao.trade.hosting.events.OrderRouteTreeEvent;
import xueqiao.trade.hosting.events.TradeAccountEvent;
import xueqiao.trade.hosting.events.UpsidePositionRateDetailsUpdatedEvent;
import xueqiao.trade.hosting.position.fee.core.calculator.AllTradeAccountPositionRateCalculator;
import xueqiao.trade.hosting.position.fee.msgbus.handler.OrderRouteTreeEventHandler;
import xueqiao.trade.hosting.position.fee.msgbus.handler.TradeAccountEventHandler;
import xueqiao.trade.hosting.position.fee.msgbus.handler.UpsidePositionRateDetailsUpdateEventHandler;

public class PositionFeeMessageConsumer implements IMessageConsumer {

    private TradeAccountEventHandler tradeAccountEventHandler;
    private UpsidePositionRateDetailsUpdateEventHandler upsidePositionRateDetailsUpdateEventHandler;
    private OrderRouteTreeEventHandler orderRouteTreeEventHandler;

    public PositionFeeMessageConsumer() {
        tradeAccountEventHandler = new TradeAccountEventHandler();
        upsidePositionRateDetailsUpdateEventHandler = new UpsidePositionRateDetailsUpdateEventHandler();
        orderRouteTreeEventHandler = new OrderRouteTreeEventHandler();
    }

    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
//        tradeAccountEventHandler.onInit();
//        upsidePositionRateDetailsUpdateEventHandler.onInit();
//        orderRouteTreeEventHandler.onInit();
        AllTradeAccountPositionRateCalculator calculator = new AllTradeAccountPositionRateCalculator();
        calculator.addTask(null);
    }

    @consume(TradeAccountEvent.class)
    public ConsumeResult onHandleTradeAccountEvent(TradeAccountEvent event) throws Exception {
        return tradeAccountEventHandler.onHandle(event);
    }

    @consume(UpsidePositionRateDetailsUpdatedEvent.class)
    public ConsumeResult onHandleUpsidePositionRateDetailsUpdatedEvent(UpsidePositionRateDetailsUpdatedEvent event) throws Exception {
        return upsidePositionRateDetailsUpdateEventHandler.onHandle(event);
    }

    @consume(OrderRouteTreeEvent.class)
    public ConsumeResult onHandleOrderRouteTreeEvent(OrderRouteTreeEvent event) throws Exception {
        return orderRouteTreeEventHandler.onHandle(event);
    }
}
