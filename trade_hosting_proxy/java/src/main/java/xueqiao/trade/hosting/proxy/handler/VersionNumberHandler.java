package xueqiao.trade.hosting.proxy.handler;

import org.soldier.platform.app.manager.thriftapi.VersionNum;

public class VersionNumberHandler {

    public static long getTotalNumber(VersionNum versionNum) {
        long totalNumber = versionNum.getReversionNum() +
                versionNum.getBuildVersionNum() * 10000L +
                versionNum.getMinorVersionNum() * 10000L * 10000L +
                versionNum.getMajorVersionNum() * 10000L * 10000L * 10000L;
        return totalNumber;
    }

    public static VersionNum map2DaoVersionNum(xueqiao.trade.hosting.proxy.VersionNum pVersionNum) {
        VersionNum versionNum = new VersionNum();
        versionNum.setMajorVersionNum(pVersionNum.getMajorVersionNum());
        versionNum.setMinorVersionNum(pVersionNum.getMinorVersionNum());
        versionNum.setBuildVersionNum(pVersionNum.getBuildVersionNum());
        versionNum.setReversionNum(pVersionNum.getReversionNum());
        return versionNum;
    }

    public static xueqiao.trade.hosting.proxy.VersionNum map2ProxyVersionNum(VersionNum dVersionNum) {
        xueqiao.trade.hosting.proxy.VersionNum versionNum = new xueqiao.trade.hosting.proxy.VersionNum();
        versionNum.setMajorVersionNum(dVersionNum.getMajorVersionNum());
        versionNum.setMinorVersionNum(dVersionNum.getMinorVersionNum());
        versionNum.setBuildVersionNum(dVersionNum.getBuildVersionNum());
        versionNum.setReversionNum(dVersionNum.getReversionNum());
        return versionNum;
    }
}
