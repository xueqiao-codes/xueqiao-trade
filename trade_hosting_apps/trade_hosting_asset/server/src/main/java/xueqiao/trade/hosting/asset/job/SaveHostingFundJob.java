package xueqiao.trade.hosting.asset.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.calculator.account.sub.AssetSaveHostingFundCalculator;

public class SaveHostingFundJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("StartJob execute, but dataMap is null");
            return;
        }
        long subAccountId;
        try {
            subAccountId = dataMap.getLong("subAccountId");
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
            return;
        }
        AppLog.w("SaveHostingFundJob for subAccountId=" + subAccountId);

        try {
            AssetSaveHostingFundCalculator calculator = (AssetSaveHostingFundCalculator) AssetCalculatorFactory.
                    getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_SAVE_HOSTING_FUND_KEY, subAccountId);
            calculator.onCalculate(null);
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
        }
    }
}
