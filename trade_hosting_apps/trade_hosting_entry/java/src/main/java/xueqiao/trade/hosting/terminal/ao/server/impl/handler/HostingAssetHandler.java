package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.io.File;
import java.util.concurrent.Callable;

public class HostingAssetHandler extends HandlerBase {

    public HostingAssetHandler(TServiceCntl serviceCntl
            , LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public TradeHostingAssetStub getAssetStub() {
        return AssetStubFactory.getStub();
    }

    public HostingSledContractPositionPage getHostingSledContractPosition(ReqHostingSledContractPositionOption option) throws ErrorInfo {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        if (!hasPermission(EHostingUserRole.AdminGroup)) {
            checkHasRelatedAccount(option.getSubAccountId());
        }

        return ErrorInfoCallHelper.call(new Callable<HostingSledContractPositionPage>() {
            @Override
            public HostingSledContractPositionPage call() throws Exception {
                return getAssetStub().getHostingSledContractPosition(option, null);
            }
        });
    }

    public HostingFundPage getHostingSubAccountFund(ReqHostingFundOption option) throws ErrorInfo {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountIdsSize() > 0, "subAccountIds size should > 0");
        if (!hasPermission(EHostingUserRole.AdminGroup)) {
            for (long subAccountId : option.getSubAccountIds()) {
                ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
                checkHasRelatedAccount(subAccountId);
            }
        }
        return ErrorInfoCallHelper.call(new Callable<HostingFundPage>() {
            @Override
            public HostingFundPage call() throws Exception {
                return getAssetStub().getHostingSubAccountFund(option);
            }
        });
    }

    public HostingSubAccountFund changeSubAccountFund(FundChange fundChange) throws ErrorInfo {
        ParameterChecker.checkNotNull(fundChange, "fundChange");
        ParameterChecker.check(fundChange.getSubAccountId() > 0, "subAccountId should > 0");
        ParameterChecker.check(fundChange.getAmount() > 0, "amount should > 0");
        ParameterChecker.checkNotNull(fundChange.getCurrency(), "currency");
        ParameterChecker.checkNotNull(fundChange.getDirection(), "direction");
        ParameterChecker.checkNotNull(fundChange.getTicket(), "ticket");

        permit(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingSubAccountFund>() {
            @Override
            public HostingSubAccountFund call() throws Exception {
                return getAssetStub().changeSubAccountFund(fundChange);
            }
        });
    }

    public HostingSubAccountFund chagneSubAccountCreditAmount(CreditAmountChange creditAmountChange) throws ErrorInfo {
        ParameterChecker.checkNotNull(creditAmountChange, "creditAmountChange");
        ParameterChecker.check(creditAmountChange.getSubAccount() > 0, "subAccountId should > 0");
        ParameterChecker.check(creditAmountChange.getTotalAmount() > 0, "amount should > 0");
        ParameterChecker.checkNotNull(creditAmountChange.getCurrency(), "currency");

        permit(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingSubAccountFund>() {
            @Override
            public HostingSubAccountFund call() throws Exception {
                return getAssetStub().setSubAccountCreditAmount(creditAmountChange);
            }
        });
    }

    public HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(ReqMoneyRecordOption option, IndexedPageOption pageOption) throws ErrorInfo {

        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        PageOptionHelper.checkIndexedPageOption(pageOption, 100);

        permit(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<HostingSubAccountMoneyRecordPage>() {
            @Override
            public HostingSubAccountMoneyRecordPage call() throws Exception {
                return getAssetStub().getHostingSubAccountMoneyRecord(option, pageOption);
            }
        });
    }

    public AssetTradeDetailPage getAssetTradeDetail(ReqHostingAssetTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getSubAccountId() > 0, "subAccountId should > 0");
        ParameterChecker.check(option.getSledContractId() > 0, "sled contract Id should  >0");
        if (!hasPermission(EHostingUserRole.AdminGroup)) {
            checkHasRelatedAccount(option.getSubAccountId());
        }
        return ErrorInfoCallHelper.call(new Callable<AssetTradeDetailPage>() {
            @Override
            public AssetTradeDetailPage call() throws Exception {
                return getAssetStub().getAssetPositionTradeDetail(option, pageOption);
            }
        });
    }

    public HostingFundPage getSubAccountFundHistory(ReqSubAccountFundHistoryOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "ReqSubAccountFundHistoryOption must set");
        ParameterChecker.check(option.getSubAccountId() > 0, "SubAccountId must set");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<HostingFundPage>() {
            @Override
            public HostingFundPage call() throws Exception {
                return getAssetStub().getSubAccountFundHistory(option, pageOption);
            }
        });
    }


    public SettlementPositionDetailPage getSubAccountPositionHistory(ReqSettlementPositionDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "ReqSettlementPositionDetailOption must set");
        ParameterChecker.checkNotNull(pageOption, "IndexedPageOption must set");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<SettlementPositionDetailPage>() {
            @Override
            public SettlementPositionDetailPage call() throws Exception {
                return getAssetStub().getSettlementPositionDetail(option, pageOption);
            }
        });
    }


    public AssetTradeDetailPage getSubAccountPositionHistoryTradeDetail(ReqSettlementPositionTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option must set");
        ParameterChecker.checkNotNull(pageOption, "IndexedPageOption must set");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<AssetTradeDetailPage>() {
            @Override
            public AssetTradeDetailPage call() throws Exception {
                return getAssetStub().getSettlementPositionTradeDetail(option, pageOption);
            }
        });
    }

    public void deleteExpiredContractPosition(long subAccountId, long sledContractId) throws ErrorInfo {
        checkHasRelatedAccount(subAccountId);
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getAssetStub().deleteExpiredContractPosition(subAccountId, sledContractId);
                return null;
            }
        });
    }

}
