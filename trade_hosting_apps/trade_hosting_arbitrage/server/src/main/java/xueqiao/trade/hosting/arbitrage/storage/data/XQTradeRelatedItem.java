package xueqiao.trade.hosting.arbitrage.storage.data;

import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;

public class XQTradeRelatedItem {
    private String orderId;
    private long tradeId;
    private long sledContractId;
    private long execOrderId;
    private long execTradeId;
    private long execTradeLegId;
    private HostingExecTradeDirection execTradeLegDirection;
    private int execTradeLegVolume;
    private double execTradeLegPrice;
    private int relatedTradeVolume;
    private long createTimestampMs;
    
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
    
    public long getSledContractId() {
        return sledContractId;
    }
    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
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
    
    public int getExecTradeLegVolume() {
        return execTradeLegVolume;
    }
    public void setExecTradeLegVolume(int execTradeLegVolume) {
        this.execTradeLegVolume = execTradeLegVolume;
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
    
    public long getCreateTimestampMs() {
        return createTimestampMs;
    }
    public void setCreateTimestampMs(long createTimestampMs) {
        this.createTimestampMs = createTimestampMs;
    }
    
    public static XQTradeRelatedItem from(HostingXQTrade xqTrade
            , HostingExecTradeLeg execTradeLeg
            , int relatedVolume) {
        XQTradeRelatedItem tradeRelatedItem = new XQTradeRelatedItem();
        tradeRelatedItem.setOrderId(xqTrade.getOrderId());
        tradeRelatedItem.setTradeId(xqTrade.getTradeId());
        tradeRelatedItem.setExecOrderId(execTradeLeg.getExecOrderId());
        tradeRelatedItem.setExecTradeId(execTradeLeg.getRelatedExecTradeId());
        tradeRelatedItem.setExecTradeLegId(execTradeLeg.getExecTradeLegId());
        tradeRelatedItem.setSledContractId(execTradeLeg.getLegContractSummary().getLegSledContractId());
        tradeRelatedItem.setExecTradeLegDirection(execTradeLeg.getTradeLegInfo().getLegUpsideTradeDirection());
        tradeRelatedItem.setExecTradeLegPrice(execTradeLeg.getTradeLegInfo().getLegTradePrice());
        tradeRelatedItem.setExecTradeLegVolume(execTradeLeg.getTradeLegInfo().getLegTradeVolume());
        tradeRelatedItem.setRelatedTradeVolume(relatedVolume);
        return tradeRelatedItem;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(128);
        resultBuilder.append("XQTradeRelatedItem(")
                     .append("orderId=").append(orderId)
                     .append(", tradeId=").append(tradeId)
                     .append(", sledContractId=").append(sledContractId)
                     .append(", execOrderId=").append(execOrderId)
                     .append(", execTradeId=").append(execTradeId)
                     .append(", execTradeLegId=").append(execTradeLegId)
                     .append(", execTradeLegDirection=").append(execTradeLegDirection)
                     .append(", execTradeLegVolume=").append(execTradeLegVolume)
                     .append(", execTradeLegPrice=").append(execTradeLegPrice)
                     .append(", relatedTradeVolume=").append(relatedTradeVolume)
                     .append(", createTimestampMs=").append(createTimestampMs)
                     .append(")");
        return resultBuilder.toString();
    }
}
