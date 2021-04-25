package xueqiao.trade.hosting.position.statis.core.cache.quotation;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatQuoCombCache {
    public long composeGraphId;
    public StatQuotationCache combItem;
    public List<StatQuotationCache> legItems;

    public long getComposeGraphId() {
        return composeGraphId;
    }

    public void setComposeGraphId(long composeGraphId) {
        this.composeGraphId = composeGraphId;
    }

    public StatQuotationCache getCombItem() {
        return combItem;
    }

    public void setCombItem(StatQuotationCache combItem) {
        this.combItem = combItem;
    }

    public List<StatQuotationCache> getLegItems() {
        return legItems;
    }

    public void setLegItems(List<StatQuotationCache> legItems) {
        this.legItems = legItems;
    }

    public void addToLegItems(StatQuotationCache quotationCache) {
        if (legItems == null) {
            legItems = new ArrayList<>();
        }
        legItems.add(quotationCache);
    }

    public static StatQuoCombCache getStatQuoCombCache(HostingQuotationComb hostingQuotationComb) {
        StatQuoCombCache statQuoCombCache = new StatQuoCombCache();
        statQuoCombCache.setComposeGraphId(hostingQuotationComb.getComposeGraphId());
        statQuoCombCache.setCombItem(StatQuotationCache.getStatQuotationCache(hostingQuotationComb.getCombItem()));

        Iterator<QuotationItem> quotationItemIterator = hostingQuotationComb.getLegItemsIterator();
        while (quotationItemIterator.hasNext()) {
            statQuoCombCache.addToLegItems(StatQuotationCache.getStatQuotationCache(quotationItemIterator.next()));
        }
        return statQuoCombCache;
    }
}
