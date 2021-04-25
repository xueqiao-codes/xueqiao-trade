package xueqiao.trade.hosting.utils.currency;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.watcher.file.FileWatcherModule;
import org.soldier.watcher.file.IFileWatcherListener;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  汇率管理器, 其他货币换算为基础货币的类
 */
public class RateManager implements IFileWatcherListener {
    private String mBaseCurrencyNo;
    private File mExchangeRateFile;
    private ConcurrentHashMap<String, Double> mExchangeRateMap = new ConcurrentHashMap<>();

    public RateManager(String baseCurrencyNo) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(baseCurrencyNo));
        mBaseCurrencyNo = baseCurrencyNo;
        mExchangeRateFile = new File("/data/configs/qconf/xueqiao/currency/" + baseCurrencyNo
                + "/exchange_rate");

        FileWatcherModule.Instance().addWatchFile(mExchangeRateFile, this);
        loadData();
    }

    public String getBaseCurrencyNo() {
        return mBaseCurrencyNo;
    }

    private synchronized void loadData() {
        AppLog.i("loadData from " + mExchangeRateFile.getAbsolutePath()
                + " for baseCurrencyNo=" + mBaseCurrencyNo);

        int retryCount = 3;
        String content = null;
        while((retryCount--) >= 0) {
            try {
                content = FileUtils.readFileToString(mExchangeRateFile, "UTF-8");
                break;
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }

        if (StringUtils.isEmpty(content)) {
            AppLog.e("Failed to read content for " + mExchangeRateFile.getAbsolutePath());
            return ;
        }

        try {
            ExchangeRateHistoryPage rateHistoryPage
                    = ThriftExtJsonUtils.fromJsonTBase(content, ExchangeRateHistoryPage.class);
            if (rateHistoryPage.getPageSize() <= 0) {
                AppLog.e("rateHistoryPage is empty for " + mExchangeRateFile.getAbsolutePath());
                return ;
            }

            for (ExchangeRateHistory rateHistory : rateHistoryPage.getPage()) {
                AppLog.i("scan " + rateHistory);
                if (!"CNY".equalsIgnoreCase(rateHistory.getCurrencyCode())) {
                    continue;
                }
                if (rateHistory.getExchangeRateSize() <= 0) {
                    AppLog.e("rateHistory for CNY, exchangeRateSize is 0");
                    break;
                }

                for (Map.Entry<String, Double> e : rateHistory.getExchangeRate().entrySet()) {
                    mExchangeRateMap.put(e.getKey().toUpperCase(), e.getValue());
                    AppLog.i("put " + e.getKey().toUpperCase()+ " to "
                            + mBaseCurrencyNo + " exchangeRate=" + e.getValue());
                }

                break;
            }

        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }

    }

    public Double getRate(String currenyNo) {
        if (StringUtils.isEmpty(currenyNo)) {
            return null;
        }
        return mExchangeRateMap.get(currenyNo.toUpperCase());
    }

    @Override
    public void onHandleFileChanged(File file) {
        loadData();
    }
}
