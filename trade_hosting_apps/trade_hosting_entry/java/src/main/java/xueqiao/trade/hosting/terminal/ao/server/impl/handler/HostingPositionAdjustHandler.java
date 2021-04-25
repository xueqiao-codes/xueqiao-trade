package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionTradeDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.adjust.thriftapi.client.TradeHostingPositionAdjustStub;
import xueqiao.trade.hosting.position.adjust.thriftapi.helper.PositionAdjustStubFactory;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.ReqTradeAccountPositionOption;

import java.util.List;
import java.util.concurrent.Callable;

public class HostingPositionAdjustHandler extends HandlerBase {

    public HostingPositionAdjustHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    private TradeHostingPositionAdjustStub getStub() {
        return PositionAdjustStubFactory.getStub();
    }


    public AssetTradeDetailPage getTradeAccountPositionTradeDetail(ReqTradeAccountPositionOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getTradeAccountId() > 0, "TradeAccountId must set");
        permit(EHostingUserRole.AdminGroup);

        return ErrorInfoCallHelper.call(new Callable<AssetTradeDetailPage>() {
            @Override
            public AssetTradeDetailPage call() throws Exception {
                return mergeAssetTradeDetailPage(option, pageOption);
            }
        });
    }

    private AssetTradeDetailPage mergeAssetTradeDetailPage(ReqTradeAccountPositionOption option, IndexedPageOption pageOption) throws TException {
        ReqPositionManualInputOption adjustOption = new ReqPositionManualInputOption();
        ReqTradeAccountPositionTradeDetailOption assetOption = new ReqTradeAccountPositionTradeDetailOption();
        if (option.isSetTradeAccountId()) {
            adjustOption.setTradeAccountId(option.getTradeAccountId());
            assetOption.setTradeAccountId(option.getTradeAccountId());
        }
        if (option.isSetSledContractId()) {
            adjustOption.setSledContractId(option.getSledContractId());
            assetOption.setSledContractId(option.getSledContractId());
        }
        if (option.isSetStartTradeTimestampMs()) {
            adjustOption.setStartTradeTimestampMs(option.getStartTradeTimestampMs());
            assetOption.setStartTradeTimestampMs(option.getStartTradeTimestampMs());
        }
        if (option.isSetEndTradeTimestampMs()) {
            adjustOption.setEndTradeTimestampMs(option.getEndTradeTimestampMs());
            assetOption.setEndTradeTimestampMs(option.getEndTradeTimestampMs());
        }

        TradeHostingAssetStub assetStub = new HostingAssetHandler(getServiceCntl(), getLandingInfo()).getAssetStub();
        AssetTradeDetailPage assetPage = assetStub.getTradeAccountPositionTradeDetail(assetOption, pageOption);
        PositionManualInputPage inputPage = getStub().reqManualInputPosition(adjustOption, pageOption);
        assetPage.setTotal(assetPage.getTotal() + inputPage.getTotal());
        for (PositionManualInput positionManualInput : inputPage.getPage()) {
            AssetTradeDetail tradeDetail = new AssetTradeDetail();
            tradeDetail.setTradeAccountId(positionManualInput.getTradeAccountId());
            tradeDetail.setSledContractId(positionManualInput.getSledContractId());
            tradeDetail.setSledCommodityId(positionManualInput.getSledCommodityId());
            tradeDetail.setTradePrice(positionManualInput.getPrice());
            tradeDetail.setTradeVolume(positionManualInput.getVolume());
            if (PositionDirection.POSITION_BUY.equals(positionManualInput.getPositionDirection())) {
                tradeDetail.setExecTradeDirection(HostingExecTradeDirection.TRADE_BUY);
            } else if (PositionDirection.POSITION_SELL.equals(positionManualInput.getPositionDirection())) {
                tradeDetail.setExecTradeDirection(HostingExecTradeDirection.TRADE_SELL);
            }
            tradeDetail.setTradeTimestampMs(positionManualInput.getPositionTimestampMs());
            tradeDetail.setCreateTimestampMs(positionManualInput.getCreateTimestampMs());
            tradeDetail.setLastmodifyTimestampMs(positionManualInput.getLastmodifyTimestampMs());
            assetPage.getPage().add(tradeDetail);
        }
        return assetPage;
    }


    public PositionVerifyPage reqPositionVerify(ReqPositionVerifyOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        ParameterChecker.check(option.getTradeAccountId() > 0, "TradeAccountId must set");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<PositionVerifyPage>() {
            @Override
            public PositionVerifyPage call() throws Exception {
                return getStub().reqPositionVerify(option, pageOption);
            }
        });
    }


    public PositionDifferencePage reqPositionDifference(ReqPositionDifferenceOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<PositionDifferencePage>() {
            @Override
            public PositionDifferencePage call() throws Exception {
                return getStub().reqPositionDifference(option, pageOption);
            }
        });
    }


    public ManualInputPositionResp manualInputPosition(List<PositionManualInput> positionManualInputs) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(positionManualInputs, "positionManualInputs");
        for (PositionManualInput positionManualInput : positionManualInputs) {
            ParameterChecker.check(positionManualInput.getVolume() > 0, "Volume must >0");
            ParameterChecker.check(positionManualInput.getSledContractId() > 0, "SledContractId must >0");
            ParameterChecker.check(positionManualInput.getTradeAccountId() > 0, "TradeAccountId must >0");
            positionManualInput.setSubUserId(getLandingInfo().getSubUserId());
        }
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<ManualInputPositionResp>() {
            @Override
            public ManualInputPositionResp call() throws Exception {
                return getStub().manualInputPosition(positionManualInputs);
            }
        });
    }


    public PositionUnassignedPage reqPositionUnassigned(ReqPositionUnassignedOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(option, "option");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<PositionUnassignedPage>() {
            @Override
            public PositionUnassignedPage call() throws Exception {
                return getStub().reqPositionUnassigned(option, pageOption);
                
            }
        });
    }


    public AssignPositionResp assignPosition(List<PositionAssignOption> assignOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(assignOption, "assignOption");
        for (PositionAssignOption positionAssignOption : assignOption) {
            ParameterChecker.checkNotNull(positionAssignOption, "positionAssignOption");
            ParameterChecker.check(positionAssignOption.getInputId() > 0, "InputId must >0");
            ParameterChecker.check(positionAssignOption.getSubAccountId() > 0, "SubAccountId must >0");
            ParameterChecker.check(positionAssignOption.getVolume() > 0, "Volume must >");
            positionAssignOption.setSubUserId(getLandingInfo().getSubUserId());
        }
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<AssignPositionResp>() {
            @Override
            public AssignPositionResp call() throws Exception {
                return getStub().assignPosition(assignOption);
            }
        });
    }


    public PositionEditLock reqPositionEditLock(String lockKey) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(lockKey, "lockKey must set");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<PositionEditLock>() {
            @Override
            public PositionEditLock call() throws Exception {
                return getStub().reqPositionEditLock(lockKey);
            }
        });
    }


    public void addPositionEditLock(PositionEditLock editLock) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(editLock, "PositionEditLock must set");
        PositionEditLock positionEditLock = getPositionEditLock(editLock.getLockArea());
        permit(EHostingUserRole.AdminGroup);
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().addPositionEditLock(positionEditLock);
                return null;
            }
        });
    }

    private PositionEditLock getPositionEditLock(String lockKey) throws ErrorInfo {
        ParameterChecker.checkNotNull(lockKey, "lockKey must set");
        PositionEditLock positionEditLock = new PositionEditLock();
        positionEditLock.setLockArea(lockKey);
        positionEditLock.setSubUserId(getLandingInfo().getSubUserId());
        return positionEditLock;
    }


    public void removePositionEditLock(PositionEditLock editLock) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(editLock, "PositionEditLock must set");
        PositionEditLock positionEditLock = getPositionEditLock(editLock.getLockArea());
        permit(EHostingUserRole.AdminGroup);
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().removePositionEditLock(positionEditLock);
                return null;
            }
        });
    }

    public SettlementTimeRelateSledReqTime reqSettlementTimeRelateSledReqTime(String dateString, long tradeAccountId) throws ErrorInfo {
        ParameterChecker.checkNotNull(dateString, "dateString must set");
        ParameterChecker.check(tradeAccountId > 0, " trade account id must >0");
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<SettlementTimeRelateSledReqTime>() {
            @Override
            public SettlementTimeRelateSledReqTime call() throws Exception {
                return getStub().reqSettlementTimeRelateSledReqTime(tradeAccountId, dateString);
            }
        });
    }

    public DailyPositionDifferencePage reqDailyPositionDifference(ReqDailyPositionDifferenceOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkInnerNotNull(option);
        ParameterChecker.checkInnerNotNull(pageOption);
        permit(EHostingUserRole.AdminGroup);
        return ErrorInfoCallHelper.call(new Callable<DailyPositionDifferencePage>() {
            @Override
            public DailyPositionDifferencePage call() throws Exception {
                return getStub().reqDailyPositionDifference(option, pageOption);
            }
        });
    }

    public void updateDailyPositionDifferenceNote(DailyPositionDifference difference) throws ErrorInfo, TException {
        ParameterChecker.checkInnerNotNull(difference);
        permit(EHostingUserRole.AdminGroup);
        ParameterChecker.check(difference.getDateSec() > 0, "date not set.");
        ParameterChecker.check(difference.getSledContractId() > 0, "contract id not set.");
        ParameterChecker.check(difference.getTradeAccountId() > 0, "trade account id not set.");

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().updateDailyPositionDifferenceNote(difference);
                return null;
            }
        });
    }

    public PositionAssignedPage reqPositionAssigned(ReqPositionAssignedOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkInnerNotNull(option);
        ParameterChecker.checkInnerNotNull(pageOption);
        if (!hasPermission(EHostingUserRole.AdminGroup)) {
            ParameterChecker.check(option.getSubAccountId() > 0, " sub account id must set.");
            checkHasRelatedAccount(option.getSubAccountId());
        }
        return ErrorInfoCallHelper.call(new Callable<PositionAssignedPage>() {
            @Override
            public PositionAssignedPage call() throws Exception {
                return getStub().reqPositionAssigned(option, pageOption);
            }
        });
    }
}
