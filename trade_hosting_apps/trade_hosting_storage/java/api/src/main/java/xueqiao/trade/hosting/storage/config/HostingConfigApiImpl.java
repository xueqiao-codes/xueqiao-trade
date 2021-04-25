package xueqiao.trade.hosting.storage.config;

import java.io.File;
import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.Md5;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.comm.StorageApiStub;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;

@SuppressWarnings("rawtypes")
public class HostingConfigApiImpl implements IHostingConfigApi {
    
    private TProtocolFactory mConfigProtocolFactory = new TCompactProtocol.Factory();
    
    @Override
    public ConfigFileEntry getConfigEntry(String configArea, String configKey) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configArea));
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configKey));
        
        return new DBQueryHelper<ConfigFileEntry>(HostingDB.Global()) {
            @Override
            protected ConfigFileEntry onQuery(Connection conn) throws Exception {
                return new HostingConfigFileTable(conn).getEntry(configArea, configKey);
            }
        }.query();
    }

    @Override
    public TBase getConfig(String configArea
            , String configKey
            , Class<? extends TBase> configClazz) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configArea));
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configKey));
        
        ConfigFileEntry configEntry = getConfigEntry(configArea, configKey);
        if (configEntry == null) {
            return null;
        }
        
        try {
            File configFile = new File(configEntry.getConfigFilePath());
            if (!(configFile.exists() && configFile.isFile())) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONFIG_LOST.getValue()
                        , "config file not existed!");
            }
            
            byte[] configContent= FileUtils.readFileToByteArray(configFile);
            if (!configEntry.getConfigFileMd5().equalsIgnoreCase(Md5.toMd5(configContent))) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONFIG_LOST.getValue()
                        , "config file content not right!");
            }
            return ProtocolUtil.unSerialize(mConfigProtocolFactory, configContent, configClazz);
        } catch (ErrorInfo e) {
            throw e;
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!");
        }
    }

    @Override
    public void setConfig(String configArea
            , String configKey
            , int configVersion
            , TBase configContent
            , TBase notifyEvent) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configArea));
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configKey));
        ParameterChecker.checkInnerArgument(configVersion > 0);
        ParameterChecker.checkInnerNotNull(configContent);
        
        UpdateConfigDescription configDescription = new UpdateConfigDescription();
        configDescription.setConfigArea(configArea);
        configDescription.setConfigKey(configKey);
        configDescription.setConfigVersion(configVersion);
        configDescription.setConfigContent(ProtocolUtil.serialize(mConfigProtocolFactory, configContent));
        if (notifyEvent != null) {
            ParameterChecker.checkInnerArgument(
                    notifyEvent.getClass().getPackage().getName().equals(HostingEvent.class.getPackage().getName()));
            configDescription.setNotifyEventClassName(notifyEvent.getClass().getSimpleName());
            configDescription.setNotifyEventBinary(ProtocolUtil.serialize(mConfigProtocolFactory, notifyEvent));
        }
        
        try {
            new StorageApiStub().updateConfig(configDescription);
        } catch (ErrorInfo e) {
            throw e;
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!");
        }
    }

    @Override
    public void clearConfigs(String configArea, TBase notifyEvent) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(configArea));
        new DBStepHelperWithMsg<Void>(HostingDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingConfigFileTable(getConnection()).clearArea(configArea);
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if (notifyEvent == null) {
                    return null;
                }
                return new SimpleEntry<TBase, IGuardPolicy>(notifyEvent, new TimeoutGuardPolicy().setTimeoutSeconds(5));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
            
        }.execute();
    }

}
