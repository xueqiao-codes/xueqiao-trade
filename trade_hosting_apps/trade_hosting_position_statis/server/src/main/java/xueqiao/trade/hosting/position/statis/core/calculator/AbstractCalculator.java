package xueqiao.trade.hosting.position.statis.core.calculator;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.thread.TaskContext;
import xueqiao.trade.hosting.position.statis.core.thread.TaskThreadManager;

public abstract class AbstractCalculator<T, Q> implements ICalculator<T, Q> {

    protected TaskContext taskContext;

    protected AbstractCalculator(Long threadKey) {
        this.taskContext = TaskThreadManager.getInstance().getTaskThread(threadKey);
    }

    @Override
    public abstract T onCalculate(Q param) throws ErrorInfo;

    @Override
    public void addTask(Q param) {
        taskContext.getmTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    onCalculate(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
