package xueqiao.trade.hosting.storage.apis.ext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.soldier.base.StringFactory;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.thriftapi.DateTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TimeSpanState;

/**
 *  GFD订单时间计算器
 * @author wangli
 */
public class GFDOrderCalculator {
    
    private static final int CLOSE_ALLOW_SECONDS = 60;  // 闭市和开始前后一分钟允许交易
    
    private TradingTimeAnalysisor mAnalysisor;
    private boolean mIsMarketClosed = false;   // 闭市
    
    private String mTradingEndDateTime;
    
    private int mNextClosedDateSpanIndex = -1;
    private DateTimeSpan mNextClosedDateSpan;
    private int mNextClosedTimeSpanIndex = -1;
    private TTimeSpan mNextClosedTimeSpan;
    private String mNextClosedStartTime;
    private String mNextClosedEndTime;
    private LocalTime mNextClosedStartTimeLt;
    private LocalTime mNextClosedEndTimeLt;
    
    public GFDOrderCalculator(TradingTimeAnalysisor analysisor) {
        Preconditions.checkArgument(analysisor != null && analysisor.isAnalysisFinished());
        this.mAnalysisor = analysisor;
    }
    
    public boolean isMarketClosed() {
        return mIsMarketClosed;
    }
    
    public String getTradingEndDateTime() {
        return mTradingEndDateTime;
    }
    
    public Date getTradingEndDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(mAnalysisor.getCommodityTimeZone());
        return format.parse(mTradingEndDateTime);
    }
    
    // 在时间结束的基础上，再加上五分钟
    public long getTTLTimestampMs() throws ParseException {
        return getTradingEndDate().getTime() + 5*60*1000;
    }
    
    private boolean findNextClosedTimeSpan() {
        // 匹配当天
        for (int nextCloseTimeSpanIndex = mAnalysisor.getMatchTimeSpanIndex() + 1
                ; nextCloseTimeSpanIndex < mAnalysisor.getMatchDateSpan().getTTimeSpanSize()
                ; ++nextCloseTimeSpanIndex) {
            TTimeSpan timeSpan = mAnalysisor.getMatchDateSpan().getTTimeSpan().get(nextCloseTimeSpanIndex);
            if (timeSpan.getTimeSpanState() == TimeSpanState.CLOSED) {
                mNextClosedDateSpanIndex = mAnalysisor.getMatchDateSpanIndex();
                mNextClosedDateSpan = mAnalysisor.getMatchDateSpan();
                mNextClosedTimeSpanIndex = nextCloseTimeSpanIndex;
                mNextClosedTimeSpan = timeSpan;
                return true;
            }
        }
        
        // 向后匹配
        for (int nextClosedDateSpanIndex = mAnalysisor.getMatchDateSpanIndex() + 1
                ; nextClosedDateSpanIndex < mAnalysisor.getCommodityTradeTime().getDateTimeSpansSize()
                ; ++nextClosedDateSpanIndex) {
            DateTimeSpan dateSpan = mAnalysisor.getCommodityTradeTime().getDateTimeSpans().get(nextClosedDateSpanIndex);
            for (int nextCloseTimeSpanIndex = 0; nextCloseTimeSpanIndex < dateSpan.getTTimeSpanSize(); ++nextCloseTimeSpanIndex) {
                TTimeSpan timeSpan = dateSpan.getTTimeSpan().get(nextCloseTimeSpanIndex);
                if (timeSpan.getTimeSpanState() == TimeSpanState.CLOSED) {
                    mNextClosedDateSpanIndex = nextClosedDateSpanIndex;
                    mNextClosedDateSpan = dateSpan;
                    mNextClosedTimeSpanIndex = nextCloseTimeSpanIndex;
                    mNextClosedTimeSpan = timeSpan;
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean calculate() {
        TTimeSpan matchTimeSpan = mAnalysisor.getMatchTimeSpan();
        if (matchTimeSpan.getTimeSpanState() == TimeSpanState.CLOSED) {
            if (mAnalysisor.getProcessTradingTimeLT().toSecondOfDay()
                    <= mAnalysisor.getMatchTimeSpanStartLt().toSecondOfDay() + CLOSE_ALLOW_SECONDS) {
                mTradingEndDateTime = mAnalysisor.getMatchDateSpan().getDate() + " " + mAnalysisor.getMatchTimeSpanStart();
                return true;
            }
            
            if (mAnalysisor.isMatchTimeSpanLast()) {
                int nextDateSpanIndex = mAnalysisor.getMatchDateSpanIndex() + 1;
                if (nextDateSpanIndex >= mAnalysisor.getCommodityTradeTime().getDateTimeSpansSize()) {
                    return false;
                }
                DateTimeSpan nextDateSpan 
                    = mAnalysisor.getCommodityTradeTime().getDateTimeSpans().get(nextDateSpanIndex);
                if (nextDateSpan.getTTimeSpanSize() <= 0) {
                    return false;
                }
                TTimeSpan nextTimeSpan = nextDateSpan.getTTimeSpan().get(0);
                if (nextTimeSpan.getTimeSpanState() == TimeSpanState.CLOSED) {
                    mIsMarketClosed = true;
                    return true;
                }
            } 
            
            if (mAnalysisor.getProcessTradingTimeLT().toSecondOfDay() 
                    < (mAnalysisor.getMatchTimeSpanEndLt().toSecondOfDay() - CLOSE_ALLOW_SECONDS)) {
                mIsMarketClosed = true;
                return true;
            }
        }
        
        // 寻找下一个闭市开始时间，作为有效期的最后时间
        if (!findNextClosedTimeSpan()) {
            return false;
        }
        
        String[] nextClosedTimeSplits = StringUtils.split(mNextClosedTimeSpan.getTimespan(), "-");
        if (nextClosedTimeSplits == null || nextClosedTimeSplits.length != 2) {
            return false;
        }
        
        mNextClosedStartTime = nextClosedTimeSplits[0];
        mNextClosedEndTime = nextClosedTimeSplits[1];
        
        mNextClosedStartTimeLt = LocalTime.parse(mNextClosedStartTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        mNextClosedEndTimeLt = LocalTime.parse(mNextClosedEndTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (mNextClosedStartTimeLt.toSecondOfDay() > mNextClosedEndTimeLt.toSecondOfDay()) {
            return false;
        }
        
        mTradingEndDateTime = mNextClosedDateSpan.getDate() + " " + mNextClosedStartTime;
        return true;
    }
    
    private static TSimpleJSONProtocol.Factory LOG_PROTOCOL_FACTORY = new TSimpleJSONProtocol.Factory();
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GFDOrderCalculator{ ")
               .append("mAnalysisor=").append(mAnalysisor)
               .append(", mIsMarketClosed=").append(mIsMarketClosed)
               .append(", mTradingEndDateTime=").append(mTradingEndDateTime)
               .append(", mNextClosedDateSpanIndex=").append(mNextClosedDateSpanIndex)
               .append(", mNextClosedDateSpan=").append(StringFactory.newUtf8String(
                       ProtocolUtil.serialize2Bytes(LOG_PROTOCOL_FACTORY, mNextClosedDateSpan)))
               .append(", mNextClosedTimeSpanIndex=").append(mNextClosedTimeSpanIndex)
               .append(", mNextClosedTimeSpan=").append(mNextClosedTimeSpan)
               .append(", mNextClosedStartTime=").append(mNextClosedStartTime)
               .append(", mNextClosedEndTime=").append(mNextClosedEndTime)
               .append(", mNextClosedStartTimeLt=").append(mNextClosedStartTimeLt)
               .append(", mNextClosedEndTimeLt=").append(mNextClosedEndTimeLt)
               .append(" }");
        return builder.toString();
    }
    
}
