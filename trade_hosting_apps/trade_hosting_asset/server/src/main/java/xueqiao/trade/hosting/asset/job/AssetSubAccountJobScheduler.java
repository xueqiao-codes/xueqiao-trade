package xueqiao.trade.hosting.asset.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import java.util.List;
import java.util.TimeZone;

public class AssetSubAccountJobScheduler {

    private AssetSubAccountJobScheduler() throws SchedulerException {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
        System.setProperty("org.quartz.threadPool.threadCount", "2");
        mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
        mJobScheduler.start();
    }

    private static AssetSubAccountJobScheduler sInstance;

    public static AssetSubAccountJobScheduler Global() {
        if (sInstance == null) {
            synchronized (AssetSubAccountJobScheduler.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new AssetSubAccountJobScheduler();
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

    private Scheduler mJobScheduler;

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
     *
     * @param subAccountId
     * @throws SchedulerException
     */
    public void addFileJob(long subAccountId) throws ErrorInfo {
        AppLog.d("add file job:　" + subAccountId);
        JobKey jobKey = new JobKey(genFileJobKey(subAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(FileJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("subAccountId", subAccountId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genFileJobKey(subAccountId) + "-trigger")
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
     * 删除定时保存内存数据到文件的任务
     */
    public void removeFileJob(long subAccountId) {
        AppLog.i("removeFileJob subAccountId=" + subAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(genFileJobKey(subAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个记录子账户资金的任务
     *
     * @param subAccountId
     * @throws SchedulerException
     */
    public void addSaveHostingFundJob(long subAccountId) throws ErrorInfo {
        AppLog.d("add hosting fund job:　" + subAccountId);
        JobKey jobKey = new JobKey(genSaveHostingFundJobKey(subAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(SaveHostingFundJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("subAccountId", subAccountId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genSaveHostingFundJobKey(subAccountId) + "-trigger")
                    .startNow()
                    .withSchedule(CronScheduleBuilder
                            .dailyAtHourAndMinute(17, 30)
                            .inTimeZone(TimeZone.getTimeZone("America/New_York")))
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
     * 删除定时记录子账户资金的任务
     */
    public void removeSaveHostingFundJob(long subAccountId) {
        AppLog.i("removePositionFundJob subAccountId=" + subAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(genSaveHostingFundJobKey(subAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个刷新行情资金的任务
     *
     * @param subAccountId
     * @throws SchedulerException
     */
    public void addPositionFundJob(long subAccountId) throws ErrorInfo {
        AppLog.d("add position fund job:　" + subAccountId);
        JobKey jobKey = new JobKey(genPositionFundJobKey(subAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(PositionFundJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("subAccountId", subAccountId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genPositionFundJobKey(subAccountId) + "-trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
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
     * 删除定时刷新行情资金的任务
     */
    public void removePositionFundJob(long subAccountId) {
        AppLog.i("removePositionFundJob subAccountId=" + subAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(genPositionFundJobKey(subAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个定时清理合约过期状态缓存的任务
     *
     * @param subAccountId
     * @throws SchedulerException
     */
    public void addClearContractExpiredCacheJob(long subAccountId) {
        AppLog.d("clear contract expired cache job:　" + subAccountId);
        JobKey jobKey = new JobKey(genClearContractExpiredCacheJobKey(subAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(ClearContractExpiredCacheJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("subAccountId", subAccountId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genClearContractExpiredCacheJobKey(subAccountId) + "-trigger")
                    .startNow()
                    .withSchedule(CronScheduleBuilder
                            .dailyAtHourAndMinute(0, 10))
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
     * 删除定时清理合约过期状态缓存的任务
     */
    public void removeClearContractExpiredCacheJob(long subAccountId) {
        AppLog.i("removeClearContractExpiredCacheJob subAccountId=" + subAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(genClearContractExpiredCacheJobKey(subAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }


    /**
     * 增加一个定时清理资金账户中合约过期状态净仓信息的任务
     *
     * @throws SchedulerException
     */
    public void addClearContractExpiredPositionJob() {
        AppLog.d("clear contract expired position job:　");
        String key = "ClearContractExpiredPositionJob";
        JobKey jobKey = new JobKey(key);
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(ClearContractExpiredPositionJob.class)
                    .withIdentity(jobKey)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(key + "-trigger")
                    .startNow()
                    .withSchedule(CronScheduleBuilder
                            .dailyAtHourAndMinute(0, 10))
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
     * 删除清理资金账户中合约过期状态净仓信息的任务
     */
    public void removeClearContractExpiredPositionJob() {
        AppLog.i("removeClearContractExpiredPositionJob");
        String key = "ClearContractExpiredPositionJob";
        try {
            mJobScheduler.deleteJob(new JobKey(key));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }


    private String genClearContractExpiredCacheJobKey(long subAccountId) {
        return "clear_contract_expired_" + Long.toString(subAccountId);
    }

    private String genPositionFundJobKey(long subAccountId) {
        return "position_fund_" + Long.toString(subAccountId);
    }

    private String genSaveHostingFundJobKey(long subAccountId) {
        return "hosting_fund_" + Long.toString(subAccountId);
    }

    private String genFileJobKey(long subAccountId) {
        return "data_file_" + Long.toString(subAccountId);
    }
}
