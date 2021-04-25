package xueqiao.trade.hosting.storage.subaccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

public class HostingSubAccountRelatedTable extends TableHelper <HostingSubAccountRelatedItem> implements IDBTable{

    public HostingSubAccountRelatedTable(Connection conn) {
        super(conn);
    }
    
    public HostingSubAccountRelatedItem getItem(long subAccountId, int subUserId) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", subAccountId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
        return super.getItem(queryBuilder);
    }
    
    public List<HostingSubAccountRelatedItem> getItemsBySubAccountId(long subAccountId) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", subAccountId);
        return super.getItemList(queryBuilder);
    }
    
    public List<HostingSubAccountRelatedItem> getAll() throws SQLException {
        return super.getItemList(super.prepareSqlQueryBuilder());
    }
    
    public Map<Long, List<HostingSubAccountRelatedItem>> getItemsBySubAccountIds(Set<Long> subAccountIds) 
            throws SQLException {
        Preconditions.checkNotNull(subAccountIds);
        Preconditions.checkArgument(!subAccountIds.isEmpty());
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_account_id", subAccountIds);
        
        List<HostingSubAccountRelatedItem> relatedItems = super.getItemList(queryBuilder);
        Map<Long, List<HostingSubAccountRelatedItem>> resultMap = new HashMap<Long, List<HostingSubAccountRelatedItem>>();
        for (HostingSubAccountRelatedItem relatedItem : relatedItems) {
            List<HostingSubAccountRelatedItem> subAccountRelatedItems = resultMap.get(relatedItem.getSubAccountId());
            if (subAccountRelatedItems == null) {
                subAccountRelatedItems = new ArrayList<HostingSubAccountRelatedItem>();
                resultMap.put(relatedItem.getSubAccountId(), subAccountRelatedItems);
            }
            subAccountRelatedItems.add(relatedItem);
        }
        return resultMap;
    }
    
    public Map<Integer, List<HostingSubAccountRelatedItem>> getItemsBySubUserIds(Set<Integer> subUserIds) throws SQLException {
        Preconditions.checkNotNull(subUserIds);
        Preconditions.checkArgument(!subUserIds.isEmpty());
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_user_id", subUserIds);
        
        List<HostingSubAccountRelatedItem> relatedItems = super.getItemList(queryBuilder);
        Map<Integer, List<HostingSubAccountRelatedItem>> resultMap = new HashMap<Integer, List<HostingSubAccountRelatedItem>>();
        for (HostingSubAccountRelatedItem relatedItem : relatedItems) {
            List<HostingSubAccountRelatedItem> subUserRelatedItems = resultMap.get(relatedItem.getSubUserId());
            if (subUserRelatedItems == null) {
                subUserRelatedItems = new ArrayList<HostingSubAccountRelatedItem>();
                resultMap.put(relatedItem.getSubUserId(), subUserRelatedItems);
            }
            subUserRelatedItems.add(relatedItem);
        }
        return resultMap;
    }
    
    public int addRelatedItem(HostingSubAccountRelatedItem relatedItem) throws SQLException {
        Preconditions.checkNotNull(relatedItem);
        Preconditions.checkArgument(relatedItem.getSubAccountId() > 0);
        Preconditions.checkArgument(relatedItem.getSubUserId() > 0);
        
        PreparedFields pf = new PreparedFields();
        pf.addLong("Fsub_account_id", relatedItem.getSubAccountId());
        pf.addInt("Fsub_user_id", relatedItem.getSubUserId());
        pf.addInt("Frelated_timestamp", (int)(System.currentTimeMillis()/1000));
        pf.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        if (relatedItem.isSetSubAccountName()) {
            pf.addString("Fsub_account_name", relatedItem.getSubAccountName());
        }
        if (relatedItem.isSetSubUserLoginName()) {
            pf.addString("Fsub_user_login_name", relatedItem.getSubUserLoginName());
        }
        if (relatedItem.isSetSubUserNickName()) {
            pf.addString("Fsub_user_nick_name", relatedItem.getSubUserNickName());
        }
       
        return super.insert(pf);
    }
    
    public int deleteRelatedItem(long subAccountId, int subUserId) throws SQLException {
        return super.delete("Fsub_account_id=? AND Fsub_user_id=?", subAccountId, subUserId);
    }
    
    public int updateSubAccountInfo(HostingSubAccount updateSubAccount) throws SQLException {
        Preconditions.checkNotNull(updateSubAccount);
        Preconditions.checkArgument(updateSubAccount.getSubAccountId() > 0);
        
        PreparedFields pf = new PreparedFields();
        if (updateSubAccount.isSetSubAccountName()) {
            pf.addString("Fsub_account_name", updateSubAccount.getSubAccountName());
        }
        pf.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.update(pf, "Fsub_account_id=?", updateSubAccount.getSubAccountId());
    }
    
    public int updateSubUserInfo(HostingUser updateUser) throws SQLException {
        Preconditions.checkNotNull(updateUser);
        Preconditions.checkArgument(updateUser.getSubUserId() > 0);
        
        PreparedFields pf = new PreparedFields();
        if (updateUser.isSetLoginName()) {
            pf.addString("Fsub_user_login_name", updateUser.getLoginName());
        }
        if (updateUser.isSetNickName()) {
            pf.addString("Fsub_user_nick_name", updateUser.getNickName());
        }
        
        pf.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.update(pf, "Fsub_user_id=?", updateUser.getSubUserId());
    }

    @Override
    public HostingSubAccountRelatedItem fromResultSet(ResultSet rs) throws Exception {
        HostingSubAccountRelatedItem relatedItem = new HostingSubAccountRelatedItem();
        relatedItem.setSubAccountId(rs.getLong("Fsub_account_id"));
        relatedItem.setSubUserId(rs.getInt("Fsub_user_id"));
        relatedItem.setRelatedTimestamp(rs.getInt("Frelated_timestamp"));
        relatedItem.setSubAccountName(rs.getString("Fsub_account_name"));
        relatedItem.setSubUserLoginName(rs.getString("Fsub_user_login_name"));
        relatedItem.setSubUserNickName(rs.getString("Fsub_user_nick_name"));
        relatedItem.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
        return relatedItem;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (10 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Frelated_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_account_name VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fsub_user_login_name VARCHAR(32) NOT NULL DEFAULT '',")
                            .append("Fsub_user_nick_name VARCHAR(32) NOT NULL DEFAULT '',")
                            .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Fsub_account_id, Fsub_user_id)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "thosting_sub_account_relate";
    }

}
