package xueqiao.trade.hosting.position.adjust.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;

import java.util.List;

public class RefreshTradeAccountJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IHostingTradeAccountApi api = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        try {
            List<HostingTradeAccount> list = api.getAllTradeAccounts();
            for (HostingTradeAccount tradeAccount : list) {
                AssignPositionJobScheduler.Global().addVerifyPositionJob(tradeAccount.getTradeAccountId(),tradeAccount.getBrokerTechPlatform());
                AssignPositionJobScheduler.Global().addVerifyDailyPositionJob(tradeAccount.getTradeAccountId(),tradeAccount.getBrokerTechPlatform());
            }
        } catch (ErrorInfo errorInfo) {
            errorInfo.printStackTrace();
        }
    }
}
