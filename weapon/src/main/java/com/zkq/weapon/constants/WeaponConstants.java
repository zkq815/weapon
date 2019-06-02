package com.zkq.weapon.constants;

/**
 * @author: zkq
 * time: 2018/11/15:15:56
 * email: zkq815@126.com
 * desc:
 */
public interface WeaponConstants {

    /**********************************************************************
     *                          String                                    *
     **********************************************************************/

    /**
     * UTF-8编码
     */
    String CHARSET_UTF_8 = "UTF-8";
    /**
     * rom相关
     * */
    String MEIZU_FLAG_DARK_STATUS_BAR_ICON = "MEIZU_FLAG_DARK_STATUS_BAR_ICON";
    String MEIZU_FLAGS = "meizuFlags";
    String XIAOMI_LAYOUTPARAMS = "android.view.MiuiWindowManager$LayoutParams";
    String XIAOMI_FLAGS_DARK = "EXTRA_FLAG_STATUS_BAR_DARK_MODE";
    /**
     * web相关
     * */
    String WEB_TITLE= "webTitle";
    String WEB_URL = "webUrl";
    String WEB_PUSH_URL = "webPushUrl";
    String SHOW_REFRESH = "webShowFresh";

    String CHINA_MOBILE_HEAD = "+86";



    /**********************************************************************
     *                          Integer                                   *
     **********************************************************************/

    /**
     * 地球半径（单位米）
     * */
    double EARTH_RADIUS = 6371.393;

    /**
     * 大陆第一代身份号码证长度
     * */
    int ID_CARD_FIRST = 15;

    /**
     * 大陆第二代身份号码证长度
     * */
    int ID_CARD_SECOND = 18;

    /**********************************************************************
     *                        Baseholder                                  *
     **********************************************************************/
    /**
     * 自动循环viewpager，循环间隔时间为4秒
     */
    short VIEW_PAGER_AUTO_SCROLL_INTERVAL = 4 * 1000;
    /**
     * 首页底部导航栏Tab切换动画默认持续时间
     */
    int HOME_CONTAINER_NAVIGATION_BAR_ANIMATION_DURATION = 100;
    /**
     * 否
     */
    int NO = 0;
    /**
     * 是
     */
    int YES = 1;
    /**
     * 是
     */
    String YES_STR = "1";
    /**
     * 否
     */
    String NO_STR = "0";
    /**
     * 正确
     */
    int SUCCESS = 0;
    /**
     * 错误
     */
    int ERROR = 1;
    /**
     * 最后一页，没有更多数据的标识
     */
    int ISLASTPAGE = 1;

}
