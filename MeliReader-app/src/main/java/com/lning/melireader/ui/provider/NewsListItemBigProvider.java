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

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/17.
 */

public class NewsListItemBigProvider extends ItemViewBinder<NewsListVo, NewsListItemBigProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public NewsListItemBigProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_big_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsBigAuthorTv.setText(item.getPublisherName());
        holder.adapterNewsBigCommentTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterNewsBigDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterNewsBigTitleTv.setText(Html.fromHtml(item.getTitle()));
        String image = item.getImage();
        if (TextUtils.isEmpty(image)) {
            holder.adapterNewsBigImageIv.setVisibility(View.GONE);
            holder.adapterNewsBigImageNumTv.setVisibility(View.GONE);
            holder.adapterNewsBigIsVideoIv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            GlideUtils.loadImage(holder.adapterNewsBigImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
            holder.adapterNewsBigImageNumTv.setText(
                    resources.getString(R.string.tips_image_num_template, images.length));
            holder.adapterNewsBigIsVideoIv.setVisibility("video".equals(item.getCtype()) ? View.VISIBLE : View.GONE);
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

        AppCompatImageView adapterNewsBigIsVideoIv;
        AppCompatTextView adapterNewsBigTitleTv;
        AppCompatTextView adapterNewsBigImageNumTv;
        AppCompatImageView adapterNewsBigImageIv;
        AppCompatTextView adapterNewsBigDateTv;
        AppCompatTextView adapterNewsBigAuthorTv;
        AppCompatTextView adapterNewsBigCommentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterNewsBigIsVideoIv = itemView.findViewById(R.id.adapter_news_big_isvideo_iv);
            adapterNewsBigTitleTv = itemView.findViewById(R.id.adapter_news_big_title_tv);
            adapterNewsBigImageIv = itemView.findViewById(R.id.adapter_news_big_image_iv);
            adapterNewsBigImageNumTv = itemView.findViewById(R.id.adapter_news_big_image_num_tv);
            adapterNewsBigDateTv = itemView.findViewById(R.id.adapter_news_big_date_tv);
            adapterNewsBigAuthorTv = itemView.findViewById(R.id.adapter_news_big_author_tv);
            adapterNewsBigCommentTv = itemView.findViewById(R.id.adapter_news_big_comment_tv);
        }
    }
}
