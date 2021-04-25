package xueqiao.trade.hosting.updator;

import org.apache.commons.lang.StringUtils;

public class ConfProviderImpl implements IConfProvider {

    @Override
    public boolean isNeedCheckMaintenanceTime() {
        String isCheckMT = System.getenv("IS_CHECK_MT");
        if (StringUtils.isNotEmpty(isCheckMT) && "0".equalsIgnoreCase(isCheckMT.trim())) {
            return false;
        }
        return true;
    }

    @Override
    public String getTradeHostingAppsISNamespace() {
        String thaIsNamepsace = System.getenv("THA_IS_NAMESPACE");
        if (StringUtils.isNotEmpty(thaIsNamepsace)) {
            return thaIsNamepsace;
        }
        return "xueqiao-trade";
    }

    @Override
    public String getTradeHostingAppsISName() {
        String thsIsName = System.getenv("THA_IS_NAME");
        if (StringUtils.isNotEmpty(thsIsName)) {
            return thsIsName;
        }
        return "trade-hosting-apps";
    }

    @Override
    public String getFastThriftProxyImage() {
        return System.getenv("FTP_IMAGE");
    }

    @Override
    public String getErrorCodeAgentImage() {
        return System.getenv("ECA_IMAGE");
    }
    
}
