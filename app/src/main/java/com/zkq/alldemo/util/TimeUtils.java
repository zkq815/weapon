package com.zkq.alldemo.util;

import android.os.Environment;
import android.text.TextUtils;

import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.common.BuildInfo;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 文件工具<br/>
 * Created by yc on 16/9/7.
 */
public class TimeUtils {

    /**
     * 将当前时间转换为目标格式的时间
     */
    public static String TimeStamp2Date(String formats) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(formats);
        String time = format.format(date);
        return time;
    }


    /**
     * "yyyy-MM-dd HH:mm:ss" 类型的时间格式转换为时间戳
     * */
    public static long toLongTime(String time){
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
     * 获取两个日期中间的间隔日期，也可以改为工作日期
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public static int getworktime(String starttime, String endtime) {
        //设置时间格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //开始日期
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = dateFormat.parse(starttime);
            dateTo = dateFormat.parse(endtime);
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
     * 获取当前日期的字符串形式
     * return String  "2015-06-07"
     */
    public static String getToday() {

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
    public static String getNextYearDay(String nowtime) {

        String[] str = nowtime.split("-");
        int yearNum = Integer.parseInt(str[0]) + 1;
        String year = yearNum + "";
        return year + "-" + str[1] + "-" + str[2];
    }

    /**
     * 比较选中天与今天之间的大小
     */
    public static boolean compareWithDayAndToday(String str) {
        Date nowdate = new Date();
        // 	String myString = "2008-09-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date d;
        boolean flag = false;
        try {
            d = sdf.parse(str);
            flag = d.before(nowdate);
            if (flag)
                System.out.print("早于今天");
            else
                System.out.print("晚于今天");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 比较两个日期之间的前后顺序
     */
    public static boolean compareWithTwoTodays(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date d1, d2;
        boolean flag = false;
        try {
            d1 = sdf.parse(str1 + " 00:00:00");
            d2 = sdf.parse(str2 + " 23:59:59");
            flag = d1.before(d2);
            if (flag)
                System.out.print("比后者早");
            else
                System.out.print("比后者晚");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
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
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
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
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 显示时间（long转化为String）
     *
     * @param time
     * @return 返回为"yyyy-MM-dd HH:mm:ss"格式的时间显示
     */
    public static String showTimeFromMilis(long time) {
        return showTimeFromMilis(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 显示时间（long转化为String）
     *
     * @param time
     * @param formatStr 需要显示的时间格式，比如"yyyy-MM-dd HH:mm:ss"
     * @return 返回指定格式的时间显示
     */
    public static String showTimeFromMilis(long time, String formatStr) {
        String res = "";
        if (time > 0 && !TextUtils.isEmpty(formatStr)) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(time);
                SimpleDateFormat sdf = new SimpleDateFormat(
                        formatStr, Locale.ENGLISH);
                res = sdf.format(cal.getTime());
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        return res;
    }

}
