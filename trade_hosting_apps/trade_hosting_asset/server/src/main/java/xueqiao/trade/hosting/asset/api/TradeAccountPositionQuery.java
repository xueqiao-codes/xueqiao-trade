package xueqiao.trade.hosting.asset.api;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeAccountNetPositionTable;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPosition;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionPage;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;

import java.sql.Connection;
import java.util.*;

public class TradeAccountPositionQuery {

    public TradeAccountPositionPage query(ReqTradeAccountPositionOption option, IndexedPageOption pageOption) throws ErrorInfo {

        List<TradeAccountPositionTable> list = new DBQueryHelper<List<TradeAccountPositionTable>>(AssetDB.Global()) {
            @Override
            protected List<TradeAccountPositionTable> onQuery(Connection connection) throws Exception {
                TradeAccountNetPositionTable netPositionTable = new TradeAccountNetPositionTable(connection);
                return netPositionTable.query(option);
            }
        }.query();
        TradeAccountPositionPage page = new TradeAccountPositionPage();
        if (list != null && list.size() > 0) {
            page.setTotal(1);
            List<TradeAccountPosition> positions = new ArrayList<>();
            TradeAccountPosition position = new TradeAccountPosition();
            position.setTradeAccountId(option.getTradeAccountId());
            Map<Long, Integer> map = new HashMap<>();
            for (TradeAccountPositionTable table : list) {
                map.put(table.getSledContractId(), table.getNetPosition());
            }
            position.setSledContractNetPositionMap(map);
            positions.add(position);
            page.setPage(positions);
        }
        return page;


    }
}
