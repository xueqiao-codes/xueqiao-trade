package xueqiao.trade.hosting.framework.thread;

import com.google.common.base.Preconditions;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskThreadPool {
    private TaskThread[] mTaskThreads;
    private AtomicInteger mLastAllocIndex = new AtomicInteger(0);

    public TaskThreadPool(String baseName) {
        this(baseName, (Runtime.getRuntime().availableProcessors() > 0) ? (Runtime.getRuntime().availableProcessors()* 2) : 2);
    }
    
    public TaskThreadPool(String baseName, int num) {
        Preconditions.checkArgument(num > 0);
        mTaskThreads = new TaskThread[num];
        for (int index = 0; index < num; ++index) {
            mTaskThreads[index] = new TaskThread();
            mTaskThreads[index].setName(baseName + "-" + index + "-" + mTaskThreads[index].getId());
        }
    }

    public TaskThread getTaskThreadByMod(int key) {
        return mTaskThreads[key % mTaskThreads.length];
    }

    public TaskThread getTaskThreadByMod(long key) {
        return mTaskThreads[(int)(key % mTaskThreads.length)];
    }
    
    public TaskThread allocTaskThread() {
        return mTaskThreads[mLastAllocIndex.incrementAndGet() % mTaskThreads.length];
    }
    
}
