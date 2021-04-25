package xueqiao.trade.hosting.position.adjust.adjust;

import com.antiy.error_code.ErrorCodeOuter;
import com.google.common.base.Preconditions;
import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.events.PositionAdjustGuardEvent;
import xueqiao.trade.hosting.events.PositionAdjustGuardEventType;
import xueqiao.trade.hosting.events.PositionEditLockEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.storage.PositionEditLockTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;

import java.sql.Connection;
import java.util.AbstractMap;

public class EditLock {

    public PositionEditLock queryLock(String lockKey) throws ErrorInfo {
        PositionEditLock lock = new DBQueryHelper<PositionEditLock>(PositionAdjustDB.Global()) {
            @Override
            protected PositionEditLock onQuery(Connection connection) throws Exception {
                PositionEditLockTable lockTable = new PositionEditLockTable(connection);
                return lockTable.query(lockKey, false);
            }
        }.query();

        if (lock == null) {
            throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "Lock key error.");
        }
        return lock;
    }

    public void addLock(PositionEditLock editLock) throws ErrorInfo {
        Preconditions.checkNotNull(editLock);
        Preconditions.checkArgument(editLock.isSetLockArea());
        Preconditions.checkArgument(editLock.isSetSubUserId());

        new DBTransactionHelperWithMsg<Void>(PositionAdjustDB.Global()) {
            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                return EditLock.this.prepareGuardMessage(editLock);
            }

            @Override
            protected TBase prepareNotifyMessage() {
                return EditLock.this.prepareNotifyMessage(editLock);
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                PositionEditLockTable lockTable = new PositionEditLockTable(getConnection());
                PositionEditLock lock = lockTable.query(editLock.getLockArea(), true);
                if (lock == null) {
                    throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "Lock key error.");
                } else {
                    lockTable.update(editLock);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

    }

    private TBase prepareNotifyMessage(PositionEditLock editLock) {
        PositionEditLockEvent event = new PositionEditLockEvent();
        event.setEventCreateTimestampMs(System.currentTimeMillis());
        event.setPositionEditLock(editLock);
        return event;
    }

    private AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage(PositionEditLock editLock) {
        PositionAdjustGuardEvent event = new PositionAdjustGuardEvent();
        event.setLockKey(editLock.getLockArea());
        event.setType(PositionAdjustGuardEventType.HOSTING_POSITION_EDIT_LOCK_CHANGE);
        return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(event
                , new TimeoutGuardPolicy().setTimeoutSeconds(2));
    }

    public void removeLock(PositionEditLock editLock) throws ErrorInfo {
        Preconditions.checkNotNull(editLock);
        Preconditions.checkArgument(editLock.isSetLockArea());

        new DBTransactionHelperWithMsg<Void>(PositionAdjustDB.Global()) {
            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                return EditLock.this.prepareGuardMessage(editLock);
            }

            @Override
            protected TBase prepareNotifyMessage() {
                return EditLock.this.prepareNotifyMessage(editLock);
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                PositionEditLockTable lockTable = new PositionEditLockTable(getConnection());
                PositionEditLock lock = lockTable.query(editLock.getLockArea(), true);
                if (lock == null) {
                    throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "Lock key error.");
                } else {
                    editLock.setSubUserId(0);
                    lockTable.update(editLock);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
