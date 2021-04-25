package xueqiao.trade.hosting.storage.apis;

import org.apache.thrift.TBase;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;

@SuppressWarnings("rawtypes")
public interface IHostingConfigApi {
    public ConfigFileEntry getConfigEntry(String configArea, String configKey) throws ErrorInfo;
    
    public TBase getConfig(String configArea
            , String configKey
            , Class<? extends TBase> configClazz) throws ErrorInfo ;
    
    public void setConfig(String configArea
            , String configKey
            , int configVersion
            , TBase configContent
            , TBase notifyEvent) throws ErrorInfo;
    
    public void clearConfigs(String configArea, TBase notifyEvent) throws ErrorInfo;
}
