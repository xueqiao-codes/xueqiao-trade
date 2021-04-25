package xueqiao.trade.hosting.arbitrage.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

public class XQOrderSuspendJob implements Job {

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
        HostingXQSuspendReason suspendReason
            = HostingXQSuspendReason.findByValue(dataMap.getInt(JobConstants.PARAM_SUSPEND_REASON_KEY));
        int suspendErrorCode = dataMap.getInt(JobConstants.PARAM_SUSPEND_ERRORCODE_KEY);
        if (suspendReason == null) {
            AppLog.e("SuspendJob execute, but suspendReason is null");
            return ;
        }
        
        AppLog.i("StartJob start executing for orderId=" + orderId);
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor == null) {
            AppLog.i("executor is not effective for orderId=" + orderId);
            return ;
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            public void onRun() throws Exception {
                try {
                    getExecutor().suspend(suspendReason, suspendErrorCode);
                } catch (ErrorInfo e) {
                    if (e.getErrorCode() == TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_STATE_CANNOT_OPERATION.getValue()) {
                        return ;
                    }
                    throw e;
                }
            }
        });
    }

}
