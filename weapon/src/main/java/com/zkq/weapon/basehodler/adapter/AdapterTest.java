package com.zkq.weapon.basehodler.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.zkq.weapon.R;
import com.zkq.weapon.basehodler.holder.BaseNativeEdtionHolder;
import com.zkq.weapon.basehodler.holder.DefaultEdtionHolder;
import com.zkq.weapon.basehodler.operation.BaseEdtionOperationModel;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * time: 2019/5/29 12:27 AM
 * email: zkq815@126.com
 * desc:
 */
public class AdapterTest extends AdapterRecyclerBase<BaseNativeEdtionHolder
        , BaseEdtionOperationModel> {
    public AdapterTest(Context context, List<BaseEdtionOperationModel> list){
        super(context,list);
    }

    @NonNull
    @Override
    public BaseNativeEdtionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (BaseEdtionOperationModel model : getList()) {
            if (viewType == model.getEdtionModule().getViewType()) {
                return model.createEdtionHolder();
            }
        }

        return new DefaultEdtionHolder(getContext()
                , getLayoutInflater().inflate(R.layout.layout_edtion_defalut
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNativeEdtionHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.showViews(getList().get(position).getEdtionModule());
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return 1;
    }
}
