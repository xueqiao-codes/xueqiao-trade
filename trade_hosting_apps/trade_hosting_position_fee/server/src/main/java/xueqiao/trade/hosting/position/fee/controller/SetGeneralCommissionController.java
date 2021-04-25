package xueqiao.trade.hosting.position.fee.controller;

import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.app.AppData;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;

public class SetGeneralCommissionController implements IController {

    private XQGeneralCommissionSettings commissionSettings;

    public SetGeneralCommissionController(XQGeneralCommissionSettings commissionSettings) {
        this.commissionSettings = commissionSettings;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        Precondition.check(commissionSettings != null, "commissionSettings should not be null");
        Precondition.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(commissionSettings.isSetType(), "type should be set");
        Precondition.check(commissionSettings.getCommissionDelta() != null, "commissionDelta should be set");
    }

    public void doSet() throws ErrorInfo {
        commissionSettings.getCommissionDelta().setCurrency(AppData.getDefaultBaseCurrency());

        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            XQGeneralCommissionSettingsTable table;
            XQGeneralCommissionSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new XQGeneralCommissionSettingsTable(getConnection());
                originSettings = table.query(commissionSettings.getSubAccountId());

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (originSettings == null) {
                    table.insert(commissionSettings);
                } else {
                    table.update(commissionSettings);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
