package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;
import xueqiao.trade.hosting.terminal.ao.HostingUserSetting;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

public class HostingUserSettingHandler extends HandlerBase {
    private static final String USER_CONFIG_AREA = "USER";
    
    private IHostingConfigApi mConfigApi;
    
    public HostingUserSettingHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        mConfigApi = Globals.getInstance().queryInterfaceForSure(IHostingConfigApi.class);
    }
    
    private void checkKey(String key) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotEmpty(key), "key should not be empty");
        ParameterChecker.check(key.length() <= 32, "key's length should <= 32");
    }
    
    private String getConfigKey(String key) {
        StringBuilder configKeyBuilder = new StringBuilder(64);
        configKeyBuilder.append(getSubUserId()).append("_").append(key);
        return configKeyBuilder.toString();
    }

    public HostingUserSetting getUserSetting(String key) throws ErrorInfo {
        checkKey(key);
        HostingUserSetting setting= HostingUserSetting.class.cast(
                mConfigApi.getConfig(USER_CONFIG_AREA, getConfigKey(key), HostingUserSetting.class));
        if (setting == null) {
            setting = new HostingUserSetting();
            setting.setVersion(0);
        }
        return setting;
    }
    
    public void updateUserSetting(String key, HostingUserSetting setting) throws ErrorInfo {
        checkKey(key);
        ParameterChecker.checkNotNull(setting, "setting should not be null");
        ParameterChecker.check(setting.isSetVersion(), "setting's version should be set");
        ParameterChecker.check(setting.getVersion() > 0, "setting's version should > 0");
        ParameterChecker.check(setting.isSetContent(), "setting's content should be set");
        
        mConfigApi.setConfig(USER_CONFIG_AREA
                , getConfigKey(key)
                , setting.getVersion()
                , setting
                , null);
    }
    
    public int getUserSettingVersion(String key) throws ErrorInfo {
        checkKey(key);
        ConfigFileEntry configEntry = mConfigApi.getConfigEntry(USER_CONFIG_AREA, getConfigKey(key));
        if (configEntry == null) {
            return 0;
        }
        return configEntry.getConfigVersion();
    }
    
    
}
