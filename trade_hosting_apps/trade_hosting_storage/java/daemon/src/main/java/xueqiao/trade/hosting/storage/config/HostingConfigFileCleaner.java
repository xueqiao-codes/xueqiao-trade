package xueqiao.trade.hosting.storage.config;

import java.io.File;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;

/**
 *  负责清理版本过期的内容
 * @author wangli
 */
public class HostingConfigFileCleaner {
    private static HostingConfigFileCleaner INSTANCE;
    
    public static HostingConfigFileCleaner Global() {
        if (INSTANCE == null) {
            synchronized(HostingConfigFileCleaner.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HostingConfigFileCleaner();
                }
            }
        }
        return INSTANCE;
    }
    
    
    private IHostingConfigApi mConfigApi;
    private HostingConfigFileCleaner() {
        mConfigApi = Globals.getInstance().queryInterfaceForSure(IHostingConfigApi.class, mConfigApi);
    }
    
    public void runOnce() throws Exception {
        AppLog.i("clean hosting config file ...");
        
        File configsDir = getHostingConfigsDir();
        if (configsDir == null) {
            return ;
        }
       
        if (!configsDir.exists()) {
            AppLog.i("configsDir " + configsDir.getAbsolutePath() + " is not existed!");
            return ;
        }
        
        AppLog.i("scan " + configsDir.getAbsolutePath() + " ...");
        File[] configAreaFiles = configsDir.listFiles();
        for (File configAreaFile : configAreaFiles) {
            if (!configAreaFile.isDirectory()) {
                continue;
            }
            
            AppLog.i("scan configAreaPath=" + configAreaFile.getAbsolutePath() + " ...");
            String configArea = configAreaFile.getName();
            
            File[] configKeyFiles = configAreaFile.listFiles();
            for (File configKeyFile : configKeyFiles) {
                if (!configKeyFile.isDirectory()) {
                    continue;
                }
                
                String configKey = configKeyFile.getName();
                try {
                    processConfigDir(configArea, configKey, configKeyFile);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        }
    }
    
    private void processConfigDir(String configArea, String configKey, File configKeyDirFile) throws ErrorInfo {
        AppLog.i("process configArea=" + configArea + ", configKey=" + configKey + ", configKeyDirFile=" + configKeyDirFile);
        
        ConfigFileEntry configEntry = mConfigApi.getConfigEntry(configArea, configKey);
        if (configEntry == null) {
            AppLog.i("delete config dir " + configKeyDirFile.getAbsolutePath());
            configKeyDirFile.delete();
            return ;
        }
        
        int currentConfigVersion = configEntry.getConfigVersion();
        File[] configVersionFiles = configKeyDirFile.listFiles();
        for (File configVersionFile : configVersionFiles) {
            int fileVersion = 0;
            try {
                fileVersion = Integer.parseInt(configVersionFile.getName());
            } catch (NumberFormatException e) {
            }
            
            if (fileVersion >= currentConfigVersion) {
                continue;
            }
            
            AppLog.i("delete version file " + configVersionFile.getAbsolutePath());
            configVersionFile.delete();
        }
    }
    
    private File getHostingConfigsDir() throws ErrorInfo {
        try {
            return new HostingConfigPathBuilder().buildConfigsDir();
        } catch (ErrorInfo e) {
            if (e.getErrorCode() == TradeHostingBasicErrorCode.HOSTING_HAS_NOT_INITED.getValue()) {
                AppLog.i("configsDir empty because hosting not inited!");
                return null;
            }
            throw e;
        }
    }
    
    
}
