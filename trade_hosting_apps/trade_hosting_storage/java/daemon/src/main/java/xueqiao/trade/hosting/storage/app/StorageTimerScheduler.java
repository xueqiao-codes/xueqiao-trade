package xueqiao.trade.hosting.storage.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.storage.config.HostingConfigFileCleaner;

/**
 *  允许被其他任务阻塞一段时间，时间不那么精确的存储定时调度器
 * @author wangli
 */
public class StorageTimerScheduler {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    private static class HostingConfigFileClearTask implements Runnable {
        @Override
        public void run() {
            try {
                HostingConfigFileCleaner.Global().runOnce();
            } catch (Throwable e) {
                AppLog.e(e.getMessage());
            }
        }
    }
    
    public static void start() {
        // 每24个小时清理一次配置目录
        scheduler.scheduleWithFixedDelay(new HostingConfigFileClearTask()
                , 1, 24*60, TimeUnit.MINUTES);
    }
}
