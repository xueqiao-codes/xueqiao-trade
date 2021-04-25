package xueqiao.trade.hosting.arbitrage.core.composelimit.engine;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeBaseEngine;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePod;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePodConstant;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePodFactory;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ComposePodEngine extends ComposeBaseEngine {

    /**
     *  当前运行的Pod
     */
    private ComposePod mCurrentPod;

    /**
     *  记录每条腿从Engine创建，撤销的订单总数
     */
    private Map<Long, Integer> mCancelledOrderCount = new HashMap<>();

    @Override
    public void onCreate(XQOrderComposeLimitExecutor executor) throws ErrorInfo {
        super.onCreate(executor);

        // 计算从上次运行到现在已经撤单的量
        long timestampMs = getOrder().getOrderState().getStateTimestampMs();
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            for (HostingExecOrder execOrder : subExecutor.getOrderMarketer().getMarketOrders().values()) {
                if (execOrder.getCreateTimestampMs() >= timestampMs) {
                    Preconditions.checkArgument(subExecutor.getSubSledContractId()
                            == execOrder.getContractSummary().getSledContractId());
                    if (execOrder.getStateInfo().getCurrentState().getValue()
                            == HostingExecOrderStateValue.ORDER_UPSIDE_DELETED) {
                        incCancelledOrderCount(subExecutor.getSubSledContractId(), 1);
                    }
                }
            }
        }

        schedulePod(null);
    }

    private void clearCancelledOrderCount() {
        mCancelledOrderCount.clear();
    }

    private void incCancelledOrderCount(long sledContractId, int count) {
        Integer value = mCancelledOrderCount.get(sledContractId);
        if (value == null) {
            value = new Integer(count);
        } else {
            value += count;
        }
        mCancelledOrderCount.put(sledContractId, value);
    }

    private int getCancelledOrderCount(long sledContractId) {
        Integer value = mCancelledOrderCount.get(sledContractId);
        if (value == null) {
            return 0;
        }
        return value;
    }

    private int getExecEveryQty() {
        int execEveryQty = getOrderDetail().getExecParams().getExecEveryQty();
        if (execEveryQty <= 0) {
            return 1;
        }
        return execEveryQty;
    }

    private int getTradedVolume() {
        int tradedVolume = 0;
        Map<Long, XQTradeWithRelatedItem> xqTrades = getExecutor().getTradeWithRelatedItems();
        for (XQTradeWithRelatedItem tradeItem : xqTrades.values()) {
            tradedVolume += tradeItem.getTrade().getTradeVolume();
        }
        return tradedVolume;
    }

    private int calPodTargetVolume(int tradedVolume, int execEveryQty) {
        if (0 == (getOrderDetail().getQuantity() % execEveryQty)) {
            return execEveryQty;
        }
        if (tradedVolume >= (getOrderDetail().getQuantity() / execEveryQty) * execEveryQty) {
            return (getOrderDetail().getQuantity() % execEveryQty);
        }
        return execEveryQty;
    }

    private int calPodIndex(int tradedVolume, int execEveryQty) {
        return (tradedVolume/execEveryQty) * execEveryQty;
    }

    private List<XQTradeWithRelatedItem> generatePodTradeRelatedItems() {
        List<XQTradeWithRelatedItem> resultList = new ArrayList<>();

        int tradedVolume = 0;
        Map<Long, XQTradeWithRelatedItem> xqTrades = getExecutor().getTradeWithRelatedItems();
        for (XQTradeWithRelatedItem tradeItem : xqTrades.values()) {
            tradedVolume += tradeItem.getTrade().getTradeVolume();
        }

        // 首先按照成交ID对所有的成交配对
        List<Long> orderedTradeIds = new ArrayList<>(xqTrades.keySet());
        Collections.sort(orderedTradeIds);

        int execEveryQty = getOrderDetail().getExecParams().getExecEveryQty();
        if (execEveryQty <= 0) {
            execEveryQty = 1;
        }
        int reservedTradeVolume = tradedVolume % execEveryQty;
        if (reservedTradeVolume <= 0) {
            return resultList;
        }

        for (int index = orderedTradeIds.size() - 1; (index >= 0 && reservedTradeVolume >= 0); --index) {
            XQTradeWithRelatedItem tradeItem = xqTrades.get(orderedTradeIds.get(index));
            if (tradeItem.getTrade().getTradeVolume() <= reservedTradeVolume) {
                resultList.add(tradeItem);
                reservedTradeVolume -= tradeItem.getTrade().getTradeVolume();
            } else {
                // 理论上不应该出现这种情况，出现这种情况应该是代码逻辑上存在BUG
                // 按照成交ID逆序排序后，未整数配对的成交应该和需要的成交对齐
                AppLog.w("[WARNING]PodTradeRelatedItem has bigger than trades, that maybe bug!!!");

                // 这里采用弥补手段， 构造假的关联条目
                XQTradeWithRelatedItem feakTradeItem = new XQTradeWithRelatedItem();
                feakTradeItem.setTrade(new HostingXQTrade(tradeItem.getTrade()));
                feakTradeItem.getTrade().setTradeVolume(reservedTradeVolume);

                // 统计每条腿的均价
                Map<Long, Double> tradeTotalFee = new HashMap<>();
                for (XQTradeRelatedItem tradeRelatedItem : tradeItem.getRelatedItems().values()) {
                    Double totalFee = tradeTotalFee.get(tradeRelatedItem.getSledContractId());
                    if (totalFee == null) {
                        totalFee = new Double(tradeRelatedItem.getExecTradeLegPrice()
                                * tradeRelatedItem.getRelatedTradeVolume());
                    } else {
                        totalFee += tradeRelatedItem.getExecTradeLegPrice() * tradeRelatedItem.getRelatedTradeVolume();
                    }
                    tradeTotalFee.put(tradeRelatedItem.getSledContractId(), totalFee);
                }

                int feakId = 0;
                for (HostingComposeLeg composeLeg : getComposeGraph().getLegs().values()) {
                    XQTradeRelatedItem feakLegItem = new XQTradeRelatedItem();
                    feakLegItem.setOrderId("FEAK_ORDER_" + String.valueOf(feakId++));
                    feakLegItem.setTradeId(feakId++);
                    feakLegItem.setSledContractId(composeLeg.getSledContractId());
                    feakLegItem.setExecOrderId(feakId++);
                    feakLegItem.setExecTradeLegId(feakId++);
                    feakLegItem.setExecTradeLegVolume(composeLeg.getQuantity());
                    feakLegItem.setRelatedTradeVolume(composeLeg.getQuantity());
                    feakLegItem.setExecTradeLegPrice(
                            tradeTotalFee.get(composeLeg.getSledContractId())/composeLeg.getQuantity());
                    HostingExecOrderTradeDirection orderTradeDirection = super.getOrderTradeDirection(composeLeg);
                    if(orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                        feakLegItem.setExecTradeLegDirection(HostingExecTradeDirection.TRADE_BUY);
                    } else {
                        feakLegItem.setExecTradeLegDirection(HostingExecTradeDirection.TRADE_SELL);
                    }
                    feakLegItem.setCreateTimestampMs(System.currentTimeMillis());

                    feakTradeItem.getRelatedItems().put(feakLegItem.getExecTradeLegId(), feakLegItem);
                }

                resultList.add(feakTradeItem);
                break;
            }
        }

        return resultList;
    }

    private void schedulePod(List<QuotationItem> initialQuotationItems) throws ErrorInfo {
        // 重新调度新的pod
        int podTargetVolume = calPodTargetVolume(getTradedVolume(), getExecEveryQty());
        int podIndex = calPodIndex(getTradedVolume(), getExecEveryQty());

        List<XQTradeWithRelatedItem> relatedTrades = generatePodTradeRelatedItems();
        Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedExecTradeLegs
                = getExecutor().getLastestUnRelatedTradeLegInfos();

        if (getExecutor().hasSubExecutorRunning()) {
            boolean needNextSchedule = false;
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                if (!subExecutor.hasOrderRunning()) {
                    continue;
                }

                String lastestOrderPodIndex = getPodIndexForLatestOrder(subExecutor);
                if (lastestOrderPodIndex.equalsIgnoreCase(String.valueOf(podIndex))) {
                    continue;
                }

                needNextSchedule = true;
                HostingExecOrder lastestOrder = subExecutor.getOrderMarketer().getLastestOrder();
                if (lastestOrder.isSetTradeSummary()
                        && lastestOrder.getTradeSummary().getTradeListTotalVolume() < lastestOrder.getOrderDetail().getQuantity()) {
                    // 上一个Pod的订单成交量以及满足了切换的需求，这个时候额外的量全是意外多买，要撤销订单
                    subExecutor.getOrderMarketer().shouldCancel();
                }
            }

            if (needNextSchedule) {
                // 等待订单状态返回驱动
                return ;
            }
        }

        mCurrentPod = ComposePodFactory.createPod(getOrderDetail().getExecParams()
                , podIndex
                , getExecutor()
                , podTargetVolume);
        if (mCurrentPod == null) {
            getExecutor().cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_COMPOSE_EXEC_TYPE_NOT_SUPPORTED.getValue());
            return ;
        }

        mCurrentPod.updateTradeInfo(relatedTrades, unRelatedExecTradeLegs);
        if (initialQuotationItems != null) {
            mCurrentPod.initQuotationItems(initialQuotationItems);
        }
        // 完成一个pod的成交，则撤单次数重新计算
        clearCancelledOrderCount();

        HostingXQOrderResumeMode resumeMode = getOrder().getOrderState().getResumeMode();
        Preconditions.checkNotNull(resumeMode);
        mCurrentPod.onStart(resumeMode);

        if (AppLog.infoEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("schedulePod, orderId=").append(getOrder().getOrderId())
                      .append(", tradedVolume=").append(getTradedVolume())
                      .append(", execEveryQty=").append(getExecEveryQty())
                      .append(", podTargetVolume=").append(podTargetVolume)
                      .append(", podIndex=").append(podIndex)
                      .append(", resumeMode=").append(resumeMode)
                      .append(", relatedTrades=").append(relatedTrades)
                      .append(", unRelatedExecTradeLegs=").append(unRelatedExecTradeLegs);
            AppLog.i(logBuilder.toString());
        }
    }

    private int getCancelLimit(long sledContractId) {
        HostingXQComposeLimitOrderExecParams execParams = getOrderDetail().getExecParams();
        HostingXQComposeLimitOrderExecType execType = execParams.getExecType();

        HostingXQComposeLimitOrderLegChaseParam legChaseParam;
        if (execType == HostingXQComposeLimitOrderExecType.LEG_BY_LEG) {
            legChaseParam = execParams.getExecLegByParams().getLegChaseParams().get(sledContractId);
        } else if (execType == HostingXQComposeLimitOrderExecType.PARALLEL_LEG) {
            legChaseParam = execParams.getExecParallelParams().getLegChaseParams().get(sledContractId);
        } else {
            return -1;
        }

        if (legChaseParam == null) {
            return -1;
        }

        return legChaseParam.getTimes();
    }


    @Override
    public void onDestroy() throws ErrorInfo {
        if (mCurrentPod != null) {
            mCurrentPod.onDestroy();
            mCurrentPod = null;
        }
    }

    protected void processQuotationItem(QuotationItem quotationItem) throws ErrorInfo {
        if (mCurrentPod != null) {
            mCurrentPod.onReceivedQuotationItem(quotationItem);
        }
    }

    private String getPodIndexForLatestOrder(XQOrderSubExecutor subExecutor) {
        Map<String, String> extraParams = subExecutor.getOrderMarketer().getLastestOrderExtraParams();
        Preconditions.checkNotNull(extraParams);
        String latestOrderPodIndex = extraParams.get(ComposePodConstant.PODINDEX_KEY);
        Preconditions.checkNotNull(latestOrderPodIndex);
        return latestOrderPodIndex;
    }

    // 检查订单服务的PodIndex
    private void checkLastestOrderPodIndex(XQOrderSubExecutor subExecutor, HostingExecOrder latestOrder) {
        if (String.valueOf(mCurrentPod.getPodIndex()).equalsIgnoreCase(getPodIndexForLatestOrder(subExecutor))) {
            return ;
        }
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
    }

    protected void onComposeLegOrderRunning(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) {
        if (mCurrentPod != null) {
            checkLastestOrderPodIndex(subExecutor, latestOrder);
            mCurrentPod.onComposeLegOrderRunning(subExecutor, latestOrder);
        }
    }

    @Override
    protected void onComposeLegOrderFailed(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        HostingExecOrderState orderState = latestOrder.getStateInfo().getCurrentState();
        if (latestOrder.getStateInfo().getFailedErrorCode()
                == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_FAILED_USER_CANCELLED.getValue()) {
            if (mCurrentPod != null) {
                mCurrentPod.onComposeLegOrderInnerCancelled(subExecutor, latestOrder);
            } else {
                schedulePod(null);
            }
            return ;
        }

        if (orderState.getValue() == HostingExecOrderStateValue.ORDER_VERIFY_FAILED) {
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_VERIFY_FAILED.getValue());
            return ;
        }
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_SEND_FAILED.getValue());

        return ;
    }

    @Override
    protected void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        incCancelledOrderCount(subExecutor.getSubSledContractId(), 1);
        int cancelLimit = getCancelLimit(subExecutor.getSubSledContractId());
        if (cancelLimit > 0) {
            // 检测撤单次数限制
            int cancelledCount = this.getCancelledOrderCount(subExecutor.getSubSledContractId());
            if (cancelledCount >= cancelLimit) {
                errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_CANCELLED_TOO_MANY.getValue());
                return ;
            }
        }

        if (mCurrentPod != null) {
            mCurrentPod.onComposeLegOrderCancelled(subExecutor, latestOrder);
        } else {
            schedulePod(null);
        }
    }

    @Override
    protected void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (mCurrentPod == null) {
            schedulePod(null);
            return ;
        }

        mCurrentPod.onComposeLegOrderFinished(subExecutor, latestOrder);
        if (getTradedVolume() < mCurrentPod.getPodIndex() + mCurrentPod.getTargetVolume()) {
            return ;
        }

        checkSchedule();
    }

    @Override
    public void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo {
        if (mCurrentPod == null) {
            return ;
        }

        if (getTradedVolume() < mCurrentPod.getPodIndex() + mCurrentPod.getTargetVolume()) {
            mCurrentPod.updateTradeInfo(generatePodTradeRelatedItems()
                    , getExecutor().getLastestUnRelatedTradeLegInfos());
            return ;
        }

        checkSchedule();
    }

    private void checkSchedule() throws ErrorInfo {
        if (getExecutor().hasSubExecutorRunning()) {
            // 成交回来了，但是订单实际还未返回
            return ;
        }

        List<QuotationItem> latestQuotationItems = mCurrentPod.getLastestQuotationItems();
        // 当前的Pod已经全部完成
        mCurrentPod.onDestroy();
        mCurrentPod = null;

        Preconditions.checkState(getTradedVolume() < getOrderQuantity());

        // 当一个Pod成交后，剩下的调度Pod应该采用原有的模式
        getExecutor().clearResumeMode();
        schedulePod(latestQuotationItems);
    }


}
