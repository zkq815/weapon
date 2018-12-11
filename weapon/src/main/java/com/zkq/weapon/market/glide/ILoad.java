package com.zkq.weapon.market.glide;

import com.zkq.weapon.market.glide.config.LoaderConfig;
import com.zkq.weapon.market.glide.strategy.ICacheStrategy;
import com.zkq.weapon.market.glide.strategy.ILoadStrategy;
import com.zkq.weapon.market.glide.strategy.IRequestStrategy;

/**
 * @author zkq
 * create:2018/12/11 9:56 AM
 * email:zkq815@126.com
 * desc: 封装三方图片框架的实现类，正在调用三方图片框架去加载，除了ICacheStrategy, ILoadStrategy
 */
public interface ILoad<T extends LoaderConfig> extends ICacheStrategy, ILoadStrategy<T>, IRequestStrategy {

}
