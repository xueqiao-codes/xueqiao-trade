package xueqiao.trade.hosting.framework.utils;

import java.util.concurrent.Callable;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class ErrorInfoCallHelper {
    
    public static <T> T call(Callable<T> f) throws ErrorInfo {
        try {
            return f.call();
        } catch (ErrorInfo ei) {
            throw ei;
        } catch (Throwable et) {
            throw new ErrorInfo(10500, "server inner error!" +  et.getMessage());
        }
    }
}
