package xueqiao.trade.hosting.arbitrage.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;

public class XQOrderCancelJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("SuspendJob execute, but dataMap is null");
            return ;
        }
        
        String orderId = dataMap.getString(JobConstants.PARAM_ORDER_ID_KEY);
        if (StringUtils.isEmpty(orderId)) {
            AppLog.e("SuspendJob execute, but orderId is empty");
            return ;
        }
        int cancelledErrorCode = dataMap.getInt(JobConstants.PARAM_CANCEL_ERRORCODE_KEY);
        
        AppLog.i("CancelJob start executing for orderId=" + orderId);
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor == null) {
            AppLog.i("executor is not effective for orderId=" + orderId);
            return ;
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            public void onRun() throws Exception {
                getExecutor().cancel(cancelledErrorCode);
            }
        });
    }

}
