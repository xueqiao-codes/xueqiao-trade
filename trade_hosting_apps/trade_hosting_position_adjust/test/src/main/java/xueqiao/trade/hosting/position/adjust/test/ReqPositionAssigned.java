package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionAssignedOption;

public class ReqPositionAssigned extends AdjustTest {
    public static final String TEST_NAME = "RPA";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {
        ReqPositionAssignedOption option = new ReqPositionAssignedOption();
        option.setTradeAccountId(tradeAccountId);
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setNeedTotalCount(true);
        result = stub.reqPositionAssigned(option, pageOption);

    }
}
