package xueqiao.trade.hosting.position.adjust.job;

import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.position.adjust.thriftapi.AoType;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AssignPositionJobScheduler {

    public static final AoType[] AO_TYPES = new AoType[]{AoType.ASSET, AoType.STATIS};

    private AssignPositionJobScheduler() throws SchedulerException {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
        System.setProperty("org.quartz.threadPool.threadCount", "2");
        mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
        mJobScheduler.start();
    }

    private static AssignPositionJobScheduler sInstance;

    public static AssignPositionJobScheduler Global() {
        if (sInstance == null) {
            synchronized (AssignPositionJobScheduler.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new AssignPositionJobScheduler();
                        sInstance.addAssignAoJobs();
                        sInstance.addRefreshTradeAccountJob();
                        sInstance.addClearContractExpiredPositionJob();
                    } catch (SchedulerException e) {
                        AppLog.e(e.getMessage(), e);
                        throw new RuntimeException(e.getMessage(), e);
                    } catch (ErrorInfo e) {
                        AppLog.e(e.getMessage(), e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return sInstance;
    }

    private Scheduler mJobScheduler;

    private boolean hasTriggersBefore(JobKey jobKey) throws SchedulerException {
        List<? extends Trigger> triggers = mJobScheduler.getTriggersOfJob(jobKey);
        if (triggers != null && !triggers.isEmpty()) {
            AppLog.d("job exists.");
            return true;
        }
        return false;

    }

    public void addAssignAoJobs() throws ErrorInfo {
        for (AoType aoType : AO_TYPES) {
            addJob(aoType);
        }
    }

    /**
     * 增加一个分配持仓到其他模块的任务
     *
     * @param aoType
     * @throws SchedulerException
     */
    private void addJob(AoType aoType) throws ErrorInfo {
        AppLog.d("add job:　" + aoType);
        JobKey jobKey = new JobKey(aoType.name());
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(AssignPositionJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("aoType", aoType.getValue())
                    .build();
            Calendar calendar = Calendar.getInstance();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(aoType + "-trigger")
                    .startAt(calendar.getTime())
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
     * 删除分配持仓到其他模块的任务
     */
    public void removeAssignAoJob(AoType aoType) {
        AppLog.i("remove Job AoType=" + aoType);
        try {
            mJobScheduler.deleteJob(new JobKey(aoType.name()));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个核对资金账户持仓的任务
     *
     * @param tradeAccountId 资金账户id
     * @throws SchedulerException
     */
    public void addVerifyPositionJob(long tradeAccountId, BrokerTechPlatform brokerPlatform) throws ErrorInfo {
        AppLog.d("add job:　" + tradeAccountId);
        JobKey jobKey = new JobKey(Long.toString(tradeAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(VerifyPositionJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("tradeAccountId", Long.toString(tradeAccountId))
                    .build();

            CronScheduleBuilder scheduleBuilder = getCronScheduleBuilder(brokerPlatform);
            if (scheduleBuilder == null) {
                return;
            }

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tradeAccountId + "-trigger")
                    .startNow()
                    .withSchedule(scheduleBuilder)
                    .build();

            mJobScheduler.scheduleJob(jobDetail, trigger);
            if (!mJobScheduler.isStarted()) {
                mJobScheduler.start();
            }

        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private CronScheduleBuilder getCronScheduleBuilder(BrokerTechPlatform brokerPlatform) {
        CronScheduleBuilder scheduleBuilder = null;
        if (BrokerTechPlatform.TECH_CTP.equals(brokerPlatform)) {
            // 根据北京时间的15:30进行核对
            scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(15, 30);
        } else if (BrokerTechPlatform.TECH_ESUNNY_9.equals(brokerPlatform)) {
            // 根据纽约时区的17:30进行核对
            scheduleBuilder = CronScheduleBuilder
                    .dailyAtHourAndMinute(17, 30)
                    .inTimeZone(TimeZone.getTimeZone("America/New_York"));
        } else {
            AppLog.e("brokerPlatform error: " + brokerPlatform);
        }
        return scheduleBuilder;
    }


    /**
     * 增加一个核对每日资金账户持仓的任务
     *
     * @param tradeAccountId 资金账户id
     * @throws SchedulerException
     */
    public void addVerifyDailyPositionJob(long tradeAccountId, BrokerTechPlatform brokerPlatform) throws ErrorInfo {
        AppLog.d("add job:　" + tradeAccountId);
        JobKey jobKey = new JobKey(genVerifyDailyPositionJobKey(tradeAccountId));
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }
            CronScheduleBuilder scheduleBuilder = getCronScheduleBuilder(brokerPlatform);
            if (scheduleBuilder == null) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(VerifyDailyPositionJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("tradeAccountId", Long.toString(tradeAccountId))
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(genVerifyDailyPositionJobKey(tradeAccountId) + "-trigger")
                    .startNow()
                    .withSchedule(scheduleBuilder)
                    .build();

            mJobScheduler.scheduleJob(jobDetail, trigger);
            if (!mJobScheduler.isStarted()) {
                mJobScheduler.start();
            }

        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private String genVerifyDailyPositionJobKey(long tradeAccountId) {
        return "Daily_Position_" + tradeAccountId;
    }

    /**
     * 删除核对资金账户每日持仓的任务
     */
    public void removeVerifyDailyPositionJob(long tradeAccountId) {
        AppLog.i("remove verify daily position Job tradeAccountId=" + tradeAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(genVerifyDailyPositionJobKey(tradeAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 删除核对资金账户持仓的任务
     */
    public void removeVerifyPositionJob(long tradeAccountId) {
        AppLog.i("remove Job tradeAccountId=" + tradeAccountId);
        try {
            mJobScheduler.deleteJob(new JobKey(Double.toString(tradeAccountId)));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    /**
     * 增加一个刷新资金账户核对持仓的任务
     *
     * @throws SchedulerException
     */
    public void addRefreshTradeAccountJob() throws ErrorInfo {
        String jobName = "RefreshTradeAccountJob";
        AppLog.d("add job:　" + jobName);
        JobKey jobKey = new JobKey(jobName);
        try {
            boolean dropped = hasTriggersBefore(jobKey);
            if (dropped) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(RefreshTradeAccountJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("REFRESH", jobName)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "-trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(10).repeatForever())
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
     * 删除任务
     */
    public void removeRefreshTradeAccountJob() {
        String jobName = "RefreshTradeAccountJob";
        AppLog.i("remove refresh Job =" + jobName);
        try {
            mJobScheduler.deleteJob(new JobKey(jobName));
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

    public void clear() {
        try {
            mJobScheduler.clear();
            mJobScheduler.shutdown();
            sInstance = null;
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
}
