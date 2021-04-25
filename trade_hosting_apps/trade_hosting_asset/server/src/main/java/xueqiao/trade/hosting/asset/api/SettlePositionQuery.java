package xueqiao.trade.hosting.asset.api;

import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.PageOptionHelper;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementPositionDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementTradeDetailTable;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;

import java.sql.Connection;

public class SettlePositionQuery {

    public SettlementPositionDetailPage getSettlementPositionDetail(ReqSettlementPositionDetailOption option, PageOption pageOption) throws ErrorInfo, TException {
        Preconditions.checkNotNull(option);
        return new DBQueryHelper<SettlementPositionDetailPage>(AssetDB.Global()) {
            @Override
            protected SettlementPositionDetailPage onQuery(Connection connection) throws Exception {

                SettlementPositionDetailTable detailTable = new SettlementPositionDetailTable(connection);
                return detailTable.query(option, pageOption);
            }
        }.query();
    }

    public AssetTradeDetailPage getSettlementPositionTradeDetail(ReqSettlementPositionTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo {
        return new DBQueryHelper<AssetTradeDetailPage>(AssetDB.Global()) {
            @Override
            protected AssetTradeDetailPage onQuery(Connection connection) throws Exception {
                SettlementTradeDetailTable tradeDetailTable = new SettlementTradeDetailTable(connection);
                return tradeDetailTable.query(option, pageOption);
            }
        }.query();

    }
}
