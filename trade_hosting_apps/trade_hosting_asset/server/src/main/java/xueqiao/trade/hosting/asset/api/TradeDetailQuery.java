package xueqiao.trade.hosting.asset.api;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.NetPositionTradeDetailTable;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingAssetTradeDetailOption;

import java.sql.Connection;

public class TradeDetailQuery {

    public AssetTradeDetailPage queryNetPositionTradeDetail(ReqHostingAssetTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo {

        return new DBQueryHelper<AssetTradeDetailPage>(AssetDB.Global()) {
            @Override
            protected AssetTradeDetailPage onQuery(Connection connection) throws Exception {

                NetPositionTradeDetailTable netTable = new NetPositionTradeDetailTable(connection);
                return netTable.queryPositionTradeDetailPage(option.getSubAccountId(), option.getSledContractId(), pageOption);
            }
        }.query();
    }
}
