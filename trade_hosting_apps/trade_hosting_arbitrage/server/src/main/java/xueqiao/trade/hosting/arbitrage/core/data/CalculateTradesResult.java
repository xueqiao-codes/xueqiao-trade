package xueqiao.trade.hosting.arbitrage.core.data;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;

import java.util.ArrayList;
import java.util.List;

public class CalculateTradesResult {
    private HostingXQTradeSummary orderTradeSummary;
    
    private List<XQTradeWithRelatedItem> tradeWithRelatedItemsList = new ArrayList<XQTradeWithRelatedItem>();

    public List<XQTradeWithRelatedItem> getTradeWithRelatedItemsList() {
        return tradeWithRelatedItemsList;
    }

    public void setTradeWithRelatedItemsList(List<XQTradeWithRelatedItem> tradeWithRelatedItemsList) {
        this.tradeWithRelatedItemsList = tradeWithRelatedItemsList;
    }

    public HostingXQTradeSummary getOrderTradeSummary() {
        return orderTradeSummary;
    }

    public void setOrderTradeSummary(HostingXQTradeSummary orderTradeSummary) {
        this.orderTradeSummary = orderTradeSummary;
    }
    
}
