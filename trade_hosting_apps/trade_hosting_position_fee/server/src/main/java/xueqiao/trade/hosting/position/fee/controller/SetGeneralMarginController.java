package xueqiao.trade.hosting.position.fee.controller;

import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.app.AppData;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings;

public class SetGeneralMarginController implements IController {

    private XQGeneralMarginSettings marginSettings;

    public SetGeneralMarginController(XQGeneralMarginSettings marginSettings) {
        this.marginSettings = marginSettings;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        Precondition.check(marginSettings != null, "marginSettings should not be null");
        Precondition.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(marginSettings.isSetType(), "type should be set");
        Precondition.check(marginSettings.getMarginDelta() != null, "marginDelta should be set");
    }

    public void doSet() throws ErrorInfo {

        marginSettings.getMarginDelta().setCurrency(AppData.getDefaultBaseCurrency());

        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            XQGeneralMarginSettingsTable table;
            XQGeneralMarginSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new XQGeneralMarginSettingsTable(getConnection());
                originSettings = table.query(marginSettings.getSubAccountId());

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (originSettings == null) {
                    table.insert(marginSettings);
                } else {
                    table.update(marginSettings);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}