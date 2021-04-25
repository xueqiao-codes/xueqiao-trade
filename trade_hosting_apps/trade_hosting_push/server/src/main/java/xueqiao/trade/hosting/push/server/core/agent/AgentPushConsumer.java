package xueqiao.trade.hosting.push.server.core.agent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageVariable;
import xueqiao.trade.hosting.arbitrage.thriftapi.client.TradeHostingArbitrageStub;
import xueqiao.trade.hosting.asset.thriftapi.HostingPositionVolumePage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingPositionVolumeOption;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;
import xueqiao.trade.hosting.events.HostingAssetGuardEventType;
import xueqiao.trade.hosting.events.HostingPositionGuardEvent;
import xueqiao.trade.hosting.events.HostingPositionVolumeEvent;
import xueqiao.trade.hosting.events.PositionAdjustGuardEvent;
import xueqiao.trade.hosting.events.PositionAdjustGuardEventType;
import xueqiao.trade.hosting.events.PositionEditLockEvent;
import xueqiao.trade.hosting.events.StatPositionEventType;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedEvent;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedGuardEvent;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEvent;
import xueqiao.trade.hosting.events.TaskNoteCreatedEvent;
import xueqiao.trade.hosting.events.TaskNoteDeletedEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEventType;
import xueqiao.trade.hosting.events.XQCLOSettingsChangedEvent;
import xueqiao.trade.hosting.events.XQOrderChangedEvent;
import xueqiao.trade.hosting.events.XQOrderCreatedEvent;
import xueqiao.trade.hosting.events.XQOrderExpiredEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.events.XQTradeListChangedEvent;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;
import xueqiao.trade.hosting.position.adjust.thriftapi.helper.PositionAdjustStubFactory;
import xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.StatPositionSummaryPage;
import xueqiao.trade.hosting.position.statis.helper.PositionStatisStubFactory;
import xueqiao.trade.hosting.push.server.core.SubAccountRelatedCacheHolder;
import xueqiao.trade.hosting.push.server.core.seq.SeqPushManager;
import xueqiao.trade.hosting.tasknote.helper.TaskNoteStubFactory;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AgentPushConsumer implements IMessageConsumer {
    private static File ARBITRAGE_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingArbitrageVariable.serviceKey + ".sock");
    
    
    public AgentPushConsumer() {
    }
    
    private TradeHostingArbitrageStub  getArbitrageStub() {
        TradeHostingArbitrageStub stub = new TradeHostingArbitrageStub();
        stub.setSocketFile(ARBITRAGE_STUB_SOCKET_FILE);
        return stub;
    }
    
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
        SubAccountRelatedCacheHolder.get().sync();
    	SeqPushManager.Global().onPushMessageAgentInit();
    }
    
    @SuppressWarnings("rawtypes")
    private void pushMessageBySubAccountId(long subAccountId, TBase msg) {
        Set<Integer> subUserIds = SubAccountRelatedCacheHolder.get().getRelatedSubUserIds(subAccountId);
        if (subUserIds == null) {
            return ;
        }
        for (Integer subUserId : subUserIds) {
            SeqPushManager.Global().push(subUserId, msg);
        }
    }


    @consume(SubAccountRelatedInfoChangedEvent.class)
    ConsumeResult onHandleSubAccountRelatedInfoChangedEvent(SubAccountRelatedInfoChangedEvent event) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleSubAccountRelatedInfoChangedEvent received " + event);
        }
        return SubAccountRelatedCacheHolder.get().handleSubAccountRelatedInfoChangedEvent(event);
    }
    
    @consume(XQOrderCreatedEvent.class)
    ConsumeResult onHandleXQOrderCreatedEvent(XQOrderCreatedEvent event) {
        if (!event.isSetCreatedOrder()) {
            AppLog.w("onHandleXQOrderCreatedEvent called, but event's createdOrder not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        pushMessageBySubAccountId(event.getCreatedOrder().getSubAccountId(), event);
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(XQOrderChangedEvent.class)
    ConsumeResult onHandleXQOrderChangedEvent(XQOrderChangedEvent event) {
        if (!event.isSetChangedOrder()) {
            AppLog.w("onHandleXQOrderChangedEvent called, but event's changedOrder not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        pushMessageBySubAccountId(event.getChangedOrder().getSubAccountId(), event);
        return ConsumeResult.CONSUME_OK;
    }
    
    private long getSubAccountIdFromXQOrderId(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return 0;
        }
        
        String[] splits = StringUtils.split(orderId, '_');
        if (splits == null || splits.length < 4) {
            return 0;
        }
        
        try {
            return NumberUtils.createLong(splits[1]);
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
        return 0;
    }
    
    @consume(XQOrderExpiredEvent.class)
    ConsumeResult onHandleXQOrderExpiredEvent(XQOrderExpiredEvent event) {
        if (StringUtils.isEmpty(event.getOrderId())) {
            AppLog.w("onHandleXQOrderExpiredEvent called, but event's orderId is empty");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        long subAccountId = getSubAccountIdFromXQOrderId(event.getOrderId());
        if (subAccountId == 0) {
            AppLog.w("Failed to parse subAccountId for xqOrderId=" + event.getOrderId());
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        pushMessageBySubAccountId(subAccountId, event);
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(XQTradeListChangedEvent.class)
    ConsumeResult onHandleXQTradeListChangedEvent(XQTradeListChangedEvent event) {
        if (!event.isSetOrder()) {
            AppLog.w("onHandleXQTradeListChangedEvent called, but event's order is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        pushMessageBySubAccountId(event.getOrder().getSubAccountId(), event);
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(XQOrderGuardEvent.class)
    ConsumeResult onHandleXQOrderGuardEvent(XQOrderGuardEvent event) throws ErrorInfo, TException {
        if (!event.isSetType() || StringUtils.isEmpty(event.getOrderId())) {
            AppLog.w("onHandleXQOrderGuardEvent called, but event's type not set or orderId is empty");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        Map<String, HostingXQOrder> orderMap  
            = getArbitrageStub().getXQOrders(new HashSet<String>(Arrays.asList(event.getOrderId())));
        if (orderMap == null || !orderMap.containsKey(event.getOrderId())) {
            AppLog.w("[WARNING]Failed to get order for event=" + event);
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        HostingXQOrder order = orderMap.get(event.getOrderId());
        
        if (event.getType() == XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD) {
            XQTradeListChangedEvent notifyEvent = new XQTradeListChangedEvent();
            notifyEvent.setOrder(order);
            Map<String, List<HostingXQTrade>> tradeListMap
                = getArbitrageStub().getXQTrades(new HashSet<String>(Arrays.asList(event.getOrderId())));
            if (tradeListMap != null && tradeListMap.containsKey(event.getOrderId())) {
                notifyEvent.setTradeList(tradeListMap.get(event.getOrderId()));
            }
            
            pushMessageBySubAccountId(order.getSubAccountId(), notifyEvent);
            return ConsumeResult.CONSUME_OK;
        } 
        
        if (order.getOrderState().getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            XQOrderCreatedEvent createdEvent = new XQOrderCreatedEvent();
            createdEvent.setCreatedOrder(order);
            pushMessageBySubAccountId(order.getSubAccountId(), createdEvent);
        } else {
            if (order.getOrderState().isEffectIndexCleaned()) {
                XQOrderExpiredEvent expiredEvent = new XQOrderExpiredEvent();
                expiredEvent.setOrderId(event.getOrderId());
                pushMessageBySubAccountId(order.getSubAccountId(), expiredEvent);
            } else {
                XQOrderChangedEvent changedEvent = new XQOrderChangedEvent();
                changedEvent.setChangedOrder(order);
                pushMessageBySubAccountId(order.getSubAccountId(), changedEvent);
            }
        }
        
        return ConsumeResult.CONSUME_OK;
    }

    @consume(HostingPositionVolumeEvent.class)
    ConsumeResult onHandlePositionVolumeEvent(HostingPositionVolumeEvent event) {
        if (!event.isSetPositionVolume()) {
            AppLog.w("onHandlePositionVolumeEvent called, but event's positionVolume not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        pushMessageBySubAccountId(event.getPositionVolume().getSubAccountId(), event);
        return ConsumeResult.CONSUME_OK;
    }

    @consume(HostingPositionGuardEvent.class)
    ConsumeResult onHandlePositionGuardEvent(HostingPositionGuardEvent event) throws TException {
        if (!event.isSetType() || !event.isSetSledContractId() || !event.isSetSubAccountId()) {
            AppLog.w("onHandlePositionGuardEvent called, but event's type or sledContractId or subAccountId not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getType() == HostingAssetGuardEventType.HOSTING_POSITION_CHANGED_GUARD) {
            ReqHostingPositionVolumeOption qryOption = new ReqHostingPositionVolumeOption();
            qryOption.setSledContractId(new HashSet<Long>(Arrays.asList(event.getSledContractId())));
            qryOption.setSubAccountId(event.getSubAccountId());

            HostingPositionVolumePage positionVolumePage
                    = AssetStubFactory.getStub().getHostingPositionVolume(qryOption, new IndexedPageOption().setPageIndex(0).setPageSize(1));
            if (positionVolumePage.getPageSize() <= 0) {
                AppLog.i("onHandlePositionGuardEvent called, event's sledContractId=" + event.getSledContractId()
                        +  ", event's subAccountId=" + event.getSubAccountId() + " no position is found");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }

            HostingPositionVolumeEvent volumeEvent = new HostingPositionVolumeEvent();
            volumeEvent.setPositionVolume(positionVolumePage.getPage().get(0));
            volumeEvent.setEventCreateTimestampMs(System.currentTimeMillis());
            pushMessageBySubAccountId(event.getSubAccountId(), volumeEvent);
        }

        return ConsumeResult.CONSUME_OK;
    }

    @consume(StatPositionSummaryChangedEvent.class)
    ConsumeResult onHandleStatPositionSummaryChangedEvent(StatPositionSummaryChangedEvent event)  {
        if (!event.isSetStatPositionSummary()) {
            AppLog.w("onHandleStatPositionSummaryChangedEvent called, but statPositionSummary is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (!event.getStatPositionSummary().isSetSubAccountId()) {
            AppLog.w("onHandleStatPositionSummaryChangedEvent called, but statPositionSummary's subAccountId is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        pushMessageBySubAccountId(event.getStatPositionSummary().getSubAccountId(), event);
        return ConsumeResult.CONSUME_OK;
    }

    @consume(StatPositionSummaryChangedGuardEvent.class)
    ConsumeResult onHandlStatPositionSummaryChangedGuardEvent(StatPositionSummaryChangedGuardEvent event) throws TException {
        if (!event.isSetSubAccountId() || event.getSubAccountId() <= 0) {
            AppLog.w("onHandlStatPositionSummaryChangedGuardEvent called, but event's subAccountId is not set or <= 0");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetTargetType()) {
            AppLog.w("onHandlStatPositionSummaryChangedGuardEvent called, but event's targetType is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetTargetKey() || StringUtils.isEmpty(event.getTargetKey())) {
            AppLog.w("onHandlStatPositionSummaryChangedGuardEvent called, but event's targetKey not set or empty");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        QueryStatPositionSummaryOption qryOption = new QueryStatPositionSummaryOption();
        qryOption.setSubAccountId(event.getSubAccountId())
                 .setTargetType(event.getTargetType())
                 .setTargetKey(event.getTargetKey());
        StatPositionSummaryPage summaryPage = PositionStatisStubFactory.getStub().queryStatPositionSummaryPage(qryOption
                , new IndexedPageOption().setPageIndex(0).setPageSize(1));

        StatPositionSummaryChangedEvent changedEvent = new StatPositionSummaryChangedEvent();
        changedEvent.setSubAccountId(event.getSubAccountId());
        if (summaryPage.getStatPositionSummaryListSize() <= 0) {
            changedEvent.setEventType(StatPositionEventType.STAT_ITEM_REMOVED);
        } else {
            changedEvent.setStatPositionSummary(summaryPage.getStatPositionSummaryList().get(0));
            changedEvent.setEventType(StatPositionEventType.STAT_ITEM_UPDATED);
        }
        changedEvent.setEventCreateTimestampMs(System.currentTimeMillis());

        pushMessageBySubAccountId(event.getSubAccountId(), changedEvent);
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQCLOSettingsChangedEvent.class)
    ConsumeResult onHandleXQCLOSettingsChangedEvent(XQCLOSettingsChangedEvent event) {
        if (StringUtils.isEmpty(event.getKey())) {
            AppLog.w("onHandleXQCLOSettingsChangedEvent called, but event's key is empty");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        SeqPushManager.Global().pushAll(event);
        return ConsumeResult.CONSUME_OK;
    }

    @consume(PositionEditLockEvent.class)
    ConsumeResult onHandlePositionEditLockEvent(PositionEditLockEvent event) {
        if (!event.isSetPositionEditLock()) {
            AppLog.w("onHandlePositionEditLockEvent called, but event's positionEditLock is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        SeqPushManager.Global().pushAll(event);
        return ConsumeResult.CONSUME_OK;
    }

    @consume(PositionAdjustGuardEvent.class)
    ConsumeResult onHandlePositionAdjustGuardEvent(PositionAdjustGuardEvent guardEvent) throws TException {
        if (!guardEvent.isSetType()) {
            AppLog.w("onHandlePositionAdjustGuardEvent called, but event's type is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (guardEvent.getType() == PositionAdjustGuardEventType.HOSTING_POSITION_EDIT_LOCK_CHANGE) {
            if (StringUtils.isEmpty(guardEvent.getLockKey())) {
                AppLog.w("onHandlePositionAdjustGuardEvent called" +
                        ", type is HOSTING_POSITION_EDIT_LOCK_CHANGE, but lockKey is empty");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }

            PositionEditLockEvent event = new PositionEditLockEvent();
            event.setPositionEditLock(PositionAdjustStubFactory.getStub().reqPositionEditLock(guardEvent.getLockKey()));
            event.setEventCreateTimestampMs(System.currentTimeMillis());

            SeqPushManager.Global().pushAll(event);
        }

        return ConsumeResult.CONSUME_OK;
    }

    @consume(TaskNoteCreatedEvent.class)
    ConsumeResult onHandleTaskNoteCreatedEvent(TaskNoteCreatedEvent event) {
        if (!event.isSetCreatedTaskNote()) {
            AppLog.w("onHandleTaskNoteCreatedEvent called, but event's createdTaskNote is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getCreatedTaskNote().getNoteType() == HostingTaskNoteType.XQ_TRADE_LAME) {
            if (!event.getCreatedTaskNote().isSetNoteKey()) {
                AppLog.w("onHandleTaskNoteCreatedEvent called, type is XQ_TRADE_LAME, but note key is not set");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }
            pushMessageBySubAccountId(event.getCreatedTaskNote().getNoteKey().getKey1(), event);
        }

        return ConsumeResult.CONSUME_OK;
    }

    @consume(TaskNoteDeletedEvent.class)
    ConsumeResult onHandleTaskNoteDeletedEvent(TaskNoteDeletedEvent event) {
        if (!event.isSetNoteType() || !event.isSetNoteKey()) {
            AppLog.w("onHandleTaskNoteDeletedEvent called, but event's noteType or noteKey is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getNoteType() == HostingTaskNoteType.XQ_TRADE_LAME) {
            pushMessageBySubAccountId(event.getNoteKey().getKey1(), event);
        }

        return ConsumeResult.CONSUME_OK;
    }

    @consume(TaskNoteGuardEvent.class)
    ConsumeResult onHandleTaskNoteGuardEvent(TaskNoteGuardEvent guardEvent) throws TException {
        if (!guardEvent.isSetGuardType()) {
            AppLog.w("onHandleTaskNoteGuardEvent called, but event's guardType is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!guardEvent.isSetNoteType()) {
            AppLog.w("onHandleTaskNoteGuardEvent called, but event's noteType is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!guardEvent.isSetNoteKey()) {
            AppLog.w("onHandleTaskNoteGuardEvent called, but event's noteKey is not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        QueryTaskNoteOption option = new QueryTaskNoteOption().setNoteType(guardEvent.getNoteType());
        if (guardEvent.getNoteKey().isSetKey1()) {
            Set<Long> key1Set = new HashSet<>();
            key1Set.add(guardEvent.getNoteKey().getKey1());
            option.setKey1(key1Set);
        }
        if (guardEvent.getNoteKey().isSetKey2()) {
            Set<Long> key2Set = new HashSet<>();
            key2Set.add(guardEvent.getNoteKey().getKey2());
            option.setKey2(key2Set);
        }
        if (guardEvent.getNoteKey().isSetKey3()) {
            Set<String> key3Set = new HashSet<>();
            key3Set.add(guardEvent.getNoteKey().getKey3());
            option.setKey3(key3Set);
        }
        HostingTaskNotePage taskNotePage
                = TaskNoteStubFactory.getStub()
                .getTaskNotePage(option, new IndexedPageOption().setPageIndex(0).setPageSize(10));

        if (guardEvent.getGuardType() == TaskNoteGuardEventType.GUARD_TASK_NOTE_CREATED) {
            if (taskNotePage.getResultListSize() <= 0) {
                return ConsumeResult.CONSUME_OK;
            } else if (taskNotePage.getResultListSize() == 1) {
                return onHandleTaskNoteCreatedEvent(
                        new TaskNoteCreatedEvent().setCreatedTaskNote(taskNotePage.getResultList().get(0)));
            } else {
                AppLog.w("onHandleTaskNoteGuardEvent guardEvent=" + guardEvent + ", but resultListSize > 1");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }
        } else if (guardEvent.getGuardType() == TaskNoteGuardEventType.GUARD_TASK_NOTE_DELETED) {
            if (taskNotePage.getResultListSize() <= 0) {
                return onHandleTaskNoteDeletedEvent(new TaskNoteDeletedEvent()
                        .setNoteKey(guardEvent.getNoteKey())
                        .setNoteType(guardEvent.getNoteType()));
            }
        }

        return ConsumeResult.CONSUME_OK;

    }
}
