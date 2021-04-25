package xueqiao.trade.hosting.framework.thread;

import org.soldier.base.logger.AppLog;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TaskThread extends Thread {
    private static class DelayedTask implements Delayed {
        /**
         * 到期时间
         */
        private final long time;

        /**
         * 问题对象
         */
        private final Runnable task;
        private static final AtomicLong atomic = new AtomicLong(0);

        private final long n;

        public DelayedTask(long timeout, Runnable t) {
            this.time = System.nanoTime() + timeout;
            this.task = t;
            this.n = atomic.getAndIncrement();
        }

        /**
         * 返回与此对象相关的剩余延迟时间，以给定的时间单位表示
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this) // compare zero ONLY if same object
                return 0;
            if (other instanceof DelayedTask) {
                DelayedTask x = (DelayedTask) other;
                long diff = time - x.time;
                if (diff < 0)
                    return -1;
                else if (diff > 0)
                    return 1;
                else if (n < x.n)
                    return -1;
                else
                    return 1;
            }
            long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }

        public Runnable getTask() {
            return this.task;
        }

        @Override
        public int hashCode() {
            return task.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof DelayedTask) {
                return object.hashCode() == hashCode() ? true : false;
            }
            return false;
        }

    }

    private DelayQueue<DelayedTask> mDelayedQueue = new DelayQueue<DelayedTask>();
    private volatile boolean mEnded = false;
    
    public TaskThread() {
        setDaemon(true);
        start();
    }
    
    public void end() {
        mEnded = true;
        this.interrupt();
    }
    
    public void postTask(Runnable r) {
        mDelayedQueue.add(new DelayedTask(0, r));
    }
    
    public void postTaskDelay(Runnable r, long delay, TimeUnit unit) {
        mDelayedQueue.add(new DelayedTask(TimeUnit.NANOSECONDS.convert(delay, unit), r));
    }
    
    public boolean isInCurrentThread() {
        return Thread.currentThread() == this;
    }
    
    @Override
    public void run() {
        while(!mEnded) {
            try {
                DelayedTask dt = mDelayedQueue.poll(100, TimeUnit.MILLISECONDS);
                if (dt == null) {
                    continue;
                }

                dt.getTask().run();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }
    
}
