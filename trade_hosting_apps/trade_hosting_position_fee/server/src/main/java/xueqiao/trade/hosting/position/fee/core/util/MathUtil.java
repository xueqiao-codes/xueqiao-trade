package xueqiao.trade.hosting.position.fee.core.util;

import java.math.BigDecimal;

public class MathUtil {

    public static double add(double num1, double num2) {
        BigDecimal num = new BigDecimal(String.valueOf(num1));
        return num.add(new BigDecimal(String.valueOf(num2))).doubleValue();
    }

    public static double subtract(double num1, double num2) {
        BigDecimal num = new BigDecimal(String.valueOf(num1));
        return num.subtract(new BigDecimal(num2)).doubleValue();
    }
}
