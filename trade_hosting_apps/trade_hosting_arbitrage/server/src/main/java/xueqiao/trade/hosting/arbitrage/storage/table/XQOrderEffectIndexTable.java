package xueqiao.trade.hosting.arbitrage.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderEffectIndexItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class XQOrderEffectIndexTable extends TableHelper<XQOrderEffectIndexItem> implements IDBTable {

    public XQOrderEffectIndexTable(Connection conn) {
        super(conn);
    }
    
    public List<XQOrderEffectIndexItem> getAll() throws SQLException {
        return super.getItemList(super.prepareSqlQueryBuilder());
    }
    
    public List<XQOrderEffectIndexItem> getTtlTimestampBeforeItems(long ttlTimestampMs, PageOption pageOption) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fttl_timestampms<?", ttlTimestampMs);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fttl_timestampms>0");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        return super.getItemList(queryBuilder);
    }
    
    public PageResult<XQOrderEffectIndexItem> getIndexItems(
            QueryEffectXQOrderIndexOption qryOption, PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetOrderIds() && !qryOption.getOrderIds().isEmpty()) {
                queryBuilder.addInFieldCondition(ConditionType.AND, "Forder_id", qryOption.getOrderIds());
            }
            if (qryOption.isSetSubUserIds() && !qryOption.getSubUserIds().isEmpty()) {
                queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_user_id", qryOption.getSubUserIds());
            }
            if (qryOption.isSetSubAccountIds() && !qryOption.getSubAccountIds().isEmpty()) {
                queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_account_id", qryOption.getSubAccountIds());
            }
            
        }
        queryBuilder.setOrder(OrderType.ASC, "Fcreate_tiemstampms");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        
        PageResult<XQOrderEffectIndexItem> resultPage = new PageResult<XQOrderEffectIndexItem>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(queryBuilder));
        }
        resultPage.setPageList(super.getItemList(queryBuilder));
        return resultPage;
    }
    
    public int addIndexItem(XQOrderEffectIndexItem indexItem) throws SQLException {
        Preconditions.checkNotNull(indexItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(indexItem.getOrderId()));
        Preconditions.checkArgument(indexItem.getSubUserId() > 0);
        Preconditions.checkArgument(indexItem.getSubAccountId() > 0);
        
        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", indexItem.getOrderId());
        pf.addInt("Fsub_user_id", indexItem.getSubUserId());
        pf.addLong("Fsub_account_id", indexItem.getSubAccountId());
        
        if (indexItem.getCreateTimestampMs() > 0) {
            pf.addLong("Fcreate_tiemstampms", indexItem.getCreateTimestampMs());
        } else {
            pf.addLong("Fcreate_tiemstampms", System.currentTimeMillis());
        }
        
        if (indexItem.getTtlTimestampMs() > 0) {
            pf.addLong("Fttl_timestampms", indexItem.getTtlTimestampMs());
        } 
        
        return super.insert(pf);
    }
    
    public int updateIndexItemTTL(String orderId, long ttlTimestampMs) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        
        PreparedFields pf = new PreparedFields();
        pf.addLong("Fttl_timestampms", ttlTimestampMs);
        return super.update(pf, "Forder_id=?", orderId);
    }
    
    public int delIndexItem(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        return super.delete("Forder_id=?", orderId);
    }

    @Override
    public XQOrderEffectIndexItem fromResultSet(ResultSet rs) throws Exception {
        XQOrderEffectIndexItem indexItem = new XQOrderEffectIndexItem();
        indexItem.setOrderId(rs.getString("Forder_id"));
        indexItem.setSubUserId(rs.getInt("Fsub_user_id"));
        indexItem.setSubAccountId(rs.getLong("Fsub_account_id"));
        indexItem.setTtlTimestampMs(rs.getLong("Fttl_timestampms"));
        indexItem.setCreateTimestampMs(rs.getLong("Fcreate_tiemstampms"));
        return indexItem;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fttl_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fcreate_tiemstampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order_index";
    }
    
}
