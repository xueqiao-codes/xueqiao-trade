package xueqiao.trade.hosting.arbitrage.core.data;

import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;

import java.util.HashMap;
import java.util.Map;

public class XQTradeWithRelatedItem {
    private HostingXQTrade mTrade;
    
    // 以execTradeLegId为Key
    private Map<Long, XQTradeRelatedItem> mRelatedItems = new HashMap<Long, XQTradeRelatedItem>();
    
    public HostingXQTrade getTrade() {
        return mTrade;
    }
    
    public void setTrade(HostingXQTrade trade) {
        mTrade = trade;
    }
    
    public void addRelatedItem(XQTradeRelatedItem relatedItem) {
        mRelatedItems.put(relatedItem.getExecTradeLegId(), relatedItem);
    }
    
    public Map<Long, XQTradeRelatedItem> getRelatedItems() {
        return mRelatedItems;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(128);
        resultBuilder.append("XQTradeWithRelatedItem{");
        resultBuilder.append("mTrade=").append(mTrade);
        resultBuilder.append(", mRelatedItems={");
        for (Map.Entry<Long, XQTradeRelatedItem> relatedItemEntry : mRelatedItems.entrySet()) {
            resultBuilder.append(relatedItemEntry.getKey())
                         .append("->")
                         .append(relatedItemEntry.getValue())
                         .append("; ");
        }
        resultBuilder.append("}}");
        return resultBuilder.toString();
    }
}