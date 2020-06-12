package com.zkq.weapon.market.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.zkq.weapon.market.sharedpreferences.PreferenceKey;
import com.zkq.weapon.market.sharedpreferences.PreferenceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author:zkq
 * create:2018/10/24 上午11:48
 * email:zkq815@126.com
 * desc: GUID工具类
 */
public class ToolGUID {

    private static String sID;
    private static final String PATH_GUID = ".meizu_store_guid";
    static final String ENCODING_UTF8 = "UTF-8";

    static {
        sID = PreferenceUtil.getString(PreferenceKey.GUID, null);
    }

    private static class UUID {

        private static String uuid = null;

        private static String getUUID() {
            if (null == uuid) {
                uuid = java.util.UUID.randomUUID().toString();
            }

            return uuid;
        }

    }

    private static String getIdFromFile() {
        String id = null;

        try {
            final File file = new File(Environment.getExternalStorageDirectory(), PATH_GUID);

            if (file.exists() && !file.isFile()) {
                final boolean ret = file.delete();
                if (!ret) {
                    return null;
                }
            }

            if (file.exists()) {
                id = read(file);
            } else {
                write(file, UUID.getUUID());
                id = UUID.getUUID();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public synchronized static String id(final Context context) {
        if (null != sID) {
            return sID;
        }
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            sID = getIdFromFile();
        }

        if (TextUtils.isEmpty(sID)) {
            sID = UUID.getUUID();
        }

        PreferenceUtil.putString(PreferenceKey.GUID, sID);

        return sID;
    }

    private static String read(@NonNull final File file) throws IOException {
        try (final RandomAccessFile f = new RandomAccessFile(file, "r")) {
            final byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            return new String(bytes, ENCODING_UTF8);
        }
    }

    private static void write(@NonNull final File file, @NonNull final String guid) throws IOException {
        try (final FileOutputStream out = new FileOutputStream(file)) {
            out.write(guid.getBytes(ENCODING_UTF8));
        }
    }
}
