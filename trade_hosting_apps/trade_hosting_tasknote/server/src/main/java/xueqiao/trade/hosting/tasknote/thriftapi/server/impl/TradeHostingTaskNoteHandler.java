package xueqiao.trade.hosting.tasknote.thriftapi.server.impl;

import java.sql.Connection;
import java.util.AbstractMap;
import java.util.Properties;

import com.google.common.base.Preconditions;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.events.TaskNoteDeletedEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.tasknote.storage.TaskNoteDB;
import xueqiao.trade.hosting.tasknote.storage.table.TaskNoteTable;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;
import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNoteErrorCode;
import xueqiao.trade.hosting.tasknote.thriftapi.server.TradeHostingTaskNoteAdaptor;

public class TradeHostingTaskNoteHandler extends TradeHostingTaskNoteAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected HostingTaskNotePage getTaskNotePage(TServiceCntl oCntl
            , QueryTaskNoteOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should not be null");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
            , "pageOption's pageIndex should set and >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
            , "pageOption's pageSize should set and > 0");

        return new DBQueryHelper<HostingTaskNotePage>(TaskNoteDB.Global()) {

            @Override
            protected HostingTaskNotePage onQuery(Connection connection) throws Exception {
                return new TaskNoteTable(connection).getTaskNotePage(qryOption, pageOption);
            }

        }.query();
    }

    @Override
    protected void delTaskNote(TServiceCntl oCntl
            , HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(noteType, "noteType should not be null");
        ParameterChecker.checkNotNull(noteKey, "noteKey should not be null");
        ParameterChecker.check(noteKey.isSetKey1()
                && noteKey.isSetKey2()
                && noteKey.isSetKey3()
            , "noteKey's all key should be set");

        new DBTransactionHelperWithMsg<Void>(TaskNoteDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                int rc = new TaskNoteTable(getConnection()).delTaskNotes(noteType, noteKey);
                if (rc <= 0) {
                    throw new ErrorInfo(TradeHostingTaskNoteErrorCode.ERROR_TASK_NOTE_NOT_EXIST.getValue()
                            , "Task note is not exist!");
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected TBase prepareNotifyMessage() {
                TaskNoteDeletedEvent event = new TaskNoteDeletedEvent();
                event.setNoteType(noteType);
                event.setNoteKey(noteKey);

                return event;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                TaskNoteGuardEvent guardEvent = new TaskNoteGuardEvent();
                guardEvent.setGuardType(TaskNoteGuardEventType.GUARD_TASK_NOTE_DELETED);
                guardEvent.setNoteType(noteType);
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

    @Override
    public void destroy() {
    }
}
