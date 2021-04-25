package xueqiao.trade.hosting.asset.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.calculator.account.sub.AssetDataFileCalculator;
import xueqiao.trade.hosting.asset.struct.FileOperator;

public class FileJob implements Job {
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

        AppLog.d("FileJob for subAccountId=" + subAccountId);

        AssetDataFileCalculator calculator = null;
        try {
            calculator = (AssetDataFileCalculator) AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.QUOTATION_DATA_FILE_KEY, subAccountId);
        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        }
        if (calculator == null) {
            AppLog.i("calculator is not effective for subAccountId=" + subAccountId);
            return;
        }
        calculator.addTask(FileOperator.WRITE);
    }
}
