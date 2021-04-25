package xueqiao.trade.hosting.position.statis.service.report;

import org.soldier.base.logger.AppLog;

public class AppReport {

	public static final boolean DEBUG = true;
	public static final boolean TRACE = true;

	public static void reportErr(String log) {
		AppLog.e(log);
	}

	public static void reportErr(String log, Throwable throwable) {
		AppLog.e(log, throwable);
	}

	public static void info(String log) {
		AppLog.i(log);
	}

	public static void warning(String log) {
		AppLog.w(log);
	}

	public static void debug(String log) {
		AppLog.d(log);
	}

	public static void trace(Class clazz, String log) {
		StringBuilder stringBuilder = new StringBuilder(clazz.getSimpleName());
		stringBuilder.append(" ## ");
		stringBuilder.append(log);
		AppLog.i(stringBuilder.toString());
	}
}
