package xueqiao.trade.hosting.arbitrage.core;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderEffectIndexItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.events.XQOrderExpiredEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

/**
 *  索引订单清理器
 * @author wangli
 *
 */
public class XQEffectOrderCleaner extends Thread {
    
    public XQEffectOrderCleaner() {
    }
    
    public void startWorking() {
        this.setName("order_cleaner");
        this.setPriority(MIN_PRIORITY);
        this.setDaemon(true);
        this.start();
    }
    
    public void runOnce(long cleanTimestampMs) throws Exception {
        final int pageIndex = 0;
        final int pageSize = 50;
        
        while(true) {
            List<XQOrderEffectIndexItem> indexItems 
                = new DBQueryHelper<List<XQOrderEffectIndexItem>>(ArbitrageDB.Global()) {
                @Override
                protected List<XQOrderEffectIndexItem> onQuery(Connection conn) throws Exception {
                    return new XQOrderEffectIndexTable(conn).getTtlTimestampBeforeItems(cleanTimestampMs
                            , new PageOption().setPageIndex(pageIndex).setPageSize(pageSize));
                }
            }.query();
            
            for (XQOrderEffectIndexItem indexItem : indexItems) {
                new DBTransactionHelperWithMsg<Void>(ArbitrageDB.Global()) {
                    @Override
                    public void onPrepareData() throws ErrorInfo, Exception {
                    }

                    @Override
                    public void onUpdate() throws ErrorInfo, Exception {
                        HostingXQOrder operateOrder = new HostingXQOrder();
                        operateOrder.setOrderId(indexItem.getOrderId());
                        operateOrder.setOrderState(new HostingXQOrderState());
                        operateOrder.getOrderState().setEffectIndexCleaned(true);
                        new XQOrderTable(getConnection()).updateOrder(operateOrder);
                        
                        new XQOrderEffectIndexTable(getConnection()).delIndexItem(indexItem.getOrderId());
                    }

                    @Override
                    public Void getResult() {
                        return null;
                    }

                    @SuppressWarnings("rawtypes")
                    @Override
                    protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                        XQOrderGuardEvent event = new XQOrderGuardEvent();
                        event.setOrderId(indexItem.getOrderId());
                        event.setType(XQOrderGuardEventType.XQ_ORDER_EXPIRED_GUARD);
                        return new SimpleEntry<TBase, IGuardPolicy>(event
                                , new TimeoutGuardPolicy().setTimeoutSeconds(2));
                    }
                    
                    @SuppressWarnings("rawtypes")
                    @Override
                    protected TBase prepareNotifyMessage() {
                        XQOrderExpiredEvent expiredEvent = new XQOrderExpiredEvent();
                        expiredEvent.setOrderId(indexItem.getOrderId());
                        return expiredEvent;
                    }

                    @Override
                    protected MessageAgent getMessageAgent() {
                        return HostingMessageContext.Global().getSenderAgent();
                    }
                    
                    @Override
                    protected void onCommitFinished() throws Exception {
                        super.onCommitFinished();
                        XQOrderManager.Global().rmExecutor(indexItem.getOrderId());
                    }
                    
                }.execute();
            }
            
            if (indexItems.size() < pageSize) {
                break;
            }
        }
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(5 * 60*1000);  // 休眠一定要在前面，留个其他模块在系统重启时一定的恢复时间
                long startTimestampMs = System.currentTimeMillis();
                runOnce(startTimestampMs);
                AppLog.w("[NOTICE]clean deal once escaped=" + (System.currentTimeMillis() - startTimestampMs) + "ms");
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                try { Thread.sleep(10000);} catch (InterruptedException e1) {}
            } 
        }
    }
    
}
