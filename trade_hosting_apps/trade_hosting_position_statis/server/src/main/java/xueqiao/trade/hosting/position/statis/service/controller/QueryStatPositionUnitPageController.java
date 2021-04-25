package xueqiao.trade.hosting.position.statis.service.controller;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.QueryStatPositionUnitOption;
import xueqiao.trade.hosting.position.statis.StatPositionUnit;
import xueqiao.trade.hosting.position.statis.StatPositionUnitPage;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionUnitTable;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;

public class QueryStatPositionUnitPageController {

    private QueryStatPositionUnitOption queryOption;
    private IndexedPageOption pageOption;

    public QueryStatPositionUnitPageController(QueryStatPositionUnitOption queryOption, IndexedPageOption pageOption) {
        this.queryOption = queryOption;
        this.pageOption = pageOption;
    }

    public void validateParams() throws ErrorInfo {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetPositionItemId() && queryOption.getPositionItemId() > 0
                , "queryOption's positionItemId should be set and > 0");
    }

    public StatPositionUnitPage query() throws ErrorInfo {
        return new DBQueryHelper<StatPositionUnitPage>(PositionStatisDB.Global()) {
            @Override
            protected StatPositionUnitPage onQuery(Connection connection) throws Exception {
                StatPositionUnitPage positionUnitPage = new StatPositionUnitPage();
                PageResult<StatPositionUnit> positionUnitPageResult = new StatPositionUnitTable(connection).getStatPositionUnitPage(queryOption, pageOption);
                if (positionUnitPageResult != null) {
                    positionUnitPage.setTotalNum(positionUnitPageResult.getTotalCount());
                    positionUnitPage.setStatPositionUnitList(positionUnitPageResult.getPageList());
                }
                return positionUnitPage;
            }
        }.query();
    }
}
