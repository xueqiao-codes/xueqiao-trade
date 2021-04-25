package xueqiao.trade.hosting.quot.comb.thriftapi.helper;

import xueqiao.trade.hosting.quot.comb.thriftapi.TradeHostingQuotCombVariable;
import xueqiao.trade.hosting.quot.comb.thriftapi.client.TradeHostingQuotCombStub;

import java.io.File;

public class QuotCombStubFactory {
    private static File QUOTCOMB_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingQuotCombVariable.serviceKey + ".sock");

    public static TradeHostingQuotCombStub getStub() {
        TradeHostingQuotCombStub stub = new TradeHostingQuotCombStub();
        stub.setSocketFile(QUOTCOMB_STUB_SOCKET_FILE);
        return stub;
    }
}
