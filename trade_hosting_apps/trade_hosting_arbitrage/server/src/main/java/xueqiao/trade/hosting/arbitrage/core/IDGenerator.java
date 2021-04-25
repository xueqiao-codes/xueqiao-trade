package xueqiao.trade.hosting.arbitrage.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

public class IDGenerator {
    private static IDGenerator sInstance;
    public static void Initialize() throws ErrorInfo {
        sInstance = new IDGenerator();
    }
    
    public static IDGenerator Global() {
        return sInstance;
    }
    
    private IDGenerator() throws ErrorInfo{
        mTradeIdMaker = IdMakerFactory.getInstance().getIdMaker(206);
        if (mTradeIdMaker == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "get trade id maker failed!");
        }
    }
    
    private IdMaker mTradeIdMaker;
    public long createTradeId() throws ErrorInfo {
        try {
            return mTradeIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "create trade id failed");
        }
    }
    
    // 内部用于创建自定义的生成ID，复用成交ID
    public long generateUniqueIdForOrderId() throws ErrorInfo {
        return createTradeId();
    }
    
}
