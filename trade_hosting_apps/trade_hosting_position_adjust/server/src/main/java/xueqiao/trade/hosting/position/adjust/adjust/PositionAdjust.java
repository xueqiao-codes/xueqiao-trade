package xueqiao.trade.hosting.position.adjust.adjust;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.job.AssignPositionJobScheduler;
import xueqiao.trade.hosting.position.adjust.storage.*;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.adjust.utils.DateTimeUtils;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.util.List;

public class PositionAdjust {

    public ManualInputPositionResp manualInputPosition(List<PositionManualInput> inputs) throws ErrorInfo {
        checkPositionInput(inputs);

        ManualInputPositionResp resp = new DBTransactionHelper<ManualInputPositionResp>(PositionAdjustDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                PositionManualInputTable manualInputTable = new PositionManualInputTable(getConnection());
                PositionUnassignedTable unssignedTable = new PositionUnassignedTable(getConnection());
                NetPositionManualInputTable netPositionManualInputTable = new NetPositionManualInputTable(getConnection());
                PositionDailyDifferenceTable positionDailyDifferenceTable = new PositionDailyDifferenceTable(getConnection());
                for (PositionManualInput input : inputs) {
                    long inputId = manualInputTable.add(input);
                    PositionUnassigned unassigned = mapInput2Unassigned(input, inputId);
                    unssignedTable.add(unassigned);

                    TradeAccountPositionTable netPosition = netPositionManualInputTable.queryForUpdate(input.getTradeAccountId(), input.getSledContractId());
                    boolean isNew = false;
                    if (netPosition == null) {
                        netPosition = new TradeAccountPositionTable();
                        netPosition.setSledContractId(input.getSledContractId());
                        netPosition.setTradeAccount(input.getTradeAccountId());
                        isNew = true;
                    }

                    int newPosition;
                    if (PositionDirection.POSITION_SELL.equals(input.getPositionDirection())) {
                        newPosition = 0 - Math.abs(input.getVolume());
                    } else {
                        newPosition = Math.abs(input.getVolume());
                    }
                    netPosition.setNetPosition(netPosition.getNetPosition() + newPosition);
                    if (isNew) {
                        netPositionManualInputTable.add(netPosition);
                    } else {
                        netPositionManualInputTable.update(netPosition);
                    }
                    long dateSec = DateTimeUtils.getDateTimeSecDatePart(DateTimeUtils.getDateStringDatePart(input.getPositionTimestampMs()));
                    DailyPositionDifference oldDifference = positionDailyDifferenceTable.queryForUpdate(dateSec, input.getTradeAccountId(), input.getSledContractId());
                    DailyPositionDifference difference = new DailyPositionDifference();
                    difference.setTradeAccountId(input.getTradeAccountId());
                    difference.setSledContractId(input.getSledContractId());
                    difference.setDateSec(dateSec);
                    if (oldDifference == null) {
                        difference.setInputNetPosition(newPosition);
                        positionDailyDifferenceTable.add(difference);
                    } else {
                        difference.setInputNetPosition(newPosition + oldDifference.getInputNetPosition());
                        positionDailyDifferenceTable.update(difference);
                    }
                }
            }

            @Override
            public ManualInputPositionResp getResult() {
                return new ManualInputPositionResp().setSuccess(true);
            }
        }.execute().getResult();
        AssignPositionJobScheduler.Global().addAssignAoJobs();
        return resp;
    }

    public AssignPositionResp assignPosition(List<PositionAssignOption> details) throws ErrorInfo {

        AssignPositionResp resp = new DBTransactionHelper<AssignPositionResp>(PositionAdjustDB.Global()) {
            PositionUnassignedTable unassignedTable;
            PositionAssignedTable assignedTable;
            PositionAssignTaskTable taskTable;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                unassignedTable = new PositionUnassignedTable(getConnection());
                assignedTable = new PositionAssignedTable(getConnection());
                taskTable = new PositionAssignTaskTable(getConnection());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {

                for (PositionAssignOption assignOption : details) {
                    PositionUnassigned unassigned = unassignedTable.queryForUpdate(assignOption.getInputId(), true);
                    if (unassigned == null) {
                        throw new ErrorInfo(TradeHostingPositionAdjustErrorCode.ERROR_POSITION_NOT_FOUND.getValue(), "position adjust not found.");
                    }

                    if (unassigned.getVolume() < assignOption.getVolume()) {
                        throw new ErrorInfo(TradeHostingPositionAdjustErrorCode.ERROR_POSITION_VOLUME_ERROR.getValue(), "position volume error.");
                    }

                    PositionAssigned newAssign = new PositionAssigned();
                    newAssign.setInputId(unassigned.getInputId());
                    newAssign.setVolume(assignOption.getVolume());
                    newAssign.setSubAccountId(assignOption.getSubAccountId());
                    newAssign.setTradeAccountId(unassigned.getTradeAccountId());
                    newAssign.setSledCommodityId(unassigned.getSledCommodityId());
                    newAssign.setSledContractId(unassigned.getSledContractId());
                    newAssign.setPrice(unassigned.getPrice());
                    newAssign.setVolume(assignOption.getVolume());
                    newAssign.setPositionDirection(unassigned.getPositionDirection());
                    newAssign.setPositionTimestampMs(unassigned.getPositionTimestampMs());
                    newAssign.setAssignSubUserId(assignOption.getSubUserId());
                    newAssign.setInputSubUserId(unassigned.getInputSubUserId());

                    long assignId = assignedTable.add(newAssign);
                    newAssign.setAssignId(assignId);
                    int volumeLeft = unassigned.getVolume() - assignOption.getVolume();
                    if (volumeLeft > 0) {
                        PositionUnassigned newUnassigned = new PositionUnassigned();
                        newUnassigned.setInputId(unassigned.getInputId());
                        newUnassigned.setVolume(volumeLeft);
                        unassignedTable.update(newUnassigned);
                    } else {
                        unassignedTable.delete(unassigned.getInputId());
                    }

                    for (AoType aoType : AssignPositionJobScheduler.AO_TYPES) {
                        PositionAssignTask task = new PositionAssignTask();
                        task.setAoType(aoType);
                        task.setPositionAssigned(newAssign);
                        taskTable.add(task);
                    }
                }
            }

            @Override
            public AssignPositionResp getResult() {
                return new AssignPositionResp().setSuccess(true);
            }
        }.execute().getResult();

        AssignPositionJobScheduler.Global().addAssignAoJobs();
        return resp;
    }

    private void checkPositionInput(List<PositionManualInput> inputs) throws ErrorInfo {
        Preconditions.checkNotNull(inputs);
        Preconditions.checkArgument(inputs.size() > 0);

        for (PositionManualInput input : inputs) {
            Preconditions.checkNotNull(input);
            Preconditions.checkArgument(input.getSubUserId() > 0);
            Preconditions.checkArgument(input.getTradeAccountId() > 0);
            Preconditions.checkArgument(input.getSledContractId() > 0);

            IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            SledContractDetails contractDetail = contractApi.getContractDetailForSure(input.getSledContractId());
            input.setSledCommodityId(contractDetail.getSledCommodity().getSledCommodityId());
        }
    }

    private PositionUnassigned mapInput2Unassigned(PositionManualInput input, long inputId) {
        PositionUnassigned unassigned = new PositionUnassigned();
        unassigned.setInputId(inputId);
        unassigned.setInputSubUserId(input.getSubUserId());
        unassigned.setTradeAccountId(input.getTradeAccountId());
        unassigned.setSledContractId(input.getSledContractId());
        unassigned.setSledCommodityId(input.getSledCommodityId());
        unassigned.setPrice(input.getPrice());
        unassigned.setVolume(input.getVolume());
        unassigned.setPositionDirection(input.getPositionDirection());
        unassigned.setPositionTimestampMs(input.getPositionTimestampMs());
        return unassigned;
    }

    public void updateDailyPositionDifferenceNote(DailyPositionDifference difference) throws ErrorInfo {
        Preconditions.checkNotNull(difference);
        Preconditions.checkArgument(difference.getSledContractId() > 0);
        Preconditions.checkArgument(difference.getTradeAccountId() > 0);
        Preconditions.checkArgument(difference.getDateSec() > 0);

        DailyPositionDifference updateDiff = new DailyPositionDifference();
        updateDiff.setDateSec(difference.getDateSec());
        updateDiff.setTradeAccountId(difference.getTradeAccountId());
        updateDiff.setSledContractId(difference.getSledContractId());
        if (difference.isSetNote()) {
            updateDiff.setNote(difference.getNote());
        }
        if (difference.isSetVerifyStatus()) {
            updateDiff.setVerifyStatus(difference.getVerifyStatus());
        }

        new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                PositionDailyDifferenceTable table = new PositionDailyDifferenceTable(getConnection());
                table.update(difference);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();


    }
}
