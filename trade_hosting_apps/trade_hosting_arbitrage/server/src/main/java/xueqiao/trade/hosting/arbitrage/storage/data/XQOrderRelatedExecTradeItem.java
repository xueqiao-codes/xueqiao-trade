package xueqiao.trade.hosting.arbitrage.storage.data;

import xueqiao.trade.hosting.HostingExecTrade;

public class XQOrderRelatedExecTradeItem {
    private String orderId;
    private long execOrderId;
    private long execTradeId;
    private long sledContractId;
    private HostingExecTrade relatedExecTrade;
    
    private long createTimestampMs;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
    
    public long getSledContractId() {
        return sledContractId;
    }
    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }
    
    public HostingExecTrade getRelatedExecTrade() {
        return relatedExecTrade;
    }
    public void setRelatedExecTrade(HostingExecTrade relatedExecTrade) {
        this.relatedExecTrade = relatedExecTrade;
    }
    
    public long getCreateTimestampMs() {
        return createTimestampMs;
    }
    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }
    
}
