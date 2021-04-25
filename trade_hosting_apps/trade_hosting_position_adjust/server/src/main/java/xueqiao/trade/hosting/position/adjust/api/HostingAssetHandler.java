package xueqiao.trade.hosting.position.adjust.api;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAssetVariable;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HostingAssetHandler {

    public AssignPositionResp assignPosition(PositionAssigned assigned) throws TException {
        TradeHostingAssetStub assetStub = AssetStubFactory.getStub();
        List<PositionAssigned> list = new ArrayList<>();
        list.add(assigned);
        return assetStub.assignPosition(list);
    }
}
