package xueqiao.trade.hosting.framework.thread;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import java.util.concurrent.Semaphore;

public abstract class SyncResult<T> {
    private TaskThread mThread;
    private Semaphore mSem;
    private ErrorInfo mErrorInfo; 
    
    private T mResult;
    
    public SyncResult(TaskThread thread) {
        this.mThread = thread;
    }
    
    protected abstract T onCall() throws Exception;
    
    public T get() throws ErrorInfo {
        mSem = new Semaphore(0);
        mThread.postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    mResult = onCall();
                } catch (ErrorInfo e) {
                    AppLog.e(e.getMessage(), e);
                    mErrorInfo = e;
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                    mErrorInfo = new ErrorInfo(10500, "server inner error!");
                }
                mSem.release();
            }
        });
        mSem.acquireUninterruptibly();
        if (mErrorInfo != null) {
            throw mErrorInfo;
        }
        return mResult;
    }
}
