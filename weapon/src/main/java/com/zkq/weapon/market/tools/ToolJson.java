package com.zkq.weapon.market.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author zkq
 * create:2018/12/11 9:59 AM
 * email:zkq815@126.com
 * desc: Json工具类
 */
public interface ToolJson {

    /**
     * list to json
     *
     * @param list the list that will transform to json string
     * @return the json string of list transform
     */
    @NonNull
    static String list2Json(@Nullable List list) {
        return JSON.toJSONString(list);
    }

    /**
     * map to json
     *
     * @param map the map that will transform to json string
     * @return the json string of map transform
     */
    @NonNull
    static String map2Json(@Nullable Map map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * object array to json
     *
     * @param objects the object array that will transform to json string
     * @return the json string of array transform
     */
    @NonNull
    static String array2Json(@Nullable Object[] objects) {
        return JSON.toJSONString(objects);
    }

    /**
     * object to json
     *
     * @param object the object that will transform to json string
     * @return the json string of object
     */
    @NonNull
    static String object2Json(@Nullable Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json to list
     *
     * @param json  the json string that will transform to list
     * @param clazz the class of the list's element
     * @param <T>   the generic of the class
     * @return the list that json string transform
     */
    @Nullable
    static <T> List<T> json2List(@Nullable String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * json to map
     *
     * @param json json string that will transform to map
     * @return the map fo json string
     */
    @Nullable
    static Map json2Map(@Nullable String json) {
        return JSONObject.parseObject(json);
    }

    /**
     * json string to object array
     *
     * @param json  the json string will transform to object array
     * @param clazz the class of the json will transform
     * @param ts    the real object array
     * @param <T>   the real object
     * @return the array of json string
     */
    @NonNull
    static <T> T[] json2Array(@Nullable String json, Class<T> clazz, @NonNull T[] ts) {
        List<T> list = JSON.parseArray(json, clazz);
        if (list == null) {
            return ts;
        }
        return list.toArray(ts);
    }

    /**
     * json string to object
     *
     * @param json  the json string that will transform to object
     * @param clazz the class that json will transform
     * @param <T>   the object class
     * @return the object of json string
     */
    @Nullable
    static <T> T json2Object(@Nullable String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    /**
     * 获取assets目录下文件的字节数组
     *
     * @param context  上下文
     * @param fileName assets目录下的文件
     * @return assets目录下文件的字节数组
     */
    static byte[] getAssetsFile(Context context, String fileName) {
        InputStream inputStream;
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open(fileName);

            BufferedInputStream bis = null;
            int length;
            try {
                bis = new BufferedInputStream(inputStream);
                length = bis.available();
                byte[] data = new byte[length];
                bis.read(data);

                return data;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取assets目录下json文件的数据
     *
     * @param context 上下文
     * @param name    assets目录下的json文件名
     * @return json数据对象
     */
    static org.json.JSONObject getJSONDataFromAsset(@NonNull Context context, String name) {
        try {
            InputStream inputStream = context.getAssets().open(name);
            BufferedReader inputStreamReader =
                    new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = inputStreamReader.readLine()) != null) {
                sb.append(str);
            }
            inputStreamReader.close();
            return new org.json.JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断用于Glide的Context是否有效
     *
     * @param context 用于Glide的Context
     * @return 用于Glide的Context是否有效
     */
    static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            return !activity.isDestroyed() && !activity.isFinishing();
        }
        return true;
    }

}
