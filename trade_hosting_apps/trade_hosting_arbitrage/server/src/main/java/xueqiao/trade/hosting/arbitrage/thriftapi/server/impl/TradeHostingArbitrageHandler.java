package xueqiao.trade.hosting.arbitrage.thriftapi.server.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderCreator;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeSettingsProvider;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderEffectIndexItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecTradeTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeRelatedTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeTable;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingEffectXQOrderIndexItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.server.TradeHostingArbitrageAdaptor;
import xueqiao.trade.hosting.arbitrage.thriftapi.trade_hosting_arbitrageConstants;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;


public class TradeHostingArbitrageHandler extends TradeHostingArbitrageAdaptor{
    @Override
    public int InitApp(Properties props){
        return 0;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void createXQOrder(TServiceCntl oCntl, HostingXQOrder order) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(order, "order should not be null");
        new XQOrderCreator(order).checkOrder().doCreate();
    }

    @Override
    protected void cancelXQOrder(TServiceCntl oCntl, String orderId) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotEmpty(orderId), "orderId should set and not be empty");
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor == null) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_NOT_EFFECTIVE_CANNOT_OPERATION.getValue()
                    , "order is not active");
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            protected void onRun() throws Exception {
                executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_BY_USER.getValue());
            }
        });
    }

    @Override
    protected void suspendXQOrder(TServiceCntl oCntl, String orderId)throws ErrorInfo, TException {
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor == null) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_NOT_EFFECTIVE_CANNOT_OPERATION.getValue()
                    , "order is not active");
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            protected void onRun() throws Exception {
                executor.suspend(HostingXQSuspendReason.SUSPENDED_BY_PERSON, 0);
            }
        });
    }

    @Override
    protected void resumeXQOrder(TServiceCntl oCntl
            , String orderId
            , HostingXQOrderResumeMode resumeMode) throws ErrorInfo, TException {
        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor == null) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_NOT_EFFECTIVE_CANNOT_OPERATION.getValue()
                    , "order is not active");
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            protected void onRun() throws Exception {
                if (resumeMode != null) {
                    executor.start(resumeMode);
                } else {
                    // 默认为按照价差追单
                    executor.start(HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_BY_PRICE);
                }
            }
        });
    }

    @Override
    protected QueryEffectXQOrderIndexPage getEffectXQOrderIndexPage(TServiceCntl oCntl
            , QueryEffectXQOrderIndexOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should not be null");
        
        PageOption apiPageOption = new PageOption()
                .setPageIndex(pageOption.getPageIndex())
                .setPageSize(pageOption.getPageSize())
                .setNeedTotalCount(pageOption.isNeedTotalCount());
        
        PageResult<XQOrderEffectIndexItem> indexItemsPage 
            = new DBQueryHelper<PageResult<XQOrderEffectIndexItem>>(ArbitrageDB.Global()) {
                @Override
                protected PageResult<XQOrderEffectIndexItem> onQuery(Connection conn) throws Exception {
                    return new XQOrderEffectIndexTable(conn).getIndexItems(qryOption, apiPageOption);
                }
        }.query();
        
        QueryEffectXQOrderIndexPage resultPage = new QueryEffectXQOrderIndexPage();
        resultPage.setTotalNum(indexItemsPage.getTotalCount());
        resultPage.setResultIndexItems(new ArrayList<HostingEffectXQOrderIndexItem>(indexItemsPage.getPageList().size() + 1));
        for (XQOrderEffectIndexItem indexItem : indexItemsPage.getPageList()) {
            HostingEffectXQOrderIndexItem hIndexItem = new HostingEffectXQOrderIndexItem();
            hIndexItem.setOrderId(indexItem.getOrderId())
                      .setSubUserId(indexItem.getSubUserId())
                      .setSubAccountId(indexItem.getSubAccountId());
            resultPage.getResultIndexItems().add(hIndexItem);
        }
        return resultPage;
    }

    @Override
    protected Map<String, HostingXQOrder> getXQOrders(TServiceCntl oCntl, Set<String> orderIds)
            throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        ParameterChecker.check(!orderIds.isEmpty(), "orderIds should not be empty");
        
        return new DBQueryHelper<Map<String, HostingXQOrder>>(ArbitrageDB.Global()) {
            @Override
            protected Map<String, HostingXQOrder> onQuery(Connection conn) throws Exception {
                return new XQOrderTable(conn).getOrdersMap(orderIds);
            }
        }.query();
    }

    @Override
    protected Map<String, List<HostingXQTrade>> getXQTrades(TServiceCntl oCntl, Set<String> orderIds)
            throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        ParameterChecker.check(!orderIds.isEmpty(), "orderIds should not be empty");
        
        return new DBQueryHelper<Map<String, List<HostingXQTrade>>>(ArbitrageDB.Global()) {
            @Override
            protected Map<String, List<HostingXQTrade>> onQuery(Connection conn) throws Exception {
                return new XQTradeTable(conn).batchGetOrderTrades(orderIds);
            }
        }.query();
    }

    @Override
    protected Map<String, HostingXQOrderWithTradeList> getXQOrderWithTradeLists(TServiceCntl oCntl,
            Set<String> orderIds) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        ParameterChecker.check(!orderIds.isEmpty(), "orderIds should not be empty");
        
        return new DBQueryHelper<Map<String, HostingXQOrderWithTradeList>>(ArbitrageDB.Global()) {
            @Override
            protected Map<String, HostingXQOrderWithTradeList> onQuery(Connection conn) throws Exception {
                Map<String, HostingXQOrderWithTradeList> resultMap = new HashMap<String, HostingXQOrderWithTradeList>();
                
                List<HostingXQOrder> ordersList = new XQOrderTable(conn).getOrdersList(orderIds); 
                Map<String, List<HostingXQTrade>> tradesMap = new XQTradeTable(conn).batchGetOrderTrades(orderIds);
                
                for (HostingXQOrder order : ordersList) {
                    HostingXQOrderWithTradeList orderWithTradeList = new HostingXQOrderWithTradeList();
                    orderWithTradeList.setOrder(order);
                    orderWithTradeList.setTradeList(tradesMap.get(order.getOrderId()));
                    resultMap.put(order.getOrderId(), orderWithTradeList);
                }
                return resultMap;
            }
        }.query();
    }

    @Override
    protected HostingXQOrderExecDetail getXQOrderExecDetail(TServiceCntl oCntl, String orderId)
            throws ErrorInfo, TException {
        ParameterChecker.check(StringUtils.isNotEmpty(orderId), "orderId should not be empty");

        XQOrderBaseExecutor executor = XQOrderManager.Global().getExecutor(orderId);
        if (executor != null) {
            AppLog.i("getXQOrderExecDetail from memory for xqOrderId=" + orderId);
            return new SyncResult<HostingXQOrderExecDetail>(executor.getContext().getWorkThread()) {
                @Override
                protected HostingXQOrderExecDetail onCall() throws Exception {
                    return executor.getExecDetail();
                }
            }.get();
        }

        return new DBQueryHelper<HostingXQOrderExecDetail>(ArbitrageDB.Global()){
            @Override
            protected HostingXQOrderExecDetail onQuery(Connection conn) throws Exception {
                HostingXQOrder xqOrder = new XQOrderTable(conn).getOrder(orderId);
                if (xqOrder == null) {
                    throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_NOT_EXISTS.getValue()
                            , "order is not exists");
                }
                HostingXQOrderExecDetail orderExecDetail = new HostingXQOrderExecDetail();
                orderExecDetail.setXqOrder(xqOrder);
                orderExecDetail.setXqTrades(new XQTradeTable(conn).getOrderTrades(orderId));
                orderExecDetail.setExecOrders(
                        new XQOrderRelatedExecOrderTable(conn).getItems(orderId)
                            .stream().map(item -> item.getRelatedExecOrder()).collect(Collectors.toList()));
                orderExecDetail.setExecTrades(
                        new XQOrderRelatedExecTradeTable(conn).getItems(orderId)
                            .stream().map(item ->item.getRelatedExecTrade()).collect(Collectors.toList()));


                Map<Long, List<HostingXQTradeRelatedItem>> xqTradeRelatedItems = new HashMap<>();
                List<XQTradeRelatedItem> dbRelatedItems = new XQTradeRelatedTable(conn).getItemsByOrderId(orderId);
                for (XQTradeRelatedItem dbRelatedItem : dbRelatedItems) {
                    List<HostingXQTradeRelatedItem> relatedItemList = xqTradeRelatedItems.get(dbRelatedItem.getTradeId());
                    if (relatedItemList == null) {
                        relatedItemList = new ArrayList<>();
                        xqTradeRelatedItems.put(dbRelatedItem.getTradeId(), relatedItemList);
                    }

                    relatedItemList.add(XQOrderHelper.dbRelatedItem2Hosting(dbRelatedItem));
                }
                orderExecDetail.setXqTradeRelatedItems(xqTradeRelatedItems);

                return orderExecDetail;
            }
        }.query();
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        new DBTransactionHelper<Void>(ArbitrageDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new XQTradeRelatedTable(getConnection()).deleteAll();
                new XQOrderRelatedExecTradeTable(getConnection()).deleteAll();
                new XQOrderRelatedExecOrderTable(getConnection()).deleteAll();
                new XQTradeTable(getConnection()).deleteAll();
                new XQOrderTable(getConnection()).deleteAll();
                new XQOrderEffectIndexTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        XQOrderManager.Global().destroyAll();
    }

    @Override
    protected Map<Long, HostingXQTrade> filterXQTrades(TServiceCntl oCntl
            , Set<String> orderIds
            , Set<Long> tradeIds) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        ParameterChecker.check(!orderIds.isEmpty(), "orderIds should not be empty");
        ParameterChecker.checkNotNull(tradeIds, "tradeIds should not be null");
        ParameterChecker.check(!tradeIds.isEmpty(), "tradeIds should not be empty");

        return new DBQueryHelper<Map<Long, HostingXQTrade>>(ArbitrageDB.Global()) {
            @Override
            protected Map<Long, HostingXQTrade> onQuery(Connection conn) throws Exception {
                return new XQTradeTable(conn).batchFilterTrades(orderIds, tradeIds);
            }
        }.query();
    }

    @Override
    protected List<HostingXQTradeRelatedItem> getXQTradeRelatedItems(TServiceCntl oCntl
            , String orderId, long tradeId) throws ErrorInfo, TException {
        ParameterChecker.check(StringUtils.isNotEmpty(orderId), "orderId should not be empty");
        ParameterChecker.check(tradeId > 0, "tradeId should > 0");

        List<XQTradeRelatedItem> dbRelatedItems = new DBQueryHelper<List<XQTradeRelatedItem>>(ArbitrageDB.Global()) {
            @Override
            protected List<XQTradeRelatedItem> onQuery(Connection conn) throws Exception {
                return new XQTradeRelatedTable(conn).getItemsByTradeId(orderId, tradeId);
            }
        }.query();

        List<HostingXQTradeRelatedItem> resultItems = new ArrayList<HostingXQTradeRelatedItem>();
        for (XQTradeRelatedItem dbRelatedItem : dbRelatedItems) {
            resultItems.add(XQOrderHelper.dbRelatedItem2Hosting(dbRelatedItem));
        }

        return resultItems;
    }

    @Override
    protected HostingXQComposeLimitOrderSettings getSystemXQComposeLimitOrderSettings(TServiceCntl oCntl)
            throws ErrorInfo, TException {
        return ComposeSettingsProvider.get(trade_hosting_arbitrageConstants.SYSTEM_SETTING_KEY).getSettings();
    }

    @Override
    protected void setSystemXQComposeLimitOrderSettings(TServiceCntl oCntl, HostingXQComposeLimitOrderSettings settings)
            throws ErrorInfo, TException {
        ComposeSettingsProvider.get(trade_hosting_arbitrageConstants.SYSTEM_SETTING_KEY).updateSettings(settings);
    }


}
