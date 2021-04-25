package xueqiao.trade.hosting.position.adjust.thriftapi.server.impl;


import java.util.List;
import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.position.adjust.adjust.EditLock;
import xueqiao.trade.hosting.position.adjust.adjust.PositionAdjust;
import xueqiao.trade.hosting.position.adjust.adjust.PositionQuery;
import xueqiao.trade.hosting.position.adjust.adjust.PositionVerifyQuery;
import xueqiao.trade.hosting.position.adjust.api.DataRemove;
import xueqiao.trade.hosting.position.adjust.api.ReqSettlementTimeRelateSledReqTime;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.adjust.thriftapi.server.TradeHostingPositionAdjustAdaptor;

public class TradeHostingPositionAdjustHandler extends TradeHostingPositionAdjustAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected ManualInputPositionResp manualInputPosition(TServiceCntl oCntl, List<PositionManualInput> positionManualInputs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        return new PositionAdjust().manualInputPosition(positionManualInputs);
    }

    @Override
    protected PositionManualInputPage reqManualInputPosition(TServiceCntl oCntl, ReqPositionManualInputOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        return new PositionQuery().queryPositionManualInput(option, pageOption);
    }

    @Override
    protected PositionUnassignedPage reqPositionUnassigned(TServiceCntl oCntl, ReqPositionUnassignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        return new PositionQuery().queryPositionUnassigned(option, pageOption);
    }

    @Override
    protected PositionAssignedPage reqPositionAssigned(TServiceCntl oCntl, ReqPositionAssignedOption option, org.soldier.platform.page.IndexedPageOption pageOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        return new PositionQuery().queryPositionAssigned(option, pageOption);
    }

    @Override
    protected AssignPositionResp assignPosition(TServiceCntl oCntl, List<PositionAssignOption> assignOption) throws ErrorInfo, TException {
        return new PositionAdjust().assignPosition(assignOption);
    }

    @Override
    protected PositionEditLock reqPositionEditLock(TServiceCntl oCntl, String lockKey) throws ErrorInfo, TException {
        return new EditLock().queryLock(lockKey);
    }

    @Override
    protected void addPositionEditLock(TServiceCntl oCntl, PositionEditLock positionEditLock) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        new EditLock().addLock(positionEditLock);
    }

    @Override
    protected void removePositionEditLock(TServiceCntl oCntl, PositionEditLock positionEditLock) throws ErrorInfo, TException {
        new EditLock().removeLock(positionEditLock);
    }

    @Override
    protected PositionVerifyPage reqPositionVerify(TServiceCntl oCntl, ReqPositionVerifyOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new PositionVerifyQuery().queryPositionVerify(option, pageOption);
    }

    @Override
    protected PositionDifferencePage reqPositionDifference(TServiceCntl oCntl, ReqPositionDifferenceOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new PositionVerifyQuery().queryPositionDifference(option, pageOption);
    }

    @Override
    protected SettlementTimeRelateSledReqTime reqSettlementTimeRelateSledReqTime(TServiceCntl oCntl, long tradeAccountId, String settlementDate) throws ErrorInfo, TException {
        return new ReqSettlementTimeRelateSledReqTime().req(tradeAccountId, settlementDate);
    }

    @Override
    protected DailyPositionDifferencePage reqDailyPositionDifference(TServiceCntl oCntl, ReqDailyPositionDifferenceOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new PositionVerifyQuery().queryDailyPositionDifference(option, pageOption);
    }

    @Override
    protected void updateDailyPositionDifferenceNote(TServiceCntl oCntl, DailyPositionDifference difference) throws ErrorInfo, TException {
        new PositionAdjust().updateDailyPositionDifferenceNote(difference);
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        new DataRemove().remove();
    }

    @Override
    public void destroy() {
    }
}
