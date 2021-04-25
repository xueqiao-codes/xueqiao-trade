package xueqiao.trade.hosting.dealing.thriftapi.server.impl;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealID;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.ExecIDGenerator;
import xueqiao.trade.hosting.dealing.core.ExecOrderCreator;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutor;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutorRunnable;
import xueqiao.trade.hosting.dealing.core.ExecOrderManager;
import xueqiao.trade.hosting.dealing.core.OrderDealIDData;
import xueqiao.trade.hosting.dealing.core.OrderRefData;
import xueqiao.trade.hosting.dealing.storage.DealingStorageApiV2;
import xueqiao.trade.hosting.dealing.thriftapi.HostingExecOrderPage;
import xueqiao.trade.hosting.dealing.thriftapi.server.TradeHostingDealingAdaptor;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class TradeHostingDealingHandler extends TradeHostingDealingAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        DealingStorageApiV2.cleanAll();
        ExecOrderManager.Global().destroyAll();
    }


    @Override
    protected void createUserDeal(TServiceCntl oCntl
            , int subUserId
            , long subAccountId
            , long execOrderId
            , xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary
            , xueqiao.trade.hosting.HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo, TException {
        ExecOrderCreator.createUserDeal(subUserId
                , subAccountId, execOrderId, contractSummary, orderDetail, source);
    }

    @Override
    protected void revokeDeal(TServiceCntl oCntl
            , long execOrderId) throws ErrorInfo, TException {
        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(execOrderId);
        if (executor == null) {
            return ;
        }

        new SyncResult<Void>(executor.getWorkThread()) {

            @Override
            protected Void onCall() throws Exception {
                return executor.revoke();
            }
        }.get();
    }

    @Override
    protected List<HostingExecOrder> getOrder(TServiceCntl oCntl, long execOrderId) throws ErrorInfo, TException {
        List<HostingExecOrder> resultList = new ArrayList<>();
        HostingExecOrder execOrder = DealingStorageApiV2.getOrder(execOrderId);
        if (execOrder != null) {
            execOrder.getStateInfo().unsetHistoryStates();
            execOrder.unsetNotifyStateHandleInfos();
            resultList.add(execOrder);
        }

        return resultList;
    }

    @Override
    protected List<HostingExecTrade> getOrderTrades(TServiceCntl oCntl, long execOrderId) throws ErrorInfo, TException {
        return DealingStorageApiV2.getOrderTrades(execOrderId);
    }

    @Override
    protected List<HostingExecTradeLeg> getTradeRelatedLegs(TServiceCntl oCntl, long execTradeId)
            throws ErrorInfo, TException {
        return DealingStorageApiV2.getRelatedTradeLegs(execTradeId);
    }

    private long matchInDealingEntries(Set<Long> execOrderIds) throws ErrorInfo {
        List<Long> matchedExecOrderIds = new ArrayList<>();
        for (Long execOrderId : execOrderIds) {
            if (null == ExecOrderManager.Global().getExecutor(execOrderId)) {
                continue;
            }
            matchedExecOrderIds.add(execOrderId);
        }

        if (matchedExecOrderIds.size() > 1) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "get multi match entries for index entry");
        }

        return matchedExecOrderIds.get(0);
    }

    @Override
    protected long getRunningExecOrderIdByOrderRef(TServiceCntl oCntl
            , HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderRef orderRef) throws ErrorInfo, TException {
        Set<Long> execOrderIds = OrderRefData.Global().get(accountSummary, orderRef);
        if (execOrderIds == null || execOrderIds.isEmpty()) {
            return -1;
        }

        return matchInDealingEntries(execOrderIds);
    }

    @Override
    protected long getRunningExecOrderIdByOrderDealID(TServiceCntl oCntl
            , HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderDealID dealId) throws ErrorInfo, TException {
        Set<Long> execOrderIds =  OrderDealIDData.Global().get(accountSummary, dealId);
        if (execOrderIds == null || execOrderIds.isEmpty()) {
            return -1;
        }

        return matchInDealingEntries(execOrderIds);
    }

    @Override
    protected HostingExecOrderPage getInDealingOrderPage(TServiceCntl oCntl, IndexedPageOption pageOption)
            throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(pageOption, "pageOption should not be null");

        PageOption apiPageOption = new PageOption();
        apiPageOption.setPageIndex(pageOption.getPageIndex())
                     .setPageSize(pageOption.getPageSize())
                     .setNeedTotalCount(pageOption.isNeedTotalCount());
        apiPageOption.checkValid(500);

        PageResult<HostingExecOrder> ordersPageResult = DealingStorageApiV2.getInDealingOrders(apiPageOption);

        HostingExecOrderPage resultPage = new HostingExecOrderPage();
        resultPage.setTotalCount(ordersPageResult.getTotalCount());

        if (ordersPageResult.getPageList() != null) {
            for (HostingExecOrder execOrder : ordersPageResult.getPageList()) {
                execOrder.unsetNotifyStateHandleInfos();
                execOrder.getStateInfo().unsetHistoryStates();
            }
        }
        resultPage.setResultList(ordersPageResult.getPageList());

        return resultPage;
    }

    @Override
    protected long createExecOrderId(TServiceCntl oCntl) throws ErrorInfo, TException {
        return ExecIDGenerator.Global().createExecOrderId();
    }

    @Override
    protected long createExecTradeId(TServiceCntl oCntl) throws ErrorInfo, TException {
        return ExecIDGenerator.Global().createExecTradeId();
    }

    @Override
    protected long createExecTradeLegId(TServiceCntl oCntl) throws ErrorInfo, TException {
        return ExecIDGenerator.Global().createExecTradeLegId();
    }

    @Override
    public void destroy() {
    }
}
