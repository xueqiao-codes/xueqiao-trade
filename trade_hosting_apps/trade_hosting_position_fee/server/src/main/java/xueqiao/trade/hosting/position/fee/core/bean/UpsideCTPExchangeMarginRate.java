package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideCTPExchangeMarginRate {
    private double longMarginRatioByMoney;
    private double longMarginRatioByVolume;
    private double shortMarginRatioByMoney;
    private double shortMarginRatioByVolume;

    public double getLongMarginRatioByMoney() {
        return longMarginRatioByMoney;
    }

    public void setLongMarginRatioByMoney(double longMarginRatioByMoney) {
        this.longMarginRatioByMoney = longMarginRatioByMoney;
    }

    public double getLongMarginRatioByVolume() {
        return longMarginRatioByVolume;
    }

    public void setLongMarginRatioByVolume(double longMarginRatioByVolume) {
        this.longMarginRatioByVolume = longMarginRatioByVolume;
    }

    public double getShortMarginRatioByMoney() {
        return shortMarginRatioByMoney;
    }

    public void setShortMarginRatioByMoney(double shortMarginRatioByMoney) {
        this.shortMarginRatioByMoney = shortMarginRatioByMoney;
    }

    public double getShortMarginRatioByVolume() {
        return shortMarginRatioByVolume;
    }

    public void setShortMarginRatioByVolume(double shortMarginRatioByVolume) {
        this.shortMarginRatioByVolume = shortMarginRatioByVolume;
    }
}
