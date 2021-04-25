package xueqiao.trade.hosting.framework;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSingleConnection {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			AppLog.e(e.getMessage());
			Runtime.getRuntime().exit(1);
		}
	}

	private static String getDBAddr() {
		String envHostAddr = System.getenv("HOST_ADDR");
		if (StringUtils.isNotEmpty(envHostAddr)) {
			return envHostAddr;
		}
		return "127.0.0.1";
	}
	
	public static Connection getNoneDbConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://" + getDBAddr() + "/", "root", "root");
	}
	
	public static Connection getDbConnection(String dbName) throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://" + getDBAddr() + "/" + dbName, "root", "root");
	}
}
