package xueqiao.trade.hosting.position.statis.service.controller;

import com.antiy.error_code.ErrorCodeInner;
import com.longsheng.xueqiao.contract.standard.thriftapi.ContractStatus;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCacheManager;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;
import xueqiao.trade.hosting.position.statis.service.thread.StatSingleWorkerThread;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;

public class DeleteExpiredContractPositionController {

    private long subAccountId;
    private long sledContractId;

    public DeleteExpiredContractPositionController(long subAccountId, long sledContractId) {
        this.subAccountId = subAccountId;
        this.sledContractId = sledContractId;
    }

    public void validateParams() throws ErrorInfo {
        ParameterChecker.check(subAccountId > 0, "invalid subAccountId");
        ParameterChecker.check(sledContractId > 0, "invalid sledContractId");
    }

    public void doDelete() throws ErrorInfo {
        checkContract();
        deleteInSingleWorkerThread();
        //退订行情
        StatQuotationHelper.unRegister(subAccountId, String.valueOf(sledContractId), HostingXQTargetType.CONTRACT_TARGET);
    }

    private void checkContract() throws ErrorInfo {
        checkContractExpired();
        checkContractPositionVolume();
    }

    private void checkContractExpired() throws ErrorInfo {
        SledContractDetails contractDetails = StatContractCacheManager.getSledContractDetails(sledContractId);
        if (contractDetails == null || contractDetails.getSledContract() == null) {
            AppLog.e("checkContract ---- fail to get sledContractDetails, sledContractId : " + sledContractId);
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "fail to get sledContractDetails");
        }
        if (contractDetails.getSledContract().getContractStatus() != ContractStatus.EXPIRED) {
            AppLog.e("checkContract ---- contract not expired, delete fail, subAccountId : " + subAccountId + ", sledContractId : " + sledContractId);
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_CONTRACT_NOT_EXPIRED.getValue(), "contract not expired, delete fail");
        }
    }

    private void checkContractPositionVolume() throws ErrorInfo {
        StatPositionSummary positionSummary = null;
        try {
            positionSummary = StatPositionHandler.queryStatPositionSummary(subAccountId, String.valueOf(sledContractId), HostingXQTargetType.CONTRACT_TARGET);
        } catch (ErrorInfo errorInfo) {
            AppLog.e("checkContractPositionVolume ---- queryStatPositionSummary fail, subAccountId : " + subAccountId + ", sledContractId : " + sledContractId, errorInfo);
        }
        if (positionSummary == null) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "fail to get positionSummary");
        }
        if (positionSummary.getShortPosition() != 0 && positionSummary.getLongPosition() != 0) {
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_POSITION_NOT_CLOSED.getValue(), "expired contract position should be closed before deleted");
        }
    }

    /*
     * 在单工作线程中删除
     * 所有的非查询操作，都在该工作线程中进行，以避免并发导致数据出错
     * */
    private void deleteInSingleWorkerThread() throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {
            @Override
            protected Void onCall() throws Exception {
                delete();
                return null;
            }
        }.get();
    }

    /*
     * 删除持仓汇总
     * 删除持仓项
     * 删除持仓小单元
     * */
    private void delete() throws ErrorInfo {
        new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                StatPositionHandler.removePosition(getConnection(), subAccountId, String.valueOf(sledContractId), HostingXQTargetType.CONTRACT_TARGET);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
