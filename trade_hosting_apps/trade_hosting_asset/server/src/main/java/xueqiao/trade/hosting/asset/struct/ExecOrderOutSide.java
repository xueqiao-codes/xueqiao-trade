package xueqiao.trade.hosting.asset.struct;

import xueqiao.trade.hosting.HostingExecTradeDirection;

public class ExecOrderOutSide {

    private long execOrderId;
    private long sledContractId;
    private long subAccountId;
    private int volume;
    private double price;
    private HostingExecTradeDirection tradeDirection;
    private long sledCommodityId;
    private long createTimestampMs;
    private long lastModifyTimestampMs;

    public long getExecOrderId() {
        return execOrderId;
    }

    public void setExecOrderId(long execOrderId) {
        this.execOrderId = execOrderId;
    }

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public HostingExecTradeDirection getTradeDirection() {
        return tradeDirection;
    }

    public void setTradeDirection(HostingExecTradeDirection tradeDirection) {
        this.tradeDirection = tradeDirection;
    }

    public long getSledCommodityId() {
        return sledCommodityId;
    }

    public void setSledCommodityId(long sledCommodityId) {
        this.sledCommodityId = sledCommodityId;
    }

    public long getCreateTimestampMs() {
        return createTimestampMs;
    }

    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }

    public long getLastModifyTimestampMs() {
        return lastModifyTimestampMs;
    }

    public void setLastModifyTimestampMs(long lastModifyTimestampMs) {
        this.lastModifyTimestampMs = lastModifyTimestampMs;
    }
}
