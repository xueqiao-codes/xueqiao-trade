package xueqiao.trade.hosting.asset.event.handler;

import com.longsheng.xueqiao.currency.thriftapi.Currency;
import com.longsheng.xueqiao.currency.thriftapi.CurrencyPage;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

import java.util.ArrayList;
import java.util.List;

public class SledCurrency {

    private final static String CURRENCY_PATH = "/xueqiao/currency";
    private final static String SLED_EXCHANGE_RATE_NODE = "/exchange_rate";
    private final static String SLED_CURRENCY_NODE = CURRENCY_PATH + "/sled_currency";

    public List<String> getSledCurrency() throws QconfException {

        CurrencyPage page = getCurrencyPage();
        List<String> currencys = new ArrayList<>();
        if (page != null) {
            for (Currency currency : page.getPage()) {
                currencys.add(currency.getCurrencyCode());
            }
        }
        return currencys;
    }

    private CurrencyPage getCurrencyPage() throws QconfException {
        String currencyData = Qconf.getConf(SLED_CURRENCY_NODE).trim();
        return ProtocolUtil.unSerialize(JsonFactory.getInstance().getFactory(), currencyData.getBytes(), CurrencyPage.class);
    }

    public ExchangeRateHistoryPage getSledExchangeRate() throws QconfException {
        ExchangeRateHistoryPage page = new ExchangeRateHistoryPage();
        page.setPage(new ArrayList<>());
        CurrencyPage currencyPage = getCurrencyPage();
        int total = 0;
        for (Currency currency : currencyPage.getPage()) {
            if (currency.isIsBaseCurrency()) {
                String exchangeRateHistoryData = Qconf.getConf(CURRENCY_PATH + "/" + currency.getCurrencyCode() + SLED_EXCHANGE_RATE_NODE).trim();
                ExchangeRateHistoryPage historyPage = ProtocolUtil.unSerialize(JsonFactory.getInstance().getFactory(), exchangeRateHistoryData.getBytes(), ExchangeRateHistoryPage.class);
                page.getPage().addAll(historyPage.getPage());
                total += page.getTotal();
            }
        }
        page.setTotal(total);
        return page;
    }
}
