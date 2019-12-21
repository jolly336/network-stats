package com.nelson.stats.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <a href="https://github.com/houko/SpringBootUnity/blob/master/core/src/main/java/info/xiaomo/core/untils/TimeUtil.java">SpringBootUnity</a>
 *
 * Created by Nelson on 2019/1/22.
 */
public class TimeUtil {

    public static final String DEFULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DATE_FORMAT_FILE = new SimpleDateFormat("yyyyMMdd");

    public static String timestampToDate(long timestamp, String style) {
        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINA);
        Date date = new Date(timestamp);
        return format.format(date);
    }

    public static String day() {
        return DATE_FORMAT_FILE.format(new Date());
    }

    public static String getTimeString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }

    /**
     * 获取今日零点时间
     */
    public static long getTodayZeroClockTimeInMillis() {
        return TimeUtil.getZeroClockTime(TimeUtil.getNowOfMills());
    }

    /**
     * 获取昨日零点时间
     */
    public static long getYesterdayZeroClockTimeInMillis() {
        Date date = TimeUtil.getDateFromNow(Calendar.DAY_OF_YEAR, -1);
        return TimeUtil.getZeroClockTime(date.getTime());
    }

    /**
     * 获取指定零点时间
     */
    public static long getZeroClockTimeInMillis(int days) {
        Date date = TimeUtil.getDateFromNow(Calendar.DAY_OF_YEAR, 1 - days);
        return TimeUtil.getZeroClockTime(date.getTime());
    }

    /**
     * 获取指定时间的零点时间
     */
    public static long getZeroClockTime(long time) {
        return TimeUtil.getTimeInMillis(time, 0, 0, 0, 0);
    }

    /**
     * 获取在指定时间戳和指定小时，分钟，秒，毫秒数的时间
     *
     * @param time 时间戳
     * @param hour 小时(24小时制)
     * @param minute 分钟
     * @param second 秒
     * @param milliSecond 毫秒
     */
    public static long getTimeInMillis(long time, int hour, int minute, int second, int milliSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回当前时间（单位/毫秒）
     *
     * @return long 毫秒
     */
    public static long getNowOfMills() {
        return System.currentTimeMillis();
    }


    /**
     * 获取指定的日期
     *
     * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
     * @param timenum 时间数字，譬如：-1 昨天，0 今天，1 明天
     * @return 日期
     */
    public static Date getDateFromNow(int timeType, int timenum) {
        Calendar cld = Calendar.getInstance();
        cld.set(timeType, cld.get(timeType) + timenum);
        return cld.getTime();
    }
}
