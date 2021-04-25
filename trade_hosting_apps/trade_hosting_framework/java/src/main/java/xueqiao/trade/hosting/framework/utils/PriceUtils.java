package xueqiao.trade.hosting.framework.utils;

public class PriceUtils {
    public static boolean isAppropriatePrice(double price) {
        return Double.isFinite(price);
    }
}
