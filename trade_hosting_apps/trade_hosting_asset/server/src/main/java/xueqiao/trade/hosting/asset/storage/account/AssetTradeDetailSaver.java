package xueqiao.trade.hosting.asset.storage.account;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.config.Config;
import xueqiao.trade.hosting.asset.core.AssetTradeHandler;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.FactoryInstance;
import xueqiao.trade.hosting.asset.storage.PositionAssignHistoryTable;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeAccountNetPositionTable;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeDetailHistoryTable;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.PositionAssignHistory;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;
import xueqiao.trade.hosting.asset.thriftapi.TradeDetailSource;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

import java.util.List;

/**
 * 根据不同数据切面需求同时落地的持仓明细
 *
 * @author walter
 */
public class AssetTradeDetailSaver {

    private void addPositionTask(List<AssetTradeDetail> assetTradeDetails) throws ErrorInfo {
        for (AssetTradeDetail assetTradeDetail : assetTradeDetails) {
            ExecutorHandler.calculatePosition(assetTradeDetail);
        }
    }

    public void saveAssign(List<AssetTradeDetail> assetTradeDetails) throws ErrorInfo {
        new DBTransactionHelper<Void>(AssetDB.Global()) {
            AssetTradeDetailTable assetTradeDetailTable;
            PositionAssignHistoryTable assignHistoryTable;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                assetTradeDetailTable = new AssetTradeDetailTable(getConnection());
                assignHistoryTable = new PositionAssignHistoryTable(getConnection());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                for (AssetTradeDetail assetTradeDetail : assetTradeDetails) {
                    boolean isAssign = assetTradeDetail.getSource().toUpperCase().startsWith(TradeDetailSource.ASSIGN.name());
                    long tradeDetailId = Config.getInstance().getTradeDetailIdMaker().createId();
                    assetTradeDetail.setAssetTradeDetailId(tradeDetailId);
                    if (isAssign) {
                        long assignId = assetTradeDetail.getExecTradeId();
                        PositionAssignHistory assign = assignHistoryTable.query(assignId);
                        if (assign != null) {
                            continue;
                        }
                        PositionAssignHistory assignHistory = new PositionAssignHistory();
                        assignHistory.setAssignId(assignId);
                        assignHistory.setContent(new String(ProtocolUtil.serialize(FactoryInstance.getInstance().getJsonFactory(), assetTradeDetail).array()));
                        assignHistoryTable.add(assignHistory);
                    } else {
                        continue;
                    }
                    assetTradeDetailTable.add(assetTradeDetail);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
        addPositionTask(assetTradeDetails);
    }

    public void save(List<AssetTradeDetail> assetTradeDetails) throws ErrorInfo {
        new DBTransactionHelper<Void>(AssetDB.Global()) {
            TradeDetailHistoryTable tradeDetailHistoryTable;
            TradeAccountNetPositionTable tradeAccountNetPositionTable;
            AssetTradeDetailTable assetTradeDetailTable;
            PositionAssignHistoryTable assignHistoryTable;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                tradeDetailHistoryTable = new TradeDetailHistoryTable(getConnection());
                tradeAccountNetPositionTable = new TradeAccountNetPositionTable(getConnection());
                assetTradeDetailTable = new AssetTradeDetailTable(getConnection());
                assignHistoryTable = new PositionAssignHistoryTable(getConnection());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                for (AssetTradeDetail assetTradeDetail : assetTradeDetails) {
                    boolean isAssign = assetTradeDetail.getSource().toUpperCase().startsWith(TradeDetailSource.ASSIGN.name());
                    if (isAssign) {
                        continue;
                    }
                    long tradeDetailId = Config.getInstance().getTradeDetailIdMaker().createId();
                    assetTradeDetail.setAssetTradeDetailId(tradeDetailId);
                    tradeDetailHistoryTable.add(assetTradeDetail);
                    assetTradeDetailTable.add(assetTradeDetail);
                    TradeAccountPositionTable netPosition = tradeAccountNetPositionTable.queryForUpdate(assetTradeDetail.getTradeAccountId(), assetTradeDetail.getSledContractId());
                    boolean isNew = false;
                    if (netPosition == null) {
                        netPosition = new TradeAccountPositionTable();
                        netPosition.setTradeAccount(assetTradeDetail.getTradeAccountId());
                        netPosition.setSledContractId(assetTradeDetail.getSledContractId());
                        isNew = true;
                    }
                    int position;
                    if (HostingExecTradeDirection.TRADE_SELL.equals(assetTradeDetail.getExecTradeDirection())) {
                        position = 0 - Math.abs(assetTradeDetail.getTradeVolume());
                    } else {
                        position = Math.abs(assetTradeDetail.getTradeVolume());
                    }
                    netPosition.setNetPosition(position + netPosition.getNetPosition());
                    if (isNew) {
                        tradeAccountNetPositionTable.add(netPosition);
                    } else {
                        tradeAccountNetPositionTable.update(netPosition);
                    }
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
        addPositionTask(assetTradeDetails);
    }
}
