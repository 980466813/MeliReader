package com.lning.melireader.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lning.melireader.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/19.
 */

public class RecommendListSubTitleProvider
        extends ItemViewBinder<String, RecommendListSubTitleProvider.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_list_subtitle_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item) {
        if (!TextUtils.isEmpty(item)) {
            holder.adapterRecommendSubTitleTv.setText(item + "");
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView adapterRecommendSubTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterRecommendSubTitleTv = itemView.findViewById(R.id.adapter_recommend_subtitle_tv);
        }
    }
}
