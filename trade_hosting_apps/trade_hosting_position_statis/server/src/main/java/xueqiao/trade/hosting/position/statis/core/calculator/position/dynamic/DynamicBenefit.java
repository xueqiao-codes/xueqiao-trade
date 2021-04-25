package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

public class DynamicBenefit {
    private String baseCurrency;
    private double baseCurrencyDynamicBenefit;
    private long updateTimeStamps;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double getBaseCurrencyDynamicBenefit() {
        return baseCurrencyDynamicBenefit;
    }

    public void setBaseCurrencyDynamicBenefit(double baseCurrencyDynamicBenefit) {
        this.baseCurrencyDynamicBenefit = baseCurrencyDynamicBenefit;
    }

    public long getUpdateTimeStamps() {
        return updateTimeStamps;
    }

    public void setUpdateTimeStamps(long updateTimeStamps) {
        this.updateTimeStamps = updateTimeStamps;
    }
}
