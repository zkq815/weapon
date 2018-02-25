package com.zkq.weapon.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
//import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zkq on 18/2/24.
 *
 */
public class StringUtil {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(final String src, final char c) {
        if (null == src) {
            return null;
        }

        int start = 0, last = src.length() - 1;
        int end = last;
        while ((start <= end) && (src.charAt(start) == c)) {
            start++;
        }
        while ((end >= start) && (src.charAt(end) == c)) {
            end--;
        }

        if (start == 0 && end == last) {
            return src;
        }

        return src.substring(start, end);
    }

    /**
     * 将价格最后面的.0和.00去掉
     * */
    public static String deleteDecimalPoint(String goodsPrice) {

        String price = String.valueOf(goodsPrice);
        String tempPrice = "";
        if (price.contains(".")) {
            if (price.split("\\.")[1].equals("0") || price.split("\\.")[1].equals("00")) {
                tempPrice = price.split("\\.")[0];
            } else if (price.split("\\.")[1].equals("0起") || price.split("\\.")[1].equals("00起")) {
                tempPrice = price.split("\\.")[0] + "起";
            } else {
                tempPrice = price;
            }
        } else {
            tempPrice = price;
        }
        return tempPrice;
    }

    public static boolean equals(final String str1, final String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    private static SimpleDateFormat sdf = null;
    public  static String formatUTC(long l, String strPattern) {
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

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {

        double EARTH_RADIUS = 6371.393;
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 米和千米单位转换
     * */
    public static String meter2KM(int meter){
        String tempDistance = "";

        if(meter<1000){//小于一千米，显示米
            tempDistance = String.valueOf(meter+"米");
        }else{//大于一千米显示千米
            DecimalFormat df = new DecimalFormat("#.00");
            tempDistance = String.valueOf(df.format((double)meter/1000.00)+"千米");

        }

        return tempDistance;
    }

    /**
     * 判断是否包含中文
     * @param str 目标字符串
     * */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 拷贝到剪贴板
     *
     * @param context context
     * @param label   用户可见标签
     * @param content 实际剪贴的文本内容
     */
    public static void copyToClipboard(final Context context, final String label, final String content) {
        final ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData data = ClipData.newPlainText(label, content);
        manager.setPrimaryClip(data);
    }

    /**
     * 手机号码正则表达式 1开头的11位数字
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 只允许英文、数字、汉字的正则判断
     */
    public static String getNickName(String nickName) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("[\\w]+");//匹配英文、数字、汉字
//        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5+\\\\w]+");
//        Pattern p = Pattern.compile("[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z0123456789_-]+");
//        Pattern p = Pattern.compile("^[a-zA-Z0-9\\u4E00-\\u9FA5]+$");
        Matcher m = p.matcher(nickName);
        if (m.matches()) {
            for (int i = 0; i <= nickName.length() - 1; i++) {
                char temp = nickName.toCharArray()[i];
                if (!(temp + "").equals("_")) {
                    sb.append(temp);
                }
            }
            return sb.toString();
        } else {
            for (int i = 0; i <= nickName.length() - 1; i++) {
                char temp = nickName.toCharArray()[i];
                if (p.matcher(temp + "").matches()) {
                    if (!(temp + "").equals("_")) {
                        sb.append(temp);
                    }
                }
            }
            return sb.toString();
        }
    }

    /**
     * 只允许英文、数字的正则判断
     */
    public static boolean passwordIsOK(String password) {
        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 只允许英文、数字、_、-  的正则判断
     */
    public static String getWXName(String password) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("[a-zA-Z0-9\\-\\_]+");
        Matcher m = p.matcher(password);
        if(m.matches()){
            for (int i = 0; i <= password.length() - 1; i++) {
                char temp = password.toCharArray()[i];
                sb.append(temp);
            }
        } else{
            for (int i = 0; i <= password.length() - 1; i++) {
                char temp = password.toCharArray()[i];
                if (p.matcher(temp + "").matches()) {
                    if (!(temp + "").equals("_")) {
                        sb.append(temp);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 只允许英文、数字、_、-  的正则判断
     */
    public static boolean wxNameIsOk(String password) {
        Pattern p = Pattern.compile("[a-zA-Z0-9\\-\\_]+");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 身份证只显示首和尾，其余用星号代替
     */
    public static String showPersonId(String personId) {
        String begin = personId.substring(0, 1);
        String ending = "";
        String body = "";
        if (personId.length() == 15) {
            body = "*************";
            ending = personId.substring(14, 15);
        } else if (personId.length() == 18) {
            body = "****************";
            ending = personId.substring(17, 18);
        }
        return begin + body + ending;
    }

    /**
     * 判断验证码的长度是否符合规则
     */
    public static boolean isCodeRight(String code) {
        if (code.length() >= 4 && code.length() <= 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static Long changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return Long.valueOf(amLong.toString());
    }

    /**
     * 分转元
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String changeF2Y(long a) {
        return BigDecimal.valueOf(a).divide(new BigDecimal(100)).toString();
    }

    /**
     * 过滤掉字符串中所有的非数字
     */
    public static String replaceAllNotNumber(String oldString) {
        String str = "";
        if (oldString.contains("+86")) {
            oldString = oldString.substring(3, oldString.length());
        }
        for (int i = 0; i < oldString.length(); i++) {
            if (Character.isDigit(oldString.charAt(i))) {
                str += oldString.charAt(i);
            }
        }
        System.out.print(str);
        return str;
    }

    /**
     * 给TextView部分文字添加下划线
     */
    public static SpannableString addUnderLine(String content, int begin, int end) {
        SpannableString spanString = new SpannableString(content);
        int length = content.length();
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    /**
     * 显示填充字符串
     *
     * @param original 填充前的字符串，以$1s,$2s表示要填充的地方
     * @param params   要填充的字符，对应$1s,$2s
     * @return 填充后的字符串<br>
     * <p/>
     * 比如，original传入“今天是$1s月$2s号”,params传入{6,4}，返回字符串为"今天是6月4号".
     */
    public static String getString(String original, String[] params) {
        if (original == null || original.length() <= 0 || params == null || params.length <= 0)
            return original;

        String result = original;

        for (int i = 0; i < params.length; i++) {
            String old = "$" + (i + 1) + "s";
            result = result.replace(old, params[i]);
        }

        return result;
    }

    /**
     * 显示列表（int数组）
     */
    public static String showIntArr(int[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append("" + list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（String数组）
     */
    public static String showStringArr(String[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append(list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（byte数组）
     */
    public static String showByteArr(byte[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append(list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（int列表）
     */
    public static String showIntArr(List<Integer> list) {
        if (list == null) {
            return "null";
        }
        if (list.isEmpty()) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i) + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（String列表）
     */
    public static String showStringArr(List<String> list) {
        if (list == null) {
            return "null";
        }
        if (list.isEmpty()) {
            return "";
        }
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i) + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（Map列表）
     */
    public static String showList(List<Map<String, Object>> list) {
        if (list == null) {
            return "null";
        }
        if (list.isEmpty()) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i) + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（float数组）
     */
    public static String showFloatArr(float[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append("" + list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示数组和列表（泛型）
     */
    public class MyLogArr<T> {
        /**
         * 显示列表（对象数组）
         */
        public String showArr(T[] list) {
            if (list == null) {
                return "null";
            }
            if (list.length == 0) {
                return "";
            }

            StringBuffer result = new StringBuffer();

            for (int i = 0; i < list.length; i++) {
                result.append(list[i] + ",");
            }
            result.delete(result.length() - 1, result.length());
            return result.toString();
        }

        /**
         * 显示列表
         */
        public String showList(List<T> list) {
            if (list == null) {
                return "null";
            }
            if (list.isEmpty()) {
                return "";
            }

            StringBuffer result = new StringBuffer();

            for (int i = 0; i < list.size(); i++) {
                result.append(list.get(i) + ",");
            }
            result.delete(result.length() - 1, result.length());
            return result.toString();
        }
    }

    /**
     * URL 解码
     */
    public static String unicodeToUrl(String url) {
        return URLDecoder.decode(url);
    }

    /**
     * bitmap转为byte数组
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 判断版本号大小，新版本弱大于老版本号，则返回true,其余情况为false
     */
    public static boolean compareVersionName(String oldVerName, String newVerName) {
//        if (!TextUtils.isEmpty(oldVerName) && !TextUtils.isEmpty(newVerName)) {
//            String[] oldNumbers = oldVerName.split("\\.");
//            String[] newNumbers = newVerName.split("\\.");
//
//            for (int i = 0; i < newNumbers.length; i++) {
//                try {
//                    int newInt = NumberUtil.strToInt(newNumbers[i]);
//                    int oldInt = NumberUtil.strToInt(oldNumbers[i]);
//                    if (newInt > oldInt) {
//                        return true;
//                    } else if (newInt == oldInt) {
//                        continue;
//                    } else {
//                        return false;
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//        }
        return false;
    }



}
