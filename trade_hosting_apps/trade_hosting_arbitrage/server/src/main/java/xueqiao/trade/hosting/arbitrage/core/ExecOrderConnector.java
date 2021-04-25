package xueqiao.trade.hosting.arbitrage.core;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.thread.RepeatRunnable;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.framework.thread.TaskThreadPool;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecOrderConnector {
    private static ExecOrderConnector sInstance;
    public static ExecOrderConnector Global() {
        if (sInstance == null) {
            synchronized(ExecOrderConnector.class) {
                if (sInstance == null) {
                    sInstance = new ExecOrderConnector();
                }
            }
        }
        return sInstance;
    }
    
    private static final String EXECORDERID_PARAM_KEY = "ExecOrderId";
    private static final String COMMODITYID_PARAM_KEY = "SledCommodityId";
    private static final String REVOKE_GROUP = "REVOKE_GROUP";

    private TaskThreadPool mThreadPool;
    private IHostingDealingApi mDealingApi;
    private Scheduler mJobScheduler;
    private SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private ExecOrderConnector() {
        mThreadPool = new TaskThreadPool("ExecOrderConnector");
        mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        
        try {
            System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
            System.setProperty("org.quartz.threadPool.threadCount", "2");
            mJobScheduler = StdSchedulerFactory.getDefaultScheduler();
            mJobScheduler.start();
        } catch (SchedulerException e) {
            AppLog.f(e.getMessage(), e);
        }
    }
    
    public void sendNewOrder(HostingExecOrder execOrder) {
        mThreadPool.getTaskThreadByMod(execOrder.getExecOrderId()).postTask(new RepeatRunnable() {
            @Override
            protected void onRun() throws Exception {
                try {
                    mDealingApi.createUserDeal(execOrder.getSubUserId()
                            , execOrder.getSubAccountId()
                            , execOrder.getExecOrderId()
                            , execOrder.getContractSummary()
                            , execOrder.getOrderDetail()
                            , execOrder.getSource());
                } catch (ErrorInfo e) {
                    if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_EXISTED.getValue()) {
                        return ;
                    }
                    throw e;
                } 
            }
        });
    }
    
    public void removeRevokeExecOrderJob(long execOrderId) {
        JobKey jobKey = new JobKey(String.valueOf(execOrderId), REVOKE_GROUP);
        try {
            if (mJobScheduler.deleteJob(jobKey)) {
                AppLog.i("removeRevokeExecOrderJob execOrderId=" + execOrderId);
            }
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
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
    
    public static class ExecOrderRevokeJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            if (dataMap == null) {
                AppLog.e("ExecRevokeJob execute, but dataMap is null");
                return ;
            }
            
            long execOrderId = dataMap.getLong(EXECORDERID_PARAM_KEY);
            if (execOrderId <= 0 ) {
                AppLog.e("ExecRevokeJob execute, but execOrderId <= 0");
                return ;
            }
            long sledCommodityId = dataMap.getLong(COMMODITYID_PARAM_KEY);
            if (sledCommodityId <= 0) {
                AppLog.e("ExecRevokeJob execute, but sledCommodityId <= 0");
                return ;
            }
            
            ExecOrderConnector.Global().revokeOrder(execOrderId, sledCommodityId);
        }
    }
    
    private void addRevokeExecOrderJob(long execOrderId, long sledCommodityId, long startTimestampMs) {
        JobKey jobKey = new JobKey(String.valueOf(execOrderId), REVOKE_GROUP);
        try {
            boolean dropped = hasTriggersBefore(jobKey, startTimestampMs);
            AppLog.i("addRevokeExecOrderJob execOrderId=" + execOrderId + ", startTime="
                    + mDateTimeFormat.format(new Date(startTimestampMs)) 
                    + ", dropped=" + dropped);
            if (dropped) {
                return ;
            }
            
            mJobScheduler.deleteJob(jobKey);
            JobDetail jobDetail = JobBuilder.newJob(ExecOrderRevokeJob.class)
                    .withIdentity(jobKey)
                    .usingJobData(EXECORDERID_PARAM_KEY, execOrderId)
                    .usingJobData(COMMODITYID_PARAM_KEY, sledCommodityId)
                    .build();
            
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(String.valueOf(execOrderId) + "-trigger", REVOKE_GROUP)
                    .startAt(new Date(startTimestampMs))
                    .build();
            mJobScheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
    public void revokeOrder(long execOrderId, long sledCommodityId) {
        try {
            TradeTimeSpanList tradeTimeSpanList = XQOrderHelper.constructTimeSpanList(sledCommodityId);
            if (tradeTimeSpanList != null) {
                long nowTimestampMs = System.currentTimeMillis();
                TradeTimeSpan nearestSpan = tradeTimeSpanList.getNearestSpan(nowTimestampMs);
                if (nearestSpan.getStartTimestampMs() > nowTimestampMs) {
                    // 说明是非交易时间段
                    addRevokeExecOrderJob(execOrderId, sledCommodityId, nearestSpan.getStartTimestampMs());
                    return ;
                }
            }
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
        
        mThreadPool.getTaskThreadByMod(execOrderId).postTask(new RepeatRunnable() {
            @Override
            protected void onRun() throws Exception {
                try {
                    mDealingApi.revokeDeal(execOrderId);
                } catch (ErrorInfo e) {
                    if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()) {
                        throw e;
                    }
                }
            }
        });
    }
}
