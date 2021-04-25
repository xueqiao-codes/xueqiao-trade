package xueqiao.trade.hosting.position.statis.core.quotation;

import com.antiy.error_code.ErrorCodeInner;
import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.events.StatPositionDynamicInfoEvent;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;
import xueqiao.trade.hosting.position.statis.core.factory.FactoryInstance;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

public abstract class StatQuotationBaseListener<T, Q> extends StatQuotation<T, Q> {

    private TradeHostingAssetStub tradeHostingAssetStub = new TradeHostingAssetStub();

    protected StatQuotationBaseListener(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    /**
     * 获取基币(CNY)动态总权益
     * */
    protected double getBaseCurrencyDynamicBenefit() throws TException {
        ReqHostingFundOption option = new ReqHostingFundOption();
        option.addToSubAccountIds(subAccountId);
        option.setBaseCurrency(true);

        HostingFundPage hostingFundPage = tradeHostingAssetStub.getHostingSubAccountFund(option);
        if (hostingFundPage != null && hostingFundPage.getPageSize() > 0) {
            for (HostingFund hostingFund : hostingFundPage.getPage()) {
                if (CNYRateHelper.getInstance().getBaseCurrency().equals(hostingFund.getCurrency())) {
                    return hostingFund.getDynamicBenefit();
                }
            }
            /*
            * CNY不为基币，或者获取不到CNY的动态权益，则计算出来
            * */
            String sourceCurrency = hostingFundPage.getPage().get(0).getCurrency();
            return CNYRateHelper.getInstance().exchangeRate(sourceCurrency, CNYRateHelper.getInstance().getBaseCurrency(), hostingFundPage.getPage().get(0).getDynamicBenefit());
        }
        throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "empty result for getHostingSubAccountFund, fail to get dynamicBenefit");
    }

    /**
     * 推送消息到客户端
     * */
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
