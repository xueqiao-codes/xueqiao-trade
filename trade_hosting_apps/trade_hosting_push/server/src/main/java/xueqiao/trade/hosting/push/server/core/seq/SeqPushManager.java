package xueqiao.trade.hosting.push.server.core.seq;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.push.protocol.ClientForceSyncEvent;
import xueqiao.trade.hosting.push.server.core.IDispatcherListener;
import xueqiao.trade.hosting.push.server.core.PushChannel;

public class SeqPushManager implements IDispatcherListener {
	private static SeqPushManager sInstance;
	
	public static SeqPushManager Global() {
		if (sInstance == null) {
			synchronized(SeqPushManager.class) {
				if (sInstance == null) {
					sInstance = new SeqPushManager();
				}
			}
		}
		return sInstance;
	}
	
	private ScheduledExecutorService mTimerScheduleService;
	private ConcurrentHashMap<Integer, SeqPushQueue> mOnlineUserPushQueues;
	private SeqPushScheduler mScheduler;
	
	private SeqPushManager() {
		mTimerScheduleService = Executors.newScheduledThreadPool(1);
		mOnlineUserPushQueues = new ConcurrentHashMap<Integer, SeqPushQueue>();
		mScheduler = new SeqPushScheduler();
	}
	
	@SuppressWarnings("rawtypes")
	public void push(int subUserId, TBase msg) {
	    if (msg == null) {
	        return ;
		}
	        
	    SeqPushQueue pushQueue = mOnlineUserPushQueues.get(subUserId);
	    if (pushQueue == null) {
	        return ;
	    }
	    if (AppLog.infoEnabled()) {
	        StringBuilder logBuilder = new StringBuilder(256);
	        logBuilder.append("push msg=")
	                    .append(msg)
	                    .append(" to subUserId=")
	                    .append(subUserId)
	                    .append(", pushQueue=")
	                    .append(pushQueue);
	        AppLog.i(logBuilder.toString());
	    }
	        
	    pushQueue.addMessage(msg);
	}

	public void pushAll(TBase msg) {
		Iterator<Entry<Integer, SeqPushQueue>> iterator = mOnlineUserPushQueues.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, SeqPushQueue> entry = iterator.next();
			if (AppLog.infoEnabled()) {
				StringBuilder logBuilder = new StringBuilder(256);
				logBuilder.append("push msg=")
						.append(msg)
						.append(" to subUserId=")
						.append(entry.getKey())
						.append(", pushQueue=")
						.append(entry.getValue());
				AppLog.i(logBuilder.toString());
			}

			entry.getValue().addMessage(msg);
		}
	}
	
	@Override
	public void onConnected(int subUserId, PushChannel pushChannel) {
		if (AppLog.infoEnabled()) {
			AppLog.i("onConnected..., subUserId=" + subUserId);
		}
		
		SeqPushQueue pushQueue = new SeqPushQueue(mScheduler
				, subUserId, pushChannel, mTimerScheduleService);
		mOnlineUserPushQueues.put(subUserId, pushQueue);
		push(subUserId, new ClientForceSyncEvent().setSubUserId(subUserId));
	}

	@Override
	public void onDisconnected(int subUserId) {
		if (AppLog.infoEnabled()) {
            AppLog.i("onDisconnected..., subUserId=" + subUserId);
        }
		
		SeqPushQueue pushQueue = mOnlineUserPushQueues.remove(subUserId);
        if (pushQueue != null) {
            pushQueue.destroy();
        }
        
	}
	
	public void onPushMessageAgentInit() {
		Iterator<Entry<Integer, SeqPushQueue>> it = mOnlineUserPushQueues.entrySet().iterator();
        while(it.hasNext()) {
            Entry<Integer, SeqPushQueue> e = it.next();
            push(e.getKey(), new ClientForceSyncEvent().setSubUserId(e.getKey()));
        }
	}
}
