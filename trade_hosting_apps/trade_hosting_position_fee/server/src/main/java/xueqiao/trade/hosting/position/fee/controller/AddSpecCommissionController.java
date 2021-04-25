package xueqiao.trade.hosting.position.fee.controller;

import com.antiy.error_code.ErrorCodeInner;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.handler.XQContractCommissionHandler;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.CommodityInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;

public class AddSpecCommissionController implements IController {

    private XQSpecCommissionSettings commissionSettings;
    private SledCommodity sledCommodity;

    public AddSpecCommissionController(XQSpecCommissionSettings commissionSettings) {
        this.commissionSettings = commissionSettings;
    }

    @Override
    public void checkParams() throws TException {
        Precondition.check(commissionSettings != null, "commissionSettings should not be null");
        Precondition.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(commissionSettings.getCommodityId() > 0, "invalid commodityId");
        Precondition.check(commissionSettings.isSetType(), "type should be set");
        Precondition.check(commissionSettings.isSetCommissionDelta(), "commissionDelta should be set");

        sledCommodity = HostingContractApi.queryCommodity((int) commissionSettings.getCommodityId());
        Precondition.check(sledCommodity != null, "invalid commodityId");
    }

    public void doAdd() throws ErrorInfo {

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

        new DBTransactionHelper<Void>(PositionFeeDB.Global()) {
            XQSpecCommissionSettingsTable table;
            XQSpecCommissionSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new XQSpecCommissionSettingsTable(getConnection());
                originSettings = table.query(commissionSettings.getSubAccountId(), commissionSettings.getCommodityId());
                if (originSettings != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("marginSettings already exist with index : (subAccountId=")
                            .append(commissionSettings.getSubAccountId())
                            .append(", commodityId=")
                            .append(commissionSettings.getCommodityId())
                            .append(")");
                    throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), builder.toString());
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                table.insert(commissionSettings);
                /*
                 * 添加商品持仓费率设置时，需要更新雪橇用户的持仓费率，偏移值由原通用值变为商品持仓费率偏移值
                 * */
                XQContractCommissionHandler.updateXQContractCommissionToCommoditySettingDataType(getConnection(), commissionSettings.getSubAccountId(), commissionSettings.getCommodityId());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
