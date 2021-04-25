package xueqiao.trade.hosting.position.adjust.base;

import com.google.common.base.Preconditions;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.thriftapi.client.TradeHostingPositionAdjustStub;
import xueqiao.trade.hosting.position.adjust.thriftapi.helper.PositionAdjustStubFactory;
import xueqiao.trade.hosting.test.util.PrintUtil;

public abstract class AdjustTest {

    protected TradeHostingPositionAdjustStub stub = PositionAdjustStubFactory.getStub();
    protected TBase result;

    protected long tradeAccountId = 1023;

    protected long adminSubUser = 59;

    protected long subAccount = 1021;

    public abstract String getName();

    public void run() {
        prepareTest();
        try {
            runTest();
        } catch (TException e) {
            PrintUtil.printCommandLine(e);
        }
        printResult();
    }

    protected abstract void prepareTest();

    protected abstract void runTest() throws TException;

    protected void printResult() {
        if (result != null) {
            PrintUtil.printCommandLine(getName()+" run.");
            PrintUtil.printCommandLine(result);
        }
    }
}
