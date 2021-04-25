package xueqiao.trade.hosting.storage.app;

import java.util.List;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.events.LandingStatusChangedEvent;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;
import xueqiao.trade.hosting.storage.session.HostingSessionChecker;

public class SessionMessageHandler implements IMessageConsumer {
	
	private IHostingSessionApi mSessionApi;
	
	public SessionMessageHandler() {
		mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
	}
	
	@Override
	public StartUpMode onStartUp() {
		return StartUpMode.CLEAR_QUEUE_INIT;
	}

	@Override
	public void onInit() throws Exception {
		HostingSessionChecker.Global().clear();
		
		List<SessionEntry> sessionList = mSessionApi.getAll();
		for (SessionEntry session : sessionList) {
			HostingSessionChecker.Global().addOnlineUser(session.getSubUserId());
		}
	}
	
	@consume(LandingStatusChangedEvent.class)
	public ConsumeResult onLandingStatusChangedEvent(LandingStatusChangedEvent event) throws ErrorInfo {
		if (event.getSubUserId() <= 0) {
			AppLog.e("error landing status changed event, drop it! " + ", subUserId=" + event.getSubUserId());
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		
		boolean isOnline = mSessionApi.isOnline(event.getSubUserId());
		if (isOnline) {
			HostingSessionChecker.Global().addOnlineUser( event.getSubUserId());
		} else {
			HostingSessionChecker.Global().removeOnlineUser(event.getSubUserId());
		}
		return ConsumeResult.CONSUME_OK;
	}
}
