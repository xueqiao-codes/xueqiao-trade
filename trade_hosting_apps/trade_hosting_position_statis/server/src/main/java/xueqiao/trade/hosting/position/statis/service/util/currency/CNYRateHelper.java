package xueqiao.trade.hosting.position.statis.service.util.currency;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.ClosedProfit;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.utils.currency.RateManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CNYRateHelper {
    private static CNYRateHelper sInstance;
    private static String BASE_CURRENCY = "CNY";

    public static CNYRateHelper getInstance() {
        if (sInstance == null) {
            synchronized (CNYRateHelper.class) {
                if (sInstance == null) {
                    sInstance = new CNYRateHelper();
                }
            }
        }
        return sInstance;
    }

    private RateManager rateManager;

    private CNYRateHelper() {
        rateManager = new RateManager(BASE_CURRENCY);
    }

    /**
     * 获取基币币种
     * */
    public String getBaseCurrency() {
        return BASE_CURRENCY;
    }

    /**
     * 获取基币汇率
     */
    public Double getBaseCurrencyExchangeRate(String currency) {
        if (BASE_CURRENCY.equals(currency)) {
            return new Double(1);
        }
        return rateManager.getRate(currency);
    }

    /**
     * 汇率计算
     *
     * @param from          源币种
     * @param to            目标币种
     * @param currencyValue 源币种下的币值
     * @return 目标币种下的币值
     */
    public double exchangeRate(String from, String to, Double currencyValue) throws ErrorInfo {
        if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
            AppReport.reportErr("CNYRateHelper # exchangeRate ---- from : " + from + ", to : " + to);
            throw StatErrorCode.errorCurrencyTypeInvalid;
        }
        if (from.equals(to)) {
            return currencyValue;
        }

        Double fromRate = null;
        Double toRate = null;
        BigDecimal cnyCurrencyValue;
        BigDecimal targetValue;
        if (BASE_CURRENCY.equals(from)) {
            cnyCurrencyValue = new BigDecimal(currencyValue.toString());
        } else {
            fromRate = rateManager.getRate(from);
            if (fromRate == null) {
                throw StatErrorCode.errorExchangeRateNotFound;
            }
            cnyCurrencyValue = new BigDecimal(currencyValue.toString());
            cnyCurrencyValue = cnyCurrencyValue.multiply(new BigDecimal(fromRate.toString()));
        }

        if (BASE_CURRENCY.equals(to)) {
            return cnyCurrencyValue.doubleValue();
        } else {
            toRate = rateManager.getRate(to);
            if (toRate == null) {
                throw StatErrorCode.errorExchangeRateNotFound;
            }
            targetValue = cnyCurrencyValue.divide(new BigDecimal(toRate.toString()), 16, BigDecimal.ROUND_HALF_UP);
            return targetValue.doubleValue();
        }
    }

    public BigDecimal exchangeRate1(String from, String to, final BigDecimal currencyValue) throws ErrorInfo {
        if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
            AppReport.reportErr("CNYRateHelper # exchangeRate1 ---- from : " + from + ", to : " + to);
            throw StatErrorCode.errorCurrencyTypeInvalid;
        }
        if (from.equals(to)) {
            return currencyValue;
        }

        Double fromRate = null;
        Double toRate = null;
        BigDecimal cnyCurrencyValue;
        BigDecimal targetValue;
        if (BASE_CURRENCY.equals(from)) {
            cnyCurrencyValue = currencyValue;
        } else {
            fromRate = rateManager.getRate(from);
            if (fromRate == null) {
                throw StatErrorCode.errorExchangeRateNotFound;
            }
            cnyCurrencyValue = currencyValue;
            cnyCurrencyValue = cnyCurrencyValue.multiply(new BigDecimal(fromRate.toString()));
        }

        if (BASE_CURRENCY.equals(to)) {
            return cnyCurrencyValue;
        } else {
            toRate = rateManager.getRate(to);
            if (toRate == null) {
                throw StatErrorCode.errorExchangeRateNotFound;
            }
            targetValue = cnyCurrencyValue.divide(new BigDecimal(toRate.toString()), 16, BigDecimal.ROUND_HALF_UP);
            return targetValue;
        }
    }

    /**
     * 计算目标币种下各币种的总收益
     *
     * @param targetCurrency 输出的种币
     * @param valueMap       各币种的收益
     * @return 目标币种下各币种的总收益
     */
    public double calculateTotalValue(String targetCurrency, Map<String, Double> valueMap) throws ErrorInfo {
        double targetValue = 0.0;
        Double currencyValue = null;
        for (String currency : valueMap.keySet()) {
            currencyValue = valueMap.get(currency);
            targetValue += exchangeRate(currency, targetCurrency, currencyValue);
        }
        return targetValue;
    }

    /**
     * 计算目标币种下各币种的总收益
     *
     * @param valueList 币种及收益列表
     * @return 目标币种下各币种的总收益
     */
    public List<ClosedProfit> calculateTotalValue(List<ClosedProfit> valueList) throws ErrorInfo {
        List<ClosedProfit> closedProfitList = new ArrayList<>();
        for (ClosedProfit closedProfit : valueList) {
            ClosedProfit closedTotalProfit = new ClosedProfit();
            closedTotalProfit.setTradeCurrency(closedProfit.getTradeCurrency());
            closedTotalProfit.setClosedProfitValue(calculateTotalValue(closedProfit.getTradeCurrency(), valueList));
            closedProfitList.add(closedTotalProfit);
        }
        return closedProfitList;
    }

    private double calculateTotalValue(String targetCurrency, List<ClosedProfit> valueList) throws ErrorInfo {
        double targetValue = 0.0;
        for (ClosedProfit closedProfit : valueList) {
            targetValue += exchangeRate(closedProfit.getTradeCurrency(), targetCurrency, closedProfit.getClosedProfitValue());
        }
        return targetValue;
    }
}
