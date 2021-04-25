package xueqiao.trade.hosting.position.statis.core.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import xueqiao.trade.hosting.position.statis.core.calculator.backup.QuotationBackupCalculator;

public class AutoBackupToFileJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        new QuotationBackupCalculator().addTask(null);
    }
}
