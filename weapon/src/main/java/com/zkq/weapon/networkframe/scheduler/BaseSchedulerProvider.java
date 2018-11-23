package com.zkq.weapon.networkframe.scheduler;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * @author zkq
 * time: 2018/11/16:10:54
 * email: zkq815@126.com
 * desc:
 */
public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
