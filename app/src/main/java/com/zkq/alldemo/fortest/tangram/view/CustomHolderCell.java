/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zkq.alldemo.fortest.tangram.view;

import androidx.annotation.NonNull;
import android.widget.TextView;

import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.ExposureSupport;

import java.util.Locale;

/**
 * Created by SunQiang
 */
public class CustomHolderCell extends BaseCell<TextView> {

    @Override
    public void bindView(@NonNull TextView view) {
        view.setBackgroundColor(0xff00c3f5);
//        if (pos % 2 == 0) {
//            view.setBackgroundColor(0xff000fff);
//        } else {
//            view.setBackgroundColor(0xfffff000);
//        }
//        view.setText(String.format(Locale.CHINA, "%s%d: %s", getClass().getSimpleName(), pos,
//                optParam("text")));
        view.setOnClickListener(this);
        if (serviceManager != null) {
            ExposureSupport exposureSupport = serviceManager.getService(ExposureSupport.class);
            if (exposureSupport != null) {
                exposureSupport.onTrace(view, this, type);
            }
        }
    }
}
