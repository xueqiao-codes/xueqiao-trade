package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.events.StatPositionDynamicInfoEvent;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractTargetCalculator;
import xueqiao.trade.hosting.position.statis.core.factory.FactoryInstance;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

public abstract class PositionDynamicCalculator<T, Q> extends AbstractTargetCalculator<T, Q> {

    private TradeHostingAssetStub tradeHostingAssetStub;
    private String baseCurrency;
    private double baseCurrencyDynamicBenefit;

    public PositionDynamicCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
        tradeHostingAssetStub = new TradeHostingAssetStub();
    }

    /**
     * 获取基币(CNY)动态总权益
     */
    protected void fetchBaseCurrencyDynamicBenefit() throws ErrorInfo {
        DynamicBenefit dynamicBenefit = DynamicBenefitManager.getInstance().fetchBaseCurrencyDynamicBenefit(subAccountId);
        if (dynamicBenefit != null) {
            baseCurrency = dynamicBenefit.getBaseCurrency();
            baseCurrencyDynamicBenefit = dynamicBenefit.getBaseCurrencyDynamicBenefit();
        } else {
            AppLog.e("PositionDynamicCalculator # fetchBaseCurrencyDynamicBenefit ---- dynamicBenefit is null, subAccountId : " + subAccountId);
        }
    }

    protected String getBaseCurrency() throws ErrorInfo {
        if (StringUtils.isBlank(baseCurrency)) {
            fetchBaseCurrencyDynamicBenefit();
        }
        return baseCurrency;
    }

    protected double getBaseCurrencyDynamicBenefit() throws ErrorInfo {
        if (baseCurrencyDynamicBenefit == 0) {
            fetchBaseCurrencyDynamicBenefit();
        }
        return baseCurrencyDynamicBenefit;
    }

    /**
     * 推送消息到客户端
     */
    protected void sendPositionDynamicInfoMsg(StatPositionDynamicInfo positionDynamicInfo) {
        StatPositionDynamicInfoEvent event = new StatPositionDynamicInfoEvent();
        event.setSubAccountId(subAccountId);
        event.setTargetKey(targetKey);
        event.setTargetType(targetType);
        event.setPositionDynamicInfo(positionDynamicInfo);
        event.setEventCreateTimestampMs(System.currentTimeMillis());

        UserMsg msg = new UserMsg();
        msg.setMsgType(event.getClass().getSimpleName());
        msg.setMsgContent(ProtocolUtil.serialize(FactoryInstance.getInstance().getCompactFactory(), event).array());
        PushApi.sendMsgBySubAccountId(subAccountId, msg);
    }
}
