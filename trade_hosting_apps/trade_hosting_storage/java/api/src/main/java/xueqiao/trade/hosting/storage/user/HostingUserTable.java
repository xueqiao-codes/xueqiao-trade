package xueqiao.trade.hosting.storage.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.AES;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.HostingUserOrderType;
import xueqiao.trade.hosting.HostingUserState;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;

public class HostingUserTable extends TableHelper<HostingUser> implements IDBTable {
	
	public HostingUserTable(Connection conn) {
		super(conn);
	}

	private static final String PROGRAM_AES_KEY = "$#program_key#$!";
	
	public PageResult<HostingUser> getHostingUserPage(QueryUserOption userOption, PageOption pageOption)  throws SQLException {
		Preconditions.checkNotNull(pageOption);
		
		SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();
		if (userOption != null) {
			if (userOption.getSubUserId() > 0) {
				sqlBuilder.addFieldCondition(ConditionType.AND
						, "Fsub_user_id=?", userOption.getSubUserId());
			}
			if (!StringUtils.isEmpty(userOption.getLoginNameWhole())) {
				sqlBuilder.addFieldCondition(ConditionType.AND
						, "Flogin_name=?", userOption.getLoginNameWhole());
			}
			if (!StringUtils.isEmpty(userOption.getLoginNamePartical())) {
				sqlBuilder.addFieldCondition(ConditionType.AND
						, "Flogin_name like ?", "%"+ userOption.getLoginNamePartical() + "%");
			}
			if (!StringUtils.isEmpty(userOption.getNickNameWhole())) {
				sqlBuilder.addFieldCondition(ConditionType.AND
						, "Fnick_name=?", userOption.getNickNameWhole());
			}
			if (!StringUtils.isEmpty(userOption.getNickNamePartical())) {
				sqlBuilder.addFieldCondition(ConditionType.AND
						, "Fnick_name like ?", "%" + userOption.getNickNamePartical() + "%");
			}
			if (userOption.getBatchSubUserIds() != null && !userOption.getBatchSubUserIds().isEmpty()) {
			    sqlBuilder.addInFieldCondition(ConditionType.AND, "Fsub_user_id", userOption.getBatchSubUserIds());
			}
			
			if (userOption.getOrder() != null){
				if (userOption.getOrder() == HostingUserOrderType.OrderByLoginNameAsc) {
					sqlBuilder.setOrder(OrderType.ASC, "Flogin_name");
				} else if (userOption.getOrder() == HostingUserOrderType.OrderByLoginNameDesc){
					sqlBuilder.setOrder(OrderType.DESC, "Flogin_name");
				} else if (userOption.getOrder() == HostingUserOrderType.OrderByCreateTimestampAsc) {
					sqlBuilder.setOrder(OrderType.ASC, "Fcreate_timestamp");
				} else if (userOption.getOrder() == HostingUserOrderType.OrderByLoginNameDesc) {
					sqlBuilder.setOrder(OrderType.DESC, "Flastmodify_timestamp");
				}
			}
		}
		sqlBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
		
		PageResult<HostingUser> resultPage = new PageResult<HostingUser>();
		if (pageOption.isNeedTotalCount()) {
			resultPage.setTotalCount(super.getTotalNum(sqlBuilder));
		}
		resultPage.setPageList(super.getItemList(sqlBuilder));
		return resultPage;
	}
	
	public int getTotalCount() throws SQLException {
		return super.getTotalNum(super.prepareSqlQueryBuilder());
	}
	
	public HostingUser getUser(int subUserId, boolean forUpdate) throws SQLException {
	    SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();
	    sqlBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
	    return super.getItem(sqlBuilder, forUpdate);
	}
	
	public HostingUser getUser(int subUserId) throws SQLException {
	    return getUser(subUserId, false);
	}
	
	public int addHostingUser(HostingUser newUser) throws SQLException {
		Preconditions.checkArgument(newUser != null);
		
		Preconditions.checkArgument(newUser.isSetSubUserId() && newUser.getSubUserId() > 0);
		Preconditions.checkArgument(newUser.isSetLoginName() && !StringUtils.isEmpty(newUser.getLoginName()));
		Preconditions.checkArgument(newUser.isSetLoginPasswd() && !StringUtils.isEmpty(newUser.getLoginPasswd()));
		Preconditions.checkArgument(newUser.isSetUserRoleValue());
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Fsub_user_id", newUser.getSubUserId());
		fields.addString("Flogin_name", newUser.getLoginName().trim());
		fields.addString("Flogin_passwd", AES.encrypt(newUser.getLoginPasswd().trim(), PROGRAM_AES_KEY));
		fields.addShort("Fuser_role", newUser.getUserRoleValue());
		if (newUser.isSetPhone()) {
			fields.addString("Fphone", newUser.getPhone().trim());
		}
		if (newUser.isSetNickName()) {
			fields.addString("Fnick_name", newUser.getNickName().trim());
		}
		if (newUser.isSetEmail()) {
			fields.addString("Femail", newUser.getEmail().trim());
		}
		
		if (newUser.isSetUserState()) {
		    fields.addShort("Fuser_state", (short)newUser.getUserState().getValue());
		} else {
		    fields.addShort("Fuser_state", (short)HostingUserState.USER_NORMAL.getValue());
		}
		
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		return super.insert(fields);
	}
	
	public int updateHostingUser(HostingUser updateUser) throws SQLException {
		Preconditions.checkArgument(updateUser != null);
		
		Preconditions.checkArgument(updateUser.isSetSubUserId() && updateUser.getSubUserId() > 0);
		
		PreparedFields fields = new PreparedFields();
		if (updateUser.isSetLoginName() && StringUtils.isNotEmpty(updateUser.getLoginName())) {
			fields.addString("Flogin_name", updateUser.getLoginName());
		}
		if (updateUser.isSetLoginPasswd()) {
			fields.addString("Flogin_passwd", AES.encrypt(updateUser.getLoginPasswd().trim(), PROGRAM_AES_KEY));
		}
		if (updateUser.isSetNickName()) {
			fields.addString("Fnick_name", updateUser.getNickName());
		}
		if (updateUser.isSetPhone()) {
			fields.addString("Fphone", updateUser.getPhone());
		}
		if (updateUser.isSetEmail()) {
			fields.addString("Femail", updateUser.getEmail());
		}
		if (updateUser.isSetUserState()) {
		    fields.addShort("Fuser_state", (short)updateUser.getUserState().getValue());
		}
		if (updateUser.isSetUserRoleValue()) {
			fields.addShort("Fuser_role", updateUser.getUserRoleValue());
		}
		
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.update(fields, "Fsub_user_id=?", updateUser.getSubUserId());
	}
	
	public int deleteHostingUser(int subUserId) throws SQLException {
		return super.delete("Fsub_user_id=?", subUserId);
	}
	
	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder sqlBuilder = new StringBuilder(128);
			sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
			          .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flogin_name VARCHAR(32) NOT NULL DEFAULT '' UNIQUE,")
			          .append("Flogin_passwd VARCHAR(128) NOT NULL DEFAULT '',")
			          .append("Fphone VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fnick_name VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fuser_role SMALLINT NOT NULL DEFAULT 0,")
			          .append("Femail VARCHAR(64) NOT NULL DEFAULT '',")
			          .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("PRIMARY KEY(Fsub_user_id)) ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(sqlBuilder.toString());
		}
		if (8 == targetVersion) {
		    if (!operator.colunmExist(getTableName(), "Fuser_state")) {
		        StringBuilder alterSqlBuilder = new StringBuilder(128);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                       .append(" ADD COLUMN Fuser_state SMALLINT UNSIGNED NOT NULL DEFAULT 1;");
                operator.execute(alterSqlBuilder.toString());
		    }
		}
	}

	@Override
	public HostingUser fromResultSet(ResultSet rs) throws Exception {
		HostingUser user = new HostingUser();
		user.setSubUserId(rs.getInt("Fsub_user_id"));
		user.setLoginPasswd(AES.decrypt(rs.getString("Flogin_passwd"), PROGRAM_AES_KEY));
		user.setLoginName(rs.getString("Flogin_name"));
		user.setPhone(rs.getString("Fphone"));
		user.setNickName(rs.getString("Fnick_name"));
		user.setEmail(rs.getString("Femail"));
		user.setUserRoleValue(rs.getShort("Fuser_role"));
		user.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		user.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		user.setUserState(HostingUserState.findByValue(rs.getShort("Fuser_state")));
		return user;
	}

	@Override
	protected String getTableName() throws SQLException {
		return "thosting_user";
	}

}
