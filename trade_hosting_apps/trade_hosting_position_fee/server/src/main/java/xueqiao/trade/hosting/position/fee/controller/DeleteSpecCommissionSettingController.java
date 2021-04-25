package xueqiao.trade.hosting.position.fee.controller;

import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.handler.XQContractCommissionHandler;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;

public class DeleteSpecCommissionSettingController implements IController {

    private long subAccountId;
    private long commodityId;

    public DeleteSpecCommissionSettingController(long subAccountId, long commodityId) {
        this.subAccountId = subAccountId;
        this.commodityId = commodityId;
    }

    @Override
    public void checkParams() throws TException {
        Precondition.check(subAccountId > 0, "invalid subAccountId");
        Precondition.check(commodityId > 0, "invalid commodityId");
    }

    public void doDelete() throws ErrorInfo {
        new DBTransactionHelper<Void>(PositionFeeDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new XQSpecCommissionSettingsTable(getConnection()).delete(subAccountId, commodityId);
                /*
                 * 删除商品持仓费率设置时，需要更新雪橇用户的持仓费率，偏移值由原商品持仓费率偏移值变为通用值
                 * */
                XQContractCommissionHandler.updateXQContractCommissionToGeneralSettingDataType(getConnection(), subAccountId, commodityId);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
