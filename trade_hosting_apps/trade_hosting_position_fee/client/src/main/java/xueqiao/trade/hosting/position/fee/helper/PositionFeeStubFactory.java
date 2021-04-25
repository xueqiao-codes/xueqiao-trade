package xueqiao.trade.hosting.position.fee.helper;

import xueqiao.trade.hosting.position.fee.thriftapi.TradeHostingPositionFeeVariable;
import xueqiao.trade.hosting.position.fee.thriftapi.client.TradeHostingPositionFeeStub;

import java.io.File;

public class PositionFeeStubFactory {
    private static File POSITION_STATIS_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingPositionFeeVariable.serviceKey + ".sock");

    public static TradeHostingPositionFeeStub getStub() {
        TradeHostingPositionFeeStub stub = new TradeHostingPositionFeeStub();
        stub.setSocketFile(POSITION_STATIS_STUB_SOCKET_FILE);
        return stub;
    }
}
