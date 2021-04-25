package xueqiao.trade.hosting.arbitrage.core;

import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;
import com.mysql.jdbc.MysqlErrorNumbers;

import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderEffectIndexItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.events.XQOrderCreatedEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;



public class XQOrderCreator {
    
    private HostingXQOrder mNewOrder;
    
    public XQOrderCreator(HostingXQOrder newOrder) {
        Preconditions.checkNotNull(newOrder);
        this.mNewOrder = newOrder;
    }
    
    public XQOrderCreator checkOrder() throws ErrorInfo {
        XQOrderExecutorFactory.createExecutor(mNewOrder).checkOrder();
        return this;
    }
    
    public void doCreate() throws ErrorInfo {
        doCreateFromSource(null);
    }
    
    /**
     * 返回对sourceOrder的变更
     */
    public HostingXQOrder doCreateFromSource(HostingXQOrder sourceOrder) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(ArbitrageDB.Global()) {
            private HostingXQOrder createdOrder;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                createdOrder = new HostingXQOrder(mNewOrder);
                createdOrder.setOrderState(XQOrderHelper.createAndGenerateStateMsg(HostingXQOrderStateValue.XQ_ORDER_CREATED));
                createdOrder.setOrderTradeSummary(new HostingXQTradeSummary());
                createdOrder.setVersion(1);
                createdOrder.setCreateTimestampMs(System.currentTimeMillis());
                if (sourceOrder != null) {
                    createdOrder.setSubAccountId(sourceOrder.getSubAccountId());
                    createdOrder.setSubUserId(sourceOrder.getSubUserId());
                    createdOrder.setSourceOrderId(sourceOrder.getOrderId());
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                try {
                    new XQOrderTable(getConnection()).addOrder(createdOrder);
                } catch (SQLException e) {
                    AppLog.e("SQLState=" + e.getSQLState() + ", SQLErrorCode=" + e.getErrorCode() +", " + e.getMessage(), e);
                    if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                        throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_EXISTED.getValue()
                                , "order is already existed!");
                    }
                    throw e;
                }
                
                XQOrderEffectIndexItem effectIndexItem = new XQOrderEffectIndexItem();
                effectIndexItem.setOrderId(createdOrder.getOrderId());
                effectIndexItem.setSubAccountId(createdOrder.getSubAccountId());
                effectIndexItem.setSubUserId(createdOrder.getSubUserId());
                effectIndexItem.setCreateTimestampMs(System.currentTimeMillis());
                new XQOrderEffectIndexTable(getConnection()).addIndexItem(effectIndexItem);
                
                if (sourceOrder != null) {
                    HostingXQOrder operateSourceOrder = new HostingXQOrder();
                    operateSourceOrder.setOrderId(sourceOrder.getOrderId());
                    operateSourceOrder.setActionOrderId(createdOrder.getOrderId());
                    operateSourceOrder.setVersion(sourceOrder.getVersion() + 1);
                    new XQOrderTable(getConnection()).updateOrder(operateSourceOrder);
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
                guardEvent.setType(XQOrderGuardEventType.XQ_ORDER_CREATED_GUARD);
                guardEvent.setOrderId(createdOrder.getOrderId());
                return new SimpleEntry<TBase, IGuardPolicy>(guardEvent
                        , new TimeoutGuardPolicy().setTimeoutSeconds(1));
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                XQOrderCreatedEvent event = new XQOrderCreatedEvent();
                event.setCreatedOrder(createdOrder);
                return event;
            }
            
            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
            
            @Override
            protected void onCommitFinished() throws Exception {
                super.onCommitFinished();
                XQOrderManager.Global().addExecutor(
                        XQOrderExecutorFactory.createExecutor(createdOrder));
            }
            
        }.execute();
        
        if (sourceOrder == null) {
            return null;
        }
        
        HostingXQOrder updateSourceOrder = new HostingXQOrder(sourceOrder);
        updateSourceOrder.setVersion(sourceOrder.getVersion() + 1);
        updateSourceOrder.setActionOrderId(mNewOrder.getOrderId());
        return updateSourceOrder;
    }
    
}
