package com.zkq.weapon.networkframe.response;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by zkq
 * on 2018/8/24.
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
