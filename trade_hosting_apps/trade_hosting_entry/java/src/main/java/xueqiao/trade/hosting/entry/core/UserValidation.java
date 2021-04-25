package xueqiao.trade.hosting.entry.core;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.FormatValidator;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.trade_hostingConstants;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

public class UserValidation {
	
	public static void checkLoginName(String loginName) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(loginName)
					, "loginName should not be blank");
		ParameterChecker.check(loginName.trim().length() <= trade_hostingConstants.MAX_LOGIN_NAME_LENGTH
					, "login name is too long");
	}
	
	public static void checkLoginPasswd(String loginPasswd) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(loginPasswd)
				, "loginPasswd should not be empty");
		ParameterChecker.check(loginPasswd.trim().length() <= trade_hostingConstants.MAX_LOGIN_PASSWD_LENGTH
				, "req's admin login password is too long");
	}
	
	public static void checkNickName(String nickName) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(nickName)
				, "nickName should not be blank");
		ParameterChecker.check(nickName.trim().length() <= trade_hostingConstants.MAX_NICKNAME_LENGTH
				, "nickName is too long");
	}
	
	public static void checkEmail(String email) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(email)
				, "email should not be blank");
		ParameterChecker.check(email.trim().length() <= trade_hostingConstants.MAX_EMAIL_LENGTH
				, "email is too long");
		ParameterChecker.check(FormatValidator.isValidEmail(email)
				, "email format is not valid!");
	}
	
	public static void checkPhone(String phone) throws ErrorInfo {
		ParameterChecker.check(StringUtils.isNotBlank(phone), "phone should not be blank");
		ParameterChecker.check(phone.trim().length() <= trade_hostingConstants.MAX_PHONE_LENGTH
				, "phone is too long");
		
		String trimPhone = phone.trim();
		for (int index = 0; index < trimPhone.length(); ++index) {
			char c = trimPhone.charAt(index);
			if (!CharUtils.isAsciiNumeric(c) && c != '-') {
				throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
						, "phone format is not valid!");
			}
		}
	}
	
	public static void checkNewUserRoleValue(short value) throws ErrorInfo {
		ParameterChecker.check(value >= 0
				&& value <= EHostingUserRole.AdminGroup.getValue()
				, "user role value is invalid");
	}
	
}
