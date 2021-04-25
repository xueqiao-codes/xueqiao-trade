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
import xueqiao.trade.hosting.position.fee.storage.handler.XQContractMarginHandler;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommodityInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;

public class AddSpecMarginController implements IController {

    private XQSpecMarginSettings marginSettings;
    private SledCommodity sledCommodity;

    public AddSpecMarginController(XQSpecMarginSettings marginSettings) {
        this.marginSettings = marginSettings;
    }

    @Override
    public void checkParams() throws TException {
        Precondition.check(marginSettings != null, "marginSettings should not be null");
        Precondition.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(marginSettings.getCommodityId() > 0, "invalid commodityId");
        Precondition.check(marginSettings.isSetType(), "type should be set");
        Precondition.check(marginSettings.isSetMarginDelta(), "marginDelta should be set");

        sledCommodity = HostingContractApi.queryCommodity((int) marginSettings.getCommodityId());
        Precondition.check(sledCommodity != null, "invalid commodityId");
    }

    public void doAdd() throws ErrorInfo {

        if (marginSettings.isSetCommodityInfo()) {
            marginSettings.getCommodityInfo().setExchangeMic(sledCommodity.getExchangeMic());
        } else {
            CommodityInfo commodityInfo = new CommodityInfo();
            commodityInfo.setExchangeMic(sledCommodity.getExchangeMic());
            marginSettings.setCommodityInfo(commodityInfo);
        }
        MarginInfo marginDelta = marginSettings.getMarginDelta();
        marginDelta.setCurrencyGroup("");
        /*
         * 币种为对应商品的币种
         * */
        marginDelta.setCurrency(sledCommodity.getTradeCurrency());

        new DBTransactionHelper<Void>(PositionFeeDB.Global()) {
            XQSpecMarginSettingsTable table;
            XQSpecMarginSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {


                table = new XQSpecMarginSettingsTable(getConnection());
                originSettings = table.query(marginSettings.getSubAccountId(), marginSettings.getCommodityId());
                if (originSettings != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("marginSettings already exist with index : (subAccountId=")
                            .append(marginSettings.getSubAccountId())
                            .append(", commodityId=")
                            .append(marginSettings.getCommodityId())
                            .append(")");
                    throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), builder.toString());
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                table.insert(marginSettings);
                /*
                 * 添加商品持仓费率设置时，需要更新雪橇用户的持仓费率，偏移值由原通用值变为商品持仓费率偏移值
                 * */
                XQContractMarginHandler.updateXQContractMarginToCommoditySettingDataType(getConnection(), marginSettings.getSubAccountId(), marginSettings.getCommodityId());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
