package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.ClosedProfit;
import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;
import xueqiao.trade.hosting.position.statis.StatPositionItem;
import xueqiao.trade.hosting.position.statis.StatPositionUnit;
import xueqiao.trade.hosting.position.statis.service.bean.PositionSummaryData;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;

import java.util.List;

public class StatPositionSummaryHandler {

    public static PositionSummaryData queryPositionSummaryData(final long subAccountId, final String targetKey, final HostingXQTargetType targetType) throws ErrorInfo {

        return new DBStepHelper<PositionSummaryData>(PositionStatisDB.Global()) {

            PositionSummaryData positionSummaryData = new PositionSummaryData();

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                List<StatPositionItem> positionItemList = new StatPositionItemTable(getConnection()).queryStatPositionItemList(subAccountId, targetKey, targetType);
                List<StatClosedPositionSummary> closedPositionSummaryList = new StatClosedPositionSummaryTable(getConnection()).query(subAccountId, targetKey, targetType);

                // 读取平仓收益
                StatSummaryClosedProfitTable statSummaryClosedProfitTable = new StatSummaryClosedProfitTable(getConnection());
                for (StatClosedPositionSummary statClosedPositionSummary : closedPositionSummaryList) {
                    List<ClosedProfit> closedProfitList = statSummaryClosedProfitTable.query(statClosedPositionSummary.getClosedId());
                    statClosedPositionSummary.setClosedProfits(closedProfitList);
                }

                List<StatPositionUnit> positionUnitList = new StatPositionUnitTable(getConnection()).queryStatPositionUnitList(subAccountId, targetKey, targetType);

                positionSummaryData.setPositionItemList(positionItemList);
                positionSummaryData.setClosedPositionSummaryList(closedPositionSummaryList);
                positionSummaryData.setPositionUnitList(positionUnitList);
            }

            @Override
            public PositionSummaryData getResult() {
                return positionSummaryData;
            }
        }.execute().getResult();
    }

    public static void clearPositionSummary() throws ErrorInfo {
        new DBStepHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new StatPositionSummaryTable(getConnection()).clearEmptyPositionSumary();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
