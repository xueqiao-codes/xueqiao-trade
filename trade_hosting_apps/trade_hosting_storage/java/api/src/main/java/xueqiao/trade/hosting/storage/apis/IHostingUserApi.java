package xueqiao.trade.hosting.storage.apis;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;

public interface IHostingUserApi {
	
	public int addUser(HostingUser newUser) throws ErrorInfo;
	
	public void updateUser(HostingUser updateUser) throws ErrorInfo;
	
	public int getTotalCount() throws ErrorInfo;
	
	public PageResult<HostingUser> queryUserPage(QueryUserOption userOption, PageOption pageOption) throws ErrorInfo;
	
	public HostingUser getUser(int subUserId) throws ErrorInfo;
	
	public void clearAll() throws ErrorInfo;
	
	public void enableUser(int subUserId) throws ErrorInfo;
	
	public void disableUser(int subUserId) throws ErrorInfo;
} 
