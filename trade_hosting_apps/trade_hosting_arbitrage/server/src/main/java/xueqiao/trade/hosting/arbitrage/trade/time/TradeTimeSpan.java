package xueqiao.trade.hosting.arbitrage.trade.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 交易时间区间
 * @author wangli
 */
public class TradeTimeSpan {
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private long mStartTimestampMs;
    private long mEndTimestampMs;
    
    public TradeTimeSpan() {
    }
    
    public TradeTimeSpan(long startTimestampMs, long endTimestampMs) {
        this.mStartTimestampMs = startTimestampMs;
        this.mEndTimestampMs = endTimestampMs;
    }
    
    public long getStartTimestampMs() {
        return mStartTimestampMs;
    }
    public TradeTimeSpan setStartTimestampMs(long startTimestampMs) {
        this.mStartTimestampMs = startTimestampMs;
        return this;
    }
    
    public long getEndTimestampMs() {
        return mEndTimestampMs;
    }
    public TradeTimeSpan setEndTimestampMs(long endTimestampMs) {
        this.mEndTimestampMs = endTimestampMs;
        return this;
    }
    
    public TradeTimeSpan intersection(TradeTimeSpan span) {
        TradeTimeSpan smallerStartSpan = this;
        TradeTimeSpan biggerStartSpan = span;
        if (this.getStartTimestampMs() > span.getStartTimestampMs()) {
            smallerStartSpan = span;
            biggerStartSpan = this;
        }
        
        if (biggerStartSpan.getStartTimestampMs() > smallerStartSpan.getEndTimestampMs()) {
            // 无任何区间重叠
            return null;
        }
        
        TradeTimeSpan resultSpan = new TradeTimeSpan();
        resultSpan.setStartTimestampMs(biggerStartSpan.getStartTimestampMs());
        resultSpan.setEndTimestampMs(Math.min(smallerStartSpan.getEndTimestampMs(), biggerStartSpan.getEndTimestampMs()));
        return resultSpan;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(48);
        builder.append("TradeTimeSpan[").append(TIME_FORMATTER.format(new Date(mStartTimestampMs)))
               .append(",").append(TIME_FORMATTER.format(new Date(mEndTimestampMs)))
               .append("]");
        return builder.toString();
    }
}
