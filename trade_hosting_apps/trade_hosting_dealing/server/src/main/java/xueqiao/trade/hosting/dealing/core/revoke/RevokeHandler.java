package xueqiao.trade.hosting.dealing.core.revoke;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.events.ExecOrderRunningEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

import java.util.AbstractMap;
import java.util.ArrayList;

public class RevokeHandler {
    private HostingExecOrder originOrder;
    private HostingExecOrder newOrder;

    public RevokeHandler(HostingExecOrder originOrder) {
        this.originOrder = originOrder;
    }

    public HostingExecOrder getNewOrder() {
        return this.newOrder;
    }

    public void revoke() throws ErrorInfo {

        new DBTransactionHelperWithMsg<Void>(DealingDBV2.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingExecOrderState currentState = originOrder.getStateInfo().getCurrentState();
                RevokeAction action = RevokeActionMap.get(currentState.getValue());
                if (action == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REVOKE_ACTION_UNKOWN.getValue()
                            , "No action found for revoke!");
                }

                if (action.isReject()) {
                    throw new ErrorInfo(action.getFailedErrorCode(), HostingDealingHelper.getErrorMsg(action.getFailedErrorCode()));
                }

                newOrder = new HostingExecOrder(originOrder);

                HostingExecOrder operateOrder = new HostingExecOrder();
                operateOrder.setExecOrderId(originOrder.getExecOrderId());

                HostingExecOrderState transferState = new HostingExecOrderState();
                transferState.setValue(action.getNextState());
                transferState.setTimestampMs(System.currentTimeMillis());

                if (newOrder.getStateInfo().getHistoryStates() == null) {
                    newOrder.getStateInfo().setHistoryStates(new ArrayList<>());
                }
                newOrder.getStateInfo().getHistoryStates().add(newOrder.getStateInfo().getCurrentState());
                newOrder.getStateInfo().setCurrentState(transferState);
                if (action.getFailedErrorCode() != 0) {
                    newOrder.getStateInfo().setFailedErrorCode(action.getFailedErrorCode());
                }
                newOrder.getStateInfo().setStatusMsg(
                        HostingDealingHelper.generateStatusMsg(transferState.getValue()
                                , action.getFailedErrorCode(), ""));

                operateOrder.setStateInfo(newOrder.getStateInfo());
                operateOrder.setVersion(newOrder.getVersion() + 1);

                HostingExecOrderRevokeInfo revokeInfo = new HostingExecOrderRevokeInfo();
                revokeInfo.setLastRevokeTimestampMs(0);
                revokeInfo.setLastRevokeFailedErrorCode(0);
                revokeInfo.setLastRevokeUpsideErrorCode(0);
                revokeInfo.setLastRevokeUpsideRejectReason("");
                operateOrder.setRevokeInfo(revokeInfo);

                newOrder.setRevokeInfo(revokeInfo);
                newOrder.setVersion(operateOrder.getVersion());

                new HostingExecOrderTableV2(getConnection()).updateOrder(operateOrder);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                ExecOrderGuardEvent event = new ExecOrderGuardEvent();
                event.setGuardExecOrderId(originOrder.getExecOrderId());
                event.setGuardType(ExecOrderGuardType.GUARD_ORDER_RUNNING);
                return new AbstractMap.SimpleEntry<>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                ExecOrderRunningEvent event = new ExecOrderRunningEvent();
                HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
                notifyOrder.getStateInfo().unsetHistoryStates();
                notifyOrder.unsetNotifyStateHandleInfos();
                event.setRunningOrder(notifyOrder);
                return event;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

        }.execute();

    }

}
