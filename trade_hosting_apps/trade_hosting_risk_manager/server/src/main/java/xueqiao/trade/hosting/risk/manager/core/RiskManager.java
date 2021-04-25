package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;

import java.util.concurrent.TimeUnit;

/**
 *  子账户风控管理器
 */
public class RiskManager implements IRawDataSampleListener {
    private RiskContext mContext;

    private RawDataPool mRawDataPool;  // 原始数据池
    private RiskRuleManager mRuleManager; // 规则管理器
    private RiskController mController; // 风险控制器, 影响订单决策

    private RiskItemManager mItemManager; // 风险条目管理器

    public RiskManager(RiskContext context) {
        this.mContext = context;
        this.mRuleManager = new RiskRuleManager(mContext);
        this.mRawDataPool = new RawDataPool(mContext);
        this.mController = new RiskController(mContext, mRawDataPool);

        context.getWorkThread().postTask(new Runnable() {

            @Override
            public void run() {
                try {
                    initialize();
                } catch (Throwable e) {
                    AppLog.f(e.getMessage(), e);
                }
            }
        });
    }

    public RiskContext getContext() {
        return mContext;
    }

    private void initialize() {
        this.mRawDataPool.addListener(this);
        this.mItemManager = new RiskItemManager(mController, mRuleManager, mRawDataPool);

        // 启动采样
        new RawDataSampler(mRawDataPool).run();
    }

    public HostingRiskFrameDataInfo getRiskFrameDataInfo() {
        Preconditions.checkState(getContext().getWorkThread().isInCurrentThread());

        return mItemManager.getRiskFrameDataInfo();
    }


    public RiskRuleManager getRuleManager() {
        return mRuleManager;
    }

    public RiskController getController() {
        return mController;
    }

    public void destroy() {
        this.mItemManager.destroy();
        this.mRawDataPool.rmListener(this);
    }

    @Override
    public void onSampleProcess(RawDataPool dataPool) {
    }

    @Override
    public void onSampleFinished(RawDataPool dataPool) {
        mContext.getWorkThread().postTaskDelay(new RawDataSampler(mRawDataPool), 1, TimeUnit.SECONDS);
    }
}
