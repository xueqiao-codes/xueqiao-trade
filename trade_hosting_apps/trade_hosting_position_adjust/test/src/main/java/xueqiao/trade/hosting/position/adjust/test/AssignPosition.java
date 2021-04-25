package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignOption;

import java.util.ArrayList;
import java.util.List;

public class AssignPosition extends AdjustTest {
    public static final String TEST_NAME = "AP";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {

        List<PositionAssignOption> assignOptions = new ArrayList<>();
        PositionAssignOption assignOption = new PositionAssignOption();
        assignOption.setSubUserId(adminSubUser);
        assignOption.setInputId(2701);
        assignOption.setSubAccountId(subAccount);
        assignOption.setVolume(2);

        assignOptions.add(assignOption);

        result = stub.assignPosition(assignOptions);

    }
}
