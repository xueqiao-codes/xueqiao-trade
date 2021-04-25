package xueqiao.trade.hosting.asset.struct;

public class FundCalculateData {

    private long subAccountId;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }
}
