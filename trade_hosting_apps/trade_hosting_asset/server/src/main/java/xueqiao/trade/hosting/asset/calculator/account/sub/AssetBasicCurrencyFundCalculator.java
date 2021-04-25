package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.event.handler.SledCurrency;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AssetBasicCurrencyFundCalculator extends AssetBaseCalculator<Void> {
    public AssetBasicCurrencyFundCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_BASIC_CURRENCY_FUND_KEY;
    }

    @Override
    public void onCalculate(Void aVoid) throws Exception {

        SledCurrency sledCurrency = new SledCurrency();
        ExchangeRateHistoryPage page = sledCurrency.getSledExchangeRate();
        if (page == null) {
            return;
        }
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(mAccountId);
        Map<String, HostingFund> fundMap = globalData.getHostingFundMap();
        for (ExchangeRateHistory history : page.getPage()) {

            HostingFund basicCurrencyFund = new HostingFund();
            String basicCurrency = history.getCurrencyCode();
            basicCurrencyFund.setCurrency(basicCurrency);
            basicCurrencyFund.setSubAccountId(mAccountId);

            BigDecimal preFund = BigDecimal.ZERO;
            BigDecimal depositAmount = BigDecimal.ZERO;
            BigDecimal withdrawAmount = BigDecimal.ZERO;
            BigDecimal closeProfit = BigDecimal.ZERO;
            BigDecimal positionProfit = BigDecimal.ZERO;
            BigDecimal useMargin = BigDecimal.ZERO;
            BigDecimal frozenMargin = BigDecimal.ZERO;
            BigDecimal useCommission = BigDecimal.ZERO;
            BigDecimal frozenCommission = BigDecimal.ZERO;
            BigDecimal availableFund = BigDecimal.ZERO;
            BigDecimal dynamicBenefit = BigDecimal.ZERO;
            BigDecimal creditAmount = BigDecimal.ZERO;
            BigDecimal goodsValue = BigDecimal.ZERO;

            Map<String, BigDecimal> currencyGoods = new HashMap<>();

            for (String currency : fundMap.keySet()) {

                HostingFund currencyFund = fundMap.get(currency);
                if (currencyFund == null) {
                    continue;
                }

                BigDecimal currencyRate = getCurrencyRate(history, basicCurrency, currency);
                if (currencyRate == null) {
                    continue;
                }

                preFund = preFund.add(doubleToBigDecimal(currencyFund.preFund).multiply(currencyRate));
                depositAmount = depositAmount.add(doubleToBigDecimal(currencyFund.depositAmount).multiply(currencyRate));
                withdrawAmount = withdrawAmount.add(doubleToBigDecimal(currencyFund.withdrawAmount).multiply(currencyRate));
                closeProfit = closeProfit.add(doubleToBigDecimal(currencyFund.closeProfit).multiply(currencyRate));
                positionProfit = positionProfit.add(doubleToBigDecimal(currencyFund.positionProfit).multiply(currencyRate));
                useMargin = useMargin.add(doubleToBigDecimal(currencyFund.useMargin).multiply(currencyRate));
                frozenMargin = frozenMargin.add(doubleToBigDecimal(currencyFund.frozenMargin).multiply(currencyRate));
                useCommission = useCommission.add(doubleToBigDecimal(currencyFund.useCommission).multiply(currencyRate));
                frozenCommission = frozenCommission.add(doubleToBigDecimal(currencyFund.frozenCommission).multiply(currencyRate));
                availableFund = availableFund.add(doubleToBigDecimal(currencyFund.availableFund).multiply(currencyRate));
                dynamicBenefit = dynamicBenefit.add(doubleToBigDecimal(currencyFund.dynamicBenefit).multiply(currencyRate));
                creditAmount = creditAmount.add(doubleToBigDecimal(currencyFund.creditAmount).multiply(currencyRate));

                goodsValue = goodsValue.add(doubleToBigDecimal(currencyFund.goodsValue).multiply(currencyRate));

                currencyGoods.put(currency, doubleToBigDecimal(currencyFund.goodsValue).multiply(currencyRate));
            }

            BigDecimal riskRate = BigDecimal.ZERO;
            if (useMargin.doubleValue() > 0.0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                riskRate = useMargin.divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100"));
            }

            BigDecimal leverage = BigDecimal.ZERO;
            if (goodsValue.doubleValue() > 0.0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                leverage = goodsValue.divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN);
            }
            basicCurrencyFund.setLeverage(leverage.doubleValue());
            basicCurrencyFund.setGoodsValue(goodsValue.doubleValue());

            basicCurrencyFund.setPreFund(preFund.doubleValue());
            basicCurrencyFund.setDepositAmount(depositAmount.doubleValue());
            basicCurrencyFund.setWithdrawAmount(withdrawAmount.doubleValue());
            basicCurrencyFund.setCloseProfit(closeProfit.doubleValue());
            basicCurrencyFund.setPositionProfit(positionProfit.doubleValue());
            basicCurrencyFund.setUseMargin(useMargin.doubleValue());
            basicCurrencyFund.setFrozenMargin(frozenMargin.doubleValue());
            basicCurrencyFund.setUseCommission(useCommission.doubleValue());
            basicCurrencyFund.setFrozenCommission(frozenCommission.doubleValue());
            basicCurrencyFund.setAvailableFund(availableFund.doubleValue());
            basicCurrencyFund.setDynamicBenefit(dynamicBenefit.doubleValue());
            basicCurrencyFund.setRiskRate(riskRate.doubleValue());
            basicCurrencyFund.setCreditAmount(creditAmount.doubleValue());

            globalData.getBasicCurrencyFundMap().put(basicCurrency, basicCurrencyFund);
            sendFundMsg(basicCurrencyFund, true);

            // 更新所有货币的杠杆
            for (String currency : currencyGoods.keySet()) {
                BigDecimal goods = currencyGoods.get(currency);
                HostingFund currencyFund = fundMap.get(currency);
                if (currencyFund == null) {
                    continue;
                }

                BigDecimal currencyLeverage = BigDecimal.ZERO;
                if (goods.doubleValue() > 0.0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                    currencyLeverage = goods.divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN);
                }

                currencyFund.setLeverage(currencyLeverage.doubleValue());
            }
        }
    }
}
