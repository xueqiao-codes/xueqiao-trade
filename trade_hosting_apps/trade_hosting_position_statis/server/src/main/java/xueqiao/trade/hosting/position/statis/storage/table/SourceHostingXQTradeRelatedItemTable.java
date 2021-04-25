package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceHostingXQTradeRelatedItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceHostingXQTradeRelatedItemTable extends TableHelper<SourceHostingXQTradeRelatedItem> implements IDBTable {

    private final static String TABLE_NAME = "source_xq_trade_related_item";

    private final static String F_SOURCE_ID = "Fsource_id";
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_SLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String F_EXEC_TRADE_LEG_PRICE = "Fexec_trade_leg_price";
    private final static String F_RELATED_TRADE_VOLUME = "Frelated_trade_volume";
    private final static String F_EXEC_TRADE_LEG_DIRECTION = "Fexec_trade_leg_direction";

    private final static String F_ORDER_ID = "Forder_id";
    private final static String F_TRADE_ID = "Ftrade_id";
    private final static String F_EXEC_ORDER_ID = "Fexec_order_id";
    private final static String F_EXEC_TRADE_ID = "Fexec_trade_id";
    private final static String F_EXEC_TRADE_LEG_ID = "Fexec_trade_leg_id";

    private final static String F_IS_COMPLETED = "Fis_completed";
    private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";

    public SourceHostingXQTradeRelatedItemTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    public int insert(SourceHostingXQTradeRelatedItem item) throws SQLException, ErrorInfo {
        PreparedFields pf = new PreparedFields();

        pf.addLong(F_SOURCE_ID, item.getSourceId());
        pf.addLong(F_SUB_ACCOUNT_ID, item.getSubAccountId());
        pf.addLong(F_SLED_CONTRACT_ID, item.getSledContractId());
        pf.addDouble(F_EXEC_TRADE_LEG_PRICE, item.getExecTradeLegPrice());
        pf.addInt(F_RELATED_TRADE_VOLUME, item.getRelatedTradeVolume());
        pf.addInt(F_EXEC_TRADE_LEG_DIRECTION, item.getExecTradeLegDirection().getValue());
        pf.addString(F_ORDER_ID, item.getOrderId());
        pf.addLong(F_TRADE_ID, item.getTradeId());
        pf.addLong(F_EXEC_ORDER_ID, item.getExecOrderId());
        pf.addLong(F_EXEC_TRADE_ID, item.getExecTradeId());
        pf.addLong(F_EXEC_TRADE_LEG_ID, item.getExecTradeLegId());
        pf.addInt(F_IS_COMPLETED, 0);
        pf.addLong(F_CREATE_TIMESTAMP_MS, item.getCreateTimestampMs());

        return super.insert(pf);
    }

    @Override
    public SourceHostingXQTradeRelatedItem fromResultSet(ResultSet resultSet) throws Exception {
        SourceHostingXQTradeRelatedItem item = new SourceHostingXQTradeRelatedItem();
        item.setSourceId(resultSet.getLong(F_SOURCE_ID));
        item.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
        item.setSledContractId(resultSet.getLong(F_SLED_CONTRACT_ID));
        item.setExecTradeLegPrice(resultSet.getDouble(F_EXEC_TRADE_LEG_PRICE));
        item.setRelatedTradeVolume(resultSet.getInt(F_RELATED_TRADE_VOLUME));
        item.setExecTradeLegDirection(HostingExecTradeDirection.findByValue(resultSet.getInt(F_EXEC_TRADE_LEG_DIRECTION)));
        item.setOrderId(resultSet.getString(F_ORDER_ID));
        item.setTradeId(resultSet.getLong(F_TRADE_ID));
        item.setExecOrderId(resultSet.getLong(F_EXEC_ORDER_ID));
        item.setExecTradeId(resultSet.getLong(F_EXEC_TRADE_ID));
        item.setExecTradeLegId(resultSet.getLong(F_EXEC_TRADE_LEG_ID));
        item.setCompleted(resultSet.getBoolean(F_IS_COMPLETED));
        item.setCreateTimestampMs(resultSet.getLong(F_CREATE_TIMESTAMP_MS));
        return item;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (4 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(255);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SOURCE_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_SLED_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXEC_TRADE_LEG_PRICE).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_RELATED_TRADE_VOLUME).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXEC_TRADE_LEG_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_ORDER_ID).append(" VARCHAR(64) NOT NULL DEFAULT '',")
                    .append(F_TRADE_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXEC_ORDER_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXEC_TRADE_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXEC_TRADE_LEG_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_IS_COMPLETED).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_SOURCE_ID).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
