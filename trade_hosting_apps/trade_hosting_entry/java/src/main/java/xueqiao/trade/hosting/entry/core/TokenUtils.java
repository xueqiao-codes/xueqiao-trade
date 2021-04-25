package xueqiao.trade.hosting.entry.core;

import org.apache.commons.lang.math.RandomUtils;

public class TokenUtils {
	private static final int TOKEN_LENGTH = 64;
	
	public static String generateToken() {
		StringBuffer buffer = new StringBuffer(32);
		for (int index = 0; index < TOKEN_LENGTH; ++index) {
			buffer.append((char)((int)'a' + RandomUtils.nextInt(26)));
		}
		return buffer.toString();
	}
}
