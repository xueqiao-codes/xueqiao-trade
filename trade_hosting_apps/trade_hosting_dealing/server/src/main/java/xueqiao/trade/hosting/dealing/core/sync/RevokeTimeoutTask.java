package xueqiao.trade.hosting.dealing.core.sync;

import xueqiao.trade.hosting.events.ExecOrderRevokeTimeoutEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;

/**
 *  撤单状态的超时事件也是绑在订单状态上，复用同步订单状态的类型事件
 *  ，利用状态事件进行调度
 */
public class RevokeTimeoutTask implements ISyncTask {
	
	private long execOrderId;
	
	public RevokeTimeoutTask(long execOrderId) {
		this.execOrderId = execOrderId;
	}
	
	@Override
	public void run() {
		ExecOrderRevokeTimeoutEvent event = new ExecOrderRevokeTimeoutEvent();
		event.setExecOrderId(execOrderId);
		HostingMessageContext.Global().getSenderAgent().sendMessageDirectly(event);
	}

	@Override
	public String type() {
		return RevokeTimeoutTask.class.getSimpleName();
	}

	@Override
	public String key() {
		return String.valueOf(execOrderId);
	}

}
