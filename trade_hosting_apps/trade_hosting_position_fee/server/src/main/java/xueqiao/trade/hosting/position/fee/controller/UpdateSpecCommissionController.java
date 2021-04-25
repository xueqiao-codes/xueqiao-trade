package xueqiao.trade.hosting.position.fee.controller;

import com.antiy.error_code.ErrorCodeInner;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.CommodityInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;

public class UpdateSpecCommissionController implements IController {

    private XQSpecCommissionSettings commissionSettings;
    private SledCommodity sledCommodity;

    public UpdateSpecCommissionController(XQSpecCommissionSettings commissionSettings) {
        this.commissionSettings = commissionSettings;
    }

    @Override
    public void checkParams() throws TException {
        Precondition.check(commissionSettings != null, "commissionSettings should not be null");
        Precondition.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(commissionSettings.getCommodityId() > 0, "invalid commodityId");

        sledCommodity = HostingContractApi.queryCommodity((int) commissionSettings.getCommodityId());
        Precondition.check(sledCommodity != null, "invalid commodityId");
    }

    public void doUpdate() throws ErrorInfo {
        if (commissionSettings.isSetCommodityInfo()) {
            commissionSettings.getCommodityInfo().setExchangeMic(sledCommodity.getExchangeMic());
        } else {
            CommodityInfo commodityInfo = new CommodityInfo();
            commodityInfo.setExchangeMic(sledCommodity.getExchangeMic());
            commissionSettings.setCommodityInfo(commodityInfo);
        }
        CommissionInfo commissionDelta = commissionSettings.getCommissionDelta();
        commissionDelta.setCurrencyGroup("");
        /*
         * 币种为对应商品的币种
         * */
        commissionDelta.setCurrency(sledCommodity.getTradeCurrency());

        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            XQSpecCommissionSettingsTable table;
            XQSpecCommissionSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new XQSpecCommissionSettingsTable(getConnection());
                originSettings = table.query(commissionSettings.getSubAccountId(), commissionSettings.getCommodityId());
                if (originSettings == null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("marginSettings not exist with index : (subAccountId=")
                            .append(commissionSettings.getSubAccountId())
                            .append(", commodityId=")
                            .append(commissionSettings.getCommodityId())
                            .append(")");
                    throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), builder.toString());
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                table.update(commissionSettings);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
