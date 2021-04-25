package xueqiao.trade.hosting.position.statis.storage.bean;

import xueqiao.trade.hosting.HostingExecTradeDirection;

/**
 * 执行成交关联数据
 * */
public class SourceHostingXQTradeRelatedItem {
    private long sourceId;         // 因为这里没有可用的唯一key，用idMaker生成
    private long subAccountId;
    private boolean isCompleted;   // 是否为已核对过的全部归档数据

    private String orderId;
    private long tradeId;
    private long execOrderId;
    private long execTradeId;
    private long execTradeLegId;
    private HostingExecTradeDirection execTradeLegDirection;
    private double execTradeLegPrice;
    private int relatedTradeVolume;
    private long sledContractId;
    private long createTimestampMs;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public long getExecOrderId() {
        return execOrderId;
    }

    public void setExecOrderId(long execOrderId) {
        this.execOrderId = execOrderId;
    }

    public long getExecTradeId() {
        return execTradeId;
    }

    public void setExecTradeId(long execTradeId) {
        this.execTradeId = execTradeId;
    }

    public long getExecTradeLegId() {
        return execTradeLegId;
    }

    public void setExecTradeLegId(long execTradeLegId) {
        this.execTradeLegId = execTradeLegId;
    }

    public HostingExecTradeDirection getExecTradeLegDirection() {
        return execTradeLegDirection;
    }

    public void setExecTradeLegDirection(HostingExecTradeDirection execTradeLegDirection) {
        this.execTradeLegDirection = execTradeLegDirection;
    }

    public double getExecTradeLegPrice() {
        return execTradeLegPrice;
    }

    public void setExecTradeLegPrice(double execTradeLegPrice) {
        this.execTradeLegPrice = execTradeLegPrice;
    }

    public int getRelatedTradeVolume() {
        return relatedTradeVolume;
    }

    public void setRelatedTradeVolume(int relatedTradeVolume) {
        this.relatedTradeVolume = relatedTradeVolume;
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
}
