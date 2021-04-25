package xueqiao.trade.hosting.dealing.storage.data;

public class ExecOrderIndexEntryV2 {
    private long execOrderId = -1;

    private long createTimestampMs;

    public long getExecOrderId() {
        return execOrderId;
    }

    public void setExecOrderId(long execOrderId) {
        this.execOrderId = execOrderId;
    }

    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }

}
