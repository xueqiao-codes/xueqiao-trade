package xueqiao.trade.hosting.asset.event.handler;

import net.qihoo.qconf.QconfException;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.core.HostingSubAccountGlobal;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.calculator.account.sub.AssetDataFileCalculator;
import xueqiao.trade.hosting.asset.job.AssetSubAccountJobScheduler;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSledContractPositionTable;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailTable;
import xueqiao.trade.hosting.asset.struct.FileOperator;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.asset.thriftapi.TradeDetailSource;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Init {

    public void initFromDb() throws Exception {
        clearCacheData();
        restoreTempData();
        initPosition();
        initFrozenPosition();
        initFund();
    }

    private void clearCacheData() {
        AssetGlobalDataFactory.getInstance().removeAll();
        AssetSubAccountJobScheduler.Global().addClearContractExpiredPositionJob();
    }

    public void initFromDealing() throws ErrorInfo {
        IHostingDealingApi dealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        List<HostingExecOrder> orders = dealingApi.getAllInDealingOrders();
        for (HostingExecOrder entry : orders) {
            HostingExecOrderTradeSummary tradeSummary = entry.getTradeSummary();
            if (tradeSummary != null && tradeSummary.getTradeListTotalVolume() > 0) {
                List<HostingExecTrade> trades = dealingApi.getOrderTrades(entry.getExecOrderId());
                HostingExecOrder execOrder = dealingApi.getOrder(entry.getExecOrderId());
                new TradeListHandler().saveHostingExecTrade(trades, TradeDetailSource.XQ_TRADE, execOrder.getSource());
            }
        }
    }

    private void restoreTempData() throws ErrorInfo, IOException {
        List<HostingSubAccount> subAccounts = HostingSubAccountGlobal.getInstance().getAllSubAccount();
        for (HostingSubAccount subAccount : subAccounts) {
            AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccount.getSubAccountId());
            AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccount.getSubAccountId());
            AssetDataFileCalculator fileExecutor = new AssetDataFileCalculator(subAccount.getSubAccountId());
            fileExecutor.addTask(FileOperator.READ);
        }
    }

    private void initFund() throws ErrorInfo, QconfException {
        List<HostingSubAccount> subAccounts = HostingSubAccountGlobal.getInstance().getAllSubAccount();
        List<String> currencyList = new SledCurrency().getSledCurrency();
        AppLog.d("subAccounts: " + subAccounts.size());

        for (HostingSubAccount subAccount : subAccounts) {
            AppLog.d("sub account: " + subAccount.getSubAccountName() + " id: " + subAccount.getSubAccountId());
            for (String currency : currencyList) {
                FundCalculateData fundCalculateData = new FundCalculateData();
                fundCalculateData.setSubAccountId(subAccount.getSubAccountId());
                fundCalculateData.setCurrency(currency);
                ExecutorHandler.calculateFund(fundCalculateData);
            }
            ExecutorHandler.printAsset(subAccount.getSubAccountId());
        }
    }

    private void initPosition() throws TException, SQLException, UnsupportedEncodingException {
        restoreHostingPosition();
        calculateExistTrades();
    }

    private void restoreHostingPosition() throws ErrorInfo, SQLException {
        List<HostingSledContractPosition> positionVolumes = queryPositionsFromDb();
        AppLog.i("init positionVolumes: " + positionVolumes);
        for (HostingSledContractPosition position : positionVolumes) {
            try {
                initPosition(position);
            } catch (TException e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }

    private List<HostingSledContractPosition> queryPositionsFromDb() throws ErrorInfo, SQLException {
        return new DBQueryHelper<List<HostingSledContractPosition>>(AssetDB.Global()) {
            @Override
            protected List<HostingSledContractPosition> onQuery(Connection connection) throws Exception {
                AssetSledContractPositionTable positionTable = new AssetSledContractPositionTable(connection);
                return positionTable.queryAllActivePosition();
            }
        }.query();
    }


    private void initPosition(HostingSledContractPosition position) throws ErrorInfo {
        AssetBaseCalculator calculator = AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_INIT_POSITION_KEY, position.getSubAccountId());
        calculator.addTask(position);
    }

    private void calculateExistTrades() throws ErrorInfo, SQLException, UnsupportedEncodingException {

        List<AssetTradeDetail> assetTradeDetails = new DBQueryHelper<List<AssetTradeDetail>>(AssetDB.Global()) {
            @Override
            protected List<AssetTradeDetail> onQuery(Connection connection) throws Exception {
                return new AssetTradeDetailTable(connection).queryAll();
            }
        }.query();

        for (AssetTradeDetail detail : assetTradeDetails) {
            ExecutorHandler.calculatePosition(detail);
        }
    }

    private void initFrozenPosition() throws Exception {
        IHostingDealingApi dealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        List<HostingExecOrder> list = dealingApi.getAllInDealingOrders();
        for (HostingExecOrder execOrder : list) {
            ExecutorHandler.calculateFrozenPosition(execOrder);
        }
    }
}
