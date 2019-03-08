package com.lning.melireader.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import android.content.res.Resources;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/13.
 */

public class HistoryListItemProvider extends ItemViewBinder<HistoryVo, HistoryListItemProvider.ViewHolder> {


    private final OnItemClickListener mListener;

    public HistoryListItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.adapter_news_single_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final HistoryVo item) {
        Resources resources = holder.itemView.getResources();
        if (!TextUtils.isEmpty(item.getImage())) {
            holder.adapterNewsIsVideoIv.setVisibility("video".equals(item.getCtype()) ? View.VISIBLE : View.GONE);
            String[] images = item.getImage().split(",");
            holder.adapterNewsImageNumTv.setText(resources.getString(R.string.tips_image_num_template, images.length));
            GlideUtils.loadImage(holder.adapterNewsImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
        } else {
            holder.adapterNewsImageNumTv.setVisibility(View.GONE);
            holder.adapterNewsImageIv.setVisibility(View.GONE);
            holder.adapterNewsIsVideoIv.setVisibility(View.GONE);
        }
        holder.adapterNewsSingleTitleTv.setText(item.getTitle());
        holder.adapterNewsSingleAuthorTv.setText(item.getPublisherName());
        holder.adapterNewsSingleCommentTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterNewsSingleDateTv.setText(CommonUtils.formatPublishDate(item.getPublishDate()));
        RxView.clicks(holder.itemView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, item.getNewsListVo(), "video".equals(item.getCtype()) ? 3 : "picture".equals(item.getCtype()) ? 2 : 1);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView adapterNewsSingleTitleTv;
        AppCompatTextView adapterNewsSingleAuthorTv;
        AppCompatTextView adapterNewsSingleCommentTv;
        AppCompatTextView adapterNewsSingleDateTv;

        AppCompatImageView adapterNewsImageIv;
        AppCompatImageView adapterNewsIsVideoIv;
        AppCompatTextView adapterNewsImageNumTv;


        ViewHolder(View itemView) {
            super(itemView);
            adapterNewsSingleTitleTv = itemView.findViewById(R.id.adapter_news_single_title_tv);
            adapterNewsSingleAuthorTv = itemView.findViewById(R.id.adapter_news_single_author_tv);
            adapterNewsSingleCommentTv = itemView.findViewById(R.id.adapter_news_single_comment_tv);
            adapterNewsSingleDateTv = itemView.findViewById(R.id.adapter_news_single_date_tv);
            adapterNewsImageIv = itemView.findViewById(R.id.adapter_news_single_image_iv);
            adapterNewsIsVideoIv = itemView.findViewById(R.id.adapter_news_single_isvideo_iv);
            adapterNewsImageNumTv = itemView.findViewById(R.id.adapter_news_single_image_num_tv);
        }
    }
}
