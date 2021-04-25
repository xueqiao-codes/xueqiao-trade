package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideCTPInstrumentMarginRate {
    private double longMarginRatioByMoney;
    private double longMarginRatioByVolume;
    private double shortMarginRatioByMoney;
    private double shortMarginRatioByVolume;
    /*
    * 是否相对交易所保证金收取
    * 若为true,上面保证金的值则为相对值，计算方式：保证金=交易所保证金的数值 + 相对收取的数值
    * 若为false，上面保证金的值则为绝对值。
    * */
    private boolean isRelative;

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

    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(boolean relative) {
        isRelative = relative;
    }
}
