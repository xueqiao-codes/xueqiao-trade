package xueqiao.trade.hosting.arbitrage.core.effectdate;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDate;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class EffectDateController {

    private static final long CANCEL_OR_SUSPEND_ORDER_AFTER_GFDORDER_END_TIMEMS = 5000;

    /**gfdMode: 如果为真，则暂停处理按照交易日进行。否则按照交易时段进行处理 **/
    private boolean mGFDMode = false;
    private HostingXQEffectDate mEffectDate;
    private TradeTimeSpan mRunningTimeSpan = null;


    public EffectDateController(boolean gfdMode
            , HostingXQEffectDate effectDate) {
        this.mGFDMode = gfdMode;
        this.mEffectDate = effectDate;
    }

    public TradeTimeSpan getRunningTimeSpan() {
        return mRunningTimeSpan;
    }


    /**
     *  处理启动时
     * @return true表示正常继续Actor的逻辑, false为中断Actor逻辑
     */
    public boolean handleActorStarting(XQOrderBaseExecutor executor) throws ErrorInfo {
        if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_TODAY) {
            // 当日有效

            XQOrderHelper.GFDEffectiveTimeSpan effectiveTimeSpan
                    = executor.createGFDEffectiveTimeSpan(executor.getOrder().getCreateTimestampMs());
            if (effectiveTimeSpan == null) {
                executor.suspend(
                        HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
                return false;
            }

            executor.setGfdOrderEndTimestampMs(effectiveTimeSpan.getEndTimestampMs());
            if (System.currentTimeMillis() >= effectiveTimeSpan.getEndTimestampMs()) {
                executor.cancel(
                        TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_STARTED_BUT_AFTER_EFFECT_TIME_PERIOD.getValue());
                return false;
            }

            XQOrderJobScheduler.Global().addCancelOrderJob(
                    executor.getOrder().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , effectiveTimeSpan.getEndTimestampMs()
                            + CANCEL_OR_SUSPEND_ORDER_AFTER_GFDORDER_END_TIMEMS);


        } else if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {
            // 指定时间段有效
            long nowTimestamp = System.currentTimeMillis()/1000;
            if (nowTimestamp > mEffectDate.getEndEffectTimestampS()) {
                executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_STARTED_BUT_AFTER_EFFECT_TIME_PERIOD.getValue());
                return false;
            }

            // 过期定时器
            XQOrderJobScheduler.Global().addCancelOrderJob(executor.getContext().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , mEffectDate.getEndEffectTimestampS()*1000);

            // 检查是否已经到达有效期
            if (nowTimestamp < mEffectDate.getStartEffectTimestampS()) {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_BEFORE_EFFECT_TIME_PERIOD.getValue());
                return false;
            }
        }

        // 检查交易时间
        if (!executor.refreshTradeTimeSpanList()) {
            executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
            return false;
        }
        long currentTimestampMs = System.currentTimeMillis();
        TradeTimeSpan nearestSpan = executor.getTradeTimeSpanList().getNearestSpan(currentTimestampMs);
        if (nearestSpan == null) {
            executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return false;
        }
        if (currentTimestampMs < nearestSpan.getStartTimestampMs()) {
            executor.suspend(HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue());
            return false;
        }

        return true;
    }


    /**
     *  处理运行时
     *  @return true表示正常继续Actor的逻辑, false为中断Actor逻辑
     */
    public boolean handleActorRunning(XQOrderBaseExecutor executor) throws ErrorInfo {
        long currentTimestampMs = System.currentTimeMillis();
        mRunningTimeSpan = executor.getTradeTimeSpanList().getNearestSpan(currentTimestampMs);
        if (mRunningTimeSpan == null) {
            executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return false;
        }

        if (currentTimestampMs < mRunningTimeSpan.getStartTimestampMs()) {
            if (mGFDMode && executor.hasSubExecutorRunning()) {
                // GFD模式允许挂单时候恢复，这个时候任然为运行状态
            } else {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue());
                return false;
            }
        }

        if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_TODAY) {

            XQOrderHelper.GFDEffectiveTimeSpan effectiveTimeSpan = executor.createGFDEffectiveTimeSpan(
                    executor.getOrder().getCreateTimestampMs());
            if (effectiveTimeSpan == null) {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
                return false;
            }

            XQOrderJobScheduler.Global().addCancelOrderJob(
                    executor.getOrder().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , effectiveTimeSpan.getEndTimestampMs()
                            + CANCEL_OR_SUSPEND_ORDER_AFTER_GFDORDER_END_TIMEMS);

            return true;

        } else if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {

            long nowTimestamp = System.currentTimeMillis()/1000;
            if (nowTimestamp > mEffectDate.getEndEffectTimestampS()) {
                executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue());
                return false;
            }

            // 确定过期定时器
            XQOrderJobScheduler.Global().addCancelOrderJob(executor.getContext().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , mEffectDate.getEndEffectTimestampS()*1000);

        }

        // 确定下一个暂停时间段
        if (mGFDMode) {
            XQOrderHelper.GFDEffectiveTimeSpan effectiveTimeSpan
                    = executor.createGFDEffectiveTimeSpan(System.currentTimeMillis());
            if (effectiveTimeSpan == null) {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
                return false;
            }
            XQOrderJobScheduler.Global().addSuspendOrderJob(
                    executor.getOrder().getOrderId()
                    , HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue()
                    , effectiveTimeSpan.getEndTimestampMs()
                            + CANCEL_OR_SUSPEND_ORDER_AFTER_GFDORDER_END_TIMEMS);
        } else {

            TradeTimeSpan nearestTimeSpan = executor.getTradeTimeSpanList().getNearestSpan(System.currentTimeMillis());
            if (nearestTimeSpan == null) {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
                return false;
            }

            XQOrderJobScheduler.Global().addSuspendOrderJob(
                    executor.getOrder().getOrderId()
                    , HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue()
                    , nearestTimeSpan.getEndTimestampMs());
        }

        return true;
    }


    /**
     * 处理已暂停时
     */
    public boolean handleActorSuspended(XQOrderBaseExecutor executor) throws ErrorInfo {
        HostingXQOrderState orderState = executor.getOrder().getOrderState();
        if (orderState.getSuspendReason() != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS) {
                // 异常暂停保留任务，让用户能够感知到异常
                XQOrderJobScheduler.Global().removeCancelOrderJob(executor.getOrder().getOrderId());
            }
            return true;
        }

        if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_TODAY) {
            // 当日有效

            XQOrderHelper.GFDEffectiveTimeSpan effectiveTimeSpan
                    = executor.createGFDEffectiveTimeSpan(executor.getOrder().getCreateTimestampMs());
            if (effectiveTimeSpan == null) {
                executor.suspend(
                        HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
                return false;
            }

            XQOrderJobScheduler.Global().addCancelOrderJob(
                    executor.getOrder().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , effectiveTimeSpan.getEndTimestampMs()
                            + CANCEL_OR_SUSPEND_ORDER_AFTER_GFDORDER_END_TIMEMS);


        } else if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {
            // 指定时间段有效
            long nowTimestamp = System.currentTimeMillis()/1000;
            if (nowTimestamp > mEffectDate.getEndEffectTimestampS()) {
                executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue());
                return false;
            }

            // 过期定时器
            XQOrderJobScheduler.Global().addCancelOrderJob(executor.getContext().getOrderId()
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue()
                    , mEffectDate.getEndEffectTimestampS()*1000);
        }

        if (mEffectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD
                && executor.getOrder().getOrderState().getSuspendedErrorCode()
                == TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_BEFORE_EFFECT_TIME_PERIOD.getValue()) {
            XQOrderJobScheduler.Global().addStartOrderJob(executor.getContext().getOrderId()
                    , mEffectDate.getStartEffectTimestampS() * 1000);
            return true;
        }

        long currentTimestampMs = System.currentTimeMillis();
        TradeTimeSpan nearestSpan = executor.getTradeTimeSpanList().getNearestSpan(currentTimestampMs);
        if (nearestSpan == null) {
            // 把暂停状态改成异常暂停，让用户手工启动
            executor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return false;
        }

        XQOrderJobScheduler.Global().addStartOrderJob(executor.getOrder().getOrderId()
                , nearestSpan.getStartTimestampMs());

        return true;
    }
}
