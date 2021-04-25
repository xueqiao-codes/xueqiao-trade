package xueqiao.trade.hosting.tasknote.app;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingEffectXQOrderIndexItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.events.TaskNoteCreatedEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEventType;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.events.XQTradeListChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.tasknote.storage.TaskNoteDB;
import xueqiao.trade.hosting.tasknote.storage.table.TaskNoteTable;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskNoteMessageConsumer implements IMessageConsumer {
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.RESERVE_QUEUE;
    }

    private void refreshAllEffectiveTrades() throws TException {
        int pageIndex = 0;
        final int pageSize = 100;

        while(true) {
            QueryEffectXQOrderIndexPage resultPage= ArbitrageStubFactory.getStub().getEffectXQOrderIndexPage(
                    new QueryEffectXQOrderIndexOption()
                    , new IndexedPageOption().setPageSize(pageSize).setPageIndex(pageIndex));
            if (resultPage == null || resultPage.getResultIndexItems().isEmpty()) {
                break;
            }

            Set<String> orderIds = new HashSet<>();
            for (HostingEffectXQOrderIndexItem indexItem : resultPage.getResultIndexItems()) {
                orderIds.add(indexItem.getOrderId());
            }

            Map<String, HostingXQOrderWithTradeList> resultMap
                    = ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(orderIds);
            for (HostingXQOrderWithTradeList orderWithTradeList : resultMap.values()) {
                handleXQTradeList(orderWithTradeList.getTradeList());
            }

            ++pageIndex;
        }
    }

    @Override
    public void onInit() throws Exception {
        AppLog.i("onInit...");

        refreshAllEffectiveTrades();
    }

    private void handleXQTradeList(List<HostingXQTrade> tradeList) throws ErrorInfo {
        if (tradeList == null || tradeList.isEmpty()) {
            return ;
        }

        for (HostingXQTrade trade : tradeList) {
            // 只处理瘸腿成交, 瘸腿成交为单合约成交，但是源标的为组合
            if (trade.getTradeTarget().getTargetType() != HostingXQTargetType.CONTRACT_TARGET
                    || trade.getSourceOrderTarget().getTargetType() != HostingXQTargetType.COMPOSE_TARGET) {
                continue;
            }

            new DBTransactionHelperWithMsg<Void>(TaskNoteDB.Global()) {
                private HostingTaskNoteKey noteKey;
                private HostingTaskNote dbNote;
                private HostingTaskNote newNote;

                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    noteKey = new HostingTaskNoteKey()
                            .setKey1(trade.getSubAccountId())
                            .setKey2(trade.getTradeId())
                            .setKey3("");
                    dbNote = new TaskNoteTable(getConnection()).getTaskNote(
                            HostingTaskNoteType.XQ_TRADE_LAME
                            , noteKey
                            , false);
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    if (dbNote != null) {
                        return ;
                    }

                    newNote = new HostingTaskNote();
                    newNote.setNoteType(HostingTaskNoteType.XQ_TRADE_LAME);
                    newNote.setNoteKey(noteKey);
                    newNote.setNoteContent(ThriftExtJsonUtils.toJsonTBase(trade));
                    newNote.setCreateTimestampMs(System.currentTimeMillis());

                    new TaskNoteTable(getConnection()).addTaskNote(newNote);
                }

                @Override
                public Void getResult() {
                    return null;
                }

                @Override
                protected TBase prepareNotifyMessage() {
                    if (dbNote != null) {
                        return null;
                    }

                    TaskNoteCreatedEvent createdEvent = new TaskNoteCreatedEvent();
                    createdEvent.setCreatedTaskNote(newNote);
                    return createdEvent;
                }

                @Override
                protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                    if (dbNote != null) {
                        return null;
                    }
                    TaskNoteGuardEvent guardEvent = new TaskNoteGuardEvent();
                    guardEvent.setGuardType(TaskNoteGuardEventType.GUARD_TASK_NOTE_CREATED);
                    guardEvent.setNoteType(HostingTaskNoteType.XQ_TRADE_LAME);
                    guardEvent.setNoteKey(noteKey);
                    return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(guardEvent
                            , new TimeoutGuardPolicy().setTimeoutSeconds(2));
                }

                @Override
                protected MessageAgent getMessageAgent() {
                    return HostingMessageContext.Global().getSenderAgent();
                }
            }.execute();

        }
    }

    @consume(XQTradeListChangedEvent.class)
    ConsumeResult onHandleXQTradeListChangedEvent(XQTradeListChangedEvent event) throws Exception{
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleXQTradeListChangedEvent event=" + event);
        }

        handleXQTradeList(event.getTradeList());
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQOrderGuardEvent.class)
    ConsumeResult onHandleXQOrderGuardEvent(XQOrderGuardEvent event) throws Exception {
        if (AppLog.debugEnabled()) {
            AppLog.d("onHandleXQOrderGuardEvent event=" + event);
        }

        if (!event.isSetType()) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        if (!event.isSetOrderId() || StringUtils.isEmpty(event.getOrderId())) {
            return ConsumeResult.CONSUME_FAILED_DROP;
        }

        if (event.getType() != XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD) {
            return ConsumeResult.CONSUME_OK;
        }

        HashSet<String> orderIds = new HashSet<>();
        orderIds.add(event.getOrderId());
        Map<String, HostingXQOrderWithTradeList> resultMap
                = ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(orderIds);
        HostingXQOrderWithTradeList orderWithTradeList = resultMap.get(event.getOrderId());
        if (orderWithTradeList != null) {
            handleXQTradeList(orderWithTradeList.getTradeList());
        }

        return ConsumeResult.CONSUME_OK;
    }
}
