package xueqiao.trade.hosting.position.fee.core.util;

import java.math.BigDecimal;

public class EsAPICombineModeCalculator {

    private double value;
    private double quota;
    private double percentage;

    public EsAPICombineModeCalculator(double value) {
        this.value = value;
        calculateQuota();
        calculatePercentage();
    }

    public double getQuota() {
        return quota;
    }

    public double getPercentage() {
        return percentage;
    }

    private void calculateQuota() {
        int i = (int) (value * 100);
        quota = i / 100.0;
    }

    private void calculatePercentage() {
        BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(value));
        BigDecimal bigDecimalQuota = new BigDecimal(String.valueOf(quota));
        BigDecimal ret = bigDecimalValue.subtract(bigDecimalQuota);
        ret = ret.multiply(new BigDecimal("10"));
        percentage = ret.doubleValue();
    }
}
