package xueqiao.trade.hosting.push.sdk.impl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.soldier.base.logger.AppLog;

public class WorkThread extends Thread {
	private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<Runnable>();
	
	private volatile boolean mEnded = false;
	
	public WorkThread(String name) {
		super(name);
		this.setDaemon(true);
		this.start();
	}
	
	public void end() {
		mEnded = true;
		interrupt();
	}
	
	public void postTask(Runnable command) {
		try {
			mQueue.put(command);
		} catch (InterruptedException e) {
			AppLog.e(e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		while(!mEnded) {
			try { 
				Runnable runnable = mQueue.poll(100, TimeUnit.MILLISECONDS);
				if (runnable != null) {
					runnable.run();
				}
			}catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
}
