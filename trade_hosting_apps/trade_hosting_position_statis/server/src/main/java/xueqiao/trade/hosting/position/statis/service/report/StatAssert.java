package xueqiao.trade.hosting.position.statis.service.report;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class StatAssert {

    public static void checkParam(boolean expression, String description) throws ErrorInfo {
        if (!expression) {
            AppReport.reportErr("checkParam #### " + description);
            throw new ErrorInfo(StatErrorCode.errorInvalidParam.errorCode, description);
        }
    }

    public static void doAssert(boolean expression, String description) throws ErrorInfo {
        if (!expression) {
            AppReport.reportErr("StatAssert #### " + description);
            throw new ErrorInfo(StatErrorCode.errorAssert.errorCode, description);
        }
    }
}
