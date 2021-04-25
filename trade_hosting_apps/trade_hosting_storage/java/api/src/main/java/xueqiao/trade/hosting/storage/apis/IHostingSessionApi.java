package xueqiao.trade.hosting.storage.apis;

import java.util.List;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public interface IHostingSessionApi {
	public boolean isOnline(int subUserId) throws ErrorInfo;
	
	public SessionEntry getSession(int subUserId) throws ErrorInfo;
	
	public List<SessionEntry> getAll() throws ErrorInfo;
	
	public int getTotalCount() throws ErrorInfo;
	
	public void insertSession(HostingSession session) throws ErrorInfo;
	public void updateSession(HostingSession session) throws ErrorInfo;
	
	public void deleteSession(int subUserId) throws ErrorInfo;
	
	public void clearAll() throws ErrorInfo;
}
