package xueqiao.trade.hosting.dealing.core.dfa.events;

import java.util.ArrayList;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.HostingUpsideNotifyStateHandleInfo;
import xueqiao.trade.hosting.HostingUpsideNotifyStateInfo;
import xueqiao.trade.hosting.HostingUpsideNotifyStateSource;
import xueqiao.trade.hosting.HostingUpsideNotifyStateType;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;

public class NotifyStateInfoInputAction extends InputAction {
	
	private HostingUpsideNotifyStateInfo notifyStateInfo;
	private HostingUpsideNotifyStateSource notifyStateSource;
	private long eventCreateTimestampMs;
	
	public NotifyStateInfoInputAction(HostingExecOrder inputOrder
			, HostingUpsideNotifyStateInfo notifyStateInfo
			, HostingUpsideNotifyStateSource notifyStateSource
			, long eventCreateTimestampMs) {
		super(inputOrder);
		this.notifyStateInfo = notifyStateInfo;
		this.notifyStateSource = notifyStateSource;
		this.eventCreateTimestampMs = eventCreateTimestampMs;
	}

	@Override
	protected void run() {
		outputOrder = new HostingExecOrder();
        outputOrder.setDealInfo(notifyStateInfo.getDealInfo());
        
        HostingExecOrderTradeSummary tradeSummary = new HostingExecOrderTradeSummary();
        if (notifyStateInfo.isSetVolumeTraded()) {
        	if (notifyStateInfo.getVolumeTraded() < inputOrder.getTradeSummary().getTradeListTotalVolume()) {
        		AppLog.w("Found notifyStateInfo=" + notifyStateInfo 
        				+ " volumnTraded < " + inputOrder.getTradeSummary().getTradeListTotalVolume());
        		transferCode = TransferCode.SLED_SKIP;
        		return ;
        	}
            tradeSummary.setUpsideTradeTotalVolume(notifyStateInfo.getVolumeTraded());
        }
        if (notifyStateInfo.isSetVolumeResting()) {
            tradeSummary.setUpsideTradeRestingVolume(notifyStateInfo.getVolumeResting());
        }
        if (notifyStateInfo.isSetTradeAveragePrice()) {
            tradeSummary.setUpsideTradeAveragePrice(notifyStateInfo.getTradeAveragePrice());
        }
        outputOrder.setTradeSummary(tradeSummary);
        
        outputOrder.setStateInfo(new HostingExecOrderStateInfo());
        outputOrder.getStateInfo().setUpsideUsefulMsg(notifyStateInfo.getStatusUsefulMsg());
        
        
        outputOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
        if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_UPSIDE_RECIVED) {
            transferCode = TransferCode.RECEIVED_UPSIDE_RECEIVED_EVENT;
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_RESTING) {
            transferCode = TransferCode.RECEIVED_ORDER_RESTING_EVENT;
            if (notifyStateInfo.isSetVolumeTraded()) {
                // 上手还是挂单状态，但是具备成交数，这个时候任然为部分成交, 这里更加信赖上手的数量
                if (notifyStateInfo.getVolumeTraded() > 0) {
                    transferCode = TransferCode.RECEIVED_ORDER_PARTFINISHED_EVENT;
                    // 如果成交数已经达到总量，说明是全部成交
                    if (notifyStateInfo.getVolumeTraded() == inputOrder.getOrderDetail().getQuantity()) {
                        transferCode = TransferCode.RECEIVED_ORDER_FINISHED_EVENT;
                    }
                }   
            }
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_PARTFINISHED) {
            transferCode = TransferCode.RECEIVED_ORDER_PARTFINISHED_EVENT;
            if (notifyStateInfo.isSetVolumeTraded() 
                    && notifyStateInfo.getVolumeTraded() == inputOrder.getOrderDetail().getQuantity()) {
                transferCode = TransferCode.RECEIVED_ORDER_FINISHED_EVENT;
            }
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_FINISHED) {
            transferCode = TransferCode.RECEIVED_ORDER_FINISHED_EVENT;
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_CONDITION_NOT_TRIGGERED) {
            transferCode = TransferCode.RECEIVED_CONDITION_NOT_TRIGGERED_EVENT;
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_CONDITION_TRIGGERED) {
            transferCode = TransferCode.RECEIVED_CONDITION_TRIGGERED_EVENT;
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_CANCELLED) {
            transferCode = TransferCode.RECEIVED_ORDER_CANCELLED_EVENT;
            outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(0);
            outputOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(0);
            outputOrder.getRevokeInfo().setLastRevokeUpsideRejectReason("");
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_REJECTED) {
            transferCode = TransferCode.RECEIVED_ORDER_REJECT_EVENT;
        } else if (notifyStateInfo.getState() == HostingUpsideNotifyStateType.NOTIFY_ORDER_CANCEL_RECEIVED) {
            transferCode = TransferCode.RECEIVED_ORDER_CANCEL_RECEIVED_EVENT;
            outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(0);
            outputOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(0);
            outputOrder.getRevokeInfo().setLastRevokeUpsideRejectReason("");
        } 
        
        outputOrder.setNotifyStateHandleInfos(inputOrder.getNotifyStateHandleInfos());
        if (outputOrder.getNotifyStateHandleInfos() == null) {
            outputOrder.setNotifyStateHandleInfos(new ArrayList<HostingUpsideNotifyStateHandleInfo>());
        }
        
        HostingUpsideNotifyStateHandleInfo newHandleInfo = new HostingUpsideNotifyStateHandleInfo();
        newHandleInfo.setStateInfo(notifyStateInfo);
        newHandleInfo.setEventCreateTimestampMs(eventCreateTimestampMs);
        newHandleInfo.setHandledTimestampMs(System.currentTimeMillis());
        newHandleInfo.setSource(notifyStateSource);
        outputOrder.getNotifyStateHandleInfos().add(newHandleInfo);
	}

}
