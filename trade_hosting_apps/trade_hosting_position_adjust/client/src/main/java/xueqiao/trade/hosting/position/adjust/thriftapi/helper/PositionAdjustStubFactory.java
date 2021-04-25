package xueqiao.trade.hosting.position.adjust.thriftapi.helper;

import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjustVariable;
import xueqiao.trade.hosting.position.adjust.thriftapi.client.TradeHostingPositionAdjustStub;

import java.io.File;

public class PositionAdjustStubFactory {

    private static File STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingPositionAdjustVariable.serviceKey + ".sock");

    public static TradeHostingPositionAdjustStub getStub() {
        TradeHostingPositionAdjustStub stub = new TradeHostingPositionAdjustStub();
        stub.setSocketFile(STUB_SOCKET_FILE);
        return stub;
    }
}
