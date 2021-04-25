package xueqiao.trade.hosting.position.statis.core.calculator;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public interface ICalculator<T, Q> {
    /**
     * 直接调用计算，返回结果，同时结果缓存在内存中
     */
    T onCalculate(Q param) throws ErrorInfo;

    /**
     * 在线程中计算，结果缓存在内存中
     */
    void addTask(Q param);
}
