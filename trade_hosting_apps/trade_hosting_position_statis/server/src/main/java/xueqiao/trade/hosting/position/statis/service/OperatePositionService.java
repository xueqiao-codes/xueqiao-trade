package xueqiao.trade.hosting.position.statis.service;

import com.antiy.error_code.ErrorCodeOuter;
import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.bean.CheckNetPositionReqData;
import xueqiao.trade.hosting.position.statis.service.bean.DisassembledData;
import xueqiao.trade.hosting.position.statis.service.bean.MergeToComposeData;
import xueqiao.trade.hosting.position.statis.service.bean.PositionData;
import xueqiao.trade.hosting.position.statis.service.handler.DisassembleComposeHandler;
import xueqiao.trade.hosting.position.statis.service.controller.MergeToComposeController;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.thread.StatSingleWorkerThread;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;

import java.util.Map;

public class OperatePositionService {

    /**
     * 构造持仓数据
     * 数据源类型有：
     * 1 组合构造
     * 2 组合反向构造
     */
    public void constructComposePositionData(StatContructComposeReq contructComposeReq) throws ErrorInfo {
        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), "constructComposePositionData -- contructComposeReq : " + contructComposeReq.toString());
        }
        throw new ErrorInfo(ErrorCodeOuter.ILLEGAL_OPEARTION_ERROR.getErrorCode(), "this operation not support anymore");

//		ConstructComposeHandler constructComposeHandler = new ConstructComposeHandler();
//
//		// 数据验证
//		constructComposeHandler.verify(contructComposeReq);
//		List<ComposeLegData> composeLegDataList = constructComposeHandler.checkComposeInfo(contructComposeReq);
//
//		/* 数据准备 */
//		StatPositionHandler statPositionHandler = new StatPositionHandler();
//
//		// 持仓明细项
//		StatPositionItem statPositionItem = constructComposeHandler.transferToStatPositionItem(contructComposeReq);
//
//		// 持仓小单元
//		List<StatPositionUnit> statPositionUnitList = constructComposeHandler.transferToStatPositionUnitList(statPositionItem, composeLegDataList);
//
//		// 组合反向构造数据
//		List<PositionData> reverseConstructionPositionDataList = constructComposeHandler.constructReversePositionDataList(contructComposeReq, composeLegDataList);
//
//		/*
//		* *****************************************************************
//		*  核对数据：操作前记录净仓   netPositionMap1 begin
//		* *****************************************************************
//		* */
//		CheckNetPositionReqData checkNetPositionReqData = new CheckNetPositionReqData(statPositionItem.getSubAccountId());
//		checkNetPositionReqData.addToPositionTargetData(statPositionItem.getTargetKey(), statPositionItem.getTargetType());
//		for (PositionData positionData : reverseConstructionPositionDataList) {
//			checkNetPositionReqData.addToPositionTargetData(positionData.getStatPositionItem().getTargetKey(), positionData.getStatPositionItem().getTargetType());
//		}
//		Map<Long, Long> netPositionMap1 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
//		/*
//		 * *****************************************************************
//		 *  核对数据：操作前记录净仓    netPositionMap1 end
//		 * *****************************************************************
//		 * */
//
//		/* 在事务里写入构造持仓数据 */
//		constructComposeHandler.constructComposePosition(statPositionItem, statPositionUnitList, reverseConstructionPositionDataList);
//
//		// 重新计算持仓汇总
//		statPositionHandler.calculateAndUpdateStatPositionSummary(statPositionItem.getSubAccountId(), statPositionItem.getTargetKey(), statPositionItem.getTargetType());
//		for (PositionData positionData : reverseConstructionPositionDataList) {
//			statPositionHandler.calculateAndUpdateStatPositionSummary(positionData.getStatPositionItem().getSubAccountId(), positionData.getStatPositionItem().getTargetKey(), positionData.getStatPositionItem().getTargetType());
//		}
//
//		/*
//		 * *****************************************************************
//		 *  核对数据：操作后记录净仓   netPositionMap2
//		 * *****************************************************************
//		 * */
//		Map<Long, Long> netPositionMap2 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
//		// 核对净仓
//		statPositionHandler.compareNetPositionForContract("constructComposePositionData", netPositionMap1, netPositionMap2);
//		/*
//		 * *****************************************************************
//		 *  核对数据 结束
//		 * *****************************************************************
//		 * */
    }

    /**
     * 拆解组合持仓数据
     * 数据源类型有：
     * 1 成交组合拆解
     * 2 构造组合拆解
     */
    public void disassembleComposePositionData(DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {
            @Override
            protected Void onCall() throws Exception {
                _disassembleComposePositionData(disassembleComposePositionReq);
                return null;
            }
        }.get();
    }

    private void _disassembleComposePositionData(DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo {
        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), "disassembleComposePositionData -- disassembleComposePositionReq : " + disassembleComposePositionReq.toString());
        }
        DisassembleComposeHandler disassembleComposeHandler = new DisassembleComposeHandler();

        // 数据验证
        disassembleComposeHandler.verify(disassembleComposePositionReq);

        for (PositionItemData positionItemData : disassembleComposePositionReq.getPositionItemDataList()) {
            doDisassemble(disassembleComposeHandler, disassembleComposePositionReq.getSubAccountId(), disassembleComposePositionReq.getTargetKey(), positionItemData);
        }
    }

    private void doDisassemble(DisassembleComposeHandler disassembleComposeHandler, long subAccountId, String targetKey, PositionItemData positionItemData) throws ErrorInfo {
        StatPositionHandler statPositionHandler = new StatPositionHandler();
        // 核查拆解的数量是否正确
        StatPositionItem statPositionItem = statPositionHandler.getStatPositionItem(positionItemData.getPositionItemId());
        ParameterChecker.check(statPositionItem != null, "positionItemId is invalid, fail to find statPositionItem");
        ParameterChecker.check(statPositionItem.getTargetType() == HostingXQTargetType.COMPOSE_TARGET, "can not disassemble contact");
        ParameterChecker.check(statPositionItem.getSubAccountId() == subAccountId, "positionItemId is invalid, positionItemId is not belong to this subAccountId");
        ParameterChecker.check(statPositionItem.getTargetKey().equals(targetKey), "positionItemId is invalid, positionItemId is not belong to this targetKey");
        ParameterChecker.check(positionItemData.getQuantity() <= statPositionItem.getQuantity(), "quantity of disassembly should not larger than quantity in positionItem");

        if (statPositionItem.getTargetType() != HostingXQTargetType.COMPOSE_TARGET) {
            AppReport.reportErr("OperatePositionService ## doDisassemble -- can not disassemble contact ---- positionItemId : " + positionItemData.getPositionItemId());
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_DISASSEMABLE_CONTACT.getValue(), "can not disassemble contact");
        }

        // 计算新的持仓数据
        DisassembledData disassembledData = disassembleComposeHandler.getDisassemblePositionDataList(statPositionItem, positionItemData.getQuantity());

        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓   netPositionMap1 begin
         * *****************************************************************
         * */
        CheckNetPositionReqData checkNetPositionReqData = new CheckNetPositionReqData(statPositionItem.getSubAccountId());
        checkNetPositionReqData.addToPositionTargetData(statPositionItem.getTargetKey(), statPositionItem.getTargetType());
        for (PositionData positionData : disassembledData.getDisassembleContractDataList()) {
            checkNetPositionReqData.addToPositionTargetData(positionData.getStatPositionItem().getTargetKey(), positionData.getStatPositionItem().getTargetType());
        }
        Map<Long, Long> netPositionMap1 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        /*
         * *****************************************************************
         *  核对数据：操作前记录净仓    netPositionMap1 end
         * *****************************************************************
         * */

        AppLog.w("OperatePositionService # doDisassemble ---- disassembledData : " + new Gson().toJson(disassembledData));

        /* 在事务里写入拆分持仓数据 */
        disassembleComposeHandler.disassembleComposePosition(disassembledData);

        // 重新计算持仓汇总
        statPositionHandler.calculateAndUpdateStatPositionSummary(subAccountId, statPositionItem.getTargetKey(), statPositionItem.getTargetType());
        for (PositionData positionData : disassembledData.getDisassembleContractDataList()/*disassemblePositionDataList*/) {
            statPositionHandler.calculateAndUpdateStatPositionSummary(subAccountId, positionData.getStatPositionItem().getTargetKey(), positionData.getStatPositionItem().getTargetType());
        }

        /*
         * *****************************************************************
         *  核对数据：操作后记录净仓   netPositionMap2
         * *****************************************************************
         * */
        Map<Long, Long> netPositionMap2 = statPositionHandler.calculateNetPositionForContract(checkNetPositionReqData);
        // 核对净仓
        statPositionHandler.compareNetPositionForContract("doDisassemble", netPositionMap1, netPositionMap2);
        /*
         * *****************************************************************
         *  核对数据 结束
         * *****************************************************************
         * */
    }

    /**
     * 合并合约为组合
     */
    public void mergeToCompose(StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {
            @Override
            protected Void onCall() throws Exception {
                _mergeToCompose(mergeToComposeReq);
                return null;
            }
        }.get();
    }

    private void _mergeToCompose(StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo {
        MergeToComposeController mergeToComposeController = new MergeToComposeController(mergeToComposeReq);
        /*
         * 检验数据的有效性
         * */
        mergeToComposeController.validateParams();

        /*
         * 事务中查询，计算，并更新库
         * */
        MergeToComposeData mergeToComposeData = new DBTransactionHelper<MergeToComposeData>(PositionStatisDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                mergeToComposeController.prepareData(getConnection());
                mergeToComposeController.validateData();
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                mergeToComposeController.store(getConnection());
            }

            @Override
            public MergeToComposeData getResult() {
                return mergeToComposeController.getMergeToComposeData();
            }
        }.execute().getResult();

        /*
         * 重新计算持仓核对
         * */
        StatPositionHandler statPositionHandler = new StatPositionHandler();
        StatPositionItem composePositionItem = mergeToComposeData.getPostMergeComposePositionData().getStatPositionItem();
        statPositionHandler.calculateAndUpdateStatPositionSummary(composePositionItem.getSubAccountId(), composePositionItem.getTargetKey(), composePositionItem.getTargetType());
        for (PositionData postMergeContractPositionData : mergeToComposeData.getPostMergeContractPositionDataList()) {
            statPositionHandler.calculateAndUpdateStatPositionSummary(mergeToComposeReq.getSubAccountId(), postMergeContractPositionData.getStatPositionItem().getTargetKey(), postMergeContractPositionData.getStatPositionItem().getTargetType());
        }
    }
}
