package xueqiao.trade.hosting.framework.utils;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

public class ProcessUtils {
     public static void checkSingleInstance(final String appId) {
         Preconditions.checkArgument(StringUtils.isNotEmpty(appId));
         try {
             JUnique.acquireLock(appId);
         } catch (AlreadyLockedException e) {
             System.err.println("process " + appId + " is already started!");
             System.exit(-1);
         }
    }
}
