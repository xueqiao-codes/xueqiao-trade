package xueqiao.trade.hosting.position.adjust.api;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.statis.TradeHostingPositionStatisVariable;
import xueqiao.trade.hosting.position.statis.client.TradeHostingPositionStatisStub;
import xueqiao.trade.hosting.position.statis.helper.PositionStatisStubFactory;

import java.io.File;

public class PositionStatisHandler {

    public void assignPosition(PositionAssigned assigned) throws TException {
        TradeHostingPositionStatisStub assetStub = PositionStatisStubFactory.getStub();
        assetStub.assignPosition(assigned);
    }
}
