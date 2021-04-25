package xueqiao.trade.hosting.position.statis.service.util;

import java.util.Calendar;

public class TimeUtil {

	/**
	 * 获取当天的0点时间缀
	 * @param millis 任意的时间缀
	 * @return millis所在当天（默认的时区）的0点时间缀
	 * */
	public static long getCurrentDateZeroMillis(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
}
