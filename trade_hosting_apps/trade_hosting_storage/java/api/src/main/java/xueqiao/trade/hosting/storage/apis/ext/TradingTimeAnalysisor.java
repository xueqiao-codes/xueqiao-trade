package xueqiao.trade.hosting.storage.apis.ext;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import com.longsheng.xueqiao.contract.thriftapi.DateTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTime;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage;
import com.longsheng.xueqiao.contract.thriftapi.TTimeSpan;

import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

/**
 *  交易时间分析器
 * @author wangli
 *
 */
public class TradingTimeAnalysisor {
    private SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private IHostingContractApi mContractApi;
    private long mSledCommodityId;
    private long mProcessTimestampMs;
    private Date mProcessDate;
    
    // 分析产生
    private SledTradeTime mCommodityTradeTime;  // 统一合约系统给出的商品交易时间列表
    private TimeZone mCommodityTimeZone;         // 商品所在时区
    private String mProcessTradingDateTime;     // 处理交易(日期+时间)
    private String mProcessTradingDate;          // 处理交易日期
    private String mProcessTradingTime;          // 处理交易时间
    private LocalTime mProcessTradingTimeLT;
    
    private int mMatchDateSpanIndex = -1;   // 交易日中匹配的索引
    private DateTimeSpan mMatchDateSpan = null; // 交易日匹配的结构
    
    private int mMatchTimeSpanIndex = -1;    // 交易时间匹配的索引  
    private TTimeSpan mMatchTimeSpan = null; // 交易时间匹配的结构
    private boolean mIsMatchTimeSpanLast = false; // 是不是一天的最后一段
    private String mMatchTimeSpanStart;
    private LocalTime mMatchTimeSpanStartLt = null;
    private String mMatchTimeSpanEnd;
    private LocalTime mMatchTimeSpanEndLt = null;
    
    private boolean mIsAnalysisFinished = false;
    
    public TradingTimeAnalysisor(IHostingContractApi contractApi) {
        this.mContractApi = contractApi;
    }
    
    public void setCommodityId(long sledCommodityId) {
        this.mSledCommodityId = sledCommodityId;
    }
    
    public void setProcessTimestampMs(long processTimestampMs) {
        this.mProcessTimestampMs = processTimestampMs;
        this.mProcessDate = new Date(mProcessTimestampMs);
    }
    
    public SledTradeTime getCommodityTradeTime() {
        return mCommodityTradeTime;
    }
    
    public TimeZone getCommodityTimeZone() {
        return mCommodityTimeZone;
    }
    
    public String getProcessTradingDateTime() {
        return mProcessTradingDateTime;
    }
    
    public String getProcessTradingDate() {
        return mProcessTradingDate;
    }
    
    public String getProcessTradingTime() {
        return mProcessTradingTime;
    }
    
    public LocalTime getProcessTradingTimeLT() {
        return mProcessTradingTimeLT;
    }
    
    public int getMatchDateSpanIndex() {
        return mMatchDateSpanIndex;
    }
    
    public DateTimeSpan getMatchDateSpan() {
        return mMatchDateSpan;
    }
    
    public int getMatchTimeSpanIndex() {
        return mMatchTimeSpanIndex;
    }
    
    public TTimeSpan getMatchTimeSpan() {
        return mMatchTimeSpan;
    }
    
    public boolean isMatchTimeSpanLast() {
        return mIsMatchTimeSpanLast;
    }
    
    public String getMatchTimeSpanStart() {
        return mMatchTimeSpanStart;
    }
    
    public LocalTime getMatchTimeSpanStartLt() {
        return mMatchTimeSpanStartLt;
    }
    
    public String getMatchTimeSpanEnd() {
        return mMatchTimeSpanEnd;
    }
    
    public LocalTime getMatchTimeSpanEndLt() {
        return mMatchTimeSpanEndLt;
    }
    
    public boolean isAnalysisFinished() {
        return mIsAnalysisFinished;
    }
    
    // 从合约系统中拉取交易时间
    private boolean chooseTradeTime() throws TException {
        ReqSledTradeTimeOption option = new ReqSledTradeTimeOption();
        option.setSledCommodityIds(Arrays.asList((int)mSledCommodityId));
        SledTradeTimePage resultPage = mContractApi.getContractOnlineStub().reqSledTradeTime(option, 0, 1);
        if (resultPage.getPageSize() <= 0) {
            return false;
        }
        SledTradeTime commodityTradeTime = resultPage.getPage().get(0);
        if (commodityTradeTime.getDateTimeSpansSize() <= 0) {
            return false;
        }
        mCommodityTradeTime = commodityTradeTime;
        return true;
    }
    
    // 匹配交易日期
    private boolean matchTradingDateSpan()  {
        for (int matchIndex = 0; matchIndex < mCommodityTradeTime.getDateTimeSpansSize(); ++matchIndex) {
            DateTimeSpan matchTradingDateSpan = mCommodityTradeTime.getDateTimeSpans().get(matchIndex);
            if (matchTradingDateSpan.getDate().equals(mProcessTradingDate)) {
                mMatchDateSpanIndex = matchIndex;
                mMatchDateSpan = matchTradingDateSpan;
                return true;
            }
        }
        return false;
    }
    
    // 匹配交易时间
    private boolean matchTradingTimeSpan() {
        for (int matchIndex = 0; matchIndex < mMatchDateSpan.getTTimeSpanSize(); ++matchIndex) {
            TTimeSpan timeSpan = mMatchDateSpan.getTTimeSpan().get(matchIndex);
            String[] timeSplits = StringUtils.split(timeSpan.getTimespan(), "-");
            if (timeSplits == null || timeSplits.length != 2) {
                return false;
            }
            
            LocalTime startLT = null;
            LocalTime endLT = null;
            try {
                startLT = LocalTime.parse(timeSplits[0], DateTimeFormatter.ofPattern("HH:mm:ss"));
                endLT = LocalTime.parse(timeSplits[1], DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (Throwable e) {
                AppLog.e("failed to parse timeSpan=" + timeSpan.getTimespan());
                return false;
            }
            if (startLT.toSecondOfDay() > endLT.toSecondOfDay()) {
                return false;
            }
            
            if (startLT.toSecondOfDay() <= mProcessTradingTimeLT.toSecondOfDay()
                    && mProcessTradingTimeLT.toSecondOfDay() <= endLT.toSecondOfDay()) {
                if (matchIndex == mMatchDateSpan.getTTimeSpanSize() - 1) {
                    mIsMatchTimeSpanLast = true;
                }
                
                mMatchTimeSpanIndex = matchIndex;
                mMatchTimeSpanStart = timeSplits[0];
                mMatchTimeSpanStartLt = startLT;
                mMatchTimeSpanEnd = timeSplits[1];
                mMatchTimeSpanEndLt = endLT;
                mMatchTimeSpan = timeSpan;
                        
                return true;
            }
        }
        return false;
    }
    
    // 分析匹配
    public boolean analysis() throws Exception {
        if (!chooseTradeTime()) {
            return false;
        }
        
        mCommodityTimeZone = TimeZone.getTimeZone(mCommodityTradeTime.getZoneId());
        mDateTimeFormat.setTimeZone(mCommodityTimeZone);
        mProcessTradingDateTime = mDateTimeFormat.format(mProcessDate);
        mProcessTradingDate = StringUtils.substring(mProcessTradingDateTime, 0, 10);
        mProcessTradingTime = StringUtils.substring(mProcessTradingDateTime, 11);
        mProcessTradingTimeLT = LocalTime.parse(mProcessTradingTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        if (!matchTradingDateSpan()) {
            return false;
        }
        if (!matchTradingTimeSpan()) {
            return false;
        }
        
        mIsAnalysisFinished = true;
        return true;
    }
    
    private static TSimpleJSONProtocol.Factory LOG_PROTOCOL_FACTORY = new TSimpleJSONProtocol.Factory();
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(256);
        builder.append("TradingTimeAnalysisor{")
               .append("mSledCommodityId=").append(mSledCommodityId)
               .append(", mProcessTimestampMs=").append(mProcessTimestampMs)
               .append(", mProcessDate=").append(mProcessDate)
               .append(", mCommodityTradeTime=").append(StringFactory.newUtf8String(
                       ProtocolUtil.serialize2Bytes(LOG_PROTOCOL_FACTORY, mCommodityTradeTime)))
               .append(", mCommodityTimeZone=").append(mCommodityTimeZone)
               .append(", mProcessTradingDateTime=").append(mProcessTradingDateTime)
               .append(", mProcessTradingDate=").append(mProcessTradingDate)
               .append(", mProcessTradingTime=").append(mProcessTradingTime)
               .append(", mProcessTradingTimeLT=").append(mProcessTradingTimeLT)
               .append(", mMatchDateSpanIndex=").append(mMatchDateSpanIndex)
               .append(", mMatchDateSpan=").append(StringFactory.newUtf8String(
                       ProtocolUtil.serialize2Bytes(LOG_PROTOCOL_FACTORY, mMatchDateSpan)))
               .append(", mMatchTimeSpanIndex=").append(mMatchTimeSpanIndex)
               .append(", mMatchTimeSpan=").append(mMatchTimeSpan)
               .append(", isMatchTimeSpanLast=").append(mIsMatchTimeSpanLast)
               .append(", mMatchTimeSpanStart=").append(mMatchTimeSpanStart)
               .append(", mMatchTimeSpanStartLt=").append(mMatchTimeSpanStartLt)
               .append(", mMatchTimeSpanEnd=").append(mMatchTimeSpanEnd)
               .append(", mMatchTimeSpanEndLt=").append(mMatchTimeSpanEndLt)
               .append(", analysisFinished=").append(mIsAnalysisFinished)
               .append("}");
        return builder.toString();
    }
    
}
