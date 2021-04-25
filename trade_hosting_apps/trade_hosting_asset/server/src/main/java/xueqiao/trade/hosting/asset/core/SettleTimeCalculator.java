package xueqiao.trade.hosting.asset.core;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.thriftapi.DateTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TimeSpanState;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.ProtocolUtil;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SettleTimeCalculator {

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

    public SettleTimeCalculator(TradingTimeAnalysisor analysisor) {
        Preconditions.checkArgument(analysisor != null && analysisor.isAnalysisFinished());
        this.mAnalysisor = analysisor;
        mIsMarketClosed = this.mAnalysisor.getMatchTimeSpan().getTimeSpanState().equals(TimeSpanState.CLOSED);
    }

    public boolean isMarketClosed() {
        return this.mIsMarketClosed;
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
        return getTradingEndDate().getTime() + 5 * 60 * 1000;
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
            int nextCloseTimeSpanIndex = 0;
            AppLog.w("mAnalysisor.getMatchTimeSpanEnd: "+ mAnalysisor.getMatchTimeSpanEnd());
            if (mIsMarketClosed && mAnalysisor.getMatchTimeSpanEnd().equals("23:59:59")){
                nextCloseTimeSpanIndex = 1;
            }
            AppLog.w("check next day close time span from index: "+ nextCloseTimeSpanIndex);
            for (; nextCloseTimeSpanIndex < dateSpan.getTTimeSpanSize(); ++nextCloseTimeSpanIndex) {
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
        builder.append("SettleTimeCalculator{ ")
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
