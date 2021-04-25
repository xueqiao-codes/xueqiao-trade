package xueqiao.trade.hosting.arbitrage.core.composelimit.pod.impl;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.handicap.HandicapManager;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePod;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotCalculator;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotData;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  追单
 */
public class ComposeChaseEngine {
    /**
     *  正在追单的Pod
     */
    private ComposePod mChasePod;

    /**
     *  关注合约腿，合约ID为值
     */
    private Set<Long> mWatchLegs = new HashSet<>();

    /**
     * 追单的底线
     */
    private Map<Long, Double> mTopChasePrices = new HashMap<>();

    public ComposeChaseEngine(ComposePod chasePod) {
        this.mChasePod = chasePod;
    }

    public Double getTopChasePrice(long sledContractId) {
        return mTopChasePrices.get(sledContractId);
    }

    public Map<Long, Double> getTopChasePrices() {
        return mTopChasePrices;
    }

    public void setTopChasePrice(long sledContractId, double topChasePrice) {
        mTopChasePrices.put(sledContractId, topChasePrice);
    }

    public ComposeQuotCalculator getQuotCalculator() {
        return mChasePod.getQuotCalculator();
    }

    public HostingExecOrderTradeDirection getOrderTradeDirection(HostingComposeLeg composeLeg) {
        return mChasePod.getOrderTradeDirection(composeLeg);
    }

    public void addWatchLeg(long sledContractId) {
        mWatchLegs.add(sledContractId);
    }

    public Set<Long> getWatchLegs() {
        return mWatchLegs;
    }

    public void startChaseOrder(XQOrderSubExecutor subExecutor) throws ErrorInfo {
        int restVolume = mChasePod.getRestVolume(subExecutor);
        if (restVolume <= 0) {
            return ;
        }

        addWatchLeg(subExecutor.getSubSledContractId());

        ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());

        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            if (quotData.getAskPrice1() == null) {
                return ;
            }

            mChasePod.lanuchOrder(subExecutor
                    , orderTradeDirection
                    , calChaseOrderPrice(subExecutor, quotData.getComposeLeg(), quotData.getAskPrice1())
                    , restVolume);
            mTopChasePrices.put(subExecutor.getSubSledContractId()
                    , calTopChasePrice(quotData.getComposeLeg(), quotData.getAskPrice1()));
        } else {
            if (quotData.getBidPrice1() == null) {
                return ;
            }

            mChasePod.lanuchOrder(subExecutor
                    , orderTradeDirection
                    , calChaseOrderPrice(subExecutor, quotData.getComposeLeg(), quotData.getBidPrice1())
                    , restVolume);

            mTopChasePrices.put(subExecutor.getSubSledContractId()
                    , calTopChasePrice(quotData.getComposeLeg(), quotData.getBidPrice1()));
        }


    }

    public double calChaseOrderPrice(XQOrderSubExecutor subExecutor
            , HostingComposeLeg composeLeg
            , double quotationPrice) {
        HostingXQComposeLimitOrderLegChaseParam legChaseParam
                = mChasePod.getLegChaseParam(composeLeg.getSledContractId());
        if (legChaseParam == null) {
            return quotationPrice;
        }
        if (!legChaseParam.isSetTicks() || legChaseParam.getTicks() <= 0) {
            return quotationPrice;
        }

        BigDecimal decimalQuotationPrice = new BigDecimal(String.valueOf(quotationPrice));

        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(composeLeg);
        SledContractDetails contractDetails = subExecutor.getOrderMarketer().getContractDetails();

        BigDecimal ticksValue
                = new BigDecimal(String.valueOf(contractDetails.getSledCommodity().getTickSize()))
                .multiply(new BigDecimal(legChaseParam.getTicks()));

        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            return decimalQuotationPrice.add(ticksValue).doubleValue();
        }

        return decimalQuotationPrice.subtract(ticksValue).doubleValue();
    }


    public double calTopChasePrice(HostingComposeLeg composeLeg, double startPrice) {
        HostingXQComposeLimitOrderLegChaseParam legChaseParam = mChasePod.getLegChaseParam(composeLeg.getSledContractId());
        if (legChaseParam == null) {
            AppLog.w("xqOrderId=" + mChasePod.getOrder().getOrderId()
                    + ", composeGraphId=" + mChasePod.getComposeGraph().getComposeGraphId()
                    + ", can not get legChaseParam for sledContractId="
                    + composeLeg.getSledContractId());

            // 没有追价参数，则不进行追价
            return startPrice;
        }

        if (!legChaseParam.isSetProtectPriceRatio() || legChaseParam.getProtectPriceRatio() < 0.0) {
            AppLog.w("xqOrderId=" + mChasePod.getOrder().getOrderId()
                    + ", composeGraphId=" + mChasePod.getComposeGraph().getComposeGraphId()
                    + ", legChaseParam's protectedPriceRatio not set or illegal for sledContractId="
                    + composeLeg.getSledContractId());
            return startPrice;
        }

        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(composeLeg);
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            return startPrice * (1 + legChaseParam.getProtectPriceRatio());
        }
        return startPrice * (1 - legChaseParam.getProtectPriceRatio());
    }


    /**
     * 尝试对某条腿进行追单
     */
    public void tryChaseLeg(XQOrderSubExecutor subExecutor, int restVolume) throws ErrorInfo {
        ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
        if (quotData == null) {
            return;
        }

        if (!mWatchLegs.contains(subExecutor.getSubSledContractId())) {
            AppLog.w("tryChaseLeg called, " + mChasePod.description()
                     + ", sledContractId=" + subExecutor.getSubSledContractId()
                     + ", but is not contains in WatchLegs");
            return ;
        }

        HandicapManager.Global().requireOpLock(mChasePod
                , new HashSet<>(Arrays.asList(subExecutor.getSubSledContractId())));
        try {
            doTryChaseLeg(subExecutor, restVolume, quotData);
        } finally {
            HandicapManager.Global().releaseOpLock(mChasePod);
        }
    }

    private void doTryChaseLeg(XQOrderSubExecutor subExecutor
            , int restVolume
            , ComposeQuotData quotData) throws ErrorInfo {
        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());

        Double topChasePrice = getTopChasePrice(subExecutor.getSubSledContractId());
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            if (quotData.getAskPrice1() == null) {
                return;
            }

            double orderPrice = calChaseOrderPrice(subExecutor, quotData.getComposeLeg(), quotData.getAskPrice1());
            if (AppLog.infoEnabled()) {
                AppLog.i("doTryChaseLeg " + mChasePod.description()
                        + ", orderTradeDirection=" + orderTradeDirection
                        + ", composeLeg=" + quotData.getComposeLeg()
                        + ", orderPrice=" + orderPrice
                        + ", topChasePrice=" + topChasePrice);
            }

            if (topChasePrice != null) {
                if (topChasePrice.compareTo(orderPrice) < 0) {
                    // 追价保护的买价低于订单的价格，触发追价保护
                    mChasePod.errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_PRICE_PROTECTED.getValue());
                    return ;
                }
            }

            mChasePod.lanuchOrder(subExecutor, orderTradeDirection, orderPrice, restVolume);

            if (topChasePrice == null) {
                mTopChasePrices.put(subExecutor.getSubSledContractId()
                        , calTopChasePrice(quotData.getComposeLeg(), quotData.getAskPrice1()));
            }

        } else {
            if (quotData.getBidPrice1() == null) {
                return;
            }

            double orderPrice = calChaseOrderPrice(subExecutor, quotData.getComposeLeg(), quotData.getBidPrice1());
            if (AppLog.infoEnabled()) {
                AppLog.i("doTryChaseLeg xqOrderId=" + mChasePod.description()
                        + ", orderTradeDirection=" + orderTradeDirection
                        + ", composeLeg=" + quotData.getComposeLeg()
                        + ", orderPrice=" + orderPrice
                        + ", topChasePrice=" + topChasePrice);
            }

            if (topChasePrice != null) {
                if (topChasePrice.compareTo(orderPrice) > 0) {
                    // 追价保护的卖价低于订单的价格，触发追价保护
                    mChasePod.errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_PRICE_PROTECTED.getValue());
                    return ;
                }
            }

            mChasePod.lanuchOrder(subExecutor, orderTradeDirection, orderPrice, restVolume);

            if (topChasePrice == null) {
                mTopChasePrices.put(subExecutor.getSubSledContractId()
                        , calTopChasePrice(quotData.getComposeLeg(), quotData.getBidPrice1()));
            }
        }

        HandicapManager.Global().set(mChasePod, subExecutor.getSubSledContractId(), orderTradeDirection, restVolume);
    }

    /**
     * 根据行情检查追单
     */
    public void checkChase(QuotationItem updatedQuotationItem) throws ErrorInfo {
        ComposeQuotData quotData = getQuotCalculator().getQuotData(updatedQuotationItem.getContractSymbol());
        if (quotData == null) {
            AppLog.w("checkChase called, " + mChasePod.description()
                    + ", but can not get quotData for contractSymbol="
                    + updatedQuotationItem.getContractSymbol());
            return ;
        }

        if (!mWatchLegs.contains(quotData.getComposeLeg().getSledContractId())) {
            return ;
        }

        XQOrderSubExecutor subExecutor = mChasePod.getExecutor()
                .getSubExecutors()
                .get(quotData.getComposeLeg().getSledContractId());
        Preconditions.checkNotNull(subExecutor);
        int restVolume = mChasePod.getRestVolume(subExecutor);
        if (restVolume <= 0) {
            return;
        }

        // 对于某条需要追价的腿，但是没有订单在跑
        if (!subExecutor.hasOrderRunning()) {
            tryChaseLeg(subExecutor, restVolume);
            return ;
        }

        // 正在撤销订单
        if (subExecutor.getOrderMarketer().isCancelling()) {
            return ;
        }

        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());
        Double currentPrice
                = subExecutor.getOrderMarketer().getLastestOrder().getOrderDetail().getLimitPrice();
        // 比较当前的订单价格和行情价格
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            if (quotData.getAskPrice1() == null) {
                return ;
            }

            if (AppLog.infoEnabled()) {
                AppLog.i("checkChase " + mChasePod.description()
                        + ", orderTradeDirection=" + orderTradeDirection
                        + ", composeLeg=" + quotData.getComposeLeg()
                        + ", currentPrice=" + currentPrice
                        + ", askPrice1=" + quotData.getAskPrice1());
            }

            if (currentPrice.compareTo(quotData.getAskPrice1()) < 0) {
                subExecutor.cancelOrderRunning();
            }

        } else {
            if (quotData.getBidPrice1() == null) {
                return ;
            }

            if (AppLog.infoEnabled()) {
                AppLog.i("checkChase xqOrderId=" + mChasePod.description()
                        + ", orderTradeDirection=" + orderTradeDirection
                        + ", composeLeg=" + quotData.getComposeLeg()
                        + ", currentPrice=" + currentPrice
                        + ", bidPrice1=" + quotData.getBidPrice1());
            }
            if (currentPrice.compareTo(quotData.getBidPrice1()) > 0) {
                subExecutor.cancelOrderRunning();
            }
        }
    }
}
