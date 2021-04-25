package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionDifferenceTable extends TableHelper<PositionDifference> implements IDBTable {

    private final static String TABLE_NAME = "t_position_difference";
    private final static String FVERIFY_ID = "Fverify_id";
    private final static String FTRADE_ACCOUNT_ID = "Ftrade_account_id";

    private final static String FSLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String FSLED_NET_POSITION = "Fsled_net_position";
    private final static String FUPSIDE_NET_POSITION = "Fupside_net_position";

    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    // TODO REQ OPTION UPDATE

    public PositionDifferenceTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionDifference fromResultSet(ResultSet resultSet) throws Exception {
        PositionDifference difference = new PositionDifference();
        difference.setVerifyId(resultSet.getLong(FVERIFY_ID));
        difference.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        difference.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        difference.setSledNetPosition(resultSet.getInt(FSLED_NET_POSITION));
        difference.setUpsideNetPosition(resultSet.getInt(FUPSIDE_NET_POSITION));
        return difference;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fverify_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fsled_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fupside_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fverify_id,Ftrade_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(PositionDifference difference) throws SQLException {
        Preconditions.checkNotNull(difference);
        long now = System.currentTimeMillis();
        PreparedFields fields = new PreparedFields();
        fields.addLong(FTRADE_ACCOUNT_ID, difference.getTradeAccountId());
        fields.addLong(FVERIFY_ID, difference.getVerifyId());
        fields.addLong(FSLED_CONTRACT_ID, difference.getSledContractId());
        fields.addInt(FSLED_NET_POSITION, difference.getSledNetPosition());
        fields.addInt(FUPSIDE_NET_POSITION, difference.getUpsideNetPosition());
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.insert(fields);
    }

    public PositionDifferencePage query(ReqPositionDifferenceOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();

        if (option.isSetTradeAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetVerifyId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FVERIFY_ID + "=?", option.getVerifyId());
        }

        PositionDifferencePage page = new PositionDifferencePage();
        if (pageOption != null) {
            if (pageOption.isSetPageIndex()) {
                sqlBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            }
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(sqlBuilder));
            }
        }

        List<PositionDifference> list = super.getItemList(sqlBuilder);
        page.setPage(list);
        return page;
    }
}
