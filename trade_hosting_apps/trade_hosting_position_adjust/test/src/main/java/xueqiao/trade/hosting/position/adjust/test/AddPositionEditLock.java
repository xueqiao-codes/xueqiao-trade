package xueqiao.trade.hosting.position.adjust.test;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;
import xueqiao.trade.hosting.position.adjust.thriftapi.trade_hosting_position_adjustConstants;

public class AddPositionEditLock extends AdjustTest {
    public static final String TEST_NAME = "APE";

    @Override
    public String getName() {
        return TEST_NAME;
    }

    @Override
    protected void prepareTest() {

    }

    @Override
    protected void runTest() throws TException {
        PositionEditLock editLock = new PositionEditLock();
        editLock.setSubUserId(adminSubUser);
        editLock.setLockArea(trade_hosting_position_adjustConstants.POSITION_EDIT_AREA_MANUAL_INPUT);
        stub.addPositionEditLock(editLock);
    }
}
