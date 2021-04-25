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
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommodityInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;

public class UpdateSpecMarginController implements IController {

    private XQSpecMarginSettings marginSettings;
    private SledCommodity sledCommodity;

    public UpdateSpecMarginController(XQSpecMarginSettings marginSettings) {
        this.marginSettings = marginSettings;
    }

    @Override
    public void checkParams() throws TException {
        Precondition.check(marginSettings != null, "marginSettings should not be null");
        Precondition.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        Precondition.check(marginSettings.getCommodityId() > 0, "invalid commodityId");

        sledCommodity = HostingContractApi.queryCommodity((int) marginSettings.getCommodityId());
        Precondition.check(sledCommodity != null, "invalid commodityId");
    }

    public void doUpdate() throws ErrorInfo {
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
        
        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            XQSpecMarginSettingsTable table;
            XQSpecMarginSettings originSettings;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new XQSpecMarginSettingsTable(getConnection());
                originSettings = table.query(marginSettings.getSubAccountId(), marginSettings.getCommodityId());
                if (originSettings == null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("marginSettings not exist with index : (subAccountId=")
                            .append(marginSettings.getSubAccountId())
                            .append(", commodityId=")
                            .append(marginSettings.getCommodityId())
                            .append(")");
                    throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), builder.toString());
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                table.update(marginSettings);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
