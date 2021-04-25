package xueqiao.trade.hosting.dealing.core;

import com.google.common.base.Preconditions;
import com.mysql.jdbc.MysqlErrorNumbers;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealInfo;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.verify.IVerifyProcessor;
import xueqiao.trade.hosting.dealing.core.verify.VerifyProcessFactory;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.data.ExecOrderIndexEntryV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderInDealingTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.events.ExecOrderCreatedEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

import java.sql.SQLException;
import java.util.AbstractMap;

public class ExecOrderCreator {

    private static IVerifyProcessor.VerifyResult verify(HostingExecOrder verifyOrder) {
        IVerifyProcessor.VerifyResult result = null;
        try {
            IVerifyProcessor processor = VerifyProcessFactory.getProcessor(verifyOrder);
            if (processor == null) {
                result = new IVerifyProcessor.VerifyResult(IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_FAILED)
                        .setFailedVerifyErrorCode(
                                TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE.getValue());
            } else {
                result = processor.verify(verifyOrder);
            }
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }

        if (result == null || result.getResultType() == null) {
            result = new IVerifyProcessor.VerifyResult(IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_FAILED)
                    .setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR.getValue());
        }

        return result;
    }

    public static void createUserDeal(int subUserId
            , long subAccountId
            , long execOrderId
            , xueqiao.trade.hosting.HostingExecOrderContractSummary contractSummary
            , xueqiao.trade.hosting.HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo {
        HostingExecOrderValidation.checkContractSummary(contractSummary);
        HostingExecOrderValidation.checkOrderDetail(orderDetail);

        HostingExecOrder operateExecOrder = new HostingExecOrder();
        operateExecOrder.setExecOrderId(execOrderId);
        operateExecOrder.setSubUserId(subUserId);
        operateExecOrder.setSubAccountId(subAccountId);
        operateExecOrder.setContractSummary(contractSummary);
        operateExecOrder.setOrderDetail(orderDetail);
        operateExecOrder.setVersion(1);
        operateExecOrder.setCreateTimestampMs(System.currentTimeMillis());
        operateExecOrder.setAccountSummary(new HostingExecOrderTradeAccountSummary());
        operateExecOrder.setTradeSummary(new HostingExecOrderTradeSummary());
        operateExecOrder.setDealInfo(new HostingExecOrderDealInfo());
        operateExecOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
        operateExecOrder.setOrderInputExt(new HostingExecOrderInputExt());

        if (!StringUtils.isEmpty(source)) {
            operateExecOrder.setSource(source);
        }

        HostingExecOrderStateInfo stateInfo = new HostingExecOrderStateInfo();
        HostingExecOrderState transferState = new HostingExecOrderState();
        transferState.setTimestampMs(System.currentTimeMillis());

        IVerifyProcessor.VerifyResult verifyResult = verify(operateExecOrder);
        if (verifyResult.getTradeAccountSummary() != null) {
            operateExecOrder.setAccountSummary(verifyResult.getTradeAccountSummary());
        }
        if (verifyResult.getOrderInputExt() != null) {
            operateExecOrder.setOrderInputExt(verifyResult.getOrderInputExt());
        }
        if (verifyResult.getLegContractSummarys() != null && !verifyResult.getLegContractSummarys().isEmpty()) {
            operateExecOrder.getContractSummary().setRelatedLegs(verifyResult.getLegContractSummarys());
        }
        if (verifyResult.getResultType() == IVerifyProcessor.VerifyResult.VerifyResultType.VERIFY_SUCCESS) {
            Preconditions.checkArgument(verifyResult.getTtlTimestampMs() > 0);
            operateExecOrder.setTtlTimestampMs(verifyResult.getTtlTimestampMs());
            transferState.setValue(HostingExecOrderStateValue.ORDER_WAITING_SLED_SEND);
        } else {
            // 审核失败的订单，在系统只保留10分钟的索引时间
            operateExecOrder.setTtlTimestampMs(System.currentTimeMillis() + 10*60*1000);
            transferState.setValue(HostingExecOrderStateValue.ORDER_VERIFY_FAILED);
        }
        operateExecOrder.setVerifyTimestampMs(System.currentTimeMillis());

        stateInfo.setCurrentState(transferState);
        stateInfo.setStatusMsg(HostingDealingHelper.generateStatusMsg(transferState.getValue()
                , verifyResult.getFailedVerifyErrorCode()
                , verifyResult.getStatusAppendMsg()));
        operateExecOrder.setStateInfo(stateInfo);

        storeOrder(operateExecOrder);
    }

    private static void storeOrder(HostingExecOrder operateExecOrder) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(DealingDBV2.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                ExecOrderIndexEntryV2 dealingIndexEntry = new ExecOrderIndexEntryV2();
                dealingIndexEntry.setExecOrderId(operateExecOrder.getExecOrderId());
                dealingIndexEntry.setCreateTimestampMs(operateExecOrder.getCreateTimestampMs());

                try {
                    new HostingExecOrderTableV2(getConnection()).addOrder(operateExecOrder);
                } catch (SQLException e) {
                    AppLog.e("SQLState=" + e.getSQLState() + ", SQLErrorCode=" + e.getErrorCode()
                            + ", " + e.getMessage(), e);
                    if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_EXISTED.getValue()
                                , "order is already existed!");
                    }
                    throw e;
                }

                new HostingExecOrderInDealingTableV2(getConnection()).insertIndexEntry(dealingIndexEntry);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                ExecOrderGuardEvent event = new ExecOrderGuardEvent();
                event.setGuardExecOrderId(operateExecOrder.getExecOrderId());
                event.setGuardType(ExecOrderGuardType.GUARD_ORDER_CREATED);
                return new AbstractMap.SimpleEntry<>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                ExecOrderCreatedEvent event = new ExecOrderCreatedEvent();
                event.setCreatedOrder(operateExecOrder);
                return event;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

            @Override
            protected void onCommitFinished() throws Exception {
                super.onCommitFinished();

                ExecOrderManager.Global().addExecutor(new ExecOrderExecutor(operateExecOrder));
            }
        }.execute();
    }
}
