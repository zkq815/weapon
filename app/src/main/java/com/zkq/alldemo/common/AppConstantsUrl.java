package com.zkq.alldemo.common;

/**
 * 客户端固定链接地址
 * Created by small_plane on 16/7/24.
 */
public enum AppConstantsUrl {
    APP_MALL_URL("http://mall.meizu.com"),
    APP_INSURANCE_URL("http://insurance.meizu.com"),
    APP_ALAD_URL("http://m.alad.meizu.com"),
    APP_PYRAMID_URL("http://pyramid.mall.meizu.com"),
    APP_H5_DETAIL_HTML_KEY("detail.mall.meizu.com/item/"),
    APP_H5_DETAIL("https://detail.meizu.com/item/"),
    APP_H5_CART("cart.mall.meizu.com"),
    APP_USER_ORDER_ALL_URL("https://ordercenter.mall.meizu.com/mall/order/init.html?type=all"),
    APP_USER_ORDER_UNPAY_URL("https://ordercenter.mall.meizu.com/mall/order/init.html?type=unpay"),
    APP_USER_ORDER_PAID_URL("https://ordercenter.mall.meizu.com/mall/order/init.html?type=paid"),
    APP_USER_ORDER_ONROAD_URL("https://ordercenter.mall.meizu.com/mall/order/init.html?type=onroad"),
    APP_USER_PHONE_URL("https://ordercenter.mall.meizu.com/mall/order/phone/init.html"),
    APP_USER_INSURANCE_URL("https://insurance.meizu.com/mall/order/init.html"),
    APP_USER_MCYCLE_URL("https://mcycle.mall.meizu.com/repo/m/my-mcycle"),
    APP_USER_COUPON_URL("https://me.m.meizu.com/home/m/coupon_list"),
    APP_USER_MARRIVE_URL("https://mall.meizu.com/marrive/summary/index.html"),
    APP_USER_RED_PACKET_URL("https://me.m.meizu.com/home/m/redenvelop_list"),
    APP_USER_CONTACT_URL("https://me.m.meizu.com/home/contact/index"),
    APP_USER_SUGGEST_URL("https://me.m.meizu.com/home/suggest/index"),
    APP_USER_ADDRESS_URL("https://me.m.meizu.com/home/address/index"),
    APP_ORDER_CENTER_URL("https://ordercenter.mall.meizu.com/order/mall/add.html"),
    APP_USER_MESSAGE_URL("https://me.m.meizu.com/home/m/message_index"),
    APP_USER_PRESENT_RULE("https://hd.mall.meizu.com/rules/new.html"),
    APP_USER_M_CODE("https://mformy.mall.meizu.com"),
    APP_PAY_SUCCESS("paycenter.meizu.com/wap/success.html"),
    APP_POINTS_RULE_URL("https://hd.mall.meizu.com/rules/jifen.html");
    private String url;

    AppConstantsUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
