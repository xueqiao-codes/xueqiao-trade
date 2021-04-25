package xueqiao.trade.hosting.dealing.core.sync;

import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutor;
import xueqiao.trade.hosting.dealing.core.ExecOrderManager;
import xueqiao.trade.hosting.upside.entry.helper.UpsideEntryStubBuilder;

public class SyncOrderTradeListTask implements ISyncTask {
	
	private long execOrderId;

	public SyncOrderTradeListTask(long execOrderId) {
		this.execOrderId = execOrderId;
	}
	
	@Override
	public void run() {
		try {
			ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(execOrderId);
			if (executor == null) {
				SyncOrderTaskManager.Global().cancelTask(this);
				return;
			}

			HostingExecOrder syncOrder = executor.getExecOrder();
			if (syncOrder == null) {
				AppLog.w("SyncOrderStateTask order not found for execOrderId=" + execOrderId);
				return;
			}

			if (AppLog.debugEnabled()) {
				AppLog.d("SyncOrderTradeListTask run, execOrderId=" + syncOrder.getExecOrderId()
						+ ", StateInfo=" + syncOrder.getStateInfo()
						+ ", tradeSummary=" + syncOrder.getTradeSummary());
			}

			UpsideEntryStubBuilder.fromOrder(syncOrder).syncOrderTrades(syncOrder);
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}

	@Override
	public String type() {
		return SyncOrderTradeListTask.class.getSimpleName();
	}

	@Override
	public String key() {
		return String.valueOf(execOrderId);
	}

}
