package xueqiao.trade.hosting.history.app;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingEffectXQOrderIndexItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.events.XQOrderChangedEvent;
import xueqiao.trade.hosting.events.XQOrderCreatedEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.events.XQTradeListChangedEvent;
import xueqiao.trade.hosting.history.storage.HistoryDB;
import xueqiao.trade.hosting.history.storage.table.HostingXQOrderHisIndexTable;
import xueqiao.trade.hosting.history.storage.table.HostingXQTradeHisIndexTable;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexItem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HistoryMessageConsumer implements IMessageConsumer  {
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.RESERVE_QUEUE;
    }

    @Override
    public void onInit() throws Exception {
        int pageSize = 500;
        int pageIndex = 0;

        while(true) {
            QueryEffectXQOrderIndexPage indexPage
                    = ArbitrageStubFactory.getStub().getEffectXQOrderIndexPage(new QueryEffectXQOrderIndexOption()
                        , new IndexedPageOption().setPageIndex(pageIndex).setPageSize(pageSize));
            if (indexPage.getResultIndexItemsSize() <= 0) {
                break;
            }

            Set<String> batchOrderIds = new HashSet<String>(pageSize);
            for (HostingEffectXQOrderIndexItem indexItem : indexPage.getResultIndexItems()) {
                batchOrderIds.add(indexItem.getOrderId());
            }

            Map<String, HostingXQOrderWithTradeList> orderTradeListMap =
                    ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(batchOrderIds);
            for (HostingXQOrderWithTradeList orderTradeList : orderTradeListMap.values()) {
                handleOrder(orderTradeList.getOrder());
                handleTradeList(orderTradeList.getTradeList());
            }

            pageIndex += 1;
        }

    }

    private void handleOrder(HostingXQOrder order) throws ErrorInfo {
        if (StringUtils.isEmpty(order.getOrderId())) {
            AppLog.w("handleOrder called, but order's orderId is empty! order=" + order);
            return ;
        }
        if (!order.isSetOrderState()) {
            AppLog.w("handleOrder called, but order's orderState not set! order=" + order);
            return ;
        }
        if (!order.isSetOrderTarget()) {
            AppLog.w("handleOrder called, but order's orderTarget not set! order=" + order);
            return ;
        }
        if (!order.getOrderTarget().isSetTargetType() || !order.getOrderTarget().isSetTargetKey()) {
            AppLog.w("handleOrder called, but orderTarget's type or key is not set! order=" + order);
            return ;
        }
        if (!order.isSetCreateTimestampMs() || order.getCreateTimestampMs() <= 0) {
            AppLog.w("handleOrder called, but order's createTimestampMs not set or <=0 ! order=" + order);
            return ;
        }

        new DBStepHelper<Void>(HistoryDB.Global()) {
            private HostingXQOrderHisIndexItem dbIndexItem;
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                dbIndexItem = new HostingXQOrderHisIndexTable(getConnection()).getOrderHisIndexItem(order.getOrderId());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (dbIndexItem == null) {
                    HostingXQOrderHisIndexItem newItem = new HostingXQOrderHisIndexItem();
                    newItem.setSubAccountId(order.getSubAccountId());
                    newItem.setSubUserId(order.getSubUserId());
                    newItem.setOrderType(order.getOrderType());
                    newItem.setOrderTarget(order.getOrderTarget());
                    newItem.setOrderCreateTimestampMs(order.getCreateTimestampMs());
                    if (order.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLED
                            || order.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHED) {
                        newItem.setOrderEndTimestampMs(order.getOrderState().getStateTimestampMs());
                    }
                    newItem.setOrderId(order.getOrderId());

                    new HostingXQOrderHisIndexTable(getConnection()).addOrderHisIndexItem(newItem);
                    return ;
                }
                if (dbIndexItem.getOrderEndTimestampMs() > 0) {
                    return ;
                }

                if (order.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLED
                        || order.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_FINISHED) {
                    HostingXQOrderHisIndexItem updateItem = new HostingXQOrderHisIndexItem();
                    updateItem.setOrderId(order.getOrderId());
                    updateItem.setOrderEndTimestampMs(order.getOrderState().getStateTimestampMs());
                    new HostingXQOrderHisIndexTable(getConnection()).updateOrderHisIndexItem(updateItem);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    private void handleTradeList(List<HostingXQTrade> tradeList) throws ErrorInfo {
        if (tradeList == null || tradeList.isEmpty()) {
            return ;
        }

        for (HostingXQTrade trade : tradeList) {
            if (StringUtils.isEmpty(trade.getOrderId())) {
                AppLog.w("handleTradeList called, but have trade's orderId is empty! trade=" + trade);
            }

            if (!trade.isSetTradeId() || trade.getTradeId() <= 0) {
                AppLog.w("handleTradeList called, but have trade not set tradeId or tradeId <= 0! trade=" + trade);
                continue ;
            }
            if (!trade.isSetCreateTimestampMs() || trade.getCreateTimestampMs() <= 0) {
                AppLog.w("handleTradeList called, but have trade not set createTimestampMs or createTimestampMs <= 0! trade=" + trade);
                continue ;
            }

            new DBStepHelper<Void>(HistoryDB.Global()) {
                private HostingXQTradeHisIndexItem dbIndexItem;

                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    dbIndexItem = new HostingXQTradeHisIndexTable(getConnection())
                            .getTradeHisIndexItem(trade.getOrderId(), trade.getTradeId());
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    if (dbIndexItem != null) {
                        return ;
                    }

                    HostingXQTradeHisIndexItem newItem = new HostingXQTradeHisIndexItem();
                    newItem.setSubAccountId(trade.getSubAccountId());
                    newItem.setSubUserId(trade.getSubUserId());
                    newItem.setTradeTarget(trade.getTradeTarget());
                    newItem.setTradeCreateTimestampMs(trade.getCreateTimestampMs());
                    newItem.setOrderId(trade.getOrderId());
                    newItem.setTradeId(trade.getTradeId());
                    new HostingXQTradeHisIndexTable(getConnection()).addTradeHisIndexItem(newItem);
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();
        }
    }


    @consume(XQOrderCreatedEvent.class)
    ConsumeResult onHandleXQOrderCreatedEvent(XQOrderCreatedEvent event) throws ErrorInfo {
        if (!event.isSetCreatedOrder()) {
            AppLog.w("onHandleXQOrderCreatedEvent called, but event's createOrder not set!");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        handleOrder(event.getCreatedOrder());
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQOrderChangedEvent.class)
    ConsumeResult onHandleXQOrderChangedEvent(XQOrderChangedEvent event) throws ErrorInfo {
        if (!event.isSetChangedOrder()) {
            AppLog.w("onHandleXQOrderChangedEvent called, but event's changedOrder not set!");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        handleOrder(event.getChangedOrder());
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQTradeListChangedEvent.class)
    ConsumeResult onHandleXQTradeListChangedEvent(XQTradeListChangedEvent event) throws ErrorInfo {
        if (!event.isSetOrder()) {
            AppLog.w("onHandleXQTradeListChangedEvent called, but event's order not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        handleOrder(event.getOrder());
        handleTradeList(event.getTradeList());
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQOrderGuardEvent.class)
    ConsumeResult onHandleXQOrderGuardEvent(XQOrderGuardEvent event) throws TException {
        if (!event.isSetType()) {
            AppLog.w("onHandleXQOrderGuardEvent called, but event's type not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetOrderId() || StringUtils.isEmpty(event.getOrderId())) {
            AppLog.w("onHandleXQOrderGuardEvent called, but event's orderId not set or empty");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getType() == XQOrderGuardEventType.XQ_ORDER_CHANGED_GUARD
                || event.getType() == XQOrderGuardEventType.XQ_ORDER_CREATED_GUARD) {
            Map<String, HostingXQOrder> guardOrderMap
                    =  ArbitrageStubFactory.getStub().getXQOrders(new HashSet<String>(Arrays.asList(event.getOrderId())));
            if (guardOrderMap.containsKey(event.getOrderId())) {
                handleOrder(guardOrderMap.get(event.getOrderId()));
            }
        } else if (event.getType() == XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD) {
            Map<String, HostingXQOrderWithTradeList> guardOrderTradeListMap =
                ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(new HashSet<String>(Arrays.asList(event.getOrderId())));
            if (guardOrderTradeListMap.containsKey(event.getOrderId())) {
                HostingXQOrderWithTradeList orderTradeList = guardOrderTradeListMap.get(event.getOrderId());
                handleOrder(orderTradeList.getOrder());
                handleTradeList(orderTradeList.getTradeList());
            }
        }

        return ConsumeResult.CONSUME_OK;
    }
}
