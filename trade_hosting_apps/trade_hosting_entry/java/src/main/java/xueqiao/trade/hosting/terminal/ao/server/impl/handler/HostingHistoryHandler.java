package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexPage;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.helper.HistoryStubFactory;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderPage;
import xueqiao.trade.hosting.terminal.ao.HostingXQTradePage;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HostingHistoryHandler extends HandlerBase {
    public HostingHistoryHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public HostingXQOrderPage getXQOrderHisPage(
            QueryXQOrderHisIndexItemOption qryOption, IndexedPageOption pageOption) throws TException {
        PageOptionHelper.checkIndexedPageOption(pageOption, 200);
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");
        ParameterChecker.check(qryOption.isSetSubAccountIds(), "subAccountIds should be set");
        HashSet<Long> hasPemissionSubAccountIds = new HashSet<Long>();
        for (Long subAccountId : qryOption.getSubAccountIds()) {
            if (super.hasRelatedAccount(subAccountId)) {
                hasPemissionSubAccountIds.add(subAccountId);
            }
        }

        HostingXQOrderPage resultPage = new HostingXQOrderPage();
        resultPage.setResultList(new ArrayList<HostingXQOrder>());
        if (hasPemissionSubAccountIds.isEmpty()) {
            resultPage.setTotalCount(0);
            return resultPage;
        }

        HostingXQOrderHisIndexPage indexPage = HistoryStubFactory.getStub().getXQOrderHisIndexPage(
                new QueryXQOrderHisIndexItemOption(qryOption).setSubAccountIds(hasPemissionSubAccountIds), pageOption);
        resultPage.setTotalCount(indexPage.getTotalNum());
        if (indexPage.getResultListSize() <= 0) {
            return resultPage;
        }

        Set<String> orderIds = new HashSet<String>();
        for (HostingXQOrderHisIndexItem indexItem : indexPage.getResultList()) {
            orderIds.add(indexItem.getOrderId());
        }

        Map<String, HostingXQOrder> ordersMap = ArbitrageStubFactory.getStub().getXQOrders(orderIds);
        for (HostingXQOrderHisIndexItem indexItem : indexPage.getResultList()) {
            if (ordersMap.containsKey(indexItem.getOrderId())) {
                resultPage.getResultList().add(ordersMap.get(indexItem.getOrderId()));
            }
        }

        return resultPage;
    }

    public HostingXQTradePage getXQTradeHisPage(
            QueryXQTradeHisIndexItemOption qryOption, IndexedPageOption pageOption) throws TException {
        PageOptionHelper.checkIndexedPageOption(pageOption, 200);
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");

        ParameterChecker.check(qryOption.isSetSubAccountIds(), "subAccountIds should be set");
        HashSet<Long> hasPemissionSubAccountIds = new HashSet<Long>();
        for (Long subAccountId : qryOption.getSubAccountIds()) {
            if (super.hasRelatedAccount(subAccountId)) {
                hasPemissionSubAccountIds.add(subAccountId);
            }
        }

        HostingXQTradePage resultPage = new HostingXQTradePage();
        resultPage.setResultList(new ArrayList<HostingXQTrade>());
        if (hasPemissionSubAccountIds.isEmpty()) {
            resultPage.setTotalCount(0);
            return resultPage;
        }

        HostingXQTradeHisIndexPage indexPage = HistoryStubFactory.getStub().getXQTradeHisIndexPage(
                new QueryXQTradeHisIndexItemOption(qryOption).setSubAccountIds(hasPemissionSubAccountIds)
                , pageOption);
        resultPage.setTotalCount(indexPage.getTotalNum());
        if (indexPage.getResultListSize() <= 0) {
            return resultPage;
        }

        Set<String> orderIds = new HashSet<String>();
        Set<Long> tradeIds = new HashSet<Long>();

        for (HostingXQTradeHisIndexItem indexItem : indexPage.getResultList()) {
            orderIds.add(indexItem.getOrderId());
            tradeIds.add(indexItem.getTradeId());
        }

        Map<Long, HostingXQTrade> tradesMap = ArbitrageStubFactory.getStub().filterXQTrades(orderIds, tradeIds);
        for (HostingXQTradeHisIndexItem indexItem : indexPage.getResultList()) {
            if (tradesMap.containsKey(indexItem.getTradeId())) {
                resultPage.getResultList().add(tradesMap.get(indexItem.getTradeId()));
            }
        }

        return resultPage;
    }
}
