package com.zkq.weapon.market.glide.strategy;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author zkq
 * create:2018/12/11 9:55 AM
 * email:zkq815@126.com
 * desc: 对request的管理策略
 */
public interface IRequestStrategy {

    /**
     * 暂停请求
     */
    void pauseRequests(@NonNull Context context);

    /**
     * 恢复请求
     */
    void resumeRequests(@NonNull Context context);

}
