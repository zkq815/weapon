package com.zkq.weapon.market.tools;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * @author zkq
 * time: 2018/12/11:16:07
 * email: zkq815@126.com
 * desc:
 */
public interface ToolFinger {

    static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        try {
            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        } catch (Throwable e) {
        }
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManager;
    }

    static FingerprintManagerCompat getFingerCompat(Context context) {

        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManagerCompat;
    }
}