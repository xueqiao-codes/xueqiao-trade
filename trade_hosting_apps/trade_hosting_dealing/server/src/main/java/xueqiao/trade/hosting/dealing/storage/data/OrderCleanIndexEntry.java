package xueqiao.trade.hosting.storage.apis.structs;

/**
 *  订单删除的条目
 */
public class OrderCleanIndexEntry {
    private long cleanTimestampMs;  // 记录应该清理的时间
    private long execOrderId; // 对应的执行订单ID
    private long createTimestampMs;

    public long getCleanTimestampMs() {
        return cleanTimestampMs;
    }

    public void setCleanTimestampMs(long cleanTimestampMs) {
        this.cleanTimestampMs = cleanTimestampMs;
    }

    public long getExecOrderId() {
        return execOrderId;
    }

    public void setExecOrderId(long execOrderId) {
        this.execOrderId = execOrderId;
    }

    public long getCreateTimestampMs() {
        return createTimestampMs;
    }

    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }


}
