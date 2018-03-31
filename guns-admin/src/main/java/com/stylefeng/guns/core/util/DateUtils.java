/*
 * Copyright 2009-2012 Evun Technology. 
 * 
 * This software is the confidential and proprietary information of
 * Evun Technology. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with evun.cn.
 */
package com.stylefeng.guns.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author wqb
 * @created 2012-12-19 上午9:49:48
 * @since v1.3.1
 */
public class DateUtils {

    public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CHINA_MINITE_DATE_PATTERN = "yyyy年MM月dd日  HH:mm";

    public static final String MAIL_CHINA_MINITE_DATE_PATTERN = "yyyy年MM月dd日 HH:mm (EEE)";

    public static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";

    public static final String CHINA_MONTH_DAY_DATE_PATTERN = "MM月dd日";

    public static final String CHINA_MONTH_DATE_PATTERN = "MM月dd日  HH:mm";

    public static final String HOUR_MINITE_PATTERN = "HH:mm";

    public static final String SECOND_PATTERN = "HH:mm:ss";

    public static final String MINITE_DATE_PATTERN = "yyyy-MM-dd HH:mm";

    public static final int MSEC_TYPE = 5;// 毫秒
    public static final int MINITE_TYPE = 0;
    public static final int HOUR_TYPE = 1;
    public static final int DAY_TYPE = 2;
    public static final int MONTH_TYPE = 3;
    public static final int YEAR_TYPE = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {
    }

    /**
     * <p>
     * Description: 分钟日期格式：2012-12-19 13:00:00
     * </p>
     *
     * @return String
     * @author wqb
     * @created 2012-12-19 上午9:48:17
     * @since v1.3.1
     */
    public static String formatDateMinite(Date date) {
        if (null == date) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(MINITE_DATE_PATTERN);
    }

    /**
     * <p>
     * Description: 长日期格式：2012-12-19 13:00:00
     * </p>
     *
     * @return String
     * @author wqb
     * @created 2012-12-19 上午9:48:17
     * @since v1.3.1
     */
    public static String formatTime(Date date) {
        if (null == date) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(LONG_DATE_PATTERN);
    }

    /**
     * <p>
     * Description: 短日期格式：2012-12-19
     * </p>
     *
     * @return String
     * @author wqb
     * @created 2012-12-19 上午9:48:53
     * @since v1.3.1
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(SHORT_DATE_PATTERN);
    }

    /**
     * <p>
     * Description: 指定日期模式格式化
     * </p>
     *
     * @return String
     * @author wqb
     * @created 2012-12-19 上午9:48:17
     * @since v1.3.1
     */
    public static String format(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    /**
     * <p>
     * Description: 指定日期模式格式化
     * </p>
     *
     * @return String
     * @author wqb
     * @created 2012-12-19 上午9:48:17
     * @since v1.3.1
     */
    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

    public static String getWeek(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfWeek().getAsText();
    }

    public static int getYear(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    /**
     * <p>
     * Description: 得到当前年数
     * </p>
     *
     * @author xiaoyaoyao
     * @created 2013-1-7 下午5:47:18
     * @since v1.3.1
     */
    public static int getCurrentYear() {
        DateTime dateTime = new DateTime();
        return dateTime.getYear();
    }

    public static Date parseDate(String source) {
        DateTime dateTime = new DateTime(source);
        return dateTime.toDate();
    }

    public static Date parseDate(String source, String pattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseDateTime(source).toDate();
    }

    public static Date parseTime(String source) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(LONG_DATE_PATTERN);
        return fmt.parseDateTime(source).toDate();
    }

    public static Date addMinutes(Date lockTime, Integer lockPeriod) {
        DateTime dateTime = new DateTime(lockTime);
        return dateTime.plusMinutes(lockPeriod).toDate();
    }

    public static Date addDays(Date pswBeainTime, Integer pswLife) {
        DateTime dateTime = new DateTime(pswBeainTime);
        return dateTime.plusDays(pswLife).toDate();
    }

    public static Date minusDays(Date pswBeainTime, Integer pswLife) {
        DateTime dateTime = new DateTime(pswBeainTime);
        return dateTime.minusDays(pswLife).toDate();
    }

    public static Date plusYears(Date pswBeainTime, Integer pswLife) {
        DateTime dateTime = new DateTime(pswBeainTime);
        return dateTime.plusYears(pswLife).toDate();
    }

    public static Date minusHours(Date pswBeainTime, Integer pswLife) {
        DateTime dateTime = new DateTime(pswBeainTime);
        return dateTime.minusHours(pswLife).toDate();
    }

    /**
     * 类似微博发布时间的时间处理方案
     */
    public static String converTime(long timeMillis) {
        long timeGap = (System.currentTimeMillis() - timeMillis) / 1000;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > 365 * 24 * 60 * 60) {// 1年以上
            timeStr = format(new Date(timeMillis), CHINA_MINITE_DATE_PATTERN);
        } else if (timeGap > 30 * 24 * 60 * 60) {// 1月以上
            timeStr = format(new Date(timeMillis), CHINA_MONTH_DATE_PATTERN);
        } else if (timeGap > 24 * 60 * 60) {// 1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {// 1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static String converTime2(Date date) {
        Calendar cal = parseDateTime(new Date());
        GregorianCalendar today = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
                0);
        GregorianCalendar yesterday = new GregorianCalendar(
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);

        String timeStr = null;
        String d = formatDate(date);
        String time = format(date, HOUR_MINITE_PATTERN);
        String todayStr = formatDate(today.getTime());
        String yesterdayStr = formatDate(yesterday.getTime());

        if (d.equals(todayStr)) {
            timeStr = "今天 " + time;
        } else if (d.equals(yesterdayStr)) {
            timeStr = "昨天 " + time;
        } else {
            timeStr = format(date, CHINA_MONTH_DATE_PATTERN);
        }
        return timeStr;
    }

    /**
     * <p>
     * Description: 上周的周日
     * </p>
     *
     * @return Date
     * @author Administrator
     * @created 2014-2-27 下午7:45:00
     * @since v1.3.1
     */
    @SuppressWarnings("deprecation")
    public static Date getPreviousWeekSunday() {
        int weeks = -1;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        monday.setHours(23);
        monday.setMinutes(59);
        monday.setSeconds(59);
        return monday;
    }

    /**
     * <p>
     * Description: 上周的周一
     * </p>
     *
     * @return Date
     * @author Administrator
     * @created 2014-2-27 下午7:44:49
     * @since v1.3.1
     */
    @SuppressWarnings("deprecation")
    public static Date getPreviousWeekMonday() {
        int weeks = -1;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        monday.setHours(0);
        monday.setMinutes(0);
        monday.setSeconds(0);
        return monday;
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(7) - 1;
        if (dayOfWeek == 1) {
            return 0;
        }
        return (1 - dayOfWeek);
    }

    /**
     * <p>
     * Description: 获取本周的日期
     * </p>
     *
     * @param day
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午4:31:43
     * @since v1.3.1
     */
    public static Date getCurrentWeekDay(int day) {
        return getWeekDay(new Date(), day);
    }

    /**
     * <p>
     * Description: 获取本周的日期
     * </p>
     *
     * @param day
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午4:31:43
     * @since v1.3.1
     */
    public static Date getWeekDay(Date d, int day) {
        Calendar cal = parseDateTime(d);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, day + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 获取天时间点
     * </p>
     *
     * @param hour
     * @param minute
     * @param second
     * @return Date
     * @author chentq
     * @created 2014-6-10 上午10:27:43
     * @since v1.3.1
     */
    public static Date getDayTime(Date d, int hour, int minute, int second) {
        Calendar cal = parseDateTime(d);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 获取当天时间点
     * </p>
     *
     * @param hour
     * @param minute
     * @param second
     * @return Date
     * @author chentq
     * @created 2014-6-10 上午10:27:43
     * @since v1.3.1
     */
    public static Date getTodayTime(int hour, int minute, int second) {
        return getDayTime(new Date(), hour, minute, second);
    }

    /**
     * <p>
     * Description: 获取本月的第一天
     * </p>
     *
     * @param date
     * @return String
     * @author chentq
     * @created 2014-6-4 上午9:07:15
     * @since v1.3.1
     */
    public static String getCurrentMonthFirstDay(Date date) {
        return formatDate(getMonthFirstDay(date));
    }

    /**
     * <p>
     * Description: 获取当月最后一天
     * </p>
     *
     * @param date
     * @return String
     * @author chentq
     * @created 2014-8-4 上午8:52:39
     * @since v1.3.1
     */
    public static String getCurrentMonthLastDay(Date date) {
        return formatDate(getMonthLastDay(date));
    }

    /**
     * <p>
     * Description: 获取月第一天
     * </p>
     *
     * @param date
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午5:03:14
     * @since v1.3.1
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 获取月最后一天
     * </p>
     *
     * @param date
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午5:04:08
     * @since v1.3.1
     */
    public static Date getMonthLastDay(Date date) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 获取月指定的日期
     * </p>
     *
     * @param date
     * @param day
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午4:56:53
     * @since v1.3.1
     */
    public static Date getMonthDay(Date date, int day) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 获取月份
     * </p>
     *
     * @param date
     * @param month
     * @param type  1月初,2月末
     * @return Date
     * @author chentq
     * @created 2014-6-10 下午5:19:11
     * @since v1.3.1
     */
    public static Date getMonth(Date date, int month, int type) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.MONTH, month - 1);
        if (2 == type) {
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            cal.set(Calendar.DAY_OF_MONTH,
                    cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * <p>
     * Description: 计算两个日期间的差值,需要指定日期格式来转化日期
     * </p>
     *
     * @param startdate
     * @param enddate
     * @param format
     * @param iType
     * @return int
     * @author chentq
     * @created 2014-5-28 上午11:08:00
     * @since v1.3.1
     */
    public static int dateCal(String startdate, String enddate, String format,
                              int iType) {
        return dateCal(parseDate(startdate, format),
                parseDate(enddate, format), iType);
    }

    /**
     * <p>
     * Description: 计算两个日期间的差值
     * </p>
     *
     * @param startdate
     * @param enddate
     * @param iType
     * @return int
     * @author chentq
     * @created 2014-5-28 上午11:01:15
     * @since v1.3.1
     */
    public static int dateCal(Date startdate, Date enddate, int iType) {
        Calendar calBegin = parseDateTime(startdate);
        Calendar calEnd = parseDateTime(enddate);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        int ss = (int) ((lEnd - lBegin) / 1000L);
        int min = ss / 60;
        int hour = min / 60;
        int day = hour / 24;
        if (iType == MINITE_TYPE) {
            return min;
        }
        if (iType == HOUR_TYPE) {
            return hour;
        }
        if (iType == DAY_TYPE) {
            return day;
        }
        if (iType == MSEC_TYPE) {
            return (int) ((lEnd - lBegin));
        } else {
            return -1;
        }
    }

    /**
     * <p>
     * Description: 计算两个日期间的差值(得到阶梯型数据) startdate - enddate
     * </p>
     *
     * @param startdate
     * @param enddate
     * @param iType
     * @return int
     * @author zhouhb
     * @created 2014-5-28 上午11:01:15
     * @since v1.3.1
     */
    public static long dateCal2(Date startdate, Date enddate, int iType) {
        Calendar calBegin = parseDateTime(startdate);
        Calendar calEnd = parseDateTime(enddate);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        long ss = (long) ((lEnd - lBegin) / 1000L);
        long day = ss / (24 * 60 * 60);
        long hour = (ss / (60 * 60) - day * 24);
        long min = ((ss / (60)) - day * 24 * 60 - hour * 60);
        if (iType == MINITE_TYPE) {
            return min;
        }
        if (iType == HOUR_TYPE) {
            return hour;
        }
        if (iType == DAY_TYPE) {
            return day;
        } else {
            return -1;
        }
    }

    public static double dateCal3(Date startdate, Date enddate, int iType) {
        Calendar calBegin = parseDateTime(startdate);
        Calendar calEnd = parseDateTime(enddate);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        long ss = (long) ((lEnd - lBegin) / 1000L);
        if (iType == MINITE_TYPE) {
            return (double) ss / (60);
        }
        if (iType == HOUR_TYPE) {
            return (double) ss / (60 * 60);
        }
        if (iType == DAY_TYPE) {
            return (double) ss / (24 * 60 * 60);
        } else {
            return -1;
        }
    }

    public static Calendar parseDateTime(Date d) {

        Calendar cal = Calendar.getInstance();
        int yy = 0, mm = 0, dd = 0, hh = 0, mi = 0, ss = 0;
        cal.setTime(d);

        yy = cal.get(Calendar.YEAR);
        mm = cal.get(Calendar.MONTH);
        dd = cal.get(Calendar.DAY_OF_MONTH);
        hh = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);
        ss = cal.get(Calendar.SECOND);

        cal.set(yy, mm, dd, hh, mi, ss);
        return cal;
    }

    public static Date getWeekStartDay(Date date) {
        return getWeekDay(date, 1);
    }

    public static Date getWeekEndDay(Date date) {
        Calendar cal = parseDateTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    public static Date getDayStart(Date date) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar cal = parseDateTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    // 获取所在月份天数
    public static int getMonthDays(Date date) {
        Calendar cal = parseDateTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取昨日日期
     *
     * @return
     */
    public static String getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat(
                SHORT_DATE_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
        return sdf.format(cal.getTime());
    }

    /**
     *
     *
     * Description:
     *
     * @author
     * @created 2015-6-16
     * @return void
     */
    /**
     * 处理日期 如果是 周末 或者工作日的17:00后，则返回当前日期加上相应天数(1到3天)之后的8:00 如果是 工作日 并且在工作时间8:00
     * ~17:00范围内 则直接返回时间， 如果在工作日的8:00前 则返回当天的8:00
     */
    // 待减去的天数
    private static String[][] weekday_gb = {{"星期日", "1"}, {"星期一", "1"},
            {"星期二", "1"}, {"星期三", "1"}, {"星期四", "1"}, {"星期五", "3"},
            {"星期六", "2"}};

    public static Date dealDate(Date paramDate) throws Exception {
        Calendar paramCal = Calendar.getInstance();
        Date returnDate = null;
        if (paramCal != null && paramDate != null) {
            paramCal.setTime(paramDate);
            returnDate = paramDate;
            // 将工作开始时间设定为日期当天的8:00
            Calendar workBegin = Calendar.getInstance();
            workBegin.set(Calendar.YEAR, paramCal.get(Calendar.YEAR));
            workBegin.set(Calendar.MONTH, paramCal.get(Calendar.MONTH));
            workBegin.set(Calendar.DATE, paramCal.get(Calendar.DATE));
            workBegin.set(Calendar.HOUR_OF_DAY, 8);
            workBegin.set(Calendar.MINUTE, 00);
            workBegin.set(Calendar.SECOND, 0);
            workBegin.set(Calendar.MILLISECOND, 0);
            Calendar workEnd = Calendar.getInstance();
            workEnd.set(Calendar.YEAR, paramCal.get(Calendar.YEAR));
            workEnd.set(Calendar.MONTH, paramCal.get(Calendar.MONTH));
            workEnd.set(Calendar.DATE, paramCal.get(Calendar.DATE));
            workEnd.set(Calendar.HOUR_OF_DAY, 17);
            workEnd.set(Calendar.MINUTE, 00);
            workEnd.set(Calendar.SECOND, 0);
            workEnd.set(Calendar.MILLISECOND, 0);
            if ((paramCal.get(Calendar.DAY_OF_WEEK) - 1) == 6
                    || (paramCal.get(Calendar.DAY_OF_WEEK) - 1) == 0
                    || paramCal.compareTo(workEnd) >= 0) {
                // 如果是 周末 或者是工作日的17:00以后，则返回当前日期加上相应天数(1到3天)的8:00
                Integer dday = 0;
                try {
                    dday = Integer.valueOf((weekday_gb[paramCal
                            .get(Calendar.DAY_OF_WEEK) - 1][1]));
                } catch (NumberFormatException e) {
                    LOGGER.error("numberFormat error", e);
                }
                paramCal.add(Calendar.DAY_OF_MONTH, +dday);
                paramCal.set(Calendar.HOUR_OF_DAY, 8);
                paramCal.set(Calendar.MINUTE, 00);
                paramCal.set(Calendar.SECOND, 0);
                paramCal.set(Calendar.MILLISECOND, 0);
                returnDate = paramCal.getTime();
            } else {
                if (paramCal.compareTo(workBegin) <= 0) {
                    // 如果是 工作日 并且在工作时间8点之前的 返回当天的8点
                    returnDate = workBegin.getTime();
                } else if (paramCal.compareTo(workEnd) <= 0) {
                    // 如果是 工作日 并且在工作时间8:00 ~17:00范围内 则直接返回时间，
                    returnDate = paramCal.getTime();
                }
            }
        }
        return returnDate;
    }

    /**
     * @param paramDate :待比较日期 days:期限天数
     * @return Boolean
     */
    public static Boolean ifOverDeadLine(Date paramDate, Double days) {
        // compareDate（待比较时间） 为 系统时间 加上设置的天数
        Calendar sysdateCalender = Calendar.getInstance();
        sysdateCalender.setTime(new Date());
        // 判断如果传入时间加上期限天数 比系统时间早 则返回TRUE
        try {
            Calendar paramClendar = Calendar.getInstance();
            paramClendar.setTime(getDelayTime(paramDate, days));
            if (paramClendar.compareTo(sysdateCalender) < 0) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            LOGGER.error("error.ifOverDeadLine", e);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public static Boolean isDelay(Date paramDate, Double days) {
        Date pDate = null;
        try {
            pDate = dealDate(paramDate);
        } catch (Exception e) {
            LOGGER.error("error.isDelay", e);
            return Boolean.FALSE;
        }
        if (pDate == null) {
            pDate = new Date();
        }
        return ifOverDeadLine(pDate, days);
    }

    /**
     * 根据传入的提醒天数与日期获取延期时间 Description:
     *
     * @param paramDate
     * @param days
     * @return void
     */
    private static Date getDelayTime(Date paramDate, Double days) {

        // paramDate 类型转换 Date=>Calendar
        Calendar paramClender = Calendar.getInstance();
        paramClender.setTime(paramDate);
        // int d = Integer.parseInt(new
        // java.text.DecimalFormat("0").format(days));
        double realDays = getRealAddDays(days, paramDate);
        String[][] timeString = doubleDayToDate(realDays);
        paramClender.add(Calendar.DATE, Integer.parseInt(timeString[0][1]));
        paramClender.add(Calendar.HOUR, Integer.parseInt(timeString[1][1]));
        paramClender.add(Calendar.MINUTE, Integer.parseInt(timeString[2][1]));
        paramClender.add(Calendar.SECOND, Integer.parseInt(timeString[3][1]));
        return paramClender.getTime();
    }

    /**
     * 根据传入天数（double型）转换成二维数组 计算出天数、小时、分钟、秒
     * <p>
     * Description:
     * </p>
     *
     * @param days
     * @return String[][]
     * @author ljh
     * @created 2015-6-16 下午4:01:50
     * @since v1.3.1
     */
    public static String[][] doubleDayToDate(double days) {
        int day = (int) days;
        double partDay = days - day;
        int hour = (int) (partDay * 24);
        double partHour = (partDay * 24) - hour;
        int min = (int) (partHour * 60);
        double partMin = (partHour * 60) - min;
        int sec = (int) (partMin * 60);
        return new String[][]{{"day", String.valueOf(day)},
                {"hour", String.valueOf(hour)},
                {"min", String.valueOf(min)}, {"sec", String.valueOf(sec)}};
    }

    /**
     * 根据传入日期与待加的天数计算出实际应该加的天数(如遇周末则要把周末天数增加上去)
     * <p>
     * Description:
     * </p>
     *
     * @param days
     * @return double
     * @author ljh
     * @created 2015-6-16 下午5:07:45
     * @since v1.3.1
     */
    public static double getRealAddDays(double days, Date pDate) {
        Calendar paramClender = Calendar.getInstance();
        paramClender.setTime(pDate);
        int intDay = (int) days;
        int weekendFlag = 0;
        // 循环，如果包含周末则计数++
        for (int i = 0; i < intDay; i++) {
            // 每次循环增加一天
            paramClender.add(Calendar.DATE, 1);
            if (paramClender.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || paramClender.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                weekendFlag++;
            }
        }
        return (weekendFlag + days);
    }

    /**
     * 计算工作日内两个日期间的天数
     * <p>
     * Description:
     * </p>
     *
     * @param begDate
     * @param endDate
     * @return int
     * @author ljh
     * @created 2015-8-3 下午3:50:29
     * @since v1.3.1
     */
    private static int workDateForDay(Date begDate, Date endDate) {
        if (begDate.after(endDate)) {
            return 0;
        }
        // 总天数
        int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        // 总周数，
        int weeks = days / 7;
        int rs = 0;
        // 整数周
        if (days % 7 == 0) {
            rs = days - 2 * weeks;
        } else {
            Calendar begCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            begCalendar.setTime(begDate);
            endCalendar.setTime(endDate);
            // 周日为1，周六为7
            int beg = begCalendar.get(Calendar.DAY_OF_WEEK);
            int end = endCalendar.get(Calendar.DAY_OF_WEEK);
            if (beg > end) {
                rs = days - 2 * (weeks + 1);
            } else if (beg < end) {
                if (end == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            } else {
                if (beg == 1 || beg == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            }
        }
        return rs;
    }

    /**
     * 取两个日期之间的工作时间
     * <p>
     * Description:
     * </p>
     *
     * @param begDate
     * @param endDate
     * @return String （xx天xx时xx分）
     * @author ljh
     * @created 2015-8-3 下午4:22:13
     * @since v1.3.1
     */
    public static String getWorkDayStr(Date begDate, Date endDate) {
        long workDays = 0;// 天数
        long hours = 0;// 小时数
        long min = 0;// 分钟数
        try {
            Date begDateFmt = dealDate(begDate);
            Date endDateFmt = dealDate(endDate);
            workDays = workDateForDay(begDateFmt, endDateFmt);// 取出两个日期之间的天数
            // 分别计算两个时间与早上上班时间8:00之间的小时数之差
            Calendar begCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            begCal.setTime(begDateFmt);
            endCal.setTime(endDateFmt);
            SimpleDateFormat sf = new SimpleDateFormat(SECOND_PATTERN);
            if (workDays >= 2) {
                workDays = workDays - 2;
                long[] res = dateDiff(sf.format(begCal.getTime()), "17:00:00",
                        SECOND_PATTERN);
                hours = res[1];
                if (hours >= 5) {// 如果减出来的结果大于等于5了 则--
                    hours--;
                }
                min = res[2];
                res = dateDiff(sf.format(endCal.getTime()), "8:00:00",
                        SECOND_PATTERN);
                hours += res[1];
                min += res[2];
                // }
                if (min >= 60) {
                    hours++;
                    min -= 60;
                }
                if (hours >= 8) {// 如果计算出来的小时数大于8小时了 则增加天数并减8小时
                    hours -= 8;
                    workDays++;
                }
            } else if (workDays == 1) {
                workDays--;
                long[] res = dateDiff(sf.format(begCal.getTime()), "17:00:00",
                        SECOND_PATTERN);
                hours = res[1];
                if (hours >= 5) {// 如果减出来的结果大于等于5了 则--
                    hours--;
                }
                min = res[2];
                res = dateDiff(sf.format(endCal.getTime()), "8:00:00",
                        SECOND_PATTERN);
                hours += res[1];
                min += res[2];
                // }
                if (min >= 60) {
                    hours++;
                    min -= 60;
                }
                if (hours >= 8) {// 如果计算出来的小时数大于8小时了减8小时
                    hours -= 8;
                }
            } else {
                workDays = 0;
                long[] res = dateDiff(sf.format(endCal.getTime()),
                        sf.format(begCal.getTime()), SECOND_PATTERN);
                if (endCal.get(Calendar.HOUR_OF_DAY) >= 17) {
                    res = dateDiff(sf.format(begCal.getTime()), "17:00:00",
                            SECOND_PATTERN);
                }
                hours += res[1];
                min += res[2];
            }
        } catch (Exception e) {
            LOGGER.error("error.getWorkDayStr", e);
        }
        return workDays + "天" + hours + "时" + min + "分";
    }

    /**
     * <p>
     * Description:
     * </p>
     *
     * @param startTime
     * @param endTime
     * @param format    HH:MM:SS
     * @return long[]
     * @throws Exception
     * @author ljh
     * @created 2015-8-4 上午8:55:10
     * @since v1.3.1
     */
    public static long[] dateDiff(String startTime, String endTime,
                                  String format) throws Exception {
        long[] result = new long[4];
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
        } catch (Exception e) {
            LOGGER.error("error.dateDiff", e);
            throw e;
        }
        result[0] = Math.abs(day);
        result[1] = Math.abs(hour);
        result[2] = Math.abs(min);
        result[3] = Math.abs(sec);
        return result;
    }

    public static long[] getWorkDay(Date startTime, Date endTime) throws Exception {
        return DateUtils.getWorkDay(startTime, endTime,
                DateUtils.parseDate("08:00:00", SECOND_PATTERN),
                DateUtils.parseDate("20:00:00", SECOND_PATTERN));
    }

    /**
     * onDate 上班时间 offDate 下班时间 只能计算同一天的工作时长
     *
     * @throws Exception
     */
    public static long[] getWorkDay(Date startTime, Date endTime, Date onDate,
                                    Date offDate) throws Exception {
        if (startTime == null || endTime == null) {
            return null;
        }
        if (!DateUtils.format(startTime, SHORT_DATE_PATTERN).equals(
                DateUtils.format(endTime, SHORT_DATE_PATTERN))) {
            return null;
        }
        if (startTime.getTime() >= endTime.getTime()) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(onDate);
        if (cal.get(Calendar.HOUR_OF_DAY) < cal1.get(Calendar.HOUR_OF_DAY)) {
            cal.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
            startTime = cal.getTime();
        }
        cal.setTime(endTime);
        cal1.setTime(offDate);
        if (cal.get(Calendar.HOUR_OF_DAY) >= cal1.get(Calendar.HOUR_OF_DAY)) {
            cal.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
            endTime = cal.getTime();
        }
        if (startTime.getTime() >= endTime.getTime()) {
            return null;
        }
        return DateUtils.dateDiff(DateUtils.format(startTime, SECOND_PATTERN),
                DateUtils.format(endTime, SECOND_PATTERN), SECOND_PATTERN);
    }

    /**
     * now 之后的一个月
     *
     * @param now
     * @return
     */
    public static Date getNextMonthFromToday(Date now) {
        // 得到从now后的1个月的日期
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, 1);

        return c.getTime();

    }

    public static void main(String[] args) {
        double a = DateUtils.dateCal3(DateUtils.parseDate(
                "2015-02-11 11:00:00", DateUtils.LONG_DATE_PATTERN), DateUtils
                        .parseDate("2015-02-12 12:58:00", DateUtils.LONG_DATE_PATTERN),
                1);
        //	System.out.println(a);
    }

}
