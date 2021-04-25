package xueqiao.trade.hosting.position.statis.helper;

import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatisVariable;
import xueqiao.trade.hosting.position.statis.client.TradeHostingPositionStatisStub;

import java.io.File;

public class PositionStatisStubFactory {

	private static File POSITION_STATIS_STUB_SOCKET_FILE = new File(
		"/data/run/service_" + TradeHostingPositionStatisVariable.serviceKey + ".sock");

	public static TradeHostingPositionStatisStub getStub() {
		TradeHostingPositionStatisStub stub = new TradeHostingPositionStatisStub();
		stub.setSocketFile(POSITION_STATIS_STUB_SOCKET_FILE);
		return stub;
	}
}
