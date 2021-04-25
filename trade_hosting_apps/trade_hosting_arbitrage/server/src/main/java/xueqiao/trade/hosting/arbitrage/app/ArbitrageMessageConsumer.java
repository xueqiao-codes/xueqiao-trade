package xueqiao.trade.hosting.arbitrage.app;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderExecutorFactory;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderEffectIndexItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecOrderItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecTradeItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecTradeTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.events.ExecOrderCreatedEvent;
import xueqiao.trade.hosting.events.ExecOrderExpiredEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.events.ExecOrderRunningEvent;
import xueqiao.trade.hosting.events.ExecOrderVerifyFailedEvent;
import xueqiao.trade.hosting.events.ExecOrderVerifySuccessEvent;
import xueqiao.trade.hosting.events.ExecTradeListChangedEvent;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArbitrageMessageConsumer implements IMessageConsumer {

    private static Set<HostingExecOrderStateValue> FILTER_FLUSH_EXEC_ORDER_STATES = new HashSet<>();

    static {
        // 这些状态，对于雪橇订单恢复时的判断并无作用
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_SLED_ALLOC_REF_FINISHED);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_SLED_SEND_UNKOWN);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_WAITING_REVOKE);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_WAITING_REVOKE);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN);
        FILTER_FLUSH_EXEC_ORDER_STATES.add(HostingExecOrderStateValue.ORDER_UPSIDE_REVOKE_RECEIVED);
    }

    private IHostingDealingApi mDealingApi;
    
    public ArbitrageMessageConsumer() throws ErrorInfo {
        mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        initilizeAllEffectXQOrders();
    }
    
    private void initilizeAllEffectXQOrders() throws ErrorInfo {
        new DBQueryHelper<Void>(ArbitrageDB.Global()) {
            @Override
            protected Void onQuery(Connection conn) throws Exception {
                List<XQOrderEffectIndexItem> allIndexEntries = new XQOrderEffectIndexTable(conn).getAll();
                int batchSize = 1000;
                int index = 0;
                
                while(index * batchSize < allIndexEntries.size()) {
                    Set<String> batchOrderIds = new HashSet<String>();
                    
                    int startEntryIndex = index * batchSize;
                    int endEntryIndex = (index+1) * batchSize;
                    if (endEntryIndex > allIndexEntries.size()) {
                        endEntryIndex = allIndexEntries.size();
                    }
                    
                    for (int entryIndex = startEntryIndex; entryIndex < endEntryIndex; ++entryIndex) {
                        batchOrderIds.add(allIndexEntries.get(entryIndex).getOrderId());
                    }
                    if (batchOrderIds.isEmpty()) {
                        break;
                    }
                    
                    List<HostingXQOrder> orderList = new XQOrderTable(conn).getOrdersList(batchOrderIds);
                    for (HostingXQOrder order : orderList) {
                        XQOrderManager.Global().addExecutor(XQOrderExecutorFactory.createExecutor(order));
                    }
                    index += 1;
                }
                return null;
            }
        }.query();
    }
    
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.RESERVE_QUEUE;
    }
    
    private void refreshAllEffectiveOrders() throws ErrorInfo {
        List<XQOrderBaseExecutor> allExecutors = XQOrderManager.Global().getAllExecutors();
        for (XQOrderBaseExecutor executor : allExecutors) {
            List<HostingExecOrder> lastestOrders = executor.getLastestOrders();
            for (HostingExecOrder waitingUpdateOrder : lastestOrders) {
                HostingExecOrder newestOrder 
                    = mDealingApi.getOrder(waitingUpdateOrder.getExecOrderId());
                if (newestOrder == null) {
                    AppLog.w("[WARNING]Failed to found execOrder=" + waitingUpdateOrder.getExecOrderId());
                    continue;
                }
                handleExecOrderTradeListChanged(newestOrder
                        , mDealingApi.getOrderTrades(newestOrder.getExecOrderId()));
            }
        }
   }

    @Override
    public void onInit() throws Exception {
        AppLog.i("onInit...");
        refreshAllEffectiveOrders();
    }
    
    private String getXQOrderId(HostingExecOrder execOrder) {
        if (!execOrder.getSource().startsWith("XQ|")) {
            return null;
        }
        if (execOrder.getSource().length() < 4) {
            return null;
        }
        return execOrder.getSource().substring(3);
    }
    
    private void handleExecOrderTradeListChanged(HostingExecOrder execOrder, List<HostingExecTrade> tradeList) throws ErrorInfo {
        String xqOrderId = getXQOrderId(execOrder);
        if (StringUtils.isEmpty(xqOrderId)) {
            return ;
        }
        if (tradeList == null || tradeList.isEmpty()) {
            return ;
        }
        
        execOrder.getStateInfo().unsetHistoryStates();
        execOrder.unsetNotifyStateHandleInfos();

        boolean updated = new DBTransactionHelper<Boolean>(ArbitrageDB.Global()) {
            private boolean needUpdate = false;
            private XQOrderRelatedExecOrderItem relatedExecOrderItem;
            private List<HostingExecTrade> unRelatedExecTradeList = new ArrayList<HostingExecTrade>(); 
            
            private boolean isInRelated(List<XQOrderRelatedExecTradeItem> relatedExecTradeItemList, HostingExecTrade execTrade) {
                for (XQOrderRelatedExecTradeItem relatedItem : relatedExecTradeItemList) {
                    if (relatedItem.getExecTradeId() == execTrade.getExecTradeId()) {
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                relatedExecOrderItem = new XQOrderRelatedExecOrderTable(getConnection())
                        .getItemByExecOrderId(xqOrderId, execOrder.getExecOrderId(), true);
                if (relatedExecOrderItem == null) {
                    AppLog.w("[WARING]Failed to found relatedExecOrderItem for xqOrderId=" + xqOrderId 
                            + ",execOrderId=" + execOrder.getExecOrderId());
                    return ;
                }
                List<XQOrderRelatedExecTradeItem> relatedExecTradeItemList 
                    = new XQOrderRelatedExecTradeTable(getConnection()).getItems(xqOrderId
                            , execOrder.getContractSummary().getSledContractId());
                for (HostingExecTrade execTrade : tradeList) {
                    if (!isInRelated(relatedExecTradeItemList, execTrade)) {
                        unRelatedExecTradeList.add(execTrade);
                    }
                }
                
                if (relatedExecOrderItem.getRelatedExecOrder().getVersion() < execOrder.getVersion()
                        && unRelatedExecTradeList.isEmpty()) {
                    return ;
                }
                needUpdate = true;
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!needUpdate) {
                    return ;
                }
                
                if (relatedExecOrderItem.getRelatedExecOrder().getVersion() < execOrder.getVersion()) {
                    XQOrderRelatedExecOrderItem operateOrderRelatedItem = new XQOrderRelatedExecOrderItem();
                    operateOrderRelatedItem.setOrderId(xqOrderId);
                    operateOrderRelatedItem.setExecOrderId(execOrder.getExecOrderId());
                    operateOrderRelatedItem.setRelatedExecOrder(execOrder);
                    new XQOrderRelatedExecOrderTable(getConnection()).updateItem(operateOrderRelatedItem);
                }
                
                XQOrderRelatedExecTradeTable relatedTradeTable = new XQOrderRelatedExecTradeTable(getConnection());
                for (HostingExecTrade unRelatedExecTrade : unRelatedExecTradeList) {
                    XQOrderRelatedExecTradeItem operateTradeRelatedItem = new XQOrderRelatedExecTradeItem();
                    operateTradeRelatedItem.setOrderId(xqOrderId);
                    operateTradeRelatedItem.setExecOrderId(unRelatedExecTrade.getExecOrderId());
                    operateTradeRelatedItem.setExecTradeId(unRelatedExecTrade.getExecTradeId());
                    operateTradeRelatedItem.setSledContractId(unRelatedExecTrade.getContractSummary().getSledContractId());
                    operateTradeRelatedItem.setRelatedExecTrade(unRelatedExecTrade);
                    operateTradeRelatedItem.setCreateTimestampMs(System.currentTimeMillis());
                    relatedTradeTable.addItem(operateTradeRelatedItem);
                }
            }

            @Override
            public Boolean getResult() {
                return needUpdate;
            }
            
        }.execute().getResult();
        
        if (!updated) {
            AppLog.d("NoUpdate from xqOrderId=" + xqOrderId + ", execOrderId=" 
                    + execOrder.getExecOrderId() +  ", execOrder's version=" + execOrder.getVersion());
            return ;
        }
        
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(xqOrderId);
        if (executor == null) {
            AppLog.i("Executor is not effective for xqOrderId=" + xqOrderId);
            return ;
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            public void onRun() throws ErrorInfo {
                getExecutor().onExecOrderTradeListChanged(execOrder, tradeList);
            }
        });
    }
    
    private void handleExecOrderUpdated(HostingExecOrder execOrder) throws ErrorInfo {
        String xqOrderId = getXQOrderId(execOrder);
        if (StringUtils.isEmpty(xqOrderId)) {
            return ;
        }
        
        execOrder.getStateInfo().unsetHistoryStates();
        execOrder.unsetNotifyStateHandleInfos();

        // 部分订单的状态对于业务逻辑而言恢复意义不大

        Boolean updated = new DBTransactionHelper<Boolean>(ArbitrageDB.Global()) {
            private boolean hasUpdated = false;
            private boolean needFlushDB = true;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                XQOrderRelatedExecOrderItem relatedItem 
                    = new XQOrderRelatedExecOrderTable(getConnection()).getItemByExecOrderId(xqOrderId, execOrder.getExecOrderId(), true);
                if (relatedItem == null) {
                    AppLog.w("[WARING]Failed to found relatedExecOrderItem for xqOrderId=" + xqOrderId 
                            + ",execOrderId=" + execOrder.getExecOrderId());
                    return ;
                }
                if (relatedItem.getRelatedExecOrder().getVersion() >= execOrder.getVersion()) {
                    AppLog.i("xqOrderId=" + xqOrderId + " relatedItem execOrderId=" + execOrder.getExecOrderId()
                            + ", version=" + relatedItem.getRelatedExecOrder().getVersion() 
                            + " >= execOrder'version=" + execOrder.getVersion());;
                    return ;
                }
                hasUpdated = true;

                HostingExecOrderState currentOrderState = execOrder.getStateInfo().getCurrentState();
                if (FILTER_FLUSH_EXEC_ORDER_STATES.contains(currentOrderState.getValue())) {
                    needFlushDB = false;
                }

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!hasUpdated || !needFlushDB) {
                    return ;
                }

                XQOrderRelatedExecOrderItem operateRelatedItem = new XQOrderRelatedExecOrderItem();
                operateRelatedItem.setOrderId(xqOrderId);
                operateRelatedItem.setExecOrderId(execOrder.getExecOrderId());
                operateRelatedItem.setRelatedExecOrder(execOrder);
                
                new XQOrderRelatedExecOrderTable(getConnection()).updateItem(operateRelatedItem);
            }

            @Override
            public Boolean getResult() {
                return hasUpdated;
            }
            
        }.execute().getResult();
        
        if (!updated) {
            AppLog.d("NoUpdate from xqOrderId=" + xqOrderId + ", execOrderId=" 
                    + execOrder.getExecOrderId() +  ", execOrder's version=" + execOrder.getVersion());
            return ;
        }
        
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(xqOrderId);
        if (executor == null) {
            AppLog.i("Executor is not effective for xqOrderId=" + xqOrderId);
            return ;
        }
        
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            public void onRun() throws ErrorInfo {
                getExecutor().onExecOrderUpdated(execOrder);
            }
        });
    }
    
    @consume(ExecOrderCreatedEvent.class) 
    ConsumeResult onHandleExecOrderCreatedEvent(ExecOrderCreatedEvent event) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderCreatedEvent event=" + event);
        }
        if (!event.isSetCreatedOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderUpdated(event.getCreatedOrder());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecOrderVerifySuccessEvent.class)
    ConsumeResult onHandleExecOrderVerifySuccessEvent(ExecOrderVerifySuccessEvent event) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderVerifySuccessEvent event=" + event);
        }
        
        if (!event.isSetVerifySuccessOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderUpdated(event.getVerifySuccessOrder());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecOrderVerifyFailedEvent.class)
    ConsumeResult onHandleExecOrderVerifyFailedEvent(ExecOrderVerifyFailedEvent event) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderVerifyFailedEvent event=" + event);
        }
        
        if (!event.isSetVerifyFailedOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderUpdated(event.getVerifyFailedOrder());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecOrderRunningEvent.class)
    ConsumeResult onHandleExecOrderRunningEvent(ExecOrderRunningEvent event) throws Exception {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderRunningEvent event=" + event);
        }
        
        if (!event.isSetRunningOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderUpdated(event.getRunningOrder());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecTradeListChangedEvent.class)
    ConsumeResult onHandleExecTradeListChangedEvent(ExecTradeListChangedEvent event) throws Exception {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecTradeListChangedEvent event=" + event);
        }
        
        if (!event.isSetExecOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetNewTradeList()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderTradeListChanged(event.getExecOrder(), event.getNewTradeList());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecOrderExpiredEvent.class)
    ConsumeResult onHandleExecOrderExpiredEvent(ExecOrderExpiredEvent event) throws Exception {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderExpiredEvent event=" + event);
        }
        if (!event.isSetExpiredOrder()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        handleExecOrderUpdated(event.getExpiredOrder());
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ExecOrderGuardEvent.class)
    ConsumeResult onHandleExecOrderGuardEvent(ExecOrderGuardEvent event) throws Exception {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleExecOrderGuardEvent event=" + event);
        }
        
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleExecOrderGuardEvent event=" + event);
        }
        if (!event.isSetGuardType()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetGuardExecOrderId()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        HostingExecOrder execOrder = mDealingApi.getOrder(event.getGuardExecOrderId());
        if (execOrder == null) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        if (event.getGuardType() == ExecOrderGuardType.GUARD_ORDER_TRADE_LIST_CHANGED) {
            handleExecOrderTradeListChanged(execOrder, mDealingApi.getOrderTrades(execOrder.getExecOrderId()));
        } else {
            handleExecOrderUpdated(execOrder);
        }
        
        return ConsumeResult.CONSUME_OK;
    }
}
