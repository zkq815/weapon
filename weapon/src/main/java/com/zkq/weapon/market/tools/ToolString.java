package com.zkq.weapon.market.tools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zkq.weapon.constants.WeaponConstants.CHINA_MOBILE_HEAD;
import static com.zkq.weapon.constants.WeaponConstants.ID_CARD_FIRST;
import static com.zkq.weapon.constants.WeaponConstants.ID_CARD_SECOND;

/**
 * @author zkq
 * time: 2018/12/11:10:22
 * email: zkq815@126.com
 * desc:
 */
public interface ToolString {
    String YEAR = "year";
    String MONTH = "month";
    String DAY = "day";
    String HOUR = "hour";
    String MINUTE = "minute";
    String SECOND = "second";
    String MILLISECOND = "millisecond";
    int TEN = 10;
    int HUNDRED = 100;
    String ZERO_END = "00";

    /**
     * 将价格最后面的.0和.00去掉
     *
     * @param goodsPrice 价格
     * @return 处理后的价格
     * */
    static String deleteDecimalPoint(String goodsPrice) {
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

    /**
     * 比较字符串内容是否相同
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return true:相同
     * */

    static boolean equals(final String str1, final String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }

    /**
     * 米转换为千米
     *
     * @param meter 米
     * @return 千米
     * */
    static String meter2KM(int meter){
        int KM = 1000;
        String tempDistance = "";
        if(meter < KM){
            //小于一千米，显示米
            tempDistance = String.valueOf(meter+"米");
        }else{
            //大于一千米显示千米
            DecimalFormat df = new DecimalFormat("#.00");
            tempDistance = String.valueOf(df.format((double)meter/1000.00)+"千米");
        }
        return tempDistance;
    }

    /**
     * 给部分文字添加下划线
     *
     * @param content 需要划线文字内容
     * @param begin 划线起始位置
     * @param end 划线结束位置
     * @return 添加下划线后的文字
     */
    static SpannableString addUnderLine(String content, int begin, int end) {
        SpannableString spanString = new SpannableString(content);
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    /**
     * 设置中部划线
     * */
    static void addMidLine(TextView tv){
        //抗锯齿
        tv.getPaint().setAntiAlias(true);
        // 设置中划线并加清晰
        tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * URL 解码
     *
     * @param url 需要解码的url
     * @return decode 解码后的url
     */
    static String unicodeToUrl(String url) {
        return URLDecoder.decode(url);
    }

    /**
     * 身份证只显示首和尾，其余用星号代替
     *
     * @param idCart 身份证号码
     * @return 替换后的身份证字符串
     */
    static String replaceIdCard(String idCart) {
        String begin = idCart.substring(0, 1);
        String ending = "";
        String body = "";
        if (idCart.length() == ID_CARD_FIRST) {
            body = "*************";
            ending = idCart.substring(14, 15);
        } else if (idCart.length() == ID_CARD_SECOND) {
            body = "****************";
            ending = idCart.substring(17, 18);
        }
        return begin + body + ending;
    }

    /**
     * 判断验证码的长度是否符合规则
     *
     * @param code 需要验证的字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     *
     * @return true:长度符合
     */
    static boolean isCodeRight(String code, int minLength, int maxLength) {
        return code.length() >= minLength && code.length() <= maxLength;
    }

    /**
     * 只保留字符串中所有的数字
     *
     * @param oldString 原始字符串
     * @return 处理后的字符串
     */
    static String saveNumStr(String oldString) {
//        String str = "";
//        if (oldString.contains(CHINA_MOBILE_HEAD)) {
//            oldString = oldString.substring(3, oldString.length());
//        }
//        for (int i = 0; i < oldString.length(); i++) {
//            if (Character.isDigit(oldString.charAt(i))) {
//                str = str + oldString.charAt(i);
//            }
//        }
//        return str;
        return oldString.replaceAll("[^\\d]","");
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
    static String getString(String original, int[] params) {
        if (original == null || original.length() <= 0 || params == null || params.length <= 0) {
            return original;
        }
        String result = new String(original);

        for (int i = 0; i < params.length; i++) {
            String old = "$" + (i + 1) + "s";
            String newS = String.valueOf(params[i]);
            result = result.replace(old, newS);
        }

        return result;
    }

    /**
     * 获取字符串中的数值部分
     * @param oldString 倒计时字符串
     * @return 数值数组
     */
    static String[] getNumStrArray(String oldString){
        return oldString.split("[^\\d]");
    }

    /**
     * 获取字符串中的数值部分，并使用指定的分隔符替换所有非数字的字符
     * @param oldString 原字符串
     * @param splitStr 分割字符串
     * @return 数值数组
     */
    static String getNumStrWithSplit(String oldString, String splitStr){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < oldString.length(); i++) {
            if(Character.isDigit(oldString.charAt(i))){
                stringBuffer.append(oldString.charAt(i));
            }else{
                if(i!=0){
                    if(Character.isDigit(oldString.charAt(i-1))){
                        stringBuffer.append(oldString.charAt(i));
                    }
                }
            }
        }
        return stringBuffer.toString().replaceAll("[^\\d]",splitStr);
    }

    /**
     * 获得字符串中的非数值的字符串,并把数值过滤掉重新组合成一个字符串，
     * 并把字符串拆分字符数组，也就是保存倒计时中间的间隔
     * @param oldString 原字符串
     * @return 非数字的数组
     */
    static char[] getNonNumInTimerStr(String oldString){
        return oldString.replaceAll("\\d","").toCharArray();
    }

    /**
     * 设置内容的Span
     * @param mSpan 目标span
     * @param span 子span
     * @param start 开始下标
     * @param end 结束下标
     */
    static void setContentSpan(SpannableString mSpan, Object span, int start, int end) {
        mSpan.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    /**
     * 获取时间map，存放各个单位的时间
     *
     * @param ms 毫秒
     * @return 时间map
     */
    static Map<String, String> getTimeMap(long ms) {
        if (ms >= 0) {
            Map<String, String> tempMap = new HashMap<>();
            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;
            int yy = dd * 365;

            long year = ms / yy;
            long day = (ms - year * yy) / dd;
            long hour = (ms - year * yy - day * dd) / hh;
            long minute = (ms - year * yy - day * dd - hour * hh) / mi;
            long second = (ms - year * yy - day * dd - hour * hh - minute * mi) / ss;
            long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

            String strMilliSecond = milliSecond <= TEN ? "0" + milliSecond : "" + milliSecond;
            strMilliSecond = milliSecond < HUNDRED ? "0" + strMilliSecond : "" + milliSecond;
            if (milliSecond < TEN) {
                strMilliSecond = ZERO_END + milliSecond;
            } else if (milliSecond >= TEN && milliSecond < HUNDRED) {
                strMilliSecond = "0" + milliSecond;
            }

            tempMap.put(MILLISECOND, strMilliSecond.substring(0, 2));
            tempMap.put(SECOND, second < TEN ? "0" + second : "" + second);
            tempMap.put(MINUTE, minute < TEN ? "0" + minute : "" + minute);
            tempMap.put(HOUR, hour < TEN ? "0" + hour : "" + hour);
            tempMap.put(DAY, day < TEN ? "0" + day : "" + day);
            tempMap.put(YEAR, year < TEN ? "0" + year : "" + year);

            return tempMap;
        }
        return null;
    }

    /**
     * 将字符串首字母转为小写
     *
     * @param str 传入的字符串
     * @return 首字母小写后的字符串，若传入的字符串去掉首末空格后为空，则返回传入的字符串
     */
    @Nullable
    static String lowerFirstLetter(@Nullable String str) {
        String trim = trim(str);
        //trim之后为空，返回原始字符串
        if (isEmptyOrNull(trim)) {
            return str;
        }

        if (!Character.isUpperCase(trim.charAt(0))) {
            return trim;
        }

        return String.valueOf((char) (trim.charAt(0) + 32)) + trim.substring(1);
    }

    /**
     * 判断是否为空
     *
     * @param str 传入的字符串
     * @return true:为空
     */
    static boolean isEmptyOrNull(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否 非空
     *
     * @param str 传入的字符串
     * @return true:传入的字符串不为空
     */
    static boolean isNotEmpty(@Nullable String str) {
        return !isEmptyOrNull(str);
    }

    /**
     * 判断字符串中是否含有指定的文案
     *
     * @param str       被检测的字符串
     * @param searchStr 指定的文案
     * @return true: 字符串中是否含有要查找的字符串
     */
    static boolean contains(@Nullable String str, @Nullable String searchStr) {
        return !isEmptyOrNull(str) && searchStr != null && str.contains(searchStr);
    }

    /**
     * 去除字符串左右空格
     *
     * @param str 传入的字符串
     * @return 去除左右空格后的字符串
     */
    @Nullable
    static String trim(@Nullable String str) {
        if (isEmptyOrNull(str)) {
            return str;
        }
        return str.trim();
    }

    /**
     * 获取字符串的长度
     *
     * @param str 字符文本
     * @return str的长度
     */
    static int length(@Nullable String str) {
        return isEmptyOrNull(str) ? 0 : str.length();
    }

    /**
     * 忽略大小写后判断字符串是否相等
     *
     * @param str1 要比较的两字符串的其中一个
     * @param str2 要比较的两字符串的另一个
     * @return true：忽略大小写后，两字符串相等
     */
    static boolean equalsIgnoreCase(@Nullable String str1, @Nullable String str2) {
        if (str1 == null) {
            //判断str2是否也为null，都为null，返回true
            return str2 == null;
        } else {
            return str1.equalsIgnoreCase(str2);
        }
    }

    /**
     * 根据传入的拆分字符，拆分字符串
     *
     * @param textStr  字符串
     * @param splitStr 拆分字符
     * @return 拆分后的字符串数组
     */
    @Nullable
    static String[] split(@Nullable String textStr, @Nullable String splitStr) {
        if (isEmptyOrNull(textStr)) {
            return null;
        }
        if (splitStr == null) {
            return new String[]{textStr};
        }
        return textStr.split(splitStr);
    }

    /**
     * 将字符串首字母转为大写
     *
     * @param str 传入的字符串
     * @return 首字母大写后的字符串，若传入的字符串去掉首末空格后为空，则返回传入的字符串
     */
    @Nullable
    static String upperFirstLetter(@Nullable String str) {
        String trim = trim(str);
        //trim之后为空，返回原始字符串
        if (isEmptyOrNull(trim)) {
            return str;
        }

        if (!Character.isLowerCase(trim.charAt(0))) {
            return trim;
        }

        return String.valueOf((char) (trim.charAt(0) - 32)) + trim.substring(1);
    }

    /**
     * 统计fullStr中包含searchStr的个数，默认返回0
     *
     * @param fullStr   长字符串
     * @param searchStr 要统计的短字符串
     * @return count  含有的个数
     */
    static int countStr(@Nullable String fullStr, @Nullable String searchStr) {
        if (!contains(fullStr, searchStr) || isEmptyOrNull(searchStr)) {
            return 0;
        }

        int count = 0;
        int index;
        String temp = fullStr;

        while ((index = temp.indexOf(searchStr)) > -1) {
            count++;
            if (temp.length() > index + searchStr.length()) {
                // fullStr最后几位恰好为searchStr时，此时index + searchStr.length() = temp.length
                // ，此时substring不会抛索引越界，但建议过滤
                temp = temp.substring(index + searchStr.length());
            } else {
                break;
            }
        }

        return count;
    }

    static boolean isBlank(String str) {
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

    static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    static String trim(final String src, final char c) {
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
     * 显示列表（int数组）
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showIntArr(int[] list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showStringArr(String[] list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showByteArr(byte[] list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showIntArr(List<Integer> list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showStringArr(List<String> list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showList(List<Map<String, Object>> list) {
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
     *
     * @param list 数组
     * @return result 内容显示
     */
    static String showFloatArr(float[] list) {
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
    class MyLogArr<T> {
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

}
