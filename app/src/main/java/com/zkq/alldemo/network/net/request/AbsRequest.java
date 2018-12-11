package com.zkq.alldemo.network.net.request;

import android.content.Context;

import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.application.MyApplication;
import com.zkq.weapon.market.tools.ToolNet;
import com.zkq.weapon.market.util.AndrUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huxiaoyuan on 16/4/11
 */
public class AbsRequest {
    //app info
    // app版本号
    private int versionCode;
    // 渠道号
    private String channelId;
    //device info
    // imei号
    private String imei;
    // Android 设备的唯一标识码
    private String androidId;
    // 品牌
    private String brand;
    // 手机型号
    private String model;
    // 手机版本号
    private int sdkVerCode;
    // mac地址
    private String mac;
    // 网络类型,未知=0,wifi=1,2g=2,3g=3,4g=4;
    private int netType;

    public AbsRequest() {
        final Context context = MyApplication.getInstance();
        this.versionCode = BuildConfig.VERSION_CODE;
//        this.channelId = BuildConfig.CHANNEL;
        this.imei = AndrUtils.getIMEI(context);
        this.androidId = AndrUtils.getPhoneId(context);
        this.brand = AndrUtils.getBrand();
        this.model = AndrUtils.getModel();
        this.sdkVerCode = AndrUtils.getSdkVersionCode();
        this.mac = AndrUtils.getMacAddress(context);
        this.netType = ToolNet.getNetworkType(context);
    }

    public Map<String, String> getBaseMap() {

        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("versionCode", String.valueOf(versionCode));
        baseMap.put("channelId", String.valueOf(channelId));
        baseMap.put("imei", String.valueOf(imei));
        baseMap.put("androidId", String.valueOf(androidId));
        baseMap.put("brand", String.valueOf(brand));
        baseMap.put("model", String.valueOf(model));
        baseMap.put("sdkVerCode", String.valueOf(sdkVerCode));
        baseMap.put("mac", String.valueOf(mac));
        baseMap.put("netType", String.valueOf(netType));
        return baseMap;
    }

}
