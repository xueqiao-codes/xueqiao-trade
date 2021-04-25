package xueqiao.trade.hosting.arbitrage.core.composelimit;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.events.XQCLOSettingsChangedEvent;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;

import java.util.concurrent.ConcurrentHashMap;

public class ComposeSettingsProvider {
    private static final String CONFIG_AREA = "COMORS";
    private static ConcurrentHashMap<String, ComposeSettingsProvider> sProviders = new ConcurrentHashMap<>();

    public static ComposeSettingsProvider get(String key) throws ErrorInfo {
        ComposeSettingsProvider instance = sProviders.get(key);
        if (instance == null) {
            synchronized (ComposeSettingsProvider.class) {
                instance = sProviders.get(key);
                if (instance == null) {
                    instance = new ComposeSettingsProvider(key);
                    sProviders.put(key, instance);
                }
            }
        }
        return instance;
    }

    private String mKey;
    private volatile HostingXQComposeLimitOrderSettings mSettings;

    private ComposeSettingsProvider(String key) throws ErrorInfo {
        this.mKey = key;
        this.mSettings = readSettings();
        logSettings();
    }

    public HostingXQComposeLimitOrderSettings getSettings() {
        return mSettings;
    }

    public void updateSettings(HostingXQComposeLimitOrderSettings settings) throws ErrorInfo {
        if (settings == null) {
            return;
        }

        HostingXQComposeLimitOrderSettings newSettings
                = new HostingXQComposeLimitOrderSettings(mSettings);
        if (settings.isSetDefaultChaseTicks() && settings.getDefaultChaseTicks() >= 0) {
            newSettings.setDefaultChaseTicks(settings.getDefaultChaseTicks());
        }
        if (settings.isSetMaxChaseTicks() && settings.getMaxChaseTicks() >= 0) {
            newSettings.setMaxChaseTicks(settings.getMaxChaseTicks());
        }
        if (newSettings.getMaxChaseTicks() < newSettings.getDefaultChaseTicks()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "maxChaseTicks should >= defaultChaseTicks");
        }

        if (settings.isSetMaxInvolRevokeLimitNum() && settings.getMaxInvolRevokeLimitNum() > 0) {
            newSettings.setMaxInvolRevokeLimitNum(settings.getMaxInvolRevokeLimitNum());
        }
        if (settings.isSetDefaultInvolRevokeLimitNum() && settings.getDefaultInvolRevokeLimitNum() > 0) {
            newSettings.setDefaultInvolRevokeLimitNum(settings.getDefaultInvolRevokeLimitNum());
        }
        if (newSettings.getMaxInvolRevokeLimitNum() < newSettings.getDefaultInvolRevokeLimitNum()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "maxInvolRevokeLimitNum should >= defaultInvolRevokeLimitNum");
        }

        if (settings.isSetDefaultPriceProtectRatio() && settings.getDefaultPriceProtectRatio() > 0.0) {
            newSettings.setDefaultPriceProtectRatio(settings.getDefaultPriceProtectRatio());
        }
        if (settings.isSetMinQuantityRatio() && settings.getMinQuantityRatio() > 0.0) {
            newSettings.setMinQuantityRatio(settings.getMinQuantityRatio());
        }
        if (newSettings.getMinQuantityRatio() > newSettings.getDefaultQuantityRatio()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "minQuantityRatio should <= defaultQuantityRatio");
        }

        if (settings.isSetDefaultPriceProtectRatio() && settings.getDefaultQuantityRatio() > 0.0) {
            newSettings.setDefaultQuantityRatio(settings.getDefaultQuantityRatio());
        }
        if (settings.isSetMaxPriceProtectRatio() && settings.getMaxPriceProtectRatio() > 0.0) {
            newSettings.setMaxPriceProtectRatio(settings.getMaxPriceProtectRatio());
        }
        if (newSettings.getMaxPriceProtectRatio() < newSettings.getDefaultPriceProtectRatio()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "maxPriceProtectRatio should >= defaultPriceProtectRatio");
        }

        updateSettintsImpl(settings);
        logSettings();
    }

    private synchronized void updateSettintsImpl(HostingXQComposeLimitOrderSettings newSettings) throws ErrorInfo {
        IHostingConfigApi configApi = Globals.getInstance().queryInterfaceForSure(IHostingConfigApi.class);

        int configVersion = 0;
        ConfigFileEntry fileEntry = configApi.getConfigEntry(CONFIG_AREA, mKey);
        if (fileEntry == null) {
            configVersion = 1;
        } else {
            configVersion = fileEntry.getConfigVersion() + 1;
        }

        XQCLOSettingsChangedEvent notifyEvent = new XQCLOSettingsChangedEvent();
        notifyEvent.setKey(mKey);
        configApi.setConfig(CONFIG_AREA, mKey, configVersion, newSettings, notifyEvent);

        mSettings = newSettings;
    }

    private HostingXQComposeLimitOrderSettings readSettings() throws ErrorInfo {
        IHostingConfigApi configApi = Globals.getInstance().queryInterfaceForSure(IHostingConfigApi.class);
        HostingXQComposeLimitOrderSettings settings = null;
        try {
            settings = HostingXQComposeLimitOrderSettings.class.cast(
                    configApi.getConfig(CONFIG_AREA, mKey, HostingXQComposeLimitOrderSettings.class));
        } catch (ErrorInfo e) {
            if (e.getErrorCode() != TradeHostingBasicErrorCode.ERROR_CONFIG_LOST.getValue()) {
                throw e;
            }
        }

        if (settings == null) {
            settings = new HostingXQComposeLimitOrderSettings();
        }
        return fillSettings(settings);
    }

    private HostingXQComposeLimitOrderSettings fillSettings(HostingXQComposeLimitOrderSettings settings) {
        if (!settings.isSetDefaultChaseTicks()) {
            settings.setDefaultChaseTicks(0);
        }
        if (!settings.isSetMaxChaseTicks()) {
            settings.setMaxChaseTicks(3);
        }

        if (!settings.isSetMaxInvolRevokeLimitNum()) {
            settings.setMaxInvolRevokeLimitNum(10);
        }
        if (!settings.isSetDefaultInvolRevokeLimitNum()) {
            settings.setDefaultInvolRevokeLimitNum(3);
        }

        if (!settings.isSetDefaultQuantityRatio()) {
            settings.setDefaultQuantityRatio(5.0);
        }
        if (!settings.isSetMinQuantityRatio()) {
            settings.setMinQuantityRatio(0.1);
        }

        if (!settings.isSetDefaultPriceProtectRatio()) {
            settings.setDefaultPriceProtectRatio(0.005);
        }
        if (!settings.isSetMaxPriceProtectRatio()) {
            settings.setMaxPriceProtectRatio(0.01);
        }

        return settings;
    }

    private void logSettings() {
        if (AppLog.infoEnabled()) {
            AppLog.i("HostingXQComposeLimitOrderSettings for mKey=" + mKey + " is " + mSettings);
        }
    }

}
