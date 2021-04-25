package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideCTPCommissionRate {
    private double openRatioByMoney;
    private double openRatioByVolume;
    private double closeRatioByMoney;
    private double closeRatioByVolume;
    private double closeTodayRatioByMoney;
    private double closeTodayRatioByVolume;

    public double getOpenRatioByMoney() {
        return openRatioByMoney;
    }

    public void setOpenRatioByMoney(double openRatioByMoney) {
        this.openRatioByMoney = openRatioByMoney;
    }

    public double getOpenRatioByVolume() {
        return openRatioByVolume;
    }

    public void setOpenRatioByVolume(double openRatioByVolume) {
        this.openRatioByVolume = openRatioByVolume;
    }

    public double getCloseRatioByMoney() {
        return closeRatioByMoney;
    }

    public void setCloseRatioByMoney(double closeRatioByMoney) {
        this.closeRatioByMoney = closeRatioByMoney;
    }

    public double getCloseRatioByVolume() {
        return closeRatioByVolume;
    }

    public void setCloseRatioByVolume(double closeRatioByVolume) {
        this.closeRatioByVolume = closeRatioByVolume;
    }

    public double getCloseTodayRatioByMoney() {
        return closeTodayRatioByMoney;
    }

    public void setCloseTodayRatioByMoney(double closeTodayRatioByMoney) {
        this.closeTodayRatioByMoney = closeTodayRatioByMoney;
    }

    public double getCloseTodayRatioByVolume() {
        return closeTodayRatioByVolume;
    }

    public void setCloseTodayRatioByVolume(double closeTodayRatioByVolume) {
        this.closeTodayRatioByVolume = closeTodayRatioByVolume;
    }
}
