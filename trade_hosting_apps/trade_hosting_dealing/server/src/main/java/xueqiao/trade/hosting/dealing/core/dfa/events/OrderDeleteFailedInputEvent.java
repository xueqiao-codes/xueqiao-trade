package xueqiao.trade.hosting.dealing.core.dfa.events;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;
import xueqiao.trade.hosting.events.UpsideOrderDeleteFailedEvent;

public class OrderDeleteFailedInputEvent extends DFAEvent {
    
    private UpsideOrderDeleteFailedEvent failedEvent;
    
    public OrderDeleteFailedInputEvent(UpsideOrderDeleteFailedEvent failedEvent) {
        this.failedEvent = failedEvent;
    }
    
    @Override
    public String getType() {
        return getClass().getSimpleName();
    }
    
    class OrderDeleteFailedInputAction extends InputAction {

        public OrderDeleteFailedInputAction(HostingExecOrder inputOrder) {
            super(inputOrder);
        }

        @Override
        protected void run() {
            transferCode = TransferCode.SLED_ORDERDELETE_FAILED_CALLBACK;
            
            AppLog.i("OrderDeleteFailed " + failedEvent);
            
            outputOrder = new HostingExecOrder();
            outputOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
            outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(failedEvent.getMappingErrorCode());
            outputOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(failedEvent.getUpsideErrorCode());
            outputOrder.getRevokeInfo().setLastRevokeUpsideRejectReason(failedEvent.getUpsideErrorMsg());
        }
    }

    @Override
    public InputAction getInputAction(HostingExecOrder inputOrder) {
        return new OrderDeleteFailedInputAction(inputOrder);
    }

}
