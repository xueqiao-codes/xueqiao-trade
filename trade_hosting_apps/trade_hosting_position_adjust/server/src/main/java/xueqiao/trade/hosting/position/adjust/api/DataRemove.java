package xueqiao.trade.hosting.position.adjust.api;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.job.AssignPositionJobScheduler;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;

import java.util.List;

public class DataRemove {

    public void remove() throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {

            @Override
            public void onPrepareData() throws Exception {
            }

            @Override
            public void onUpdate() throws Exception {
                List<TableHelper> tables = PositionAdjustDB.Global().getAllTables(getConnection());
                for (TableHelper table : tables) {
                    table.deleteAll();
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        AssignPositionJobScheduler.Global().clear();
    }
}
