package xueqiao.trade.hosting.dealing.core.dfa.events;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;
import xueqiao.trade.hosting.events.UpsideOrderInsertFailedEvent;

public class OrderInsertFailedInputEvent extends DFAEvent {

    private UpsideOrderInsertFailedEvent failedEvent;
    
    public OrderInsertFailedInputEvent(UpsideOrderInsertFailedEvent failedEvent) {
        this.failedEvent = failedEvent;
    }
    
    @Override
    public String getType() {
        return OrderInsertFailedInputEvent.class.getSimpleName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append(this.getType()).append(":").append(failedEvent);
        return builder.toString();
    }
    
    class OrderInsertFailedAction extends InputAction {
        public OrderInsertFailedAction(HostingExecOrder inputOrder) {
            super(inputOrder);
        }

        @Override
        protected void run() {
            transferCode = TransferCode.SLED_ORDERINSERT_FAILED_CALLBACK;
            outputOrder = new HostingExecOrder();
            outputOrder.setStateInfo(new HostingExecOrderStateInfo());
            outputOrder.getStateInfo().setUpsideErrorCode(failedEvent.getUpsideErrorCode());
            outputOrder.getStateInfo().setUpsideUsefulMsg(failedEvent.getUpsideErrorMsg());
            outputOrder.getStateInfo().setFailedErrorCode(failedEvent.getMappingErrorCode());
        }
    }

    @Override
    public InputAction getInputAction(HostingExecOrder inputOrder) {
        return new OrderInsertFailedAction(inputOrder);
    }

}
