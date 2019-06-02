package com.zkq.weapon.market.mmkv;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.ParcelableMMKV;

/**
 * @author zkq
 * time: 2019/5/29 10:20 AM
 * email: zkq815@126.com
 * desc: MMKV封装，已经在application中初始化
 */
public class MmkvUtils {

    private static MMKV instance;

    public static MMKV getInstance(){
        if(null == instance){
            instance = MMKV.defaultMMKV();
        }
        return instance;
    }

    /**
     * 展示MMKV的使用方法
     * @param key       key值
     * @param object    values值
     */
    private void saveValue(String key, Object object){
        //存储数据 byte[],int、float、double、long、string、boolean、Parcelable、StringSet
        instance.encode(key, object.toString());

        //取基础数据
        instance.decodeString(key);
        instance.decodeBool(key);
        instance.decodeInt(key);
        instance.decodeBytes(key);
        instance.decodeDouble(key);
        instance.decodeFloat(key);
        instance.decodeLong(key);
        //取基础数据 添加默认值
        instance.decodeLong(key, 0);
        //取序列化的对象值
        instance.decodeParcelable(key, ParcelableMMKV.class);
        instance.decodeStringSet(key);
        //移除数据
        instance.remove(key);
        instance.removeValueForKey(key);
        instance.removeValuesForKeys(new String[]{key, key});
    }
}
