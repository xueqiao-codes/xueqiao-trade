package xueqiao.trade.hosting.position.fee.app;

import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;

/**
 * 全局信息
 */
public class AppData {
    private static TechPlatformEnv techPlatformEnv;

    public static TechPlatformEnv getTechPlatformEnv() {
        return techPlatformEnv;
    }

    public static void setTechPlatformEnv(TechPlatformEnv env) {
        techPlatformEnv = env;
    }

    /**
     * 获取默认基币
     * */
    public static String getDefaultBaseCurrency() {
        return "CNY";
    }
}
