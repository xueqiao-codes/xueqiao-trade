package xueqiao.trade.hosting.position.statis.server.impl;

import java.sql.Connection;
import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.server.TradeHostingPositionStatisAdaptor;
import xueqiao.trade.hosting.position.statis.service.ClosePositionService;
import xueqiao.trade.hosting.position.statis.service.ImportDataService;
import xueqiao.trade.hosting.position.statis.service.OperatePositionService;
import xueqiao.trade.hosting.position.statis.service.controller.DeleteExpiredContractPositionController;
import xueqiao.trade.hosting.position.statis.service.controller.QueryHistoryClosedPositionPageController;
import xueqiao.trade.hosting.position.statis.service.controller.QueryPositionSummaryExController;
import xueqiao.trade.hosting.position.statis.service.controller.QueryStatPositionUnitPageController;
import xueqiao.trade.hosting.position.statis.service.handler.ResetHostingHandler;
import xueqiao.trade.hosting.position.statis.service.handler.StatArchiveHandler;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

public class TradeHostingPositionStatisHandler extends TradeHostingPositionStatisAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        new ResetHostingHandler().clearAll();
    }

    @Override
    protected void contructCompose(TServiceCntl oCntl, StatContructComposeReq contructComposeReq) throws ErrorInfo, TException {
        new OperatePositionService().constructComposePositionData(contructComposeReq);
    }

    @Override
    protected void disassembleCompose(TServiceCntl oCntl, DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo, TException {
        new OperatePositionService().disassembleComposePositionData(disassembleComposePositionReq);
    }

    @Override
    protected void batchClosePosition(TServiceCntl oCntl, BatchClosedPositionReq batchClosedPositionReq) throws ErrorInfo, TException {
        new ClosePositionService().batchClosePosition(batchClosedPositionReq);
    }

    @Override
    protected void recoverClosedPosition(TServiceCntl oCntl, long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo, TException {
        new ClosePositionService().recoverPosition(subAccountId, targetKey, targetType);
    }

    @Override
    protected void assignPosition(TServiceCntl oCntl, PositionAssigned positionAssigned) throws ErrorInfo, TException {
        new ImportDataService().importAssignTradeData(positionAssigned);
    }

    @Override
    protected void mergeToCompose(TServiceCntl oCntl, StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo, TException {
        new OperatePositionService().mergeToCompose(mergeToComposeReq);
    }

    @Override
    protected void deleteExpiredStatContractPosition(TServiceCntl oCntl, long subAccountId, long sledContractId) throws ErrorInfo, TException {
        DeleteExpiredContractPositionController controller = new DeleteExpiredContractPositionController(subAccountId, sledContractId);
        controller.validateParams();
        controller.doDelete();
    }

    @Override
    protected StatPositionSummaryPage queryStatPositionSummaryPage(TServiceCntl oCntl, QueryStatPositionSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        PageResult<StatPositionSummary> itemPageResult =
                new DBQueryHelper<PageResult<StatPositionSummary>>(PositionStatisDB.Global()) {
                    @Override
                    protected PageResult<StatPositionSummary> onQuery(Connection conn) throws Exception {
                        return new StatPositionSummaryTable(conn).getStatPositionSummaryPage(queryOption, pageOption);
                    }
                }.query();

        StatPositionSummaryPage resultPage = new StatPositionSummaryPage();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setStatPositionSummaryList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    protected StatPositionItemPage queryStatPositionItemPage(TServiceCntl oCntl, QueryStatPositionItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        PageResult<StatPositionItem> itemPageResult =
                new DBQueryHelper<PageResult<StatPositionItem>>(PositionStatisDB.Global()) {
                    @Override
                    protected PageResult<StatPositionItem> onQuery(Connection conn) throws Exception {
                        return new StatPositionItemTable(conn).getStatPositionItemPage(queryOption, pageOption);
                    }
                }.query();

        StatPositionItemPage resultPage = new StatPositionItemPage();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setStatPositionItemList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(TServiceCntl oCntl, long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo, TException {
        return new ClosePositionService().queryCurrentDayStatClosedPosition(subAccountId, targetKey, targetType);
    }

    @Override
    protected StatClosedPositionDetail queryStatClosedPositionDetail(TServiceCntl oCntl, QueryStatClosedPositionItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        PageResult<StatClosedPositionItem> itemPageResult =
                new DBQueryHelper<PageResult<StatClosedPositionItem>>(PositionStatisDB.Global()) {
                    @Override
                    protected PageResult<StatClosedPositionItem> onQuery(Connection conn) throws Exception {
                        return new StatClosedPositionItemTable(conn).getStatClosedPositionItemPage(queryOption, pageOption);
                    }
                }.query();

        StatClosedPositionDetail resultPage = new StatClosedPositionDetail();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setStatClosedPositionItemList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(TServiceCntl oCntl, QueryStatClosedPositionDateSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        PageResult<StatClosedPositionDateSummary> itemPageResult = new StatArchiveHandler().queryArchivedClosedPosition(queryOption, pageOption);

        StatClosedPositionDateSummaryPage resultPage = new StatClosedPositionDateSummaryPage();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setStatClosedPositionDateSummaryList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    protected StatClosedPositionDetail queryArchivedClosedPositionDetail(TServiceCntl oCntl, QueryStatArchiveItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");

        PageResult<StatClosedPositionItem> itemPageResult =
                new DBQueryHelper<PageResult<StatClosedPositionItem>>(PositionStatisDB.Global()) {
                    @Override
                    protected PageResult<StatClosedPositionItem> onQuery(Connection conn) throws Exception {
                        return new StatArchiveItemTable(conn).getArchiveClosedPositionItemPage(queryOption, pageOption);
                    }
                }.query();

        StatClosedPositionDetail resultPage = new StatClosedPositionDetail();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setStatClosedPositionItemList(itemPageResult.getPageList());

        return resultPage;
    }

    @Override
    protected StatPositionSummaryExPage queryStatPositionSummaryExPage(TServiceCntl oCntl, QueryStatPositionSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryPositionSummaryExController controller = new QueryPositionSummaryExController(queryOption, pageOption);
        controller.validateParams();
        return controller.query();
    }

    @Override
    protected StatPositionUnitPage queryStatPositionUnitPage(TServiceCntl oCntl, QueryStatPositionUnitOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryStatPositionUnitPageController controller = new QueryStatPositionUnitPageController(queryOption, pageOption);
        controller.validateParams();
        return controller.query();
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(TServiceCntl oCntl, QueryHistoryClosedPositionOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryHistoryClosedPositionPageController controller = new QueryHistoryClosedPositionPageController(queryOption, pageOption);
        controller.validateParams();
        return controller.queryHistoryClosedPositionPage();
    }

    @Override
    protected StatClosedPositionDetail queryHistoryClosedPositionDetail(TServiceCntl oCntl, QueryHistoryClosedPositionOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryHistoryClosedPositionPageController controller = new QueryHistoryClosedPositionPageController(queryOption, pageOption);
        controller.validateParams();
        return controller.queryHistoryClosedPositionDetail();
    }

    @Override
    public void destroy() {
    }
}
