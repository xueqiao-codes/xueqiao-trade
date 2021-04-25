package xueqiao.trade.hosting.storage.session;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.events.LandingStatusChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;
import xueqiao.trade.hosting.storage.comm.SessionDB;

public class HostingSessionApiImpl implements IHostingSessionApi {
	
	@Override
	public boolean isOnline(int subUserId) throws ErrorInfo {
		return null != getSession(subUserId);
	}
	
	@Override
	public SessionEntry getSession(int subUserId) throws ErrorInfo {
		return new DBQueryHelper<SessionEntry>(SessionDB.Global()){
			@Override
			protected SessionEntry onQuery(Connection conn) throws Exception {
				return new HostingSessionTable(conn).getSessionEntry(subUserId);
			}

		}.query();
	}
	
	@Override
	public List<SessionEntry> getAll() throws ErrorInfo {
		return new DBQueryHelper<List<SessionEntry>>(SessionDB.Global()){
			@Override
			protected List<SessionEntry> onQuery(Connection conn) throws Exception {
				return new HostingSessionTable(conn).getAll();
			}
		}.query();
	}

	@Override
	public void insertSession(HostingSession session) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(session);
	    ParameterChecker.checkInnerArgument(session.getSubUserId() > 0);
	    ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(session.getToken()));
		
		new DBStepHelperWithMsg<Void>(SessionDB.Global()){
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			}

			@Override
			public void onUpdate() throws Exception {
				HostingSessionTable table = new HostingSessionTable(getConnection());
				
				SessionEntry entry = new SessionEntry();
				entry.setSubUserId(session.getSubUserId());
				entry.setToken(session.getToken());
				entry.setLoginIP(session.getLoginIP());
				
				table.insertSessionEntry(entry);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				LandingStatusChangedEvent event = new LandingStatusChangedEvent();
				event.setSubUserId(session.getSubUserId());
				return new SimpleEntry<TBase, IGuardPolicy>(
						event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
			
		}.execute();
		
	}

	@Override
	public void updateSession(HostingSession session) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(session);
	    ParameterChecker.checkInnerArgument(session.getSubUserId() > 0);
		
		new DBStepHelper<Void>(SessionDB.Global()) {
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			}

			@Override
			public void onUpdate() throws Exception {
				HostingSessionTable table = new HostingSessionTable(getConnection());
				
				SessionEntry entry = new SessionEntry();
				entry.setSubUserId(session.getSubUserId());
				entry.setToken(session.getToken());
				entry.setLoginIP(session.getLoginIP());
				
				int n = table.updateSessionEntry(entry);
				if (n <= 0) {
					throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()
							, "session is not found for update");
				}
			}

			@Override
			public Void getResult() {
				return null;
			}
			
		}.execute();
	}

	@Override
	public void deleteSession(int subUserId) throws ErrorInfo {
		new DBStepHelperWithMsg<Void>(SessionDB.Global()) {
		    private boolean sessionExist = true;
		    
		    // 直接删除并探测session是否存在
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			    HostingSessionTable table = new HostingSessionTable(getConnection());
                int n = table.deleteSessionEntry(subUserId);
                if (n <= 0) {
                    sessionExist = false;
                }
			}

			@Override
			public void onUpdate() throws Exception {
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
			    if (!sessionExist) {
			        return null;
			    }
			    
				LandingStatusChangedEvent event = new LandingStatusChangedEvent();
				event.setSubUserId(subUserId);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
		}.execute();
		
	}

	@Override
	public int getTotalCount() throws ErrorInfo {
		return new DBQueryHelper<Integer>(SessionDB.Global()){
			@Override
			protected Integer onQuery(Connection conn) throws Exception {
				return new HostingSessionTable(conn).getTotalCount();
			}
		}.query();
	}

    @Override
    public void clearAll() throws ErrorInfo {
        new DBStepHelper<Void>(SessionDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingSessionTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
    }

	

	

}
