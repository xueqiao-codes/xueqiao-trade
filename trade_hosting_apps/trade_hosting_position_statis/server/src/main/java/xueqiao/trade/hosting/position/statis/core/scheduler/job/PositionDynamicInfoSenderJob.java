package xueqiao.trade.hosting.position.statis.core.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatPositionDynamicInfoSenderCalculator;

public class PositionDynamicInfoSenderJob implements Job {

    private StatPositionDynamicInfoSenderCalculator positionDynamicInfoSenderCalculator;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        getPositionDynamicInfoSenderCalculator().addTask(null);
    }

    private StatPositionDynamicInfoSenderCalculator getPositionDynamicInfoSenderCalculator() {
        if (positionDynamicInfoSenderCalculator == null) {
            positionDynamicInfoSenderCalculator = new StatPositionDynamicInfoSenderCalculator();
        }
        return positionDynamicInfoSenderCalculator;
    }
}
