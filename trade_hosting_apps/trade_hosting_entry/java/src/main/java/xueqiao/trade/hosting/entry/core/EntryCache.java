package xueqiao.trade.hosting.entry.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.soldier.base.beanfactory.Globals;

import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.ext.HostingSubAccountRelatedCache;

public class EntryCache {
	private static EntryCache sInstance;
	
	private Map<Integer, HostingUser> mUserCaches = new ConcurrentHashMap<Integer, HostingUser>();
	private volatile HostingRunningMode mHostingRunningMode = null;
	private HostingSubAccountRelatedCache mSubAccountRelatedCache;
	
	public static EntryCache Global() {
		if (sInstance == null) {
			synchronized(EntryCache.class) {
				if (sInstance == null) {
					sInstance = new EntryCache();
				}
			}
		}
		return sInstance;
	}
	
	private EntryCache() {
	    mSubAccountRelatedCache = new HostingSubAccountRelatedCache(
	            Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class));
	}
	
	public void clearUsers() {
	    mUserCaches.clear();
	}
	
	public void putUser(HostingUser user) {
		user.unsetLoginPasswd();
		mUserCaches.put(user.getSubUserId(), user);
	}
	
	public HostingUser getUser(int subUserId) {
		return mUserCaches.get(subUserId);
	}
	
	public void rmUser(int subUserId) {
	    mUserCaches.remove(subUserId);
	}
	
	public HostingRunningMode getHostingRunningMode() {
	    return mHostingRunningMode;
	}
	
	public void setHostingRunningMode(HostingRunningMode runningMode) {
	    this.mHostingRunningMode = runningMode;
	}
	
	public HostingSubAccountRelatedCache getSubAccountRelatedCache() {
	    return mSubAccountRelatedCache;
	}
	
}
