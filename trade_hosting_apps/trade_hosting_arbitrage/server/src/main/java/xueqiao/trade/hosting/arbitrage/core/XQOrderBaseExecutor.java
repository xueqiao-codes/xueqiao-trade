package xueqiao.trade.hosting.arbitrage.core;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderCancelledActor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderCancellingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderCreatedActor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderFinishedActor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderFinishingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderSuspendingActor;
import xueqiao.trade.hosting.arbitrage.core.data.CalculateTradesResult;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeRelatedTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeTable;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSubTradeSummary;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.events.XQOrderChangedEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.events.XQTradeListChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;


/**
 *  雪橇订单执行器
 */
public abstract class XQOrderBaseExecutor {
    private static final SimpleDateFormat TTL_TIMESTAMPMS_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    private volatile boolean mIsInitializeFinished;
    private boolean mIsDetroyed = false;
    
    private XQOrderContext mOrderContext;
    private HostingXQOrder mOrder;

    private Map<Long, XQOrderSubRecorder> mSubRecords = new HashMap<>();
    
    protected Map<Long, XQTradeWithRelatedItem> mTradeWithRelatedItems = new HashMap<>();
    protected Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> mUnRelatedExecTradeLegInfos;

    // 子类
    protected Map<Long, XQOrderSubExecutor> mSubExecutors = new HashMap<>();
    private IXQOrderActor mCurrentActor;

    //
    private long mGfdOrderEndTimestampMs = 0;
    
    public XQOrderBaseExecutor(HostingXQOrder order) throws ErrorInfo {
        Preconditions.checkNotNull(order);
        
        mOrder = order;
        mOrderContext = new XQOrderContext(mOrder.getOrderId(), mOrder.getSubUserId(), mOrder.getSubAccountId());
        mIsInitializeFinished = false;
    }
    
    public void checkOrder() throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotEmpty(mOrder.getOrderId()), "orderId should be set and not be empty");
        ParameterChecker.check(mOrder.isSetSubUserId() && mOrder.getSubUserId() > 0, "subUserId should be set and not be empty");
        ParameterChecker.check(mOrder.isSetSubAccountId() && mOrder.getSubAccountId() > 0, "subAccountId should be set and > 0");
        ParameterChecker.check(mOrder.isSetOrderTarget(), "orderTarget should be set");
        checkOrderTarget(mOrder.getOrderTarget());
        ParameterChecker.check(mOrder.isSetOrderDetail(), "orderDetail should be set");
        checkOrderDetail(mOrder.getOrderDetail());
    }

    public void setGfdOrderEndTimestampMs(long timestampMs) {
        AppLog.i("update xqOrderId=" + getOrder().getOrderId() + " GfdOrderEndTimestampMs=" + timestampMs);
        this.mGfdOrderEndTimestampMs = timestampMs;
    }

    public abstract boolean refreshTradeTimeSpanList() throws ErrorInfo;
    public abstract TradeTimeSpanList getTradeTimeSpanList();
    public XQOrderHelper.GFDEffectiveTimeSpan createGFDEffectiveTimeSpan(long processTimestampMs) {
        return null;
    }
    
    protected abstract long calCleanTimestampMs() throws ErrorInfo;
    protected abstract void checkOrderTarget(HostingXQTarget target) throws ErrorInfo;
    protected abstract void checkOrderDetail(HostingXQOrderDetail detail) throws ErrorInfo;
    
    public void checkInWorkThread() throws ErrorInfo {
        if (!mOrderContext.getWorkThread().isInCurrentThread()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "working thread not correct");
        }
    }

    public void initialize() throws ErrorInfo {
        Preconditions.checkNotNull(mOrder.getOrderState());
        
        checkInWorkThread();
        if (mOrder.getOrderState().getStateValue() != HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            restoreFromDB();
        }
        prepare();
        
        int originTradeTotalVolume = mOrder.getOrderTradeSummary().getTotalVolume();
        
        // 触发计算恢复
        if (mOrder.getOrderState().getStateValue() != HostingXQOrderStateValue.XQ_ORDER_FINISHED
                && mOrder.getOrderState().getStateValue() != HostingXQOrderStateValue.XQ_ORDER_FINISHING) {
            handleSubExecutorTradeListChanged();
        }
        
        if (originTradeTotalVolume != mOrder.getOrderTradeSummary().getTotalVolume()
                && mOrder.getOrderTradeSummary().getTotalVolume() >= getOrderTargetVolume()) {
            finish();
            return ;
        } 
        
        mCurrentActor = createActor(mOrder.getOrderState().getStateValue());
        if (mCurrentActor != null) {
            mCurrentActor.onCreate(this);
        }
        
        mIsInitializeFinished = true;
        
        if (AppLog.infoEnabled()) {
            AppLog.d("initalize finished, " + mOrder);
        }
    }

    public void innerErrorAction() {
        getContext().getWorkThread().postTask(new OrderExecutorRunnable(this) {
            @Override
            protected void onRun() throws Exception {
                if (getExecutor().suspendSupported()) {
                    getExecutor().suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                            , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_EXCEPTION_OCCURS.getValue());
                } else {
                    getExecutor().cancel(
                            TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_EXCEPTION_OCCURS.getValue());
                }
            }
        });
    }
    
    public boolean isInitializeFinished() {
        return mIsInitializeFinished;
    }
    
    public void destroy() throws ErrorInfo {
        checkInWorkThread();
        if (AppLog.infoEnabled()) {
            AppLog.i("destroy executor for " + mOrder.getOrderId());
        }
        
        if (mCurrentActor != null) {
            mCurrentActor.onDestroy();
        }
        
        mIsDetroyed = true;
    }
    
    public boolean isDetroyed() {
        return mIsDetroyed;
    }
    
    public HostingXQOrder getOrder() {
        return mOrder;
    }
    
    public void createNewOrderForTrigger(HostingXQOrder newOrder) throws ErrorInfo {
        mOrder = new XQOrderCreator(newOrder).doCreateFromSource(mOrder);
    }

    public XQOrderSubRecorder getSubRecorder(long sledContractId) throws ErrorInfo {
        checkInWorkThread();
        if (!mSubExecutors.containsKey(sledContractId)) {
            return null;
        }

        XQOrderSubRecorder subRecorder = mSubRecords.get(sledContractId);
        if (subRecorder == null) {
            subRecorder = new XQOrderSubRecorder(getContext(), sledContractId);
            mSubRecords.put(sledContractId, subRecorder);
        }

        return subRecorder;
    }
    
    public boolean hasSubExecutorRunning() throws ErrorInfo {
        checkInWorkThread();
        
        for (XQOrderSubExecutor subExecutor: mSubExecutors.values()) {
            if (subExecutor.hasOrderRunning()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isTradeListComplete() {
        for (XQOrderSubExecutor subExecutor : mSubExecutors.values()) {
            if (!subExecutor.isTradeListComplete()) {
                return false;
            }
        }
        return true;
    }
    
    public List<HostingExecOrder> getLastestOrders() throws ErrorInfo {
        return new SyncResult<List<HostingExecOrder>>(mOrderContext.getWorkThread()) {
            @Override
            protected List<HostingExecOrder> onCall() {
                List<HostingExecOrder> resultExecOrders= new ArrayList<HostingExecOrder>();
                for (Entry<Long, XQOrderSubExecutor> subExecutor : mSubExecutors.entrySet()) {
                    HostingExecOrder execOrder = subExecutor.getValue().getOrderMarketer().getLastestOrder();
                    if (execOrder != null) {
                        resultExecOrders.add(execOrder);
                    }
                }
                return resultExecOrders;
            }
        }.get();
    }

    public Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> getLastestUnRelatedTradeLegInfos() {
        Preconditions.checkNotNull(mUnRelatedExecTradeLegInfos);
        return mUnRelatedExecTradeLegInfos;
    }
    
    /**
     * 获取未关联的成交信息
     * 以合约ID为Key，同时以成交腿ID顺序排列
     */
    public Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> generateUnRelatedExecTradeLegInfos() {
        Map<Long, UnRelatedExecTradeLegInfo> unRelatedTradeLegInfos = new HashMap<Long, UnRelatedExecTradeLegInfo>();
        
        for (XQOrderSubExecutor subExecutor : mSubExecutors.values()) {
            for(List<HostingExecTradeLeg> tradeLegList : subExecutor.getRelatedExecTradeLegs().values()) {
                for (HostingExecTradeLeg tradeLeg : tradeLegList) {
                    UnRelatedExecTradeLegInfo tradeLegInfo = new UnRelatedExecTradeLegInfo();
                    tradeLegInfo.setTradeLeg(tradeLeg);
                    tradeLegInfo.setLeftVolume(tradeLeg.getTradeLegInfo().getLegTradeVolume());
                    unRelatedTradeLegInfos.put(tradeLeg.getExecTradeLegId(), tradeLegInfo);
                }
            }
        }

        if (AppLog.debugEnabled()) {
            StringBuilder logBuilder = new StringBuilder(256);
            logBuilder.append("BeforeFilter OrderId=").append(mOrder.getOrderId())
                    .append(", UnRelatedTradeLegInfos is");

            for (UnRelatedExecTradeLegInfo tradeLegInfo : unRelatedTradeLegInfos.values()) {
                logBuilder.append("\n").append(tradeLegInfo);
            }

            AppLog.d(logBuilder.toString());
        }
        
        for (XQTradeWithRelatedItem tradeWithRelatedItem : mTradeWithRelatedItems.values()) {
            for (XQTradeRelatedItem tradeRelatedItem : tradeWithRelatedItem.getRelatedItems().values()) {
                if (tradeRelatedItem.getExecTradeLegVolume() == 0) {
                    // 不参与交易的关联，直接跳过
                    continue;
                }

                UnRelatedExecTradeLegInfo tradeLegInfo = unRelatedTradeLegInfos.get(tradeRelatedItem.getExecTradeLegId());
                if (tradeLegInfo == null) {
                    AppLog.w("[WARNING]getUnRelatedExecTradeLegInfos has relatedItem but no tradeLegInfos for execTradeLegId="
                            + tradeRelatedItem.getExecTradeLegId() + ", xqOrderId=" + mOrder.getOrderId());
                    continue;
                }
                if (tradeRelatedItem.getRelatedTradeVolume() < 0
                        || tradeLegInfo.getLeftVolume() < tradeRelatedItem.getRelatedTradeVolume()) {
                    AppLog.w("[WARNING]getUnRelatedExecTradeLegInfos leftRelatedItem is not acceptable for execTradeLegId="
                            + tradeRelatedItem.getExecTradeLegId() + ", xqOrderId=" + mOrder.getOrderId());
                    continue;
                }
                
                tradeLegInfo.setLeftVolume(tradeLegInfo.getLeftVolume() - tradeRelatedItem.getRelatedTradeVolume());
                if (tradeLegInfo.getLeftVolume() <= 0) {
                    // 匹配量已经全部用完
                    unRelatedTradeLegInfos.remove(tradeRelatedItem.getExecTradeLegId());
                }
            }
        }
        
        Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> resultMap = new HashMap<Long, TreeSet<UnRelatedExecTradeLegInfo>>();
        for (UnRelatedExecTradeLegInfo tradeLegInfo : unRelatedTradeLegInfos.values()) {
            TreeSet<UnRelatedExecTradeLegInfo> tradeLegInfoList = resultMap.get(
                    tradeLegInfo.getTradeLeg().getLegContractSummary().getLegSledContractId());
            if (tradeLegInfoList == null) {
                tradeLegInfoList = new TreeSet<UnRelatedExecTradeLegInfo>();
                resultMap.put(tradeLegInfo.getTradeLeg().getLegContractSummary().getLegSledContractId(), tradeLegInfoList);
            }
            tradeLegInfoList.add(tradeLegInfo);
        }
        
        if (AppLog.debugEnabled()) {
            StringBuilder logBuilder = new StringBuilder(256);
            logBuilder.append("AfterFilter OrderId=").append(mOrder.getOrderId())
                      .append(", UnRelatedTradeLegInfos is");
            for (Entry<Long, TreeSet<UnRelatedExecTradeLegInfo>> tradeLegInfoEntry : resultMap.entrySet()) {
                for (UnRelatedExecTradeLegInfo tradeLegInfo : tradeLegInfoEntry.getValue()) {
                    logBuilder.append("\n").append(tradeLegInfo);
                }
            }
            AppLog.d(logBuilder.toString());
        }
        
        return resultMap;
    }
    
    // 汇总自成交信息
    public Map<String, HostingXQSubTradeSummary> generateSubTradeSummaries() {
        // 汇总所有的执行腿成交, 按照合约ID分类
        Map<Long, List<HostingExecTradeLeg>> totalTradeLegs = new HashMap<Long, List<HostingExecTradeLeg>>();
        for (XQOrderSubExecutor subExecutor : mSubExecutors.values()) {
            for (Entry<Long, List<HostingExecTradeLeg>> tradeLegsEntry 
                    : subExecutor.getRelatedExecTradeLegs().entrySet()) {
                List<HostingExecTradeLeg> tradeLegList = totalTradeLegs.get(tradeLegsEntry.getKey());
                if (tradeLegList == null) {
                    tradeLegList = new ArrayList<HostingExecTradeLeg>(tradeLegsEntry.getValue());
                    totalTradeLegs.put(tradeLegsEntry.getKey(), tradeLegList);
                } else {
                    tradeLegList.addAll(tradeLegsEntry.getValue());
                }
            }
        }
        
        Map<String, HostingXQSubTradeSummary> resultTradeSummaries = new HashMap<String, HostingXQSubTradeSummary>();
        for (Entry<Long, List<HostingExecTradeLeg>> tradeLegsEntry : totalTradeLegs.entrySet()) {
            HostingXQSubTradeSummary subTradeSummary = new HostingXQSubTradeSummary();
            
            HostingXQTarget subTarget = new HostingXQTarget();
            subTarget.setTargetType(HostingXQTargetType.CONTRACT_TARGET);
            subTarget.setTargetKey(String.valueOf(tradeLegsEntry.getKey()));
            subTradeSummary.setSubTarget(subTarget);
            
            calculateVolumeAndPrice(tradeLegsEntry.getValue(), subTradeSummary);
            
            resultTradeSummaries.put(XQOrderHelper.generateTargetUniqueKey(subTarget), subTradeSummary);
        }
        return resultTradeSummaries;
    }
    
    private void calculateVolumeAndPrice(List<HostingExecTradeLeg> tradeLegs, HostingXQSubTradeSummary subTradeSummary) {
        int totalVolume = 0;
        BigDecimal totalFee = new BigDecimal(0);
        
        for (HostingExecTradeLeg execTradeLeg : tradeLegs) {
            totalFee = totalFee.add(
                    new BigDecimal(execTradeLeg.getTradeLegInfo().getLegTradeVolume())
                           .multiply(new BigDecimal(String.valueOf(execTradeLeg.getTradeLegInfo().getLegTradePrice()))));
            totalVolume += execTradeLeg.getTradeLegInfo().getLegTradeVolume();
        }
        
        subTradeSummary.setSubTradeAveragePrice(totalFee.divide(new BigDecimal(totalVolume)
                , 16, BigDecimal.ROUND_HALF_UP).doubleValue());
        subTradeSummary.setSubTradeVolume(totalVolume);
    }
    
    public Map<Long, XQTradeWithRelatedItem> getTradeWithRelatedItems() {
        return mTradeWithRelatedItems;
    }
    
    public Map<Long, XQOrderSubExecutor> getSubExecutors() {
        return mSubExecutors;
    }

    public XQOrderSubExecutor getSubExecutor(long sledContractId) {
        return mSubExecutors.get(sledContractId);
    }

    private void restoreFromDB() throws ErrorInfo {
        new DBQueryHelper<Void>(ArbitrageDB.Global()){
            @Override
            protected Void onQuery(Connection conn) throws Exception {
                List<HostingXQTrade> orderTrades 
                        = new XQTradeTable(conn).getOrderTrades(mOrder.getOrderId());
                List<XQTradeRelatedItem> orderRelatedItems 
                        = new XQTradeRelatedTable(conn).getItemsByOrderId(mOrder.getOrderId());
                
                for (HostingXQTrade trade : orderTrades) {
                    XQTradeWithRelatedItem tradeWithRelatedItem = new XQTradeWithRelatedItem();
                    tradeWithRelatedItem.setTrade(trade);
                    mTradeWithRelatedItems.put(trade.getTradeId(), tradeWithRelatedItem);
                }
                
                for (XQTradeRelatedItem tradeRelatedItem : orderRelatedItems) {
                    XQTradeWithRelatedItem tradeWithRelatedItem = mTradeWithRelatedItems.get(tradeRelatedItem.getTradeId());
                    if (tradeWithRelatedItem == null) {
                        AppLog.w("[WARNING]Found relatedItem for tradeId=" + tradeRelatedItem.getTradeId() + ", but no trade");
                        continue;
                    }
                    
                    tradeWithRelatedItem.getRelatedItems().put(
                            tradeRelatedItem.getExecTradeLegId(), tradeRelatedItem);
                }
                return null;
            }
        }.query();
    }
    
    protected abstract boolean suspendSupported() ; // 是否支持手工暂停
    protected abstract void prepare() throws ErrorInfo;
    protected IXQOrderActor createActor(HostingXQOrderStateValue targetStateValue) {
        if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            return new XQOrderCreatedActor();
        } if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_CANCELLING) {
            return new XQOrderCancellingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_SUSPENDING) {
            return new XQOrderSuspendingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_CANCELLED) {
            return new XQOrderCancelledActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_FINISHED) {
            return new XQOrderFinishedActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_FINISHING) {
            return new XQOrderFinishingActor();
        }
        return  null;
    }
    
    protected abstract void calculateTrades(CalculateTradesResult result) throws ErrorInfo;
    protected abstract int getOrderTargetVolume();
    
    public void updateTradeInfo(HostingXQTradeSummary orderTradeSummary
            , List<XQTradeWithRelatedItem> tradeWithRelatedItemList) throws ErrorInfo {
        final boolean tradeListChanged = (tradeWithRelatedItemList != null && !tradeWithRelatedItemList.isEmpty());
        if (orderTradeSummary == null && !tradeListChanged) {
            return ;
        }
        
        new DBTransactionHelperWithMsg<Void>(ArbitrageDB.Global()) {
            private HostingXQOrder notifyOrder;
            private List<HostingXQTrade> notifyTrades;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                notifyOrder = new HostingXQOrder(mOrder);
                notifyTrades = new ArrayList<HostingXQTrade>();
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (orderTradeSummary != null) {
                    HostingXQOrder operatorOrder = new HostingXQOrder();
                    operatorOrder.setOrderId(mOrderContext.getOrderId());
                
                    operatorOrder.setOrderTradeSummary(orderTradeSummary);
                    notifyOrder.setOrderTradeSummary(orderTradeSummary);
                    operatorOrder.setVersion(mOrder.getVersion() + 1);
                    notifyOrder.setVersion(operatorOrder.getVersion());
                    
                    new XQOrderTable(getConnection()).updateOrder(operatorOrder);
                }
                
                if (tradeListChanged) {
                    XQTradeTable tradeTable = new XQTradeTable(getConnection());
                    XQTradeRelatedTable tradeRelatedTable = new XQTradeRelatedTable(getConnection());
                
                    for (XQTradeWithRelatedItem tradeWithRelatedItems : tradeWithRelatedItemList) {
                        tradeWithRelatedItems.getTrade().setCreateTimestampMs(System.currentTimeMillis());
                        tradeWithRelatedItems.getTrade().setSourceOrderTarget(getOrder().getOrderTarget());
                        tradeTable.addTrade(tradeWithRelatedItems.getTrade());
                        notifyTrades.add(tradeWithRelatedItems.getTrade());
                    
                        for (Entry<Long, XQTradeRelatedItem> tradeRelatedItemEntry 
                            : tradeWithRelatedItems.getRelatedItems().entrySet()) {
                            tradeRelatedItemEntry.getValue().setCreateTimestampMs(System.currentTimeMillis());
                            tradeRelatedTable.addRelatedItem(tradeRelatedItemEntry.getValue());
                        }
                    }
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                XQOrderGuardEvent guardEvent = new XQOrderGuardEvent();
                guardEvent.setOrderId(mOrderContext.getOrderId());
                if (tradeListChanged) {
                    guardEvent.setType(XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD);
                } else {
                    guardEvent.setType(XQOrderGuardEventType.XQ_ORDER_CHANGED_GUARD);
                }
                return new SimpleEntry<TBase, IGuardPolicy>(guardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(3));
            }
            
            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                if (tradeListChanged) {
                    XQTradeListChangedEvent event = new XQTradeListChangedEvent();
                    event.setOrder(notifyOrder);
                    event.setTradeList(notifyTrades);
                    return event;
                }
                
                XQOrderChangedEvent event = new XQOrderChangedEvent();
                event.setChangedOrder(notifyOrder);
                return event;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
            
            @Override
            protected void onCommitFinished() throws Exception {
                super.onCommitFinished();
                if (orderTradeSummary != null) {
                    mOrder.setOrderTradeSummary(orderTradeSummary);
                    mOrder.setVersion(mOrder.getVersion() + 1);
                }
                if (tradeListChanged) {
                    for (XQTradeWithRelatedItem tradeWithRelatedItem : tradeWithRelatedItemList) {
                        mTradeWithRelatedItems.put(tradeWithRelatedItem.getTrade().getTradeId()
                                , tradeWithRelatedItem);
                    }
                }
            }
        }.execute();
    }
    
    private void handleSubExecutorTradeListChanged() throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("handleSubExecutorTradeListChanged xqOrderId=" + getOrder().getOrderId());
        }
        CalculateTradesResult result = new CalculateTradesResult();
        calculateTrades(result);
        updateTradeInfo(result.getOrderTradeSummary(), result.getTradeWithRelatedItemsList());

        // 重新生成内部最新的未关联信息
        mUnRelatedExecTradeLegInfos = generateUnRelatedExecTradeLegInfos();
    }
    
    public XQOrderContext getContext() {
        return mOrderContext;
    }
   
    public void onExecOrderUpdated(HostingExecOrder execOrder) throws ErrorInfo {
        checkInWorkThread();
        if (AppLog.debugEnabled()) {
            AppLog.d("onExecOrderUpdated xqOrderId=" + mOrder.getOrderId()
                    + ", execOrder=" + execOrder);
        }
        
        long sledContractId = execOrder.getContractSummary().getSledContractId();
        XQOrderSubExecutor subExecutor = mSubExecutors.get(sledContractId);
        if (subExecutor == null) {
            AppLog.w("[WARNING]No SubExecutor for execOrderId=" + execOrder.getExecOrderId() 
                + ", sledContractId=" + sledContractId
                + ", xqOrderId=" + mOrder.getOrderId());
            return ;
        }
        if (subExecutor.orderUpdated(execOrder)) {
            Preconditions.checkState(execOrder == subExecutor.getOrderMarketer().getLastestOrder());
            if(mCurrentActor != null) {
                mCurrentActor.onLastestOrderChanged(subExecutor, execOrder);
            }
        }
    }
    
    public void onExecOrderTradeListChanged(HostingExecOrder execOrder, List<HostingExecTrade> newTradeList) throws ErrorInfo {
        checkInWorkThread();
        
        long sledContractId = execOrder.getContractSummary().getSledContractId();
        XQOrderSubExecutor subExecutor = mSubExecutors.get(sledContractId);
        if (subExecutor == null) {
            AppLog.w("[WARNING]No SubExecutor for execOrderId=" + execOrder.getExecOrderId() 
                    + ", sledContractId=" + sledContractId
                    + ", xqOrderId=" + mOrder.getOrderId());
            return ;
        }
        if (subExecutor.orderUpdated(execOrder)) {
            if (mCurrentActor != null) {
                mCurrentActor.onLastestOrderChanged(subExecutor, execOrder);
            }
        }
        
        if (subExecutor.orderTradeListChanged(newTradeList)) {
            handleSubExecutorTradeListChanged();
            if (mOrder.getOrderTradeSummary().getTotalVolume() >= getOrderTargetVolume()) {
                finish();
            }
            if (mCurrentActor != null) {
                mCurrentActor.onTradeListChanged(subExecutor);
            }
        }
    }
    
    public void finish() throws ErrorInfo {
        checkInWorkThread();

        if (mOrder.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHING
                || mOrder.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHED) {
            return ;
        }
        transferState(XQOrderHelper.createAndGenerateStateMsg(HostingXQOrderStateValue.XQ_ORDER_FINISHING));
    }

    public void finishCompleted() throws ErrorInfo {
        checkInWorkThread();

        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_FINISHING) {
            return ;
        }
        transferState(XQOrderHelper.generateStateMsg(
                XQOrderHelper.createOrderState(HostingXQOrderStateValue.XQ_ORDER_FINISHED)));
    }
    
    public void cancel(int cancelledErrorCode) throws ErrorInfo {
        cancel(cancelledErrorCode, null);
    }
    
    public void cancel(int cancelledErrorCode, String execUsefulMsg) throws ErrorInfo {
        checkInWorkThread();
        
        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLING
                || currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLED
                || currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHING
                || currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHED) {
            return ;
        }
        
        transferState(XQOrderHelper.generateStateMsg(
                XQOrderHelper.createOrderState(HostingXQOrderStateValue.XQ_ORDER_CANCELLING)
                    .setCancelledErrorCode(cancelledErrorCode).setExecUsefulMsg(execUsefulMsg)));
    }
    
    public void cancelFinished() throws ErrorInfo {
        checkInWorkThread();
        
        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_CANCELLING) {
            return ;
        }
        transferState(XQOrderHelper.generateStateMsg(
                XQOrderHelper.createOrderState(HostingXQOrderStateValue.XQ_ORDER_CANCELLED)
                .setCancelledErrorCode(mOrder.getOrderState().getCancelledErrorCode())
                .setExecUsefulMsg(mOrder.getOrderState().getExecUsefulMsg())));
    }

    public void start() throws ErrorInfo {
        start(HostingXQOrderResumeMode.RESUME_MODE_NONE);
    }
    
    public void start(HostingXQOrderResumeMode resumeMode) throws ErrorInfo {
        checkInWorkThread();

        if (resumeMode == null) {
            resumeMode = HostingXQOrderResumeMode.RESUME_MODE_NONE;
        }

        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_STARTING
                || currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_RUNNING) {
            return ;
        }
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_SUSPENDED
                && currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_STATE_CANNOT_OPERATION.getValue()
                    , "order state can not start");
        }
        transferState(XQOrderHelper.createAndGenerateStateMsg(HostingXQOrderStateValue.XQ_ORDER_STARTING)
                .setResumeMode(resumeMode));
    }

    public void clearResumeMode() throws ErrorInfo {
        transferState(new HostingXQOrderState(mOrder.getOrderState())
                .setResumeMode(HostingXQOrderResumeMode.RESUME_MODE_NONE));
    }

    public void startFinished() throws ErrorInfo {
        startFinished(HostingXQOrderResumeMode.RESUME_MODE_NONE);
    }

    public void startFinished(HostingXQOrderResumeMode resumeMode) throws ErrorInfo {
        checkInWorkThread();
        if (resumeMode == null) {
            resumeMode = HostingXQOrderResumeMode.RESUME_MODE_NONE;
        }

        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_STARTING) {
            return ;
        }
        transferState(XQOrderHelper.createAndGenerateStateMsg(HostingXQOrderStateValue.XQ_ORDER_RUNNING)
                .setResumeMode(resumeMode));
    }

    public void suspend(HostingXQSuspendReason reason, int suspendedErrorCode) throws ErrorInfo {
        suspend(reason, suspendedErrorCode, null);
    }
    
    public void suspend(HostingXQSuspendReason reason, int suspendedErrorCode, String execUsefulMsg) throws ErrorInfo {
        checkInWorkThread();
        Preconditions.checkArgument(reason != null && reason != HostingXQSuspendReason.SUSPENDED_REASON_NONE);
        
        if (!suspendSupported()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TYPE_SUSPEND_NOT_SUPPORTED.getValue()
                    , "suspend not supported");
        }
        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDING
                || currentOrderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            if (currentOrderState.getSuspendReason() == reason) {
                return ;
            }
            if (reason == HostingXQSuspendReason.SUSPENDED_BY_PERSON 
                    || reason == HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS) {
                transferState(XQOrderHelper.generateStateMsg(
                        XQOrderHelper.createOrderState(currentOrderState.getStateValue())
                        .setSuspendReason(reason).setSuspendedErrorCode(suspendedErrorCode)));
                if (mCurrentActor != null) {
                    mCurrentActor.onSuspendReasonChanged(reason);
                }
            }
            return ;
        }
        
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_RUNNING
                && currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_STARTING
                && currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_STATE_CANNOT_OPERATION.getValue()
                    , "state can not suspend");
        }
        
        transferState(XQOrderHelper.generateStateMsg(
                XQOrderHelper.createOrderState(HostingXQOrderStateValue.XQ_ORDER_SUSPENDING)
                .setSuspendReason(reason).setSuspendedErrorCode(suspendedErrorCode))
                .setExecUsefulMsg(execUsefulMsg));
    }
    
    public void suspendFinished() throws ErrorInfo {
        checkInWorkThread();
        
        HostingXQOrderState currentOrderState = mOrder.getOrderState();
        if (currentOrderState.getStateValue() != HostingXQOrderStateValue.XQ_ORDER_SUSPENDING) {
            return ;
        }
        
        transferState(XQOrderHelper.generateStateMsg(
                XQOrderHelper.createOrderState(HostingXQOrderStateValue.XQ_ORDER_SUSPENDED)
                .setSuspendReason(mOrder.getOrderState().getSuspendReason())
                .setSuspendedErrorCode(mOrder.getOrderState().getSuspendedErrorCode())));
    }
    
    public void updateCleanTimestampMs() throws ErrorInfo {
        long cleanTimestampMs = calCleanTimestampMs();
        if (cleanTimestampMs <= 0) {
            return ;
        }
        
        new DBStepHelper<Void>(ArbitrageDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new XQOrderEffectIndexTable(getConnection()).updateIndexItemTTL(
                        mOrder.getOrderId(), cleanTimestampMs);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
        
        if (AppLog.infoEnabled()) {
            AppLog.i("updateCleanTimestampMs for xqOrderId=" + mOrder.getOrderId()
                    + " ttlTimestamp is " + TTL_TIMESTAMPMS_FORMATTER.format(new Date(cleanTimestampMs)));
        }
    }
    
    public void transferState(HostingXQOrderState targetState) throws ErrorInfo {
        checkInWorkThread();
        
        if (AppLog.infoEnabled()) {
            AppLog.i("transferState xqOrderId=" + mOrder.getOrderId() + ", fromState="
                    + mOrder.getOrderState() + ", targetState=" + targetState);
        }
        
        boolean stateValueChanged = true;
        if (targetState.getStateValue() == mOrder.getOrderState().getStateValue()) {
            stateValueChanged = false;
        }
        
        new DBTransactionHelperWithMsg<Void>(ArbitrageDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }
            
            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingXQOrder operateOrder = new HostingXQOrder();
                operateOrder.setOrderId(mOrder.getOrderId());
                operateOrder.setOrderState(targetState);
                if (mGfdOrderEndTimestampMs > 0) {
                    operateOrder.setGfdOrderEndTimestampMs(mGfdOrderEndTimestampMs);
                }
                operateOrder.setVersion(mOrder.getVersion() + 1);
                new XQOrderTable(getConnection()).updateOrder(operateOrder);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                XQOrderGuardEvent guardEvent = new XQOrderGuardEvent();
                guardEvent.setType(XQOrderGuardEventType.XQ_ORDER_CHANGED_GUARD);
                guardEvent.setOrderId(mOrderContext.getOrderId());
                return new SimpleEntry<TBase, IGuardPolicy>(guardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }
            
            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                HostingXQOrder notifyOrder = new HostingXQOrder(mOrder);
                notifyOrder.setVersion(mOrder.getVersion() + 1);
                notifyOrder.setOrderState(targetState);
                if (mGfdOrderEndTimestampMs > 0) {
                    notifyOrder.setGfdOrderEndTimestampMs(mGfdOrderEndTimestampMs);
                }
                
                XQOrderChangedEvent event = new XQOrderChangedEvent();
                event.setChangedOrder(notifyOrder);
                return event;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();

        if (mGfdOrderEndTimestampMs > 0) {
            mOrder.setGfdOrderEndTimestampMs(mGfdOrderEndTimestampMs);
        }
        mOrder.setVersion(mOrder.getVersion() + 1);
        mOrder.setOrderState(targetState);
        
        if (!stateValueChanged) {
            return ;
        }
        if (mCurrentActor != null) {
            mCurrentActor.onDestroy();
        }
        mCurrentActor = createActor(targetState.getStateValue());
        if (mCurrentActor != null) {
            mCurrentActor.onCreate(this);
        }
    }
    
    public String generateActionOrderId() throws ErrorInfo {
        StringBuilder orderIdBuilder = new StringBuilder(64);
        orderIdBuilder.append(getOrder().getOrderId())
                      .append("_")
                      .append(IDGenerator.Global().generateUniqueIdForOrderId());
        return orderIdBuilder.toString();
    }

    public HostingXQOrderExecDetail getExecDetail() throws ErrorInfo {
        checkInWorkThread();

        HostingXQOrderExecDetail execDetail = new HostingXQOrderExecDetail();
        execDetail.setXqOrder(getOrder());
        execDetail.setXqTrades(new ArrayList<>());
        execDetail.setXqTradeRelatedItems(new HashMap<>());
        for (XQTradeWithRelatedItem tradeWithRelatedItem : getTradeWithRelatedItems().values()) {
            execDetail.getXqTrades().add(tradeWithRelatedItem.getTrade());

            List<HostingXQTradeRelatedItem> relatedItemList = new ArrayList<>();
            for (XQTradeRelatedItem dbTradeRelatedItem : tradeWithRelatedItem.getRelatedItems().values()) {
                relatedItemList.add(XQOrderHelper.dbRelatedItem2Hosting(dbTradeRelatedItem));
            }

            execDetail.getXqTradeRelatedItems().put(tradeWithRelatedItem.getTrade().getTradeId(), relatedItemList);
        }
        execDetail.setExecOrders(new ArrayList<>());
        execDetail.setExecTrades(new ArrayList<>());

        for (XQOrderSubExecutor subExecutor : getSubExecutors().values()) {
            execDetail.getExecTrades().addAll(subExecutor.getRelatedTrades().values());
            execDetail.getExecOrders().addAll(subExecutor.getOrderMarketer().getMarketOrders().values());
        }

        return execDetail;

    }
    
}
