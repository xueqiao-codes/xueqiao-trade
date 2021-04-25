package xueqiao.trade.hosting.upside.entry.helper;

import com.google.common.base.Preconditions;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.upside.entry.client.TradeHostingUpsideEntryStub;

import java.io.File;

public class UpsideEntryStubBuilder {
	public static TradeHostingUpsideEntryStub fromOrder(HostingExecOrder order) {
        Preconditions.checkArgument(order.isSetAccountSummary());
        Preconditions.checkArgument(order.getAccountSummary().isSetTradeAccountId()
                && order.getAccountSummary().getTradeAccountId() > 0);
        
        return fromTradeAccountId(order.getAccountSummary().getTradeAccountId());
    }
	
	public static TradeHostingUpsideEntryStub fromTradeAccountId(long tradeAccountId) {
		 TradeHostingUpsideEntryStub stub = new TradeHostingUpsideEntryStub();
		 StringBuilder localSocketFilePathBuilder = new StringBuilder(64);
		 localSocketFilePathBuilder.append("/data/trade_hosting_upside_entry/run/")
		 						   .append(tradeAccountId)
		 						   .append(".sock");
		 stub.setSocketFile(new File(localSocketFilePathBuilder.toString()));
		 return stub;
	}
}
