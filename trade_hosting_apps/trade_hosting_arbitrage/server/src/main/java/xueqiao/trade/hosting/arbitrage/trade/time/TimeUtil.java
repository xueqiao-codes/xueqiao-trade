package xueqiao.trade.hosting.arbitrage.trade.time;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
    
    public static long fromNow1Minute() {
        return fromNowMs(1, TimeUnit.MINUTES);
    }
    
    public static long fromNow24H() {
        return fromNowMs(24, TimeUnit.HOURS);
    }
    
    public static long fromNowMs(long period, TimeUnit tu) {
        return System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(period, tu);
    }
    
    public static long fromTimePointMs(long timestampMs, long period, TimeUnit tu) {
        return timestampMs + TimeUnit.MILLISECONDS.convert(period, tu);
    }
}
