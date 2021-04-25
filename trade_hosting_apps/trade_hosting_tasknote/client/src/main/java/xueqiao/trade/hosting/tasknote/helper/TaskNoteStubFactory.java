package xueqiao.trade.hosting.tasknote.helper;


import xueqiao.trade.hosting.tasknote.thriftapi.TradeHostingTaskNoteVariable;
import xueqiao.trade.hosting.tasknote.thriftapi.client.TradeHostingTaskNoteStub;

import java.io.File;

public class TaskNoteStubFactory {
    private static File DEALING_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingTaskNoteVariable.serviceKey + ".sock");

    public static TradeHostingTaskNoteStub getStub() {
        TradeHostingTaskNoteStub stub = new TradeHostingTaskNoteStub();
        stub.setSocketFile(DEALING_STUB_SOCKET_FILE);
        return stub;
    }
}
