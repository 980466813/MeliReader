package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.ScreenUtils;
import com.lning.melireader.ui.provider.bean.CollectionPro;

import java.util.concurrent.TimeUnit;

import am.widget.wraplayout.WrapLayout;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/16.
 */

public class CollectionListItemProvider extends ItemViewBinder<CollectionPro, CollectionListItemProvider.ViewHolder> {
    private final OnItemClickListener mListener;

    public CollectionListItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_collection_news_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull CollectionPro item) {
        final CollectionVo collectionVo = item.getCollectionVo();
        Resources resources = holder.itemView.getResources();
        String image = collectionVo.getImage();
        if (!TextUtils.isEmpty(image)) {
            String[] images = image.split(",");
            holder.adapterNewsImageNumTv.setText(resources.getString(R.string.tips_image_num_template, images.length));
            for (String str : images) {
                if (!str.endsWith(".gif")) {
                    GlideUtils.loadImage(holder.adapterNewsImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
                    break;
                }
            }
            holder.adapterNewsIsVideoIv.setVisibility("video".equals(collectionVo.getCtype()) ? View.VISIBLE : View.GONE);
        } else {
            holder.adapterNewsImageNumTv.setVisibility(View.GONE);
            holder.adapterNewsIsVideoIv.setVisibility(View.GONE);
            holder.adapterNewsImageIv.setVisibility(View.GONE);
        }
        holder.adapterNewsTitleTv.setText(collectionVo.getTitle());
        holder.adapterNewsDateTv.setText(CommonUtils.formatPublishDate(collectionVo.getCreated()));
        if (!TextUtils.isEmpty(collectionVo.getTag())) {
            holder.adapterCollectionTagWl.removeAllViews();
            holder.adapterTagContainerCl.setVisibility(View.VISIBLE);
            String[] tags = collectionVo.getTag().split(",");
            for (String tag : tags) {
                holder.adapterCollectionTagWl.addView(makeTagView(holder.itemView, tag));
            }
        }
        holder.adapterNewsContainerCl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(view, collectionVo, holder.getAdapterPosition());
                }
                return false;
            }
        });
        RxView.clicks(holder.adapterNewsContainerCl).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mListener != null) {
                    mListener.OnItemClick(null, collectionVo);
                }
            }
        });

    }

    private TextView makeTagView(final View itemView, final String tag) {
        TextView textView = new TextView(itemView.getContext());
        textView.setText(tag);
        textView.setTextColor(itemView.getResources().getColor(R.color.tag_color_selector));
        textView.setBackgroundResource(R.drawable.bg_tag_tv);
        RxView.clicks(textView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mListener != null) {
                    mListener.OnItemClick(itemView, tag);
                }
            }
        });
        return textView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterNewsIsVideoIv;
        AppCompatTextView adapterNewsTitleTv;
        WrapLayout adapterCollectionTagWl;
        AppCompatImageView adapterNewsImageIv;
        AppCompatTextView adapterNewsDateTv;
        AppCompatTextView adapterNewsImageNumTv;
        ConstraintLayout adapterTagContainerCl;
        ConstraintLayout adapterNewsContainerCl;


        public ViewHolder(View itemView) {
            super(itemView);
            adapterNewsTitleTv = itemView.findViewById(R.id.adapter_news_title_tv);
            adapterNewsImageIv = itemView.findViewById(R.id.adapter_news_image_iv);
            adapterNewsDateTv = itemView.findViewById(R.id.adapter_news_date_tv);
            adapterNewsIsVideoIv = itemView.findViewById(R.id.adapter_news_isvideo_iv);
            adapterNewsImageNumTv = itemView.findViewById(R.id.adapter_news_image_num_tv);
            adapterCollectionTagWl = itemView.findViewById(R.id.adapter_collection_tag_wl);
            adapterTagContainerCl = itemView.findViewById(R.id.adapter_tag_container_cl);
            adapterNewsContainerCl = itemView.findViewById(R.id.adapter_news_container_cl);
        }
    }
}
