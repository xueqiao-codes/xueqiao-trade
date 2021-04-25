package xueqiao.trade.hosting.storage.apis.structs;

public class ConfigFileEntry {
    private String configArea;
    private String configKey;
    
    private int configVersion = -1;
    
    private String configFilePath;
    private String configFileMd5;
    
    private int createTimestamp;
    private int lastmodifyTimestamp;
    
    public String getConfigArea() {
        return configArea;
    }
    public void setConfigArea(String configArea) {
        this.configArea = configArea;
    }
    
    public int getConfigVersion() {
        return configVersion;
    }
    public void setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
    }
    
    public String getConfigFilePath() {
        return configFilePath;
    }
    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }
    
    public String getConfigFileMd5() {
        return configFileMd5;
    }
    public void setConfigFileMd5(String configFileMd5) {
        this.configFileMd5 = configFileMd5;
    }
    
    public int getCreateTimestamp() {
        return createTimestamp;
    }
    public void setCreateTimestamp(int createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
    
    public int getLastmodifyTimestamp() {
        return lastmodifyTimestamp;
    }
    public void setLastmodifyTimestamp(int lastmodifyTimestamp) {
        this.lastmodifyTimestamp = lastmodifyTimestamp;
    }
    
    public String getConfigKey() {
        return configKey;
    }
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
    
    
    
}
