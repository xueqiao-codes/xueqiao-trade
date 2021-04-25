package xueqiao.trade.hosting.position.fee.core.common.calculator;

import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.position.fee.core.common.thread.TaskContext;
import xueqiao.trade.hosting.position.fee.core.common.thread.TaskThreadManager;

public abstract class AbstractCalculator<T, Q> implements ICalculator<T, Q> {

    protected TaskContext taskContext;

    protected AbstractCalculator(Long threadKey) {
        this.taskContext = TaskThreadManager.getInstance().getTaskThread(threadKey);
    }

    @Override
    public abstract T onCalculate(Q param) throws Throwable;

    @Override
    public void addTask(Q param) {
        taskContext.getmTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    onCalculate(param);
                } catch (Exception e) {
                    AppLog.e("addTask ---- run ---- Exception", e);
                } catch (Throwable throwable) {
                    AppLog.e("addTask ---- run ---- throwable", throwable);
                }
            }
        });
    }
}
