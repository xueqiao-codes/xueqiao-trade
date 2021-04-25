package xueqiao.trade.hosting.arbitrage.core.executor;


import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.actor.contractparked.XQOrderContractParkedCreatedActor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractparked.XQOrderContractParkedRunningActor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractparked.XQOrderContractParkedStartingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractparked.XQOrderContractParkedSuspendedActor;
import xueqiao.trade.hosting.arbitrage.core.data.CalculateTradesResult;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractParkedOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPrice;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPriceType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.trade.time.TimeUtil;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.GFDOrderCalculator;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;


/**
 * 
 * @author wangli
 *
 */
public class XQOrderContractParkedExecutor extends XQOrderBaseExecutor {
    
    private IHostingContractApi mContractApi;
    private SledContractDetails mTargetContractDetail;
    private TradeTimeSpanList mTradeTimeSpanList = new TradeTimeSpanList();
    
    public XQOrderContractParkedExecutor(HostingXQOrder order) throws ErrorInfo {
        super(order);
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
    }
    
    public SledContractDetails getTargetContractDetail() {
        return mTargetContractDetail;
    }

    @Override
    public boolean refreshTradeTimeSpanList() throws ErrorInfo {
        mTradeTimeSpanList = XQOrderHelper.constructTimeSpanList(mTargetContractDetail.getSledCommodity().getSledCommodityId());
        if (mTradeTimeSpanList == null) {
            mTradeTimeSpanList = new TradeTimeSpanList();
            return false;
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("refreshTradeTimeSpanList nearest trade time is " 
                    + mTradeTimeSpanList.getNearestSpan(System.currentTimeMillis())
                    + " for xqOrderId=" + getOrder().getOrderId()
                    + ",orderTarget=" + getOrder().getOrderTarget());
        }
        
        return true;
    }

    @Override
    public TradeTimeSpanList getTradeTimeSpanList() {
        return mTradeTimeSpanList;
    }

    @Override
    protected long calCleanTimestampMs() throws ErrorInfo {
        // 先计算预埋单的触发时间
        TradeTimeSpan triggerTimeSpan = getTradeTimeSpanList().getNearestSpan(getOrder().getCreateTimestampMs());
        if (triggerTimeSpan == null) {
            return TimeUtil.fromNow24H();
        }
        
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(mContractApi);
        analysisor.setCommodityId(mTargetContractDetail.getSledCommodity().getSledCommodityId());
        analysisor.setProcessTimestampMs(triggerTimeSpan.getStartTimestampMs());
        try {
            if (!analysisor.analysis()) {
                AppLog.e(analysisor);
                return TimeUtil.fromNow24H();
            }
            GFDOrderCalculator cal = new GFDOrderCalculator(analysisor);
            if (!cal.calculate()) {
                AppLog.e(cal);
                return TimeUtil.fromNow24H();
            }
            if (cal.isMarketClosed()) {
                return System.currentTimeMillis() + 60*1000;
            }
            return cal.getTTLTimestampMs();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } 
        return TimeUtil.fromNow24H();
        
    }

    @Override
    protected void checkOrderTarget(HostingXQTarget target) throws ErrorInfo {
        ParameterChecker.check(target.getTargetType() == HostingXQTargetType.CONTRACT_TARGET
                , "target's type should be set to contract target");
        ParameterChecker.check(target.isSetTargetKey() && StringUtils.isNotEmpty(target.getTargetKey())
                , "targetKey should be set and not empty");
        
        try {
            NumberUtils.createLong(target.getTargetKey());
        } catch (NumberFormatException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "target's key should be number");
        }
        
    }

    @Override
    protected void checkOrderDetail(HostingXQOrderDetail detail) throws ErrorInfo {
        ParameterChecker.check(detail.isSetContractParkedOrderDetail()
                , "orderDetail's contractParkedOrderDetail should be set");
        
        HostingXQContractParkedOrderDetail contractParkedOrderDetail
            = detail.getContractParkedOrderDetail();
        ParameterChecker.check(contractParkedOrderDetail.isSetDirection()
                , "contractParkedOrderDetail's direction should be set");
        ParameterChecker.check(contractParkedOrderDetail.isSetQuantity() && contractParkedOrderDetail.getQuantity() > 0
                , "contractParkedOrderDetail's quantity should be set and > 0");
        ParameterChecker.check(contractParkedOrderDetail.isSetPrice()
                , "contractParkedOrderDetail's price should be set");
        
        HostingXQOrderPrice price = contractParkedOrderDetail.getPrice();
        ParameterChecker.check(price.isSetPriceType()
                , "contractParkedOrderDetail's price must set priceType");
        if (price.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_LIMIT) {
            ParameterChecker.check(price.isSetLimitPrice() 
                    && PriceUtils.isAppropriatePrice(price.getLimitPrice())
                , "contractParkedOrderDetail's price must set appropriate limitPrice");
        }
        if (price.isSetChasePriceTicks()) {
            ParameterChecker.check(price.getChasePriceTicks() >= 0
                , "contractParkedOrderDetail's price of chasePriceTicks must >= 0");
        }
        if (price.isSetChasePriceValue()) {
            ParameterChecker.check(price.getChasePriceValue() >= 0.0
                , "contractParkedOrderDetail's price of chasePriceValue must >= 0");
        }
        
    }
    
    @Override
    protected IXQOrderActor createActor(HostingXQOrderStateValue targetStateValue) {
        if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_CREATED) {
            return new XQOrderContractParkedCreatedActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_STARTING) {
            return new XQOrderContractParkedStartingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_RUNNING) {
            return new XQOrderContractParkedRunningActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            return new XQOrderContractParkedSuspendedActor();
        }
        return super.createActor(targetStateValue);
    }

    @Override
    protected boolean suspendSupported() {
        return true;
    }

    @Override
    protected void prepare() throws ErrorInfo {
        mTargetContractDetail = mContractApi.getContractDetailForSure(
                NumberUtils.createLong(getOrder().getOrderTarget().getTargetKey()));
        
        refreshTradeTimeSpanList();
    }

    @Override
    protected void calculateTrades(CalculateTradesResult result) throws ErrorInfo {
    }

    @Override
    protected int getOrderTargetVolume() {
        return Integer.MAX_VALUE;
    }

}
