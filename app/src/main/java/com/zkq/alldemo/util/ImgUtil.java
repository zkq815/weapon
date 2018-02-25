package com.zkq.alldemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.MApplication.MyApplication;
import com.zkq.alldemo.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * 图片加载工具
 *
 * @author yc
 * @since 17/1/9
 */
public class ImgUtil {
    private static final String ZKQLOG = "ZKQLog";
    private static final String TAG = ImgUtil.class.getCanonicalName();

    @SuppressLint("StaticFieldLeak")
    private static Picasso sPicasso;

    public static Picasso getPicasso() {
        if (null == sPicasso) {
            synchronized (ImgUtil.class) {
                if (null == sPicasso) {
                    final Context context = MyApplication.getInstance();
                    sPicasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(context)).build();
//                    sPicasso.setIndicatorsEnabled(true);
                }
            }
        }

        return sPicasso;
    }

    /**
     * 退出程序时，shutdown Picasso的实例，优化cpu与内存占用
     */
    public static void destroy() {
        ZKQLog.i(TAG, "destroy");
        ThreadPool.exe(new Runnable() {
            @Override
            public void run() {
                synchronized (ImgUtil.class) {
                    if (null != sPicasso) {
                        try {
                            ZKQLog.i(TAG, "shutdown picasso");
                            sPicasso.shutdown();
                        } catch (Exception e) {
                            if (BuildConfig.LOG_DEBUG) {
                                ZKQLog.t(TAG, "destroy", e);
                            }
                        }
                        sPicasso = null;
                    }
                }
            }
        });
    }

    public static void load(String url, ImageView view) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).fit().centerCrop().placeholder(R.color.grey_placeholder).into(view);
    }

    public static void loadWithNoHolder(String url, ImageView view) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).into(view);
    }

    public static void loadJpgWithCallBack(String url, ImageView view, Callback callback) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).placeholder(R.color.grey_placeholder).into(view, callback);
    }

    public static void load(String url, ImageView view, Transformation transformation) {
        if (filter(url)) {
            return;
        }
        if (transformation != null) {
            getPicasso().load(url).placeholder(R.color.grey_placeholder).transform(transformation).into(view);
        } else {
            getPicasso().load(url).placeholder(R.color.grey_placeholder).into(view);
        }
    }

    public static void loadWithHolderColor(String url, ImageView view, int placeholderResId, Transformation transformation) {
        if (filter(url)) {
            return;
        }
        if (transformation != null) {
            getPicasso().load(url).placeholder(placeholderResId).transform(transformation).into(view);
        } else {
            getPicasso().load(url).placeholder(placeholderResId).into(view);
        }
    }

    public static void fitLoad(final String url, final ImageView imageView) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).fit().placeholder(R.color.grey_placeholder).into(imageView);
    }

    private static boolean filter(final String url) {
        if (url == null || url.trim().length() == 0) {
            return true;
        }

        return false;
    }


    /**
     * 将本地图片路径取出后重新压缩转存为新图片，并返回图片路径
     *
     * @param path       照片路径
     * @param flag       是否压缩
     * @param options    精度
     * @param isTakPhone 是否拍照
     */
    public static String getCondensePath(Context context, String path, boolean flag, int options, boolean isTakPhone) {
        //将保存在本地的图片取出并缩小后显示在界面上
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return getCondense(context, bitmap, flag, options, isTakPhone);
    }

    public static String getCondense(Context context, Bitmap bitmap, boolean flag, int options, boolean isTakPhone) {
        Bitmap topBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_refresh);

        File myCaptureFile = new File(Environment.getExternalStorageDirectory().toString() + "/hiyun", System.currentTimeMillis() + "temp.jpg");
        BufferedOutputStream bos;
        ByteArrayOutputStream baos;
        try {
            baos = new ByteArrayOutputStream();
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            if (flag) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                Log.e("ZKQLOG", "图片大小====" + baos.toByteArray().length / 1024 + "-----kb");
                while (baos.toByteArray().length / 1024 > 2048) {  //循环判断如果压缩后图片是否大于30kb,大于继续压缩
                    baos.reset();//重置baos即清空baos
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                    options -= 30;//每次都减少10
                    Log.e("ZKQLOG", "图片大小  压缩中====" + baos.toByteArray().length / 1024 + "-----kb");
                }
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
                bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

                Log.e("ZKQLOG", "图片大小  压缩后====" + baos.toByteArray().length / 1024 + "-----kb");
                Log.e("ZKQLOG", "bitmap大小 压缩后  ====" + bitmap.getByteCount() / 1024 + "-----单位未知");

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
                ZKQLog.e("ZKQLOG", "bitmap大小 保存后  ====" + bitmap.getByteCount() / 1024 + "-----单位未知");
////=======================================上面是需要压缩的处理方法===========================================
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String bitmapPath = "";
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
//            图片存储地址
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
        int degree = readPictureDegree(bitmapPath, isTakPhone);
        ZKQLog.e("ZKQLOG", "旋转角度====" + degree);
        //将压缩后的图片转换为bitmap
        Bitmap tempBitmap = BitmapFactory.decodeFile(bitmapPath);
        //获取图片旋转后的bitmap
        Bitmap lastBitmap = rotaingImageView(degree, tempBitmap);

        bitmap = watermarkBitmap(lastBitmap, topBitmap, null);

        //获取图片旋转后保存图片的路径
        String lastPicPath = saveBitMapToPic(bitmap);
//        return bitmapPath;
        return lastPicPath;
    }

    /**
     * 水印添加
     * */
    private static Bitmap watermarkBitmap(Bitmap bottomBitmap, Bitmap topBitmap, String title) {
        if (bottomBitmap == null) {
            return null;
        }

        int w = bottomBitmap.getWidth();
        int h = bottomBitmap.getHeight();
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(bottomBitmap, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint = new Paint();
        //加入图片
        if (topBitmap != null) {
            int ww = topBitmap.getWidth();
            int wh = topBitmap.getHeight();
//            paint.setAlpha(50);
            cv.drawBitmap(topBitmap, w - ww, h - wh, paint);// 在src的右下角画入水印
        }
        //加入文字
        if (title != null) {
            String familyName = "宋体";
            Typeface font = Typeface.create(familyName, Typeface.BOLD);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(22);
            //这里是自动换行的
            StaticLayout layout = new StaticLayout(title, textPaint, w, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            layout.draw(cv);
            //文字就加左上角算了
            //cv.drawText(title,0,40,paint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newb;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path, boolean isTakPhone) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String phoneName = android.os.Build.MODEL;
        ZKQLog.e("ZKQLOG", "手机品牌型号===" + phoneName);
        if (isTakPhone) {
            if (phoneName.contains("HM")//红米2a（HM 2a）
                    || phoneName.contains("SM")//三星S6 SM-G920P
                    ) {
                degree += 90;
            } else if (phoneName.equals("m2")//魅族 M2
                    || phoneName.contains("HUAWEI")//华为P7 = HUAWEI P7-L07
                    || phoneName.contains("OPPO")//OPPO R7
                    || phoneName.contains("NX508J")//努比亚Z9 =  NX508J
                    || phoneName.contains("NX511J")//努比亚Z9mini = NX511J
                    || phoneName.contains("vivo X5S L")//vivo X5S L
                    ) {
                return degree;
            } else {
                return degree;
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 封装Gilde图片加载
     * 优点：加载速度快
     * 弊端：偶尔图片会出现加载出来后模糊,图片加载后内存回收严重，导致多网络图片加载后上滑时出现屏幕跳跃、卡顿等问题
     */
    public static void gildeLoadPic(Context context, String imageUrl, ImageView imageView) {
//        Glide.with(context)
//                .load(imageUrl)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .dontAnimate()
//                .into(imageView);
    }

    /**
     * 封装Picasso图片加载
     * 需导入：compile 'com.squareup.picasso:picasso:2.5.2'  //picasso 图片加载框架
     * 优点：加载速度快，电商首页动态配置目前没发现卡顿、屏幕跳跃等问题，也没有发生OOM
     * 缺点：缓存图片较大
     */
    public static void picassoLoadPic(Context context,String imageUrl,ImageView imageView) {
        Picasso.with(context)
                .load(imageUrl)
//                .resize(dp2px(250),dp2px(250))
//                .centerCrop()
                .config(Bitmap.Config.RGB_565)

                .into(imageView);
    }

    /**
     * 保存bitmap到一张图片里，并返回图片的绝对路径
     */
    public static String saveBitMapToPic(Bitmap bitmap) {
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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();
                ZKQLog.e("ZKQLog","存放图片==="+bitmapPath);

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
//            Toast.makeText(activity, "SD卡未检测到，请检查是否存在", Toast.LENGTH_SHORT).show();
        }
        return bitmapPath;
    }

    /**
     * 保存bitmap数组图片
     */
    public static void saveBitmapArray(Activity activity, ArrayList<Bitmap> bitmapArrayList) {

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
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hiyun/";
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();

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
    public static void saveBitmapArrayList(Activity activity, Map<Integer, Bitmap> bitmapMap) {

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
//            图片存储地址
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/hiyun/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                Log.e("",year + " " + month + " " + day + " " + hour + " " + minute + " " + sec);
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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();

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
     * 保存bitmap图片（ 单独一张） 并返回当前的图片路径
     */
    public static String saveBitmap(Activity activity, Bitmap bitmap, String picName, Handler handler) {

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
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hiyun/";
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(0);
                //刷新手机图库(没用)
                MediaScannerConnection.scanFile(activity, new String[]{bitmapPath}, null, null);
                return bitmapPath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                Toast.makeText(activity, "保存失败，请重新保存", Toast.LENGTH_SHORT).show();
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
    public static String saveBitmapForWXShare(Activity activity, Bitmap bitmap) {

        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            //图片重新命名
            Time time = new Time("GMT+8");
            time.setToNow();
//            图片存储地址
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hiyun/";
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
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
     * */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
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
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,	Uri.fromFile(new File(file.getPath()))));
        Toast.makeText(context,"图片保存成功",Toast.LENGTH_LONG).show();
    }

    /**
     * 保存bitmap图片（ 单独一张） 到相机
     */
    public static void saveBitmap(Activity activity, Bitmap bitmap) {

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

//                Toast.makeText(activity, "保存成功，存储路径为：" + bitmapPath, Toast.LENGTH_SHORT).show();

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
     * bitmap 压缩
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        ZKQLog.e("ZKQLOG", "图片大小====" + baos.toByteArray().length / 1024 + "-----kb");
        while (baos.toByteArray().length / 1024 > 30) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
     */
    public static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
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
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;

            } catch (Exception e) {
                ZKQLog.d(e.toString());
            }

            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    /**
     * 获取网络图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 图片上传至阿里云
     * */
    public static void asyncPutObjectFromLocalFile(Context context, final String uploadFilePath, final Bitmap bitmap,final Handler handler) {
//        final HashMap <String,Object> map = new HashMap<String,Object>();
//        Time time = new Time("GMT+8");
//        time.setToNow();
//        int year = time.year;
//        int month = time.month + 1;
//        int day = time.monthDay;
//        int hour = time.hour + 8;
//        int minute = time.minute;
//        int sec = time.second;
//
//        String uploadPicPath = "Android-"+year + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day) +
//                (hour < 10 ? "0" + hour : hour) + (minute < 10 ? "0" + minute : minute) + (sec < 10 ? "0" + sec : sec) + ".png";
//
//        OSS oss;
//        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("scl16iPO2OUD1goj", "1J9wWa1ZSVzZ6pSFZ6nTGVhT8BvjG9");
//
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog();
//
////        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
////        String testBucket = "<bucket_name>";
//        String testBucket = "haiynoss";
//        final String uploadObject = "member/"+uploadPicPath;
//        String downloadObject = "sampleObject";
//
//        oss = new OSSClient(context, endpoint, credentialProvider, conf);
//
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject, uploadFilePath);
//
//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//            }
//        });
//
//        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                Log.d("PutObject", "UploadSuccess");
//
//                Log.d("ETag", result.getETag());
//                Log.d("RequestId", result.getRequestId());
//                e("ZKQLOG","阿里云OSS上传成功，图片本地地址=="+uploadFilePath);
//                map.put("image_url","http://haiynoss.oss-cn-hangzhou.aliyuncs.com/"+uploadObject);
//                map.put("image_bitmap",bitmap);
//                handler.obtainMessage(0,map).sendToTarget();
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                    e("ZKQLOG","失败");
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//                map.clear();
//                handler.obtainMessage(0,map).sendToTarget();
//            }
//        });
    }

    //把一个url的网络图片变成一个本地的BitMap
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p/>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    private static int IO_BUFFER_SIZE = 2 * 1024;

    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}
