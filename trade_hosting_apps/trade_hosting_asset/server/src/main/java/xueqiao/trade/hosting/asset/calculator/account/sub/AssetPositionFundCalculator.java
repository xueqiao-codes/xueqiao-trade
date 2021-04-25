package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import org.apache.thrift.TException;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.AssetCalculateConfig;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public class AssetPositionFundCalculator extends AssetBaseCalculator<PositionFundCalculateData> {

    public AssetPositionFundCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY;
    }

    @Override
    public void onCalculate(PositionFundCalculateData data) throws Exception {
        refreshHostingPosition(data.getSledContractId(), data.getCalculatePrice());
    }

    private void refreshHostingPosition(long sledContractId, BigDecimal price) throws TException, UnsupportedEncodingException {
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(this.mAccountId);
        HostingSledContractPosition position = globalData.getHostingSledContractPosition(sledContractId);
        if (position != null) {
            AssetCalculateConfig calculateConfig = globalData.getAssetCalculateConfig(sledContractId);
            double contractSize = calculateConfig.getContractSize();
            double chargeUnit = calculateConfig.getChargeUnit();
            BigDecimal contractSizePrice = doubleToBigDecimal(contractSize).multiply(doubleToBigDecimal(chargeUnit));
            position.getPositionFund().setCalculatePrice(price.doubleValue());
            BigDecimal deltaPrice = price.subtract(doubleToBigDecimal(position.getPositionVolume().getPositionAvgPrice()));
            BigDecimal positionProfit = deltaPrice.multiply(doubleToBigDecimal(position.getPositionVolume().getNetPosition())).multiply(contractSizePrice);
            position.getPositionFund().setPositionProfit(positionProfit.doubleValue());
            setMargin(position, calculateConfig);
            BigDecimal positionVolume = new BigDecimal(Math.abs(position.getPositionVolume().getNetPosition()));
            BigDecimal goodsValue = price.multiply(contractSizePrice).multiply(positionVolume);


            ExchangeRateHistory history = getExchangeRateHistory();

            if (history != null) {
                BigDecimal currencyRate = getCurrencyRate(history, history.getCurrencyCode(), position.getCurrency());
                position.getPositionFund().setGoodsValue(goodsValue.doubleValue());

                HostingFund hostingFund = globalData.getBasicCurrencyFundMap().get(history.getCurrencyCode());
                if (hostingFund == null) {
                    return;
                }
                BigDecimal dynamicBenefit = doubleToBigDecimal(hostingFund.getDynamicBenefit());
                BigDecimal leverage = BigDecimal.ZERO;
                if (BigDecimal.ZERO.compareTo(goodsValue) != 0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                    leverage = goodsValue.multiply(currencyRate).divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN);
                }
                position.getPositionFund().setLeverage(leverage.doubleValue());
            }

            recycle(contractSizePrice);
            recycle(deltaPrice);
            recycle(positionProfit);
            globalData.getHostingPositionMap().put(sledContractId, position);
            sendPositionMsg(position);
        }
    }
}
