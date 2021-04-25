package xueqiao.trade.hosting.tasknote.storage;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.tasknote.storage.table.TaskNoteTable;

import java.sql.SQLException;

public class TaskNoteDB extends DBBase {

    private static TaskNoteDB sInstance;
    public static TaskNoteDB Global() {
        if (sInstance == null) {
            synchronized(TaskNoteDB.class) {
                if (sInstance == null) {
                    sInstance = new TaskNoteDB();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getDBName() {
        return "task_note";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new TaskNoteTable(null).onUpgradeOneStep(operator, targetVersion);
    }
}
