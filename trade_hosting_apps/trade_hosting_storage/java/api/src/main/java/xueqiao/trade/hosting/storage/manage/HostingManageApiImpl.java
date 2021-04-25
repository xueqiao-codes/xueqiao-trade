package xueqiao.trade.hosting.storage.manage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TBase;
import org.soldier.base.beanfactory.Globals;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingInfo;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.HostingStatus;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.events.HostingEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.db.DBGlobalProperties;
import xueqiao.trade.hosting.framework.db.DBGlobalPropetiesInnerKey;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.HostingInitInfo;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.user.HostingUserTable;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;

public class HostingManageApiImpl implements IHostingManageApi {
	private static final String KEY_MACHINE_ID = "MachineId";
	private static final String KEY_LAST_SUB_USER_ID = "LastSubUserId";
	private static final String KEY_AES16_KEY = "AES16Key";
	private static final String KEY_RUNNING_MODE = "RunningMode";
	private static final String KEY_HOSTING_UUID = "HostingUUID";
	private static final String KEY_HOSTING_CLEARING = "HostingClearing";
	
	private long getMachineId(Connection conn, boolean forUpdate) throws SQLException {
		DBGlobalProperties properties = new DBGlobalProperties(conn);
		String machineIdProperty = properties.getProperty(KEY_MACHINE_ID, forUpdate);
		if (StringUtils.isNotEmpty(machineIdProperty)) {
			return NumberUtils.createLong(machineIdProperty);
		}
		return 0;
	}
	
	private HostingRunningMode getRunningMode(Connection conn, boolean forUpdate) throws SQLException {
		DBGlobalProperties properties = new DBGlobalProperties(conn);
		String runningModelProperty = properties.getProperty(KEY_RUNNING_MODE, forUpdate);
		if (StringUtils.isNotEmpty(runningModelProperty)) {
			return HostingRunningMode.findByValue(NumberUtils.createInteger(runningModelProperty));
		}
		return null;
	}
	
	@Override
	public void initHosting(HostingInitInfo info) throws ErrorInfo {
		ParameterChecker.checkInnerNotNull(info);
		ParameterChecker.checkInnerArgument(info.getMachineId() > 0);
		ParameterChecker.checkInnerNotNull(info.getRunningMode());
		if (StringUtils.isNotEmpty(info.getAdminLoginName())) {
			ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(info.getAdminLoginName()));
			ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(info.getAdminLoginPasswd()));
		}
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()){
			
			private int adminSubUserId = 0;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				long machineId = getMachineId(getConnection(), true);
				if (machineId > 0) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.HOSTING_HAS_ALREADY_INITED.getValue()
							, "hosting is already inited!");
				}
				
				DBGlobalProperties properties = new DBGlobalProperties(getConnection());
				if (!StringUtils.isEmpty(properties.getProperty(KEY_HOSTING_CLEARING))) {
				    throw new ErrorInfo(TradeHostingBasicErrorCode.HOSTING_CLEARING.getValue()
				            , "hosting is clearing");
				}

				String lastSubUserIdProperty = properties.getProperty(KEY_LAST_SUB_USER_ID);
				if (StringUtils.isNotEmpty(info.getAdminLoginName())) {
					if (StringUtils.isEmpty(lastSubUserIdProperty)) {
						adminSubUserId = 1;
					} else {
						lastSubUserIdProperty = properties.getProperty(KEY_LAST_SUB_USER_ID, true);
						adminSubUserId = NumberUtils.createInteger(lastSubUserIdProperty) + 1;
					}
				}

				if (StringUtils.isEmpty(lastSubUserIdProperty) || adminSubUserId > 0) {
					properties.setProperty(KEY_LAST_SUB_USER_ID, String.valueOf(adminSubUserId));
				}
			}

			@Override
			public void onUpdate() throws Exception {
				if (StringUtils.isNotEmpty(info.getAdminLoginName())) {
					HostingUser adminUser = new HostingUser();
					adminUser.setSubUserId(adminSubUserId);
					adminUser.setLoginName(info.getAdminLoginName());
					adminUser.setLoginPasswd(info.getAdminLoginPasswd());
					adminUser.setUserRoleValue((short) EHostingUserRole.AdminGroup.getValue());
					adminUser.setNickName("administrator");

					HostingUserTable userTable = new HostingUserTable(getConnection());
					userTable.addHostingUser(adminUser);
				}
				
				DBGlobalProperties properties = new DBGlobalProperties(getConnection());
				properties.setProperty(KEY_MACHINE_ID, String.valueOf(info.getMachineId()));
				properties.setProperty(KEY_AES16_KEY, info.getHostingAES16Key());
				properties.setProperty(KEY_RUNNING_MODE, String.valueOf(info.getRunningMode().getValue()));
				properties.setProperty(KEY_HOSTING_UUID, UUID.randomUUID().toString());
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				HostingEvent event = new HostingEvent();
				event.setType(HostingEventType.HOSTING_INITED);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(3));
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
	public void resetHosting(long machineId, String hostingAES16Key) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(machineId > 0);
	    ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(hostingAES16Key));
	    
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()){

			@Override
			public void onPrepareData() throws ErrorInfo, SQLException {
				DBGlobalProperties properties = new DBGlobalProperties(getConnection());
				long dbMachineId = getMachineId(getConnection(), true);
				if (dbMachineId == 0) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.HOSTING_HAS_NOT_INITED.getValue()
							, "hosting has not inited!");
				}
				if (machineId != dbMachineId) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.MACHINEID_ERROR.getValue()
							, "machineId is not equals to current hosting machineId, check failed!");
				}
				
				String dbAES16Key = properties.getProperty(KEY_AES16_KEY);
				if (dbAES16Key != null && !dbAES16Key.equals(hostingAES16Key)) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.MACHINEID_ERROR.getValue()
							, "aeskey is not equals to hosting aeskey");
				}
			}

			@Override
			public void onUpdate() throws SQLException {
				DBGlobalProperties properties = new DBGlobalProperties(getConnection());
				properties.setProperty(KEY_MACHINE_ID, String.valueOf(0));
				properties.setProperty(KEY_AES16_KEY, "");
				properties.setProperty(KEY_RUNNING_MODE, "");
				properties.setProperty(KEY_HOSTING_CLEARING, "1");
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				HostingEvent event = new HostingEvent();
				event.setType(HostingEventType.HOSTING_DESTORIED);
				return new SimpleEntry<TBase, IGuardPolicy>(event
						, new TimeoutGuardPolicy().setTimeoutSeconds(1));
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
	public HostingInfo getHostingInfo(boolean needStaticsData) throws ErrorInfo {
		IHostingSessionApi hostingSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
		return new DBQueryHelper<HostingInfo>(HostingDB.Global()) {
			@Override
			protected HostingInfo onQuery(Connection conn) throws Exception {
				HostingInfo info = new HostingInfo();
				
				DBGlobalProperties properties = new DBGlobalProperties(conn);
				long machineId = getMachineId(conn, false);
				if (machineId <= 0) {
				    String hostingClearing = properties.getProperty(KEY_HOSTING_CLEARING);
				    if (StringUtils.isNotEmpty(hostingClearing)) {
				        info.setStatus(HostingStatus.CLEARING);
				    } else {
				        info.setStatus(HostingStatus.EMPTY);
				    }
					info.setMachineId(0);
				} else {
					info.setStatus(HostingStatus.NORMAL);
					info.setMachineId(machineId);
				}
				info.setTableVersion(
					NumberUtils.createInteger(
							properties.getProperty(DBGlobalPropetiesInnerKey.KEY_DB_VERSION)));
				
				info.setRunningMode(getRunningMode(conn, false));
				if (needStaticsData) {
				    info.setSubUserTotalCount(new HostingUserTable(conn).getTotalCount());
				    info.setOnlineUserTotalCount(hostingSessionApi.getTotalCount());
				}
				
				return info;
			}
			
		}.query();
	}

	@Override
	public int allocSubUserId() throws ErrorInfo {
		return new DBTransactionHelper<Integer>(HostingDB.Global()){
			private int newSubUserId = 0;
			
			@Override
			public void onPrepareData() throws ErrorInfo, SQLException {
				DBGlobalProperties properties = new DBGlobalProperties(getConnection());
				String subUserIdProperty = properties.getProperty(KEY_LAST_SUB_USER_ID, true);
				ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(subUserIdProperty));
				newSubUserId = NumberUtils.createInteger(subUserIdProperty) + 1;
			}
			
			@Override
			public void onUpdate() throws SQLException {
				new DBGlobalProperties(getConnection()).setProperty(KEY_LAST_SUB_USER_ID
						, String.valueOf(newSubUserId));
			}
			@Override
			public Integer getResult() {
				return newSubUserId;
			}
		}.execute().getResult();
	}

	@Override
	public long getMachineId() throws ErrorInfo {
		return new DBQueryHelper<Long>(HostingDB.Global()) {
			@Override
			protected Long onQuery(Connection conn) throws Exception {
				return getMachineId(conn, false);
			}
		}.query();
	}

	@Override
	public HostingRunningMode getRunningMode() throws ErrorInfo {
		return new DBQueryHelper<HostingRunningMode>(HostingDB.Global()) {
			@Override
			protected HostingRunningMode onQuery(Connection conn) throws Exception {
				return getRunningMode(conn, false);
			}
		}.query();
	}

    @Override
    public File getHostingDataDir() throws ErrorInfo {
        String hostingUUID = new DBQueryHelper<String>(HostingDB.Global()) {
            @Override
            protected String onQuery(Connection conn) throws Exception {
                return new DBGlobalProperties(conn).getProperty(KEY_HOSTING_UUID);
            }
        }.query();
        if (StringUtils.isEmpty(hostingUUID)) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.HOSTING_HAS_NOT_INITED.getValue()
                    , "Hosting has not inited!");
        }
        return new File("/data/hosting/"+ hostingUUID);
    }

    @Override
    public void clearFinished() throws ErrorInfo {
        new DBTransactionHelper<Void>(HostingDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                DBGlobalProperties properties = new DBGlobalProperties(getConnection());
                properties.setProperty(KEY_HOSTING_UUID, "");
                properties.setProperty(KEY_HOSTING_CLEARING, "");
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

}
