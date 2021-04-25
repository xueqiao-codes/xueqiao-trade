package xueqiao.trade.hosting.arbitrage.trade.time;

import com.longsheng.xueqiao.contract.thriftapi.DateTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTime;
import com.longsheng.xueqiao.contract.thriftapi.TTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TimeSpanState;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * 交易时间区间列表
 * @author wangli
 *
 */
public class TradeTimeSpanList {
    private List<TradeTimeSpan>  mTradeTimeSpanList = new ArrayList<TradeTimeSpan>();
    
    /**
     *  通过商品交易时间构造
     */
    public boolean construct(SledTradeTime commodityTradeTime) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone(commodityTradeTime.getZoneId()));
        for (DateTimeSpan dateTimeSpan : commodityTradeTime.getDateTimeSpans()) {
            for (TTimeSpan timeSpan : dateTimeSpan.getTTimeSpan()) {
                if (timeSpan.getTimeSpanState() != TimeSpanState.T_OPEN
                        && timeSpan.getTimeSpanState() != TimeSpanState.T_PLUS_ONE_OPEN) {
                    continue;
                }
                
                String[] timeSplits = StringUtils.split(timeSpan.getTimespan(), "-");
                if (timeSplits == null || timeSplits.length != 2) {
                    AppLog.e("failed to parse timeSpan=" + timeSpan.getTimespan());
                    return false;
                }
                
                TradeTimeSpan tradeTimeSpan = new TradeTimeSpan();
                try {
                    tradeTimeSpan.setStartTimestampMs(
                            dateTimeFormat.parse(dateTimeSpan.getDate() + " " + timeSplits[0]).getTime());
                    tradeTimeSpan.setEndTimestampMs(
                            dateTimeFormat.parse(dateTimeSpan.getDate() + " " + timeSplits[1]).getTime());
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                    return false;
                }
                
                if (tradeTimeSpan.getStartTimestampMs() > tradeTimeSpan.getEndTimestampMs()) {
                    AppLog.e("failed to order timeSpan, startTimestamMs > endTimestampMs, tradeTimeSpan="
                             + tradeTimeSpan);
                    return false;
                }
                
                if (!mTradeTimeSpanList.isEmpty()) {
                    TradeTimeSpan lastTradeTimeSpan = mTradeTimeSpanList.get(mTradeTimeSpanList.size() - 1);
                    if (lastTradeTimeSpan.getEndTimestampMs() > tradeTimeSpan.getStartTimestampMs()) {
                        AppLog.e("lastTimestampSpan's endTimestampMs > tradeTimeSpan's startTimestampMs"
                            + ", lastTradeTimeSpan=" + lastTradeTimeSpan
                            + ", tradeTimeSpan=" + tradeTimeSpan);
                        return false;
                    }
                    // 时间戳跳秒, 属于连续开盘
                    if (lastTradeTimeSpan.getEndTimestampMs() >= (tradeTimeSpan.getStartTimestampMs() - 1000)) {
                        lastTradeTimeSpan.setEndTimestampMs(tradeTimeSpan.getEndTimestampMs());
                        continue;
                    } 
                }
                
                mTradeTimeSpanList.add(tradeTimeSpan);
            }
        }
        
        return true;
    }
    
    public List<TradeTimeSpan> getTradeTimeSpanList() {
        return mTradeTimeSpanList;
    }
    
    public boolean isEmpty() {
        return mTradeTimeSpanList.isEmpty();
    }
    
    // 对交易时间段坐交集处理
    public TradeTimeSpanList merge(TradeTimeSpanList mergeSpanList) {
        int thisIndex = 0;
        int mergeSpanListIndex = 0;
        
        TradeTimeSpanList resultSpanList = new TradeTimeSpanList();
        while(thisIndex < mTradeTimeSpanList.size()
                && mergeSpanListIndex < mergeSpanList.getTradeTimeSpanList().size()) {
            TradeTimeSpan compThisSpan = mTradeTimeSpanList.get(thisIndex);
            TradeTimeSpan compMergeSpan = mergeSpanList.getTradeTimeSpanList().get(mergeSpanListIndex);
            
            TradeTimeSpan interSpan = compThisSpan.intersection(compMergeSpan);
            if (interSpan != null) {
                resultSpanList.getTradeTimeSpanList().add(interSpan);
            }
            
            if (compThisSpan.getEndTimestampMs() < compMergeSpan.getEndTimestampMs()) {
                thisIndex += 1;
            } else if (compThisSpan.getEndTimestampMs() > compMergeSpan.getEndTimestampMs()) {
                mergeSpanListIndex += 1;
            } else {
                thisIndex += 1;
                mergeSpanListIndex += 1;
            }
        }
        
        return resultSpanList;
    }

    /**
     *  调整交易时间段
     */
    public void ajustTimeSpan(long ajustStartTimestampMs, long ajustEndTimestampMs) {
        List<TradeTimeSpan> resultList = new ArrayList<>();

        for (TradeTimeSpan timeSpan : mTradeTimeSpanList) {

            timeSpan.setStartTimestampMs(timeSpan.getStartTimestampMs() + ajustStartTimestampMs);
            timeSpan.setEndTimestampMs(timeSpan.getEndTimestampMs() + ajustEndTimestampMs);
            if (timeSpan.getStartTimestampMs() >= timeSpan.getEndTimestampMs()) {
                // 调整得没有交易时间了, 直接滤过
                continue;
            }

            TradeTimeSpan lastTradeTimeSpan = null;
            if (!resultList.isEmpty()) {
                lastTradeTimeSpan = resultList.get(resultList.size() - 1);
            }
            if (lastTradeTimeSpan != null && lastTradeTimeSpan.getEndTimestampMs() >= timeSpan.getStartTimestampMs()) {
                if (timeSpan.getEndTimestampMs() > lastTradeTimeSpan.getEndTimestampMs()) {
                    lastTradeTimeSpan.setEndTimestampMs(timeSpan.getEndTimestampMs());
                }
            } else {
                resultList.add(timeSpan);
            }

        }

        mTradeTimeSpanList =  resultList;


    }
    
    // 获取最近的交易时间段
    public TradeTimeSpan getNearestSpan(long timestampMs) {
        for (TradeTimeSpan timeSpan : mTradeTimeSpanList) {
            if (timestampMs > timeSpan.getEndTimestampMs()) {
                continue;
            }
            return timeSpan;
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("TradeTimeSpanList(");
        for (int index = 0; index < mTradeTimeSpanList.size(); ++index) {
            if (index > 0) {
                builder.append(",");
            }
            builder.append(mTradeTimeSpanList.get(index));
        }
        builder.append(")");
        return builder.toString();
    }
    
}
