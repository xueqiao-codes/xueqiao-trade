package xueqiao.trade.hosting.storage.thriftapi.server.impl;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.Md5;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.config.HostingConfigFileTable;
import xueqiao.trade.hosting.storage.config.HostingConfigPathBuilder;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;

public class HostingConfigUpdator {
    private TProtocolFactory mEventProtocolFactory = new TCompactProtocol.Factory();
    private Map<String, ReentrantLock> mOperatorLock = new HashMap<>();
    
    public HostingConfigUpdator() {
    }
    
    public void updateConfig(UpdateConfigDescription description) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(description.getConfigArea()));
        ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(description.getConfigKey()));
        ParameterChecker.checkInnerArgument(description.getConfigVersion() > 0);
        ParameterChecker.checkInnerArgument(description.isSetConfigContent());
        
        if (description.isSetNotifyEventClassName()) {
            ParameterChecker.checkInnerArgument(description.isSetNotifyEventBinary() 
                    && description.getNotifyEventBinary().length > 0);
        }
        
        String lockKey = getLockKey(description.getConfigArea(), description.getConfigKey());
        lock(lockKey);
        try {
            new DBStepHelperWithMsg<Void>(HostingDB.Global()) {
                private ConfigFileEntry dbEntry;
                private File configDir;
                private File configFile;
                private String configMd5;
                
                @SuppressWarnings("rawtypes")
                private TBase notifyEvent;
                
                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    dbEntry = new HostingConfigFileTable(getConnection()).getEntry(
                            description.getConfigArea(), description.getConfigKey());
                    if (dbEntry != null) {
                        if (dbEntry.getConfigVersion() >= description.getConfigVersion()) {
                            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONFIG_VERSION_LOW.getValue()
                                    , "config version is lower");
                        }
                    }
                    
                    if (description.isSetNotifyEventClassName()) {
                        notifyEvent = TBase.class.cast(
                                Class.forName(HostingEvent.class.getPackage().getName() 
                                        + "." + description.getNotifyEventClassName()).newInstance());
                        notifyEvent.read(mEventProtocolFactory.getProtocol(
                                new TMemoryInputTransport(description.getNotifyEventBinary())));
                    }
                    
                    
                    HostingConfigPathBuilder builder 
                        = new HostingConfigPathBuilder()
                            .setConfigArea(description.getConfigArea())
                            .setConfigKey(description.getConfigKey())
                            .setConfigVersion(description.getConfigVersion());
                         
                    configDir = builder.buildDir();
                    configFile = builder.buildFile();
                    if (!configDir.exists()) {
                        if (!configDir.mkdirs()) {
                            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                                    , "failed to mkdir for config");
                        }
                    }
                    if (configFile.exists()) {
                        if (!configFile.delete()) {
                            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                                    , "failed to delete old config for version");
                        }
                    }
                    
                    configMd5 = Md5.toMd5(description.getConfigContent());
//                    if (configMd5.equalsIgnoreCase(dbEntry.getConfigFileMd5())) {
//                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONFIG_SAME_CONTENT_MD5.getValue()
//                                , "same content md5");
//                    }
                    
                    File tmpConfigFile = new File(configFile.getAbsolutePath() + ".tmp");
                    FileUtils.writeByteArrayToFile(tmpConfigFile, description.getConfigContent());
                    if (!tmpConfigFile.renameTo(configFile)) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                                , "failed to rename tmp file");
                    }
                    
                    if (!configMd5.equalsIgnoreCase(Md5.toMD5(configFile))) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                                , "failed to write file, md5 is not equals");
                    }
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    ConfigFileEntry operateEntry = new ConfigFileEntry();
                    operateEntry.setConfigArea(description.getConfigArea());
                    operateEntry.setConfigKey(description.getConfigKey());
                    operateEntry.setConfigVersion(description.getConfigVersion());
                    operateEntry.setConfigFilePath(configFile.getAbsolutePath());
                    operateEntry.setConfigFileMd5(configMd5);
                    
                    if (dbEntry == null) {
                        new HostingConfigFileTable(getConnection()).addEntry(operateEntry);
                    } else {
                        new HostingConfigFileTable(getConnection()).updateEntry(operateEntry);
                    }
                }

                @Override
                public Void getResult() {
                    return null;
                }

                @SuppressWarnings("rawtypes")
                @Override
                protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                    if (notifyEvent == null) {
                        return null;
                    }
                    return new SimpleEntry<TBase, IGuardPolicy>(notifyEvent, new TimeoutGuardPolicy().setTimeoutSeconds(2));
                }

                @Override
                protected MessageAgent getMessageAgent() {
                    return HostingMessageContext.Global().getSenderAgent();
                }
            }.execute();
        } finally {
            unLock(lockKey);
        }
    }
    
    private void lock(String lockKey) {
        ReentrantLock lock = null;
        synchronized(mOperatorLock) {
            lock = mOperatorLock.get(lockKey);
            if (lock == null) {
                lock = new ReentrantLock();
            }
            mOperatorLock.put(lockKey, lock);
        }
        
        lock.lock();
    }
    
    private void unLock(String lockKey) {
        ReentrantLock lock = null;
        synchronized(mOperatorLock) {
            lock = mOperatorLock.get(lockKey);
        }
        Preconditions.checkNotNull(lock);
        lock.unlock();
    }
    
    private String getLockKey(String configArea, String configKey) {
        return configArea + "_" + configKey;
    }
}
