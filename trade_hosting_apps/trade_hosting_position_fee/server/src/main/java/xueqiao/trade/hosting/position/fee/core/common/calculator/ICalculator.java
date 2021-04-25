package xueqiao.trade.hosting.position.fee.core.common.calculator;

public interface ICalculator<T, Q> {
    /**
     * 直接调用计算，返回结果，同时结果缓存在内存中
     */
    T onCalculate(Q param) throws Throwable;

    /**
     * 在线程中计算，结果缓存在内存中
     */
    void addTask(Q param);
}
