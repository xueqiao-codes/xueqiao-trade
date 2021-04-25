//package xueqiao.trade.hosting.dealing.core.verify;
//
//import org.apache.thrift.TBase;
//import org.soldier.base.logger.AppLog;
//import org.soldier.framework.message_bus.api.IGuardPolicy;
//import org.soldier.framework.message_bus.api.MessageAgent;
//import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
//import org.soldier.platform.svr_platform.comm.ErrorInfo;
//import xueqiao.trade.hosting.HostingExecOrder;
//import xueqiao.trade.hosting.HostingExecOrderContractSummary;
//import xueqiao.trade.hosting.HostingExecOrderState;
//import xueqiao.trade.hosting.HostingExecOrderStateValue;
//import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
//import xueqiao.trade.hosting.dealing.core.cleaner.ExecOrderInDealingCleaner;
//import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
//import xueqiao.trade.hosting.dealing.storage.data.ExecOrderIndexEntryV2;
//import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderInDealingTableV2;
//import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
//import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
//import xueqiao.trade.hosting.events.ExecOrderGuardType;
//import xueqiao.trade.hosting.events.ExecOrderVerifyFailedEvent;
//import xueqiao.trade.hosting.events.ExecOrderVerifySuccessEvent;
//import xueqiao.trade.hosting.framework.HostingMessageContext;
//import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
//import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
//import xueqiao.trade.hosting.framework.utils.ParameterChecker;
//import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;
//
//import java.util.AbstractMap;
//import java.util.ArrayList;
//
//public class VerifyHandler {
//
//    private HostingExecOrder originOrder;
//    private HostingExecOrder newOrder;
//
//    public VerifyHandler(HostingExecOrder originOrder) {
//        this.originOrder = originOrder;
//    }
//
//    public HostingExecOrder getNewOrder() {
//        return newOrder;
//    }
//
//    public void process() throws ErrorInfo {
//        long startVerifyTimestampMs = System.currentTimeMillis();
//
//        IVerifyProcessor.VerifyResult result = null;
//        if (System.currentTimeMillis() >= originOrder.getStateInfo().getCurrentState().getTimestampMs() + 15000) {
//            result = new IVerifyProcessor.VerifyResult(IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_FAILED)
//                    .setFailedVerfiyErrorCode(
//                            TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS.getValue());
//        } else {
//            try {
//                IVerifyProcessor processor = VerifyProcessFactory.getProcessor(originOrder);
//                if (processor == null) {
//                    result = new IVerifyProcessor.VerifyResult(IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_FAILED)
//                            .setFailedVerfiyErrorCode(
//                                    TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE.getValue());
//                } else {
//                    result = processor.verify(originOrder);
//                }
//            } catch (Throwable e) {
//                AppLog.e(e.getMessage(), e);
//            }
//        }
//
//        if (result == null || result.getResultType() == null) {
//            result = new IVerifyProcessor.VerifyResult(IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_FAILED)
//                    .setFailedVerfiyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR.getValue());
//        }
//
//        try {
//            HostingExecOrder resultOrder = new HostingExecOrder();
//            resultOrder.setExecOrderId(originOrder.getExecOrderId());
//            resultOrder.setAccountSummary(result.getTradeAccountSummary());
//            resultOrder.setOrderInputExt(result.getOrderInputExt());
//            if (result.getLegContractSummarys() != null && !result.getLegContractSummarys().isEmpty()) {
//                resultOrder.setContractSummary(new HostingExecOrderContractSummary());
//                resultOrder.getContractSummary().setRelatedLegs(result.getLegContractSummarys());
//            }
//            if (result.getResultType() == IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_SUCCESS) {
//                if (result.getTtlTimestampMs() > 0) {
//                    resultOrder.setTtlTimestampMs(result.getTtlTimestampMs());
//                }
//                orderVerifyPassed(resultOrder);
//            } else {
//                // 审核失败的订单，在系统只保留10分钟的索引时间
//                resultOrder.setTtlTimestampMs(System.currentTimeMillis() + 10*60*1000);
//                orderVerifyFailed(resultOrder, result.getFailedVerfiyErrorCode());
//            }
//
//            if (resultOrder.isSetTtlTimestampMs() && resultOrder.getTtlTimestampMs() > 0) {
//                ExecOrderInDealingCleaner.Global().addCleanIndex(resultOrder.getTtlTimestampMs()
//                        , originOrder.getExecOrderId());
//            }
//
//        } catch (ErrorInfo ei) {
//            if (ei.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_NOT_EXISTED.getValue()) {
//                AppLog.w("UnExpected verify order not existed! execOrderId=" + originOrder.getExecOrderId());
//                return ;
//            } else if (ei.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY.getValue()) {
//                AppLog.i("Order already goes to other status for execOrderId=" + originOrder.getExecOrderId());
//                return ;
//            }
//            throw ei;
//        } finally {
//            if (AppLog.infoEnabled()) {
//                AppLog.i("Verify execOrderId=" + originOrder.getExecOrderId() + ", Result=" + result
//                        + ", timeEscaped=" + (System.currentTimeMillis() - startVerifyTimestampMs) + "ms");
//            }
//        }
//    }
//
//    public void orderVerifyPassed(HostingExecOrder passedOrder) throws ErrorInfo {
//        new DBStepHelperWithMsg<Void>(DealingDBV2.Global()) {
//
//            @Override
//            public void onPrepareData() throws ErrorInfo, Exception {
//                if (originOrder.getStateInfo().getCurrentState().getValue()
//                        != HostingExecOrderStateValue.ORDER_WAITING_VERIFY) {
//                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY.getValue()
//                            , "execOrder not in wating verify status");
//                }
//            }
//
//            @Override
//            public void onUpdate() throws ErrorInfo, Exception {
//                newOrder = new HostingExecOrder(originOrder);
//
//                HostingExecOrder operateOrder = new HostingExecOrder();  // 要更新的内容
//                operateOrder.setExecOrderId(passedOrder.getExecOrderId());
//
//                operateOrder.setAccountSummary(passedOrder.getAccountSummary());
//                newOrder.setAccountSummary(operateOrder.getAccountSummary());
//                operateOrder.setOrderInputExt(passedOrder.getOrderInputExt());
//                newOrder.setOrderInputExt(operateOrder.getOrderInputExt());
//
//                if (passedOrder.isSetContractSummary()) {
//                    operateOrder.setContractSummary(new HostingExecOrderContractSummary());
//                    if (passedOrder.getContractSummary().isSetRelatedLegs()) {
//                        newOrder.getContractSummary().setRelatedLegs(
//                                passedOrder.getContractSummary().getRelatedLegs());
//                        operateOrder.getContractSummary().setRelatedLegs(
//                                passedOrder.getContractSummary().getRelatedLegs());
//                    }
//                }
//
//                if (passedOrder.isSetTtlTimestampMs() && passedOrder.getTtlTimestampMs() > 0) {
//                    operateOrder.setTtlTimestampMs(passedOrder.getTtlTimestampMs());
//                    newOrder.setTtlTimestampMs(passedOrder.getTtlTimestampMs());
//                }
//
//                operateOrder.setVerifyTimestampMs(System.currentTimeMillis());
//                newOrder.setVerifyTimestampMs(operateOrder.getVerifyTimestampMs());
//
//                HostingExecOrderState transferState = new HostingExecOrderState();
//                transferState.setTimestampMs(System.currentTimeMillis());
//                transferState.setValue(HostingExecOrderStateValue.ORDER_WAITING_SLED_SEND);
//
//                if (newOrder.getStateInfo().getHistoryStates() == null) {
//                    newOrder.getStateInfo().setHistoryStates(new ArrayList<>());
//                }
//                newOrder.getStateInfo().getHistoryStates().add(newOrder.getStateInfo().getCurrentState());
//                newOrder.getStateInfo().setCurrentState(transferState);
//                newOrder.getStateInfo().setStatusMsg(HostingDealingHelper.generateStatusMsg(
//                        transferState.getValue(), 0, ""));
//
//                operateOrder.setStateInfo(newOrder.getStateInfo());
//                operateOrder.setVersion(newOrder.getVersion() + 1);
//
//                newOrder.setVersion(operateOrder.getVersion());
//
//                new HostingExecOrderTableV2(getConnection()).updateOrder(operateOrder);
//            }
//
//            @Override
//            public Void getResult() {
//                return null;
//            }
//
//            @SuppressWarnings("rawtypes")
//            @Override
//            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
//                ExecOrderGuardEvent event = new ExecOrderGuardEvent();
//                event.setGuardType(ExecOrderGuardType.GUARD_ORDER_VERIFY_SUCCESS);
//                event.setGuardExecOrderId(originOrder.getExecOrderId());
//                return new AbstractMap.SimpleEntry<>(event
//                        , new TimeoutGuardPolicy().setTimeoutSeconds(2));
//            }
//
//            @SuppressWarnings("rawtypes")
//            @Override
//            protected TBase prepareNotifyMessage() {
//                ExecOrderVerifySuccessEvent event = new ExecOrderVerifySuccessEvent();
//                HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
//                // do not notify history states
//                notifyOrder.getStateInfo().unsetHistoryStates();
//                event.setVerifySuccessOrder(notifyOrder);
//                return event;
//            }
//
//            @Override
//            protected MessageAgent getMessageAgent() {
//                return HostingMessageContext.Global().getSenderAgent();
//            }
//
//        }.execute();
//    }
//
//    public void orderVerifyFailed(HostingExecOrder failedOrder, int failedErrorCode) throws ErrorInfo {
//        new DBStepHelperWithMsg<Void>(DealingDBV2.Global()) {
//
//            @Override
//            public void onPrepareData() throws ErrorInfo, Exception {
//                if (originOrder.getStateInfo().getCurrentState().getValue()
//                        != HostingExecOrderStateValue.ORDER_WAITING_VERIFY) {
//                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY.getValue()
//                            , "execOrder not in wating verify status");
//                }
//            }
//
//            @Override
//            public void onUpdate() throws ErrorInfo, Exception {
//                newOrder = new HostingExecOrder(originOrder);
//
//                HostingExecOrder operateOrder = new HostingExecOrder();
//                operateOrder.setExecOrderId(originOrder.getExecOrderId());
//
//                if (failedOrder.isSetAccountSummary()) {
//                    operateOrder.setAccountSummary(failedOrder.getAccountSummary());
//                    newOrder.setAccountSummary(failedOrder.getAccountSummary());
//                }
//                if (failedOrder.isSetOrderInputExt()) {
//                    operateOrder.setOrderInputExt(failedOrder.getOrderInputExt());
//                    newOrder.setOrderInputExt(failedOrder.getOrderInputExt());
//                }
//                if (failedOrder.isSetContractSummary()) {
//                    operateOrder.setContractSummary(new HostingExecOrderContractSummary());
//                    if (failedOrder.getContractSummary().isSetRelatedLegs()) {
//                        newOrder.getContractSummary().setRelatedLegs(
//                                failedOrder.getContractSummary().getRelatedLegs());
//                        operateOrder.getContractSummary().setRelatedLegs(
//                                failedOrder.getContractSummary().getRelatedLegs());
//                    }
//                }
//
//                if (failedOrder.isSetTtlTimestampMs() && failedOrder.getTtlTimestampMs() > 0) {
//                    operateOrder.setTtlTimestampMs(failedOrder.getTtlTimestampMs());
//                    newOrder.setTtlTimestampMs(failedOrder.getTtlTimestampMs());
//                }
//
//                operateOrder.setVerifyTimestampMs(System.currentTimeMillis());
//                newOrder.setVerifyTimestampMs(operateOrder.getVerifyTimestampMs());
//
//                HostingExecOrderState transferState = new HostingExecOrderState();
//                transferState.setTimestampMs(System.currentTimeMillis());
//                transferState.setValue(HostingExecOrderStateValue.ORDER_VERIFY_FAILED);
//
//                if (newOrder.getStateInfo().getHistoryStates() == null) {
//                    newOrder.getStateInfo().setHistoryStates(new ArrayList<>());
//                }
//                newOrder.getStateInfo().getHistoryStates().add(newOrder.getStateInfo().getCurrentState());
//                newOrder.getStateInfo().setCurrentState(transferState);
//                newOrder.getStateInfo().setFailedErrorCode(failedErrorCode);
//                newOrder.getStateInfo().setStatusMsg(HostingDealingHelper.generateStatusMsg(
//                        HostingExecOrderStateValue.ORDER_VERIFY_FAILED, failedErrorCode, ""));
//
//                operateOrder.setStateInfo(newOrder.getStateInfo());
//                operateOrder.setVersion(newOrder.getVersion() + 1);
//
//                newOrder.setVersion(operateOrder.getVersion());
//                new HostingExecOrderTableV2(getConnection()).updateOrder(operateOrder);
//            }
//
//            @Override
//            public Void getResult() {
//                return null;
//            }
//
//            @SuppressWarnings("rawtypes")
//            @Override
//            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
//                ExecOrderGuardEvent event = new ExecOrderGuardEvent();
//                event.setGuardExecOrderId(originOrder.getExecOrderId());
//                event.setGuardType(ExecOrderGuardType.GUARD_ORDER_VERIFY_FAILED);
//                return new AbstractMap.SimpleEntry<>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
//            }
//
//            @SuppressWarnings("rawtypes")
//            @Override
//            protected TBase prepareNotifyMessage() {
//                ExecOrderVerifyFailedEvent event = new ExecOrderVerifyFailedEvent();
//                HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
//                // do not notify history states
//                notifyOrder.getStateInfo().unsetHistoryStates();
//                event.setVerifyFailedOrder(notifyOrder);
//                return event;
//            }
//
//            @Override
//            protected MessageAgent getMessageAgent() {
//                return HostingMessageContext.Global().getSenderAgent();
//            }
//
//        }.execute();
//    }
//
//}
