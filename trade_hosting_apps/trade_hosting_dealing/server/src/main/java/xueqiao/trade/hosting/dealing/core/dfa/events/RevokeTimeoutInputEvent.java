package xueqiao.trade.hosting.dealing.core.dfa.events;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;

public class RevokeTimeoutInputEvent extends DFAEvent {
	
	private static class RevokeTimeoutInputAction extends InputAction {
		public RevokeTimeoutInputAction(HostingExecOrder inputOrder) {
			super(inputOrder);
		}

		@Override
		protected void run() {
			transferCode = TransferCode.SLED_REVOKE_TIMEOUT;
			
			outputOrder = new HostingExecOrder();
            outputOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
            outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(
            		TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REVOKE_TIMEOUT.getValue());
            outputOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(0);
            outputOrder.getRevokeInfo().setLastRevokeUpsideRejectReason("");
		}
	}
	
	@Override
	public String getType() {
		return getClass().getSimpleName();
	}

	@Override
	public InputAction getInputAction(HostingExecOrder inputOrder) {
		return new RevokeTimeoutInputAction(inputOrder);
	}
}
