package xueqiao.trade.hosting.entry.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

public class PermissionChecker {
	
	private int mSubUserId = -1;
	
	private PermissionChecker() {
	}
	
	public PermissionChecker setSubUserId(int subUserId) {
		this.mSubUserId = subUserId;
		return this;
	}
	
	public boolean hasPermission(EHostingUserRole role) throws ErrorInfo {
		Preconditions.checkNotNull(role);
		
		HostingUser user = EntryCache.Global().getUser(mSubUserId);
		if (user == null) {
			IHostingUserApi api = Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class);
			
			QueryUserOption option = new QueryUserOption();
			option.setSubUserId(mSubUserId);
			PageResult<HostingUser> userPage = api.queryUserPage(option, new PageOption().setPageSize(1));
			if (userPage.getPageList().isEmpty()) {
				return false;
			}
			user = userPage.getPageList().get(0);
		} else {
		    if (AppLog.debugEnabled()) {
		        AppLog.d("user(" +  mSubUserId + ") from cache, " + user);
		    }
		}
		
		if (user.getUserRoleValue() < role.getValue()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean hasPermission(LandingInfo landingInfo, EHostingUserRole role) throws ErrorInfo{
	    if (landingInfo == null) {
	        return false;
	    }
	    
		PermissionChecker checker = new PermissionChecker();
		checker.setSubUserId(landingInfo.getSubUserId());
		return checker.hasPermission(role);
	}
	
	public static void checkHasPermission(LandingInfo landingInfo
			, EHostingUserRole role) throws ErrorInfo {
		if (!hasPermission(landingInfo, role)) {
			throw new ErrorInfo(TradeHostingBasicErrorCode.USER_FORBIDDEN_ERROR.getValue()
					, "user forbidden, do not have permissions");
		}
	}
}
