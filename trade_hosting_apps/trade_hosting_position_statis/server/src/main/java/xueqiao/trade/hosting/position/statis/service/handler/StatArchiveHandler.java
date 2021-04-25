package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.ClosedProfit;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummary;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatArchiveDateSummaryTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatDateSummaryClosedProfitTable;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.List;

public class StatArchiveHandler {

	public PageResult<StatClosedPositionDateSummary> queryArchivedClosedPosition(QueryStatClosedPositionDateSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo {
		return new DBQueryHelper<PageResult<StatClosedPositionDateSummary>>(PositionStatisDB.Global()) {
			@Override
			protected PageResult<StatClosedPositionDateSummary> onQuery(Connection conn) throws Exception {
				PageResult<StatClosedPositionDateSummary> statClosedPositionDateSummaryPage =  new StatArchiveDateSummaryTable(conn).getArchiveDateSummaryPage(queryOption , pageOption);

				// 读取平仓收益
				List<ClosedProfit> closedProfitList = null;
				StatDateSummaryClosedProfitTable statDateSummaryClosedProfitTable = new StatDateSummaryClosedProfitTable(conn);
				for (StatClosedPositionDateSummary statClosedPositionDateSummary : statClosedPositionDateSummaryPage.getPageList()) {
					closedProfitList = statDateSummaryClosedProfitTable.query(statClosedPositionDateSummary.getDateSummaryId());
					/*
					 * 按每种币种计算总收益
					 * 数据里存储的是各币种下的收益
					 * 但计算各币种的总收益返回
					 * */
					closedProfitList = CNYRateHelper.getInstance().calculateTotalValue(closedProfitList);
					statClosedPositionDateSummary.setClosedProfits(closedProfitList);
				}

				return statClosedPositionDateSummaryPage;
			}
		}.query();
	}
}
