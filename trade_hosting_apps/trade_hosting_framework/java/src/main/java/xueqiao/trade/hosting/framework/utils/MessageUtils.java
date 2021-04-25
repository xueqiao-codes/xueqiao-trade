package xueqiao.trade.hosting.framework.utils;

import org.apache.commons.lang.StringUtils;
import org.soldier.framework.message_bus.api.MessageAgent;

public class MessageUtils {
	public static void rmGuardMessageQuietly(MessageAgent messageAgent, String guardId) {
		if (!StringUtils.isEmpty(guardId)) {
			messageAgent.rmGuardMessage(guardId);
		}
	}
}
