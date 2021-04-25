package xueqiao.trade.hosting.dealing.thriftapi.impl;

import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.dealing.thriftapi.HostingExecOrderPage;
import xueqiao.trade.hosting.dealing.thriftapi.helper.DealingStubFactory;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HostingDealApiClient implements IHostingDealingApi {

    private IHostingContractApi mContractApi;

    public HostingDealApiClient(IHostingContractApi contractApi) {
        this.mContractApi = contractApi;
    }

    private void checkAccountSummary(HostingExecOrderTradeAccountSummary accountSummary) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(accountSummary.isSetTradeAccountId()
                && accountSummary.getTradeAccountId() > 0);
        ParameterChecker.checkInnerArgument(accountSummary.isSetBrokerId()
                && accountSummary.getBrokerId() > 0);
        ParameterChecker.checkInnerArgument(accountSummary.isSetTechPlatform());
    }

    @Override
    public long createOrderId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return DealingStubFactory.getStub().createExecOrderId();
            }
        });
    }

    @Override
    public long createTradeId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return DealingStubFactory.getStub().createExecTradeId();
            }
        });
    }

    @Override
    public long createTradeLegId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return DealingStubFactory.getStub().createExecTradeLegId();
            }
        });
    }

    @Override
    public void createUserDeal(int subUserId
            , long subAccountId
            , long execOrderId
            , HostingExecOrderContractSummary contractSummary
            , HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subUserId > 0);
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerArgument(execOrderId > 0);
        ParameterChecker.checkInnerArgument(contractSummary != null);
        ParameterChecker.checkInnerArgument(orderDetail != null);

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                DealingStubFactory.getStub().createUserDeal(subUserId
                        , subAccountId
                        , execOrderId
                        , contractSummary
                        , orderDetail
                        , source);
                return null;
            }
        });
    }

    @Override
    public void createUserDeal(int subUserId
            , long subAccountId
            , long execOrderId
            , long sledContractId
            , HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(sledContractId > 0);
        createUserDeal(subUserId
                , subAccountId
                , execOrderId
                , HostingDealingHelper.contractSummaryFromSledContractID(sledContractId, mContractApi)
                , orderDetail
                , source);
    }

    @Override
    public void revokeDeal(long execOrderId) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(execOrderId > 0);

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                DealingStubFactory.getStub().revokeDeal(execOrderId);
                return null;
            }
        });
    }

    @Override
    public HostingExecOrder getOrder(long execOrderId) throws ErrorInfo {
        List<HostingExecOrder> execOrderList = ErrorInfoCallHelper.call(new Callable<List<HostingExecOrder>>() {
            @Override
            public List<HostingExecOrder> call() throws Exception {
                return DealingStubFactory.getStub().getOrder(execOrderId);
            }
        });
        if (execOrderList == null || execOrderList.isEmpty()) {
            return null;
        }
        return execOrderList.get(0);
    }

    @Override
    public List<HostingExecTrade> getOrderTrades(long execOrderId) throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<List<HostingExecTrade>>() {
            @Override
            public List<HostingExecTrade> call() throws Exception {
                return DealingStubFactory.getStub().getOrderTrades(execOrderId);
            }
        });
    }

    @Override
    public List<HostingExecTradeLeg> getTradeRelatedLegs(long execTradeId) throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<List<HostingExecTradeLeg>>() {
            @Override
            public List<HostingExecTradeLeg> call() throws Exception {
                return DealingStubFactory.getStub().getTradeRelatedLegs(execTradeId);
            }
        });
    }

    @Override
    public List<HostingExecOrder> getAllInDealingOrders() throws ErrorInfo {
        int pageIndex = 0;
        final int pageSize = 500;

        List<HostingExecOrder> resultList = new ArrayList<>();
        while(true) {
            PageResult<HostingExecOrder> resultPage = getInDealingOrders(
                    new PageOption().setPageSize(pageSize).setPageIndex(pageIndex));
            if (resultPage == null || resultPage.getPageList().isEmpty()) {
                break;
            }

            resultList.addAll(resultPage.getPageList());
            ++pageIndex;
        }

        return resultList;
    }

    @Override
    public PageResult<HostingExecOrder> getInDealingOrders(PageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(pageOption != null);
        pageOption.checkValid(500);

        HostingExecOrderPage orderPage = ErrorInfoCallHelper.call(new Callable<HostingExecOrderPage>() {
            @Override
            public HostingExecOrderPage call() throws Exception {
                return DealingStubFactory.getStub().getInDealingOrderPage(
                        new IndexedPageOption().setPageIndex(pageOption.getPageIndex())
                                .setPageSize(pageOption.getPageSize())
                                .setNeedTotalCount(pageOption.isNeedTotalCount())
                );
            }
        });

        PageResult<HostingExecOrder> resultPage = new PageResult<>();
        resultPage.setTotalCount(orderPage.getTotalCount());
        resultPage.setPageList(orderPage.getResultList());
        return resultPage;
    }

    @Override
    public void clearAll() throws ErrorInfo {
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                DealingStubFactory.getStub().clearAll();
                return null;
            }
        });
    }
}
