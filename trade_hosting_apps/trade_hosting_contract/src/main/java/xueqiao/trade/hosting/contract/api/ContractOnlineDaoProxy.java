package xueqiao.trade.hosting.contract.api;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.server.ContractOnlineDaoServiceAdaptor;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.server.impl.ContractOnlineDaoServiceHandler;
import com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledExchangeOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledExchangePage;
import com.longsheng.xueqiao.contract.thriftapi.ContractVersion;
import com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage;
import com.longsheng.xueqiao.contract.thriftapi.DbLockingInfo;
import com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption;
import com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTime;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage;
import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ContractOnlineDaoProxy extends ContractOnlineDaoServiceAdaptor {

    private ContractOnlineDaoServiceHandler mProxy;

    public ContractOnlineDaoProxy() {
        mProxy = new ContractOnlineDaoServiceHandler();
    }

    @Override
    public int InitApp(Properties properties) {
        return mProxy.InitApp(properties);
    }

    @Override
    public void destroy() {
        mProxy.destroy();
    }

    @Override
    protected SledContractPage reqSledContract(TServiceCntl oCntl
            , ReqSledContractOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        if (option == null || option.getSledContractIdListSize() <= 0) {
            return mProxy.reqSledContract(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
        }

        // 首先从缓存里面取，缓存里面取不到的，再从DB中补充
        SledContractPage resultPage = new SledContractPage();
        resultPage.setPage(new ArrayList<>());

        List<Integer> unCachedContractIds = new ArrayList<>();
        for (Integer sledContractId : option.getSledContractIdList()) {
            SledContract contract = ContractDataCache.Global().getContract(sledContractId);
            if (contract != null) {
                resultPage.getPage().add(contract);
            } else {
                unCachedContractIds.add(sledContractId);
            }
        }

        if (unCachedContractIds.isEmpty()) {
            return resultPage;
        }

        SledContractPage backendPage =
                mProxy.reqSledContract(
                        oCntl.getPlatformArgs()
                        , new ReqSledContractOption().setSledContractIdList(unCachedContractIds)
                        , 0
                        , unCachedContractIds.size());
        if (backendPage.getPageSize() <= 0) {
            return resultPage;
        }

        for(SledContract contract : backendPage.getPage()) {
            ContractDataCache.Global().putContract(contract);
            resultPage.getPage().add(contract);
        }

        return resultPage;
    }

    @Override
    protected SledContractDetailsPage reqSledContractDetail(TServiceCntl oCntl
            , ReqSledContractDetailsOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        if (option == null || option.getSledContractIdsSize() != 1) {
            return mProxy.reqSledContractDetail(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
        }

        SledContractDetailsPage resultPage = new SledContractDetailsPage();
        resultPage.setPage(new ArrayList<>());
        Integer sledContractId = option.getSledContractIdsIterator().next();

        SledContractPage contractPage = this.reqSledContract(oCntl.getPlatformArgs()
                , new ReqSledContractOption().setSledContractIdList(Arrays.asList(sledContractId))
                , 0
                , 1);
        if (contractPage.getPageSize() <= 0) {
            return resultPage;
        }

        SledContractDetails contractDetails = new SledContractDetails();
        contractDetails.setSledContract(contractPage.getPage().get(0));
        resultPage.getPage().add(contractDetails);

        SledCommodityPage commodityPage = this.reqSledCommodity(oCntl.getPlatformArgs()
                , new ReqSledCommodityOption().setSledCommodityIdList(
                        Arrays.asList(contractDetails.getSledContract().getSledCommodityId()))
                , 0
                , 1);
        if (commodityPage.getPageSize() > 0) {
            contractDetails.setSledCommodity(commodityPage.getPage().get(0));
        }

        return resultPage;
    }

    @Override
    protected CommodityMappingPage reqCommodityMapping(TServiceCntl oCntl
            , ReqCommodityMappingOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        CommodityMappingPage resultPage = ContractDataCache.Global().getCommodityMapping(option);
        if (resultPage != null) {
            return resultPage;
        }

        resultPage = mProxy.reqCommodityMapping(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
        if (resultPage != null) {
            ContractDataCache.Global().putCommodityMapping(option, resultPage);
        }
        return resultPage;
    }

    @Override
    protected SledExchangePage reqSledExchange(TServiceCntl oCntl
            , ReqSledExchangeOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        return mProxy.reqSledExchange(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
    }

    @Override
    protected SledCommodityPage reqSledCommodity(TServiceCntl oCntl
            , ReqSledCommodityOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        if (option == null || option.getSledCommodityIdListSize() <= 0) {
            return mProxy.reqSledCommodity(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
        }

        SledCommodityPage resultPage = new SledCommodityPage();
        resultPage.setPage(new ArrayList<>());
        List<Integer> unCachedCommodityIds = new ArrayList<>();

        for (Integer sledCommodityId : option.getSledCommodityIdList()) {
            SledCommodity commodity = ContractDataCache.Global().getCommodity(sledCommodityId);
            if (commodity != null) {
                resultPage.getPage().add(commodity);
            } else {
                unCachedCommodityIds.add(sledCommodityId);
            }
        }

        if (unCachedCommodityIds.isEmpty()) {
            return resultPage;
        }

        SledCommodityPage backendPage = mProxy.reqSledCommodity(oCntl.getPlatformArgs()
                , new ReqSledCommodityOption().setSledCommodityIdList(unCachedCommodityIds)
                , 0
                , unCachedCommodityIds.size());
        if (backendPage.getPageSize() <= 0) {
            return resultPage;
        }

        for (SledCommodity commodity : backendPage.getPage()) {
            ContractDataCache.Global().putCommodity(commodity);
            resultPage.getPage().add(commodity);
        }

        return resultPage;
    }

    @Override
    protected ContractVersionPage reqContractVersion(TServiceCntl oCntl
            , ReqContractVersionOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        return mProxy.reqContractVersion(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
    }

    @Override
    protected void updateContractVersion(TServiceCntl oCntl, ContractVersion contractVersion)
            throws ErrorInfo, TException {
        throw new ErrorInfo(10500, "not supported");
    }

    @Override
    protected void removeContractVersion(TServiceCntl oCntl, int versionId) throws ErrorInfo, TException {
        throw new ErrorInfo(10500, "not supported");
    }

    @Override
    protected void addDbLocking(TServiceCntl oCntl, DbLockingInfo dbLockingInfo) throws ErrorInfo, TException {
        throw new ErrorInfo(10500, "not supported");
    }

    @Override
    protected void removeDbLocking(TServiceCntl oCntl, String lockedBy) throws ErrorInfo, TException {
        throw new ErrorInfo(10500, "not supported");
    }

    @Override
    protected DbLockingInfo reqDbLockingInfo(TServiceCntl oCntl) throws ErrorInfo, TException {
        throw new ErrorInfo(10500, "not supported");
    }

    @Override
    protected SledTradeTimePage reqSledTradeTime(TServiceCntl oCntl
            , ReqSledTradeTimeOption option
            , int pageIndex
            , int pageSize) throws ErrorInfo, TException {
        if (option == null || option.getSledCommodityIdsSize() <= 0) {
            return mProxy.reqSledTradeTime(oCntl.getPlatformArgs(), option, pageIndex, pageSize);
        }

        SledTradeTimePage resultPage = new SledTradeTimePage();
        resultPage.setPage(new ArrayList<>());

        List<Integer> unCachedCommodityIds = new ArrayList<>();
        for (Integer sledCommodityId : option.getSledCommodityIds()) {
            SledTradeTime tradeTime = ContractDataCache.Global().getTradeTime(sledCommodityId);
            if (tradeTime != null) {
                resultPage.addToPage(tradeTime);
            } else {
                unCachedCommodityIds.add(sledCommodityId);
            }
        }
        if (unCachedCommodityIds.isEmpty()) {
            return resultPage;
        }

        SledTradeTimePage backendPage = mProxy.reqSledTradeTime(oCntl.getPlatformArgs()
                , new ReqSledTradeTimeOption().setSledCommodityIds(unCachedCommodityIds)
                , 0
                , unCachedCommodityIds.size());
        if (backendPage.getPageSize() <= 0) {
            return resultPage;
        }

        for (SledTradeTime tradeTime : backendPage.getPage()) {
            resultPage.addToPage(tradeTime);
            ContractDataCache.Global().putTradeTime(tradeTime);
        }

        return resultPage;
    }

}
