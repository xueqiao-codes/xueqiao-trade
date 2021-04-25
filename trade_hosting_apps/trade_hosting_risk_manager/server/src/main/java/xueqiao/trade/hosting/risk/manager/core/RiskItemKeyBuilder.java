package xueqiao.trade.hosting.risk.manager.core;

import org.soldier.base.logger.AppLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

public class RiskItemKeyBuilder {

    public static String buildKey(String itemId, TreeMap<String, String> params) {
        StringBuilder keyBuilder = new StringBuilder(64);
        keyBuilder.append(itemId);

        if (params != null) {
            int paramIndex = 0;
            for (Map.Entry<String, String> e : params.entrySet()) {
                if (paramIndex > 0) {
                    keyBuilder.append("&");
                } else {
                    keyBuilder.append("?");
                }
                try {
                    keyBuilder.append(e.getKey())
                            .append("=")
                            .append(URLEncoder.encode(e.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    AppLog.f(e1.getMessage(), e1);
                }
                ++paramIndex;
            }
        }

        return keyBuilder.toString();

    }

}
