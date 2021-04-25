package xueqiao.trade.hosting.history.storage.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

public class HostingXQTargetUtil {

    public static String generateSymbol(final HostingXQTarget xqTarget) {
        Preconditions.checkNotNull(xqTarget);
        if (!xqTarget.isSetTargetType() || StringUtils.isEmpty(xqTarget.getTargetKey())) {
            return "";
        }

        StringBuilder symbolBuilder = new StringBuilder(42);
        symbolBuilder.append(xqTarget.getTargetType().getValue())
                     .append("_")
                     .append(xqTarget.getTargetKey());
        return symbolBuilder.toString();
    }

    public static HostingXQTarget generateTarget(final String targetSymbol) {
        if (StringUtils.isEmpty(targetSymbol)) {
            return null;
        }
        int pos = targetSymbol.indexOf("_");
        if (pos < 0) {
            return null;
        }

        try {
            HostingXQTarget xqTarget = new HostingXQTarget();
            xqTarget.setTargetType(HostingXQTargetType.findByValue(
                    NumberUtils.createInteger(targetSymbol.substring(0, pos))));
            xqTarget.setTargetKey(targetSymbol.substring(pos + 1));
            return xqTarget;
        } catch (Throwable e) {
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println(generateSymbol(generateTarget("1_123123sdfsdf")));
    }
}
