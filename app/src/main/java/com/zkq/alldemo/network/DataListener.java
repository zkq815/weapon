package com.zkq.alldemo.network;

import android.support.annotation.NonNull;

import com.zkq.alldemo.network.exception.NetworkException;


/**
 * @author small_plane
 * @since 16/7/22
 */
public interface DataListener<T> {

    void onDataReceive(@NonNull final T data);

    void onErrorHappened(@NonNull NetworkException error);
}
