package xueqiao.trade.hosting.position.statis.core.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.scheduler.job.AutoBackupToFileJob;
import xueqiao.trade.hosting.position.statis.core.scheduler.job.CleanCacheJob;
import xueqiao.trade.hosting.position.statis.core.scheduler.job.PositionDynamicInfoSenderJob;

import java.util.List;

public class StatJobScheduler {

    private static StatJobScheduler sInstance;
    private Scheduler mJobScheduler;

    private StatJobScheduler() throws SchedulerException {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
        System.setProperty("org.quartz.threadPool.threadCount", "1");
        mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
        mJobScheduler.start();
    }

    public static StatJobScheduler Global() {
        if (sInstance == null) {
            synchronized (StatJobScheduler.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new StatJobScheduler();
                    } catch (SchedulerException e) {
                        AppLog.e(e.getMessage(), e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return sInstance;
    }

    public void clear() {
        try {
            AppLog.w("clear all jobs");
            mJobScheduler.clear();
            mJobScheduler.shutdown();
            sInstance = null;
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private boolean hasTriggersBefore(JobKey jobKey) throws SchedulerException {
        List<? extends Trigger> triggers = mJobScheduler.getTriggersOfJob(jobKey);
        if (triggers != null && !triggers.isEmpty()) {
            System.out.println("job exists.");
            return true;
        }
        return false;
    }

    /**
     * 增加一个保存内存数据到文件的任务
     * (这个JOB 可以一直存在，不需要清理)
     *
     * @throws SchedulerException
     */
    public void addQuotationBackupJob() throws ErrorInfo {
        JobKey jobKey = new JobKey(genBackupJobKey());
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(AutoBackupToFileJob.class)
                    .withIdentity(jobKey)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genBackupJobKey() + "-trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever())
                    .build();

            mJobScheduler.scheduleJob(jobDetail, trigger);
            if (!mJobScheduler.isStarted()) {
                mJobScheduler.start();
            }
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个定时推送持仓动态信息的任务
     * 每1秒触发一次发送
     * (这个JOB 可以一直存在，不需要清理)
     *
     * @throws SchedulerException
     */
    public void addPositionDynamicInfoSenderJob() throws ErrorInfo {
        JobKey jobKey = new JobKey(genPositionDynamicInfoKey());
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(PositionDynamicInfoSenderJob.class)
                    .withIdentity(jobKey)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genPositionDynamicInfoKey() + "-trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();

            mJobScheduler.scheduleJob(jobDetail, trigger);
            if (!mJobScheduler.isStarted()) {
                mJobScheduler.start();
            }
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 添加定时清理缓存任务
     * 每10分钟触发一次发送
     * (这个JOB 可以一直存在，不需要清理)
     *
     * @throws SchedulerException
     */
    public void addCleanCacheJob() throws ErrorInfo {
        JobKey jobKey = new JobKey(genCleanCacheJobKey());
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(CleanCacheJob.class)
                    .withIdentity(jobKey)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genCleanCacheJobKey() + "-trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(600).repeatForever())
                    .build();

            mJobScheduler.scheduleJob(jobDetail, trigger);
            if (!mJobScheduler.isStarted()) {
                mJobScheduler.start();
            }
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 删除定时保存内存数据到文件的任务
     */
    public void removeQuotationBackupJob() {
        try {
            mJobScheduler.deleteJob(new JobKey(genBackupJobKey()));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private String genBackupJobKey() {
        return "quotation_backup";
    }

    private String genPositionDynamicInfoKey() {
        return "position_dynamic_info_sender";
    }

    private String genCleanCacheJobKey() {
        return "clean_clean";
    }
}
