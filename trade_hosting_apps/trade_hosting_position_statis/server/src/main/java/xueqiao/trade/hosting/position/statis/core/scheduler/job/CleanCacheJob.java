package xueqiao.trade.hosting.position.statis.core.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import xueqiao.trade.hosting.position.statis.core.calculator.cleancache.CleanCacheCalculator;

public class CleanCacheJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        new CleanCacheCalculator().addTask(null);
    }
}
