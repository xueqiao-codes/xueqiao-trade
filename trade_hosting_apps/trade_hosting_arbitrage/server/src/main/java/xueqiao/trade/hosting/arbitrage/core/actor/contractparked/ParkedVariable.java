package xueqiao.trade.hosting.arbitrage.core.actor.contractparked;

class ParkedVariable {
    // 预启动准备时间偏离
    public final static int PRE_START_PERIOD_MS = 2000;
    
    // 启动过程间隔检测时间
    public final static int MAX_PARKED_STARTING_PERIOD_MS = 10000;
    
    // 运行过程间隔检测时间
    public final static int MAX_PARKED_RUNNING_PERIOD_MS = 20000;
    
    // 限价单等待行情的最大延时
    public final static int MAX_PARKED_LIMIT_WAITING_QUOATION_TIMEMS = 10000;
}
