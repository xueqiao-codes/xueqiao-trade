package xueqiao.trade.hosting.asset.thriftapi.server.impl;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.asset.api.*;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.storage.*;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSledContractPositionTable;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountMoneyRecordTable;
import xueqiao.trade.hosting.asset.storage.PageOptionHelper;
import xueqiao.trade.hosting.asset.storage.account.sub.SubAccountHostingFundTable;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeDetailHistoryTable;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.asset.thriftapi.server.TradeHostingAssetAdaptor;
import xueqiao.trade.hosting.asset.utils.CalculatorUtils;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

public class TradeHostingAssetHandler extends TradeHostingAssetAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected HostingSledContractPositionPage getHostingSledContractPosition(TServiceCntl oCntl, ReqHostingSledContractPositionOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");

        HostingSledContractPositionPage page = new DBQueryHelper<HostingSledContractPositionPage>(AssetDB.Global()) {
            @Override
            protected HostingSledContractPositionPage onQuery(Connection connection) throws Exception {
                AssetSledContractPositionTable table = new AssetSledContractPositionTable(connection);
                if (pageOption == null) {
                    List<HostingSledContractPosition> list = table.query(option);
                    new HostingSledContractPositionPage().setPage(list).setTotal(list.size());
                }
                return table.query(option, pageOption);
            }
        }.query();

        for (HostingSledContractPosition position : page.getPage()) {
            AssetSubAccountGlobalData data = AssetGlobalDataFactory.getInstance().getAssetGlobalData(position.getSubAccountId());
            HostingSledContractPosition cacheData = data.getHostingSledContractPosition(position.getSledContractId());
            if (cacheData != null) {
                position.setPositionFund(cacheData.getPositionFund());
            }
            if (CalculatorUtils.isDoubleZero(position.getPositionFund().getCalculatePrice())) {
                position.getPositionFund().unsetCalculatePrice();
                position.getPositionFund().unsetPositionProfit();
            }
        }
        return page;
    }

    @Override
    protected HostingFundPage getHostingSubAccountFund(TServiceCntl oCntl, ReqHostingFundOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountIdsSize() > 0, "subAccountIds size should > 0");
        for (long subAccountId : option.getSubAccountIds()) {
            ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        }

        HostingFundPage page = new HostingFundPage();
        List<HostingFund> list = new LinkedList<>();
        for (long subAccountId : option.getSubAccountIds()) {
            AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccountId);

            if (option.isBaseCurrency()) {
                Map<String, HostingFund> basicMap = globalData.getBasicCurrencyFundMap();
                if (basicMap.size() > 0) {
                    list.addAll(basicMap.values());
                }
            } else {
                Map<String, HostingFund> map = globalData.getHostingFundMap();
                if (map.size() > 0) {
                    list.addAll(map.values());
                }
            }
        }

        int total = 0;
        for (HostingFund fund : list) {
            page.addToPage(fund);
            total++;
        }
        page.setTotal(total);
        return page;
    }

    @Override
    protected HostingSubAccountFund changeSubAccountFund(TServiceCntl oCntl, FundChange fundChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        ParameterChecker.checkNotNull(fundChange, "fundChange");
        ParameterChecker.check(fundChange.getSubAccountId() > 0, "subAccountId should > 0");
        ParameterChecker.check(fundChange.getAmount() > 0, "amount should > 0");
        ParameterChecker.checkNotNull(fundChange.getCurrency(), "currency");
        ParameterChecker.checkNotNull(fundChange.getDirection(), "direction");
        ParameterChecker.checkNotNull(fundChange.getTicket(), "ticket");

        FundChangeLogic fundChangeLogic = new FundChangeLogic();
        fundChangeLogic.change(fundChange);
        PageOption pageOption = new PageOption();
        pageOption.setPageIndex(0);
        pageOption.setPageSize(1);

        ReqSubAccountFundOption option = new ReqSubAccountFundOption();
        option.setCurrency(fundChange.getCurrency());
        option.setSubAccountId(fundChange.getSubAccountId());
        PageResult<HostingSubAccountFund> page = fundChangeLogic.query(option, pageOption);
        return page.getPageList().get(0);
    }

    @Override
    protected HostingSubAccountFund setSubAccountCreditAmount(TServiceCntl oCntl, CreditAmountChange amountChange) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        ParameterChecker.checkNotNull(amountChange, "creditAmountChange");
        ParameterChecker.check(amountChange.getSubAccount() > 0, "subAccountId should > 0");
        ParameterChecker.check(amountChange.getTotalAmount() > 0, "amount should > 0");
        ParameterChecker.checkNotNull(amountChange.getCurrency(), "currency");

        FundChangeLogic fundChangeLogic = new FundChangeLogic();
        fundChangeLogic.updateCreditAmount(amountChange);
        PageOption pageOption = new PageOption();
        pageOption.setPageIndex(0);
        pageOption.setPageSize(1);

        ReqSubAccountFundOption option = new ReqSubAccountFundOption();
        option.setCurrency(amountChange.getCurrency());
        option.setSubAccountId(amountChange.getSubAccount());
        PageResult<HostingSubAccountFund> page = fundChangeLogic.query(option, pageOption);
        return page.getPageList().get(0);
    }

    @Override
    protected SettlementPositionDetailPage getSettlementPositionDetail(TServiceCntl oCntl, ReqSettlementPositionDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        Preconditions.checkNotNull(pageOption);
        PageOption apiPageOption = map2ApiPageOption(pageOption);
        return new SettlePositionQuery().getSettlementPositionDetail(option, apiPageOption);
    }

    private PageOption map2ApiPageOption(IndexedPageOption pageOption) {
        PageOption apiPageOption = new PageOption();
        if (pageOption.isSetPageSize()) {
            apiPageOption.setPageSize(pageOption.getPageSize());
        }
        if (pageOption.isSetPageIndex()) {
            apiPageOption.setPageIndex(pageOption.getPageIndex());
        }
        if (pageOption.isSetNeedTotalCount()) {
            apiPageOption.setNeedTotalCount(pageOption.isNeedTotalCount());
        }
        return apiPageOption;
    }

    @Override
    protected HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(TServiceCntl oCntl, ReqMoneyRecordOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {

        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        PageOptionHelper.checkIndexedPageOption(pageOption, 100);

        PageResult<HostingSubAccountMoneyRecord> pageResult = new DBQueryHelper<PageResult<HostingSubAccountMoneyRecord>>(AssetDB.Global()) {
            @Override
            protected PageResult<HostingSubAccountMoneyRecord> onQuery(Connection connection) throws Exception {
                return new AssetSubAccountMoneyRecordTable(connection).query(option, map2ApiPageOption(pageOption));
            }
        }.query();

        return new HostingSubAccountMoneyRecordPage().setPage(pageResult.getPageList()).setTotal(pageResult.getTotalCount());
    }

    @Override
    protected AssetTradeDetailPage getAssetPositionTradeDetail(TServiceCntl oCntl, ReqHostingAssetTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        ParameterChecker.check(option.getSledContractId() > 0, "sledContractId should > 0");
        return new TradeDetailQuery().queryNetPositionTradeDetail(option, pageOption);
    }

    @Override
    protected AssetTradeDetailPage getSettlementPositionTradeDetail(TServiceCntl oCntl, ReqSettlementPositionTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        Preconditions.checkNotNull(option);
        Preconditions.checkNotNull(pageOption);
        return new SettlePositionQuery().getSettlementPositionTradeDetail(option, pageOption);
    }

    @Override
    public void destroy() {
    }

    @Override
    protected HostingPositionVolumePage getHostingPositionVolume(TServiceCntl oCntl, ReqHostingPositionVolumeOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        PageOptionHelper.checkIndexedPageOption(pageOption, 100);

        HostingSledContractPositionPage page = getHostingSledContractPositionPage(pageOption, option.isSetSubAccountId(), option.getSubAccountId(), option.isSetSledContractId(), option.getSledContractIdSize(), option.getSledContractId());

        HostingPositionVolumePage volumePage = new HostingPositionVolumePage();
        volumePage.setTotal(page.getTotal());
        List<HostingPositionVolume> list = new ArrayList<>();
        for (HostingSledContractPosition position : page.getPage()) {
            list.add(position.getPositionVolume());
        }
        volumePage.setPage(list);
        return volumePage;
    }

    @Override
    protected HostingPositionFundPage getHostingPositionFund(TServiceCntl oCntl, ReqHostingPositionFundOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        PageOptionHelper.checkIndexedPageOption(pageOption, 100);
        HostingSledContractPositionPage page = getHostingSledContractPositionPage(pageOption, option.isSetSubAccountId(), option.getSubAccountId(), option.isSetSledContractId(), option.getSledContractIdSize(), option.getSledContractId());

        HostingPositionFundPage fundPage = new HostingPositionFundPage();
        fundPage.setTotal(page.getTotal());

        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(option.getSubAccountId());
        List<HostingPositionFund> list = new ArrayList<>();
        for (HostingSledContractPosition position : page.getPage()) {
            HostingSledContractPosition contractPosition = globalData.getHostingSledContractPosition(position.getSledContractId());
            if (contractPosition.getPositionVolume().getNetPosition() == 0) {
                contractPosition.getPositionFund().unsetPositionProfit();
            }

            list.add(contractPosition.getPositionFund());
        }
        fundPage.setPage(list);
        return fundPage;
    }

    @Override
    protected HostingFundPage getSubAccountFundHistory(TServiceCntl oCntl, ReqSubAccountFundHistoryOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");

        return new DBQueryHelper<HostingFundPage>(AssetDB.Global()) {
            @Override
            protected HostingFundPage onQuery(Connection connection) throws Exception {
                return new SubAccountHostingFundTable(connection).query(option, pageOption);
            }
        }.query();
    }

    @Override
    protected void deleteExpiredContractPosition(TServiceCntl oCntl, long subAccountId, long sledContractId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId must >0");
        ParameterChecker.check(sledContractId > 0, "sledContractId must >0");
        new PositionExpired().remove(subAccountId, sledContractId);
    }

    @Override
    protected AssetTradeDetailPage getTradeAccountPositionTradeDetail(TServiceCntl oCntl, ReqTradeAccountPositionTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {

        return new DBQueryHelper<AssetTradeDetailPage>(AssetDB.Global()) {
            @Override
            protected AssetTradeDetailPage onQuery(Connection connection) throws Exception {
                TradeDetailHistoryTable tradeDetailHistoryTable = new TradeDetailHistoryTable(connection);
                return tradeDetailHistoryTable.query(option, pageOption);
            }
        }.query();
    }

    @Override
    protected TradeAccountPositionPage getTradeAccountPosition(TServiceCntl oCntl, ReqTradeAccountPositionOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new TradeAccountPositionQuery().query(option, pageOption);
    }

    @Override
    protected AssignPositionResp assignPosition(TServiceCntl oCntl, List<PositionAssigned> positonAssigneds) throws ErrorInfo, TException {

        return new PositionAssign().assign(positonAssigneds);
    }

    private HostingSledContractPositionPage getHostingSledContractPositionPage(IndexedPageOption pageOption, boolean setSubAccountId, long subAccountId, boolean setSledContractId, int sledContractIdSize, Set<Long> sledContractId) throws ErrorInfo {
        return new DBQueryHelper<HostingSledContractPositionPage>(AssetDB.Global()) {
            @Override
            protected HostingSledContractPositionPage onQuery(Connection connection) throws Exception {
                AssetSledContractPositionTable table = new AssetSledContractPositionTable(connection);
                ReqHostingSledContractPositionOption reqOption = new ReqHostingSledContractPositionOption();
                if (setSubAccountId) {
                    reqOption.setSubAccountId(subAccountId);
                }
                if (setSledContractId && sledContractIdSize > 0) {
                    reqOption.setSledContractIds(sledContractId);
                }
                return table.query(reqOption, pageOption);
            }
        }.query();
    }

    @Override
    protected void removeAllAssetData(TServiceCntl oCntl) throws ErrorInfo, TException {
        new DataRemove().remove();
    }
}
