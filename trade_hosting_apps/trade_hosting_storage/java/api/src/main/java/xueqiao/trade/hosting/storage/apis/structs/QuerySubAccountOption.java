package xueqiao.trade.hosting.storage.apis.structs;

public class QuerySubAccountOption {
    private long subAccountId = 0;
    private String subAccountNameWhole;
    private String subAccountNamePartical;
    
    public long getSubAccountId() {
        return subAccountId;
    }
    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }
    
    public String getSubAccountNamePartical() {
        return subAccountNamePartical;
    }
    public void setSubAccountNamePartical(String subAccountNamePartical) {
        this.subAccountNamePartical = subAccountNamePartical;
    }
    
    public String getSubAccountNameWhole() {
        return subAccountNameWhole;
    }
    public void setSubAccountNameWhole(String subAccountNameWhole) {
        this.subAccountNameWhole = subAccountNameWhole;
    }
    
    
}
