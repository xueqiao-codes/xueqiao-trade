package xueqiao.trade.hosting.entry.core;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

public class SessionValidation {
	public static void checkLoginPasswordMd5(String passwordMd5) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(passwordMd5), "login password md5 should not be blank");
	}
	
	public static void checkHostingSession(HostingSession session) throws ErrorInfo {
		ParameterChecker.check(session != null, "session should not be null");
		ParameterChecker.check(session.getMachineId() > 0, "session's machineId should > 0");
		ParameterChecker.check(session.getSubUserId() > 0, "sessions's subUserId should > 0");
		ParameterChecker.check(StringUtils.isNotBlank(session.getToken()), "session's token should not be blank");
	}
}
