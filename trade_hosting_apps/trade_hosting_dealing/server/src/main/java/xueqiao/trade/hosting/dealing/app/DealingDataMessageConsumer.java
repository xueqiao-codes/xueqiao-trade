package xueqiao.trade.hosting.dealing.app;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealInfo;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutor;
import xueqiao.trade.hosting.dealing.core.ExecOrderManager;
import xueqiao.trade.hosting.dealing.core.OrderDealIDData;
import xueqiao.trade.hosting.dealing.core.OrderRefData;
import xueqiao.trade.hosting.dealing.core.cleaner.ExecOrderInDealingCleaner;
import xueqiao.trade.hosting.dealing.storage.DealingStorageApiV2;
import xueqiao.trade.hosting.dealing.storage.data.ExecOrderIndexEntryV2;
import xueqiao.trade.hosting.events.ExecOrderRevokeTimeoutEvent;
import xueqiao.trade.hosting.events.UpsideNotifyForwardStateEvent;
import xueqiao.trade.hosting.events.UpsideNotifyForwardTradeEvent;
import xueqiao.trade.hosting.events.UpsideNotifySyncStateEvent;
import xueqiao.trade.hosting.events.UpsideNotifySyncTradeEvent;
import xueqiao.trade.hosting.events.UpsideOrderDeleteFailedEvent;
import xueqiao.trade.hosting.events.UpsideOrderInsertFailedEvent;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  订单所需要的数据事件处理
 */
public class DealingDataMessageConsumer implements IMessageConsumer {

    public DealingDataMessageConsumer() throws ErrorInfo {
        initializeAllExecOrders();
    }

    private void initializeAllExecOrders() throws ErrorInfo {
        int pageIndex = 0;
        final int pageSize = 500;

        while(true) {
            PageOption pageOption = new PageOption();
            pageOption.setPageIndex(pageIndex);
            pageOption.setPageSize(pageSize);

            PageResult<HostingExecOrder> orderPageResult = DealingStorageApiV2.getInDealingOrders(pageOption);
            if (orderPageResult.getPageList() == null || orderPageResult.getPageList().isEmpty()) {
                break;
            }

            for (HostingExecOrder execOrder : orderPageResult.getPageList()) {
                ExecOrderManager.Global().addExecutor(new ExecOrderExecutor(execOrder));
            }

            pageIndex++;
        }
    }

    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.RESERVE_QUEUE;
    }

    @Override
    public void onInit() throws Exception {
        // 上手事件无法回溯，订单同步机制弥补
    }

    @consume(UpsideNotifyForwardStateEvent.class)
    ConsumeResult onHandleUpsideNotifyForwardStateEvent(UpsideNotifyForwardStateEvent forwardStateEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleUpsideNotifyForwardStateEvent recived " + forwardStateEvent);
        }

        if (forwardStateEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleUpsideNotifyForwardStateEvent forwardStateEvent's execOrdeId = "
                    + forwardStateEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!forwardStateEvent.isSetForwardStateInfo()) {
            AppLog.e("onHandleUpsideNotifyForwardStateEvent received empty forwardStateInfo");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(forwardStateEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {

            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideNotifyForwardStateEvent(forwardStateEvent);
            }

        }.get();
    }

    @consume(UpsideNotifyForwardTradeEvent.class)
    ConsumeResult onHandleUpsideNotifyForwardTradeEvent(UpsideNotifyForwardTradeEvent forwardTradeEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleUpsideNotifyForwardTradeEvent received " + forwardTradeEvent);
        }

        if (forwardTradeEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleUpsideNotifyForwardTradeEvent called, but forwardTradeEvent's execOrderId="
                    + forwardTradeEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!forwardTradeEvent.isSetForwardTradeLegInfo()) {
            AppLog.e("onHandleUpsideNotifyForwardTradeEvent called, but forwardTradeEvents's tradeLegInfo not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!forwardTradeEvent.isSetForwardTradeLegContractSummary()) {
            AppLog.e("onHandleUpsideNotifyForwardTradeEvent called, but forwardTradeEvent's tradeLegContractSummary not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(forwardTradeEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {
            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideNotifyForwardTradeEvent(forwardTradeEvent);
            }
        }.get();
    }

    @consume(UpsideNotifySyncStateEvent.class)
    ConsumeResult onHandleUpsideNotifySyncStateEvent(UpsideNotifySyncStateEvent syncStateEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleUpsideNotifySyncStateEvent received " + syncStateEvent);
        }

        if (syncStateEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleUpsideNotifySyncStateEvent called, but syncStateEvent' execOrderId="
                    +  syncStateEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(syncStateEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {
            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideNotifySyncStateEvent(syncStateEvent);
            }
        }.get();
    }

    @consume(UpsideNotifySyncTradeEvent.class)
    ConsumeResult onHandleUpsideNotifySyncTradeEvent(UpsideNotifySyncTradeEvent syncTradeEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleUpsideNotifySyncTradeEvent received " + syncTradeEvent);
        }

        if (syncTradeEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleUpsideNotifySyncTradeEvent called, but syncTradeEvent's execOrderId="
                    + syncTradeEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!syncTradeEvent.isSetSyncTradeLegInfos()) {
            AppLog.e("onHandleUpsideNotifySyncTradeEvent called, but syncTradeEvent's tradeLegInfos not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!syncTradeEvent.isSetSyncTradeLegContractSummary()) {
            AppLog.e("onHandleUpsideNotifySyncTradeEvent called, but syncTradeEvents's tradeLegContractSummary not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(syncTradeEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {

            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideNotifySyncTradeEvent(syncTradeEvent);
            }
        }.get();
    }

    @consume(UpsideOrderDeleteFailedEvent.class)
    ConsumeResult onHandleUpsideOrderDeleteFailedEvent(UpsideOrderDeleteFailedEvent deleteFailedEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleUpsideOrderDeleteFailedEvent received " + deleteFailedEvent);
        }

        if (deleteFailedEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleUpsideOrderDeleteFailedEvent called, but deleteFailedEvent's exeOrderId="
                    + deleteFailedEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(deleteFailedEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {

            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideOrderDeleteFailedEvent(deleteFailedEvent);
            }
        }.get();
    }

    @consume(UpsideOrderInsertFailedEvent.class)
    ConsumeResult oHandleUpsideOrderInsertFailedEvent(UpsideOrderInsertFailedEvent insertFailedEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("oHandleUpsideOrderInsertFailedEvent received " + insertFailedEvent);
        }

        if (insertFailedEvent.getExecOrderId() <= 0) {
            AppLog.e("oHandleUpsideOrderInsertFailedEvent called, but insertFailedEvent's execOrderId="
                    + insertFailedEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(insertFailedEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {

            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleUpsideOrderInsertFailedEvent(insertFailedEvent);
            }
        }.get();
    }

    @consume(ExecOrderRevokeTimeoutEvent.class)
    ConsumeResult onHandleExecOrderRevokeTimeoutEvent(ExecOrderRevokeTimeoutEvent revokeTimeoutEvent) throws Exception {
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleExecOrderRevokeTimeoutEvent received " + revokeTimeoutEvent);
        }
        if (revokeTimeoutEvent.getExecOrderId() <= 0) {
            AppLog.e("onHandleExecOrderRevokeTimeoutEvent called, but revokeTimeoutEvent's execOrderId="
                    + revokeTimeoutEvent.getExecOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(revokeTimeoutEvent.getExecOrderId());
        if (executor == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        return new SyncResult<ConsumeResult>(executor.getWorkThread()) {

            @Override
            protected ConsumeResult onCall() throws Exception {
                return executor.handleExecOrderRevokeTimeoutEvent(revokeTimeoutEvent);
            }
        }.get();
    }

}
