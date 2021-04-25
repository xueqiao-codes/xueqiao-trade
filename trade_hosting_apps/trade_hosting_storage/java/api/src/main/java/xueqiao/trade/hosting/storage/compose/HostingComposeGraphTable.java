package xueqiao.trade.hosting.storage.compose;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeGraphEnv;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

/**
 *  组合图描述表
 * @author wileywang
 */
public class HostingComposeGraphTable extends TableHelper<HostingComposeGraph> implements IDBTable {
	public HostingComposeGraphTable(Connection conn) {
		super(conn);
	}
	
	private String legsToText(Map<String, HostingComposeLeg> legs) throws SQLException {
		try {
			return ThriftExtJsonUtils.mapToJsonTBase(legs);
		} catch (TException e) {
			throw new SQLException(e);
		}
	}
	
	private Map<String, HostingComposeLeg> legsFromText(String text) throws SQLException {
		try {
			return ThriftExtJsonUtils.mapFromJsonTBase(text, HostingComposeLeg.class);
		} catch (Throwable e) {
			throw new SQLException(e);
		}
	}
	
	private String specValues2Str(List<Double> specValues) {
		StringBuilder builder = new StringBuilder(256);
		for (int index = 0; index < specValues.size(); ++index) {
			if (index > 0) {
				builder.append(",");
			}
			builder.append(
					new BigDecimal(specValues.get(index).toString()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		return builder.toString();
	}
	
	
	public HostingComposeGraph getComposeGraph(
			long graphId, boolean forUpdate) throws SQLException {
		Preconditions.checkArgument(graphId > 0);
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND, "Fgraph_id=?", graphId);
		return super.getItem(queryBuilder, forUpdate);
	}
	
	public Map<Long, HostingComposeGraph> getComposeGraphs(Set<Long> graphIds) throws SQLException {
		Preconditions.checkNotNull(graphIds);
		Preconditions.checkArgument(!graphIds.isEmpty());
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addInFieldCondition(ConditionType.AND, "Fgraph_id", graphIds);
		List<HostingComposeGraph> composeGraphs = super.getItemList(queryBuilder);
		Map<Long, HostingComposeGraph> resultMap = new HashMap<Long, HostingComposeGraph>();
		for (HostingComposeGraph composeGraph : composeGraphs) {
			resultMap.put(composeGraph.getComposeGraphId(), composeGraph);
		}
		return resultMap;
	}
	
	public PageResult<HostingComposeGraph> getSameComposeGraphPage(HostingComposeGraph graph, PageOption pageOption) throws SQLException  {
		Preconditions.checkNotNull(graph);
		Preconditions.checkNotNull(pageOption);
		Preconditions.checkArgument(graph.isSetFormular() && StringUtils.isNotEmpty(graph.getFormular()));
		Preconditions.checkArgument(graph.isSetLegs() && !graph.getLegs().isEmpty());
		
		HostingComposeGraphCalculator calculator = new HostingComposeGraphCalculator(graph);
		calculator.calculate();
		
		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addFieldCondition(ConditionType.AND
				, "Fleg_sled_contract_ids=?", StringUtils.join(calculator.getLegSledContractIds(), ","));
		queryBuilder.addFieldCondition(ConditionType.AND
				, "Fspec_values=?", specValues2Str(calculator.getSepcValues()));
		queryBuilder.addFieldCondition(ConditionType.AND
				, "Fleg_trade_ratios=?", StringUtils.join(calculator.getLegTradeRatios(), ","));
		queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
		queryBuilder.setOrder(OrderType.ASC, "Fcreate_timestamp");
		
		PageResult<HostingComposeGraph> result = new PageResult<HostingComposeGraph>();
		if (pageOption.isNeedTotalCount()) {
			result.setTotalCount(super.getTotalNum(queryBuilder));
		}
		result.setPageList(super.getItemList(queryBuilder));
		return result;
	}
	
	public int addComposeGraph(HostingComposeGraph graph) throws SQLException {
		Preconditions.checkNotNull(graph);
		Preconditions.checkArgument(graph.getComposeGraphId() > 0);
		Preconditions.checkArgument(graph.getCreateSubUserId() > 0);
		Preconditions.checkArgument(graph.isSetFormular() && StringUtils.isNotEmpty(graph.getFormular()));
		Preconditions.checkArgument(graph.isSetLegs() && !graph.getLegs().isEmpty());
		Preconditions.checkArgument(graph.isSetComposeGraphEnv());
		Preconditions.checkArgument(graph.isSetComposeGraphKey());
		
		HostingComposeGraphCalculator calculator = new HostingComposeGraphCalculator(graph);
		calculator.calculate();
		
		PreparedFields fields = new PreparedFields();
		fields.addLong("Fgraph_id", graph.getComposeGraphId());
		fields.addInt("Fcreate_sub_user_id", graph.getCreateSubUserId());
		fields.addString("Fformular", graph.getFormular());
		
		fields.addString("Fleg_sled_contract_ids", StringUtils.join(calculator.getLegSledContractIds(), ","));
		fields.addString("Flegs", legsToText(graph.getLegs()));
		fields.addString("Fspec_values", specValues2Str(calculator.getSepcValues()));
		fields.addString("Fleg_trade_ratios", StringUtils.join(calculator.getLegTradeRatios(), ","));
		fields.addString("Fgraph_key", graph.getComposeGraphKey());
		fields.addByte("Fgraph_env", (byte)graph.getComposeGraphEnv().getValue());
		
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.insert(fields);
	}
	
	public int deleteComposeGraph(long graphId) throws SQLException {
		Preconditions.checkArgument(graphId > 0);
		return super.delete("Fgraph_id=?", graphId);
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder graphSqlBuilder = new StringBuilder(128);
			graphSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
					  .append("Fgraph_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fcreate_sub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fformular VARCHAR(600) NOT NULL DEFAULT '',")
					  .append("Flegs TEXT,")
					  .append("Fleg_sled_contract_ids VARCHAR(256) NOT NULL DEFAULT '',") // 合约索引项
					  .append("Fspec_values VARCHAR(256) NOT NULL DEFAULT '',")  // 公式索引项
					  .append("Fleg_trade_ratios VARCHAR(128) NOT NULL DEFAULT '',") // 买卖配比索引项
					  .append("Fgraph_key VARCHAR(64) NOT NULL DEFAULT '',")
					  .append("Fgraph_env TINYINT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("PRIMARY KEY(Fgraph_id),")
			          .append("INDEX(Fleg_sled_contract_ids, Fspec_values, Fleg_trade_ratios)") // 
			          .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(graphSqlBuilder.toString());
		}
	}

	@Override
	public HostingComposeGraph fromResultSet(ResultSet rs) throws Exception {
		HostingComposeGraph graph = new HostingComposeGraph();
		graph.setComposeGraphId(rs.getLong("Fgraph_id"));
		graph.setCreateSubUserId(rs.getInt("Fcreate_sub_user_id"));
		graph.setFormular(rs.getString("Fformular"));
		graph.setLegs(legsFromText(rs.getString("Flegs")));
		graph.setComposeGraphKey(rs.getString("Fgraph_key"));
		graph.setComposeGraphEnv(HostingComposeGraphEnv.findByValue(rs.getByte("Fgraph_env")));
		graph.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		graph.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return graph;
	}

	@Override
	protected String getTableName() throws SQLException {
		return "tcompose_graph";
	}

}
