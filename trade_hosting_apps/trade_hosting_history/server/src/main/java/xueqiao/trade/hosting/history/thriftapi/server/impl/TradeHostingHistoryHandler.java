package xueqiao.trade.hosting.history.thriftapi.server.impl;


import java.sql.Connection;
import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.history.storage.HistoryDB;
import xueqiao.trade.hosting.history.storage.table.HostingXQOrderHisIndexTable;
import xueqiao.trade.hosting.history.storage.table.HostingXQTradeHisIndexTable;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.QueryTimePeriod;
import xueqiao.trade.hosting.history.thriftapi.server.TradeHostingHistoryAdaptor;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

public class TradeHostingHistoryHandler extends TradeHostingHistoryAdaptor{
    @Override
    public int InitApp(Properties props){
        return 0;
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        new DBTransactionHelper<Void>(HistoryDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingXQTradeHisIndexTable(getConnection()).deleteAll();
                new HostingXQOrderHisIndexTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    private void checkQueryTimePeriod(QueryTimePeriod timePeriod, String namePrefix) throws ErrorInfo {
        ParameterChecker.check(timePeriod.isSetStartTimestampMs(), namePrefix + "'s startTimestampMs must set");
        ParameterChecker.check(timePeriod.isSetEndTimestampMs(), namePrefix + "'s endTimestampMs must set");
        ParameterChecker.check(timePeriod.getEndTimestampMs() >= timePeriod.getStartTimestampMs()
                            , namePrefix + "'s endTimestampMs must >= startTimestampMs");
    }

    @Override
    protected HostingXQOrderHisIndexPage getXQOrderHisIndexPage(TServiceCntl oCntl
            , QueryXQOrderHisIndexItemOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException{
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");
        ParameterChecker.check(qryOption.isSetOrderCreateTimePeriod() || qryOption.isSetOrderEndTimePeriod()
                , "orderCreateTimePeriod or orderEndTimePeriod must at least set one");
        if(qryOption.isSetOrderCreateTimePeriod()) {
            checkQueryTimePeriod(qryOption.getOrderCreateTimePeriod(), "orderCreateTimePeriod");
        }
        if (qryOption.isSetOrderEndTimePeriod()) {
            checkQueryTimePeriod(qryOption.getOrderEndTimePeriod(), "orderEndTimePeriod");
        }

        PageResult<HostingXQOrderHisIndexItem> itemPageResult=
                new DBQueryHelper<PageResult<HostingXQOrderHisIndexItem>>(HistoryDB.Global()) {
            @Override
            protected PageResult<HostingXQOrderHisIndexItem> onQuery(Connection conn) throws Exception {
                return new HostingXQOrderHisIndexTable(conn).getOrderHisIndexPage(qryOption
                        , new PageOption().setPageIndex(pageOption.getPageIndex())
                            .setPageSize(pageOption.getPageSize())
                            .setNeedTotalCount(pageOption.isNeedTotalCount()));
            }
        }.query();

        HostingXQOrderHisIndexPage resultPage = new HostingXQOrderHisIndexPage();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setResultList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    protected HostingXQTradeHisIndexPage getXQTradeHisIndexPage(TServiceCntl oCntl
            , QueryXQTradeHisIndexItemOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException{
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");
        ParameterChecker.check(qryOption.isSetTradeCreateTimePeriod(), "qryOption's tradeCreateTimePeriod should be set");
        checkQueryTimePeriod(qryOption.getTradeCreateTimePeriod(), "tradeCreateTimePeriod");

        PageResult<HostingXQTradeHisIndexItem> itemPageResult =
                new DBQueryHelper<PageResult<HostingXQTradeHisIndexItem>>(HistoryDB.Global()) {

                    @Override
                    protected PageResult<HostingXQTradeHisIndexItem> onQuery(Connection conn) throws Exception {
                        return new HostingXQTradeHisIndexTable(conn).getTradeHisIndexItemPage(qryOption
                                , new PageOption().setPageIndex(pageOption.getPageIndex())
                                        .setPageSize(pageOption.getPageSize())
                                        .setNeedTotalCount(pageOption.isNeedTotalCount()));
                    }
        }.query();

        HostingXQTradeHisIndexPage resultPage = new HostingXQTradeHisIndexPage();
        resultPage.setTotalNum(itemPageResult.getTotalCount());
        resultPage.setResultList(itemPageResult.getPageList());
        return resultPage;
    }

    @Override
    public void destroy() {
    }
}
