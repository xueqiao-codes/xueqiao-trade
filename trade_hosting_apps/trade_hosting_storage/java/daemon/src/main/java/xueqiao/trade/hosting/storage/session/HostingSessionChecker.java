package xueqiao.trade.hosting.storage.session;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.events.LandingStatusChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public class HostingSessionChecker extends Thread {
	private static HostingSessionChecker INSTANCE; 
	
	private static final int SESSION_TIMEOUT_SECONDS = 60;
	
	private IHostingSessionApi mSessionApi;
	
	private Set<Integer> mOnlineUsers = new HashSet<Integer>();
	private ReentrantLock mLock = new ReentrantLock();
	
	public static HostingSessionChecker Global() {
		if (INSTANCE == null) {
			synchronized(HostingSessionChecker.class) {
				if (INSTANCE == null) {
					INSTANCE = new HostingSessionChecker();
				}
			}
		}
		return INSTANCE;
	}
	
	private HostingSessionChecker() {
		this.mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
		
		this.setName("hosting_session_checker");
		this.setDaemon(true);
		this.start();
	}
	
	public void clear() {
		mLock.lock();
		try {
			mOnlineUsers.clear();
		} finally {
			mLock.unlock();
		}
	}
	
	public void addOnlineUser(int subUserId) {
		mLock.lock();
		try {
			mOnlineUsers.add(subUserId);
		} finally {
			mLock.unlock();
		}
	}
	
	public void removeOnlineUser(int subUserId) {
		mLock.lock();
		try {
			mOnlineUsers.remove(subUserId);
		} finally {
			mLock.unlock();
		}
	}
	
	public void checkOnce() {
		Set<Integer> copyOnlineUsers = null;
		mLock.lock();
		try {
			copyOnlineUsers = new HashSet<Integer>(mOnlineUsers);
		} finally {
			mLock.unlock();
		}
	
		int currentTimestampS = (int)(System.currentTimeMillis()/1000);
		for (Integer subUserId : copyOnlineUsers) {
			try {
				SessionEntry entry = mSessionApi.getSession(subUserId);
				if (entry == null) {
					LandingStatusChangedEvent event = new LandingStatusChangedEvent();
					event.setSubUserId(subUserId);
					HostingMessageContext.Global().getSenderAgent().sendMessageDirectly(event);
				} else {
					if (currentTimestampS - entry.getLastUpdateTimestamp() >= SESSION_TIMEOUT_SECONDS) {
						mSessionApi.deleteSession(subUserId);
					}
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				AppLog.i("start check session...");
				Thread.sleep(30000);
				checkOnce();
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
}
