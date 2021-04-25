package xueqiao.trade.hosting.storage.tradeaccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.AES;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountAccessState;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;

public class HostingTradeAccountTable extends TableHelper<HostingTradeAccount> implements IDBTable {
	public HostingTradeAccountTable(Connection conn) {
		super(conn);
	}
	
	private static final int REMARK_LENGTH = 32;

	private static final String INNER_PASSWD_KEY = "account_key##^_^";
	
	private Set<Short> statusSetValue(Set<TradeAccountState> states) {
		Set<Short> resultSet = new HashSet<Short>();
		Iterator<TradeAccountState> it = states.iterator();
		while(it.hasNext()) {
			resultSet.add((short)it.next().getValue());
		}
		return resultSet;
	}
	
	public HostingTradeAccount getTradeAccount(long tradeAccountId
			, boolean forUpdate) throws SQLException {
		Preconditions.checkArgument(tradeAccountId > 0);
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND, "Ftrade_account_id=?", tradeAccountId);
		
		return super.getItem(queryBuilder, forUpdate);
	}
	
	public List<HostingTradeAccount> getAllTradeAccounts() throws SQLException {
		return super.getItemList(super.prepareSqlQueryBuilder());
	}
	
	public PageResult<HostingTradeAccount> queryTradeAccountPage(
			QueryTradeAccountOption queryOption, PageOption pageOption) throws SQLException {
	    Preconditions.checkNotNull(pageOption);
	    
		SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();
		if (queryOption != null) {
			if (queryOption.getTradeBrokerId() > 0) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Ftrade_broker_id=?", queryOption.getTradeBrokerId());
			}
			if (StringUtils.isNotBlank(queryOption.getLoginUserNamePartical())) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Flogin_user_name like ?", 
						"%" + queryOption.getLoginUserNamePartical().trim() + "%");
			}
			if (StringUtils.isNotBlank(queryOption.getLoginUserNameWhole())) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Flogin_user_name=?"
						, queryOption.getLoginUserNameWhole().trim());
			}
			if (queryOption.getTradeBrokerTechPlatform() != null) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Fbroker_tech_platform=?"
						, queryOption.getTradeBrokerTechPlatform().getValue());
			}
			if (queryOption.getTradeBrokerAccessId() > 0) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Ftrade_broker_access_id=?"
						, queryOption.getTradeBrokerAccessId());
			}
			if (queryOption.getTradeAccountId() > 0) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Ftrade_account_id=?"
						, queryOption.getTradeAccountId());
			}
			if (queryOption.getInStates() != null && queryOption.getInStates().size() > 0) {
				sqlBuilder.addInFieldCondition(ConditionType.AND, "Faccount_state"
						, statusSetValue(queryOption.getInStates()));
			}
			if (queryOption.getNotInStates() != null && queryOption.getNotInStates().size() > 0) {
				sqlBuilder.addNotInFieldCondition(ConditionType.AND, "Faccount_state"
						, statusSetValue(queryOption.getNotInStates()));
			}
		}
		
		sqlBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
		sqlBuilder.setOrder(OrderType.DESC, "Fcreate_timestamp");
		
		PageResult<HostingTradeAccount> resultPage = new PageResult<HostingTradeAccount>();
		if (pageOption.isNeedTotalCount()) {
			resultPage.setTotalCount(super.getTotalNum(sqlBuilder));
		}
		
		resultPage.setPageList(super.getItemList(sqlBuilder));
		return resultPage;
	}
	
	public BrokerAccessEntry getBrokerAccessEntry(long tradeAccountId) throws SQLException {
		Preconditions.checkArgument(tradeAccountId > 0);
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.addFields("Ftrade_account_id", "Ftrade_broker_access_entry");
		builder.addTables(getTableName());
		builder.addFieldCondition(ConditionType.AND, "Ftrade_account_id=?", tradeAccountId);
		
		return new QueryRunner().query(getConnection(), builder.getItemsSql()
				,new ResultSetHandler<BrokerAccessEntry>() {
					@Override
					public BrokerAccessEntry handle(ResultSet rs) throws SQLException {
						if (rs.next()) {
							try {
								return ThriftExtJsonUtils.fromJsonTBase(
									rs.getString("Ftrade_broker_access_entry"), BrokerAccessEntry.class);
							} catch (Throwable e) {
								throw new SQLException(e);
							}
						}
						return null;
					}
		}, builder.getParameterList());
	}
	
	public int updateBrokerAccessEntry(long tradeAccountId, BrokerAccessEntry updateAccessEntry) throws SQLException {
		Preconditions.checkArgument(tradeAccountId > 0);
		Preconditions.checkNotNull(updateAccessEntry);
		
		PreparedFields fields = new PreparedFields();
		try {
			fields.addString("Ftrade_broker_access_entry"
				, ThriftExtJsonUtils.toJsonTBase(updateAccessEntry));
		} catch (Throwable e) {
			throw new SQLException(e);
		}
		
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		return super.update(fields, "Ftrade_account_id=?", tradeAccountId);
	}
	
	public int addTradeAccount(HostingTradeAccount account
			, BrokerAccessEntry initedAccessEntry) throws SQLException {
		Preconditions.checkNotNull(account);
		Preconditions.checkArgument(account.getTradeAccountId() > 0);
		Preconditions.checkArgument(account.getTradeBrokerAccessId() > 0);
		Preconditions.checkArgument(StringUtils.isNotBlank(account.getLoginUserName()));
		Preconditions.checkArgument(StringUtils.isNotBlank(account.getLoginPassword()));
		Preconditions.checkNotNull(account.getBrokerTechPlatform());
		Preconditions.checkArgument(account.getTradeBrokerId() > 0);
		Preconditions.checkNotNull(initedAccessEntry);
		
		PreparedFields fields = new PreparedFields();
		fields.addLong("Ftrade_account_id", account.getTradeAccountId());
		fields.addInt("Ftrade_broker_access_id", account.getTradeBrokerAccessId());
		fields.addString("Flogin_user_name", account.getLoginUserName());
		fields.addString("Flogin_password", AES.encrypt(account.getLoginPassword(), INNER_PASSWD_KEY));
		fields.addInt("Ftrade_broker_id", account.getTradeBrokerId());
		fields.addShort("Fbroker_tech_platform", (short)account.getBrokerTechPlatform().getValue());
		
		try {
			if (account.isSetAccountProperties()) {
				fields.addString("Faccount_properties"
					, ThriftExtJsonUtils.mapToJson(account.getAccountProperties()));
			}
			
			fields.addString("Ftrade_broker_access_entry"
					, ThriftExtJsonUtils.toJsonTBase(initedAccessEntry));
		} catch (Throwable e) {
			throw new SQLException(e);
		}
		
		if (account.isSetAccountState()) {
			fields.addShort("Faccount_state", (short)account.getAccountState().getValue());
		} else {
			fields.addShort("Faccount_state", (short)TradeAccountState.ACCOUNT_NORMAL.getValue());
		}
		
		if (account.isSetAccountAccessState()) {
			fields.addShort("Faccount_access_state", (short)account.getAccountAccessState().getValue());
		} else {
			fields.addShort("Faccount_access_state", (short)TradeAccountAccessState.ACCOUNT_INVALID.getValue());
		}
		
		if (account.isSetApiRetCode()) {
			fields.addInt("Fapi_ret_code", account.getApiRetCode());
		}
		if (account.isSetInvalidErrorCode()) {
			fields.addInt("Finvalid_error_code", account.getInvalidErrorCode());
		}
		if (account.isSetInvalidReason()) {
			fields.addString("Finvalid_reason", account.getInvalidReason());
		}
		if (account.isSetTradeAccountRemark()) {
            fields.addString("Fremark", StringUtils.left(account.getTradeAccountRemark(), REMARK_LENGTH - 1));
        }
		
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.insert(fields);
	}
	
	public int updateTradeAccount(HostingTradeAccount account, BrokerAccessEntry updateAccessEntry) throws SQLException {
		Preconditions.checkNotNull(account);
		Preconditions.checkArgument(account.getTradeAccountId() > 0);
		
		PreparedFields fields = new PreparedFields();
		if (account.isSetLoginUserName()) {
			fields.addString("Flogin_user_name", account.getLoginUserName());
		}
		if (account.isSetLoginPassword()) {
			fields.addString("Flogin_password", AES.encrypt(account.getLoginPassword(), INNER_PASSWD_KEY));
		}

		try {
			if (account.isSetAccountProperties()) {
				fields.addString("Faccount_properties"
						, ThriftExtJsonUtils.mapToJson(account.getAccountProperties()));
			}
			if (updateAccessEntry != null) {
				fields.addString("Ftrade_broker_access_entry", ThriftExtJsonUtils.toJsonTBase(updateAccessEntry));
			}
		} catch (Throwable e) {
			throw new SQLException(e);
		}

		if (account.isSetAccountState()) {
			fields.addShort("Faccount_state", (short)account.getAccountState().getValue());
		}
		if (account.isSetAccountAccessState()) {
			fields.addShort("Faccount_access_state", (short)account.getAccountAccessState().getValue());
		}
		if (account.isSetApiRetCode()) {
			fields.addInt("Fapi_ret_code", account.getApiRetCode());
		}
		if (account.isSetInvalidErrorCode()) {
			fields.addInt("Finvalid_error_code", account.getInvalidErrorCode());
		}
		if (account.isSetInvalidReason()) {
			fields.addString("Finvalid_reason", account.getInvalidReason());
		}
		if (account.isSetTradeBrokerAccessId()) {
			fields.addInt("Ftrade_broker_access_id", account.getTradeBrokerAccessId());
		}
		if (account.isSetTradeBrokerId()) {
			fields.addInt("Ftrade_broker_id", account.getTradeBrokerId());
		}
		if (account.isSetHadBeenActived()) {
			fields.addShort("Fhad_been_actived", (short)(account.isHadBeenActived() ? 1 : 0));
		}
		if (account.isSetTradeAccountRemark()) {
		    fields.addString("Fremark", StringUtils.left(account.getTradeAccountRemark(), REMARK_LENGTH - 1));
		}

		
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.update(fields, "Ftrade_account_id=?", account.getTradeAccountId());
	}
	
	public int deleteTradeAccount(long tradeAccountId) throws SQLException {
		Preconditions.checkArgument(tradeAccountId > 0);
		
		return super.delete("Ftrade_account_id=?", tradeAccountId);
	}
	
	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (5 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
					  .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Ftrade_broker_access_id INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Flogin_user_name VARCHAR(128) NOT NULL DEFAULT '',")
					  .append("Flogin_password VARCHAR(128) NOT NULL DEFAULT '',")
					  .append("Faccount_properties TEXT,")
					  .append("Ftrade_broker_id INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fbroker_tech_platform SMALLINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Faccount_state SMALLINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Finvalid_reason VARCHAR(256) NOT NULL DEFAULT '',")
					  .append("Finvalid_error_code INTEGER NOT NULL DEFAULT 0,")
					  .append("Fapi_ret_code INTEGER NOT NULL DEFAULT 0,")
					  .append("Faccount_access_state SMALLINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Ftrade_broker_access_entry TEXT,")
					  .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("PRIMARY KEY(Ftrade_account_id),")
					  .append("UNIQUE KEY unique_login_access_name(Ftrade_broker_access_id, Flogin_user_name)")
					  .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
			return ;
		}
		if (6 == targetVersion) {
		    if (!operator.colunmExist(getTableName(), "Fhad_been_actived")) {
		        StringBuilder addActivedFlagSqlBuilder = new StringBuilder(128);
		        addActivedFlagSqlBuilder.append("ALTER TABLE ").append(getTableName())
                       .append(" ADD COLUMN Fhad_been_actived TINYINT UNSIGNED NOT NULL DEFAULT 0;");
                operator.execute(addActivedFlagSqlBuilder.toString());
		    }
		    return ;
		}
		if (9 == targetVersion) {
		    if (!operator.colunmExist(getTableName(), "Fremark")) {
		        StringBuilder addRemarkSqlBuilder = new StringBuilder(128);
		        addRemarkSqlBuilder.append("ALTER TABLE ").append(getTableName())
		               .append(" ADD COLUMN Fremark VARCHAR(")
		               .append(REMARK_LENGTH)
		               .append(") NOT NULL DEFAULT '';");
		        operator.execute(addRemarkSqlBuilder.toString());
		    }
		    return ;
		}
	}

	@Override
	public HostingTradeAccount fromResultSet(ResultSet rs) throws Exception {
		HostingTradeAccount account = new HostingTradeAccount();
		account.setTradeAccountId(rs.getLong("Ftrade_account_id"));
		account.setTradeBrokerAccessId(rs.getInt("Ftrade_broker_access_id"));
		account.setLoginUserName(rs.getString("Flogin_user_name"));
		account.setLoginPassword(AES.decrypt(rs.getString("Flogin_password"), INNER_PASSWD_KEY));
		account.setTradeBrokerId(rs.getInt("Ftrade_broker_id"));
		account.setBrokerTechPlatform(BrokerTechPlatform.findByValue(rs.getShort("Fbroker_tech_platform")));
		account.setAccountProperties(ThriftExtJsonUtils.mapFromJson(rs.getString("Faccount_properties")));
		account.setAccountState(TradeAccountState.findByValue(rs.getShort("Faccount_state")));
		account.setInvalidReason(rs.getString("Finvalid_reason"));
		account.setInvalidErrorCode(rs.getInt("Finvalid_error_code"));
		account.setApiRetCode(rs.getInt("Fapi_ret_code"));
		account.setAccountAccessState(TradeAccountAccessState.findByValue(rs.getShort("Faccount_access_state")));
		account.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		account.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		account.setHadBeenActived(rs.getShort("Fhad_been_actived") > 0);
		account.setTradeAccountRemark(rs.getString("Fremark"));
		return account;
	}

	@Override
	protected String getTableName() throws SQLException {
		return "thosting_trade_account";
	}
}
