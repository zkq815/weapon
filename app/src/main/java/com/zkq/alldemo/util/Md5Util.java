package com.zkq.alldemo.util;

import com.zkq.alldemo.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5工具<br/>
 * Created by yc on 16/8/30.
 */
public class Md5Util {

    public static String getMd5(final String path) {
        return getMd5(new File(path));
    }

    public static String getMd5(final File file) {
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
            if (BuildConfig.LOG_DEBUG) {
                ZKQLog.t("getMd5", e);
            }
        }

        return null;
    }

    /**
     * MD5加密
     */
    public static String toMD5(String plainText) {
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
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32位: " + buf.toString());// 32位的加密
            System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
