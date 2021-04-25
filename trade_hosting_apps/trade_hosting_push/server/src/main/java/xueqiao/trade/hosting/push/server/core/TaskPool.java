package xueqiao.trade.hosting.push.server.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wileywang
 */
public class TaskPool  {
	private ThreadPoolExecutor mTaskExector;
	private int mCorePoolSize;
	private int mMaxPoolSize;
	
	public TaskPool(int corePoolSize, int maxPoolSize, int maxPendingTask) {
		if (corePoolSize < 0) {
			throw new IllegalArgumentException("corePoolSize should not < 0");
		}
		if (maxPoolSize < corePoolSize) {
			throw new IllegalArgumentException("maxPoolSize should not < corePoolSize");
		}
		if (maxPendingTask <= 0) {
			throw new IllegalArgumentException("maxPendingTask should not <= 0");
		}
		
		this.mCorePoolSize = corePoolSize;
		this.mMaxPoolSize = maxPoolSize;
		this.mTaskExector = new ThreadPoolExecutor(
				corePoolSize, maxPoolSize, 300,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(maxPendingTask),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "TaskThread");
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
	}
	
	public int getCorePoolSize() {
		return mCorePoolSize;
	}
	
	public int getMaxPoolSize() {
		return mMaxPoolSize;
	}
	
	public boolean addTask(Runnable task) {
		try {
			mTaskExector.execute(task);
			return true;
		} catch (RejectedExecutionException e) {
			return false;
		}
	}
}
