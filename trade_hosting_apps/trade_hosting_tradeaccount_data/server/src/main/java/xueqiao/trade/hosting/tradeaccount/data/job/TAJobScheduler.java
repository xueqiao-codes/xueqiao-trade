package xueqiao.trade.hosting.tradeaccount.data.job;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.HostingTradeAccount;

import java.util.HashSet;
import java.util.Set;

public class TAJobScheduler {
    private static TAJobScheduler sInstance;
    public static TAJobScheduler Global() {
        if (sInstance == null) {
            synchronized (TAJobScheduler.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new TAJobScheduler();
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return sInstance;
    }

    private static String FUNDS_SAMPLE_GROUP = "FUNDS_SAMPLE";

    private Scheduler mJobScheduler;
    private Set<Long> mTradeAccountIds = new HashSet<>();

    private TAJobScheduler() throws SchedulerException {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
        System.setProperty("org.quartz.threadPool.threadCount", "2");
        mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
        mJobScheduler.start();
    }

    public synchronized void addTAJobs(long tradeAccountId, BrokerTechPlatform techPlatform) {
        Preconditions.checkArgument(tradeAccountId > 0);
        Preconditions.checkNotNull(techPlatform);

        if (mTradeAccountIds.contains(tradeAccountId)) {
            return ;
        }

        // 资金同步任务
        // CTP 每天15:40点采样一次
        // 易盛 每天06:00点采样一次
        JobKey jobKey = new JobKey(String.valueOf(tradeAccountId), FUNDS_SAMPLE_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(TAFundSampleJob.class)
                        .withIdentity(jobKey)
                        .usingJobData(JobConstants.TRADEACCOUNT_ID_KEY, tradeAccountId)
                        .build();

        TriggerBuilder<Trigger> triggerBuilder
                = TriggerBuilder.newTrigger().withIdentity("Trigger-" + tradeAccountId, FUNDS_SAMPLE_GROUP);

        if (techPlatform == BrokerTechPlatform.TECH_CTP) {
            triggerBuilder.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 40));
        } else if (techPlatform == BrokerTechPlatform.TECH_ESUNNY_9
                || techPlatform == BrokerTechPlatform.TECH_ESUNNY_3) {
            triggerBuilder.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(6, 0));
        } else {
            return ;
        }

        try {
            mJobScheduler.scheduleJob(jobDetail, triggerBuilder.build());
            mTradeAccountIds.add(tradeAccountId);
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    public synchronized void rmTAJobs(long tradeAccountId) {
        Preconditions.checkArgument(tradeAccountId > 0);
        if (!mTradeAccountIds.contains(tradeAccountId)) {
            return ;
        }

        try {
            mJobScheduler.deleteJob(new JobKey(String.valueOf(tradeAccountId), FUNDS_SAMPLE_GROUP));
            mTradeAccountIds.remove(tradeAccountId);
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

}
