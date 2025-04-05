package cn.easyz.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Relative date format.
 */
public class RelativeDateFormat {
    private static final FastDateFormat TIMESTAMP_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:m:s");
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws ParseException the parse exception
     */
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = format.parse("2011-11-11 11:11:11");
        System.out.println(format(date));
    }
    /**
     * Format string.
     *
     * @param date the date
     * @return the string
     */
    public static String format(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        // if (delta < 30L * ONE_DAY) {
        //     long days = toDays(delta);
        //     return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        // }
        // if (delta < 12L * 4L * ONE_WEEK) {
        //     long months = toMonths(delta);
        //     return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        // }
        // if (delta < 48L * 4L * ONE_WEEK) {
        //     long years = toYears(delta);
        //     return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        // }
        else {
            return TIMESTAMP_FORMAT.format(date);
        }
    }
    private static long toSeconds(long date) {
        return date / 1000L;
    }
    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }
    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }
    private static long toDays(long date) {
        return toHours(date) / 24L;
    }
    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }
    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
