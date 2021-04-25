package xueqiao.trade.hosting.asset.calculator;

import com.antiy.error_code.ErrorCodeOuter;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.account.sub.*;
import xueqiao.trade.hosting.asset.calculator.account.trade.AssetTradeAccountPositionCalculator;

public class AssetCalculatorFactory {

    /* 子账户计算器系列 */
    public final static String SUB_ACCOUNT_FROZEN_FUND_KEY = "SUB_ACCOUNT_FROZEN_FUND";
    public final static String SUB_ACCOUNT_POSITION_FUND_KEY = "SUB_ACCOUNT_POSITION_FUND";
    public final static String SUB_ACCOUNT_FROZEN_POSITION_KEY = "SUB_ACCOUNT_FROZEN_POSITION";
    public final static String SUB_ACCOUNT_FUND_KEY = "SUB_ACCOUNT_FUND";
    public final static String SUB_ACCOUNT_POSITION_KEY = "SUB_ACCOUNT_POSITION";
    public final static String SUB_ACCOUNT_SETTLEMENT_KEY = "SUB_ACCOUNT_SETTLEMENT";
    public final static String SUB_ACCOUNT_INIT_POSITION_KEY = "SUB_ACCOUNT_INIT_POSITION";
    public final static String SUB_ACCOUNT_BASIC_CURRENCY_FUND_KEY = "SUB_ACCOUNT_BASIC_CURRENCY_FUND";
    public final static String QUOTATION_DATA_FILE_KEY = "QUOTATION_DATA_FILE";
    public final static String SUB_ACCOUNT_CALCULATE_CONFIG_KEY = "SUB_ACCOUNT_CALCULATE_CONFIG";
    public final static String SUB_ACCOUNT_SAVE_HOSTING_FUND_KEY = "SUB_ACCOUNT_SAVE_HOSTING_FUND";
    public final static String SUB_ACCOUNT_CLEAR_CACHE_KEY = "SUB_ACCOUNT_CLEAR_CACHE";
    public final static String SUB_ACCOUNT_EXPIRED_POSITION_REMOVE_KEY = "SUB_ACCOUNT_EXPIRED_POSITION_REMOVE";

    /* 资金账户计算器系列 */
    public final static String TRADE_ACCOUNT_POSITION_KEY = "TRADE_ACCOUNT_POSITION";

    private static AssetCalculatorFactory ourInstance;

    public static AssetCalculatorFactory getInstance() {
        if (ourInstance == null) {
            synchronized (AssetCalculatorFactory.class) {
                if (ourInstance == null) {
                    ourInstance = new AssetCalculatorFactory();
                }
            }

        }
        return ourInstance;
    }

    private AssetCalculatorFactory() {
    }

    public AssetBaseCalculator getCalculator(String key, long accountId) throws ErrorInfo {
        if (SUB_ACCOUNT_FROZEN_FUND_KEY.equals(key)) {
            return new AssetFrozenFundCalculator(accountId);
        }
        if (SUB_ACCOUNT_FROZEN_POSITION_KEY.equals(key)) {
            return new AssetFrozenPositionCalculator(accountId);
        }
        if (SUB_ACCOUNT_POSITION_KEY.equals(key)) {
            return new AssetPositionCalculator(accountId);
        }
        if (SUB_ACCOUNT_POSITION_FUND_KEY.equals(key)) {
            return new AssetPositionFundCalculator(accountId);
        }
        if (SUB_ACCOUNT_FUND_KEY.equals(key)) {
            return new AssetFundCalculator(accountId);
        }
        if (SUB_ACCOUNT_SETTLEMENT_KEY.equals(key)) {
            return new AssetSettlementCalculator(accountId);
        }
        if (SUB_ACCOUNT_INIT_POSITION_KEY.equals(key)) {
            return new AssetInitPositionCalculator(accountId);
        }
        if (SUB_ACCOUNT_BASIC_CURRENCY_FUND_KEY.equals(key)) {
            return new AssetBasicCurrencyFundCalculator(accountId);
        }
        if (QUOTATION_DATA_FILE_KEY.equals(key)) {
            return new AssetDataFileCalculator(accountId);
        }
        if (TRADE_ACCOUNT_POSITION_KEY.equals(key)) {
            return new AssetTradeAccountPositionCalculator(accountId);
        }
        if (SUB_ACCOUNT_CALCULATE_CONFIG_KEY.equals(key)) {
            return new AssetCalculateConfigCalculator(accountId);
        }
        if (SUB_ACCOUNT_SAVE_HOSTING_FUND_KEY.equals(key)) {
            return new AssetSaveHostingFundCalculator(accountId);
        }
        if (SUB_ACCOUNT_CLEAR_CACHE_KEY.equals(key)) {
            return new AssetClearCacheCalculator(accountId);
        }
        if (SUB_ACCOUNT_EXPIRED_POSITION_REMOVE_KEY.equals(key)){
            return new AssetExpiredPositionRemoveCalculator(accountId);
        }
        throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "Asset calculator not found: " + key);
    }


}
