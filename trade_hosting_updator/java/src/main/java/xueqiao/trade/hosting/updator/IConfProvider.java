package xueqiao.trade.hosting.updator;

public interface IConfProvider {
    public boolean isNeedCheckMaintenanceTime(); // 是否需要检测维护时间段
    
    public String getTradeHostingAppsISNamespace();  // trade_hosting_apps的镜像空间
    
    public String getTradeHostingAppsISName(); // trade_hosting_apps的镜像名称
    
    public String getFastThriftProxyImage();  // fast_thrift_proxy的镜像URI
    
    public String getErrorCodeAgentImage();  // errorcode-agent的镜像URI
}
