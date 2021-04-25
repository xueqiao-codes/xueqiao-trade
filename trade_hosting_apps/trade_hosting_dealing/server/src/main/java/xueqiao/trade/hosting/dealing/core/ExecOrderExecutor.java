package xueqiao.trade.hosting.dealing.core;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealInfo;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.HostingExecTradeLegInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.cleaner.CleanHandler;
import xueqiao.trade.hosting.dealing.core.cleaner.ExecOrderInDealingCleaner;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.StateMachine;
import xueqiao.trade.hosting.dealing.core.dfa.events.NotifyForwardStateInputEvent;
import xueqiao.trade.hosting.dealing.core.dfa.events.NotifySyncStateInputEvent;
import xueqiao.trade.hosting.dealing.core.dfa.events.OrderDeleteFailedInputEvent;
import xueqiao.trade.hosting.dealing.core.dfa.events.OrderInsertFailedInputEvent;
import xueqiao.trade.hosting.dealing.core.dfa.events.OrderStateInputEvent;
import xueqiao.trade.hosting.dealing.core.dfa.events.RevokeTimeoutInputEvent;
import xueqiao.trade.hosting.dealing.core.revoke.RevokeHandler;
import xueqiao.trade.hosting.dealing.core.sync.ISyncPolicy;
import xueqiao.trade.hosting.dealing.core.sync.OrderStateSyncPolicyMap;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderTaskManager;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderTradeListTask;
import xueqiao.trade.hosting.dealing.core.sync.SyncTradeListChecker;
import xueqiao.trade.hosting.events.ExecOrderRevokeTimeoutEvent;
import xueqiao.trade.hosting.events.UpsideNotifyForwardStateEvent;
import xueqiao.trade.hosting.events.UpsideNotifyForwardTradeEvent;
import xueqiao.trade.hosting.events.UpsideNotifySyncStateEvent;
import xueqiao.trade.hosting.events.UpsideNotifySyncTradeEvent;
import xueqiao.trade.hosting.events.UpsideOrderDeleteFailedEvent;
import xueqiao.trade.hosting.events.UpsideOrderInsertFailedEvent;
import xueqiao.trade.hosting.framework.thread.TaskThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecOrderExecutor {
    private TaskThread mWorkThread;
    private final long mExecOrderId;

    private HostingExecOrder mExecOrder;

    private boolean mIsDetoryed = false;

    public ExecOrderExecutor(HostingExecOrder execOrder) {
        Preconditions.checkNotNull(execOrder);

        mExecOrderId = execOrder.getExecOrderId();
        mExecOrder = execOrder;
        if (!mExecOrder.isSetAccountSummary()) {
            mExecOrder.setAccountSummary(new HostingExecOrderTradeAccountSummary());
        }
        if (!mExecOrder.isSetTradeSummary()) {
            mExecOrder.setTradeSummary(new HostingExecOrderTradeSummary());
        }
        if (!mExecOrder.isSetDealInfo()) {
            mExecOrder.setDealInfo(new HostingExecOrderDealInfo());
        }
        if (!mExecOrder.isSetRevokeInfo()) {
            mExecOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
        }
        if (!mExecOrder.isSetOrderInputExt()) {
            mExecOrder.setOrderInputExt(new HostingExecOrderInputExt());
        }
        if (!mExecOrder.getStateInfo().isSetHistoryStates()) {
            mExecOrder.getStateInfo().setHistoryStates(new ArrayList<>());
        }

        OrderRefData.Global().put(execOrder.getAccountSummary()
                , execOrder.getUpsideOrderRef()
                , execOrder.getExecOrderId());

        if (execOrder.isSetDealInfo()) {
            OrderDealIDData.Global().put(execOrder.getAccountSummary()
                    , execOrder.getDealInfo().getDealId()
                    , execOrder.getExecOrderId());
        }
    }

    public long getExecOrderId() {
        return mExecOrderId;
    }

    public void setWorkThread(TaskThread workThread) {
        this.mWorkThread = workThread;
    }

    public TaskThread getWorkThread() {
        return mWorkThread;
    }

    private void checkInWorkThread() throws ErrorInfo {
        if (!getWorkThread().isInCurrentThread()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "working thread not correct");
        }
    }

    public HostingExecOrder getExecOrder() {
        return mExecOrder;
    }

    public void destroy() throws ErrorInfo {
        checkInWorkThread();
        if (AppLog.infoEnabled()) {
            AppLog.i("destroy executor for execOrderId=" + getExecOrderId());
        }

        OrderRefData.Global().clear(mExecOrder.getAccountSummary()
                , mExecOrder.getUpsideOrderRef(), getExecOrderId());

        if (mExecOrder.isSetDealInfo()) {
            OrderDealIDData.Global().clear(mExecOrder.getAccountSummary()
                    , mExecOrder.getDealInfo().getDealId(), getExecOrderId());
        }

        SyncTradeListChecker.Global().rmCheck(getExecOrderId());

        mIsDetoryed = true;
    }

    public Void clean() throws ErrorInfo {
        checkInWorkThread();

        if (mIsDetoryed) {
            return null;
        }

        CleanHandler cleanHandler = new CleanHandler(mExecOrder);
        cleanHandler.process();
        if (cleanHandler.getNewOrder() != null) {
            mExecOrder = cleanHandler.getNewOrder();
        }

        return null;
    }

    private boolean isTradeListComplete() {
        if (mExecOrder.isSetTradeSummary()
                && mExecOrder.getTradeSummary().getTradeListTotalVolume()
                != mExecOrder.getTradeSummary().getUpsideTradeTotalVolume()) {
            return false;
        }
        return true;
    }

    public void initialize() throws ErrorInfo {
        checkInWorkThread();

        if (AppLog.infoEnabled()) {
            AppLog.i("initialize for execOrderId=" + getExecOrderId());
        }

        if (mExecOrder.getTtlTimestampMs() > 0) {
            ExecOrderInDealingCleaner.Global().addCleanIndex(mExecOrder.getTtlTimestampMs(), getExecOrderId());
        }
        if (!isTradeListComplete()) {
            SyncTradeListChecker.Global().addCheck(getExecOrderId());
        }

        ISyncPolicy policy = OrderStateSyncPolicyMap.Global().getSyncPolicy(
                mExecOrder.getStateInfo().getCurrentState().getValue());
        if (policy != null) {
            policy.getTaskManager().addTask(policy.getSyncTask(getExecOrderId())
                    , policy.getDelayMs(), policy.getPeriodMs());
        }

        scheduleState();
    }

    public Void revoke() throws ErrorInfo {
        checkInWorkThread();

        if (mIsDetoryed) {
            return null;
        }

        RevokeHandler rh = new RevokeHandler(mExecOrder);
        rh.revoke();
        if (rh.getNewOrder() != null) {
            mExecOrder = rh.getNewOrder();
        }

        getWorkThread().postTask(new ExecOrderExecutorRunnable(this) {
            @Override
            public String getName() {
                return "revoke";
            }

            @Override
            protected void onRun() throws Exception {
                scheduleState();
            }
        });

        return null;
    }


    // 状态机驱动流转
    private void scheduleState() throws ErrorInfo {
        HostingExecOrderState orderState = mExecOrder.getStateInfo().getCurrentState();
        Preconditions.checkState(orderState.getValue() != HostingExecOrderStateValue.ORDER_WAITING_VERIFY);

        if (AppLog.infoEnabled()) {
            AppLog.i("scheduleState execOrderId=" + getExecOrderId() + ", orderState=" + orderState);
        }

        OrderStateInputEvent inputEvent = new OrderStateInputEvent();
        if (!inputEvent.needHandleState(mExecOrder.getStateInfo().getCurrentState().getValue())) {
            return;
        }

        transferState(inputEvent);
    }

    // 处理订单审核
//    private void processOrderVerify() throws ErrorInfo {
//        VerifyHandler vh = new VerifyHandler(mExecOrder);
//        vh.process();
//        if (vh.getNewOrder() != null) {
//            mExecOrder = vh.getNewOrder();
//        }
//
//        getWorkThread().postTask(new ExecOrderExecutorRunnable(this) {
//            @Override
//            public String getName() {
//                return "processOrderVerify";
//            }
//
//            @Override
//            protected void onRun() throws Exception {
//                scheduleState();
//            }
//        });
//    }

    private void transferState(DFAEvent inputEvent) throws ErrorInfo {
        StateMachine st = new StateMachine(mExecOrder);
        st.transfer(inputEvent);
        if (st.getNewOrder() != null) {
            mExecOrder = st.getNewOrder();
        } else {
            return ;
        }

        if (!isTradeListComplete()) {
            SyncTradeListChecker.Global().addCheck(getExecOrderId());
        } else {
            SyncTradeListChecker.Global().rmCheck(getExecOrderId());
        }

        getWorkThread().postTask(new ExecOrderExecutorRunnable(this) {
            @Override
            public String getName() {
                return "scheduleState";
            }

            @Override
            protected void onRun() throws Exception {
                scheduleState();
            }
        });
    }

    private void processTradeList(HostingExecOrderLegContractSummary legContractSummary
        , List<HostingExecTradeLegInfo> tradeLegInfos) throws ErrorInfo {
        TradeListHandler handler = new TradeListHandler(mExecOrder);
        handler.process(legContractSummary, tradeLegInfos);
        if (handler.getNewOrder() != null) {
            mExecOrder = handler.getNewOrder();
        }

        if (isTradeListComplete()) {
            SyncTradeListChecker.Global().rmCheck(getExecOrderId());
        }
    }

    public IMessageConsumer.ConsumeResult handleUpsideNotifyForwardStateEvent(
            UpsideNotifyForwardStateEvent forwardStateEvent) throws Exception {
        checkInWorkThread();
        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        transferState(new NotifyForwardStateInputEvent(forwardStateEvent));
        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public IMessageConsumer.ConsumeResult handleUpsideNotifyForwardTradeEvent(
            UpsideNotifyForwardTradeEvent forwardTradeEvent) throws Exception {
        checkInWorkThread();
        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        SyncOrderTaskManager.Global().cancelTask(new SyncOrderTradeListTask(forwardTradeEvent.getExecOrderId()));
        processTradeList(
                forwardTradeEvent.getForwardTradeLegContractSummary()
                , Arrays.asList(forwardTradeEvent.getForwardTradeLegInfo()));

        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public IMessageConsumer.ConsumeResult handleUpsideNotifySyncStateEvent(
            UpsideNotifySyncStateEvent syncStateEvent) throws Exception {
        checkInWorkThread();

        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        transferState(new NotifySyncStateInputEvent(syncStateEvent));

        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public IMessageConsumer.ConsumeResult handleUpsideNotifySyncTradeEvent(
            UpsideNotifySyncTradeEvent syncTradeEvent) throws Exception {
        checkInWorkThread();

        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        SyncOrderTaskManager.Global().cancelTask(new SyncOrderTradeListTask(syncTradeEvent.getExecOrderId()));
        processTradeList(syncTradeEvent.getSyncTradeLegContractSummary()
                , syncTradeEvent.getSyncTradeLegInfos());

        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public IMessageConsumer.ConsumeResult handleUpsideOrderDeleteFailedEvent(
            UpsideOrderDeleteFailedEvent deleteFailedEvent) throws Exception {
        checkInWorkThread();
        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        transferState(new OrderDeleteFailedInputEvent(deleteFailedEvent));

        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public IMessageConsumer.ConsumeResult handleUpsideOrderInsertFailedEvent(
            UpsideOrderInsertFailedEvent insertFailedEvent) throws Exception {
        checkInWorkThread();
        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        transferState(new OrderInsertFailedInputEvent(insertFailedEvent));

        return IMessageConsumer.ConsumeResult.CONSUME_FAILED_DROP;
    }

    public IMessageConsumer.ConsumeResult handleExecOrderRevokeTimeoutEvent(
            ExecOrderRevokeTimeoutEvent revokeTimeoutEvent) throws Exception {
        checkInWorkThread();

        if (mIsDetoryed) {
            return IMessageConsumer.ConsumeResult.CONSUME_OK;
        }

        transferState(new RevokeTimeoutInputEvent());

        return IMessageConsumer.ConsumeResult.CONSUME_FAILED_DROP;
    }
}
