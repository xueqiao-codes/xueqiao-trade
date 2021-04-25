package xueqiao.trade.hosting.asset.api;

import com.google.common.base.Preconditions;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountFundTable;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountMoneyRecordTable;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class FundChangeLogic {

    public void change(FundChange fundChange) throws ErrorInfo {
        Preconditions.checkNotNull(fundChange);
        Preconditions.checkArgument(fundChange.getSubAccountId() > 0);
        Preconditions.checkArgument(fundChange.getAmount() > 0);
        Preconditions.checkArgument(fundChange.isSetCurrency());
        Preconditions.checkArgument(fundChange.isSetDirection());
        Preconditions.checkArgument(fundChange.isSetTicket());

        synchronized (FundChangeLogic.class) {
            new DBTransactionHelper<Void>(AssetDB.Global()) {
                AssetSubAccountFundTable assetSubAccountFundTable;
                AssetSubAccountMoneyRecordTable assetSubAccountMoneyRecordTable;
                HostingSubAccountFund hostingSubAccountFund;
                boolean isNew;

                @Override
                public void onPrepareData() throws Exception {

                    assetSubAccountFundTable = new AssetSubAccountFundTable(getConnection());
                    assetSubAccountMoneyRecordTable = new AssetSubAccountMoneyRecordTable(getConnection());
                    HostingSubAccountMoneyRecord record = assetSubAccountMoneyRecordTable.getTicketMR(fundChange.getSubAccountId(), fundChange.getTicket());
                    if (record != null) {
                        throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_FUND_CHANGE_OP_MONEY_DUPLICATE_TICKET.getValue()
                                , "Duplicate ticket.");
                    }
                    hostingSubAccountFund = assetSubAccountFundTable.query(fundChange.getSubAccountId(), fundChange.getCurrency());
                    if (hostingSubAccountFund == null) {
                        hostingSubAccountFund = new HostingSubAccountFund();
                        isNew = true;
                    }
                    if (HostingSubAccountMoneyRecordDirection.WITHDRAW.equals(fundChange.getDirection())) {
                        if (hostingSubAccountFund.getBalance() < fundChange.getAmount()) {
                            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_FUND_CHANGE_BALANCE_NOT_ENOUGH.getValue()
                                    , "Balance not enough.");
                        }
                    }
                }

                @Override
                public void onUpdate() throws Exception {

                    HostingSubAccountMoneyRecord lastOpMR = assetSubAccountMoneyRecordTable.getLatestMR(fundChange.getSubAccountId(), fundChange.getCurrency());
                    if (lastOpMR == null) {
                        lastOpMR = new HostingSubAccountMoneyRecord();
                    }

                    HostingSubAccountMoneyRecord opMR = new HostingSubAccountMoneyRecord();
                    HostingSubAccountFund opSubAccount = new HostingSubAccountFund();

                    opSubAccount.setSubAccountId(fundChange.getSubAccountId());
                    opSubAccount.setCurrency(fundChange.getCurrency());
                    opMR.setSubAccountId(fundChange.getSubAccountId());
                    opMR.setRecordTimestampMs(System.currentTimeMillis());
                    opMR.setTotalAmount(fundChange.getAmount());
                    opMR.setTicket(fundChange.getTicket());
                    opMR.setCurrency(fundChange.getCurrency());

                    if (HostingSubAccountMoneyRecordDirection.DEPOSIT.equals(fundChange.getDirection())) {
                        opSubAccount.setDepositAmount(hostingSubAccountFund.getDepositAmount() + fundChange.getAmount());
                        opSubAccount.setBalance(hostingSubAccountFund.getBalance() + fundChange.getAmount());
                        opMR.setDirection(HostingSubAccountMoneyRecordDirection.DEPOSIT);
                        opMR.setDepositAmountBefore(lastOpMR.getDepositAmountAfter());
                        opMR.setDepositAmountAfter(lastOpMR.getDepositAmountAfter() + fundChange.getAmount());
                        opMR.setWithdrawAmountBefore(lastOpMR.getWithdrawAmountBefore());
                        opMR.setWithdrawAmountAfter(lastOpMR.getWithdrawAmountAfter());
                    } else if (HostingSubAccountMoneyRecordDirection.WITHDRAW.equals(fundChange.getDirection())) {
                        opSubAccount.setWithdrawAmount(hostingSubAccountFund.getWithdrawAmount() + fundChange.getAmount());
                        opSubAccount.setBalance(hostingSubAccountFund.getBalance() - fundChange.getAmount());
                        opMR.setDirection(HostingSubAccountMoneyRecordDirection.WITHDRAW);
                        opMR.setDepositAmountBefore(lastOpMR.getDepositAmountBefore());
                        opMR.setDepositAmountAfter(lastOpMR.getDepositAmountAfter());
                        opMR.setWithdrawAmountBefore(lastOpMR.getWithdrawAmountAfter());
                        opMR.setWithdrawAmountAfter(lastOpMR.getWithdrawAmountAfter() + fundChange.getAmount());
                    } else {
                        throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_FUND_CHANGE_DIRECTION_NOT_FOUND.getValue(), "fund change direction not found.");
                    }

                    if (isNew) {
                        assetSubAccountFundTable.add(opSubAccount);
                    } else {
                        assetSubAccountFundTable.update(opSubAccount);
                    }
                    assetSubAccountMoneyRecordTable.add(opMR);
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();
        }
        calculateFund(fundChange);
    }

    private void calculateFund(FundChange fundChange) throws ErrorInfo {
        FundCalculateData fundData = new FundCalculateData();
        fundData.setSubAccountId(fundChange.getSubAccountId());
        fundData.setCurrency(fundChange.getCurrency());
        ExecutorHandler.calculateFundDelay(fundData, 1, TimeUnit.SECONDS);
    }

    public PageResult<HostingSubAccountFund> query(ReqSubAccountFundOption option, PageOption pageOption) throws ErrorInfo {

        return new DBQueryHelper<PageResult<HostingSubAccountFund>>(AssetDB.Global()) {
            @Override
            protected PageResult<HostingSubAccountFund> onQuery(Connection connection) throws Exception {
                return new AssetSubAccountFundTable(connection).query(option, pageOption);

            }
        }.query();
    }

    public void updateCreditAmount(CreditAmountChange amountChange) throws ErrorInfo {

        new DBTransactionHelper<Void>(AssetDB.Global()) {
            AssetSubAccountFundTable assetSubAccountFundTable;

            boolean isNew;

            @Override
            public void onPrepareData() throws Exception {
                assetSubAccountFundTable = new AssetSubAccountFundTable(getConnection());

                HostingSubAccountFund hostingSubAccountFund = assetSubAccountFundTable.query(amountChange.getSubAccount(), amountChange.getCurrency());
                if (hostingSubAccountFund == null) {
                    isNew = true;
                }
            }

            @Override
            public void onUpdate() throws Exception {

                HostingSubAccountFund opSubAccount = new HostingSubAccountFund();
                opSubAccount.setSubAccountId(amountChange.getSubAccount());
                opSubAccount.setCurrency(amountChange.getCurrency());
                opSubAccount.setCreditAmount(amountChange.getTotalAmount());

                if (isNew) {
                    assetSubAccountFundTable.add(opSubAccount);
                } else {
                    assetSubAccountFundTable.update(opSubAccount);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

    }
}
