package xueqiao.trade.hosting.position.statis.storage.util;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import javax.sql.DataSource;
import java.util.AbstractMap;
import java.util.List;

public abstract class DBTransactionHelperWithMsgList<T> extends DBTransactionHelper<T> {

	private List<StatMessage> statMessageList = null;

	protected List<StatMessage> prepareStatMessage() {
		return null;
	}

	protected abstract MessageAgent getMessageAgent();

	public DBTransactionHelperWithMsgList(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected void onUpdateFinished() throws ErrorInfo, Exception {
		statMessageList = prepareStatMessage();
		if (statMessageList != null && !statMessageList.isEmpty()) {
			for (StatMessage message : statMessageList) {
				if (message.guardMessage != null) {
					if (message.guardMessage.getValue() != null) {
						message.guardId = getMessageAgent().prepareGuardMessage(message.guardMessage.getKey(), message.guardMessage.getValue());
					} else {
						message.guardId = getMessageAgent().prepareGuardMessage(message.guardMessage.getKey());
					}
				}
			}
		}

		super.onUpdateFinished();
	}

	@Override
	protected void onCommitFinished() throws Exception {
		if (statMessageList != null && !statMessageList.isEmpty()) {
			TBase notifyMessage = null;
			boolean success = false;
			for (StatMessage message : statMessageList) {
				if (message.guardMessage != null) {
					notifyMessage = message.notifyMessage;
					if (notifyMessage == null) {
						getMessageAgent().sendMessageDirectly(message.guardMessage.getKey());
						getMessageAgent().rmGuardMessage(message.guardId);
					} else {
						success = getMessageAgent().sendMessageDirectly(notifyMessage);
						if (success) {
							getMessageAgent().rmGuardMessage(message.guardId);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onRollbackFinished() throws Exception {
		if (statMessageList != null && !statMessageList.isEmpty()) {
			for (StatMessage message : statMessageList) {
				if (message.guardMessage != null) {
					getMessageAgent().rmGuardMessage(message.guardId);
				}
			}
		}
	}

	@Override
	protected void onTransactionOperationException() throws Exception {
		if (statMessageList != null && !statMessageList.isEmpty()) {
			for (StatMessage message : statMessageList) {
				if (message.guardMessage != null) {
					getMessageAgent().rmGuardMessage(message.guardId);
				}
			}
		}
	}

	public static class StatMessage {
		public AbstractMap.SimpleEntry<TBase, IGuardPolicy> guardMessage;
		public String guardId = "";
		public TBase notifyMessage;
	}
}
