/*
 * Created on 2005-3-1
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.up72.hq.utils;

import com.up72.framework.exception.UtilException;
import com.up72.hq.constant.Cnst;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunan
 *         <p/>
 *         Preferences - Java - Code Style - Code Templates
 */
public class DateUtils {

    Calendar cld;

    public final long YEAR = 10000000000l;

    public final long MONTH = 100000000;

    public final long DAY = 1000000;

    public final long HOUR = 10000;

    public final long MINUTE = 100;

    public long newDate() {
        long date = 0;
        return date;
    }

    public long setYear(long date, long year) {
        date = year * YEAR + date % YEAR;
        return date;
    }

    public long setYear(long date, String year) {
        long thisYear = 0;
        try {
            thisYear = Long.parseLong(year);
        } catch (Exception e) {
            return date;
        }

        return setYear(date, thisYear);
    }

    public  long getYear(long date) {

        return date / YEAR;
    }


    public static int getCurrYear(){
        Calendar a=Calendar.getInstance();

        return a.get(Calendar.YEAR);
    }


    public long setMonth(long date, long month) {
        long year = getYear(date);
        date = date - year * YEAR;
        date = month * MONTH + date % MONTH;

        return year * YEAR + date;
    }

    public long setMonth(long date, String month) {
        long thisMonth = 0;
        try {
            thisMonth = Long.parseLong(month);
        } catch (Exception e) {
            return date;
        }

        return setMonth(date, thisMonth);
    }

    public long getMonth(long date) {
        long year = getYear(date);
        date = date - year * YEAR;

        return date / MONTH;
    }

    public long setDay(long date, long day) {
        long year = getYear(date);
        long month = getMonth(date);
        date = (date - year * YEAR) - month * MONTH;
        date = day * DAY + date % DAY;

        return year * YEAR + month * MONTH + date;
    }

    public long setDay(long date, String day) {
        long thisDay = 0;
        try {
            thisDay = Long.parseLong(day);
        } catch (Exception e) {
            return date;
        }

        return setDay(date, thisDay);
    }

    public long getDay(long date) {
        long year = getYear(date);
        long month = getMonth(date);
        date = date - year * YEAR - month * MONTH;

        return date / DAY;
    }

    public long setHour(long date, long hour) {
        long thisDate = 0;
        long year = getYear(date);
        long month = getMonth(date);
        long day = getDay(date);

        thisDate = date % HOUR;
        thisDate = year * YEAR + month * MONTH + day * DAY + hour * HOUR
                + thisDate;

        return thisDate;
    }

    public long setHour(long date, String hour) {
        long thisHour = 0;
        try {
            thisHour = Long.parseLong(hour);
        } catch (Exception e) {
            return date;
        }

        return setHour(date, thisHour);

    }

    public long getHour(long date) {
        long hour = 0;
        hour = date % DAY;
        hour = hour / HOUR;
        return hour;
    }

    public String getHour() {
        Calendar c = Calendar.getInstance();
        return towStr(c.get(Calendar.HOUR_OF_DAY));
    }

    public long setMinute(long date, long minute) {
        long thisDate = 0;
        long year = getYear(date);
        long month = getMonth(date);
        long day = getDay(date);
        long hour = getHour(date);

        thisDate = date % MINUTE;
        thisDate = year * YEAR + month * MONTH + day * DAY + hour * HOUR
                + minute * MINUTE + thisDate;

        return thisDate;
    }

    public long setMinute(long date, String minute) {
        long thisMinute = 0;
        try {
            thisMinute = Long.parseLong(minute);
        } catch (Exception e) {
            return date;
        }

        return setMinute(date, thisMinute);
    }


    public long getMinute(long date) {
        long minute = 0;

        minute = date % HOUR;
        minute = minute / MINUTE;

        return minute;
    }

    public long setSecond(long date, long second) {
        long thisDate = 0;
        long year = getYear(date);
        long month = getMonth(date);
        long day = getDay(date);
        long hour = getHour(date);
        long minute = getMinute(date);

        thisDate = year * YEAR + month * MONTH + day * DAY + hour * HOUR
                + minute * MINUTE + second;

        return thisDate;
    }

    public long setSecond(long date, String second) {
        long thisSecond = 0;
        try {
            thisSecond = Long.parseLong(second);
        } catch (Exception e) {
            return date;
        }

        return setSecond(date, thisSecond);
    }

    public long getSecond(long date) {
        long second = 0;
        second = date % MINUTE;

        return second;
    }

    public long getNowDate() {
        long date = 0;
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String sDate = df.format(nowDate);
        int start = 0;
        int end = 0;
        String year = null;
        String month = null;
        String day = null;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            year = sDate.substring(start, end);
        }
        start = end + 1;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            month = sDate.substring(start, end);
        }
        start = end + 1;
        day = sDate.substring(start);
        /**
         * debug start
         */
        // System.out.println("year: " + year + "/month: " + month + "/day: " +
        // day);
        /**
         * debug end
         */
        date = setYear(date, year);
        date = setMonth(date, Long.parseLong(month));
        date = setDay(date, day);

        return date;
    }

    public long getNowDateTime() {
        long date = 0;

        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String sDate = df.format(nowDate);
        int start = 0;
        int end = 0;
        String year = null;
        String month = null;
        String day = null;
        String hour = null;
        String minute = null;
        String second = null;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            year = sDate.substring(start, end);
        }
        start = end + 1;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            month = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(" ", start);
        if (end > 0) {
            day = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            hour = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            minute = sDate.substring(start, end);
        }

        start = end + 1;
        second = sDate.substring(start);

        /**
         * debug start
         */
        // System.out.println("year: " + year + "/month: " + month + "/day: " +
        // day + "/hour: " + hour + "/minute: " + minute + "/second: " + second
        // + "\n" + sDate);
        /**
         * debug end
         */

        date = setYear(date, year);
        date = setMonth(date, month);
        date = setDay(date, day);
        date = setHour(date, hour);
        date = setMinute(date, minute);
        date = setSecond(date, second);
        return date;
    }

    public String getDate(long date, String separator) {
        String returnDate = null;
        String day = null;
        String month = null;
        String year = null;
        year = Long.toString(getYear(date));
        if (getMonth(date) < 10) {
            month = "0" + Long.toString(getMonth(date));
        } else {
            month = Long.toString(getMonth(date));
        }

        if (getDay(date) < 10) {
            day = "0" + Long.toString(getDay(date));
        } else {
            day = Long.toString(getDay(date));
        }
        returnDate = year + separator + month + separator + day;

        return returnDate;
    }

    public String getDate(long date) {
        String returnDate = "";
        String day = null;
        String month = null;
        String year = null;
        year = Long.toString(getYear(date));
        if (getMonth(date) < 10) {
            month = "0" + Long.toString(getMonth(date));
        } else {
            month = Long.toString(getMonth(date));
        }

        if (getDay(date) < 10) {
            day = "0" + Long.toString(getDay(date));
        } else {
            day = Long.toString(getDay(date));
        }
        returnDate = year + "-" + month + "-" + day;

        return returnDate;
    }

    public String getDateCn(long date) {
        String returnDate = "";
        String day = null;
        String month = null;
        String year = null;
        String hour = null;
        String minute = null;
        String second = null;

        year = Long.toString(getYear(date));
        if (getMonth(date) < 10) {
            month = "0" + Long.toString(getMonth(date));
        } else {
            month = Long.toString(getMonth(date));
        }

        if (getDay(date) < 10) {
            day = "0" + Long.toString(getDay(date));
        } else {
            day = Long.toString(getDay(date));
        }

        if (getHour(date) < 10) {
            hour = "0" + Long.toString(getHour(date));
        } else {
            hour = Long.toString(getHour(date));
        }

        if (getMinute(date) < 10) {
            minute = "0" + Long.toString(getMinute(date));
        } else {
            minute = Long.toString(getMinute(date));
        }

        if (getSecond(date) < 10) {
            second = "0" + Long.toString(getSecond(date));
        } else {
            second = Long.toString(getSecond(date));
        }

        returnDate = year + "年" + month + "月" + day + "日" + hour + "点" + minute
                + "分" + second + "秒";

        return returnDate;

    }

    public long getDateTime(Date someDate) {
        long date = 0;

        Date nowDate = someDate;
        DateFormat df = DateFormat.getDateTimeInstance();
        String sDate = df.format(nowDate);
        int start = 0;
        int end = 0;
        String year = null;
        String month = null;
        String day = null;
        String hour = null;
        String minute = null;
        String second = null;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            year = sDate.substring(start, end);
        }
        start = end + 1;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            month = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(" ", start);
        if (end > 0) {
            day = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            hour = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            minute = sDate.substring(start, end);
        }

        start = end + 1;
        second = sDate.substring(start);

        /**
         * debug start
         */
        // System.out.println("year: " + year + "/month: " + month + "/day: " +
        // day + "/hour: " + hour + "/minute: " + minute + "/second: " + second
        // + "\n" + sDate);
        /**
         * debug end
         */

        date = setYear(date, year);
        date = setMonth(date, month);
        date = setDay(date, day);
        date = setHour(date, hour);
        date = setMinute(date, minute);
        date = setSecond(date, second);
        return date;
    }

    // 获取当前日期
    public String getStrDate() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + towStr((c.get(Calendar.MONTH) + 1))
                + "-" + towStr(c.get(Calendar.DATE));
    }

    public String getStrDateTime() {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dd.format(new Date());
    }

    public String format(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR) + "-" + towStr(c.get(Calendar.MONTH) + 1)
                + "-" + towStr(c.get(Calendar.DATE));

    }

    public String format(long date) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        return c.get(Calendar.YEAR) + "-" + towStr(c.get(Calendar.MONTH) + 1)
                + "-" + towStr(c.get(Calendar.DATE));

    }

    public String formatLongDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        return c.get(Calendar.YEAR) + "-" + towStr(c.get(Calendar.MONTH) + 1)
                + "-" + towStr(c.get(Calendar.DATE)) + " " + towStr(c.get(Calendar.HOUR))
                + ":" + towStr(c.get(Calendar.MINUTE)) + ":" + towStr(c.get(Calendar.SECOND));

    }

    /**
     * 20140717102910
     *
     * @param date
     * @return
     */
    public String formatLong2Str(long date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        return c.get(Calendar.YEAR) + towStr(c.get(Calendar.MONTH) + 1) + towStr(c.get(Calendar.DATE)) + towStr(c.get(Calendar.HOUR))
                + towStr(c.get(Calendar.MINUTE)) + towStr(c.get(Calendar.SECOND));

    }

    public long getDateTime(String someDate) {
        long date = 0;

        String format = "yyyy-MM-dd";
        if (someDate.length() > 10) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dd = new SimpleDateFormat(format);
        Date nowDate = new Date();
        try {
            nowDate = dd.parse(someDate);
        } catch (ParseException e) {
            throw new UtilException(e);
        }

        // Date nowDate = someDate;
        DateFormat df = DateFormat.getDateTimeInstance();
        String sDate = df.format(nowDate);
        int start = 0;
        int end = 0;
        String year = null;
        String month = null;
        String day = null;
        String hour = null;
        String minute = null;
        String second = null;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            year = sDate.substring(start, end);
        }
        start = end + 1;
        end = sDate.indexOf("-", start);
        if (end > 0) {
            month = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(" ", start);
        if (end > 0) {
            day = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            hour = sDate.substring(start, end);
        }

        start = end + 1;
        end = sDate.indexOf(":", start);
        if (end > 0) {
            minute = sDate.substring(start, end);
        }

        start = end + 1;
        second = sDate.substring(start);

        /**
         * debug start
         */
        // System.out.println("year: " + year + "/month: " + month + "/day: " +
        // day + "/hour: " + hour + "/minute: " + minute + "/second: " + second
        // + "\n" + sDate);
        /**
         * debug end
         */

        date = setYear(date, year);
        date = setMonth(date, month);
        date = setDay(date, day);
        date = setHour(date, hour);
        date = setMinute(date, minute);
        date = setSecond(date, second);
        return date;
    }

    // public  long stringToLong(String someDate) {
    // long date = 0;
    //
    // String sDate = someDate;
    // int start = 0;
    // int end = 0;
    // String year = null;
    // String month = null;
    // String day = null;
    // String hour = null;
    // String minute = null;
    // String second = null;
    // end = sDate.indexOf("-", start);
    // if (end > 0) {
    // year = sDate.substring(start, end);
    // }
    // start = end + 1;
    // end = sDate.indexOf("-", start);
    // if (end > 0) {
    // month = sDate.substring(start, end);
    // }
    //
    // start = end + 1;
    // end = sDate.indexOf(" ", start);
    // if (end > 0) {
    // day = sDate.substring(start, end);
    // }
    //
    // start = end + 1;
    // end = sDate.indexOf(":", start);
    // if (end > 0) {
    // hour = sDate.substring(start, end);
    // }
    //
    // start = end + 1;
    // end = sDate.indexOf(":", start);
    // if (end > 0) {
    // minute = sDate.substring(start, end);
    // }
    //
    // start = end + 1;
    // second = sDate.substring(start);
    //
    // /**
    // * debug start
    // */
    // // System.out.println("year: " + year + "/month: " + month + "/day: " +
    // day + "/hour: " + hour + "/minute: " + minute + "/second: " + second +
    // "\n" + sDate);
    //
    // /**
    // * debug end
    // */
    //
    // date = setYear(date, year);
    // date = setMonth(date, month);
    // date = setDay(date, day);
    // date = setHour(date, hour);
    // date = setMinute(date, minute);
    // date = setSecond(date, second);
    // return date;
    // }

    public long stringToLong(String someDate) {
        String format = "yyyy-MM-dd";
        if (someDate.length() > 16) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        if (someDate.length() > 10 && 16 >= someDate.length()) {
            format = "yyyy-MM-dd HH:mm";
        }
        SimpleDateFormat dd = new SimpleDateFormat(format);
        try {
            return dd.parse(someDate).getTime();
        } catch (Exception e) {
            return new Date().getTime();
        }
    }

    public Long stringToLong( String someDate, String dateFormat ) {
        String format = "yyyy-MM-dd";
        if( StringUtils.isNotBlank(dateFormat) ) {
            format = dateFormat;
        } else if (someDate.length() > 10) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dd = new SimpleDateFormat(format);
        try {
            return dd.parse(someDate).getTime();
        } catch (Exception e) {
            return new Date().getTime();
        }
    }

    public Date longToDate(Long param) {
        if (cld == null) {
            cld = new GregorianCalendar();
        }
        if (param != null) {
            cld.clear();
            cld.setTimeInMillis(param);
            Date d = cld.getTime();
            return d;
        } else {
            return null;
        }
    }

    public long dateToLong(Date param) {
        long date = param.getTime();
        return date;
    }

    public long dateToLong(int day) {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date.getTime();
    }
    public static long dateToLong(Date date,int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date.getTime();
    }

    /**
     * Parse a datetime string.
     *
     * @param param datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
     * @return java.util.Date
     */
    public Date parse(String param) {
        Date date = null;
        if (param == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(param);
            } catch (ParseException ex) {
            }
            return date;
        }
    }

    /**
     * 获取一周中的第一天
     */
    public String getFirstDateOfWeek() {
        Calendar c = Calendar.getInstance();
        int dd = c.get(GregorianCalendar.DAY_OF_WEEK);
        if (dd == 1)
            dd = 7;
        else if (dd > 1)
            dd = dd - 1;
        c.add(Calendar.DATE, 1 - dd);
        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // try {
        // return sdf.parse(date);
        // } catch (ParseException e) {
        // return new Date();
        // }
        return date;
    }

    /**
     * 获取一周中的第一天
     */
    public String getLastDateOfMonth() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        // SimpleDateFormat simpleFormate = new SimpleDateFormat( " yyyy-MM-dd "
        // );
        // System.out.println(simpleFormate.format(calendar.getTime()));
        return date;
    }

    /**
     * 获取当月中的第一天
     */
    public String getFirstDateOfCurrentMonth() {
        Calendar c = Calendar.getInstance();

        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-01";

        return date;
    }

    /**
     * 获取一周中的第一天
     */
    public String getFirstDateOfWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dd = c.get(GregorianCalendar.DAY_OF_WEEK);
        if (dd == 1)
            dd = 7;
        else if (dd > 1)
            dd = dd - 1;
        c.add(Calendar.DATE, 1 - dd);
        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // try {
        // return sdf.parse(date);
        // } catch (ParseException e) {
        // return new Date();
        // }
        return date;
    }

    public String getDateOfWeek(String week) {
        if (week == null)
            return "";

        Calendar c = Calendar.getInstance();
        int dd = c.get(GregorianCalendar.DAY_OF_WEEK);

        if (week.equals("星期一")) {
            c.add(Calendar.DATE, 2 - dd);
        } else if (week.equals("星期二")) {
            c.add(Calendar.DATE, 3 - dd);
        } else if (week.equals("星期三")) {
            c.add(Calendar.DATE, 4 - dd);
        } else if (week.equals("星期四")) {
            c.add(Calendar.DATE, 5 - dd);
        } else if (week.equals("星期五")) {
            c.add(Calendar.DATE, 6 - dd);
        } else if (week.equals("星期六")) {
            c.add(Calendar.DATE, 7 - dd);
        } else if (week.equals("星期日")) {
            c.add(Calendar.DATE, 8 - dd);
        }

        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        return date;
    }

    /**
     * 获取一周中的最后一天
     */
    public String getLastDateOfWeek() {
        Calendar c = Calendar.getInstance();
        int dd = c.get(GregorianCalendar.DAY_OF_WEEK);
        if (dd == 1)
            dd = 7;
        else if (dd > 1)
            dd = dd - 1;
        c.add(Calendar.DATE, 7 - dd);
        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // try {
        // return sdf.parse(date);
        // } catch (ParseException e) {
        // return new Date();
        // }
        return date;
    }

    /**
     * 获取一周中的最后一天
     */
    public String getLastDateOfWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dd = c.get(GregorianCalendar.DAY_OF_WEEK);
        if (dd == 1)
            dd = 7;
        else if (dd > 1)
            dd = dd - 1;
        c.add(Calendar.DATE, 7 - dd);
        String date = c.get(Calendar.YEAR) + "-"
                + towStr(1 + c.get(Calendar.MONTH)) + "-"
                + towStr(c.get(Calendar.DATE));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // try {
        // return sdf.parse(date);
        // } catch (ParseException e) {
        // return new Date();
        // }
        return date;
    }

    /**
     * 获取某个时间段中的日期列表
     */
    public List<String> getDatesBetween(String beginDate, String endDate) {
        List<String> dates = new ArrayList<String>();
        int num = diffDate(stringToLong(beginDate), stringToLong(endDate));
        for (int i = 0; i < num; i++) {
            dates.add(diffDate(beginDate, i));
        }

        return dates;
    }

    public String towStr(int n) {
        if (n < 10)
            return "0" + n;
        else
            return "" + n;
    }

    /**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public Date parseStringToDate(String date) throws ParseException {
        Date result = null;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");

        DateFormat format = new SimpleDateFormat(parse);

        result = format.parse(date);

        return result;
    }

    /**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
     *
     * @return
     * @throws ParseException
     */
    public int getWeeksBetwinDate(Date beginDate) {
        Calendar now = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.setTime(beginDate);
        int beginWeek = b.get(Calendar.WEEK_OF_YEAR);
        int nowWeek = now.get(Calendar.WEEK_OF_YEAR);

        int beginYear = b.get(Calendar.YEAR);
        String lastDayOfWeek = getLastDateOfWeek();
        Date lastDate = new Date(stringToLong(lastDayOfWeek));

        Calendar last = Calendar.getInstance();
        last.setTime(lastDate);
        int nowYear = last.get(Calendar.YEAR);

        // System.out.println((nowYear-beginYear) * 52 + nowWeek-beginWeek + 1);
        return ((nowYear - beginYear) * 52 + nowWeek - beginWeek + 1);
    }

    /**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public int getWeeksBetwinDate(Date beginDate, String date) {
        Date nowDate = new Date(stringToLong(date));

        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);

        Calendar b = Calendar.getInstance();
        b.setTime(beginDate);
        int beginWeek = b.get(Calendar.WEEK_OF_YEAR);
        int nowWeek = now.get(Calendar.WEEK_OF_YEAR);

        int beginYear = b.get(Calendar.YEAR);
        String lastDayOfWeek = getLastDateOfWeek(nowDate);
        Date lastDate = new Date(stringToLong(lastDayOfWeek));

        Calendar last = Calendar.getInstance();
        last.setTime(lastDate);
        int nowYear = last.get(Calendar.YEAR);

        // System.out.println((nowYear-beginYear) * 52 + nowWeek-beginWeek + 1);
        return ((nowYear - beginYear) * 52 + nowWeek - beginWeek + 1);
    }

    /**
     * 两日期相减
     *
     * @return
     * @throws ParseException
     */
    public int diffDate(Date beginDate, Date endDate) {
        return diffDate(beginDate.getTime(), endDate.getTime());
    }

    /**
     * 两日期相减
     *
     * @return
     * @throws ParseException
     */
    public int diffDate(long beginDate, long endDate) {
        long diff = endDate - beginDate;

        long mint = (diff) / (1000);
        int hor = (int) mint / 3600;
//		int secd = (int) mint % 3600;
        int day = (int) hor / 24;

        // System.out.println(day+1);
        return (day + 1);
    }

    /**
     * 两日期相减
     *
     * @return
     * @throws ParseException
     */
    public String diffDate(String beginDate, int num) {
        Date d = new Date();

        d = new Date(stringToLong(beginDate));

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, num);
//		c.roll(Calendar.DAY_OF_YEAR, true);
        // return getDate(c.getTime().getTime());
        return c.get(Calendar.YEAR) + "-" + towStr(c.get(Calendar.MONTH) + 1) + "-"
                + towStr(c.get(Calendar.DATE));

        // System.out.println(day+1);
    }

    /**
     * 两日期相减
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public String diffDate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // System.out.println(c.get(Calendar.YEAR));
        // System.out.println(c.get(Calendar.MONTH));
        // System.out.println(c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, (0 - num));
        // c.roll(Calendar.DAY_OF_MONTH, false);
        // System.out.println(c.get(Calendar.YEAR));
        // System.out.println(c.get(Calendar.MONTH));
        // System.out.println(c.get(Calendar.DAY_OF_MONTH));

        String strDate = c.get(Calendar.YEAR) + "-"
                + towStr(c.get(Calendar.MONTH) + 1) + "-"
                + towStr(c.get(Calendar.DATE));

        return strDate;

        // System.out.println(day+1);
    }

    /**
     * 返回指定日期是星期几
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 返回某月的开始与结束日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public Long[] getBeginAndEndDateOfMonth(String date) {
        // Calendar calendar = Calendar.getInstance();
        // calendar.setTime(date);
        // return calendar.get(calendar.DAY_OF_WEEK)-1;

        String d[] = date.split("-");
        int year = Integer.parseInt(d[0]);
        int month = Integer.parseInt(d[1]);
        // Calendar bd = Calendar.getInstance();
        // c.set(c.YEAR, year);
        // c.set(c.MONTH, month-1);
        // c.set(c.DATE, 15);

        Long[] ld = new Long[2];
        // ld[0] = c.get(c.DAY_OF_MONTH);
        Calendar bd = Calendar.getInstance();
        bd.set(Calendar.YEAR, year);
        bd.set(Calendar.MONTH, month - 1);
        bd.set(Calendar.DAY_OF_MONTH, 1);
        String bds = bd.get(Calendar.YEAR) + "-" + towStr(bd.get(Calendar.MONTH) + 1) + "-"
                + towStr(bd.get(Calendar.DATE))+" 00:00:00";
        System.out.println(bds);

        Calendar ed = Calendar.getInstance();
        ed.set(Calendar.YEAR, year);
        ed.set(Calendar.MONTH, month);
        ed.set(Calendar.DAY_OF_MONTH, 1);
        ed.add(Calendar.DATE, -1);
        String eds = ed.get(Calendar.YEAR) + "-" + towStr(ed.get(Calendar.MONTH) + 1) + "-"
                + towStr(ed.get(Calendar.DATE))+" 23:59:59";
        System.out.println(eds);

        ld[0] = Cnst.getCurTime(bds);
        ld[1] = Cnst.getCurTime(eds);

        return ld;

    }


    /**
     * 获取系统的上个月开始时间和结束时间
     *
     * @return
     * @throws ParseException
     */
    public Long[] getBeginAndEndDateOfLastMonth() {
        Long[] ld = new Long[2];
        Date nowdate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        /* 设置为当前时间 */
        cal.setTime(nowdate);
       // System.out.println("当前时间是：" + sdf.format(nowdate));
        /* 当前日期月份-1 */
        cal.add(Calendar.MONTH, -1);
        // 得到前一个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String bds = cal.get(Calendar.YEAR) + "-" + towStr(cal.get(Calendar.MONTH) + 1) + "-"
                + towStr(cal.get(Calendar.DATE))+" 00:00:00";
       // System.out.println("上个月的第一天是：" + sdf.format(cal.getTime()));
        // 得到前一个月的最后一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String eds = cal.get(Calendar.YEAR) + "-" + towStr(cal.get(Calendar.MONTH) + 1) + "-"
                + towStr(cal.get(Calendar.DATE))+" 23:59:59";
        //System.out.println("上个月的最后一天是：" + sdf.format(cal.getTime()));

        ld[0] = Cnst.getCurTime(bds);
        ld[1] = Cnst.getCurTime(eds);
        return ld;

    }

    // 根据当前选择时间获得规定上传时间
    public String getUploadDateOfWeek(String uploadValue, Date date) {
        // DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // Date date = format.parse(weekDate);
        // Calendar c= Calendar.getInstance();
        // c.setTime(date);
        // c.add(Calendar.DATE, 1);
        // String d = c.get(Calendar.YEAR) + "-" + (1+c.get(Calendar.MONTH)) +
        // "-" + (c.get(Calendar.DATE));
        // System.out.println(d);

        if (uploadValue == null)
            return "";

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (uploadValue.equals("星期一")) {
            c.add(Calendar.DATE, 0);
        } else if (uploadValue.equals("星期二")) {
            c.add(Calendar.DATE, 1);
        } else if (uploadValue.equals("星期三")) {
            c.add(Calendar.DATE, 2);
        } else if (uploadValue.equals("星期四")) {
            c.add(Calendar.DATE, 3);
        } else if (uploadValue.equals("星期五")) {
            c.add(Calendar.DATE, 4);
        } else if (uploadValue.equals("星期六")) {
            c.add(Calendar.DATE, 5);
        } else if (uploadValue.equals("星期日")) {
            c.add(Calendar.DATE, 6);
        }
        String d = c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH))
                + "-" + (c.get(Calendar.DATE));
        return d;
    }

    public static Date parseStr(String param) {
        Date date = null;
        if (param == null || param.trim() == "") {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(param);
            } catch (ParseException ex) {
            }
            return date;
        }
    }

    public String DateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String dateToString(Date date,String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(date);
    }

    public String addDate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // System.out.println(c.get(Calendar.YEAR));
        // System.out.println(c.get(Calendar.MONTH));
        // System.out.println(c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, num);
        // c.roll(Calendar.DAY_OF_MONTH, false);
        // System.out.println(c.get(Calendar.YEAR));
        // System.out.println(c.get(Calendar.MONTH));
        // System.out.println(c.get(Calendar.DAY_OF_MONTH));

        String strDate = c.get(Calendar.YEAR) + "-"
                + towStr(c.get(Calendar.MONTH) + 1) + "-"
                + towStr(c.get(Calendar.DATE)) + " "
                + towStr(c.get(Calendar.HOUR_OF_DAY)) + ":"
                + towStr(c.get(Calendar.MINUTE)) + ":"
                + towStr(c.get(Calendar.SECOND));

        return strDate;

        // System.out.println(day+1);
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public Date parseToDate(String timeString, String formate) {
        Date result = null;
        if (null == timeString || timeString.trim().equals("")) {
            throw new UtilException("参数不能为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        try {
            result = sdf.parse(timeString);
        } catch (ParseException e) {
            throw new UtilException(e);
        }
        return result;
    }

    public Long parseToLong(String timeString, String formate) {
        Long result = null;
        Date date = parseToDate(timeString, formate);
        if (null != date) {
            result = date.getTime();
        }
        return result;
    }

    public String getDateTimeStr(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dt = sdf.format(new Date());
        return dt;
    }

    /**
     * 字符日期转换为DATE类型
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            throw new UtilException("转换错误");
        }
        return result;
    }

    /**
     * 字符日期转换为DATE类型
     *
     * @param date
     * @return
     */
    public Date stringToDate(String date, String format) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            throw new UtilException("转换错误");
        }
        return result;
    }

    /**
     * 用于将2012-12-12格式的日期转换成java.util.Date格式
     */
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 用于将2012-12-12 12:12:12格式的日期转换成java.util.Date格式
     */
    public static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 返回2012-12-12格式的日期
     */
    public String toDateYyyyMmDd(long time) {
        return sdf.format(new Date(time));
    }
    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }
    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }
    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static Date getCurrMonthLastDay(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        last = last + " 23:59:59";
        System.out.println("===============last:" + last + " 23:59:59");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return  sdf.parse(last);
        } catch (ParseException ex) {
            return null;
        }
    }

     /*
      *获取指定时间之前或之后几分钟 minute
      */
    public static String getTimeByMinute(Date date,int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getNext7(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }
    public static Date getDay7Before(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        return calendar.getTime();
    }
    public static String getLastDayByDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = Cnst.SDF_TIME.format(ca.getTime());
        return last;
    }


    public static Date getCurrMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        first = first +" 00:00:00";
        System.out.println("===============first:" + first + " 00:00:00");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return  sdf.parse(first);
        } catch (ParseException ex) {
            ex.printStackTrace();;
            return null;
        }
    }

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;

        String[] u = {"天","月","年"};
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";
        date2 = date2==null?getCurrentDate():date2;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }
        n = n-1;

        if(stype==2){
            n = (int)n/365;
        }
        /*System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);*/
        return n;
    }

    /**
     * 得到当前日期
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    public static void main(String[] args) {
   /*     String strDate = "2016-01-01";// 定义日期字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            format.setLenient(false);
            date = format.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
            System.out.println("输入的日期格式不合理！");
        }
        System.out.println(strDate + "是:" + getWeek(date));
        System.out.println(strDate + "是一年的第:" + getWeekOfYear(date) + "周");
        System.out.println(strDate + "是一年的" + (date.getMonth() + 1) + "月有:"
                + getDaysOfMonth(date.getYear(), date.getMonth() + 1) + "天");
        System.out.println(strDate + "距离" + (format.format(new Date())) + "还有"
                + getDaysBetween(date, new Date()) + "天");
*/

     /*   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //获取当前月第一天：
                 Calendar c = Calendar.getInstance();
                 c.add(Calendar.MONTH, 0);
                 c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                 String first = format.format(c.getTime());
                 System.out.println("===============first:"+first);

                 //获取当前月最后一天
                 Calendar ca = Calendar.getInstance();
                ca.setTime(DateUtils.stringToDate("2016-03-01 00:00:00"));
                ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                 String last = format.format(ca.getTime());
                 System.out.println("===============last:"+last);*/

        printWeeks();

    }

    /**
     * 两个时间之间相差距离多少天
     * @param one 时间参数 1：
     * @param two 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days=0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }


    // 根据日期取得星期几
    public static String getWeek(Date date) {
        // String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        // Calendar cal = Calendar.getInstance();
        // cal.setTime(date);
        // int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        // if(week_index<0){
        // week_index = 0;
        // }
        // return weeks[week_index];
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    // 取得日期是某年的第几周
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    // 取得某个月有多少天
    public static int getDaysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        int days_of_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days_of_month;
    }

    // 取得两个日期之间的相差多少天
    public static long getDaysBetween(Date date0, Date date1) {
        long daysBetween = (date0.getTime() - date1.getTime() + 1000000) / 86400000;// 86400000=3600*24*1000  用立即数，减少乘法计算的开销
        return daysBetween;
    }

    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }


    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static void printWeeks() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        int month = calendar.get(Calendar.MONTH);
        int count = 0;
        while (calendar.get(Calendar.MONTH) == month) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                StringBuilder builder = new StringBuilder();
                builder.append("week:");
                builder.append(++count);
                builder.append(" (");
                builder.append(format.format(calendar.getTime()));
                builder.append(" - ");
                calendar.add(Calendar.DATE, 6);
                builder.append(format.format(calendar.getTime()));
                builder.append(")");
                System.out.println(builder.toString());
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

}
