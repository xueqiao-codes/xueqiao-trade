package xueqiao.trade.hosting.dealing.core.dfa.events;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingUpsideNotifyStateHandleInfo;
import xueqiao.trade.hosting.HostingUpsideNotifyStateSource;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;
import xueqiao.trade.hosting.events.UpsideNotifySyncStateEvent;

public class NotifySyncStateInputEvent extends DFAEvent {
	private final static int SYNC_EFFECTIVE_TIMEMS = 1000;
	
	private UpsideNotifySyncStateEvent syncStateEvent;
	
	public NotifySyncStateInputEvent(UpsideNotifySyncStateEvent syncStateEvent) {
		this.syncStateEvent = syncStateEvent;
	}
	
	@Override
	public String getType() {
		return NotifySyncStateInputEvent.class.getSimpleName();
	}
	
	public class OrderNotFoundInputAction extends InputAction {

		public OrderNotFoundInputAction(HostingExecOrder inputOrder) {
			super(inputOrder);
		}

		@Override
		protected void run() {
			transferCode = TransferCode.RECEIVED_ORDER_NOTFOUND_EVENT;
			
			outputOrder = new HostingExecOrder();
			outputOrder.setStateInfo(new HostingExecOrderStateInfo());
			outputOrder.getStateInfo().setFailedErrorCode(inputOrder.getStateInfo().getFailedErrorCode());
			outputOrder.getStateInfo().setUpsideErrorCode(inputOrder.getStateInfo().getUpsideErrorCode());
			outputOrder.getStateInfo().setUpsideUsefulMsg(inputOrder.getStateInfo().getUpsideUsefulMsg());
		}
	}
	
	@Override
	public InputAction getInputAction(HostingExecOrder inputOrder) {
		if (syncStateEvent.getMappingErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND.getValue()) {
			return new OrderNotFoundInputAction(inputOrder);
		}
		if (syncStateEvent.getMappingErrorCode() != 0) {
			return null;
		}
		if (!syncStateEvent.isSetSyncStateInfo()) {
			AppLog.e("Received no stateInfo syncStateEvent=" + syncStateEvent);
            return null;
		}
		
		if (syncStateEvent.getSyncRespTimestampMs() 
				<= inputOrder.getStateInfo().getCurrentState().getTimestampMs() + SYNC_EFFECTIVE_TIMEMS) {
			AppLog.i("Ignore syncState info, because syncTimestamp is too close");
			return null;
		}
		
		if (inputOrder.getNotifyStateHandleInfos() != null && !inputOrder.getNotifyStateHandleInfos().isEmpty()) {
			HostingUpsideNotifyStateHandleInfo lastHandleInfo = inputOrder.getNotifyStateHandleInfos().get(
					inputOrder.getNotifyStateHandleInfos().size() - 1);
			if (syncStateEvent.getSyncStateInfo().equals(lastHandleInfo.getStateInfo())) {
				AppLog.i("Ignore syncState Info, because already processed!");
				return null;
			}
		}
		
		return new NotifyStateInfoInputAction(inputOrder
				, syncStateEvent.getSyncStateInfo()
				, HostingUpsideNotifyStateSource.UPSIDE_SYNC
				, syncStateEvent.getEventCreateTimestampMs());
	}

}
