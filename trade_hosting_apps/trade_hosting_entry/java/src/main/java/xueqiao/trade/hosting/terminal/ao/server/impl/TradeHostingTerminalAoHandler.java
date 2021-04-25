package xueqiao.trade.hosting.terminal.ao.server.impl;

import java.util.*;

import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

import xueqiao.mailbox.user.message.thriftapi.UserMessagePage;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.QueryHostingUserOption;
import xueqiao.trade.hosting.QueryHostingUserPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.*;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.fee.thriftapi.*;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskFrameDataInfo;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.terminal.ao.*;
import xueqiao.trade.hosting.terminal.ao.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.terminal.ao.server.TradeHostingTerminalAoAdaptor;
import xueqiao.trade.hosting.terminal.ao.server.impl.handler.*;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.working.order.thriftapi.AssetAccount;

public class TradeHostingTerminalAoHandler extends TradeHostingTerminalAoAdaptor {

    private IHostingSessionApi mSessionApi;

    @Override
    public int InitApp(Properties props) {
        mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
        return 0;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected QueryHostingUserPage getHostingUserPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHostingUserOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingUserHandler(oCntl, landingInfo).getHostingUserPage(queryOption, pageOption);
    }

    @Override
    protected void heartBeat(TServiceCntl oCntl, LandingInfo landingInfo) throws ErrorInfo, TException {
        HostingSession session = new HostingSession();
        session.setSubUserId(landingInfo.getSubUserId());
        mSessionApi.updateSession(session);
    }

    @Override
    protected void logout(TServiceCntl oCntl, LandingInfo landingInfo) throws ErrorInfo, TException {
        try {
            mSessionApi.deleteSession(landingInfo.getSubUserId());
        } catch (ErrorInfo err) {
            if (err.getErrorCode() != ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
                throw err;
            }
        }
    }

    @Override
    protected long addTradeAccount(TServiceCntl oCntl
            , LandingInfo landingInfo
            , HostingTradeAccount newAccount)
            throws ErrorInfo, TException {
        return new HostingTradeAccountHandler(oCntl, landingInfo).addTradeAccount(newAccount);
    }

    @Override
    protected void disableTradeAccount(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId)
            throws ErrorInfo, TException {
        new HostingTradeAccountHandler(oCntl, landingInfo).disableTradeAccount(tradeAccountId);
    }

    @Override
    protected QueryHostingTradeAccountPage getTradeAccountPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHostingTradeAccountOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingTradeAccountHandler(oCntl, landingInfo).getTradeAccountPage(queryOption, pageOption);
    }

    @Override
    protected void enableTradeAccount(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId)
            throws ErrorInfo, TException {
        new HostingTradeAccountHandler(oCntl, landingInfo).enableTradeAccount(tradeAccountId);
    }

    @Override
    protected void updateTradeAccountInfo(TServiceCntl oCntl
            , LandingInfo landingInfo
            , HostingTradeAccount updateAccount) throws ErrorInfo, TException {
        new HostingTradeAccountHandler(oCntl, landingInfo).updateTradeAccountInfo(updateAccount);
    }

    @Override
    protected void rmTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long tradeAccountId)
            throws ErrorInfo, TException {
        new HostingTradeAccountHandler(oCntl, landingInfo).rmTradeAccount(tradeAccountId);
    }

    @Override
    protected List<HostingTradeAccount> getPersonalUserTradeAccount(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws ErrorInfo, TException {
        return new PersonalUserHostingHandler(oCntl, landingInfo).getTradeAccount(subAccountId);
    }

    @Override
    protected HostingOrderRouteTree getHostingOrderRouteTree(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId)
            throws ErrorInfo, TException {
        return new HostingOrderRouteTreeHandler(oCntl, landingInfo).getHostingOrderRouteTree(subAccountId);
    }

    @Override
    protected void updateHostingOrderRouteTree(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , HostingOrderRouteTree routeTree) throws ErrorInfo, TException {
        new HostingOrderRouteTreeHandler(oCntl, landingInfo).updateHostingOrderRouteTree(subAccountId, routeTree);
    }

    @Override
    protected int getHostingOrderRouteTreeVersion(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId)
            throws ErrorInfo, TException {
        return new HostingOrderRouteTreeHandler(oCntl, landingInfo).getHostingOrderRouteTreeVersion(subAccountId);
    }

    @Override
    protected HostingOrderRouteTree getPersonalUserHostingOrderRouteTree(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws ErrorInfo, TException {
        return new PersonalUserHostingHandler(oCntl, landingInfo).getHostingOrderRouteTree(subAccountId);
    }

    @Override
    protected Map<Long, HostingComposeViewDetail> getComposeViewDetails(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<Long> composeGraphIds) throws ErrorInfo, TException {
        return new HostingComposeHandler(oCntl, landingInfo).getComposeViewDetails(composeGraphIds);
    }

    @Override
    protected void changeComposeViewPrecisionNumber(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long composeGraphId
            , short precisionNumber) throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).changeComposeViewPrecisionNumber(composeGraphId, precisionNumber);
    }

    @Override
    protected long createComposeGraph(TServiceCntl oCntl
            , LandingInfo landingInfo
            , HostingComposeGraph newGraph
            , String aliasName
            , short precisionNumber) throws ErrorInfo, TException {
        return new HostingComposeHandler(oCntl, landingInfo).createComposeGraph(newGraph, aliasName, precisionNumber);
    }

    @Override
    protected void delComposeView(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId)
            throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).delComposeView(composeGraphId);
    }

    @Override
    protected QueryHostingComposeViewDetailPage getComposeViewDetailPage(
            TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHostingComposeViewDetailOption queryOption
            , IndexedPageOption pageOption)
            throws ErrorInfo, TException {
        return new HostingComposeHandler(oCntl, landingInfo).getComposeViewDetailPage(queryOption, pageOption);
    }

    @Override
    protected void subscribeComposeViewQuotation(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId)
            throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).subscribeComposeViewQuotation(composeGraphId);
    }

    @Override
    protected void unSubscribeComposeViewQuotation(TServiceCntl oCntl, LandingInfo landingInfo, long composeGraphId)
            throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).unSubscribeComposeViewQuotation(composeGraphId);
    }

    @Override
    protected void addComposeViewBySearch(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long composeGraphId
            , String composeGraphKey
            , String aliasName
            , short precisionNumber) throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).addComposeViewBySearch(
                composeGraphId, composeGraphKey, aliasName, precisionNumber);
    }

    @Override
    protected void changeComposeViewAliasName(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long composeGraphId
            , String aliasName) throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).changeComposeViewAliasName(composeGraphId, aliasName);
    }

    @Override
    protected QuerySameComposeGraphsPage getSameComposeGraphsPage(
            TServiceCntl oCntl
            , LandingInfo landingInfo
            , HostingComposeGraph graph
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingComposeHandler(oCntl, landingInfo).getSameComposeGraphsPage(graph, pageOption);
    }

    @Override
    protected Map<Long, HostingComposeGraph> getComposeGraphs(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<Long> composeGraphIds) throws ErrorInfo, TException {
        return new HostingComposeHandler(oCntl, landingInfo).getComposeGraphs(composeGraphIds);
    }

    @Override
    protected void addComposeViewByShare(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long composeGraphId
            , String aliasName
            , short precisionNumber) throws ErrorInfo, TException {
        new HostingComposeHandler(oCntl, landingInfo).addComposeViewByShare(composeGraphId, aliasName, precisionNumber);
    }

    @Override
    protected HostingUserSetting getUserSetting(TServiceCntl oCntl
            , LandingInfo landingInfo
            , String key) throws ErrorInfo, TException {
        return new HostingUserSettingHandler(oCntl, landingInfo).getUserSetting(key);
    }

    @Override
    protected void updateUserSetting(TServiceCntl oCntl
            , LandingInfo landingInfo
            , String key
            , HostingUserSetting setting) throws ErrorInfo, TException {
        new HostingUserSettingHandler(oCntl, landingInfo).updateUserSetting(key, setting);
    }

    @Override
    protected int getUserSettingVersion(TServiceCntl oCntl
            , LandingInfo landingInfo
            , String key) throws ErrorInfo, TException {
        return new HostingUserSettingHandler(oCntl, landingInfo).getUserSettingVersion(key);
    }

    @Override
    protected HostingSAWRUItemListPage getSAWRUTListPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHostingSAWRUItemListOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingSubAccountHandler(oCntl, landingInfo).getSAWRUTListPage(queryOption, pageOption);
    }

    @Override
    protected Map<Long, List<HostingSubAccountRelatedItem>> getSARUTBySubAccountId(
            TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<Long> subAccountIds) throws ErrorInfo, TException {
        return new HostingSubAccountHandler(oCntl, landingInfo).getSARUTBySubAccountId(subAccountIds);
    }

    @Override
    protected Map<Integer, List<HostingSubAccountRelatedItem>> getSARUTBySubUserId(
            TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<Integer> subUserIds) throws ErrorInfo, TException {
        return new HostingSubAccountHandler(oCntl, landingInfo).getSARUTBySubUserId(subUserIds);
    }

    @Override
    protected void assignSubAccountRelatedUsers(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , Set<Integer> relatedSubUserIds
            , Set<Integer> unRelatedSubUserIds) throws ErrorInfo, TException {
        new HostingSubAccountHandler(oCntl, landingInfo).assignSubAccountRelatedUsers(subAccountId
                , relatedSubUserIds, unRelatedSubUserIds);
    }

    @Override
    protected void renameSubAccount(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , String subAccountName) throws ErrorInfo, TException {
        new HostingSubAccountHandler(oCntl, landingInfo).renameSubAccount(subAccountId, subAccountName);
    }

    @Override
    protected long createSubAccount(TServiceCntl oCntl, LandingInfo landingInfo, HostingSubAccount newSubAccount)
            throws ErrorInfo, TException {
        return new HostingSubAccountHandler(oCntl, landingInfo).createSubAccount(newSubAccount);
    }

    @Override
    protected HostingSledContractPositionPage getHostingSledContractPosition(TServiceCntl oCntl, LandingInfo landingInfo, ReqHostingSledContractPositionOption option) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getHostingSledContractPosition(option);
    }

    @Override
    protected HostingFundPage getHostingSubAccountFund(TServiceCntl oCntl, LandingInfo landingInfo, ReqHostingFundOption option) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getHostingSubAccountFund(option);
    }

    @Override
    protected HostingSubAccountFund changeSubAccountFund(TServiceCntl oCntl, LandingInfo landingInfo, FundChange fundChange) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).changeSubAccountFund(fundChange);
    }

    @Override
    protected HostingSubAccountFund setSubAccountCreditAmount(TServiceCntl oCntl, LandingInfo landingInfo, CreditAmountChange amountChange) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).chagneSubAccountCreditAmount(amountChange);
    }

    @Override
    protected AssetTradeDetailPage getAssetPositionTradeDetail(TServiceCntl oCntl, LandingInfo landingInfo, ReqHostingAssetTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getAssetTradeDetail(option, pageOption);
    }

    @Override
    protected xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordPage getHostingSubAccountMoneyRecord(TServiceCntl oCntl, LandingInfo landingInfo, ReqMoneyRecordOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getHostingSubAccountMoneyRecord(option, pageOption);
    }

    @Override
    protected HostingFundPage getSubAccountFundHistory(TServiceCntl oCntl, LandingInfo landingInfo, ReqSubAccountFundHistoryOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getSubAccountFundHistory(option, pageOption);
    }

    @Override
    protected SettlementPositionDetailPage getSubAccountPositionHistory(TServiceCntl oCntl, LandingInfo landingInfo, ReqSettlementPositionDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getSubAccountPositionHistory(option, pageOption);
    }

    @Override
    protected AssetTradeDetailPage getSubAccountPositionHistoryTradeDetail(TServiceCntl oCntl, LandingInfo landingInfo, ReqSettlementPositionTradeDetailOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingAssetHandler(oCntl, landingInfo).getSubAccountPositionHistoryTradeDetail(option, pageOption);
    }

    @Override
    protected void deleteExpiredContractPosition(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long sledContractId) throws ErrorInfo, TException {
        new HostingAssetHandler(oCntl, landingInfo).deleteExpiredContractPosition(subAccountId, sledContractId);
    }


    @Override
    protected void createXQOrder(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , String orderId
            , HostingXQOrderType orderType
            , HostingXQTarget orderTarget
            , HostingXQOrderDetail orderDetail)
            throws ErrorInfo, TException {
        new HostingXQOrderHandler(oCntl, landingInfo).create(subAccountId
                , orderId, orderType, orderTarget, orderDetail);
    }

    @Override
    protected Map<String, ErrorInfo> batchSuspendXQOrders(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<String> orderIds) throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).batchSuspendXQOrders(orderIds);
    }

    @Override
    protected Map<String, ErrorInfo> batchResumeXQOrders(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<String> orderIds
            , Map<String, HostingXQOrderResumeMode> resumeModes) throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).batchResumeXQOrders(orderIds, resumeModes);
    }

    @Override
    protected Map<String, ErrorInfo> batchCancelXQOrders(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<String> orderIds) throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).batchCancelXQOrders(orderIds);
    }

    @Override
    protected HostingXQOrderWithTradeListPage getEffectXQOrderWithTradeListPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryEffectXQOrderIndexOption qryOption
            , IndexedPageOption pageOption)
            throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).getEffectXQOrderWithTradeListPage(qryOption, pageOption);
    }

    @Override
    protected Map<String, HostingXQOrderWithTradeList> getXQOrderWithTradeLists(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<String> orderIds) throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).getXQOrderWithTradeLists(orderIds);
    }

    @Override
    protected HostingXQOrderExecDetail getXQOrderExecDetail(TServiceCntl oCntl
            , LandingInfo landingInfo
            , String orderId) throws ErrorInfo, TException {
        return new HostingXQOrderHandler(oCntl, landingInfo).getXQOrderExecDetail(orderId);
    }

    @Override
    protected HostingXQOrderPage getXQOrderHisPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryXQOrderHisIndexItemOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingHistoryHandler(oCntl, landingInfo).getXQOrderHisPage(qryOption, pageOption);
    }

    @Override
    protected HostingXQTradePage getXQTradeHisPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryXQTradeHisIndexItemOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingHistoryHandler(oCntl, landingInfo).getXQTradeHisPage(qryOption, pageOption);
    }

    @Override
    protected List<HostingTAFundItem> getTradeAccountFundNow(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId) throws ErrorInfo, TException {
        return new HostingTADataHandler(oCntl, landingInfo).getFundNow(tradeAccountId);
    }

    @Override
    protected List<HostingTAFundHisItem> getTradeAccountFundHis(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId
            , String fundDateBegin
            , String fundDateEnd) throws ErrorInfo, TException {
        return new HostingTADataHandler(oCntl, landingInfo).getFundHis(tradeAccountId, fundDateBegin, fundDateEnd);
    }

    @Override
    protected List<TradeAccountSettlementInfo> getTradeAccountSettlementInfos(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId
            , String settlementDateBegin
            , String settlementDateEnd) throws ErrorInfo, TException {
        return new HostingTADataHandler(oCntl, landingInfo).getSettlementInfos(tradeAccountId
                , settlementDateBegin
                , settlementDateEnd);
    }

    @Override
    protected List<TradeAccountSettlementInfoWithRelatedTime> getTradeAccountSettlementInfosWithRelatedTime(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long tradeAccountId
            , String settlementDateBegin
            , String settlementDateEnd) throws ErrorInfo, TException {
        List<TradeAccountSettlementInfo> list = new HostingTADataHandler(oCntl, landingInfo).getSettlementInfos(tradeAccountId, settlementDateBegin, settlementDateEnd);
        List<TradeAccountSettlementInfoWithRelatedTime> infosWithTime = new ArrayList<>();
        for (TradeAccountSettlementInfo info : list) {
            TradeAccountSettlementInfoWithRelatedTime infoWithRelatedTime = new TradeAccountSettlementInfoWithRelatedTime();
            infoWithRelatedTime.setReqTime(new HostingPositionAdjustHandler(oCntl, landingInfo).reqSettlementTimeRelateSledReqTime(info.getSettlementDate(), info.getTradeAccountId()));
            infoWithRelatedTime.setTradeAccountSettlementInfo(info);
            infosWithTime.add(infoWithRelatedTime);
        }
        return infosWithTime;
    }

    @Override
    protected AssetTradeDetailPage getTradeAccountPositionTradeDetail(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqTradeAccountPositionOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).getTradeAccountPositionTradeDetail(option, pageOption);
    }

    @Override
    protected PositionVerifyPage reqPositionVerify(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqPositionVerifyOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqPositionVerify(option, pageOption);
    }

    @Override
    protected PositionDifferencePage reqPositionDifference(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqPositionDifferenceOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqPositionDifference(option, pageOption);
    }

    @Override
    protected ManualInputPositionResp manualInputPosition(TServiceCntl oCntl
            , LandingInfo landingInfo
            , List<PositionManualInput> positionManualInputs) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).manualInputPosition(positionManualInputs);
    }

    @Override
    protected PositionUnassignedPage reqPositionUnassigned(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqPositionUnassignedOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqPositionUnassigned(option, pageOption);
    }

    @Override
    protected AssignPositionResp assignPosition(TServiceCntl oCntl
            , LandingInfo landingInfo
            , List<PositionAssignOption> assignOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).assignPosition(assignOption);
    }

    @Override
    protected PositionEditLock reqPositionEditLock(TServiceCntl oCntl
            , LandingInfo landingInfo
            , String lockKey) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqPositionEditLock(lockKey);
    }

    @Override
    protected void addPositionEditLock(TServiceCntl oCntl
            , LandingInfo landingInfo
            , PositionEditLock positionEditLock) throws ErrorInfo, TException {
        new HostingPositionAdjustHandler(oCntl, landingInfo).addPositionEditLock(positionEditLock);
    }

    @Override
    protected void removePositionEditLock(TServiceCntl oCntl
            , LandingInfo landingInfo
            , PositionEditLock positionEditLock) throws ErrorInfo, TException {
        new HostingPositionAdjustHandler(oCntl, landingInfo).removePositionEditLock(positionEditLock);
    }

    @Override
    protected DailyPositionDifferencePage reqDailyPositionDifference(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqDailyPositionDifferenceOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqDailyPositionDifference(option, pageOption);
    }

    @Override
    protected void updateDailyPositionDifferenceNote(TServiceCntl oCntl
            , LandingInfo landingInfo
            , DailyPositionDifference difference) throws ErrorInfo, TException {
        new HostingPositionAdjustHandler(oCntl, landingInfo).updateDailyPositionDifferenceNote(difference);
    }

    @Override
    protected PositionAssignedPage reqPositionAssigned(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqPositionAssignedOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionAdjustHandler(oCntl, landingInfo).reqPositionAssigned(option, pageOption);
    }

    @Override
    protected void contructCompose(TServiceCntl oCntl
            , LandingInfo landingInfo
            , StatContructComposeReq contructComposeReq) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).contructCompose(contructComposeReq);
    }

    @Override
    protected void disassembleCompose(TServiceCntl oCntl
            , LandingInfo landingInfo
            , DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).disassembleCompose(disassembleComposePositionReq);
    }

    @Override
    protected void batchClosePosition(TServiceCntl oCntl
            , LandingInfo landingInfo
            , BatchClosedPositionReq batchClosedPositionReq) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).batchClosePosition(batchClosedPositionReq);
    }

    @Override
    protected void recoverClosedPosition(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , String targetKey
            , HostingXQTargetType targetType) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).recoverClosedPosition(subAccountId, targetKey, targetType);
    }

    @Override
    protected void mergeToCompose(TServiceCntl oCntl
            , LandingInfo landingInfo
            , StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).mergeToCompose(mergeToComposeReq);
    }

    @Override
    protected void deleteExpiredStatContractPosition(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , long sledContractId) throws ErrorInfo, TException {
        new HostingPositionStatisHandler(oCntl, landingInfo).deleteExpiredStatContractPosition(subAccountId, sledContractId);
    }

    @Override
    protected StatPositionSummaryPage queryStatPositionSummaryPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatPositionSummaryOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryStatPositionSummaryPage(queryOption, pageOption);
    }

    @Override
    protected StatPositionItemPage queryStatPositionItemPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatPositionItemOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryStatPositionItemPage(queryOption, pageOption);
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , String targetKey
            , HostingXQTargetType targetType) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryCurrentDayStatClosedPositionPage(subAccountId, targetKey, targetType);
    }

    @Override
    protected StatClosedPositionDetail queryStatClosedPositionDetail(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatClosedPositionItemOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryStatClosedPositionDetail(queryOption, pageOption);
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatClosedPositionDateSummaryOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryArchivedClosedPositionPage(queryOption, pageOption);
    }

    @Override
    protected StatClosedPositionDetail queryArchivedClosedPositionDetail(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatArchiveItemOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryArchivedClosedPositionDetail(queryOption, pageOption);
    }

    @Override
    protected StatPositionSummaryExPage queryStatPositionSummaryExPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatPositionSummaryOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryStatPositionSummaryExPage(queryOption, pageOption);
    }

    @Override
    protected StatPositionUnitPage queryStatPositionUnitPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryStatPositionUnitOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryStatPositionUnitPage(queryOption, pageOption);
    }

    @Override
    protected StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHistoryClosedPositionOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryHistoryClosedPositionPage(queryOption, pageOption);
    }

    @Override
    protected StatClosedPositionDetail queryHistoryClosedPositionDetail(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryHistoryClosedPositionOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionStatisHandler(oCntl, landingInfo).queryHistoryClosedPositionDetail(queryOption, pageOption);
    }

    @Override
    protected xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage getXQTradeLameTaskNotePage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , QueryXQTradeLameTaskNotePageOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingTaskNoteHandler(oCntl, landingInfo).getXQTradeLameTaskNotePage(qryOption, pageOption);
    }

    @Override
    protected Map<Long, ErrorInfo> batchDeleteXQTradeLameTaskNotes(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , Set<Long> xqTradeIds) throws ErrorInfo, TException {
        return new HostingTaskNoteHandler(oCntl, landingInfo)
                .batchDeleteXQTradeLameTaskNotes(subAccountId, xqTradeIds);
    }

    @Override
    protected UserMessagePage queryMailBoxMessage(TServiceCntl oCntl
            , LandingInfo landingInfo
            , ReqMailBoxMessageOption option
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new MailBoxHandler(oCntl, landingInfo).queryMailBoxMessage(option, pageOption);
    }

    @Override
    protected boolean markMessageAsRead(TServiceCntl oCntl
            , LandingInfo landingInfo
            , Set<Long> hostingMessageIds) throws ErrorInfo, TException {
        return new MailBoxHandler(oCntl, landingInfo).markMessageAsRead(hostingMessageIds);
    }

    @Override
    protected List<HostingRiskSupportedItem> getAllSupportedItems(TServiceCntl oCntl
            , LandingInfo landingInfo) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).getAllSupportedItems();
    }

    @Override
    protected int getRiskRuleJointVersion(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).getRiskRuleJointVersion(subAccountId);
    }

    @Override
    protected HostingRiskRuleJoint getRiskRuleJoint(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).getRiskRuleJoint(subAccountId);
    }

    @Override
    protected HostingRiskRuleJoint batchSetSupportedItems(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , int version
            , Set<String> openedItemIds
            , Set<String> closedItemIds) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).batchSetSupportedItems(subAccountId
                , version, openedItemIds, closedItemIds);
    }

    @Override
    protected HostingRiskRuleJoint batchSetTradedCommodityItems(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , int version
            , Set<Long> enabledCommodityIds
            , Set<Long> disabledCommodityIds) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).batchSetTradedCommodityItems(subAccountId
                , version, enabledCommodityIds, disabledCommodityIds);
    }

    @Override
    protected HostingRiskRuleJoint batchSetGlobalRules(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , int version
            , Map<String, HostingRiskRuleItem> ruleItems) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).batchSetGlobalRules(subAccountId, version, ruleItems);
    }

    @Override
    protected HostingRiskRuleJoint batchSetCommodityRules(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , int version
            , Map<Long, Map<String, HostingRiskRuleItem>> rules) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).batchSetCommodityRules(subAccountId, version, rules);
    }

    @Override
    protected HostingRiskRuleJoint setRiskEnabled(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId
            , int version
            , boolean riskEnabled) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).setRiskEnabled(subAccountId, version, riskEnabled);
    }

    @Override
    protected HostingRiskFrameDataInfo getRiskFrameDataInfo(TServiceCntl oCntl
            , LandingInfo landingInfo
            , long subAccountId) throws ErrorInfo, TException {
        return new HostingRiskHandler(oCntl, landingInfo).getRiskFrameDataInfo(subAccountId);
    }

    @Override
    protected void setGeneralMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQGeneralMarginSettings marginSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).setGeneralMarginSetting(marginSettings);
    }

    @Override
    protected void setGeneralCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQGeneralCommissionSettings commissionSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).setGeneralCommissionSetting(commissionSettings);
    }

    @Override
    protected void addSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).addSpecMarginSetting(marginSettings);
    }

    @Override
    protected void addSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).addSpecCommissionSetting(commissionSettings);
    }

    @Override
    protected void updateSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).updateSpecMarginSetting(marginSettings);
    }

    @Override
    protected void updateSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).updateSpecCommissionSetting(commissionSettings);
    }

    @Override
    protected void deleteSpecMarginSetting(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long commodityId) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).deleteSpecMarginSetting(subAccountId, commodityId);
    }

    @Override
    protected void deleteSpecCommissionSetting(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId, long commodityId) throws ErrorInfo, TException {
        new HostingPositionFeeHandler(oCntl, landingInfo).deleteSpecCommissionSetting(subAccountId, commodityId);
    }

    @Override
    protected XQGeneralMarginSettings queryXQGeneralMarginSettings(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQGeneralMarginSettings(subAccountId);
    }

    @Override
    protected XQGeneralCommissionSettings queryXQGeneralCommissionSettings(TServiceCntl oCntl, LandingInfo landingInfo, long subAccountId) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQGeneralCommissionSettings(subAccountId);
    }

    @Override
    protected XQSpecMarginSettingPage queryXQSpecMarginSettingPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQSpecMarginSettingPage(queryOptions, pageOption);
    }

    @Override
    protected XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQSpecCommissionSettingPage(queryOptions, pageOption);
    }

    @Override
    protected UpsideContractMarginPage queryUpsideContractMarginPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryUpsideContractMarginPage(queryOptions, pageOption);
    }

    @Override
    protected UpsideContractCommissionPage queryUpsideContractCommissionPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryUpsideContractCommissionPage(queryOptions, pageOption);
    }

    @Override
    protected XQContractMarginPage queryXQContractMarginPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQContractMarginPage(queryOptions, pageOption);
    }

    @Override
    protected XQContractCommissionPage queryXQContractCommissionPage(TServiceCntl oCntl, LandingInfo landingInfo, QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingPositionFeeHandler(oCntl, landingInfo).queryXQContractCommissionPage(queryOptions, pageOption);
    }

    @Override
    protected long addAssetAccountWorkingOrder(TServiceCntl oCntl, LandingInfo landingInfo, AssetAccount assetAccount) throws ErrorInfo, TException {
        return new WorkingOrderHandler(oCntl, landingInfo).addAssetAccountWorkingOrder(assetAccount);
    }

}
