package xueqiao.trade.hosting.storage.user;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.beanfactory.Globals;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.HostingUserState;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.UserEvent;
import xueqiao.trade.hosting.events.UserEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.subaccount.HostingSubAccountRelatedTable;

public class HostingUserApiImpl implements IHostingUserApi {
    
    public HostingUserApiImpl() {
    }
    
    @Override
    public HostingUser getUser(int subUserId) throws ErrorInfo {
        return new DBQueryHelper<HostingUser>(HostingDB.Global()) {
            @Override
            protected HostingUser onQuery(Connection conn) throws Exception {
                return new HostingUserTable(conn).getUser(subUserId);
            }
        }.query();
    }
	
	@Override
	public int addUser(HostingUser newUser) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(!StringUtils.isEmpty(newUser.getLoginName()));
	    ParameterChecker.checkInnerArgument(!StringUtils.isEmpty(newUser.getLoginPasswd()));
		
		return new DBStepHelperWithMsg<Integer>(HostingDB.Global()){
			private int newUserId = 0;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingUserTable table = new HostingUserTable(getConnection());
				
				QueryUserOption option = new QueryUserOption();
				option.setLoginNameWhole(newUser.getLoginName().trim());
				
				PageResult<HostingUser> result = table.getHostingUserPage(option, new PageOption().setPageSize(1));
				if (!result.getPageList().isEmpty()) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_ALREADY_EXISTED.getValue()
							, "user is already existed!");
				}
				
				newUserId = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class).allocSubUserId();
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				HostingUserTable table = new HostingUserTable(getConnection());
				newUser.setSubUserId(newUserId);
				table.addHostingUser(newUser);
			}

			@Override
			public Integer getResult() {
				return newUserId;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				UserEvent event = new UserEvent();
				event.setType(UserEventType.USER_ADD);
				event.setSubUserId(newUserId);
				
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}
			
			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

		}.execute().getResult();
	}

	@Override
	public void updateUser(HostingUser updateUser) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(updateUser.getSubUserId() > 0);
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingUser hostingUser = new HostingUserTable(getConnection()).getUser(updateUser.getSubUserId(), true);
				if (hostingUser == null) {
				    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                            , "user is not existed!");
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				new HostingUserTable(getConnection()).updateHostingUser(updateUser);
				if (updateUser.isSetLoginName() || updateUser.isSetNickName()) {
				    new HostingSubAccountRelatedTable(getConnection()).updateSubUserInfo(updateUser);
				}
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				UserEvent event = new UserEvent();
				event.setType(UserEventType.USER_INFO_UPDATED);
				event.setSubUserId(updateUser.getSubUserId());
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}
			
		}.execute();
		
	}

	@Override
	public int getTotalCount() throws ErrorInfo {
		return new DBQueryHelper<Integer>(HostingDB.Global()) {
			@Override
			protected Integer onQuery(Connection conn) throws Exception {
				return new HostingUserTable(conn).getTotalCount();
			}
		}.query();
	}

	@Override
	public PageResult<HostingUser> queryUserPage(QueryUserOption userOption, PageOption pageOption) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(pageOption);
		pageOption.checkValid();
		
		return new DBQueryHelper<PageResult<HostingUser>>(HostingDB.Global()) {

			@Override
			protected PageResult<HostingUser> onQuery(Connection conn) throws Exception {
				return new HostingUserTable(conn).getHostingUserPage(userOption, pageOption);
			}
			
		}.query();
	}

    @Override
    public void clearAll() throws ErrorInfo {
        new DBStepHelperWithMsg<Void>(HostingDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingUserTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                UserEvent event = new UserEvent();
                event.setType(UserEventType.USER_ALL_CLEARD);
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(5));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }

    @Override
    public void enableUser(int subUserId) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
            private boolean needUpdate = false;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                HostingUser enableUser = new HostingUserTable(getConnection()).getUser(subUserId, true);
                if (enableUser == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                            , "user not existed!");
                }
                
                if (enableUser.getUserState() != HostingUserState.USER_NORMAL) {
                    needUpdate = true;
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!needUpdate) {
                    return ;
                }
                
                HostingUser operateUser = new HostingUser();
                operateUser.setSubUserId(subUserId);
                operateUser.setUserState(HostingUserState.USER_NORMAL);
                
                new HostingUserTable(getConnection()).updateHostingUser(operateUser);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if (!needUpdate) {
                    return null;
                }
                UserEvent event = new UserEvent();
                event.setType(UserEventType.USER_STATE_CHANGED);
                event.setSubUserId(subUserId);
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }
            
            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }

    @Override
    public void disableUser(int subUserId) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
            private boolean needUpdate = false;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                HostingUser disableUser = new HostingUserTable(getConnection()).getUser(subUserId, true);
                if (disableUser == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                            , "user not existed!");
                }
                
                if (disableUser.getUserState() != HostingUserState.USER_DISABLED) {
                    needUpdate = true;
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!needUpdate) {
                    return ;
                }
                
                HostingUser operateUser = new HostingUser();
                operateUser.setSubUserId(subUserId);
                operateUser.setUserState(HostingUserState.USER_DISABLED);
                
                new HostingUserTable(getConnection()).updateHostingUser(operateUser);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if (!needUpdate) {
                    return null;
                }
                UserEvent event = new UserEvent();
                event.setType(UserEventType.USER_STATE_CHANGED);
                event.setSubUserId(subUserId);
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }

    

}
