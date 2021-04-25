package xueqiao.trade.hosting.arbitrage.core;

import xueqiao.trade.hosting.framework.thread.TaskThread;

public class XQOrderContext {
    private TaskThread mWorkThread;
    private String mOrderId;  
    private int mSubUserId;
    private long mSubAccountId;

    public XQOrderContext(
            String orderId
            , int subUserId
            , long subAccountId) {
        this.mOrderId = orderId;
        this.mSubUserId = subUserId;
        this.mSubAccountId = subAccountId;
    }
    
    public TaskThread getWorkThread() {
        return mWorkThread;
    }
    
    public void setWorkThread(TaskThread workThread) {
        this.mWorkThread = workThread;
    }
    
    public String getOrderId() {
        return mOrderId;
    }
    
    public int getSubUserId() {
        return mSubUserId;
    }
    
    public long getSubAccountId() {
        return mSubAccountId;
    }
}
