package xueqiao.trade.hosting.history.thriftapi.helper;

import xueqiao.trade.hosting.history.thriftapi.TradeHostingHistoryVariable;
import xueqiao.trade.hosting.history.thriftapi.client.TradeHostingHistoryStub;

import java.io.File;

public class HistoryStubFactory {
    private static File HISTORY_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingHistoryVariable.serviceKey + ".sock");

    public static TradeHostingHistoryStub getStub() {
        TradeHostingHistoryStub stub = new TradeHostingHistoryStub();
        stub.setSocketFile(HISTORY_STUB_SOCKET_FILE);
        return stub;
    }
}
