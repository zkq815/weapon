package com.zkq.weapon.market.tools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import com.zkq.weapon.market.util.ZLog;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author zkq
 * create:2018/12/11 9:58 AM
 * email:zkq815@126.com
 * desc: 加密工具类
 */
public interface ToolEncrypt {

    @StringDef
    @interface ENCRYPT_TYPE {
        /**
         * MD5加密方式
         */
        String ENC_TYPE_MD5 = "MD5";
        /**
         * SHA-256加密方式
         */
        String ENC_TYPE_SHA256 = "SHA-256";
    }

    /**
     * 获取加密后字符串
     * 默认使用SHA-256
     *
     * @param value   要加密的字符串
     * @param encName 加密类型 {@link ENCRYPT_TYPE}
     * @return 加密后字符串 16进制32位字符串(小写)
     */
    @NonNull
    static String getEncryptString(@Nullable String value, @ENCRYPT_TYPE String encName) {
        if (ToolText.isEmptyOrNull(value)) {
            return "";
        }

        String strDes;
        try {
            MessageDigest md = MessageDigest.getInstance(ToolText.isNotEmpty(encName)
                    ? encName : ENCRYPT_TYPE.ENC_TYPE_SHA256);
            md.update(value.getBytes());
            strDes = bytes2Hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            strDes = "";
        }
        return strDes;
    }

    /**
     * bytes转16进制字符串
     *
     * @param bts bytes数组
     * @return 16进制字符串 (小写)
     */
    @NonNull
    static String bytes2Hex(@NonNull byte[] bts) {
        StringBuilder sb = new StringBuilder();
        String tmp;

        for (byte bt : bts) {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }

        return sb.toString();
    }

    static String getMd5(final String path) {
        return getMd5(new File(path));
    }

    static String getMd5(final File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        final byte[] buf = new byte[8192];

        int len;

        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final FileInputStream is = new FileInputStream(file);
            try {
                while (-1 != (len = is.read(buf))) {
                    digest.update(buf, 0, len);
                }
                final BigInteger bigInt = new BigInteger(1, digest.digest());
                String output = bigInt.toString(16);
                for (; output.length() < 32; ) {
                    output = "0" + output;
                }

                return output;
            } finally {
                try {
                    is.close();
                } catch (Exception ignored) {
                }
            }

        } catch (Exception e) {
            ZLog.t("getMd5", e);
        }

        return null;
    }

    /**
     * MD5加密
     */
    static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位的加密
            ZLog.e("32位: " + buf.toString());
            // 16位的加密，其实就是32位加密后的截取
            ZLog.e("16位: " + buf.toString().substring(8, 24));
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
