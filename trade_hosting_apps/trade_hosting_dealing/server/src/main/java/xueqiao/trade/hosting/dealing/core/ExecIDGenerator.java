package xueqiao.trade.hosting.dealing.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;


public class ExecIDGenerator {

    private static ExecIDGenerator sInstance;
    public static void Initialize() throws Exception {
        sInstance = new ExecIDGenerator();
    }

    public static ExecIDGenerator Global() {
        return sInstance;
    }


    private IdMaker mExecOrderIdMaker;
    private IdMaker mExecTradeIdMaker;
    private IdMaker mExecTradeLegIdMaker;

    private ExecIDGenerator() throws Exception {
        mExecOrderIdMaker = IdMakerFactory.getInstance().getIdMaker(202);
        if (mExecOrderIdMaker == null) {
            throw new Exception("initialize execOrderId maker failed");
        }
        mExecTradeIdMaker = IdMakerFactory.getInstance().getIdMaker(203);
        if (mExecTradeIdMaker == null) {
            throw new Exception("initialize execTradeId maker failed");
        }
        mExecTradeLegIdMaker = IdMakerFactory.getInstance().getIdMaker(204);
        if (mExecTradeLegIdMaker == null) {
            throw new Exception("initialize execTradeLegId maker failed");
        }
    }

    public long createExecOrderId() throws ErrorInfo {
        try {
            return mExecOrderIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!" + e.getMessage());
        }
    }

    public long createExecTradeId() throws ErrorInfo {
        try {
            return mExecTradeIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!" + e.getMessage());
        }
    }

    public long createExecTradeLegId() throws ErrorInfo {
        try {
            return mExecTradeLegIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!" + e.getMessage());
        }
    }

}
