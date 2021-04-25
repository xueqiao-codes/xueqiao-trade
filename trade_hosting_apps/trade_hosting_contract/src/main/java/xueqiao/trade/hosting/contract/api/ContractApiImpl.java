package xueqiao.trade.hosting.contract.api;

import java.io.File;
import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

import xueqiao.trade.hosting.events.ContractVersionChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

public class ContractApiImpl implements IContractApi {
    private static File STUB_SOCKET_FILE = new File("/data/run/service_253.sock");
    private static File TRADE_HOSTING_FLAG_FILE = new File("/usr/local/soldier/trade_hosting");
    
    private EApiMode apiMode;
    public ContractApiImpl() {
        if (TRADE_HOSTING_FLAG_FILE.exists()) {
            apiMode= EApiMode.IN_HOSTING;
        } else {
            apiMode = EApiMode.OUT_HOSTING;
        }
        AppLog.i("ContractApi apiMode=" + apiMode);
    }
    
	@Override
	public ContractVersionEntry getLastestVersion() throws ErrorInfo {
		return new DBQueryHelper<ContractVersionEntry>(ContractInfoDB.Global()) {
			@Override
			protected ContractVersionEntry onQuery(Connection conn) throws Exception {
				return new ContractVersionTable(conn).getVersion(false);
			}
		}.query();
	}

	@Override
	public void updateLastestVersion(int lastestVersion) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(lastestVersion > 0);
		
		new DBTransactionHelperWithMsg<Void>(ContractInfoDB.Global()) {
			private ContractVersionEntry fromEntry;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				fromEntry = new ContractVersionTable(getConnection()).getVersion(true);
				if (fromEntry.getLastestVersion() >= lastestVersion) {
					throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode()
							, "updateVersion is lower!");
				}
			}

			@Override
			public void onUpdate() throws Exception {
				new ContractVersionTable(getConnection()).updateVersion(
						lastestVersion, fromEntry.getLastestVersion());
			}

			@Override
			public Void getResult() {
				return null;
			}

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if(apiMode != EApiMode.IN_HOSTING) {
                    return null;
                }
                ContractVersionChangedEvent event = new ContractVersionChangedEvent();
                event.setEventTimestampMs(System.currentTimeMillis());
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
		}.execute();
	}

    @Override
    public File getStubFile() throws ErrorInfo {
        return STUB_SOCKET_FILE;
    }

    @Override
    public EApiMode getApiMode() {
        return apiMode;
    }
}
