package com.zkq.weapon.market.util;

import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author zkq
 * create:2018/11/16 10:25 AM
 * email:zkq815@126.com
 * desc:文件工具
 */
public class FileUtils {

    public final static String MEIZU = "meizu";
    public final static String DOWNLOAD = "download";
    public final static String PATCH = "patch";
    public final static String TINKER = "tinker";

    private static boolean checkDir(final File dir) {
        boolean ret = true;

        if (dir.exists() && !dir.isDirectory()) {
            if (!dir.delete()) {
                    ZLog.e("delete failed: " + dir.getAbsolutePath());
                ret = false;
            }
        }

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                    ZLog.e("mkdirs failed: " + dir.getAbsolutePath());
                ret = false;
            }
        }

        return ret;
    }

    public static File getDownloadDir() {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "applicationid");

        checkDir(dir);

        return dir;
    }

    public static File getDownloadDir(String dirName) {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dirName);

        checkDir(dir);

        return dir;
    }

    public static File getPatchDir() {
        final File dir = new File(getDownloadDir(), PATCH);

        checkDir(dir);

        return dir;
    }

    public static File getTinkerPatchDir() {
        final File dir = new File(getPatchDir(), TINKER);

        checkDir(dir);

        return dir;
    }

    /**
     * 获取文件夹内容的大小
     */
    public static double getDirSize(File file) {

        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children){
                    size += getDirSize(f);
                }

                BigDecimal bigDecimal = new BigDecimal(size);
                return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                return size/ 1024 / 1024;//以字节为单位
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            file.mkdir();
            return 0;
        }
    }

    /**
     * 删除文件夹所有内容（文件夹保留）
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
//              delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }




}
