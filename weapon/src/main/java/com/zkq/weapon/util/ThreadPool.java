package com.zkq.weapon.util;

import com.zkq.weapon.BuildConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具
 * Created by yc on 2016/12/6.
 */
public class ThreadPool {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2;

    private volatile static ThreadPoolExecutor executor = null;

    private static ThreadPoolExecutor getExecutor() {
        if (null == executor || executor.isShutdown()) {
            synchronized (ThreadPool.class) {
                if (null == executor || executor.isShutdown()) {
                    executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(30), new RejectedExecutionHandler() {

                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            if (BuildConfig.LOG_DEBUG) {
                                ZKQLog.e("discard " + r);
                            }
                        }
                    });
                }
            }
        }

        return executor;
    }

    public static void exe(final Runnable r) {
        getExecutor().execute(r);
    }

}
