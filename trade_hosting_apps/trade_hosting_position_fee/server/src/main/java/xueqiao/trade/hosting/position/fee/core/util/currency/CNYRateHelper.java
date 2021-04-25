package xueqiao.trade.hosting.position.fee.core.util.currency;

import com.antiy.error_code.ErrorCodeInner;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.utils.currency.RateManager;

import java.math.BigDecimal;

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
     */
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
//    public double exchangeRate(String from, String to, Double currencyValue) throws ErrorInfo {
//        if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
//            AppLog.e("CNYRateHelper # exchangeRate ---- from : " + from + ", to : " + to);
//            throw StatErrorCode.errorCurrencyTypeInvalid;
//        }
//        if (from.equals(to)) {
//            return currencyValue;
//        }
//
//        Double fromRate = null;
//        Double toRate = null;
//        BigDecimal cnyCurrencyValue;
//        BigDecimal targetValue;
//        if (BASE_CURRENCY.equals(from)) {
//            cnyCurrencyValue = new BigDecimal(currencyValue.toString());
//        } else {
//            fromRate = rateManager.getRate(from);
//            if (fromRate == null) {
//                throw StatErrorCode.errorExchangeRateNotFound;
//            }
//            cnyCurrencyValue = new BigDecimal(currencyValue.toString());
//            cnyCurrencyValue = cnyCurrencyValue.multiply(new BigDecimal(fromRate.toString()));
//        }
//
//        if (BASE_CURRENCY.equals(to)) {
//            return cnyCurrencyValue.doubleValue();
//        } else {
//            toRate = rateManager.getRate(to);
//            if (toRate == null) {
//                throw StatErrorCode.errorExchangeRateNotFound;
//            }
//            targetValue = cnyCurrencyValue.divide(new BigDecimal(toRate.toString()), 16, BigDecimal.ROUND_HALF_UP);
//            return targetValue.doubleValue();
//        }
//    }
    public BigDecimal exchangeRate(String from, String to, final BigDecimal currencyValue) throws ErrorInfo {
        if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
            AppLog.e("CNYRateHelper # exchangeRate1 ---- from : " + from + ", to : " + to);
            throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), "currency is not defined, from : " + from + ", to : " + to);
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
                AppLog.e("exchangeRate1 ---- currency rate not found in xueqiao,  from : " + from);
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "currency rate not found in xueqiao,  from : " + from);
            }
            cnyCurrencyValue = currencyValue;
            cnyCurrencyValue = cnyCurrencyValue.multiply(new BigDecimal(fromRate.toString()));
        }

        if (BASE_CURRENCY.equals(to)) {
            return cnyCurrencyValue;
        } else {
            toRate = rateManager.getRate(to);
            if (toRate == null) {
                AppLog.e("exchangeRate1 ---- currency rate not found in xueqiao,  to : " + to);
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "currency rate not found in xueqiao,  to : " + to);
            }
            targetValue = cnyCurrencyValue.divide(new BigDecimal(toRate.toString()), 16, BigDecimal.ROUND_HALF_UP);
            return targetValue;
        }
    }

    public boolean isCurrencyExistInXueqiao(String currency) {
        if (BASE_CURRENCY.equals(currency)) {
            return true;
        }
        Double rate = rateManager.getRate(currency);
        if (rate == null) {
            AppLog.e("isCurrencyExistInXueqiao, currency not exist in xueqiao : " + currency);
            return false;
        }
        return true;
    }
}
