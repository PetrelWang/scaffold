package com.bbc.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 时间，日期工具类
 *
 * @author ccs
 * @date 2020 /11/9
 */
public class DateTimeUtil {

    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_TIME_2 = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_2 = "yyyyMMdd";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_3 = "yyyy/MM/dd";
    public static final String FORMAT_YEAR = "yyyy";
    public static final String FORMAT_MONTH = "MM";
    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String FORMAT_DATE_HOUR = "yyyy-MM-dd HH";
    public static final String FORMAT_DATE_MINUTE = "yyyy-MM-dd HH:mm";

    public static String dateToString(Date date,
                                      String format) {
        String datetime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            datetime = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    public static String dateToString(Date date) {
        String datetime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        try {
            datetime = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    public static String dateToString(LocalDateTime date,
                                      String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        String time = date.format(formatter);
        return time;
    }

    public static Date stringToDate(String dateString) {
        Date parse = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
        try {
            parse = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public static Date stringToYM(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH);
        return sdf.parse(dateString);
    }


    public static String getCurrentDateTime(String format) {
        return dateToString(new Date(), format);
    }

    public static String getCurrentTime(String format) {
        return dateToString(new Date(), format);
    }

    public static String getCurrentDate() {
        return dateToString(new Date(), FORMAT_DATE);
    }

    public static int getDaysBetween(String startDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        int dayGap = 0;
        if (startDate != null && startDate.length() > 0 && endDate != null
                && endDate.length() > 0) {
            Date end = format.parse(endDate);
            Date start = format.parse(startDate);
            dayGap = (int) ((end.getTime() - start.getTime()) / (3600000 * 24));
        }
        return dayGap;
    }

    public static int getDaysBetween(Date startDate, Date endDate) {
        int dayGap = (int) ((endDate.getTime() - startDate.getTime()) / (3600000 * 24));
        return dayGap;
    }

    /**
     * 计算两个日期相差月数
     * 在非重置情况下，如果起始日期的天大于结束日期的天，月数要少算1（不足1个月）
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param isReset   是否重置时间为起始时间（重置天时分秒）
     * @return 相差月数
     */
    public static int betweenMonth(String beginDate, String endDate, boolean isReset) {
        if (beginDate.length() <= 7) {
            beginDate = beginDate + "-01";
        }
        if (endDate.length() <= 7) {
            endDate = endDate + "-01";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        try {
            final Calendar beginCal = Calendar.getInstance();
            beginCal.setTime(sdf.parse(beginDate));
            final Calendar endCal = Calendar.getInstance();
            endCal.setTime(sdf.parse(endDate));
            final int betweenYear = endCal.get(Calendar.YEAR) - beginCal.get(Calendar.YEAR);
            final int betweenMonthOfYear = endCal.get(Calendar.MONTH) - beginCal.get(Calendar.MONTH);
            int result = betweenYear * 12 + betweenMonthOfYear;
            if (isReset) {
                endCal.set(Calendar.YEAR, beginCal.get(Calendar.YEAR));
                endCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH));
                long between = endCal.getTimeInMillis() - beginCal.getTimeInMillis();
                if (between < 0) {
                    return result - 1;
                }
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算月份差距
     *
     * @param startDate yyyy-MM
     * @param endDate   yyyy-MM
     * @return
     * @throws ParseException
     */
    public static int getMonthsBetweenNew(String startDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YEAR_MONTH);

        Calendar beginCal = Calendar.getInstance();
        Calendar beginCal2 = Calendar.getInstance();

        beginCal.setTime(format.parse(startDate));
        int year = beginCal.get(Calendar.YEAR);
        int month = beginCal.get(Calendar.MONTH);

        beginCal2.setTime(format.parse(endDate));
        int year2 = beginCal2.get(Calendar.YEAR);
        int month2 = beginCal2.get(Calendar.MONTH);

        return 12 * (year2 - year) + (month2 - month);
    }

    /**
     * 计算年份差距.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the days between
     * @throws ParseException the parse exception
     */
    public static int getYearsBetween(String startDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YEAR);
        Calendar beginCal = Calendar.getInstance();
        Calendar beginCal2 = Calendar.getInstance();

        beginCal.setTime(format.parse(startDate));
        int year = beginCal.get(Calendar.YEAR);

        beginCal2.setTime(format.parse(endDate));
        int year2 = beginCal2.get(Calendar.YEAR);

        return year2 - year;
    }

    /**
     * 比较两个日期大小
     *
     * @throws ParseException the parse exception
     */
    public static boolean isDateALessThanDateB(String dateA, String dateB) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        Date date1 = format.parse(dateA);
        Date date2 = format.parse(dateB);
        return date1.getTime() < date2.getTime();
    }

    public static Date addMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * Get the maximum days of the month
     *
     * @param date the date
     * @return the Integer
     */
    public static Integer getMaxDayByYearMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * @param date the date
     * @return the date of plus one day
     */
    public static Date plusOneDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        String stringDate = sdf.format(date);
//        System.out.println(stringDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * @param date the date
     * @return the date of plus one month
     */
    public static Date plusOneMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * @param startDate
     * @param endDate
     * @return months the differ of the two date
     */
    public static Integer subMonth(Date startDate, Date endDate) throws ParseException {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(startDate);
        aft.setTime(endDate);
        int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        surplus = surplus <= 0 ? 0 : 1;
        return (Math.abs(month + result) + surplus);
    }


    /**
     * @param startDate
     * @param endDate
     * @return days the differ of the two date
     */
    public static Integer subDay(Date startDate, Date endDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (3600000 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 用于将excel数值日期转换为Date
     *
     * @param days
     * @return
     */
    public static Date getDateByDayNumber(int days) {
        Calendar calendar = new GregorianCalendar(1900, Calendar.JANUARY, -1);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 获取指定date的年月日
     *
     * @param date 日期
     * @return
     */
    public static int[] getYmd(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return new int[]{instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH)};
    }


    /**
     * 获取月份的天数
     *
     * @param date yyyy-MM格式
     * @return
     */
    public static double getDaysByMonth(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取年份的天数
     *
     * @param date yyyy格式
     * @return
     */
    public static double getDaysByYear(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        return c.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 根据开始时间和截止时间获取之间的日期列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDayList(String startDate, String endDate) {
        List<String> list = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(startDate));
            for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = getDayStep(cal)) {
                list.add(sdf.format(d));
            }
        } catch (Exception e) {
            throw new RuntimeException("获取日期区间失败");
        }
        return list;
    }

    private static long getDayStep(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }

    private static long getMonthStep(Calendar c) {
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        return c.getTimeInMillis();
    }

    /**
     * 判断是否是同一年数据
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isTheSameYear(String date1, String date2) {
        boolean res = date1.substring(0, 4).equals(date2.substring(0, 4));
        return res;
    }

    /**
     * 获取过去时间
     *
     * @param date
     * @param year 过去年数
     * @return
     */
    public static Date getPastYearDate(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -year);
        return c.getTime();
    }

    /**
     * 获取过去时间
     *
     * @param date
     * @param month 过去月数
     * @return
     */
    public static Date getPastMonthDate(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -month);
        return c.getTime();
    }

    /**
     * 获取过去时间
     *
     * @param date
     * @param day  过去天数
     * @return
     */
    public static Date getPastDayDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -day);
        return c.getTime();
    }

    /**
     * 获取过去时间
     *
     * @param date
     * @param hour 过去小时
     * @return
     */
    public static Date getPastHourDate(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, -hour);
        return c.getTime();
    }

    /**
     * 获取过去时间
     *
     * @param date
     * @param minute 过去分钟
     * @return
     */
    public static Date getPastMinuteDate(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, -minute);
        return c.getTime();
    }

    public static Date getOnHourTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getZeroDayTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


    public static Date plusHour(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, i);
        return calendar.getTime();
    }
    public static Date plusSecond(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, i);
        return calendar.getTime();
    }
}
