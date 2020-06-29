package com.zkq.weapon.market.tools;

import android.text.TextUtils;

import com.zkq.weapon.market.util.ZLog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import androidx.annotation.Nullable;

/**
 * @author:zkq
 * create:2018/10/24 下午2:38
 * email:zkq815@126.com
 * desc: 价格工具类
 */
public interface ToolPriceFormat {

    DecimalFormat sDecimalFormat = new DecimalFormat("##0.00");
    /**
     * 省略小数点尾部的0
     */
    DecimalFormat sDecimalFormat1 = new DecimalFormat("##0.##");

    static String toFloat2(float f) {
        return sDecimalFormat.format(f);

//        BigDecimal b = new BigDecimal(f);
//        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//        return f1;
    }

    static String formatPriceCent(final BigDecimal b) {
        if (b == null) {
            return "0.00";
        }

        final float price = b.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue() * 1.0f / 100;
        return sDecimalFormat.format(price);
    }

    static String formatPrice(@Nullable final BigDecimal b) {
        if (b == null) {
            return "0.00";
        }

        final float price = b.floatValue();
        return sDecimalFormat.format(price);
    }


    static String formatPrice1(final double d) {
        return sDecimalFormat1.format(d);
    }

    static String formatPrice1(@Nullable final BigDecimal b) {
        if (b == null) {
            return "0";
        }

        final float price = b.floatValue();
        return sDecimalFormat1.format(price);
    }

    static String formatPriceCent1(final long priceCent) {
        return sDecimalFormat1.format(((float) priceCent / 100));
    }

    /**
     * 元转分
     * @param money
     * @return
     */
    static String getFen(String money) {
        if (TextUtils.isEmpty(money))
            return "";

        float fen = 0;
        try {
            fen = Float.parseFloat(money) * 100;
        } catch (Exception e) {
            ZLog.d(e.getMessage());
        }
        return getFormatedFloat(fen, 0);
    }

    /**
     * 分转元
     * @param money
     * @return
     */
    static String getYuan(float money) {

        float y = 0;
        try {
            y = money / 100;
        } catch (Exception e) {
            ZLog.d(e.getMessage());
        }
        return getFormatedFloat(y, 2);
    }

    /**
     * 取得浮点数保留小数点后两位的字符串
     */
    static String getFormatedFloat(final float value, int precision) {
        if (Float.isNaN(value)) {
            return "--";
        }
        return getFormattedFloat(value, precision);
    }

    static String getFormatedFloatForPriceAlert(final double value) {
        if (Double.isNaN(value)) {
            return "";
        }
        return getFormattedDouble(value);
    }

    static String getFormattedDouble(final double value) {
        return getFormatedFloat((float) value, 2);
    }

    static String getFormattedFloat(final float value, int precision) {
        return getFormattedFloat(value, precision, false);
    }

    static String getFormattedFloat(final float value, int precision, boolean withSign) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        switch (precision) {
            case 0:
                nf.setMaximumFractionDigits(0);
                nf.setMinimumFractionDigits(0);
                break;
            case 1:
                nf.setMaximumFractionDigits(1);
                nf.setMinimumFractionDigits(1);
                break;
            case 2:
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
                break;
            case 3:
                nf.setMaximumFractionDigits(3);
                nf.setMinimumFractionDigits(3);
                break;
            case 4:
                nf.setMaximumFractionDigits(4);
                nf.setMinimumFractionDigits(4);
                break;
            case 5:
                nf.setMaximumFractionDigits(5);
                nf.setMinimumFractionDigits(5);
                break;
            case 6:
                nf.setMaximumFractionDigits(6);
                nf.setMinimumFractionDigits(6);
                break;
            default:
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
                break;
        }
        String valueStr = nf.format(value);
        return withSign && value > 0 ? ("+" + valueStr) : valueStr;
    }


    static String getFormatedFloatWithoutDecimal(final double value) {
        if (Double.isNaN(value)) {
            return "--";
        }
        return (int) value + "";
    }
}
