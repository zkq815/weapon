package com.zkq.weapon.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.zkq.weapon.R;
import com.zkq.weapon.customview.CameraPreview;
import com.zkq.weapon.customview.CustomPhotoCoverView;
import com.zkq.weapon.market.tools.ToolBitmap;
import com.zkq.weapon.market.tools.ToolFile;
import com.zkq.weapon.market.tools.ToolScreen;
import com.zkq.weapon.market.tools.ToolSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zkq
 * create:2020/6/17 5:15 PM
 * email:zkq815@126.com
 * desc: 自定义拍照页面
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener{
    ImageView ivBack;
    private CameraPreview cameraPreview;
    private CustomPhotoCoverView coverView;
    private Button btnTakePhoto;
    private Camera.PictureCallback photo;
    static int PHONE_PHOTO_REQUEST_CODE = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_custom_camera);
        initView();
    }

    private void initView(){
        ivBack = findViewById(R.id.iv_back);
        cameraPreview = findViewById(R.id.camera_preview);
        coverView = findViewById(R.id.cover_view);
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        // 设置显示图片
        photo = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                camera.startPreview();
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                bm = ToolBitmap.rotaingImageView(90, bm);
                int screenWidth = ToolScreen.getScreenWidth(getBaseContext());
                int screenHeight = ToolScreen.getScreenHeight(getBaseContext());
                int width = screenWidth - ToolSize.dip2px(getBaseContext(), 90);
                int leftTopX = ToolSize.dip2px(getBaseContext(), 45);
                int leftTopY = (screenHeight - ToolSize.dip2px(getBaseContext(), 90))/3;

                //比例换算
                int leftTopXAfter = (int)((float)leftTopX/screenWidth * bm.getWidth());
                int leftTopYAfter = (int)((float)leftTopY/screenHeight * bm.getHeight());
                int needBitmapWidth = (int)((float)width/screenWidth * (bm.getHeight() > bm.getWidth() ? bm.getWidth() : bm.getHeight()));
                if(leftTopXAfter + needBitmapWidth > bm.getWidth()){
                    needBitmapWidth = bm.getWidth() - leftTopXAfter;
                }

                if(leftTopYAfter + needBitmapWidth > bm.getHeight()){
                    needBitmapWidth = bm.getHeight() - leftTopYAfter;
                }

                Bitmap bitmap = Bitmap.createBitmap(bm,leftTopXAfter,leftTopYAfter,needBitmapWidth,needBitmapWidth);
//                saveImageToPhone(getBaseContext(), bitmap);
                ToolFile.saveBitmap(CameraActivity.this, bitmap);
            }
        };
        ivBack.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_take_photo) {
            cameraPreview.setCameraPicListener(photo);
        }else if(view.getId() == R.id.iv_back){
            setRequestIntent("");
        }
    }

    private void saveImageToPhone(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "zkq");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "user_sign.jpg";
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
        setRequestIntent(file.getPath());
    }

    @Override
    protected void onPause() {
        super.onPause();
        setRequestIntent("");
    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraPreview.releaseCamera();
    }

    @Override
    public void onBackPressed() {
        setRequestIntent("");
        super.onBackPressed();
    }

    private void setRequestIntent(String filePath){
        Intent intent = new Intent();
        intent.putExtra("webPicUrl", filePath);
        setResult(PHONE_PHOTO_REQUEST_CODE, intent);
        finish();
    }
}
