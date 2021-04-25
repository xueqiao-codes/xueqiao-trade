package xueqiao.trade.hosting.framework.utils;

import java.util.AbstractMap.SimpleEntry;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class DBStepHelperWithMsg<T> extends DBStepHelper<T> {

	@SuppressWarnings("rawtypes")
	private SimpleEntry<TBase, IGuardPolicy> guradMessage;
	private String guardId = "";
	
	public DBStepHelperWithMsg(DataSource dataSource) {
		super(dataSource);
	}
	
	@SuppressWarnings("rawtypes")
	protected abstract SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage();
	
	@SuppressWarnings("rawtypes")
    protected TBase prepareNotifyMessage() { return null; }
	
	protected abstract MessageAgent getMessageAgent();

	@Override
	protected void onPrepareDataFinished() throws ErrorInfo, Exception {
	    guradMessage = prepareGuardMessage();
		if (guradMessage != null) {
			if (guradMessage.getValue() != null) {
				guardId = getMessageAgent().prepareGuardMessage(guradMessage.getKey(), guradMessage.getValue());
			} else {
				guardId = getMessageAgent().prepareGuardMessage(guradMessage.getKey());
			}
		}
	}
	
	@Override
	protected void onUpdateFinished() throws ErrorInfo, Exception {
		if (guradMessage != null) {
			@SuppressWarnings("rawtypes")
            TBase notifyMessage = prepareNotifyMessage();
			if (notifyMessage == null) {
			    getMessageAgent().sendMessageDirectly(guradMessage.getKey());
			    getMessageAgent().rmGuardMessage(guardId);
			} else {
			    boolean success =getMessageAgent().sendMessageDirectly(notifyMessage);
			    if (success) {
			    	getMessageAgent().rmGuardMessage(guardId);
			    }
			}
		}
		super.onUpdateFinished();
	}
	

	@Override
	protected void onException() {
		if (!StringUtils.isEmpty(guardId)) {
			getMessageAgent().rmGuardMessage(guardId);
		}
	}

}
