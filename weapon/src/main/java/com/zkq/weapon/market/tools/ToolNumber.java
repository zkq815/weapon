package com.zkq.weapon.market.tools;

import android.graphics.Bitmap;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.zkq.weapon.constants.WeaponConstants.EARTH_RADIUS;

/**
 * @author zkq
 * create:2018/12/11 10:01 AM
 * email:zkq815@126.com
 * desc: 数字工具类
 */
public interface ToolNumber {

    /**
     * 校验字符串是否都是数字 <br/>
     * 示例：<br/>
     * true: 0  11 1234545679 <br/>
     * false: -2  -1.1  0.0  1.3  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的数字字符串
     * @return true:是数字
     */
    static boolean isDigit(@Nullable String numberValue) {
        if (numberValue == null || numberValue.length() == 0) {
            return false;
        }

        return numberValue.matches("[0-9]+");
    }

    /**
     * 校验字符串是否是整数 ("整数"指的整数类型，非小数点类型。即：包括负数、Long数据类型均返回true) <br/>
     * 示例：<br/>
     * true: -12  0  15 <br/>
     * false: -1.1  0.0  1.3  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的数值字符串
     * @return true:是数值
     */
    static boolean isInteger(@Nullable String numberValue) {
        if (numberValue == null || numberValue.length() == 0) {
            return false;
        }

        return numberValue.matches("-[0-9]+|[0-9]+");
    }

    /**
     * 校验字符串是否是数值 (包含负数与浮点数) <br/>
     * <p>
     * 示例：<br/>
     * true: -12  -12.0  -12.056  12  12.0  12.056 <br/>
     * false: .  1.  1sr  -   12.  -12.  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的字符串
     * @return true：是数值
     */
    static boolean isNumeric(@Nullable String numberValue) {
        String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";

        if (numberValue == null || !numberValue.matches(regex)) {
            return false;
        }

        return true;
    }

    /**
     * 强转成int基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * 示例：<br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 11、11.13、"11.99" -> 11 <br/>
     * -1、-1.13、"-1.99" -> -1 <br/>
     *
     * @param intValue Integer、Number或String类型的int值
     * @return 强转后的int值
     */
    static int toInt(@Nullable Object intValue) {
        if (intValue instanceof Integer) {
            return (Integer) intValue;
        } else if (intValue instanceof Number) {
            return ((Number) intValue).intValue();
        } else if (intValue instanceof String) {
            try {
                return (int) Double.parseDouble((String) intValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成long基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * 示例：<br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 11、11.13、"11.99" -> 11 <br/>
     * -1、-1.13、"-1.99" -> -1 <br/>
     *
     * @param longValue Long、String类型的Long值
     * @return 强转后的long值
     */
    static long toLong(@Nullable Object longValue) {
        if (longValue instanceof Long) {
            return (Long) longValue;
        } else if (longValue instanceof Number) {
            return ((Number) longValue).longValue();
        } else if (longValue instanceof String) {
            try {
                return (long) Double.parseDouble((String) longValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成short基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * short类型：最小值是 -32768，最大值是32767。超出范围转换后的数值不准确。<br/>
     * 如果不确定数据范围，请使用 {@link #toInt(Object)}、 {@link #toLong(Object)}。<br/>
     * <p>
     * 示例： <br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 55、55.13、"55.99" -> 55 <br/>
     * -5、-5.13、"-5.99" -> -5 <br/>
     *
     * @param shortValue Short、Number、String类型的short值
     * @return 强转后的short值
     */
    static short toShort(@Nullable Object shortValue) {
        if (shortValue instanceof Short) {
            return (Short) shortValue;
        } else if (shortValue instanceof Number) {
            return ((Number) shortValue).shortValue();
        } else if (shortValue instanceof String) {
            try {
                return (short) Double.parseDouble((String) shortValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成double基本类型值，默认0.0 (譬如：数据类型不匹配、转换异常) <br/>
     *
     * @param decFormat   浮点数格式化器
     * @param doubleValue 浮点值
     * @return 格式化后的double值
     */
    static double toDouble(@NonNull DecimalFormat decFormat, @Nullable Object doubleValue) {
        return Double.parseDouble(decFormat.format(toDouble(doubleValue)));
    }

    /**
     * 强转成double基本类型值，不截取、不会四舍五入，默认0.0 (譬如：数据类型不匹配、转换异常) <br/>
     * 可能存在(1.0/3 = 0.333333333333333)未截取情况，使用 {@link #toDouble(DecimalFormat, Object)} 可处理。<br/>
     * <p>
     * 示例：<br/>
     * null、""、1.1abc、ab2.2、１２３４(全角) -> 0.0 <br/>
     * 55.66、"55.66" -> 55.66 <br/>
     * -5.4999、"-5.4999" -> -5.4999 <br/>
     *
     * @param doubleValue 浮点值
     * @return 转换后的浮点值
     */
    static double toDouble(@Nullable Object doubleValue) {
        if (doubleValue instanceof Double) {
            return (Double) doubleValue;
        } else if (doubleValue instanceof Number) {
            return ((Number) doubleValue).doubleValue();
        } else if (doubleValue instanceof String) {
            try {
                return Double.parseDouble((String) doubleValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0.0;
    }

    /**
     * 获取 1 到 x 之间随机数 (包括1、X) <br/>
     * x最小数字是1，否则会不准确。<br/>
     *
     * @param x 随机数最大数
     * @return 随机数
     */
    static int getRandom1ToX(@IntRange(from = 1) int x) {
        long randomValue = (long) (Math.random() * x + 1);

        if (randomValue > Integer.MAX_VALUE) {
            randomValue = Integer.MAX_VALUE;
        }

        return (int) randomValue;
    }

    /**
     * bitmap转为byte数组
     *
     * @param bm bitmap
     * @return byte[] byte数组
     */
    static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 根据纬度获取弧度
     * @param d 纬度
     * @return 弧度
     * */
    static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 获取两个经纬度坐标之间的距离
     * @param longitude1 第一点的经度
     * @param latitude1 第一点纬度
     * @param longitude2 第二点经度
     * @param latitude2 第二点纬度
     *
     * @return 以米为单位的距离
     * */
    static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {

        double lat1 = rad(latitude1);
        double lat2 = rad(latitude2);
        double a = lat1 - lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount 价格
     * @return 分
     */
    static Long changeY2F(String amount) {
        //处理包含, ￥ 或者$的金额
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return Long.valueOf(amLong.toString());
    }

    /**
     * 分转元
     *
     * @param price 价格
     * @return 元
     */
    static String changeF2Y(long price) {
        return BigDecimal.valueOf(price).divide(new BigDecimal(100)).toString();
    }
}
