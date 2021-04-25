package xueqiao.trade.hosting.contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.Md5;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.oss.api.AliyunOssStorage;
import org.soldier.platform.oss.api.OssFailedException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.client.ContractOnlineDaoServiceStub;
import com.longsheng.xueqiao.contract.thriftapi.ContractVersion;
import com.longsheng.xueqiao.contract.thriftapi.ContractVersionPage;
import com.longsheng.xueqiao.contract.thriftapi.ReqContractVersionOption;

import xueqiao.trade.hosting.contract.api.ContractVersionEntry;
import xueqiao.trade.hosting.contract.api.IContractApi;
import xueqiao.trade.hosting.framework.DBSingleConnection;

public class ContractSyncProcessor {
	private static AliyunOssStorage OSS_STORAGE;
	private static final String STORAGE_KEY = "contract";
	
	public static class SyncResult {
        private boolean updated;
        private int newVersion = 0;
        
        public SyncResult(int version) {
            if (version > 0) {
                updated = true;
                setNewVersion(version);
            } else {
                updated = false;
            }
        }
        
        public boolean isUpdated() {
            return updated;
        }
        public void setUpdated(boolean updated) {
            this.updated = updated;
        }

        public int getNewVersion() {
            return newVersion;
        }

        public void setNewVersion(int newVersion) {
            this.newVersion = newVersion;
        }
    }
    
    private static final SyncResult NO_UPDATED_RESULT = new SyncResult(0);
	
	
	private IContractApi mContractApi;
	
	public ContractSyncProcessor() {
		mContractApi = Globals.getInstance().queryInterfaceForSure(IContractApi.class);
	}
	
	/**
	 *  触发一次同步检验 
	 */
	public SyncResult syncOnce() throws Exception {
		// 从远端获取合约库最新的版本号，和本地库对比
		ContractVersion remoteLastestVersion = getRemoteLastestVersion();
		if (remoteLastestVersion == null) {
			AppLog.i("remote has no lastest version");
			return NO_UPDATED_RESULT;
		}
		if (StringUtils.isEmpty(remoteLastestVersion.getFileMD5())
				|| StringUtils.isEmpty(remoteLastestVersion.getFilePath())) {
			AppLog.i("remote file info is empty, wait remote to sync db file" );
			return NO_UPDATED_RESULT;
		}
		
		ContractVersionEntry localLastestVersion = mContractApi.getLastestVersion();
		Preconditions.checkNotNull(localLastestVersion);
		
		if (localLastestVersion.getLastestVersion() >= remoteLastestVersion.getVersionId()) {
			AppLog.i("local is lastest, local version=" + localLastestVersion.getLastestVersion()
					+ ", remote version=" + remoteLastestVersion.getVersionId());
			return NO_UPDATED_RESULT;
		}
		
		// 从远端下载文件
		File tmpSqlFile = File.createTempFile("contract_", ".sql");
		try {
			downloadSqlFile(remoteLastestVersion, tmpSqlFile);
			if (!remoteLastestVersion.getFileMD5().equalsIgnoreCase(Md5.toMD5(tmpSqlFile))){
				AppLog.e("download remote sqlfile is not equals to local, need redo!");
				return NO_UPDATED_RESULT;
			}
			
			importData(remoteLastestVersion, tmpSqlFile);
			mContractApi.updateLastestVersion(remoteLastestVersion.getVersionId());
			return new SyncResult(remoteLastestVersion.getVersionId());
		} finally {
			tmpSqlFile.delete();
		}
	}
	
	/**
	 *  清理过期DB
	 * @throws SQLException 
	 * @throws ErrorInfo 
	 */
	public void cleanOldDBs() throws SQLException, ErrorInfo {
	    dropOldDatabase(getDropDBNames());
	}
	
	private List<String> getDropDBNames() throws SQLException, ErrorInfo {
	    List<String> dbNames = getContractDataBases();
        List<String> dropDBNames = new ArrayList<String>();
	    
	    ContractVersionEntry localVersionEntry = mContractApi.getLastestVersion();
        if (localVersionEntry == null) {
            return dropDBNames;
        }
        
        for (String dbName : dbNames) {
            Connection conn = DBSingleConnection.getDbConnection(dbName);
            try {
                String[] contractDBNameSplits = StringUtils.split(dbName, '_');
                if (contractDBNameSplits == null || contractDBNameSplits.length != 2) {
                    AppLog.e("Unexpected DBName=" + dbName + " for clean..., split not right");
                    continue;
                }
                
                int opDbVersion = 0;
                try {
                    opDbVersion = Integer.parseInt(contractDBNameSplits[1]);
                } catch (Throwable e) {
                    AppLog.e("Unexpected DBName=" + dbName + " for clean..., parse version failed!");
                    continue;
                }
                
                if (opDbVersion >= localVersionEntry.getLastestVersion()) {
                    continue;
                }
                
                // 未完成的过期DB删除
                ResultSet flagTableExistRs = conn.getMetaData().getTables(null, null, "tcontract_finished_flag", null);
                try {
                    if (!flagTableExistRs.next()) {
                        dropDBNames.add(dbName);
                        continue;
                    }
                } finally {
                    DbUtils.closeQuietly(flagTableExistRs);
                }
                
                // 完成的过期DB保留3分钟
                long reserveExpiredFinishedDBTimeMs = 3*60*1000;
                
                SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
                queryBuilder.addTables("tcontract_finished_flag");
                queryBuilder.addFields("*");
                queryBuilder.addFieldCondition(ConditionType.AND, "Fflag=?", "Success");
                
                Timestamp importFinishedTimestamp = new QueryRunner().query(conn, queryBuilder.getItemsSql(), new ResultSetHandler<Timestamp>() {
                    @Override
                    public Timestamp handle(ResultSet rs) throws SQLException {
                        if (rs.next()) {
                            return rs.getTimestamp("Fflag_time");
                        }
                        return null;
                    }
                }, queryBuilder.getParameterList());
                
                if (importFinishedTimestamp == null) {
                    dropDBNames.add(dbName);
                    continue;
                }
                
                long currentTimestampMs = System.currentTimeMillis();
                
                AppLog.d(dbName + " import finished timestampMs=" + importFinishedTimestamp.getTime() + " current is " + currentTimestampMs);
                if (currentTimestampMs >= (importFinishedTimestamp.getTime() + reserveExpiredFinishedDBTimeMs)) {
                    dropDBNames.add(dbName);
                    continue;
                }
                
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        
        return dropDBNames;
	}
	
	private List<String> getContractDataBases() throws SQLException {
	    Connection conn = DBSingleConnection.getNoneDbConnection();
        ResultSet rs = null;
        List<String> resultDBNames = new ArrayList<String>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            rs = metaData.getCatalogs();
            while(rs.next()) {
                String dbName  = rs.getString("TABLE_CAT");
                if (dbName.startsWith("contract_")) {
                    resultDBNames.add(dbName);
                }
            }
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
        
        return resultDBNames;
	}
	
	private void dropOldDatabase(List<String> dropDBNames) throws SQLException {
	    Connection conn = DBSingleConnection.getNoneDbConnection();
	    try {
	        for (String dropDBName : dropDBNames) {
	            AppLog.w("[NOTICE]Drop database " + dropDBName);
	            new QueryRunner().update(conn, "DROP DATABASE " + dropDBName);
	        }
	    } finally {
	        DbUtils.closeQuietly(conn);
	    }
	}
	
	private void downloadSqlFile(ContractVersion remoteLastestVersion, File sqlFile) throws Exception {
		byte[] content = null;
		
		int retryTimes = 0;
		while((retryTimes++) < 3) {
			try {
				content = getOssStorage().readFile(STORAGE_KEY, remoteLastestVersion.getFilePath());
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
		
		if (content == null) {
			throw new Exception("download " + remoteLastestVersion.getFilePath() + " failed!");
		}
		
		FileOutputStream output = new FileOutputStream(sqlFile);
		try {
			IOUtils.write(content, output);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}
	
	private AliyunOssStorage getOssStorage() throws OssFailedException {
		if (OSS_STORAGE == null) {
			synchronized(ContractSyncProcessor.class) {
				if (OSS_STORAGE == null) {
					OSS_STORAGE = new AliyunOssStorage("oss/config");
				}
			}
		}
		return OSS_STORAGE;
	}
	
	private void importData(ContractVersion remoteLastestVersion, File sqlFile)
			throws Exception {
		String dbName = createDataBase(remoteLastestVersion);
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(sqlFile, true));
			bw.append("\n");
			bw.append("CREATE TABLE tcontract_finished_flag(Fflag VARCHAR(32) NOT NULL DEFAULT '', Fflag_time DATETIME, PRIMARY KEY(Fflag)) CHARSET UTF8;\n");
			bw.append("INSERT INTO tcontract_finished_flag SET Fflag='Success', Fflag_time=NOW();\n");
			bw.append("\n");
		} finally {
			IOUtils.closeQuietly(bw);
		}
		
		// 给文件添加一个结束导出的表，方便检测source的完整性
		sourceSql(dbName, sqlFile);
		
		// 检验DB导入数据的完整性, 利用末尾手工添加的数据表
		checkSourceFinished(dbName);
	}
	
	// 使用mysql命令行的source命令导入大SQL，避免自身写的程序出现解析不严格，而导致的数据遗漏的错误
	private void sourceSql(String dbName, File sqlFile) throws Exception{
		ProcessBuilder importProcessBuilder = new ProcessBuilder();
		String envHostAddr = System.getenv("HOST_ADDR");
		importProcessBuilder.command("mysql"
				, "-h", StringUtils.isEmpty(envHostAddr) ? "127.0.0.1" : envHostAddr
				, "-uroot", "-proot", "--abort-source-on-error"
				, dbName, "-e", "source " + sqlFile.getAbsolutePath());
		File outputFile = File.createTempFile("import_contract_", ".output");
		boolean success = false;
		try {
			importProcessBuilder.redirectOutput(outputFile);
			importProcessBuilder.redirectError(outputFile);
			
			Process importProcess = importProcessBuilder.start();
			int ret = importProcess.waitFor();
			if (ret != 0) {
				throw new Exception("call mysql import failed, ret=" + ret);
			}
			success = true;
		} finally {
			dumpLog(outputFile, success);
			outputFile.delete();
		}
	}
	
	private void checkSourceFinished(String dbName) throws Exception {
		Connection dbConnection = DBSingleConnection.getDbConnection(dbName);
		try {
			SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
			queryBuilder.addTables("tcontract_finished_flag");
			queryBuilder.addFields("*");
			queryBuilder.addFieldCondition(ConditionType.AND, "Fflag=?", "Success");
			
			int count = new QueryRunner().query(
					dbConnection, queryBuilder.getTotalCountSql()
					, new ScalarHandler<Long>(), queryBuilder.getParameterList()).intValue();
			if (count <= 0) {
				throw new Exception("importData for " + dbName + " not completed!");
			}
		} finally {
			DbUtils.closeQuietly(dbConnection);
		}
		
	}
	
	private void dumpLog(File outputFile, boolean success) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
		try {
			if(success) {
				AppLog.i(bufferedReader.readLine());
			} else {
				AppLog.e(bufferedReader.readLine());
			}
		} finally {
			IOUtils.closeQuietly(bufferedReader);
		}
	}
	
	private String createDataBase(ContractVersion remoteLastestVersion)
			throws SQLException, ClassNotFoundException {
		Connection noneDbConnection = DBSingleConnection.getNoneDbConnection();
		try {
			String dbName = "contract_" + remoteLastestVersion.getVersionId();
			QueryRunner runner = new QueryRunner();
			runner.update(noneDbConnection, "DROP DATABASE IF EXISTS " + dbName);
			runner.update(noneDbConnection, "CREATE DATABASE " + dbName);
			return dbName;
		} finally {
			DbUtils.closeQuietly(noneDbConnection);
		}
	}
	
	private ContractVersion getRemoteLastestVersion() throws ErrorInfo, TException {
		ContractOnlineDaoServiceStub stub = new ContractOnlineDaoServiceStub();
		ReqContractVersionOption option = new ReqContractVersionOption();
		option.setLatest(true);
		ContractVersionPage resultPage
			= stub.reqContractVersion(RandomUtils.nextInt(), 3000, option, 0, 1);
		if (resultPage.getPageSize() > 0) {
			return resultPage.getPage().get(0);
		}
		return null;
	}
}
