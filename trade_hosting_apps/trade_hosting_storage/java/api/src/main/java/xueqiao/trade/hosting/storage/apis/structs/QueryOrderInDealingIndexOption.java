package xueqiao.trade.hosting.storage.apis.structs;

import java.util.Set;

import xueqiao.trade.hosting.HostingExecOrderDealID;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;

public class QueryOrderInDealingIndexOption {
    private long execOrderId = -1;
    private int subUserId= -1;
    private long subAccountId = -1;
    
    private HostingExecOrderTradeAccountSummary accountSummary;
    private HostingExecOrderRef orderRef;
    private HostingExecOrderDealID dealID;
    
    private Set<HostingExecOrderStateValue> inStates;
    private Set<HostingExecOrderStateValue> notInState;
    
    private Set<Long> batchExecOrderIds;
    private Set<Long> batchSledContractIds;
    private Set<Integer> batchSubUserIds;
    private Set<Long> batchSubAccountIds;
    
    private Boolean tradeListCompleted = null;
    private long beforeTtlTimestampMs = -1;
    
    public long getExecOrderId() {
        return execOrderId;
    }
    public void setExecOrderId(long execOrderId) {
        this.execOrderId = execOrderId;
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
    
    public HostingExecOrderTradeAccountSummary getAccountSummary() {
        return accountSummary;
    }
    public void setAccountSummary(HostingExecOrderTradeAccountSummary accountSummary) {
        this.accountSummary = accountSummary;
    }
    
    public HostingExecOrderRef getOrderRef() {
        return orderRef;
    }
    public void setOrderRef(HostingExecOrderRef orderRef) {
        this.orderRef = orderRef;
    }
    
    public HostingExecOrderDealID getDealID() {
        return dealID;
    }
    public void setDealID(HostingExecOrderDealID dealID) {
        this.dealID = dealID;
    }
    
    public Set<HostingExecOrderStateValue> getInStates() {
        return inStates;
    }
    public void setInStates(Set<HostingExecOrderStateValue> inStates) {
        this.inStates = inStates;
    }
    
    public Set<HostingExecOrderStateValue> getNotInState() {
        return notInState;
    }
    public void setNotInState(Set<HostingExecOrderStateValue> notInState) {
        this.notInState = notInState;
    }
    
	public Boolean getTradeListCompleted() {
		return tradeListCompleted;
	}
	public void setTradeListCompleted(Boolean tradeListCompleted) {
		this.tradeListCompleted = tradeListCompleted;
	}
	
    public Set<Long> getBatchExecOrderIds() {
        return batchExecOrderIds;
    }
    public void setBatchExecOrderIds(Set<Long> batchExecOrderIds) {
        this.batchExecOrderIds = batchExecOrderIds;
    }
    
    public Set<Long> getBatchSledContractIds() {
        return batchSledContractIds;
    }
    public void setBatchSledContractIds(Set<Long> batchSledContractIds) {
        this.batchSledContractIds = batchSledContractIds;
    }
    
    public long getBeforeTtlTimestampMs() {
        return beforeTtlTimestampMs;
    }
    public void setBeforeTtlTimestampMs(long beforeTtlTimestampMs) {
        this.beforeTtlTimestampMs = beforeTtlTimestampMs;
    }
    
    public Set<Integer> getBatchSubUserIds() {
        return batchSubUserIds;
    }
    public void setBatchSubUserIds(Set<Integer> batchSubUserIds) {
        this.batchSubUserIds = batchSubUserIds;
    }
    
    public Set<Long> getBatchSubAccountIds() {
        return batchSubAccountIds;
    }
    public void setBatchSubAccountIds(Set<Long> batchSubAccountIds) {
        this.batchSubAccountIds = batchSubAccountIds;
    }
    
}
