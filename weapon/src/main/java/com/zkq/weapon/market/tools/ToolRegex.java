package com.zkq.weapon.market.tools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Pattern;

/**
 * @author zkq
 * create:2018/12/11 10:01 AM
 * email:zkq815@126.com
 * desc: 正则工具类
 */
public interface ToolRegex {

    /**
     * 正则表达式：邮箱
     */
    String REGEX_EMAIL = "^([a-zA-Z0-9_\\-.|]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";

    /**
     * 正则表达式：验证用户名
     */
    String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    String REGEX_PASS_WORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证汉字
     */
    String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证只有英文和数字
     * */
    String REGEX_ENGLISH_NUMBER = "[a-zA-Z0-9]+";

    /**
     * 正则表达式：验证只有英文、数字、中文
     * */
    String REGEX_ENGLISH_NUMBER_CHINESE = "[\\w]+";

    /**
     * 正则表达式：验证只有英文、数字、_、-
     * */
    String REGEX_ENGLISH_NUMBER_CHAR = "[a-zA-Z0-9\\-\\_]+";

    /**
     * 验证email是否符合邮箱规则
     *
     * @param email  待验证的邮箱
     * @return true：符合邮箱规则
     */
    static boolean isEmail(@Nullable String email) {
        return ToolText.isNotEmpty(email) && Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 验证电话号码是否符合规则
     *
     * @param phoneNumber 电话号码
     * @return true:符合规则
     * */
    static boolean isPhone(@Nullable String phoneNumber){
        return ToolText.isNotEmpty(phoneNumber) && Pattern.matches(REGEX_MOBILE, phoneNumber);
    }

    /**
     * 验证字符串是否符合全部为汉字规则
     *
     * @param chinese 汉字字符串
     * @return true:符合规则
     * */
    static boolean isChinese(@Nullable String chinese){
        return ToolText.isNotEmpty(chinese) && Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 验证身份证是否符合规则
     *
     * @param idCard 身份证
     * @return true:符合规则
     * */
    static boolean isIdCard(@Nullable String idCard){
        return ToolText.isNotEmpty(idCard) && Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 验证url是否符合规则
     *
     * @param url url地址
     * @return true:符合规则
     * */
    static boolean isUrl(@Nullable String url){
        return ToolText.isNotEmpty(url) && Pattern.matches(REGEX_URL, url);
    }

    /**
     * 验证ip是否符合规则
     *
     * @param ip ip地址
     * @return true:符合规则
     * */
    static boolean isIP(@Nullable String ip){
        return ToolText.isNotEmpty(ip) && Pattern.matches(REGEX_IP_ADDR, ip);
    }

    /**
     * 只允许英文、数字的正则判断
     *
     * @param str 需验证的字符串
     * @return true:只包含数字和英文字母
     */
    static boolean isOnlyEnglishAndNumber(String str) {
        return ToolText.isNotEmpty(str) && Pattern.matches(REGEX_ENGLISH_NUMBER, str);
    }

    /**
     * 只允许英文、数字、汉字的正则判断
     *
     * @param str 需要验证的字符串
     * @return true:只有英文数字和汉字
     */
    static boolean isEnglishNumberAndChinese(String str) {
        return ToolText.isNotEmpty(str) && Pattern.matches(REGEX_ENGLISH_NUMBER_CHINESE, str);
    }

    /**
     * 只允许英文、数字、_、-的正则判断
     *
     * @param str 需要验证的字符串
     * @return true:只有英文数字和汉字
     */
    static boolean isEnglishNumberAndChar(String str) {
        return ToolText.isNotEmpty(str) && Pattern.matches(REGEX_ENGLISH_NUMBER_CHAR, str);
    }

    /**
     * 验证字符串是否满足正则表达式
     *
     * @param input  待验证的字符串
     * @param regex  任意非空正则表达式
     * @return       true：传入的字符串满足正则规则
     */
    static boolean isMatches(@Nullable String input, @NonNull String regex) {
        return ToolText.isNotEmpty(input) && Pattern.matches(regex, input);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    @NonNull
    static String getReplaceAll(@Nullable final String input, @NonNull final String regex
            , @NonNull final String replacement) {
        if (input == null) {
            return "";
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    /**
     * 判断是否包含中文
     *
     * @param str 目标字符串
     * @return true:包含中文
     * */
    static boolean isContainChinese(String str) {
        return Pattern.compile(REGEX_CHINESE).matcher(str).find();
    }
}
