package xueqiao.trade.hosting.position.fee.common.util;

import java.util.List;

public class CollectionUtil {
    public static boolean isListNotBlank(List list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isListBlank(List list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static int getListSize(List list) {
        return list == null ? 0 : list.size();
    }
}
