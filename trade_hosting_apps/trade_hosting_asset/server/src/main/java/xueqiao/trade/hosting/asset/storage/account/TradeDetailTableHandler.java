package xueqiao.trade.hosting.asset.storage.account;

import org.soldier.base.sql.PreparedFields;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeDetailTableHandler {

    public static final String COLUMN_FTRADE_DETAIL_ID = "Ftrade_detail_id";
    public static final String COLUMN_FEXEC_TRADE_ID = "Fexec_trade_id";
    public static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    public static final String COLUMN_FSLED_CONTRACT_ID = "Fsled_contract_id";
    public static final String COLUMN_FEXEC_ORDER_ID = "Fexec_order_id";
    public static final String COLUMN_FTRADE_PRICE = "Ftrade_price";
    public static final String COLUMN_FTRADE_VOLUME = "Ftrade_volume";
    public static final String COLUMN_FTRADE_DIRECTION = "Ftrade_direction";
    public static final String COLUMN_FSLED_COMMODITY_ID = "Fsled_commodity_id";
    public static final String COLUMN_FSOURCE = "Fsource";
    public static final String COLUMN_FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    public static final String COLUMN_FTRADE_TIMESTAMPMS = "FtradeTimestampMs";
    public static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    public static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    public static final String COLUMN_FSUB_USER_ID = "Fsub_user_id";
    public static final String COLUMN_FSLED_ORDER_ID = "Fsled_order_id";

    public static AssetTradeDetail getAssetTradeDetailFromResultSet(ResultSet resultSet) throws SQLException {
        AssetTradeDetail detail = new AssetTradeDetail();
        detail.setExecTradeId(resultSet.getLong(COLUMN_FEXEC_TRADE_ID));
        detail.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        detail.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        detail.setExecOrderId(resultSet.getLong(COLUMN_FEXEC_ORDER_ID));
        detail.setTradePrice(resultSet.getDouble(COLUMN_FTRADE_PRICE));
        detail.setTradeVolume(resultSet.getInt(COLUMN_FTRADE_VOLUME));
        detail.setExecTradeDirection(HostingExecTradeDirection.findByValue(resultSet.getInt(COLUMN_FTRADE_DIRECTION)));
        detail.setSledCommodityId(resultSet.getLong(COLUMN_FSLED_COMMODITY_ID));
        detail.setSource(resultSet.getString(COLUMN_FSOURCE));
        detail.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        detail.setLastmodifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));

        detail.setAssetTradeDetailId(resultSet.getLong(COLUMN_FTRADE_DETAIL_ID));
        detail.setTradeAccountId(resultSet.getLong(COLUMN_FTRADE_ACCOUNT_ID));
        detail.setTradeTimestampMs(resultSet.getLong(COLUMN_FTRADE_TIMESTAMPMS));
        detail.setSubUserId(resultSet.getInt(COLUMN_FSUB_USER_ID));
        detail.setSledOrderId(resultSet.getString(COLUMN_FSLED_ORDER_ID));
        return detail;
    }

    public static PreparedFields getPreparedFields(AssetTradeDetail assetTradeDetail) {
        PreparedFields fields = new PreparedFields();
        if (assetTradeDetail.isSetExecTradeId()) {
            fields.addLong(COLUMN_FEXEC_TRADE_ID, assetTradeDetail.getExecTradeId());
        }
        if (assetTradeDetail.isSetSubAccountId()) {
            fields.addLong(COLUMN_FSUB_ACCOUNT_ID, assetTradeDetail.getSubAccountId());
        }
        if (assetTradeDetail.isSetSledContractId()) {
            fields.addLong(COLUMN_FSLED_CONTRACT_ID, assetTradeDetail.getSledContractId());
        }
        if (assetTradeDetail.isSetExecOrderId()) {
            fields.addLong(COLUMN_FEXEC_ORDER_ID, assetTradeDetail.getExecOrderId());
        }
        if (assetTradeDetail.isSetTradePrice()) {
            fields.addDouble(COLUMN_FTRADE_PRICE, assetTradeDetail.getTradePrice());
        }
        if (assetTradeDetail.isSetTradeVolume()) {
            fields.addInt(COLUMN_FTRADE_VOLUME, assetTradeDetail.getTradeVolume());
        }
        if (assetTradeDetail.isSetExecTradeDirection()) {
            fields.addInt(COLUMN_FTRADE_DIRECTION, assetTradeDetail.getExecTradeDirection().getValue());
        }
        if (assetTradeDetail.isSetSledCommodityId()) {
            fields.addLong(COLUMN_FSLED_COMMODITY_ID, assetTradeDetail.getSledCommodityId());
        }
        if (assetTradeDetail.isSetSource()) {
            fields.addString(COLUMN_FSOURCE, assetTradeDetail.getSource());
        }
        if (assetTradeDetail.isSetTradeTimestampMs()) {
            fields.addLong(COLUMN_FTRADE_TIMESTAMPMS, assetTradeDetail.getTradeTimestampMs());
        }
        if (assetTradeDetail.isSetTradeAccountId()) {
            fields.addLong(COLUMN_FTRADE_ACCOUNT_ID, assetTradeDetail.getTradeAccountId());
        }
        if (assetTradeDetail.isSetAssetTradeDetailId()) {
            fields.addLong(COLUMN_FTRADE_DETAIL_ID, assetTradeDetail.getAssetTradeDetailId());
        }
        if (assetTradeDetail.isSetSubUserId()) {
            fields.addInt(COLUMN_FSUB_USER_ID, assetTradeDetail.getSubUserId());
        }
        if (assetTradeDetail.isSetSledOrderId()) {
            fields.addString(COLUMN_FSLED_ORDER_ID, assetTradeDetail.getSledOrderId());
        }

        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, System.currentTimeMillis());
        return fields;
    }
}
