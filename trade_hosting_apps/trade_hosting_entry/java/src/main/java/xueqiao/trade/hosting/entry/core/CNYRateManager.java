package xueqiao.trade.hosting.entry.core;

import xueqiao.trade.hosting.utils.currency.RateManager;

public class CNYRateManager extends RateManager  {
    private static CNYRateManager sInstance;
    public static CNYRateManager Global() {
        if (sInstance == null) {
            synchronized (CNYRateManager.class) {
                if (sInstance == null) {
                    sInstance = new CNYRateManager();
                }
            }
        }
        return sInstance;
    }

    private CNYRateManager() {
        super("CNY");
    }
}
