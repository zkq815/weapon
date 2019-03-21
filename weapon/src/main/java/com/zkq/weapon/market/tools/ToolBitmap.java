package com.zkq.weapon.market.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zkq
 * create:2018/12/11 9:58 AM
 * email:zkq815@126.com
 * desc: Bitmap工具类
 */
public interface ToolBitmap {
    int IO_BUFFER_SIZE = 2 * 1024;
    /**
     * 清除回收图片
     *
     * @param bitmaps 图片集合
     */
    static void recycleBitmaps(@Nullable Bitmap... bitmaps) {
        if (bitmaps != null) {
            for (Bitmap bitmap : bitmaps) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**
     * 根据角度，旋转图片 <br/>
     *
     * @param degrees 旋转角度
     * @param bitmap  原始图片
     * @return 旋转后的图片。如果传入的原图为空，则返回空
     */
    @Nullable
    static Bitmap rotatingBitmap(@FloatRange(from = 0.0f, to = 360.0f) float degrees, @Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        //旋转角度为0或者负数时，不进行旋转
        if (degrees <= 0) {
            return bitmap;
        }

        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
                    , matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        if (returnBm == null) {
            return bitmap;
        }

        return returnBm;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param filePath 图片绝对路径
     * @return degree旋转的角度, 默认返回'0'
     */
    static int readPictureDegree(@Nullable String filePath) {
        int degree = 0;
        if (TextUtils.isEmpty(filePath)) {
            return degree;
        }

        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION
                    , ExifInterface.ORIENTATION_NORMAL);
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
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param bitmap Bitmap
     * @return 获取bitmap的config, 为空则返回ARGB.8888
     */
    @NonNull
    static Bitmap.Config getBitmapConfig(@Nullable Bitmap bitmap) {
        return (bitmap != null && bitmap.getConfig() != null) ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    /**
     * 图片灰处理 <br/>
     * 以原始图片为模板重新绘制图片，绘制中加入灰色蒙板<br/>
     *
     * @param originalBitmap 原始图片
     * @param greyBitmap     被绘制上灰色的图片
     * @return 灰处理后的图片
     */
    @Nullable
    static Bitmap changBitmapToGrey(@Nullable Bitmap originalBitmap, @Nullable Bitmap greyBitmap) {
        if (originalBitmap == null) {
            return null;
        }
        //创建承载灰色蒙板的图片实例
        Bitmap changeGreyBitmap = greyBitmap;
        if (changeGreyBitmap == null) {
            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            changeGreyBitmap = Bitmap.createBitmap(width, height, getBitmapConfig(originalBitmap));
        }

        Canvas canvas = new Canvas(changeGreyBitmap);
        Paint paint = new Paint();
        //创建颜色变换矩阵
        ColorMatrix colorMatrix = new ColorMatrix();
        //设置灰度影响范围
        colorMatrix.setSaturation(0);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        //设置画笔的颜色过滤矩阵
        paint.setColorFilter(colorMatrixFilter);
        //使用处理后的画笔绘制图像
        canvas.drawBitmap(originalBitmap, 0, 0, paint);
        return changeGreyBitmap;
    }

    /**
     * 根据Config 获取压缩后的图片 <br/>
     * PS:同时回收原始图片，即recycler掉
     *
     * @param bitmap  原图
     * @param config  {@link Bitmap.Config}
     * @param quality 压缩值，范围在0-100数值
     * @return 压缩后的图片，图片格式为JPEG
     */
    @Nullable
    static Bitmap getCompressBitmapByConfigAndQuality(@Nullable Bitmap bitmap, @NonNull Bitmap.Config config
            , @IntRange(from = 0, to = 100) int quality) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = config;
        Bitmap outBm = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length
                , options);
        recycleBitmaps(bitmap);
        return outBm;
    }

    /**
     * 获取压缩后的图片 <br/>
     * 由于创建图片时Options的inSampleSize参数为int类型，压缩后无法真正达到目标所希望的图片大小，只能是接近目标宽高值
     *
     * @param filePath 图片文件地址
     * @param config   {@link Bitmap.Config}
     * @param destW    压缩后的目标宽度
     * @param destH    压缩后的目标高度
     * @param quality  压缩值，范围在0-100数值
     * @return 压缩后的图片
     */
    @Nullable
    static Bitmap getCompressBitmap(@Nullable String filePath, @NonNull Bitmap.Config config, int destW
            , int destH, @IntRange(from = 0, to = 100) int quality) {
        if (TextUtils.isEmpty(filePath) || destH <= 0 || destW <= 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        // 压缩宽高比例
        float blW = (float) options.outWidth / destW;
        float blH = (float) options.outHeight / destH;
        options.inSampleSize = 1;
        if (blW > 1 || blH > 1) {
            float bl = (blW > blH ? blW : blH);
            // 尽量不失真
            options.inSampleSize = (int) (bl + 0.9f);
        }

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        //需要再次压缩图片质量时
        if (quality != 100) {
            bitmap = getCompressBitmapByConfigAndQuality(bitmap, config, quality);
        }
        //处理拍照角度旋转的问题
        int degree = readPictureDegree(filePath);
        return degree == 0 ? bitmap : rotatingBitmap(degree, bitmap);
    }

    /**
     * 获取网络图片资源
     *
     * @param url
     * @return
     */
    static Bitmap getHttpBitmap(String url) {
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

//        URL myFileUrl = null;
//        Bitmap bitmap = null;
//        try {
//            myFileUrl = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 水印添加
     * */
    static Bitmap watermarkBitmap(Bitmap bottomBitmap, Bitmap topBitmap, String title) {
        if (bottomBitmap == null) {
            return null;
        }

        int w = bottomBitmap.getWidth();
        int h = bottomBitmap.getHeight();
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        // 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        // 在 0，0坐标开始画入src
        cv.drawBitmap(bottomBitmap, 0, 0, null);
        Paint paint = new Paint();
        //加入图片
        if (topBitmap != null) {
            int ww = topBitmap.getWidth();
            int wh = topBitmap.getHeight();
            // 在src的右下角画入水印
            cv.drawBitmap(topBitmap, w - ww, h - wh, paint);
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
        // 保存
//        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.save();
        // 存储
        cv.restore();
        return newb;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    static int readPictureDegree(String path, boolean isTakPhone) {
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
        if (isTakPhone) {
            //红米2a（HM 2a）
            //三星S6 SM-G920P
            if (phoneName.contains("HM") || phoneName.contains("SM")) {
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
     * bitmap 压缩
     */
    static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 30) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            options -= 10;
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    static Bitmap PicZoom(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
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
    static Bitmap getLocalOrNetBitmap(String url) {

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
        }finally {
            try {
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}
