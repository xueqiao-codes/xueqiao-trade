package xueqiao.trade.hosting.position.adjust.api;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.thriftapi.SettlementTimeRelateSledReqTime;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;

import java.sql.Date;
import java.util.Calendar;

public class ReqSettlementTimeRelateSledReqTime {

    public SettlementTimeRelateSledReqTime req(long tradeAccountId, String settlementDate) throws ErrorInfo {
        IHostingTradeAccountApi api = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        BrokerAccessEntry entry = api.getBrokerAccessEntry(tradeAccountId);
        BrokerPlatform techPlatform = entry.getPlatform();
        // 建议时间区间
        Date date = Date.valueOf(settlementDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int beginDay = 0;
        int endDay = 0;
        int beginHour = 0;
        int endHour = 0;
        long hourMs = 60 * 60 * 1000;
        if (BrokerPlatform.CTP.equals(techPlatform)) {
            // 上日21点-16点
            beginHour = 21 - 24;
            endHour = 15;
            if (dayOfWeek == 2) {
                // 星期一
                beginDay = -2;
                endDay = 0;
            }
        } else if (BrokerPlatform.ESUNNY.equals(techPlatform)) {
            // 6点-次日6点
            beginHour = 6;
            endHour = 24 + 6;
            if (dayOfWeek == 2) {
                // 星期一
                beginDay = -3;
                endDay = -1;
            }
        }

        SettlementTimeRelateSledReqTime timeRelateSledReqTime = new SettlementTimeRelateSledReqTime();
        timeRelateSledReqTime.setSettlementDate(settlementDate);
        timeRelateSledReqTime.setTradeAccountId(tradeAccountId);
        timeRelateSledReqTime.setStartTimestampMs(date.getTime() + beginDay * 24 * hourMs + beginHour * hourMs);
        timeRelateSledReqTime.setEndTimestampMs(date.getTime() + endDay * 24 + endHour * hourMs);
        return timeRelateSledReqTime;
    }
}
