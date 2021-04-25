package org.soldier.platform.id_maker_dao.server.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.DbUtil;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.server.IdMakerDaoAdaptor;
import org.soldier.platform.id_maker_dao.storage.IdMakerDB;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class IdMakerDaoHandler extends IdMakerDaoAdaptor{
	private final String[] tableFields = {"Fsect", "Fid", 
			"Falloc_size", "Fdesc", "Fcreate_timestamp", "Flastmodify_timestamp"};
	
	private String roleName;
	
	private IdMakerInfo fromResultSet(ResultSet rs) throws SQLException {
		IdMakerInfo item = new IdMakerInfo();
		item.setType(rs.getInt("Fsect"));
		item.setId(rs.getLong("Fid"));
		item.setAllocSize(rs.getInt("Falloc_size"));
		item.setDesc(rs.getString("Fdesc"));
		item.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		item.setLastmodifTimestamp(rs.getInt("Flastmodify_timestamp"));
		return item;
	}
	
	@Override
	public int InitApp(Properties props) {
		return 0;
	}

	@Override
	protected IdAllocResult allocIds(TServiceCntl oCntl, int type)
		  throws ErrorInfo, TException{
		if (type == 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "type should not be 0");
		}
		
		Connection conn = null;
		Statement statement = null;
		try {
			conn = IdMakerDB.Global().getConnection();
			
			SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
			queryBuilder.addFields("Fid,Falloc_size");
			queryBuilder.addTables("tallocid_bysect");
			queryBuilder.addCondition(ConditionType.AND, "Fsect=" + type);
			
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			boolean isSuccess = false;
			try {
				ResultSet rs = null;
				String querySql = queryBuilder.getItemsSql() + " FOR UPDATE ";
				rs = statement.executeQuery(querySql);
				
				if (rs.next()) {
					long result = rs.getLong("Fid");
					int allocSize = rs.getInt("Falloc_size");
					
					if (allocSize <= 0) {
						conn.rollback();
						throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), 
								"allocSize from db <= 0, that is not right!");
					}

					StringBuffer updateBuffer = new StringBuffer(128);
					updateBuffer.append("UPDATE ");
					updateBuffer.append("tallocid_bysect");
					updateBuffer.append(" SET Fid=Fid + ");
					updateBuffer.append(allocSize);
					updateBuffer.append(", Flastmodify_timestamp=");
					updateBuffer.append((int)(System.currentTimeMillis() / 1000));
					updateBuffer.append(" WHERE Fsect=");
					updateBuffer.append(type);
					statement.executeUpdate(updateBuffer.toString());
					
					isSuccess = true;
					return new IdAllocResult(result, allocSize);
				} else {
					throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
							"Type " + type + " Is Not Registered!");
				}
			} finally {
				if (isSuccess) {
					conn.commit();
				} else {
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(), 
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(conn);
		}
	}
	
	@Override
	public void destroy() {
	}

	@Override
	protected void registerType(TServiceCntl oCntl, IdMakerInfo info)
			throws ErrorInfo, TException {
		if (info.getType() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "info's type is wrong");
		}
		if (!info.isSetId() || info.getId() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "info's id is wrong");
		}
		if (!info.isSetAllocSize() || info.getAllocSize() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "info's allocSize is wrong");
		}
		if (!info.isSetDesc()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), 
					"please set desc");
		}
		
		try {
			QueryRunner runner = new QueryRunner(IdMakerDB.Global());
			
			PreparedFields fields = new PreparedFields();
			fields.addInt("Fsect" , info.getType());
			fields.addLong("Fid", info.getId());
			fields.addInt("Falloc_size", info.getAllocSize());
			fields.addString("Fdesc", info.getDesc());
			fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
			fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
			
			StringBuffer sqlInsert = new StringBuffer(128);
			sqlInsert.append(" INSERT INTO ");
			sqlInsert.append("tallocid_bysect");
			sqlInsert.append(" SET ");
			sqlInsert.append(fields.getPreparedSql());
			
			runner.update(sqlInsert.toString(), fields.getParameters());
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(), 
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected IdMakerInfo getIdMakerInfoByType(TServiceCntl oCntl, int type)
			throws ErrorInfo, TException {
		Connection conn = null;
		try {
			conn = IdMakerDB.Global().getConnection();
			IdMakerInfo result = _queryIdMakerInfo(conn, type);
			if (result == null) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(), 
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
			return result;
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		} finally {
			DbUtil.closeQuietly(conn);
		}
	}
	
	private IdMakerInfo _queryIdMakerInfo(Connection conn, int type) throws SQLException {
		IdMakerInfo result = null;
		SqlQueryBuilder queryBuilder =  new SqlQueryBuilder();
		queryBuilder.addFields(tableFields);
		queryBuilder.addTables( "tallocid_bysect");
		queryBuilder.addFieldCondition(ConditionType.AND, "Fsect=?" , type);
		
		QueryRunner runner = new QueryRunner();
		result = runner.query(conn, queryBuilder.getItemsSql(), 
				new ResultSetHandler<IdMakerInfo>(){
				@Override
				public IdMakerInfo handle(ResultSet rs)
						throws SQLException {
					if (!rs.next()) {
						return null;
					}
					return fromResultSet(rs);
				}
		}, queryBuilder.getParameterList());
			
		return result;
	}
	
	@Override
	protected void updateType(TServiceCntl oCntl, IdMakerInfo info)
			throws ErrorInfo, TException {
		if (!info.isSetType() || info.getType() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"info should set right type information");
		}
		
		PreparedFields fields = new PreparedFields();
		if (info.isSetDesc()) {
			fields.addString("Fdesc", info.getDesc());
		}
		if (info.isSetAllocSize()) {
			fields.addInt("Falloc_size", info.getAllocSize());
		}
		if (fields.getSize() == 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"Please set desc or alloc_size");
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer updateBuffer = new StringBuffer(128);
		updateBuffer.append("UPDATE ");
		updateBuffer.append("tallocid_bysect");
		updateBuffer.append(" SET ");
		updateBuffer.append(fields.getPreparedSql());
		updateBuffer.append(" WHERE Fsect=" + info.getType());
		
		QueryRunner runner = new QueryRunner(IdMakerDB.Global());
		
		try {
			runner.update(updateBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected IdMakerInfoList queryIdMakerTypeInfoList(TServiceCntl oCntl,
													   int pageIndex, int pageSize, IdMakerQueryOption option) throws ErrorInfo, TException {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageIndex should not < 0");
		}
		if (pageSize <= 0 || pageSize > 100) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageSize should not <= 0 or > 100");
		}
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.addFields(tableFields);
		builder.addTables("tallocid_bysect");
		builder.setPage(pageIndex, pageSize);
		builder.setOrder(OrderType.ASC, "Fsect");
		if (option.isSetType()) {
			builder.addFieldCondition(ConditionType.AND, 
					"Fsect=?", option.getType());
		} else if (option.isSetDesc()) {
			builder.addFieldCondition(ConditionType.AND,
					"Fdesc like ?", "%" + option.getDesc() + "%");
		}
		
		QueryRunner runner = new QueryRunner(IdMakerDB.Global());
		try {
			IdMakerInfoList result = new IdMakerInfoList();
			result.setTotalCount(
					runner.query(builder.getTotalCountSql(), 
						new ScalarHandler<Long>(1), builder.getParameterList()).intValue());
			
			result.setResultList(
					runner.query(builder.getItemsSql(), 
						new AbstractListHandler<IdMakerInfo>() {
							public IdMakerInfo handleRow(ResultSet rs) throws SQLException {
								return fromResultSet(rs);
							}
					}, builder.getParameterList()));
			return result;
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}
}
