package xueqiao.trade.hosting.asset.event.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.asset.actor.AssetActorFactory;
import xueqiao.trade.hosting.asset.actor.IAssetActor;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetTradeHandler;
import xueqiao.trade.hosting.asset.quotation.AssetQuotationHelper;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailSaver;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeDetailHistoryTable;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.TradeDetailSource;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TradeListHandler {

    public List<AssetTradeDetail> saveHostingExecTrade(List<HostingExecTrade> trades, TradeDetailSource source, String sledOrderId) throws ErrorInfo {
        List<AssetTradeDetail> assetTradeDetails = new ArrayList<>();
        for (HostingExecTrade trade : trades) {
            List<AssetTradeDetail> list = AssetTradeHandler.map2AssetTradeDetail(trade, source.name(), sledOrderId);
            if (list.size() > 0) {
                assetTradeDetails.addAll(list);
            }
        }
        if (assetTradeDetails.size() > 0) {
            List<AssetTradeDetail> tradeDetails = getAssetTradeDetailNotExists(assetTradeDetails);
            new AssetTradeDetailSaver().save(tradeDetails);
        }
        return assetTradeDetails;
    }

    // 去重
    private List<AssetTradeDetail> getAssetTradeDetailNotExists(List<AssetTradeDetail> assetTradeDetails)
            throws org.soldier.platform.svr_platform.comm.ErrorInfo {
        List<AssetTradeDetail> tradeDetails = new ArrayList<>();
        for (AssetTradeDetail detail : assetTradeDetails) {
            AssetTradeDetail item = new DBQueryHelper<AssetTradeDetail>(AssetDB.Global()) {
                @Override
                protected AssetTradeDetail onQuery(Connection connection) throws Exception {

                    TradeDetailHistoryTable table = new TradeDetailHistoryTable(connection);
                    return table.queryForUpdate(detail.getExecTradeId(), false);

                }
            }.query();
            if (item == null) {
                tradeDetails.add(detail);
            }

        }
        return tradeDetails;
    }
}
