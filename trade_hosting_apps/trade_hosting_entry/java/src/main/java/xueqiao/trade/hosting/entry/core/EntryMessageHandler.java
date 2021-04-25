package xueqiao.trade.hosting.entry.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.events.HostingEventType;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEvent;
import xueqiao.trade.hosting.events.UserEvent;
import xueqiao.trade.hosting.events.UserEventType;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;

public class EntryMessageHandler implements IMessageConsumer {
    
    private IHostingUserApi mHostingUserApi;
    private IHostingManageApi mHostingManageApi;
    
    public EntryMessageHandler() {
        mHostingUserApi =  Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class);
        mHostingManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
    }
    
	@Override
	public StartUpMode onStartUp() {
		return StartUpMode.CLEAR_QUEUE_INIT;
	}

	@Override
	public void onInit() throws Exception {
	    EntryCache.Global().setHostingRunningMode(mHostingManageApi.getRunningMode());
		syncHostingUsers();
		EntryCache.Global().getSubAccountRelatedCache().sync();
	}
	
	private void syncHostingUsers() throws ErrorInfo {
		EntryCache.Global().clearUsers();
		PageResult<HostingUser> userList 
			= mHostingUserApi.queryUserPage(null, new PageOption().setPageSize(Integer.MAX_VALUE));
		for (HostingUser user : userList.getPageList()) {
			EntryCache.Global().putUser(user);
		}
	}
	
	@consume(UserEvent.class)
	public ConsumeResult onHandleUserEvent(UserEvent event) throws ErrorInfo {
	    if (AppLog.infoEnabled()) {
	        AppLog.i("onHandleUserEvent received " + event);
	    }
	    
	    if (event.getType() == UserEventType.USER_ALL_CLEARD) {
	        syncHostingUsers();
	        return ConsumeResult.CONSUME_OK;
	    }
	    
		if (event.getSubUserId() <= 0) {
			AppLog.w("receive user event, but event parameter is invalid!");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		
		// 添加，删除和更新统一处理
		QueryUserOption option = new QueryUserOption();
		option.setSubUserId(event.getSubUserId());
		
		PageResult<HostingUser> userList = mHostingUserApi.queryUserPage(option, new PageOption().setPageSize(1));
		if (userList.getPageList().isEmpty()) {
			EntryCache.Global().rmUser(event.getSubUserId());
		} else {
			EntryCache.Global().putUser(userList.getPageList().get(0));
		}
		
		return ConsumeResult.CONSUME_OK;
	}
	
	@consume(HostingEvent.class)
	public ConsumeResult onHandleHostingEvent(HostingEvent event) throws ErrorInfo {
	    if (AppLog.infoEnabled()) {
	        AppLog.i("onHandleHostingEvent received " + event);
	    }
	    
		if (event.getType() == null) {
			AppLog.w("receive hosting event, bug event parameter is invalid!");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		
		if (event.getType() == HostingEventType.HOSTING_INITED) {
		    EntryCache.Global().setHostingRunningMode(mHostingManageApi.getRunningMode());
			syncHostingUsers();
		}  
		
		return ConsumeResult.CONSUME_OK;
	}
	
	@consume(SubAccountRelatedInfoChangedEvent.class)
	public ConsumeResult onHandleSubAccountRelatedInfoChangedEvent(SubAccountRelatedInfoChangedEvent event) throws ErrorInfo {
	    if (AppLog.infoEnabled()) {
	        AppLog.i("onHandleSubAccountRelatedInfoChangedEvent received " + event);
	    }
	    return EntryCache.Global().getSubAccountRelatedCache().handleSubAccountRelatedInfoChangedEvent(event);
	}

}
