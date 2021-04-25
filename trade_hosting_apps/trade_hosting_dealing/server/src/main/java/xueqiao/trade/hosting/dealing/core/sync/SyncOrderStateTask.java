package xueqiao.trade.hosting.dealing.core.sync;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutor;
import xueqiao.trade.hosting.dealing.core.ExecOrderManager;
import xueqiao.trade.hosting.upside.entry.helper.UpsideEntryStubBuilder;

public class SyncOrderStateTask implements ISyncTask {
	protected long execOrderId;

	public SyncOrderStateTask(long execOrderId) {
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
				AppLog.d("SyncOrderStateTask run, execOrderId=" + syncOrder.getExecOrderId()
						+ ", StateInfo=" + syncOrder.getStateInfo());
			}
			UpsideEntryStubBuilder.fromOrder(syncOrder).syncOrderState(syncOrder);
		} catch (ErrorInfo e) {
			if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_OPERATION_FORBIDDEN.getValue()) {
				return ;
			}
			AppLog.e(e.getMessage(), e);
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}

	@Override
	public String type() {
		return SyncOrderStateTask.class.getSimpleName();
	}

	@Override
	public String key() {
		return String.valueOf(execOrderId);
	}

}
