package xueqiao.trade.hosting.dealing.core.cleaner;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.dealing.core.sync.RevokeTimeoutTask;
import xueqiao.trade.hosting.dealing.core.sync.RevokeTimeoutTaskManager;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderStateTask;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderTaskManager;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderCleanIndexTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderInDealingTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.events.ExecOrderExpiredEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.storage.apis.structs.OrderCleanIndexEntry;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

import java.util.AbstractMap;

public class CleanHandler {
    private HostingExecOrder originOrder;
    private HostingExecOrder newOrder;

    public CleanHandler(HostingExecOrder originOrder) {
        this.originOrder = originOrder;
    }

    public HostingExecOrder getNewOrder() {
        return newOrder;
    }

    public void process() throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(DealingDBV2.Global()) {
            private boolean orderFinished = true;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                if (!isFinalState()) {
                    orderFinished = false;
                }
            }

            private boolean isFinalState()  {
                HostingExecOrderState orderState = originOrder.getStateInfo().getCurrentState();
                if (orderState.getValue() == HostingExecOrderStateValue.ORDER_EXPIRED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_VERIFY_FAILED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_FINISHED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_DELETED
                        || orderState.getValue() == HostingExecOrderStateValue.ORDER_CONDITION_TRIGGEDED) {
                    return true;
                }
                return false;
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                newOrder = new HostingExecOrder(originOrder);

                // 如果订单在清理前未进入终态，则变更为过期状态
                if (!orderFinished) {
                    HostingExecOrder operateOrder = new HostingExecOrder();
                    operateOrder.setExecOrderId(originOrder.getExecOrderId());

                    HostingExecOrderState nextState = new HostingExecOrderState();
                    nextState.setValue(HostingExecOrderStateValue.ORDER_EXPIRED);
                    nextState.setTimestampMs(System.currentTimeMillis());

                    operateOrder.setStateInfo(new HostingExecOrderStateInfo());
                    operateOrder.getStateInfo().setHistoryStates(newOrder.getStateInfo().getHistoryStates());
                    operateOrder.getStateInfo().getHistoryStates().add(newOrder.getStateInfo().getCurrentState());
                    operateOrder.getStateInfo().setCurrentState(nextState);
                    operateOrder.getStateInfo().setStatusMsg(
                            HostingDealingHelper.generateStatusMsg(nextState.getValue(), 0, ""));

                    newOrder.setStateInfo(operateOrder.getStateInfo());

                    operateOrder.setVersion(newOrder.getVersion() + 1);
                    newOrder.setVersion(operateOrder.getVersion());

                    new HostingExecOrderTableV2(getConnection()).updateOrder(operateOrder);
                }

                // 无任何成交的订单加入记录清理计划，相当于废单
                HostingExecOrderTradeSummary tradeSummary = newOrder.getTradeSummary();
                if (tradeSummary == null
                            || (tradeSummary.getTradeListTotalVolume() <= 0 && tradeSummary.getUpsideTradeTotalVolume() <= 0)) {
                    OrderCleanIndexEntry indexEntry = new OrderCleanIndexEntry();
                    indexEntry.setCleanTimestampMs(System.currentTimeMillis() + ExecOrderDbCleaner.DB_RECORD_RESERVE_TIMEMS);
                    indexEntry.setExecOrderId(originOrder.getExecOrderId());
                    new HostingExecOrderCleanIndexTableV2(getConnection()).addIndexEntry(indexEntry);
                }

                new HostingExecOrderInDealingTableV2(getConnection()).deleteIndexEntry(originOrder.getExecOrderId());
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if (orderFinished) {
                    return null;
                }

                ExecOrderGuardEvent guardEvent = new ExecOrderGuardEvent();
                guardEvent.setGuardExecOrderId(originOrder.getExecOrderId());
                guardEvent.setGuardType(ExecOrderGuardType.GUARD_ORDER_EXPIRED);
                return new AbstractMap.SimpleEntry<>(guardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @SuppressWarnings("rawtypes")
            protected TBase prepareNotifyMessage()  {
                if (orderFinished) {
                    return null;
                }

                ExecOrderExpiredEvent expiredEvent = new ExecOrderExpiredEvent();
                HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
                notifyOrder.unsetNotifyStateHandleInfos();
                notifyOrder.getStateInfo().unsetHistoryStates();
                expiredEvent.setExpiredOrder(notifyOrder);
                return expiredEvent;
            }

            @Override
            protected void onCommitFinished() throws Exception {
                super.onCommitFinished();

                SyncOrderTaskManager.Global().cancelTask(new SyncOrderStateTask(originOrder.getExecOrderId()));
                RevokeTimeoutTaskManager.Global().cancelTask(new RevokeTimeoutTask(originOrder.getExecOrderId()));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

        }.execute();
    }

}
