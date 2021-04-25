package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideES9CommissionRate {
    private ESAPICalculateMode calculateMode;
    private String currencyGroupNo;
    private String currencyNo;
    private double openCloseFee;
    private double closeTodayFee;

    public ESAPICalculateMode getCalculateMode() {
        return calculateMode;
    }

    public void setCalculateMode(ESAPICalculateMode calculateMode) {
        this.calculateMode = calculateMode;
    }

    public String getCurrencyGroupNo() {
        return currencyGroupNo;
    }

    public void setCurrencyGroupNo(String currencyGroupNo) {
        this.currencyGroupNo = currencyGroupNo;
    }

    public String getCurrencyNo() {
        return currencyNo;
    }

    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    public double getOpenCloseFee() {
        return openCloseFee;
    }

    public void setOpenCloseFee(double openCloseFee) {
        this.openCloseFee = openCloseFee;
    }

    public double getCloseTodayFee() {
        return closeTodayFee;
    }

    public void setCloseTodayFee(double closeTodayFee) {
        this.closeTodayFee = closeTodayFee;
    }
}
