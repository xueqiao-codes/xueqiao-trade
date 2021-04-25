package xueqiao.trade.hosting.asset.api;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSledContractPositionTable;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountFundTable;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountMoneyRecordTable;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.NetPositionTradeDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementFundDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementPositionDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementTradeDetailTable;

import java.util.ArrayList;
import java.util.List;

public class DataRemove {

    public void remove() throws ErrorInfo {

        new DBTransactionHelper<Void>(AssetDB.Global()) {

            @Override
            public void onPrepareData() throws Exception {
            }

            @Override
            public void onUpdate() throws Exception {
                List<TableHelper> tables = AssetDB.Global().getAllTables(getConnection());
                for (TableHelper table : tables) {
                    table.deleteAll();
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
        AssetGlobalDataFactory.getInstance().removeAll();
    }
}
