package xueqiao.trade.hosting.asset.api;

import com.google.common.base.Preconditions;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.actor.AssetActorFactory;
import xueqiao.trade.hosting.asset.actor.IAssetActor;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetTradeHandler;
import xueqiao.trade.hosting.asset.quotation.AssetQuotationHelper;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailSaver;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PositionAssign {

    public AssignPositionResp assign(List<PositionAssigned> positonAssigneds) throws ErrorInfo {
        Preconditions.checkNotNull(positonAssigneds);
        Preconditions.checkArgument(positonAssigneds.size() > 0);

        for (PositionAssigned positionAssigned : positonAssigneds) {
            Preconditions.checkNotNull(positionAssigned);
            Preconditions.checkArgument(positionAssigned.getSubAccountId() > 0);
            Preconditions.checkArgument(positionAssigned.getTradeAccountId() > 0);
            Preconditions.checkArgument(positionAssigned.getSledContractId() > 0);
            Preconditions.checkArgument(positionAssigned.getVolume() > 0);
        }
        List<AssetTradeDetail> assetTradeDetails = AssetTradeHandler.map2AssetTradeDetail(positonAssigneds);
        new AssetTradeDetailSaver().saveAssign(assetTradeDetails);

        return new AssignPositionResp().setSuccess(true);
    }
}
