package com.zkq.alldemo.util;

/**
 * SharedPreferences缓存key
 *
 * @author small_plane
 * @since 16/7/22
 */
public enum PreferenceKey {

    /**
     * 账户名称，通过AccountManager获取，用于判断用户是否退出登录或切换账户
     */
    ACCOUNT_NAME("account_name"),

    /**
     * 公开版本登录token
     */
    FLYME_LOGIN_REFRESH_TOKEN("flyme_login_refresh_token"),

    /**
     * MPlus登录token
     */
    MPLUS_LOGIN_TOKEN("mplus_login_token"),

    /**
     * cookie
     */
    MALL_LOGIN_COOKIES("mall_login_cookies_bean"),

    /**
     * cookie失效时间
     */
    COOKIE_EXPIRE_TIME("cookie_expire_time"),

    /**
     * cookie记录时间
     */
    COOKIE_RECORD_TIME("cookie_record_time"),

    ADDRESS_MAP("address_map"),
    USER_INFO_ICON("user_info_icon"),
    USER_INFO_NAME("user_info_name"),

    /**
     * WebView跳转登录的拦截地址
     */
    LOGIN_LINKS("login_links"),

    /**
     * WebView跳转登录的拦截地址更新时间
     */
    LOGIN_LINKS_UPDATE_TIME("login_links_update_time"),

    /**
     * 广告数据
     */
    AD_INFO("ad_info"),

    /**
     * 强制更新记录
     */
    HAS_FORCE_UPDATE("has_force_update"),

    /**
     * 自更新检查时间
     */
    LAST_CHECK_UPDATE_TIME("last_check_update_time"),

    /**
     * 自更新下载ID，用于安装后清除通知栏图标
     */
    LAST_UPDATE_DOWNLOAD_ID("last_update_download_id"),

    /**
     * 搜索历史列表
     */
    SEARCH_HISTORY("search_words_history_list"),

    /**
     * 首页数据缓存
     */
    MAIN_PAGE_CACHE("main_page_cache"),

    /**
     * 分类界面 推荐+标题缓存
     */
    CATEGORY_RECOMMEND_TITLE("category_recommend_title"),

    /**
     * 分类界面 除了推荐页面的子页面缓存
     */
    CATEGORY_ITEM_PAGE_EXCEPT_RECOMMEND("category_item_page_except_recommend"),

    FAVORITE_CACHE("favorite_cache"),
    USER_SESSION_ID("MEIZUSTORESESSIONID"),
    SID("sid"),

    /**
     * uid
     */
    MZ_APP_USER_INFO("MZ_APP_USER_INFO"),
    UID("uid"),

    COOKIE("cookie"),

    GUID("guid"),
    /**
     * APP 是否被打开过
     * */
     MZ_APP_HAS_OPENED("MZ_APP_HAS_OPENED"),
    /**
     * 是否成功领取新人礼优惠券
     * */
//            首次启动权限申请
    NETWORK_PERMISSION("network_permission"),
    ;

    private String key;

    PreferenceKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

