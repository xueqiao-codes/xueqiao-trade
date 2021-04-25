package xueqiao.trade.hosting.arbitrage.storage.data;

public class XQOrderEffectIndexItem {
    private String orderId;
    private int subUserId;
    private long subAccountId;
    private long ttlTimestampMs;
    private long createTimestampMs;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public int getSubUserId() {
        return subUserId;
    }
    public void setSubUserId(int subUserId) {
        this.subUserId = subUserId;
    }
    
    public long getSubAccountId() {
        return subAccountId;
    }
    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }
    
    public long getTtlTimestampMs() {
        return ttlTimestampMs;
    }
    public void setTtlTimestampMs(long ttlTimestampMs) {
        this.ttlTimestampMs = ttlTimestampMs;
    }
    
    public long getCreateTimestampMs() {
        return createTimestampMs;
    }
    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }
    
    
}
