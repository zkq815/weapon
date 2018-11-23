package com.zkq.weapon.networkframe.listener;

import android.support.annotation.NonNull;

import com.zkq.weapon.networkframe.exception.CustomException;

/**
 * @author zkq
 * create:2018/11/16 10:39 AM
 * email:zkq815@126.com
 * desc:
 */
public interface DataListener<T> {

    void onDataReceive(@NonNull final T data);

    void onErrorHappened(@NonNull CustomException error);
}
