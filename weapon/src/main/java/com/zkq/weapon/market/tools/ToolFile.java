package com.zkq.weapon.market.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zkq.weapon.R;
import com.zkq.weapon.market.util.ZLog;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author zkq
 * time: 2018/12/11:14:38
 * email: zkq815@126.com
 * desc: 文件工具类
 */
public interface ToolFile {

    String PATCH = "patch";
    String TINKER = "tinker";

    static boolean checkDir(final File dir) {
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

    static File getDownloadDir() {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "applicationid");

        checkDir(dir);

        return dir;
    }

    static File getDownloadDir(String dirName) {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dirName);

        checkDir(dir);

        return dir;
    }

    static File getPatchDir() {
        final File dir = new File(getDownloadDir(), PATCH);

        checkDir(dir);

        return dir;
    }

    static File getTinkerPatchDir() {
        final File dir = new File(getPatchDir(), TINKER);

        checkDir(dir);

        return dir;
    }

    /**
     * 获取文件夹内容的大小
     */
    static double getDirSize(File file) {

        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children) {
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
    static boolean delAllFile(String path) {
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


    /**
     * 将本地图片路径取出后重新压缩转存为新图片，并返回图片路径
     *
     * @param path       照片路径
     * @param flag       是否压缩
     * @param options    精度
     * @param isTakPhone 是否拍照
     */
    static String getCondensePath(Context context, String path, boolean flag, int options, boolean isTakPhone) {
        //将保存在本地的图片取出并缩小后显示在界面上
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return getCondense(context, bitmap, flag, options, isTakPhone);
    }

    static String getCondense(Context context, Bitmap bitmap, boolean flag, int options, boolean isTakPhone) {
        Bitmap topBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        File myCaptureFile = new File(Environment.getExternalStorageDirectory().toString() + "/hiyun", System.currentTimeMillis() + "temp.jpg");
        BufferedOutputStream bos;
        ByteArrayOutputStream baos;
        try {
            baos = new ByteArrayOutputStream();
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            if (flag) {
                baos = new ByteArrayOutputStream();
                //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                //循环判断如果压缩后图片是否大于30kb,大于继续压缩
                while (baos.toByteArray().length / 1024 > 2048) {
                    //重置baos即清空baos
                    baos.reset();
                    //这里压缩options%，把压缩后的数据存放到baos中
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                    //每次都减少10
                    options -= 30;
                }
                //把压缩后的数据baos存放到ByteArrayInputStream中
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                //把ByteArrayInputStream数据生成图片
                bitmap = BitmapFactory.decodeStream(isBm, null, null);

                if (bitmap.getWidth() <= 1024 || bitmap.getHeight() <= 1024) {
                    if (bitmap.getWidth() >= 600 && bitmap.getWidth() < 500) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, 600, bitmap.getHeight(), true);
                        topBitmap = Bitmap.createScaledBitmap(topBitmap, 120, bitmap.getHeight() / 5, true);
                    } else if (bitmap.getWidth() < 600 && bitmap.getWidth() >= 500) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), 500, true);
                        topBitmap = Bitmap.createScaledBitmap(topBitmap, bitmap.getWidth() / 5, 100, true);
                    } else if (bitmap.getWidth() < 600 && bitmap.getHeight() < 500) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                        topBitmap = Bitmap.createScaledBitmap(topBitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5, true);
                    }
                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 600, 500, true);
                    topBitmap = Bitmap.createScaledBitmap(topBitmap, 120, 100, true);
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
////=======================================上面是需要压缩的处理方法===========================================
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        String bitmapPath = "";
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片存储地址
            String endPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/hiyun/";
            File file = new File(endPath);
            if (!file.exists()) {
                file.mkdir();
            }
            bitmapPath = endPath + System.currentTimeMillis() + "temp.jpg";
            File saveBitmap = new File(bitmapPath);
            BufferedOutputStream os = null;
            try {
                int end = bitmapPath.lastIndexOf(File.separator);
                String fileLength = bitmapPath.substring(0, end);
                File filePath = new File(fileLength);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                saveBitmap.createNewFile();
                os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
//            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
        //获取图片的旋转角度
        int degree = ToolBitmap.readPictureDegree(bitmapPath, isTakPhone);
        //将压缩后的图片转换为bitmap
        Bitmap tempBitmap = BitmapFactory.decodeFile(bitmapPath);
        //获取图片旋转后的bitmap
        Bitmap lastBitmap = ToolBitmap.rotaingImageView(degree, tempBitmap);

        bitmap = ToolBitmap.watermarkBitmap(lastBitmap, topBitmap, null);

        //获取图片旋转后保存图片的路径
        String lastPicPath = saveBitMapToPic(bitmap);
        return lastPicPath;
    }

    /**
     * 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
     */
    static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i, j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }

        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 80, 80), null);
            if (paramBoolean) {
                bitmap.recycle();
            }
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;

            } catch (Exception e) {
            }

            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    /**
     * 保存bitmap图片（ 单独一张） 并返回当前的图片路径
     */
    static String saveBitmap(Activity activity, Bitmap bitmap, String picName, Handler handler) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            Time time = new Time("GMT+8");
            time.setToNow();
            int year = time.year;
            int month = time.month + 1;
            int day = time.monthDay;
            int hour = time.hour + 8;
            int minute = time.minute;
            int sec = time.second;
            //图片存储地址
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zkq/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String bitmapPath = path + "/" + year + month + day + hour + minute + sec + picName + ".png";
            File saveBitmap = new File(bitmapPath);

            BufferedOutputStream os = null;
            try {
                int end = bitmapPath.lastIndexOf(File.separator);
                String fileLength = bitmapPath.substring(0, end);
                File filePath = new File(fileLength);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                saveBitmap.createNewFile();

                os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

                handler.sendEmptyMessage(0);
                //刷新手机图库(没用)
                MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
                return bitmapPath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
//            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(2);
        }
        return "";
    }

    /**
     * 保存bitmap图片（ 单独一张） 并返回当前的图片路径
     */
    static String saveBitmapForWXShare(Activity activity, Bitmap bitmap) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            Time time = new Time("GMT+8");
            time.setToNow();
//            图片存储地址
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zkq/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String bitmapPath = path + "/" + "share.png";
            File saveBitmap = new File(bitmapPath);

            BufferedOutputStream os = null;
            try {
                int end = bitmapPath.lastIndexOf(File.separator);
                String fileLength = bitmapPath.substring(0, end);
                File filePath = new File(fileLength);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                saveBitmap.createNewFile();

                os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

                //刷新手机图库(没用)
                MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
                return bitmapPath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                Toast.makeText(activity, "保存失败，请重新保存", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
//            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    /**
     * 保存图片到系统相册
     */
    static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "haiyn");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
        Toast.makeText(context, "图片保存成功", Toast.LENGTH_LONG).show();
    }

    /**
     * 保存bitmap图片（单独一张） 到相机
     */
    static void saveBitmap(Activity activity, Bitmap bitmap) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            Time time = new Time("GMT+8");
            time.setToNow();
            int year = time.year;
            int month = time.month + 1;
            int day = time.monthDay;
            int hour = time.hour + 8;
            int minute = time.minute;
            int sec = time.second;
//            图片存储地址
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/hiyun/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String bitmapPath = path + year + month + day + hour + minute + sec + ".png";
            File saveBitmap = new File(bitmapPath);

            BufferedOutputStream os = null;
            try {
                int end = bitmapPath.lastIndexOf(File.separator);
                String fileLength = bitmapPath.substring(0, end);
                File filePath = new File(fileLength);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                saveBitmap.createNewFile();

                os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

                //刷新手机图库(没用)
                MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(activity, "保存失败，请重新保存", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存bitmap到一张图片里，并返回图片的绝对路径
     */
    static String saveBitMapToPic(Bitmap bitmap) {
        String bitmapPath = "";
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
//            图片存储地址
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/hiyun/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            bitmapPath = path + System.currentTimeMillis() + ".jpg";
            File saveBitmap = new File(bitmapPath);

            BufferedOutputStream os = null;
            try {
                int end = bitmapPath.lastIndexOf(File.separator);
                String fileLength = bitmapPath.substring(0, end);
                File filePath = new File(fileLength);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                saveBitmap.createNewFile();
                os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                //刷新手机图库(没用)
//                MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
        }
        return bitmapPath;
    }

    /**
     * 保存bitmap数组图片
     */
    static void saveBitmapArray(Activity activity, ArrayList<Bitmap> bitmapArrayList) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            for (int i = 0; i < bitmapArrayList.size(); i++) {
                Time time = new Time("GMT+8");
                time.setToNow();
                int year = time.year;
                int month = time.month + 1;
                int day = time.monthDay;
                int hour = time.hour + 8;
                int minute = time.minute;
                int sec = time.second;
//            图片存储地址
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zkq/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String bitmapPath = path + "/" + year + month + day + hour + minute + sec + ".png";
                File saveBitmap = new File(bitmapPath);

                BufferedOutputStream os = null;
                try {
                    int end = bitmapPath.lastIndexOf(File.separator);
                    String fileLength = bitmapPath.substring(0, end);
                    File filePath = new File(fileLength);
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    saveBitmap.createNewFile();

                    os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                    bitmapArrayList.get(i).compress(Bitmap.CompressFormat.PNG, 100, os);
                    //刷新手机图库(没用)
                    MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "保存失败，请重新保存", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存bitmap数组图片到相机
     */
    static void saveBitmapArrayList(Activity activity, Map<Integer, Bitmap> bitmapMap) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            for (int i = 0; i < bitmapMap.size(); i++) {
                Time time = new Time("GMT+8");
                time.setToNow();
                int year = time.year;
                int month = time.month + 1;
                int day = time.monthDay;
                int hour = time.hour + 8;
                int minute = time.minute;
                int sec = time.second;
                //图片存储地址
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/hiyun/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String bitmapPath = path + year + month + day + hour + minute + sec + ".png";
                File saveBitmap = new File(bitmapPath);

                BufferedOutputStream os = null;
                try {
                    int end = bitmapPath.lastIndexOf(File.separator);
                    String fileLength = bitmapPath.substring(0, end);
                    File filePath = new File(fileLength);
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    saveBitmap.createNewFile();

                    os = new BufferedOutputStream(new FileOutputStream(saveBitmap));
                    bitmapMap.get(i).compress(Bitmap.CompressFormat.PNG, 100, os);
                    //刷新手机图库(没用)
                    MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "保存失败，请重新保存", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拼接view与sufview bitmap并保存为图片存储，返回路径
     * */
    static String cropVideoImage(TextureView textureView, View layoutView, WindowManager windowManager
            , int topY, int paddingLeft) {
        if (textureView == null || layoutView == null) {
            return null;
        }
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/zkq/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File imgFile = new File(filePath, System.currentTimeMillis() + ".png");
        Bitmap subViewBitmap = textureView.getBitmap();
        layoutView.setDrawingCacheEnabled(true);
//        Bitmap layout = layoutView.getDrawingCache();
        Bitmap layout = convertViewToBitmap(layoutView);
        Bitmap screenshot = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);

        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);

        //拼接
        Canvas canvas = new Canvas(screenshot);
        canvas.drawBitmap(subViewBitmap, paddingLeft, topY, new Paint());
        canvas.drawBitmap(layout, 0, 0, new Paint());
        canvas.save();
        canvas.restore();

        OutputStream fout = null;
        try {
            fout = new FileOutputStream(imgFile);
            screenshot.compress(Bitmap.CompressFormat.PNG, 70, fout);
            fout.flush();
            fout.close();
            return imgFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            Log.d("com.sscf.investment", "FileNotFoundException");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d("com.sscf.investment", "IOException");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将view的缓存转为bitmap
     * */
    static Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}
