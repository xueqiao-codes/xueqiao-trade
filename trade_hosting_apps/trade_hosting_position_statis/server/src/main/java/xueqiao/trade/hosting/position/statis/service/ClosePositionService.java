package xueqiao.trade.hosting.position.statis.service;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.bean.*;
import xueqiao.trade.hosting.position.statis.service.handler.RecoverClosedPositionHandler;
import xueqiao.trade.hosting.position.statis.service.handler.StatClosedPositionHandler;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.thread.StatSingleWorkerThread;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClosePositionService {

    /**
     * 查询当天平仓天汇总
     * 由当天平仓记录计算出来
     */
    public StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPosition(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        ParameterChecker.check(subAccountId >= 0, "subAccountId should be larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(targetKey), "targetKey should not be blank");

        StatClosedPositionDateSummaryPage statClosedPositionDateSummaryPage = new StatClosedPositionDateSummaryPage();

        StatClosedPositionDateSummary statClosedPositionDateSummary = new StatClosedPositionHandler().getDateSummary(subAccountId, targetKey, targetType);
        if (statClosedPositionDateSummary == null) {
            statClosedPositionDateSummaryPage.setTotalNum(0);
            statClosedPositionDateSummaryPage.setStatClosedPositionDateSummaryList(new ArrayList<>());
            return statClosedPositionDateSummaryPage;
        }
        List<StatClosedPositionDateSummary> statClosedPositionDateSummaryList = new ArrayList<>();
        statClosedPositionDateSummaryList.add(statClosedPositionDateSummary);
        statClosedPositionDateSummaryPage.setTotalNum(1);
        statClosedPositionDateSummaryPage.setStatClosedPositionDateSummaryList(statClosedPositionDateSummaryList);
        return statClosedPositionDateSummaryPage;
    }

    /**
     * 平仓
     * 平仓时不处理持仓小单元，因为平仓记录可能会恢复
     * 到归档时，再更新持仓小单元
     */
    public void batchClosePosition(BatchClosedPositionReq batchClosedPositionReq) throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {

            @Override
            protected Void onCall() throws Exception {
                _batchClosePosition(batchClosedPositionReq);
                return null;
            }
        }.get();
    }

    private void _batchClosePosition(BatchClosedPositionReq batchClosedPositionReq) throws ErrorInfo {

        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), "batchClosePosition -- batchClosedPositionReq : " + batchClosedPositionReq.toString());
        }

        // 参数校验
        ParameterChecker.checkNotNull(batchClosedPositionReq, "batchClosedPositionReq");
        ParameterChecker.check(batchClosedPositionReq.getSubAccountId() > 0, "subAccountId should larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(batchClosedPositionReq.getTargetKey()), "targetKey should larger than zero");
        ParameterChecker.check(batchClosedPositionReq.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(batchClosedPositionReq.getClosedPositionDetailItemsSize() > 0, "closedPositionDetailItems should larger than zero");
        for (ClosedPositionDetailItem item : batchClosedPositionReq.getClosedPositionDetailItems()) {
            ParameterChecker.check(item.getPositionItemId() > 0, "positionItemId should larger than zero");
            ParameterChecker.check(item.getClosedVolume() > 0, "closedVolume should larger than zero");
        }

        StatPositionHandler statPositionHandler = new StatPositionHandler();
        // 查回相关的持仓明细数据
        List<StatPositionItem> statPositionItemList = statPositionHandler.queryStatPositionItemList(batchClosedPositionReq.getSubAccountId(), batchClosedPositionReq.getTargetKey(), batchClosedPositionReq.getTargetType());
        if (statPositionItemList == null || statPositionItemList.size() < 1) {
            AppReport.reportErr(new StringBuilder("ClosePositionService ## ").append(" batchClosePosition ---- ").append("position items empty(subaccountId:").append(batchClosedPositionReq.getSubAccountId()).append(", targetKey:").append(batchClosedPositionReq.getTargetKey()).append(")").toString());
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_ITEM_NOT_EXIST.getValue(), "position items empty");
        }

        StatClosedPositionHandler statClosedPositionHandler = new StatClosedPositionHandler();
        ClosedData closedData = statClosedPositionHandler.getClosedData(batchClosedPositionReq, statPositionItemList);

        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓   netPositionMap1 begin
         * *****************************************************************
         * */
        CheckNetPositionReqData checkNetPositionReqData = new CheckNetPositionReqData(batchClosedPositionReq.getSubAccountId());
        checkNetPositionReqData.addToPositionTargetData(batchClosedPositionReq.getTargetKey(), batchClosedPositionReq.getTargetType());
        Map<Long, Long> netPositionMap1 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓    netPositionMap1 end
         * *****************************************************************
         * */

        /* 在事务中写入平仓数据 */
//		statClosedPositionHandler.batchClosePosition(statPositionItemList, statClosedPositionItemList, statClosedPositionSummary);
        statClosedPositionHandler.batchClosePosition(closedData);

        // 更新持仓汇总
        new StatPositionHandler().calculateAndUpdateStatPositionSummary(batchClosedPositionReq.getSubAccountId(), batchClosedPositionReq.getTargetKey(), batchClosedPositionReq.getTargetType());

        /*
         * *****************************************************************
         *  核对数据：操作后记录净仓   netPositionMap2
         * *****************************************************************
         * */
        Map<Long, Long> netPositionMap2 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        // 核对净仓
        statPositionHandler.compareNetPositionForContract("batchClosePosition", netPositionMap1, netPositionMap2);
        /*
         * *****************************************************************
         *  核对数据 结束
         * *****************************************************************
         * */
    }


    /**
     * 平仓恢复
     */
    public void recoverPosition(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {

            @Override
            protected Void onCall() throws Exception {
                _recoverPosition(subAccountId, targetKey, targetType);
                return null;
            }
        }.get();
    }

    private void _recoverPosition(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {

        ParameterChecker.check(subAccountId > 0, "subAccountId should larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(targetKey), "targetKey should not be blank");

        List<StatClosedPositionSummary> statClosedPositionSummaryList = new StatClosedPositionHandler().queryStatClosedPositionSummary(subAccountId, targetKey, targetType);
        if (statClosedPositionSummaryList == null || statClosedPositionSummaryList.isEmpty()) {
            AppReport.reportErr(new StringBuilder("ClosePositionService ## ")
                    .append(" recoverPosition ---- closed position items not exist---- ( subAccountId : ")
                    .append(subAccountId)
                    .append(", targetKey : ")
                    .append(targetKey)
                    .append(", targetType : ")
                    .append(targetType.name())
                    .append(")").toString());
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_CLOSED_POSITION_ITEM_NOT_EXIST.getValue(), "closedPositionSummary items not exist");
        }

        for (StatClosedPositionSummary statClosedPositionSummary : statClosedPositionSummaryList) {
            _recoverPosition(statClosedPositionSummary.getClosedId(), subAccountId, targetKey, targetType);
        }
    }

    /**
     * 按平仓的记录恢復持仓
     */
    private void _recoverPosition(long closedId, long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {

        if (AppReport.TRACE) {
            StringBuilder builder = new StringBuilder("recoverPosition -- closedId : ").append(closedId)
                    .append(", subAccountId : ").append(subAccountId)
                    .append(", targetKey : ").append(targetKey)
                    .append(", targetType : ").append(targetType.name());
            AppReport.trace(this.getClass(), builder.toString());
        }

//		StatClosedPositionHandler statClosedPositionHandler = new StatClosedPositionHandler();
        RecoverClosedPositionHandler recoverClosedPositionHandler = new RecoverClosedPositionHandler();
        StatPositionHandler statPositionHandler = new StatPositionHandler();
        // 查询需要恢复的所有平仓明细项
//		List<StatClosedPositionItem> statClosedPositionItemList = statClosedPositionHandler.queryStatClosedPositionItemList(closedId);
        RecoveryClosedData recoveryClosedData = recoverClosedPositionHandler.getRecoveryClosedData(closedId);

        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓   netPositionMap1 begin
         * *****************************************************************
         * */

        CheckNetPositionReqData checkNetPositionReqData = new CheckNetPositionReqData(subAccountId);
        checkNetPositionReqData.addToPositionTargetData(targetKey, targetType);
        Map<Long, Long> netPositionMap1 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓    netPositionMap1 end
         * *****************************************************************
         * */

        // 在事务中做恢复平仓处理
//		recoverClosedPositionHandler.recoverPosition(closedId, statClosedPositionItemList);
        recoverClosedPositionHandler.recoverPosition(recoveryClosedData);

        // 重新计算持仓汇总
        statPositionHandler.calculateAndUpdateStatPositionSummary(subAccountId, targetKey, targetType);

        /*
         * *****************************************************************
         *  核对数据：操作后记录净仓   netPositionMap2
         * *****************************************************************
         * */
        Map<Long, Long> netPositionMap2 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        // 核对净仓
        statPositionHandler.compareNetPositionForContract("recoverPosition", netPositionMap1, netPositionMap2);
        /*
         * *****************************************************************
         *  核对数据 结束
         * *****************************************************************
         * */
    }

}
