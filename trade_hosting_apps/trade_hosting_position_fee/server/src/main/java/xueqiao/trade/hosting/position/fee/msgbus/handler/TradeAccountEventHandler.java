package xueqiao.trade.hosting.position.fee.msgbus.handler;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.events.TradeAccountEvent;
import xueqiao.trade.hosting.events.TradeAccountEventType;
import xueqiao.trade.hosting.position.fee.core.api.HostingTradeAccountApi;
import xueqiao.trade.hosting.position.fee.core.calculator.OneTradeAccountPositionRateCalculator;

public class TradeAccountEventHandler implements IMessageHandler<TradeAccountEvent> {

    @Override
    public void onInit() {

    }

    /**
     * 只处理账号添加事件
     * 因为用过的资金账号，可能还存在关联的持仓
     */
    @Override
    public IMessageConsumer.ConsumeResult onHandle(TradeAccountEvent event) {


        if (event.getType() == null) {
            if (AppLog.warnEnabled()) {
                AppLog.w("received null type trade account event! drop it!");
            }
            return IMessageConsumer.ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getType() == TradeAccountEventType.TRADE_ACCOUNT_ADDED) {

            if (AppLog.infoEnabled()) {
                AppLog.i("##### Message Event ##### TradeAccountEventHandler received " + event);
            }

            OneTradeAccountPositionRateCalculator calculator = new OneTradeAccountPositionRateCalculator();
            calculator.addTask(event.getTradeAccountId());
        } else if (event.getType() == TradeAccountEventType.TRADE_ACCOUNT_STATE_CHANGED) {

            if (AppLog.infoEnabled()) {
                AppLog.i("##### Message Event ##### TradeAccountEventHandler received " + event);
            }

            HostingTradeAccount tradeAccount = null;
            try {
                tradeAccount = HostingTradeAccountApi.queryTradeAccount(event.getTradeAccountId());
            } catch (ErrorInfo errorInfo) {
                AppLog.e("onHandle ---- queryTradeAccount fail, tradeAccountId : " + event.getTradeAccountId(), errorInfo);
            }
            if (tradeAccount != null && tradeAccount.getAccountState() == TradeAccountState.ACCOUNT_NORMAL) {
                OneTradeAccountPositionRateCalculator calculator = new OneTradeAccountPositionRateCalculator();
                calculator.addTask(event.getTradeAccountId());
            }
        }

        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }
}
