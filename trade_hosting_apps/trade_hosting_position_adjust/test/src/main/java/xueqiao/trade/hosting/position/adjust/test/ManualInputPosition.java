package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput;

import java.util.ArrayList;
import java.util.List;

public class ManualInputPosition extends AdjustTest {

    public static final String TEST_NAME = "MIP";
    private List<PositionManualInput> inputs;

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {
        inputs = new ArrayList<>();
        PositionManualInput input = new PositionManualInput();
        input.setSubUserId(adminSubUser);
        input.setTradeAccountId(tradeAccountId);
        input.setSledCommodityId(10536);
        input.setSledContractId(35588);
        input.setPrice(95.75);
        input.setVolume(5);
        input.setPositionDirection(PositionDirection.POSITION_BUY);
        input.setPositionTimestampMs(System.currentTimeMillis());

        inputs.add(input);
    }

    @Override
    protected void runTest() throws TException {
        result = stub.manualInputPosition(inputs);
    }
}
