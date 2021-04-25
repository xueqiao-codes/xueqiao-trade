package xueqiao.trade.hosting.position.fee.core.api;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.upside.entry.TPositionRateDetails;
import xueqiao.trade.hosting.upside.entry.helper.UpsideEntryStubBuilder;

public class UpsideEntryApi {

    public static TPositionRateDetails fromTradeAccountId(long tradeAccountId) throws TException {
        return UpsideEntryStubBuilder.fromTradeAccountId(tradeAccountId).getPositionRateDetails();
    }
}
