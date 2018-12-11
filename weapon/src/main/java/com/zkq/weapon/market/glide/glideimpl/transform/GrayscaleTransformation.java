package com.zkq.weapon.market.glide.glideimpl.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;

/**
 * @author zkq
 * create:2018/12/11 9:53 AM
 * email:zkq815@126.com
 * desc: Bitmap 灰度裁剪处理
 */
public class GrayscaleTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID = GrayscaleTransformation.class.getCanonicalName() + VERSION;

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform
            , int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        Canvas canvas = new Canvas(bitmap);
        ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturation));
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return bitmap;
    }

    @Override
    public String toString() {
        return "GrayscaleTransformation()";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GrayscaleTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }
}
