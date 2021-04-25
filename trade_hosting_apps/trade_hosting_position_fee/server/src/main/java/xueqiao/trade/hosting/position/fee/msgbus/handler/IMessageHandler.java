package xueqiao.trade.hosting.position.fee.msgbus.handler;

import org.soldier.framework.message_bus.api.IMessageConsumer;

public interface IMessageHandler<E> {
    void onInit();

    IMessageConsumer.ConsumeResult onHandle(E event);
}
