package com.zkq.weapon.networkframe.listener;

import android.support.annotation.NonNull;
import com.zkq.weapon.networkframe.exception.CustomException;
import java.lang.ref.WeakReference;

/**
 * @author zkq
 * create:2018/11/16 10:41 AM
 * email:zkq815@126.com
 * desc: 软引用
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

    public abstract void onError(@NonNull final R r, @NonNull CustomException error);

    /**
     * error with R recycled
     */
    @SuppressWarnings("WeakerAccess")
    protected void onError(@NonNull CustomException error) {
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
    public final void onErrorHappened(@NonNull CustomException error) {
        if (ref.get() != null) {
            onError(ref.get(), error);
        } else {
            onError(error);
        }
    }
}
