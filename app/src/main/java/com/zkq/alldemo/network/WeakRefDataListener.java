package com.zkq.alldemo.network;

import android.support.annotation.NonNull;


import com.zkq.alldemo.network.exception.NetworkException;

import java.lang.ref.WeakReference;

/**
 * @author yc
 * @since 2017/10/10
 */
public abstract class WeakRefDataListener<R, T> implements DataListener<T> {

    private final WeakReference<R> ref;

    public WeakRefDataListener(final R r) {
        ref = new WeakReference<>(r);
    }

    protected R get() {
        return ref.get();
    }

    protected abstract void onSuccess(@NonNull final R r, @NonNull T data);

    /**
     * success with R recycled
     */
    @SuppressWarnings("WeakerAccess")
    protected void onSuccess(@NonNull T data) {
    }

    public abstract void onError(@NonNull final R r, @NonNull NetworkException error);

    /**
     * error with R recycled
     */
    @SuppressWarnings("WeakerAccess")
    protected void onError(@NonNull NetworkException error) {
    }

    @Override
    public final void onDataReceive(@NonNull T data) {
        if (ref.get() != null) {
            onSuccess(ref.get(), data);
        } else {
            onSuccess(data);
        }
    }

    @Override
    public final void onErrorHappened(@NonNull NetworkException error) {
        if (ref.get() != null) {
            onError(ref.get(), error);
        } else {
            onError(error);
        }
    }
}
