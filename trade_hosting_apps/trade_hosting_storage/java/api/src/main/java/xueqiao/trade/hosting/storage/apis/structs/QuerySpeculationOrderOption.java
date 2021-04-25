package xueqiao.trade.hosting.storage.apis.structs;

import java.util.Set;

public class QuerySpeculationOrderOption {
    private String speculationOrderId;
    private int subUserId = 0;
    
    private Set<Short> inStates; 
    private Set<Short> notInStates;
    
    private Set<Long> batchSledContractIds;
    private Set<String> batchSpeculationOrderIds;
    
    public int getSubUserId() {
        return subUserId;
    }
    public void setSubUserId(int subUserId) {
        this.subUserId = subUserId;
    }
    
    public String getSpeculationOrderId() {
        return speculationOrderId;
    }
    public void setSpeculationOrderId(String speculationOrderId) {
        this.speculationOrderId = speculationOrderId;
    }
    
    public Set<Short> getInStates() {
        return inStates;
    }
    public void setInStates(Set<Short> inStates) {
        this.inStates = inStates;
    }
    
    public Set<Short> getNotInStates() {
        return notInStates;
    }
    public void setNotInStates(Set<Short> notInStates) {
        this.notInStates = notInStates;
    }
    
    public Set<Long> getBatchSledContractIds() {
        return batchSledContractIds;
    }
    public void setBatchSledContractIds(Set<Long> batchSledContractIds) {
        this.batchSledContractIds = batchSledContractIds;
    }
    
    public Set<String> getBatchSpeculationOrderIds() {
        return batchSpeculationOrderIds;
    }
    public void setBatchSpeculationOrderIds(Set<String> batchSpeculationOrderIds) {
        this.batchSpeculationOrderIds = batchSpeculationOrderIds;
    }
   
    
}
