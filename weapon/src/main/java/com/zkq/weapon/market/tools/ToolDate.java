package com.zkq.weapon.market.tools;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author zkq
 * create:2018/12/11 9:58 AM
 * email:zkq815@126.com
 * desc: 日期工具类
 */
public interface ToolDate {

    /**
     * 获取指定时期模版的时间格式
     * 默认指定{@link Locale#US}统一输出为阿拉伯数字
     *
     * @param timeStamp 时间戳 单位秒数
     * @param pattern   例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     */
    @NonNull
    static String getFormatDate(long timeStamp, @NonNull String pattern) {
        return getFormatDate(new Date(timeStamp * 1000), pattern, Locale.US);
    }

    /**
     * 获取指定时期模版的时间格式
     * Locale指定了US统一输出阿拉伯数字
     *
     * @param date    日期
     * @param pattern 例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     * @see ToolDate#getFormatDate(Date, String, Locale)
     */
    static String getFormatDate(@NonNull Date date, @NonNull String pattern) {
        return getFormatDate(date, pattern, Locale.US);
    }

    /**
     * 获取指定时期模版的时间格式
     *
     * @param date    日期
     * @param pattern 例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @param locale  区域 如果要对输出日期本地化处理可以指定locale为 {@link Locale#getDefault}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     */
    @NonNull
    static String getFormatDate(@NonNull Date date, @NonNull String pattern, @NonNull Locale locale) {
        try {
            return new SimpleDateFormat(pattern, locale).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据秒数获取时分秒
     *
     * @param second 秒数
     * @return 返回大小为3的int数组 int[0]-时 int[1]-分 int[2]-秒
     */
    @NonNull
    static int[] getHourMinSecond(long second) {
        int numHour = 60 * 60;
        long timeMin = second % numHour;
        return new int[]{
                // 时
                (int) (second / numHour)
                // 分
                , (int) (timeMin / 60)
                // 秒
                , (int) (timeMin % 60)};
    }

    /**
     * 根据秒数获取日时分秒
     *
     * @param second 秒数
     * @return 返回大小为4的int数组 int[0]-天 int[1]-时 int[2]-分 int[3]-秒
     */
    @NonNull
    static int[] getDayHourMinSecond(long second) {
        int numDay = 60 * 60 * 24;
        // 日
        int timeDay = (int) (second / numDay);
        int[] srcArr = getHourMinSecond(second % numDay);
        int[] timeArray = new int[4];
        timeArray[0] = timeDay;
        System.arraycopy(srcArr, 0, timeArray, 1, 3);
        return timeArray;
    }

    /**
     * 判断两个时间戳是否处于同一天
     *
     * @param timeMillis1 之前的时间 单位为毫秒
     * @param timeMillis2 之后的时间 单位为毫秒
     * @return true:是
     */
    static boolean isSameDay(long timeMillis1, long timeMillis2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(timeMillis1));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(timeMillis2));
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        return isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期中间的间隔日期，也可以改为工作日期
     *
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return 间隔日期
     */
    static int getDaysBetweenTwoDays(String startTime, String endTime) {
        //设置时间格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //开始日期
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = dateFormat.parse(startTime);
            dateTo = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int workdays = 0;
        Calendar cal = null;
        while (dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
            cal = Calendar.getInstance();
            //设置日期
            cal.setTime(dateFrom);
//           if((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
//                   &&(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)){
            //进行比较，如果日期不等于周六也不等于周日，工作日+1
            workdays++;
//           }
            //日期加1
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dateFrom = cal.getTime();
        }
        return workdays - 1;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss" 类型的时间格式转换为时间戳
     * */
    static long toLongTime(String time){
        //Date或者String转化为时间戳
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取当前日期的字符串形式
     * return String  "2015-06-07"
     */
    static String getToday() {

        Calendar now = Calendar.getInstance();
        String year = now.get(Calendar.YEAR) + "";
        String month = "";
        String day = "";
        if (now.get(Calendar.MONTH) < 9) {
            month = "0" + (now.get(Calendar.MONTH) + 1) + "";
        } else {
            month = (now.get(Calendar.MONTH) + 1) + "";
        }

        if (now.get(Calendar.DAY_OF_MONTH) < 9) {
            day = "0" + now.get(Calendar.DAY_OF_MONTH) + "";
        } else {
            day = now.get(Calendar.DAY_OF_MONTH) + "";
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * 获取输入日期的一年后的日期
     *
     * @param nowtime 现在的日期  例如："2013-06-09"
     * @return string  "2014-06-09"
     */
    static String getNextYearDay(String nowtime) {

        String[] str = nowtime.split("-");
        int yearNum = Integer.parseInt(str[0]) + 1;
        String year = yearNum + "";
        return year + "-" + str[1] + "-" + str[2];
    }

    /**
     * 比较选中天与今天之间的大小
     */
    static boolean compareWithDayAndToday(String str) {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date d;
        boolean flag = false;
        try {
            d = sdf.parse(str);
            flag = d.before(nowdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 比较两个日期之间的前后顺序
     */
    static boolean compareWithTwoTodays(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date d1, d2;
        boolean flag = false;
        try {
            d1 = sdf.parse(str1 + " 00:00:00");
            d2 = sdf.parse(str2 + " 23:59:59");
            flag = d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串 将时间戳转为时间格式  xxxx-xx-xx xx:xx:xx
     *                调用方式：imeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
     * @return
     */
    static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static GregorianCalendar tomorrowStartTime(@NonNull final GregorianCalendar today) {
        final GregorianCalendar tomorrowStart = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        tomorrowStart.setTimeInMillis(tomorrowStart.getTimeInMillis() + TimeUnit.DAYS.toMillis(1));
        return tomorrowStart;
    }

    static boolean sameYear(@NonNull final GregorianCalendar calendar1, @NonNull final GregorianCalendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    static boolean sameDay(@NonNull final GregorianCalendar calendar1, @NonNull final GregorianCalendar calendar2) {
        return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    static boolean sameDate(@NonNull final GregorianCalendar calendar1, @NonNull final GregorianCalendar calendar2) {
        return sameYear(calendar1, calendar2) && sameDay(calendar1, calendar2);
    }

    static boolean theYearBefore(@NonNull final GregorianCalendar src, @NonNull final GregorianCalendar dest) {
        return src.get(Calendar.YEAR) < dest.get(Calendar.YEAR);
    }

    static boolean theDayBefore(@NonNull final GregorianCalendar src, @NonNull final GregorianCalendar dest) {
        return theYearBefore(src, dest) || sameYear(src, dest) && src.get(Calendar.DAY_OF_YEAR) < dest.get(Calendar.DAY_OF_YEAR);
    }

    static boolean theYearAfter(@NonNull final GregorianCalendar src, @NonNull final GregorianCalendar dest) {
        return src.get(Calendar.YEAR) > dest.get(Calendar.YEAR);
    }

    static boolean theDayAfter(@NonNull final GregorianCalendar src, @NonNull final GregorianCalendar dest) {
        return theYearAfter(src, dest) || sameYear(src, dest) && src.get(Calendar.DAY_OF_YEAR) > dest.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 防止快速点击，设置保护
     */
//    // 两次点击按钮之间的点击间隔不能少于100毫秒
//    int MIN_CLICK_DELAY_TIME = 100;
//    long lastClickTime = 0;
//    static boolean isEffectiveClick() {
//        boolean flag = true;
//        long curClickTime = System.currentTimeMillis();
//        if ((curClickTime - lastClickTime) < MIN_CLICK_DELAY_TIME) {
//            flag = false;
//        }
//        lastClickTime = curClickTime;
//        return flag;
//    }

    static String formatUTC(long l, String strPattern) {
        SimpleDateFormat sdf = null;
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }



}