package xueqiao.trade.hosting.arbitrage.quotation;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

public class QuotationItemUtil {
    
    
    public static Double getLastPrice(QuotationItem quotItem) {
        if (!quotItem.isSetLastPrice()) {
            return null;
        }
        return quotItem.getLastPrice();
    }
    
    public static Double getBid1Price(QuotationItem quotItem) {
        if (quotItem.getBidPriceSize() <= 0
                || !PriceUtils.isAppropriatePrice(quotItem.getBidPrice().get(0))) {
            return null;
        }
        return quotItem.getBidPrice().get(0);
    }
    
    public static Double getAsk1Price(QuotationItem quotItem) {
        if (quotItem.getAskPriceSize() <= 0
                || !PriceUtils.isAppropriatePrice(quotItem.getAskPrice().get(0))) {
            return null;
        }
        return quotItem.getAskPrice().get(0);
    }
}
