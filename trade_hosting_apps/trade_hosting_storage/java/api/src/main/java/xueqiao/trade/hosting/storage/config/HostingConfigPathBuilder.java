package xueqiao.trade.hosting.storage.config;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.storage.apis.IHostingManageApi;

public class HostingConfigPathBuilder {
    private IHostingManageApi mManageApi;
    
    private String mConfigArea;
    private String mConfigKey;
    private int mConfigVersion = -1;
    
    public HostingConfigPathBuilder() {
        mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
    }
    
    public HostingConfigPathBuilder setConfigArea(String configArea) {
        this.mConfigArea = configArea;
        return this;
    }
    
    public HostingConfigPathBuilder setConfigKey(String configKey) {
        this.mConfigKey = configKey;
        return this;
    }
    
    public HostingConfigPathBuilder setConfigVersion(int configVersion) {
        this.mConfigVersion = configVersion;
        return this;
    }
    
    public File buildConfigsDir() throws ErrorInfo {
        return new File(mManageApi.getHostingDataDir(), "configs");
    }
    
    public File buildDir() throws ErrorInfo, UnsupportedEncodingException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(mConfigArea));
        Preconditions.checkArgument(StringUtils.isNotEmpty(mConfigKey));
        
        StringBuilder subPathBuilder = new StringBuilder(64);
        subPathBuilder.append(URLEncoder.encode(mConfigArea, "UTF-8"))
                      .append("/").append(URLEncoder.encode(mConfigKey, "UTF-8"));
        
        return new File(buildConfigsDir(),  subPathBuilder.toString());
    }
    
    public File buildFile() throws ErrorInfo, UnsupportedEncodingException {
        Preconditions.checkArgument(mConfigVersion > 0);
        return new File(buildDir(), String.valueOf(mConfigVersion));
    }
}
