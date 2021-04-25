package xueqiao.trade.hosting.arbitrage.storage.data;

import xueqiao.trade.hosting.HostingExecOrder;

import java.util.HashMap;
import java.util.Map;

public class XQOrderRelatedExecOrderItem {
    private String orderId;
    private long execOrderId;
    private long sledContractId;
    private HostingExecOrder relatedExecOrder;
    private Map<String, String> extraParams;
    
    private long createTimestampMs;
    private long lastmodifyTimestampMs;
    
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
    
    public long getSledContractId() {
        return sledContractId;
    }
    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }
    
    public HostingExecOrder getRelatedExecOrder() {
        return relatedExecOrder;
    }
    public void setRelatedExecOrder(HostingExecOrder relatedExecOrder) {
        this.relatedExecOrder = relatedExecOrder;
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

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

}
