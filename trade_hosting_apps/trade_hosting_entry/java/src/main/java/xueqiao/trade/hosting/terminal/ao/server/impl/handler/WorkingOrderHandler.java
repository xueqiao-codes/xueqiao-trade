package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import com.longsheng.xueqiao.goose.thriftapi.client.GooseAoStub;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.soldier.platform.svr_platform.util.ProtocolUtil;
import xueqiao.trade.hosting.entry.core.UserIdCache;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.working.order.dao.thriftapi.WorkingOrderStorage;
import xueqiao.working.order.dao.thriftapi.client.WorkingOrderDaoStub;
import xueqiao.working.order.thriftapi.AssetAccount;
import xueqiao.working.order.thriftapi.BaseWorkingOrder;
import xueqiao.working.order.thriftapi.WorkingOrderState;
import xueqiao.working.order.thriftapi.WorkingOrderType;

public class WorkingOrderHandler extends HandlerBase {
    public WorkingOrderHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public long addAssetAccountWorkingOrder(AssetAccount assetAccount) throws TException {
        long companyUserId = UserIdCache.getInstance().getUserId(super.getMachineId(), super.getSubUserId());

        String classType = assetAccount.getClass().getName();
        byte[] content = ProtocolUtil.serialize2Bytes(new TJSONProtocol.Factory(), assetAccount);

        WorkingOrderStorage storage = new WorkingOrderStorage();
        BaseWorkingOrder baseWorkingOrder = new BaseWorkingOrder();
        baseWorkingOrder.setCompanyId(UserIdCache.getInstance().getCompanyId());
        baseWorkingOrder.setCompanyUserId(companyUserId);
        baseWorkingOrder.setWorkingOrderType(WorkingOrderType.ASSET_ACCOUNT);
        baseWorkingOrder.setState(WorkingOrderState.CREATE);

        storage.setBaseWorkingOrder(baseWorkingOrder);
        storage.setOrderClassType(classType);
        storage.setContent(new String(content));
        long workingOrderId = new WorkingOrderDaoStub().addWorkingOrderStorage(storage);
        new GooseAoStub().sendMaintenanceNotificationSms("New working order add: " + workingOrderId);
        return workingOrderId;
    }
}
