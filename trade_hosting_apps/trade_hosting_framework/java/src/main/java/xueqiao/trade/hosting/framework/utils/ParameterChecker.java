package xueqiao.trade.hosting.framework.utils;

import org.soldier.base.StackTrace;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class ParameterChecker {
    /**
     *  面向接口成的检测
     */
	public static void check(boolean condition
			, String errorMsg) throws ErrorInfo {
		if (!condition) {
			// hard code for hosting, 2001 is parameter error!
			throw new ErrorInfo(2001, errorMsg);
		}
	}
	
	public static void checkNotNull(Object obj, String objName) throws ErrorInfo {
	    if (obj == null) {
	        throw new ErrorInfo(2001, objName + " should not be null");
	    }
	}
	
	/**
	 *  面向内部使用中间层的检测, 利用栈追踪
	 */
	public static void checkInnerArgument(boolean condition) throws ErrorInfo {
        if (!condition) {
            throw new ErrorInfo(2001, StackTrace.toString(new IllegalArgumentException()));
        }
    }
	
	public static void checkInnerNotNull(Object obj) throws ErrorInfo {
	    if(obj == null) {
	        throw new ErrorInfo(2001, StackTrace.toString(new NullPointerException()));
	    }
	}
	
}
