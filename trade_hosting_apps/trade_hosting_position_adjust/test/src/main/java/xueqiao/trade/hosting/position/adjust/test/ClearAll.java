package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;

public class ClearAll extends AdjustTest {
    public static final String TEST_NAME = "CA";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {
        stub.clearAll();
    }
}
