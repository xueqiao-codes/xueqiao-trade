package xueqiao.trade.hosting.arbitrage.job;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XQOrderJobScheduler {
    private static XQOrderJobScheduler sInstance;
    public static XQOrderJobScheduler Global() {
        if (sInstance == null) {
            synchronized(XQOrderJobScheduler.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new XQOrderJobScheduler();
                    } catch (SchedulerException e) {
                        AppLog.e(e.getMessage(), e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return sInstance;
    }
    
    
    private static final String START_ORDER_GROUP = "START_GROUP";
    private static final String SUSPEND_ORDER_GROUP = "SUSPEND_GROUP";
    private static final String CANCEL_ORDER_GROUP = "CANCEL_GROUP";
    
    private Scheduler mJobScheduler;
    private SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private XQOrderJobScheduler() throws SchedulerException {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
        System.setProperty("org.quartz.threadPool.threadCount", "2");
        mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
        mJobScheduler.start();
    }
    
    private boolean hasTriggersBefore(JobKey jobKey, long timestampMs) throws SchedulerException {
        List<? extends Trigger> triggers = mJobScheduler.getTriggersOfJob(jobKey);
        if (triggers != null && !triggers.isEmpty()) {
            for (int index = 0; index < triggers.size(); ++index) {
                if (triggers.get(index).getStartTime().getTime() <= timestampMs) {
                    return true;
                }
            }
        }
        return false;
        
    }
    
    /**
     *  增加一个定时开启订单的任务
     * @param orderId
     * @param startTimestampMs
     * @throws SchedulerException 
     */
    public void addStartOrderJob(String orderId, long startTimestampMs) {
        JobKey jobKey = new JobKey(orderId, START_ORDER_GROUP);
        try {
            boolean dropped = hasTriggersBefore(jobKey, startTimestampMs);
            AppLog.i("addStartOrderJob orderId=" + orderId + ", startTime="
                    + mDateTimeFormat.format(new Date(startTimestampMs)) 
                    + ", dropped=" + dropped);
            if (dropped) {
                return ;
            }
            
            mJobScheduler.deleteJob(jobKey);
            JobDetail jobDetail = JobBuilder.newJob(XQOrderStartJob.class)
                    .withIdentity(jobKey)
                    .usingJobData(JobConstants.PARAM_ORDER_ID_KEY, orderId)
                    .build();
            
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(orderId + "-trigger", START_ORDER_GROUP)
                    .startAt(new Date(startTimestampMs))
                    .build();
            mJobScheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    /**
     * 增加一个定时关闭订单的任务
     */
    public void addSuspendOrderJob(String orderId
            , HostingXQSuspendReason suspendReason
            , int suspendErrorCode
            , long startTimestampMs) {
        JobKey jobKey = new JobKey(orderId, SUSPEND_ORDER_GROUP);
        try {
            boolean dropped = hasTriggersBefore(jobKey, startTimestampMs);
            AppLog.i("addSuspendOrderJob orderId=" + orderId 
                    + ", suspendReason=" + suspendReason
                    + ", suspendErrorCode=" + suspendErrorCode
                    + ", startTime=" + mDateTimeFormat.format(new Date(startTimestampMs))
                    + ", dropped=" + dropped);
            if (dropped) {
                return ;
            }
            mJobScheduler.deleteJob(jobKey);
            
            JobDetail jobDetail = JobBuilder.newJob(XQOrderSuspendJob.class)
                    .withIdentity(jobKey)
                    .usingJobData(JobConstants.PARAM_ORDER_ID_KEY, orderId)
                    .usingJobData(JobConstants.PARAM_SUSPEND_REASON_KEY, suspendReason.getValue())
                    .usingJobData(JobConstants.PARAM_SUSPEND_ERRORCODE_KEY, suspendErrorCode)
                    .build();
            
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(orderId + "-trigger", SUSPEND_ORDER_GROUP)
                    .startAt(new Date(startTimestampMs))
                    .build();
            
            mJobScheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    /**
     *  增加一个定时撤销订单的任务
     */
    public void addCancelOrderJob(String orderId, int cancelErrorCode, long startTimestampMs) {
        JobKey jobKey = new JobKey(orderId, CANCEL_ORDER_GROUP);
        try {
            boolean dropped = hasTriggersBefore(jobKey, startTimestampMs);
            AppLog.i("addCancelOrderJob orderId=" + orderId
                    + ", cancelErrorCode=" + cancelErrorCode
                    + ", startTime=" + mDateTimeFormat.format(new Date(startTimestampMs))
                    + ", dropped=" + dropped);
            if (dropped) {
                return ;
            }
            
            mJobScheduler.deleteJob(jobKey);
            
            JobDetail jobDetail = JobBuilder.newJob(XQOrderCancelJob.class)
                     .withIdentity(jobKey)
                     .usingJobData(JobConstants.PARAM_ORDER_ID_KEY, orderId)
                     .usingJobData(JobConstants.PARAM_CANCEL_ERRORCODE_KEY, cancelErrorCode)
                     .build();
            
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(orderId + "-trigger", CANCEL_ORDER_GROUP)
                    .startAt(new Date(startTimestampMs))
                    .build();
            
            mJobScheduler.scheduleJob(jobDetail, trigger);
            
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    /**
     * 删除定时开启订单的任务
     */
    public void removeStartOrderJob(String orderId) {
        AppLog.i("removeStartOrderJob orderId=" + orderId);
        try {
            mJobScheduler.deleteJob(new JobKey(orderId, START_ORDER_GROUP));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    /**
     * 删除定时暂停订单的任务
     */
    public void removeSuspendOrderJob(String orderId) {
        AppLog.i("removeSuspendOrderJob orderId=" + orderId);
        try {
            mJobScheduler.deleteJob(new JobKey(orderId, SUSPEND_ORDER_GROUP));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    /**
     * 删除定时撤单的任务
     */
    public void removeCancelOrderJob(String orderId) {
        AppLog.i("removeCancelOrderJob orderId=" + orderId);
        try {
            mJobScheduler.deleteJob(new JobKey(orderId, CANCEL_ORDER_GROUP));
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    public void removeAllOrderJob(String orderId) {
        removeStartOrderJob(orderId);
        removeSuspendOrderJob(orderId);
        removeCancelOrderJob(orderId);
    }
}
