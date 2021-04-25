package xueqiao.trade.hosting.position.fee.common.base;

import com.antiy.error_code.ErrorCodeInner;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

/**
 * 前置参数检查
 */
public class Precondition {

    public static void check(boolean condition, String errorMsg) throws ErrorInfo {
        if (!condition) {
            throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), errorMsg);
        }
    }
}
