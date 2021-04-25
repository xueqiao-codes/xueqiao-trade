package xueqiao.trade.hosting.position.fee.msgbus.handler;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import xueqiao.trade.hosting.events.UpsidePositionRateDetailsUpdatedEvent;
import xueqiao.trade.hosting.position.fee.core.calculator.OneTradeAccountPositionRateCalculator;

public class UpsidePositionRateDetailsUpdateEventHandler implements IMessageHandler<UpsidePositionRateDetailsUpdatedEvent> {
    @Override
    public void onInit() {

    }

    @Override
    public IMessageConsumer.ConsumeResult onHandle(UpsidePositionRateDetailsUpdatedEvent event) {
        if (AppLog.infoEnabled()) {
            AppLog.i("##### Message Event ##### UpsidePositionRateDetailsUpdateEventHandler received " + event);
        }
        if (event.getTradeAccountId() < 1) {
            return IMessageConsumer.ConsumeResult.CONSUME_FAILED_DROP;
        }
        OneTradeAccountPositionRateCalculator calculator = new OneTradeAccountPositionRateCalculator();
        calculator.addTask(event.getTradeAccountId());
        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }
}
