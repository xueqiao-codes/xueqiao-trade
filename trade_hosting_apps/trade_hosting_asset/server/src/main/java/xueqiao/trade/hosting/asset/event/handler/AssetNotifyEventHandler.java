package xueqiao.trade.hosting.asset.event.handler;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.events.*;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.io.UnsupportedEncodingException;

public class AssetNotifyEventHandler implements IMessageConsumer {
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
        AppLog.e("trigger onInit: start");
        Init init = new Init();
        init.initFromDb();
        init.initFromDealing();
    }

    /* 订单事件触发计算冻结保证金与手续费，订阅/取消订阅合约行情 */
    @consume(ExecOrderRunningEvent.class)
    ConsumeResult onHandleExecOrderRunningEvent(ExecOrderRunningEvent event) throws TException, UnsupportedEncodingException {
        HostingExecOrder execOrder = event.getRunningOrder();
        ExecutorHandler.calculateFrozenPosition(execOrder);
        return ConsumeResult.CONSUME_OK;
    }

    /* 订单过期事件触发移除锁仓信息 */
    @consume(ExecOrderExpiredEvent.class)
    ConsumeResult onHandleExecOrderExpiredEvent(ExecOrderExpiredEvent event) throws TException {
        HostingExecOrder execOrder = event.getExpiredOrder();
        ExecutorHandler.calculateFrozenPosition(execOrder);
        refreshFund(execOrder);
        ExecutorHandler.printAsset(execOrder.getSubAccountId());
        return ConsumeResult.CONSUME_OK;
    }

    @consume(XQOrderGuardEvent.class)
    ConsumeResult onHandlerXQOrderGuardEventType(XQOrderGuardEvent guardEvent) throws TException {
        if (XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD.equals(guardEvent.getType())) {
            return ConsumeResult.CONSUME_OK;
        }
        long execOrderId = Long.valueOf(guardEvent.getOrderId());
        IHostingDealingApi dealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        HostingExecOrder execOrder = dealingApi.getOrder(execOrderId);

        if (XQOrderGuardEventType.XQ_ORDER_CREATED_GUARD.equals(guardEvent.getType())
                || XQOrderGuardEventType.XQ_ORDER_CHANGED_GUARD.equals(guardEvent.getType())) {
            ExecutorHandler.calculateFrozenPosition(execOrder);
            refreshFund(execOrder);
        }

        if (XQOrderGuardEventType.XQ_ORDER_EXPIRED_GUARD.equals(guardEvent.getType())) {
            ExecutorHandler.calculateFrozenPosition(execOrder);
            refreshFund(execOrder);
        }

        return ConsumeResult.CONSUME_OK;
    }

    private void refreshFund(HostingExecOrder execOrder) throws TException {
        FundCalculateData calculateData = new FundCalculateData();
        calculateData.setSubAccountId(execOrder.getSubAccountId());
        SledCommodity sledCommodity = AssetGlobalDataFactory.getInstance()
                .getContractGlobalData(execOrder.getSubAccountId())
                .getSledCommodity(execOrder.getContractSummary().getSledCommodityId());
        calculateData.setCurrency(sledCommodity.getTradeCurrency());
        ExecutorHandler.calculateFund(calculateData);
    }

    @consume(PositionFeeChangedEvent.class)
    ConsumeResult onHandlerPositionFeeChangedEventType(PositionFeeChangedEvent changedEvent) {
        AppLog.w("onHandlerPositionFeeChangedEventType: "+ changedEvent);
        return PositionFeeHandler.onChange(changedEvent.getSubAccountId(),changedEvent.getContractId());
    }

    @consume(PositionFeeChangedGuardEvent.class)
    ConsumeResult onHandlerPositionFeeChangedGuardEventType(PositionFeeChangedGuardEvent guardEvent) {
        AppLog.w("onHandlerPositionFeeChangedEventType: "+ guardEvent);
        return PositionFeeHandler.onChange(guardEvent.getSubAccountId(), guardEvent.getContractId());
    }
}
