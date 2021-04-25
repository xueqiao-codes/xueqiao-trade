package xueqiao.trade.hosting.arbitrage.storage.data;

public class XQOrderRecordItem {
    private String orderId;
    private long sledContractId;

    private Double lastestUsedPrice;

    private long createTimestampMs;
    private long lastmodifyTimestampMs;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public long getCreateTimestampMs() {
        return createTimestampMs;
    }

    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }

    public long getLastmodifyTimestampMs() {
        return lastmodifyTimestampMs;
    }

    public void setLastmodifyTimestampMs(long lastmodifyTimestampMs) {
        this.lastmodifyTimestampMs = lastmodifyTimestampMs;
    }

    public Double getLastestUsedPrice() {
        return lastestUsedPrice;
    }

    public void setLastestUsedPrice(Double lastestUsedPrice) {
        this.lastestUsedPrice = lastestUsedPrice;
    }
}
