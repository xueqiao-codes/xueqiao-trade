package xueqiao.trade.hosting.framework.utils;

import java.util.AbstractMap.SimpleEntry;

import javax.sql.DataSource;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class DBTransactionHelperWithMsg<T> extends DBTransactionHelper<T> {
	
	@SuppressWarnings("rawtypes")
	private SimpleEntry<TBase, IGuardPolicy> guardMessage;
	private String guardId = "";
	
	@SuppressWarnings("rawtypes")
	protected abstract SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage();
	
	@SuppressWarnings("rawtypes")
    protected TBase prepareNotifyMessage() { return null; }
	
	protected abstract MessageAgent getMessageAgent();
	
	public DBTransactionHelperWithMsg(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	protected void onUpdateFinished() throws ErrorInfo, Exception {
	    guardMessage = prepareGuardMessage();
		if (guardMessage != null) {
			if (guardMessage.getValue() != null) {
				guardId = getMessageAgent().prepareGuardMessage(guardMessage.getKey(), guardMessage.getValue());
			} else {
				guardId = getMessageAgent().prepareGuardMessage(guardMessage.getKey());
			}
		}
		
		super.onUpdateFinished();
	}
	
	@Override
	protected void onCommitFinished() throws Exception {
	    if (guardMessage != null) {
	        @SuppressWarnings("rawtypes")
            TBase notifyMessage = prepareNotifyMessage();
	        if (notifyMessage == null) {
	            getMessageAgent().sendMessageDirectly(guardMessage.getKey());
	            getMessageAgent().rmGuardMessage(guardId);
	        } else {
	            boolean success = getMessageAgent().sendMessageDirectly(notifyMessage);
	            if (success) {
	            	getMessageAgent().rmGuardMessage(guardId);
	            }
	        }
	    }
	}
	
	@Override
	protected void onRollbackFinished() throws Exception {
		if (guardMessage != null) {
			getMessageAgent().rmGuardMessage(guardId);
		}
	}
	
	@Override
	protected void onTransactionOperationException() throws Exception {
		if (guardMessage != null) {
			getMessageAgent().rmGuardMessage(guardId);
		}
	}

}
