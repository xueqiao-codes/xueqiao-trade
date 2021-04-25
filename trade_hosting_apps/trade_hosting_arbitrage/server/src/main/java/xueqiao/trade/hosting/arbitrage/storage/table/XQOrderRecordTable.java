package xueqiao.trade.hosting.arbitrage.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRecordItem;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XQOrderRecordTable extends TableHelper<XQOrderRecordItem> implements IDBTable {
    public XQOrderRecordTable(Connection conn) {
        super(conn);
    }

    public int insertRecord(XQOrderRecordItem newItem) throws SQLException {
        Preconditions.checkNotNull(newItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(newItem.getOrderId()));
        Preconditions.checkArgument(newItem.getSledContractId() >= 0);

        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", newItem.getOrderId());
        pf.addLong("Fsled_contract_id", newItem.getSledContractId());

        if (newItem.getLastestUsedPrice() != null
                && PriceUtils.isAppropriatePrice(newItem.getLastestUsedPrice())) {
            pf.addByte("Flatest_used_price_isset", (byte)1);
            pf.addDouble("Flatest_used_price", newItem.getLastestUsedPrice());
        }

        pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
        pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());

        return super.insert(pf);
    }

    public int updateRecord(XQOrderRecordItem updateItem) throws SQLException {
        Preconditions.checkNotNull(updateItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(updateItem.getOrderId()));
        Preconditions.checkArgument(updateItem.getSledContractId() >= 0);

        PreparedFields pf = new PreparedFields();
        if (updateItem.getLastestUsedPrice() != null
                && PriceUtils.isAppropriatePrice(updateItem.getLastestUsedPrice())) {
            pf.addByte("Flatest_used_price_isset", (byte)1);
            pf.addDouble("Flatest_used_price", updateItem.getLastestUsedPrice());
        }

        pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());

        return super.update(pf, "Forder_id=? AND Fsled_contract_id=?"
                , updateItem.getOrderId(), updateItem.getSledContractId());
    }

    public int delRecords(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        return super.delete("Forder_id=?", orderId);
    }

    public XQOrderRecordItem getRecord(String orderId, long sledContractId) throws SQLException {
        Preconditions.checkNotNull(orderId);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Forder_id=?", orderId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fsled_contract_id=?", sledContractId);

        return super.getItem(qryBuilder);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (8 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flatest_used_price_isset TINYINT NOT NULL DEFAULT 0,")
                            .append("Flatest_used_price DOUBLE DEFAULT 0.00,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id, Fsled_contract_id)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order_record";
    }

    @Override
    public XQOrderRecordItem fromResultSet(ResultSet rs) throws Exception {
        XQOrderRecordItem resultItem = new XQOrderRecordItem();
        resultItem.setOrderId(rs.getString("Forder_id"));
        resultItem.setSledContractId(rs.getLong("Fsled_contract_id"));
        if (0 != rs.getByte("Flatest_used_price_isset")) {
            resultItem.setLastestUsedPrice(rs.getDouble("Flatest_used_price"));
        }
        resultItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        resultItem.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        return resultItem;
    }
}
