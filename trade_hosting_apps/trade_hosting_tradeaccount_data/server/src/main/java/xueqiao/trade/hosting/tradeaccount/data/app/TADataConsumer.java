package xueqiao.trade.hosting.tradeaccount.data.app;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.events.TradeAccountEvent;
import xueqiao.trade.hosting.events.TradeAccountEventType;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.tradeaccount.data.core.TADataSynchonizerManager;

import java.util.List;
import java.util.Set;

public class TADataConsumer implements IMessageConsumer {

    private IHostingTradeAccountApi mTradeAccountApi;

    public TADataConsumer() {
        mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
    }

    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
        syncTradeAccounts();
    }

    @consume(TradeAccountEvent.class)
    ConsumeResult onHandleTradeAccountEvent(TradeAccountEvent event) throws Exception {
        AppLog.i("onHandleTradeAccountEvent event=" + event);
        if (!event.isSetType() || event.getTradeAccountId() <= 0) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getType() == TradeAccountEventType.TRADE_ACCOUNT_ADDED
                || event.getType() == TradeAccountEventType.TRADE_ACCOUNT_STATE_CHANGED
                || event.getType() == TradeAccountEventType.TRADE_ACCOUNT_DELETED) {
            HostingTradeAccount tradeAccount = mTradeAccountApi.getTradeAccount(event.getTradeAccountId());
            if (tradeAccount == null) {
                TADataSynchonizerManager.Global().rmSynchronizer(event.getTradeAccountId());
                return ConsumeResult.CONSUME_OK;
            }

            if (tradeAccount.getAccountState() == TradeAccountState.ACCOUNT_NORMAL) {
                TADataSynchonizerManager.Global().addSynchronizer(event.getTradeAccountId()
                        , tradeAccount.getBrokerTechPlatform()
                        , tradeAccount.getCreateTimestamp());
            } else {
                TADataSynchonizerManager.Global().rmSynchronizer(event.getTradeAccountId());
            }
        }

        return ConsumeResult.CONSUME_OK;
    }

    private void syncTradeAccounts() throws ErrorInfo {
        List<HostingTradeAccount> tradeAccounts = mTradeAccountApi.getAllTradeAccounts();
        Set<Long> synchonizerTradeAccountIds = TADataSynchonizerManager.Global().getTradeAccountIds();
        for (HostingTradeAccount tradeAccount : tradeAccounts) {
            if (tradeAccount.getAccountState() == TradeAccountState.ACCOUNT_NORMAL) {
                TADataSynchonizerManager.Global().addSynchronizer(tradeAccount.getTradeAccountId()
                        , tradeAccount.getBrokerTechPlatform()
                        , tradeAccount.getCreateTimestamp());
            } else {
                TADataSynchonizerManager.Global().rmSynchronizer(tradeAccount.getTradeAccountId());
            }

            synchonizerTradeAccountIds.remove(tradeAccount.getTradeAccountId());
        }

        for (Long unexpectedTradeAccountId : synchonizerTradeAccountIds) {
            TADataSynchonizerManager.Global().rmSynchronizer(unexpectedTradeAccountId);
        }
    }
}
