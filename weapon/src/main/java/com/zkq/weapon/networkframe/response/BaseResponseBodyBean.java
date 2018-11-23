package com.zkq.weapon.networkframe.response;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * @author zkq
 * create:2018/11/16 10:43 AM
 * email:zkq815@126.com
 * desc:
 */
public class BaseResponseBodyBean extends ResponseBody {

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
