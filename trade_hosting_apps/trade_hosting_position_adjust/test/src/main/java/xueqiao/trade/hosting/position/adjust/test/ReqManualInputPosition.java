package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionManualInputOption;

public class ReqManualInputPosition extends AdjustTest {
    public static final String TEST_NAME = "RMIP";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {

        ReqPositionManualInputOption option = new ReqPositionManualInputOption();
        option.setTradeAccountId(tradeAccountId);
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setNeedTotalCount(true);
        result = stub.reqManualInputPosition(option, pageOption);

    }
}
