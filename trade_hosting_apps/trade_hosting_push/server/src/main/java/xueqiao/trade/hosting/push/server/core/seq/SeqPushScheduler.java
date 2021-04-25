package xueqiao.trade.hosting.push.server.core.seq;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.soldier.base.logger.AppLog;

/**
 *  调度队列发送
 * @author wangli
 *
 */
public class SeqPushScheduler {
    
    private ScheduledExecutorService mScheduleExecutor;
    private ConcurrentHashMap<Runnable, TaskImpl> mWaitingRunnables;
    
    private class TaskImpl implements Runnable {
        private Runnable r;
        
        public TaskImpl(Runnable r) {
            this.r = r;
        }
        
        @Override
        public void run() {
            try {
                mWaitingRunnables.remove(r);
                r.run();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            } 
        }
    }
    
    public SeqPushScheduler() {
        mScheduleExecutor = Executors.newScheduledThreadPool(1);
        mWaitingRunnables = new ConcurrentHashMap<Runnable, TaskImpl>();
    }
    
    public synchronized void markRunnable(Runnable r, int delayMs) {
        if (r == null) {
            return ;
        }
        
        if (mWaitingRunnables.containsKey(r)) {
        	if (AppLog.infoEnabled()) {
        		AppLog.i(r + " is already markRunnable");
        	}
            return ;
        }
        
        TaskImpl t = new TaskImpl(r);
        mWaitingRunnables.put(r, t);
        try {
        	mScheduleExecutor.schedule(t, delayMs, TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
        	AppLog.e(e.getMessage(), e);
        	mWaitingRunnables.remove(r);
        	throw e;
        }
    }
    
}
