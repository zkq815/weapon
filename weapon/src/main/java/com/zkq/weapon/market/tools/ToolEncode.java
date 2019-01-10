package com.zkq.weapon.market.tools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import java.net.URLDecoder;
import java.net.URLEncoder;
import static com.zkq.weapon.constants.WeaponConstants.CHARSET_UTF_8;

/**
 * @author zkq
 * create:2018/12/11 9:58 AM
 * email:zkq815@126.com
 * desc: 编码工具类
 */
public interface ToolEncode {

    /**
     * 使用URL编码 UTF-8
     *
     * @param url url编码前值
     * @return 编码后的值
     */
    @NonNull
    static String encodeUrl(@Nullable String url) {
        if (ToolString.isEmptyOrNull(url)) {
            return "";
        }
        try {
            url = URLEncoder.encode(url, CHARSET_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 使用URL解码 默认UTF-8
     *
     * @param url url解码前值
     * @return 解码后的值
     */
    @NonNull
    static String decodeUrl(@Nullable String url) {
        if (ToolString.isEmptyOrNull(url)) {
            return "";
        }
        try {
            url = URLDecoder.decode(url, CHARSET_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取Base64编码后的值
     * 使用{@link Base64#NO_WRAP}方式省略所有换行符
     *
     * @param value 编码前字符串
     * @return 编码后的值
     */
    @NonNull
    static String encodeBase64(@Nullable String value) {
        if (ToolString.isEmptyOrNull(value)) {
            return "";
        }

        try {
            value = new String(Base64.encode(value.getBytes(), Base64.NO_WRAP), CHARSET_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 使用Base64解码.
     * 使用{@link Base64#NO_WRAP}方式省略所有换行符
     *
     * @param value 解码后字符串
     * @return 解码后的值
     */
    @NonNull
    static String decodeBase64(@Nullable String value) {
        if (ToolString.isEmptyOrNull(value)) {
            return "";
        }

        try {
            value = new String(Base64.decode(value.getBytes(), Base64.NO_WRAP), CHARSET_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
