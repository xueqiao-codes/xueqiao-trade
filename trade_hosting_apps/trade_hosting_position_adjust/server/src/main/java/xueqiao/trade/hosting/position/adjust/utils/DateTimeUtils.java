package xueqiao.trade.hosting.position.adjust.utils;

import com.longsheng.xueqiao.contract.thriftapi.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static final String FULL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String HOUR_PATTERN = "HH:mm:ss";
    public final static long DATE_LONG = 24 * 60 * 60 * 1000L;
    public final static long ONE_SEC = 1000L;
    public final static Days[] WEEK = new Days[]{Days.SUNDAY, Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY, Days.THURSDAY, Days.FRIDAY, Days.SATURDAY};

    /**
     * compare date time span
     * time : 20180928
     */
    public static SimpleDateFormat DATA_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public static String getDateStringDatePart(long time) {
        SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateSimpleDateFormat.format(new Date(time));
    }

    public static long getHourStringDateTime(String hour) {
        SimpleDateFormat hourSimpleDateFormat = new SimpleDateFormat(HOUR_PATTERN);
        try {
            return hourSimpleDateFormat.parse(hour).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getHourStringDatePart(long time) {
        SimpleDateFormat hourSimpleDateFormat = new SimpleDateFormat(HOUR_PATTERN);
        return hourSimpleDateFormat.format(new Date(time));
    }

    public static String getDateStringFull(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_DATE_TIME_PATTERN);
        return simpleDateFormat.format(new Date(time));
    }

    public static String getTimeHourStringPart(long time) {
        SimpleDateFormat hourSimpleDateFormat = new SimpleDateFormat(HOUR_PATTERN);
        return hourSimpleDateFormat.format(new Date(time));
    }


    public static long getDateTimeFull(String formatTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_DATE_TIME_PATTERN);
            Date date = simpleDateFormat.parse(formatTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDateTimeSecDatePart(String formatDay) {
        try {
            SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
            Date date = dateSimpleDateFormat.parse(formatDay);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Calendar getDateTimeDatePartDate(String formatDay) {
        try {
            SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
            Date date = dateSimpleDateFormat.parse(formatDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Days getDays(long startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        Days days = Days.findByValue(index - 1);
        return days;
    }
}
