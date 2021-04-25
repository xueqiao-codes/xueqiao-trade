package xueqiao.trade.hosting.asset.struct;

import java.math.BigDecimal;

public class PreFund4Calculate {

    private long subAccountId;
    private String currency;
    private BigDecimal preFund;
    private BigDecimal depositAmount;
    private BigDecimal withdrawAmount;
    private BigDecimal creditAmount;
    private long refreshTimestamp;

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getRefreshTimestamp() {
        return refreshTimestamp;
    }

    public void setRefreshTimestamp(long refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

    public BigDecimal getPreFund() {
        return preFund;
    }

    public void setPreFund(BigDecimal preFund) {
        this.preFund = preFund;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }
}
