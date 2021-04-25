package xueqiao.trade.hosting.storage.apis.structs;

import java.util.Set;

public class QueryExecTradeOption {
    private int subUserId = -1;
    private long subAccountId = -1;
    
    private Set<Integer> batchSubUserIds;
    private Set<Long> batchExecOrderIds;
    private Set<Long> batchSledContractIds;
    private Set<Long> batchExecTradeIds;
    private Set<Long> batchSubAccountIds;
    
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
    
    public Set<Long> getBatchExecTradeIds() {
        return batchExecTradeIds;
    }
    public void setBatchExecTradeIds(Set<Long> batchExecTradeIds) {
        this.batchExecTradeIds = batchExecTradeIds;
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
