package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/17.
 */

public class NewsListItemSingleProvider extends
        ItemViewBinder<NewsListVo, NewsListItemSingleProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public NewsListItemSingleProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_single_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterNewsDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterNewsSingleCommentTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterNewsSingleAuthorTv.setText(item.getPublisherName());
        String image = item.getImage();
        if (TextUtils.isEmpty(image)) {
            holder.adapterNewsIsVideoIv.setVisibility(View.GONE);
            holder.adapterNewsImageIv.setVisibility(View.GONE);
            holder.adapterNewsImageNumTv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            holder.adapterNewsIsVideoIv.setVisibility("video".equals(item.getCtype()) ? View.VISIBLE : View.GONE);
            holder.adapterNewsImageNumTv.setText(resources.getString(R.string.tips_image_num_template, images.length));
            GlideUtils.loadImage(holder.adapterNewsImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
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

        AppCompatImageView adapterNewsIsVideoIv;
        AppCompatTextView adapterNewsTitleTv;
        AppCompatImageView adapterNewsImageIv;
        AppCompatTextView adapterNewsDateTv;
        AppCompatTextView adapterNewsImageNumTv;
        AppCompatTextView adapterNewsSingleAuthorTv;
        AppCompatTextView adapterNewsSingleCommentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterNewsIsVideoIv = itemView.findViewById(R.id.adapter_news_single_isvideo_iv);
            adapterNewsTitleTv = itemView.findViewById(R.id.adapter_news_single_title_tv);
            adapterNewsImageIv = itemView.findViewById(R.id.adapter_news_single_image_iv);
            adapterNewsDateTv = itemView.findViewById(R.id.adapter_news_single_date_tv);
            adapterNewsImageNumTv = itemView.findViewById(R.id.adapter_news_single_image_num_tv);
            adapterNewsSingleAuthorTv = itemView.findViewById(R.id.adapter_news_single_author_tv);
            adapterNewsSingleCommentTv = itemView.findViewById(R.id.adapter_news_single_comment_tv);
        }
    }
}
