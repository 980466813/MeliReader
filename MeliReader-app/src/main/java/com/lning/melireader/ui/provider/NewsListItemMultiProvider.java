package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/17.
 */

public class NewsListItemMultiProvider
        extends ItemViewBinder<NewsListVo, NewsListItemMultiProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public NewsListItemMultiProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_multi_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsMultiTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterNewsMultiCommentTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterNewsMultiDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterNewsMultiAuthorTv.setText(item.getPublisherName());
        final String image = item.getImage();
        if (!TextUtils.isEmpty(image)) {
            holder.adapterNewsMultiLl.setVisibility(View.VISIBLE);
            String[] images = image.split(",");
            for (int i = 0; i < 3; i++) {
                holder.adapterNewsImagesIv[i].setVisibility(View.VISIBLE);
                GlideUtils.loadImage(holder.adapterNewsImagesIv[i], String.format(AppConstant.IMAGE_URL, images[i]));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView adapterNewsMultiTitleTv;
        LinearLayout adapterNewsMultiLl;
        AppCompatImageView[] adapterNewsImagesIv;
        AppCompatTextView adapterNewsImageNumTv;
        AppCompatTextView adapterNewsMultiDateTv;
        AppCompatTextView adapterNewsMultiAuthorTv;
        AppCompatTextView adapterNewsMultiCommentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterNewsImagesIv = new AppCompatImageView[3];
            adapterNewsMultiLl = itemView.findViewById(R.id.adapter_news_multi_ll);
            adapterNewsMultiTitleTv = itemView.findViewById(R.id.adapter_news_multi_title_tv);
            adapterNewsImagesIv[0] = itemView.findViewById(R.id.adapter_news_image_1_tv);
            adapterNewsImagesIv[1] = itemView.findViewById(R.id.adapter_news_image_2_tv);
            adapterNewsImagesIv[2] = itemView.findViewById(R.id.adapter_news_image_3_tv);
            adapterNewsImageNumTv = itemView.findViewById(R.id.adapter_news_image_num_tv);
            adapterNewsMultiAuthorTv = itemView.findViewById(R.id.adapter_news_multi_author_tv);
            adapterNewsMultiDateTv = itemView.findViewById(R.id.adapter_news_multi_date_tv);
            adapterNewsMultiCommentTv = itemView.findViewById(R.id.adapter_news_multi_comment_tv);

        }
    }
}
