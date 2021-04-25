package xueqiao.trade.hosting.arbitrage.core.executor;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractlimit.XQOrderContractLimitRunningActor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractlimit.XQOrderContractLimitStartingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.contractlimit.XQOrderContractLimitSuspendedActor;
import xueqiao.trade.hosting.arbitrage.core.effectdate.EffectDateController;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.trade.time.TimeUtil;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.GFDOrderCalculator;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

/**
 *  雪橇合约限价单执行器
 */
public class XQOrderContractLimitExecutor extends XQOrderContractBaseExecutor {
    private static final long AJUST_START_TIMESTAMPMS = -2000;
    private static final long AJUST_END_TIMESTAMPMS = 15000;

    private IHostingContractApi mContractApi;
    private TradeTimeSpanList mTradeTimeSpanList;
    private EffectDateController mEffectDateController;

    public XQOrderContractLimitExecutor(HostingXQOrder order) throws ErrorInfo {
        super(order);
        
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
    }

    public EffectDateController getEffectDateController() {
        if (mEffectDateController == null) {
            mEffectDateController = new EffectDateController(true
                , getOrder().getOrderDetail().getContractLimitOrderDetail().getEffectDate());
        }
        return mEffectDateController;
    }

    @Override
    public XQOrderHelper.GFDEffectiveTimeSpan createGFDEffectiveTimeSpan(long processTimestampMs) {
        return XQOrderHelper.createGFDEffectiveTimeSpan(
                getTargetContractDetail().getSledCommodity().getSledCommodityId()
                , processTimestampMs
                , AJUST_START_TIMESTAMPMS
                , AJUST_END_TIMESTAMPMS
        );
    }

    @Override
    public boolean refreshTradeTimeSpanList() throws ErrorInfo {
        mTradeTimeSpanList =  XQOrderHelper.constructTimeSpanList(
                getTargetContractDetail().getSledCommodity().getSledCommodityId());
        if (mTradeTimeSpanList == null) {
            return false;
        }
        /**
         *  提前准备，并且延长盘尾，增加尝试时间
         */
        mTradeTimeSpanList.ajustTimeSpan(AJUST_START_TIMESTAMPMS, AJUST_END_TIMESTAMPMS);

        return true;
    }

    @Override
    protected boolean suspendSupported() {
        return true;
    }

    @Override
    protected void prepare() throws ErrorInfo {
        super.prepare();
        refreshTradeTimeSpanList();
    }


    @Override
    protected int getOrderTargetVolume() {
        return getOrder().getOrderDetail().getContractLimitOrderDetail().getQuantity();
    }
    
    @Override
    protected long calCleanTimestampMs() throws ErrorInfo {
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(mContractApi);
        analysisor.setCommodityId(getTargetContractDetail().getSledCommodity().getSledCommodityId());
        analysisor.setProcessTimestampMs(System.currentTimeMillis());
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
                return TimeUtil.fromNow1Minute();
            }
            return cal.getTTLTimestampMs();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } 
        return TimeUtil.fromNow24H();
    }

    @Override
    protected IXQOrderActor createActor(HostingXQOrderStateValue targetStateValue) {
        if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_STARTING) {
            return new XQOrderContractLimitStartingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_RUNNING) {
            return new XQOrderContractLimitRunningActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            return new XQOrderContractLimitSuspendedActor();
        }
        return super.createActor(targetStateValue);
    }

    @Override
    protected void checkOrderDetail(HostingXQOrderDetail detail) throws ErrorInfo {
        ParameterChecker.check(detail.isSetContractLimitOrderDetail()
                , "orderDetail's contractLimitOrderDetail should be set");
        HostingXQContractLimitOrderDetail limitOrderDetail = detail.getContractLimitOrderDetail();
        ParameterChecker.check(limitOrderDetail.isSetDirection()
                , "contractLimitOrderDetail's direction should be set");
        ParameterChecker.check(limitOrderDetail.isSetQuantity() && limitOrderDetail.getQuantity() > 0
                , "contractLimitOrderDetail's quantity should be set and > 0");
        ParameterChecker.check(limitOrderDetail.isSetLimitPrice()
                && PriceUtils.isAppropriatePrice(limitOrderDetail.getLimitPrice())
                , "contractLimitOrderDetail's limitPrice should be set and price should be appropriate");
        ParameterChecker.check(limitOrderDetail.isSetEffectDate()
                , "contractLimitOrderDetail's effectDate must be set");
        ParameterChecker.check(limitOrderDetail.getEffectDate().isSetType()
                , "contractLimitOrderDetail's type of effectDate must be set");
        if (limitOrderDetail.getEffectDate().getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {
            ParameterChecker.check(limitOrderDetail.getEffectDate().isSetStartEffectTimestampS()
                        && limitOrderDetail.getEffectDate().getStartEffectTimestampS() >= 0
                    , "effectDate's startEffectTimestampS should be set and >= 0");
            ParameterChecker.check(limitOrderDetail.getEffectDate().isSetEndEffectTimestampS()
                        && limitOrderDetail.getEffectDate().getEndEffectTimestampS() > 0
                    , "effectDate's endEffectTimestampS should be set and > 0");
            ParameterChecker.check(limitOrderDetail.getEffectDate().getEndEffectTimestampS()
                        > limitOrderDetail.getEffectDate().getStartEffectTimestampS()
                    , "effectDate's endEffectTimestampS should > startEffectTimestampS");
        }
    }

    @Override
    public TradeTimeSpanList getTradeTimeSpanList() {
        if (mTradeTimeSpanList == null) {
            mTradeTimeSpanList = new TradeTimeSpanList();
        }
        return mTradeTimeSpanList;
    }

    public SledContractDetails getTargetContractDetail() {
        XQOrderSubExecutor subExecutor = mSubExecutors.get(mSledContractId);
        Preconditions.checkNotNull(subExecutor);
        return subExecutor.getOrderMarketer().getContractDetails();
    }

}
