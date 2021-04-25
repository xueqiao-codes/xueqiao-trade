package xueqiao.trade.hosting.storage.compose;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewStatus;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;

public class HostingComposeViewTable extends TableHelper<HostingComposeView> implements IDBTable {

	public HostingComposeViewTable(Connection conn) {
		super(conn);
	}
	
	public Map<Integer, HostingComposeView> getUsersComposeView(
			Set<Integer> subUserIds
			, long graphId
			, HostingComposeViewStatus viewStatus) throws SQLException {
		Preconditions.checkArgument(subUserIds != null && !subUserIds.isEmpty());
		Preconditions.checkArgument(graphId > 0);
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_user_id", subUserIds);
		queryBuilder.addFieldCondition(ConditionType.AND, "Fgraph_id=?", graphId);
		if (viewStatus != null) {
			queryBuilder.addFieldCondition(ConditionType.AND
					, "Fview_status=?"
					, viewStatus.getValue());
		}
		
		List<HostingComposeView> resultComposeViews = super.getItemList(queryBuilder);
		Map<Integer, HostingComposeView> resultMap = new HashMap<>(resultComposeViews.size() + 1);
		for (HostingComposeView composeView : resultComposeViews) {
			resultMap.put(composeView.getSubUserId(), composeView);
		}
		return resultMap;
	}

	public Map<Long, HostingComposeView> getComposeViewBySubUserId(
			int subUserId
			, Set<Long> composeGraphIds
			, HostingComposeViewStatus viewStatus) throws SQLException {
		Preconditions.checkArgument(subUserId > 0);
		Preconditions.checkArgument(composeGraphIds != null && !composeGraphIds.isEmpty());

		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
		queryBuilder.addInFieldCondition(ConditionType.AND, "Fgraph_id", composeGraphIds);
		if (viewStatus != null) {
			queryBuilder.addFieldCondition(ConditionType.AND, "Fview_status=?", viewStatus.getValue());
		}

		List<HostingComposeView>  composeViewList = super.getItemList(queryBuilder);
		Map<Long, HostingComposeView> resultMap = new HashMap<>(composeViewList.size() + 1);
		for (HostingComposeView composeView : composeViewList) {
			resultMap.put(composeView.getComposeGraphId(), composeView);
		}

		return resultMap;
	}
	
	public PageResult<HostingComposeView> getComposeViewPage(QueryComposeViewOption qryOption
			, PageOption pageOption) throws SQLException {
		Preconditions.checkNotNull(pageOption != null);
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		if (qryOption != null) {
			if (qryOption.getSubUserId() > 0) {
				queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", qryOption.getSubUserId());
			}
			if (qryOption.getGraphId() > 0) {
				queryBuilder.addFieldCondition(ConditionType.AND, "Fgraph_id=?", qryOption.getGraphId());
			}
			if (StringUtils.isNotEmpty(qryOption.getNamePartical())) {
				queryBuilder.addFieldCondition(ConditionType.AND, "Falias_name like ?"
						, "%" + qryOption.getNamePartical() + "%");
			}
			if (qryOption.getSubscribeStatus() != null) {
				queryBuilder.addFieldCondition(ConditionType.AND, "Fsubscribe_status=?", qryOption.getSubscribeStatus().getValue());
			}
			if (qryOption.getViewStatus() != null) {
				queryBuilder.addFieldCondition(ConditionType.AND
						, "Fview_status=?"
						, (byte)qryOption.getViewStatus().getValue());
			}
		}
		queryBuilder.setOrder(OrderType.ASC, "Fcreate_timestamp");
		queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
		
		PageResult<HostingComposeView> pageResult = new PageResult<HostingComposeView>();
		if (pageOption.isNeedTotalCount()) {
			pageResult.setTotalCount(super.getTotalNum(queryBuilder));
		}
		pageResult.setPageList(super.getItemList(queryBuilder));
		return pageResult;
	}
	
	public int getSubscribedViewCount(int subUserId) throws SQLException {
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
		queryBuilder.addFieldCondition(ConditionType.AND, "Fsubscribe_status=?"
				, HostingComposeViewSubscribeStatus.SUBSCRIBED.getValue());
		queryBuilder.addFieldCondition(ConditionType.AND, "Fview_status=?"
				, HostingComposeViewStatus.VIEW_NORMAL.getValue());
		return super.getTotalNum(queryBuilder);
	}
	
	public HostingComposeView getComposeView(int subUserId, long graphId, boolean forUpdate) throws SQLException {
		Preconditions.checkArgument(subUserId > 0);
		Preconditions.checkArgument(graphId > 0);
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
		queryBuilder.addFieldCondition(ConditionType.AND, "Fgraph_id=?", graphId);
		return super.getItem(queryBuilder, forUpdate);
	}
	
	public int addComposeView(HostingComposeView newView) throws SQLException {
		Preconditions.checkNotNull(newView);
		Preconditions.checkArgument(newView.getSubUserId() > 0);
		Preconditions.checkArgument(newView.getComposeGraphId() > 0);
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Fsub_user_id", newView.getSubUserId());
		fields.addLong("Fgraph_id", newView.getComposeGraphId());
		if (newView.isSetAliasName()) {
			fields.addString("Falias_name", newView.getAliasName());
		}
		if (newView.isSetViewSource()) {
			fields.addByte("Fview_source", (byte)newView.getViewSource().getValue());
		}
		if (newView.isSetSubscribeStatus()) {
			fields.addByte("Fsubscribe_status", (byte)newView.getSubscribeStatus().getValue());
		}
		if (newView.isSetViewStatus()) {
			fields.addByte("Fview_status", (byte)newView.getViewStatus().getValue());
		}
		if (newView.isSetPrecisionNumber()) {
			fields.addShort("Fprecision_num", newView.getPrecisionNumber());
		}

		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.insert(fields);
	}
	
	public int updateComposeView(HostingComposeView updateView) throws SQLException {
		Preconditions.checkNotNull(updateView);
		Preconditions.checkArgument(updateView.getSubUserId() > 0);
		Preconditions.checkArgument(updateView.getComposeGraphId() > 0);
		
		PreparedFields fields = new PreparedFields();
		if (updateView.isSetAliasName()) {
			fields.addString("Falias_name", updateView.getAliasName());
		}
		if (updateView.isSetSubscribeStatus()) {
			fields.addByte("Fsubscribe_status", (byte)updateView.getSubscribeStatus().getValue());
		}
		if (updateView.isSetViewStatus()) {
			fields.addByte("Fview_status", (byte)updateView.getViewStatus().getValue());
		}
		if (updateView.isSetPrecisionNumber()) {
			fields.addShort("Fprecision_num", updateView.getPrecisionNumber());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.update(fields, "Fsub_user_id=? AND Fgraph_id=?", updateView.getSubUserId(), updateView.getComposeGraphId());
	}
	
	public int deleteComposeView(int subUserId, long graphId) throws SQLException {
		Preconditions.checkArgument(subUserId > 0);
		Preconditions.checkArgument(graphId > 0);

		return super.delete("Fsub_user_id=? AND Fgraph_id=?", subUserId, graphId);
	}

	@Override
	public HostingComposeView fromResultSet(ResultSet rs) throws Exception {
		HostingComposeView resultView = new HostingComposeView();
		resultView.setSubUserId(rs.getInt("Fsub_user_id"));
		resultView.setComposeGraphId(rs.getLong("Fgraph_id"));
		resultView.setAliasName(rs.getString("Falias_name"));
		resultView.setSubscribeStatus(HostingComposeViewSubscribeStatus.findByValue(rs.getByte("Fsubscribe_status")));
		resultView.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		resultView.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		resultView.setViewStatus(HostingComposeViewStatus.findByValue(rs.getByte("Fview_status")));
		resultView.setPrecisionNumber(rs.getShort("Fprecision_num"));
		return resultView;
	}

	@Override
	protected String getTableName() throws SQLException {
		return "tcompose_view";
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder sqlBuilder = new StringBuilder(128);
			sqlBuilder.append("CREATE TABLE ").append(getTableName()).append("(")
			          .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fgraph_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Falias_name VARCHAR(64) NOT NULL DEFAULT '',")
			          .append("Fview_source TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fsubscribe_status TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("PRIMARY KEY(Fsub_user_id, Fgraph_id)) ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(sqlBuilder.toString());
			return ;
		}
		if (2 == targetVersion) {
			if (!operator.colunmExist(getTableName(), "Fview_status")) {
				StringBuilder viewStatusSqlBuilder = new StringBuilder(128);
				viewStatusSqlBuilder.append("ALTER TABLE ").append(getTableName())
						.append(" ADD COLUMN Fview_status TINYINT NOT NULL DEFAULT 0;");
				operator.execute(viewStatusSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fprecision_num")) {
				StringBuilder precisionNumSqlBuilder = new StringBuilder(128);
				precisionNumSqlBuilder.append("ALTER TABLE ").append(getTableName())
						.append(" ADD COLUMN Fprecision_num SMALLINT NOT NULL DEFAULT 2;");
				operator.execute(precisionNumSqlBuilder.toString());
			}
		}
	}

}
