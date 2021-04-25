package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionManualInputOption;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionUnassignedOption;

public class ReqPositionUnassigned extends AdjustTest {

    public static final String TEST_NAME = "RPU";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {

        ReqPositionUnassignedOption option = new ReqPositionUnassignedOption();
        option.setTradeAccountId(tradeAccountId);
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setNeedTotalCount(true);
        result = stub.reqPositionUnassigned(option, pageOption);
    }
}
