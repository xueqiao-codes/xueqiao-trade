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

public class XQOrderStartJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("StartJob execute, but dataMap is null");
            return ;
        }
        
        String orderId = dataMap.getString(JobConstants.PARAM_ORDER_ID_KEY);
        if (StringUtils.isEmpty(orderId)) {
            AppLog.e("StartJob execute, but orderId is empty");
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
            protected void onRun() throws Exception {
                try {
                    if (getExecutor().getOrder().getOrderState().getSuspendReason() 
                            != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
                        return ;
                    }
                    getExecutor().start();
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
