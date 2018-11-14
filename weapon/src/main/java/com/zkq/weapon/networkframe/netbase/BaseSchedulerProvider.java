package com.zkq.weapon.networkframe.netbase;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * @author: zkq
 * time: 2018/11/14:21:13
 * email: zkq815@126.com
 * desc:
 */
public interface BaseSchedulerProvider {
    Scheduler computation();
    Scheduler io();
    Scheduler ui();
    <T> ObservableTransformer<T, T> applySchedulers();
}
