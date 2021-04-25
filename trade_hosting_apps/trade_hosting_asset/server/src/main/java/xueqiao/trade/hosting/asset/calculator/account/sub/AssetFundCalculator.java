package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import org.apache.thrift.TException;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.struct.FrozenPosition;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.struct.PreFund4Calculate;
import xueqiao.trade.hosting.asset.thriftapi.*;

import java.math.BigDecimal;

public class AssetFundCalculator extends AssetBaseCalculator<FundCalculateData> {


    public AssetFundCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_FUND_KEY;
    }

    @Override
    public void onCalculate(FundCalculateData data) throws Exception {
        Preconditions.checkNotNull(data);
        Preconditions.checkArgument(null != data.getCurrency() && !"".equals(data.getCurrency()));
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(mAccountId);
        HostingFund hostingFund = calculateHostingFund(globalData, data.getCurrency());
        if (hostingFund == null) {
            return;
        }

        globalData.getHostingFundMap().put(data.getCurrency(), hostingFund);
        sendFundMsg(hostingFund);

        AssetBaseCalculator assetBaseCalculator = AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_BASIC_CURRENCY_FUND_KEY, mAccountId);
        assetBaseCalculator.onCalculate(null);
    }

    public HostingFund calculateHostingFund(AssetSubAccountGlobalData data, String currency) throws TException {
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(mAccountId);
        PreFund4Calculate preFund4Calculate = data.getPreFund4Calculate(currency);
        BigDecimal preFund = preFund4Calculate.getPreFund();
        BigDecimal depositAmount = preFund4Calculate.getDepositAmount();
        BigDecimal withdrawAmount = preFund4Calculate.getWithdrawAmount();
        BigDecimal creditAmount = preFund4Calculate.getCreditAmount();
        // 根据持仓信息获取
        BigDecimal frozenMargin = BigDecimal.ZERO;
        BigDecimal frozenCommission = BigDecimal.ZERO;
        BigDecimal useMargin = BigDecimal.ZERO;
        BigDecimal useCommission = BigDecimal.ZERO;
        BigDecimal closeProfit = BigDecimal.ZERO;
        BigDecimal positionProfit = BigDecimal.ZERO;
        BigDecimal goodsValue = BigDecimal.ZERO;

        // 根据实时锁仓信息计算冻结手续费和保证金
        for (FrozenPosition frozenPosition : data.getFrozenPositionMap().values()) {
            HostingSledContractPosition hscp = data.getHostingSledContractPosition(frozenPosition.getSledContractId());
            hscp.getPositionFund().setFrozenCommission(frozenPosition.getFrozenCommission().doubleValue());

            BigDecimal totalFrozenMargin;
            BigDecimal sellFrozenMargin = BigDecimal.ZERO;
            BigDecimal buyFrozenMargin = BigDecimal.ZERO;

            if (hscp.getPositionVolume().getNetPosition() >= 0) {
                sellFrozenMargin = frozenPosition.getShortFrozenMargin().subtract(doubleToBigDecimal(hscp.getPositionFund().getUseMargin()));
            } else {
                buyFrozenMargin = frozenPosition.getLongFrozenMargin().subtract(doubleToBigDecimal(hscp.getPositionFund().getUseMargin()));
            }

            if (buyFrozenMargin.compareTo(sellFrozenMargin) >= 0) {
                totalFrozenMargin = buyFrozenMargin;
            } else {
                totalFrozenMargin = sellFrozenMargin;
            }
            hscp.getPositionFund().setFrozenMargin(totalFrozenMargin.doubleValue());
        }

        for (HostingSledContractPosition position : data.getHostingPositionMap().values()) {
            if (contractGlobal.isContractExpired(position.getSledContractId())) {
                continue;
            }
            if (currency.equals(position.getCurrency())) {
                frozenMargin = frozenMargin.add(doubleToBigDecimal(position.getPositionFund().getFrozenMargin()));
                frozenCommission = frozenCommission.add(doubleToBigDecimal(position.getPositionFund().getFrozenCommission()));
                useMargin = useMargin.add(doubleToBigDecimal(position.getPositionFund().getUseMargin()));
                useCommission = useCommission.add(doubleToBigDecimal(position.getPositionVolume().getUseCommission()));
                closeProfit = closeProfit.add(doubleToBigDecimal(position.getPositionVolume().getCloseProfit()));
                positionProfit = positionProfit.add(doubleToBigDecimal(position.getPositionFund().getPositionProfit()));

                goodsValue = goodsValue.add(doubleToBigDecimal(position.getPositionFund().getGoodsValue()));
            }
        }

        BigDecimal dynamicBenefit = preFund.add(closeProfit).add(positionProfit).subtract(useCommission).add(depositAmount).subtract(withdrawAmount);
        BigDecimal availableFund = dynamicBenefit.subtract(useMargin).subtract(frozenMargin).subtract(frozenCommission).add(creditAmount);
        BigDecimal riskRate = BigDecimal.ZERO;
        if (useMargin.doubleValue() > 0.0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
            riskRate = useMargin.divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100"));
        }

        // 过滤掉全是0的资金信息
        if (isZero(dynamicBenefit) && isZero(availableFund) && isZero(frozenCommission) && isZero(useCommission)
                && isZero(frozenMargin) && isZero(useMargin) && isZero(closeProfit) && isZero(positionProfit)
                && isZero(preFund) && isZero(depositAmount) && isZero(withdrawAmount) && isZero(creditAmount)) {
            return null;
        }

        HostingFund hostingFund = new HostingFund();
        hostingFund.setSubAccountId(data.getSubAccountId());
        hostingFund.setAvailableFund(availableFund.doubleValue());
        hostingFund.setCloseProfit(closeProfit.doubleValue());
        hostingFund.setCreditAmount(creditAmount.doubleValue());
        hostingFund.setCurrency(currency);
        hostingFund.setDynamicBenefit(dynamicBenefit.doubleValue());
        hostingFund.setFrozenCommission(frozenCommission.doubleValue());
        hostingFund.setFrozenMargin(frozenMargin.doubleValue());
        hostingFund.setPositionProfit(positionProfit.doubleValue());
        hostingFund.setPreFund(preFund.doubleValue());
        hostingFund.setRiskRate(riskRate.doubleValue());
        hostingFund.setUseCommission(useCommission.doubleValue());
        hostingFund.setUseMargin(useMargin.doubleValue());
        hostingFund.setWithdrawAmount(withdrawAmount.doubleValue());
        hostingFund.setDepositAmount(depositAmount.doubleValue());

        hostingFund.setGoodsValue(goodsValue.doubleValue());

        ExchangeRateHistory history = getExchangeRateHistory();
        BigDecimal currencyRate = BigDecimal.ONE;
        if (history != null) {
            currencyRate = getCurrencyRate(history, history.getCurrencyCode(), currency);
        }

        HostingFund basicHostingFund = data.getBasicCurrencyFundMap().get(history.getCurrencyCode());
        if (basicHostingFund != null) {
            BigDecimal basicDynamicBenefit = doubleToBigDecimal(basicHostingFund.getDynamicBenefit());

            BigDecimal leverage = BigDecimal.ZERO;
            if (goodsValue.doubleValue() > 0.0 && basicDynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                leverage = goodsValue.multiply(currencyRate).divide(basicDynamicBenefit, 4, BigDecimal.ROUND_DOWN);
            }
            hostingFund.setLeverage(leverage.doubleValue());
        }

        recycle(preFund);
        recycle(depositAmount);
        recycle(withdrawAmount);
        recycle(creditAmount);
        recycle(frozenMargin);
        recycle(frozenCommission);
        recycle(useMargin);
        recycle(useCommission);
        recycle(closeProfit);
        recycle(positionProfit);
        recycle(dynamicBenefit);
        recycle(availableFund);
        recycle(riskRate);
        return hostingFund;
    }

    private boolean isZero(BigDecimal bigDecimal) {
        return BigDecimal.ZERO.compareTo(bigDecimal) == 0;
    }
}
