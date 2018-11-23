package com.zkq.weapon.networkframe.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zkq
 * create:2018/11/16 10:43 AM
 * email:zkq815@126.com
 * desc: 解析response基础bean
 */
public class BaseBean implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel source) {
            return new BaseBean(source);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };
}
