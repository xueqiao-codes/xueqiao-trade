package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideES9MarginRate {
    private ESAPICalculateMode calculateMode;
    private String currencyGroupNo;
    private String currencyNo;
    private double initialMargin;
    private double maintenanceMargin;
    private double sellInitialMargin;
    private double sellMaintenanceMargin;
    private double lockMargin;

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

    public double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public double getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(double maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    public double getSellInitialMargin() {
        return sellInitialMargin;
    }

    public void setSellInitialMargin(double sellInitialMargin) {
        this.sellInitialMargin = sellInitialMargin;
    }

    public double getSellMaintenanceMargin() {
        return sellMaintenanceMargin;
    }

    public void setSellMaintenanceMargin(double sellMaintenanceMargin) {
        this.sellMaintenanceMargin = sellMaintenanceMargin;
    }

    public double getLockMargin() {
        return lockMargin;
    }

    public void setLockMargin(double lockMargin) {
        this.lockMargin = lockMargin;
    }
}
