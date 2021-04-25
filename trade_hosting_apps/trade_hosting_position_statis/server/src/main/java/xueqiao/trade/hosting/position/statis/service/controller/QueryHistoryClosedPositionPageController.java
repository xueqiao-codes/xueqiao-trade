package xueqiao.trade.hosting.position.statis.service.controller;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.ClosePositionService;
import xueqiao.trade.hosting.position.statis.service.handler.StatArchiveHandler;
import xueqiao.trade.hosting.position.statis.service.util.TimeUtil;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatArchiveItemTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatClosedPositionItemTable;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;

public class QueryHistoryClosedPositionPageController {
    private QueryHistoryClosedPositionOption queryOption;
    private IndexedPageOption pageOption;

    public QueryHistoryClosedPositionPageController(QueryHistoryClosedPositionOption queryOption, IndexedPageOption pageOption) {
        this.queryOption = queryOption;
        this.pageOption = pageOption;
    }

    public void validateParams() throws ErrorInfo {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0
                , "invalid subAccountId");
        ParameterChecker.check(StringUtils.isNotBlank(queryOption.getTargetKey())
                , "invalid targetKey");
        ParameterChecker.check(queryOption.isSetTargetType()
                , "targetType should be set");
        ParameterChecker.check(queryOption.isSetClosedDateTimestampMs() && queryOption.getClosedDateTimestampMs() > 0
                , "invalid closedDateTimestampMs");
    }

    public StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage() throws ErrorInfo {
        long currentDateZeroMillis = TimeUtil.getCurrentDateZeroMillis(System.currentTimeMillis());
        long targetDateZeroMillis = TimeUtil.getCurrentDateZeroMillis(queryOption.getClosedDateTimestampMs());

        StatClosedPositionDateSummaryPage resultPage = new StatClosedPositionDateSummaryPage();

        if (currentDateZeroMillis == targetDateZeroMillis) {
            // 当天，查平仓记录
            return new ClosePositionService().queryCurrentDayStatClosedPosition(queryOption.getSubAccountId(), queryOption.getTargetKey(), queryOption.getTargetType());
        } else if (currentDateZeroMillis > targetDateZeroMillis) {
            // 历史，查归档记录
            QueryStatClosedPositionDateSummaryOption closedPositionDateSummaryOption = new QueryStatClosedPositionDateSummaryOption();
            closedPositionDateSummaryOption.setSubAccountId(queryOption.getSubAccountId());
            closedPositionDateSummaryOption.setTargetKey(queryOption.getTargetKey());
            closedPositionDateSummaryOption.setTargetType(queryOption.getTargetType());
            closedPositionDateSummaryOption.setArchivedDateTimestampMs(queryOption.getClosedDateTimestampMs());

            PageResult<StatClosedPositionDateSummary> itemPageResult = new StatArchiveHandler().queryArchivedClosedPosition(closedPositionDateSummaryOption, pageOption);

            resultPage = new StatClosedPositionDateSummaryPage();
            resultPage.setTotalNum(itemPageResult.getTotalCount());
            resultPage.setStatClosedPositionDateSummaryList(itemPageResult.getPageList());
            return resultPage;
        }
        return resultPage;
    }

    public StatClosedPositionDetail queryHistoryClosedPositionDetail() throws ErrorInfo {
        long currentDateZeroMillis = TimeUtil.getCurrentDateZeroMillis(System.currentTimeMillis());
        long targetDateZeroMillis = TimeUtil.getCurrentDateZeroMillis(queryOption.getClosedDateTimestampMs());

        StatClosedPositionDetail resultPage;

        if (currentDateZeroMillis == targetDateZeroMillis) {
            // 当天，查平仓记录
            QueryStatClosedPositionItemOption queryStatClosedPositionItemOption = new QueryStatClosedPositionItemOption();
            queryStatClosedPositionItemOption.setSubAccountId(queryOption.getSubAccountId());
            queryStatClosedPositionItemOption.setTargetKey(queryOption.getTargetKey());
            queryStatClosedPositionItemOption.setTargetType(queryOption.getTargetType());

            PageResult<StatClosedPositionItem> itemPageResult =
                    new DBQueryHelper<PageResult<StatClosedPositionItem>>(PositionStatisDB.Global()) {
                        @Override
                        protected PageResult<StatClosedPositionItem> onQuery(Connection conn) throws Exception {
                            return new StatClosedPositionItemTable(conn).getStatClosedPositionItemPage(queryStatClosedPositionItemOption, pageOption);
                        }
                    }.query();

            resultPage = new StatClosedPositionDetail();
            resultPage.setTotalNum(itemPageResult.getTotalCount());
            resultPage.setStatClosedPositionItemList(itemPageResult.getPageList());
            return resultPage;
        } else if (currentDateZeroMillis > targetDateZeroMillis) {
            // 历史，查归档记录

            QueryStatArchiveItemOption queryStatArchiveItemOption = new QueryStatArchiveItemOption();
            queryStatArchiveItemOption.setSubAccountId(queryOption.getSubAccountId());
            queryStatArchiveItemOption.setTargetKey(queryOption.getTargetKey());
            queryStatArchiveItemOption.setTargetType(queryOption.getTargetType());
            queryStatArchiveItemOption.setArchivedDateTimestampMs(queryOption.getClosedDateTimestampMs());

            PageResult<StatClosedPositionItem> itemPageResult =
                    new DBQueryHelper<PageResult<StatClosedPositionItem>>(PositionStatisDB.Global()) {
                        @Override
                        protected PageResult<StatClosedPositionItem> onQuery(Connection conn) throws Exception {
                            return new StatArchiveItemTable(conn).getArchiveClosedPositionItemPage(queryStatArchiveItemOption, pageOption);
                        }
                    }.query();

            resultPage = new StatClosedPositionDetail();
            resultPage.setTotalNum(itemPageResult.getTotalCount());
            resultPage.setStatClosedPositionItemList(itemPageResult.getPageList());

            return resultPage;
        }
        return new StatClosedPositionDetail();
    }
}
