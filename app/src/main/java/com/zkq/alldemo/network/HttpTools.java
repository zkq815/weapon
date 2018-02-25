package com.zkq.alldemo.network;

import android.support.annotation.Nullable;


import java.util.HashMap;
import java.util.Map;

/**
 * @author yc
 * @since 2017/9/7
 */
public class HttpTools {

    /**
     * 增加通用HTTP头
     */
    public static Map<String, String> addCommonHeaders(@Nullable final Map<String, String> headers) {
        final Map<String, String> map = new HashMap<>();
        if (headers != null && !headers.isEmpty()) {
            map.putAll(headers);
        }

//        map.put("mz_version", String.valueOf(BuildConfig.VERSION_CODE));
//        map.put("mz_channel", BuildConfig.CHANNEL);

        return map;
    }
}
