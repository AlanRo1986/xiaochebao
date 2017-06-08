package cn.xiaochebao.app.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间操作类
 * Created by Alan on 2016/05/16 0016.
 */
public class DateTimeUtil {
    public static final String DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_SHORT = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FULL = "yyyy-MM-dd HH:mm:ss EEEE";
    public static final String DATE_DEFAULT = "yyyy-MM-dd";
    public static final String TIME_DEFAULT = "HH:mm:ss";
    public static final String TIME_DEFAULT_SHORT = "HH:mm";
    public static final String WEEK = "EEEE";

    public static final String DATE_TIME_DEFAULT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_TIME_SHORT_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String DATE_DEFAULT_CN = "yyyy年MM月dd日";
    public static final String TIME_DEFAULT_CN = "HH时mm分ss秒";
    public static final String TIME_DEFAULT_CN_SHORT = "HH时mm分";

    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_MONTH = "MM";
    public static final String DATE_DAY = "dd";
    public static final String TIME_HOUR = "HH";
    public static final String TIME_MINUTE = "mm";
    public static final String TIME_SECOND = "ss";

    private static SimpleDateFormat sdf;

    public DateTimeUtil(){

    }

    /**
     * 取时间戳
     * @return
     */
    public static long getTime(){
        return new Date().getTime();
    }

    /**
     * 取日期时间对象
     * @param time
     * @return
     */
    public static Date getDate(long time){
        return new Date(time);
    }

    /**
     * 取日期时间对象
     * @param time
     * @return
     */
    public static Date getDate(){
        return new Date();
    }

    /**
     * 当前日期到时间
     * @param calendar
     * @return
     */
    public Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 全局同意格式化方法
     * @param d
     * @param tmp
     * @return
     */
    private static String __format(Date d,String tmp){
        sdf = new SimpleDateFormat(tmp, Locale.CHINA);
        return sdf.format(d);
    }

    /**
     * 取默认日期 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateDefault(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_DEFAULT);
    }

    /**
     * 取默认日期中文格式 yyyy年MM月dd日
     * @param date
     * @return
     */
    public static String getDateForCN(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_DEFAULT_CN);
    }

    /**
     * 取年份
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_YEAR);
    }

    /**
     * 取月份
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_MONTH);
    }

    /**
     * 取日期
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_DAY);
    }

    /**
     * 取长时间 格式:HH:mm:ss
     * @param date
     * @return
     */
    public static String getTimeDefault(Date date){
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_DEFAULT);
    }

    /**
     * 取短时间 格式:HH:mm
     * @param date
     * @return
     */
    public static String getTimeDefaultShort(Date date){
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_DEFAULT_SHORT);
    }

    /**
     * 取时间中文格式 HH时mm分ss秒
     * @param date
     * @return
     */
    public static String getTimeForCN(Date date){
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_DEFAULT_CN);
    }

    /**
     * 取时间中文短格式 HH时mm分
     * @param date
     * @return
     */
    public static String getTimeForCnShort(Date date){
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_DEFAULT_CN_SHORT);
    }

    /**
     * 取小时
     * @param date
     * @return
     */
    public static String getHour(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_HOUR);
    }

    /**
     * 取分钟
     * @param date
     * @return
     */
    public static String getMinute(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_MINUTE);
    }

    /**
     * 取秒
     * @param date
     * @return
     */
    public static String getSecond(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,TIME_SECOND);
    }

    /**
     * 取星期
     * @param date
     * @return
     */
    public static String getWeek(Date date){
        if(date == null){
            date = new Date();
        }
        return __format(date,WEEK);
    }


    /**
     * 取日期时间短格式 yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    public static String getDateTimeShort(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_TIME_SHORT);
    }

    /**
     * 取日期时间短中文格式 yyyy年MM月dd日 HH时mm分
     * @param date
     * @return
     */
    public static String getDateTimeShortForCn(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_TIME_SHORT_CN);
    }

    /**
     * 取日期时间默认格式
     * @param date
     * @return
     */
    public static String getDateTimeDefault(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_TIME_DEFAULT);
    }

    /**
     * 取日期时间默认中文格式 yyyy年MM月dd日 HH时mm分ss秒
     * @param date
     * @return
     */
    public static String getDateTimeDefaultForCn(Date date) {
        if(date == null){
            date = new Date();
        }
        return  __format(date,DATE_TIME_DEFAULT_CN);
    }

    /**
     * 取日期时间,满格式,包含星期
     * @param date
     * @return
     */
    public static String getDateTimeFull(Date date) {
        if(date == null){
            date = new Date();
        }
        return __format(date,DATE_TIME_FULL);
    }

    /**
     * 日期时间格式化
     * example:DateTimeUtil.dateTimeFormat(DateTimeUtil.getTime(),null)
     * @param time
     * @param tmp 如果传入 null ,那么则取默认模板
     * @return
     */
    public static String formatDateTime(long time,String tmp){
        if (tmp == null){
            tmp = DATE_TIME_DEFAULT;
        }

        return __format(new Date(time),tmp);
    }

    /**
     * 日期时间格式化
     * example:DateTimeUtil.dateTimeFormat(DateTimeUtil.getTime(),null)
     * @param date
     * @param tmp 如果传入 null ,那么则取默认模板
     * @return
     */
    public static String formatDateTime(Date date,String tmp){
        if (tmp == null){
            tmp = DATE_TIME_DEFAULT;
        }

        return __format(date,tmp);
    }

    /**
     * 格式化字符串到日期时间对象
     * @param string
     * @param tmp
     * @return
     */
    public static Date formatStringToDateTime(String string,String tmp){
        if(tmp == null){
            tmp = DATE_TIME_DEFAULT;
        }
        sdf = new SimpleDateFormat(tmp);
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    /**
     * 格式化Date到 日期时间星期
     * @param date
     * @return
     */
    public static String formatDateTimeWeek(Date date) {
        return getDateTimeDefault(date) + " " + getWeek(date);
    }

    /**
     * 格式化Date到 日期时间星期
     * @param time
     * @return
     */
    public static String formatDateTimeWeek(long time) {
        Date date = getDate(time);
        return getDateTimeDefault(date) + " " + getWeek(date);
    }

    /**
     * 取时间间隔
     * example:
     * DateTimeUtil.getDateForDifference(formatStringToDateTime("2016-05-22",DATE_DEFAULT),formatStringToDateTime("2016-06-02",DATE_DEFAULT));
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDateForDifference(Date date1, Date date2) {
        int nDay = (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }

    /**
     * 指定日期时间加、减指定的时间单位
     * @param calendarFinal
     *            Calendar类中代表时间的字段常量
     *            Calendar.YEAR 年
     *            Calendar.MONTH 月(月份从0开始)
     *            Calendar.DATE 日
     *            Calendar.HOUR 时
     *            Calendar.MINUTE 分
     *            Calendar.SECOND 秒
     * @param date 指定时间
     * @param times 相加或减的时间，相加传正数，相减传负数
     * @return 相加或相减后的时间
     */
    public static Date dateTimeAddOrLess(int calendarFinal, Date date, int times) {
        if(date == null)
            date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarFinal, times);
        return calendar.getTime();
    }

    /**
     * 增加或者减少天数 addDays(null, 4)
     * @param date 如果为null则取当前时间
     * @param i 要增加或者减少的天数
     * @return
     */
    public static Date addDays(Date date, int i) {
        if(date == null){
            date = new Date();
        }
        return dateTimeAddOrLess(Calendar.DATE, date, i);
    }

    /**
     * 增加或减少月份 addMonths(null, 2))
     * @param date 如果为null则取当前时间
     * @param i 要增加或者减少的月份
     * @return
     */
    public static Date addMonths(Date date, int i) {
        if(date == null){
            date = new Date();
        }
        return dateTimeAddOrLess(Calendar.MONTH, date, i);
    }

    /**
     * 增加或者减少天数 addYears(null, 4)
     * @param date 如果为null则取当前时间
     * @param i 要增加或者减少的年份
     * @return
     */
    public static Date addYears(Date date, int i) {
        if(date == null){
            date = new Date();
        }
        return dateTimeAddOrLess(Calendar.YEAR, date, i);
    }

    /**
     * 日期时间格式验证 validateDateFormat("2016-06-16","yyyy-MM-dd")
     * @param str 如:2010-01-01 00:00
     * @param tmpl 如:yyyy-MM-dd HH:mm
     * @return 返回 false 验证失败
     */
    public static boolean validateDateFormat(String str, String tmpl) {
        sdf = new SimpleDateFormat(tmpl);
        sdf.setLenient(false);
        Date date;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return false;
        }

        return str.equals(__format(date,tmpl));
    }

}
